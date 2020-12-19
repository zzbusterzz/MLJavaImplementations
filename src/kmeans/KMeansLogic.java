/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;

/**
 *
 * @author 1
 */
public class KMeansLogic {

    private List<Integer> dataSet;
    private int K;//Clusters
    private List<Integer> centroids;
    private List<List<Integer>> distanceFromCenteroid;

    private List<List<Integer>> clusterValues;

    private List<Integer> previousCentroids;

    public KMeansLogic(List<Integer> dataSet, List<Integer> centroids) {
        this.dataSet = new ArrayList<Integer>();
        this.dataSet.addAll(dataSet);
        
        this.centroids = new ArrayList<Integer>();
        this.centroids.addAll(centroids);
        
        this.K = centroids.size();
        previousCentroids = new ArrayList<>();
        clusterValues = new ArrayList<>();

        distanceFromCenteroid = new ArrayList<>();

        for (int i = 0; i < this.K; i++) {
            distanceFromCenteroid.add(new ArrayList<>());
            clusterValues.add(new ArrayList<>());
        }
    }

    public void GenerateClusters(TextArea output) {
        boolean isNotCentroidSame = true;
        int maxIteration = 1000;
        int iteration = 0;

        output.clear();

        while (isNotCentroidSame && iteration < maxIteration) {
            //clear previous distances
            for (int i = 0; i < distanceFromCenteroid.size(); i++) {
                distanceFromCenteroid.get(i).clear();
                clusterValues.get(i).clear();
            }

            for (int i = 0; i < distanceFromCenteroid.size(); i++) {//Distance array iteration
                for (int m = 0; m < dataSet.size(); m++) {//Data set iteration
                    //if(centroids.size() < i )
                        distanceFromCenteroid.get(i).add(Math.abs(centroids.get(i) - dataSet.get(m)));
                }
            }

            Integer[] indexArray = new Integer[dataSet.size()];//Get array depciting which cluster the set value will belong
            for (int i = 0; i < indexArray.length; i++) {
                int minIndex = 0;//Represents clusterNo where minimum distance is located
                for (int j = 1; j < distanceFromCenteroid.size(); j++) {
                    if (distanceFromCenteroid.get(j).get(i) < distanceFromCenteroid.get(minIndex).get(i)) {
                        minIndex = j;
                    }
                }
                indexArray[i] = minIndex;
            }

            previousCentroids.clear();
            previousCentroids.addAll(centroids);//Keep a record of previous centroids        
            centroids.clear();//clear current centroid

            //Assign values to clusters based on indexArray        
            for (int i = 0; i < indexArray.length; i++) {
                clusterValues.get(indexArray[i]).add(dataSet.get(i));
            }

            for (int i = 0; i < clusterValues.size(); i++) {//Creating new centroids from sum
                System.out.print("Cluster " + (i) + " : ");
                output.appendText("Cluster " + (i) + " : ");
                
                List<Integer> clusterIth = clusterValues.get(i);
                
                if(clusterIth.size() > 0){
                    int sum = 0;
                    for (int j = 0; j < clusterIth.size(); j++) {
                        System.out.print(clusterIth.get(j) + " ");
                        output.appendText(clusterIth.get(j) + " ");
                        sum += clusterIth.get(j);
                    }

                    sum = sum / clusterIth.size();
                    centroids.add(sum);

                    System.out.print("and with mean at " + sum+"\n");
                    output.appendText("and with mean  " + sum + "\n");
                }else{
                    System.out.print("Cluster is Empty\n" );
                    output.appendText("Cluster is Empty\n");
                }
                
            }
            
            isNotCentroidSame = false;
            System.out.println("\nNew Centroids are : ");
            output.appendText("\nNew Centroids are : ");

            for (int i = 0; i < centroids.size(); i++) {
                int x = previousCentroids.get(i);
                int y = centroids.get(i);
                if (x != y) {
                    isNotCentroidSame = true;
                }
                System.out.print(y + " ");
                output.appendText(y + "  ");
            }
            
            output.appendText("\n");
            System.out.println();
            iteration++;
        }

        System.out.print("Converged on Centroids: ");
        output.appendText("Converged on Centroids: ");
        for (int i = 0; i < centroids.size(); i++) {
            System.out.print(centroids.get(i) + " ");
            output.appendText(centroids.get(i) + " ");
        }
        output.appendText("\n");
        System.out.println();

        for (int i = 0; i < clusterValues.size(); i++) {//Display values in clusters
            System.out.print("Cluster " + i + " : ");
            output.appendText("Cluster " + i + " : ");

            List<Integer> clusterIth = clusterValues.get(i);
            if(clusterIth.size() > 0){
                   for (int j = 0; j < clusterIth.size(); j++) {
                    System.out.print(clusterIth.get(j) + " ");
                    output.appendText(clusterIth.get(j) + " ");
                }
                System.out.println();
                output.appendText("\n"); 
            }else{
                System.out.print("Cluster is Empty" );
                output.appendText("Cluster is Empty\n");    
            }
        }
    }
}