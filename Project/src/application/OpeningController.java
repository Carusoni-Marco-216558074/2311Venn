package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class OpeningController {

	@FXML
	private AnchorPane root;
	
	@FXML
	private void makeNewProject(ActionEvent event) throws IOException {
		Parent homePageParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		Scene homePageScene = new Scene(homePageParent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(homePageScene);
		appStage.centerOnScreen();
		appStage.show();
	}

}
