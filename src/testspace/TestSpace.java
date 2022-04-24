/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testspace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader; // versione precedente 
import java.io.StringWriter;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ste_1
 */
public class TestSpace {

    public static void main(String[] args) throws IOException {

        String fileName = "C:\\Users\\ste_1\\Desktop\\PhytonDemo.py";
        System.out.println("Nome del file=" + fileName + ". Tipo di file= " + getFileExtension(fileName));

        String typeOfFile = getFileExtension(fileName);

        if (typeOfFile.equals("c") || typeOfFile.equals("java")) {
            cLike(fileName);
        } else if (typeOfFile.equals("py")) {
            pythonType(fileName);
        }

    }

    //funzione  per estrarre il tipo di  file  , return string del tipo del file
    public static String getFileExtension(String fullName) {

        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private static void stampaCommenti(List<String> listaCommenti) {
        System.err.println("--------------- \n ");
        for (String string : listaCommenti) {
            System.err.println(string);

        }
    }

    public static void cLike(String fileName) throws IOException {
        String contenuto;
        try ( var reader = new BufferedReader(new FileReader(fileName));) {
            try ( var writer = new StringWriter()) {
                reader.transferTo(writer);
                contenuto = writer.toString();
            }
        }

        StringBuilder commentoAttuale = new StringBuilder();
        List<String> listaCommenti = new ArrayList<>();
        boolean contenutoMultiRiga = false;
        boolean contenutoRiga = false;
        boolean inStringa = false;
        char caratterePrecedente = 0;

        for (int i = 0; i < contenuto.length(); i++) {
            boolean ultimoCarattere = i == contenuto.length() - 1;
            char carattereAttuale = contenuto.charAt(i);

            if (inStringa) {

                if (carattereAttuale == '"' && caratterePrecedente != '\\') {
                    inStringa = false;
                }
            } else if (contenutoRiga || contenutoMultiRiga) {
                if (contenutoRiga && ((carattereAttuale == '\n' || ultimoCarattere) || ultimoCarattere)) {
                    contenutoRiga = false;
                    listaCommenti.add(commentoAttuale.toString());
                    commentoAttuale.setLength(0);
                } else if (contenutoMultiRiga && (carattereAttuale == '/' && caratterePrecedente == '*')) {
                    contenutoMultiRiga = false;
                    listaCommenti.add(commentoAttuale.toString());
                    commentoAttuale.setLength(0);
                } else {
                    commentoAttuale.append(carattereAttuale);
                }

            } else if (carattereAttuale == '/' && caratterePrecedente == '/') {
                contenutoRiga = true;
            } else if (carattereAttuale == '*' && caratterePrecedente == '/') {
                contenutoMultiRiga = true;
            } else if (carattereAttuale == '"') {
                inStringa = true;
            }

            caratterePrecedente = carattereAttuale;

        }
        stampaCommenti(listaCommenti);
    }

    private static void pythonType(String fileName) throws IOException {
        String contenuto;
        try ( var reader = new BufferedReader(new FileReader(fileName));) {
            try ( var writer = new StringWriter()) {
                reader.transferTo(writer);
                contenuto = writer.toString();

                StringBuilder commentoAttuale = new StringBuilder();
                List<String> listaCommenti = new ArrayList<>();
                boolean contenutoMultiRiga = false;
                boolean contenutoRiga = false;
                boolean inStringa = false;
                char caratterePrecedente = 0;

                for (int i = 0; i < contenuto.length(); i++) {
                    boolean ultimoCarattere = i == contenuto.length() - 1;
                    char carattereAttuale = contenuto.charAt(i);

                    if (inStringa) {

                        if (carattereAttuale == '"' && caratterePrecedente != '#') {
                            inStringa = false;
                        }
                    } else if (contenutoRiga || contenutoMultiRiga) {
                        if (contenutoRiga && ((carattereAttuale == '\n' || ultimoCarattere) || ultimoCarattere)) {
                            contenutoRiga = false;
                            listaCommenti.add(commentoAttuale.toString());
                            commentoAttuale.setLength(0);
                        } else {
                            commentoAttuale.append(carattereAttuale);
                        }

                    } else if (carattereAttuale == '#') {
                        contenutoRiga = true;
                    } else if (carattereAttuale == '"') {
                        inStringa = true;
                    }

                    caratterePrecedente = carattereAttuale;

                }
                stampaCommenti(listaCommenti);

            }
        }
    }

}
