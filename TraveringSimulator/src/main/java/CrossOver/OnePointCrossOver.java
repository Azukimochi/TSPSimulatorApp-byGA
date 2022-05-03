package CrossOver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import instance.Genom;
import util.Logger;

public class OnePointCrossOver implements absCrossOver{

	@Override
	public Genom[] apply(Genom Gone, Genom Gtwo) {

		Logger.Log("-----交叉前-----");
		Logger.Log(Gone.toString());
		Logger.Log(Gtwo.toString());

		Random rand = new Random();
		//1以上
		int cutPos = rand.nextInt(Gone.getList().size()-1) + 1;

		Logger.Log("CutPos:" + cutPos);
		//切り取り入れ替え用配列 ディープコピーじゃないとConcurrentModificationException
		List<Integer> One = new ArrayList<Integer>(Gone.getList().subList(0, cutPos));
		List<Integer> Two = new ArrayList<Integer>(Gtwo.getList().subList(0, cutPos));
		List<Integer>tmpOne = new ArrayList<Integer>(Gone.getList().subList(cutPos, Gone.getList().size()));
		List<Integer>tmpTwo = new ArrayList<Integer>(Gtwo.getList().subList(cutPos, Gone.getList().size()));

		One.addAll(tmpTwo);
		Two.addAll(tmpOne);

		Logger.Log("-----入れ替え後-----");
		Logger.Log(One.toString());
		Logger.Log(Two.toString());

		One = new ArrayList<Integer>(new LinkedHashSet<>(One));
		Two = new ArrayList<Integer>(new LinkedHashSet<>(Two));

		Logger.Log("-----重複削除後-----");
		Logger.Log(One.toString());
		Logger.Log(Two.toString());

		for(int value = 0; value < Gone.getList().size(); value++) {
			if(One.indexOf(value) == -1)
				One.add(value);
			if(Two.indexOf(value) == -1)
				Two.add(value);
		}
		Logger.Log("-----重複補完後-----");
		Logger.Log(new Genom(One));
		Logger.Log(new Genom(Two));
		return new Genom[] {new Genom(One), new Genom(Two)};
	}
}
