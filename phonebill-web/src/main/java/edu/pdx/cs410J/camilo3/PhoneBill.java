package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * PhoneBill class extends AbstractPhoneBill
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;
  private List<PhoneCall> phoneCalls;

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
    this.phoneCalls = new ArrayList<>();
  }

  /**
   * super simple getCustomer() returns the string customer name
   * overrides the abstract phone bill's abstract getCustomer method
   */
  @Override
  public String getCustomer() {
    return this.customer;
  }

  /**
   * addPhoneCall
   * adds a phone call to a growable List
   * now sorts every single time you add something to it, what a waste.
   * @param call
   *        the call to be added to the phoneCalls list.
   */
  @Override
  public void addPhoneCall(PhoneCall call) {
    phoneCalls.add(call);
    // sorts after every phone call, could get to when get phone calls?
    Collections.sort(phoneCalls);
  }

  /**
   * returns the phonecalls list as a collection
   */
  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    return this.phoneCalls;
  }
}
