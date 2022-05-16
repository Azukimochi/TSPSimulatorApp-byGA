package Enum;

import Mutation.NormalMutation;
import Mutation.ReverseMutation;
import Mutation.absMutation;

public enum MutationType {

	CHOICE_NORMAL("ノーマル", new NormalMutation()),
	CHOICE_REVERSE("逆位", new ReverseMutation());

	private String name;
	private absMutation mutation;
	private MutationType(String name, absMutation mu) {
		this.name = name;
		this.mutation = mu;
	}
	public boolean isMatch(String name) {
		if(this.name.equals(name))
			return true;
		else
			return false;
	}
	public absMutation getMutation() {
		return this.mutation;
	}
	public static MutationType anyMatch(String name) {

		switch(name) {
		case "ノーマル" : return MutationType.CHOICE_NORMAL;
		case "逆位" : return MutationType.CHOICE_REVERSE;
		default : return null;
		}
	}
	@Override
	public String toString() {
		return name;
	}

}
