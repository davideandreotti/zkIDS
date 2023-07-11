from hashlib import sha256
import sys, copy, math, pyshark
from datetime import datetime
import threading
sessions = {}
status={}
tcp_streams={}
transcripts={}
threads = list()
append=False
now = str(datetime.timestamp(datetime.now())).split(".")[0]

def parseApplicationData(tls_data):
	#print(tls_data)
	msgs=[]
	offset = 0
	while offset < len(tls_data):
		content_type = int(tls_data[offset:offset+1].hex(), 16)
		record_length = int(tls_data[offset+3:offset+5].hex(), 16)
		tls_message = tls_data[offset+5:offset+5+record_length]
		'''
		# Process each TLS message separately based on the content type
		if content_type == 20:
			# ChangeCipherSpec message
			print("Parsed as ChangeCipherSpec:",record_length)
		elif content_type == 21:
			# Alert message
			print("Alert:")
		elif content_type == 22:
			# Handshake message
			print("Parsed as handshake:",record_length)
		elif content_type == 23:
			# Application Data message
			print("Parsed as Application Data:", record_length)
			#if(len(tls_message)<record_length):
			#	buffer=tls_message
			#print(tls_message.hex())
			#print("-----------------")
		else: print("Error parsing", content_type, record_length)
		'''
		msgs.append([content_type, tls_message])
		offset += record_length + 5
	return msgs		
		
def toHex(payloadstr):
	return(bytearray.fromhex(payloadstr.replace(':','')))

def moreDataCondition(packet):
	#print(tcp_streams)
	if(len(tcp_streams[packet.tcp.stream])>1 
    	and packet.tcp.dstport==tcp_streams[packet.tcp.stream][-1].tcp.dstport	 #they are from the same direction 
    	and int(packet.tcp.len)>0											 #and not an ack/fin/syn...
		and packet.tcp.seq==tcp_streams[packet.tcp.stream][-2].tcp.nxtseq):	 #and sequence numbers are adjacent
		return True
	else: return False

def getTail(servExt, ch_sh): #implements the get_tail_minus_36 from ChannelShortcut
	tot=ch_sh+servExt
	output = bytearray()
	tot_len = len(tot)
	whole_blocks = math.floor((tot_len - 36) / 64)
	tail_len = tot_len - whole_blocks * 64
	output=tot[whole_blocks*64:tot_len]
	return output

def printTranscript(transcripts):
	for id, transcript in transcripts.items():
		for k,v in transcript.items():
			print(k,": ",(v.hex() if isinstance(v, (bytearray, bytes)) else v))

def elaborateAppData(packet):
	if status[packet.tcp.stream]["S_CS"] and not status[packet.tcp.stream]["SF"]: #it's still HANDSHAKE
		if (len(toHex(packet.tls.app_data)) < 60): #set this to a reasonable lowerbound that includes Mandatory Extensions, min length Certificate + Verify and Finished (53)
			msgs=parseApplicationData(toHex(packet.tcp.payload))
			if len(msgs)==6:
				status[packet.tcp.stream]["SF"] = True
				status[packet.tcp.stream]["src"]=packet.tcp.srcport
				status[packet.tcp.stream]["dst"]=packet.tcp.dstport
				print("Encrypted Ext + Certificate + Server Finished")
			else:
				print("Error")
			handshake_ct = bytearray()
			transcripts[packet.tcp.stream]['ServExt_ct_EncExt']=msgs[2][1][:-17]
			transcripts[packet.tcp.stream]['ServExt_ct_Cert']=msgs[3][1][:-17]
			transcripts[packet.tcp.stream]['ServExt_ct_CertVerify']=msgs[4][1][:-17]
			transcripts[packet.tcp.stream]['ServExt_ct_SF']=msgs[5][1][:-17]
			for msg in msgs[2:]:
				handshake_ct+=msg[1][:-17]
