/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manipuladores;


/**
 *
 * @author erik
 */
public class Cryp {
    
    
    //gera a chave de cifra baseada nos username e senha
    public int gerateVal(String a, String b) {
        
        char aArray[] = a.toCharArray();
        char bArray[] = b.toCharArray();
        
        int val = 0;
        
        for (char aChar : aArray) {
            val += aChar;
        }
        
        for (char bChar : bArray) {
            val += bChar;
        }
        
        return val;
    }
    
    public String cifre(String message, int key) {
        String prop = "";
        
        for (int i = 0; i < message.length(); i++) {
            prop += (char) (message.charAt(i) + key);
        }
        
        return prop;
    }
    
    public String unCifre(String message, int key) {
        String prop = "";
        
        for (int i = 0; i < message.length(); i++) {
            prop += (char) (message.charAt(i) - key);
        }
        
        return prop;
    }
    
}
