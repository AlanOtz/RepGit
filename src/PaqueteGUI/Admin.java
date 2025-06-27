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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends JFrame {

    private String correoUsuario;

    private String nombreAdmin;
    private String apellidoPaternoAdmin;
    private String apellidoMaternoAdmin;
    private String usuarioTipo;
    private JLabel lblNombreCompletoAdmin;
    private JLabel lblCerrarSesionIcon;
    private JLabel lblCerrarSesionTexto;
    private JLabel lblAgregarPelicula;
    private JLabel lblBuscarPelicula;
    private JTextField txtBuscar;
    private JButton btnCancelarBuscar;
    private JPanel panelListaPeliculas; 
    private JPanel panelControles;
    private JComboBox<String> comboOrden;
    private JComboBox<String> comboClasificacion;
    private JPanel panelAlfabeto; 
    private JButton[] botonesAlfabeto; 
    private String letraActual = ""; 

    String ordenActual = "ASC";
    String clasificacionActual = "Todos";

    
    private static final int PANEL_PELICULA_ANCHO = 220; 
    private static final int PANEL_PELICULA_ALTO = 240; 
    private static final int PELICULAS_POR_FILA = 4;
    private static final int GAP_HORIZONTAL_PELICULAS = 15;
    private static final int GAP_VERTICAL_PELICULAS = 15;

    public Admin(String nombre, String apellidoPaterno, String apellidoMaterno, String usuarioTipo, String correoUsuario) {
        super("Admin");
        this.nombreAdmin = nombre;
        this.apellidoPaternoAdmin = apellidoPaterno;
        this.apellidoMaternoAdmin = apellidoMaterno;
        this.usuarioTipo = usuarioTipo;
        this.correoUsuario = correoUsuario; 

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(40, 40, 40));

        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBackground(Color.BLACK);
        barraSuperior.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel lblTituloNeflis = new JLabel("NEFLIS");
        lblTituloNeflis.setForeground(Color.RED);
        lblTituloNeflis.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloNeflis.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        barraSuperior.add(lblTituloNeflis, BorderLayout.WEST);

        JPanel panelDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelDerecha.setBackground(Color.BLACK);
        panelDerecha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        String nombreCompleto = nombreAdmin + " " + apellidoPaternoAdmin + " " + apellidoMaternoAdmin;
        lblNombreCompletoAdmin = new JLabel(nombreCompleto);
        lblNombreCompletoAdmin.setForeground(Color.WHITE);
        lblNombreCompletoAdmin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panelDerecha.add(lblNombreCompletoAdmin);

        ImageIcon iconoCerrarSesion = new ImageIcon(getClass().getResource("/images/salir.png"));
        Image imgIcon = iconoCerrarSesion.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconoCerrarSesion = new ImageIcon(imgIcon);
        lblCerrarSesionIcon = new JLabel(iconoCerrarSesion);
        lblCerrarSesionIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrarSesionIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new Neflis());
            }
        });
        panelDerecha.add(lblCerrarSesionIcon);

        lblCerrarSesionTexto = new JLabel("Cerrar Sesión");
        lblCerrarSesionTexto.setForeground(Color.WHITE);
        lblCerrarSesionTexto.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCerrarSesionTexto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCerrarSesionTexto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new Neflis());
            }
        });
        panelDerecha.add(lblCerrarSesionTexto);

        barraSuperior.add(panelDerecha, BorderLayout.EAST);
        panelPrincipal.add(barraSuperior, BorderLayout.NORTH);

        panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); 
        panelControles.setBackground(new Color(40, 40, 40));
        panelControles.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); 

        lblAgregarPelicula = new JLabel("Agregar Película");
        lblAgregarPelicula.setForeground(Color.LIGHT_GRAY);
        lblAgregarPelicula.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblAgregarPelicula.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblAgregarPelicula.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VentanaAgregarPelicula vap = new VentanaAgregarPelicula(Admin.this);
            }
        });
        if (this.usuarioTipo.equals("admin")) {
            panelControles.add(lblAgregarPelicula);
        }

        lblBuscarPelicula = new JLabel("Buscar");
        lblBuscarPelicula.setForeground(Color.LIGHT_GRAY);
        lblBuscarPelicula.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBuscarPelicula.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblBuscarPelicula.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblBuscarPelicula.setVisible(false);
                txtBuscar.setVisible(true);
                btnCancelarBuscar.setVisible(true);
                txtBuscar.requestFocusInWindow();
            }
        });
        panelControles.add(lblBuscarPelicula);

        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBackground(new Color(60, 60, 60));
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setVisible(false);
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarPeliculas(txtBuscar.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarPeliculas(txtBuscar.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
        panelControles.add(txtBuscar);

        btnCancelarBuscar = new JButton("Cancelar");
        btnCancelarBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancelarBuscar.setBackground(new Color(80, 80, 80));
        btnCancelarBuscar.setForeground(Color.WHITE);
        btnCancelarBuscar.setFocusPainted(false);
        btnCancelarBuscar.setBorderPainted(false);
        btnCancelarBuscar.setVisible(false);
        btnCancelarBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtBuscar.setText(""); 
                txtBuscar.setVisible(false);
                btnCancelarBuscar.setVisible(false);
                lblBuscarPelicula.setVisible(true);
                cargarPeliculas(ordenActual, clasificacionActual);
            }
        });
        panelControles.add(btnCancelarBuscar);

        
        String[] opcionesOrden = {"Ascendente", "Descendente"};
        comboOrden = new JComboBox<>(opcionesOrden);
        comboOrden.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboOrden.setBackground(new Color(60, 60, 60));
        comboOrden.setForeground(Color.WHITE);
        comboOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenActual = comboOrden.getSelectedItem().equals("Ascendente") ? "ASC" : "DESC";
                cargarPeliculas(ordenActual, clasificacionActual);
            }
        });
        panelControles.add(comboOrden);

        
        List<String> listaClasificaciones = obtenerClasificaciones();
        listaClasificaciones.add(0, "Todos");
        String[] opcionesClasificacion = listaClasificaciones.toArray(new String[0]);
        comboClasificacion = new JComboBox<>(opcionesClasificacion);
        comboClasificacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboClasificacion.setBackground(new Color(60, 60, 60));
        comboClasificacion.setForeground(Color.WHITE);
        comboClasificacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clasificacionActual = (String) comboClasificacion.getSelectedItem();
                cargarPeliculas(ordenActual, clasificacionActual);
            }
        });
        panelControles.add(comboClasificacion);

        // --- NEW: Alphabet Filter Panel ---
        panelAlfabeto = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0)); 
        panelAlfabeto.setBackground(new Color(40, 40, 40));
        panelControles.add(panelAlfabeto);

        botonesAlfabeto = new JButton[26];
        for (char c = 'A'; c <= 'Z'; c++) {
            final String letter = String.valueOf(c);
            JButton btnLetter = new JButton(letter);
            btnLetter.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnLetter.setForeground(Color.LIGHT_GRAY);
            btnLetter.setBackground(new Color(40, 40, 40));
            btnLetter.setFocusPainted(false);
            btnLetter.setBorderPainted(false);
            btnLetter.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnLetter.addActionListener(e -> {
                
                if (letraActual.equalsIgnoreCase(letter)) {
                    letraActual = ""; 
                } else {
                    letraActual = letter; 
                }
                actualizarEstiloBotonesAlfabeto();
                cargarPeliculas(ordenActual, clasificacionActual);
            });
            botonesAlfabeto[c - 'A'] = btnLetter;
            panelAlfabeto.add(btnLetter);
        }
        

        // Initial style update
        actualizarEstiloBotonesAlfabeto();
        


        JPanel panelContenedorLista = new JPanel(new BorderLayout());
        panelContenedorLista.setBackground(new Color(40, 40, 40));
        panelContenedorLista.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        panelContenedorLista.add(panelControles, BorderLayout.NORTH);

        panelListaPeliculas = new JPanel();
        panelListaPeliculas.setLayout(new GridLayout(0, PELICULAS_POR_FILA, GAP_HORIZONTAL_PELICULAS, GAP_VERTICAL_PELICULAS));
        panelListaPeliculas.setBackground(new Color(40, 40, 40));

        JScrollPane scrollPanePeliculas = new JScrollPane(panelListaPeliculas);
        scrollPanePeliculas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanePeliculas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanePeliculas.setBorder(BorderFactory.createEmptyBorder());

        panelContenedorLista.add(scrollPanePeliculas, BorderLayout.CENTER);

        panelPrincipal.add(panelContenedorLista, BorderLayout.CENTER);

        add(panelPrincipal);

        cargarPeliculas(ordenActual, clasificacionActual);

        setVisible(true);
    }

    
    private void actualizarEstiloBotonesAlfabeto() {
        for (JButton btn : botonesAlfabeto) {
            
            String originalText = btn.getText().replace("<html><u>", "").replace("</u></html>", "");

            if (letraActual.equalsIgnoreCase(originalText)) {
                btn.setForeground(Color.WHITE);
                btn.setBackground(new Color(60, 60, 60)); 
                btn.setText("<html><u>" + originalText + "</u></html>"); 
            } else {
                btn.setForeground(Color.LIGHT_GRAY);
                btn.setBackground(new Color(40, 40, 40)); 
                btn.setText(originalText); 
            }
        }
    }


    public String getOrdenActual() {
        return ordenActual;
    }

    public String getClasificacionActual() {
        return clasificacionActual;
    }

    private List<String> obtenerClasificaciones() {
        List<String> clasificaciones = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultados = null;

        try {
            conexion = Conector.conectar();
            if (conexion != null) {
                String sql = "SELECT DISTINCT clasificacion FROM Peliculas ORDER BY clasificacion ASC";
                consulta = conexion.prepareStatement(sql);
                resultados = consulta.executeQuery();
                while (resultados.next()) {
                    clasificaciones.add(resultados.getString("clasificacion"));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener las clasificaciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            cerrarConexiones(resultados, consulta, conexion);
        }
        return clasificaciones;
    }

    public void cargarPeliculas(String orden, String clasificacion) {
        panelListaPeliculas.removeAll(); 
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultados = null;

        try {
            conexion = Conector.conectar();
            if (conexion != null) {
                StringBuilder sqlBuilder = new StringBuilder("SELECT titulo, anio, duracion, clasificacion, idioma, actores, imagen, reseña FROM Peliculas WHERE 1=1");

                if (!clasificacion.equals("Todos")) {
                    sqlBuilder.append(" AND clasificacion = ?");
                }
                if (!letraActual.isEmpty()) { 
                    sqlBuilder.append(" AND LOWER(titulo) LIKE ?");
                }
                sqlBuilder.append(" ORDER BY LOWER(titulo) ").append(orden);

                consulta = conexion.prepareStatement(sqlBuilder.toString());

                int paramIndex = 1;
                if (!clasificacion.equals("Todos")) {
                    consulta.setString(paramIndex++, clasificacion);
                }
                if (!letraActual.isEmpty()) {
                    consulta.setString(paramIndex++, letraActual.toLowerCase() + "%");
                }

                resultados = consulta.executeQuery();

                while (resultados.next()) {
                    String titulo = resultados.getString("titulo");
                    String anio = resultados.getString("anio");
                    String duracion = resultados.getString("duracion");
                    String clasificacionPelicula = resultados.getString("clasificacion");
                    String idioma = resultados.getString("idioma");
                    String actores = resultados.getString("actores");
                    String resena = resultados.getString("reseña");
                    Object imagenBlob = resultados.getBytes("imagen");

                    JPanel panelPelicula = crearPanelPelicula(titulo, anio, duracion, clasificacionPelicula, idioma, actores, resena, imagenBlob);
                    panelListaPeliculas.add(panelPelicula);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las películas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            cerrarConexiones(resultados, consulta, conexion);
        }
        panelListaPeliculas.revalidate();
        panelListaPeliculas.repaint();
    }

    private void filtrarPeliculas(String textoBusqueda) {
        panelListaPeliculas.removeAll();
        String textoBusquedaSQL = "%" + textoBusqueda.toLowerCase() + "%";

        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultados = null;

        try {
            conexion = Conector.conectar();
            if (conexion != null) {
                
                StringBuilder sqlBuilder = new StringBuilder("SELECT titulo, anio, duracion, clasificacion, idioma, actores, imagen, reseña FROM Peliculas WHERE LOWER(titulo) LIKE ?");

                if (!clasificacionActual.equals("Todos")) {
                    sqlBuilder.append(" AND clasificacion = ?");
                }
                if (!letraActual.isEmpty()) { 
                    sqlBuilder.append(" AND LOWER(titulo) LIKE ?");
                }
                sqlBuilder.append(" ORDER BY LOWER(titulo) ").append(ordenActual);

                consulta = conexion.prepareStatement(sqlBuilder.toString());
                
                int paramIndex = 1;
                consulta.setString(paramIndex++, textoBusquedaSQL); 

                if (!clasificacionActual.equals("Todos")) {
                    consulta.setString(paramIndex++, clasificacionActual);
                }
                if (!letraActual.isEmpty()) {
                    consulta.setString(paramIndex++, letraActual.toLowerCase() + "%");
                }
                resultados = consulta.executeQuery();

                while (resultados.next()) {
                    String titulo = resultados.getString("titulo");
                    String anio = resultados.getString("anio");
                    String duracion = resultados.getString("duracion");
                    String clasificacionPelicula = resultados.getString("clasificacion");
                    String idioma = resultados.getString("idioma");
                    String actores = resultados.getString("actores");
                    String resena = resultados.getString("reseña");
                    Object imagenBlob = resultados.getBytes("imagen");

                    JPanel panelPelicula = crearPanelPelicula(titulo, anio, duracion, clasificacionPelicula, idioma, actores, resena, imagenBlob);
                    panelListaPeliculas.add(panelPelicula);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al realizar la búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            cerrarConexiones(resultados, consulta, conexion);
        }

        panelListaPeliculas.revalidate();
        panelListaPeliculas.repaint();
    }

    private JPanel crearPanelPelicula(String titulo, String anioStr, String duracion,
                                       String clasificacion, String idioma, String actores, String resena,
                                       Object imagenBlob) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(60, 60, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(PANEL_PELICULA_ANCHO, PANEL_PELICULA_ALTO));

        if (imagenBlob != null) {
            try {
                byte[] imageData = (byte[]) imagenBlob;
                if (imageData.length > 0) {
                    ImageIcon imageIcon = new ImageIcon(imageData);
                    Image scaledImage = imageIcon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                    JLabel lblImagen = new JLabel(new ImageIcon(scaledImage));
                    panel.add(lblImagen, BorderLayout.WEST);
                } else {
                    JLabel lblSinImagen = new JLabel("Sin Imagen");
                    lblSinImagen.setForeground(Color.LIGHT_GRAY);
                    panel.add(lblSinImagen, BorderLayout.WEST);
                }
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen BLOB: " + e.getMessage());
                JLabel lblErrorImagen = new JLabel("Error Imagen");
                lblErrorImagen.setForeground(Color.RED);
                panel.add(lblErrorImagen, BorderLayout.WEST);
            }
        } else {
            JLabel lblSinImagen = new JLabel("Sin Imagen");
            lblSinImagen.setForeground(Color.LIGHT_GRAY);
            panel.add(lblSinImagen, BorderLayout.WEST);
        }

        JPanel panelInfoBasica = new JPanel();
        panelInfoBasica.setLayout(new BoxLayout(panelInfoBasica, BoxLayout.Y_AXIS));
        panelInfoBasica.setBackground(new Color(60, 60, 60));
        panelInfoBasica.setForeground(Color.WHITE);

        JLabel lblTitulo = new JLabel("<html><p style='width: 100px;'><b>" + titulo + "</b></p></html>");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfoBasica.add(lblTitulo);
        panelInfoBasica.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel lblFechaEstreno = new JLabel("Estreno: " + anioStr);
        lblFechaEstreno.setForeground(Color.LIGHT_GRAY);
        lblFechaEstreno.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFechaEstreno.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfoBasica.add(lblFechaEstreno);

        String displayedDuracion = duracion;
        JLabel lblDuracion = new JLabel("Duración: " + displayedDuracion);
        lblDuracion.setForeground(Color.LIGHT_GRAY);
        lblDuracion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDuracion.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfoBasica.add(lblDuracion);

        JLabel lblClasificacion = new JLabel("Clasificación: " + clasificacion);
        lblClasificacion.setForeground(Color.LIGHT_GRAY);
        lblClasificacion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblClasificacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfoBasica.add(lblClasificacion);

        JLabel lblIdioma = new JLabel("Idioma: " + idioma);
        lblIdioma.setForeground(Color.LIGHT_GRAY);
        lblIdioma.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblIdioma.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfoBasica.add(lblIdioma);

        JLabel lblPromedio = mostrarPromedioValoracion(titulo);
        if (lblPromedio != null) {
            panelInfoBasica.add(lblPromedio);
        }
        panelInfoBasica.add(Box.createVerticalGlue());

        panel.add(panelInfoBasica, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(60, 60, 60));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        JPanel panelLeftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelLeftButtons.setBackground(new Color(60, 60, 60));
        if (!this.usuarioTipo.equals("admin")) {
            JLabel lblValorar = new JLabel("Tu valoración:");
            lblValorar.setForeground(Color.LIGHT_GRAY);
            lblValorar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            panelLeftButtons.add(lblValorar);

            JComboBox<Integer> comboValoracion = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
            comboValoracion.setPreferredSize(new Dimension(45, 20));
            comboValoracion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            panelLeftButtons.add(comboValoracion);

            JButton btnEnviar = new JButton("Enviar");
            btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 10));
            btnEnviar.setForeground(Color.WHITE);
            btnEnviar.setFocusPainted(false);
            btnEnviar.setBorderPainted(false);
            btnEnviar.setPreferredSize(new Dimension(65, 20));
            panelLeftButtons.add(btnEnviar);

            Integer valoracionExistente = obtenerValoracionUsuario(titulo);
            if (valoracionExistente != null) {
                comboValoracion.setSelectedItem(valoracionExistente);
                btnEnviar.setText("Editar");
                btnEnviar.setBackground(new Color(80, 80, 80));
            } else {
                btnEnviar.setText("Enviar");
                btnEnviar.setBackground(new Color(200, 0, 0));
            }

            btnEnviar.addActionListener(e -> {
                int valor = (int) comboValoracion.getSelectedItem();
                new Thread(() -> {
                    guardarValoracion(titulo, valor);
                    SwingUtilities.invokeLater(() -> {
                        cargarPeliculas(getOrdenActual(), getClasificacionActual());
                    });
                }).start();
            });
        }
        panelInferior.add(panelLeftButtons, BorderLayout.WEST);

        JPanel panelRightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panelRightButtons.setBackground(new Color(60, 60, 60));

        JButton btnVerMas = new JButton("Ver más");
        btnVerMas.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btnVerMas.setBackground(new Color(200, 0, 0));
        btnVerMas.setForeground(Color.WHITE);
        btnVerMas.setFocusPainted(false);
        btnVerMas.setBorderPainted(false);
        btnVerMas.setPreferredSize(new Dimension(80, 25));
        btnVerMas.addActionListener(e -> {
            mostrarDetallesPelicula(titulo, actores, resena);
        });
        panelRightButtons.add(btnVerMas);

        if (this.usuarioTipo.equals("admin")) {
            JButton btnEliminarPelicula = new JButton("Eliminar");
            btnEliminarPelicula.setFont(new Font("Segoe UI", Font.BOLD, 10));
            btnEliminarPelicula.setBackground(new Color(150, 0, 0));
            btnEliminarPelicula.setForeground(Color.WHITE);
            btnEliminarPelicula.setFocusPainted(false);
            btnEliminarPelicula.setBorderPainted(false);
            btnEliminarPelicula.setPreferredSize(new Dimension(80, 25));
            btnEliminarPelicula.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que quieres eliminar la película '" + titulo + "'? Esto también eliminará todas sus valoraciones.",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    eliminarPeliculaYValoraciones(titulo);
                }
            });
            panelRightButtons.add(btnEliminarPelicula);

            JButton btnEditarPelicula = new JButton("Editar");
            btnEditarPelicula.setFont(new Font("Segoe UI", Font.BOLD, 10));
            btnEditarPelicula.setBackground(new Color(50, 100, 150));
            btnEditarPelicula.setForeground(Color.WHITE);
            btnEditarPelicula.setFocusPainted(false);
            btnEditarPelicula.setBorderPainted(false);
            btnEditarPelicula.setPreferredSize(new Dimension(80, 25));
            btnEditarPelicula.addActionListener(e -> {
                abrirVentanaEditarPelicula(titulo);
            });
            panelRightButtons.add(btnEditarPelicula);
        }
        
        panelInferior.add(panelRightButtons, BorderLayout.EAST);
        panel.add(panelInferior, BorderLayout.SOUTH);

        return panel;
    }

    private void abrirVentanaEditarPelicula(String tituloPelicula) {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultados = null;
        try {
            conexion = Conector.conectar();
            if (conexion != null) {
                String sql = "SELECT titulo, anio, duracion, clasificacion, idioma, actores, imagen, reseña FROM Peliculas WHERE titulo = ?";
                consulta = conexion.prepareStatement(sql);
                consulta.setString(1, tituloPelicula);
                resultados = consulta.executeQuery();

                if (resultados.next()) {
                    String titulo = resultados.getString("titulo");
                    String anio = resultados.getString("anio");
                    String duracion = resultados.getString("duracion");
                    String clasificacion = resultados.getString("clasificacion");
                    String idioma = resultados.getString("idioma");
                    String actores = resultados.getString("actores");
                    byte[] imagenBlob = resultados.getBytes("imagen");
                    String resena = resultados.getString("reseña");

                    VentanaEditarPelicula ventanaEditar = new VentanaEditarPelicula(this,
                                                                                    titulo,
                                                                                    anio,
                                                                                    duracion,
                                                                                    clasificacion,
                                                                                    idioma,
                                                                                    actores,
                                                                                    imagenBlob,
                                                                                    resena);
                    
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró la película para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la película para edición: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            cerrarConexiones(resultados, consulta, conexion);
        }
    }

    private void mostrarDetallesPelicula(String titulo, String actores, String resena) {
        JDialog detallesDialog = new JDialog(this, "Detalles de " + titulo, Dialog.ModalityType.APPLICATION_MODAL);
        detallesDialog.setUndecorated(true);
        detallesDialog.setSize(new Dimension(500, 400));
        detallesDialog.setLocationRelativeTo(this);

        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBackground(new Color(40, 40, 40));
        panelContenido.setBorder(BorderFactory.createLineBorder(new Color(200, 0, 0), 2));

        JPanel panelTextoPrincipal = new JPanel();
        panelTextoPrincipal.setLayout(new BoxLayout(panelTextoPrincipal, BoxLayout.Y_AXIS));
        panelTextoPrincipal.setBackground(new Color(60, 60, 60));
        panelTextoPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTituloDialog = new JLabel("<html><p style='width: 400px;'><b>" + titulo + "</b></p></html>");
        lblTituloDialog.setForeground(Color.WHITE);
        lblTituloDialog.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTituloDialog.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTextoPrincipal.add(lblTituloDialog);
        panelTextoPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextArea txtActores = new JTextArea("Actores: " + actores);
        txtActores.setLineWrap(true);
        txtActores.setWrapStyleWord(true);
        txtActores.setEditable(false);
        txtActores.setBackground(new Color(60, 60, 60));
        txtActores.setForeground(Color.LIGHT_GRAY);
        txtActores.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtActores.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        txtActores.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrollActores = new JScrollPane(txtActores);
        scrollActores.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollActores.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollActores.setBorder(BorderFactory.createEmptyBorder());
        scrollActores.setPreferredSize(new Dimension(450, 80));
        scrollActores.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        panelTextoPrincipal.add(scrollActores);
        panelTextoPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.GRAY);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        panelTextoPrincipal.add(separator);
        panelTextoPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextArea txtResena = new JTextArea("Reseña: " + resena);
        txtResena.setLineWrap(true);
        txtResena.setWrapStyleWord(true);
        txtResena.setEditable(false);
        txtResena.setBackground(new Color(60, 60, 60));
        txtResena.setForeground(Color.LIGHT_GRAY);
        txtResena.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtResena.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        txtResena.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrollResena = new JScrollPane(txtResena);
        scrollResena.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollResena.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollResena.setBorder(BorderFactory.createEmptyBorder());
        scrollResena.setPreferredSize(new Dimension(450, 120));
        scrollResena.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        panelTextoPrincipal.add(scrollResena);
        panelTextoPrincipal.add(Box.createVerticalGlue());
        panelTextoPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel panelBotonCerrar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonCerrar.setBackground(new Color(60, 60, 60));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(200, 0, 0));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setPreferredSize(new Dimension(120, 35));
        btnCerrar.addActionListener(e -> detallesDialog.dispose());
        panelBotonCerrar.add(btnCerrar);

        panelContenido.add(panelTextoPrincipal, BorderLayout.CENTER);
        panelContenido.add(panelBotonCerrar, BorderLayout.SOUTH);

        detallesDialog.add(panelContenido);
        detallesDialog.setVisible(true);
    }

    private void eliminarPeliculaYValoraciones(String tituloPelicula) {
        Connection conexion = null;
        PreparedStatement stmtDeleteValoraciones = null;
        PreparedStatement stmtDeletePelicula = null;
        ResultSet rsSerieId = null;

        try {
            conexion = Conector.conectar();
            conexion.setAutoCommit(false);

            String sqlGetSerieId = "SELECT serie_id FROM Peliculas WHERE titulo = ?";
            PreparedStatement stmtGetSerieId = conexion.prepareStatement(sqlGetSerieId);
            stmtGetSerieId.setString(1, tituloPelicula);
            rsSerieId = stmtGetSerieId.executeQuery();
            
            int serieId = -1;
            if (rsSerieId.next()) {
                serieId = rsSerieId.getInt("serie_id");
            }
            stmtGetSerieId.close();

            if (serieId == -1) {
                JOptionPane.showMessageDialog(this, "Película no encontrada para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                conexion.rollback();
                return;
            }

            String sqlDeleteValoraciones = "DELETE FROM valoraciones WHERE id_serie = ?";
            stmtDeleteValoraciones = conexion.prepareStatement(sqlDeleteValoraciones);
            stmtDeleteValoraciones.setInt(1, serieId);
            stmtDeleteValoraciones.executeUpdate();

            String sqlDeletePelicula = "DELETE FROM Peliculas WHERE serie_id = ?";
            stmtDeletePelicula = conexion.prepareStatement(sqlDeletePelicula);
            stmtDeletePelicula.setInt(1, serieId);
            int filasAfectadasPelicula = stmtDeletePelicula.executeUpdate();

            if (filasAfectadasPelicula > 0) {
                conexion.commit();
                JOptionPane.showMessageDialog(this, "Película y sus valoraciones eliminadas exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarPeliculas(getOrdenActual(), getClasificacionActual());
            } else {
                conexion.rollback();
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la película.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            try {
                if (conexion != null) conexion.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar la película y/o sus valoraciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rsSerieId != null) rsSerieId.close();
                if (stmtDeleteValoraciones != null) stmtDeleteValoraciones.close();
                if (stmtDeletePelicula != null) stmtDeletePelicula.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    private Integer obtenerValoracionUsuario(String tituloPelicula) {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultados = null;
        Integer valoracionExistente = null;

        try {
            conexion = Conector.conectar();
            if (conexion != null) {
                int serieId = obtenerSerieId(tituloPelicula);
                int usuarioId = obtenerUsuarioId(this.correoUsuario);

                if (serieId != -1 && usuarioId != -1) {
                    String sqlValoracion = "SELECT valoracion FROM valoraciones WHERE id_serie = ? AND Usuario_id = ?";
                    consulta = conexion.prepareStatement(sqlValoracion);
                    consulta.setInt(1, serieId);
                    consulta.setInt(2, usuarioId);
                    resultados = consulta.executeQuery();

                    if (resultados.next()) {
                        valoracionExistente = resultados.getInt("valoracion");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error al obtener valoración del usuario: " + ex.getMessage());
        } finally {
            cerrarConexiones(resultados, consulta, conexion);
        }
        return valoracionExistente;
    }

    private void guardarValoracion(String tituloPelicula, int valoracion) {
        int serieId = -1;
        int usuarioId = -1;
        try {
            serieId = obtenerSerieId(tituloPelicula);
            usuarioId = obtenerUsuarioId(this.correoUsuario);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener IDs de película o usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (serieId == -1 || usuarioId == -1) {
            JOptionPane.showMessageDialog(this, "No se encontró la película o el usuario para registrar la valoración.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO valoraciones (id_serie, Usuario_id, valoracion, fecha_valoracion) " +
                     "VALUES (?, ?, ?, CURRENT_TIMESTAMP) " +
                     "ON DUPLICATE KEY UPDATE valoracion = VALUES(valoracion), fecha_valoracion = CURRENT_TIMESTAMP";

        try (Connection conn = Conector.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, serieId);
            stmt.setInt(2, usuarioId);
            stmt.setInt(3, valoracion);

            int affectedRows = stmt.executeUpdate();
            String message;
            if (affectedRows > 0) {
                message = (affectedRows == 1) ? "¡Valoración enviada!" : "¡Valoración actualizada!";
                actualizarPromedio(tituloPelicula);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar ni actualizar la valoración.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error en la operación de valoración: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private int obtenerSerieId(String titulo) throws SQLException {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int serieId = -1;
        try {
            conexion = Conector.conectar();
            String sql = "SELECT serie_id FROM peliculas WHERE titulo = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, titulo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                serieId = rs.getInt("serie_id");
            }
        } finally {
            cerrarConexiones(rs, stmt, conexion);
        }
        return serieId;
    }

    private int obtenerUsuarioId(String correo) throws SQLException {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int usuarioId = -1;
        try {
            conexion = Conector.conectar();
            String sql = "SELECT Usuario_id FROM usuario WHERE correo = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, correo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                usuarioId = rs.getInt("Usuario_id");
            }
        } finally {
            cerrarConexiones(rs, stmt, conexion);
        }
        if (usuarioId == -1) {
            throw new SQLException("Usuario no encontrado con el correo: " + correo);
        }
        return usuarioId;
    }

    private void actualizarPromedio(String tituloPelicula) {
        Connection conexion = null;
        PreparedStatement consulta = null;
        try {
            conexion = Conector.conectar();
            String sql = "UPDATE peliculas p " +
                         "SET p.promedio_valoraciones = (" +
                         "    SELECT ROUND(AVG(v.valoracion), 1) " +
                         "    FROM valoraciones v " +
                         "    WHERE v.id_serie = p.serie_id" +
                         ") " +
                         "WHERE p.titulo = ?";
            consulta = conexion.prepareStatement(sql);
            consulta.setString(1, tituloPelicula);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el promedio de valoraciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            cerrarConexiones(null, consulta, conexion);
        }
    }

    private JLabel mostrarPromedioValoracion(String tituloPelicula) {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        JLabel lblPromedio = null;

        try {
            conexion = Conector.conectar();
            String sql = "SELECT promedio_valoraciones FROM Peliculas WHERE titulo = ?";
            consulta = conexion.prepareStatement(sql);
            consulta.setString(1, tituloPelicula);
            resultado = consulta.executeQuery();

            if (resultado.next()) {
                double promedio = resultado.getDouble("promedio_valoraciones");
                String textoPromedio;
                if (promedio == 0.0) {
                    textoPromedio = "Valoración promedio: N/A";
                } else {
                    textoPromedio = String.format("Valoración promedio: %.1f ★", promedio);
                }
                lblPromedio = new JLabel(textoPromedio);
                lblPromedio.setForeground(Color.YELLOW);
                lblPromedio.setFont(new Font("Segoe UI", Font.BOLD, 12));
            } else {
                lblPromedio = new JLabel("Valoración promedio: N/A");
                lblPromedio.setForeground(Color.YELLOW);
                lblPromedio.setFont(new Font("Segoe UI", Font.BOLD, 12));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error al mostrar el promedio de valoraciones: " + ex.getMessage());
        } finally {
            cerrarConexiones(resultado, consulta, conexion);
        }
        return lblPromedio;
    }

    private void cerrarConexiones(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}