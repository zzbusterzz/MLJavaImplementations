/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author 1
 */
public class BaysDataClass {
    //Notes from https://machinelearningmastery.com/naive-bayes-for-machine-learning/
    //https://www.saedsayad.com/missing_values.htm
    //https://www.analyticsvidhya.com/blog/2017/09/naive-bayes-explained/
    
    private HashMap<ParameterName, Float> meanMap;
    private HashMap<ParameterName, Float> stdDeviaMap;
    private HashMap<ParameterName, List<Double>> attributeValuesList;
    private HashMap<ParameterName, List<List<Double>>> attributeBinMap;
    
    private HashMap<ParameterName, List<CategoryData>> frequencyTable = null;

    public HashMap<ParameterName, List<CategoryData>> getFrequencyTable() {
        return frequencyTable;
    }
    
    private List<Double> PT = new ArrayList<Double>(){};
    private List<Double> PGC = new ArrayList<Double>(){};
    private List<Double> DBP = new ArrayList<Double>(){};
    private List<Double> TST = new ArrayList<Double>(){};
    private List<Double> SI = new ArrayList<Double>(){};
    private List<Double> BMI = new ArrayList<Double>(){};
    private List<Double> DPF = new ArrayList<Double>(){};
    private List<Double> AGE = new ArrayList<Double>(){};
    
    private int totalValues;

    public int getTotalValues() {
        return totalValues;
    }
    
    public BaysDataClass() {
        meanMap = new HashMap<>();
        stdDeviaMap = new HashMap<>();
        attributeBinMap = new HashMap<>();
        attributeValuesList = new HashMap<>();
        
        attributeValuesList.put(ParameterName.PregnantTimes, PT);
        attributeValuesList.put(ParameterName.PlasmaGlucoseConcentration, PGC);
        attributeValuesList.put(ParameterName.DiastolicBP, DBP);
        attributeValuesList.put(ParameterName.TicepsSkinfoldThicknes, TST);
        attributeValuesList.put(ParameterName.SerumInsulin, SI);
        attributeValuesList.put(ParameterName.BMI, BMI);
        attributeValuesList.put(ParameterName.DiabetesPedigreeFunction, DPF);
        attributeValuesList.put(ParameterName.Age, AGE);
    }
    
    public Float GetMean(ParameterName param){
        return meanMap.get(param);
    }
    
