import subprocess, psutil, time, json, pandas, math, numpy, threading, os, random, sys
import matplotlib.pyplot as plt
import matplotlib.cm as cm
import requests
sys.path.insert(0, "../Middlebox/Merkle Proof")
from membership_proofs import *

exec_memory=[]
#cpu_time = 0
cpu_current_time = 0

def trackMem(popen, cputime):

	global cpu_time
	while popen.poll() is None:
		proc = psutil.Process(popen.pid)
		java_memory_usage = proc.memory_full_info().uss / 1000000
		#print(proc.cpu_times())
		cpu_time = proc.cpu_times().user + proc.cpu_times().system
		#print(cpu_time)
		#print(java_memory_usage)

		if cputime:
			exec_memory.append([java_memory_usage, time.time()-current_time, cpu_time+cpu_current_time])
		else:
			exec_memory.append([java_memory_usage, time.time()-current_time])
		#print(exec_memory[-1][1])
		time.sleep(1)
	popen.wait()
	print("Process finished")



#df = pandas.read_json('data.json')
def trackRun(cmd, outname, call_time):
	exec_output = []
	global exec_memory
	global cpu_time
	exec_memory = []
	#print(call_time)
	global current_time
	current_time = call_time
	#exec_memory=[]
	popen = subprocess.Popen(cmd, stdout=subprocess.PIPE, universal_newlines=True)
	print("Starting ",cmd)
	thread = threading.Thread(target=trackMem, args=[popen, False])
	thread.start()
	for line in iter(popen.stdout.readline, ""):
		#print(line)

		instant = time.time()-current_time
		try:
			cpu_time = proc.cpu_times().user + proc.cpu_times().system
			java_memory_usage = psutil.Process(popen.pid).memory_full_info().uss / 1000000
			exec_memory.append([java_memory_usage, instant, cpu_time + cpu_current_time])
			#print(java_memory_usage)
			exec_output.append([line, instant, cpu_time + cpu_current_time])
		except psutil.NoSuchProcess:
			print("\n WARNING: Process already ended\n")
			pass
			
	thread.join()
	#print("done, ", exec_memory)
	exec_output.append(["Done", time.time()-current_time, cpu_time + cpu_current_time])

	'''
	current_time=time.time()
	popen = subprocess.Popen(cmd, stdout=subprocess.PIPE, universal_newlines=True)
	print("Executing memory profiling run...")
	while popen.poll() is None:
		java_memory_usage = psutil.Process(popen.pid).memory_full_info().uss / 1000000
		print(java_memory_usage)
		exec_memory.append([java_memory_usage, time.time()-current_time])
		time.sleep(1)
	popen.wait()
	'''
	#with open(outname+"_output.json", 'w', encoding='utf-8') as f:
	#    json.dump(exec_output, f, ensure_ascii=False, indent=4)
	#with open(outname+"_memory.json", 'w', encoding='utf-8') as f:
	#    json.dump(exec_memory, f, ensure_ascii=False, indent=4)
	return(exec_output, exec_memory, cpu_time)

def trackRun_cputime(cmd, outname, call_time):
	exec_output = []
	global exec_memory
	global cpu_time
	exec_memory = []
	#print(call_time)
	global current_time
	global cpu_current_time
	current_time = call_time[0]
	cpu_current_time = call_time[1]
	#exec_memory=[]
	popen = subprocess.Popen(cmd, stdout=subprocess.PIPE, universal_newlines=True)
	proc = psutil.Process(popen.pid)
	print("Starting ",cmd)
	thread = threading.Thread(target=trackMem, args=[popen, True])
	thread.start()
	for line in iter(popen.stdout.readline, ""):
		#print(line)
		instant = time.time()-current_time
		try:
			cpu_time = proc.cpu_times().user + proc.cpu_times().system
			java_memory_usage = psutil.Process(popen.pid).memory_full_info().uss / 1000000
			exec_memory.append([java_memory_usage, instant, cpu_time+cpu_current_time])
			#print(java_memory_usage)
			exec_output.append([line, instant, cpu_time+cpu_current_time])
		except psutil.NoSuchProcess:
			print("\n\n WARNING!!!!!!!!!! Process already ended\n\n")
			pass
			
	thread.join()
	#print("done, ", exec_memory)
	exec_output.append(["Done", time.time()-current_time, cpu_time+cpu_current_time])

	'''
	current_time=time.time()
	popen = subprocess.Popen(cmd, stdout=subprocess.PIPE, universal_newlines=True)
	print("Executing memory profiling run...")
	while popen.poll() is None:
		java_memory_usage = psutil.Process(popen.pid).memory_full_info().uss / 1000000
		print(java_memory_usage)
		exec_memory.append([java_memory_usage, time.time()-current_time])
		time.sleep(1)
	popen.wait()
	'''
	#with open(outname+"_output.json", 'w', encoding='utf-8') as f:
	#    json.dump(exec_output, f, ensure_ascii=False, indent=4)
	#with open(outname+"_memory.json", 'w', encoding='utf-8') as f:
	#    json.dump(exec_memory, f, ensure_ascii=False, indent=4)
	return(exec_output, exec_memory, cpu_time)

