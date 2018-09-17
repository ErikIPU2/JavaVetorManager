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

    public UserManer(File user, File passwords) {
        this.user = user;
        
        ifNotExist_Create(user);
    }

    public UserManer() {
        this.user = new File("data/users.user");
        
        ifNotExist_Create(user);
    }
   
    //verifica a existencia do arquivo, caso não exista, o cria
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
    
    
    //cadastra um novo nome
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
    
    public String getUsers(int index) {
        String[] users = this.getUsers();
        return users[index];
    }
    
    public boolean veryExistentUser(String name) {
        String[] user = this.getUsers();
        
        for (int i = 0; i < user.length; i++) {
            if (name.equals(user[i])) {
                return true;
            }
        }
        
        return false;
        
    }
    
    public int getUserIndex(String name) {
        int index = -1;
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
