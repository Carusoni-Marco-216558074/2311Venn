package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Main extends Application {

	public void start(Stage primaryStage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			Platform.setImplicitExit(false);

			primaryStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {

					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Exit Venn Software");
					alert.setHeaderText("Are you sure you want exit?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {

						System.exit(0);

					} else {

						t.consume();

					}

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
