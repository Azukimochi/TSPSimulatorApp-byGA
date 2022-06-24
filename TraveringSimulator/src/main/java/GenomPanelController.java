
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import Enum.ChoiceType;
import Enum.CrossType;
import Enum.MutationType;
import GenomAlgo.GA;
import JsonObject.JsonGenom;
import JsonObject.JsonGenomDate;
import instance.GenDate;
import instance.Genom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import util.Logger;

public class GenomPanelController implements Initializable {

	List<Genom> genom = new ArrayList<Genom>();
	static GenerateCityImage cityImage;
	ChangeListener<String> changeGenNum = ((ObservableValue<? extends String> observable, String oldValue,
			String newValue) -> setValueToGA());
	ChangeListener<Boolean> changeLimit = ((ObservableValue<? extends Boolean> observable, Boolean oldValue,
			Boolean newValue) -> updateGraph());

	int sumGen = 0;
	@FXML
	LineChart<Integer, Double> chart;
	@FXML
	NumberAxis xAxis;
	@FXML
	NumberAxis yAxis;
	@FXML
	private ImageView cityPanel;
	@FXML
	private CheckBox check_Elite;
	@FXML
	private Button button_newCity;
	@FXML
	private Button button_Reset;
	@FXML
	private Button button_Initialize;
	@FXML
	private Button button_execute;
	@FXML
	private Button button_apply;
	@FXML
	private MenuItem menu_Open;
	@FXML
	private MenuItem menu_Save;
	@FXML
	private MenuItem menu_GenOpen;
	@FXML
	private MenuItem menu_GenSave;
	@FXML
	private MenuItem menu_SaveIMG;
	@FXML
	private MenuItem menu_OpenWindow;
	@FXML
	private ComboBox<String> box_CityNum;
	@FXML
	private ComboBox<String> box_GenNum;
	@FXML
	private ComboBox<String> box_tournamentSize;
	@FXML
	private ComboBox<String> box_eliteSize;
	@FXML
	private ComboBox<String> box_popNum;
	@FXML
	private ComboBox<String> box_cross;
	@FXML
	private ComboBox<String> box_mutation;
	@FXML
	private ComboBox<String> type_crossOver;
	@FXML
	private ComboBox<String> type_mutation;
	@FXML
	private ComboBox<String> type_choice;
	@FXML
	private Text infoText;
	@FXML
	private Text infoDist;

