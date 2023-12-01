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
class UserInterface {
	private Scanner sc;
	private int x;
	private String user_name_input;
	private String user_password_input;
	private String current_customer_input;
	private String copay_amount_display;
	private byte[] copay_amount;
	private byte[] notes;

	UserInterface(){
		this.sc = new Scanner(System.in);
	}
	public void setNotes(byte[] input) {
		this.notes = input;
	}
	public byte[] getNotes(){
		return notes;} //String newName = sc.nextLine();
	public void writeNotes(){
		System.out.println("Write a note telling why a amputee does not deserve a prosthetic leg. IF you write keywords SELECT, INSERT, UPDATE, DELETE, DROP, CREATE,ALTER, UNION, FROM, WHERE");
		String note = sc.nextLine();
		setNotes(note.getBytes());
		 }
	public void setCopayAmount(byte[] input) {
		this.copay_amount = input;
	}
	public byte[] getCopayAmount(){
		return copay_amount;}
	public void setCopayAmountDisplay(String input) {
		this.copay_amount_display = input;
	}
	public String getCopayAmountDisplay(){
		return this.copay_amount_display;}
	public void setX(int x) {
		this.x = x;}
	public int getX() {
		return x;}
	public void setCurrent_customer_input(String current_customer_input) {
		this.current_customer_input = current_customer_input;}
	public String getCurrent_customer_input() {
		return current_customer_input;}
	public void setUserNameInput(String input) {
		this.user_name_input = input;
	}
	public String getUserNameInput(){
		return this.user_name_input;}
	public void setUserPasswordInput(String input) {
		this.user_password_input = input;
	}
	public String getUserPasswordInput(){
		return this.user_password_input;}
	// A1-4 log in to profile
	// A3.2: Security Code. Genertate a code to

	// A1-4 log in to profile
	// A3.2: Security Code. Genertate a code to
	public void requestPatientNotes(){
		System.out.println("Type 1 and hit Enter to search for user info:all other options quits ");
		int choice = sc.nextInt();
		setX(choice);
		sc.nextLine(); // Consume the newline character
		if (choice == 1) {
			System.out.println("Enter the name of the patient you want to search(example enter bob):");
			String newName = sc.nextLine();
			setCurrent_customer_input(newName);
		} else {
			System.out.println("quiting");
		}
	}
	public void enterCopayAmount(){
		System.out.println("Enter Copay Amount ");
		String choice = sc.nextLine();
		//setCopayAmountDisplay(choice);
		setCopayAmount(choice.getBytes());

	}
	public void loginUser(){
		System.out.println("Enter the username: ");
		String user_name = sc.nextLine();
		setUserNameInput(user_name);
		System.out.println("The username you entered is: " + user_name);
		// FOR this problem no password/ encryption
		//System.out.println("Enter the password: ");
		//String password = sc.nextLine();
		//setUserPasswordInput(password);
		//System.out.println("The password you entered is: " + password);
	}

	//A8 change 1 part of profile
	public void decryptAll(Decryption decryption,CustomerNotes customerNotes) throws Exception {
		customerNotes.setName(decryption.decrypt(customerNotes.getName()));;
		customerNotes.setSSN(decryption.decrypt(customerNotes.getSSN()));
		customerNotes.setCopayAmount(decryption.decrypt(customerNotes.getCopayAmount()));
		customerNotes.setDoctorName(decryption.decrypt(customerNotes.getDoctorName()));
	}
	//A6 show user profile
	public void displayProfile(CustomerNotes profile) {
		System.out.println("Name: " + new String(profile.getName(), StandardCharsets.UTF_8));
		System.out.println("SSN: " + new String(profile.getSSN(), StandardCharsets.UTF_8));
		System.out.println("CopayAmount: " + new String(profile.getCopayAmount(), StandardCharsets.UTF_8));
		System.out.println("DoctorName: " + new String(profile.getDoctorName(), StandardCharsets.UTF_8));
	}
	public void closeScanner() {
		if (sc != null) {
			System.out.println("");
		}
	}
	//A12 Show confirm
	public void displayConfirmed(boolean  confirm) {
		if(Objects.equals(confirm, true)){
			System.out.print("\nConfirmed\n");
		}
	}
}
class CellPhone {
	private SecretKey user_secret_key;
	private Cipher user_cipher;
	private Key phone_key;
	private String message;
	CellPhone(){
		this.phone_key = new Key();
	}
	public void setMessage(String message){
		this.message = message ;}
	public String getMessage(){
		return message ;}
	public void setSecretKey(SecretKey sec_key){
		this.user_secret_key = sec_key ;}
	public SecretKey getSecretKey(){
		return user_secret_key ;}
	public void setUserCipher(Cipher sec_key){
		this.user_cipher = sec_key ;}
	public Cipher getUserCipher(){
		return user_cipher ;}
	public void setPhoneKey(){
		this.phone_key.setUserCipher(this.getUserCipher());
		this.phone_key.setSecretKey(this.getSecretKey());
	}
	public Key getPhoneKey() {
		return phone_key;
	}
	// A3.3: Security Code
	public void getCode(byte [] num) throws Exception {
		Decryption decryption = new Decryption();
		decryption.setKey( this.getPhoneKey());
		byte[] str1 = decryption.decrypt(num);
		String number = new String(str1);
		System.out.println("\nNumber is "  +number+ ".");
	}
	public void printMessage(){
		System.out.println("\nmesage is " +getMessage()+".");

	}
}// end of class
class Encryption {
	private Key key;
	Encryption(){
		this.key = new Key();
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Key getKey() {
		return key;
	}
	// Encrypt data
	byte[] encryption(byte[] s) throws Exception {
		this.key.getUserCipher().init(Cipher.ENCRYPT_MODE, this.key.getSecretKey());
		//c.init(Cipher.ENCRYPT_MODE, sk);
		return  this.key.getUserCipher().doFinal(s);
	}
}// end of class


/*
«security»

 */
class Key {
	private SecretKey user_secret_key;
	private Cipher user_cipher;
	Key(){
	}
	public void setSecretKey(SecretKey sec_key){
		this.user_secret_key = sec_key ;}
	public SecretKey getSecretKey(){
		return user_secret_key ;}
	public void setUserCipher(Cipher sec_key){
		this.user_cipher = sec_key ;}
	public Cipher getUserCipher(){
		return user_cipher ;}
}// end of class
/*
«security» «algorithm»

 */
class Decryption {
	private Key key;
	Decryption(){
		this.key = new Key();
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Key getKey() {
		return key;
	}
	byte[] decrypt(byte[] s) throws Exception {
		this.key.getUserCipher().init(Cipher.DECRYPT_MODE,  this.key.getSecretKey());
		return key.getUserCipher().doFinal(s);
	}
}// end of class

class CustomerNotes {
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
	CustomerNotes() {
	}
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
	// A3.7 [authorized]: Decrypt the Profile
	public String byteToString(byte[] inp){
		return new String(inp, StandardCharsets.UTF_8);
	}
	public void decryptAll(Decryption decryption) throws Exception {
		setName(decryption.decrypt(getName()));;
		setSSN(decryption.decrypt(getSSN()));
		setCopayAmount(decryption.decrypt(getCopayAmount()));
		setDoctorName(decryption.decrypt(getDoctorName()));
	}
	// A9.1: Encrypts the Profile
	public void encryptAll(Encryption encryption) throws Exception {
		setName(encryption.encryption(getName()));;
		setSSN(encryption.encryption(getSSN()));
		setCopayAmount(encryption.encryption(getCopayAmount()));
		setDoctorName(encryption.encryption(getDoctorName()));
	}

