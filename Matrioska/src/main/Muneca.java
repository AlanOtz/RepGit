package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class Muneca extends JPanel {
    private int numeroMunecas;

    public Muneca(int numeroMunecas) {
        this.numeroMunecas = numeroMunecas;
        setPreferredSize(new Dimension(1500, 1500));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imagen = new ImageIcon(getClass().getResource("Mun.png"));
        for (int i = 0; i < numeroMunecas; i++) {
            g.drawImage(imagen.getImage(), 10 + (i * 170), 400 + (i * 20), 150 - (i * 10), 400 - (i * 20), this);
        }
    }

    public int getNumeroMunecas() {
        return numeroMunecas;
    }

    public void setNumeroMunecas(int numeroMunecas) {
        this.numeroMunecas = numeroMunecas;
        repaint();
    }
}

class MatrioskaGUI extends JFrame {
    private JTextField textField;
    private JTextArea textAreaPila;
    private JPanel panelDibujo;
    private int pilaSize = 0;  
    private int maxMunecas;  

    public MatrioskaGUI() {
        super("Matrioskas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);  
        setLayout(new BorderLayout());

        
        JPanel panelControl = new JPanel();
        panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.Y_AXIS));
        panelControl.setBackground(new Color(60, 63, 65));
        panelControl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

       
        textField = new JTextField(4);
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        textField.setPreferredSize(new Dimension(80, 30));  

        
        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); 
        panelTexto.setBackground(new Color(60, 63, 65));
        JLabel label = new JLabel("Número de Muñecas:");
        label.setForeground(Color.WHITE);  // Letras blancas
        panelTexto.add(label);
        panelTexto.add(textField);

        JButton botonCrear = new JButton("Crear Matrioska");
        JButton botonSumar = new JButton("Añadir Muñeca");
        JButton botonRestar = new JButton("Quitar Muñeca");

        
        botonCrear.setBackground(new Color(51, 153, 255));
        botonCrear.setForeground(Color.WHITE);
        botonCrear.setFont(new Font("Arial", Font.BOLD, 14));

        botonSumar.setBackground(new Color(76, 175, 80));
        botonSumar.setForeground(Color.WHITE);
        botonSumar.setFont(new Font("Arial", Font.BOLD, 14));

        botonRestar.setBackground(new Color(255, 87, 34));
        botonRestar.setForeground(Color.WHITE);
        botonRestar.setFont(new Font("Arial", Font.BOLD, 14));

      
        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numero = Integer.parseInt(textField.getText());
                    if (numero >= 1 && numero <= 8) {
                        maxMunecas = numero;  // Establece el máximo de muñecas
                        pilaSize = numero;  // Inicializa la pila con ese número
                        panelDibujo.removeAll();
                        Muneca muneca = new Muneca(numero);
                        panelDibujo.add(muneca);
                        panelDibujo.revalidate();
                        panelDibujo.repaint();
                        actualizarTextAreaPila();
                    } else {
                        JOptionPane.showMessageDialog(null, "El número debe estar en el rango de 1 a 8.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
                }
            }
        });

        
        botonSumar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pilaSize < maxMunecas) {
                    pilaSize++;
                    panelDibujo.removeAll();
                    Muneca muneca = new Muneca(pilaSize);
                    panelDibujo.add(muneca);
                    panelDibujo.revalidate();
                    panelDibujo.repaint();
                    actualizarTextAreaPila();
                } else {
                    JOptionPane.showMessageDialog(null, "No puedes agregar más muñecas, ya alcanzaste el límite.");
                }
            }
        });

        
        botonRestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pilaSize > 1) {
                    pilaSize--;
                    panelDibujo.removeAll();
                    Muneca muneca = new Muneca(pilaSize);
                    panelDibujo.add(muneca);
                    panelDibujo.revalidate();
                    panelDibujo.repaint();
                    actualizarTextAreaPila();
                } else {
                    JOptionPane.showMessageDialog(null, "No puedes quitar más muñecas, ya solo queda una.");
                }
            }
        });

        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        panelBotones.setBackground(new Color(60, 63, 65));
        panelBotones.add(botonCrear);
        panelBotones.add(botonSumar);
        panelBotones.add(botonRestar);

       
        panelBotones.setPreferredSize(new Dimension(200, 100));  

        
        panelControl.add(panelTexto);
        panelControl.add(panelBotones);

        
        panelDibujo = new JPanel();
        panelDibujo.setBackground(new Color(240, 240, 240));  

        
        add(panelControl, BorderLayout.WEST);
        add(panelDibujo, BorderLayout.CENTER);

        
        textAreaPila = new JTextArea(10, 20);
        textAreaPila.setEditable(false);
        textAreaPila.setFont(new Font("Arial", Font.PLAIN, 18));
        textAreaPila.setForeground(Color.WHITE);  
        textAreaPila.setBackground(new Color(60, 63, 65));  
        JScrollPane scrollPane = new JScrollPane(textAreaPila);
        scrollPane.setBackground(new Color(60, 63, 65));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 87, 34)));
        add(scrollPane, BorderLayout.SOUTH);
    }

  
    private void actualizarTextAreaPila() {
        textAreaPila.setText("Pila de muñecas (máx " + maxMunecas + "): " + pilaSize + "\n");
    }

}









