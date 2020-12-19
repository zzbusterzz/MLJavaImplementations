/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebays;



/**
 *
 * @author 1
 */
public class InputValue implements Cloneable {

    int pregnantTimes;
    float plasmaGlucoseConcentration;
    float diastolicBP;
    float ticepsSkinfoldThicknes;
    float serumInsulin;
    float bMI;
    float diabetesPedigreeFunction;
    int age;

    public void setPregnantTimes(int pregnantTimes) {
        this.pregnantTimes = pregnantTimes;
    }

    public void setPlasmaGlucoseConcentration(int plasmaGlucoseConcentration) {
        this.plasmaGlucoseConcentration = plasmaGlucoseConcentration;
    }

    public void setDiastolicBP(int diastolicBP) {
        this.diastolicBP = diastolicBP;
    }

    public void setTicepsSkinfoldThicknes(int ticepsSkinfoldThicknes) {
        this.ticepsSkinfoldThicknes = ticepsSkinfoldThicknes;
    }

    public void setSerumInsulin(int serumInsulin) {
        this.serumInsulin = serumInsulin;
    }

    public void setbMI(float bMI) {
        this.bMI = bMI;
    }

    public void setDiabetesPedigreeFunction(float diabetesPedigreeFunction) {
        this.diabetesPedigreeFunction = diabetesPedigreeFunction;
    }

    public void setAge(int age){
        this.age = age;
    }
    
    public int getPregnantTimes() {
        return pregnantTimes;
    }

    public float getPlasmaGlucoseConcentration() {
        return plasmaGlucoseConcentration;
    }

    public float getDiastolicBP() {
        return diastolicBP;
    }

    public float getTicepsSkinfoldThicknes() {
        return ticepsSkinfoldThicknes;
    }

    public float getSerumInsulin() {
        return serumInsulin;
    }

    public float getbMI() {
        return bMI;
    }

    public float getDiabetesPedigreeFunction() {
        return diabetesPedigreeFunction;
    }
    
    public int getAge() {
        return age;
    }

    public void SetData(int pregnantTimes,
            int plasmaGlucoseConcentration,
            int diastolicBP,
            int ticepsSkinfoldThicknes,
            int serumInsulin,
            float bMI,
            float diabetesPedigreeFunction,int age) {
        this.pregnantTimes = pregnantTimes;
        this.plasmaGlucoseConcentration = plasmaGlucoseConcentration;
        this.diastolicBP = diastolicBP;
        this.ticepsSkinfoldThicknes = ticepsSkinfoldThicknes;
        this.serumInsulin = serumInsulin;
        this.bMI = bMI;
        this.diabetesPedigreeFunction = diabetesPedigreeFunction;
        this.age = age;
    }

    @Override
    protected Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }
}
