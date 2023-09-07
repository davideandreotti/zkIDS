# zkIDS
### A privacy-oriented Intrusion Detection System based on Zero-Knowledge proofs
# Build Instructions
! Make sure that Docker Compose is installed
! Tested on Ubuntu 20.04
- Clone the repository
- Move to libsnark directory and create the build folder: ```cd libsnark && mkdir build && cd build```
- Compile libsnark with the preferred compilation flags: ```cmake <flags> ..``` then ```make``` Tested flags: ```-DMULTICORE=ON``` and ```-DUSE_PT_COMPRESSION=OFF```

  Note: if compiled on a more recent Ubuntu version, set the same version in the dockerfiles
- Run ```docker compose build``` from the root repository directory to build the docker images
- Run ```docker compose up``` to run the compose environment
- To control the client TLS requests, in a new terminal do ```docker attach zkfw-client-1```. If setup is finished press enter or type the URI to make the request to.
  
# Run Benchmarks
- Build the docker compose images
- In the ```vars.env``` file, set the environment variables such that TEST and RUN are set for tests, then choose the circuit to run.
- Start everything up as before
- Results are found in the ```/home/Tests``` folders of each container
# Create Plots
- Make sure that the generated results are in the same Tests folder as the interactive notebook 
- Open ```Plots.ipynb```
- Run any chosen section
