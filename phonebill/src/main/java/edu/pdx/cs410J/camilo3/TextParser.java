package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class TextParser implements PhoneBillParser<PhoneBill> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

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

      // need to insert going through these phonecalls here
      // and add them all to the phonebill class.
      while ((customer = br.readLine()) != null) {
        String[] aCallArray = customer.split("\\s+");
        PhoneCall aCall = new PhoneCall(
                aCallArray[1], aCallArray[2], aCallArray[3] + " " + aCallArray[4],
                aCallArray[5] + " " + aCallArray[6]);
        aBill.addPhoneCall(aCall);
      }

      return aBill;

    } catch (IOException e) {
      throw new ParserException("While parsing phone bill text", e);
    }
  }
}
