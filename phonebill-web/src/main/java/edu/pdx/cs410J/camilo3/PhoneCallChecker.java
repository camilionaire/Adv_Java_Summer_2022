package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * a class for doing a lot of validation on the individual phone calls
 */
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
        return Pattern.matches("^[01]?\\d:[0-5]\\d ([AP]M|[ap]m)$", time);
    }

    /**
     * returns true if the start is before or equal to the end
     * false otherwise
     */
    @VisibleForTesting
    static boolean isStartBeforeEnd(Date start, Date end) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(
                end.getTime() - start.getTime());
        if (seconds >= 0L) { return true; }
        else { return false;}
    }

    /**
     * throws error if there are no command line arguments
     */
    @VisibleForTesting
    static boolean isArrayZero(ArrayList args) {
        if (args.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * takes the args and makes sure they are the correct length, and then
     * makes sure each individual argument is in the correct format,
     * responds with an exception depending on which argument is invalid.
     */
    static void checkForImproperFormatting(ArrayList<String> args)
            throws MissingCommandLineArguments, ImproperTime, ImproperDate,
            ImproperPhoneNumber, ExtraneousCommandLineArguments,
            ParseException, EndIsBeforeStart {
        if (args.size() < 8) {
            throw new MissingCommandLineArguments();
        } else if (args.size() > 8) {
            throw new ExtraneousCommandLineArguments();
        } else if (! isValidPhoneNumber(args.get(0)) ||
                ! isValidPhoneNumber(args.get(1))) {
            throw new ImproperPhoneNumber();
        } else if (! isValidDate(args.get(2)) ||
                ! isValidDate(args.get(5))) {
            throw new ImproperDate();
        } else if (! isValidTime(args.get(3) + " " + args.get(4)) ||
                ! isValidTime(args.get(6) + " " + args.get(7))) {
            throw new ImproperTime();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
        Date start = sdf.parse(args.get(2) + " " + args.get(3) + " " + args.get(4));
        Date end = sdf.parse(args.get(5) + " " + args.get(6) + " " + args.get(7));
        if ( ! isStartBeforeEnd(start, end)) {
            throw new EndIsBeforeStart();
        }
    }

    static void checkADateTime(ArrayList<String> args) throws ImproperDate, ExtraneousCommandLineArguments,
            MissingCommandLineArguments, ImproperTime, ParseException, EndIsBeforeStart {
//        System.out.println(args); // put in for error testing.
        if (args.size() < 6) {
            throw new MissingCommandLineArguments();
        } else if (args.size() > 6) {
            throw new ExtraneousCommandLineArguments();
        } else if (! isValidDate(args.get(0)) || ! isValidDate(args.get(3))) {
            throw new ImproperDate();
        } else if (! isValidTime(args.get(1) + " " + args.get(2)) ||
                ! isValidTime(args.get(4) + " " + args.get(5))) {
            throw new ImproperTime();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm a");
        Date start = sdf.parse(args.get(0) + " " + args.get(1) + " " + args.get(2));
        Date end = sdf.parse(args.get(3) + " " + args.get(4) + " " + args.get(5));
        if ( ! isStartBeforeEnd(start, end)) {
            throw new EndIsBeforeStart();
        }
    }

    /**
     * exception that is thrown when end time is before start
     */
    static class EndIsBeforeStart extends Exception {
        public EndIsBeforeStart() {
            super( "END TIME IS BEFORE START!\n" +
                    "When we were examining the phonecall,\n" +
                    "we noticed the end time is before the start time.\n" +
                    "Please check your input or laws of physics.");
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
                    "should be nn:nn or n:nn (AM/am/PM/pm) where n is a\n" +
                    "digit between 0-9 seperated by a colon.\n" +
                    "example: 03:42 pm or 11:13 AM\n" + "Thank you.");
        }
    }

    /**
     * exception that is thrown when there are too many arguments
     */
    static class ExtraneousCommandLineArguments extends Exception {
        public ExtraneousCommandLineArguments() {
            super( "TOO MANY ARGUMENTS");
        }
    }
    /**
     * exception that is thrown when there are too few arguments
     */
    @VisibleForTesting
    static class MissingCommandLineArguments extends Exception {
        public MissingCommandLineArguments() {
            super("TOO FEW ARGUMENTS");
        }
    }

}
