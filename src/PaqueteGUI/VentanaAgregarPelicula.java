/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaqueteGUI;

/**
 *
 * @author alan1
 */
import PaqueteDatos.Conector;
import PaqueteDatos.PilasPeliculas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaAgregarPelicula extends JDialog {
    private JTextField txtTitulo;
    private JTextField txtFechaEstreno;
    private JTextField txtDuracionHoras;
    private JTextField txtDuracionMinutos;
    private JComboBox<String> comboClasificacion;
    private JTextField txtIdioma;
    private JTextField txtActores;
    private JButton btnAgregarImagen;
    private JLabel lblNombreImagen;
    private JTextArea txtResena;
    private JLabel lblLimiteResena;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private String rutaImagenSeleccionada = "";

    private static final String DEFAULT_DATE_FORMAT = "yyyy/mm/dd";

    public VentanaAgregarPelicula(JFrame parent) {
        super(parent, "Agregar Película", true);
        setUndecorated(true);

        setSize(600, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(40, 40, 40));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        JLabel lblTituloVentana = new JLabel("Agregar Nueva Película");
        lblTituloVentana.setForeground(Color.WHITE);
        lblTituloVentana.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTituloVentana.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTituloVentana, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 20, 5, 20);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setForeground(Color.LIGHT_GRAY);
        lblTitulo.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblTitulo, gbc);
        txtTitulo = new JTextField(25);
        txtTitulo.setFont(textFont);
        txtTitulo.setBackground(new Color(60, 60, 60));
        txtTitulo.setForeground(Color.WHITE);
        txtTitulo.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtTitulo, gbc);

        JLabel lblFechaEstreno = new JLabel("Fecha de Estreno:");
        lblFechaEstreno.setForeground(Color.LIGHT_GRAY);
        lblFechaEstreno.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblFechaEstreno, gbc);
        txtFechaEstreno = new JTextField(DEFAULT_DATE_FORMAT, 25);
        txtFechaEstreno.setFont(textFont);
        txtFechaEstreno.setBackground(new Color(60, 60, 60));
        txtFechaEstreno.setForeground(Color.GRAY);
        txtFechaEstreno.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtFechaEstreno, gbc);

        txtFechaEstreno.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtFechaEstreno.getText().equals(DEFAULT_DATE_FORMAT)) {
                    txtFechaEstreno.setText("");
                    txtFechaEstreno.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtFechaEstreno.getText().isEmpty()) {
                    txtFechaEstreno.setText(DEFAULT_DATE_FORMAT);
                    txtFechaEstreno.setForeground(Color.GRAY);
                }
            }
        });

        JLabel lblDuracion = new JLabel("Duración:");
        lblDuracion.setForeground(Color.LIGHT_GRAY);
        lblDuracion.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblDuracion, gbc);

        JPanel panelDuracion = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelDuracion.setBackground(new Color(40, 40, 40));
        
        txtDuracionHoras = new JTextField("0", 3);
        txtDuracionHoras.setFont(textFont);
        txtDuracionHoras.setBackground(new Color(60, 60, 60));
        txtDuracionHoras.setForeground(Color.WHITE);
        txtDuracionHoras.setCaretColor(Color.WHITE);
        panelDuracion.add(txtDuracionHoras);
       
        JLabel labelHoras = new JLabel("horas");
        labelHoras.setForeground(Color.WHITE); 
        panelDuracion.add(labelHoras);
        
        txtDuracionMinutos = new JTextField("0", 3);
        txtDuracionMinutos.setFont(textFont);
        txtDuracionMinutos.setBackground(new Color(60, 60, 60));
        txtDuracionMinutos.setForeground(Color.WHITE);
        txtDuracionMinutos.setCaretColor(Color.WHITE);
        panelDuracion.add(txtDuracionMinutos);
        
        
        JLabel labelMinutos = new JLabel("minutos");
        labelMinutos.setForeground(Color.WHITE); 
        panelDuracion.add(labelMinutos);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(panelDuracion, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblClasificacion = new JLabel("Clasificación:");
        lblClasificacion.setForeground(Color.LIGHT_GRAY);
        lblClasificacion.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblClasificacion, gbc);
        String[] clasificaciones = {"G", "PG", "PG-13", "R", "NC-17"};
        comboClasificacion = new JComboBox<>(clasificaciones);
        comboClasificacion.setFont(textFont);
        comboClasificacion.setBackground(new Color(60, 60, 60));
        comboClasificacion.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(comboClasificacion, gbc);

        JLabel lblIdioma = new JLabel("Idioma:");
        lblIdioma.setForeground(Color.LIGHT_GRAY);
        lblIdioma.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(lblIdioma, gbc);
        txtIdioma = new JTextField(25);
        txtIdioma.setFont(textFont);
        txtIdioma.setBackground(new Color(60, 60, 60));
        txtIdioma.setForeground(Color.WHITE);
        txtIdioma.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(txtIdioma, gbc);

        JLabel lblActores = new JLabel("Actores:");
        lblActores.setForeground(Color.LIGHT_GRAY);
        lblActores.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(lblActores, gbc);
        txtActores = new JTextField(25);
        txtActores.setFont(textFont);
        txtActores.setBackground(new Color(60, 60, 60));
        txtActores.setForeground(Color.WHITE);
        txtActores.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(txtActores, gbc);

        JLabel lblImagen = new JLabel("Imagen:");
        lblImagen.setForeground(Color.LIGHT_GRAY);
        lblImagen.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(lblImagen, gbc);

        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelImagen.setBackground(new Color(40, 40, 40));

        btnAgregarImagen = new JButton("Agregar Imagen");
        btnAgregarImagen.setFont(buttonFont);
        btnAgregarImagen.setBackground(new Color(80, 80, 80));
        btnAgregarImagen.setForeground(Color.WHITE);
        btnAgregarImagen.setFocusPainted(false);
        btnAgregarImagen.setBorderPainted(false);
        panelImagen.add(btnAgregarImagen);
        
        lblNombreImagen = new JLabel("Ningún archivo seleccionado");
        lblNombreImagen.setForeground(Color.LIGHT_GRAY);
        lblNombreImagen.setFont(textFont);
        panelImagen.add(lblNombreImagen);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        add(panelImagen, gbc);

        btnAgregarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                    "Archivos de imagen (jpg, jpeg, png, gif)", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filtro);
            int result = fileChooser.showOpenDialog(VentanaAgregarPelicula.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                rutaImagenSeleccionada = selectedFile.getAbsolutePath();
                lblNombreImagen.setText(selectedFile.getName());
                System.out.println("Ruta de la imagen seleccionada: " + rutaImagenSeleccionada);
            }
        });

        JLabel lblResena = new JLabel("Reseña (Max 300 caracteres):");
        lblResena.setForeground(Color.LIGHT_GRAY);
        lblResena.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(lblResena, gbc);
        txtResena = new JTextArea(5, 25);
        txtResena.setFont(textFont);
        txtResena.setBackground(new Color(60, 60, 60));
        txtResena.setForeground(Color.WHITE);
        txtResena.setCaretColor(Color.WHITE);
        txtResena.setLineWrap(true);
        txtResena.setWrapStyleWord(true);
        JScrollPane scrollPaneResena = new JScrollPane(txtResena);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPaneResena, gbc);
        gbc.weighty = 0.0;
        lblLimiteResena = new JLabel("0 / 300");
        lblLimiteResena.setForeground(Color.GRAY);
        lblLimiteResena.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLimiteResena.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lblLimiteResena, gbc);
        txtResena.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarContadorResena();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarContadorResena();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarContadorResena();
            }
            private void actualizarContadorResena() {
                lblLimiteResena.setText(txtResena.getText().length() + " / 300");
                if (txtResena.getText().length() > 300) {
                    lblLimiteResena.setForeground(Color.RED);
                } else {
                    lblLimiteResena.setForeground(Color.GRAY);
                }
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(panelBotones, gbc);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(buttonFont);
        btnGuardar.setBackground(new Color(220, 0, 0));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        panelBotones.add(btnGuardar);
        btnGuardar.addActionListener(e -> {
           
            if (txtTitulo.getText().trim().isEmpty() ||
                txtFechaEstreno.getText().trim().isEmpty() ||
                txtFechaEstreno.getText().equals(DEFAULT_DATE_FORMAT) ||
                txtDuracionHoras.getText().trim().isEmpty() ||
                txtDuracionMinutos.getText().trim().isEmpty() ||
                txtIdioma.getText().trim().isEmpty() ||
                txtActores.getText().trim().isEmpty() ||
                txtResena.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben ser llenados.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            
            String fechaEstrenoStr = txtFechaEstreno.getText().trim();
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            inputDateFormat.setLenient(false);
            Date fechaEstrenoParsed;
            try {
                fechaEstrenoParsed = inputDateFormat.parse(fechaEstrenoStr);
                fechaEstrenoStr = inputDateFormat.format(fechaEstrenoParsed);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de Fecha de Estreno inválido. Use YYYY/MM/DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
           
            int horas;
            int minutos;
            try {
                horas = Integer.parseInt(txtDuracionHoras.getText().trim());
                minutos = Integer.parseInt(txtDuracionMinutos.getText().trim());
                if (horas < 0 || minutos < 0 || minutos >= 60) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Las horas y minutos deben ser números enteros válidos. Los minutos deben ser de 0 a 59.", "Error de Duración", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            if (rutaImagenSeleccionada.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una imagen para la película.", "Imagen Requerida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String titulo = txtTitulo.getText().trim();
            if (peliculaExiste(titulo)) {
                JOptionPane.showMessageDialog(this, "Ya existe una película con este título. Por favor, use un título diferente.", "Título Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String duracion = horas + "h " + minutos + "min";
            String clasificacion = (String) comboClasificacion.getSelectedItem();
            String idioma = txtIdioma.getText();
            String actores = txtActores.getText();
            String resena = txtResena.getText();

            Connection conexion = null;
            PreparedStatement consulta = null;
            FileInputStream fis = null;

            try {
                conexion = Conector.conectar();
                if (conexion != null) {
                    String sql = "INSERT INTO Peliculas (titulo, anio, duracion, clasificacion, idioma, actores, imagen, reseña) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    consulta = conexion.prepareStatement(sql);
                    consulta.setString(1, titulo);
                    consulta.setString(2, fechaEstrenoStr);
                    consulta.setString(3, duracion);
                    consulta.setString(4, clasificacion);
                    consulta.setString(5, idioma);
                    consulta.setString(6, actores);

                    File imagen = new File(rutaImagenSeleccionada);
                    fis = new FileInputStream(imagen);
                    consulta.setBinaryStream(7, fis, (int) imagen.length());
                    
                    consulta.setString(8, resena);

                    int filasAfectadas = consulta.executeUpdate();

                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(VentanaAgregarPelicula.this, "Película agregada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        PilasPeliculas.agregarPelicula(titulo);

                        if (getParent() instanceof Admin) {
                            ((Admin) getParent()).cargarPeliculas("ASC", "Todos");
                        }
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(VentanaAgregarPelicula.this, "Error al agregar la película", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaAgregarPelicula.this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(VentanaAgregarPelicula.this, "Error al ejecutar la consulta de registro de película: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(VentanaAgregarPelicula.this, "Error al leer el archivo de imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (fis != null) fis.close();
                    if (consulta != null) consulta.close();
                    if (conexion != null) conexion.close();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(buttonFont);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(80, 80, 80));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        panelBotones.add(btnCancelar);
        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private boolean peliculaExiste(String titulo) {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultados = null;
        boolean existe = false;
        try {
            conexion = Conector.conectar();
            if (conexion != null) {
                String sql = "SELECT COUNT(*) FROM Peliculas WHERE LOWER(titulo) = LOWER(?)";
                consulta = conexion.prepareStatement(sql);
                consulta.setString(1, titulo);
                resultados = consulta.executeQuery();
                if (resultados.next()) {
                    if (resultados.getInt(1) > 0) {
                        existe = true;
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al verificar si la película existe: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al verificar el título de la película en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultados != null) resultados.close();
                if (consulta != null) consulta.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return existe;
    }
}