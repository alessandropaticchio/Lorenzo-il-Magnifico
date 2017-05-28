package it.polimi.ingsw.GC_21.EFFECT;

import java.awt.Choice;
import java.util.Scanner;
import java.util.function.IntPredicate;

import org.junit.internal.Throwables;

import it.polimi.ingsw.GC_21.ACTION.Action;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.Item;
import it.polimi.ingsw.GC_21.GAMECOMPONENTS.Possession;
import it.polimi.ingsw.GC_21.PLAYER.Player;


public class Convert extends Immediate implements ToCallDuringCraft{
	private Possession toPay1;
	private Possession toTake1;
	private Possession toPay2;
	private Possession toTake2;
	
	public Convert(Possession rewards, Possession toPay1, Possession toTake1, Possession toPay2, Possession toTake2, int privileges) {
		super(rewards, privileges);
		this.toPay1 = toPay1;
		this.toTake1 = toTake1;
		this.toPay2 = toPay2;
		this.toTake2 = toTake2;
		if (this.toPay2.equals(new Possession())){
			this.toPay2=null;
		}
		if (this.toTake2.equals(new Possession())){
			this.toTake2=null;
		}
	}

	@Override
	public void activateEffect(Player player,Action action){
		super.activateEffect(player, action);
		if (toPay2!=null && toTake2!=null){
			if (this.chooseConversion(player)==true){
				if (toPay1.checkRequirements(player)==true)
					payAndEarn(player, toTake1, toPay1);
				else{
					System.out.println("You don't have enough resources to convert!");
				}
			}
			else{
					payAndEarn(player, toTake2, toPay2);
				}
				}
		else{
			if (toPay1.checkRequirements(player)==true)
				payAndEarn(player, toTake1, toPay1);
			else{
				System.out.println("You don't have enough resources to convert!");
				return;
			}
		}
	}

	public Possession getToPay1() {
		return toPay1;
	}


	public Possession getToTake1() {
		return toTake1;
	}


	public Possession getToPay2() {
		return toPay2;
	}


	public Possession getToTake2() {
		return toTake2;
	}


	public boolean chooseConversion(Player player) {
		Scanner in = new Scanner(System.in);
		System.out.println("Choose your conversion! Type 0 for Conversion 1, type 1 for Conversion 2!");
		int choice = in.nextInt();
		if (choice == 0) { return true; }
		if (choice == 1) { return false; }
		else {
			System.out.println("Invalid input, try again!");
			return chooseConversion(player);
		}
		
	}

	@Override
	public String toString() {
		if (toPay2!=null && toTake2!=null){
		{
		return "This effect gives the following reward=" + rewards.toString() + "]"+
		" It converts " + toPay1.toString() + " into " + toTake1.toString() + " or " + toPay2.toString() + " into " + toTake2.toString();}
		}
		else {
			return "This effect gives the following reward=" + rewards.toString() + "]" + 
					"It converts " + toPay1.toString() + " into" + toTake1.toString();
		}
		
		}
	

}