/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manipuladores;


/**
 * Classe para geração de chave e operações de cifragem
 * @author erik
 */
public class Cryp {
    
    
    /**
     * Função usada para gerar uma chave de cifragem com base em duas Strings
     * @param a Primeiro valor usado
     * @param b Segundo valor usado
     * @return int - Chave resultante
     */ 
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
    
    /**
     * Função usada para cifrar uma String
     * @param message Mensagem a ser cifrada
     * @param key Chave de cifragem
     * @return String - Mensagem cifrada
     */
    public String cifre(String message, int key) {
        String prop = "";
        
        for (int i = 0; i < message.length(); i++) {
            prop += (char) (message.charAt(i) + key);
        }
        
        return prop;
    }
    
    /**
     * Função usada para descifrar uma String
     * @param message Mensagem a ser Descifrada
     * @param key Chave de decifragem
     * @return String - Mensagem descifrada
     */
    public String unCifre(String message, int key) {
        String prop = "";
        
        for (int i = 0; i < message.length(); i++) {
            prop += (char) (message.charAt(i) - key);
        }
        
        return prop;
    }
    
}
