package main;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    public static Buffer buffer = new Buffer();
    public static StringProperty consumerStatus = new SimpleStringProperty(R.CONSUMER_WAIT);
    public static StringProperty producerStatus = new SimpleStringProperty(R.PRODUCER_PRODUCES);
    public static BooleanProperty isBufferFull = new SimpleBooleanProperty(false);
    public static ProducerConsumerG producerConsumerG;

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
                    int i = buffer.Consume();
                    System.out.println("\t\t\tConsumer consumed " + i);
                    Main.producerConsumerG.changeConsumerMessage("Consumer consumed " + i);
                    Main.consumerStatus.setValue(R.CONSUMER_CONSUME);
                    Thread.sleep((int)((Math.random() * 10000)));
                    Main.consumerStatus.setValue(R.CONSUMER_WAIT);
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
                    Main.producerConsumerG.changeProducerMessage("Producer produces" + i);
                    Main.producerStatus.setValue(R.PRODUCER_PRODUCES);

                    buffer.Produce(i++); // Add a value to the buffer

                    // Put the thread into sleep
                    Thread.sleep((int)((Math.random() * 10000)));
                    Main.producerStatus.setValue(R.PRODUCER_WAIT);
                    Main.isBufferFull.setValue(true);
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
                    Main.producerConsumerG.changeProducerMessage("Wait for notFull condition");

                    Main.producerStatus.setValue(R.PRODUCER_WAIT);
                    Main.isBufferFull.setValue(true);
                    notFull.await();
                    Main.producerStatus.setValue(R.PRODUCER_PRODUCES);
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
                    Main.producerConsumerG.changeConsumerMessage("Wait for notEmpty condition");

                    Main.consumerStatus.setValue(R.CONSUMER_WAIT);

                    notEmpty.await();
                    Main.consumerStatus.setValue(R.CONSUMER_CONSUME);
                    Main.producerStatus.setValue(R.PRODUCER_PRODUCES);
                }
                value = queue.remove();
                if(producerStatus.getValue() == R.PRODUCER_WAIT){
                    Main.isBufferFull.setValue(true);
                }
                else {
                    Main.isBufferFull.setValue(false);
                }

                notFull.signal(); // Signal notFull condition
                Main.producerStatus.setValue(R.PRODUCER_PRODUCES);
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
    public  Main(ProducerConsumerG producerConsumerG){
        this.producerConsumerG = producerConsumerG;

    }
}