def run_looped_tests_string(circuit, num):
	path = "../Tests/outputs/"+circuit+"/run"+str(num)
	
	os.makedirs(os.path.dirname(path+"/output"), exist_ok=True)
	os.makedirs(os.path.dirname(path+"/memory"), exist_ok=True)
	
	pathj = path+"/cputimes_java_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathj) and os.stat(pathj).st_size != 0:
		with open(pathj, 'w') as file:
			file.truncate()
	else:
		with open(pathj, 'w') as file:
			pass
	pathls = path+"/cputimes_libsnakr_setup_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathls) and os.stat(pathls).st_size != 0:
		with open(pathls, 'w') as file:
			file.truncate()
	else:
		with open(pathls, 'w') as file:
			pass
	pathlp = path+"/cputimes_libsnark_prove_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathlp) and os.stat(pathlp).st_size != 0:
		with open(pathlp, 'w') as file:
			file.truncate()
	else:
		with open(pathlp, 'w') as file:
			pass
	pathlv = path+"/cputimes_libsnark_verify_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathlv) and os.stat(pathlv).st_size != 0:
		with open(pathlv, 'w') as file:
			file.truncate()
	else:
		with open(pathlv, 'w') as file:
			pass
	
	
		
	for i in [200, 400, 600, 800, 1000, 1500, 2000]:
		start_time = time.time()

		(out, mem, cpu_time) = trackRun_cputime(("java -Xmx6G -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck."+circuit+" run tls_data.txt /function run"+str(i)+" 1 "+str(i)+" 20").split(), "", [start_time, 0])
		with open(pathj, 'a') as file:
			file.write(str(cpu_time) + '\n')
		print("Tot CPU Time: ",cpu_time)
		with open(path+"/output_java_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open(path+"/memory_java_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
		
		#subprocess.run(("mv files/Test_"+circuit+".arith .").split())
		(out, mem, cpu_time) = trackRun_cputime(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith setup').split(), "", [time.time(), 0])
		out +=[["PK Size", os.path.getsize('files/provKey.bin')]]
		out +=[["VK Size", os.path.getsize('files/veriKey.bin')]]
		with open(pathls, 'a') as file:
			file.write(str(cpu_time) + '\n')
		with open(path+"/output_libsnark_setup_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open(path+"/memory_libsnark_setup_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
			
		(out, mem, cpu_time) = trackRun_cputime(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+circuit+'_run'+str(i)+'1.in prove run'+str(i)+' 1').split(), "", [time.time(), 0])
		with open(pathlp, 'a') as file:
			file.write(str(cpu_time) + '\n')
		with open(path+"/output_libsnark_prove_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open(path+"/memory_libsnark_prove_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
			
		(out, mem, cpu_time) = trackRun_cputime(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+circuit+'_run'+str(i)+'1.in verify files/proofrun'+str(i)+'1.bin').split(), "", [time.time(), 0])
		with open(pathlv, 'a') as file:
			file.write(str(cpu_time) + '\n')
		with open(path+"/output_libsnark_verify_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open(path+"/memory_libsnark_verify_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
		


def save_to_correct_folder(data_type, section, run, circuit, param, out):
	path = ("outputs/"+circuit+"/run"+str(run)+"/"+data_type+"/"+data_type+"_"+section+"_"+circuit+str(param)+"_"+str(run)+".json")
	os.makedirs(os.path.dirname(path), exist_ok=True)
	with open(path, 'w', encoding='utf-8') as f:
		json.dump(out, f, ensure_ascii=False, indent=4)


def generate_list(height):
	list_len = 2**height
	#print(list_len)
	#word_site = "https://www.mit.edu/~ecprice/wordlist.10000"
	#response = requests.get(word_site)
	with open("ecprice_wordlist.10000", 'r') as wordlist:
		WORDS = wordlist.read().split()

	#WORDS = response.content.splitlines()
	methods = ["GET ", "POST ", "PUT ", "DELETE "]
	wordlist = ["GET /function/run"]
	for i in range(1, list_len-1):
		#print(i)
		string = methods[random.randint(0,3)]
		sequence = [WORDS[random.randint(0, len(WORDS)-1)] for _ in range(1, random.randint(2,6))]
		for word in sequence:
			string+= '/'+word
		wordlist += [string]
	with open("files/allowlist_"+str(height)+".txt", "w") as file:
		for line in wordlist:
			file.write(str(line) + '\n')
	tree_struct = compute_tree("files/allowlist_"+str(height)+".txt", False)
	subprocess.run(("mv files/generated_merkle_tree.txt files/tree_"+str(height)+".txt").split())
	return_val = compute_proof("GET /function/run", "files/allowlist_"+str(height)+".txt", False, "files/proof_"+str(height)+".txt", "files/tree_"+str(height)+".txt")
	#print("RETURN VAL: ",return_val)
	return("files/proof_"+str(height)+".txt")

def run_looped_tests_merkle(circuit, num):
	os.makedirs(os.path.dirname("outputs/"+circuit+"/run"+str(num)+"/"), exist_ok=True)
	os.makedirs(os.path.dirname("files/"+circuit+"/run"+str(num)+"/"), exist_ok=True)
	
	pathj = "outputs/"+circuit+"/run"+str(num)+"/cputimes_java_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathj) and os.stat(pathj).st_size != 0:
		with open(pathj, 'w') as file:
			file.truncate()
	else:
		with open(pathj, 'w') as file:
			pass
	pathls = "outputs/"+circuit+"/run"+str(num)+"/cputimes_libsnark_setup_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathls) and os.stat(pathls).st_size != 0:
		with open(pathls, 'w') as file:
			file.truncate()
	else:
		with open(pathls, 'w') as file:
			pass
	pathlp = "outputs/"+circuit+"/run"+str(num)+"/cputimes_libsnark_prove_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathlp) and os.stat(pathlp).st_size != 0:
		with open(pathlp, 'w') as file:
			file.truncate()
	else:
		with open(pathlp, 'w') as file:
			pass
	pathlv = "outputs/"+circuit+"/run"+str(num)+"/cputimes_libsnark_verify_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathlv) and os.stat(pathlv).st_size != 0:
		with open(pathlv, 'w') as file:
			file.truncate()
	else:
		with open(pathlv, 'w') as file:
			pass
		
	for i in range(4, 10, 1):
		print(i)
		start_time = time.time()
		os.makedirs(os.path.dirname("files/"+circuit+"/run"+str(num)), exist_ok=True)
		merkle_file = generate_list(i)
		(out, mem, cpu_time) = trackRun(("java -Xmx6G -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck."+circuit+" run tls_data.txt "+merkle_file+" /pippo run"+str(i)+" 1 500 20 "+str(i)).split(), "", start_time)
		with open(pathj, 'a') as file:
			file.write(str(cpu_time) + '\n')
		save_to_correct_folder("output", "java", num, circuit, i, out)
		save_to_correct_folder("memory", "java", num, circuit, i, mem)

		
		#subprocess.run(("mv files/Test_"+circuit+".arith .").split())
		(out, mem, cpu_time) = trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith setup').split(), "", time.time())
		out +=[["PK Size", os.path.getsize('files/provKey.bin')]]
		out +=[["VK Size", os.path.getsize('files/veriKey.bin')]]
		with open(pathls, 'a') as file:
			file.write(str(cpu_time) + '\n')
		save_to_correct_folder("output", "libsnark_setup", num, circuit, i, out)
		save_to_correct_folder("memory", "libsnark_setup", num, circuit, i, mem)

			
		(out, mem, cpu_time) = trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+circuit+'_run'+str(i)+'1.in prove run'+str(i)+' 1').split(), "", time.time())
		with open(pathlp, 'a') as file:
			file.write(str(cpu_time) + '\n')
		save_to_correct_folder("output", "libsnark_prove", num, circuit, i, out)
		save_to_correct_folder("memory", "libsnark_prove", num, circuit, i, mem)

			
		(out, mem, cpu_time) = trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+circuit+'_run'+str(i)+'1.in verify proofrun'+str(i)+'1.bin').split(), "", time.time())
		with open(pathlv, 'a') as file:
			file.write(str(cpu_time) + '\n')
		save_to_correct_folder("output", "libsnark_verify", num, circuit, i, out)
		save_to_correct_folder("memory", "libsnark_verify", num, circuit, i, mem)
		os.rename("files/"+circuit+".arith", "files/"+circuit+"/run"+str(num)+"/"+circuit+".arith")
		os.rename("files/"+circuit+"_run"+str(i)+"1.in", "files/"+circuit+"/run"+str(num)+"/"+circuit+"_run"+str(i)+"1.in")
		os.rename("files/proofrun"+str(i)+"1.bin", "files/"+circuit+"/run"+str(num)+"/proofrun"+str(i)+"1.bin")	
		os.rename("files/provKey.bin", "files/"+circuit+"/run"+str(num)+"/provKey.bin")	
		os.rename("files/veriKey.bin", "files/"+circuit+"/run"+str(num)+"/veriKey.bin")
		os.rename("files/allowlist_"+str(i)+".txt", "files/"+circuit+"/run"+str(num)+"/allowlist_"+str(i)+".txt")
		os.rename("files/tree_"+str(i)+".txt", "files/"+circuit+"/run"+str(num)+"/tree_"+str(i)+".txt")
		os.rename("files/proof_"+str(i)+".txt", "files/"+circuit+"/run"+str(num)+"/proof_"+str(i)+".txt")	

