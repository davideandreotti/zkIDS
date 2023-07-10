import requests, subprocess
from runprocess import runProcess
from flask import Flask, request, send_file, Response, make_response
client_list = {"7000": "asdfghc", "9088": "cvbnm", "2344": "hjklo", "5669": "qwerty"} 
app = Flask(__name__)

@app.route('/prove', methods=['POST'])
def upload_file():
	client_id = request.headers['Client-ID']
	if (client_id in client_list):
		print(request.headers['Client-ID'])
		print(request.headers['Random-ID'])
		file = request.files['proof']
		print('files/verify.'+request.headers['Random-ID']+'.'+request.headers['PacketNum']+'.bin')
		file.save('files/verify.'+request.headers['Random-ID']+'.'+request.headers['PacketNum']+'.bin')
		print('File uploaded successfully.')
	circuit = "DNS_Shortcut"
	print('./libsnark/build/libsnark/jsnark_interface/run_zkmb '+circuit+'.arith '+request.headers['Random-ID']+'.'+request.headers['PacketNum']+'.in verify')
	#for line in runProcess(('./libsnark/build/libsnark/jsnark_interface/hello gg '+circuit+'.arith '+circuit+'_Sample_Run1.in verify').split()):
	#	print(line)
	return Response(status=200)
	
@app.route('/prover-key', methods=['GET'])
def return_file():
	if(request.headers['Client-ID'] in client_list):
		print(request.headers['Client-ID'])
		response = make_response(send_file("files/provKey.bin", mimetype='application/octet-stream'))
		response.headers['Client-Token'] = client_list[request.headers['Client-ID']]
		return response
	else:
		return Response("{'Error':'Client not in list'}", status=200, mimetype='application/json')
            
            
circuit = "DNS_Shortcut_doh_get"
#invoke java to generate the circuit:
#TODO: change this line with the actual one
print('java -Xmx6g -cp bin:xjsnark_backend.jar xjsnark.e2eDNS.'+circuit)
#for line in runProcess(('java -Xmx6g -cp bin:xjsnark_backend.jar xjsnark.e2eDNS.'+circuit).split()):
returned = runProcess("echo ciao".split())
    
#for line in runProcess(('./libsnark/build/libsnark/jsnark_interface/run_zkmb '+circuit+'.arith setup').split()):
returned = runProcess("echo ciao2".split())

print("Generation done. Starting Flask Server")
app.run(host='0.0.0.0', port=5001)


#print("Sending key to Client...")
#file_path = "provKey.bin"
#url = "http://127.0.0.1:5001/upload"
#send_file(file_path, url, circuit)

