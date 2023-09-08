# zkIDS
### A privacy-oriented Intrusion Detection System based on Zero-Knowledge proofs
# Build Instructions
! Make sure that Docker Compose is installed
! Tested on Ubuntu 20.04
- Clone the repository
- If your Ubuntu version is > 20.04, skip building libsnark (compose will automatically build it)
- Move to libsnark directory and create the build folder 
```bash
cd libsnark && mkdir build && cd build
```
- Make sure that libsnark requisite packages are installed and Ubuntu 20.04 is in use  
```bash
sudo apt install build-essential cmake git libgmp3-dev libprocps-dev python3-markdown libboost-program-options-dev libssl-dev python3 pkg-config
```
- Compile libsnark with the preferred compilation flags
```bash
cmake <flags> ..
```
then 
```bash
make
``` 
Tested flags: ```-DMULTICORE=ON``` and ```-DUSE_PT_COMPRESSION=OFF```

- Build the docker images from the repo root
```bash
cd ../.. && docker compose build
```
- Run the compose environment
```bash
docker compose up
```
- To control the client TLS requests, in a new terminal do 
```bash
docker attach zkids-client-1
```
If setup is finished press enter or type the URI to make the request to.
  
# Run Benchmarks
- Build the docker compose images
- In the ```vars.env``` file, set the environment variables such that TEST and RUN are set for tests, then choose the circuit to run.
- Start everything up as before
- Results are found in the ```/home/Tests``` mounted shared volume, also accessible from outside the containers and permanent.
# Create Plots
- Make sure that the generated results are in the same Tests folder as the interactive notebook 
- Open ```Plots.ipynb```
- Run any chosen section
