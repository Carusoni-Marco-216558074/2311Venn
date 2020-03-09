package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
				Thread.sleep(5000);
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
					}
					
				});
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

}
