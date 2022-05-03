
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GenomAlgo.GA;
import instance.City;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GenomFileIO {

	/**
	 * 街の座標データ保存
	 * @param url 保存先パス
	 */
	public static void saveCityPos(File url) {

		try (FileWriter filewriter = new FileWriter(url)) {
			City[] cities = GA.getCity();
			for (City c : cities) {
				filewriter.write(c.getFileFormat());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 街の座標データ読み込み
	 * @param url 読み込み先パス
	 * @return 読み込みデータ
	 */
	public static int openCityPos(File url) {

		List<City> list = new ArrayList<City>();

		try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
			String line;
			String[] val;
			while ((line = reader.readLine()) != null) {
				if ((val = line.split(",")).length == 2) {
					int val0 = Integer.valueOf(val[0]);
					int val1 = Integer.valueOf(val[1]);
					if(val0 >= 0 && val0 <= 300 &&val1 >= 0 &&  val1 <= 300) {
						list.add(new City(val0, val1));
					}
					else {
						Alert alrt = new Alert(AlertType.INFORMATION); //アラートを作成
						alrt.setTitle("読み込みエラー");
						alrt.setHeaderText("入力データが範囲外です(0 <= x <= 300)");
						alrt.showAndWait(); //表示
						return 0;
					}


				} else {
					Alert alrt = new Alert(AlertType.INFORMATION); //アラートを作成
					alrt.setTitle("読み込みエラー");
					alrt.setHeaderText("CSVファイルの記述が間違っています");
					alrt.showAndWait(); //表示
					return 0;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		City[] cities = new City[list.size()];
		for (int i = 0; i < list.size(); i++)
			cities[i] = list.get(i);
		GA.LoadCity(cities);

		return cities.length;
	}

}
