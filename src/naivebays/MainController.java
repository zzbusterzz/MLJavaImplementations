/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebays;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 1
 */
public class MainController implements Initializable {

    @FXML
    private TextField valueFirst;    
    @FXML
    private TextField valueSecond;    
    @FXML
    private TextField valueThird;    
    @FXML
    private TextField valueFourth;    
    @FXML
    private TextField valueFifth;    
    @FXML
    private TextField valueSixth;    
    @FXML
    private TextField valueSeventh;    
    @FXML
    private TextField valueEighth;    
    
    @FXML
    private Button predict;
    
    @FXML
    private TextArea output;    
    
    @FXML
    private TextField inputPath;    
    
    @FXML
    private Button findPath;    
    
    private Alert alert;
    private NaiveBaysLogic NBL = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new Alert(Alert.AlertType.NONE);
       
        
    }    
    
    @FXML
    protected void predict(ActionEvent event) {
        if(NBL != null){
            int pregnantTimes = GetValueFromField(valueFirst);
            float plasmaGlucoseConcentration = GetValueFromFieldFloat(valueSecond);
            float diastolicBP = GetValueFromFieldFloat(valueThird);
            float ticepsSkinfoldThicknes = GetValueFromFieldFloat(valueFourth);
            float serumInsulin = GetValueFromFieldFloat(valueFifth);        
            float bMI = GetValueFromFieldFloat(valueSixth);
            float diabetesPedigreeFunction = GetValueFromFieldFloat(valueSeventh);
            int age = GetValueFromField(valueEighth);

            if( pregnantTimes == -1 && plasmaGlucoseConcentration == -1 && diastolicBP == -1  && ticepsSkinfoldThicknes == -1 &&
                serumInsulin == -1 &&  bMI == -1 &&  diabetesPedigreeFunction == -1 && age == -1){
                DisplayWarning("Enter atleast one valid input");
            }else{
                String Predict = NBL.Predict(pregnantTimes, plasmaGlucoseConcentration, diastolicBP, ticepsSkinfoldThicknes, serumInsulin, bMI, diabetesPedigreeFunction, age);
                output.appendText(Predict);
            }        
        }else{
            DisplayWarning("Give a valid file and generate set");
        }
    }
    
    private int GetValueFromField(TextField textField) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException ex) {
            textField.setText("-1");
            // DisplayWarning("Invalid value in input field");
        }
        return -1;
    }
    
    private float GetValueFromFieldFloat(TextField textField) {
        try {
            return Float.parseFloat(textField.getText());
        } catch (NumberFormatException ex) {
             textField.setText("-1");
            //DisplayWarning("Invalid value in input field " + textField.getId());
        }
        return -1.0f;
    }
    
    public void DisplayWarning(String message) {
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setHeaderText(message);
        // show the dialog 
        alert.show();
    }
    
     public void DisplaySuccess(String message) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        // show the dialog 
        alert.show();
    }
    
    public void ReadAndCreateModule(ActionEvent event){
        
        Stage primaryStage = (Stage) findPath.getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open files directory");
        File defaultDirectory;
        defaultDirectory = new File(System.getProperty("user.home"));
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showOpenDialog(primaryStage);
        
        if(selectedDirectory != null ){
            if (selectedDirectory != null && selectedDirectory.getPath().contains(".csv")) {
                inputPath.setText(selectedDirectory.toString());
            }
            if (!selectedDirectory.isDirectory() && inputPath.getText().contains(".csv")) {
                NBL = new NaiveBaysLogic(inputPath.getText(), this);
                DisplaySuccess("File Loaded successfully");
            } else{
                DisplayWarning("Enter a valid csv file");
            }
        }
    }
}
