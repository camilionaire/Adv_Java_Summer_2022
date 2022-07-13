package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AppointmentBookDumper;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  @Override
  public void dump(PhoneBill bill) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {
      pw.println(bill.getCustomer());
      // this may be all I really needed to write in here I think...
      Collection<PhoneCall> theCalls = bill.getPhoneCalls();
      for (PhoneCall aCall : theCalls) {
        pw.println(aCall.getCaller() + " " + aCall.getCallee() +
                " " + aCall.getBeginTimeString() + " " + aCall.getEndTimeString());
      }
      pw.flush();
    }
  }
}
