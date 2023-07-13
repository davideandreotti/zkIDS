import sys, os
sys.path.insert(0, "./tlslite-ng-0.8.0-alpha40")
from socket import *
from tlslite import TLSConnection, HandshakeSettings, HTTPTLSConnection, messages
from tlslite.api import *
from tlslite.constants import *
from cryptography.hazmat.primitives.ciphers import (Cipher, algorithms, modes) 
import sha2_compressions
import json, time, subprocess

#SERVER = 'server'
SERVER = 'localhost'
pathstr = '/pippo'
allowed = "/function"
circuitname = "HTTP_String"
# takes bytearray key, iv as input; string plaintext
def encrypt_aes_gcm(key, iv, plaintext):
	encryptor = Cipher(
		algorithms.AES(key),
		modes.GCM(iv),
	).encryptor()
	pippo =  encryptor.update(bytes.fromhex(plaintext))
	#print("CIPHERTEXT:", pippo)
	mario = encryptor.finalize()
	#print("TAG: ",mario)
	ciphertext = pippo + mario
	return ciphertext
# Given a TLS connection established using tlslite-ng,
# this function extracts the required test values
def get_test_values(tlsconn):
	# We have configured tlsconn to store the following values.
	psk             = tlsconn.psk
	ec_sk           = tlsconn.clientPrivate
	ec_pk_client    = tlsconn.clientPublic
	ec_pk_server    = tlsconn.serverPublic
	hs              = tlsconn.handshakeSecret
	ch_sh           = tlsconn.serverHelloTranscript
	H_2             = tlsconn.serverHelloTranscriptHash
	H_7             = tlsconn.serverExtensionsTranscriptHash
	SF              = tlsconn.serverFinishedValue
	H_3             = tlsconn.serverFinishedTranscriptHash

	len_sh = len(tlsconn.serverHelloTranscript)

	# server's handshake key
	tk_shs = tlsconn.serverHSKey
	iv_shs = tlsconn.serverHSIV
	ctx=[]
	ptx=[tlsconn.handshakeEncryptedExtensionsRecordLayer, tlsconn.handshakeCertificateRecordLayer, tlsconn.handshakeCertificateVerifyRecordLayer, tlsconn.handshakeFinishedRecordLayer]
	counter = bytearray(len(iv_shs))
	for p in ptx:
		xorato = bytearray(b1 ^ b2 for b1, b2 in zip(iv_shs, counter))
		ctx.append(encrypt_aes_gcm(tk_shs, xorato, p).hex())
		counter[-1]+=1
	#print(ctx)

	#print(tlsconn.serverExtensionsTranscript)
	#print(ptx)

	# client application key
	# used for verification
	c_ap_key = tlsconn._recordLayer._writeState.encContext.key
	c_ap_iv = tlsconn._recordLayer._writeState.fixedNonce

	# tlsconn stores the plaintext of the transcripts sent
	# A bit of a hack, but  we just encrypt them to get the ciphertexts that are input to the circuit
	tr_7 = tlsconn.serverExtensionsTranscript # CH || SH || Extensions_without_SF_value --------------------> This one is way bigger in DNS.Google than in Cloudflare
	ct_7 = encrypt_aes_gcm(tk_shs, iv_shs, tr_7[len_sh:]).hex()
	#print('\n\n',tlsconn.messages[2].extensions)
	
	
	# obtain ct_3, the encrypted part of tr3
	tr3 = tlsconn.serverFinishedTranscript # CH || SH || Extensions_with_SF_value
	#print("tlsconn.serverHSIV: ", tlsconn.serverHSIV)

	ct_3 = encrypt_aes_gcm(tlsconn.serverHSKey, tlsconn.serverHSIV, tr3[len_sh:]).hex()
	# This function returns the checkpoint SHA256 state (H values) for tr7 
	# By checkpoint, this means this is the H-value at the last whole SHA block of tr7 (without padding)
	H_state_tr7 = sha2_compressions.get_H_state(tlsconn.serverExtensionsTranscript)

	# the rest of this recovers the DNS application ciphertext
	# (and the plaintext for testing)
	ciphertexts = tlsconn._recordLayer.ciphertextMessage

	plaintexts = tlsconn._recordLayer.plaintextMessage
	#print(plaintexts[3].hex)
	assert len(ciphertexts) > 0
	#TODO: this is a hack to decide which method was used
	if len(ciphertexts) == 4:
		#four entries means dot or doh get.
		dns_ct = ciphertexts[3].write().hex()
		# 16 bytes for tag + 1 byte for message type
		dns_ciphertext = dns_ct[0:-34]
		dns_plaintext = plaintexts[3].hex()
	elif len(ciphertexts) >= 5: #NOTE: I put >=5 to support sending files; the first chunk should contain headers and url, that's enough.
		#five entries means doh post.
		dns_cts = ciphertexts[3:]
		dns_ciphertext = list(map(lambda x : x.write().hex()[0:-34], dns_cts))
		dns_pt1 = plaintexts[3].hex()
		c_ap_iv1 = bytearray(c_ap_iv)
		c_ap_iv1[-1] ^= 1
		dns_pt2 = plaintexts[4].hex()
		dns_plaintext = dns_pt1 + dns_pt2
	else:
		raise Exception("We only support cases where four or five messages are sent, " +\
						" and there are either < 4 or >5 messages.")

	test_dict = {
		"psk": psk,
		"ec_sk": ec_sk[2:],
		"ec_pk_client_x": ec_pk_client[2:66],
		"ec_pk_client_y": ec_pk_client[66:],
		"ec_pk_server_x": ec_pk_server[2:66],
		"ec_pk_server_y": ec_pk_server[66:],
		"HS": hs,
		"H_2": H_2,
		"ct_7": ct_7,
		"H_7": H_7,
		"SF": SF,
		"ch_sh": ch_sh,
		"ct_3": ctx,
		"H_3": H_3, 
		"dns_ciphertext": dns_ciphertext,
		"s_hs_key": tlsconn.serverHSKey.hex(),
		"s_hs_iv": tlsconn.serverHSIV.hex(),
		"c_ap_key": c_ap_key.hex(),
		"c_ap_iv": c_ap_iv.hex(),
		"dns_plaintext": dns_plaintext,
		"H_state_tr7": H_state_tr7
	}

	return test_dict
