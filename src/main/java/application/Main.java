package application;


import gui.HowToMenu;
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
        HowToMenu howTo = new HowToMenu();
        Canvas gameCanvas = new Canvas(800,600); //the hidden game

        gameCanvas.setVisible(false);
        main.setVisible(false);
        howTo.setVisible(false);
        intro.setVisible(true);

        intro.setOnAction(() -> {
            intro.setVisible(false);
            main.setVisible(true);
        });

        main.setOnHowToAction(() -> {
            main.setVisible(false);
            howTo.setVisible(true);
        });

        /// Show gameCanvas
        /*
        main.setOnStartAction(() -> {
            main.setVisible(false);
            gameCanvas.setVisible(true);
            // startGame(); //Something like this
        });

         */

        root.getChildren().addAll(gameCanvas, howTo,intro,main);
        Scene scene = new Scene(root,1280,720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("vvargetable");
        primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
