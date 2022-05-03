
import java.util.ArrayList;
import java.util.List;

import instance.Genom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class GenomChartModel {

	private static ObservableList<Series<Integer, Double>> SeriesList = FXCollections.observableArrayList();
	private static Series<Integer, Double> series = new Series<>();
	private static Series<Integer, Double> series2 = new Series<>();

	/**
	 * Chart描画用データの生成
	 * @param goodgenom 最良値遺伝子データ
	 * @param badgenom  最悪値遺伝子データ
	 * @return Chart描画用データモデル
	 */
	public static List<Series<Integer, Double>> addChartModel(List<Genom> goodgenom, List<Genom> badgenom, boolean limit) {

		List<Genom> good = new ArrayList<Genom> (goodgenom);
		List<Genom> bad = new ArrayList<Genom> (badgenom);
		SeriesList = FXCollections.observableArrayList();
		series = new Series<>();
		series2 = new Series<>();

		series.setName("最良値");
		series2.setName("最悪値");

		if(limit && good.size() > 1000) {
			good = good.subList(good.size()-1000, good.size());
			bad = bad.subList(bad.size()-1000, bad.size());
		}
		int comp = (int) (good.size() / 500);
		for(int i = 0; i < good.size(); i ++) {

			if(good.size() > 1000) {
				if((i % comp) == 0 && i > 5) {
					Double s1avValue = good.get(i).getEval() + good.get(i-1).getEval()+ good.get(i-2).getEval() + good.get(i-3).getEval() + good.get(i-4).getEval() / 5.0;
					Double s2avValue = bad.get(i).getEval() + bad.get(i-1).getEval()+ bad.get(i-2).getEval() + bad.get(i-3).getEval() + bad.get(i-4).getEval()/ 5.0; 
					series.getData().add(new Data<Integer, Double>(i,s1avValue));
					series2.getData().add(new Data<Integer, Double>(i,s2avValue));
				}
			}
			else {
				series.getData().add(new Data<Integer, Double>(i,good.get(i).getEval()));
				series2.getData().add(new Data<Integer, Double>(i,bad.get(i).getEval()));
			}

		}
		SeriesList.add(series);
		SeriesList.add(series2);
		return SeriesList;
	}
	/**
	 * Chart初期化用
	 * @return 空のデータモデル
	 */
	public static List<Series<Integer, Double>> addEmptyModel() {
		SeriesList = FXCollections.observableArrayList();
		series = new Series<>();
		series2 = new Series<>();

		series.setName("最良値");
		series2.setName("最悪値");;

		SeriesList.add(series);
		SeriesList.add(series2);
		return SeriesList;
	}
}
