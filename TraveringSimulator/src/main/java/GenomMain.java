

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GenomMain extends Application {

	private static Stage primaryStage;

	static {
		System.setProperty("-Dglass.win.minHiDPI", "1");
		System.out.println(System.getProperty("-Dglass.win.minHiDPI"));
		
	}
	public static void main(String[] args) {


		
		boolean geneLog = false;
		if (args.length == 0) {
			launch(args);
		} else {
			for (int index = 0; index < args.length; index++) {

				String str[] = args[index].split("=");
				if (str[0].equals("logsave") && str[1].equals("true")) {
					System.out.println("Log保存");
					geneLog = true;
				}
				if(str[0].equals("debug")&& str[1].equals("true")) {
					System.out.println("デバッグ出力");
					util.Logger.isEnabled(true);
				}
			}
			if (geneLog) {
				try (FileOutputStream fos = new FileOutputStream("log.txt")) {
					PrintStream ps = new PrintStream(fos);
					System.setOut(ps);
					launch(args);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			} else {
				launch(args);
			}
		}
	}

	@Override
	public void start(Stage primaryStage) {

		GenomMain.primaryStage = primaryStage;
		try {
			// FXMLのレイアウトをロード
			Parent root = FXMLLoader.load(getClass().getResource("GenomMain.fxml"));

			// タイトルセット
			primaryStage.setTitle("Traveling Problem Simulator");

			// シーン生成
			ScalableContentPane Sscene = new ScalableContentPane(root);
			Sscene.setAspectScale(false);
			Scene scene = new Scene(Sscene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setHeight(560);
			primaryStage.setWidth(800);
			primaryStage.setResizable(true);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void openSubWindow() {
		@SuppressWarnings("unused")
		GenomSubPanelRunner window = new GenomSubPanelRunner(primaryStage);
	}

	public static Stage getStage() {
		return primaryStage;
	}
}
