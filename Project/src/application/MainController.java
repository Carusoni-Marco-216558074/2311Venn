package application;

import java.awt.AWTException;
import java.awt.Paint;
import java.io.File;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MainController {

	// used for tracking dynamic labels
	public static int counter = 0;

	// used for moving elements to gridpane
	public String text;
	public static Integer colIndex;
	public static Integer rowIndex;

	// refrencing objects from the fxml page via their fx:id
	// do not edit anything without scenebuilder downloaded, and change their id
	// from scenebuilder

	@FXML
	Pane mainPane;
	@FXML
	GridPane WordBox;
	@FXML
	TextField Title;
	@FXML
	TextField NumVen1;
	@FXML
	TextField NumVen2;
	@FXML
	TextField NumVen3;
	@FXML
	TextField subTitle1;
	@FXML
	TextField subTitle2;
	@FXML
	CheckBox chkTitle;
	@FXML
	CheckBox chkSub;
	@FXML
	ColorPicker cpkVen1;
	@FXML
	ColorPicker cpkVen2;
	@FXML
	Circle Ven1;
	@FXML
	Circle Ven2;
	@FXML
	Button input;
	@FXML
	TextField submitText;
	@FXML
	RadioButton radText;
	@FXML
	RadioButton radNum;
	@FXML
	ToggleButton darkToggle;

	// stuff to initialize before the frame is shown (adding listeners, setting
	// defaults)

	public void initialize() {

		Title.setBackground(null);
		Title.addEventFilter(KeyEvent.KEY_TYPED, maxLength(35));

		NumVen1.setBackground(null);
		NumVen2.setBackground(null);
		NumVen3.setBackground(null);

		NumVen1.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
		NumVen2.addEventFilter(KeyEvent.KEY_TYPED, maxLength(5));
		NumVen3.addEventFilter(KeyEvent.KEY_TYPED, maxLength(4));

		subTitle1.setBackground(null);
		subTitle1.addEventFilter(KeyEvent.KEY_TYPED, maxLength(25));

		subTitle2.setBackground(null);
		subTitle2.addEventFilter(KeyEvent.KEY_TYPED, maxLength(25));

		submitText.addEventFilter(KeyEvent.KEY_TYPED, maxLength(25));

		cpkVen1.setValue((Color) Ven1.getFill());
		cpkVen2.setValue((Color) Ven2.getFill());

		cpkVen1.getStyleClass().add("split-button");
		cpkVen2.getStyleClass().add("split-button");
	}

	// shows/hides titles

	@FXML
	private void chkTitleEvnt() {

		Title.setDisable(!chkTitle.isSelected());
		Title.setVisible(chkTitle.isSelected());
		Title.setText("");
		Title.setPromptText("Insert Title Here");

	}

	// shows/hides subtitles

	@FXML
	private void chkSubEvnt() {

		subTitle1.setDisable(!chkSub.isSelected());
		subTitle1.setVisible(chkSub.isSelected());
		subTitle1.setText("");
		subTitle1.setPromptText("Insert Title Here");

		subTitle2.setDisable(!chkSub.isSelected());
		subTitle2.setVisible(chkSub.isSelected());
		subTitle2.setText("");
		subTitle2.setPromptText("Insert Title Here");
	}

	// color picker for ven1(left)

	@FXML
	private void cpkVen1Evnt() {

		Color colour = cpkVen1.getValue();
		colour = Color.color(colour.getRed(), colour.getGreen(), colour.getBlue(), 0.5);
		Ven1.setFill(colour);

	}

	// color picker for ven2(right)

	@FXML
	private void cpkVen2Evnt() {

		Color colour = cpkVen2.getValue();
		colour = Color.color(colour.getRed(), colour.getGreen(), colour.getBlue(), 0.5);
		Ven2.setFill(colour);

	}

	// method is called when a new label is created, all have incremented id's

	@FXML
	private void enterWordEvnt(KeyEvent e) throws IOException {

		if (e.getCode().toString() == "ENTER") {
			// each new lbl object has an incremented id
			Label lbl = new Label(submitText.getText());
			lbl.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672); -fx-font-size:14px;");
			lbl.setId("" + (counter++));
			lbl.addEventFilter(MouseEvent.MOUSE_DRAGGED, drag(counter));
			WordBox.add(lbl, 11, counter - 1);

			submitText.setText("");

		}

	}

	// used for showing/reseting the textfields for only separating numbers

	@FXML
	private void radNumClick() {

		submitText.setDisable(true);

		NumVen1.setVisible(true);
		NumVen1.setDisable(false);

		NumVen2.setVisible(true);
		NumVen2.setDisable(false);

		NumVen3.setVisible(true);
		NumVen3.setDisable(false);

		NumVen1.setText("");
		NumVen1.setPromptText("#");

		NumVen2.setText("");
		NumVen2.setPromptText("#");

		NumVen3.setText("");
		NumVen3.setPromptText("#");
	}

	// used for hiding the textfields for only separating numbers

	@FXML
	private void radTextClick() {

		submitText.setDisable(false);

		NumVen1.setVisible(false);
		NumVen1.setDisable(true);
		NumVen2.setVisible(false);
		NumVen2.setDisable(true);
		NumVen3.setVisible(false);
		NumVen3.setDisable(true);

	}

	@FXML
	private void darkToggleEvnt() {

		if (darkToggle.isSelected() == true) {
			// dark mode

			Title.setStyle("-fx-text-inner-color: white;");
			NumVen1.setStyle("-fx-text-inner-color: white;");
			NumVen2.setStyle("-fx-text-inner-color: white;");

			mainPane.setBackground(
					new Background(new BackgroundFill(Color.web("#333333"), CornerRadii.EMPTY, Insets.EMPTY)));

		}

		else {
			// light mode

			Title.setStyle("-fx-text-inner-color: black;");
			NumVen1.setStyle("-fx-text-inner-color: black;");
			NumVen2.setStyle("-fx-text-inner-color: black;");
			
			mainPane.setBackground(
					new Background(new BackgroundFill(Color.web("#f2f2f2"), CornerRadii.EMPTY, Insets.EMPTY)));

		}

	}

	// not complete, supposed to take screenshot of just center pane, or just the
	// window if too hard

	@FXML
	private void screenshot() throws AWTException, IOException {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("png file", "png"));

		if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

			String filename = fileChooser.getSelectedFile().toString();
			if (!filename.endsWith(".png"))
				filename += ".png";

			File file = fileChooser.getSelectedFile();

			// take screenshot here, using above file as the name/extension. then use an
			// image writer to actually save the data

		}

	}

	// event handlers

	// used for setting the max length a textfield can be
	public EventHandler<KeyEvent> maxLength(final Integer len) {
		return new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {

				TextField tx = (TextField) arg0.getSource();
				if (tx.getText().length() >= len) {
					arg0.consume();
				}
			}
		};
	}

	// need a listener for TextBox that will check where the mouse releases a drag
	// use above listener to get coordinates in grid
	// call method to make identical label in grid
	// ??delete previous label??

	// used for checking which lbl is being dragged
	public EventHandler<MouseEvent> drag(final int id) {

		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// drag event on lbl.id
				Label lbl = (Label) arg0.getSource(); // this specifies which label is being dragged
				System.out.println("dragged: " + lbl.getId());

			}
		};

	}

}