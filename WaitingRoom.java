
public class WaitingRoom {

    private int size;
    private Patient store[];
    private int inptr = 0;
    private int outptr = 0;
    private int count = 0;

    private Semaphore spaces;
    private Semaphore elements;
    private Semaphore mutex;

    public WaitingRoom(int size) {
        this.size = size;
        store = new Patient[size];

        spaces = new Semaphore(size);
        elements = new Semaphore(0);
        mutex = new Semaphore(1);
    }


    public void produce(Patient p) {

        if (!spaces.deadline(0)) {
            if (p.getPatientId() != 0) {
                System.out.println("The waiting room is full");
                System.out.println("P" + p.getPatientId()
                        + " is waiting outside for an available seat");
            }

            spaces.P();
        }

        mutex.P();

        store[inptr] = p;
        inptr = (inptr + 1) % size;
        count++;

        if (p.getPatientId() != 0) {
            System.out.println("P" + p.getPatientId()
                    + " entered the waiting room");
        }

        mutex.V();

        elements.V();
    }


    public Patient consume() {

        elements.P();

        mutex.P();

        Patient p = store[outptr];
        outptr = (outptr + 1) % size;
        count--;

        mutex.V();

        spaces.V();

        return p;
    }
}
