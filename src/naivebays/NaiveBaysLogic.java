/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebays;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 1
 */
//https://www.geeksforgeeks.org/naive-bayes-classifiers/
//https://en.wikipedia.org/wiki/Naive_Bayes_classifier
//https://towardsdatascience.com/naive-bayes-classifier-81d512f50a7c
//https://www.analyticsvidhya.com/blog/2017/09/naive-bayes-explained/
//https://www.geeksforgeeks.org/binning-in-data-mining/
//http://www.lastnightstudy.com/Show?id=38/Data-Cleaning-in-Data-Mining
//https://www.saedsayad.com/missing_values.htm
//https://dev.acquia.com/blog/how-to-handle-missing-data-in-machine-learning-5-techniques/09/07/2018/19651
public class NaiveBaysLogic {
    private List<InputValue> diabetestrue = null;
    private List<InputValue> diabetesFalse = null;
    private List<InputValue> diabetesAll = null;
    
    private BaysDataClass BDC_diabeticsAndNonDB;
    private BaysDataClass BDC_diabetics;
    private BaysDataClass BDC_nonDiabetics;
    
    private int pregnantTimes;
    private float plasmaGlucoseConcentration; 
    private float diastolicBP;
    private float ticepsSkinfoldThicknes;
    private float serumInsulin;
    private float bMI;
    private float diabetesPedigreeFunction;
    private int age;
    
    public NaiveBaysLogic(String path, MainController control){
       // String path = "C:\\Users\\1\\Desktop\\pima-indians-diabetes.csv";
        
        FileReader reader = new FileReader();
        
        Object[] outputLists = reader.ReadDataSet(path, control);
        
        diabetestrue = (List<InputValue>)outputLists[0];
        diabetesFalse = (List<InputValue>)outputLists[1];
        
        diabetesAll = new ArrayList<InputValue>();
        diabetesAll.addAll(diabetestrue);
        diabetesAll.addAll(diabetesFalse);
        
        BDC_diabeticsAndNonDB = new BaysDataClass(); //P(Xi)
        BDC_diabeticsAndNonDB.GenerateMean(diabetesAll);
        BDC_diabeticsAndNonDB.CreateBinsWithFrequencyMap();
        
        BDC_diabetics = new BaysDataClass();//P(Xi/True)
        BDC_diabetics.GenerateMean(diabetestrue);
        
        BDC_nonDiabetics = new BaysDataClass();//P(Xi/False)
        BDC_nonDiabetics.GenerateMean(diabetesFalse);
        
        BDC_diabetics.CreateBinsWithFrequencyMap(BDC_diabeticsAndNonDB);
        BDC_nonDiabetics.CreateBinsWithFrequencyMap(BDC_diabeticsAndNonDB);
        //BDC_diabetics.GenerateMean(diabetestrue);
        //BDC_nonDiabetics.GenerateMean(diabetesFalse);
    }
    
    public String Predict(int pregnantTimes, float plasmaGlucoseConcentration, float diastolicBP, float ticepsSkinfoldThicknes,
            float serumInsulin, float bMI, float diabetesPedigreeFunction, int age){
        
         this.pregnantTimes = pregnantTimes;
         this.plasmaGlucoseConcentration = plasmaGlucoseConcentration;
         this.diastolicBP = diastolicBP;
         this.ticepsSkinfoldThicknes = ticepsSkinfoldThicknes;
         this.serumInsulin = serumInsulin;
         this.bMI = bMI;
         this.diabetesPedigreeFunction = diabetesPedigreeFunction;
         this.age = age;
         
         float isDiabetic = getProbability(BDC_diabetics);
         float isNotDiabetic = getProbability(BDC_nonDiabetics);
         
         //Normalise the result
         float yes = isDiabetic/(isNotDiabetic+isDiabetic);
         float no = isNotDiabetic/(isNotDiabetic+isDiabetic);
         
         System.out.println("Prob of being diabetic is : " +  yes +" && " +
                 "Prob of being non diabetic is : " + no);
         
         if( yes > no)
             return "Diabetes will onset";
         else
             return "Diabetes wont onset";
    }
    
