package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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
  static boolean checkForReadme(ArrayList<String> argList) {
    String prev = null;
    for (String arg : argList) {
      if (arg.equals("-README")) {
        printReadme();
        return true;
      } else if (! arg.startsWith("-") && !prev.equals("-textFile")) {
        return false;
      }
      prev = arg;
    }
    return false;
  }

  /**
   * just sees if there is a -print option in any of the options
   * this currently only checks the first argument if any as that
   * is the only legal argument left after -README has been read
   */
  public static boolean checkForPrint(ArrayList<String> argList) {
    String prev = null;
    for (int i=0; i < argList.size(); i++) {
      if (argList.get(i).equals("-print")) {
        argList.remove(i);
        return true;
      } else if (! argList.get(i).startsWith("-") && !prev.equals("-textFile")) {
        return false;
      }
      prev = argList.get(i);
    }
    return false;
  }

  /**
   * just sees if there is a -textFile file option in any of the options
   * if there is, it will remove the next line as the filename, delete that
   * delete the option and return the string, else return null string.
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
          System.out.println("This is where the file logic goes.");
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

        if (! aBill.getCustomer().equals(ourName)) {
          throw new NamesDontMatch();
        }

        aCall = new PhoneCall(
                argList.get(0), argList.get(1), argList.get(2) + " " + argList.get(3),
                argList.get(4) + " " + argList.get(5));
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
      super("IT LOOKS LIKE YOU'RE NAMES DON'T MATCH.\n" +
              "The filename customer and command line customer\n" +
              "do not match.  Please check input and try again.\n" +
              "Thank you.");
    }
  }

} // end of Project2 class.