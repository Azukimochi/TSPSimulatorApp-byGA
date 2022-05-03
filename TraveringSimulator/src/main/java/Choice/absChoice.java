package Choice;

import java.util.List;

import instance.Genom;

public abstract interface absChoice {

	/**
	 * 選択アルゴリズムの適用
	 * @param genom 適用先の遺伝子郡
	 * @param popSize 選択遺伝子数
	 * @return 選択された遺伝子群
	 */
	public List<Genom> apply(List<Genom> genom, int popSize);
}
