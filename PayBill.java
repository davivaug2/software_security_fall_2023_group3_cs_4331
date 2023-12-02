/*
=============================================================================
Title : PayBill.java
Description : This program is the implementation of Secure Pay Bill use case
Author : Landon Hassin: landon.hassin@ttu.edu
Date : 12/02/23
Version : 1.0
Usage : Compile and run this program using your Java compiler (ex: IDE eclipse)
=============================================================================
*/

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Scanner;

public class PayBill {
    private Scanner scanner;

    public PayBill(Scanner scanner) {
        this.scanner = scanner;
    }

    public void runProgram() {
        System.out.println("Performing 'Pay Bill'...");
        System.out.print("Enter the customer's bill number: ");
        String billNumber = scanner.nextLine();

        // Simulate retrieving bill information from the system
        BillInformation billInformation = getBillInformation(billNumber);

        if (billInformation != null) {
            displayBillInformation(billInformation);

            // Step 3: The patient enters credit card information
            CreditCardInformation creditCardInfo = getCreditCardInformation();

            // Step 4: Patient receives confirmation receipt after sending information
            // Enhancing security: Hide sensitive payment information
            String encryptedPaymentInfo = encryptPaymentInfo(creditCardInfo);
            displayConfirmationReceipt(encryptedPaymentInfo);

            // Step 5: The system sends the credit card information to a bank
            BankResponse bankResponse = sendToBank(creditCardInfo);

            // Step 6: If the bank approves the payment
            if (bankResponse.isApproved()) {
                // The bank returns a reference number
                String referenceNumber = bankResponse.getReferenceNumber();
                // The system displays a receipt for the payment
                displayPaymentReceipt(referenceNumber);

                // Security enhancement: Send confirmation receipt to the patient's email
                sendConfirmationEmail(billInformation, creditCardInfo, referenceNumber);
            } else {
                // Alternative sequence: If the bank denies the payment
                System.out.println("Error: Payment denied by the bank. Please check your information.");
            }
        } else {
            // Display an error message if the bill information is not found
            System.out.println("Error: Bill information not found.");
        }
    }

    private BillInformation getBillInformation(String billNumber) {
        // Simulate retrieving bill information from the system (replace with actual implementation)
        // For simplicity, a mock BillInformation is returned.
        return new BillInformation(billNumber, "John Doe", "Dr. Smith", 100.0);
    }

    private void displayBillInformation(BillInformation billInformation) {
        // Display the customer’s bill information
        System.out.println("Customer's Bill Information:");
        System.out.println("Bill Number: " + billInformation.getBillNumber());
        System.out.println("Name: " + billInformation.getCustomerName());
        System.out.println("Doctor's Name: " + billInformation.getDoctorName());
        System.out.println("Amount to Pay: $" + billInformation.getAmountToPay());
    }

    private CreditCardInformation getCreditCardInformation() {
        // Prompt the patient to enter credit card information
        System.out.println("Enter credit card information:");
        System.out.print("Name on the Card: ");
        String nameOnCard = scanner.nextLine();
        System.out.print("Card Number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Security Code: ");
        String securityCode = scanner.nextLine();
        System.out.print("Billing Address: ");
        String billingAddress = scanner.nextLine();

        // Create and return CreditCardInformation object
        return new CreditCardInformation(nameOnCard, cardNumber, securityCode, billingAddress);
    }

    private void displayConfirmationReceipt(String encryptedPaymentInfo) {
        // Display confirmation receipt after sending information
        System.out.println("Confirmation Receipt:");
        System.out.println("Payment information received. Processing payment...");
        // Display only the last 4 digits of the credit card number and * for other digits
        String maskedCardNumber = maskCreditCardNumber(encryptedPaymentInfo);
        System.out.println("Encrypted Payment Information: " + maskedCardNumber);
    }

    private String maskCreditCardNumber(String encryptedPaymentInfo) {
        // Extract the last 4 digits of the credit card number
        String last4Digits = encryptedPaymentInfo.substring(encryptedPaymentInfo.length() - 4);
        // Mask the other digits with *
        String maskedCardNumber = "**** **** **** " + last4Digits;
        return maskedCardNumber;
    }

    private BankResponse sendToBank(CreditCardInformation creditCardInfo) {
        // Simulate sending credit card information to the bank (replace with actual implementation)
        // For simplicity, a mock BankResponse is returned.
        return new BankResponse(true, "ABC123"); // Simulating an approved payment
    }

    private void displayPaymentReceipt(String referenceNumber) {
        // Display a receipt for the payment
        System.out.println("Payment Successful!");
        System.out.println("Reference Number: " + referenceNumber);
    }

    // Security enhancement: Send confirmation receipt to the patient's email using JavaMail API
    private void sendConfirmationEmail(BillInformation billInformation, CreditCardInformation creditCardInfo, String referenceNumber) {
        System.out.print("Enter your email address for the receipt: ");
        String userEmail = scanner.nextLine();

        // JavaMail API properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "your_mail_host");
        properties.put("mail.smtp.port", "your_mail_port");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // JavaMail API session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your_email@example.com", "your_email_password");
            }
        });

        try {
            // JavaMail API message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email@example.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Payment Confirmation");

            // Email content
            String emailContent = "Dear " + billInformation.getCustomerName() + ",\n\n"
                    + "Your payment was successful!\n\n"
                    + "Bill Information:\n"
                    + "Bill Number: " + billInformation.getBillNumber() + "\n"
                    + "Doctor's Name: " + billInformation.getDoctorName() + "\n"
                    + "Amount Paid: $" + billInformation.getAmountToPay() + "\n\n"
                    + "Reference Number: " + referenceNumber + "\n\n"
                    + "Thank you for choosing our services!\n";

            message.setText(emailContent);

            // JavaMail API transport
            Transport.send(message);
            System.out.println("Email sent successfully to " + userEmail + "!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending email.");
        }
    }

    // Security enhancement: Encrypt sensitive payment information using Java's Cipher
    private String encryptPaymentInfo(CreditCardInformation creditCardInfo) {
        try {
            // Implement encryption logic here (e.g., using Java's Cipher)
            // For simplicity, we'll use a simple masking approach for illustration purposes
            String cardNumber = creditCardInfo.getCardNumber();
            String maskedCardNumber = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
            return maskedCardNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during encryption";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PayBill payBill = new PayBill(scanner);
        payBill.runProgram();
    }
}

