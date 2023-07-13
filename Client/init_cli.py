import requests, time
from tls import *
client_id = '9088'
#host = "middlebox"
host = 'localhost'
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



def main():
	print("Connecting to middlebox as client "+client_id)
	url = "http://"+host+":5001/prover-key"
	headers = {'Client-ID': client_id}
	while True:
		try:
			response = requests.get(url, headers=headers)
		except requests.ConnectionError:
			print("Retrying...")
			time.sleep(2)
			continue
		print("Connected!")
		break
	# Save the response file
	save_file(response, 'files/provKey.bin')
	print("Circuit saved as: test.bin")
	# Retrieve the Client-Token header
	client_token = response.headers.get('Client-Token')
	if client_token:
		with open('token.txt', 'w') as token_file:
			token_file.write(client_token)
		print("Authentication succeeded, Client-Token obtained.")
	else:
		print("Error, Client-Token not found")


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
			(random_id, numPackets) = make_tls_connection(function, keepalive)
			for pkt in numPackets:
				print(pkt)
				file_path = "files/proof"+random_id.hex()+pkt+".bin"		#TODO: must be replaced with proof+random_id+numpacket+.txt
				url = "http://"+host+":5001/prove"
				print("Random ID: "+random_id.hex())
				headers = {'Client-ID': client_id, 'Random-ID':random_id.hex(), 'PacketNum': pkt}
				send_file(file_path, url, headers)

	   

if __name__=='__main__':
	main()
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

