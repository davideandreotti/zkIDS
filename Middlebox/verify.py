from flask import Flask, request

app = Flask(__name__)

@app.route('/upload-proof', methods=['POST'])
def upload_file():
    file = request.files['file']
    file.save('./verify.bin')
    print('File uploaded successfully.')
    for line in runProcess(('./libsnark/build/libsnark/jsnark_interface/hello gg '+circuit+'.arith '+circuit+'_Sample_Run1.in verify').split()):
    	print(line)
    	
@app.route('/prover-key', methods=['GET'])
	try:
		return send_file('provKey.bin', attachment_filename='provKey.bin')
	except Exception as e:
		return str(e)

    	
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

