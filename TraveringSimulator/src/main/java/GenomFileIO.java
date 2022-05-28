
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import GenomAlgo.GA;
import JsonObject.JsonCityDate;
import JsonObject.JsonGenomDate;
import instance.City;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Logger;

public class GenomFileIO {

	/**
	 * 街の座標データ保存
	 * 
	 * @param url 保存先パス
	 */
	public static void saveCityPos(File url) {

		if( ! url.toString().substring(url.toString().length() - 5).equals(".json"))
			url = new File( url + ".json");
		try (FileWriter filewriter = new FileWriter(url)) {
			JsonCityDate jcity = new JsonCityDate();
			jcity.cities = GA.getCity();
			jcity.citynum = jcity.cities.length;

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			String sjson = mapper.writeValueAsString(jcity);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 街の座標データ読み込み
	 * 
	 * @param url 読み込み先パス
	 * @return 読み込みデータ
	 */
	public static int openCityPos(File url) {

		JsonCityDate jcity = null;

		try (BufferedReader reader = new BufferedReader(new FileReader(url))) {

			StringBuilder json = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
			Logger.Log("-----Loded File----");
			Logger.Log(json);
			ObjectMapper mapper = new ObjectMapper();
			jcity = mapper.readValue(json.toString(), JsonCityDate.class);

			for (City c : jcity.cities) {
				if (!(0 <= c.getPosX() && c.getPosX() <= 300 && 0 <= c.getPosY() && c.getPosY() <= 300)) {
					Alert alrt = new Alert(AlertType.INFORMATION); // アラートを作成
					alrt.setTitle("読み込みエラー");
					alrt.setHeaderText("入力データが範囲外です(0 <= x <= 300)");
					alrt.showAndWait(); // 表示
					return 0;
				}
			}
			GA.LoadCity(jcity.cities);
		} catch (Exception e) {
			e.printStackTrace();
			Alert alrt = new Alert(AlertType.INFORMATION); // アラートを作成
			alrt.setTitle("読み込みエラー");
			alrt.setHeaderText("データの読み込みに失敗しました");
			alrt.showAndWait(); // 表示
		}
		return jcity.citynum;
	}

	public static void saveGenDate(File url, JsonGenomDate jdate) {

		if( ! url.toString().substring(url.toString().length() - 5).equals(".json"))
			url = new File( url + ".json");
		try (FileWriter filewriter = new FileWriter(url)) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			String sjson = mapper.writeValueAsString(jdate);
			System.out.println(sjson);

			filewriter.write(sjson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JsonGenomDate ppenGenDate(File url) {

		JsonGenomDate jsondate = null;

		try (BufferedReader reader = new BufferedReader(new FileReader(url))) {

			StringBuilder json = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
			ObjectMapper mapper = new ObjectMapper();
			jsondate = mapper.readValue(json.toString(), JsonGenomDate.class);
			
			if(jsondate.cities.length != jsondate.param_CityNum) 
				throw new Exception();
			if(jsondate.WorstGenom.length != jsondate.BestGenom.length && jsondate.BestGenom.length != jsondate.SaveGenom.length) 
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			Alert alrt = new Alert(AlertType.INFORMATION); // アラートを作成
			alrt.setTitle("読み込みエラー");
			alrt.setHeaderText("データの読み込みに失敗しました");
			alrt.showAndWait(); // 表示
		}
		return jsondate;
	}
}
