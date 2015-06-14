/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Usuario
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DemoController implements Initializable {

    @FXML
    private Label myLabel1;
    @FXML
    private Label myLabel2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myLabel1.setText("Hola");
        myLabel2.setText("Mati :) , javaFX adentro");
    }

}
