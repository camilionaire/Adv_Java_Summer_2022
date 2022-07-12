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

//  /**
//   * this removes the print statement.
//   */
//  public static boolean removePrint(String[] args) {
//    int place = 0;
//    for (int i = 0; i < args.length; i++) {
//      if (args[i].equals("-print")) {
//        place = i;
//      }
//    }
////    return Arrays.copyOfRange(args, 0, place) + Arrays.copyOfRange(args, place + 1, args.length);
//    return true;
//  }

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
        PhoneCallChecker checker = new PhoneCallChecker();
        checker.isArrayZero(args);

        aBill = new PhoneBill(clippedArgs[0]);

        checker.checkForImproperFormatting(Arrays.copyOfRange(clippedArgs, 1, clippedArgs.length));

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

}