if __name__=='__main__':
	#memory_over_time_with_highlights("HTTP_String")
	#trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb ../Middlebox/files/HTTP_String.arith setup').split(), "libsnark_setup_HTTP_String.json")
	#trackRun(('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.HTTP_String pub ../Middlebox/files/test.txt /function circuitgen 1').split(), "xjsnark_setup_HTTP_String.json")
	#run_looped_tests("Test_HTTP_String", 2)
	for i in range(4,10):
		run_looped_tests_string("Test_HTTP_String", i)
	'''circuit = "Test_HTTP_Merkle"
	num = 2
	i = 4
	os.makedirs(os.path.dirname("outputs/"+circuit+"/run"+str(num)+"/"), exist_ok=True)
	os.makedirs(os.path.dirname("files/"+circuit+"/run"+str(num)+"/"), exist_ok=True)
	os.rename("files/"+circuit+".arith", "files/"+circuit+"/run"+str(num)+"/"+circuit+".arith")
	os.rename("files/"+circuit+"_run"+str(i)+"1.in", "files/"+circuit+"/run"+str(num)+"/"+circuit+"_run"+str(i)+"1.in")
	os.rename("files/proofrun"+str(i)+"1.bin", "files/"+circuit+"/run"+str(num)+"/proofrun"+str(i)+"1.bin")	
	os.rename("files/provKey.bin", "files/"+circuit+"/run"+str(num)+"/provKey.bin")	
	os.rename("files/veriKey.bin", "files/"+circuit+"/run"+str(num)+"/veriKey.bin")
	os.rename("files/allowlist_"+str(i)+".txt", "files/"+circuit+"/run"+str(num)+"/allowlist_"+str(i)+".txt")
	os.rename("files/tree_"+str(i)+".txt", "files/"+circuit+"/run"+str(num)+"/tree_"+str(i)+".txt")
	os.rename("files/proof_"+str(i)+".txt", "files/"+circuit+"/run"+str(num)+"/proof_"+str(i)+".txt")'''
	#run_looped_tests_merkle("Test_HTTP_Merkle", 2)
	#generate_list(5)
