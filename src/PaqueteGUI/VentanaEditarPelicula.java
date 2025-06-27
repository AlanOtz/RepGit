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
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaEditarPelicula extends JDialog {

    private Admin adminParent; 
    private String tituloOriginal; 
    private byte[] imagenOriginalBlob; 

    private JTextField txtTitulo;
    private JTextField txtFechaEstreno;
    private JTextField txtDuracionHoras;
    private JTextField txtDuracionMinutos;
    private JComboBox<String> comboClasificacion;
    private JTextField txtIdioma;
    private JTextField txtActores;
    private JButton btnCambiarImagen; 
    private JLabel lblNombreImagen;
    private JTextArea txtResena;
    private JLabel lblLimiteResena;
    private JButton btnGuardarCambios;
    private JButton btnCancelar;
    private String rutaNuevaImagen = ""; 

    private static final String DEFAULT_DATE_FORMAT = "yyyy/mm/dd";

    
    public VentanaEditarPelicula(Admin parent, String titulo, String anio, String duracion,
                                  String clasificacion, String idioma, String actores,
                                  byte[] imagenBlob, String resena) {
        super(parent, "Editar Película: " + titulo, true);
        this.adminParent = parent;
        this.tituloOriginal = titulo; 
        this.imagenOriginalBlob = imagenBlob; 

        setUndecorated(true);
        setSize(600, 680); 
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

        JLabel lblTituloVentana = new JLabel("Editar Película: " + titulo);
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
        gbc.gridx = 0; gbc.gridy = 1; add(lblTitulo, gbc);
        txtTitulo = new JTextField(titulo, 25);
        txtTitulo.setFont(textFont);
        txtTitulo.setBackground(new Color(60, 60, 60));
        txtTitulo.setForeground(Color.WHITE);
        txtTitulo.setCaretColor(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 1; add(txtTitulo, gbc);

        
        JLabel lblFechaEstreno = new JLabel("Fecha de Estreno:");
        lblFechaEstreno.setForeground(Color.LIGHT_GRAY);
        lblFechaEstreno.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 2; add(lblFechaEstreno, gbc);
        txtFechaEstreno = new JTextField(anio, 25); 
        txtFechaEstreno.setFont(textFont);
        txtFechaEstreno.setBackground(new Color(60, 60, 60));
        txtFechaEstreno.setForeground(Color.WHITE); 
        txtFechaEstreno.setCaretColor(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 2; add(txtFechaEstreno, gbc);
        
        txtFechaEstreno.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtFechaEstreno.getText().equals(DEFAULT_DATE_FORMAT) || txtFechaEstreno.getText().isEmpty()) {
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
        if (anio.isEmpty() || anio.equals(DEFAULT_DATE_FORMAT)) { 
            txtFechaEstreno.setText(DEFAULT_DATE_FORMAT);
            txtFechaEstreno.setForeground(Color.GRAY);
        }


        
        JLabel lblDuracion = new JLabel("Duración:");
        lblDuracion.setForeground(Color.LIGHT_GRAY);
        lblDuracion.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 3; add(lblDuracion, gbc);

        JPanel panelDuracion = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelDuracion.setBackground(new Color(40, 40, 40));
        
       
        String[] parts = duracion.split("h ");
        String hStr = "0";
        String mStr = "0";
        if (parts.length > 0) {
            hStr = parts[0].trim();
            if (parts.length > 1) {
                mStr = parts[1].replace("min", "").trim();
            }
        }

        txtDuracionHoras = new JTextField(hStr, 3);
        txtDuracionHoras.setFont(textFont);
        txtDuracionHoras.setBackground(new Color(60, 60, 60));
        txtDuracionHoras.setForeground(Color.WHITE);
        txtDuracionHoras.setCaretColor(Color.WHITE);
        panelDuracion.add(txtDuracionHoras);
        JLabel labelHoras = new JLabel("horas");
        labelHoras.setForeground(Color.WHITE);
        panelDuracion.add(labelHoras);
        
        txtDuracionMinutos = new JTextField(mStr, 3);
        txtDuracionMinutos.setFont(textFont);
        txtDuracionMinutos.setBackground(new Color(60, 60, 60));
        txtDuracionMinutos.setForeground(Color.WHITE);
        txtDuracionMinutos.setCaretColor(Color.WHITE);
        panelDuracion.add(txtDuracionMinutos);
        JLabel labelMinutos = new JLabel("minutos");
        labelMinutos.setForeground(Color.WHITE);
        panelDuracion.add(labelMinutos);

        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.WEST; add(panelDuracion, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        JLabel lblClasificacion = new JLabel("Clasificación:");
        lblClasificacion.setForeground(Color.LIGHT_GRAY);
        lblClasificacion.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 4; add(lblClasificacion, gbc);
        String[] clasificaciones = {"G", "PG", "PG-13", "R", "NC-17"};
        comboClasificacion = new JComboBox<>(clasificaciones);
        comboClasificacion.setSelectedItem(clasificacion); 
        comboClasificacion.setFont(textFont);
        comboClasificacion.setBackground(new Color(60, 60, 60));
        comboClasificacion.setForeground(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 4; add(comboClasificacion, gbc);

        
        JLabel lblIdioma = new JLabel("Idioma:");
        lblIdioma.setForeground(Color.LIGHT_GRAY);
        lblIdioma.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 5; add(lblIdioma, gbc);
        txtIdioma = new JTextField(idioma, 25);
        txtIdioma.setFont(textFont);
        txtIdioma.setBackground(new Color(60, 60, 60));
        txtIdioma.setForeground(Color.WHITE);
        txtIdioma.setCaretColor(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 5; add(txtIdioma, gbc);

        
        JLabel lblActores = new JLabel("Actores:");
        lblActores.setForeground(Color.LIGHT_GRAY);
        lblActores.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 6; add(lblActores, gbc);
        txtActores = new JTextField(actores, 25);
        txtActores.setFont(textFont);
        txtActores.setBackground(new Color(60, 60, 60));
        txtActores.setForeground(Color.WHITE);
        txtActores.setCaretColor(Color.WHITE);
        gbc.gridx = 1; gbc.gridy = 6; add(txtActores, gbc);

        
        JLabel lblImagen = new JLabel("Imagen:");
        lblImagen.setForeground(Color.LIGHT_GRAY);
        lblImagen.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 7; add(lblImagen, gbc);

        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelImagen.setBackground(new Color(40, 40, 40));

        btnCambiarImagen = new JButton("Cambiar Imagen");
        btnCambiarImagen.setFont(buttonFont);
        btnCambiarImagen.setBackground(new Color(80, 80, 80));
        btnCambiarImagen.setForeground(Color.WHITE);
        btnCambiarImagen.setFocusPainted(false);
        btnCambiarImagen.setBorderPainted(false);
        panelImagen.add(btnCambiarImagen);
        
        lblNombreImagen = new JLabel(imagenBlob != null && imagenBlob.length > 0 ? "Imagen actual cargada" : "Sin imagen");
        lblNombreImagen.setForeground(Color.LIGHT_GRAY);
        lblNombreImagen.setFont(textFont);
        panelImagen.add(lblNombreImagen);

        gbc.gridx = 1; gbc.gridy = 7; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST; add(panelImagen, gbc);
        btnCambiarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                    "Archivos de imagen (jpg, jpeg, png, gif)", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filtro);
            int result = fileChooser.showOpenDialog(VentanaEditarPelicula.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                rutaNuevaImagen = selectedFile.getAbsolutePath(); 
                lblNombreImagen.setText(selectedFile.getName() + " (nueva)"); 
                System.out.println("Ruta de la nueva imagen seleccionada: " + rutaNuevaImagen);
            }
        });

        
        JLabel lblResena = new JLabel("Reseña (Max 300 caracteres):");
        lblResena.setForeground(Color.LIGHT_GRAY);
        lblResena.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = GridBagConstraints.REMAINDER; add(lblResena, gbc);
        txtResena = new JTextArea(resena, 5, 25); 
        txtResena.setFont(textFont);
        txtResena.setBackground(new Color(60, 60, 60));
        txtResena.setForeground(Color.WHITE);
        txtResena.setCaretColor(Color.WHITE);
        txtResena.setLineWrap(true);
        txtResena.setWrapStyleWord(true);
        JScrollPane scrollPaneResena = new JScrollPane(txtResena);
        gbc.gridx = 0; gbc.gridy = 9; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH; add(scrollPaneResena, gbc);
        gbc.weighty = 0.0;
        lblLimiteResena = new JLabel(txtResena.getText().length() + " / 300"); 
        lblLimiteResena.setForeground(Color.GRAY);
        lblLimiteResena.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLimiteResena.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = 10; gbc.fill = GridBagConstraints.HORIZONTAL; add(lblLimiteResena, gbc);
        txtResena.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) { actualizarContadorResena(); }
            @Override
            public void insertUpdate(DocumentEvent e) { actualizarContadorResena(); }
            @Override
            public void removeUpdate(DocumentEvent e) { actualizarContadorResena(); }
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
        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = GridBagConstraints.REMAINDER; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST; gbc.insets = new Insets(20, 20, 20, 20); add(panelBotones, gbc);

        btnGuardarCambios = new JButton("Guardar Cambios");
        btnGuardarCambios.setFont(buttonFont);
        btnGuardarCambios.setBackground(new Color(50, 150, 50)); 
        btnGuardarCambios.setForeground(Color.WHITE);
        btnGuardarCambios.setFocusPainted(false);
        btnGuardarCambios.setBorderPainted(false);
        panelBotones.add(btnGuardarCambios);

        
        btnGuardarCambios.addActionListener(e -> {
            
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
            try {
                Date fechaEstrenoParsed = inputDateFormat.parse(fechaEstrenoStr);
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

            
            String nuevoTitulo = txtTitulo.getText().trim();
            if (!nuevoTitulo.equalsIgnoreCase(tituloOriginal) && peliculaExiste(nuevoTitulo)) {
                JOptionPane.showMessageDialog(this, "Ya existe otra película con este título. Por favor, use un título diferente.", "Título Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            String duracionGuardar = horas + "h " + minutos + "min";
            String clasificacionGuardar = (String) comboClasificacion.getSelectedItem();
            String idiomaGuardar = txtIdioma.getText();
            String actoresGuardar = txtActores.getText();
            String resenaGuardar = txtResena.getText();

           
            Connection conexion = null;
            PreparedStatement consulta = null;
            FileInputStream fis = null;

            try {
                conexion = Conector.conectar();
                if (conexion != null) {
                    String sql = "UPDATE Peliculas SET titulo = ?, anio = ?, duracion = ?, clasificacion = ?, idioma = ?, actores = ?, imagen = ?, reseña = ? WHERE titulo = ?";
                    consulta = conexion.prepareStatement(sql);
                    consulta.setString(1, nuevoTitulo);
                    consulta.setString(2, fechaEstrenoStr);
                    consulta.setString(3, duracionGuardar);
                    consulta.setString(4, clasificacionGuardar);
                    consulta.setString(5, idiomaGuardar);
                    consulta.setString(6, actoresGuardar);

                    if (!rutaNuevaImagen.isEmpty()) { 
                        File nuevaImagenFile = new File(rutaNuevaImagen);
                        fis = new FileInputStream(nuevaImagenFile);
                        consulta.setBinaryStream(7, fis, (int) nuevaImagenFile.length());
                    } else { 
                        consulta.setBytes(7, imagenOriginalBlob);
                    }
                    
                    consulta.setString(8, resenaGuardar);
                    consulta.setString(9, tituloOriginal); 

                    int filasAfectadas = consulta.executeUpdate();

                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(VentanaEditarPelicula.this, "Película actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        
                        adminParent.cargarPeliculas(adminParent.ordenActual, adminParent.clasificacionActual);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(VentanaEditarPelicula.this, "No se pudo actualizar la película. Verifique el título original.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaEditarPelicula.this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(VentanaEditarPelicula.this, "Error al actualizar la película: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(VentanaEditarPelicula.this, "Error al leer el archivo de imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            System.err.println("Error al verificar si la película existe (desde editar): " + ex.getMessage());
            ex.printStackTrace();
            
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