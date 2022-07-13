package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * TextParser class implements PhoneBillParser
 */
public class TextParser implements PhoneBillParser<PhoneBill> {
  private final Reader reader;

  /**
   * Creates a new <code>PhoneBill</code>
   *
   * @param reader
   * the objece of the Reader class that the text parser will be reading from
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

      // need to go through these phonecalls here
      // and add them all to the phonebill class.
      while ((customer = br.readLine()) != null) {
        ArrayList aCallArray = new ArrayList(Arrays.asList(customer.split(" ")));
        checker.checkForImproperFormatting(aCallArray);
        PhoneCall aCall = new PhoneCall(
                aCallArray.get(0).toString(), aCallArray.get(1).toString(),
                aCallArray.get(2) + " " + aCallArray.get(3),
                aCallArray.get(4) + " " + aCallArray.get(5));
        aBill.addPhoneCall(aCall);
      }

      return aBill;

    } catch (Exception e) { // switched from IOException initially.
      throw new ParserException("While parsing phone bill text:\n" + e.getMessage());
    }
  }
}
