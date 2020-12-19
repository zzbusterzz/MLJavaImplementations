/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author 1
 */
public class TableData {

        SimpleStringProperty no;

        TableData(int no) {
            this.no = new SimpleStringProperty( no + "" );             
        }

        public int getNo() {
            return Integer.parseInt(no.get());
        }

        public void setNo(int no) {
            this.no.set( no + "");
        }
        
         public void setNo(String no) {
            this.no.set( no);
        }
}
