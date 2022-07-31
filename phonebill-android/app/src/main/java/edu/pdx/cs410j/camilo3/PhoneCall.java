package edu.pdx.cs410j.camilo3;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
    @Override
    public String getCaller() {
        return "123-456-7890";
    }

    @Override
    public String getCallee() {
        return "980-765-4321";
    }

    @Override
    public String getBeginTimeString() {
        return "1/1/2022 1:00 am";
    }

    @Override
    public String getEndTimeString() {
        return "1/1/2022 2:00 am";
    }
}
