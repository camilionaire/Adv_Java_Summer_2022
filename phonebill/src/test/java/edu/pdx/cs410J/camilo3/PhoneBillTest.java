package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneBill} class.
 */
public class PhoneBillTest {
    /**
     * This unit test makes sure something is created when we create the object.
     */
    @Test
    void testToSeeThatBillIsCreated() {
        PhoneBill aBill = new PhoneBill("Steven");
        assertNotNull(aBill);
    }
    /**
     *
     * This unit test makes sure getCustomer works
     */
    @Test
    void testToSeeThatGetCustomerWorks() {
        PhoneBill aBill = new PhoneBill("Steven");
        assertThat(aBill.getCustomer(), containsString("Steven"));
    }

    /**
     * This unit test makes sure getPhoneCalls returns an empty list
     * of phone calls.
     */
    @Test
    void testToSeeThatGetPhoneCallsWorksZeroEntries() {
        PhoneBill aBill = new PhoneBill("Steven");
        // I guess this is how this works?...
        assertEquals(aBill.getPhoneCalls(), Arrays.asList(new PhoneCall[0]));
    }

    /**
     * This unit test makes sure getPhoneCalls works when there are no Phone calls.
     * with the size of the list that is returned.
     */
    @Test
    void testToSeeThatPhoneCallsIsZeroSetAndGetPhoneCallsWorksZeroEntries() {
        PhoneBill aBill = new PhoneBill("Steven");
        // I guess this is how this works?...
        assertEquals(aBill.getPhoneCalls().size(), 0);
    }

    /**
     * This unit test makes sure addPhoneCalls works when there is one phone call
     */
    @Test
    void testToSeeThatAddingAPhoneCallWorks() {
        PhoneBill aBill = new PhoneBill("Steven");
        PhoneCall call = new PhoneCall(
                "503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");

        aBill.addPhoneCall(call);
        Collection<PhoneCall> comparePhoneCalls = Arrays.asList(call);

        assertEquals(aBill.getPhoneCalls().size(), 1);
        assertEquals(aBill.getPhoneCalls(), comparePhoneCalls);
    }

    /**
     * This unit test makes sure getPhoneCalls works when we add 2 phoneCalls
     */
    @Test
    void testToSeeThatAddingAPhoneCallWorksForSizeTwo() {
        PhoneBill aBill = new PhoneBill("Steven");
        PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");
        PhoneCall call2 = new PhoneCall("503-867-5309", "800-666-1234", "03/16/2022 1:03" ,"3/16/2022 10:39");

        aBill.addPhoneCall(call);
        aBill.addPhoneCall(call2);

        assertEquals(aBill.getPhoneCalls().size(), 2);
    }

    /**
     * This unit test makes sure our print statement works with our phone bill after adding
     * NOT SURE HOW TO DO A FAKE OR A MOCK YET... STILL WATCHING THE VIDEOS
     */
    @Test
    void testToSeeThatAddingAPhoneCallAndPrintToStringWorks() {
        PhoneBill aBill = new PhoneBill("Steven");
        PhoneCall call = new PhoneCall("503-867-5309", "800-666-1234", "03/2/2022 1:03" ,"3/15/2022 10:39");

        aBill.addPhoneCall(call);

        assertEquals(aBill.toString(), "Steven's phone bill with 1 phone calls");
    }
}
