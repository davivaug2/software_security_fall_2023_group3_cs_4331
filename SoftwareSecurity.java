/*
Title : SoftwareSecurity.java
Software Security
Description : This is a Java File for Software Security cs_4331  Project 1 
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
					TreatPatient tp = new TreatPatient();
					tp.run_program();
					break;
				case "2":
					System.out.println("Performing 'Fill Prescription'...");
					FillPrescription fp = new FillPrescription();
					fp.run_program();
					break;
				case "3":
					System.out.println("Performing 'Approve Copayment'...");
					ApproveCopayment co = new ApproveCopayment();
					co.run_program();
					break;
				case "4":
					System.out.println("Performing 'Pay Bill'...");
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

		} while (!action.equals("5"));
	}
}

