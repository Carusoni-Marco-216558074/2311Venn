package Application;
import javafx.stage.*;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
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
	
	public static void displaySearch() {
		Stage window = new Stage();
		
		window.setMinWidth(100);
		window.setMinHeight(100);
		
		
		TextField search = new TextField();
		search.setPromptText("Search");
		search.setFocusTraversable(false);
		search.setMinHeight(15);
		search.setMinWidth(15);
		
		
		HBox layout = new HBox(35);
		layout.setPadding(new Insets(10,10,10,10));
		layout.getChildren().addAll(search);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
