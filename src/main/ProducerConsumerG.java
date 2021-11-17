package main;

import javafx.application.Application;
import javafx.application.Platform;
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

        main = new Main(this);
        main.start();
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
}
