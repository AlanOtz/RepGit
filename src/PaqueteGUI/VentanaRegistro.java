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
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import PaqueteDatos.Conector; 

public class VentanaRegistro extends JDialog {

    private JTextField txtNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmarContrasena;
    private JCheckBox mostrarContrasena; 
    private JButton btnRegistrar;
    private JButton btnCancelar; 
    public VentanaRegistro(JDialog parent) {
        super(parent, "Registro", true);
        setSize(600, 450); 
        setUndecorated(true); 
        setLayout(new GridBagLayout());
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Font checkboxFont = new Font("Segoe UI", Font.PLAIN, 12);

        // Título de la ventana
        JLabel lblTitulo = new JLabel("Registro de Nuevo Usuario");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(titleFont);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(20, 20, 10, 20);
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 20, 5, 20);

        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.LIGHT_GRAY);
        lblNombre.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        add(lblNombre, gbc);

        txtNombre = new JTextField(25);
        txtNombre.setFont(textFont);
        txtNombre.setBackground(new Color(60, 60, 60));
        txtNombre.setForeground(Color.WHITE);
        txtNombre.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtNombre, gbc);

        JLabel lblApellidoPaterno = new JLabel("Apellido Paterno:");
        lblApellidoPaterno.setForeground(Color.LIGHT_GRAY);
        lblApellidoPaterno.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        add(lblApellidoPaterno, gbc);

        txtApellidoPaterno = new JTextField(25);
        txtApellidoPaterno.setFont(textFont);
        txtApellidoPaterno.setBackground(new Color(60, 60, 60));
        txtApellidoPaterno.setForeground(Color.WHITE);
        txtApellidoPaterno.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtApellidoPaterno, gbc);

        JLabel lblApellidoMaterno = new JLabel("Apellido Materno:");
        lblApellidoMaterno.setForeground(Color.LIGHT_GRAY);
        lblApellidoMaterno.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        add(lblApellidoMaterno, gbc);

        txtApellidoMaterno = new JTextField(25);
        txtApellidoMaterno.setFont(textFont);
        txtApellidoMaterno.setBackground(new Color(60, 60, 60));
        txtApellidoMaterno.setForeground(Color.WHITE);
        txtApellidoMaterno.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(txtApellidoMaterno, gbc);

        JLabel lblCorreo = new JLabel("Correo Electrónico:");
        lblCorreo.setForeground(Color.LIGHT_GRAY);
        lblCorreo.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        add(lblCorreo, gbc);

        txtCorreo = new JTextField(25);
        txtCorreo.setFont(textFont);
        txtCorreo.setBackground(new Color(60, 60, 60));
        txtCorreo.setForeground(Color.WHITE);
        txtCorreo.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(txtCorreo, gbc);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(Color.LIGHT_GRAY);
        lblContrasena.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        add(lblContrasena, gbc);

        txtContrasena = new JPasswordField(25);
        txtContrasena.setFont(textFont);
        txtContrasena.setBackground(new Color(60, 60, 60));
        txtContrasena.setForeground(Color.WHITE);
        txtContrasena.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(txtContrasena, gbc);

        JLabel lblConfirmarContrasena = new JLabel("Confirmar Contraseña:");
        lblConfirmarContrasena.setForeground(Color.LIGHT_GRAY);
        lblConfirmarContrasena.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        add(lblConfirmarContrasena, gbc);

        txtConfirmarContrasena = new JPasswordField(25);
        txtConfirmarContrasena.setFont(textFont);
        txtConfirmarContrasena.setBackground(new Color(60, 60, 60));
        txtConfirmarContrasena.setForeground(Color.WHITE);
        txtConfirmarContrasena.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(txtConfirmarContrasena, gbc);

        mostrarContrasena = new JCheckBox("Mostrar Contraseña");
        mostrarContrasena.setForeground(Color.LIGHT_GRAY);
        mostrarContrasena.setFont(checkboxFont);
        mostrarContrasena.setBackground(new Color(40, 40, 40));
        mostrarContrasena.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(mostrarContrasena, gbc);

        mostrarContrasena.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (mostrarContrasena.isSelected()) {
                    txtContrasena.setEchoChar((char) 0);
                    txtConfirmarContrasena.setEchoChar((char) 0);
                } else {
                    txtContrasena.setEchoChar('•');
                    txtConfirmarContrasena.setEchoChar('•');
                }
            }
        });

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(panelBotones, gbc);

       
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(buttonFont);
        btnRegistrar.setBackground(new Color(220, 0, 0)); 
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String apellidoPaterno = txtApellidoPaterno.getText().trim();
                String apellidoMaterno = txtApellidoMaterno.getText().trim();
                String correo = txtCorreo.getText().trim();
                char[] contrasenaChars = txtContrasena.getPassword();
                String contrasena = new String(contrasenaChars);
                char[] confirmarContrasenaChars = txtConfirmarContrasena.getPassword();
                String confirmarContrasena = new String(confirmarContrasenaChars);

             
                if (!nombre.matches("[a-zA-Z]+") || !apellidoPaterno.matches("[a-zA-Z]+") || !apellidoMaterno.matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(VentanaRegistro.this, "Nombre y apellidos deben contener solo letras", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (contrasena.length() < 8 || contrasena.length() > 10) {
                    JOptionPane.showMessageDialog(VentanaRegistro.this, "La contraseña debe tener entre 8 y 10 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!contrasena.equals(confirmarContrasena)) {
                    JOptionPane.showMessageDialog(VentanaRegistro.this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidEmail(correo)) {
                    JOptionPane.showMessageDialog(VentanaRegistro.this, "El formato del correo electrónico no es válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                
                if (correoExiste(correo)) {
                    JOptionPane.showMessageDialog(VentanaRegistro.this, "El correo electrónico ya está registrado. Por favor, use otro.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                Connection conexion = null;
                PreparedStatement consulta = null;

                try {
                    
                    conexion = Conector.conectar();
                    if (conexion != null) {
                        
                        String sql = "INSERT INTO Usuario (nombre, apellidop, apellidom, correo, contraseña, usuario_tipo) VALUES (?, ?, ?, ?, ?, ?)";
                            consulta = conexion.prepareStatement(sql);
                            consulta.setString(1, nombre);
                            consulta.setString(2, apellidoPaterno);
                            consulta.setString(3, apellidoMaterno);
                            consulta.setString(4, correo);
                            consulta.setString(5, contrasena);
                            consulta.setString(6, "usuario");

                            int filasAfectadas = consulta.executeUpdate();

                            if (filasAfectadas > 0) {
                                
                                String registro = nombre + " " + apellidoPaterno + " " + apellidoMaterno + " - " + correo;
                                PaqueteDatos.PilasRegistro.agregarRegistro(registro);

                                JOptionPane.showMessageDialog(VentanaRegistro.this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                dispose(); 
                            } else {
                                JOptionPane.showMessageDialog(VentanaRegistro.this, "No se pudo registrar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
}
                    }

                } catch (SQLException ex) {
                    System.err.println("Error de SQL durante el registro:"); 
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(VentanaRegistro.this, "Error al ejecutar la consulta de registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        if (consulta != null) consulta.close();
                        if (conexion != null) conexion.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        panelBotones.add(btnRegistrar);

        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(buttonFont);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(80, 80, 80));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnCancelar);

        setVisible(true);
    }

    
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z]+\\.(com|org)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    
    private boolean correoExiste(String correo) {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultados = null;
        boolean existe = false;
        try {
            conexion = Conector.conectar();
            if (conexion != null) {
                String sql = "SELECT COUNT(*) FROM Usuario WHERE correo = ?";
                consulta = conexion.prepareStatement(sql);
                consulta.setString(1, correo);
                resultados = consulta.executeQuery();
                if (resultados.next()) {
                    if (resultados.getInt(1) > 0) {
                        existe = true;
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al verificar si el correo existe: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al verificar el correo en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
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