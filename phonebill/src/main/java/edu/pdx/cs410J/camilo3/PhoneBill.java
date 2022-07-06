package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;

import java.util.Arrays;

/**
 * PhoneBill class extends AbstractPhoneBill
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;
  private PhoneCall[] phoneCalls;
  private int numPhoneCalls;

/**
 * Creates a new <code>PhoneBill</code>
 *
 * @param customer
 *        The customer's number as a String.
 *        Format "whatever string" where the string is unicode? i think...
 *        the phone call list will automatically be made with 1 slot
 *        and will grow as necessary.
 */

public PhoneBill(String customer) {
    this.customer = customer;
    this.phoneCalls = new PhoneCall[1];
    this.numPhoneCalls = 0;
  }

  /**
   * super simple getNumPhoneCalls() returns the number of phone calls present
   * helpful so you don't have to use .size() on the collections sometimes.
   */
  public int getNumPhoneCalls() {
    return this.numPhoneCalls;
  }

  /**
   * super simple getCustomer() returns the string customer name
   * overrides the abstract phone bill's abstract getCustomer method
   */
  @Override
  public String getCustomer() {
    return this.customer;
  }

  // used this as I wasn't quite sure how to do an array inside of a class in java.
  // https://www.geeksforgeeks.org/creating-a-dynamic-array-in-java/
  // to get the array that's growable, but we will see...
  /**
   * addPhoneCall
   * adds a phone call to a growable array.  array starts as single element and
   * grows by double each time it expands beyond limits
   * adds 1 to the number of calls
   * @param call
   *        the call to be added to the phoneCalls list.
   */
  @Override
  public void addPhoneCall(PhoneCall call) {
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
  // and how to get a copy of range here:
  // https://www.geeksforgeeks.org/how-to-get-slice-of-a-primitive-array-in-java/
  /**
   * returns the array as a copy from the range of # of phone calls and then
   * turns that into a list and returns that, there is probably a simpler way.
   * but this seems to work.
   */
  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    return Arrays.asList(Arrays.copyOfRange(phoneCalls, 0, numPhoneCalls));
  }
}