#			handshake_ct = handshake_ct[:-1]
			transcripts[packet.tcp.stream]['ServExt_ct']=handshake_ct
			transcripts[packet.tcp.stream]['ServExt_ct_len']=len(handshake_ct)
			transcripts[packet.tcp.stream]['ServExt_ct_tail']=getTail(handshake_ct, transcripts[packet.tcp.stream]['ch_sh'])
			print("Is this less than 48? -> ",math.floor((len(transcripts[packet.tcp.stream]['ServExt_ct'])-36)/64))
			print("Tail: ",len(transcripts[packet.tcp.stream]['ServExt_ct_tail']))
		else: #there is only one appData record layer
			print("Server Finished")
			handshake_ct=toHex(packet.tls.app_data)
			#assume SF is inside the big packet
			status[packet.tcp.stream]["SF"] = True
			status[packet.tcp.stream]["src"]=packet.tcp.srcport
			status[packet.tcp.stream]["dst"]=packet.tcp.dstport
			handshake_ct=handshake_ct[:-17]
			transcripts[packet.tcp.stream]['ServExt_ct']=handshake_ct
			transcripts[packet.tcp.stream]['ServExt_ct_len']=len(handshake_ct)
			transcripts[packet.tcp.stream]['ServExt_ct_tail']=getTail(handshake_ct, transcripts[packet.tcp.stream]['ch_sh'])
			
	elif status[packet.tcp.stream]["C_CS"] and not status[packet.tcp.stream]["CF"]: #it's still HANDSHAKE
		print("Client Finished")
		status[packet.tcp.stream]["CF"]=True 
		status[packet.tcp.stream]["src"]=packet.tcp.srcport
		status[packet.tcp.stream]["dst"]=packet.tcp.dstport
		print(status[packet.tcp.stream], transcripts[packet.tcp.stream])
	elif status[packet.tcp.stream]["CF"] and status[packet.tcp.stream]["src"]==packet.tcp.srcport: #app data after CF-> the request
		print("(",stream_id,") App Layer Request")
		transcripts[stream_id]["appl_ct"]=packet.tls.app_data
		transcripts[packet.tcp.stream]["appl_ct"]
		print(packet.tls.app_data)
		print("CIAO")


def print_transcript(transcript, stream_id):
	original_stdout = sys.stdout
	print(int(now)+int(stream_id))
	name = "middlebox_transcript"+"."+str(int(now))+"."+str(int(stream_id))+"."+str(transcript['PacketNumber'])+".txt" #OR we could use the unique stream_id...
	
	f = open("transcripts/"+name, "w")
	sys.stdout = f
	print('0'*32)	
	print('0'*32)
	print(transcript['Cx'].hex())
	print(transcript['Cy'].hex())
	print(transcript['Sx'].hex())
	print(transcript['Sy'].hex())	
	print(transcript['ServExt_ct'].hex())
	print(transcript['H2'].hex())
	print(transcript['ServExt_ct_SF'].hex())
	print(transcript['ch_sh'].hex())
	print(transcript['ch_sh_len'])
	print(transcript['ServExt_ct_EncExt'].hex())
	print(transcript['ServExt_ct_Cert'].hex())
	print(transcript['ServExt_ct_CertVerify'].hex())
	print(transcript['appl_ct'].hex())
	print(transcript['PacketNumber'])
	print('0'*32)
	f.close()
	sys.stdout = original_stdout
	return name
def call_java(name):
#	for line in runProcess(("java -cp xjsnark_decompiled/backend_bin_mod/:xjsnark_decompiled/xjsnark_bin/ xjsnark.channel_openings.ChannelShortcut pub "+name).split()):
#		print(line)
	return runProcess(("java -cp xjsnark_decompiled/backend_bin_mod/:xjsnark_decompiled/xjsnark_bin/ xjsnark.channel_openings.ChannelShortcut pub "+name).split())