	// A9: Profile Input changes the profile
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
class CustomerNotesArrays {
	// Declaring an array of student
	CustomerNotes[] customer_arr;
	int current_prof_num;
	// Allocating memory for 3 objects
	CustomerNotesArrays() throws Exception {
		customer_arr = new CustomerNotes[5];
		customer_arr[0] = new CustomerNotes();
		customer_arr[0].setName( ("Susan".getBytes(StandardCharsets.UTF_8)));
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
	public void setCurrent_profNum(int x) {
		this.current_prof_num = x;}
	public int getCurrent_profNum() {
		return current_prof_num;}
	// Search the Profile
	public CustomerNotes searchProfiles(String searchName) throws Exception {
		CustomerNotes search_profile = null;
		int x = 0;
		for (CustomerNotes profile : customer_arr) {
			if (new String((profile.getName()), StandardCharsets.UTF_8).equalsIgnoreCase(searchName)) {
				search_profile = profile;
				setCurrent_profNum(x);
				return search_profile;
			}
			x+=1;
		}
		return search_profile;
	}
	//Change the profile in the Array
	public void setProfile(CustomerNotes profile) throws Exception {
		int y = getCurrent_profNum();
		profile.setConfirmed(true);
		// only need to change
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


public class ApproveCopayment {
	public  void run_program() throws Exception {
		System.out.print("Hello and welcome to secure copayment!");
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		SecretKey key = keygen.generateKey();
		Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		Key user_stored_key = new Key();
		System.out.print("User key"+user_stored_key+"\n");
		user_stored_key.setSecretKey(key);
		user_stored_key.setUserCipher(aesCipher);
		CellPhone phone = new CellPhone();
		phone.setSecretKey(key);
		phone.setUserCipher(aesCipher);
		phone.setPhoneKey();
		Encryption encryption = new Encryption();
		encryption.setKey(user_stored_key);
		Decryption decryption = new Decryption();
		decryption.setKey(user_stored_key);
		CustomerNotesArrays customerNotesArrays = new CustomerNotesArrays();
		// Susan for
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


		// w
		userInterface.requestPatientNotes();
		if(userInterface.getX() != 1){
			System.out.println("\nQuit from copayment"  + ".");
			System.exit(1);
		}
		String in = userInterface.getCurrent_customer_input();
		// A3: Request Profile
		CustomerNotes foundProfile = customerNotesArrays.searchProfiles(in);
		if(foundProfile == null){
			System.out.println("\nNo profile found "  + ".");
			System.exit(1);
		}

		// validate
		// AAAAAAA

		boolean allowed = validateNotes.validate(foundProfile);
		if (!allowed){
			System.out.println("\nError in validation "  + ".");
			System.exit(1);
		}
		//
		foundProfile.encryptAll(encryption);
		userInterface.decryptAll(decryption,foundProfile);
		userInterface.displayProfile(foundProfile);
		userInterface.enterCopayAmount();
		foundProfile.setCopayAmount((userInterface.getCopayAmount()));
		boolean allowed2 = validateNotes.validate(foundProfile);
		if (!allowed2){
			System.out.println("\nError in validation "  + ".");
			System.exit(1);
		}

		foundProfile.changeProfile(3, userInterface.getCopayAmount());
		// can get rid of this ,maybe
		userInterface.displayProfile(foundProfile);
		foundProfile.encryptAll(encryption);
		customerNotesArrays.setProfile(foundProfile);
		//foundProfile.setConfirmed(true);
		userInterface.displayConfirmed(foundProfile.getConfirmed());
		String message_bool = String.valueOf(foundProfile.getConfirmed());
		phone.setMessage(message_bool);
		phone.printMessage();
		// can get rid of this ,maybe
		userInterface.displayProfile(foundProfile);
		userInterface.closeScanner();
		System.out.print("Done with program");
	}
}
