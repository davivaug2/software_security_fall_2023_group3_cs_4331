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

import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class PayBill {

    public void run_program() {
        try (UI ui = new UI()) {
            Bill bill = new Bill();

            // Request customer information
            String name = ui.requestInput("Enter your name:");
            String creditCard = ui.requestInput("Enter your credit card number:");
            String securityCode = ui.requestInput("Enter your security code:");
            String billingAddress = ui.requestInput("Enter your billing address:");
            String email = ui.requestInput("Enter your email address:");

            // Construct customer information
            String customerInfo = name + ", " + creditCard + ", " + securityCode + ", " + billingAddress + ", " + email;
            bill.setCustomerInfo(customerInfo);

            // Encrypt customer information
            String encryptedCustomerInfo = encryptData(customerInfo);
            bill.setEncryptedCustomerInfo(encryptedCustomerInfo);

            // Display encrypted customer information
            ui.displayEncryptedInfo(encryptedCustomerInfo);

            // Request payment information
            String paymentInfo = ui.requestInput("Enter payment amount:");
            bill.setPaymentInfo(paymentInfo);

            // Validate payment
            boolean paymentValid = ValidatePayment.validate(bill);

            // Display payment confirmation
            ui.displayConfirmed(paymentValid);

            // Generate and display receipt upon successful payment
            if (paymentValid) {
                String receipt = generateReceipt(name, creditCard, email);
                ui.displayReceipt(receipt);
            }
        }
    }

    private static String encryptData(String data) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return new String(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateReceipt(String name, String creditCard, String email) {
        Random random = new Random();
        int receiptNumber = 100000 + random.nextInt(900000); // Generates a random 6-digit receipt number
        return "Receipt Number: " + receiptNumber + "\nName: " + name + "\nLast 4 Digits of Credit Card: " +
                creditCard.substring(creditCard.length() - 4) + "\nEmail: " + email;
    }
}

class UI implements AutoCloseable {
    private Scanner scanner;

    public UI() {
        scanner = new Scanner(System.in);
    }

    public String requestInput(String prompt) {
        System.out.println(prompt);
        return getInput();
    }

    public void displayEncryptedInfo(String encryptedInfo) {
        System.out.println("Encrypted Customer Information: " + encryptedInfo);
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void displayConfirmed(boolean confirmed) {
        if (confirmed) {
            System.out.println("Payment Confirmed!");
        } else {
            System.out.println("Payment Not Confirmed. Please check your information.");
        }
    }

    public void displayReceipt(String receipt) {
        System.out.println("\nPayment Receipt:\n" + receipt);
    }

    @Override
    public void close() {
        if (scanner != null) {
            //scanner.close();
        }
    }
}

class Bill {
    private String encryptedCustomerInfo;
    private String paymentInfo;

    public String getEncryptedCustomerInfo() {
        return encryptedCustomerInfo;
    }

    public void setEncryptedCustomerInfo(String encryptedCustomerInfo) {
        this.encryptedCustomerInfo = encryptedCustomerInfo;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    // Additional customer information for validation or other purposes
    private String customerInfo;

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }
}

class ValidatePayment {
    public static boolean validate(Bill bill) {
        // Add your validation logic here
        return true;
    }
}
