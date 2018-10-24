/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manipuladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erik
 */
public class UserManer {
    
    private File user;
    
    /**
     * 
     * @param user
     * @param passwords 
     */
    public UserManer(File user, File passwords) {
        this.user = user;
        
        ifNotExist_Create(user);
    }
    
    /**
     * Verifica a existencia do arquivo que armazena os nomes de usuarios
     */
    public UserManer() {
        this.user = new File("users.user");
        
        ifNotExist_Create(user);
    }
   
    /**
     * Verifica a existencia do arquivo passado como parametro, se não existe o cria
     * @param file Arquivo a ser verificado
     */
    private void ifNotExist_Create(File file) {
        if (!file.exists()) {
            try {
                
                System.out.println("creating file: "+file);
                                
                file.createNewFile();
                
            } catch (IOException ex) {
                Logger.getLogger(UserManer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    /**
     * Cadastra um novo usuario
     * @param name Nome do usuario
     * @return boolean - Sucesso ao ser criado
     */
    public boolean newUser(String name) {
        try {
            FileWriter fl = new FileWriter(user, true);
            BufferedWriter write = new BufferedWriter(fl);
            
            write.write(name);
            
            write.newLine();
            
            write.close();
            
            return true;
        } catch (IOException ex) {
            Logger.getLogger(UserManer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //retorna um vetor com todos os usuarios
    /**
     * Retorna um vetor com todos os nomes de usuarios
     * @return String[] - Nomes de usuarios
     */
    public String[] getUsers() {
        try {
            FileReader f = new FileReader(user);
            BufferedReader reader = new BufferedReader(f);
            
            ArrayList<String> prop = new ArrayList<String>();
            
            
            while (reader.ready()) {
                prop.add(reader.readLine());
            }
            
            return prop.toArray(new String[0]);
                        
            
            
        } catch (Exception e) {
            return null;
        }   
    }
    
    /**
     * Retorna o usuario da linha especifica
     * @param index Posição 
     * @return String - Usuario da linha especifica
     */
    public String getUsers(int index) {
        String[] users = this.getUsers();
        return users[index];
    }
    
    /**
     * Verifica a existencia de um usuario
     * @param name Nome de usuario a ser verificado
     * @return boolean - Condição de existencia
     */
    public boolean veryExistentUser(String name) {
        String[] user = this.getUsers();
        
        for (int i = 0; i < user.length; i++) {
            if (name.equals(user[i])) {
                return true;
            }
        }
        
        return false;
        
    }
    
    /**
     * Pega a posição de um nome de usuario
     * @param name
     * @return int - Retorna o indice do usuario, se não existir retorna -1
     */
    public int getUserIndex(String name) {
        if (this.veryExistentUser(name)) {
            String[] users = this.getUsers();
            
            for (int i = 0; i < users.length; i++) {
                if (name.equals(users[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    
}
