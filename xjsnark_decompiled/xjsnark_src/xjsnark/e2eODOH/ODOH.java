package xjsnark.e2eODOH;

/*Generated by MPS */

import backend.auxTypes.SmartMemory;
import backend.auxTypes.UnsignedInteger;
import backend.auxTypes.Bit;
import backend.structure.CircuitGenerator;
import backend.auxTypes.ConditionalScopeTracker;

public class ODOH {

  public static final int HTTP_REQUEST_MAX_LENGTH = 500;

  // the string 'HTTP/1.1' in ASCII
  public static final int[] http11_ints = {0x48, 0x54, 0x54, 0x50, 0x2f, 0x31, 0x2e, 0x31};

  // the string 'targethost=' in ASCII
  public static final int[] targesthosteq_ints = {116, 97, 114, 103, 101, 116, 104, 111, 115, 116, 61};

  // hardcoded resolver name ''
  public static final int[] resolver_name_ints = {108, 97, 98, 101, 108, 49, 46, 108, 97, 98, 101, 108, 50, 46, 108, 97, 98, 101, 108, 51, 46, 108, 97, 98, 101, 108, 52, 46, 116, 97, 114, 103, 101, 116, 104, 111, 115, 116, 46, 101, 120, 97, 109, 112, 108, 101, 46, 110, 101, 116};

  public static final int resolver_name_length = 50;

  // Carriage Return (CR) and Line Feed (LF) chars in ASCII
  public static final int cr_int = 0x0d;
  public static final int lf_int = 0x0a;


  // Function to verify that the first time the two characters CRLF appear
  // is indeed at first_crlf_index
  private static void verify_first_crlf(SmartMemory<UnsignedInteger> http_bytes_ram, UnsignedInteger first_crlf_index) {

    UnsignedInteger cr = UnsignedInteger.instantiateFrom(16, cr_int).copy(16);
    UnsignedInteger lf = UnsignedInteger.instantiateFrom(16, lf_int).copy(16);


    UnsignedInteger CRLF_CONCAT = (UnsignedInteger.instantiateFrom(16, cr).shiftLeft(16)).orBitwise((UnsignedInteger.instantiateFrom(16, lf))).copy(16);

    // this ensures that the CRLF patten isn't present before first_crlf_index 
    for (int i = 1; i < HTTP_REQUEST_MAX_LENGTH; i++) {
      {
        Bit bit_a0i0v = UnsignedInteger.instantiateFrom(16, i).isLessThan(first_crlf_index).copy();
        boolean c_a0i0v = CircuitGenerator.__getActiveCircuitGenerator().__checkConstantState(bit_a0i0v);
        if (c_a0i0v) {
          if (bit_a0i0v.getConstantValue()) {
            UnsignedInteger prev_char = http_bytes_ram.read(i - 1);
            UnsignedInteger curr_char = http_bytes_ram.read(i);
            UnsignedInteger curr_concat = ((UnsignedInteger.instantiateFrom(16, prev_char).shiftLeft(16)).orBitwise(UnsignedInteger.instantiateFrom(16, curr_char))).copy(16);
            CRLF_CONCAT.isNotEqualTo(curr_concat).forceEqual(new Bit(true));
          } else {

          }
        } else {
          ConditionalScopeTracker.pushMain();
          ConditionalScopeTracker.push(bit_a0i0v);
          UnsignedInteger prev_char = http_bytes_ram.read(i - 1);
          UnsignedInteger curr_char = http_bytes_ram.read(i);
          UnsignedInteger curr_concat = ((UnsignedInteger.instantiateFrom(16, prev_char).shiftLeft(16)).orBitwise(UnsignedInteger.instantiateFrom(16, curr_char))).copy(16);
          CRLF_CONCAT.isNotEqualTo(curr_concat).forceEqual(new Bit(true));

          ConditionalScopeTracker.pop();

          ConditionalScopeTracker.push(new Bit(true));

          ConditionalScopeTracker.pop();
          ConditionalScopeTracker.popMain();
        }

      }
    }

    // check that CRLF is present at first_crlf_index 
    cr.forceEqual(http_bytes_ram.read(first_crlf_index));
    lf.forceEqual(http_bytes_ram.read(first_crlf_index.add(UnsignedInteger.instantiateFrom(8, 1))));

  }

  // Inputs are the http_message, the index where 'targethost=' string appears 
  //  and the first CRLF index.
  // The function does the following:
  // (1) Verify that the 11 chars starting at targethost_index is 'targethost="
  // (2) Verify that the next 'resolver_name_length' chars are the resolve name that is hardcoded into the circuit
  // (3) Then verify that CRLF does not appear before the claimed first_crlf_index
  public static void test_odoh(UnsignedInteger[] http_bytes, UnsignedInteger targethost_index, UnsignedInteger first_crlf_index) {

    SmartMemory<UnsignedInteger> http_bytes_ram = new SmartMemory(UnsignedInteger.instantiateFrom(8, http_bytes), UnsignedInteger.__getClassRef(), new Object[]{"8"});

    UnsignedInteger[] targethost_label = UnsignedInteger.instantiateFrom(8, targesthosteq_ints);
    UnsignedInteger[] resolver_name = UnsignedInteger.instantiateFrom(8, resolver_name_ints);

    // Does (1) 
    for (int i = 0; i < 11; i++) {
      http_bytes_ram.read(targethost_index.add(UnsignedInteger.instantiateFrom(8, i))).forceEqual(targethost_label[i]);
    }

    // Does (2) 
    for (int i = 0; i < resolver_name_length; i++) {
      http_bytes_ram.read(targethost_index.add(UnsignedInteger.instantiateFrom(8, 11 + i))).forceEqual(resolver_name[i]);
    }

    // Does (3) 
    verify_first_crlf(http_bytes_ram, first_crlf_index.copy(16));

    targethost_index.isLessThan(first_crlf_index).forceEqual(new Bit(true));
  }


}
