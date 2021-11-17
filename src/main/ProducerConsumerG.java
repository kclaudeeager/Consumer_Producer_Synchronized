package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class ProducerConsumerG extends Application {
    private StackPane bar;
    private Scene scene;

    private Main main;

    int width = 1200;
    int height = 533;

    @Override
    public void start(Stage primaryStage) {
        bar = new StackPane();
        scene = new Scene(bar);

        primaryStage.setScene(scene);
        primaryStage.show();
        updateToProduceEmptyWait();

        main = new Main();
        main.start();

        InvalidationListener listener = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {

                if(Main.producerStatus.getValue() == R.PRODUCER_PRODUCES && Main.consumerStatus.getValue() == R.CONSUMER_CONSUME&& Main.isBufferFull.getValue() == false){
                    updateToProduceEmptyConsume();
                }
                else if(Main.producerStatus.getValue() ==R.PRODUCER_PRODUCES && Main.consumerStatus.getValue() == R.CONSUMER_WAIT&& Main.isBufferFull.getValue() == false){
                    updateToProduceEmptyWait();
                }
                else if(Main.producerStatus.getValue() ==R.PRODUCER_PRODUCES  && Main.isBufferFull.getValue() == true && Main.consumerStatus.getValue() == R.CONSUMER_CONSUME){
                    updateToProduceFullConsume();
                }
                else if(Main.producerStatus.getValue() ==R.PRODUCER_WAIT&& Main.isBufferFull.getValue() == true  && Main.consumerStatus.getValue() == R.CONSUMER_CONSUME){
                    updateToWaitFullConsume();
                }
                else if(Main.producerStatus.getValue() == R.PRODUCER_WAIT && Main.isBufferFull.getValue() == true && Main.consumerStatus.getValue() == R.CONSUMER_CONSUME){
                    updateToNotProduceFullConsume();
                }
                else{
                    return;
                }
            }
        };

        Main.producerStatus.addListener(listener);
        Main.consumerStatus.addListener(listener);
        Main.isBufferFull.addListener(listener);
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void updateToProduceEmptyWait(){
        ImageView produceEmptyWait = new ImageView(new Image("produce_empty_wait.png"));
        produceEmptyWait.setFitHeight(height);
        produceEmptyWait.setFitWidth(width);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bar.getChildren().clear();
                bar.getChildren().add(produceEmptyWait);
            }
        });

    }
    public void updateToProduceEmptyConsume(){
        ImageView produceEmptyWait = new ImageView(new Image("produce_empty_consume.png"));
        produceEmptyWait.setFitHeight(height);
        produceEmptyWait.setFitWidth(width);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bar.getChildren().clear();
                bar.getChildren().add(produceEmptyWait);
            }
        });

    }
    public void updateToProduceFullConsume(){
        ImageView produceEmptyWait = new ImageView(new Image("produce_full_consume.png"));
        produceEmptyWait.setFitHeight(height);
        produceEmptyWait.setFitWidth(width);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bar.getChildren().clear();
                bar.getChildren().add(produceEmptyWait);
            }
        });

    }
    public void updateToWaitFullConsume(){
        ImageView produceEmptyWait = new ImageView(new Image("wait_full_consume.png"));
        produceEmptyWait.setFitHeight(height);
        produceEmptyWait.setFitWidth(width);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bar.getChildren().clear();
                bar.getChildren().add(produceEmptyWait);
            }
        });

    }
    public void updateToNotProduceFullConsume(){
        ImageView produceEmptyWait = new ImageView(new Image("notProduce_full_consume.png"));
        produceEmptyWait.setFitHeight(height);
        produceEmptyWait.setFitWidth(width);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bar.getChildren().clear();
                bar.getChildren().add(produceEmptyWait);
            }
        });
    }
}
