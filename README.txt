Password Manager Program:

TO AVOID ANY INITIAL, POTENTIAL ISSUES: Clear your json file before running the program for the first time, you will only need to do this
one time.

What it does: Allows user to create account information, given a valid url, and save it in encrypted
form to a local json file. Additionally, if a user wishes to retrieve account information, it can do so
with the search bar.

How to use the two different approaches:

*Make sure you have the Jar files downloaded or added to the project in 'Required Jar Files'

1. Run from an IDE. Simply load it in and meet an requirments your IDE requires such as loading jars.
   BE SURE to view json file from the home directory of the project
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
	type whatever master password in and then continue. This will be the master password for that account,
	meaning you could have a unique master password per account. However, for simplicity you could of course
	use the same password, just be sure it is randomly secure and changed at least every 3 months. Changing your
        password would require you to write down all the account info, clear the JSON, and add everything back with the
        new master password.
To retrieve account information, you must:
 - Use the search bar to type in url that already exists
 - When typing in the bar, you may:
   - Click the url in the drop down ONCE to auto fill the search bar
   - Click the url in the drop down TWICE to retrieve details
 - If you choose to only hit the url ONCE, you may:
    - Hit enter on your keyboard to pop up the credentials form
    - Hit the enter button to pop up the credentials form
 - You will be prompted for the authentification password(the one you used for this account)
   before the credentials tab loads, simply type the correct password in the continue.
 - Once the credentials form is displayed, BE SURE no one is looking
   at your screen. Once you're sure, hit display credentials to view your
   account info in decrypted form!

NOTE: If you are a developer and wish to improve the performance of the encryption and decryption,
please find your way to the the Scrypt_Encrypt_Decrypt file. Here, you'll want to edit the parameters
for the scrypt (cost (iterations), block size, key size, and parallelization). Increasing parallelization
to what your CPU will handle may improve speed as long as no other bottlenecks exist. As for the others, you can
decrease them to improve speed, but it will be a trade-off in terms of security. Pick your poison!