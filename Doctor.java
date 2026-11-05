import java.util.Random;

public class Doctor extends Thread {
    private int doctor_Id;
    private WaitingRoom w_Room;
    private TreatmentRooms t_Rooms;
    private Random rand = new Random();

    public Doctor(int doctorId, WaitingRoom waitingRoom, TreatmentRooms treatmentRooms) {
        this.doctor_Id = doctorId;
        this.w_Room = waitingRoom;
        this.t_Rooms = treatmentRooms;
    }

    @Override
    public void run(){

        while (true){

            Patient p = w_Room.consume();

            if (p.getPatientId() == 0) {
                break;
            }

            String dn = "Doctor " + doctor_Id;
            String pn = "P" + p.getPatientId();

            System.out.println(dn+ " is assigned to " + pn + ".");
            System.out.println(dn+ " is trying to acquire a treatment room.");

            if (t_Rooms.reserveARoom(3000)) {

                System.out.println(dn + "acquired a treatment room.");
                System.out.println(dn + "begins treating " + pn + ".");

                try {
                    sleep(1000 + rand.nextInt(4000));
                }
                catch (InterruptedException e) {}

                System.out.println(dn + " finished treating " + pn + ".");

                t_Rooms.leavingRoom();
                System.out.println(dn + " released the treatment room.");

                p.treatmentFinished();
                System.out.println(dn + " is now available.");

            }
            else {

                System.out.println(dn + " could not acquire a treatment room.");
                System.out.println(dn + " is sending " + pn + " back to the waiting room.");

                p.sendBack();

                System.out.println(dn + " is now available.");
            }
        }

    }
}