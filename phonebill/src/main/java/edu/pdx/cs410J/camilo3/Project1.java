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
public class Project1 {

  private static void printReadme() {
    try (InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      for (String line; (line = reader.readLine()) != null;) {
        System.out.println(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

//  @VisibleForTesting
  public static void checkForZeroArgs(String[] args) throws MissingCommandLineArguments {
    if ((args == null) || (args.length == 0)) {
      throw new MissingCommandLineArguments();
    }
  }

  public static boolean checkForReadme(String[] args) {
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
  public static boolean checkForPrint(String[] args) {
//    return (args[0].equals("-print"));
    for (String arg : args) {
      if (arg.equals("-print")) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  @VisibleForTesting
  static boolean isValidPhoneNumber(String phoneNumber) {
    return Pattern.matches("^\\d{3}-\\d{3}-\\d{4}$", phoneNumber);
  }

  @VisibleForTesting
  static boolean isValidDate(String date) {
    return Pattern.matches("^\\d?\\d/\\d?\\d/\\d{4}$", date);
  }

  @VisibleForTesting
  static boolean isValidTime(String time) {
    return Pattern.matches("^[012]?\\d:[0-5]\\d$", time);
  }


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


  public static void main(String[] args) {
    boolean readme, printOption;
    PhoneBill aBill;
    PhoneCall aCall;
    String[] clippedArgs;

    try {
      checkForZeroArgs(args);
    } catch (MissingCommandLineArguments e) {
      System.err.println(e.getMessage());
    }

    readme = checkForReadme(args);

    if (!readme) {
//      System.out.println("This is where the other stuff will work out!");

      printOption = checkForPrint(args);
      if (printOption) {
        clippedArgs = Arrays.copyOfRange(args, 1, args.length);
      } else {
        clippedArgs = args;
      }
//
      try {
        checkForImproperFormatting(clippedArgs);
        aBill = new PhoneBill(clippedArgs[0]);
        aCall = new PhoneCall(
                clippedArgs[1], clippedArgs[2], clippedArgs[3] + clippedArgs[4], clippedArgs[5] + clippedArgs[6]);
        aBill.addPhoneCall(aCall);
        if (printOption) {
          System.out.println(aBill);
        }
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }



    }

//    System.out.println("\n FOR TESTING!: These were the arguments we got:");
//    for (String arg : args) {
//      System.out.println(arg);
//    }
  } // end of main

  static class ImproperPhoneNumber extends Exception {
    public ImproperPhoneNumber() {
      super( "INCORRECT FORMATTING OF PHONE NUMBERS\n" +
              "should be nnn-nnn-nnnn where n is a digit btwn 0-9\n" +
              "seperated by hyphens.\n" +
              "example: 503-867-5309\n" + "Thank you.");
    }
  }

  static class ImproperDate extends Exception {
    public ImproperDate() {
      super("INCORRECT FORMATTING OF DATES\n" +
              "should be nn/nn/nnnn or n/n/nnnn where n is a\n" +
              "digit btwn 0-9 seperated by backslashes.\n" +
              "example: 03/7/2022\n" + "Thank you.");
    }
  }

  static class ImproperTime extends Exception {
    public ImproperTime() {
      super("INCORRECT FORMATTING OF TIMES\n" +
              "should be nn:nn or n:nn where n is a\n" +
              "digit btwn 0-9 seperated by a colon.\n" +
              "example: 03:42 or 11:13\n" + "Thank you.");
    }
  }
  @VisibleForTesting
  static class MissingCommandLineArguments extends Exception {
    public MissingCommandLineArguments() {
      super( "INCORRECT USE OF COMMAND LINE ARGUMENTS\n" +
              "usage: java -jar target/phonebill-2022.0.0.jar [options] <args>\n" +
              "args are (in this order):\n" +
              "customer \tPerson whose phone bill we’re modeling\n" +
              "callerNumber \tPhone number of caller\n" +
              "calleeNumber \tPhone number of person who was called\n" +
              "begin \tDate and time call began (24-hour time)\n" +
              "end \tDate and time call ended (24-hour time)\n" +
              "options are (options may appear in any order):\n" +
              "-print \tPrints a description of the new phone call\n" +
              "-README \tPrints a README for this project and exits\n" +
              "Date and time should be in the format: mm/dd/yyyy hh:mm");
    }
  }
}