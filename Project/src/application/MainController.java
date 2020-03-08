package application;

import java.awt.AWTException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainController {
	final ContextMenu contextMenu = new ContextMenu();
	MenuItem edit = new MenuItem("Edit");
	MenuItem delete = new MenuItem("Delete");
	TextField editText = new TextField();
	boolean selectionMode = false;
	ArrayList<Object> listOfText = new ArrayList<Object>();
	ArrayList[][] textGrid = new ArrayList[100][3];
	// used for tracking dynamic labels
	public static int counter = 0;

	public static int lastDragged;
	public static boolean dragged;
	public static String lastDraggedText;
	public Object lastSelectedText;
	public static boolean toDelete = true;
	// used for moving elements to gridpane
	public String text;
	public static Integer colIndex;
	public static Integer rowIndex;
	public static int c;

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
	@FXML
	MenuItem about;
	@FXML
	Label selectionModeLabel;

	// initializing elements before the frame is shown (adding filters, setting
	// defaults)

	public void initialize() {

		int numCols = 7;
		int numRows = 17;

		for (int i = 0; i < numCols; i++) {
			ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setHgrow(Priority.SOMETIMES);
			WordBox.getColumnConstraints().add(colConstraints);
		}

		for (int i = 0; i < numRows; i++) {
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setVgrow(Priority.SOMETIMES);
			WordBox.getRowConstraints().add(rowConstraints);
		}

		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				if (i != 2 && i != 4)
					addPane(i, j);
			}
		}

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

		// editWindow();
		contextMenu.getItems().addAll(edit, delete);
		contextMenu.setStyle("-fx-font-size:14px;");
		addKeyEvent();
	}

	private void addPane(int colIndex, int rowIndex) {
		Pane pane = new Pane();

		pane.setOnMouseEntered(e -> {

			if (dragged == true) {

				draggedObj(colIndex, rowIndex);
			}

			dragged = false;
		});

		WordBox.add(pane, colIndex, rowIndex);

	}

	// create edit scene
	public void editWindow() {

		VBox v = new VBox(5);
		HBox h = new HBox(2);
		Scene popupScene;
		Label l = new Label("Edit text element(s):");
		Label l2 = new Label("Edit font size:");
		Label l3 = new Label("change background color:");
		ColorPicker cpk = new ColorPicker();
		CheckBox chkBox = new CheckBox("Apply color change:");
		TextField fontSize = new TextField("14");
		Button doneEditButton = new Button("Done");
		Button cancelEditButton = new Button("Cancel");
		h.getChildren().addAll(doneEditButton, cancelEditButton);
		fontSize.addEventFilter(KeyEvent.KEY_TYPED, maxLength(2));
		fontSize.addEventFilter(KeyEvent.KEY_TYPED, onlyNumber());
		editText.setText(((Label) lastSelectedText).getText());
		editText.addEventFilter(KeyEvent.KEY_TYPED, maxLength(25));

		if (selectionMode) {
			if (listOfText.size() == 1)

				v.getChildren().addAll(l, l2, fontSize, l3, cpk, chkBox, new Label("Enter new text here:"), editText,
						h);

			else
				v.getChildren().addAll(l, l2, fontSize, l3, cpk, chkBox, h);
			popupScene = new Scene(v, 200, 250);
		} else {

			v.getChildren().addAll(l, l2, fontSize, l3, cpk, chkBox, new Label("Enter new text here:"), editText, h);
			popupScene = new Scene(v, 200, 250);
		}

		Stage stage = new Stage();
		stage.setScene(popupScene);

		doneEditButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				if (Integer.parseInt(fontSize.getText()) < 0 || Integer.parseInt(fontSize.getText()) > 14) {
					Alert alert = new Alert(AlertType.CONFIRMATION);

					alert.setHeaderText("Please select a font size between 0 and 14.");
					alert.show();
					return;
				}

				if (listOfText.size() == 0)
					listOfText.add(lastSelectedText);
				for (int i = 0; i < listOfText.size(); i++) {

					((Label) listOfText.get(i))
							.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, null, null, null)));
					((Label) listOfText.get(i))
							.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672); -fx-font-size:"
									+ fontSize.getText() + "px;");

					if (chkBox.isSelected()) {
						// ((Label)listOfText.get(i)).setTextFill(cpk.getValue());
						((Label) listOfText.get(i))
								.setBackground(new Background(new BackgroundFill(cpk.getValue(), null, null)));
					}

				}
				((Label) listOfText.get(0)).setText(editText.getText());

				listOfText.clear();
				if (selectionMode) {
					showSelectionModeLabel();
					selectionMode = false;
				}
				stage.close();

			}
		});

		cancelEditButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
			}
		});

		editText.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {

				((Label) lastSelectedText).setText(editText.getText());
				stage.close();
			}
		});

		edit.setOnAction((event) -> {

			stage.show();

		});

		delete.setOnAction((event) -> {
			if (!selectionMode)
				WordBox.getChildren().remove(lastSelectedText);
			else {
				for (int i = 0; i < listOfText.size(); i++)
					WordBox.getChildren().remove(((Label) listOfText.get(i)));
				listOfText.clear();

			}

		});

	}

	// shows/hides titles

	@FXML
	private void showSelectionModeLabel() {
		if (selectionModeLabel.isDisabled())
			selectionModeLabel.setDisable(false);
		else
			selectionModeLabel.setDisable(true);

	}

	@FXML
	private void aboutMenu() {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setHeaderText("Press \"CONTROL\" to go into a multi-edit Selection Mode.\n"
				+ "Then select the text objects you want to edit.\n"
				+ "Once selected, right-click on any one of the selected texts.\n"
				+ "A context menu will pop-up that will let you edit or delete the\n" + "selected text.\n"
				+ "Any individual text can be right-clicked to edit that alone.");
		alert.show();

	}

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

	static String[] textObjects = new String[100];
	static String[] coord = new String[100];

	@FXML
	private void enterWordEvnt(KeyEvent e) throws IOException {

		if (e.getCode().toString() == "ENTER") {

			createObj();

		}

	}

	static int i = 0;

	private void createObj() {

		// each new lbl object has an incremented id
		Label lbl = new Label(submitText.getText());
		lbl.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672); -fx-font-size:14px;");
		textObjects[counter] = submitText.getText();
		lbl.setId("" + (counter++));
		lbl.addEventFilter(MouseEvent.MOUSE_DRAGGED, drag(counter));
		lbl.addEventFilter(MouseEvent.MOUSE_CLICKED, clicked(counter));

		if (counter > 15) {
			if (i < 15) {
				WordBox.add(lbl, 8, i);

				i++;

			} else
				i = 0;
		} else {

			WordBox.add(lbl, 8, counter - 1);
		}

		submitText.setText("");

	}

	private void createObjFromFile(String str) {

		// each new lbl object has an incremented id
		Label lbl = new Label(str);
		lbl.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672); -fx-font-size:14px;");
		textObjects[counter] = str;
		lbl.setId("" + (counter++));
		lbl.addEventFilter(MouseEvent.MOUSE_DRAGGED, drag(counter));
		lbl.addEventFilter(MouseEvent.MOUSE_CLICKED, clicked(counter));

		// lbl.setContextMenu(contextMenu);

		WordBox.add(lbl, 8, counter - 1);

	}

	private void draggedObj(int col, int row) {
		// lbl.getStyle()
		Label lbl = new Label(lastDraggedText);
		lbl.setStyle(((Label) lastSelectedText).getStyle());
		// lbl.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);
		// -fx-font-size:14px;");
		lbl.setId("" + lastDragged);
		lbl.addEventFilter(MouseEvent.MOUSE_DRAGGED, drag(lastDragged));
		lbl.addEventFilter(MouseEvent.MOUSE_CLICKED, clicked(counter));

		toDelete = true;
		WordBox.add(lbl, col, row);
		this.coord[c] = col + " " + row;
		c++;

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
			darkToggle.setText("Light Mode");
			mainPane.setBackground(
					new Background(new BackgroundFill(Color.web("#333333"), CornerRadii.EMPTY, Insets.EMPTY)));

		}

		else {
			// light mode
			darkToggle.setText("Dark Mode");
			Title.setStyle("-fx-text-inner-color: black;");
			NumVen1.setStyle("-fx-text-inner-color: black;");
			NumVen2.setStyle("-fx-text-inner-color: black;");

			mainPane.setBackground(
					new Background(new BackgroundFill(Color.web("#f2f2f2"), CornerRadii.EMPTY, Insets.EMPTY)));

		}

	}

	String filename = "";

	@FXML
	private void saveEvnt() throws FileNotFoundException {

		if (filename != "") {

			File file = new File(filename);
			file.delete();

			PrintWriter writer = new PrintWriter(filename);

			int i = 0;
			int j = 0;
			while (textObjects[i] != null) {

				writer.println(textObjects[i] + " " + coord[j]);
				i++;
			}

			writer.close();

		}

		else {

			saveAsEvnt();
		}

	}

	@FXML
	private void saveAsEvnt() throws FileNotFoundException {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text file", "txt"));

		if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

			filename = fileChooser.getSelectedFile().toString();

			if (!filename.endsWith(".txt"))
				filename += ".txt";

			PrintWriter writer = new PrintWriter(filename);

			int i = 0;
			int j = 0;
			while (textObjects[i] != null) {

				writer.println(textObjects[i] + " " + coord[j]);
				j++;
				i++;
			}

			writer.close();

		}

	}

	@FXML
	private void openEvnt() throws IOException {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text file", "txt"));

		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

			String filename = fileChooser.getSelectedFile().toString();

			BufferedReader read;

			read = new BufferedReader(new FileReader(filename));
			String line = read.readLine();

			while (line != null) {

				createObjFromFile(line);

				line = read.readLine();
			}

			read.close();

		}

	}

	// not complete, supposed to take screenshot of just center pane, or just the
	// window if too hard

	@FXML
	private void screenshot() throws AWTException, IOException {

	}

	@FXML

	private void close() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit Venn Software");
		alert.setHeaderText("Are you sure you want exit?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {

			System.exit(0);

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

	public EventHandler<KeyEvent> onlyNumber() {
		return new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {

				TextField tx = (TextField) arg0.getSource();
				String newText = tx.getText();
				String testCase = ".*[a-z].*";
				String testCase2 = ".*[A-Z].*";

				if (tx.getText().matches(testCase) || tx.getText().matches(testCase2) || tx.getText().length() >= 5) {
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
				lastSelectedText = arg0.getSource();
				if (toDelete == true) {

					WordBox.getChildren().remove(arg0.getSource());
					toDelete = false;
				}

				lastDragged = Integer.valueOf(lbl.getId());
				dragged = true;
				lastDraggedText = lbl.getText();
			}
		};

	}

	public EventHandler<MouseEvent> clicked(final int id) {

		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				if (arg0.getButton() == MouseButton.SECONDARY) {

					Label lbl = (Label) arg0.getSource();

					// if a label is right clicked, do something like open a menu or make it
					// editable
					// the filter works perfectly but needs something after activation
					// either delete the object or make the text editable

					// to delete it would use
					lastSelectedText = arg0.getSource();
					editWindow();
					// contextMenu.setStyle("-fx-font-size:14px;");

					contextMenu.show(lbl, arg0.getScreenX(), arg0.getScreenY());
					// WordBox.getChildren().remove(arg0.getSource());
				} else if (arg0.getButton() == MouseButton.PRIMARY && selectionMode) {

					if (listOfText.contains(((Label) arg0.getSource()))) {
						((Label) arg0.getSource()).setBorder(new Border(new BorderStroke(null, null, null, null)));
						listOfText.remove(((Label) arg0.getSource()));

					} else {

						((Label) arg0.getSource()).setBorder(new Border(new BorderStroke(Color.RED,
								BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
						listOfText.add((Label) arg0.getSource());

					}

				}

			}
		};

	}

	public void addKeyEvent() {

		mainPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case CONTROL:
				showSelectionModeLabel();
				if (listOfText.size() > 0) {
					for (int i = 0; i < listOfText.size(); i++) {

						((Label) listOfText.get(i))
								.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, null, null, null)));
					}
					listOfText.clear();
					selectionMode = false;
					break;
				}
				selectionMode = true;

				break;

			//
			default:
				break;
			}
		});

	}

}