    //Following colums 0 data needs to be replaced with mean
    //1: Plasma glucose concentration
    //2: Diastolic blood pressure
    //3: Triceps skinfold thickness
    //4: 2-Hour serum insulin
    //5: Body mass index
    //Mean/Median/Mode Imputation
    public void GenerateMean(List<InputValue> inputList) {
        float pregnantTimes = 0, plasmaGlucoseConcentration = 0, diastolicBP = 0, ticepsSkinfoldThicknes = 0, serumInsulin = 0, bMI = 0,
                diabetesPedigreeFunction = 0, age = 0;
        
        int N = totalValues = inputList.size();
        
        //mean is given by mean(x) = 1/n * sum(x)
        int reduceMeanplasmaGlucoseConcentration = 0;
        int reduceMeandiastolicBP = 0;
        int reduceMeanticepsSkinfoldThicknes = 0;
        int reduceMeanserumInsulin = 0;
        int reduceMeanbMI = 0;
        
        for (int i = 0; i < N; i++) {
            InputValue IPV = inputList.get(i);   
            
            pregnantTimes += IPV.pregnantTimes;
            
            plasmaGlucoseConcentration += IPV.plasmaGlucoseConcentration;
            if(IPV.plasmaGlucoseConcentration == 0) reduceMeanplasmaGlucoseConcentration++;
            
            diastolicBP += IPV.diastolicBP;
            if(IPV.diastolicBP == 0) reduceMeandiastolicBP++;
            
            ticepsSkinfoldThicknes += IPV.ticepsSkinfoldThicknes;
            if(IPV.ticepsSkinfoldThicknes == 0) reduceMeanticepsSkinfoldThicknes++;
            
            serumInsulin += IPV.serumInsulin;
            if(IPV.serumInsulin == 0) reduceMeanserumInsulin++;
            
            bMI += IPV.bMI;
            if(IPV.bMI == 0) reduceMeanbMI++;
            
            diabetesPedigreeFunction += IPV.diabetesPedigreeFunction;
            
            age += IPV.age;
        }
        //Create mean
        meanMap.put(ParameterName.PregnantTimes, pregnantTimes / N);
        meanMap.put(ParameterName.PlasmaGlucoseConcentration, plasmaGlucoseConcentration / (N-reduceMeanplasmaGlucoseConcentration));
        meanMap.put(ParameterName.DiastolicBP, diastolicBP / (N-reduceMeandiastolicBP) );
        meanMap.put(ParameterName.TicepsSkinfoldThicknes, ticepsSkinfoldThicknes / (N - reduceMeanticepsSkinfoldThicknes)) ;
        meanMap.put(ParameterName.SerumInsulin, serumInsulin / (N - reduceMeanserumInsulin));
        meanMap.put(ParameterName.BMI, bMI / (N - reduceMeanbMI));
        meanMap.put(ParameterName.DiabetesPedigreeFunction, diabetesPedigreeFunction / N);
        meanMap.put(ParameterName.Age, age / N);
        
        
        //Updates values with mean parameters
        for (int i = 0; i < N; i++) {
            InputValue IPV = inputList.get(i);
            if(IPV.plasmaGlucoseConcentration == 0) IPV.plasmaGlucoseConcentration =  meanMap.get(ParameterName.PlasmaGlucoseConcentration);            
            if(IPV.diastolicBP == 0) IPV.diastolicBP =  meanMap.get(ParameterName.DiastolicBP);            
            if(IPV.ticepsSkinfoldThicknes == 0) IPV.ticepsSkinfoldThicknes =  meanMap.get(ParameterName.TicepsSkinfoldThicknes);            
            if(IPV.serumInsulin == 0) IPV.serumInsulin =  meanMap.get(ParameterName.SerumInsulin);            
            if(IPV.bMI == 0) IPV.bMI =  meanMap.get(ParameterName.BMI);
            
            PT.add((double)IPV.pregnantTimes);
            PGC.add((double)IPV.plasmaGlucoseConcentration);
            DBP.add((double)IPV.diastolicBP);
            TST.add((double)IPV.ticepsSkinfoldThicknes);
            SI.add((double)IPV.serumInsulin);
            BMI.add((double)IPV.bMI);
            DPF.add((double)IPV.diabetesPedigreeFunction);
            AGE.add((double)IPV.age);
        }
    }
            
    public void CreateBinsWithFrequencyMap(){
        int categoryBin = 3;
        frequencyTable = new HashMap<ParameterName, List<CategoryData>>();
        
        List<List<Double>> binData = Binning.equiwidthDouble(PT, categoryBin);
        attributeBinMap.put(ParameterName.PregnantTimes, binData);
        AddToFrequencyTable(binData, ParameterName.PregnantTimes);
        
        binData = Binning.equiwidthDouble(PGC, categoryBin);
        attributeBinMap.put(ParameterName.PlasmaGlucoseConcentration, binData);
        AddToFrequencyTable(binData, ParameterName.PlasmaGlucoseConcentration);
         
        binData = Binning.equiwidthDouble(DBP, categoryBin);
        attributeBinMap.put(ParameterName.DiastolicBP, binData);
        AddToFrequencyTable(binData, ParameterName.DiastolicBP);
         
        binData = Binning.equiwidthDouble(TST, categoryBin);
        attributeBinMap.put(ParameterName.TicepsSkinfoldThicknes, binData);
        AddToFrequencyTable(binData, ParameterName.TicepsSkinfoldThicknes);
        
        binData = Binning.equiwidthDouble(SI, categoryBin);
        attributeBinMap.put(ParameterName.SerumInsulin, binData);
        AddToFrequencyTable(binData, ParameterName.SerumInsulin);
        
        binData = Binning.equiwidthDouble(BMI, categoryBin);
        attributeBinMap.put(ParameterName.BMI, binData);
        AddToFrequencyTable(binData, ParameterName.BMI);
        
        binData = Binning.equiwidthDouble(DPF, categoryBin);
        attributeBinMap.put(ParameterName.DiabetesPedigreeFunction, binData);
        AddToFrequencyTable(binData, ParameterName.DiabetesPedigreeFunction);
        
        binData = Binning.equiwidthDouble(AGE, categoryBin);
        attributeBinMap.put(ParameterName.Age, binData);
        AddToFrequencyTable(binData, ParameterName.Age);
    }
    
