package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements PhoneBillDumper {

    private final Writer writer;

    /**
     * Creates a new <code>PrettyPrinter</code>
     *
     * @param writer
     *      the object of the Writer class that the pretty printer will be writing to
     */
    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    /**
     * @param bill
     *      the bill that dump will be reading from and pretty printing to writer.
     * parses the phonebill that is given as an argument
     */
    @Override
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
                        "  " + minutes + " mins");
            }
            pw.flush();
        }
    }
}
