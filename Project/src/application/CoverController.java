package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class CoverController implements Initializable {
	
    @FXML
    private AnchorPane parent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	new CoverPage().start();
    }
    
    class CoverPage extends Thread{
    	@Override
    	public void run() {
    		try {
				Thread.sleep(3500);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Parent root = null;
						try {
							root = FXMLLoader.load(getClass().getResource("OpeningPage.fxml"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Stage primaryStage = new Stage();
						Scene scene = new Scene(root);
						primaryStage.setScene(scene);
						primaryStage.setResizable(false);
						primaryStage.setTitle("Venn Diagram Software 2.0");
						primaryStage.show();
						parent.getScene().getWindow().hide();
						primaryStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent t) {

								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Exit Venn Software");
								alert.setHeaderText("Are you sure you want exit?");

								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == ButtonType.OK) {

									System.exit(0);

								} else {

									t.consume();
								}

							}
						});
					}
					
				});
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

}
