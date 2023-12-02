/*
Title : ApproveCopayment.java
Software Security
Description : This is a Java File for ApproveCopayment use case.
Author : David Vaughan (R#11666390)
Date : 12/2/2023
Version : 1.0
Usage : run
Notes :
Java Version:
=============================================================================
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import javax.crypto.Cipher;
// import java.util.Scanner;
import java.nio.charset.StandardCharsets;

import java.security.SecureRandom;
import javax.crypto.SecretKey;



/**
 * Class to run the main secure copayment program.
 */
class Run_main_sec {
    /**
     * Method to execute the main program logic.
     * @throws Exception if an error occurs during execution.
     */
    public void run_program() throws Exception {
        // Welcome message and key initialization
        System.out.print("Hello and welcome to secure copayment!");
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey key = keygen.generateKey();
        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        // Setting up user's stored key and phone
        Key user_stored_key = new Key();
        System.out.print("User key" + user_stored_key + "\n");
        user_stored_key.setSecretKey(key);
        user_stored_key.setUserCipher(aesCipher);
        CellPhone phone = new CellPhone();
        phone.setSecretKey(key);
        phone.setUserCipher(aesCipher);
        phone.setPhoneKey();
        // Setting up encryption and decryption components
        Encryption encryption = new Encryption();
        encryption.setKey(user_stored_key);
        Decryption decryption = new Decryption();
        decryption.setKey(user_stored_key);
        // Setting up customer notes and user interface
        CustomerNotesArrays customerNotesArrays = new CustomerNotesArrays();
        UserInterface userInterface = new UserInterface();
        ValidateNotes validateNotes = new ValidateNotes();
        /*
        userInterface.writeNotes();
        String note = new String(userInterface.getNotes(), StandardCharsets.UTF_8);
        boolean valid_note = validateNotes.validateString(note);
        if (!valid_note){
            System.out.println("\nError in validation "  + ".");
            System.exit(1);
        }
         */
        // Commented out code block for writing and validating user notes
        // Requesting patient notes
        userInterface.requestPatientNotes();
        if (userInterface.getX() != 1) {
            System.out.println("\nQuit from copayment" + ".");
            System.exit(1);
        }
        // Retrieving and validating customer profile
        String in = userInterface.getCurrent_customer_input();
        CustomerNotes foundProfile = customerNotesArrays.searchProfiles(in);
        if (foundProfile == null) {
            System.out.println("\nNo profile found" + ".");
            System.exit(1);
        }
        boolean allowed = validateNotes.validate(foundProfile);
        if (!allowed) {
            System.out.println("\nError in validation" + ".");
            System.exit(1);
        }
        // Encrypting and decrypting notes
        foundProfile.encryptAll(encryption);
        userInterface.decryptAll(decryption, foundProfile);
        userInterface.displayProfile(foundProfile);
        // Entering copay amount and validating
        userInterface.enterCopayAmount();
        foundProfile.setCopayAmount((userInterface.getCopayAmount()));
        boolean allowed2 = validateNotes.validate(foundProfile);
        if (!allowed2) {
            System.out.println("\nError in validation" + ".");
            System.exit(1);
        }
        // Changing profile and displaying
        foundProfile.changeProfile(3, userInterface.getCopayAmount());
        userInterface.displayProfile(foundProfile);
        foundProfile.encryptAll(encryption);
        customerNotesArrays.setProfile(foundProfile);
        // Displaying confirmation and sending message
        userInterface.displayConfirmed(foundProfile.getConfirmed());
        String message_bool = String.valueOf(foundProfile.getConfirmed());
        phone.setMessage(message_bool);
        phone.printMessage();
        userInterface.displayProfile(foundProfile);
        // Closing scanner and completing the program
        userInterface.closeScanner();
        System.out.print("Done with program");
    }//end of function
}//end of class



/**
 * Class to handle user interface interactions.
 */
