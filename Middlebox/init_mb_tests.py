import requests, subprocess, sys, os, time, json
sys.path.insert(0, "./Merkle Proof")
from runprocess import runProcess
from flask import Flask, request, send_file, Response, make_response
from membership_proofs import *
from trackers import *
client_list = {"7000": "asdfghc", "9088": "cvbnm", "2344": "hjklo", "5669": "qwerty"} 
client_url = {"7000": "/function", "9088": "/notfunction", "2344": "/function/run", "5669": "/otherpath"} 
#client_circuit = {"7000": "/function", "9088": "/notfunction", "2344": "/function/run", "5669": "/otherpath"} 
app = Flask(__name__)
circuit = ""
url = ""
merkle=False
token=False
anon = True
start_time = time.time()
@app.route('/prove', methods=['POST'])
def upload_file():
	client_id = request.headers['Client-ID']
	if (client_id in client_list):
		print("Client allowed")
		start_time=time.time()
		out2=[["Verification starts now", time.time()-start_time]]
		random_id = request.headers['Random-ID']
		packet_num = request.headers['PacketNum']
		file = request.files['proof']
		filename = 'files/verify.'+random_id+'.'+packet_num+'.bin'
		print(filename)
		file.save(filename)
		print('Proof received! '+filename)
		print('File received successfully.')
		out2= out2 + [["Proof received", time.time()-start_time]]
		print(out2)
		if (merkle):
			print(merkle, "Merkle")
			if(token):
				print(token, "Token")
				circuit = 'HTTP_Merkle_Token'
				print(type(random_id), type(packet_num))
				jrun = (('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuit+' pub ../Middlebox/files/transcript_'+random_id+packet_num+'.txt '+'../Middlebox/files/merkle_proof_pub.txt '+client_list[client_id] + ' ' + random_id+' '+packet_num).split())

			else:
				circuit = 'HTTP_Merkle'
				jrun = (('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuit+' pub ../Middlebox/files/transcript_'+random_id+packet_num+'.txt '+'../Middlebox/files/merkle_proof_pub.txt placeholder '+random_id+' '+packet_num).split())

		else:
			print(merkle, token, "String")
			circuit = 'HTTP_String'
			jrun = (('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuit+' pub ../Middlebox/files/transcript_'+random_id+packet_num+'.txt '+client_url[client_id]+' '+random_id+' '+packet_num).split())


		
		try:
			#subprocess.run(jrun).check_returncode()
			(out_tmp, mem_tmp, cpu_time) = trackRun_cputime(jrun, "xjsnark_verify"+circuit, [start_time, 0])
			out = out2 + out_tmp
			mem = mem_tmp
		except subprocess.CalledProcessError:
			print("Wrong java parameters! " + random_id + " " + packet_num)
		try:
			(out_tmp, mem_tmp, cpu_time2)=trackRun_cputime(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+circuit+'_'+random_id+packet_num+'.pub.in verify '+filename).split(), "libsnark_verify"+circuit, [start_time, out[-1][2]])
			cpu_time +=cpu_time2
			out = out + out_tmp
			mem = mem + mem_tmp
			#subprocess.run(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith '+circuit+'_'+random_id+packet_num+'.pub.in verify '+filename).split()).check_returncode()
		except subprocess.CalledProcessError:
			print("Wrong libsnark parameters! " + random_id + " " + packet_num)
			Response(status=403)
	else:
		print("CLIENT NOT ALLOWED")
		Response(status=401)
	with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/cputime_"+circuit+"_libsnark_verify.json", 'w', encoding='utf-8') as f:
		json.dump(cpu_time, f, ensure_ascii=False, indent=4)
	with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/verify_"+circuit+"_output.json", 'w', encoding='utf-8') as f:
		json.dump(out, f, ensure_ascii=False, indent=4)
	with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/verify_"+circuit+"_memory.json", 'w', encoding='utf-8') as f:
		json.dump(mem, f, ensure_ascii=False, indent=4)
	return Response(status=200)

@app.route('/prover-key', methods=['GET'])
def return_file():
	if(request.headers['Client-ID'] in client_list):
		print(request.headers['Client-ID'])
		response = make_response(send_file("files/provKey.bin", mimetype='application/octet-stream'))
		response.headers['Client-ID'] = client_list[request.headers['Client-ID']]
		return response
	else:
		return Response(status=401)
		
@app.route('/parameters', methods=['GET'])
def return_params():
	if(request.headers['Client-ID'] in client_list):
		print(request.headers['Client-ID'])
		if(merkle):
			#TODO: generate tree and root file
			response = make_response(send_file("files/generated_merkle_tree.txt", mimetype='text/plain'))
			response.headers['Anonymized-Tree'] = ("True" if anon else "")
			if(token):
				response.headers['Client-Token'] = client_list[request.headers['Client-ID']]
		else:
			response = Response(status=200)
			response.headers['Allowed-URL'] = client_url[request.headers['Client-ID']]
		return response
	else:
		return Response(status=401)
		
@app.route('/url-list', methods=['GET'])
def return_urllist():
	if(request.headers['Client-ID'] in client_list):
		print(request.headers['Client-ID'])
		if(anon):
			#TODO: generate tree and root file
			response = make_response(send_file("files/anon_tree.txt", mimetype='text/plain'))
		else:
			response = make_response(send_file("files/allowlist.txt", mimetype='text/plain'))
		return response
	else:
		return Response(status=401)

run = sys.argv[1]
if (len(sys.argv)>2 and sys.argv[2]=='merkle'):
	
	if(len(sys.argv)>3 and sys.argv[3]=='token'):
		circuit = 'HTTP_Merkle_Token'
		token=True
		merkle=True
		jrun = (('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuit+' pub ../Middlebox/files/test.txt ../Middlebox/files/merkle_proof_pub.txt token circuitgen 1').split())
		lrun = (('../libsnark/build/libsnark/jsnark_interface/run_zkmb ../Middlebox/files/'+circuit+'.arith setup').split())
	else:
		circuit = 'HTTP_Merkle'
		merkle=True
		jrun = (('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuit+' pub ../Middlebox/files/test.txt ../Middlebox/files/merkle_proof_pub.txt /function circuitgen 1').split())
		lrun = (('../libsnark/build/libsnark/jsnark_interface/run_zkmb ../Middlebox/files/'+circuit+'.arith setup').split())
	compute_tree('files/allowlist.txt', anon)
else:
	circuit = 'HTTP_String'
	jrun = (('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.'+circuit+' pub ../Middlebox/files/test.txt /function circuitgen 1').split())
	lrun = (('../libsnark/build/libsnark/jsnark_interface/run_zkmb ../Middlebox/files/'+circuit+'.arith setup').split())
jname = "xjsnark_setup_"+circuit+".json"
os.makedirs(os.path.dirname("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/"), exist_ok=True)
try:
	print("Running Java")
	start_time=time.time()
	(out_tmp, mem_tmp, cpu_time)=trackRun_cputime(jrun, jname, [start_time, 0])
	with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/cputime_"+circuit+"_java.json", 'w', encoding='utf-8') as f:
		json.dump(cpu_time, f, ensure_ascii=False, indent=4)
	out=out_tmp
	mem=mem_tmp
	#start_time+=out[-1][1]
	#subprocess.run(('mv '+circuit+'.arith files/').split()).check_returncode()
	subprocess.run(('rm files/'+circuit+'_circuitgen1.pub.in').split()).check_returncode()
	
except subprocess.CalledProcessError:
	print("Wrong parameters, server not starting")
	exit()
print("Running Libsnark")
lname = "libsnark_setup_"+circuit
(out_tmp, mem_tmp, cpu_time)=trackRun_cputime(lrun, lname, [start_time, out[-1][2]])
out = out+out_tmp
out +=[["PK Size", os.path.getsize('files/provKey.bin')]]
out +=[["VK Size", os.path.getsize('files/veriKey.bin')]]
mem=mem+mem_tmp
#start_time+=out[-1][1]
#print(start_time)
with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/cputime_"+circuit+"_libsnark_setup.json", 'w', encoding='utf-8') as f:
	json.dump(cpu_time, f, ensure_ascii=False, indent=4)
with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/setup_"+circuit+"_output.json", 'w', encoding='utf-8') as f:
	json.dump(out, f, ensure_ascii=False, indent=4)
with open("../Tests/outputs/full_simulations/"+circuit+"/run"+str(run)+"/setup_"+circuit+"_memory.json", 'w', encoding='utf-8') as f:
	json.dump(mem, f, ensure_ascii=False, indent=4)
print("Generation done. Starting Flask Server")
app.run(host='0.0.0.0', port=5001)
