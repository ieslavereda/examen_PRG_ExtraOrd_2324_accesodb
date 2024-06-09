package es.ieslavereda.model;

import java.io.Serializable;

public abstract class Worker implements Payable, Comparable<Worker>, Serializable {
    private int id;
    private String nombre;
    private String apellidos;
    private String DNI;
    private int edad;
    private String email;
    protected int experiencia;

    public Worker(int id, String nombre, String apellidos, String DNI, int edad, String email, int experiencia) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
        this.edad = edad;
        this.email = email;
        this.experiencia = experiencia;
    }

    public abstract String getRole();

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDNI() {
        return DNI;
    }

    public int getEdad() {
        return edad;
    }

    public String getEmail() {
        return email;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return getDNI().toLowerCase().hashCode();
    }

    @Override
    public int compareTo(Worker o) {
        return (apellidos.compareToIgnoreCase(o.apellidos)==0)?
                nombre.compareToIgnoreCase(o.nombre):
                apellidos.compareToIgnoreCase(o.apellidos);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(!(obj instanceof Worker)) return false;
        Worker p = (Worker) obj;
        return p.getDNI().equalsIgnoreCase(getDNI());
    }

    @Override
    public String getFullName(){
        return getNombre() + " " + getApellidos();
    }
    @Override
    public int getYearsExperience(){
        return getExperiencia();
    }

    @Override
    public String toString() {
        return "Worker{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", DNI='" + DNI + '\'' +
                ", edad=" + edad +
                ", email='" + email + '\'' +
                ", experiencia=" + experiencia +
                '}';
    }
}
