package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * TextParser class implements PhoneBillParser
 */
public class TextParser implements PhoneBillParser<PhoneBill> {
  private final Reader reader;

  /**
   * Creates a new <code>TextParser</code>
   *
   * @param reader
   * the object of the Reader class that the text parser will be reading from
   */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

/**
 * parses the phonebill that is on the reader
 * if everything passes muster, will return a Phonebill
 * otherwise, will throw a ParserException with the msg attached from
 * actual custom build errors and messages.
 */
 @Override
  public PhoneBill parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {

      String customer = br.readLine();

      if (customer == null) {
        throw new ParserException("Missing customer");
      }

      PhoneBill aBill = new PhoneBill(customer);
      PhoneCallChecker checker = new PhoneCallChecker();

      SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
      // need to go through these phonecalls here
      // and add them all to the phonebill class.
      while ((customer = br.readLine()) != null) {
        ArrayList aCallArray = new ArrayList(Arrays.asList(customer.split(" ")));
        checker.checkForImproperFormatting(aCallArray);
        PhoneCall aCall = new PhoneCall(
                        (String) aCallArray.get(0), (String) aCallArray.get(1), sdf.parse(aCallArray.get(2) + " " + aCallArray.get(3) + " " + aCallArray.get(4)),
                        sdf.parse(aCallArray.get(5) + " " + aCallArray.get(6) + " " + aCallArray.get(7)));
        aBill.addPhoneCall(aCall);
      }

      return aBill;

    } catch (Exception e) { // switched from IOException initially.
      throw new ParserException("While parsing phone bill text an error was encountered.\n" +
              "One or more of the phone call listings was improperly formatted.\n" +
              "Proper usage: caller callee mm/dd/yyyy hh:mm a/pm mm/dd/yyyy hh:mm a/pm\n" +
              "Error was as shown:\n" +
              e.getMessage());
    }
  }
}
