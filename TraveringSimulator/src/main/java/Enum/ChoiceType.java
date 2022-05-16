package Enum;

import Choice.ChoiceRandom;
import Choice.ChoiceRoulette;
import Choice.ChoiceTournament;
import Choice.absChoice;

public enum ChoiceType {

	CHOICE_TOURNAMENT("トーナメント", new ChoiceTournament()),
	CHOICE_ROULETTE("ルーレット", new ChoiceRoulette()),
	CHOICE_RANDOM("ランダム", new ChoiceRandom());

	private String name;
	private absChoice choice;
	private ChoiceType(String name, absChoice choice) {
		this.name = name;
		this.choice = choice;
	}
	public boolean isMatch(String name) {
		if(this.name.equals(name))
			return true;
		else
			return false;
	}
	public absChoice getChoice() {
		return this.choice;
	}
	public static ChoiceType anyMatch(String name) {

		switch(name) {
		case "トーナメント" : return ChoiceType.CHOICE_TOURNAMENT;
		case "ルーレット" : return ChoiceType.CHOICE_ROULETTE;
		case "ランダム" : return ChoiceType.CHOICE_RANDOM;
		default : return null;
		}
	}
	public String toString() {
		return this.name;
	}

}
