Password Manager Program:

TO AVOID ANY INITIAL POTENTIAL ISSUES: Clear your json file before running the program for the first time, you will only need to do this
one time.

What it does: Allows user to create account information, given a valid url, and save it in encrypted
form to a local json file. Additionally, if a user wishes to retrieve account information, it can do so
with the search bar.

How to use:

*Make sure you have the Jar files downloaded or added to the project in 'Required Jar Files'

1. Run from IDE. BE SURE to view json file from the home directory of the project
and not the dist folder.
2. Run from command prompt using executable jar. Simple set directory using 'cd' followed
by the project folder path 'dist'. Then just type 'java -jar PasswordManager.jar' to run.
BE SURE to view json file in the dist and not the home directory of the project.

To create a new account, you must:
 - Enter a valid url
 - Enter a username of your choice
 - Enter a password of your choice OR click the 'Generate Secure Password" button
     - Note: If you choose to generate a password, it will not be filled on screen rather
	     automatically stored if you hit 'OK'. If you hit cancel, it will allow you to
	     edit any changes to the url or username that you'd like. There is no need to re-
             generate again if you choose to go back and edit.
      - You will be prompted for the authentification password before adding an accouunt, simply 
	type the correct password in the continue. If you do not know the password, contact me.

To retrieve account information, you must:
 - Use the search bar to type in url that already exists
 - When typing in the bar, you may:
   - Click the url in the drop down ONCE to auto fill the search bar
   - Click the url in the drop down TWICE to retrieve details
 - If you choose to only hit the url ONCE, you may:
    - Hit enter on your keyboard to pop up the credentials form
    - Hit the enter button to pop up the credentials form
 - You will be prompted for the authentification password before the credentials tab loads, simply 
   type the correct password in the continue. If you do not know the password, contact me.
 - Once the credentials form is displayed, BE SURE no one is looking
   at your screen. Once you're sure, hit display credentials to view your
   account info in decrypted form!