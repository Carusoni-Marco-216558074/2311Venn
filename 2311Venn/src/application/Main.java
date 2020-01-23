package application;

import com.sun.javafx.geom.Shape;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

//using ChangeListener 
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.awt.font.TextAttribute;

@SuppressWarnings({ "unused", "restriction" })
public class Main extends Application {

	@SuppressWarnings("static-access")
	public void start(Stage stage) {

		Rectangle2D screenBounds = Screen.getPrimary().getBounds();

		double scalar = 1 / ((1920 * 1080) / (screenBounds.getMaxX() * screenBounds.getMaxY()));

		BorderPane root = new BorderPane();
		
		ColorPicker cpV1 = new ColorPicker();
		ColorPicker cpV2 = new ColorPicker();

		cpV1.setValue(Color.rgb(0, 0, 150, 0.2));
		cpV2.setValue(Color.rgb(150, 0, 0, 0.2));

		Slider slider = new Slider();
		slider.setMin(50 * scalar);
		slider.setMax(100 * scalar);
		slider.setValue(75 * scalar);
		slider.setMaxWidth(200 * scalar); // change to screen size
		slider.setShowTickMarks(true);
		slider.setBlockIncrement(5);

		Circle Ven1 = new Circle(4 * slider.getValue());// change to screen size
		Ven1.setStroke(Color.BLACK);
		Ven1.setFill(Color.rgb(0, 0, 150, 0.2));

		Circle Ven2 = new Circle(4 * slider.getValue()); // change to screen size
		Ven2.setStroke(Color.BLACK);
		Ven2.setFill(Color.rgb(150, 0, 0, 0.2));

		CheckBox chk1 = new CheckBox("Subtitles");
		chk1.setFont(new Font("Arial", Ven1.getRadius() / (scalar * 13)));

		HBox subTitle = new HBox();
		subTitle.setAlignment(Pos.TOP_CENTER);
		TextField ven1Title = new TextField("Insert Title Here");
		TextField ven2Title = new TextField("Insert Title Here");
		ven1Title.setAlignment(Pos.CENTER);
		ven2Title.setAlignment(Pos.CENTER);
		ven1Title.setDisable(true);
		ven2Title.setDisable(true);
		ven1Title.setVisible(false);
		ven2Title.setVisible(false);

		Font subTitleFont = new Font("Arial Bold", Ven1.getRadius() / (scalar * 13));
		ven1Title.setFont(subTitleFont);
		ven2Title.setFont(subTitleFont);
		ven1Title.setBackground(null);
		ven2Title.setBackground(null);
		subTitle.getChildren().addAll(ven1Title, ven2Title);
		subTitle.setTranslateY(-(Ven1.getRadius() * 1.25));
		ven1Title.setTranslateX(-(Ven1.getRadius() * 0.35));
		ven2Title.setTranslateX(Ven1.getRadius() * 0.35);

		TextField Title = new TextField("Insert Title Here");

		Font Titlefont = new Font("Arial Bold", 36);

		Title.setFont(Titlefont);
		Title.setAlignment(Pos.CENTER);
		Title.setBackground(null);
		VBox TitleBox = new VBox(1);
		TitleBox.getChildren().addAll(Title);
		TitleBox.setAlignment(Pos.BASELINE_CENTER);
		TitleBox.setTranslateY(-(30 * scalar));
		root.setTop(TitleBox);

		Insets in = new Insets(-(151 * scalar));
		HBox levelUp = new HBox(10);
		VBox left = new VBox();
		left.getChildren().addAll(chk1, cpV1, cpV2, slider);
		left.setAlignment(Pos.CENTER_LEFT);
		root.setBottom(left);
		levelUp.setHgrow(Ven1, Priority.ALWAYS);
		levelUp.setHgrow(Ven2, Priority.ALWAYS);
		levelUp.setMargin(Ven1, in);
		levelUp.setMargin(Ven2, in);
		VBox centerBox = new VBox();
		centerBox.setAlignment(Pos.CENTER);
		levelUp.setAlignment(Pos.BOTTOM_CENTER);
		levelUp.getChildren().addAll(Ven1, Ven2);
		centerBox.getChildren().addAll(levelUp, subTitle);
		root.setCenter(centerBox);

		// listeners

		slider.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Ven1.setRadius(4 * newValue.doubleValue());
				Ven2.setRadius(4 * newValue.doubleValue());

				Insets in = new Insets((-2 * newValue.doubleValue())); // change screen size

				levelUp.setMargin(Ven1, in);
				levelUp.setMargin(Ven2, in);

				subTitle.setTranslateY(-(Ven1.getRadius() * 1.25));
				ven1Title.setTranslateX(-(Ven1.getRadius() * 0.35));
				ven2Title.setTranslateX(Ven1.getRadius() * 0.35);

				Font changedSubFont = new Font("Arial Bold", Ven1.getRadius() / (scalar * 13));

				ven1Title.setFont(changedSubFont);
				ven2Title.setFont(changedSubFont);

			}
		});
//

		ven1Title.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			
			@Override
			public void handle(MouseEvent arg0) {
				
				if (ven1Title.getText().contains("Insert"))
					ven1Title.setText("");
			}

		});

//

		ven2Title.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				
				if (ven2Title.getText().contains("Insert"))
					ven2Title.setText("");
					

			}

		});

//

		Title.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				if (Title.getText().contains("Insert")) {
					Title.setText("");

				}
			}

		});

//
		chk1.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				ven1Title.setDisable(!newValue);
				ven2Title.setDisable(!newValue);
				ven1Title.setVisible(newValue);
				ven2Title.setVisible(newValue);

				ven1Title.setText("Insert Title Here");
				ven2Title.setText("Insert Title Here");

			}
		});

//
		ven1Title.setOnKeyTyped(event -> {
			int maxCharacters = 25;
			if (ven1Title.getText().length() > maxCharacters)
				event.consume();
		});
//

		ven2Title.setOnKeyTyped(event -> {
			int maxCharacters = 25;
			if (ven2Title.getText().length() > maxCharacters)
				event.consume();
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

			int red = (int) (255 * newValue.getRed());
			int blue = (int) (255 * newValue.getBlue());
			int green = (int) (255 * newValue.getGreen());

			Ven1.setFill(Color.rgb(red, green, blue, 0.2));

		});
		// color picker for ven2
		cpV2.valueProperty().addListener((observable, oldValue, newValue) -> {

			int red = (int) (255 * newValue.getRed());
			int blue = (int) (255 * newValue.getBlue());
			int green = (int) (255 * newValue.getGreen());

			Ven2.setFill(Color.rgb(red, green, blue, 0.2));

		});

		stage.setTitle("Slider");
		Scene scene = new Scene(root, screenBounds.getMaxX() - (100 * scalar), screenBounds.getMaxY() - (100 * scalar));
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {

		Application.launch(args);
	}
}