class UserInterface {
    private Scanner sc;
    private int x;
    private String user_name_input;
    private String user_password_input;
    private String current_customer_input;
    private String copay_amount_display;
    private byte[] copay_amount;
    private byte[] notes;
    /**
     * Constructor to initialize the scanner.
     */
    UserInterface() {
        this.sc = new Scanner(System.in);}
    // Setters and getters for notes
    public void setNotes(byte[] input) {
        this.notes = input;}
    public byte[] getNotes(){
        return notes;}
    /**
     * Method to write a string note, not used since Note class is different
     */
    public void writeNotes() {
        System.out.println("Write a note..."); // Instructions to write a note
        String note = sc.nextLine();
        setNotes(note.getBytes());
    }
    // Setters and getters for copay amount
    public void setCopayAmount(byte[] input) {
        this.copay_amount = input;}
    public byte[] getCopayAmount(){
        return copay_amount;}
    // Setters and getters for copay amount display
    public void setCopayAmountDisplay(String input) {
        this.copay_amount_display = input;}
    public String getCopayAmountDisplay(){
        return this.copay_amount_display;}
    // Setters and getters for user choice
    public void setX(int x) {
        this.x = x;}
    public int getX() {
        return x;}
    // Setters and getters for current customer input
    public void setCurrent_customer_input(String current_customer_input) {
        this.current_customer_input = current_customer_input;}
    public String getCurrent_customer_input() {
        return current_customer_input;}
    // Setters and getters for username input
    public void setUserNameInput(String input) {
        this.user_name_input = input;}
    public String getUserNameInput(){
        return this.user_name_input;}
    // Setters and getters for user password input
    public void setUserPasswordInput(String input) {
        this.user_password_input = input;}
    public String getUserPasswordInput(){
        return this.user_password_input;}
    /**
     * Method to request patient notes and set current customer input.
     */
    public void requestPatientNotes(){
        System.out.println("Type 1 to search for user info, any other option quits:");
        int choice = sc.nextInt();
        setX(choice);
        sc.nextLine(); // Consume the newline character
        if (choice == 1) {
            System.out.println("Enter the name of the patient you want to search(example bob ):");
            String newName = sc.nextLine();
            setCurrent_customer_input(newName);
        } else {
            System.out.println("Quitting");
        }
    }

    /**
     * Method to enter copay amount.
     */
    public void enterCopayAmount(){
        System.out.println("Enter Copay Amount(must be a int or error will occur):");
        String choice = sc.nextLine();
        setCopayAmount(choice.getBytes());
    }

    /**
     * Method to login user by entering username. not used
     */
    public void loginUser(){
        System.out.println("Enter the username: ");
        String user_name = sc.nextLine();
        setUserNameInput(user_name);
        System.out.println("The username you entered is: " + user_name);
    }

    /**
     * Method to decrypt all customer notes.
     */
    public void decryptAll(Decryption decryption, CustomerNotes customerNotes) throws Exception {
        customerNotes.setName(decryption.decrypt(customerNotes.getName()));
        customerNotes.setSSN(decryption.decrypt(customerNotes.getSSN()));
        customerNotes.setCopayAmount(decryption.decrypt(customerNotes.getCopayAmount()));
        customerNotes.setDoctorName(decryption.decrypt(customerNotes.getDoctorName()));
    }

    /**
     * Method to display user profile.
     */
    public void displayProfile(CustomerNotes profile) {
        System.out.println("Name: " + new String(profile.getName(), StandardCharsets.UTF_8));
        System.out.println("SSN: " + new String(profile.getSSN(), StandardCharsets.UTF_8));
        System.out.println("CopayAmount: " + new String(profile.getCopayAmount(), StandardCharsets.UTF_8));
        System.out.println("DoctorName: " + new String(profile.getDoctorName(), StandardCharsets.UTF_8));
    }

    /**
     * Method to close the scanner.
     */
    public void closeScanner() {
        if (sc != null) {
            sc.close();
        }
    }

    /**
     * Method to display confirmation.
     */
    public void displayConfirmed(boolean confirm) {
        if(Objects.equals(confirm, true)){
            System.out.print("\nConfirmed\n");
        }
    }
}

/**
 * Class representing a cell phone with encryption capabilities.
 */
class CellPhone {
    private SecretKey user_secret_key;
    private Cipher user_cipher;
    private Key phone_key;
    private String message;

    /**
     * Constructor to initialize the cell phone with a key.
     */
    CellPhone(){
        this.phone_key = new Key();
    }

    // Setters and getters for message
    public void setMessage(String message){
        this.message = message ;
    }
    public String getMessage(){
        return message ;
    }

    // Setters and getters for secret key
    public void setSecretKey(SecretKey sec_key){
        this.user_secret_key = sec_key ;
    }
    public SecretKey getSecretKey(){
        return user_secret_key ;
    }

    // Setters and getters for user cipher
    public void setUserCipher(Cipher sec_key){
        this.user_cipher = sec_key ;
    }
    public Cipher getUserCipher(){
        return user_cipher ;
    }

