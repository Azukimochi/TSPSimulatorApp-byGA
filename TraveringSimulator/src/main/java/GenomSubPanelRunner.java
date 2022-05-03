
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GenomSubPanelRunner extends Stage{

	public GenomSubPanelRunner(Stage stage) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("GenomSubMenu.fxml"));

			ScalableContentPane Sscene = new ScalableContentPane(root);
			Sscene.setAspectScale(false);
			Scene scene = new Scene(Sscene);
			this.setScene(scene);
			this.setTitle("Genom GenViewer");
			//this.setResizable(false);
			this.setResizable(true);
			this.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
