import subprocess, psutil, time, json, pandas, math, numpy, threading, os, random, sys
import matplotlib.pyplot as plt
import matplotlib.cm as cm
import requests
sys.path.insert(0, "../Middlebox/Merkle Proof")
from membership_proofs import *

exec_memory=[]
cpu_time = 0

def trackMem(popen):
	print("Call time in htread ", current_time)
	global cpu_time
	while popen.poll() is None:
		proc = psutil.Process(popen.pid)
		java_memory_usage = proc.memory_full_info().uss / 1000000
		#print(proc.cpu_times())
		cpu_time = proc.cpu_times().user + proc.cpu_times().system
		print(cpu_time)
		print(java_memory_usage)
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
	print("Executing output capture run...")
	thread = threading.Thread(target=trackMem, args=[popen])
	thread.start()
	for line in iter(popen.stdout.readline, ""):
		print(line)
		instant = time.time()-current_time
		try:
			java_memory_usage = psutil.Process(popen.pid).memory_full_info().uss / 1000000
			exec_memory.append([java_memory_usage, instant])
			print(java_memory_usage)
			exec_output.append([line, instant])
		except psutil.NoSuchProcess:
			print("\n\n WARNING!!!!!!!!!! Process already ended\n\n")
			pass
			
	thread.join()
	#print("done, ", exec_memory)
	exec_output.append(["Done", time.time()-current_time])

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
	pathj = "outputs/cputimes_java_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathj) and os.stat(pathj).st_size != 0:
		with open(pathj, 'w') as file:
			file.truncate()
	else:
		with open(pathj, 'w') as file:
			pass
	pathls = "outputs/cputimes_libsnakr_setup_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathls) and os.stat(pathls).st_size != 0:
		with open(pathls, 'w') as file:
			file.truncate()
	else:
		with open(pathls, 'w') as file:
			pass
	pathlp = "outputs/cputimes_libsnark_prove_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathlp) and os.stat(pathlp).st_size != 0:
		with open(pathlp, 'w') as file:
			file.truncate()
	else:
		with open(pathlp, 'w') as file:
			pass
	pathlv = "outputs/cputimes_libsnark_verify_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathlv) and os.stat(pathlv).st_size != 0:
		with open(pathlv, 'w') as file:
			file.truncate()
	else:
		with open(pathlv, 'w') as file:
			pass
	
	
		
	for i in range(400, 1100, 100):
		start_time = time.time()
		
		(out, mem, cpu_time) = trackRun(("java -Xmx6G -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck."+circuit+" run tls_data.txt /function run"+str(i)+" 1 "+str(i)+" 20").split(), "", start_time)
		with open(pathj, 'a') as file:
			file.write(str(cpu_time) + '\n')
		print("Tot CPU Time: ",cpu_time)
		with open("outputs/output_java_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open("outputs/memory_java_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
		
		#subprocess.run(("mv files/Test_"+circuit+".arith .").split())
		(out, mem, cpu_time) = trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith setup').split(), "", time.time())
		out +=[["PK Size", os.path.getsize('files/provKey.bin')]]
		out +=[["VK Size", os.path.getsize('files/veriKey.bin')]]
		with open(pathls, 'a') as file:
			file.write(str(cpu_time) + '\n')
		with open("outputs/output_libsnark_setup_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open("outputs/memory_libsnark_setup_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
			
		(out, mem, cpu_time) = trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+circuit+'_run'+str(i)+'1.in prove run'+str(i)+' 1').split(), "", time.time())
		with open(pathlp, 'a') as file:
			file.write(str(cpu_time) + '\n')
		with open("outputs/output_libsnark_prove_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open("outputs/memory_libsnark_prove_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
			
		(out, mem, cpu_time) = trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+circuit+'_run'+str(i)+'1.in verify run'+str(i)+' 1').split(), "", time.time())
		with open(pathlv, 'a') as file:
			file.write(str(cpu_time) + '\n')
		with open("outputs/output_libsnark_verify_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open("outputs/memory_libsnark_verify_HTTP_String_"+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
		


def memory_over_time_with_highlights(circuit):
###SETUP PLOT###
	pastel_cmap = cm.get_cmap('Pastel1')

	df2 = pandas.read_json("setup_"+circuit+"_output.json").round(3)
	df2.columns = ['output', 'timestamp']
	#print(df.to_string())
	timestamps=df2['timestamp']
	df2 = df2.iloc[:, [1,0]]
	df2.set_index('timestamp')
	
	df = pandas.read_json("setup_"+circuit+"_memory.json").round(3)
	df.columns = ['memory', 'timestamp']
	df = df.iloc[:, [1,0]]
	df.set_index('timestamp')
	#df2['timestamp'].add(last_time)
	#df=df.append(df2,ignore_index=True)
	#print(df.to_string())
	print(df.to_string())
	print(df2.to_string())
	timestamps=df2['timestamp']
	
	highlights = [timestamps[0], timestamps[96], timestamps[102], timestamps[108], timestamps[118], timestamps[191], timestamps[192], timestamps[199]]
	labels = ["Analysis", "Circuit Generation", "Circuit Write", "Circuit Translation", "SRS Computation", "PK Write", "VK Write"]
	df.set_index('timestamp')
	df2.set_index('timestamp')
	df = df.merge(df2, how = 'outer')
	df['memory'] = df['memory'].fillna(value=0)
	print(df.to_string())

	
	plt.figure()
	
	#print(plt.style.available)
	plt.style.use('fivethirtyeight')
	plt.plot(df['timestamp'], df['memory'], linewidth=0, zorder=3)

	#print(df['timestamp'] in highlights)
	#print(df[df['timestamp'] in highlights].to_string())

	ticks=[]
	for tick in highlights:
		if not ticks or ticks[-1]<tick-1.5:
			ticks.append(tick)
	plt.xlabel("Execution time (s)")
	plt.ylabel("Memory occupation (MB)")
	print(type(numpy.arange(0, max(df['memory']), step=max(df['memory'])/5)))
	plt.yticks(numpy.append(numpy.arange(0, max(df['memory']), step=max(df['memory'])/5), max(df['memory'])))
	#plt.xticks(numpy.arange(0, max(df['timestamp']), step=max(df['timestamp'])/5))
	plt.xticks(ticks, rotation = 45, fontsize=10)
	
	for (i,instant) in enumerate(highlights[:-1]):
		index = df['timestamp'].between(highlights[i], highlights[i+1], inclusive="both")
		plt.fill_between(df['timestamp'][index], df['memory'][index], label=labels[i], alpha=0.8, zorder=4+i)
	plt.legend().set_zorder(100)
	plt.grid(linestyle = '--', linewidth = 1, zorder=2)
	plt.axvspan(0, highlights[3], alpha=0.3, zorder=1)
	plt.axvspan(highlights[3], highlights[-1], alpha=0.2, zorder=1)
	plt.axvline(highlights[3])
	
	plt.show()
###END SETUP###
###PROVE###
###SETUP PLOT###
	df2 = pandas.read_json("../Client/prove_"+circuit+"_output.json")
	df2.columns = ['output', 'timestamp']
	#print(df.to_string())
	timestamps=df2['timestamp']
	df2 = df2.iloc[:, [1,0]]
	df2.set_index('timestamp')
	#print(df2.to_string())
	df = pandas.read_json("../Client/prove_"+circuit+"_memory.json")
	df.columns = ['memory', 'timestamp']
	df = df.iloc[:, [1,0]]
	df.set_index('timestamp')

	timestamps=df2['timestamp']
	
	highlights = [timestamps[0], timestamps[96], timestamps[102], timestamps[108], timestamps[118], timestamps[191], timestamps[192], timestamps[199]]
	labels = ["Analysis", "Circuit Generation", "Circuit Write", "Circuit Translation", "SRS Computation", "PK Write", "VK Write"]

	#print(df.to_string())
	#print(df2.to_string())

	df = df.merge(df2, how = 'outer')
	print(df.to_string())
	df['memory'] = df['memory'].fillna(value=0)
	
	
	
	#print(df.to_string())

	
	plt.figure()
	
	#print(plt.style.available)
	plt.style.use('fivethirtyeight')
	plt.plot(df['timestamp'], df['memory'], linewidth=0, zorder=3)

	#print(df['timestamp'] in highlights)
	#print(df[df['timestamp'] in highlights].to_string())

	ticks=[]
	for tick in highlights:
		if not ticks or ticks[-1]<tick-1.5:
			ticks.append(tick)
	plt.xlabel("Execution time (s)")
	plt.ylabel("Memory occupation (MB)")
	print(type(numpy.arange(0, max(df['memory']), step=max(df['memory'])/5)))
	plt.yticks(numpy.append(numpy.arange(0, max(df['memory']), step=max(df['memory'])/5), max(df['memory'])))
	#plt.xticks(numpy.arange(0, max(df['timestamp']), step=max(df['timestamp'])/5))
	plt.xticks(ticks, rotation = 45, fontsize=10)
	
	for (i,instant) in enumerate(highlights[:-1]):
		index = df['timestamp'].between(highlights[i], highlights[i+1], inclusive="both")
		plt.fill_between(df['timestamp'][index], df['memory'][index], label=labels[i], alpha=0.8, zorder=4+i)
	plt.legend().set_zorder(100)
	plt.grid(linestyle = '--', linewidth = 1, zorder=2)
	plt.axvspan(0, highlights[3], alpha=0.3, zorder=1)
	plt.axvspan(highlights[3], highlights[-1], alpha=0.2, zorder=1)
	plt.axvline(highlights[3])
	
	plt.show()



def generate_list(height):
	list_len = 2**height
	#print(list_len)
	#word_site = "https://www.mit.edu/~ecprice/wordlist.10000"
	#response = requests.get(word_site)
	with open("files/ecprice_wordlist.10000", 'r') as wordlist:
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
	pathj = "outputs/cputimes_java_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathj) and os.stat(pathj).st_size != 0:
		with open(pathj, 'w') as file:
			file.truncate()
	else:
		with open(pathj, 'w') as file:
			pass
	pathls = "outputs/cputimes_libsnark_setup_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathls) and os.stat(pathls).st_size != 0:
		with open(pathls, 'w') as file:
			file.truncate()
	else:
		with open(pathls, 'w') as file:
			pass
	pathlp = "outputs/cputimes_libsnark_prove_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathlp) and os.stat(pathlp).st_size != 0:
		with open(pathlp, 'w') as file:
			file.truncate()
	else:
		with open(pathlp, 'w') as file:
			pass
	pathlv = "outputs/cputimes_libsnark_verify_"+circuit+"_"+str(num)+".txt"
	if os.path.isfile(pathlv) and os.stat(pathlv).st_size != 0:
		with open(pathlv, 'w') as file:
			file.truncate()
	else:
		with open(pathlv, 'w') as file:
			pass
		
	for i in range(4, 10, 1):
		print(i)
		start_time = time.time()
		merkle_file = generate_list(i)
		(out, mem, cpu_time) = trackRun(("java -Xmx6G -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck."+circuit+" run tls_data.txt "+merkle_file+" /pippo run"+str(i)+" 1 500 20 "+str(i)).split(), "", start_time)
		with open(pathj, 'a') as file:
			file.write(str(cpu_time) + '\n')
		print("Tot CPU Time: ",cpu_time)
		with open("outputs/output_java_"+circuit+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open("outputs/memory_java_"+circuit+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
		
		#subprocess.run(("mv files/Test_"+circuit+".arith .").split())
		(out, mem, cpu_time) = trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith setup').split(), "", time.time())
		out +=[["PK Size", os.path.getsize('files/provKey.bin')]]
		out +=[["VK Size", os.path.getsize('files/veriKey.bin')]]
		with open(pathls, 'a') as file:
			file.write(str(cpu_time) + '\n')
		with open("outputs/output_libsnark_setup_"+circuit+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open("outputs/memory_libsnark_setup_"+circuit+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
			
		(out, mem, cpu_time) = trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+circuit+'_run'+str(i)+'1.in prove run'+str(i)+' 1').split(), "", time.time())
		with open(pathlp, 'a') as file:
			file.write(str(cpu_time) + '\n')
		with open("outputs/output_libsnark_prove_"+circuit+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open("outputs/memory_libsnark_prove_"+circuit+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)
			
		(out, mem, cpu_time) = trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb files/'+circuit+'.arith files/'+circuit+'_run'+str(i)+'1.in verify run'+str(i)+' 1').split(), "", time.time())
		with open(pathlv, 'a') as file:
			file.write(str(cpu_time) + '\n')
		with open("outputs/output_libsnark_verify_"+circuit+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(out, f, ensure_ascii=False, indent=4)
		with open("outputs/memory_libsnark_verify_"+circuit+str(i)+"_"+str(num)+".json", 'w', encoding='utf-8') as f:
			json.dump(mem, f, ensure_ascii=False, indent=4)



if __name__=='__main__':
	#memory_over_time_with_highlights("HTTP_String")
	#trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb ../Middlebox/files/HTTP_String.arith setup').split(), "libsnark_setup_HTTP_String.json")
	#trackRun(('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.HTTP_String pub ../Middlebox/files/test.txt /function circuitgen 1').split(), "xjsnark_setup_HTTP_String.json")
	#run_looped_tests("Test_HTTP_String", 2)
	for i in [5, 7, 10, 15, 20, 25]:
		generate_list(i)
	run_looped_tests_merkle("Test_HTTP_Merkle", 1)
 