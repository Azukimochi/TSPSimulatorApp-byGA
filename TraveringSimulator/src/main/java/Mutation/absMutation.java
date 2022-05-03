package Mutation;

import instance.Genom;

@FunctionalInterface
public abstract interface absMutation {

	/**
	 * 変異アルゴリズムを適用する
	 * @param genom 適用遺伝子
	 * @return 適用後遺伝子
	 */
	public abstract Genom apply(Genom genom);

}
