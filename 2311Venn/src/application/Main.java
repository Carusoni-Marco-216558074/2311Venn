package application;
	
import com.sun.javafx.geom.Shape;

//Java program to implement Slider Class 
//using ChangeListener 
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SuppressWarnings({ "unused", "restriction" })
public class Main extends Application {

	@SuppressWarnings("static-access")
	public void start(Stage stage) {

		Slider slider = new Slider();

		Circle Ven1 = new Circle(325.2);
		Ven1.setStroke(Color.DARKSLATEBLUE);
		Ven1.setFill(Color.TRANSPARENT);

		Circle Ven2 = new Circle(325.2);
		Ven2.setStroke(Color.DARKRED);
		Ven2.setFill(Color.TRANSPARENT);

		slider.setMin(50);
		slider.setMax(115);
		slider.setValue(81.3);

		slider.setShowTickMarks(true);
		slider.setBlockIncrement(5);

		Insets in = new Insets(-2.1 * 81.3);
		
		HBox levelUp = new HBox(10);
		
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Ven1.setRadius(4 * newValue.doubleValue());
				Ven2.setRadius(4 * newValue.doubleValue());
				
				Insets in = new Insets(-2.1 * newValue.doubleValue());

				
				levelUp.setMargin(Ven1, in);
				levelUp.setMargin(Ven2, in);
				
			}
		});

		BorderPane root = new BorderPane();

		root.setBottom(slider);


		levelUp.setAlignment(Pos.CENTER);

		levelUp.setHgrow(Ven1, Priority.ALWAYS);
		levelUp.setHgrow(Ven2, Priority.ALWAYS);

		levelUp.setMargin(Ven1, in);
		levelUp.setMargin(Ven2, in);

		levelUp.getChildren().addAll(Ven1, Ven2);

		root.setCenter(levelUp);

		stage.setTitle("Slider");

		Scene scene = new Scene(root, 1900, 1000);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {

		Application.launch(args);
	}
}
