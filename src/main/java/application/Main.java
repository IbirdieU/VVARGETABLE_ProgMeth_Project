package application;


import gui.HowToMenu;
import gui.IntroMenu;

import gui.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.GameManager;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

        StackPane root = new StackPane();
        Scene scene = new Scene(root,1280,720);

        GameManager gameManager = new GameManager(root);
        gameManager.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("vvargetable");
        primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
