package controllers;

import models.Nota;

import java.io.*;
import java.util.ArrayList;

public class Streams {

    public static void exportarNotas(ArrayList<Nota> notas) throws IOException {
        FileOutputStream fileWriter = new FileOutputStream("src/resources/data/Notas");
        ObjectOutputStream objectWriter = new ObjectOutputStream(fileWriter);
        objectWriter.writeObject(notas);
        objectWriter.close();
        fileWriter.close();
    }

    public static ArrayList<Nota> importarNotas() throws IOException, ClassNotFoundException{
        ArrayList<Nota> notas = new ArrayList<Nota>();
        FileInputStream fileReader = new FileInputStream("src/resources/data/Notas");
        ObjectInputStream objectReader = new ObjectInputStream(fileReader);
        notas = (ArrayList<Nota>) objectReader.readObject();
        objectReader.close();
        fileReader.close();
        return notas;
    }
}
