Project 4: Designing a Phone-Bill-Web Application
Student: Camilo Schaser-Hughes
Date: Summer 2022
Class: Adv Prog in Java
Prof: David Whitlock

Description:
Program will contact a servelet with some information, either
a get or a put request and, from the command line, will be able
to add a phonebill, look up a phone bill or phone calls.

Usage:
java -jar target/phonebill-2022.0.0.jar [options] <args>
args are (in this order):
customer        Person whose phone bill we’re modeling
callerNumber    Phone number of caller
calleeNumber    Phone number of person who was called
begin           Date and time call began
end             Date and time call ended
Date and time should be in the format: mm/dd/yyyy hh:mm

options are (options may appear in any order):
-host hostname  Host computer on which the server runs
-port port      Port on which the server is listening
-search         Phone calls should be searched for
-print          Prints a description of the new phone call
-README         Prints a README for this project and exits