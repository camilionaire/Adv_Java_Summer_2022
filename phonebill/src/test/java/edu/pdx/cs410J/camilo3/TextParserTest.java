package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextParserTest {

  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-phonebill.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    PhoneBill bill = parser.parse();
    assertThat(bill.getCustomer(), equalTo("Test Phone Bill"));
  }

  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-phonebill.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  @Test
  void wrongTimeFormatOnEndTime() {
    InputStream resource = getClass().getResourceAsStream("wrong-callee-number.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Exception exception = assertThrows(ParserException.class, parser::parse);
    assertTrue(exception.getMessage().contains(
            "While parsing phone bill text an error was encountered.\n" +
                    "One or more of the phone call listings was improperly formatted.\n" +
                    "Proper usage: caller callee mm/dd/yyyy hh:mm a/pm mm/dd/yyyy hh:mm a/pm\n" +
                    "Error was as shown:\n" +
                    "INCORRECT FORMATTING OF PHONE NUMBERS"));
  }
}
