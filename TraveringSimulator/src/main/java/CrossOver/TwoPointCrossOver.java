package CrossOver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import instance.Genom;
import util.Logger;

public class TwoPointCrossOver implements absCrossOver{

	@Override
	public Genom[] apply(Genom Gone, Genom Gtwo) {
		Logger.Log("-----交叉前-----");
		Logger.Log(Gone.toString());
		Logger.Log(Gtwo.toString());

		Random rand = new Random();
		//1以上
		int cutPosBegin = rand.nextInt(Gone.getList().size()-1) + 1;
		int cutPosEnd = rand.nextInt(Gone.getList().size() - cutPosBegin) + cutPosBegin;

		Logger.Log("CutPos:" + cutPosBegin + ", EndPos:" + cutPosEnd);
		//切り取り入れ替え用配列 ディープコピーじゃないとConcurrentModificationException
		List<Integer> One = new ArrayList<Integer>(Gone.getList().subList(0, cutPosBegin));
		List<Integer> Two = new ArrayList<Integer>(Gtwo.getList().subList(0, cutPosBegin));
		List<Integer>tmpOne = new ArrayList<Integer>(Gone.getList().subList(cutPosBegin, cutPosEnd));
		List<Integer>tmpTwo = new ArrayList<Integer>(Gtwo.getList().subList(cutPosBegin, cutPosEnd));
		List<Integer> OneLast = new ArrayList<Integer>(Gone.getList().subList(cutPosEnd, Gone.getList().size()));
		List<Integer> TwoLast = new ArrayList<Integer>(Gtwo.getList().subList(cutPosEnd, Gone.getList().size()));

		One.addAll(tmpTwo);
		Two.addAll(tmpOne);
		One.addAll(OneLast);
		Two.addAll(TwoLast);

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
