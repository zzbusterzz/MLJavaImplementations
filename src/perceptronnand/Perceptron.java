/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptronnand;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;

/**
 *
 * @author 1
 */
public class Perceptron {

    private List<NandGateRow> gateResults;
    private float learningRate = 0.01f;//learning rate should be lesser than bais
    private int closingIteration = 1000;
    
    public Perceptron() {
        //Generate default NAND gate values
        gateResults = new ArrayList();
        gateResults.add(new NandGateRow(0, 0, 1));
        gateResults.add(new NandGateRow(0, 1, 1));
        gateResults.add(new NandGateRow(1, 0, 1));
        gateResults.add(new NandGateRow(1, 1, -1));
    }

    public void Train(float bais, float w1, float w2, TextArea output) {
        // float threshold = 0.0000001f;
        boolean isNotConverged = true;
        boolean hasChangedWeight = false;
        int iteration = 0;

        float previousW1 = 0;
        float previousW2 = 0;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumIntegerDigits(4);

        while (isNotConverged) {
            iteration++;
            hasChangedWeight = false;

            for (int i = 0; i < gateResults.size(); i++) {
                NandGateRow NGR = gateResults.get(i);
                float currentResult = (float) (bais + (w1 * NGR.x1) + (w2 * NGR.x2));
                //currentResult = df.format(currentResult);
                currentResult = Math.round(currentResult * 1000000.0) / 1000000.0f;

                if (currentResult >= 0) {
                    currentResult = 1;
                } else {
                    currentResult = -1;
                }

                if ((NGR.result == -1 && currentResult != -1) || (NGR.result == 1 && currentResult != 1)) {
                    System.out.println("Weight change For value " + i + " iteration :" + iteration + " Weight W1 :" + w1 + " Weight W2 :" + w2 + "result : " + NGR.result + " current" + currentResult);

                    hasChangedWeight = true;
                    previousW1 = w1;
                    previousW2 = w2;

                    //w1 =   Math.round( (w1+getNewDeltaWeight(NGR.result ,currentResult, NGR.x1)) * 10000.0) / 10000.0;
                    //w2 = Math.round( (w2+getNewDeltaWeight(NGR.result ,currentResult, NGR.x2)) *10000.0) / 10000.0;
                    w1 = w1 + getNewDeltaWeight(NGR.result, currentResult, NGR.x1);
                    w2 = w2 + getNewDeltaWeight(NGR.result, currentResult, NGR.x2);

                    boolean hasNotRecievedWeights = true;

                    while (hasNotRecievedWeights) {
                        currentResult = (float) (bais + (w1 * NGR.x1) + (w2 * NGR.x2));
                        // currentResult = Math.round(currentResult * 1000000.0) / 1000000.0f;

//                        if(currentResult >= 0) currentResult = 1;
//                        else currentResult = -1;
                        if ((NGR.result == -1 && currentResult > 0) || (NGR.result == 1 && currentResult < 0)) {
                            previousW1 = w1;
                            previousW2 = w2;

                            w1 = w1 + getNewDeltaWeight(NGR.result, currentResult, NGR.x1);
                            w2 = w2 + getNewDeltaWeight(NGR.result, currentResult, NGR.x2);
                        } else {
                            hasNotRecievedWeights = false;
                        }

                        System.out.println("New Weight change For value " + i + " iteration :" + iteration + " Weight W1 :" + w1 + " Weight W2 :" + w2 + "result : " + NGR.result + " current" + currentResult);
                    }

//                    double X1error = Math.abs( previousW1) - Math.abs(w1);
//                    double X2error = Math.abs( previousW2) - Math.abs(w2);
//                    
//                    if(  X1error == 0 && X2error == 0 )
//                    {   
//                        hasChangedWeight = false;
//                    }
//                    System.out.println("Not Converged on iteration : " + iteration + ", Error : " + X1error + " : " + X2error + " with weights W1:" + w1 + " W2:" + w2);
                    //   output.appendText("For iteration :" + iteration + " Weight W1 :" + w1 + " Weight W2 :" + w2 + "\n");
                    // System.out.println("Not Converged on iteration : " + iteration + ", Error : " + X1error + " : " + X2error + " with weights W1:" + w1 + "W2:" + w2);
                }

                System.out.println("For value " + i + " iteration :" + iteration + " Weight W1 :" + w1 + " Weight W2 :" + w2 + "result : " + NGR.result + " current" + currentResult);
            }
            if (!hasChangedWeight) {
                isNotConverged = false;
            }
        }

        output.appendText("Converged on iteration : " + iteration + " with weights W1:" + w1 + " W2:" + w2 + "\n");
        for (int i = 0; i < gateResults.size(); i++) {
            NandGateRow NGR = gateResults.get(i);
            double currentResult = bais + (w1 * NGR.x1) + (w2 * NGR.x2);
            if (currentResult >= 0) {
                currentResult = 1;
            } else {
                currentResult = -1;
            }

            //  output.appendText("Result for X1: " + NGR.x1 + " & X2: "+ NGR.x2 +"with new weights w1:" + w1 + " w2: "+ w2 + " is : " + currentResult + " for the actual output : "+ NGR.result +" which is negative" + "\n");
            if ((NGR.result == -1 && currentResult == -1))//classify as negative
            {
                output.appendText("Result for X1: " + NGR.x1 + " & X2: " + NGR.x2 + "with new weights w1:" + w1 + " w2: " + w2 + " is : " + currentResult + " for the actual output : " + NGR.result + " which is negative" + "\n");
            } else if ((NGR.result == 1 && currentResult == 1))//classify as positive
            {
                output.appendText("Result for X1: " + NGR.x1 + "& X2: " + NGR.x2 + "with new weights w1:" + w1 + "w2: " + w2 + " is : " + currentResult + "for the actual output : " + NGR.result + " which is positive" + "\n");
            }
        }
    }

