
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
import instance.Genom;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GenomFileIO {

	/**
	 * 街の座標データ保存
	 * 
	 * @param url 保存先パス
	 */
	public static void saveCityPos(File url) {

		try (FileWriter filewriter = new FileWriter(url)) {
			JsonCityDate jcity = new JsonCityDate();
			jcity.cityes = GA.getCity();
			jcity.citynum =jcity.cityes.length;

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			String sjson = mapper.writeValueAsString(jcity);
			filewriter.write(sjson);
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
			while((line = reader.readLine()) != null) {
				json.append(line);
			}
			ObjectMapper mapper = new ObjectMapper();
			jcity = mapper.readValue(json.toString(), JsonCityDate.class);
			
			for(City c : jcity.cityes) {
				if(0 <= c.getPosX() && c.getPosX() <= 300 && 0 <= c.getPosY() && c.getPosY() <= 300) {
					Alert alrt = new Alert(AlertType.INFORMATION); // アラートを作成
					alrt.setTitle("読み込みエラー");
					alrt.setHeaderText("入力データが範囲外です(0 <= x <= 300)");
					alrt.showAndWait(); // 表示
					return 0;
				}
			}
			GA.LoadCity(jcity.cityes);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jcity.citynum;
	}

	public static void saveGenDate(File url) {

		try /* (FileWriter filewriter = new FileWriter(url)) */ {

			JsonGenomDate json = new JsonGenomDate();
			json.citynum = 10;
			json.citypos = new City(50, 80);
			json.genom = new Genom(5);

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			String sjson = mapper.writeValueAsString(json);
			System.out.println(sjson);

			// City[] cities = GA.getCity();
			// for (City c : cities) {
			// filewriter.write(c.getFileFormat());
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
