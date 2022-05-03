package Choice;

import java.util.ArrayList;
import java.util.List;

import instance.Genom;

public class ChoiceRoulette implements absChoice{

	@Override
	public List<Genom> apply(List<Genom> genom, int popSize) {

		List<Genom> reList = new ArrayList<Genom>();

		for (int i = 0; i < popSize; i++) {
			//評価値の合計
			double sum = genom.stream().mapToDouble(s -> s.getEval()).sum();
			//正規化配列
			double[] normalizedEval = genom.stream().mapToDouble(s -> s.getEval() / sum).toArray();

			//ランダム値 > 配列の最初からの評価値合計になったらブレイク
			double rand = Math.random(); //選択
			double dSum = 0;
			int count = 0;
			for (double nEval : normalizedEval) {
				dSum += nEval;
				if (dSum >= rand) {
					break;
				}
				count++;
			}
			//選択された遺伝子を親遺伝子に追加
			reList.add(genom.get(count));
			genom.remove(count);
		}
		return reList;
	}

}