	/**
	 * ボタン内容の変更のリスナ
	 */
	public void setValueToGA() {
		GA.setProcessGenSize(Integer.valueOf(box_GenNum.getValue()));
	}
	/**
	 * グラフ描画更新用
	 */
	public void updateGraph() {
		if (GA.getBestGenom().size() != 0)
			chart.getData().setAll(
					GenomChartModel.addChartModel(GA.getBestGenom(), GA.getWorstGenom()));
	}
	/**
	 * ボタン：初期化
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cityPanel.setImage(new Image(GenerateCityImage.clearCityImage()));
		changeAllComponentStats(false);
		box_CityNum.setValue("3");
		box_GenNum.setValue("10");
		box_tournamentSize.setValue("5");
		box_eliteSize.setValue("1");
		box_popNum.setValue("100");
		box_cross.setValue("10");
		box_mutation.setValue("5");
		type_crossOver.setValue("1点交叉");
		type_mutation.setValue("ノーマル");
		type_choice.setValue("ルーレット");
		Logger.Log("init");
		infoText.setText("");
		infoDist.setText("");
		sumGen = 0;
		chart.setAnimated(false);
		button_Reset.setDisable(true);
		box_GenNum.valueProperty().addListener(changeGenNum);
		addTooltip(button_Initialize, "すべてを初期化します");
		addTooltip(button_Reset, "都市データを保持したまま遺伝子を初期化します");
		addTooltip(button_apply, "現在の設定を適用します");
		addTooltip(button_execute, "処理を実行します");
		addTooltip(button_newCity, "現在の設定で都市を生成します");
	}
	/**
	 * ボタン：都市生成
	 * @param event
	 */
	@FXML
	public void onButtonCity(ActionEvent event) {
		cityImage = new GenerateCityImage(Integer.valueOf(box_CityNum.getValue()));
		cityPanel.setImage(new Image(cityImage.Generate(null, false)));
		button_apply.setDisable(false);
		button_Reset.setDisable(false);
		menu_Save.setDisable(false);
		Logger.Log(event.getTarget().toString());
	}
	/**
	 * ボタン：リセット
	 * @param event
	 */
	@FXML
	public void onButtonReset(ActionEvent event) {
		System.out.println("----------Reset----------");
		changeAllComponentStats(false);
		box_CityNum.setDisable(true);
		button_newCity.setDisable(true);
		button_apply.setDisable(false);
		Logger.Log(event.getTarget().toString());
		cityPanel.setImage(new Image(cityImage.Generate(null, false)));
		chart.getData().setAll(GenomChartModel.addEmptyModel());
		GA.reset();
		GenomSubPanelController.deleteTableDate();
	}
	/**
	 * ボタン：初期化
	 * @param event
	 */
	@FXML
	public void onButtonInit(ActionEvent event) {
		System.out.println("----------Initialize----------");
		changeAllComponentStats(false);
		cityPanel.setImage(new Image(GenerateCityImage.clearCityImage()));
		Logger.Log(event.getTarget().toString());
		chart.getData().setAll(GenomChartModel.addEmptyModel());
		button_Reset.setDisable(true);
		GA.reset();
		GenomSubPanelController.deleteTableDate();
		box_CityNum.setValue("3");
		box_GenNum.setValue("10");
		box_tournamentSize.setValue("5");
		box_eliteSize.setValue("1");
		box_popNum.setValue("100");
		box_cross.setValue("10");
		box_mutation.setValue("5");
		type_crossOver.setValue("1点交叉");
		type_mutation.setValue("ノーマル");
		type_choice.setValue("ルーレット");
		check_Elite.setSelected(true);
	}
	/**
	 * ボタン：適用
	 * @param event
	 */
	@FXML
	public void onButtonApply(ActionEvent event) {
		GA.initialize(
				Integer.valueOf(box_CityNum.getValue()), 
				Integer.valueOf(box_GenNum.getValue()),
				Integer.valueOf(box_popNum.getValue()), 
				Integer.valueOf(box_cross.getValue()),
				Integer.valueOf(box_mutation.getValue()), 
				Integer.valueOf(box_eliteSize.getValue()),
				Integer.valueOf(box_tournamentSize.getValue()), 
				check_Elite.isSelected(),
				CrossType.anyMatch(type_crossOver.getValue()), 
				MutationType.anyMatch(type_mutation.getValue()),
				ChoiceType.anyMatch(type_choice.getValue()));
		changeAllComponentStats(true);
		button_newCity.setDisable(true);
		menu_GenSave.setDisable(false);
		Runtime r = Runtime.getRuntime();
		r.gc();
	}
	/**
	 * ボタン：実行
	 * @param event
	 */
	@FXML
	public void onButtonExcute(ActionEvent event) {
		genom = GA.excute();
		button_apply.setDisable(true);
		Logger.Log(event.getTarget().toString());

		cityPanel.setImage(new Image(cityImage.Generate(genom.get(genom.size() - 1), true)));
		chart.getData().setAll(GenomChartModel.addChartModel(genom, GA.getWorstGenom()));
		sumGen += Integer.valueOf(box_GenNum.getValue());
		infoText.setText("累積交配数：" + sumGen);
		infoDist.setText("距離：" + GA.getBestGenom().get(GA.getBestGenom().size() - 1).getDistance());

		GenomSubPanelController.deleteTableDate();
		List<Genom> bg = GA.getBestGenom();
		for (int i = 0; i < bg.size(); i++) {
			GenomSubPanelController.setTableDate(
					new GenDate(i + 1, bg.get(i).getEval(), bg.get(i).getDistance(), bg.get(i).getList().toString()));
		}
	}
	/**
	 * メニュー：都市を保存
	 * @param event
	 */
	@FXML
	public void onMenuSave(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ファイル保存");
		fileChooser.setInitialFileName("CityPos.json");
		File f = fileChooser.showSaveDialog(GenomMain.getStage());
		if (f != null)
			GenomFileIO.saveCityPos(f);
		Logger.Log(event.getTarget().toString());
	}
	/**
	 * メニュー：都市を開く
	 * @param event
	 */
	@FXML
	public void onMenuOpen(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ファイルを読み込む");
		fileChooser.getExtensionFilters().add(new ExtensionFilter(".JSONファイル", "*.json"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("All", "*.*"));
		File f = fileChooser.showOpenDialog(GenomMain.getStage());
		if (f != null) {
			int city = GenomFileIO.openCityPos(f);
			if (city != 0) {
				box_CityNum.setValue(String.valueOf(city));
				cityImage = new GenerateCityImage();
				cityPanel.setImage(new Image(cityImage.Generate(null, false)));
				button_apply.setDisable(false);
				menu_Save.setDisable(false);
				button_Reset.setDisable(false);
			}
		}
		Logger.Log(event.getTarget().toString());
	}
	/**
	 * メニュー：保存
	 * @param event
	 */

	@FXML
	public void onMenuGenSave(ActionEvent event) {
		JsonGenomDate jdate = new JsonGenomDate();

		jdate.cities = GA.getCity();
		jdate.param_CityNum = Integer.valueOf(box_CityNum.getValue());
		jdate.param_GenNum = Integer.valueOf(box_GenNum.getValue());
		jdate.param_PopNum = Integer.valueOf(box_popNum.getValue());
		jdate.param_Cross = Integer.valueOf(box_cross.getValue());
		jdate.param_Mutation = Integer.valueOf(box_mutation.getValue());
		jdate.type_Choice = ChoiceType.anyMatch(type_choice.getValue());
		jdate.param_TournamentSize = Integer.valueOf(box_tournamentSize.getValue());
		jdate.param_EliteSize = Integer.valueOf(box_eliteSize.getValue());
		jdate.type_Crossover = CrossType.anyMatch(type_crossOver.getValue());
		jdate.type_Mutation = MutationType.anyMatch(type_mutation.getValue());
		jdate.isUseElite = check_Elite.isSelected();

		List<JsonGenom> list = new LinkedList<JsonGenom>();
		for(Genom g : GA.getBestGenom()) {
			JsonGenom jg = new JsonGenom();
			jg.genom = g.getList().toArray(new Integer[g.getList().size()]);
			list.add(jg);
		}
		jdate.BestGenom =  list.toArray(new JsonGenom[list.size()]);
		list = new LinkedList<JsonGenom>();
		
		for(Genom g : GA.getWorstGenom()) {
			JsonGenom jg = new JsonGenom();
			jg.genom = g.getList().toArray(new Integer[g.getList().size()]);
			list.add(jg);
		}
		jdate.WorstGenom = list.toArray(new JsonGenom[list.size()]);
		list = new LinkedList<JsonGenom>();
		
		for(Genom g : GA.getSaveGenom()) {
			JsonGenom jg = new JsonGenom();
			jg.genom = g.getList().toArray(new Integer[g.getList().size()]);
			list.add(jg);
		}
		jdate.SaveGenom = list.toArray(new JsonGenom[list.size()]);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ファイル保存");
		fileChooser.setInitialFileName("GenomDate.json");
		File f = fileChooser.showSaveDialog(GenomMain.getStage());
		if (f != null)
			GenomFileIO.saveGenDate(f, jdate);
		Logger.Log(event.getTarget().toString());
	}
	/**
	 * メニュー：開く
	 * @param event
	 */
	@FXML
	public void onMenuGenOpen(ActionEvent event) {

		JsonGenomDate jdate;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ファイルを読み込む");
		fileChooser.getExtensionFilters().add(new ExtensionFilter(".JSONファイル", "*.json"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("All", "*.*"));
		File f = fileChooser.showOpenDialog(GenomMain.getStage());
		if (f != null) {
			jdate = GenomFileIO.ppenGenDate(f);

			changeAllComponentStats(false);

			GA.reset();
			GA.setCity(jdate.cities);
			GA.initialize(jdate.param_CityNum, jdate.param_GenNum, jdate.param_PopNum, jdate.param_Cross,
					jdate.param_Mutation, jdate.param_EliteSize, jdate.param_TournamentSize, jdate.isUseElite, jdate.type_Crossover,
					jdate.type_Mutation, jdate.type_Choice);
			
			List<Genom> list = new LinkedList<Genom>();
			for(JsonGenom g : jdate.WorstGenom) 
				list.add(new Genom(Arrays.asList(g.genom)));
			GA.setWorstGenom(list);
			list = new LinkedList<Genom>();
			for(JsonGenom g : jdate.BestGenom) 
				list.add(new Genom(Arrays.asList(g.genom)));
			GA.setBestGenom(list);
			list = new LinkedList<Genom>();
			for(JsonGenom g : jdate.SaveGenom)
				list.add(new Genom(Arrays.asList(g.genom)));
			GA.setSaveGenom(list);

			box_CityNum.setValue(String.valueOf(jdate.param_CityNum));
			box_GenNum.setValue(String.valueOf(jdate.param_GenNum));
			box_tournamentSize.setValue(String.valueOf(jdate.param_TournamentSize));
			box_eliteSize.setValue(String.valueOf(jdate.param_EliteSize));
			box_popNum.setValue(String.valueOf(jdate.param_PopNum));
			box_cross.setValue(String.valueOf(jdate.param_Cross));
			box_mutation.setValue(String.valueOf(jdate.param_Mutation));
			type_crossOver.setValue(jdate.type_Crossover.toString());
			type_mutation.setValue(jdate.type_Mutation.toString());
			type_choice.setValue(jdate.type_Choice.toString());
			check_Elite.setSelected(jdate.isUseElite);
			
			
			changeAllComponentStats(true);
			button_newCity.setDisable(true);
			menu_GenSave.setDisable(false);
			button_Reset.setDisable(false);
			button_apply.setDisable(true);
			Logger.Log(event.getTarget().toString());
			
			genom = GA.getBestGenom();
			chart.getData().setAll(GenomChartModel.addEmptyModel());
			cityImage = new GenerateCityImage();
			cityPanel.setImage(new Image(cityImage.Generate(genom.get(genom.size() - 1), true)));
			chart.getData().setAll(GenomChartModel.addChartModel(genom, GA.getWorstGenom()));
			
			GenomSubPanelController.deleteTableDate();
			List<Genom> bg = GA.getBestGenom();

			
			for (int i = 0; i < bg.size(); i++) {
				GenomSubPanelController.setTableDate(
						new GenDate(i + 1, bg.get(i).getEval(), bg.get(i).getDistance(), bg.get(i).getList().toString()));
			}
			sumGen = GA.getBestGenom().size();
			infoText.setText("累積交配数：" + sumGen);
			infoDist.setText("距離：" + GA.getBestGenom().get(GA.getBestGenom().size() - 1).getDistance());
		}
	}
	/**
	 * メニュー：都市画像を保存
	 * @param event
	 */
	@FXML
	public void onMenuSaveIMG(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("画像の保存場所");
		fileChooser.setInitialFileName("CityImage.png");
		File f = fileChooser.showSaveDialog(GenomMain.getStage());
		if (f != null) {
			if( ! f.toString().substring(f.toString().length() - 4).equals(".png"))
				f = new File(f + ".png");
			BufferedImage image = new GenerateCityImage().getCityBufferedImage();
			try {
				ImageIO.write(image, "png", f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * サブウインドウの表示
	 * @param event
	 */
	@FXML
	public void onOpenSubWindow(ActionEvent event) {
		GenomMain.openSubWindow();
	}
	/**
	 * エリート使用可否ボタン
	 * 現在未使用
	 * @param event
	 */
	@FXML
	public void changeElite(ActionEvent event) {
	}
	/**
	 * ツールチップの追加
	 * @param b ボタン
	 * @param str テキスト
	 */
	private void addTooltip(Button b, String str) {
		Tooltip t = new Tooltip(str);
		Tooltip.install(b, t);
	}
	/**
	 * コンポーネント有効/無効
	 * @param bool
	 */
	private void changeAllComponentStats(boolean bool) {
		sumGen = 0;
		infoText.setText("");
		box_CityNum.setDisable(bool);
		box_cross.setDisable(bool);
		box_eliteSize.setDisable(bool);
		box_mutation.setDisable(bool);
		box_popNum.setDisable(bool);
		box_tournamentSize.setDisable(bool);
		type_choice.setDisable(bool);
		type_mutation.setDisable(bool);
		type_crossOver.setDisable(bool);
		check_Elite.setDisable(bool);
		button_execute.setDisable(!bool);
		button_newCity.setDisable(bool);
		button_apply.setDisable(!bool);
		menu_Save.setDisable(!bool);
		menu_GenSave.setDisable(!bool);
		menu_SaveIMG.setDisable(!bool);
	}
}
