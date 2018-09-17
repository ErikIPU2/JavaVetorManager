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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erik
 */
public class ListdataInterpreter {

    private final String START_HEADER = "&HEADER&";
    private final String END_HEADER = "$HEADER$";

    private final String START_HEADER_USER = "&USER&";
    private final String END_HEADER_USER = "$USER$";

    private final String START_HEADER_NAME = "&NAME&";
    private final String END_HEADER_NAME = "$NAME$";

    private final String START_HEADER_PUBLIC = "&PUBLIC&";
    private final String END_HEADER_PUBLIC = "$PUBLIC$";

    private final String START_HEADER_TYPE = "&TYPE&";
    private final String END_HEADER_TYPE = "$TYPE$";

    private final String START_CONTENT = "&CONTENT&";
    private final String END_CONTENT = "$CONTENT$";

    private final String START_DATA = "&DATA&";
    private final String END_DATA = "$DATA$";

    private File path;

    public ListdataInterpreter(File path) {
        this.path = path;
        if (!this.path.exists()) {
            try {
                System.out.println("Creating: " + path.getName());
                this.path.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ListdataInterpreter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //apaga todo o arquivo e cria um cabeçalho
    private void writeHeader(String author, String name, boolean pub, String type, int key) {
        try {
            FileWriter f = new FileWriter(path, false);
            BufferedWriter writer = new BufferedWriter(f);

            /*Header layout: 
                user
                file name
                public
                type
             */
            //escreve o cabeçalho
            writer.write(START_HEADER);
            writer.newLine();
            writer.write("\t" + START_HEADER_USER + new Cryp().cifre(author, key) + END_HEADER_USER);
            writer.newLine();
            writer.write("\t" + START_HEADER_NAME + name + END_HEADER_NAME);
            writer.newLine();
            writer.write("\t" + START_HEADER_PUBLIC + pub + END_HEADER_PUBLIC);
            writer.newLine();
            writer.write("\t" + START_HEADER_TYPE + type + END_HEADER_TYPE);
            writer.newLine();
            writer.write(END_HEADER);
            writer.newLine();
            writer.newLine();

            writer.write(START_CONTENT);
            writer.newLine();
            writer.newLine();
            writer.write(END_CONTENT);

            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(ListdataInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //cria um cabeçalho criptogradado
    public void writeHeader(String author, String name, String type, int key) {
        this.writeHeader(author, name, false, type, key);
    }

    //cria um cabeçalho publico: key = 0
    public void writeHeader(String author, String name, String type) {
        this.writeHeader(author, name, true, type, 0);
    }

    //retorna um vetor com todo o conteudo cru da header
    public String[] getHeader() throws Exception {

        try {
            FileReader f = new FileReader(path);
            BufferedReader reader = new BufferedReader(f);

            ArrayList<String> header = new ArrayList<>();

            while (reader.ready()) {
                header.add(reader.readLine());
            }

            int h_start = header.indexOf(START_HEADER);
            int h_end = header.indexOf(END_HEADER);

            if (h_start == -1) {
                throw new Exception("Inicio do cabeçalho não encontrado");
            }

            if (h_end == -1) {
                throw new Exception("Fim do cabeçalho não encontrado");
            }

            ArrayList<String> prop = new ArrayList<String>();
            int ll = 0;

            for (int i = 0; i < header.size(); i++) {
                if (i > h_start) {
                    String content = header.get(i);
                    if (!content.equals(START_HEADER) && !content.equals(END_HEADER)) {
                        prop.add(content);
                        ll++;
                    }

                    if (i == h_end) {
                        break;
                    }
                }

            }

            return prop.toArray(new String[ll]);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListdataInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ListdataInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new String[0];
    }
    
    public String[] getContent() throws Exception{

        try {
            FileReader f = new FileReader(path);
            BufferedReader reader = new BufferedReader(f);

            ArrayList<String> content = new ArrayList<>();

            while (reader.ready()) {
                content.add(reader.readLine());
            }

            int c_start = content.indexOf(START_CONTENT);
            int c_end = content.indexOf(END_CONTENT);

            if (c_start == -1) {
                throw new Exception("Inicia da Tag content não encontrada");
            }
            if (c_end == -1) {
                throw new Exception("Fim da Tag content não encontrada");
            }

            ArrayList<String> prop = new ArrayList<>();
            int ll = 0;
            
            for (int i = 0; i < content.size(); i++) {                
                if (i > c_start) {
                    String data = content.get(i);
                    
                    if (!data.equals(START_CONTENT) && !data.equals(END_CONTENT)) {
                        prop.add(data);
                        ll++;
                    }
                    
                    if (i == c_end) {
                        break;
                    }
                }
            }
            
            return prop.toArray(new String[ll]);
            


        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListdataInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ListdataInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new String[0];
    }

    public String header_getUser() throws Exception {
        String[] content = this.getHeader();
        String prop = "";

        for (int i = 0; i < content.length; i++) {
            if (content[i].contains(START_HEADER_USER)) {
                int s = content[i].lastIndexOf(START_HEADER_USER);
                int e = content[i].lastIndexOf(END_HEADER_USER);

                if (e == -1) {
                    throw new Exception("fim da Tag USER não encontrada");
                }

                return content[i].substring(s + START_HEADER_USER.length(), e);
            }
        }

        throw new Exception("Tag USER não encontrada");
    }

    public String header_getFileName() throws Exception {
        String[] content = this.getHeader();

        for (int i = 0; i < content.length; i++) {
            if (content[i].contains(START_HEADER_NAME)) {
                int s = content[i].lastIndexOf(START_HEADER_NAME);
                int e = content[i].lastIndexOf(END_HEADER_NAME);

                if (e == -1) {
                    throw new Exception("fim da Tag NAME não encontrada");
                }

                return content[i].substring(s + START_HEADER_NAME.length(), e);
            }
        }

        throw new Exception("Tag NAME não encontrada");
    }

    public boolean header_isPublic() throws Exception {
        String[] content = this.getHeader();

        for (int i = 0; i < content.length; i++) {
            if (content[i].contains(START_HEADER_PUBLIC)) {
                int s = content[i].lastIndexOf(START_HEADER_PUBLIC);
                int e = content[i].lastIndexOf(END_HEADER_PUBLIC);

                if (e == -1) {
                    throw new Exception("fim da Tag PUBLIC não encontrada");
                }

                return Boolean.parseBoolean(content[i].substring(s + START_HEADER_PUBLIC.length(), e));
            }
        }

        throw new Exception("Tag PUBLIC não encontrada");
    }

    public String header_getType() throws Exception {
        String[] content = this.getHeader();

        for (int i = 0; i < content.length; i++) {
            if (content[i].contains(START_HEADER_TYPE)) {
                int s = content[i].lastIndexOf(START_HEADER_TYPE);
                int e = content[i].lastIndexOf(END_HEADER_TYPE);

                if (e == -1) {
                    throw new Exception("fim da Tag TYPE não encontrada");
                }

                return content[i].substring(s + START_HEADER_TYPE.length(), e);
            }
        }

        throw new Exception("Tag TYPE não encontrada");
    }
    
    private String content_getData(String tag) throws Exception {
        int s = tag.lastIndexOf(START_DATA);
        int e = tag.lastIndexOf(END_DATA);
        
        if (s == -1) {
            throw new Exception("Tag DATA não encontrada");
        }
        if (e == -1) {
            throw new Exception("Fim da Tag DATA não encontrada");
        }
        
        return tag.substring(s + START_DATA.length(), e);
    }

    public String[] content_getDatas() throws Exception {
        ArrayList<String> prop = new ArrayList<>();
        String[] contents = this.getContent();
        
        for (int i = 0; i < contents.length; i++) {
            try {
                prop.add(this.content_getData(contents[i]));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        
        return prop.toArray(new String[prop.size()]);
    }
    
    private void set_contentData(String[] toSet) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        
        ArrayList<String> arq = new ArrayList<>();
        
        while(reader.ready()) {
            
            String prop = reader.readLine();
            
            if (!prop.equals(START_CONTENT)) {
                arq.add(prop);
            }
            else {
                arq.add(prop);
                break;
            }
            
        }
        
        for (String el : toSet) {
            arq.add("\t" + START_DATA + el + END_DATA);
        }
        
        arq.add(END_CONTENT);
        
        
        StringBuilder storenewData = new StringBuilder();
        
        for (String el : arq) {
            storenewData.append(el).append("\n");
        }
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(path, false));
        
        writer.write(storenewData.toString());
        writer.close();
        
    }
    
    public void add_contentData(String newEl) throws Exception {
        ArrayList<String> contents = new ArrayList<>();
        
        for (String comp: this.content_getDatas()) {
            contents.add(comp);
        }
        
        contents.add(newEl);
        
        this.set_contentData(contents.toArray(new String[contents.size()]));
    }
    
    public void add_contentData(String[] newEls) throws Exception {
        ArrayList<String> contents = new ArrayList<>();
        
        for (String comp : this.content_getDatas()) {
            contents.add(comp);
        }
        
        for (String comp : newEls) {
            contents.add(comp);
        }
        
        this.set_contentData(contents.toArray(new String[contents.size()]));
    }

}
