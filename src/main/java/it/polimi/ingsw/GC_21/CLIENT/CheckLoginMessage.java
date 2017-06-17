package it.polimi.ingsw.GC_21.CLIENT;

import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.GC_21.view.InputForm;
import it.polimi.ingsw.GC_21.view.LobbyInput;

public class CheckLoginMessage extends MessageToClient {
	private ArrayList<String> games;

	public CheckLoginMessage(boolean result, String string, ArrayList<String> games) {
		super(result, string);
		this.games = games;
	}
	
	@Override
	public InputForm executeCLI(Scanner keyboard) {
		super.executeCLI(keyboard);
		LobbyInput lobbyInput = new LobbyInput();
		lobbyInput.chooseGame(keyboard);
		return lobbyInput;
	}
	
	
	public ArrayList<String> getGames() {
		return games;
	}

	public void setGames(ArrayList<String> games) {
		this.games = games;
	}

	

	

	
	
	

}