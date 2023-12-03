/*
Title: SoftwareSecurity.java
Software Security
Description: This is a Java File for Software Security cs_4331 Project 1
Author:s
    David Vaughan davivaug@ttu.edu
    Landon Hassin Landon.Hassin@ttu.edu
    Quinten Bruce qbruce@ttu.edu
    Eric Ward war71624@ttu.edu
Date: 12/2/2023
Version: 1.0
Usage: run
Notes:
Java Version:
=============================================================================
*/

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

// Main class for the Software Security program
class SoftwareSecurity {
	// Main method
	public static void main(String[] args) throws Exception {
		// Initialize Scanner to take user input
		Scanner scanner = new Scanner(System.in);
		String action;

		// Main program loop
		do {
			// Display menu options
			System.out.println("What do you want to do?");
			System.out.println("	1. Treat Patient");
			System.out.println("	2. Fill Prescription");
			System.out.println("	3. Approve Copayment");
			System.out.println("	4. Pay Bill");
			System.out.println("	5. Exit");

			// Prompt user for choice
			System.out.print("Enter your choice: ");
			action = scanner.nextLine();

			// Switch statement to perform actions based on user input
			switch (action) {
				case "1":
					System.out.println("Performing 'Treat Patient'...");
					// Create and run TreatPatient object
					TreatPatient tp = new TreatPatient();
					tp.run_program();
					break;
				case "2":
					System.out.println("Performing 'Fill Prescription'...");
					// Create and run FillPrescription object
					FillPrescription fp = new FillPrescription();
					fp.run_program();
					break;
				case "3":
					System.out.println("Performing 'Approve Copayment'...");
					// Create and run ApproveCopayment object
					ApproveCopayment co = new ApproveCopayment();
					co.run_program();
					break;
				case "4":
					System.out.println("Performing 'Pay Bill'...");
					// Create and run PayBill object
					PayBill pb = new PayBill();
					pb.run_program();
					break;
				case "5":
					System.out.println("Exiting system.");
					break;
				default:
					System.out.println("Please enter a number between 1 and 5.");
					break;
			}

		} while (!action.equals("5")); // Continue loop until the user chooses to exit
	}
}
/*

Group 3
Stage 3: Project Report
David Vaughan davivaug@ttu.edu
    Landon Hassin Landon.Hassin@ttu.edu
    Quinten Bruce qbruce@ttu.edu
    Eric Ward war71624@ttu.edu

CS4331: Software Security Project Fall 2023

THREAT IDENTIFICATION
Use case name: Login
Threat
Security
Assets
Description
Vulnerability
Spoofing
User Login
The users login can be stolen and used to login
Improper access to user account

MITIGATION USE CASE DIAGRAM:

NEW SECURITY USE CASE TO MITIGATE THREAT:
Input Validation
Security Use Case: Two-Step Validation
Summary: The system has a two-step validation by sending a code to the users email
Actor: Patient, Doctor, Pharmacist, Insurance company, and Researcher
Precondition: None.
Main sequence:
1. The system sends a code to users email
2. The system confirms code validity.
Alternative sequence:
• Step 2: System terminated if code is invalid.
Postcondition: The system has successfully validated the actors login
INTEGRATED USE CASE WITH SECURITY USE CASE:
Secure Login.
Security Use Case Name: Secure Login
Summary: The user (patient, doctor, pharmacist, insurance company, and researcher) logs into
the system.
Actor: Patient, Doctor, Pharmacist, Insurance company, and Researcher
Precondition: The user has created an account in the healthcare system..
Main sequence:
1. The user inputs the ID and password.
2. The system checks if the patient ID and password are valid.
<<Two-Step validation>>
3. The system displays a welcome message if the ID and password are valid.
4. System started 15 minute timer
5. After 15 minutes, the user enters login information again.
6. System continues where the user was previously.
Alternatives:
Step 3: The system terminates if the customer ID does not exist.
Step 3: If the customer ID is correct and the password is incorrect, the system prompts the customer to enter the correct password. The system terminates if the customer does not provide a correct password three times.
Step 6: The system terminates if the customer ID does not exist.
Step 6: If the customer ID is correct and the password is incorrect, the system prompts the customer to enter the correct password. The system terminates if the customer does not provide a correct password three times.
Postcondition: The user has logged into the system




—----------------------------------------------------------------------------------------------------------------------------

Use case name: Treat Patient
THREAT IDENTIFICATION
Threat
Security
Assets
Description
Vulnerability
Tampering
User Information
Information the doctor has entered can be modified
The information is modified and the user
MITIGATION USE CASE DIAGRAM:
NEW SECURITY USE CASE TO MITIGATE THREAT:
HASH security use case:
Security use case name: Hash Integrity Check
Summary: converts data using hash table
Actor: Doctor, System
Precondition: None
Main sequence:
1. Data is compressed by a hashing algorithm into a hash value.
2. Hash value and original data sent to the system.
3. System verifies data consistency by comparing received hash value to computed hash value.
4. Data is consistent and is updated to the system.
Alternative sequence:
• Step 4: Hash values do not match, data is not updated, and vulnerability is reported.
Postcondition: Data is consistent and has been updated in system, or data is inconsistent and system rejects update request

INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
Security use case name: Securely Treat Patient with Hash.
Summary: The doctor treats the patient.
Actor: Doctor
Precondition: The doctor has logged into the system.
Main sequence:
1. The doctor enters the patient’s name and SSN.
2. The system displays the patient’s medical chart (name, birthday, SSN, treatment notes, prescribed medications, and doctor’s name).
3. The doctor enters a treatment note and prescription (if any) into the patient’s medical
chart after meeting the patient.
	<<Hashing Integrity Check>>
4. The system updates the patient’s medical chart with the patient’s name, birthday, SSN, treatment note, prescription, and doctor’s name.
5. The system displays a confirmation message if the patient’s medical chart has been
updated successfully.
Alternative sequence:
Step 5: The system displays an error message if it fails to update the patient’s medical
record.
Postcondition: The doctor has treated the patient.

—----------------------------------------------------------------------------------------------------------------------------
Use case name: Fill Prescription
THREAT IDENTIFICATION
Threat
Security
Assets
Description
Vulnerability
Information Disclosure
Customer Information
System fails to hide sensitive customer information
The customer information is shown.


MITIGATION USE CASE DIAGRAM:


NEW SECURITY USE CASE TO MITIGATE THREAT:
Prescription Hides Info
Security use case : Hide Customer Information
Summary: System hides customers information when pharmacist request prescription information
Actor: N/A
Precondition: N/A
Main sequence:
1. System encrypts are hides sensitive customer information
Alternative sequence:
N/A
Postcondition: System hides customers information on pharmacist screen


INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
Security use case name: Securely Fill Prescription
Summary: The prescription will be checked within the systems database to check if any current prescription taking can cause harm. The system also accurately calculates BMI from the database to prescribe the correct dosage and amount.
Actor: Pharmacist, Prescription Database System
Precondition: Doctor has prescribed the patient. Pharmacists is logged into the system.
Main sequence:
1. Pharmacist enter patient's name and SSN
2.The system displays the patient’s prescription (patient’s name, SSN, and prescription,
doctor’s name).
<<Hide Customer Information>>
3. The pharmacist enters the patient’s birthday, day/time, and pharmacist’s name after filling the prescription
4. The database system stores the prescription information (patient’s name, birthday, SSN, day/time, prescription, and pharmacist’s name)
5. The system displays a confirmation message if the prescription information has been stored successfully
Alternative sequence:
• Step 4: The system displays an error message if it fails to store the prescription information
Postcondition: Pharmacist has filled prescription

Q3:


Threat
Security
Assets
Description
Vulnerability
Input Validation
Prescription Info
System validates Pharmacist prescription info.
Improper input to the system.



—----------------------------------------------------------------------------------------------------------------------------

Use case name: Approve Copayment
[p-
THREAT IDENTIFICATION
Threat
Security
Assets
Description
Vulnerability
Improper Input Validation
Insurance company system
A patient enters invalid info
Improper info into the insurance system.


MITIGATION USE CASE DIAGRAM:


NEW SECURITY USE CASE TO MITIGATE THREAT:
Security use case:  Validate patients’ treatment notes
Summary: The system validates the researcher/patient input
Actor: Researcher/Patient
Precondition: None
Main sequence:
1. The system validates the format and range of the patient notes.
Alternative sequence:
• Step 1: The system fails to validate the user input form and displays the incorrect  form along with an error message.
Postcondition: The system has validated the input for correct formatting, SQL injections, XSS, and CSRF, or has rejected the request due to an invalid input and returned an error message.
INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
Security Use Case Name:Securely Approve Copayment
Summary: The insurance company reviews patients’ treatment notes and approves the copay
amount.
Actor: Insurance company, Patient
Precondition: The insurance company has logged into the system.
Main sequence:
1. The insurance company requests the patients’ treatment notes that have not been
Approved.
<< Validate patients’ treatment notes>>
2. The system displays the unapproved patients’ medical charts.
3. The insurance company enters each patient’s copay amount.
4. The system stores each patient’s copayment information (patient’s name, SSN, copay
amount, and doctor’s name).
5. The system displays a confirmation message if the copayment information has been
stored successfully.
6. The system sends each patient the insurance copayment approval message.
Alternative sequence:
Alternative sequence:
Step 5: The system displays an error message if it fails to store the copayment
information.
Postcondition: The insurance company has approved the copay amount


Threat
Security
Assets
Description
Vulnerability
Information Disclosure
Patients’ Medical Chart
System does not encrypt data before sending
Data is unencrypted.




—----------------------------------------------------------------------------------------------------------------------------

Use case name: Request Trial Research Data
THREAT IDENTIFICATION
Threat
Security
Assets
Description
Vulnerability
Improper Input Validation
Request Trial Research Data (use case)
Unvalidated input can breach the use case.
The system does not validate user input. SQL Injection, XSS, CSRF


MITIGATION USE CASE DIAGRAM:


NEW SECURITY USE CASE TO MITIGATE THREAT:
Security use case:  Validate Request Trial Research Data Input:
Summary: The system validates the researcher/patient input
Actor: Researcher/Patient
Precondition: None
Main sequence:
1. The system validates the format and range of the user disease search form.
2. The system validates the format of the compliance agreement.
Alternative sequence:
• Step 1: The system fails to validate the user input form and displays the incorrect  form along with an error message.
• Step 2: The system fails to validate the compliance agreement format and displays an error message.
Postcondition: The system has validated the user’s input for correct formatting, SQL injections, XSS, and CSRF, or has rejected the request due to an invalid input and returned an error message.

INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
Security Use Case Name: Securely Request Trial Research Data
Summary: The researcher requests patient treatment data for trial research. User input is validated.
Actor: Researcher, Patient
Precondition: None
Main sequence:
1. The researcher enters a disease with the compliance agreement via a (privacy
management) blockchain system.
	<<Validate Request Trial Research Data Input>>
2. The blockchain system retrieves the patients who have been treated for the disease from
the medical charts.
3. The blockchain system sends the patients an approval request message with the
compliance agreement if there are patients with the disease.
Alternative sequence:
Step 3: The blockchain system displays a message if there is no patient for the disease.
Postcondition: The researcher has requested trial research data.


4)








—----------------------------------------------------------------------------------------------------------------------------

Use case name: Approve Trial Research Data Request
THREAT IDENTIFICATION
Threat
Security
Assets
Description
Vulnerability
Elevation of Privileges
System Research Data
Allow actor without proper security level to approve data request
The system does not check actor privileges level.


MITIGATION USE CASE DIAGRAM:


NEW SECURITY USE CASE TO MITIGATE THREAT:
Privilege Escalation Detection security use case:
Security use case: Privilege Escalation Detection
Summary: Notes various suspicious activities related to Improper privilege
Actor: System
Precondition: None
Main sequence:
1. Note suspicious logins like users logging in far from the hospital.
2. Prevents rapid escalation of user privileges.
3. System will continuously check for these anomalies.
Alternative sequence:
• Step 2: Block rapid escalation of privileges and request authorization from confirmed admin.
Postcondition: None
INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
Security Use Case Name: Securely Approve Trial Research Data Request
Summary: The patient  securely approves the trial research data request.
Actor: Patient, Researcher
Precondition:
Main sequence:
1. The patient reviews an approval request message with the compliance agreement from
the researcher.
2. The patient enters the decision for trial research data request into the blockchain system.
3. The blockchain system creates a permission code and stores the approval information if
the patient approves the trial research data request.
 <<Privilege Escalation Detection>>
4. The blockchain system sends the permission code to the researcher.
Alternative sequence:
Step 3: The blockchain system terminates if the patient denies the trial research data
request.
Postcondition: The patient has approved the trial research data request.

4)


—----------------------------------------------------------------------------------------------------------------------------

Use case name: Pay Bill
THREAT IDENTIFICATION
Threat
Security
Assets
Description
Vulnerability
Repudiation
Non-repudiation
Bank claims to have not received payment
Bank has not received payment, and releases “failure to pay”


MITIGATION USE CASE DIAGRAM:


NEW SECURITY USE CASE TO MITIGATE THREAT:
Bill Validation security use case:
Security use case : Bill Receipt
Summary: Patient receives a confirmation receipt to his/her email
Actor: Patient
Precondition: Patient has paid the copay bill
Main sequence:
1. Patient enters banking information and submits payment
2. Patient receives confirmation receipt via email to verify
Alternative sequence:
• Step 1: Patient enters incorrect information causing payment error
• Step 2: Patient doesn’t receive email automatically contacting bank for fraud

Postcondition: Confirmation receipt received by patient

INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
Security Use Case Name: Securely Pay Bill
Summary: The patient pays the copay amount and receives confirmation of payment via email
Actor: Patient, Bank
Precondition: The patient has logged in and received a bill via online
Main sequence:
1. The patient enters the customer’s bill number.
2. The system displays the customer’s bill information (bill number, name, doctor’s name,
and amount to pay).
3. The patient enters credit card information (name, card number, security code, address)
into the system.
4. Patient receives confirmation receipt after sending information
5. The system sends the credit card information to a bank to pay the amount.
6. If the bank approves the payment, the bank returns a reference number, and the system
displays a receipt for the payment.
<<Bill Receipt>>
Alternative sequence:
Step 3: If information incorrectly entered displays an error message
Step 6: If the bank denies the payment, the system displays an error message.
Postcondition: The patient has paid the bill and received payment confirmation receipt

Q3:

Threat
Security Asset
Description
Vulnerability
Steal Customers Payment Information
Customer Payment Info (entity)
Customers payment information is leaked or stolen
System doesn’t encrypt payment information


Hide Payment Information security use case:
Security use case : Hide Payment Information
Summary: System hides customers payment information when paying a bill
Actor: N/A
Precondition: Patient has bill
Main sequence:
1. System encrypts are hides sensitive payment information
Alternative sequence:
N/A
Postcondition: System hides customers payment information on receipt and payment screen

INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
Security Use Case Name: Securely Pay Bill
Summary: The patient pays the copay amount and receives confirmation of payment via email
Actor: Patient, Bank
Precondition: The patient has logged in and received a bill via online
Main sequence:
1. The patient enters the customer’s bill number.
2. The system displays the customer’s bill information (bill number, name, doctor’s name,
and amount to pay).
3. The patient enters credit card information (name, card number, security code, address)
into the system.
4. Patient receives confirmation receipt after sending information
5. The system sends the credit card information to a bank to pay the amount.
6. If the bank approves the payment, the bank returns a reference number, and the system
displays a receipt for the payment.
<<Hidden Payment Information>>
<<Bill Receipt>>
Alternative sequence:
Step 3: If information incorrectly entered displays an error message
Step 6: If the bank denies the payment, the system displays an error message.
Postcondition: The patient has paid the bill and received payment confirmation receipt


Q4:


—----------------------------------------------------------------------------------------------------------------------------

Use case name: View Medical Chart
THREAT IDENTIFICATION
Threat
Security
Assets
Description
Vulnerability
Disclose sensitive
customer profile
output
Customer medical record
(output)
The customer medical record can be
released.
The system displays the sensitive
customer profile.


MITIGATION USE CASE DIAGRAM:


NEW SECURITY USE CASE TO MITIGATE THREAT:
Security use case: Data Encryption
Summary: Data is encrypted while it is being accessed
Actor: Patient, System
Precondition: None
Main sequence:
1. System Request Key.
2. System encrypts data with a key.
3. System sends data
4. User system decrypts data with a key inorder to read data.
Alternative sequence: None
Postcondition: Data is encrypted during transit

INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
Security Use Case Name: View Medical Charts
Summary: The patient looks at the medical record.
Actor: Patient
Precondition: The patient has logged in.
Main sequence:
1. The patient enters the name and SSN.
	<< Data Encryption >>
2. The system displays the patient’s medical chart.
3. The patient requests approved trial research.
	<< Data Encryption >>
4. The system displays approved trial research.
Alternative sequence: None
Postcondition: The patient has looked at the medical chart.

Q3:
Threat
Security Asset
Description
Vulnerability
Unauthorized Access
Patients Medical Chart (entity)
Customers medical information leaked
System doesn’t verify person accessing chart




NEW SECURITY USE CASE TO MITIGATE THREAT:
Input Validation
Security Use Case: Medical Chart Two-Step Validation
Summary: The system has a two-step validation by sending a code to the users email
Actor: Patient
Precondition: None.
Main sequence:
1. The system sends a code to users email
2. The system confirms code validity.
Alternative sequence:
• Step 2: System terminated if code is invalid.
Postcondition: The system has successfully validated the patient can view medical records

INTEGRATED USE CASE WITH NEW SECURITY USE CASE:
Security Use Case Name: View Medical Charts
Summary: The patient looks at the medical record.
Actor: Patient
Precondition: The patient has logged in.
Main sequence:
1. The patient enters the name and SSN.
	<< Data Encryption >>
<< Two Step Verification >>
2. The system displays the patient’s medical chart.
3. The patient requests approved trial research.
	<< Data Encryption >>
4. The system displays approved trial research.
Alternative sequence: None
Postcondition: The patient has looked at the medical chart.


Q4:

 */
