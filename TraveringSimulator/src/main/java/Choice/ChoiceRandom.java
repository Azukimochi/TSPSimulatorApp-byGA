package Choice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import instance.Genom;

public class ChoiceRandom implements absChoice{

	@Override
	public List<Genom> apply(List<Genom> genom, int popSize) {

		List<Genom> reList = new ArrayList<Genom>();
		Random rand = new Random();

		while(reList.size() <= popSize) {
			reList.add(genom.get(rand.nextInt(genom.size())));
		}
		return reList;
	}
}
