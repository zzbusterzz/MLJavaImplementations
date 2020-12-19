/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author MScIT17
 */
public class Binning {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] data = new int[]{5, 10, 11, 13, 15, 35, 50, 55, 72, 92, 204, 215};
        int m = 3;

        System.out.println("equal frequency binning");
        equifreq(data, m);

        System.out.println("\n\nequal width binning");
        equiwidth(data, 3);
        ArrayList<Float> numbers = new ArrayList<>(Arrays.asList(5.11f, 10.22f, 11.33f, 13.44f, 15.66f, 35.77f, 50.12f, 55.55f, 72.98f, 92.22f, 204.2f, 215.7f));
        equiwidth(numbers, 4);
    }

    public static void equifreq(int[] arr1, int m) {

        int a = arr1.length;
        int n = a / m;
        for (int i = 0; i < m; i++) {
            List<Integer> arr = new ArrayList<>();
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j >= a) {
                    break;
                }
                arr.add(arr1[j]);
            }
            System.out.println("Final values of array:");
            for (int j = 0; j < arr.size(); j++) {
                System.out.println(arr.get(j));
            }
        }
    }

    //The algorithm divides the data into k intervals of equal size. The width of intervals is: 
    //    w = (max-min)/k
    //		
    //And the interval boundaries are: 		
    //
    //    min+w, min+2w, ... , min+(k-1)w
    public static void equiwidth(int[] arr1, int k) {
        int a = arr1.length;

        int max = Arrays.stream(arr1).max().getAsInt();
        int min = Arrays.stream(arr1).min().getAsInt();

        int w = (max - min) / k;
        int min1 = min;

        List<Integer> arr = new ArrayList<>();

        for (int i = 0; i < k + 1; i++) {//creates the range
            arr.add(min1 + w * i);//min + (width * required bins)
        }

        List<Integer[]> arri = new ArrayList<>();

        List<Integer> temp = new ArrayList<>();

        for (int i = 0; i < arr.size() - 1; i++) {
            temp.clear();
            for (int j = 0; j < arr1.length; j++) {
                if (arr1[j] > arr.get(i) && arr1[j] < arr.get(i + 1)) {
                    temp.add(arr1[j]);
                }
            }

            Integer[] val = new Integer[temp.size()];
            val = temp.toArray(val);
            arri.add(val);
        }

        System.out.println("Final values of array:");
        for (int i = 0; i < arri.size(); i++) {
            for (int j = 0; j < arri.get(i).length; j++) {
                System.out.print(arri.get(i)[j] + " ");
            }
            System.out.println();
        }
    }

    public static List<Float[]> equiwidth(List<Float> arr1, int k) {
        int a = arr1.size();

        float max = Collections.max(arr1);
        float min = Collections.min(arr1);

        float w = (max - min) / k;
        // int min1 = min;

        List<Float> arr = new ArrayList<>();

        for (int i = 0; i < k + 1; i++) {//creates the range
            arr.add(min + w * i);//min + (width * required bins)
        }

        List<Float[]> arri = new ArrayList<>();

        List<Float> temp = new ArrayList<>();

        for (int i = 0; i < arr.size() - 1; i++) {
            temp.clear();
            for (int j = 0; j < arr1.size(); j++) {
                if (arr1.get(j) > arr.get(i) && arr1.get(j) < arr.get(i + 1)) {
                    temp.add(arr1.get(j));
                }
            }

            Float[] val = new Float[temp.size()];
            val = temp.toArray(val);
            arri.add(val);
        }

        System.out.println("Final values of array:");
        for (int i = 0; i < arri.size(); i++) {
            for (int j = 0; j < arri.get(i).length; j++) {
                System.out.print(arri.get(i)[j] + " ");
            }
            System.out.println();
        }
        return arri;
    }

    public static List<List<Double>> equiwidthDouble(List<Double> arr1, int k) {
        int a = arr1.size();

        double max = Collections.max(arr1);
        double min = Collections.min(arr1);

        double w = (max - min) / k;

        List<Double> arr = new ArrayList<>();

        for (int i = 0; i < k + 1; i++) {//creates the range
            arr.add(min + w * i);//min + (width * required bins)
        }

        List<List<Double>> arri = new ArrayList<>();

        List<Double> temp = new ArrayList<>();

        for (int i = 0; i < arr.size() - 1; i++) {
            temp.clear();
            for (int j = 0; j < arr1.size(); j++) {
                if (arr1.get(j) > arr.get(i) && arr1.get(j) < arr.get(i + 1)) {
                    temp.add(arr1.get(j));
                }
            }

            List<Double> val = new ArrayList<>();
            val.addAll(temp);
            arri.add(val);
        }

        System.out.println("Final values of array:");
        for (int i = 0; i < arri.size(); i++) {
            for (int j = 0; j < arri.get(i).size(); j++) {
                System.out.print(arri.get(i).get(j) + " ");
            }
            System.out.println();
        }
        return arri;
    }
    
    public static List<List<Double>> equiwidthDouble(List<Double> arr1, int k, double max, double min){
        int a = arr1.size();

        //double max = Collections.max(arr1);
        //double min = Collections.min(arr1);

        double w = (max - min) / k;

        List<Double> arr = new ArrayList<>();

        for (int i = 0; i < k + 1; i++) {//creates the range
            arr.add(min + w * i);//min + (width * required bins)
        }

        List<List<Double>> arri = new ArrayList<>();

        List<Double> temp = new ArrayList<>();

        for (int i = 0; i < arr.size() - 1; i++) {
            temp.clear();
            for (int j = 0; j < arr1.size(); j++) {
                if (arr1.get(j) > arr.get(i) && arr1.get(j) < arr.get(i + 1)) {
                    temp.add(arr1.get(j));
                }
            }

            List<Double> val = new ArrayList<>();
            val.addAll(temp);
            arri.add(val);
        }

        System.out.println("Final values of array:");
        for (int i = 0; i < arri.size(); i++) {
            for (int j = 0; j < arri.get(i).size(); j++) {
                System.out.print(arri.get(i).get(j) + " ");
            }
            System.out.println();
        }
        return arri;
    }
    //https://www.geeksforgeeks.org/binning-in-data-mining/
    //https://www.geeksforgeeks.org/ml-binning-or-discretization/
}
