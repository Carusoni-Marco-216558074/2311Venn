package Application;
import javafx.stage.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
public class newPopUp {

	public static void display(String title, String message) {
		
		Stage window = new Stage();
		window.setTitle(title);
		window.setMinWidth(350);
		window.setMinHeight(200);
		
		
		Label label = new Label();
		label.setText(message);
		
		VBox layout = new VBox(35);
		layout.getChildren().addAll(label);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		
	}
}
