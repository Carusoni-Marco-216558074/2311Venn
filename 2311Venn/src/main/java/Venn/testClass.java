package Venn;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class testClass extends Application { 

	public void start(Stage stage) 
	{ 

		Label l = new Label(" "); 

		l.setTextFill(Color.BLACK); 
		l.setFont(new Font("Arial", 30));
		
		
		Slider slider = new Slider(); 
		
		Circle Ven1 = new Circle(100);
		Ven1.setStroke(Color.DARKSLATEBLUE);
		Ven1.setFill(Color.TRANSPARENT);
	
		Circle Ven2 = new Circle(100);
		Ven2.setStroke(Color.DARKRED);
		Ven2.setFill(Color.TRANSPARENT);
		
		slider.setMin(0); 
		slider.setMax(100); 
		slider.setValue(50); 

		slider.setShowTickLabels(true); 
		slider.setShowTickMarks(true); 
		slider.setSnapToTicks(true);
		slider.setBlockIncrement(10); 

		slider.valueProperty().addListener( 
			new ChangeListener<Number>() { 

			public void changed(ObservableValue <? extends Number > 
					observable, Number oldValue, Number newValue) 
			{ 
				Ven1.setRadius(2 * newValue.doubleValue());
				Ven2.setRadius(2 * newValue.doubleValue());

				l.setText("value: " +  newValue.intValue()); 
			} 
		}); 

		VBox root = new VBox(); 

		root.setPadding(new Insets(20)); 
		root.setSpacing(10); 
		root.getChildren().addAll( slider, l, Ven1, Ven2); 

		stage.setTitle("Slider"); 

		Scene scene = new Scene(root, 1920, 1080); 
		stage.setScene(scene); 
		stage.show(); 
	} 

	public static void main(String[] args) 
	{ 

		Application.launch(args); 
	} 
} 
