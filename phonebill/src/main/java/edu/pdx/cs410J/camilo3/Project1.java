package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {
  @VisibleForTesting
  static boolean isValidPhoneNumber(String phoneNumber) {
    return true;
  }

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
  static boolean checkForReadme(String[] args) throws MissingCommandLineArguments {
    if (args.length == 0) {
//      System.err.println("Missing command line arguments");
      throw new MissingCommandLineArguments();
    } else if (args.length == 1) {
      if (args[0].equals("-README")) {
        printReadme();
        return true;
      } else {
        throw new MissingCommandLineArguments();
      }
    } else if (args.length == 2) {
      if (args[0].equals("-README") || args[1].equals("-README") && args[0].equals("-print")) {
        printReadme();
        return true;
      } else {
        throw new MissingCommandLineArguments();
      }
    } else if (args.length > 2) {
      if (args[0].equals("-README") || args[1].equals("-README") && args[0].equals("-print")) {
        printReadme();
        return true;
      }
    }
    {
      return false;
    }
  }
  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    boolean readme = false;

    try {
      readme = checkForReadme(args);
    } catch (MissingCommandLineArguments e) {
      System.err.println(e.getMessage());
    }

    if (!readme) {
      System.out.println("This is where the other stuff will work out!");
    }

    System.out.println("\n FOR TESTING!: These were the arguments we got:");
    for (String arg : args) {
      System.out.println(arg);
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