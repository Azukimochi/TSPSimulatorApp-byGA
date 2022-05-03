package CrossOver;

import instance.Genom;

@FunctionalInterface
public abstract interface absCrossOver {

	/**
	 * 交叉アルゴリズムの適用
	 * @param one 適用遺伝子1
	 * @param two 適用遺伝子2
	 * @return 適用後の遺伝子配列
	 */
	public abstract Genom[] apply(Genom one, Genom two);


}
