package Application;

import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class objectMaker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void create() {
	}

	public static void createSlider(Slider slide, double scalar) {

		slide.setMin(50 * scalar);
		slide.setMax(100 * scalar);
		slide.setValue(75 * scalar);
		slide.setMaxWidth(200 * scalar);
		slide.setShowTickMarks(true);
		slide.setBlockIncrement(5);

	}

	public static void createSubtitle(TextField txt, Font font) {

		txt.setAlignment(Pos.CENTER);
		txt.setDisable(true);
		txt.setVisible(false);
		txt.setFont(font);
		txt.setBackground(null);

	}

}
