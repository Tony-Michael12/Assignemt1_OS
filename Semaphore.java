
public class Semaphore {
    protected int value = 0;

    protected Semaphore() {
        value = 0;
    }

    protected Semaphore(int number) {
        value = number;
    }

    public synchronized void P(){
        value--;
        if(value<0)
            try {
                wait();
            }
            catch (InterruptedException e){}
    }

    public synchronized void V() {
        value++;

        if (value <= 0)
            notify();
    }

    public synchronized boolean deadline(long time) {
        value--;

        if (value >= 0)
            return true;

        long start = System.currentTimeMillis();
        long remaining = time;

        while (value < 0 && remaining > 0) {
            try {
                wait(remaining);
            } catch (InterruptedException e) {
                if (value < 0) {
                    value++;
                    Thread.currentThread().interrupt();
                    return false;
                }

                Thread.currentThread().interrupt();
                return true;
            }

            long waited = System.currentTimeMillis() - start;
            remaining = time - waited;
        }

        if (value < 0) {
            value++;
            return false;
        }

        return true;
    }
}
