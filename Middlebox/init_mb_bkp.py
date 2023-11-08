import requests, subprocess
from runprocess import runProcess
from flask import Flask, request, send_file, Response, make_response
client_list = {"7000": "asdfghc", "9088": "cvbnm", "2344": "hjklo", "5669": "qwerty"} 
app = Flask(__name__)
circuit = "HTTP_String"
url = "/function"

@app.route('/prove', methods=['POST'])
def upload_file():
	client_id = request.headers['Client-ID']
	if (client_id in client_list):
		print("Client allowed")
		random_id = request.headers['Random-ID']
		packet_num = request.headers['PacketNum']
		file = request.files['proof']
		filename = 'files/verify.'+random_id+'.'+packet_num+'.bin'
		print(filename)
		file.save(filename)
		print('Proof received! '+filename)
		print('File received successfully.')
		try:
			subprocess.run(('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuit+' pub ../Middlebox/files/transcript_'+random_id+packet_num+'.txt '+url + ' '+random_id+' '+packet_num).split()).check_returncode()
		except subprocess.CalledProcessError:
			print("Wrong java parameters! " + random_id + " " + packet_num)
		#subprocess.run(('../libsnark/build/libsnark/jsnark_interface/run_zkmb '+circuit+'.arith '+circuit+'_Sample_Run1.pub.in verify verify.'+random_id+'.'+packet_num+'.bin').split())
		#TODO: uncomment this line when java is fixed
		try:
			subprocess.run(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith '+circuit+'_'+random_id+packet_num+'.pub.in verify '+filename).split()).check_returncode()
		except subprocess.CalledProcessError:
			print("Wrong libsnark parameters! " + random_id + " " + packet_num)
			Response(status=403)
	else:
		print("CLIENT NOT ALLOWED")
		Response(status=401)

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
            
            

#invoke java to generate the circuit:
#TODO: change this line with the actual one

try:
	subprocess.run(('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuit+' pub ../Middlebox/files/test.txt /function circuitgen 1').split()).check_returncode()
	#print(returned.returncode)
	subprocess.run(('mv '+circuit+'.arith files/').split()).check_returncode()
	subprocess.run(('../libsnark/build/libsnark/jsnark_interface/run_zkmb ../Middlebox/files/'+circuit+'.arith setup').split()).check_returncode()
except subprocess.CalledProcessError:
	print("Wrong parameters, server not starting")
	exit()

print("Generation done. Starting Flask Server")
app.run(host='0.0.0.0', port=5001)
