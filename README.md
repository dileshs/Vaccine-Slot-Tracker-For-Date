# Vaccine-Slot-Tracker-For-Date

Its a Spring-Boot Java based project created to help people Automatically track the available vaccine slots for the Date Passed as 1st Runtime Arguement (DD-MM-YYYY).


Note : Tested on Windows 10  64 bit.

#

Description-

When the project starts it would trigger the browser process and be ready with a default page of google.com

It constantly searches for availability of vaccine slots for the given requirement of Age, Vaccine-type , Fees , pin-codes , Dose types - and when found
1. Plays configured Alarm sound on your system.
2. Opens the Cowin website to login immediately
3. If both 18+ and 45+ slots are found then it would open 2 browser tabs assuming the logins would be different for each age group.
4. A status page would open in browser showing a tabular representation of the Slots Data found for reference while logging in.
5. Sends an alert email to the configured email-id in application.properties 

#

Steps 

1. Clone the project in your system & configure as Maven & update maven project.
2. application.properties file present in the src/main/resources/  needs to be modified as per requirement for customized tracking.
3. Follow the comments in the application.properties file to correctly configure it before running the application.
4. Set the Date required as 1st Runtime Arguement in DD-MM-YYYY format.
5. After Date & application.properties is configured completely as per requirement, start the application and Relax.
6. Make sure your system sleep settings are set to Never And Volume of system to Maximum
7. If email alert feature is needed -configure correct email details in application.properties file Else keep them blank. 

#

# If any configuration is modified in application.properties then Restart the Application.

