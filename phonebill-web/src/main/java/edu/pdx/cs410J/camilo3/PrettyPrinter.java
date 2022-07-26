package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter {
  private final Writer writer;

//  @VisibleForTesting
//  static String formatWordCount(int count )
//  {
//    return String.format( "Dictionary on server contains %d words", count );
//  }
//
//  @VisibleForTesting
//  static String formatDictionaryEntry(String word, String definition )
//  {
//    return String.format("  %s : %s", word, definition);
//  }


  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  /**
   * @param bill
   *      the bill that dump will be reading from and pretty printing to writer.
   * parses the phonebill that is given as an argument
   * makes everything look so gosh darn good.
   */
  public void dump(AbstractPhoneBill bill) {
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yy  hh:mm a");

    try (
            PrintWriter pw = new PrintWriter(this.writer)
    ) {
      pw.println("Customer name:\n" + bill.getCustomer());
      pw.println();
      pw.println("Caller:       Callee:       Call Begins:          Call Ends:            Time:");
      pw.println("------------  ------------  --------------------  --------------------  --------");
      // this may be all I really needed to write in here I think...
      Collection<PhoneCall> theCalls = bill.getPhoneCalls();
      for (PhoneCall aCall : theCalls) {
        Date begin = aCall.getBeginTime();
        Date end = aCall.getEndTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(
                end.getTime() - begin.getTime());
        pw.println(aCall.getCaller() +
                "  " + aCall.getCallee() +
                "  " + sdf.format(begin) +
                "  " + sdf.format(end) +
                // could edit to be responsive for 1 min?....
                "  " + minutes + " mins");
      }
      pw.flush();
    }
  }

//  public void dump(Map<String, String> dictionary) {
//    try (
//      PrintWriter pw = new PrintWriter(this.writer)
//    ) {
//
//      pw.println(formatWordCount(dictionary.size()));
//
//      for (Map.Entry<String, String> entry : dictionary.entrySet()) {
//        String word = entry.getKey();
//        String definition = entry.getValue();
//        pw.println(formatDictionaryEntry(word, definition));
//      }
//
//      pw.flush();
//    }
//
//  }
}
