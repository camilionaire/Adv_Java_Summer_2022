package edu.pdx.cs410J.camilo3;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

public class TextDumper {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * @param bill
   *      the bill that dump will be reading from and dumping to writer.
   * parses the phonebill that is given as an argument
   */
  public void dump(PhoneBill bill) {
    SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
    try (
            PrintWriter pw = new PrintWriter(this.writer)
    ) {
      pw.println(bill.getCustomer());
      // this may be all I really needed to write in here I think...
      Collection<PhoneCall> theCalls = bill.getPhoneCalls();
      for (PhoneCall aCall : theCalls) {
        pw.println(aCall.getCaller() + " " + aCall.getCallee() +
                " " + sdf.format(aCall.getBeginTime()).toLowerCase()
                + " " + sdf.format(aCall.getEndTime()).toLowerCase());
      }
      pw.flush();
    }
  }

// this is all of the old code that I'm going to be replacing I guess...
//  public void dump(Map<String, String> dictionary) {
//    try (
//      PrintWriter pw = new PrintWriter(this.writer)
//    ){
//      for (Map.Entry<String, String> entry : dictionary.entrySet()) {
//        pw.println(entry.getKey() + " : " + entry.getValue());
//      }
//
//      pw.flush();
//    }
//  }
}
