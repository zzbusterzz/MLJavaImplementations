/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebays;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author 1
 */
public class FileReader {

   // @SuppressWarnings("empty-statement")
    public Object[] ReadDataSet(String path, MainController controller) {
        List<InputValue> trueValue = null;
        List<InputValue> falseValue = null;

        try {
            Scanner sc = new Scanner(new File(path));

            InputValue value = new InputValue();

            trueValue = new ArrayList();
            falseValue = new ArrayList();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                String[] split = line.split(",");
                try {
                    InputValue newValue = (InputValue) value.clone();
                    newValue.SetData(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
                            Integer.parseInt(split[2]), Integer.parseInt(split[3]),
                            Integer.parseInt(split[4]), Float.parseFloat(split[5]), Float.parseFloat(split[6]), Integer.parseInt(split[7]));

                    if (Integer.parseInt(split[8]) == 0) {
                        falseValue.add(newValue);
                    } else {
                        trueValue.add(newValue);
                    }

                } catch (Exception e) {
                    System.out.println("Exception in split & read" + e.getMessage());
                    controller.DisplayWarning("Exception in split & read");
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception in clone" + ex.getMessage());
            controller.DisplayWarning("Exception in clone" + ex.getMessage());
        }

        return new Object[]{trueValue, falseValue};
    }
}
