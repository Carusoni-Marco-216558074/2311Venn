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
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
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
import javafx.stage.StageStyle;

public class MainController {

	// The Object used for the edit window pop-up
	Stage editWindowStage = new Stage();
	Stage descWindowStage = new Stage();

	// The Objects used for the context menu (right-click)
	final ContextMenu contextMenu = new ContextMenu();
	final MenuItem edit = new MenuItem("Edit");
	final MenuItem delete = new MenuItem("Delete");
	final MenuItem desc = new MenuItem("Description");
	// The Objects needed for Multi-edit tool
	ArrayList<Object> listOfText = new ArrayList<Object>();

	ArrayList<Object> listOfElements = new ArrayList<Object>();

	// The descriptions of each object
	ArrayList<Object> listOfDescriptions = new ArrayList<Object>();

	// used for tracking dynamic labels
	public static int counter = 0;
	static int objCounter = 0;
	public static int desccounter = 0;

	public static int lastDragged;
	public static boolean dragged;
	public static String lastDraggedText;
	public Object lastSelectedText;
	public static boolean toDelete = true;
	// used for moving elements to gridpane
	public String text;
	public static Integer colIndex;
	public static Integer rowIndex;

	Stack<Object> changes = new Stack<Object>();
	Integer stackInd = 0;
	int count = 1;

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
	MenuItem manual;
	@FXML
	MenuItem undo;
	@FXML
	MenuItem redo;
	@FXML
	MenuItem about;
	@FXML
	MenuItem test;
	@FXML
	Label selectionModeLabel;
	@FXML
	MenuItem closeButton;

	// initializing elements before the frame is shown (adding filters, setting
	// defaults)

	public void initialize() {

		changes.add("start");

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

		// TextField editText = new TextField();
		contextMenu.getItems().addAll(desc, edit, delete);
		contextMenu.setStyle("-fx-font-size:14px;");

		addKeyEvent();

		if (OpeningController.openExisting) {
			try {
				openEvnt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
		Label l3 = new Label("Change background color:");
		ColorPicker cpk = new ColorPicker();
		CheckBox chkBox = new CheckBox("Apply color change:");
		TextField fontSize = new TextField("14");
		TextField editText = new TextField();
		Button doneEditButton = new Button("Done");
		Button cancelEditButton = new Button("Cancel");
		h.getChildren().addAll(doneEditButton, cancelEditButton);
		fontSize.addEventFilter(KeyEvent.KEY_TYPED, maxLength(2));
		fontSize.addEventFilter(KeyEvent.KEY_TYPED, onlyNumber());
		editText.setText(((Label) lastSelectedText).getText());
		editText.addEventFilter(KeyEvent.KEY_TYPED, maxLength(25));

		if (!selectionModeLabel.isDisable()) {
			if (listOfText.size() == 1)

				v.getChildren().addAll(l, l2, fontSize, l3, cpk, chkBox, new Label("Enter new text here:"), editText,
						h);

			else
				v.getChildren().addAll(l, l2, fontSize, l3, cpk, chkBox, h);
			popupScene = new Scene(v, 200, 235);
		} else {

			v.getChildren().addAll(l, l2, fontSize, l3, cpk, chkBox, new Label("Enter new text here:"), editText, h);
			popupScene = new Scene(v, 200, 235);
		}

		editWindowStage = new Stage();
		editWindowStage.setScene(popupScene);

		if (listOfText.size() == 0)
			listOfText.add(lastSelectedText);
		if (listOfText.size() == 1)
			fontSize.setText(Integer.toString((int) ((Label) listOfText.get(0)).getFont().getSize()));

		doneEditButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				if (changes.size() > 1 && stackInd < changes.size() - 1) {
					for (int i = changes.size(); i > stackInd + 1; i--)
						changes.removeElementAt(changes.size() - 1);
				}

				if (Integer.parseInt(fontSize.getText()) < 0 || Integer.parseInt(fontSize.getText()) > 14) {
					Alert alert = new Alert(AlertType.CONFIRMATION);

					alert.setHeaderText("Please select a font size between 0 and 14.");
					alert.show();
					return;
				}

				boolean a = false;
				for (int i = 0; i < listOfText.size(); i++) {

					((Label) listOfText.get(i))
							.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, null, null, null)));

					if (!Integer.toString((int) ((Label) listOfText.get(i)).getFont().getSize())
							.equals(fontSize.getText())) {

						if (!a) {
							changes.add("BeginFormatChange");

							a = true;
						}

						String tempFont = Integer.toString((int) ((Label) listOfText.get(i)).getFont().getSize());

						changes.add(((Label) listOfText.get(i)).getId());
						((Label) listOfText.get(i))
								.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672); -fx-font-size:"
										+ fontSize.getText() + "px;");

