package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Kwizera
 */
public class Main extends Thread{
    ProducerConsumerG design;
    public static Buffer buffer = new Buffer();
    public static StringProperty gStatus = new SimpleStringProperty("");


    @Override
    public void run() {
        // Create a thread pool with two threads
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute((Runnable) new ProducerTask());
        executor.execute(new ConsumerTask());
        executor.shutdown();
    }
    static class ConsumerTask implements Runnable {

        public void run() {
            try {
                while (true) {
                    System.out.println("\t\t\tConsumer consumed " + buffer.Consume());
                    Thread.sleep((int)(Math.random() * 10000));
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    static class ProducerTask implements Runnable {

        int i = 1;
        public void run() {
            try {

                while (true) {
                    System.out.println("Producer produces " + i);

                    buffer.Produce(i++); // Add a value to the buffer

                    // Put the thread into sleep
                    Thread.sleep((int)(Math.random() * 10000));
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }




    }

    // A task for adding an int to the buffer
    static class Buffer {
        private static final int CAPACITY = 1; // buffer size
        private java.util.LinkedList<Integer> queue = new java.util.LinkedList<>();



        // Create a new lock
        private static Lock lock = new ReentrantLock();

        // Create two conditions
        private static Condition notEmpty = lock.newCondition();
        private static Condition notFull = lock.newCondition();

        public void Produce(int value) {
            lock.lock(); // Acquire the lock
            try {

                while (queue.size() == CAPACITY) {
                    System.out.println("Wait for notFull condition");
                    notFull.await();
                }


                queue.offer(value);
                notEmpty.signal(); // Signal notEmpty condition
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            finally {
                lock.unlock(); // Release the lock
            }
        }

        public int Consume() {
            int value = 0;
            lock.lock(); // Acquire the lock
            try {
                while (queue.isEmpty()) {

                    System.out.println("\t\t\tWait for notEmpty condition");

                    notEmpty.await();
                }
                value = queue.remove();

                notFull.signal(); // Signal notFull condition
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            finally {
                lock.unlock(); // Release the lock
                return value;
            }
        }
    }
    public  Main(ProducerConsumerG design){
        this.design = design;

    }
}
