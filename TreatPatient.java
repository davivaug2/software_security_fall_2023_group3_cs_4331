
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
Title : TreatPatient.java
Software Security
Description : .java file for TreatPatient use case
Author : Quinten Bruce (R#11703344)
Date : 12/2/2023
Notes :
Java Version:
=============================================================================
 */


public class TreatPatient {
    public void run_program() throws Exception {
        UserInterface_.EnterPatientInfo();
        UserInterface_.SendMedicalChart();
    }
    public static String SearchPatientInfo(String name) {
        return UserProfile.SearchPatientChart(name);

    }
    public static boolean SendMedicalChart(String name, String info) {
        return UserProfile.RecieveUserProfile(name, info);
        

    }
}

class UserInterface_ {
	//gathers user input for A1
    public static void EnterPatientInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter patient name (Bob for testing): ");
        String name = scanner.nextLine();
        String res = TreatPatient.SearchPatientInfo(name);
        System.out.println("	Patient Medical Chart: "+res);
    }
    //Gathers user input for A5
    public static void SendMedicalChart() {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter name (Bob for testing): ");
        String name = scanner.nextLine();
        System.out.print("Enter medical info (any string): ");
        String info = scanner.nextLine();
        boolean res = TreatPatient.SendMedicalChart(name, info);
        if (res) {
        	System.out.println("	Hash Integrity check successfull! Data updated.");
        }
    }

}

class PatientsMedicalChart {
	//medical information
    private static Map<String, String> medicalInfoDictionary = new HashMap<>();

    static {
        medicalInfoDictionary.put("Bob", "Blood Type: A+, Allergies: None, Medications: None");
        medicalInfoDictionary.put("Jane", "Blood Type: O-, Allergies: Penicillin, Medications: Asthma inhaler");
    }
    //gets medical information from charts
    public static String GetChart(String name) {
        if (medicalInfoDictionary.containsKey(name)) {
            return medicalInfoDictionary.get(name);
        }
        return "";
    }
    //inserts data into medical charts and returns info
    public static String SetChart(String name, String info) {
        if (medicalInfoDictionary.containsKey(name)) {
            medicalInfoDictionary.replace(name, info);
            return info;
        }
        medicalInfoDictionary.put(name, info);
        return info;

    }
}

class UserProfile {
	//returns patient chart info
    public static String SearchPatientChart(String name) {
        String res = PatientsMedicalChart.GetChart(name);
        if (res == "" || "".compareTo(res) == 1) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Patient not found\nEnter patient name: ");
            name = scanner.nextLine();
            return SearchPatientChart(name);
        }
        return res;
    }
    //return status of insert
    public static boolean RecieveUserProfile(String name, String info) {
    	//hash information prior to insert
        byte[] hash = HashIntegrityChart.hashString(info);
        System.out.println("	hash value before insert: "+hash);
        String chart = PatientsMedicalChart.SetChart(name, info);
        //hash information post insert
        byte[] hash2 = HashIntegrityChart.hashString(chart);
        //compare equality of hash values to test integrity
        System.out.println("	hash value after insert: "+hash);
        if (Arrays.equals(hash, hash2)) {
            return true;
        }
        return false;
    }
}

class HashIntegrityChart {
	//hash a string with SHA-256
    public static byte[] hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
