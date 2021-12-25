import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {

    }

    public synchronized void setWinStatus(){
        MainClass.winnerStatus=true;
    }
    public void startRace(Semaphore m) {
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (!MainClass.winnerStatus){
            setWinStatus();
            System.out.println(getName() + " - WINNER!!!");
        }
        MainClass.counterFinishCar++;
        if (MainClass.counterFinishCar==CARS_COUNT){
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        }
    }
    public void prepareToRace(){
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.readyCount++;
            if (MainClass.readyCount==CARS_COUNT){
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}