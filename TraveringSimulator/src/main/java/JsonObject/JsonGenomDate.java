package JsonObject;

import java.util.List;

import Enum.ChoiceType;
import Enum.CrossType;
import Enum.MutationType;
import instance.City;
import instance.Genom;

public class JsonGenomDate {
	
	public City[] cities;
	
	public List<Genom> BestGenom;
	public List<Genom> WorstGenom;
	public List<Genom> SaveGenomList;
	public int param_CityNum;
	public int param_GenNum; //実行あたり世代数
	public int param_PopNum; //染色体数

	public int param_Cross; //交叉率
	public int param_Mutation; //変異率

	public ChoiceType type_Choice;
	public int param_TournamentSize;
	public int param_EliteSize;
	
	public CrossType type_Crossover; //交叉タイプ
	public MutationType type_Mutation; //変異タイプ
	
	public City[] getCities() {
		return this.cities;
	}
	public int getCityNum() {
		return this.param_CityNum;
	}
	public int getGenNum() {
		return this.param_GenNum;
	}
	public int getPopNum() {
		return this.param_PopNum;
	}
	public int getCrossProb() {
		return this.param_Cross;
	}
	public int getMutaProb() {
		return this.param_Mutation;
	}
	public ChoiceType getChoiceType() {
		return this.type_Choice;
	}
	public int getTournamentSize() {
		return this.param_TournamentSize;
	}
	public int getEliteSize() {
		return this.param_EliteSize;
	}
	public CrossType getCrossType() {
		return this.type_Crossover;
	}
	public MutationType getMutationType() {
		return this.type_Mutation;
	}
	public List<Genom> getBestGen() {
		return this.BestGenom;
	}
	public List<Genom> getWorstGen() {
		return this.WorstGenom;
	}
	public List<Genom> getSaveGen() {
		return this.SaveGenomList;
	}
}