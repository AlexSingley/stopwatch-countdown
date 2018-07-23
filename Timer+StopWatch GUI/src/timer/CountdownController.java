package timer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CountdownController implements Initializable {

    @FXML
    private GridPane countdownModePanel;

    @FXML
    private AnchorPane countdownDisplayPanel;

    @FXML
    private JFXButton startCountdownButton;

    @FXML
    private JFXButton pauseCountdownButton;

    @FXML
    private Text countdownHoursTimer;

    @FXML
    private Text countdownMinutesTimer;

    @FXML
    private Text countdownSecondsTimer;

    @FXML
    private AnchorPane countdownModeInputPanel;

    @FXML
    private JFXButton confirmInputButton;
    
    @FXML
    private JFXButton restartCountdownButton;

    @FXML
    private JFXComboBox<Integer> hoursInput;

    @FXML
    private JFXComboBox<Integer> minutesInput;

    @FXML
    private JFXComboBox<Integer> secondsInput;
    
    @FXML
    private JFXButton menuButton;
    
    long startTime;
    
    boolean isPaused = true;
    
    boolean isRunning;
	
	Integer inputTime;
    
    Map<Integer, String> timerMap;

    @FXML
    void confirmInput(ActionEvent event) {
    	setInput();
    	inputToDisplay();
    	
    	inputTime = totalInSeconds(hoursInput.getValue(), minutesInput.getValue(), secondsInput.getValue());
    	hoursInput.setValue(0);
    	minutesInput.setValue(0);
    	secondsInput.setValue(0);
    }
    
    @FXML
    void restartCountdown(ActionEvent event) {
    	displayToInput();
    	startCountdownButton.setText("Start");
    }

    @FXML
    void pauseCountdown(ActionEvent event) {
    	if(isPaused == false && isRunning == true)	{
    		timeline.pause();
    		isPaused = true;
    		startCountdownButton.setText("Resume");
    	}
    }

    @FXML
    void setInput() {
    	countdownHoursTimer.setText(hoursInput.getValue().toString());
    	countdownMinutesTimer.setText(minutesInput.getValue().toString());
    	countdownSecondsTimer.setText(secondsInput.getValue().toString());
    	
    	if(countdownHoursTimer.getText().length() == 1) {
    		countdownHoursTimer.setText("0" + hoursInput.getValue().toString());
    	}
    	if(countdownMinutesTimer.getText().length() == 1) {
    		countdownMinutesTimer.setText("0" + minutesInput.getValue().toString());
    	}
    	if(countdownSecondsTimer.getText().length() == 1) {
    		countdownSecondsTimer.setText("0" + secondsInput.getValue().toString());
    	}
    }

    @FXML
    void startCountdown(ActionEvent event) {
    	if(isRunning == false || isPaused == true)	{
    		isPaused = false;
    		isRunning = true;
    		startCountdownButton.setText("Start");
    		timeline.setCycleCount(Animation.INDEFINITE);
    		timeline.play();
    	}
    }
    
    @FXML
    void changeToMenu(ActionEvent event) throws IOException {
    	Parent menuParent = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
    	Scene menuScene = new Scene(menuParent);
    	
    	//Below gets the stage info
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	window.setScene(menuScene);
    }
    
    void endCountdown()	{
    	isRunning = false;
    	timeline.stop();
    	displayToInput();
    }
    
    Integer totalInSeconds(Integer h, Integer m, Integer s) {
    	Integer hToS = h * 3600;
    	Integer mToS = m * 60;
    	Integer totalS = hToS + mToS + s;
    	return totalS;
    }
    
    ArrayList<Integer> sToHMS(Integer curSec)	{
    	Integer hrs = curSec / 3600;
    	curSec = curSec % 3600; 
    	Integer min = curSec / 60;
    	curSec = curSec % 60;
    	Integer sec = curSec;
    	ArrayList<Integer> hmsList = new ArrayList<>();
    	hmsList.add(hrs);
    	hmsList.add(min);
    	hmsList.add(sec);
    	return hmsList;
    }
    
    void setDisplay()	{
    	ArrayList<Integer> currTimeLeft = sToHMS(inputTime);
    	countdownHoursTimer.setText(timerMap.get(currTimeLeft.get(0)));
    	countdownMinutesTimer.setText(timerMap.get(currTimeLeft.get(1)));
    	countdownSecondsTimer.setText(timerMap.get(currTimeLeft.get(2)));
    }
    
    void inputToDisplay()	{
    	TranslateTransition cInputTrans = new TranslateTransition();
    	cInputTrans.setDuration(Duration.millis(100));
    	cInputTrans.setToX(0);
    	cInputTrans.setToY(-200);
    	cInputTrans.setNode(countdownModeInputPanel);
    	TranslateTransition cDisplay = new TranslateTransition();
    	cDisplay.setDuration(Duration.millis(100));
    	cDisplay.setFromX(0);
    	cDisplay.setFromY(200);
    	cDisplay.setToX(0);
    	cDisplay.setToY(0);
    	cDisplay.setNode(countdownDisplayPanel);
    	ParallelTransition cPar = new ParallelTransition(cInputTrans, cDisplay);
    	cPar.play();
    }
    
    void displayToInput()	{
    	TranslateTransition cDisplay = new TranslateTransition();
    	cDisplay.setDuration(Duration.millis(100));
    	cDisplay.setToX(0);
    	cDisplay.setToY(-200);
    	cDisplay.setNode(countdownDisplayPanel);
    	TranslateTransition cInputTrans = new TranslateTransition();
    	cInputTrans.setDuration(Duration.millis(100));
    	cInputTrans.setFromX(0);
    	cInputTrans.setFromY(200);
    	cInputTrans.setToX(0);
    	cInputTrans.setToY(0);
    	cInputTrans.setNode(countdownModeInputPanel);
    	ParallelTransition cPar = new ParallelTransition(cDisplay, cInputTrans);
    	cPar.play();
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Integer> hrsList = FXCollections.observableArrayList();
		ObservableList<Integer> minAndSecList = FXCollections.observableArrayList();
		for(int i = 0; i <= 60; i++)	{
			if(0 <= i && i <= 24) {
				hrsList.add(new Integer(i));
			}
			minAndSecList.add(new Integer(i));
		}
		hoursInput.setItems(hrsList);
		hoursInput.setValue(0);
		
		minutesInput.setItems(minAndSecList);
		minutesInput.setValue(0);
		
		secondsInput.setItems(minAndSecList);
		secondsInput.setValue(0);
		
		timerMap = new TreeMap<Integer, String>();
		for(Integer i = 0; i <= 60; i++) {
			if(0 <= i && i <= 9) {
				timerMap.put(i,  "0" + i.toString());
			}
			else	{
				timerMap.put(i,  i.toString());
			}
		}
	}

    final Timeline timeline = new Timeline(
    	    new KeyFrame(
    	        Duration.millis(1000),
    	        startCountdownButton -> {
    	        	
    	        	setDisplay();
    	        	inputTime -= 1;
    	        	if(inputTime == -1)	{
    	        		endCountdown();
    	        	}
    	        }
    	    )
    	);
    
}