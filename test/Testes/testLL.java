/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import Manipuladores.ListdataInterpreter;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erik
 */
public class testLL {
    public static void main(String[] args) {
        try {
            ListdataInterpreter p = new ListdataInterpreter(new File("test.listdata"));
            
            String[] b = {"a", "b", "c", "d"};
            
            p.addFirst_contentData(b, 10);
            
            
            
            for (String a : p.content_getDatas(10)) {
                System.out.println(a);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(testLL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
