import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    final static int BUILD_TIME = 300;
    final static int NUMBER_OF_CLIENTS = 3;
    static ReentrantLock locker = new ReentrantLock();
    static Condition condition = locker.newCondition();

    public static void main(String[] args) {
        Seller seller = new Seller();

        Runnable runnable = () -> {
            locker.lock();
            String buyerName = Thread.currentThread().getName();
            System.out.println(buyerName + " зашел в автосалон");
            if (seller.getAvailableCars() == 0) {
                try {
                    System.out.println("Нет доступных машин");
                    condition.await();
                } catch (InterruptedException e) {
                    return;
                }
            }
            seller.carSell();
            System.out.println(buyerName + " купил авто");
            locker.unlock();
        };

        for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
            new Thread(runnable, "Покупатель " + i).start();
        }
        new Thread(() -> {
            while (seller.getCarsSold() < NUMBER_OF_CLIENTS) {
                try {
                    Thread.sleep(BUILD_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                locker.lock();
                seller.carBuild();
                condition.signal();
                locker.unlock();
            }

        }).start();
    }
}
