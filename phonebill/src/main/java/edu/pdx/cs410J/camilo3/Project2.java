package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.*;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {

  /**
   * if -README option is invoked, this method is called and prints the
   * readme file found at README.txt.
   */
  private static void printReadme() {
    try (InputStream readme = Project2.class.getResourceAsStream("README.txt")
    ) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      for (String line; (line = reader.readLine()) != null;) {
        System.out.println(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * just sees if there is a -README option in any of the options
   */
  @VisibleForTesting
  static boolean checkForReadme(String[] args) {
    for (String arg : args) {
      if (arg.equals("-README")) {
        printReadme();
        return true;
      } else if (! arg.startsWith("-")) {
        return false;
      }
    }
    return false;
  }

  /**
   * just sees if there is a -print option in any of the options
   * this currently only checks the first argument if any as that
   * is the only legal argument left after -README has been read
   */
  public static boolean checkForPrint(String[] args) {
    boolean printOption = false;
    for (String arg : args) {
      if (arg.equals("-print")) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  /**
   * returns true if is of the form nnn-nnn-nnnn, false otherwise
   */
  @VisibleForTesting
  static boolean isValidPhoneNumber(String phoneNumber) {
    return Pattern.matches("^\\d{3}-\\d{3}-\\d{4}$", phoneNumber);
  }

  /**
   * returns true if date is of the regex form ^\\d?\\d/\\d?\\d/\\d{4}$
   * false otherwise
   */
  @VisibleForTesting
  static boolean isValidDate(String date) {
    return Pattern.matches("^\\d?\\d/\\d?\\d/\\d{4}$", date);
  }

  /**
   * returns true if time is of the regex form ^[012]?\\d:[0-5]\\d$
   * false otherwise
   */
  @VisibleForTesting
  static boolean isValidTime(String time) {
    return Pattern.matches("^[012]?\\d:[0-5]\\d$", time);
  }


  /**
   * takes the args and makes sure they are the correct length, and then
   * makes sure each individual argument is in the correct format,
   * responds with an exception depending on which argument is invalid.
   */
  static void checkForImproperFormatting(String[] args)
          throws MissingCommandLineArguments, ImproperPhoneNumber, ImproperDate, ImproperTime {
    if (args.length != 7) {
      throw new MissingCommandLineArguments();
    } else if (! isValidPhoneNumber(args[1]) || ! isValidPhoneNumber(args[2])) {
      throw new ImproperPhoneNumber();
    } else if (! isValidDate(args[3]) || ! isValidDate(args[5])) {
      throw new ImproperDate();
    } else if (! isValidTime(args[4]) || ! isValidTime(args[6])) {
      throw new ImproperTime();
    }
  }


  /**
   * main method, can take is arguments from the command line, parses and checks them for
   * validity and prints exception messages if there are any
   * makes a {@link PhoneCall} object and a {@link PhoneBill} and adds phoneCall to
   * phoneBill
   * prints out a message if -print option is employed.
   */
  public static void main(String[] args) {
    boolean readme, printOption;
    PhoneBill aBill;
    PhoneCall aCall;
    String[] clippedArgs;

    // runs through options and checks for a -README
    readme = checkForReadme(args);

    // if there is no readme, we check for -print
    if (!readme) {

      // checks only first item for -print, since only 2 possible
      // options are viable, throws false otherwise.
      printOption = checkForPrint(args);

      if (printOption) {
        clippedArgs = Arrays.copyOfRange(args, 1, args.length);
      } else {
        clippedArgs = args;
      }

      // this part checks for the formatting.
      // and then creates the actual objects with the formatted array.
      try {
        checkForImproperFormatting(clippedArgs);
        aBill = new PhoneBill(clippedArgs[0]);
        aCall = new PhoneCall(
                clippedArgs[1], clippedArgs[2], clippedArgs[3] + " " + clippedArgs[4],
                clippedArgs[5] + " " + clippedArgs[6]);
        aBill.addPhoneCall(aCall);
        if (printOption) {
          System.out.println(aCall);
        }
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  } // end of main

  /**
   * exception that is thrown when the phone number arguments are messed up.
   */
  static class ImproperPhoneNumber extends Exception {
    public ImproperPhoneNumber() {
      super( "INCORRECT FORMATTING OF PHONE NUMBERS\n" +
              "should be nnn-nnn-nnnn where n is a digit btwn 0-9\n" +
              "seperated by hyphens.\n" +
              "example: 503-867-5309\n" + "Thank you.");
    }
  }

  /**
   * exception that is thrown when the date arguments are messed up.
   */
  static class ImproperDate extends Exception {
    public ImproperDate() {
      super("INCORRECT FORMATTING OF DATES\n" +
              "should be nn/nn/nnnn or n/n/nnnn where n is a\n" +
              "digit btwn 0-9 seperated by backslashes.\n" +
              "example: 03/7/2022\n" + "Thank you.");
    }
  }

  /**
   * exception that is thrown when the time arguments are messed up.
   */
  static class ImproperTime extends Exception {
    public ImproperTime() {
      super("INCORRECT FORMATTING OF TIMES\n" +
              "should be nn:nn or n:nn where n is a\n" +
              "digit btwn 0-9 seperated by a colon.\n" +
              "example: 03:42 or 11:13\n" + "Thank you.");
    }
  }

  /**
   * exception that is thrown when the time arguments are messed up.
   */
  @VisibleForTesting
  static class MissingCommandLineArguments extends Exception {
    public MissingCommandLineArguments() {
      super( "INCORRECT USE OF COMMAND LINE ARGUMENTS\n" +
              "usage: java -jar target/phonebill-2022.0.0.jar [options] <args>\n" +
              "Please use option -README for complete list of options and args.");
    }
  }
}