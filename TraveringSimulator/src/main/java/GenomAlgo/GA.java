package GenomAlgo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import Enum.ChoiceType;
import Enum.CrossType;
import Enum.MutationType;
import instance.City;
import instance.Genom;
import util.Logger;

public class GA {

	private static Random rand = new Random();
	private static int gen  = 0;
	private static City[] Cities;
	private static List<Genom> BestGenom = new ArrayList<Genom>();
	private static List<Genom> WorstGenom = new ArrayList<Genom>();
	private static List<Genom> ParentGenomList;
	private static List<Genom> SaveGenomList = new ArrayList<Genom>();;
	private static List<Genom> nextGenGenomList;

	private static int GenNum; //実行世代数
	private static int popNum; //遺伝子数
	private static int crossProb; //交叉確率
	private static int mutateProb; //変異確率
	private static int eliteSize; //エリート数
	private static int tournamentSize; //トーナメント数
	private static boolean isEliteUse;
	private static CrossType crossType; //交叉タイプ
	private static MutationType mutationType; //変異タイプ
	private static ChoiceType choiceType; //選択タイプ
	/**
	 * Cityインスタンスの生成
	 * @param num 街の数
	 */
	public static void GenerateCity(int num) {

		Logger.Log("-----Generate City-----");
		Cities = new City[num];
		for (int i = 0; i < num; i++) {
			Cities[i] = new City();
			Logger.Log(Cities[i]);
		}
		Logger.Log("-------------------------");
	}
	/**
	 * ファイルから街データを読み込んだとき用
	 * @param cities 街の座標データ群
	 */
	public static void LoadCity(City[] cities) {
		Cities = cities;
	}
	/**
	 * 街データの取得用
	 * @return 保持している街データ
	 */
	public static City[] getCity() {
		return Cities;
	}
	/**
	 * リセットボタン押下時のパラメータ初期化
	 */
	public static void reset() {
		gen = 0;
	}
	/**
	 * 初期化
	 * @param cityNum 街の数
	 * @param gen 実行世代数
	 * @param pop 1世代の遺伝子数
	 * @param cross 交叉確率
	 * @param muta 変異確率
	 * @param elite エリート選択の可否
	 * @param torna トーナメント選択時のサイズ
	 * @param isEUse  エリート選択の可否
	 * @param crosstype 交叉の種類
	 * @param mutattype 変異の種類
	 * @param choicetype 選択の種類
	 */
	public static void initialize(int cityNum, int gen, int pop, int cross, int muta, int elite, int torna,
			boolean isEUse, CrossType crosstype, MutationType mutattype, ChoiceType choicetype) {
		BestGenom = new ArrayList<Genom>();
		WorstGenom = new ArrayList<Genom>();
		SaveGenomList = new ArrayList<Genom>();

		GenNum = gen;
		popNum = pop;
		crossProb = cross;
		mutateProb = muta;
		eliteSize = elite;
		tournamentSize = torna;
		isEliteUse = isEUse;
		crossType = crosstype;
		mutationType = mutattype;
		choiceType = choicetype;
		gen = 0;

		for (int i = 0; i < popNum; i++) {
			SaveGenomList.add(new Genom(cityNum));
		}
	}
	/**
	 * トーナメントの大きさの取得
	 * @return トーナメントの大きさ
	 */
	public static int getTournamentSize() {
		return tournamentSize;
	}
	/**
	 * 最悪遺伝子群の取得
	 * @return 最悪遺伝子群
	 */
	public static List<Genom> getWorstGenom() {
		return WorstGenom;
	}
	/**
	 * 最良遺伝子群の取得
	 * @return 最良遺伝子群
	 */
	public static List<Genom> getBestGenom() {
		return BestGenom;
	}
	/**
	 * 処理の実行
	 * @return 最良遺伝子群
	 */
	public static List<Genom> excute() {

		//世代実行数ぶん繰り返し処理
		for (int runTime = 0; runTime < GenNum; runTime++) {

			gen ++ ;
			int popSize = SaveGenomList.size();
			ParentGenomList = new ArrayList<Genom>(SaveGenomList);

			//交叉処理
			SaveGenomList.stream().parallel().forEach(s -> {
				if (rand.nextInt(100) < crossProb) {
					Genom[] tmp = crossType.getCrossOver()
							.apply(s, ParentGenomList.get(rand.nextInt(popSize)));
					ParentGenomList.add(tmp[0]);
					ParentGenomList.add(tmp[1]);
				}
			});
			//変異処理
			SaveGenomList.stream().parallel().forEach(s -> {
				if (rand.nextInt(100) < mutateProb) {
					ParentGenomList.add(mutationType.getMutation().apply(s));
				}
			});
			//選択処理
			ParentGenomList.removeAll(Collections.singleton(null));
			ParentGenomList = ParentGenomList.stream().sorted((y, x) -> (int) y.getDistance() - (int) x.getDistance()).collect(Collectors.toList());


			List<Genom> elitePop = new ArrayList<Genom>(ParentGenomList.subList(0, eliteSize));
			if (isEliteUse) {
				nextGenGenomList = choiceType.getChoice().apply(ParentGenomList, popNum - eliteSize);
				nextGenGenomList.addAll(elitePop);
			} else {
				nextGenGenomList = choiceType.getChoice().apply(ParentGenomList, popNum);
			}
			//次世代移行処理
			nextGenGenomList = nextGenGenomList.stream().sorted((y, x) -> (int) y.getDistance() - (int) x.getDistance()).collect(Collectors.toList());
			BestGenom.add(nextGenGenomList.get(0));
			WorstGenom.add(nextGenGenomList.get(nextGenGenomList.size()-1));

			System.out.println("gen:" + gen + " genSize:" + nextGenGenomList.size());
			System.out.println("BestGenom: " + ParentGenomList.get(0));
			System.out.println("WorstGenom" + ParentGenomList.get(ParentGenomList.size()-1));
			SaveGenomList = new ArrayList<Genom>(nextGenGenomList);
		}
		return BestGenom;
	}
}
