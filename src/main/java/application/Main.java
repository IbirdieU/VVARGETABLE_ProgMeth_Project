package application;


import gui.IntroMenu;

import gui.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

        StackPane root = new StackPane();



        IntroMenu intro = new IntroMenu();
        MainMenu main = new MainMenu();
        Canvas gameCanvas = new Canvas(800,600); //the hidden game
        main.setVisible(false);
        intro.setOnAction(() -> {
            intro.setVisible(false);
            main.setVisible(true);
        });
        root.getChildren().addAll(gameCanvas,intro,main);
        Scene scene = new Scene(root,1280,720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("vvargetable");
        primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