    public float getCountForParam(ParameterName param, double value){
        if(value == -1) return totalValues;
        CategoryData data = GetCetegory(param, value);
        return data.getCount();
    }
    
    public void CreateBinsWithFrequencyMap(BaysDataClass BDC ){
        int categoryBin = 3;
        frequencyTable = new HashMap<ParameterName, List<CategoryData>>();
        
        List<CategoryData> data = BDC.getFrequencyTable().get(ParameterName.PregnantTimes);
        
        List<List<Double>> binData = Binning.equiwidthDouble(PT, categoryBin,data.get(data.size() - 1).getMax(), data.get(0).getMin());
        attributeBinMap.put(ParameterName.PregnantTimes, binData);
        AddToFrequencyTable(binData, ParameterName.PregnantTimes);
        
        data = BDC.getFrequencyTable().get(ParameterName.PlasmaGlucoseConcentration);
        
        binData = Binning.equiwidthDouble(PGC, categoryBin, data.get(data.size() - 1).getMax(), data.get(0).getMin());
        attributeBinMap.put(ParameterName.PlasmaGlucoseConcentration, binData);
        AddToFrequencyTable(binData, ParameterName.PlasmaGlucoseConcentration);
        
        data = BDC.getFrequencyTable().get(ParameterName.DiastolicBP);
        
        binData = Binning.equiwidthDouble(DBP, categoryBin, data.get(data.size() - 1).getMax(), data.get(0).getMin());
        attributeBinMap.put(ParameterName.DiastolicBP, binData);
        AddToFrequencyTable(binData, ParameterName.DiastolicBP);
        
        data = BDC.getFrequencyTable().get(ParameterName.TicepsSkinfoldThicknes);
        
        binData = Binning.equiwidthDouble(TST, categoryBin, data.get(data.size() - 1).getMax(), data.get(0).getMin());
        attributeBinMap.put(ParameterName.TicepsSkinfoldThicknes, binData);
        AddToFrequencyTable(binData, ParameterName.TicepsSkinfoldThicknes);
        
        data = BDC.getFrequencyTable().get(ParameterName.SerumInsulin);
        
        binData = Binning.equiwidthDouble(SI, categoryBin, data.get(data.size() - 1).getMax(), data.get(0).getMin());
        attributeBinMap.put(ParameterName.SerumInsulin, binData);
        AddToFrequencyTable(binData, ParameterName.SerumInsulin);
        
        data = BDC.getFrequencyTable().get(ParameterName.BMI);
        
        binData = Binning.equiwidthDouble(BMI, categoryBin, data.get(data.size() - 1).getMax(), data.get(0).getMin());
        attributeBinMap.put(ParameterName.BMI, binData);
        AddToFrequencyTable(binData, ParameterName.BMI);
        
        data = BDC.getFrequencyTable().get(ParameterName.DiabetesPedigreeFunction);
        
        binData = Binning.equiwidthDouble(DPF, categoryBin, data.get(data.size() - 1).getMax(), data.get(0).getMin());
        attributeBinMap.put(ParameterName.DiabetesPedigreeFunction, binData);
        AddToFrequencyTable(binData, ParameterName.DiabetesPedigreeFunction);
        
        data = BDC.getFrequencyTable().get(ParameterName.Age);
        
        binData = Binning.equiwidthDouble(AGE, categoryBin, data.get(data.size() - 1).getMax(), data.get(0).getMin());
        attributeBinMap.put(ParameterName.Age, binData);
        AddToFrequencyTable(binData, ParameterName.Age);
    }
    
    private void AddToFrequencyTable(List<List<Double>> binData, ParameterName param){
        List<CategoryData> dataGroup = new ArrayList<CategoryData>();
        for(int i = 0; i < binData.size(); i++){
            CategoryData data = new CategoryData();
            if(binData.get(i) != null && binData.get(i).size() > 0){
                data.setCount(binData.get(i).size());
                data.setMax(Collections.max(binData.get(i)));
                data.setMin(Collections.min(binData.get(i)));
            }
            data.setParam(param);
            dataGroup.add(data);
        }
        frequencyTable.put(param, dataGroup);
    }
    
    public CategoryData GetCetegory(ParameterName param, double value){
        List<CategoryData> categoryGroup = frequencyTable.get(param);
        
        if(categoryGroup.get(0).getMin() > value) return categoryGroup.get(0);
        if(categoryGroup.get(categoryGroup.size() - 1).getMax() < value) 
            return categoryGroup.get(categoryGroup.size() - 1);
        
        for(int i = 0; i < categoryGroup.size(); i++){
            if(value >= categoryGroup.get(i).getMin() || 
                value <= categoryGroup.get(i).getMax() ){
                return categoryGroup.get(i);
            }
        }
        return null;
    }
    
