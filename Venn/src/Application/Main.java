package Application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import Application.objectMaker;

public class Main extends Application {
	// test
	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
	// public double scalar = 1 / ((1920 * 1080) / (screenBounds.getMaxX() *
	// screenBounds.getMaxY()));
	double scalar = 1;

	@SuppressWarnings("static-access")
	public void start(Stage stage) {

		StackPane root = new StackPane();

		ChoiceBox<String> File = new ChoiceBox<>();
		File.getItems().addAll("File", "New", "Open File", "Save", "Print", "Exit");

		Menu fileMenu = new Menu("_File");

		MenuItem newFile = new MenuItem("New...");
		newFile.setOnAction(e -> {
			newPopUp.display("New", "making a new Venn Diagram...");
		});

		MenuItem openFile = new MenuItem("Open...");
		openFile.setOnAction(e -> {
			newPopUp.display("Open", "opening a file...");
		});

		MenuItem saveFile = new MenuItem("Save As...");
		saveFile.setOnAction(e -> {
			newPopUp.display("Save As", "saving a file...");
		});

		MenuItem importFile = new MenuItem("Import...");
		importFile.setOnAction(e -> {
			newPopUp.display("Import", "importing a file...");
		});

		MenuItem exportFile = new MenuItem("Export...");
		exportFile.setOnAction(e -> {
			newPopUp.display("Export", "exporting a file...");
		});

		MenuItem searchFile = new MenuItem("Search");
		searchFile.setOnAction(e -> {
			newPopUp.displaySearch();
		});

		MenuItem exitFile = new MenuItem("Exit");
		exitFile.setOnAction(e -> {
			confirmBox.display("Venn Diagram Maker", "are you sure you want to close?");
			e.consume();

			if (confirmBox.answer == true) {
				stage.close();
			}
		});

		// search button

		fileMenu.getItems().add(newFile);
		fileMenu.getItems().add(openFile);
		fileMenu.getItems().add(new SeparatorMenuItem());
		fileMenu.getItems().add(new MenuItem("Save"));
		fileMenu.getItems().add(saveFile);
		fileMenu.getItems().add(new SeparatorMenuItem());
		fileMenu.getItems().add(importFile);
		fileMenu.getItems().add(exportFile);
		fileMenu.getItems().add(new SeparatorMenuItem());

		fileMenu.getItems().add(new MenuItem("Print"));
		fileMenu.getItems().add(new SeparatorMenuItem());
		fileMenu.getItems().add(exitFile);

		// edit menu
		Menu editMenu = new Menu("_Edit");
		editMenu.getItems().add(new MenuItem("Undo"));
		editMenu.getItems().add(new MenuItem("Redo"));
		editMenu.getItems().add(new SeparatorMenuItem());
		editMenu.getItems().add(new MenuItem("Cut"));
		editMenu.getItems().add(new MenuItem("Copy"));
		editMenu.getItems().add(new MenuItem("Paste"));
		editMenu.getItems().add(new SeparatorMenuItem());
		editMenu.getItems().add(new MenuItem("Delete"));

		// view menu
		Menu viewMenu = new Menu("_View");
		viewMenu.getItems().add(new MenuItem("insert"));

		// help menu
		Menu helpMenu = new Menu("_Help");
		helpMenu.getItems().add(searchFile);
		helpMenu.getItems().add(new MenuItem("About"));
		helpMenu.getItems().add(new MenuItem("Tutorial"));

		// mainMenuBar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);

		ColorPicker cpV1 = new ColorPicker();
		ColorPicker cpV2 = new ColorPicker();
		cpV1.setValue(Color.rgb(0, 0, 150, 0.2));
		cpV2.setValue(Color.rgb(150, 0, 0, 0.2));

		Slider sizeSlider = new Slider();
		objectMaker.createSlider(sizeSlider, scalar - 0.17);

		Slider Ven1Slider = new Slider();
		objectMaker.createSlider(Ven1Slider, scalar - 0.17);

		Slider Ven2Slider = new Slider();
		objectMaker.createSlider(Ven2Slider, scalar - 0.17);

		Circle Ven1 = new Circle(4 * sizeSlider.getValue());// change to screen size
		Ven1.setStroke(Color.BLACK);
		Ven1.setFill(Color.rgb(0, 0, 150, 0.2));

		double radius = Ven1.getRadius() * scalar;
		double mainFontSize = Ven1.getRadius() / (scalar * 13);
		double smallFont = mainFontSize / 2;
		// double subTitleTranslate = 0.35*scalar;
		double subTitleTranslate = 0;

		Font subTitleFont = new Font("Arial Bold", mainFontSize);
		Font chkBoxFont = new Font("Arial", smallFont);
		Font Titlefont = new Font("Arial Bold", 36);

		Circle Ven2 = new Circle(4 * sizeSlider.getValue()); // change to screen size
		Ven2.setStroke(Color.BLACK);
		Ven2.setFill(Color.rgb(150, 0, 0, 0.2));

		CheckBox chkSub = new CheckBox("Subtitles");
		CheckBox chkTitle = new CheckBox("Title");

		ToggleGroup radGroup = new ToggleGroup();

		RadioButton text = new RadioButton("Text");
		RadioButton Numbers = new RadioButton("Numbers");

		text.setToggleGroup(radGroup);
		Numbers.setToggleGroup(radGroup);

		chkSub.setFont(chkBoxFont);
		chkTitle.setFont(chkBoxFont);

		text.setSelected(true);
		chkTitle.setSelected(true);

		HBox subTitle = new HBox();

		subTitle.setAlignment(Pos.TOP_CENTER);

		TextField ven1Title = new TextField("Insert Title Here");
		TextField ven2Title = new TextField("Insert Title Here");

		objectMaker.createSubtitle(ven1Title, subTitleFont);
		objectMaker.createSubtitle(ven2Title, subTitleFont);

		subTitle.getChildren().addAll(ven1Title, ven2Title);

		// subTitle.setTranslateY(-(radius * 1.25));
		//
		// ven1Title.setTranslateY(-(radius * 1.25)/scalar);
		// ven2Title.setTranslateY(-(radius * 1.25)/scalar);
		//
		// ven1Title.setTranslateX((-(radius * subTitleTranslate)*scalar));
		// ven2Title.setTranslateX((radius * subTitleTranslate)*scalar);

		VBox TitleBox = new VBox(1);

		TextField Title = new TextField("Insert Title Here");
		Title.setFont(Titlefont);
		Title.setAlignment(Pos.CENTER);
		Title.setBackground(null);
		TitleBox.getChildren().addAll(menuBar, Title);
		TitleBox.setAlignment(Pos.BASELINE_CENTER);
		// TitleBox.setTranslateY(-(30 * scalar));
		// TitleBox.setTranslateX((50 * scalar));

		// root.setTop(TitleBox);

		Insets in = new Insets(-(151 * scalar));

		VBox leftPanal = new VBox(1);

		Separator leftSeparator = new Separator();
		leftSeparator.setOrientation(Orientation.VERTICAL);

		leftPanal.getChildren().addAll(leftSeparator, text, Numbers, chkTitle, chkSub, cpV1, cpV2, Ven1Slider,
				Ven2Slider, sizeSlider);
		leftPanal.setAlignment(Pos.BOTTOM_LEFT);

		// root.setLeftAnchor(leftPanal, 8.0);
		HBox divider = new HBox();
		divider.getChildren().addAll(leftPanal, leftSeparator);
		root.setAlignment(divider, Pos.BASELINE_LEFT);
		// root.setAlignment(leftSeparator, Pos.BASELINE_LEFT);

		HBox MainCenter = new HBox(10);

		MainCenter.setMargin(Ven1, in);
		MainCenter.setMargin(Ven2, in);

		VBox centerBox = new VBox();
		centerBox.setAlignment(Pos.CENTER);
		MainCenter.setAlignment(Pos.BOTTOM_CENTER);
		// MainCenter.setAlignment(Pos.CENTER);
		MainCenter.setTranslateY(20);
		MainCenter.getChildren().addAll(Ven1, Ven2);
		centerBox.getChildren().addAll(MainCenter, subTitle);
		// root.setCenter(centerBox);
		// root.getChildren().add(centerBox );

		/*
		 * thing below makes the panal layer and titlebox layer transparent to the mouse
		 * so that mouse can be used on every layer and not blocking each other
		 * otherwise mouse wont work on the ui elements
		 */
		leftPanal.setPickOnBounds(false);
		TitleBox.setPickOnBounds(false);

		root.getChildren().addAll(centerBox, TitleBox, leftPanal);

		// listeners - figure out how to better format, maybe separate file?

		sizeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Ven1.setRadius(4 * newValue.doubleValue());
				Ven2.setRadius(4 * newValue.doubleValue());

				double radius = Ven1.getRadius();

				Insets in = new Insets((-2 * newValue.doubleValue())); // change screen size

				MainCenter.setMargin(Ven1, in);
				MainCenter.setMargin(Ven2, in);

				ven1Title.setTranslateY(-(radius * 1.25));
				ven2Title.setTranslateY(-(radius * 1.25));
				//
				ven1Title.setTranslateX((-(radius * subTitleTranslate) * scalar));
				ven2Title.setTranslateX((radius * subTitleTranslate) * scalar);

				Ven1Slider.setValue(newValue.doubleValue());
				Ven2Slider.setValue(newValue.doubleValue());

				Font changedSubFont = new Font("Arial Bold", radius / (scalar * 13));

				ven1Title.setFont(changedSubFont);
				ven2Title.setFont(changedSubFont);

			}
		});

		Ven1Slider.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Ven1.setRadius(4 * newValue.doubleValue());

				double radius = Ven1.getRadius();

				Insets in = new Insets((-2 * newValue.doubleValue())); // change screen size

				MainCenter.setMargin(Ven1, in);
				ven1Title.setTranslateY(-(radius * 1.25));
				//
				ven1Title.setTranslateX((-(radius * subTitleTranslate) * scalar));
				Font changedSubFont = new Font("Arial Bold", radius / (scalar * 13));
				ven1Title.setFont(changedSubFont);

			}
		});
