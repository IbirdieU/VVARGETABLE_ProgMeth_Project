package application;


import gui.MainMenu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

        StackPane root = new StackPane();

        Canvas gameCanvas = new Canvas(800,600);

        MainMenu menupane = new MainMenu();

        root.getChildren().addAll(gameCanvas,menupane);
        Scene scene = new Scene(root,800,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("vvargetable");
        primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
