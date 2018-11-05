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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Essa classe faz a interpretação e modificação dos arquivos
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
    
    /**
     * Bubble sort
     */
    public final int BUBBLE_SORT = 0;
    
    /**
     * Selection sort
     */
    public final int SELECTION_SORT = 1;
    
    /**
     * Merge sort
     */
    public final int MERGE_SORT = 2;
    
    /**
     * Quick sort
     */
    public final int QUICK_SORT = 3;
    
    /**
     * Insertion sort
     */
    public final int INSERTION_SORT = 4;
    
    /**
     * Comb sort
     */
    public final int COMB_SORT = 5;
    
    /**
     * Head sort
     */
    public final int HEAP_SORT = 6;
    
    /**
     * Shell sort
     */
    public final int SHELL_SORT = 7;
    
    /**
     * Gnome sort
     */
    public final int GNOME_SORT = 8;
    
    /**
     * Cocktail sort
     */
    public final int COCKTAIL_SORT = 9;
    
    /**
     * Bogo sort
     */
    public final int BOGO_SORT = 10;


    private File path;
    
    /**
     * Verifica a existencia do arquivo a ser interpretado, caso ele não exita o cria
     * @param path Arquivo
     */
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

    /**
     * Cria um cabeçalho criptografado
     * @param author Autor
     * @param name Nome do arquivo
     * @param type Typo da lista
     * @param key Chave de criptografia
     */
    public void writeHeader(String author, String name, String type, int key) {
        this.writeHeader(author, name, false, type, key);
    }

    //cria um cabeçalho publico: key = 0
    /**
     * Cria um cabeçalho sem criptografia
     * @param author Autor
     * @param name Nome do arquivo
     * @param type Tipo da lista
     */
    public void writeHeader(String author, String name, String type) {
        this.writeHeader(author, name, true, type, 0);
    }

    /**
     * Retorna um vetor com o conteudo do cabeçalho bruto
     * @return String[] - Vetor do conteudo do cabeçalho
     * @throws Exception Inicio ou fim do cabeçalho não encontrado
     */
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
    
    /**
     * Retorna um vetor com o conteudo dos dados bruto
     * @return String[] - Vetor do conteudo de dados
     * @throws Exception Inicio ou fim do conteudo não encontrado
     */
    public String[] getContent() throws Exception {

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
    
    /**
     * Retorna o campo de usuario do header
     * @return String - Usuario
     * @throws Exception Inicio ou fim da tag USER não encontrada
     */
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
    
    /**
     * Retorna o campo de nome do arquivo do header
     * @return String - Nome do arquivo
     * @throws Exception Inicio ou fim da tag NAME não encontrada
     */
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
    
    /**
     * Retorna se o arquivo é publico ou não
     * @return boolean - verificabilidade da publicidade do arquivo
     * @throws Exception Inicio ou fim da tag PUBLIC não encontrada
     */
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
    
    /**
     * Retorna o tipo da lista do arquivo do header
     * @return String - Tipo da lista
     * @throws Exception Inicio ou fim da tag PUBLIC não encontrada
     */
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

    private String content_getData(String tag, int key) throws Exception {
        int s = tag.lastIndexOf(START_DATA);
        int e = tag.lastIndexOf(END_DATA);

        if (s == -1) {
            throw new Exception("Tag DATA não encontrada");
        }
        if (e == -1) {
            throw new Exception("Fim da Tag DATA não encontrada");
        }

        return new Cryp().unCifre(tag.substring(s + START_DATA.length(), e), key);
    }
    
    /**
     * Retorna um vetor com todo o conteudo da tag CONTENT
     * @param key Chave de cifragem
     * @return String[] - Dados contidos na tag CONTENT
     * @throws Exception Fim ou Inicio da tag DATA não encontrada
     */
    public String[] content_getDatas(int key) throws Exception {
        ArrayList<String> prop = new ArrayList<>();
        String[] contents = this.getContent();

        for (int i = 0; i < contents.length; i++) {
            try {
                prop.add(this.content_getData(contents[i], key));
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return prop.toArray(new String[prop.size()]);
    }

    private void set_contentData(String[] toSet, int key) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        ArrayList<String> arq = new ArrayList<>();

        while (reader.ready()) {

            String prop = reader.readLine();

            if (!prop.equals(START_CONTENT)) {
                arq.add(prop);
            } else {
                arq.add(prop);
                break;
            }

        }

        Cryp cc = new Cryp();

        for (String el : toSet) {
            arq.add("\t" + START_DATA + cc.cifre(el, key) + END_DATA);
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
    
    /**
     * Adiciona uma String aos dados em uma posição especifica
     * @param newEl Novo dado a ser adicionado
     * @param index Posição ao ser adicionado
     * @param key Chave de cifragem
     * @throws Exception Posição inexistente
     */
    public void add_contentData(String newEl, int index, int key) throws Exception{
        try {
            ArrayList<String> contents = new ArrayList<>();

            for (String comp : this.content_getDatas(key)) {
                contents.add(comp);
            }

            contents.add(index, newEl);

            this.set_contentData(contents.toArray(new String[contents.size()]), key);
        }catch (Exception e) {
            throw new Exception("Posição inexistente");
        }
    }
    
    /**
     * Adiciona varias Strings aos dados em uma posição especifica
     * @param newEl Vetor de novos elementos
     * @param index Posição ao ser adicionados
     * @param key Chave de cifragem
     * @throws Exception Posição inexistente
     */
    public void add_contentData(String newEl[], int index, int key) throws Exception {
        try {
            ArrayList<String> contents = new ArrayList<>();
            
            for (String comp : this.content_getDatas(key)) {
                contents.add(comp);
            }
            
            for (String el : newEl) {
                contents.add(index, el);
                index++;
            }
            
            this.set_contentData(contents.toArray(new String[contents.size()]), key);
        }catch (Exception e) {
            throw new Exception("Posição inexistente");
        }
    }
    
    
    /**
     * Adiciona uma String na ultima posição dos dados
     * @param newEl Elemento a ser adicionado
     * @param key Chave de cifragem
     * @throws Exception 
     */
    public void addLast_contentData(String newEl, int key) throws Exception {
        ArrayList<String> contents = new ArrayList<>();

        for (String comp : this.content_getDatas(key)) {
            contents.add(comp);
        }

        contents.add(newEl);

        this.set_contentData(contents.toArray(new String[contents.size()]), key);
    }
    
    /**
     * Adiciona Varias Strings na ultima posição dos dados
     * @param newEls Elementos a serem adicionados
     * @param key Chave de cifragem
     * @throws Exception 
     */
    public void addLast_contentData(String[] newEls, int key) throws Exception {
        ArrayList<String> contents = new ArrayList<>();

        for (String comp : this.content_getDatas(key)) {
            contents.add(comp);
        }

        for (String comp : newEls) {
            contents.add(comp);
        }

        this.set_contentData(contents.toArray(new String[contents.size()]), key);
    }
    
    /**
     * Adiciona uma String na primeira posição dos dados
     * @param newEL Elemento a ser adicionado
     * @param key Chave de cifragem
     * @throws Exception 
     */
    public void addFirst_contentData(String newEL, int key) throws Exception {
        ArrayList<String> contents = new ArrayList<>();

        contents.add(newEL);

        for (String comp : this.content_getDatas(key)) {
            contents.add(comp);
        }

        this.set_contentData(contents.toArray(new String[contents.size()]), key);
    }

    
    /**
     * Adiciona Varias Strings na primeira posição dos dados
     * @param newEls Elemento a ser adicionado
     * @param key Chave de cifragem
     * @throws Exception 
     */
    public void addFirst_contentData(String[] newEls, int key) throws Exception {
        ArrayList<String> contents = new ArrayList<>();

        for (String comp : newEls) {
            contents.add(comp);
        }

        for (String comp : this.content_getDatas(key)) {
            contents.add(comp);
        }

        this.set_contentData(contents.toArray(new String[contents.size()]), key);
    }
    
    /**
     * Remove um item em determinada posição dos dados
     * @param index Posição do item a ser removido
     * @param key Chave de cifragem
     * @throws Exception Não existe um elemento nessa posição
     */
    public void remove_contentData(int index, int key) throws Exception {
        try {
            ArrayList<String> content = new ArrayList<>();

            for (String comp : this.content_getDatas(key)) {
                content.add(comp);
            }

            content.remove(index);

            this.set_contentData(content.toArray(new String[content.size()]), key);
        } catch (Exception e) {
            throw new Exception("Não existe um elemento nessa posição");
        }

    }
    
    /**
     * Remove um item na ultima posição dos dados
     * @param key Chave de cifragem
     * @throws Exception 
     */
    public void removeLast_contentData(int key) throws Exception {
        int size = this.content_getDatas(key).length;

        remove_contentData(size - 1, key);
    }
    
    /**
     * Remove um item na primeira posição dos dados
     * @param key Chave de cifragem
     * @throws Exception 
     */
    public void removeFirst_contentData(int key) throws Exception {
        remove_contentData(0, key);
    }
    
    /**
     * Organiza a lista com o metodo selecionado
     * @param method Metodo de sorteamento - 10 disponiveis até o momento
     * @param key chave de cifrage,
     * @return long - Tempo da ordenação
     * @throws Exception 
     */
    public long sort(int method, int key) throws Exception{
        
        //bubble sort
        if (method == this.BUBBLE_SORT) {
            long ms = System.currentTimeMillis();
            String[] content = this.content_getDatas(key);
            String temp;
                
            for (int i = 0; i < content.length - 1; i++) {
                for (int j  = 0; j < content.length - i - 1; j++) {
                    if (content[j+1].compareTo(content[j]) < 0) {
                        temp = content[j];
                        content[j] = content[j + 1];
                        content[j + 1] = temp;
                    }
                }
            }
                
            this.set_contentData(content, key);
            return System.currentTimeMillis() - ms;
        }
        
        //selection sort
        else if (method == this.SELECTION_SORT) {
            long ms = System.currentTimeMillis();
            String[] content = this.content_getDatas(key);
            
            for (int i = 0; i < content.length - 1; i++) {
                for (int j = i + 1; j < content.length; j++) {
                    if (content[i].compareTo(content[j]) > 0) {
                        String temp = content[j];
                        content[j] = content[i];
                        content[i] = temp;
                    }
                }
            }
            
            this.set_contentData(content, key);
            return System.currentTimeMillis() - ms;
        }
        
        //merge sort
        else if (method == this.MERGE_SORT) {
            
            class mergeSort {
                
                protected void mergeSort(String[] a, int n) {
                    if (n < 2) {
                        return;
                    }

                    int mid = n / 2;
                    String[] l = new String[mid];
                    String[] r = new String[n - mid];

                    for (int i = 0; i < mid; i++) {
                        l[i] = a[i];
                    }

                    for (int i = mid; i < n; i++) {
                        r[i - mid] = a[i];
                    }

                    mergeSort(l, mid);
                    mergeSort(r, n - mid);

                    merge(a, l, r, mid, n - mid);
                }

                protected void merge(String[] a, String[] l, String[] r, int left, int right) {

                    int i = 0, j = 0, k = 0;

                    while (i < left && j < right) {
                        if (l[i].compareTo(r[j]) < 0) {
                            a[k++] = l[i++];
                        } else {
                            a[k++] = r[j++];
                        }
                    }

                    while (i < left) {
                        a[k++] = l[i++];
                    }

                    while (j < right) {
                        a[k++] = r[j++];
                    }        

                }
            }
            
            mergeSort merge = new mergeSort();
            
            long ms = System.currentTimeMillis();
            
            String[] content = this.content_getDatas(key);
                        
            merge.mergeSort(content, content.length);
            
            this.set_contentData(content, key);
            
            return System.currentTimeMillis() - ms;
           
        }
        
        //quick sort
        else if (method == this.QUICK_SORT) {
            
            class quickSort {
                
                protected void quickSort(String[] vet, int start, int end) {
                    if (start < end) {
                        int posPivo = separe(vet, start, end);
                        quickSort(vet, start, posPivo - 1);
                        quickSort(vet, posPivo + 1, end);
                    }
                }
                
                protected int separe(String[] vet, int start, int end) {
                    String pivo = vet[start];
                    int i = start + 1;
                    int f = end;

                    while (i <= f) {
                        if (vet[i].compareTo(pivo) <= 0) {
                            i++;
                        }
                        else if (pivo.compareTo(vet[f]) < 0) {
                            f--;
                        }
                        else {
                            String change = vet[i];
                            vet[i] = vet[f];
                            vet[f] = change;
                            i++;
                            f--;
                        }
                    }
                    vet[start] = vet[f];
                    vet[f] = pivo;
                    return f;
                }
            }
            
            quickSort quick = new quickSort();
            
            long ms = System.currentTimeMillis();
            
            String[] content = this.content_getDatas(key);
                        
            quick.quickSort(content, 0, content.length - 1);
            
            this.set_contentData(content, key);
            
            return System.currentTimeMillis() - ms;
        }
        
        //insertion sort
        else if (method == this.INSERTION_SORT) {
            long ms = System.currentTimeMillis();
            String[] content = this.content_getDatas(key);
            
            int n = content.length;
            for (int i = 1; i < n; ++i) {
                String k = content[i];
                int j = i - 1;
                
                while (j >= 0 && content[j].compareTo(k) > 0) {
                    content[j+1] = content[j];
                    j = j - 1;
                }
                content[j+1] = k;
            }
            
            this.set_contentData(content, key);
            
            
            return System.currentTimeMillis() - ms;
        }
        
        //combsort
        else if (method == this.COMB_SORT) {
            
            class combSort {
                
                protected int getNextGap(int gap) {
                    gap = (gap * 10)/13;
                    if (gap < 1) {
                        return 1;
                    }
                    return gap;
                }
                
                protected void sort(String arr[]) {
                    int n = arr.length;
                    
                    int gap = n;
                    
                    boolean swapped = true;
                    
                    while (gap != 1 || swapped == true) {
                        gap = this.getNextGap(gap);
                        swapped = false;
                        
                        for (int i = 0; i < n-gap; i++) {
                            if (arr[i].compareTo(arr[i+gap]) > 0) {
                                String temp = arr[i];
                                arr[i] = arr[i+gap];
                                arr[i+gap] = temp;
                                
                                swapped = true;
                            }
                        }
                    }
                }
                
            }
            
            combSort comb = new combSort();
            
            long ms = System.currentTimeMillis();
            
            String[] content = this.content_getDatas(key);
            
            comb.sort(content);
            
            this.set_contentData(content, key);
            
            return System.currentTimeMillis() - ms;
            
        }
        
        //heap sort
        else if (method == this.HEAP_SORT) {
            
            class heapSort {

                protected void sort(String[] arr) {
                    int n = arr.length;
                    
                    for (int i = n / 2 - 1; i >= 0; i--) {
                        heapify(arr, n, i);
                    }
                    
                    for (int i = n - 1; i >= 0; i--) {
                        String temp = arr[0];
                        arr[0] = arr[i];
                        arr[i] = temp;
                        
                        heapify(arr, i, 0);
                    }
                }
                
                protected void heapify(String arr[], int n, int i) {
                    int largest = i;
                    int l = 2 * i + 1;
                    int r = 2 * i + 2;
                    
                    if (l < n && arr[l].compareTo(arr[largest]) > 0) {
                        largest = l;
                    }
                    
                    if (r < n && arr[r].compareTo(arr[largest]) > 0) {
                        largest = r;
                    }
                    
                    if (largest != i) {
                        String swap = arr[i];
                        arr[i] = arr[largest];
                        arr[largest] = swap;
                        
                        heapify(arr, n, largest);
                    }
                }
            }
            
            heapSort heap = new heapSort();
            
            long ms = System.currentTimeMillis();
            
            String[] content = this.content_getDatas(key);
            
            heap.sort(content);
            
            this.set_contentData(content, key);
            
            return System.currentTimeMillis() - ms;
        }
        
        //Shell sort
        else if (method == this.SHELL_SORT) {
            long ms = System.currentTimeMillis();
            
            String[] content = this.content_getDatas(key);
            
            int n = content.length;
            for (int gap = n/2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i += 1) {
                    String temp = content[i];
                    
                    int j;
                    
                    for (j = i; j >= gap && content[j - gap].compareTo(temp) > 0; j -= gap) 
                        content[j] = content[j - gap];
                    content[j] = temp;
                }
            }
            
            this.set_contentData(content, key);
            
            return System.currentTimeMillis() - ms;
        }
        
        //gnome sort
        else if (method == this.GNOME_SORT) {
            
            long ms = System.currentTimeMillis();
            
            String[] content = this.content_getDatas(key);
            
            int n = content.length;
            int index = 0;
            
            while (index < n) {
                if (index == 0) {
                    index++;
                }
                if (content[index].compareTo(content[index - 1]) >= 0) {
                    index++;
                }
                else {
                    String temp = "";
                    temp = content[index];
                    content[index] = content[index - 1];
                    content[index - 1] = temp;
                    index--;
                }
            }
            
            this.set_contentData(content, key);
            
            return System.currentTimeMillis() - ms;
            
        }
        
        //coctail sort
        else if (method == this.COCKTAIL_SORT) {
            
            long ms = System.currentTimeMillis();
            
            String[] content = this.content_getDatas(key);
            
            boolean swaped = true;
            int start = 0;
            int end = content.length;
            
            while (swaped == true) {
                swaped = false;
                
                for (int i = start; i < end - 1; ++i) {
                    if (content[i].compareTo(content[i + 1]) > 0) {
                        String temp = content[i];
                        content[i] = content[i + 1];
                        content[i + 1] = temp;
                        swaped = true;
                    }
                }
                
                if (swaped == false) {
                    break;
                }
                
                swaped = false;
                
                end = end - 1;
                
                for (int i = end - 1; i >= start; i--) {
                    if (content[i].compareTo(content[i + 1]) > 0) {
                        String temp = content[i];
                        content[i] = content[i + 1];
                        content[i + 1] = temp;
                        swaped = true;
                    }
                }
                
                start = start + 1;
            }
            
            this.set_contentData(content, key);
            
            return System.currentTimeMillis() - ms;
            
        }
        
        //bogo sort
        else if (method == this.BOGO_SORT) {
           class BogoSort {
               protected BogoSort(String[] i) {
                   while (!isSorted(i)) {
                       shuffle(i);
                   }
               }
               
               private void shuffle(String[] i) {
                   for (int x = 0; x < i.length; ++x) {
                       int index1 = (int) (Math.random() * i.length);
                       int index2 = (int) (Math.random() * i.length);
                       String a = i[index1];
                       i[index1] = i[index2];
                       i[index2] = a;
                   }
               }
               
               private boolean isSorted(String[] i) {
                   for (int x = 0; x < i.length - 1; ++x) {
                       if (i[x].compareTo(i[x + 1]) > 0) {
                           return false;
                       }
                   }
                   return true;
               }
           }
           
           long ms = System.currentTimeMillis();
           
           String[] content = this.content_getDatas(key);
           
           new BogoSort(content);
           
           this.set_contentData(content, key);
           
           return System.currentTimeMillis() - ms;
        }

        else {
            throw new Exception("Metodo de ordenação não encontrado");
        }
        
    }
    
    
}