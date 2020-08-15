package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Tableview implements Initializable {
    @FXML
    private TableView<Country> table_info;
    @FXML
    private TableColumn<Country, String> country_name;
    @FXML
    private TableColumn<Country, Integer> total_cases;
    @FXML
    private TableColumn<Country, Integer> new_cases;
    @FXML
    private TableColumn<Country, Integer> new_deaths;
    @FXML
    private TableColumn<Country, Integer> total_deaths;
    @FXML
    private TableColumn<Country, Integer> populations;
    @FXML
    private TableColumn<Country, Integer> mortality;
    @FXML
    private TableColumn<Country, Integer> attack_rate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        getDataUrl();
    }
    private void initTable(){
        initColums();
    }
    private void initColums(){
        country_name.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        total_cases.setCellValueFactory(new PropertyValueFactory<>("totalCase"));
        new_cases.setCellValueFactory(new PropertyValueFactory<>("newCase"));
        total_deaths.setCellValueFactory(new PropertyValueFactory<>("totalDeaths"));
        new_deaths.setCellValueFactory(new PropertyValueFactory<>("newDeath"));
        populations.setCellValueFactory(new PropertyValueFactory<>("population"));
        mortality.setCellValueFactory(new PropertyValueFactory<>("mortality"));
        attack_rate.setCellValueFactory(new PropertyValueFactory<>("attackRate"));

    }
    public void getDataUrl(){

        ObservableList<Country> countries= FXCollections.observableArrayList();
        for (int i = 0; i < Controller.countries.size() ; i++) {
            countries.add(Controller.countries.get(i));

        }

        table_info.setItems(countries);
    }
    public void goLineChart(ActionEvent actionEvent) throws Exception {
        Parent tableview = FXMLLoader.load(getClass().getResource("lineChart.fxml"));
        Scene scene=new Scene(tableview,2000,700);

        Stage window=(Stage)((javafx.scene.Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void goContinentChart(ActionEvent actionEvent) throws Exception {
        Parent tableview = FXMLLoader.load(getClass().getResource("ContinentGraph.fxml"));
        Scene scene=new Scene(tableview,1300,800);

        Stage window=(Stage)((javafx.scene.Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
