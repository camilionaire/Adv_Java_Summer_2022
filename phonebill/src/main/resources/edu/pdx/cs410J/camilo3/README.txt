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
calleeNumber:   Phone number of person who was called
begin:          Date and time call began (24-hour time)
end:            Date and time call ended (24-hour time)
options are (options may appear in any order):
-pretty file    Pretty print the phone bill to a text file
                or standard out (file -).
-textFile file  Where to read/write the phone bill
-print:         Prints a description of the new phone call
-README         Prints a README for this project and exits
Date and time should be in the format: mm/dd/yyyy hh:mm
