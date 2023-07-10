import subprocess
def runProcess(exe):    
    p = subprocess.Popen(exe, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
#    while(True):
        # returns None while subprocess is running
#        retcode = p.poll() 
#        line = p.stdout.readline()
#        yield line
#        if retcode is not None:
#            print("")
#            break
    return p