def print_test(test_dict):
	print(test_dict['psk'])
	print(test_dict['ec_sk'])
	print(test_dict['ec_pk_client_x'])
	print(test_dict['ec_pk_client_y'])
	print(test_dict['ec_pk_server_x'])
	print(test_dict['ec_pk_server_y'])
	print(test_dict['HS'])
	print(test_dict['H_2'])
	print(test_dict['H_7'])
	print(test_dict['H_3'])
	print(test_dict['SF'])
	print(test_dict['ch_sh'])
	for c in test_dict['ct_3']:
		print(c)
	print(test_dict['dns_ciphertext'])
	print(test_dict['H_state_tr7'])
	print("******** EXPECTED VALUES BELOW ********")
	print("plaintext: " + test_dict['dns_plaintext'])
	print("H3: " + test_dict['H_3'])
	print("s hs key: " + test_dict['s_hs_key'])
	print("s hs iv: " + test_dict['s_hs_iv'])
	print("c ap key: " + test_dict['c_ap_key'])
	print("c ap iv: " + test_dict['c_ap_iv'])
	
def make_tls_connection(pathstr, keepalive):
	oursettings = HandshakeSettings()
	oursettings.usePaddingExtension = False
	#oursettings.method_flag_for_padding = "doh"
	oursettings.versions = [(3,4)]
	oursettings.cipherNames = ["aes128gcm"]
	oursettings.eccCurves = ["secp256r1"]
	oursettings.keyShares = ["secp256r1"]
	oursettings.usePaddingExtension = False
	# oursettings.eccCurves = ["x25519"]
	# oursettings.keyShares = ["x25519"]
	packetNumber = 1
	http_conn = HTTPTLSConnection(SERVER, 443, settings=oursettings, printhandshake=True)
	tls_conn=None
	headers = {
		"Client-Token": "aabbcc",
		"Host": "oo", 
		"Accept-Encoding": "application/binary"
	}
	#http_conn.request("POST", pathstr, open("ebpf.pdf","rb"), headers)
	#this is the correct command, uncomment after
	http_conn.request('GET', pathstr, body=None, headers=headers)
	
	
	
	resp = http_conn.getresponse()
	resp_raw = http_conn.sock.read()
	tls_conn=http_conn.sock


	'''
	tls_session=tls_conn.session
	oursettings.is_resumption_for_padding = True
	http_conn2 = HTTPTLSConnection(SERVER, 443, settings=oursettings, printhandshake=True, resumptionsession=tls_session)
	http_conn2.request('GET', pathstr)
	resp2 = http_conn2.getresponse()
	resp2_raw = http_conn2.sock.read()
	tls_conn2=http_conn2.sock
	'''

	dict=get_test_values(tls_conn) #PSK is not set in the first session creation
	original_stdout = sys.stdout
	filename = ("transcript_baseline."+tls_conn._clientRandom.hex()+str(packetNumber)+".txt")
	f = open("files/"+filename, "w")
	sys.stdout = f
	print_test(dict)
	f.close()

	'''
	f = open("transcript_resumption.txt", "w")
	sys.stdout = f
	print_test(get_test_values(tls_conn2)) #PSK is now set! Previous session was resumed as specified on resumptionsession variable!
	f.close() '''
	sys.stdout = original_stdout
	print("Generating circuit and parameters...")
	subprocess.run(('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuitname+' run ../Client/files/'+filename+" "+allowed).split())
	print("PROOF COMPUTED!")
	subprocess.run(('../libsnark/build/libsnark/jsnark_interface/run_zkmb '+circuitname+'.arith '+circuitname+'_Sample_Run1.in prove '+tls_conn._clientRandom.hex()+" 1").split())
	

	#print(output)

	return (tls_conn._clientRandom, ['1'])


if __name__ == '__main__':
	make_tls_connection('/function/run',False)

