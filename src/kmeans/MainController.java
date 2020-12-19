/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
//https://stackoverflow.com/questions/17035478/javafx-propertyvaluefactory-not-populating-tableview
//https://www.youtube.com/watch?v=SnAcSCcz0Sw
//https://sites.google.com/site/dataclusteringalgorithms/k-means-clustering-algorithm
//https://www.youtube.com/watch?v=JSJIolgFYqw&feature=youtu.be 
//Let  X = {x1,x2,x3,……..,xn} be the set of data points and V = {v1,v2,…….,vc} be the set of centers.
//
//1) Randomly select ‘c’ cluster centers.
//
//2) Calculate the distance between each data point and cluster centers.
//
//3) Assign the data point to the cluster center whose distance from the cluster center is minimum of all the cluster centers..
//
//4) Recalculate the new cluster center using: 
//
//
//where, ‘ci’ represents the number of data points in ith cluster.
//
//
//5) Recalculate the distance between each data point and new obtained cluster centers.
//
//6) If no data point was reassigned then stop, otherwise repeat from step 3).
/**
 * FXML Controller class
 *
 * @author 1
 */
public class MainController implements Initializable {

    @FXML
    private TableView<TableData> clusterTableData;
    @FXML
    private TableView<TableData> clusterTablePoints;

    @FXML
    private TextField inputFieldData;
    @FXML
    private TextField inputFieldClusterPoint;

    @FXML
    private Button addToData;
    @FXML
    private Button removeFromData;

    @FXML
    private Button addToClusterPoint;
    @FXML
    private Button removeFromClusterPoint;

    @FXML
    TableColumn<TableData, String> clusterTableDataColumn;
    @FXML
    TableColumn<TableData, String> clusterTablePointsColumn;

    @FXML
    Label ClusterCount;

    @FXML
    TextArea outputArea;

    private List<Integer> dataList;
    private List<Integer> clusterCenters;
    private Alert alert;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new Alert(Alert.AlertType.NONE);

        dataList = new ArrayList<>();
        clusterCenters = new ArrayList<>();

        clusterTableDataColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("no"));
        clusterTableDataColumn.setStyle("-fx-alignment: CENTER;");
        clusterTablePointsColumn.setCellValueFactory(new PropertyValueFactory<TableData, String>("no"));
        clusterTablePointsColumn.setStyle("-fx-alignment: CENTER;");
    }

    
    @FXML
    protected void addDataToDataTable(ActionEvent event) {
        ObservableList<TableData> data = clusterTableData.getItems();
        try {
            int value = Integer.parseInt(inputFieldData.getText());
            data.add(new TableData(value));
            dataList.add(value);
            inputFieldData.setText("");
        } catch (NumberFormatException ex) {
            DisplayWarning("Invalid value in Data set, Please recheck and add integer values");
            System.out.println("no format exception");
        }
    }

    @FXML
    protected void removeRowFromDataTable(ActionEvent event) {
        if (clusterTableData.getSelectionModel().getSelectedItem() != null) {
            if (dataList.size() - 1 >= clusterCenters.size()) {
                dataList.remove(dataList.indexOf(clusterTableData.getSelectionModel().getSelectedItem().getNo()));
                clusterTableData.getItems().removeAll(clusterTableData.getSelectionModel().getSelectedItem());
            } else {
                DisplayWarning("Data list size will be lesser than cluster points, Add new data or remove clusters");
                System.out.println("Data list size will be lesser than cluster points, Add new data or remove clusters");
            }
        }
    }

    @FXML
    protected void addDataToClusterPointTable(ActionEvent event) {
        if (clusterCenters.size() < dataList.size()) {
            ObservableList<TableData> data = clusterTablePoints.getItems();
            try {
                int value = Integer.parseInt(inputFieldClusterPoint.getText());
                data.add(new TableData(value));
                clusterCenters.add(value);
                ClusterCount.setText(clusterCenters.size() + "");
                inputFieldClusterPoint.setText("");
            } catch (NumberFormatException ex) {
                System.out.println("no format exception");
            }
        } else {
            DisplayWarning("Do not enter cluster size greater than input else remove one of the cluster");
            System.out.println("Do not enter cluster size greater than input else remove one of the cluster");
        }
    }

    @FXML
    protected void removeRowFromClusterPointsTable(ActionEvent event) {
        if (clusterTablePoints.getSelectionModel().getSelectedItem() != null) {
            int no = clusterTablePoints.getSelectionModel().getSelectedItem().getNo();
            clusterCenters.remove(clusterCenters.indexOf(no));
            ClusterCount.setText(clusterCenters.size() + "");
            clusterTablePoints.getItems().removeAll(clusterTablePoints.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    protected void generateCluster(ActionEvent event) {
        if (dataList.size() > 0 && clusterCenters.size() > 0) {
            KMeansLogic KML = new KMeansLogic(dataList, clusterCenters);
            KML.GenerateClusters(outputArea);
        }
    }

    private void DisplayWarning(String message) {
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setHeaderText(message);
        // show the dialog 
        alert.show();
    }
}
