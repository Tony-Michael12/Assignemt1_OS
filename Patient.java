
public class Patient extends Thread {

    private int id;
    private WaitingRoom waitingRoom;
    private Semaphore finished;

    public Patient(int id, WaitingRoom waitingRoom) {
        this.id = id;
        this.waitingRoom = waitingRoom;
        finished = new Semaphore(0);
    }

    public int getPatientId() {
        return id;
    }

    private void enterWaitingRoom() {
        waitingRoom.produce(this);
    }

    public void sendBack() {
        new Thread() {
            public void run() {
                enterWaitingRoom();
            }
        }.start();
    }

    public void treatmentFinished() {
        finished.V();
    }

    @Override
    public void run() {
        System.out.println("P" + id + "arrived");

        enterWaitingRoom();

        finished.P();

        System.out.println("P" + id + "left");
    }
}
