public class ThreadRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Потік " + Thread.currentThread().getId() + " виконується.");
    }
}