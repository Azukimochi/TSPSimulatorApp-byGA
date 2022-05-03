package Mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import instance.Genom;
import util.Logger;

public class NormalMutation implements absMutation {

	@Override
	public Genom apply(Genom genom) {

		List<Integer> Genom = new ArrayList<Integer>(genom.getList());
		Random rand = new Random();

		Logger.Log("-----変異前-----");
		Logger.Log(genom.toString());

		int pointOne = rand.nextInt(genom.getList().size() - 1) ;
		int pointTwo = rand.nextInt(genom.getList().size() - pointOne - 1) + pointOne + 1;

		Logger.Log("point one : two " + pointOne + " : " + pointTwo);
		int tmp = Genom.get(pointOne);
		Genom.set(pointOne, Genom.get(pointTwo));
		Genom.set(pointTwo, tmp);

		Genom newGenom = new Genom(Genom);
		Logger.Log("-----変異後-----");
		Logger.Log(newGenom);

		return newGenom;
	}

}
