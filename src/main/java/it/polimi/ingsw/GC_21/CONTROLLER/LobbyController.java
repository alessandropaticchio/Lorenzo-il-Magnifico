package it.polimi.ingsw.GC_21.CONTROLLER;

import java.util.ArrayList;

import it.polimi.ingsw.GC_21.CLIENT.CheckLobbyMessage;
import it.polimi.ingsw.GC_21.CLIENT.CheckLoginMessage;
import it.polimi.ingsw.GC_21.GAMEMANAGEMENT.Game;
import it.polimi.ingsw.GC_21.VIEW.RemoteView;

public class LobbyController extends ControllerForm {
	private ControllerManager controllerManager;
	private RemoteView remoteView;
	private Game modelGame;
	private boolean create;
	private int joined;
	
	public LobbyController(boolean create, int joined) {
		this.create = create;
		this.joined = joined;
	}
	
	@Override
	public boolean executeController() {
		controllerManager = controller.getControllerManager();
		remoteView  = controller.getRemoteView();
		controllerManager.addRemoteView(remoteView);
		modelGame = controller.getModelGame();
		CheckLobbyMessage checkLobbyMessage;
		if(create) {
	        modelGame = controllerManager.createGame(controller);
	        checkLobbyMessage = new CheckLobbyMessage(true, "Lobby ok"); 
			modelGame.attach(remoteView);
			remoteView.setGame(modelGame);

	    } 
	    else if (joined <=  controllerManager.getGames().size() && joined > 0) {
	    	 modelGame = controllerManager.getGames().get(joined-1); //take the selected game
	    	 modelGame.attach(remoteView);
	    	 remoteView.setGame(modelGame);
		     checkLobbyMessage = new CheckLobbyMessage(true, "Lobby ok"); 
		     
		}
	    else {
	         checkLobbyMessage = new CheckLobbyMessage(false, "Your game choice is not valid"); 
		}  
		
		remoteView.getAdapter().sendObject(checkLobbyMessage);
        remoteView.inputObject();
	      
	       
		return true;
	}
	
}