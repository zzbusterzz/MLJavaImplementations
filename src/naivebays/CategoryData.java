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
public class CategoryData {
    private int count;
    private double max;
    private double min;
    private ParameterName param;

    public ParameterName getParam() {
        return param;
    }

    public void setParam(ParameterName param) {
        this.param = param;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

  
}
