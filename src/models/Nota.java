package models;

import java.io.Serializable;

public class Nota implements Serializable {

    private String nombre;
    private String apellidos;
    private String nota;

    public Nota(String nombre, String apellidos, String nota) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nota = nota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
