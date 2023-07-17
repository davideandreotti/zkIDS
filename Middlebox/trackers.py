import subprocess, psutil, time, json, pandas, math, numpy
import matplotlib.pyplot as plt
from matplotlib.ticker import FormatStrFormatter


#df = pandas.read_json('data.json')
def trackRun(cmd, outname):
	exec_output = []
	exec_memory=[]
	current_time=time.time()
	popen = subprocess.Popen(cmd, stdout=subprocess.PIPE, universal_newlines=True)
	print("Executing output capture run...")
	for line in iter(popen.stdout.readline, ""):
		# Get the memory usage of the Java process
		#java_memory_usage = psutil.Process(popen.pid).memory_full_info().uss / 1000000
		#print(java_memory_usage)
		exec_output.append([line, time.time()-current_time])
		#print(exec_output)
	#java_memory_usage = psutil.Process(popen.pid).memory_full_info().rss
	popen.wait()
	exec_output.append(["Done", time.time()-current_time])
	current_time=time.time()
	popen = subprocess.Popen(cmd, stdout=subprocess.PIPE, universal_newlines=True)
	print("Executing memory profiling run...")
	while popen.poll() is None:
		java_memory_usage = psutil.Process(popen.pid).memory_full_info().uss / 1000000
		print(java_memory_usage)
		exec_memory.append([java_memory_usage, time.time()-current_time])
		time.sleep(1)
	popen.wait()
	with open(outname+"_output.txt", 'w', encoding='utf-8') as f:
	    json.dump(exec_output, f, ensure_ascii=False, indent=4)
	with open(outname+"_memory.txt", 'w', encoding='utf-8') as f:
	    json.dump(exec_memory, f, ensure_ascii=False, indent=4)

def plot(filename):
	df = pandas.read_json(filename+"_output.txt")
	df.columns = ['output', 'timestamp']
	timestamps=df['timestamp']
	highlights = [timestamps[0], timestamps[8], timestamps[82], timestamps[83], timestamps[89], timestamps[90]]
	print(highlights)
	#parsing/translation, example creation, keys computation, write PK, VK precomputation, write VK
	#xt=print(numpy.arange(0, max(df['timestamp']), 0.1))
	df = pandas.read_json(filename+"_memory.txt")
	df.columns = ['memory', 'timestamp']
	plt.figure()
	print(plt.style.available)
	plt.style.use('fivethirtyeight')
	plt.plot(df['timestamp'], df['memory'])
	plt.xlabel("Execution time (s)")
	plt.ylabel("Memory occupation (MB)")

	plt.yticks(numpy.arange(0, max(df['memory']), step=max(df['memory'])/5))
	plt.xticks(numpy.arange(0, max(df['timestamp']), step=max(df['timestamp'])/5))
	#for idx,section in enumerate(highlights[:-1]):
	#	print(section)
	#section = numpy.arange(highlights[0], highlights[1], 1/20)
	plt.fill_between(df['timestamp'], df['memory'], where = df['timestamp']<= highlights[1])
	plt.fill_between(df['timestamp'], df['memory'], where = df['timestamp']> highlights[1])
	#plt.fill_between(df['timestamp'], df['memory'], edgecolor="b", linewidth=0.0, where = df['timestamp'].between(highlights[2], highlights[3]))
	plt.grid(linestyle = '--', linewidth = 0.5)

	#ax.yaxis.set_major_formatter(FormatStrFormatter('%.2f'))

	plt.show()
	


if __name__=='__main__':
	plot("libsnark_setup_HTTP_String.json")
