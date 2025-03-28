package main;

import java.util.Vector;

class Pila {
    private int tamañoMaximo; 
    private int cima;
    protected Vector<Muneca> listaPila;

    public Pila(int tamañoMaximo) {
        this.tamañoMaximo = tamañoMaximo; 
        cima = -1;
        listaPila = new Vector<>(tamañoMaximo);
    }

    public void insertar(Muneca muneca) throws Exception {
        if (listaPila.size() >= tamañoMaximo) {
            throw new Exception("No se puede agregar más muñecas. El tamaño de la pila ha sido alcanzado.");
        }
        cima++;
        listaPila.addElement(muneca);
    }

    public Muneca quitar() throws Exception {
        if (pilaVacia()) {
            throw new Exception("La pila está vacía. No se puede quitar ninguna muñeca.");
        }
        Muneca aux = listaPila.elementAt(cima);
        listaPila.removeElementAt(cima);
        cima--;
        return aux;
    }

    public Muneca cimaPila() throws Exception {
        if (pilaVacia()) {
            throw new Exception("La pila está vacía.");
        }
        return listaPila.elementAt(cima);
    }

    public boolean pilaVacia() {
        return cima == -1;
    }

    public void limpiarPila() throws Exception {
        while (!pilaVacia()) {
            quitar();
        }
    }

    public void sumarUno() {
        if (!pilaVacia()) {
            Muneca muneca = listaPila.elementAt(cima);
            int numeroMunecas = muneca.getNumeroMunecas() + 1;
            if (numeroMunecas <= 8) {
                muneca.setNumeroMunecas(numeroMunecas);
            }
        }
    }

    public void restarUno() {
        if (!pilaVacia()) {
            Muneca muneca = listaPila.elementAt(cima);
            int numeroMunecas = muneca.getNumeroMunecas() - 1;
            if (numeroMunecas >= 1) {
                muneca.setNumeroMunecas(numeroMunecas);
            }
        }
    }

    public int getTamañoMaximo() {
        return tamañoMaximo;
    }
}