    // Method to set phone key based on user cipher and secret key
    public void setPhoneKey(){
        this.phone_key.setUserCipher(this.getUserCipher());
        this.phone_key.setSecretKey(this.getSecretKey());
    }

    // Getter for phone key
    public Key getPhoneKey() {
        return phone_key;
    }

    /**
     * Method to decrypt a security code using the phone's key.
     * @param num The security code to decrypt.
     * @throws Exception if an error occurs during decryption.
     */
    public void getCode(byte [] num) throws Exception {
        Decryption decryption = new Decryption();
        decryption.setKey(this.getPhoneKey());
        byte[] str1 = decryption.decrypt(num);
        String number = new String(str1);
        System.out.println("\nNumber is "  +number+ ".");
    }

    /**
     * Method to print the stored message.
     */
    public void printMessage(){
        System.out.println("\nMessage is " + getMessage() + ".");
    }
} // end of class

/**
 * Class responsible for encryption operations.
 */
class Encryption {
    private Key key;
    /**
     * Constructor to initialize the Encryption class with a key.
     */
    Encryption(){
        this.key = new Key();}
    /**
     * Setter method to set the encryption key.
     * @param key The key to be set.
     */
    public void setKey(Key key) {
        this.key = key;}
    /**
     * Getter method to retrieve the encryption key.
     * @return The encryption key.
     */
    public Key getKey() {
        return key;}
    /**
     * Method to perform encryption on the provided data using the stored key.
     * @param s The data to be encrypted.
     * @return The encrypted data.
     * @throws Exception if an error occurs during encryption.
     */
    byte[] encryption(byte[] s) throws Exception {
        // Initializing the cipher with the encryption mode and the stored secret key
        this.key.getUserCipher().init(Cipher.ENCRYPT_MODE, this.key.getSecretKey());
        // Performing the encryption on the provided data
        return  this.key.getUserCipher().doFinal(s);
    }
} // end of class


/*
«security»
 */
/**
 * Class representing a cryptographic key.
 */
class Key {
    private SecretKey user_secret_key;
    private Cipher user_cipher;
    /**
     * Default constructor for the Key class.
     */
    Key() {
    }
    /**
     * Setter method to set the secret key.
     * @param sec_key The secret key to be set.
     */
    public void setSecretKey(SecretKey sec_key) {
        this.user_secret_key = sec_key;}
    /**
     * Getter method to retrieve the secret key.
     * @return The secret key.
     */
    public SecretKey getSecretKey() {
        return user_secret_key;}
    /**
     * Setter method to set the user cipher.
     * @param sec_key The user cipher to be set.
     */
    public void setUserCipher(Cipher sec_key) {
        this.user_cipher = sec_key;}
    /**
     * Getter method to retrieve the user cipher.
     * @return The user cipher.
     */
    public Cipher getUserCipher() {
        return user_cipher;}
} // end of class
/*
«security» «algorithm»
 */
/**
 * Class responsible for decryption operations.
 */
class Decryption {
    private Key key;
    /**
     * Constructor to initialize the Decryption class with a key.
     */
    Decryption(){
        this.key = new Key();
    }
    /**
     * Setter method to set the decryption key.
     * @param key The key to be set.
     */
    public void setKey(Key key) {
        this.key = key;}
    /**
     * Getter method to retrieve the decryption key.
     * @return The decryption key.
     */
    public Key getKey() {
        return key;}
    /**
     * Method to perform decryption on the provided data using the stored key.
     * @param s The data to be decrypted.
     * @return The decrypted data.
     * @throws Exception if an error occurs during decryption.
     */
    byte[] decrypt(byte[] s) throws Exception {
        // Initializing the cipher with the decryption mode and the stored secret key
        this.key.getUserCipher().init(Cipher.DECRYPT_MODE,  this.key.getSecretKey());
        // Performing the decryption on the provided data
        return key.getUserCipher().doFinal(s);
    }
} // end of class


/**
 * Class representing customer notes with various attributes and operations.
 */
class CustomerNotes {
    // Attributes
    private boolean authorized;
    private byte[] name;
    private byte[] SSN;
    private byte[] copay_amount;
    private byte[] doctor_name;
    private String display_name;
    private String display_SSN;
    private String display_copay_amount;
    private String display_doctor_name;
    private boolean confirmed;

