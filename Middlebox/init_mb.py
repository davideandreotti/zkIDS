import requests, subprocess
from runprocess import runProcess
from flask import Flask, request, send_file, Response, make_response
client_list = {"7000": "asdfghc", "9088": "cvbnm", "2344": "hjklo", "5669": "qwerty"} 
app = Flask(__name__)
circuit = "HTTP_String"

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

	subprocess.Popen(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+request.headers['Random-ID']+'.'+request.headers['PacketNum']+'.in verify').split())
	#for line in runProcess(('./libsnark/build/libsnark/jsnark_interface/hello gg '+circuit+'.arith '+circuit+'_Sample_Run1.in verify').split()):
	#	print(line)
	return Response(status=200)
	
@app.route('/prover-key', methods=['GET'])
def return_file():
	if(request.headers['Client-ID'] in client_list):
		print(request.headers['Client-ID'])
		response = make_response(send_file("provKey.bin", mimetype='application/octet-stream'))
		response.headers['Client-Token'] = client_list[request.headers['Client-ID']]
		return response
	else:
		return Response("{'Error':'Client not in list'}", status=200, mimetype='application/json')
            
            

#invoke java to generate the circuit:
#TODO: change this line with the actual one
subprocess.run(('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuit+' pub ../Middlebox/files/test.txt /function').split())
subprocess.run(('mv '+circuit+'.arith files/').split())
subprocess.run(('../libsnark/build/libsnark/jsnark_interface/run_zkmb ../Middlebox/files/'+circuit+'.arith setup').split())

print("Generation done. Starting Flask Server")
app.run(host='0.0.0.0', port=5001)
