package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {

  @VisibleForTesting
  /**
   * wrapper function for throwing error if the names don't match
   */
  static void checkNamesMatch(String fileName, String CommandName) throws NamesDontMatch {
    if (! fileName.equals(CommandName)) { throw new NamesDontMatch(); }
  }

  @VisibleForTesting
  /**
   * wrapper function for throwing error if there are too many options
   */
  static void checkOutOfOptions(ArrayList<String> args) throws TooManyOptions {
    if (args.get(0).startsWith("-")) {
      throw new TooManyOptions();
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
    ArrayList<String> argList = new ArrayList<String>(Arrays.asList(args));

    OptionsChecker optChecker = new OptionsChecker();

    // runs through options and checks for a -README
    readme = optChecker.checkForReadme(argList);

    // if there is no readme, we check for -print
    if (!readme) {
      // checks only first item for -print, since only 2 possible
      // options are viable, throws false otherwise.
      printOption = optChecker.checkForPrint(argList);

      // this part checks for the formatting.
      // and then creates the actual objects with the formatted array.
      try {
        PhoneCallChecker checker = new PhoneCallChecker();
        String fileName = optChecker.checkForTextFile(argList);
        String prettyFileName = optChecker.checkForPrettyFile(argList);
        String ourName;

        // checks for handling on the command line and then passes that
        // to a formatted command line exception to be thrown and outputted
        try {
          checker.isArrayZero(argList);
          checkOutOfOptions(argList);
          ourName = argList.get(0);
          argList.remove(0);
          checker.checkForImproperFormatting(argList);
        } catch (Exception e) {
          throw new CommandLineException(e.getMessage());
        }


        // this looks for the file to open and load, or it
        // just makes a new file...
        if (fileName != null) {
          File customerFile = new File(fileName);
          if (customerFile.exists()) {
            FileReader fr = new FileReader(customerFile);
            TextParser parser = new TextParser(fr);
            aBill = parser.parse();
          } else {
            aBill = new PhoneBill(ourName);
          }
        } else {
          aBill = new PhoneBill(ourName);
        }

        checkNamesMatch(aBill.getCustomer(), ourName);

        // the date format that we are using...
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy h:mm a");

        aCall = new PhoneCall(
                argList.get(0), argList.get(1), sdf.parse(argList.get(2) + " " + argList.get(3) + " " + argList.get(4)),
                sdf.parse(argList.get(5) + " " + argList.get(6) + " " + argList.get(7)));
        aBill.addPhoneCall(aCall);

        if (fileName != null) {
          FileWriter fw = new FileWriter(fileName);
          PrintWriter pw = new PrintWriter(fw);
          TextDumper td = new TextDumper(pw);
          td.dump(aBill);
        }

        if (prettyFileName != null) {
          if (prettyFileName.equals("-")) {
            StringWriter sw = new StringWriter();
            PrettyPrinter pp = new PrettyPrinter(sw);
            pp.dump(aBill);
            System.out.println();
            System.out.println(sw);
          } else {
            FileWriter fw = new FileWriter(prettyFileName);
            PrintWriter pw = new PrintWriter(fw);
            PrettyPrinter pp = new PrettyPrinter(pw);
            pp.dump(aBill);
          }
        }

        if (printOption) {
          System.out.println(aCall);
        }
       // end of big try block which catches all exceptions.
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  } // end of main

  /**
   * exception that is thrown when the names don't match.
   */
  static class CommandLineException extends Exception {
    public CommandLineException(String msg) {
      super("While parsing the command line, there were irregularities\n" +
              "usage: java -jar target/phonebill-2022.0.0.jar [options] <args>\n" +
              "Run with the '-README' flag enabled for proper usage.\n" +
              "Error message shown was:\n" + msg);
    }
  }

  /**
   * exception that is thrown when there are too many options
   */
  static class TooManyOptions extends Exception {
    public TooManyOptions() {
      super( "UNRECOGNIZED OPTIONS!\n" +
              "Only options currently available are -print,\n" +
              "-README, -textFile file, and -pretty (- or file)");
    }
  }

  /**
   * exception that is thrown when the names don't match.
   */
  static class NamesDontMatch extends Exception {
    public NamesDontMatch() {
      super("IT LOOKS LIKE YOUR NAMES DON'T MATCH.\n" +
              "The filename customer and command line customer\n" +
              "do not match.  Please check input and try again.\n" +
              "Thank you.");
    }
  }

} // end of Project2 class.