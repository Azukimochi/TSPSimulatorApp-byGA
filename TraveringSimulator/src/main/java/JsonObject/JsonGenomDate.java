package JsonObject;

import Enum.ChoiceType;
import Enum.CrossType;
import Enum.MutationType;
import instance.City;

public class JsonGenomDate {
	
	public City[] cities;
	
	public JsonGenom[] BestGenom;
	public JsonGenom[] WorstGenom;
	public JsonGenom[] SaveGenom;
	public int param_CityNum;
	public int param_GenNum; //実行あたり世代数
	public int param_PopNum; //染色体数

	public int param_Cross; //交叉率
	public int param_Mutation; //変異率

	public ChoiceType type_Choice;
	public boolean isUseElite;
	public int param_TournamentSize;
	public int param_EliteSize;
	
	public CrossType type_Crossover; //交叉タイプ
	public MutationType type_Mutation; //変異タイプ
}