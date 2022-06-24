
import java.net.URL;
import java.util.ResourceBundle;

import instance.GenDate;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class GenomSubPanelController implements Initializable{
	private static ObservableList<GenDate> SeriesList = FXCollections.observableArrayList();

	@FXML
	private TableView<GenDate> table;
	@FXML
	private TableColumn<GenDate, Integer> ColumGen;
	@FXML
	private TableColumn<GenDate, String> ColumEval;
	@FXML
	private TableColumn<GenDate, String> ColumDist;
	@FXML
	private TableColumn<GenDate, String> ColumGenom;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		System.out.println("ロード");
		ColumGen.setCellValueFactory(new PropertyValueFactory<>("gen"));
		ColumEval.setCellValueFactory(new PropertyValueFactory<>("eval"));
		ColumDist.setCellValueFactory(new PropertyValueFactory<>("dist"));
		ColumGenom.setCellValueFactory(new PropertyValueFactory<>("genom"));

		ColumDist.setCellFactory(TextFieldTableCell.<GenDate>forTableColumn());
		ColumDist.setOnEditCommit(
		    (CellEditEvent<GenDate, String> t) -> {
		        ((GenDate) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setGenom(t.getNewValue());
		});
		ColumEval.setCellFactory(TextFieldTableCell.<GenDate>forTableColumn());
		ColumEval.setOnEditCommit(
		    (CellEditEvent<GenDate, String> t) -> {
		        ((GenDate) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setGenom(t.getNewValue());
		});
		ColumGenom.setCellFactory(TextFieldTableCell.<GenDate>forTableColumn());
		ColumGenom.setOnEditCommit(
		    (CellEditEvent<GenDate, String> t) -> {
		        ((GenDate) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setGenom(t.getNewValue());
		});
		ColumGen.setSortable(false);
		ColumDist.setSortable(false);
		ColumEval.setSortable(false);
		ColumGenom.setSortable(false);


		table.setEditable(true);
		table.setItems(SeriesList);
		SeriesList.addListener((ListChangeListener<GenDate> ) a -> {
			if(SeriesList.size() != 0 && table.getItems().size() != SeriesList.size()) {
				int length = SeriesList.size()-1;
				for(int i = 0; i < length; i++) {
					table.getItems().remove(0);
				}
				for(int i = 0; i < length; i++) {
					table.getItems().add(SeriesList.get(i));
				}
			}
		});
	}
	public static void setTableDate(GenDate gen) {
		SeriesList.add(gen);
	}
	public static void deleteTableDate() {

		if(SeriesList != null) {
			int size = SeriesList.size();
			for(int i = 0 ; i < size; i++)
				SeriesList.remove(0);
		}
	}
}