//

		Ven2Slider.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Ven2.setRadius(4 * newValue.doubleValue());

				double radius = Ven2.getRadius();

				Insets in = new Insets((-2 * newValue.doubleValue())); // change screen size

				MainCenter.setMargin(Ven2, in);
				ven2Title.setTranslateY(-(radius * 1.25));
				//
				ven2Title.setTranslateX((radius * subTitleTranslate) * scalar);
				Font changedSubFont = new Font("Arial Bold", radius / (scalar * 13));
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
		radGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (radGroup.getSelectedToggle() != null) {

					if (text.isSelected() == true) {
						System.out.println("text");

					} else {
						System.out.println("Number");

					}
				}
			}
		});
		//
		chkTitle.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				Title.setDisable(!newValue);
				Title.setVisible(newValue);

				Title.setText("Insert Title Here");

			}
		});
//
		chkSub.selectedProperty().addListener(new ChangeListener<Boolean>() {
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

		ven1Title.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					if (ven1Title.getText().length() >= 15) {

						ven1Title.setText(ven1Title.getText().substring(0, 15));
					}
				}
			}
		});
		//
		ven2Title.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					if (ven2Title.getText().length() >= 15) {

						ven2Title.setText(ven2Title.getText().substring(0, 15));
					}
				}
			}
		});
		//

		Title.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					if (Title.getText().length() >= 30) {

						Title.setText(Title.getText().substring(0, 30));
					}
				}
			}
		});

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
		// Scene scene = new Scene(root, screenBounds.getMaxX() - (30 * scalar),
		// screenBounds.getMaxY() - (60 * scalar));
		// stage.setResizable(false);
		Scene scene = new Scene(root, screenBounds.getMaxX() - 20, screenBounds.getMaxY() - 30);

		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}

	public static void main(String[] args) {

		Application.launch(args);
	}
}