    /**
     * Default constructor for CustomerNotes class.
     */
    CustomerNotes() {}
    // Getter and setter methods for display attributes
    public String getDisplayName() {
        return display_name;}
    public void setDisplayName(String display_name) {
        this.display_name = display_name;}
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;}
    public boolean getConfirmed() {
        return confirmed;}
    public String getDisplaySSN() {
        return display_SSN;}
    public void setDisplaySSN(String display_SSN) {
        this.display_SSN = display_SSN;}
    public String getDisplayCopayAmount() {
        return display_copay_amount;}
    public void setDisplayCopayAmount(String display_copay_amount) {
        this.display_copay_amount = display_copay_amount;}
    public String getDisplayDoctorName() {
        return display_doctor_name;}
    public void setDisplayDoctorName(String display_doctor_name) {
        this.display_doctor_name = display_doctor_name;}
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;}
    public Boolean getAuthorized() {
        return this.authorized;}
    public void setName(byte[] input) {
        this.name = input;}
    public byte[] getName() {
        return this.name;}
    public void setSSN(byte[] input) {
        this.SSN = input;}
    public byte[] getSSN() {
        return this.SSN;}
    public void setCopayAmount(byte[] input) {
        this.copay_amount = input;}
    public byte[] getCopayAmount() {
        return this.copay_amount;}
    public void setDoctorName(byte[] input) {
        this.doctor_name = input;}
    public byte[] getDoctorName() {
        return this.doctor_name;}
    /**
     * Converts byte array to a string using UTF-8 encoding.
     * @param inp The input byte array.
     * @return The resulting string.
     */
    public String byteToString(byte[] inp) {
        return new String(inp, StandardCharsets.UTF_8);
    }
    /**
     * Decrypts all encrypted attributes using the provided Decryption object.
     * @param decryption The Decryption object to use for decryption.
     * @throws Exception if an error occurs during decryption.
     */
    public void decryptAll(Decryption decryption) throws Exception {
        setName(decryption.decrypt(getName()));
        setSSN(decryption.decrypt(getSSN()));
        setCopayAmount(decryption.decrypt(getCopayAmount()));
        setDoctorName(decryption.decrypt(getDoctorName()));
    }

    /**
     * Encrypts all attributes using the provided Encryption object.
     * @param encryption The Encryption object to use for encryption.
     * @throws Exception if an error occurs during encryption.
     */
    public void encryptAll(Encryption encryption) throws Exception {
        setName(encryption.encryption(getName()));
        setSSN(encryption.encryption(getSSN()));
        setCopayAmount(encryption.encryption(getCopayAmount()));
        setDoctorName(encryption.encryption(getDoctorName()));
    }
    /**
     * Generates and returns an authorization code using the provided AuthorizationCodeChecker object.
     * @param code_generator The AuthorizationCodeChecker object to generate the code.
     * @return The generated authorization code.
     */
    public String authorizeCode(AuthorizationCodeChecker code_generator) {
        code_generator.generateNumber();
        return code_generator.getNumber();
    }
    /**
     * Changes the profile based on the provided choice and data.
     * @param choice The choice indicating which attribute to change.
     * @param data The new data to set.
     * @throws Exception if an error occurs during the profile change.
     */
    public void changeProfile(int choice, byte[] data) throws Exception {
        switch (choice) {
            case 1:
                this.setName(data);
                break;
            case 2:
                this.setSSN(data);
                break;
            case 3:
                this.setCopayAmount(data);
                break;
            case 4:
                this.setDoctorName(data);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
        setConfirmed(true);
    }
}
/**
 * Class representing an array of customer notes and operations on the array.
 */
class CustomerNotesArrays {
    // Array to store customer profiles
    CustomerNotes[] customer_arr;
    // Index to track the current profile number
    int current_prof_num;
    /**
     * Constructor to initialize the CustomerNotesArrays class with sample data.
     * @throws Exception if an error occurs during initialization.
     */
    CustomerNotesArrays() throws Exception {
        customer_arr = new CustomerNotes[5];
        // Initializing sample profiles
        customer_arr[0] = new CustomerNotes();
        customer_arr[0].setName(("Susan".getBytes(StandardCharsets.UTF_8)));
        customer_arr[0].setSSN(("123456789".getBytes(StandardCharsets.UTF_8)));
        customer_arr[0].setCopayAmount(("23".getBytes(StandardCharsets.UTF_8)));
        customer_arr[0].setDoctorName(("INSERT Terry".getBytes(StandardCharsets.UTF_8)));

        customer_arr[1] = new CustomerNotes();
        customer_arr[1].setName(("Alex".getBytes(StandardCharsets.UTF_8)));
        customer_arr[1].setSSN(("223456789".getBytes(StandardCharsets.UTF_8)));
        customer_arr[1].setCopayAmount(("0".getBytes(StandardCharsets.UTF_8)));
        customer_arr[1].setDoctorName(("Terry".getBytes(StandardCharsets.UTF_8)));

        customer_arr[2] = new CustomerNotes();
        customer_arr[2].setName(("Bob".getBytes(StandardCharsets.UTF_8)));
        customer_arr[2].setSSN(("323456789".getBytes(StandardCharsets.UTF_8)));
        customer_arr[2].setCopayAmount(("532".getBytes(StandardCharsets.UTF_8)));
        customer_arr[2].setDoctorName(("Dave".getBytes(StandardCharsets.UTF_8)));
    }

