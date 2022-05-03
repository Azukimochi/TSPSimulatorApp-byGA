package Choice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import GenomAlgo.GA;
import instance.Genom;

public class ChoiceTournament implements absChoice{

	@Override
	public List<Genom> apply(List<Genom> genom, int popSize) {
		List<Genom> reList = new ArrayList<Genom>();
		Random rand = new Random();
		int TSize = GA.getTournamentSize();

		IntStream.range(0, popSize).forEach(s -> {
			List<Genom> list = new ArrayList<Genom>();
			for(int i = 0; i < TSize; i ++) {
				list.add(genom.get(rand.nextInt(genom.size())));
			}
			list = list.stream().sorted((y, x) -> (int) y.getDistance() - (int) x.getDistance()).collect(Collectors.toList());
			reList.add(list.get(0));
		});
		return reList;
	}
}