def process_with_pyshark(fileName):
	tls_session={"CH": False, "SH": False, "S_CS": False, "SF": False, "C_CS": False, "CF": False, "App": 0, "src": '', "dst": ''}
	transcript={"Cx":'', "Cy":'', "Sx":'', "Sy":'', "ch_sh":'', "ch_sh_len":'',"H2":'',"ServExt_ct":'', "ServExt_ct_EncExt":'',"ServExt_ct_Cert":'',"ServExt_ct_CertVerify":'',"ServExt_ct_SF":'', "ServExt_ct_tail":'', "appl_ct":'', "PacketNumber": '' }
	#pcap_data = pyshark.FileCapture(fileName)
	capture=pyshark.LiveCapture(interface='lo', bpf_filter="tcp and not port 5001")

	ch_sh = bytearray()


	#scan all packets in the capture
	#for packet in pcap_data:
	for packet in capture.sniff_continuously():
		if 'tcp' in packet:
			stream_id=packet.tcp.stream
			if stream_id not in tcp_streams:
				print("(",stream_id,") New stream")
				tcp_streams[stream_id]=[]
				status[stream_id]=copy.deepcopy(tls_session)
			tcp_streams[stream_id].append(packet)
			
			if 'tls' in packet:
				#if hasattr(packet.tls, 'handshake') and not hasattr(packet.tls, 'handshake_session_id_length'): #???
				#	print(packet.tls)
				#	continue
				if hasattr(packet.tls, 'handshake') and int(packet.tls.handshake_session_id_length)>0:
					if packet.tls.handshake_type == '1': #Client Hello
						print("(",stream_id,") Client Hello")
						status[stream_id]['CH'] = True
						status[stream_id]['src']=packet.tcp.srcport
						status[stream_id]['dst']=packet.tcp.dstport
						#print(status)
						transcripts[stream_id]=copy.deepcopy(transcript)
						Cx=toHex(packet.tls.handshake_extensions_key_share_key_exchange)[1:33]
						transcripts[stream_id]["Cx"]=Cx
						Cy=toHex(packet.tls.handshake_extensions_key_share_key_exchange)[33:64]
						transcripts[stream_id]["Cy"]=Cy
						ch_sh = parseApplicationData(toHex(packet.tcp.payload))[0][1]
						
						#print(packet.tls._all_fields)
						#session_id = packet.tls.handshake_session_id
						client_random = packet.tls.handshake_random
						cipher_suites = packet.tls.handshake_ciphersuites
					elif packet.tls.handshake_type == '2':
						print("(",stream_id,") Server Hello")
						if status[stream_id] and status[stream_id]["CH"] and status[stream_id]["src"]==packet.tcp.dstport:
							status[stream_id]["SH"]=True
							status[stream_id]["src"]=packet.tcp.srcport
							status[stream_id]["dst"]=packet.tcp.dstport
							ch_sh+=parseApplicationData(toHex(packet.tcp.payload))[0][1]
							transcripts[stream_id]["ch_sh"]=ch_sh
							transcripts[stream_id]["ch_sh_len"]=len(ch_sh)
							Sx=toHex(packet.tls.handshake_extensions_key_share_key_exchange)[1:33]
							transcripts[stream_id]["Sx"]=Sx
							Sy=toHex(packet.tls.handshake_extensions_key_share_key_exchange)[33:64]
							transcripts[stream_id]["Sy"]=Sy
							hasher=sha256()
							hasher.update(ch_sh)
							ch_sh_hash=hasher.digest()
							transcripts[stream_id]["H2"]=ch_sh_hash
						else: print("Errors")
				if hasattr(packet.tls, 'change_cipher_spec'):
					if status[stream_id] and packet.tcp.srcport==status[stream_id]["src"]:	#it's subsequent to a server hello, so it's from SERVER
						print("(",stream_id,") Server ", end="")
						if status[stream_id]["SH"]:
							status[stream_id]["S_CS"]=True
						else: print("Errors")

						#raw_payload = bytearray.fromhex(packet.tcp.payload.replace(':','')) #saves the raw bytes of the entire tcp payload of the packet
						#msgs = parseApplicationData(raw_payload)
						'''for msg in msgs:
							if msg[0]==23:
								print("Found app data in SH")
								app_data+=msg[1]'''
						
					elif status[packet.tcp.stream] and packet.tcp.dstport==status[packet.tcp.stream]["src"]: 		#it's subsequent to a server finished -> is from the CLIENT
						print("(",stream_id,") Client ", end="")
						if status[packet.tcp.stream]["SF"]:
							status[packet.tcp.stream]["C_CS"]=True
							status[packet.tcp.stream]["src"]=packet.tcp.srcport
							status[packet.tcp.stream]["dst"]=packet.tcp.dstport
						else: print("Errors at Client_ChangeCipherSpec")
					print("Change_CipherSpec")
					if hasattr(packet.tls, 'app_data'):
						print("(",stream_id,") Application Data: ", end="")
						app_data=elaborateAppData(packet)

				
				elif not hasattr(packet.tls, 'handshake'):
					#print("Not a handshake")
					#if(append) and moreDataCondition(packet): 	#uncomment if appendMoreData is fixed and if necessary to manually rebuild tls
					#	buffer = appendMoreData(packet, buffer)

					#TODO: check this condition: Could be that SF is not contained in app_data?
					
					if (hasattr(packet.tls, 'app_data') 
	 					and status[stream_id]["src"]==packet.tcp.srcport): #if two from same direction: it's next to change_ciphertext or another app_data
						print("(",stream_id,") Application Data: ", end="")
						
						if status[packet.tcp.stream]["S_CS"] and not status[packet.tcp.stream]["SF"]: #it's still handshake
							print("Shouldnt enter here")
							if status[packet.tcp.stream]["App"]:
								print("Implement: merge all appData packets; pyshark cannot separate the 4 record layers autonomosuly, extract tcp.payload")
							
							else:
								print("Server Finished")
								handshake_ct=toHex(packet.tls.app_data)
								#assume SF is inside the big packet
								status[packet.tcp.stream]["SF"] = True
								status[packet.tcp.stream]["src"]=packet.tcp.srcport
								status[packet.tcp.stream]["dst"]=packet.tcp.dstport
								#handshake_ct=handshake_ct[:-17]
								transcripts[stream_id]['ServExt_ct']=handshake_ct
								transcripts[stream_id]['ServExt_ct_len']=len(handshake_ct)
								transcripts[stream_id]['ServExt_ct_tail']=getTail(handshake_ct, ch_sh)
						elif status[packet.tcp.stream]["C_CS"] and not status[packet.tcp.stream]["CF"]: #it's still handshake
							print("Shouldnt enter here 2")
							status[packet.tcp.stream]["CF"]=True 
							status[packet.tcp.stream]["src"]=packet.tcp.srcport
							status[packet.tcp.stream]["dst"]=packet.tcp.dstport
							print(status)
						elif status[packet.tcp.stream]["CF"] and status[packet.tcp.stream]["src"]==packet.tcp.srcport: #app data after CF-> the request
							print("App Layer Request")
							#print(packet.tls.app_data)
							status[stream_id]['App']+=1
							print(status[stream_id])
							transcripts[stream_id]['appl_ct']=toHex(packet.tls.app_data)[:-17] #Check: odd byte number
							transcripts[stream_id]['PacketNumber'] = status[stream_id]['App']
							name = print_transcript(transcripts[stream_id], stream_id)
							print("Computing inputs")
							#call_java(name)
							java_call = threading.Thread(target=call_java, args=(name,))
							threads.append(java_call)
							java_call.start()
							#TODO: consider implementing a queue for incoming new processes

					#print(status)
					#print(buffer.hex(),'\n')
					#print(packet.tls.record_length, packet.tls.app_data)

			#handle TCP streams to make sure the flow is consistent
			elif packet.tcp.stream in status and int(packet.tcp.len)>0: 
					
					print("(",stream_id,") Not TLS")
					'''if moreDataCondition(packet):
						if append:
							
							buffer = appendMoreData(packet, buffer)
						else:
							
							buffer = toHex(packet.tcp.payload)
							#print(packet.tcp)
					elif not moreDataCondition(packet) and len(buffer)>0:
						#print(buffer.hex())
						print("Finished buffering")
						#print(buffer)
						msgs=parseApplicationData(buffer)
						print('\n')
						buffer=bytearray()'''

	#printTranscript(transcripts)
	#for line in runProcess("java -cp xjsnark_decompiled/backend_bin_mod/:xjsnark_decompiled/xjsnark_bin/ xjsnark.channel_openings.ChannelShortcut pub".split()):
	#	print(line)
	
import threading
from runprocess import runProcess
print("HELO")
pcap_file = "serverone_new.pcapng" #when capturing remember to BPF filter by destination ip and port!
capturer = threading.Thread(target=process_with_pyshark, args=(pcap_file,))
#process_with_pyshark(pcap_file)
threads.append(capturer)
capturer.start()
	

