package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import entidades.Mascota;
import entidades.Perro;

public class Utils {
  public static char askForChar(String msg, char values[]) {
    char c = ' ';
    Scanner input = new Scanner(System.in);

    boolean validChar = false;

    while (!validChar) {
      System.out.print(msg);
      String strIn = "";
      strIn = input.nextLine();

      if (strIn.length() > 1)
        continue;

      c = strIn.charAt(0);

      for (int i = 0; i < values.length && !validChar; i++) {
        if (values[i] == c) {
          validChar = true;
        }
      }
    }

    return c;
  }

  public static int printMenu(String[] menuOptions) {
    int opt = 0;

    do {
      clearConsole();
      for (int i = 0; i < menuOptions.length; i++) {
        System.out.println((i + 1) + ". " + menuOptions[i]);
      }
      System.out.print("> ");
      opt = scInt();
      System.out.println("");
    } while (opt < 1 || opt > menuOptions.length);

    return opt;
  }

  public static int printMenu(String[] menuOptions, String msg) {
    int opt = 0;

    do {
      clearConsole();
      System.out.println(msg);
      for (int i = 0; i < menuOptions.length; i++) {
        System.out.println((i + 1) + ". " + menuOptions[i]);
      }
      System.out.print("> ");
      opt = scInt();
      System.out.println("");
    } while (opt < 1 || opt > menuOptions.length);

    return opt;
  }

  public static void clearConsole() {
    for (int i = 0; i < 1000; i++) {
      System.out.println("\b");
    }
  }

  public static void pause() {
    Scanner input = new Scanner(System.in);

    System.out.print("\nPulse enter para continuar...");
    input.nextLine();
    clearConsole();
  }

  // Inputs Try Catch

  public static int scInt() {
    Scanner input = new Scanner(System.in);
    int n = -1;

    try {
      n = input.nextInt();
    } catch (Exception e) {
      writeLog(e);

      n = -1;
      System.out.print("\nEnvia Un Entero: ");
      return scInt();
    }

    return n;
  }

  public static float scFloat() {
    Scanner input = new Scanner(System.in);
    float n = -1;

    try {
      n = input.nextFloat();
    } catch (Exception e) {
      writeLog(e);
      n = -1;
      System.out.print("\nEnvia Un Float: ");
      return scFloat();
    }

    return n;
  }

  public static String scLine() {
    Scanner input = new Scanner(System.in);
    String s = "";

    try {
      s = input.nextLine();
    } catch (Exception e) {
      writeLog(e);
      s = "";
      System.out.print("\nEnvia Un String:");
      return scLine();
    }

    return s;
  }

  public static Calendar scCalendar() {
    DateFormat format = new SimpleDateFormat("dd/MM/yyy");
    Calendar d = Calendar.getInstance();
    String fecha;

    try {
      fecha = scLine();
      d.setTime(format.parse(fecha));
    } catch (ParseException e) {
      writeLog(e);
      e.getStackTrace();
      System.out.print("\nEnvia una fecha valida (dd/MM/yyyy):");
      return scCalendar();
    }

    return d;
  }

  public static float getAgeInYears(Calendar fechaN) {
    Calendar fecha = new GregorianCalendar();

    long msToDays = 1000L * 60L * 60L * 24L;
    long msActual = fecha.getTime().getTime();
    long msNacimiento = fechaN.getTime().getTime();
    long daysDif = (msActual - msNacimiento) / msToDays;

    float yearsDif = (float) daysDif / 365;

    return yearsDif;
  }

  public static String getStringMaxLength(int length) {
    try {
      String s = scLine();
      if (s.length() > length) {
        throw new Exception("Cedula no valida, envie otra");
      }
      return s;
    } catch (Exception e) {
      writeLog(e);
      System.out.print("\n" + e.getMessage() + ": ");
      return getStringMaxLength(length);
    }
  }

  public static void writeLog(Exception e) {
    try {
      BufferedWriter file = new BufferedWriter(new FileWriter("excepciones.txt", true));
      Calendar fecha = new GregorianCalendar();

      String line = fecha.getTime().toString() + " | " + (e.getMessage() != null ? e.getMessage() : e.toString())
          + "\n";

      file.write(line);

      file.close();
    } catch (Exception err) {
      writeLog(err);
      System.out.println("Hay un error escribiendo las excepciones");
    }

  }

  public static void waitFor(int milli) {
    TimeUnit time = TimeUnit.MILLISECONDS;
    try {
      time.sleep(milli);
    } catch (InterruptedException e) {
      
    }
  }

  public static String padEnd(String str, int length, String c) {
    String fStr = str;
    int size = str.length();

    fStr += c.repeat(length - size);

    return fStr;
  }

  public static String padStart(String str, int length, String c) {
    String fStr = str;
    int size = str.length();

    fStr = c.repeat(length - size) + str;

    return fStr;
  }

  public static String formatDate(Calendar date) {
    String formated = "";

    formated += date.get(Calendar.YEAR) + "/";
    formated += date.get(Calendar.MONTH) + "/";
    formated += date.get(Calendar.DAY_OF_MONTH);

    return formated;
  }

  public static void imprimirMascota(Mascota m, int size) {
    System.out.println("+-------------+--------------------------------------+");
    if (m instanceof Perro) {
      System.out.print("|   .-\"-.     |");
    } else {
      System.out.print("|   /\\_/\\  (  |");
    }

    if (m instanceof Perro)
      System.out.println(Utils.padEnd("  Perro  🐶🐶🐶", size - 24, " ") +
          "|");
    else
      System.out.println(Utils.padEnd("  Gato 😺😺😺", size - 24, " ") +
          "|");

    if (m instanceof Perro)
      System.out.print("|  /|6 6|\\    |");
    else
      System.out.print("|  ( ^.^ ) _) |");

    System.out.println(Utils.padEnd("  Nombre: " + m.getNombre(), size - 24, " ") +
        "|");

    if (m instanceof Perro)
      System.out.print("| {/(_0_)\\}   |");
    else
      System.out.print("|    \\\"/  (   |");

    System.out.println(Utils.padEnd("  Raza: " + m.getRaza(), size - 24, " ")
        + "|");

    if (m instanceof Perro)
      System.out.print("|  _/ ^ \\_    |");
    else
      System.out.print("|  ( | | )    |");

    System.out.println(
        Utils.padEnd("  Peso: " + m.getPeso() + "kg - Sexo: " + (m.getSexo() ? "♂️" : "♀️"), size - 23, " ") + "|");

    if (m instanceof Perro)
      System.out.print("| (/ /^\\ \\)-' |");
    else
      System.out.print("| (__d b__)   |");

    if (m.getFechaNacimiento() != null) {
      System.out.println(
          Utils.padEnd("  Fecha Nacimiento: " +
              Utils.formatDate(m.getFechaNacimiento()), size - 24, " ") + "|");
    } else {
      System.out
          .println(Utils.padEnd(" ", size - 24, " ") + "|");
    }

    if (m instanceof Perro)
      System.out.print("|  \"\"' '\"\"    |");
    else
      System.out.print("|             |");

    if (m.getFechaAdopcion() != null) {
      System.out
          .println(Utils.padEnd("  Fecha Adopcion: " +
              Utils.formatDate(m.getFechaAdopcion()), size - 24, " ") + "|");
    } else {
      System.out
          .println(Utils.padEnd(" ", size - 24, " ") + "|");
    }
  }
}
