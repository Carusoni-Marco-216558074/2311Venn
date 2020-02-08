package Application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class confirmBox {

	static boolean answer;

	public static boolean display(String title, String message) {

		Stage window = new Stage();
		window.setTitle(title);
		window.setMinWidth(350);
		window.setMinHeight(250);

		Label label = new Label();
		label.setText(message);

		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");

		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});

		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});

		VBox layout = new VBox(35);
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		return answer;
	}

}
