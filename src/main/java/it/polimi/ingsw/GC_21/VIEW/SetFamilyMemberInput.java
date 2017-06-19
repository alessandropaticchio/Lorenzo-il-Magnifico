package it.polimi.ingsw.GC_21.VIEW;

import java.util.Scanner;

import it.polimi.ingsw.GC_21.GAMEMANAGEMENT.SetFamilyMemberController;
import it.polimi.ingsw.GC_21.PLAYER.FamilyMemberColor;
import it.polimi.ingsw.GC_21.PLAYER.Player;

public class SetFamilyMemberInput extends InputForm{
	private int newFamilyMemberValue;
	private Player player;
	private FamilyMemberColor selectedFamilyMember;
	

	
	public SetFamilyMemberInput(int newFamilyMemberValue, Player player) {
		this.newFamilyMemberValue = newFamilyMemberValue;
		this.player = player;
	}

	public int getNewFamilyMemberValue() {
		return newFamilyMemberValue;
	}

	public void setNewFamilyMemberValue(int newFamilyMemberValue) {
		this.newFamilyMemberValue = newFamilyMemberValue;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void execute(RemoteView remoteView) {
		super.execute(remoteView);
		SetFamilyMemberController setFamilyMemberMessage = new SetFamilyMemberController(newFamilyMemberValue, player, selectedFamilyMember);
		remoteView.notifyController(setFamilyMemberMessage);
	}
	
	
	@Override
	public void inputFromCli(Scanner keyboard) {
		PlacementInput placementInput = new PlacementInput();
		placementInput.chooseFamilyMember(keyboard);
		selectedFamilyMember = placementInput.getFamilyMemberColor();
	}
	
	
}