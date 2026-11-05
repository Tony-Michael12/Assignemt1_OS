
import java.util.Scanner;

public class Hospital {

    public static synchronized void print(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) throws InterruptedException {

        Scanner input = new Scanner(System.in);

        System.out.print("Waiting room capacity: ");
        int capacity = input.nextInt();

        System.out.print("Number of doctors: ");
        int doctorCount = input.nextInt();

        System.out.print("Number of patients: ");
        int patientCount = input.nextInt();

        if (capacity <= 0 || doctorCount <= 0 || patientCount <= 0) {
            System.out.println("All values must be positive");
            input.close();
            return;
        }

        WaitingRoom waitingRoom = new WaitingRoom(capacity);
        TreatmentRooms treatmentRooms = new TreatmentRooms(2);

        Doctor[] doctors = new Doctor[doctorCount];

        for (int i = 0; i < doctorCount; i++) {
            doctors[i] = new Doctor(i + 1, waitingRoom, treatmentRooms);
        }

        Patient[] patients = new Patient[patientCount];

        System.out.println("Patients arriving :");

        for (int i = 0; i < patientCount; i++) {
            patients[i] = new Patient(i + 1, waitingRoom);
            System.out.print("P" + (i + 1) + " ");
        }

        System.out.println();

        for (Doctor doctor : doctors) {
            doctor.start();
        }

        for (Patient patient : patients) {
            patient.start();
            Thread.sleep(200);
        }

        for (Patient patient : patients) {
            patient.join();
        }

        for (int i = 0; i < doctorCount; i++) {
            waitingRoom.produce(new Patient(0, waitingRoom));
        }

        for (Doctor doctor : doctors) {
            doctor.join();
        }

        System.out.println("All patients have been treated");
        System.out.println("Simulation completed");

        input.close();
    }
}