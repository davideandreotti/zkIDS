# zkIDS
### A privacy-oriented Intrusion Detection System based on Zero-Knowledge proofs
# Instructions
! Make sure that Docker Compose is installed
- Clone the repository
- Move to libsnark directory and create the build folder: ```cd libsnark && mkdir build && cd build```
- Compile libsnark with the preferred compilation flags: ```cmake <flags> ..``` then ```make``` Tested flags: ```-DMULTICORE=ON``` and ```-DUSE_PT_COMPRESSION=OFF``` 
- Run ```docker compose build``` from the root repository directory to build the docker images
- Run ```docker compose up``` to run the compose environment
- To control the client TLS requests, in a new terminal do ```docker attach zkfw-client-1```. If setup is finished press enter or type the URI to make the request to.
  
