package medicarehospital;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalSystemTest {

    @Test
    public void testRegisterPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                101,
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        assertTrue(hospital.registerPatient(patient));
    }

    @Test
    public void testDuplicatePatientId() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient1 = new Patient(
                101,
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                101,
                "Jane",
                "Brown",
                30,
                "Female",
                "Asthma",
                PatientCategory.OUTPATIENT
        );

        assertTrue(hospital.registerPatient(patient1));
        assertFalse(hospital.registerPatient(patient2));
    }

    @Test
    public void testSearchPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                102,
                "Sarah",
                "Jones",
                30,
                "Female",
                "Asthma",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        Patient result = hospital.searchPatient(102);

        assertNotNull(result);
        assertEquals("Sarah", result.getFirstName());
    }

    @Test
    public void testSearchMissingPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient result = hospital.searchPatient(999);

        assertNull(result);
    }

    @Test
    public void testDeletePatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                103,
                "David",
                "Brown",
                40,
                "Male",
                "Injury",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.deletePatient(103));
        assertNull(hospital.searchPatient(103));
    }

    @Test
    public void testAllocateBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient = new Inpatient(
                104,
                "Michael",
                "Smith",
                45,
                "Male",
                "Injury",
                PatientCategory.INPATIENT,
                "1",
                "Not Assigned"
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.allocateBed(104, "B1"));
        assertEquals("B1", patient.getBedNumber());
    }

    @Test
    public void testCannotAllocateOccupiedBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient1 = new Inpatient(
                105,
                "Peter",
                "White",
                50,
                "Male",
                "Injury",
                PatientCategory.INPATIENT,
                "1",
                "Not Assigned"
        );

        Inpatient patient2 = new Inpatient(
                106,
                "Mary",
                "Black",
                35,
                "Female",
                "Illness",
                PatientCategory.INPATIENT,
                "1",
                "Not Assigned"
        );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        assertTrue(hospital.allocateBed(105, "B2"));
        assertFalse(hospital.allocateBed(106, "B2"));
    }

    @Test
    public void testOutpatientCannotGetBed() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                107,
                "Lisa",
                "Green",
                28,
                "Female",
                "Checkup",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        assertFalse(hospital.allocateBed(107, "B3"));
    }

    @Test
    public void testReleaseBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient = new Inpatient(
                108,
                "James",
                "King",
                42,
                "Male",
                "Injury",
                PatientCategory.INPATIENT,
                "1",
                "Not Assigned"
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.allocateBed(108, "B4"));
        assertTrue(hospital.releaseBed("B4"));
    }
}