package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * a class for doing a lot of validation on the individual options
 * and arguments
 */
public class OptionsChecker {

    /**
     * if -README option is invoked, this method is called and prints the
     * readme file found at README.txt.
     */
    @VisibleForTesting
    static void printReadme() {
        try (InputStream readme = Project4.class.getResourceAsStream("README.txt")
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
    public static boolean checkForReadme(ArrayList<String> argList) {
        String prev = null;
        for (String arg : argList) {
            if (arg.equals("-README")) {
                printReadme();
                return true;
            } else if (! arg.startsWith("-") &&
                    (arg == argList.get(0) ||
                            (!prev.equals("-textFile") && !prev.equals("-pretty")))) {
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
            } else if (! argList.get(i).startsWith("-") &&
                    (i == 0 || ( !prev.equals("-textFile") && !prev.equals("-pretty")))) {
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
        String prev = null;
        String turnString = null;
        for (int i=0; i < argList.size(); i++) {
            String curr = argList.get(i);
            if (curr.equals("-textFile")) {
                // if we haven't reached the end of the args
                // and the next arg isn't an option
                if (i + 1 < argList.size() && !argList.get(i + 1).startsWith("-")) {
                    turnString = argList.get(i + 1);
                    argList.remove(i + 1);
                    argList.remove(i);
                } else {
                    throw new MissingFileName();
                }
            } else if (! curr.startsWith("-") &&
                    (i == 0 || !prev.equals("-pretty"))) {
                    break;
                }
                prev = curr;
        }
        return turnString;
    }

    /**
     * just sees if there is a -pretty option in any of the options
     * if there is, it will remove the next arg as the filename/'-', delete that
     * delete the option and return the string, else return null string.
     * if the next arg is an option or is non-existent, throws error.
     */
    public static String checkForPrettyFile(ArrayList<String> argList) throws MissingFileName {
        String turnString = null;
        String prev = null;
        for (int i=0; i < argList.size(); i++) {
            String curr = argList.get(i);
            if (curr.equals("-pretty")) {
                // if we haven't reached the end of the args
                // and the next arg isn't an option or is '-'
                if (i+1 < argList.size() &&
                        (argList.get(i+1).equals("-") ||
                                ! argList.get(i+1).startsWith("-"))) {
                    turnString = argList.get(i+1);
                    argList.remove(i+1);
                    argList.remove(i);
                } else {
                    throw new MissingFileName();
                }
            } else if (! curr.startsWith("-") &&
                    (i == 0 || !prev.equals("-textFile"))) {
                break;
            }
            prev = curr;
        }
        return turnString;
    }

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

}
