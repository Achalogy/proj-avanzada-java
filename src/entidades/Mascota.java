package entidades;

import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class Mascota {

  private String raza;
  private Calendar fechaNacimiento;
  private float peso;
  private String nombre;
  private Calendar fechaAdopcion;
  private Calendar ultimaInteraccion;
  private boolean sexo;

  private Persona dueno;

  public String getRaza() {
    return raza;
  }

  public Calendar getFechaNacimiento() {
    return fechaNacimiento;
  }

  public float getPeso() {
    return peso;
  }

  public String getNombre() {
    return nombre;
  }

  public Calendar getFechaAdopcion() {
    return fechaAdopcion;
  }

  public Calendar getUltimaInteraccion() {
    return ultimaInteraccion;
  }

  public Persona getDueno() {
    return dueno;
  }

  public boolean getSexo() {
    return this.sexo;
  }

  public void setRaza(String raza) {
    this.raza = raza;
  }

  public void setFechaNacimiento(Calendar fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public void setPeso(float peso) {
    this.peso = peso;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setFechaAdopcion(Calendar fechaAdopcion) {
    this.fechaAdopcion = fechaAdopcion;
  }

  public void setUltimaInteraccion(Calendar ultimaInteraccion) {
    this.ultimaInteraccion = ultimaInteraccion;
  }

  public void setDueno(Persona dueno) {
    this.dueno = dueno;
  }

  public void setSexo(boolean sexo) {
    this.sexo = sexo;
  }

  public Mascota(String raza, Calendar fechaNacimiento, float peso, String nombre, boolean sexo) {
    this.raza = raza;
    this.fechaNacimiento = fechaNacimiento;
    this.peso = peso;
    this.nombre = nombre;
    this.sexo = sexo;

    fechaAdopcion = null;
    ultimaInteraccion = null;
  }

  public int calcularEdad() {
    Calendar fechaActual = new GregorianCalendar();
    long msToDays = 1000L * 60L * 60L * 24L;
    long msActual = fechaActual.getTime().getTime();
    long msNacimiento = fechaNacimiento.getTime().getTime();
    long daysDif = (msActual - msNacimiento) / msToDays;

    int yearsDif = ((int) daysDif) / 365;

    return (int) yearsDif;
  }

  public abstract void jugar();
}