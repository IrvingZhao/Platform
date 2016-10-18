import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by irving on 2016/10/17.
 */
public class Main {
    public static void main(String[] args) {
        TestLock testLock = new TestLock();
        testLock.refresh();
        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println("execMinute:1:" + i);
                    testLock.minute();
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("finish:1");
        });
        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    System.out.println("execMinute:2:" + i);
                    testLock.minute();
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("finish:2");
        });
        thread1.start();
        thread2.start();
    }

    static class TestLock {

        Lock testLock = new ReentrantLock(true);
        Condition refresh = testLock.newCondition();

        int count = 10;
        boolean ifRefresh = false;
        int time = 0;

        public void refresh() {
            testLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + ":refresh");
                count = 11;
                Thread.sleep(100);
//                refresh.signal();
                refresh.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                ifRefresh = false;
                testLock.unlock();
                System.out.println("unlock");
            }
        }

        public void minute() {
            System.out.println(Thread.currentThread().getName() + ":begin:Minute:" + count + ":" + (++time));
            testLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + ":entry:Minute:" + count + ":" + time);
                if (count == 0) {
                    if (!ifRefresh) {
                        ifRefresh = true;
                        new Thread(this::refresh).start();
//                        refresh();
                    }
                    System.out.println(Thread.currentThread().getName() + ":await:Minute:" + count + ":" + time);
                    refresh.await();
                }
                count--;
                System.out.println(Thread.currentThread().getName() + ":exit:Minute:" + count + ":" + time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                testLock.unlock();
                System.out.println(Thread.currentThread().getName() + ":exit:unlock:" + count + ":" + time);
            }
        }
    }

}