    public int getCategoryTotal(ParameterName param){
        int total = 0;
        List<CategoryData> categoryGroup = frequencyTable.get(param);
        for(int i = 0; i < categoryGroup.size(); i++){
            total += categoryGroup.get(i).getCount();
        }
        return total;
    }
//    public void GenerateStandardDeviation(List<InputValue> inputList){
//         float pregnantTimes = 0, plasmaGlucoseConcentration = 0, diastolicBP = 0, ticepsSkinfoldThicknes = 0, serumInsulin = 0, bMI = 0,
//                diabetesPedigreeFunction = 0, age = 0;       
//        
//         int N = inputList.size();
//         
//        //Sum of squared squared differnce from mean
//        for (int i = 0; i < inputList.size(); i++) {
//             InputValue IPV = inputList.get(i);
//             
//            double xiMinusMean = IPV.pregnantTimes - meanMap.get(ParameterName.PregnantTimes);
//            pregnantTimes += xiMinusMean*xiMinusMean;
//            
//            xiMinusMean = IPV.plasmaGlucoseConcentration - meanMap.get(ParameterName.PlasmaGlucoseConcentration);
//            plasmaGlucoseConcentration += xiMinusMean*xiMinusMean;
//            
//            xiMinusMean = IPV.diastolicBP - meanMap.get(ParameterName.DiastolicBP);
//            diastolicBP += xiMinusMean*xiMinusMean;
//            
//            xiMinusMean = IPV.ticepsSkinfoldThicknes - meanMap.get(ParameterName.TicepsSkinfoldThicknes);
//            ticepsSkinfoldThicknes +=  xiMinusMean*xiMinusMean;
//            
//            xiMinusMean = IPV.serumInsulin - meanMap.get(ParameterName.SerumInsulin);
//            serumInsulin +=  xiMinusMean*xiMinusMean;
//            
//            xiMinusMean = IPV.bMI - meanMap.get(ParameterName.BMI);
//            bMI +=  xiMinusMean*xiMinusMean;
//            
//            xiMinusMean = IPV.diabetesPedigreeFunction - meanMap.get(ParameterName.DiabetesPedigreeFunction);
//            diabetesPedigreeFunction +=  xiMinusMean*xiMinusMean;
//            
//            xiMinusMean = IPV.plasmaGlucoseConcentration - meanMap.get(ParameterName.Age);
//            age +=  xiMinusMean*xiMinusMean;
//        }
//        
//        //Std Deviation is given by sqrt(1/n * sum(xi-mean(x))^2 )
//        //https://machinelearningmastery.com/naive-bayes-for-machine-learning/
//                
//        pregnantTimes = (float) Math.sqrt(pregnantTimes /N);            
//        plasmaGlucoseConcentration = (float) Math.sqrt(plasmaGlucoseConcentration /N);
//        diastolicBP = (float) Math.sqrt(diastolicBP /N);
//        ticepsSkinfoldThicknes = (float) Math.sqrt(ticepsSkinfoldThicknes /N);
//        serumInsulin = (float) Math.sqrt(serumInsulin /N);
//        bMI = (float) Math.sqrt(bMI /N);
//        diabetesPedigreeFunction = (float) Math.sqrt(diabetesPedigreeFunction /N);
//        age = (float) Math.sqrt(age /N);
//        
//        stdDeviaMap.put(ParameterName.PregnantTimes, pregnantTimes);
//        stdDeviaMap.put(ParameterName.PlasmaGlucoseConcentration, plasmaGlucoseConcentration);
//        stdDeviaMap.put(ParameterName.DiastolicBP, diastolicBP);
//        stdDeviaMap.put(ParameterName.TicepsSkinfoldThicknes, ticepsSkinfoldThicknes);
//        stdDeviaMap.put(ParameterName.SerumInsulin, serumInsulin);
//        stdDeviaMap.put(ParameterName.BMI, bMI);
//        stdDeviaMap.put(ParameterName.DiabetesPedigreeFunction, diabetesPedigreeFunction);
//        stdDeviaMap.put(ParameterName.Age, age);        
//    }
}
