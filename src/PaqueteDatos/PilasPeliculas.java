/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaqueteDatos;

/**
 *
 * @author alan1
 */
import java.util.Stack;

public class PilasPeliculas {
    private static Stack<String> pilaPeliculas = new Stack<>();

    public static void agregarPelicula(String tituloPelicula) {
        pilaPeliculas.push(tituloPelicula);
        System.out.println("Película agregada a la pila: " + tituloPelicula);
        System.out.println("Estado actual de la pila de películas: " + pilaPeliculas);
    }

    public static String sacarUltimaPelicula() {
        if (!pilaPeliculas.isEmpty()) {
            String ultimaPelicula = pilaPeliculas.pop();
            System.out.println("Última película sacada de la pila: " + ultimaPelicula);
            System.out.println("Estado actual de la pila de películas: " + pilaPeliculas);
            return ultimaPelicula;
        } else {
            System.out.println("La pila de películas está vacía.");
            return null;
        }
    }

    public static boolean estaVacia() {
        return pilaPeliculas.isEmpty();
    }

    public static int tamanoPila() {
        return pilaPeliculas.size();
    }

    public static String verCima() {
        if (!pilaPeliculas.isEmpty()) {
            return pilaPeliculas.peek();
        }
        return null;
    }
}
