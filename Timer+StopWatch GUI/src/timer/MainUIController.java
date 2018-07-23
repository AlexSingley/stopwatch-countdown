package timer;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainUIController {

    @FXML
    private GridPane selectModePanel;

    @FXML
    private Button countdownButton;

    @FXML
    private Button stopwatchButton;


    @FXML
    void changeToCountdown(ActionEvent event) throws IOException {
    	Parent countdownParent = FXMLLoader.load(getClass().getResource("Countdown.fxml"));
    	Scene countdownScene = new Scene(countdownParent);
    	
    	//Below gets the stage info
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	window.setScene(countdownScene);
    }
    
    @FXML
    void changeToStopwatch(ActionEvent event) throws IOException {
    	Parent stopwatchParent = FXMLLoader.load(getClass().getResource("Stopwatch.fxml"));
    	Scene stopwatchScene = new Scene(stopwatchParent);
    	
    	//Below gets the stage info
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	window.setScene(stopwatchScene);
    }
}