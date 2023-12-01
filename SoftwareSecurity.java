/*
Title : SoftwareSecurity.java
Software Security
Description : This is a Java File for Software Secuirityu cs_4331  Project 1 
Author : David	Vaughan	davivaug@ttu.edu
Landon	Hassin	Landon.Hassin@ttu.edu
Quinten	Bruce	qbruce@ttu.edu
Eric	Ward	war71624@ttu.edu
Date : 12/2/2023
Version : 1.0
Usage : run
Notes :
Java Version:
=============================================================================
 */

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

class SoftwareSecurity {
  public static void main(String[] args) throws Exception {
	  Scanner scanner = new Scanner(System.in);
	  String action;

	  do {
		  System.out.println("What do you want to do?");
		  System.out.println("	1. Treat Patient");
		  System.out.println("	2. Fill Prescription");
		  System.out.println("	3. Approve Copayment");
		  System.out.println("	4. Pay Bill");
		  System.out.println("	5. Exit");
		  

		  System.out.print("Enter your choice: ");
		  action = scanner.nextLine();

		  switch (action) {
			  case "1":
				  System.out.println("Performing 'Treat Patient'...");
				  // TreatPatient tp = new TreatPatient();
          // tp.run_program();
				  break;
			  case "2":
				  System.out.println("Performing 'Fill Prescription'...");
				  // FillPrescription fp = new FillPrescription();
				  // fp.run_program();
				  break;
			  case "3":
				  System.out.println("Performing 'Approve Copayment'...");
				  ApproveCopayment co = new ApproveCopayment();
				  co.run_program();
				  break;
			  case "4":
				  System.out.println("Performing 'Pay Bill'...");
				  // PayBill pb = new PayBill();
          // pb.run_program();
				  break;
			  case "5":
				  System.out.println("Exiting system.");
				  break;
			  default:
				  System.out.println("Please enter a number between 1 and 5.");
				  break;
		  }

	  } while (!action.equals("5"));
  }
}


