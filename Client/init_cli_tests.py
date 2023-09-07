import requests, time, os, sys, time
sys.path.insert(0, "../Middlebox")
from tls import *
from trackers import *
#client_id = '7000'
#host = "middlebox"
host = 'localhost'
circuit = ""
client_token = ""
url_wildcard = ""
list_path = ""
tree_path = ""
start_time = time.time()

def send_file(file_path, url, headers):
	with open(file_path, 'rb') as file:
		files = {'proof': file}
		response = requests.post(url, files=files, headers=headers)
		if response.status_code == 200:
			print("File sent successfully.")
		else:
			print("Error sending file. Status code:", response.status_code)

def save_file(response, path):
	with open(path, 'wb') as file:
		file.write(response.content)



def main(host, server):
	global circuit 
	global client_token 
	global url_wildcard 
	global list_path
	global start_time
	anon = ""
	run = sys.argv[1]
	print(run)
	#client_id = input("Insert Client-ID:\n")
	client_id = "7000"
	print("Connecting to middlebox as client "+client_id)
	#url = "http://"+host+":5001/prover-key"
	headers = {'Client-ID': client_id}
	c=0
	while True:
		try:
			start_time = time.time()
			outputs=[["Request sent", time.time()-start_time, 0]]
			memory = [[0, time.time()-start_time, 0]]
			response_key = requests.get("http://"+host+":5001/prover-key", headers=headers)
			response_params = requests.get("http://"+host+":5001/parameters", headers=headers)
		except requests.ConnectionError:
			c+=1
			if c==1: 
				print("Retrying until the IDS goes online...")
			time.sleep(2)
			continue
		print("Connected!")
		break
	# Save the response file
	save_file(response_key, 'files/provKey.bin')
	print("Circuit saved as: provKey.bin")
	outputs+=[["Key received", time.time()-start_time, 0]]
	memory +=[[0, time.time()-start_time, 0]]
	if 'Anonymized-Tree' in response_params.headers:
		response_tree = requests.get("http://"+host+":5001/url-list", headers=headers)
		anon = response_params.headers.get('Anonymized-Tree')
		tree_path = 'files/generated_merkle_tree.txt'
		list_path = 'files/allowlist.txt'
		save_file(response_params, tree_path)
		print("Tree saved as: "+tree_path)
		if (anon!=''):
			list_path = 'files/anon_list.txt'
		save_file(response_tree, list_path)
		if 'Client-Token' in response_params.headers:
			circuit = 'HTTP_Merkle_Token'
			client_token = response_params.headers.get('Client-Token')
			print("Client Token: ", client_token)
		else:
			circuit = 'HTTP_Merkle'
	elif 'Allowed-URL' in response_params.headers:
		url_wildcard = response_params.headers.get('Allowed-URL')
		print("Allowed URL wildcard: ", url_wildcard)
		circuit = 'HTTP_String'
	outputs+=[["Merkle computations done", time.time()-start_time, 0]]
	memory += [[0, time.time()-start_time, 0]]
	
	#if client_token:
	#	with open('token.txt', 'w') as token_file:
	#		token_file.write(client_token)
	#	print("Authentication succeeded, Client-Token obtained.")
	#else:
	#	print("Error, Client-Token not found")
	while True:
		prompt=input("Setup done. Press enter to generate request or 'q' to quit: ")
		if prompt.lower() == "":
			function = "/function/run"
		else: 
			function = prompt.lower()
		if prompt.lower() == "q":
			print("Exiting the program.")
			break
		else:
			keepalive = False
			print("Sending HTTP request(s) to "+function)
			time_placeholder = time.time()-start_time
			#mem += [[],[]]
			(random_id, numPackets, out, mem, cpu_times) = make_tls_connection(server, function, keepalive, circuit, list_path, url_wildcard, anon, client_token, True)
			for item in out:
				item[1] += time_placeholder
			for item in mem:
				item[1] += time_placeholder
			outputs += out
			memory += mem
			os.makedirs(os.path.dirname("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/"), exist_ok=True)
			with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/cputime_"+circuit+"_libsnark_prove.json", 'w', encoding='utf-8') as f:
				json.dump(cpu_times, f, ensure_ascii=False, indent=4)
			with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/prove_"+circuit+"_output.json", 'w', encoding='utf-8') as f:
				json.dump(outputs, f, ensure_ascii=False, indent=4)
			with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/prove_"+circuit+"_memory.json", 'w', encoding='utf-8') as f:
				json.dump(memory, f, ensure_ascii=False, indent=4)
			for pkt in numPackets:
				print(pkt)
				file_path = "files/proof"+random_id.hex()+pkt+".bin"
				url = "http://"+host+":5001/prove"
				print("Proof sent!")
				headers = {'Client-ID': client_id, 'Random-ID':random_id.hex(), 'PacketNum': pkt}
				send_file(file_path, url, headers)

	   

if __name__=='__main__':
	if (len(sys.argv)>2 and sys.argv[2]=='docker'):
		main("middlebox", "server")
	else:
		main("localhost", "localhost")
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