    // Setter and getter methods for current profile number
    public void setCurrent_profNum(int x) {
        this.current_prof_num = x;}
    public int getCurrent_profNum() {
        return current_prof_num;}
    /**
     * Method to search for a profile in the array based on the name.
     * @param searchName The name to search for.
     * @return The found customer profile or null if not found.
     * @throws Exception if an error occurs during the search.
     */
    public CustomerNotes searchProfiles(String searchName) throws Exception {
        CustomerNotes search_profile = null;
        int x = 0;
        for (CustomerNotes profile : customer_arr) {
            if (new String((profile.getName()), StandardCharsets.UTF_8).equalsIgnoreCase(searchName)) {
                search_profile = profile;
                setCurrent_profNum(x);
                return search_profile;
            }
            x += 1;
        }
        return search_profile;
    }

    /**
     * Method to update a profile in the array.
     * @param profile The profile to update.
     * @throws Exception if an error occurs during the update.
     */
    public void setProfile(CustomerNotes profile) throws Exception {
        int y = getCurrent_profNum();
        profile.setConfirmed(true);
        // Updating the profile in the array
        customer_arr[y].setName(profile.getName());
        customer_arr[y].setSSN(profile.getSSN());
        customer_arr[y].setCopayAmount(profile.getCopayAmount());
        customer_arr[y].setDoctorName(profile.getDoctorName());
    }
}


class ValidateNotes{

