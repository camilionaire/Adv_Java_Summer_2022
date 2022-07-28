package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneBill} class.
 */
public class PhoneBillTest {

    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy h:mm a");
    /**
     * This unit test makes sure something is created when we create the object.
     */
    @Test
    void test01ToSeeThatBillIsCreated() {
        PhoneBill aBill = new PhoneBill("Steven");
        assertNotNull(aBill);
    }
    /**
     *
     * This unit test makes sure getCustomer works
     */
    @Test
    void test02ToSeeThatGetCustomerWorks() {
        PhoneBill aBill = new PhoneBill("Steven");
        assertThat(aBill.getCustomer(), containsString("Steven"));
    }

    /**
     * This unit test makes sure getPhoneCalls returns an empty list
     * of phone calls.
     */
    @Test
    void test03ToSeeThatGetPhoneCallsWorksZeroEntries() {
        PhoneBill aBill = new PhoneBill("Steven");
        // I guess this is how this works?...
        assertEquals(aBill.getPhoneCalls(), Arrays.asList(new PhoneCall[0]));
    }

    /**
     * This unit test makes sure getPhoneCalls works when there are no Phone calls.
     * with the size of the list that is returned.
     */
    @Test
    void test04ToSeeThatPhoneCallsIsZeroSetAndGetPhoneCallsWorksZeroEntries() {
        PhoneBill aBill = new PhoneBill("Steven");
        assertEquals(aBill.getPhoneCalls().size(), 0);
    }

    /**
     * This unit test makes sure addPhoneCalls works when there is one phone call
     */
    @Test
    void test05ToSeeThatAddingAPhoneCallWorks() throws ParseException {
        PhoneBill aBill = new PhoneBill("Steven");
        PhoneCall call = new PhoneCall(
                "503-867-5309", "800-666-1234", sdf.parse("03/2/2022 1:03 am") ,sdf.parse("3/15/2022 10:39 pm"));

        aBill.addPhoneCall(call);
        Collection<PhoneCall> comparePhoneCalls = Arrays.asList(call);

        assertEquals(aBill.getPhoneCalls().size(), 1);
        assertEquals(aBill.getPhoneCalls(), comparePhoneCalls);
    }

    /**
     * This unit test makes sure getPhoneCalls works when we add 2 phoneCalls
     */
    @Test
    void test06ToSeeThatAddingAPhoneCallWorksForSizeTwo() throws ParseException {
        PhoneBill aBill = new PhoneBill("Steven");
        PhoneCall call = new PhoneCall(
                "503-867-5309", "800-666-1234", sdf.parse("03/2/2022 1:03 am") ,sdf.parse("3/15/2022 10:39 pm"));
        PhoneCall call2 = new PhoneCall(
                "503-867-5309", "800-666-1234", sdf.parse("03/2/2022 1:03 am") ,sdf.parse("3/15/2022 10:39 pm"));

        aBill.addPhoneCall(call);
        aBill.addPhoneCall(call2);

        assertEquals(aBill.getPhoneCalls().size(), 2);
    }

    /**
     * This unit test makes sure getPhoneCalls works when we add 2 phoneCalls
     */
    @Test
    void test07ToSeeThatTwoPhoneCallsGetOrderedCorrectlyInArray() throws ParseException {
        PhoneBill aBill = new PhoneBill("Steven");
        PhoneCall call = new PhoneCall(
                "503-867-5309", "800-666-1234", sdf.parse("03/2/2022 1:03 pm") ,sdf.parse("3/02/2022 10:39 pm"));
        PhoneCall call2 = new PhoneCall(
                "503-867-5309", "800-666-1234", sdf.parse("03/2/2022 12:01 pm") ,sdf.parse("3/02/2022 10:39 pm"));

        aBill.addPhoneCall(call);
        aBill.addPhoneCall(call2);

        ArrayList<PhoneCall> ordList = (ArrayList<PhoneCall>) aBill.getPhoneCalls();
        assertEquals(ordList.size(), 2);
        assertTrue(ordList.get(0).equals(call2));
    }

    /**
     * This unit test makes sure our print statement works with our phone bill after adding
     * NOT SURE HOW TO DO A FAKE OR A MOCK YET... STILL WATCHING THE VIDEOS
     */
    @Test
    void test08ToSeeThatAddingAPhoneCallAndPrintToStringWorks() throws ParseException {
        PhoneBill aBill = new PhoneBill("Steven");
        PhoneCall call = new PhoneCall(
                "503-867-5309", "800-666-1234", sdf.parse("03/2/2022 1:03 am") ,sdf.parse("3/15/2022 10:39 pm"));

        aBill.addPhoneCall(call);

        assertEquals(aBill.toString(), "Steven's phone bill with 1 phone calls");
    }
}
