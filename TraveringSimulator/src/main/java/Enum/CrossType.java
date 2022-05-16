package Enum;

import CrossOver.OnePointCrossOver;
import CrossOver.TwoPointCrossOver;
import CrossOver.absCrossOver;

public enum CrossType{
	CROSS_ONEPOINT("1点交叉", new OnePointCrossOver()),
	CROSS_TWOPOINT("2点交叉", new TwoPointCrossOver());

	private String name;
	private absCrossOver cross;
	private CrossType(String name, absCrossOver cross) {
		this.name = name;
		this.cross = cross;
	}
	public boolean isMatch(String name) {
		if(this.name.equals(name))
			return true;
		else
			return false;
	}
	public absCrossOver getCrossOver() {
		return this.cross;
	}
	public static CrossType anyMatch(String name) {
		switch(name) {
		case "1点交叉" : return CrossType.CROSS_ONEPOINT;
		case "2点交叉" : return CrossType.CROSS_TWOPOINT;
		default : return null;
		}
	}
	@Override
	public String toString() {
		return name;
	}
}
