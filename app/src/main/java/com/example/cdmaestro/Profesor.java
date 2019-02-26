package com.example.cdmaestro;

public class Profesor
{
    private int idProfesor;
    private String nombre;
    private String apellido;
    private int idMinisterio;
    private String usuario;
    private String contrasenia;

    public Profesor()
    {

    }

    public Profesor(int idProfesor, String nombre, String apellido, int idMinisterio, String usuario, String contrasenia) {
        this.idProfesor = idProfesor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idMinisterio = idMinisterio;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getIdMinisterio() {
        return idMinisterio;
    }

    public void setIdMinisterio(int idMinisterio) {
        this.idMinisterio = idMinisterio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
