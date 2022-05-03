package Mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import instance.Genom;
import util.Logger;

public class ReverseMutation implements absMutation {

	@Override
	public Genom apply(Genom genom) {
		Random rand = new Random();

		Logger.Log("-----変異前-----");
		Logger.Log(genom.toString());

		int pointOne = rand.nextInt(genom.getList().size() - 1) ;
		int pointTwo = rand.nextInt(genom.getList().size() - pointOne - 1) + pointOne + 1;

		Logger.Log("point one : two " + pointOne + " : " + pointTwo);

		List<Integer> foward = new ArrayList<Integer>(genom.getList().subList(0, pointOne));
		List<Integer> sub = new ArrayList<Integer>(genom.getList().subList(pointOne, pointTwo));
		List<Integer> back = new ArrayList<Integer>(genom.getList().subList(pointTwo, genom.getList().size()));
		Collections.reverse(sub);
		List<Integer> Genom = new ArrayList<Integer> (foward);
		Genom.addAll(sub);
		Genom.addAll(back);

 		Genom newGenom = new Genom(Genom);
		Logger.Log("-----変異後-----");
		Logger.Log(newGenom);

		return newGenom;
	}

}
