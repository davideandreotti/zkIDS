//This runs on the Middlebox
#include "CircuitReader.hpp"
#include <libsnark/common/libsnark_serialization.hpp>
#include <libsnark/gadgetlib2/integration.hpp>
#include <libsnark/gadgetlib2/adapters.hpp>
#include <libsnark/zk_proof_systems/ppzksnark/r1cs_ppzksnark/examples/run_r1cs_ppzksnark.hpp>
#include <libsnark/zk_proof_systems/ppzksnark/r1cs_ppzksnark/r1cs_ppzksnark.hpp>
#include <libsnark/zk_proof_systems/ppzksnark/r1cs_gg_ppzksnark/examples/run_r1cs_gg_ppzksnark.hpp>
#include <libsnark/zk_proof_systems/ppzksnark/r1cs_gg_ppzksnark/r1cs_gg_ppzksnark.hpp>
#include <libsnark/common/default_types/r1cs_gg_ppzksnark_pp.hpp>
#include <sstream>
#include <fstream>
#include <type_traits>
#include <libff/common/profiling.hpp>
#include <cassert>

//TODO: - put input/output functions in a proper hpp file
//TODO: - uniform input output called functions	


using namespace libsnark;

int main(int argc, char **argv) {

	libff::start_profiling();
	gadgetlib2::initPublicParamsFromDefaultPp();
	gadgetlib2::GadgetLibAdapter::resetVariableIndex();
	ProtoboardPtr pb = gadgetlib2::Protoboard::create(gadgetlib2::R1P);

	const bool test_serialization = false;
	bool successBit = false;	
		
	if(strcmp(argv[2], "setup") == 0)
	{
		// Completely useless copy-paste but the compiler was complaining...
		cout << "Executing setup..."<<endl;

		CircuitReader reader(argv[1], pb);
		//get constraint system from protoboard (inputs are not involved)
		r1cs_constraint_system<FieldT> cs = get_constraint_system_from_gadgetlib2(*pb);
		//assigning values to inputs
		const r1cs_variable_assignment<FieldT> full_assignment = get_variable_assignment_from_gadgetlib2(*pb);
		cs.primary_input_size = reader.getNumInputs() + reader.getNumOutputs();
		cs.auxiliary_input_size = full_assignment.size() - cs.num_inputs();

		//in verification phase the auxiliary input will not be relevant (indeed it will be empty)
		const r1cs_primary_input<FieldT> primary_input(full_assignment.begin(),
				full_assignment.begin() + cs.num_inputs());
		const r1cs_auxiliary_input<FieldT> auxiliary_input(
				full_assignment.begin() + cs.num_inputs(), full_assignment.end());
		//creating circuit example structures (still, if only cs or primary_input is needed, the other two will not be relevant, it's just for initialization of structures)	
		r1cs_example<FieldT> example(cs, primary_input, auxiliary_input);
		cout << "Example created!" << endl;
		//---------------
		//generate keypair
		r1cs_gg_ppzksnark_keypair<libsnark::default_r1cs_gg_ppzksnark_pp> keypair = r1cs_gg_ppzksnark_generator<libsnark::default_r1cs_gg_ppzksnark_pp>(example.constraint_system);
		cout << "Writing prover key to file..." <<endl;
		std::ofstream out("files/provKey.bin", std::ios::binary);
		out << keypair.pk;
		out.close();
		cout << "Processing verification key... ";
		//processed verification key contains a small amount of precomputed information that enables faster verification times.
		r1cs_gg_ppzksnark_processed_verification_key<libsnark::default_r1cs_gg_ppzksnark_pp> pvk = r1cs_gg_ppzksnark_verifier_process_vk<libsnark::default_r1cs_gg_ppzksnark_pp>(keypair.vk);
		cout << "Writing to file..."<<endl;
		out.open("files/veriKey.bin", std::ios::binary);
		out << pvk;

		out.close();
		//std::ifstream in("provKey.bin", std::ios::binary);
		//r1cs_gg_ppzksnark_proving_key<libsnark::default_r1cs_gg_ppzksnark_pp> prova;
		//in >> prova;
		//cout<< (prova==keypair.pk);

	}
	else if(strcmp(argv[3], "prove") == 0)
	{	cout << "Proof generation..." <<endl;
		string randomid = argv[4];
		string packetnum = argv[5];	
		string filename = "files/proof" + randomid + packetnum + ".bin";
		cout << filename;
		CircuitReader reader(argv[1], argv[2], pb);
		//get constraint system from protoboard (inputs are not involved)
		r1cs_constraint_system<FieldT> cs = get_constraint_system_from_gadgetlib2(*pb);
		//assigning values to inputs
		const r1cs_variable_assignment<FieldT> full_assignment = get_variable_assignment_from_gadgetlib2(*pb);
		cs.primary_input_size = reader.getNumInputs() + reader.getNumOutputs();
		cs.auxiliary_input_size = full_assignment.size() - cs.num_inputs();

		//in verification phase the auxiliary input will not be relevant (indeed it will be empty)
		const r1cs_primary_input<FieldT> primary_input(full_assignment.begin(),
				full_assignment.begin() + cs.num_inputs());
		const r1cs_auxiliary_input<FieldT> auxiliary_input(
				full_assignment.begin() + cs.num_inputs(), full_assignment.end());
		//creating circuit example structures (still, if only cs or primary_input is needed, the other two will not be relevant, it's just for initialization of structures)	
		r1cs_example<FieldT> example(cs, primary_input, auxiliary_input);
		cout << "Example created!" << endl;
		//-----------------------------------------------------------------------------------------------------------
		//verify that constraint system is properly satisfied, given all inputs and circuit
		if(!cs.is_satisfied(primary_input, auxiliary_input)){
			cout << "The constraint system is  not satisifed by the value assignment - Terminating." << endl;
			return -1;
		}
		
		cout << "Reading key..."<<endl;	
		std::ifstream in("files/provKey.bin", std::ios::binary);
		r1cs_gg_ppzksnark_proving_key<libsnark::default_r1cs_gg_ppzksnark_pp> proverKey;
		in >> proverKey;
		
		cout << "Generating proof..."<<endl;
		r1cs_gg_ppzksnark_proof<libsnark::default_r1cs_gg_ppzksnark_pp> proof = r1cs_gg_ppzksnark_prover<libsnark::default_r1cs_gg_ppzksnark_pp>(proverKey, example.primary_input, example.auxiliary_input);
		cout << "Writing proof to file..."<<endl;
		//NOTE: this instruction uses an "input"/"output" function that is THE SAME as the << >> operators but with a name. It is in the serialization.hpp file of libff.
		libff::output<r1cs_gg_ppzksnark_proof<libsnark::default_r1cs_gg_ppzksnark_pp>>(proof, filename.c_str()); 
		
		//r1cs_gg_ppzksnark_proof<libsnark::default_r1cs_gg_ppzksnark_pp> prova;
		//prova=libff::input<r1cs_gg_ppzksnark_proof<libsnark::default_r1cs_gg_ppzksnark_pp>>("proof.bin"); 
	}
	else if(strcmp(argv[3], "verify") == 0)
	{	
		cout << "Verifying proof..." <<endl;
		string proof_path = argv[4];
		cout << proof_path <<endl;
		CircuitReader reader(argv[1], argv[2], pb);
		//get constraint system from protoboard (inputs are not involved)
		r1cs_constraint_system<FieldT> cs = get_constraint_system_from_gadgetlib2(*pb);
		//assigning values to inputs
		const r1cs_variable_assignment<FieldT> full_assignment = get_variable_assignment_from_gadgetlib2(*pb);
		cs.primary_input_size = reader.getNumInputs() + reader.getNumOutputs();
		cs.auxiliary_input_size = full_assignment.size() - cs.num_inputs();

		//in verification phase the auxiliary input will not be relevant (indeed it will be empty)
		const r1cs_primary_input<FieldT> primary_input(full_assignment.begin(),
				full_assignment.begin() + cs.num_inputs());
		const r1cs_auxiliary_input<FieldT> auxiliary_input(
				full_assignment.begin() + cs.num_inputs(), full_assignment.end());
		//creating circuit example structures (still, if only cs or primary_input is needed, the other two will not be relevant, it's just for initialization of structures)	
		r1cs_example<FieldT> example(cs, primary_input, auxiliary_input);
		cout << "Example created!" << endl;
		//-----------------------------------------------------------------------------------------------------------
		cout << "Reading verification key (preprocessed)..."<<endl;
		std::ifstream in("files/veriKey.bin", std::ios::binary); //TODO: uniform input/output serialization methods
		r1cs_gg_ppzksnark_processed_verification_key<libsnark::default_r1cs_gg_ppzksnark_pp> verificationKey;
		in >> verificationKey;
		cout << "Reading proof..."<<endl;
    		r1cs_gg_ppzksnark_proof<libsnark::default_r1cs_gg_ppzksnark_pp> proof = libff::input<r1cs_gg_ppzksnark_proof<libsnark::default_r1cs_gg_ppzksnark_pp>>(proof_path.c_str()); 
    		//this instruction to be used if not processed verification key
    		//const bool ans2 = r1cs_gg_ppzksnark_verifier_strong_IC<libsnark::default_r1cs_gg_ppzksnark_pp>(verificationKey, example.primary_input, proof);
    		const bool ans2 = r1cs_gg_ppzksnark_online_verifier_strong_IC<libsnark::default_r1cs_gg_ppzksnark_pp>(verificationKey, example.primary_input, proof);
		cout << (ans2 ? "PASS" : "FAIL");

		//cout << "pippoVerifica Online: " << (ans2 ? "PASS" : "FAIL");
	}
	else{return 0;}
	
	

	successBit = true;
	//libsnark::run_r1cs_gg_ppzksnark_generateparameters<libsnark::default_r1cs_gg_ppzksnark_pp>(example, test_serialization);


	if(!successBit){
		cout << "Problem occurred while running the ppzksnark algorithms .. " << endl;
		return -1;
	}	
	return 0;
}





