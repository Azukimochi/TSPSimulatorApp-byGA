

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import Enum.ChoiceType;
import Enum.CrossType;
import Enum.MutationType;
import GenomAlgo.GA;
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
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import util.Logger;

public class GenomPanelController implements Initializable {

	List<Genom> genom = new ArrayList<Genom>();
	static GenerateCityImage cityImage;
	ChangeListener<String> changeCity = ((ObservableValue<? extends String> observable, String oldValue,
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
	private MenuItem menu_SaveIMG;
	@FXML
	private MenuItem menu_OpenWindow;
	@FXML
	private RadioMenuItem radio_Limit;
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

	public void setValueToGA() {
		button_apply.setDisable(true);
		Logger.Log("wasChanged");
	}

	public void updateGraph() {
		if (GA.getBestGenom().size() != 0)
			chart.getData().setAll(
					GenomChartModel.addChartModel(GA.getBestGenom(), GA.getWorstGenom(), radio_Limit.isSelected()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cityPanel.setImage(new Image(GenerateCityImage.clearCityImage()));
		componentChangeEnable(false);
		box_CityNum.setValue("3");
		box_GenNum.setValue("10");
		box_tournamentSize.setValue("5");
		box_eliteSize.setValue("3");
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
		box_CityNum.valueProperty().addListener(changeCity);
		radio_Limit.selectedProperty().addListener(changeLimit);
	}

	@FXML
	public void onButtonCity(ActionEvent event) {
		cityImage = new GenerateCityImage(Integer.valueOf(box_CityNum.getValue()));
		cityPanel.setImage(new Image(cityImage.Generate(null, false)));
		button_apply.setDisable(false);
		button_Reset.setDisable(false);
		menu_Save.setDisable(false);
		Logger.Log(event.getTarget().toString());
	}

	@FXML
	public void onButtonReset(ActionEvent event) {
		System.out.println("----------Reset----------");
		componentChangeEnable(false);
		box_CityNum.setDisable(true);
		button_newCity.setDisable(true);
		button_apply.setDisable(false);
		Logger.Log(event.getTarget().toString());
		cityPanel.setImage(new Image(cityImage.Generate(null, false)));
		chart.getData().setAll(GenomChartModel.addEmptyModel());
		GA.reset();
		GenomSubPanelController.deleteTableDate();
	}

	@FXML
	public void onButtonInit(ActionEvent event) {
		System.out.println("----------Initialize----------");
		componentChangeEnable(false);
		cityPanel.setImage(new Image(GenerateCityImage.clearCityImage()));
		Logger.Log(event.getTarget().toString());
		chart.getData().setAll(GenomChartModel.addEmptyModel());
		button_Reset.setDisable(true);
		GA.reset();
		GenomSubPanelController.deleteTableDate();
	}

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
		componentChangeEnable(true);
		button_newCity.setDisable(true);
		Runtime r = Runtime.getRuntime();
		r.gc();
	}

	@FXML
	public void onButtonExcute(ActionEvent event) {
		genom = GA.excute();
		button_apply.setDisable(true);
		Logger.Log(event.getTarget().toString());

		cityPanel.setImage(new Image(cityImage.Generate(genom.get(genom.size() - 1), true)));
		chart.getData().setAll(GenomChartModel.addChartModel(genom, GA.getWorstGenom(), radio_Limit.isSelected()));
		sumGen += Integer.valueOf(box_GenNum.getValue());
		infoText.setText("累積交配数：" + sumGen);
		infoDist.setText("距離：" + GA.getBestGenom().get(GA.getBestGenom().size()-1).getDistance());

		GenomSubPanelController.deleteTableDate();
		List<Genom> bg = GA.getBestGenom();
		for(int i = 0; i < bg.size(); i++) {
			GenomSubPanelController.setTableDate(new GenDate(i+1, bg.get(i).getEval(), bg.get(i).getList().toString()));
		}
	}

	@FXML
	public void onMenuSave(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ファイル保存");
		fileChooser.setInitialFileName("CityPos.csv");
		File f = fileChooser.showSaveDialog(GenomMain.getStage());
		if (f != null)
			GenomFileIO.saveCityPos(f);
		Logger.Log(event.getTarget().toString());
	}
	@FXML
	public void onMenuSaveIMG(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("画像の保存場所");
		fileChooser.setInitialFileName("CityImage.png");
		File f = fileChooser.showSaveDialog(GenomMain.getStage());
		if (f != null) {
			BufferedImage image = new GenerateCityImage().getCityBufferedImage();
			try {
				ImageIO.write(image, "png", f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	public void onMenuOpen(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ファイルを読み込む");
		fileChooser.getExtensionFilters().add(new ExtensionFilter(".CSVファイル", "*.csv"));
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
			}
		}
		Logger.Log(event.getTarget().toString());
	}
	@FXML
	public void onOpenSubWindow(ActionEvent event) {
		GenomMain.openSubWindow();
	}

	@FXML
	public void changeElite(ActionEvent event) {
	}

	private void componentChangeEnable(boolean bool) {
		sumGen = 0;
		infoText.setText("");
		box_CityNum.setDisable(bool);
		box_cross.setDisable(bool);
		box_eliteSize.setDisable(bool);
		box_GenNum.setDisable(bool);
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
		menu_Open.setDisable(bool);
		menu_SaveIMG.setDisable(!bool);
	}
}
