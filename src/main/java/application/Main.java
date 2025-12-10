package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.GameManager;
import logic.managers.SoundManager;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1280, 720);

        GameManager gameManager = new GameManager(root);
        gameManager.start();

        SoundManager.playBackgroundMusic();

        primaryStage.setScene(scene);
        primaryStage.setTitle("vvargetable");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
