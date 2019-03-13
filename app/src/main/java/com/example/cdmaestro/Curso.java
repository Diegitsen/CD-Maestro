package com.example.cdmaestro;

public class Curso
{
    int idCurso;
    String nombre;
    int turno;
    int temporada;
    int idProfesor;
    int hora;
    String salon;

    public Curso()
    {

    }

    public Curso(int idCurso, String nombre, int turno, int temporada, int idProfesor) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.turno = turno;
        this.temporada = temporada;
        this.idProfesor = idProfesor;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }
}
