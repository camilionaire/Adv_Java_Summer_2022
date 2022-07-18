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

  /**
   * if -README option is invoked, this method is called and prints the
   * readme file found at README.txt.
   */
  private static void printReadme() {
    try (InputStream readme = Project3.class.getResourceAsStream("README.txt")
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
  static boolean checkForReadme(ArrayList<String> argList) {
    String prev = null;
    for (String arg : argList) {
      if (arg.equals("-README")) {
        printReadme();
        return true;
      } else if (! arg.startsWith("-") && (arg == argList.get(0) || !prev.equals("-textFile"))) {
        return false;
      }
      prev = arg;
    }
    return false;
  }

  /**
   * just sees if there is a -print option in any of the options
   * works with textfile additional possibility
   */
  public static boolean checkForPrint(ArrayList<String> argList) {
    String prev = null;
    for (int i=0; i < argList.size(); i++) {
      if (argList.get(i).equals("-print")) {
        argList.remove(i);
        return true;
      } else if (! argList.get(i).startsWith("-") && (i == 0 || !prev.equals("-textFile"))) {
        return false;
      }
      prev = argList.get(i);
    }
    return false;
  }

  /**
   * just sees if there is a -textFile file option in any of the options
   * if there is, it will remove the next arg as the filename, delete that
   * delete the option and return the string, else return null string.
   * if the next arg is an option or is non-existent, throws error.
   */
  public static String checkForTextFile(ArrayList<String> argList) throws MissingFileName {
    String turnString = null;
    for (int i=0; i < argList.size(); i++) {
      if (argList.get(i).equals("-textFile")) {
        if (i+1 < argList.size() && ! argList.get(i+1).startsWith("-")) {
          turnString = argList.get(i+1);
          argList.remove(i+1);
          argList.remove(i);
        } else {
          throw new MissingFileName();
        }
      }
    }
    return turnString;
  }

  @VisibleForTesting
  /**
   * wrapper function for throwing error if the names don't match
   */
  static void checkNamesMatch(String fileName, String CommandName) throws NamesDontMatch {
    if (! fileName.equals(CommandName)) { throw new NamesDontMatch(); }
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

    // runs through options and checks for a -README
    readme = checkForReadme(argList);

    // if there is no readme, we check for -print
    if (!readme) {
      // checks only first item for -print, since only 2 possible
      // options are viable, throws false otherwise.
      printOption = checkForPrint(argList);

      // this part checks for the formatting.
      // and then creates the actual objects with the formatted array.
      try {
        PhoneCallChecker checker = new PhoneCallChecker();
        String fileName = checkForTextFile(argList);

        checker.isArrayZero(argList);

        String ourName = argList.get(0);
        argList.remove(0);

        checker.checkForImproperFormatting(argList);

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
   * exception that is thrown when the file argument is missing.
   */
  static class MissingFileName extends Exception {
    public MissingFileName() {
      super("IT LOOKS LIKE YOU'RE MISSING A FILENAME.\n" +
              "proper usage is -textFile file <args>\n" +
              "please run with -README flag for more details.\n" +
              "Thank you.");
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