package application;

import gui.ControlGridPane;
import gui.ControlPane;
import gui.MineSweeperPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
        HBox root = new HBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.setPrefHeight(400);
        root.setPrefWidth(800);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("vvargetable");
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
