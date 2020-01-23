package application;

import com.sun.javafx.geom.Shape;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

//using ChangeListener 
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;

@SuppressWarnings({ "unused", "restriction" })
public class Main extends Application {

	@SuppressWarnings("static-access")
	public void start(Stage stage) {

		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		
		double scalar = (1920 * 1080) / (screenBounds.getMaxX() * screenBounds.getMaxY());
		
		
		BorderPane root = new BorderPane();

		ColorPicker cpV1 = new ColorPicker();
		ColorPicker cpV2 = new ColorPicker();
		
		
		cpV1.setValue(Color.rgb(0, 0, 150, 0.2));
		cpV2.setValue(Color.rgb(150, 0, 0, 0.2));


		Slider slider = new Slider();
		
		Circle Ven1 = new Circle(325 * scalar);//change to screen size		
		Ven1.setStroke(Color.BLACK);
		Ven1.setFill(Color.rgb(0, 0, 150, 0.2));

		Circle Ven2 = new Circle(325 * scalar); //change to screen size
		Ven2.setStroke(Color.BLACK);
		Ven2.setFill(Color.rgb(150, 0, 0, 0.2));

		slider.setMin(50);
		slider.setMax(100);
		slider.setValue(75);
		slider.setMaxWidth(200 * scalar); //change to screen size
		slider.setShowTickMarks(true);
		slider.setBlockIncrement(5);

		TextField Title = new TextField("Insert Title Here");
		
		Font font = new Font("TimesRoman", 36);
		
		
		Title.setFont(font);
		Title.setAlignment(Pos.CENTER);
		Title.setBackground(null);

		VBox TitleBox = new VBox(1);
		TitleBox.getChildren().addAll(Title);
		TitleBox.setAlignment(Pos.BASELINE_CENTER);

		root.setTop(TitleBox);

		Insets in = new Insets(-171 * scalar); //change to screen size

		HBox levelUp = new HBox(10);
		VBox left = new VBox();
		
		left.getChildren().addAll(cpV1, cpV2, slider);
		left.setAlignment(Pos.CENTER_LEFT);

		root.setBottom(left);

		levelUp.setAlignment(Pos.CENTER);
		levelUp.setHgrow(Ven1, Priority.ALWAYS);
		levelUp.setHgrow(Ven2, Priority.ALWAYS);
		levelUp.setMargin(Ven1, in);
		levelUp.setMargin(Ven2, in);

		
		
		
		// listeners

		slider.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Ven1.setRadius(4 * newValue.doubleValue());
				Ven2.setRadius(4 * newValue.doubleValue());

				Insets in = new Insets((-2 * newValue.doubleValue()) * scalar); //change screen size

				levelUp.setMargin(Ven1, in);
				levelUp.setMargin(Ven2, in);

			}
		});
	

		//

		Title.setOnKeyTyped(event -> {
			int maxCharacters = 60;
			if (Title.getText().length() > maxCharacters)
				event.consume();
		});

		//
		// color picker for ven1
		cpV1.valueProperty().addListener((observable, oldValue, newValue) -> {
			
			int red =  	(int) (255 * newValue.getRed());
			int blue =	(int) (255 * newValue.getBlue());
			int green =	(int) (255 * newValue.getGreen());

			Ven1.setFill(Color.rgb(red, green, blue, 0.2));
			
		});
		// color picker for ven2
		cpV2.valueProperty().addListener((observable, oldValue, newValue) -> {

			int red =  	(int) (255 * newValue.getRed());
			int blue =	(int) (255 * newValue.getBlue());
			int green =	(int) (255 * newValue.getGreen());

			Ven2.setFill(Color.rgb(red, green, blue, 0.2));

		});

		
		
		levelUp.getChildren().addAll(Ven1, Ven2);
		root.setCenter(levelUp);
		stage.setTitle("Slider");
		Scene scene = new Scene(root, screenBounds.getMaxX()-(100 * scalar), screenBounds.getMaxY()-(100 * scalar));
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {

		Application.launch(args);
	}
}