    //P(C/A) = (P(Ai/C) * P(C))/(P(Ai))
    private float getProbability(BaysDataClass probType){
        //y = true or false
        
        float y = probType.getTotalValues();
        float total = (float)BDC_diabeticsAndNonDB.getTotalValues();
        
        float Prob_PT_Y = probType.getCountForParam(ParameterName.PregnantTimes, pregnantTimes)/y;//Xi/y
        if(Prob_PT_Y == 0) Prob_PT_Y = 1;        
        float Prob_PT_Total = probType.getCountForParam(ParameterName.PregnantTimes, pregnantTimes)/total;//Xi
        if(Prob_PT_Total == 0) Prob_PT_Total = 1;
        
        float Prob_PGC_Y = probType.getCountForParam(ParameterName.PlasmaGlucoseConcentration, plasmaGlucoseConcentration)/y;
        if(Prob_PGC_Y == 0) Prob_PGC_Y = 1;
        float Prob_PGC_Total = probType.getCountForParam(ParameterName.PlasmaGlucoseConcentration, plasmaGlucoseConcentration)/total;
        if(Prob_PGC_Total == 0) Prob_PGC_Total = 1;
        
        float Prob_DBP_Y = probType.getCountForParam(ParameterName.DiastolicBP, diastolicBP)/y;
        if(Prob_DBP_Y == 0) Prob_DBP_Y = 1;
        float Prob_DBP_Total = probType.getCountForParam(ParameterName.DiastolicBP, diastolicBP)/total;
        if(Prob_DBP_Total == 0) Prob_DBP_Total = 1;
        
        float Prob_TSFT_Y = probType.getCountForParam(ParameterName.TicepsSkinfoldThicknes, ticepsSkinfoldThicknes)/y;
        if(Prob_TSFT_Y == 0) Prob_TSFT_Y = 1;
        float Prob_TSFT_Total = probType.getCountForParam(ParameterName.TicepsSkinfoldThicknes, ticepsSkinfoldThicknes)/total;
        if(Prob_TSFT_Total == 0) Prob_TSFT_Total = 1;
        
        float Prob_SI_Y = probType.getCountForParam(ParameterName.SerumInsulin, serumInsulin)/y;
        if(Prob_SI_Y == 0) Prob_SI_Y = 1;
        float Prob_SI_Total = probType.getCountForParam(ParameterName.SerumInsulin, serumInsulin)/total;
        if(Prob_SI_Total == 0) Prob_SI_Total = 1;
        
        float Prob_BMI_Y = probType.getCountForParam(ParameterName.BMI, bMI)/y;
        if(Prob_BMI_Y == 0) Prob_BMI_Y = 1;
        float Prob_BMI_Total = probType.getCountForParam(ParameterName.BMI, bMI)/total;
        if(Prob_BMI_Total == 0) Prob_BMI_Total = 1;
        
        float Prob_DPF_Y = probType.getCountForParam(ParameterName.DiabetesPedigreeFunction, diabetesPedigreeFunction)/y;
        if(Prob_DPF_Y == 0) Prob_DPF_Y = 1;
        float Prob_DPF_Total = probType.getCountForParam(ParameterName.DiabetesPedigreeFunction, diabetesPedigreeFunction)/total;
        if(Prob_DPF_Total == 0) Prob_DPF_Total = 1;
        
        float Prob_AGE_Y = probType.getCountForParam(ParameterName.Age, age)/y;
        if(Prob_AGE_Y == 0) Prob_AGE_Y = 1;
        float Prob_AGE_Total = probType.getCountForParam(ParameterName.Age, age)/total;
        if(Prob_AGE_Total == 0) Prob_AGE_Total = 1;
        
        float Prob_Total_Y = y/total;
        if(Prob_Total_Y == 0) Prob_Total_Y = 1;
        
        float PAi_YTimesPY = Prob_PT_Y * Prob_PGC_Y * Prob_DBP_Y * Prob_TSFT_Y * Prob_SI_Y * Prob_BMI_Y *  Prob_DPF_Y * Prob_AGE_Y * Prob_Total_Y;
      //Constant denominator //  float PAi = Prob_PT_Total * Prob_PGC_Total * Prob_DBP_Total * Prob_TSFT_Total * Prob_SI_Total * Prob_BMI_Total *  Prob_DPF_Total * Prob_AGE_Total;
        
        return PAi_YTimesPY; 
    }
}
