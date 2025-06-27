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

public class PilasRegistro {
    private static Stack<String> pilaUsuarios = new Stack<>();

    public static void agregarRegistro(String registro) {
        pilaUsuarios.push(registro);
    }

    public static String obtenerUltimoRegistro() {
        return pilaUsuarios.isEmpty() ? null : pilaUsuarios.peek();
    }

    public static String eliminarUltimoRegistro() {
        return pilaUsuarios.isEmpty() ? null : pilaUsuarios.pop();
    }

    public static boolean estaVacia() {
        return pilaUsuarios.isEmpty();
    }

    public static void mostrarContenido() {
        System.out.println("Contenido de la pila:");
        for (String usuario : pilaUsuarios) {
            System.out.println(usuario);
        }
    }
}