
import java.util.Random;

public class Doctor extends Thread {

    private int doctorId;
    private WaitingRoom waitingRoom;
    private TreatmentRooms treatmentRooms;
    private Random random = new Random();

    public Doctor(int doctorId, WaitingRoom waitingRoom,
                  TreatmentRooms treatmentRooms) {

        this.doctorId = doctorId;
        this.waitingRoom = waitingRoom;
        this.treatmentRooms = treatmentRooms;
    }

    @Override
    public void run() {

        while (true) {

            Patient patient = waitingRoom.consume();

            if (patient.getPatientId() == 0)
                break;

            String doctorName = "Doctor " + doctorId;
            String patientName = "P" + patient.getPatientId();

            Hospital.print(doctorName + " is assigned to "
                    + patientName + ".");

            Hospital.print(doctorName
                    + " is trying to acquire a treatment room.");

            if (treatmentRooms.reserveARoom(3000)) {

                Hospital.print(doctorName + " acquired a treatment room.");

                Hospital.print(doctorName + " begins treating "
                        + patientName + ".");

                try {
                    sleep(4000 + random.nextInt(2000));
                } catch (InterruptedException e) {
                }

                Hospital.print(doctorName + " finished treating "
                        + patientName + ".");

                treatmentRooms.leavingRoom();

                Hospital.print(doctorName
                        + " released the treatment room.");

                patient.treatmentFinished();

                Hospital.print(doctorName + " is now available.");

            } else {

                Hospital.print(doctorName
                        + " could not acquire a treatment room.");

                Hospital.print(doctorName
                        + " is sending " + patientName
                        + " back to the waiting room.");

                patient.sendBack();

                Hospital.print(doctorName + " is now available.");
            }
        }
    }
}
