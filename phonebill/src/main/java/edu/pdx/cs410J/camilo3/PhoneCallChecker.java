package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class PhoneCallChecker {

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
     * returns true if time is of the regex form ^[01]?\\d:[0-5]\\d$
     * false otherwise
     */
    @VisibleForTesting
    static boolean isValidTime(String time) {
        return Pattern.matches("^[01]?\\d:[0-5]\\d [ap]m$", time);
    }

    /**
     * throws error if there are no command line arguments
     */
    @VisibleForTesting
    static void isArrayZero(ArrayList args) throws MissingCommandLineArguments {
        if (args.size() == 0) {
            throw new MissingCommandLineArguments();
        }
    }

    /**
     * takes the args and makes sure they are the correct length, and then
     * makes sure each individual argument is in the correct format,
     * responds with an exception depending on which argument is invalid.
     */
    static void checkForImproperFormatting(ArrayList args)
            throws MissingCommandLineArguments, ImproperTime, ImproperDate,
                ImproperPhoneNumber, ExtraneousCommandLineArguments, TooManyOptions {
        if (args.get(0).toString().startsWith("-")) {
           throw new TooManyOptions();
        } else if (args.size() < 8) {
            throw new MissingCommandLineArguments();
        } else if (args.size() > 8) {
            throw new ExtraneousCommandLineArguments();
        } else if (! isValidPhoneNumber(args.get(0).toString()) || ! isValidPhoneNumber(args.get(1).toString())) {
            throw new ImproperPhoneNumber();
        } else if (! isValidDate(args.get(2).toString()) || ! isValidDate(args.get(5).toString())) {
            throw new ImproperDate();
        } else if (! isValidTime(args.get(3).toString() + " " + args.get(4).toString()) ||
                ! isValidTime(args.get(6).toString() + " " + args.get(7).toString())) {
            throw new ImproperTime();
        }
    }

    /**
     * exception that is thrown when there are too many options
     */
    static class TooManyOptions extends Exception {
        public TooManyOptions() {
            super( "UNRECOGNIZED OPTIONS!\n" +
                    "Only options currently available are -print\n" +
                    "-README and -textFile file\n" +
                    "Please run with option -REAME for more options.");
        }
    }

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
                    "should be nn:nn or n:nn am/pm where n is a\n" +
                    "digit btwn 0-9 seperated by a colon.\n" +
                    "example: 03:42 pm or 11:13 am\n" + "Thank you.");
        }
    }

    /**
     * exception that is thrown when there are too many arguments
     */
    static class ExtraneousCommandLineArguments extends Exception {
        public ExtraneousCommandLineArguments() {
            super( "TOO MANY COMMAND LINE ARGUMENTS\n" +
                    "usage: java -jar target/phonebill-2022.0.0.jar [options] <args>\n" +
                    "Please use option -README for complete list of options and args.");
        }
    }
    /**
     * exception that is thrown when there are too few arguments
     */
    @VisibleForTesting
    static class MissingCommandLineArguments extends Exception {
        public MissingCommandLineArguments() {
            super( "TOO FEW COMMAND LINE ARGUMENTS\n" +
                    "usage: java -jar target/phonebill-2022.0.0.jar [options] <args>\n" +
                    "Please use option -README for complete list of options and args.");
        }
    }

}
