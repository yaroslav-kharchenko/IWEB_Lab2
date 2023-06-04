import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreThread implements Runnable {
    private static Semaphore semaphore = new Semaphore(4);
    private static Random random = new Random();
    private String threadName;
    private int t1;
    private int t2;

    public SemaphoreThread(String threadName, int t1, int t2) {
        this.threadName = threadName;
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public void run() {
        try {
            System.out.println(threadName + " намагається отримати семафор.");

            semaphore.acquire();
            System.out.println(threadName + " отримав семафор.");

            int sleepTime = t1 + random.nextInt(t2 - t1);
            System.out.println(threadName + " переходить у неактивний стан на " + sleepTime + " мс.");
            Thread.sleep(sleepTime);

            System.out.println(threadName + " завершив свою роботу.");

            semaphore.release();
            System.out.println(threadName + " звільнив семафор.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}