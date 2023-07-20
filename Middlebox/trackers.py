import subprocess, psutil, time, json, pandas, math, numpy, threading
import matplotlib.pyplot as plt
import matplotlib.cm as cm

from matplotlib.ticker import FormatStrFormatter
exec_memory=[]


def trackMem(popen):
	print("Call time in htread ", current_time)
	while popen.poll() is None:

		java_memory_usage = psutil.Process(popen.pid).memory_full_info().uss / 1000000
		exec_memory.append([java_memory_usage, time.time()-current_time])
		print(exec_memory[-1][1])
		time.sleep(1)
	popen.wait()



#df = pandas.read_json('data.json')
def trackRun(cmd, outname, call_time):
	exec_output = []
	global exec_memory
	exec_memory = []
	print(call_time)
	global current_time
	current_time = call_time
	#exec_memory=[]
	popen = subprocess.Popen(cmd, stdout=subprocess.PIPE, universal_newlines=True)
	print("Executing output capture run...")
	thread = threading.Thread(target=trackMem, args=[popen])
	thread.start()
	for line in iter(popen.stdout.readline, ""):
		instant = time.time()-current_time
		java_memory_usage = psutil.Process(popen.pid).memory_full_info().uss / 1000000
		exec_memory.append([java_memory_usage, instant])
		print(java_memory_usage)
		exec_output.append([line, instant])

	popen.wait()
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
	return(exec_output, exec_memory)

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


if __name__=='__main__':
	memory_over_time_with_highlights("HTTP_String")
	#trackRun(('../libsnark/build/libsnark/jsnark_interface/run_zkmb ../Middlebox/files/HTTP_String.arith setup').split(), "libsnark_setup_HTTP_String.json")
	#trackRun(('java -cp ../xjsnark_decompiled/backend_bin_mod/:../xjsnark_decompiled/xjsnark_bin/ xjsnark.PolicyCheck.HTTP_String pub ../Middlebox/files/test.txt /function circuitgen 1').split(), "xjsnark_setup_HTTP_String.json")
	
	
