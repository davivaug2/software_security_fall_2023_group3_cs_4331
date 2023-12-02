/*
Title : FillPrescription.java
Software Security
Description : This is a Java File for Securely Fill Prescription use case.
Author : Eric Ward (R#11697072)
Date : 12/2/2023
Version : 1.0
Usage : 
Notes :
Java Version:
=============================================================================
 */
import java.util.ArrayList;
import java.util.List;

public class FillPrescription {
  public  void run_program() throws Exception {
    PharmacistInterface pharmacistInterface = new PharmacistInterface();
    PatientDatabase patientDatabase = new PatientDatabase();
    patientDatabase.addPatient(new Patient("111", "John Doe", "Xanax", "Dr. Smith", "01/01/1999", null));
    pharmacistInterface.run_program(patientDatabase);
  }
}

class Patient{
  private String ssn;
  private String hiddenSSN;
  private String name;
  private String prescription;
  private String doctor;
  private String birthdate;
  private String hiddenBirthdate;
  private List<String> prescriptionHistory;
  public Patient(String ssn, String name, String prescription, String doctor, String birthdate, String[] prescriptionHistory){
    this.ssn = ssn;
    this.name = name;
    this.prescription = prescription;
    this.doctor = doctor;
    this.birthdate = birthdate;
    this.prescriptionHistory = new ArrayList<>();
  }

    public String getSsn() {
        return ssn;
    }

    public void sethiddenSSN(String hiddenSSN) {
      this.hiddenSSN = hiddenSSN;
    }
    public String gethiddenSSN() {
      return hiddenSSN;
    }

    public String getName() {
        return name;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getDoctor() {
        return doctor;
    }
    public String getBirthdate() {
        return birthdate;
    }
    public void sethiddenBirthdate(String hiddenBirthdate) {
      this.hiddenBirthdate = hiddenBirthdate;
    }
    public String gethiddenBirthdate() {
      return hiddenBirthdate;
    }
    public List<String> getPrescriptionHistory() {
        return prescriptionHistory;
    }
    public void setPrescriptionHistory(List<String> prescriptionHistory) {
        this.prescriptionHistory = prescriptionHistory;
    }
}

class PatientDatabase {
    private List<Patient> patients;

