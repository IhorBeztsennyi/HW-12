import java.util.concurrent.*;

public class H2O {

    static H2O h2o = new H2O();

    private static final CyclicBarrier BARRIER = new CyclicBarrier(3, new H2OCreated());
    private final Semaphore twoHydrogen = new Semaphore(2);
    private final Semaphore oneOxygen = new Semaphore(1);

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newScheduledThreadPool(5);

        String molecules = "HHHOOHHHHOOH";
        System.out.println("Input: " + molecules);
        System.out.print("Output: ");

        char[] HAndO = molecules.toCharArray();

        for (Character letter : HAndO) {
            if (letter.equals('H')) {
                threadPool.execute(() -> h2o.releaseHydrogen());
            } else if (letter.equals('O')) {
                threadPool.execute(() -> h2o.releaseOxygen());
            } else {
                System.out.println("It isn't water");
            }
        }
        threadPool.shutdown();
    }

    public void releaseHydrogen() {
        try {
            twoHydrogen.acquire();
            BARRIER.await();
            System.out.print("H");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        } finally {
            twoHydrogen.release();
        }
    }

    public void releaseOxygen() {

        try {
            oneOxygen.acquire();
            BARRIER.await();
            System.out.print("O");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        } finally {
            oneOxygen.release();
        }
    }

    public static class H2OCreated implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.print(" ");
            } catch (InterruptedException e) {
            }
        }
    }
}

