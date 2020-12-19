 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptronnand;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;

/**
 *
 * @author 1
 */
public class MainController implements Initializable {
    
    @FXML
    private TextField weight1; 
    @FXML
    private TextField weight2; 
    @FXML
    private TextField bais; 
       
    @FXML
    private TextArea output; 
     
    private Perceptron per = null;
    private Alert alert;
    
    @FXML
    private void convergeWeight(ActionEvent event) {       
        Pair<Boolean, Double> w1InputValidW1 = isInputValidDouble(weight1);
        Pair<Boolean, Double> w1InputValidW2 = isInputValidDouble(weight2);
        Pair<Boolean, Float> w1InputValidBais = isInputValid(bais);
       
       if( w1InputValidW1.getKey() &&  w1InputValidW2.getKey() && w1InputValidBais.getKey()){
           if(w1InputValidBais.getValue() < 0.01f )
               DisplayWarning("Enter bais greater than equals 0.01");
           else if(w1InputValidBais.getValue() > 1)
               DisplayWarning("Enter bais less than equals 1");
           else
                per.Train(w1InputValidBais.getValue(), w1InputValidW1.getValue(), w1InputValidW2.getValue(), output);
       } else{
           if(!w1InputValidW1.getKey())
               DisplayWarning("Invalid input in Weight1");
           else if(!w1InputValidW2.getKey())
               DisplayWarning("Invalid input in Weight12");
           else
               DisplayWarning("Invalid input in Bais");
       }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new Alert(Alert.AlertType.NONE);
        per = new Perceptron();
    }    
    
    private Pair<Boolean, Float> isInputValid(TextField field){
        if(field.getText() != null && field.getText() != ""){
            String text = field.getText();
            try{
                return new Pair<Boolean, Float>( true,Float.parseFloat(text));
            }catch(NumberFormatException ex){ }
        }
        return new Pair<Boolean, Float>(false,0.0f);
    }
    
    private Pair<Boolean, Double> isInputValidDouble(TextField field){
        if(field.getText() != null && field.getText() != ""){
            String text = field.getText();
            try{
                return new Pair<Boolean, Double>( true,Double.parseDouble(text));
            }catch(NumberFormatException ex){ }
        }
        return new Pair<Boolean, Double>(false,0.0);
    }
    
    private void DisplayWarning(String message){
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setHeaderText(message);
        // show the dialog 
        alert.show();
    }
}
