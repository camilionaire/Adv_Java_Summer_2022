package edu.pdx.cs410J.camilo3;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String requiredParameterNotFormattedCorrectly( String parameterName )
    {
        String msg;
        if (parameterName.equals("caller") || parameterName.equals("callee")) {
            msg = "nnn-nnn-nnnn where n is a digit.";
        } else if (parameterName.equals("begin") || parameterName.equals("end") || parameterName.equals("Dates")) {
            msg = "MM/dd/yyyy hh:mm am, where am may re placed with pm\n" +
                    "and all the other numbers are digits for month, day, etc.";
            if (parameterName.equals("Dates")) {
                msg = msg + "\nBegin date must be after End Date as well.";
            }
        } else {
            msg = "Golly... I'm not sure what went wrong.";
        }

        return String.format("The required parameter \"%s\" is is incorrectly formed.\n" +
                "Proper formatting is: %s", parameterName, msg);
    }
    public static String addedPhoneCall(String name, PhoneCall newCall )
    {
        return String.format( "%s\nwas added to bill for: %s", newCall, name );
    }

    public static String allDictionaryEntriesDeleted() {
        return "All dictionary entries have been deleted";
    }

    public static String noPhoneBillFound(String customer) {
        return String.format("We could not locate any phone bill's by the\n" +
        "name %s.  Sorry.", customer);
    }

}