    public PatientDatabase() {
        this.patients = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public void addPrescriptionHistory(Patient patient, String prescription, String date, String pharmacistName) {
        patient.getPrescriptionHistory().add(prescription + " " + date + " " + pharmacistName);
    }
    public List<Patient> getPatients() {
        return patients;
    }

    public Patient getPatientBySsn(String ssn) {
        for (Patient patient : patients) {
            if (patient.getSsn().equals(ssn)) {
                return patient;
            }
        }
        return null;
    }
}


class PharmacistInterface{
  private String ssn;
  private int exitCode;
  PharmacistInterface(){
    this.ssn = null;
  }
  public void setSsn(String ssn) {
    this.ssn = ssn;
  }
  public String getSsn() {
    return ssn;
  }
  public void setExitCode(int exitCode) {
    this.exitCode = exitCode;
  }
  public int getExitCode() {
    return exitCode;
  }
  public void run_program(PatientDatabase patientDatabase) throws Exception {
    do{
      System.out.println("\nWhat do you want to do?");
			System.out.println("    1. Add Patient(For Testing Purposes)");
      System.out.println("    2. List Patient(For Testing Purposes)");
			System.out.println("    3. Fill Patient Prescription");
			System.out.println("    4. Exit");

			System.out.print("Enter your choice: ");
      String ssn = System.console().readLine();
      switch (ssn) {
        case "4":
          System.out.println("Exiting...\n");
          this.setExitCode(1);
          break;
        case "3":
          System.out.println("Searching for patient...\n");
          System.out.println("Please enter patient ssn (For Testing: 111): ");
          ssn = System.console().readLine();
          if(!InputValidation.validateSSN(ssn)){
            System.out.println("Invalid SSN");
            break;
          }
          Patient patient = patientDatabase.getPatientBySsn(ssn);
          if (patient == null) {
              System.out.println("Patient not found");
          } else {
              System.out.println("Patient found");
              HidePatientInfo.hidePatientInfo(patient);
              System.out.println("Name: " + patient.getName());
              System.out.println("Prescription: " + patient.getPrescription());
              System.out.println("Doctor: " + patient.getDoctor());
              System.out.println("Birthdate: " + patient.gethiddenBirthdate());
              System.out.println("SSN: " + patient.gethiddenSSN());
              System.out.println("\nPress enter to continue...\n");
              System.console().readLine();
              System.out.println("Enter Patient Birthdate(MM/DD/YYYY) (For Testing: 01/01/1999): ");
              String birthdate = System.console().readLine();
              if(birthdate.equals(patient.getBirthdate())){
                System.out.println("Patient Birthdate Verified");
              }
              else{
                System.out.println("Patient Birthdate Not Verified");
                break;
              }
              System.out.println("Enter current date(MM/DD/YYYY): ");
              String currentDate = System.console().readLine();
              System.out.println("Enter Parmacist's Name: ");
              String pharmacistName = System.console().readLine();
              if(!InputValidation.validateAddPrescriptionHistory(birthdate, currentDate, pharmacistName)){
                System.out.println("Invalid Input");
                break;
              }
              
              patientDatabase.addPrescriptionHistory(patient, patient.getPrescription(), currentDate, pharmacistName);
              System.out.println("Prescription filled");

          }
          break;
        case "2":
          System.out.println("Listing patients...\n");
          for (Patient patient1 : patientDatabase.getPatients()) {
              System.out.println("Name: " + patient1.getName());
              System.out.println("Prescription: " + patient1.getPrescription());
              System.out.println("Doctor: " + patient1.getDoctor());
              System.out.println("Birthdate: " + patient1.getBirthdate());
              System.out.println("Prescription History: " + patient1.getPrescriptionHistory());
              System.out.println("\n---------------------\n");
          }
          break;
        case "1":
          System.out.println("Adding patient...\n");
          System.out.println("Please enter patient name: ");
          String name = System.console().readLine();
          System.out.println("Please enter patient prescription: ");
          String prescription = System.console().readLine();
          System.out.println("Please enter patient doctor: ");
          String doctor = System.console().readLine();
          System.out.println("Please enter patient ssn: ");
          ssn = System.console().readLine();
          System.out.println("Please enter patient birthdate: ");
          String birthdate = System.console().readLine();
          if(!InputValidation.validateAddPatient(name, prescription, doctor, ssn, birthdate)){
            System.out.println("Invalid Input");
            break;
          }
          Patient newPatient = new Patient(ssn, name, prescription, doctor, birthdate, null);
          patientDatabase.addPatient(newPatient);
          break;
        default:
          break;
      }
      this.setSsn(ssn);
    } while (this.exitCode == 0);
    
  }

}

class InputValidation{
  public static boolean validateSSN(String ssn){
    if(ssn.length() != 9 && ssn.length() != 3){
      System.out.println(ssn.length());
      return false;
    }
    for(int i = 0; i < ssn.length(); i++){
      if(!Character.isDigit(ssn.charAt(i))){
        return false;
      }
    }
    return true;
  }
  public static boolean validateName(String name){
    if(name.length() > 30){
      return false;
    }
    for(int i = 0; i < name.length(); i++){
      if(!Character.isLetter(name.charAt(i))){
        return false;
      }
    }
    return true;
  }
  public static boolean validatePrescription(String prescription){
    if(prescription.length() > 30){
      return false;
    }
    for(int i = 0; i < prescription.length(); i++){
      if(!Character.isLetter(prescription.charAt(i))){
        return false;
      }
    }
    return true;
  }
  public static boolean validateDoctor(String doctor){
    if(doctor.length() > 30){
      return false;
    }
    for(int i = 0; i < doctor.length(); i++){
      if(!Character.isLetter(doctor.charAt(i))){
        return false;
      }
    }
    return true;
  }
  public static boolean validateBirthdate(String birthdate){
    if(birthdate.length() > 10){
      return false;
    }
    for(int i = 0; i < birthdate.length(); i++){
      if(!Character.isDigit(birthdate.charAt(i)) && birthdate.charAt(i) != '/'){
        return false;
      }
    }
    return true;
  }
  public static boolean validateCurrentDate(String currentDate){
    if(currentDate.length() > 10){
      return false;
    }
    for(int i = 0; i < currentDate.length(); i++){
      if(!Character.isDigit(currentDate.charAt(i)) && currentDate.charAt(i) != '/'){
        return false;
      }
    }
    return true;
  }
  public static boolean validatePharmacistName(String pharmacistName){
    if(pharmacistName.length() > 30){
      return false;
    }
    for(int i = 0; i < pharmacistName.length(); i++){
      if(!Character.isLetter(pharmacistName.charAt(i))){
        return false;
      }
    }
    return true;
  }
  public static boolean validateAddPrescriptionHistory(String birthdate, String currentDate, String pharmacistName){
    if(!validateBirthdate(birthdate)){
      System.out.println("Invalid Birthdate");
      return false;
    }
    if(!validateCurrentDate(currentDate)){
      System.out.println("Invalid Current Date");
      return false;
    }
    if(!validatePharmacistName(pharmacistName)){
      System.out.println("Invalid Pharmacist Name");
      return false;
    }
    return true;
  }

  public static boolean validateAddPatient(String name, String prescription, String doctor, String ssn, String birthdate){
    if(!validateName(name)){
      System.out.println("Invalid Name");
      return false;
    }
    if(!validatePrescription(prescription)){
      System.out.println("Invalid Prescription");
      return false;
    }
    if(!validateDoctor(doctor)){
      System.out.println("Invalid Doctor");
      return false;
    }
    if(!validateSSN(ssn)){
      System.out.println("Invalid SSN");
      return false;
    }
    if(!validateBirthdate(birthdate)){
      System.out.println("Invalid Birthdate");
      return false;
    }
    return true;
  }
}

class HidePatientInfo{

  public static void hidePatientInfo(Patient patient){
    String hiddenSSN = "";
    String hiddenBirthdate = "";
    for(int i = 0; i < patient.getSsn().length(); i++){
      if(i > 5){
        hiddenSSN += patient.getSsn().charAt(i);
      }
      else{
        hiddenSSN += "*";
      }
    }
    for(int i = 0; i < patient.getBirthdate().length(); i++){
      if(i < 5){
        hiddenBirthdate += "*";
      }
      else{
        hiddenBirthdate += patient.getBirthdate().charAt(i);
      }
    }
    patient.sethiddenSSN(hiddenSSN);
    patient.sethiddenBirthdate(hiddenBirthdate);
  }

}



