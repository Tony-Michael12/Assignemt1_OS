public class Semaphore {
    protected int value = 0;
    protected int number = 0;
    protected Semaphore() {
        value = 0;
    }

    protected Semaphore(int number) {
        value = number;
    }

    public synchronized void P() {
        value--;
        if (value < 0) {
            while (number == 0)
                try {
                    wait();
                }
                catch (InterruptedException e) {}
            number--;
        }
    }

    public synchronized void V() {
        value++;
        if (value <= 0) {
            number++;
            notify();
        }
    }

    public synchronized boolean deadline(long time) {
        value--;
        if (value >= 0)
            return true;

        long start = System.currentTimeMillis();
        long waited = 0;

        while (number == 0) {
            if (waited >= time) {
                value++;
                return false;
            }
            try {
                wait(time - waited);
            }
            catch (InterruptedException e) {}
            waited = System.currentTimeMillis() - start;
        }

        number--;
        return true;
    }
}