						changes.add(tempFont + "," + fontSize.getText());

					}

					if (chkBox.isSelected()) {

						((Label) listOfText.get(i))
								.setBackground(new Background(new BackgroundFill(cpk.getValue(), null, null)));
					}

				}
				if (a) {

					changes.add("EndFormatChange");

				}

				if (listOfText.size() == 1 && !editText.getText().equals((((Label) listOfText.get(0)).getText()))) {
					changes.add("BeginEditText");
					changes.add(((Label) listOfText.get(0)).getText());
					changes.add(((Label) listOfText.get(0)).getId());

					for (int i = 0; i < counter; i++) {

						if (textObjects[i].equals((String) (((Label) listOfText.get(0)).getText()))) {
							textObjects[i] = (String) editText.getText();
							break;
						}

					}

					((Label) listOfText.get(0)).setText(editText.getText());
					changes.add(editText.getText());
					changes.add("EndEditText");
				}

				listOfText.clear();
				if (!selectionModeLabel.isDisable()) {
					showSelectionModeLabel();
				}

				desc.setDisable(false);
				editWindowStage.close();

				stackInd = changes.size() - 1;

			}
		});

		cancelEditButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				listOfText.clear();
				if (!selectionModeLabel.isDisable()) {
					showSelectionModeLabel();
				}

				editWindowStage.close();
			}

		});
		//
		// editText.setOnKeyReleased(event -> {
		// if (event.getCode() == KeyCode.ENTER) {
		//
		// ((Label) lastSelectedText).setText(editText.getText());
		// ((Label) lastSelectedText)
		// .setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);
		// -fx-font-size:"
		// + fontSize.getText() + "px;");
		// stage.close();
		// }
		// });

		edit.setOnAction((event) -> {

			editWindowStage.show();

		});

		delete.setOnAction((event) -> {

			if (changes.size() > 1 && stackInd < changes.size() - 1) {
				for (int i = changes.size(); i > stackInd + 1; i--)
					changes.removeElementAt(changes.size() - 1);
			}

			changes.add("BeginListOfTextDeleted");
			for (int i = 0; i < listOfText.size(); i++) {

				((Label) ((Label) listOfText.get(i))).setBorder(new Border(new BorderStroke(null, null, null, null)));

				changes.add(((Label) listOfText.get(i)));
				int row = GridPane.getRowIndex((Label) listOfText.get(i));
				int col = GridPane.getColumnIndex((Label) listOfText.get(i));
				changes.add(row + "," + col);
				WordBox.getChildren().remove(((Label) listOfText.get(i)));

			}
			changes.add("EndListOfTextDeleted");
			listOfText.clear();

			listOfText.clear();
			if (!selectionModeLabel.isDisable()) {
				showSelectionModeLabel();
			}

			stackInd = changes.size() - 1;

		});

	}// end of editWindow()

	public void descriptionWindow() {
		VBox v2 = new VBox(1);
		HBox h2 = new HBox(1);
		TextArea editDesc = new TextArea();
		int u = listOfElements.indexOf(lastSelectedText);

		if ((String) listOfDescriptions.get(u) == "Add a description") {
			editDesc.setPromptText((String) listOfDescriptions.get(u));
			editDesc.setPromptText("test");
		}

		else
			editDesc.setText((String) listOfDescriptions.get(u));

		editDesc.setMaxHeight(80);
		editDesc.setWrapText(true);
		Button doneDescButton = new Button("Save Description");
		h2.getChildren().addAll(doneDescButton);
//		Text description = new Text("" + listOfDescriptions.get(listOfElements.indexOf(lastSelectedText)));
		v2.getChildren().addAll(new Label("Edit description here:"), editDesc, h2);
		Scene descScene;
		descScene = new Scene(v2, 200, 130);
		descWindowStage = new Stage();
		descWindowStage.setScene(descScene);
		descWindowStage.initStyle(StageStyle.UTILITY);
		doneDescButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				descWindowStage.close();
				listOfDescriptions.add(listOfElements.indexOf(lastSelectedText), (editDesc.getText()));
			}
		});
		desc.setOnAction((event) -> {

			descWindowStage.show();

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
	private void redo() {
		if (changes.size() == 1)
			return;

		if (stackInd + 1 < changes.size())
			stackInd++;

		if (changes.get(stackInd).equals("BeginListOfTextDeleted")) {

			stackInd++;

			while (!changes.get(stackInd).equals("EndListOfTextDeleted")) {

				WordBox.getChildren().remove(getLabel((Label) changes.get(stackInd)));

				stackInd++;
				stackInd++;

			}

		} // end if

		else if (changes.get(stackInd).equals("BeginFormatChange")) {

			// changes.remove(changes.lastElement());
			stackInd++;

			// int stackInd = changes.size()-1;
			while (!changes.get(stackInd).equals("EndFormatChange")) {

				String str = (String) changes.get(stackInd + 1);

				String[] arrOfStr = str.split(",");

				(getLabelWithID((String) changes.get(stackInd)))
						.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672); -fx-font-size:"
								+ arrOfStr[1] + "px;");
				stackInd++;
				stackInd++;

			}
		} // end if

		else if (changes.get(stackInd).equals("BeginDrag")) {

			stackInd++;

			while (!changes.get(stackInd).equals("EndDrag")) {

				String str = (String) changes.get(stackInd + 2);
				String[] arrOfStr = str.split(",");

				Label l = (Label) getLabelWithID((String) changes.get(stackInd + 1));
				WordBox.getChildren().remove(getLabel((Label) getLabelWithID((String) changes.get(stackInd + 1))));

				WordBox.add(l, Integer.parseInt(arrOfStr[0]), Integer.parseInt(arrOfStr[1]));

				stackInd++;
				stackInd++;
				stackInd++;

			}

		} else if (changes.get(stackInd).equals("BeginEditText")) {

			stackInd++;

			while (!changes.get(stackInd).equals("EndEditText")) {

				Label l = (Label) getLabelWithID((String) changes.get(stackInd + 1));
				l.setText((String) changes.get(stackInd + 2));

				stackInd++;
				stackInd++;
				stackInd++;

				for (int i = 0; i < counter; i++) {

					if (textObjects[i].equals((String) changes.get(stackInd))) {
						textObjects[i] = (String) changes.get(stackInd + 2);
						break;
					}

				}

			}

		} // end if

	}

	private Node getLabel(Label l) {

		// l = changes.get(stackInd+1);
		ObservableList<Node> childrens = WordBox.getChildren();
		for (Node node : childrens) {
			if (node instanceof Label && ((Label) node).toString().equals(l.toString())) {

				Node lbl = node; // use what you want to remove
				return lbl;

			}
		}

		return null;

	}

	private Node getLabelWithID(String id) {

		// l = changes.get(stackInd+1);
		ObservableList<Node> childrens = WordBox.getChildren();
		for (Node node : childrens) {
			if (node instanceof Label && ((Label) node).toString().contains("id=" + id)) {

				Node lbl = node; // use what you want to remove
				return lbl;

			}
		}

		return null;

	}

	@FXML
	private void testCode() {
		System.out.println("=============================");
		for (int i = 0; i < changes.size(); i++)
			if (i == stackInd)
				System.out.println("====>" + changes.get(i));
			else
				System.out.println(changes.get(i));

	}

	@FXML
	private void undo() {

		if (changes.size() == 0 || changes.get(stackInd).equals("start")) {
			return;
		}

		else if (changes.get(stackInd).equals("EndListOfTextDeleted")) {

			stackInd--;

			while (!changes.get(stackInd).equals("BeginListOfTextDeleted")) {

				// the row/column of text
				String str = (String) changes.get(stackInd);

				String[] arrOfStr = str.split(",");
				stackInd--;

				WordBox.add((Label) changes.get(stackInd), Integer.parseInt(arrOfStr[1]),
						Integer.parseInt(arrOfStr[0]));
				stackInd--;

			}

		} // end if

		else if (changes.get(stackInd).equals("EndFormatChange")) {

			stackInd--;

			while (!changes.get(stackInd).equals("BeginFormatChange")) {
				String str = (String) changes.get(stackInd);

				String[] arrOfStr = str.split(",");
				// String str = (String) changes.get(stackInd);

				// changes.remove(changes.lastElement());
				stackInd--;

				(getLabelWithID((String) changes.get(stackInd)))
						.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672); -fx-font-size:"
								+ arrOfStr[0] + "px;");
				// changes.remove(changes.lastElement());
				stackInd--;

			}

		}

		else if (changes.get(stackInd).equals("EndDrag")) {

			stackInd--;

			while (!changes.get(stackInd).equals("BeginDrag")) {

				// the row/column of text
				String str = (String) changes.get(stackInd - 2);
				String[] arrOfStr = str.split(",");

				// WordBox.getChildren().remove(getLabelWithID((String)
				// changes.get(stackInd-1)));

				Label l = (Label) getLabelWithID((String) changes.get(stackInd - 1));
				WordBox.getChildren().remove(getLabelWithID((String) changes.get(stackInd - 1)));
				WordBox.add(l, Integer.parseInt(arrOfStr[0]), Integer.parseInt(arrOfStr[1]));

				stackInd--;
				stackInd--;
				stackInd--;

			}

		} // end if

		else if (changes.get(stackInd).equals("EndEditText")) {

			stackInd--;

			while (!changes.get(stackInd).equals("BeginEditText")) {

				Label l = (Label) getLabelWithID((String) changes.get(stackInd - 1));
				l.setText((String) changes.get(stackInd - 2));

				for (int i = 0; i < counter; i++) {

					if (textObjects[i].equals((String) changes.get(stackInd))) {
						textObjects[i] = (String) changes.get(stackInd - 2);
						break;
					}

				}

				stackInd--;
				stackInd--;
				stackInd--;

			}

		} // end if

		stackInd--;

	}

	@FXML
	private void aboutMenu() {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setHeaderText("This software makes venn diagrams."

		);
		alert.show();

	}

	@FXML
	private void manualMenu() {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setHeaderText("The main Title and each of the Subtitles can be changed.\n"
				+ "The colour of each venn circle can be changed.\n"
				+ "Text is entered in the field on the right and by pressing the \"Enter\" key.\n"
				+ "Text elements can be dragged and dropped around the venn.\n"
				+ "By pressing the \"Control\" key Selection Mode will be enabled, allowing you\n"
				+ "to select multiple text elements by clicking on them.\n"
				+ "When right-clicking on a text element editing options will be displayed.\n"
				+ "The software can be operated in dark or light mode.\n"
				+ "There is an Undo and Redo feature in the Edit option above.\n"
				+ "The venn can be saved as a txt file and later opened by selecting that file.\n");
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

	private void createObj() {

		// each new lbl object has an incremented id
		Label lbl = new Label(submitText.getText());
		lbl.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672); -fx-font-size:14px;");
		textObjects[counter] = submitText.getText();
		lbl.setId("" + (counter++));
		lbl.addEventFilter(MouseEvent.MOUSE_DRAGGED, drag(counter));
		lbl.addEventFilter(MouseEvent.MOUSE_CLICKED, clicked(counter));

		listOfElements.add(lbl);

		if (counter > 15) {
			if (objCounter < 15) {
				WordBox.add(lbl, 8, objCounter);

				objCounter++;

			} else
				objCounter = 0;
		} else {

			WordBox.add(lbl, 8, counter - 1);
			coord[counter - 1] = 8 + "," + (counter - 1);
		}

		submitText.setText("");

		listOfDescriptions.add("Add a description");

	}

	private void createObjFromFile(String str[]) {

		// each new lbl object has an incremented id
		Label lbl = new Label(str[0]);
		lbl.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672); -fx-font-size:14px;");
		textObjects[counter] = str[0];
		lbl.setId("" + (counter++));
		lbl.addEventFilter(MouseEvent.MOUSE_DRAGGED, drag(counter));
		lbl.addEventFilter(MouseEvent.MOUSE_CLICKED, clicked(counter));
		listOfElements.add(lbl);

		// lbl.setContextMenu(contextMenu);

		WordBox.add(lbl, Integer.parseInt(str[1]), Integer.parseInt(str[2]));
		coord[counter] = str[1] + "," + str[2];
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
		listOfElements.add(lbl);
		listOfDescriptions.add(listOfElements.indexOf(lbl),
				listOfDescriptions.get(listOfElements.indexOf(lastSelectedText)));

		toDelete = true;
		WordBox.add(lbl, col, row);
		coord[lastDragged] = col + "," + row;
		changes.add(lbl.getId());
		changes.add(col + "," + row);
		changes.add("EndDrag");
		stackInd = changes.size() - 1;
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

			selectionModeLabel
					.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

			Title.setStyle("-fx-text-inner-color: white;");
			NumVen1.setStyle("-fx-text-inner-color: white;");
			NumVen2.setStyle("-fx-text-inner-color: white;");
			darkToggle.setText("Light Mode");
			mainPane.setBackground(
					new Background(new BackgroundFill(Color.web("#333333"), CornerRadii.EMPTY, Insets.EMPTY)));

		}

		else {
			// light mode

			selectionModeLabel.setBackground(
					new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

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
			while (textObjects[i] != null) {

				writer.println(textObjects[i] + "," + coord[i]);
				i++;

			}

			writer.println("newLine");

			writer.println(Title.getText());
			writer.println(subTitle1.getText());
			writer.println(subTitle2.getText());
			writer.println(cpkVen1.getValue());
			writer.println(cpkVen2.getValue());

			int j = 0;
			while (j < listOfDescriptions.size()) {
				writer.println(listOfDescriptions.get(j));
				j++;
			}

			writer.println("newLine");

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
			while (textObjects[i] != null) {

				writer.println(textObjects[i] + "," + coord[i]);
				i++;
			}

			writer.println("newLine");

			writer.println(Title.getText());
			writer.println(subTitle1.getText());
			writer.println(subTitle2.getText());
			writer.println(cpkVen1.getValue().getRed() + "," + cpkVen1.getValue().getGreen() + ","
					+ cpkVen1.getValue().getBlue());
			writer.println(cpkVen2.getValue().getRed() + "," + cpkVen2.getValue().getGreen() + ","
					+ cpkVen2.getValue().getBlue());

			int j = 0;
			while (j < listOfDescriptions.size()) {
				writer.println(listOfDescriptions.get(j));
				j++;
			}

			writer.println("newLine");

			writer.close();

		}

	}

	@FXML
	private void openEvnt() throws IOException {
		changes.clear();
		changes.add("start");
		stackInd = 0;

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text file", "txt"));

		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

			String filename = fileChooser.getSelectedFile().toString();

			BufferedReader read;

			read = new BufferedReader(new FileReader(filename));
			String line = read.readLine();

			// delete all previous elements and reset the counters

			for (int i = 0; i < listOfElements.size(); i++)
				WordBox.getChildren().remove(((Label) listOfElements.get(i)));
			listOfElements.clear();

			counter = 0;
			objCounter = 0;

			while (!(line.contains("newLine"))) {

				String[] tokens = line.split(",");
				createObjFromFile(tokens);
				line = read.readLine();
				counter++;

			}

			Title.setText(read.readLine());
			subTitle1.setText((read.readLine()));
			subTitle2.setText((read.readLine()));

			String[] colours = read.readLine().split(",");

			Color colour = Color.color(Double.parseDouble(colours[0]), Double.parseDouble(colours[1]),
					Double.parseDouble(colours[2]), 0.5);
			Ven1.setFill(colour);
			cpkVen1.setValue(colour);

			colours = read.readLine().split(",");

			colour = Color.color(Double.parseDouble(colours[0]), Double.parseDouble(colours[1]),
					Double.parseDouble(colours[2]), 0.5);
			Ven2.setFill(colour);
			cpkVen2.setValue(colour);

			line = read.readLine();
			while (!(line.contains("newLine"))) {
				listOfDescriptions.add(line);
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

					if (changes.size() > 1 && stackInd < changes.size() - 1) {
						for (int i = changes.size(); i > stackInd + 1; i--)
							changes.removeElementAt(changes.size() - 1);
					}

					changes.add("BeginDrag");

					changes.add(GridPane.getColumnIndex(lbl) + "," + GridPane.getRowIndex(lbl));

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
					descriptionWindow();

					contextMenu.show(lbl, arg0.getScreenX(), arg0.getScreenY());
					// WordBox.getChildren().remove(arg0.getSource());
				} else if (arg0.getButton() == MouseButton.PRIMARY && !selectionModeLabel.isDisable()) {

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

			// if (event.isControlDown() && event.getCode().toString().equals("z"))
			// undo();
			// else if (event.isControlDown() && event.getCode().toString().equals("y"))
			// redo();

			if (!editWindowStage.isShowing() && !contextMenu.isShowing()) {

				switch (event.getCode()) {
				case CONTROL:

					desc.setDisable(!desc.isDisable());

					showSelectionModeLabel();
					if (listOfText.size() > 0) {
						for (int i = 0; i < listOfText.size(); i++) {

							((Label) listOfText.get(i))
									.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, null, null, null)));
						}
						listOfText.clear();
						break;
					}

					break;

				// default case
				default:
					break;
				}

			} else {

			}
		});

	}

}