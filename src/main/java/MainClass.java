import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static int readyCount=0;
    public static boolean winnerStatus = false;
    public static int counterFinishCar = 0;
    final static Semaphore smp = new Semaphore(2);

    final static CyclicBarrier cb1 = new CyclicBarrier(CARS_COUNT);
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            final int w=i;
            new Thread(()->{
                cars[w].prepareToRace();
                try {
                    cb1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                cars[w].startRace(smp);
            }).start();
        }
    }
}
