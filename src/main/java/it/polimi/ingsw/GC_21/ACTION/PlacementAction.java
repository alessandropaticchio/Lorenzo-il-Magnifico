package it.polimi.ingsw.GC_21.ACTION;

import java.util.ArrayList;
import java.util.PrimitiveIterator.OfDouble;

import org.omg.PortableServer.Servant;

import it.polimi.ingsw.GC_21.BOARD.ActionSpace;
import it.polimi.ingsw.GC_21.BOARD.Board;
import it.polimi.ingsw.GC_21.EFFECT.ToCallBeforePlacement;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.Possession;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.Servants;
import it.polimi.ingsw.GC_21.PLAYER.FamilyMember;
import it.polimi.ingsw.GC_21.PLAYER.Player;

public class PlacementAction extends Action {

	protected int actionValue;
	protected final FamilyMember selectedFamilyMember;
	protected  ActionSpace selectedActionSpace;
	protected final Servants servantsToConvert;
	protected Possession discount;
	protected Possession overcharge;
	protected final Board board;
	
	public PlacementAction(Player playerInAction, int actionValue, FamilyMember selectedFamilyMember, ActionSpace selectedActionSpace, Servants servantsToConvert, Board board) {
		super(playerInAction);
		this.actionValue = actionValue;
		this.selectedFamilyMember = selectedFamilyMember;
		this.selectedActionSpace = selectedActionSpace;
		this.servantsToConvert = servantsToConvert;
		this.board = board;
		this.discount = new Possession();
		this.overcharge = new Possession();
	}
	
	@Override
	public void Execute() {
		callBeforePlacementEffects();
		convertServant(servantsToConvert);
	    place();
	    selectedActionSpace.callSpaceEffect(playerInAction, this);
		}
	
	private void callBeforePlacementEffects() {
		if (!playerInAction.getMyPersonalBoard().getToCallBeforePlacementEffects().isEmpty()){
			int size = playerInAction.getMyPersonalBoard().getToCallBeforePlacementEffects().size();
			ArrayList<ToCallBeforePlacement> effects = playerInAction.getMyPersonalBoard().getToCallBeforePlacementEffects();
			for (int i = 0; i < size; i++) {
				effects.get(i).activateEffect(playerInAction, this);
			}
		}
	}

	@Override
	public boolean checkAction() {
		return checkPlaceRequirement();
	}
	
	public boolean checkPlaceRequirement(){
		return checkDiceRequirement() &&
			   !checkBusyActionSpace() &&
			   !checkBusyFamiliyMember() &&
			   !checkOtherFamilyMember();
			   
	}
	
	public boolean checkOtherFamilyMember() {
		return false;
	}


	public void convertServant(Servants servants) {
		this.actionValue += servants.getValue();
		playerInAction.getMyPersonalBoard().getMyPossession().subtractItemToPossession(servants);
	}
	
	public boolean checkBusyFamiliyMember() {
		return selectedFamilyMember.isPlaced();
	}
	public boolean checkBusyActionSpace() {
		return selectedActionSpace.isBusy();
	}

	public boolean checkDiceRequirement() {
		return selectedActionSpace.getRequiredDice() <= (this.actionValue + servantsToConvert.getValue());
	}
	
	

	public void place() {
		if (selectedFamilyMember != null){
			selectedActionSpace.placeFamilyMember(selectedFamilyMember);
			selectedFamilyMember.setPlaced(true);
			
		}
		
		
		
	}

	public Possession getDiscount() {
		return discount;
	}

	public void setDiscount(Possession discount) {
		this.discount = discount;
	}

	public Possession getOvercharge() {
		return overcharge;
	}

	public void setOvercharge(Possession overcharge) {
		this.overcharge = overcharge;
	}

	public Servants getServantsToConvert() {
		return servantsToConvert;
	}

	public Board getBoard() {
		return board;
	}

	public void setSelectedActionSpace(ActionSpace selectedActionSpace) {
		this.selectedActionSpace = selectedActionSpace;
	}

	public int getActionValue() {
		return actionValue;
	}
	
	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
	}


	public FamilyMember getSelectedFamilyMember() {
		return selectedFamilyMember;
	}

	public ActionSpace getSelectedActionSpace() {
		return selectedActionSpace;
	}
	
	@Override
	public String toString() {
		String string = "null";
		if (selectedActionSpace.getActionSpaceEffect() != null) {
			string = selectedActionSpace.getActionSpaceEffect().getRewards().toString();
		}
		return  "PLACEMENT " + super.toString() + "\n{" + selectedFamilyMember.toString() + "\nAction Value=" + actionValue +
				"\nSpace Effect=" + string + "\nDiscount=" + discount.toString() + "\nOvercharge=" + overcharge.toString();
	}
	
	@Override
	public String checkToString() {
		return  "Check Dice Requirement=" + checkDiceRequirement() + "\nCheck Not Busy Family Member=" + !checkBusyFamiliyMember() + 
				"\nCheck Not Busy Action Space=" + !checkBusyActionSpace() + "\nCheck Not Other My Family Member=" + !checkOtherFamilyMember();
				
	}

}