/*
1
CS 4331-Fall 2023-Project
Project: Healthcare System (HS)
The healthcare system can provide services for the patient, doctor, pharmacy, insurance, and
researcher. The use cases for HS are described below.
Your team must design a secure healthcare system to protect the system and sensitive assets from
threats or attacks. The following describes the instructions for designing a secure healthcare
system.
1. Define the system boundary using a software system context model that shows how the
system interfaces with the external environment. Revise the Authenticate security use case
using the use case template to mitigate the impersonated threat. The revised authenticate
security use case needs to use 2-factor authentication and apply a time limitation to insert the
second credentials.
2. Identify and analyze threats to each use case to develop the secure use case model. Specify
the security use cases to mitigate the threats and indicate where the security use cases extend
the application use cases using extension points.
a. Identify one or two threats in each use case and six different threats in all the use
cases.
3. Develop the class diagram that shows the entity and interface classes for application use
cases and security use cases specified in (1) and (2). Indicate security classes among the
classes with a stereotype. Apply threat modeling to the class diagram to identify additional
threats to classes and provide security classes against the threats identified.
4. Develop the secure interaction model that realizes each secure application use case, where
the model needs to integrate application and security objects.
5. Implement the secure Treat Patient, secure Fill Prescription, secure Approval Copayment,
and secure Pay Bill security use cases developed in (4) above. You can use an object-oriented
programming language (Java, C++, C#, or Python) to implement.
Make your assumptions as necessary.
2
Use Case Description for Healthcare System:
Use case name: Login
Summary: The user (patient, doctor, pharmacist, insurance company, and researcher) logs into
the system.
Actor: Patient, Doctor, Pharmacist, Insurance company, and Researcher
Precondition: The user has created an account in the healthcare system.
Main sequence:
1. The user inputs the ID and password.
2. The system checks if the patient ID and password are valid.
3. The system displays a welcome message if the ID and password are valid.
Alternative sequence:
Postcondition: The user has logged into the system.
Use case name: Treat Patient
Summary: The doctor treats the patient.
Actor: Doctor
Precondition: The doctor has logged into the system.
Main sequence:
1. The doctor enters the patient’s name and SSN.
2. The system displays the patient’s medical chart (name, birthday, SSN, treatment notes,
prescribed medications, and doctor’s name).
3. The doctor enters a treatment note and prescription (if any) into the patient’s medical
chart after meeting the patient.
4. The system updates the patient’s medical chart with the patient’s name, birthday, SSN,
treatment note, prescription, and doctor’s name.
5. The system displays a confirmation message if the patient’s medical chart has been
updated successfully.
Alternative sequence:
 Step 5: The system displays an error message if it fails to update the patient’s medical
record.
Postcondition: The doctor has treated the patient.
Use case name: Fill Prescription
Summary: The pharmacist fills a prescription for a patient.
Actor: Pharmacist
Precondition: The pharmacist has logged into the system. (Assume a patient visits the pharmacy
to get prescribed medication, and the pharmacy is part of the healthcare system.)
Main sequence:
1. The pharmacist enters the patient’s name and SSN.
2. The system displays the patient’s prescription (patient’s name, SSN, and prescription,
doctor’s name).
3. The pharmacist enters the patient’s birthday, day/time, and pharmacist’s name after
filling the prescription.
4. The system stores the prescription information (patient’s name, birthday, SSN, day/time,
prescription, and pharmacist’s name).
3
5. The system displays a confirmation message if the prescription information has been
stored successfully.
Alternative sequence:
 Step 5: The system displays an error message if it fails to store the prescription
information.
Postcondition: The pharmacist has filled a prescription.
Use case name: Approve Copayment
Summary: The insurance company reviews patients’ treatment notes and approves the copay
amount.
Actor: Insurance company, Patient
Precondition: The insurance company has logged into the system.
Main sequence:
1. The insurance company requests the patients’ treatment notes that have not been
approved.
2. The system displays the unapproved patients’ medical charts.
3. The insurance company enters each patient’s copay amount.
4. The system stores each patient’s copayment information (patient’s name, SSN, copay
amount, and doctor’s name).
5. The system displays a confirmation message if the copayment information has been
stored successfully.
6. The system sends each patient the insurance copayment approval message.
Alternative sequence:
 Step 5: The system displays an error message if it fails to store the copayment
information.
Alternative sequence: None.
Postcondition: The insurance company has approved the copay amount.
Use case name: Request Trial Research Data
Summary: The researcher requests patient treatment data for trial research.
Actor: Researcher, Patient
Precondition:
Main sequence:
1. The researcher enters a disease with the compliance agreement via a (privacy
management) blockchain system.
2. The blockchain system retrieves the patients who have been treated for the disease from
the medical charts.
3. The blockchain system sends the patients an approval request message with the
compliance agreement if there are patients with the disease.
Alternative sequence:
 Step 3: The blockchain system displays a message if there is no patient for the disease.
Postcondition: The researcher has requested trial research data.
4
Use case name: Approve Trial Research Data Request
Summary: The patient approves the trial research data request.
Actor: Patient, Researcher
Precondition:
Main sequence:
1. The patient reviews an approval request message with the compliance agreement from
the researcher.
2. The patient enters the decision for trial research data request into the blockchain system.
3. The blockchain system creates a permission code and stores the approval information if
the patient approves the trial research data request.
4. The blockchain system sends the permission code to the researcher.
Alternative sequence:
 Step 3: The blockchain system terminates if the patient denies the trial research data
request.
Postcondition: The patient has approved the trial research data request.
Use case name: Pay Bill
Summary: The patient pays the copay amount.
Actor: Patient, Bank
Precondition: The patient has logged in and received a bill. (Assume the patient pays the bill via
the payment bill website, which has the patient’s bill information. The website is part of the
healthcare system)
Main sequence:
1. The patient enters the customer’s bill number.
2. The system displays the customer’s bill information (bill number, name, doctor’s name,
and amount to pay).
3. The patient enters credit card information (name, card number, security code, address)
into the system.
4. The system sends the credit card information to a bank to pay the amount.
5. If the bank approves the payment, the bank returns a reference number, and the system
displays a receipt for the payment.
Alternative sequence:
 Step 6: If the bank denies the payment, the system displays an error message.
Postcondition: The patient has paid the bill.
Use case name: View Medical Chart
Summary: The patient looks at the medical record.
Actor: Patient
Precondition: The patient has logged in.
Main sequence:
1. The patient enters the name and SSN.
2. The system displays the patient’s medical chart.
3. The patient requests approved trial research.
4. The system displays approved trial research.
Alternative sequence: None
Postcondition: The patient has looked at the medical chart.
 */
