package it.polimi.ingsw.GC_21.fx;

import java.awt.Button;

import javax.xml.ws.handler.MessageContext;

import it.polimi.ingsw.GC_21.BOARD.Color;
import it.polimi.ingsw.GC_21.CLIENT.CheckColorMessage;
import it.polimi.ingsw.GC_21.CLIENT.ChooseActionMessage;
import it.polimi.ingsw.GC_21.CLIENT.MessageToClient;
import it.polimi.ingsw.GC_21.CONTROLLER.ControllerForm;
import it.polimi.ingsw.GC_21.VIEW.CreatePlayerInput;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorInput;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLColorController extends MetaController {

	private Color colorplayer = null ;
	private boolean start;
	private ColorThread colorThread;
	
	@FXML private Text texttarget;
	@FXML private Text welcometext;
	@FXML private ToggleGroup place;
	@FXML private ToggleButton blue;
	@FXML private ToggleButton red;
	@FXML private ToggleButton yellow;
	@FXML private ToggleButton green;
	
	@FXML
    public void initialize() {
        System.out.println("inizializzazione schermata colore");
        green.setAccessibleText(Color.Green.toString());
        blue.setAccessibleText(Color.Blue.toString());
        yellow.setAccessibleText(Color.Yellow.toString());
        red.setAccessibleText(Color.Red.toString());
        
        colorThread = new ColorThread(texttarget, client, this);
        colorThread.start();
        
    }
	

	 @FXML protected void Color(ActionEvent event){
	   //client.sendGUI("start"); //se sei l'host fa partire effettivamente la partita altrimenti ti fa andare sulla nuova schrmata senza eseguire il gioco	     
		ToggleButton button = (ToggleButton) place.getSelectedToggle();
		 colorplayer = Color.valueOf(button.getAccessibleText());
		 CreatePlayerInput createPlayerInput = new  CreatePlayerInput(colorplayer);
		 client2.setInputToSend(createPlayerInput);
		 CheckColorMessage checkColorMessage = (CheckColorMessage) client.getReceivedMessage();
		 if (!checkColorMessage.isResult()) {
			this.popup();
		} else if (checkColorMessage.isResult() && "Write 'start' when you want to start the game! \nYou must be 2 at least".equals(checkColorMessage.getDescription())) {
			System.out.println(checkColorMessage.getDescription());
			colorThread = new ColorThread(texttarget, client, this);
	        colorThread.start();
		} else {
	     this.gameScene();
		} 
	 }
	 
	 @FXML public void Ready(ActionEvent event) {
		System.out.println("sono nella ready");
		 if(colorplayer!=null) {
		 //occhio perchè se lo preme prima inizia comunque, c'è da mettere un controllo! LOCK
		 gameScene();
		 }
		 return;
	}
	 

	public synchronized void gameScene() {
		Stage stage = (Stage) welcometext.getScene().getWindow();
	        FXMLGame fxmlGame = new FXMLGame();
	        try {
				fxmlGame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	 
	public void popup() {
		
		
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("color already in use!");
			alert.setContentText(null);
			alert.showAndWait();
		
		
	} 
	
	
}
