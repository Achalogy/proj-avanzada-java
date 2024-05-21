package entidades;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import utils.Utils;

public class CentroAdopcion {

  private String nombre;
  private int ganancias;

  private ArrayList<Persona> clientes;
  private ArrayList<Mascota> guarderia;
  private ArrayList<Mascota> internos;

  public String getNombre() {
    return nombre;
  }

  public int getGanancias() {
    return ganancias;
  }

  public ArrayList<Persona> getClientes() {
    return clientes;
  }

  public ArrayList<Mascota> getGuarderia() {
    return guarderia;
  }

  public ArrayList<Mascota> getInternos() {
    return internos;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setGanancias(int ganancias) {
    this.ganancias = ganancias;
  }

  public void setClientes(ArrayList<Persona> clientes) {
    this.clientes = clientes;
  }

  public void setGuarderia(ArrayList<Mascota> guarderia) {
    this.guarderia = guarderia;
  }

  public void setInternos(ArrayList<Mascota> internos) {
    this.internos = internos;
  }

  public CentroAdopcion(String nombre) {
    this.nombre = nombre;
    this.clientes = new ArrayList<>();
    this.guarderia = new ArrayList<>();
    this.internos = new ArrayList<>();
  }

  public void mostrarInternos() {
    int size = 62;
    System.out.println("+----------------------------------------------------+");
    System.out.println("|                      INTERNOS                      |");
    for (Mascota m : internos) {
      if (m.calcularEdad() > 1.0)
        Utils.imprimirMascota(m, size);
    }
    System.out.println("+-------------+--------------------------------------+");
  }

  public void mostrarGuarderia() {
    int size = 62;
    System.out.println("+----------------------------------------------------+");
    System.out.println("|                     GUARDERIA                      |");
    for (Mascota m : guarderia) {
      Utils.imprimirMascota(m, size);
    }
    System.out.println("+-------------+--------------------------------------+");
  }

  public void mostrarAdopciones() {
    int size = 62;
    for (Persona c : clientes) {
      System.out.println("+----------------------------------------------------+");
      System.out.println(Utils.padEnd("| ADOPTADOS POR: " + c.getNombre(), size - 9, " ") + "|");
      System.out.println(Utils.padEnd("| Cedula:        " + c.getCedula(), size - 9, " ") + "|");
      System.out.println(Utils.padEnd("| Edad:          " + c.getEdad(), size - 9, " ") + "|");
      System.out.println(Utils.padEnd("| Residencia:    " + c.getResidencia(), size - 9, " ") + "|");
      for (Mascota m : c.getMascotas()) {
        Utils.imprimirMascota(m, size);
      }
      System.out.println("+-------------+--------------------------------------+");
    }
  }

  public void rescatarMascota(Mascota mascota) {
    internos.add(mascota);
  }

  public void darEnAdopcion(Mascota mascota, Persona persona) {
    persona.adoptarMascota(mascota);
    internos.remove(mascota);
  }

  public void agregarCliente(Persona cliente) {
    clientes.add(cliente);
  }

  public Persona buscarCliente(String cedula) {
    Persona cliente = null;

    for (Persona c : clientes)
      if (c.getCedula().equals(cedula))
        cliente = c;

    return cliente;
  }

  public void dejarMascota(Persona cliente) {
    Mascota m = null;
    cliente.mostrarMascotas();

    System.out.println("\n¿Que mascota desea dejar a nuestro cuidado?");

    while (m == null) {
      System.out.println("Nombre (0 Para salir): ");
      String nombre = Utils.scLine();

      m = this.buscarMascota(nombre, cliente.getMascotas());

      if (nombre.equals("0"))
        break;
    }

    if (m != null) {
      guarderia.add(m);
      cliente.dejarAlCuidado(m);
    }

  }

  public void recojerMascota(String nombre, Persona cliente) {
    Mascota m = this.buscarMascota(nombre, guarderia);

    if (m != null) {
      if (m.getDueno().getCedula().equals(cliente.getCedula())) {
        guarderia.remove(m);
        cliente.recojerMascota(m);
      }
    }
  }

  public void interactuar(Mascota m) {
    Utils.clearConsole();
    int opt = Utils.printMenu(new String[] { "Jugar con la mascota",
        (m instanceof Perro ? "Bañarlo" : "Cortar las uñas") },
        "Ademas de jugar con tu mascota ofrecemos otros servicios:");
    switch (opt) {
      case 1:
        m.jugar();
        break;
      case 2:
        if (m instanceof Perro) {
          ((Perro) m).baniar();
        } else {
          ((Gato) m).cortarUnias();
        }
        break;
    }

    this.ganancias += 10;
    System.out.println("Ganacias del centro de adopcion: " + this.ganancias);
  }

  public Mascota buscarMascota(String nombre, ArrayList<Mascota> lista) {
    Mascota mascota = null;

    for (Mascota m : lista) {
      if (m.getNombre().equals(nombre))
        mascota = m;
    }

    return mascota;
  }

  public void saveToFile() {
    try {
      ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("adopcion.bin"));

      file.writeObject(this.internos);
      file.close();

      System.out.println("Se han guardado correctamente los datos.");
    } catch (Exception e) {
      
      Utils.writeLog(e);
      System.out.println("No se pudo guardar el archivo");
    }

    try {
      ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("guarderia.bin"));

      file.writeObject(this.guarderia);
      file.close();

      System.out.println("Se han guardado correctamente los datos.");
    } catch (Exception e) {
      
      Utils.writeLog(e);
      System.out.println("No se pudo guardar el archivo");
    }

    try {
      ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("clientes.bin"));

      file.writeObject(this.clientes);
      file.close();

      System.out.println("Se han guardado correctamente los datos.");
    } catch (Exception e) {
      
      Utils.writeLog(e);
      System.out.println("No se pudo guardar el archivo");
    }
  }

  public void loadFromFile() {
    try {
      ObjectInputStream file = new ObjectInputStream(new FileInputStream("adopcion.bin"));

      this.internos = (ArrayList<Mascota>) file.readObject();

      file.close();
    } catch (Exception e) {
      
      Utils.writeLog(e);
      System.out.println("No se pudo cargar el archivo");
    }

    try {
      ObjectInputStream file = new ObjectInputStream(new FileInputStream("guarderia.bin"));

      this.guarderia = (ArrayList<Mascota>) file.readObject();

      file.close();
    } catch (Exception e) {
      
      Utils.writeLog(e);
      System.out.println("No se pudo cargar el archivo");
    }

    try {
      ObjectInputStream file = new ObjectInputStream(new FileInputStream("clientes.bin"));

      this.clientes = (ArrayList<Persona>) file.readObject();

      file.close();
    } catch (Exception e) {
      
      Utils.writeLog(e);
      System.out.println("No se pudo cargar el archivo");
    }
  }
}