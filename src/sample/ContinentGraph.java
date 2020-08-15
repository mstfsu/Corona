package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class ContinentGraph implements Initializable {

    @FXML
    private StackedBarChart stackedBarChart;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
    private void loadData(){

        stackedBarChart.getData().addAll(getData());

    }
    private ObservableList<XYChart.Series<String,Integer>> getData(){

        ObservableList<XYChart.Series<String,Integer>> data= FXCollections.observableArrayList();
        XYChart.Series<String,Integer> asia = new XYChart.Series<>();
        XYChart.Series<String,Integer> europe = new XYChart.Series<>();
        XYChart.Series<String,Integer> ocenian = new XYChart.Series<>();
        XYChart.Series<String,Integer> america = new XYChart.Series<>();
        XYChart.Series<String,Integer> africa = new XYChart.Series<>();
        asia.setName("Asia");
        europe.setName("Europe");
        america.setName("America");
        africa.setName("Africa");
        ocenian.setName("Ocenian");

        for (int i = 0; i < Controller.dateArray.size() ; i++) {
            asia.getData().add(new XYChart.Data<>(Controller.dateArray.get(i),Controller.asia.get(i)));
            europe.getData().add(new XYChart.Data<>(Controller.dateArray.get(i),Controller.europe.get(i)));
            ocenian.getData().add(new XYChart.Data<>(Controller.dateArray.get(i),Controller.oceania.get(i)));
            america.getData().add(new XYChart.Data<>(Controller.dateArray.get(i),Controller.america.get(i)));
            africa.getData().add(new XYChart.Data<>(Controller.dateArray.get(i),Controller.africa.get(i)));

        }
        data.addAll(asia,europe,america,ocenian,africa);


        return  data;
    }
    public void GoBackTableView(ActionEvent actionEvent) throws Exception {
        Parent tableview = FXMLLoader.load(getClass().getResource("tableview.fxml"));
        Scene scene=new Scene(tableview,1050,700);

        Stage window=(Stage)((javafx.scene.Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
    public void GoBackDailyDeathsGraph(ActionEvent actionEvent) throws Exception {
        Parent tableview = FXMLLoader.load(getClass().getResource("ContinentGraphDeath.fxml"));
        Scene scene=new Scene(tableview,1300,800);
        Stage window=(Stage)((javafx.scene.Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
}
