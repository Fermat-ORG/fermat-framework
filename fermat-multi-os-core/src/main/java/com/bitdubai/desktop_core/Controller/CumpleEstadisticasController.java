/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Person;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Mati
 */
public class CumpleEstadisticasController implements Initializable{

    @FXML
    private BarChart<String, Integer> grafBarras;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> nombreMeses = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
    }
    private void initialize() {
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        nombreMeses.addAll(Arrays.asList(months));

        xAxis.setCategories(nombreMeses);
    }

    /**
     * Setea las personas para las estidisticas.
     * 
     * @param persons
     */
    public void setPersonData(List<Person> persons) {
        // Cuenta el numero de personas que cumplen en cada mes.
        int[] monthCounter = new int[12];
        for (Person p : persons) {
            int month = p.getBirthday().getMonthValue() - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();

        // Crea un XYChart.Data object por cada mes y lo agrega al series
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<String, Integer>(nombreMeses.get(i), monthCounter[i]));
        }

        grafBarras.getData().add(series);
    }

    
}
