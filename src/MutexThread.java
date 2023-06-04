import java.util.Random;
import java.util.concurrent.Semaphore;

public class MutexThread implements Runnable {
    private static Semaphore mutex = new Semaphore(1);
    private static Random random = new Random();
    private String threadName;
    private int t1;
    private int t2;

    public MutexThread(String threadName, int t1, int t2) {
        this.threadName = threadName;
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public void run() {
        try {
            System.out.println(threadName + " очікує доступу до м'ютексу.");

            mutex.acquire();
            System.out.println(threadName + " захопив м'ютекс.");

            int sleepTime = t1 + random.nextInt(t2 - t1);
            System.out.println(threadName + " переходить у неактивний стан на " + sleepTime + " мс.");
            Thread.sleep(sleepTime);

            System.out.println(threadName + " звільнив м'ютекс.");
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}