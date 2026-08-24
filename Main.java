package medicarehospital;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        HospitalSystem hospital = new HospitalSystem();

        int choice = -1;

        do {
            System.out.println();
            System.out.println("======================================");
            System.out.println("       MEDICARE HOSPITAL SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Display Ward Beds");
            System.out.println("2. Register Patient");
            System.out.println("3. Search Patient");
            System.out.println("4. Update Patient");
            System.out.println("5. Delete Patient");
            System.out.println("6. Allocate Bed");
            System.out.println("7. Release Bed");
            System.out.println("8. Display Available Beds");
            System.out.println("9. Display Occupied Beds");
            System.out.println("10. Bed Occupancy Report");
            System.out.println("11. Display All Patients");
            System.out.println("12. Sort Patients by Surname");
            System.out.println("13. Sort Patients by Patient ID");
            System.out.println("0. Exit");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                input.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    hospital.displayBeds();
                    break;

                case 2:

                    System.out.println();
                    System.out.println("========== REGISTER PATIENT ==========");

                    System.out.print("Enter Patient ID: ");
                    int patientId = input.nextInt();
                    input.nextLine();

                    if (hospital.searchPatient(patientId) != null) {
                        System.out.println("Patient ID already exists.");
                        break;
                    }

                    System.out.print("Enter First Name: ");
                    String firstName = input.nextLine();

                    System.out.print("Enter Last Name: ");
                    String lastName = input.nextLine();

                    System.out.print("Enter Age: ");
                    int age = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter Gender: ");
                    String gender = input.nextLine();

                    System.out.print("Enter Medical Condition: ");
                    String medicalCondition = input.nextLine();

                    System.out.println();
                    System.out.println("Patient Category:");
                    System.out.println("1. Inpatient");
                    System.out.println("2. Outpatient");
                    System.out.println("3. Emergency");
                    System.out.print("Enter category: ");

                    int categoryChoice = input.nextInt();
                    input.nextLine();

                    PatientCategory category;

                    if (categoryChoice == 1) {
                        category = PatientCategory.INPATIENT;
                    } else if (categoryChoice == 2) {
                        category = PatientCategory.OUTPATIENT;
                    } else if (categoryChoice == 3) {
                        category = PatientCategory.EMERGENCY;
                    } else {
                        System.out.println("Invalid category.");
                        break;
                    }

                    Patient newPatient;

                    if (category == PatientCategory.INPATIENT) {

                        System.out.print("Enter Ward Number: ");
                        String wardNumber = input.nextLine();

                        newPatient = new Inpatient(
                                patientId,
                                firstName,
                                lastName,
                                age,
                                gender,
                                medicalCondition,
                                category,
                                wardNumber,
                                "Not Assigned"
                        );

                    } else {

                        newPatient = new Patient(
                                patientId,
                                firstName,
                                lastName,
                                age,
                                gender,
                                medicalCondition,
                                category
                        );
                    }

                    if (hospital.registerPatient(newPatient)) {
                        System.out.println("Patient registered successfully.");
                    } else {
                        System.out.println("Patient registration failed.");
                    }

                    break;

                case 3:

                    System.out.println();
                    System.out.println("========== SEARCH PATIENT ==========");

                    System.out.print("Enter Patient ID: ");
                    int searchId = input.nextInt();
                    input.nextLine();

                    Patient foundPatient = hospital.searchPatient(searchId);

                    if (foundPatient != null) {
                        foundPatient.displayDetails();
                    } else {
                        System.out.println("Patient not found.");
                    }

                    break;

                case 4:

                    System.out.println();
                    System.out.println("========== UPDATE PATIENT ==========");

                    System.out.print("Enter Patient ID: ");
                    int updateId = input.nextInt();
                    input.nextLine();

                    Patient patientToUpdate = hospital.searchPatient(updateId);

                    if (patientToUpdate == null) {
                        System.out.println("Patient not found.");
                        break;
                    }

                    System.out.print("Enter New First Name: ");
                    String newFirstName = input.nextLine();

                    System.out.print("Enter New Last Name: ");
                    String newLastName = input.nextLine();

                    System.out.print("Enter New Age: ");
                    int newAge = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter New Gender: ");
                    String newGender = input.nextLine();

                    System.out.print("Enter New Medical Condition: ");
                    String newCondition = input.nextLine();

                    System.out.println("Select New Category:");
                    System.out.println("1. Inpatient");
                    System.out.println("2. Outpatient");
                    System.out.println("3. Emergency");
                    System.out.print("Enter category: ");

                    int newCategoryChoice = input.nextInt();
                    input.nextLine();

                    PatientCategory newCategory;

                    if (newCategoryChoice == 1) {
                        newCategory = PatientCategory.INPATIENT;
                    } else if (newCategoryChoice == 2) {
                        newCategory = PatientCategory.OUTPATIENT;
                    } else {
                        newCategory = PatientCategory.EMERGENCY;
                    }

                    if (hospital.updatePatient(
                            updateId,
                            newFirstName,
                            newLastName,
                            newAge,
                            newGender,
                            newCondition,
                            newCategory)) {

                        System.out.println("Patient updated successfully.");

                    } else {
                        System.out.println("Patient update failed.");
                    }

                    break;

                case 5:

                    System.out.println();
                    System.out.println("========== DELETE PATIENT ==========");

                    System.out.print("Enter Patient ID: ");
                    int deleteId = input.nextInt();
                    input.nextLine();

                    if (hospital.deletePatient(deleteId)) {
                        System.out.println("Patient deleted successfully.");
                    } else {
                        System.out.println("Patient not found.");
                    }

                    break;

                case 6:

                    System.out.println();
                    System.out.println("========== ALLOCATE BED ==========");

                    System.out.print("Enter Patient ID: ");
                    int allocatePatientId = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter Bed Number (example B1): ");
                    String bedNumber = input.nextLine();

                    if (hospital.allocateBed(allocatePatientId, bedNumber)) {
                        System.out.println("Bed allocated successfully.");
                    } else {
                        System.out.println("Bed allocation failed.");
                    }

                    break;

                case 7:

                    System.out.println();
                    System.out.println("========== RELEASE BED ==========");

                    System.out.print("Enter Bed Number: ");
                    String releaseBedNumber = input.nextLine();

                    if (hospital.releaseBed(releaseBedNumber)) {
                        System.out.println("Bed released successfully.");
                    } else {
                        System.out.println("Bed is not occupied or does not exist.");
                    }

                    break;

                case 8:
                    hospital.displayAvailableBeds();
                    break;

                case 9:
                    hospital.displayOccupiedBeds();
                    break;

                case 10:
                    hospital.displayBedInformation();
                    break;

                case 11:
                    hospital.displayAllPatients();
                    break;

                case 12:
                    hospital.sortPatientsBySurname();
                    break;

                case 13:
                    hospital.sortPatientsById();
                    break;

                case 0:
                    System.out.println("Thank you for using MediCare Hospital System.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        input.close();
    }
}