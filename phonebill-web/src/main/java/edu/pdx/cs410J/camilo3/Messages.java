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

    public static String addedPhoneCall(String name, PhoneCall newCall )
    {
        return String.format( "%s was added to \nbill for: %s", newCall, name );
    }

    public static String allDictionaryEntriesDeleted() {
        return "All dictionary entries have been deleted";
    }

}
