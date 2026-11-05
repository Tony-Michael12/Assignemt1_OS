
public class Patient extends Thread {

    private int id;
    private WaitingRoom waiting_room;
    private Semaphore finished;

    public Patient(int id, WaitingRoom waitingRoom) {
        this.id = id;
        this.waiting_room = waitingRoom;
        finished = new Semaphore(0);
    }

    public int getPatientId() {
        return id;
    }

    private void enterWaitingRoom() {
        waiting_room.produce(this);
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
        System.out.println("P" + id + " arrived");

        enterWaitingRoom();

        finished.P();

        System.out.println("P" + id + " left");
    }
}