    ///make sure bais is >= 0.02
    public void Train(float bais, double w1, double w2, TextArea output) {
        output.setText("");
        boolean isNotConverged = true;
        boolean hasChangedWeight = false;
        int iteration = 0;

        double previousW1 = 0;
        double previousW2 = 0;
        
        //Close the iteration on 1000
        while (isNotConverged && iteration < closingIteration) {
            iteration++;
            hasChangedWeight = false;

            for (int i = 0; i < gateResults.size(); i++) {
                NandGateRow NGR = gateResults.get(i);
                double currentResult = (bais + (w1 * NGR.x1) + (w2 * NGR.x2));
                //currentResult = Math.round(currentResult * 1000000.0) / 1000000.0f;

                if (currentResult >= 0) {
                    currentResult = 1;
                } else {
                    currentResult = -1;
                }

                if ((NGR.result == -1 && currentResult != -1) || (NGR.result == 1 && currentResult != 1)) {
                    System.out.println("Weight change For value " + i + " iteration :" + iteration + " Weight W1 :" + w1 + " Weight W2 :" + w2 + " result : " + NGR.result + " current" + currentResult);

                    hasChangedWeight = true;
                    previousW1 = w1;
                    previousW2 = w2;

                    w1 = Math.round((w1 + getNewDeltaWeight(NGR.result, currentResult, NGR.x1)) * 1000000.0) / 1000000.0;
                    w2 = Math.round((w2 + getNewDeltaWeight(NGR.result, currentResult, NGR.x2)) * 1000000.0) / 1000000.0;//Upto 6 digit accuracy of weights
//
//                       w1 =  w1+getNewDeltaWeight(NGR.result ,currentResult, NGR.x1);
//                        w2 =  w2+getNewDeltaWeight(NGR.result ,currentResult, NGR.x2);
                    boolean hasNotRecievedWeights = true;

                    while (hasNotRecievedWeights) {
                        currentResult = (bais + (w1 * NGR.x1) + (w2 * NGR.x2));
                        currentResult = Math.round(currentResult * 1000000.0) / 1000000.0f;

//                        if(currentResult >= 0) currentResult = 1;
//                        else currentResult = -1;
                        if ((NGR.result == -1 && currentResult > 0) || (NGR.result == 1 && currentResult < 0)) {
                            previousW1 = w1;
                            previousW2 = w2;

                            w1 = Math.round((w1 + getNewDeltaWeight(NGR.result, currentResult, NGR.x1)) * 1000000.0) / 1000000.0;
                            w2 = Math.round((w2 + getNewDeltaWeight(NGR.result, currentResult, NGR.x2)) * 1000000.0) / 1000000.0;

//                            w1 =  w1+getNewDeltaWeight(NGR.result ,currentResult, NGR.x1);
//                            w2 =  w2+getNewDeltaWeight(NGR.result ,currentResult, NGR.x2);
                        } else {
                            hasNotRecievedWeights = false;
                        }

                        System.out.println("New Weight change For value " + i + " iteration :" + iteration + " Weight W1 :" + w1 + " Weight W2 :" + w2 + " result : " + NGR.result + " current" + currentResult);
                    }

                    output.appendText("For iteration :" + iteration + " Weight W1 :" + w1 + " Weight W2 :" + w2 + "\n");
                    // System.out.println("Not Converged on iteration : " + iteration + ", Error : " + X1error + " : " + X2error + " with weights W1:" + w1 + "W2:" + w2);
                }

                System.out.println("For value " + i + " iteration :" + iteration + " Weight W1 :" + w1 + " Weight W2 :" + w2 + " result : " + NGR.result + " current" + currentResult);
            }
            if (!hasChangedWeight) {
                isNotConverged = false;
            }
        }

        output.appendText("Converged on iteration : " + iteration + " with weights W1:" + w1 + " W2:" + w2 + "\n");
        for (int i = 0; i < gateResults.size(); i++) {
            NandGateRow NGR = gateResults.get(i);
            double currentResult = bais + (w1 * NGR.x1) + (w2 * NGR.x2);
            if (currentResult >= 0) {
                currentResult = 1;
            } else {
                currentResult = -1;
            }

            //  output.appendText("Result for X1: " + NGR.x1 + " & X2: "+ NGR.x2 +"with new weights w1:" + w1 + " w2: "+ w2 + " is : " + currentResult + " for the actual output : "+ NGR.result +" which is negative" + "\n");
            if ((NGR.result == -1 && currentResult == -1))//classify as negative
            {
                output.appendText("Result for X1: " + NGR.x1 + " & X2: " + NGR.x2 + " with new weights w1:" + w1 + " w2: " + w2 + " is : " + currentResult + " for the actual output : " + NGR.result + " which is negative" + "\n");
            } else if ((NGR.result == 1 && currentResult == 1))//classify as positive
            {
                output.appendText("Result for X1: " + NGR.x1 + " & X2: " + NGR.x2 + " with new weights w1:" + w1 + " w2: " + w2 + " is : " + currentResult + " for the actual output : " + NGR.result + " which is positive" + "\n");
            }
        }
    }

    private float getNewDeltaWeight(float expectedOP, float currentOP, float x) {
        return learningRate * (expectedOP - currentOP) * x;
    }

    private double getNewDeltaWeight(double expectedOP, double currentOP, float x) {
        return learningRate * (expectedOP - currentOP) * x;
    }
}
