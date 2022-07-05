package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;
  private PhoneCall[] phoneCalls;
  private int numPhoneCalls;

/**
 * Creates a new <code>Student</code>
 *
 * @param customer
 *        The customer's number as a String.
 *        Format "whatever string" where the string is unicode? i think...
 *        the phone call list will automatically be made with 8 slots
 *        and will grow as necessary.
 */

public PhoneBill(String customer) {
    this.customer = customer;
    this.phoneCalls = new PhoneCall[8];
    this.numPhoneCalls = 0;
  }

  @Override
  public String getCustomer() {
    return this.customer;
  }

  // probably going to use
  // https://www.geeksforgeeks.org/creating-a-dynamic-array-in-java/
  // to get the array that's growable, but we will see...
  @Override
  public void addPhoneCall(PhoneCall call) {
//    throw new UnsupportedOperationException("This method is not implemented yet");
    if (phoneCalls.length == numPhoneCalls) {
      PhoneCall newPhoneList[] = new PhoneCall[2 * numPhoneCalls];
      for (int i = 0; i < numPhoneCalls; i++) {
        newPhoneList[i] = phoneCalls[i];
      }
      this.phoneCalls = newPhoneList;
    }
    this.phoneCalls[numPhoneCalls++] = call;
  }

  // looked up how to return a collection of something here:
  // https://stackoverflow.com/questions/24491067/how-to-return-a-collection
  @Override
  public Collection<PhoneCall> getPhoneCalls() {
//    throw new UnsupportedOperationException("This method is not implemented yet");
    // this below was not really what I wanted... but something to explore later.
//    return IntStream.range(0, numPhoneCalls).mapToObj(i -> phoneCalls[i]).toArray();
    return Arrays.asList(phoneCalls);
//    return Collections.list(phoneCalls); // this does not work
  }
}
