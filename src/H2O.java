import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class H2O {

    static H2O h2o = new H2O();

    private static final CyclicBarrier BARRIER = new CyclicBarrier(3, new H2OCreated());

    public static void main(String[] args) {

        Thread hydrogen1 = new Thread(h2o::releaseHydrogen);
        Thread hydrogen2 = new Thread(h2o::releaseHydrogen);
        Thread oxygen = new Thread(h2o::releaseOxygen);

        hydrogen1.start();
        hydrogen2.start();
        oxygen.start();
    }

    public void releaseHydrogen(){
        while (true){
            System.out.print("H");
            try {
                BARRIER.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public void releaseOxygen(){
        while (true){
            System.out.print("O");
            try {
                BARRIER.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
    public static class H2OCreated implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.print(", ");
            } catch (InterruptedException e) {
            }
        }
    }
}

