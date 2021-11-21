package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ProducerConsumerG extends Application {
    private StackPane bar;
    private Scene scene;

    private Main main;
    private Label producerLabel = new Label("producer");
    private Label consumerLabel = new Label("consumerLabel");
    private StackPane gPane ;

    int width = 1200;
    int height = 533;

    @Override
    public void start(Stage primaryStage) {
        bar = new StackPane();
        gPane = new StackPane();
        bar.getChildren().addAll(producerLabel, consumerLabel);

        StackPane pPane = new StackPane(producerLabel);
        pPane.setAlignment(Pos.TOP_LEFT);
        pPane.setPadding(new Insets(0,0,0,20));
        producerLabel.setFont(Font.font(20));
        StackPane cPane = new StackPane(consumerLabel);
        consumerLabel.setFont(Font.font(20));
        cPane.setAlignment(Pos.TOP_RIGHT);

        cPane.setPadding(new Insets(0,20,0,0));
        bar.setAlignment(Pos.CENTER);
        gPane.getChildren().addAll(bar, pPane, cPane);
        scene = new Scene(gPane, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
        updateToProduceEmptyWait();

        main = new Main(this);
        main.start();

        InvalidationListener listener = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {

                if(Main.producerStatus.getValue() == R.PRODUCER_PRODUCES && Main.consumerStatus.getValue() == R.CONSUMER_CONSUME&& Main.isBufferFull.getValue() == false){
                    updateToProduceEmptyConsume(); // this calls the function to ulstrate  the image of producer who is producing while the buffer is empty and consumer consuming

                }
                else if(Main.producerStatus.getValue() ==R.PRODUCER_PRODUCES && Main.consumerStatus.getValue() == R.CONSUMER_WAIT&& Main.isBufferFull.getValue() == false){
                    updateToProduceEmptyWait();// this calls the function to shows the image of producer who is producing while the buffer is empty and consumer waiting

                }
                else if(Main.producerStatus.getValue() ==R.PRODUCER_PRODUCES  && Main.isBufferFull.getValue() == true && Main.consumerStatus.getValue() == R.CONSUMER_CONSUME){
                    updateToProduceFullConsume(); // this calls the function to shows the image of producer who is producing while the buffer is full and consumer consuming

                }
                else if(Main.producerStatus.getValue() ==R.PRODUCER_WAIT&& Main.isBufferFull.getValue() == true  && Main.consumerStatus.getValue() == R.CONSUMER_CONSUME){
                    updateToWaitFullConsume(); // this calls the function to shows  the image of producer who is waiting while the buffer is full and consumer consuming

                }
                else if(Main.producerStatus.getValue() == R.PRODUCER_WAIT && Main.isBufferFull.getValue() == true && Main.consumerStatus.getValue() == R.CONSUMER_CONSUME){
                    updateToNotProduceFullConsume(); // this calls the function to shows  the image of producer who stopped producing while the buffer is full and consumer consuming

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
    }//main function

    /* These are the functions that updates the views in our layout by changing the shown images*/


    public void updateToProduceEmptyWait(){
        ImageView produceEmptyWait = new ImageView(new Image("images/produce_empty_wait.png"));
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
        ImageView produceEmptyWait = new ImageView(new Image("images/produce_empty_consume.png"));
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
        ImageView produceEmptyWait = new ImageView(new Image("images/produce_full_consume.png"));
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
        ImageView produceEmptyWait = new ImageView(new Image("images/wait_full_consume.png"));
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
        ImageView produceEmptyWait = new ImageView(new Image("images/notProduce_full_consume.png"));
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
    /*ENd of the views updaters*/


    //Function that updates the consumer label to match with how the stuation is
    public void changeConsumerMessage(String newMessage){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                consumerLabel.setText(newMessage);
            }
        });
    }
    //Function that updates the producer label to match with how the stuation is
    public void changeProducerMessage(String newMessage){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                producerLabel.setText(newMessage);
            }
        });
    }
}
