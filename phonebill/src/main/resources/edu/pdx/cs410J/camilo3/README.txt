Project 1+: Designing a Phone Bill Application
Student: Camilo Schaser-Hughes
Date: Summer 2022
Class: Adv Prog in Java
Prof: David Whitlock

Description:
Program will create upload a phone bill and then add a phone
call to said phone bill as well as optionally upload and
save said phone bill.

Usage:
java -jar target/phonebill-2022.0.0.jar [options] <args>
args are (in this order)
customer:       Person whose phone bill we’re modeling
callerNumber:   Phone number of caller
calleeNumber:   Phone number of person who was called\n" +
begin:          Date and time call began (24-hour time)\n" +
end:            Date and time call ended (24-hour time)\n" +
options are (options may appear in any order):\n" +
-print:         Prints a description of the new phone call\n" +
-README         Prints a README for this project and exits\n" +
Date and time should be in the format: mm/dd/yyyy hh:mm");
