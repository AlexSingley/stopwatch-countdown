package timer;

import java.io.IOException;
import java.util.Map;

import com.jfoenix.controls.JFXButton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StopwatchController {

    @FXML
    private GridPane stopwatchModePanel;

    @FXML
    private AnchorPane stopwatchPanel;

    @FXML
    private JFXButton startStopwatchButton;

    @FXML
    private JFXButton pauseStopwatchButton;

    @FXML
    private JFXButton resetStopwatchButton;

    @FXML
    private Text stopwatchHoursTimer;

    @FXML
    private Text stopwatchMinutesTimer;

    @FXML
    private Text stopwatchSecondsTimer;
    
    @FXML
    private JFXButton menuButton;
    
    private long totalTime;
    
    long startTime;
    
    boolean isPaused = true;
    
    boolean isRunning;
    
    Map<Integer, String> numberMap;

    @FXML
    void startStopwatch(ActionEvent event) throws InterruptedException {
    	if(isRunning == false || isPaused == true)	{
    		startTime = System.currentTimeMillis();
    		isPaused = false;
    		isRunning = true;
    		startStopwatchButton.setText("Start");
    		timeline.setCycleCount(Animation.INDEFINITE);
    		timeline.play();
    	}
    	
    	
    	/**
    	long startTime = System.currentTimeMillis();
		
		while(true)	{
			long timePassed = System.currentTimeMillis() - startTime;
			long secPassed = timePassed / 1000;
			long minPassed = secPassed / 60;
			long hrsPassed = minPassed / 60;
			
			String displaySec = Long.toString(secPassed % 60);
			String displayMin = Long.toString(minPassed % 60);
			String displayHrs = Long.toString(hrsPassed % 60);
			
			long updateDisplay = 1000 - (timePassed % 1000);
			
			if(displayHrs.length() == 1)	{
				displayHrs = "0" + displayHrs;
			}
			if(displayMin.length() == 1)	{
				displayMin = "0" + displayMin;
			}
			if(displaySec.length() == 1)	{
				displaySec = "0" + displaySec;
			}
			stopwatchHoursTimer.setText(displayHrs);
			stopwatchMinutesTimer.setText(displayMin);
			stopwatchSecondsTimer.setText(displaySec);
			System.out.println(stopwatchSecondsTimer.getText());
			
			Thread.sleep(updateDisplay);
			*/
		}
    
  /** 	
   		void setOutput()	{
    	LinkedList<Text> updateTimer = new LinkedList<Text>();
    	updateTimer.add(stopwatchHoursTimer);
    	updateTimer.add(stopwatchMinutesTimer);
    	updateTimer.add(stopwatchSecondsTimer);
    }
    */

    @FXML
    void resetStopwatch(ActionEvent event) {
    	timeline.stop();
    	totalTime = 0;
    	stopwatchSecondsTimer.setText("00");
    	stopwatchMinutesTimer.setText("00");
    	stopwatchHoursTimer.setText("00");
    	isPaused = true;
    	isRunning = false;
    	startStopwatchButton.setText("Start");
    	
    }

	@FXML
    void pauseStopwatch(ActionEvent event) {
    	if(isPaused == false)	{
    		timeline.pause();
    		totalTime += System.currentTimeMillis() - startTime;
    		isPaused = true;
    		startStopwatchButton.setText("Resume");
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
	
	final Timeline timeline = new Timeline(
	    new KeyFrame(
	        Duration.millis(1000),
	        startStopwatchButton -> {
	        		long timePassed = (System.currentTimeMillis() + totalTime) - startTime;
					long secPassed = timePassed / 1000;
					long minPassed = secPassed / 60;
					long hrsPassed = minPassed / 60;
					
					String displaySec = Long.toString(secPassed % 60);
					String displayMin = Long.toString(minPassed % 60);
					String displayHrs = Long.toString(hrsPassed % 60);
					
					if(displayHrs.length() == 1)	{
						displayHrs = "0" + displayHrs;
					}
					if(displayMin.length() == 1)	{
						displayMin = "0" + displayMin;
					}
					if(displaySec.length() == 1)	{
						displaySec = "0" + displaySec;
					}
		                stopwatchSecondsTimer.setText(displaySec);
		                stopwatchMinutesTimer.setText(displayMin);
		                stopwatchHoursTimer.setText(displayHrs);
	        }
	    )
	);
}