package medicarehospital;

import java.util.ArrayList;
import java.util.Comparator;

public class HospitalSystem {

    private String[][] beds;
    private ArrayList<Patient> patients;

    public HospitalSystem() {

        patients = new ArrayList<>();

        beds = new String[4][5];

        int bedNumber = 1;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                beds[row][column] = "B" + bedNumber;
                bedNumber++;
            }
        }
    }

    // ==================== PATIENT MANAGEMENT ====================

    public boolean registerPatient(Patient patient) {

        if (patient == null) {
            return false;
        }

        if (searchPatient(patient.getPatientId()) != null) {
            return false;
        }

        patients.add(patient);

        return true;
    }

    public Patient searchPatient(int patientId) {

        for (Patient patient : patients) {

            if (patient.getPatientId() == patientId) {
                return patient;
            }
        }

        return null;
    }

    public boolean updatePatient(
            int patientId,
            String firstName,
            String lastName,
            int age,
            String gender,
            String medicalCondition,
            PatientCategory category) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
        patient.setCategory(category);

        return true;
    }

    public boolean deletePatient(int patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patients.remove(patient);

        return true;
    }

    // ==================== DISPLAY PATIENTS ====================

    public void displayAllPatients() {

        if (patients.isEmpty()) {

            System.out.println("No patients are registered.");

            return;
        }

        System.out.println();
        System.out.println("========== REGISTERED PATIENTS ==========");

        for (Patient patient : patients) {

            patient.displayDetails();

            System.out.println("------------------------------------------");
        }
    }

    // ==================== BED MANAGEMENT ====================

    public void displayBeds() {

        System.out.println();
        System.out.println("========== MEDICARE HOSPITAL - WARD BEDS ==========");

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                System.out.print(beds[row][column] + "\t");
            }

            System.out.println();
        }
    }

    public boolean allocateBed(int patientId, String bedNumber) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        // Only inpatients may receive beds
        if (!(patient instanceof Inpatient)) {
            return false;
        }

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                String currentBed = beds[row][column];

                // Check whether the requested bed exists
                if (currentBed.equals(bedNumber)
                        || currentBed.equals(bedNumber + " - Occupied")) {

                    // Bed is already occupied
                    if (currentBed.contains("Occupied")) {
                        return false;
                    }

                    beds[row][column] = bedNumber + " - Occupied";

                    Inpatient inpatient = (Inpatient) patient;

                    inpatient.setBedNumber(bedNumber);

                    return true;
                }
            }
        }

        // Bed does not exist
        return false;
    }

    public boolean releaseBed(String bedNumber) {

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column].equals(
                        bedNumber + " - Occupied")) {

                    beds[row][column] = bedNumber;

                    return true;
                }
            }
        }

        return false;
    }

    // ==================== AVAILABLE BEDS ====================

    public void displayAvailableBeds() {

        int available = 0;

        System.out.println();
        System.out.println("========== AVAILABLE BEDS ==========");

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (!beds[row][column].contains("Occupied")) {

                    System.out.print(beds[row][column] + "\t");

                    available++;
                }
            }
        }

        System.out.println();
        System.out.println("Total Available Beds: " + available);
    }

    // ==================== OCCUPIED BEDS ====================

    public void displayOccupiedBeds() {

        int occupied = 0;

        System.out.println();
        System.out.println("========== OCCUPIED BEDS ==========");

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column].contains("Occupied")) {

                    System.out.print(beds[row][column] + "\t");

                    occupied++;
                }
            }
        }

        if (occupied == 0) {
            System.out.println("No beds are currently occupied.");
        } else {
            System.out.println();
        }

        System.out.println("Total Occupied Beds: " + occupied);
    }

    // ==================== BED REPORT ====================

    public void displayBedInformation() {

        int totalBeds = beds.length * beds[0].length;
        int occupiedBeds = 0;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column].contains("Occupied")) {
                    occupiedBeds++;
                }
            }
        }

        int availableBeds = totalBeds - occupiedBeds;

        double occupancyPercentage =
                ((double) occupiedBeds / totalBeds) * 100;

        System.out.println();
        System.out.println("========== BED OCCUPANCY REPORT ==========");

        System.out.println("Total Beds: " + totalBeds);
        System.out.println("Occupied Beds: " + occupiedBeds);
        System.out.println("Available Beds: " + availableBeds);

        System.out.printf(
                "Ward Occupancy Percentage: %.2f%%%n",
                occupancyPercentage);
    }

    // ==================== SORT BY SURNAME ====================

    public void sortPatientsBySurname() {

        patients.sort(
                Comparator.comparing(
                        Patient::getLastName,
                        String.CASE_INSENSITIVE_ORDER));

        System.out.println();
        System.out.println("========== PATIENTS SORTED BY SURNAME ==========");

        displayAllPatients();
    }

    // ==================== SORT BY PATIENT ID ====================

    public void sortPatientsById() {

        patients.sort(
                Comparator.comparingInt(
                        Patient::getPatientId));

        System.out.println();
        System.out.println("========== PATIENTS SORTED BY PATIENT ID ==========");

        displayAllPatients();
    }
}