    public  Boolean validateString(String note) {
        if (isValidFormatString(note) && !containsSQLInjectionString(note)
                && !containsXSS_String(note) && !isCSRFAttackString(note)) {
            System.out.println("Patient notes are valid.");
            return true;
            // Additional processing if needed
        } else {
            System.out.println("Invalid patient notes. Please check your input.");
            return false;
            // Handle the error or reject the request
        }
    }
    private  boolean isValidFormatString(String customerNote) {
        return !isEmpty(customerNote);
    }
    private  boolean containsSQLInjectionString(String customerNote) {
        String[] sqlKeywords = {"SELECT", "INSERT", "UPDATE", "DELETE", "DROP", "CREATE", "ALTER", "UNION", "FROM", "WHERE"};
        for (String keyword : sqlKeywords) {
            // has to be better way
            if (containsCaseInsensitive(customerNote, keyword)) {
                return true;
            }
        }
        return false;
    }
    private  boolean containsCaseInsensitiveString(String haystack, String needle) {
        return haystack.toLowerCase().contains(needle.toLowerCase());
    }
    // XSS (Cross-Site Scripting)
    private  boolean containsXSS_String(String customerNote) {
        String[] xssPatterns = {"<script>", "javascript:", "onload=", "alert("};

        for (String pattern : xssPatterns) {
            if (containsCaseInsensitive(customerNote, pattern)) {
                return true;
            }

        }
        return false;
    }
    //Cross-Site Request Forgery
    private  boolean isCSRFAttackString(String customerNote) {
        // Placeholder, maybe implement ?
        return false;
    }
    ////
    //
    public  Boolean validate(CustomerNotes customerNote) {
        if (isValidFormat(customerNote) && !containsSQLInjection(customerNote)
                && !containsXSS(customerNote) && !isCSRFAttack(customerNote)) {
            System.out.println("Patient notes are valid.");
            return true;
            // Additional processing if needed
        } else {
            System.out.println("Invalid patient notes. Please check your input.");
            return false;
            // Handle the error or reject the request
        }
    }
    private  boolean isValidFormat(CustomerNotes customerNote) {
        return !isEmpty(customerNote.byteToString(customerNote.getName()))
                && isValidSSNFormat(customerNote.byteToString(customerNote.getSSN()))
                && isValidCopayAmountFormat(customerNote.byteToString(customerNote.getCopayAmount()))
                && isValidDoctorFormat(customerNote.byteToString(customerNote.getDoctorName()));
    }
    private  boolean isValidSSNFormat(String ssn) {
        // if ssn not 9 False and not null
        return ssn != null && ssn.length() == 9;
    }
    private  boolean isValidCopayAmountFormat(String copayAmount) {
        if(copayAmount == null){
            return false;
        }
        int foo = Integer.parseInt(copayAmount);
        // if foo < 0 return false
        return foo >= 0;
    }
    private  boolean isValidDoctorFormat(String doctor) {
        // Add copay amount format validation logic here
        // Example: Check if it's a valid numeric value
        return doctor != null;
    }
    private  boolean containsSQLInjection(CustomerNotes customerNote) {
        String[] sqlKeywords = {"SELECT", "INSERT", "UPDATE", "DELETE", "DROP", "CREATE", "ALTER", "UNION", "FROM", "WHERE"};
        for (String keyword : sqlKeywords) {
            // has to be better way
            if (containsCaseInsensitive(customerNote.byteToString(customerNote.getName()), keyword)) {
                return true;
            }
            else if (containsCaseInsensitive(customerNote.byteToString(customerNote.getSSN()), keyword)) {
                return true;
            }
            else if (containsCaseInsensitive(customerNote.byteToString(customerNote.getCopayAmount()), keyword)) {
                return true;
            }
            else if (containsCaseInsensitive(customerNote.byteToString(customerNote.getDoctorName()), keyword)) {
                return true;
            }
        }
        return false;
    }
    private  boolean containsCaseInsensitive(String haystack, String needle) {
        return haystack.toLowerCase().contains(needle.toLowerCase());
    }
    // XSS (Cross-Site Scripting)
    private  boolean containsXSS(CustomerNotes customerNote) {
        String[] xssPatterns = {"<script>", "javascript:", "onload=", "alert("};

        for (String pattern : xssPatterns) {
            if (containsCaseInsensitive(customerNote.byteToString(customerNote.getName()), pattern)) {
                return true;
            }
            else if (containsCaseInsensitive(customerNote.byteToString(customerNote.getSSN()), pattern)) {
                return true;
            }
            else if (containsCaseInsensitive(customerNote.byteToString(customerNote.getCopayAmount()), pattern)) {
                return true;
            }
            else if (containsCaseInsensitive(customerNote.byteToString(customerNote.getDoctorName()), pattern)) {
                return true;
            }
        }
        return false;
    }
    //Cross-Site Request Forgery
    private  boolean isCSRFAttack(CustomerNotes customerNote) {
        // Placeholder, maybe implement ?
        return false;
    }
    private  boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    ValidateNotes(){
    }
}//end of class



/**
 * NEW SECURITY USE CASE TO MITIGATE THREAT:
 * Security use case:  Validate patients’ treatment notes
 * Summary: The system validates the researcher/patient input
 * Actor: Researcher/Patient
 * Precondition: None
 * Main sequence:
 * 1. The system validates the format and range of the patient notes.
 * Alternative sequence:
 * • Step 1: The system fails to validate the user input form and displays the incorrect
 *   form along with an error message.
 * Postcondition: The system has validated the input for correct formatting, SQL injections,
 *   XSS, and CSRF, or has rejected the request due to an invalid input and returned an error message.
 */

/**
 * INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
 * Security Use Case Name: Securely Approve Copayment
 * Summary: The insurance company reviews patients’ treatment notes and approves the copay amount.
 * Actor: Insurance company, Patient
 * Precondition: The insurance company has logged into the system.
 * Main sequence:
 * 1. The insurance company requests the patients’ treatment notes that have not been Approved.
 * << Validate patients’ treatment notes >>
 * 2. The system displays the unapproved patients’ medical charts.
 * 3. The insurance company enters each patient’s copay amount.
 * 4. The system stores each patient’s copayment information (patient’s name, SSN, copay
 *    amount, and doctor’s name).
 * 5. The system displays a confirmation message if the copayment information has been
 *    stored successfully.
 * 6. The system sends each patient the insurance copayment approval message.
 * Alternative sequence:
 * Alternative sequence:
 * Step 5: The system displays an error message if it fails to store the copayment information.
 * Postcondition: The insurance company has approved the copay amount
 */
