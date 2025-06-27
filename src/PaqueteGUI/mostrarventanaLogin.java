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
import PaqueteDatos.Conector; 

public class mostrarventanaLogin extends JDialog {

    private JPasswordField txtContrasena;
    private JCheckBox mostrarContrasena;
    private JTextField txtCorreo;
    private JFrame ventanaNeflis; 

    public mostrarventanaLogin(JFrame parent) {
        super(parent, "Inicio de Sesión", true);
        this.ventanaNeflis = parent; 
        setSize(600, 350); 
        setUndecorated(true);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;

        Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Font checkboxFont = new Font("Segoe UI", Font.PLAIN, 12);

        
        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(titleFont);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 10, 20);
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 20, 5, 20);

       
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setForeground(Color.LIGHT_GRAY);
        lblCorreo.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        add(lblCorreo, gbc);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setForeground(Color.LIGHT_GRAY);
        lblContrasena.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        add(lblContrasena, gbc);

       
        txtCorreo = new JTextField(25);
        txtCorreo.setFont(textFont);
        txtCorreo.setBackground(new Color(60, 60, 60));
        txtCorreo.setForeground(Color.WHITE);
        txtCorreo.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtCorreo, gbc);

        txtContrasena = new JPasswordField(25);
        txtContrasena.setFont(textFont);
        txtContrasena.setBackground(new Color(60, 60, 60));
        txtContrasena.setForeground(Color.WHITE);
        txtContrasena.setCaretColor(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtContrasena, gbc);

        
        mostrarContrasena = new JCheckBox("Mostrar Contraseña");
        mostrarContrasena.setForeground(Color.LIGHT_GRAY);
        mostrarContrasena.setFont(checkboxFont);
        mostrarContrasena.setBackground(new Color(40, 40, 40));
        mostrarContrasena.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(mostrarContrasena, gbc);

        mostrarContrasena.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (mostrarContrasena.isSelected()) {
                    txtContrasena.setEchoChar((char) 0);
                } else {
                    txtContrasena.setEchoChar('•');
                }
            }
        });

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        panelBotones.setBackground(new Color(40, 40, 40));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(panelBotones, gbc);

        
        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(buttonFont);
        btnIngresar.setBackground(new Color(220, 0, 0));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = txtCorreo.getText();
                char[] contrasenaChars = txtContrasena.getPassword();
                String contrasena = new String(contrasenaChars);

                Connection conexion = null;
                PreparedStatement consulta = null;
                ResultSet resultado = null;

                try {
                    conexion = Conector.conectar();
                    if (conexion != null) {
                        consulta = conexion.prepareStatement("SELECT contraseña, nombre, apellidop, apellidom, usuario_tipo FROM Usuario WHERE correo = ?");
                        consulta.setString(1, correo);
                        resultado = consulta.executeQuery();

                        if (resultado.next()) {
                            String contrasenaBD = resultado.getString("contraseña");
                            String nombreUsuarioBD = resultado.getString("nombre");
                            String apellidoPaternoBD = resultado.getString("apellidop");
                            String apellidoMaternoBD = resultado.getString("apellidom");
                            String usuarioTipoBD = resultado.getString("usuario_tipo");
                            if (contrasena.equals(contrasenaBD)) {
                                dispose();
                                if (ventanaNeflis != null) {
                                    ventanaNeflis.dispose();
                                }

                               
                                SwingUtilities.invokeLater(() -> {
                                    Admin ventanaAdmin = new Admin(nombreUsuarioBD, apellidoPaternoBD,
                                            apellidoMaternoBD, usuarioTipoBD, correo);
                                    ventanaAdmin.setVisible(true);
                                });

                            } else {
                                JOptionPane.showMessageDialog(mostrarventanaLogin.this, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(mostrarventanaLogin.this, "No se encontró un usuario con ese correo", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(mostrarventanaLogin.this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mostrarventanaLogin.this, "Error al ejecutar la consulta", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        if (resultado != null) {
                            resultado.close();
                        }
                        if (consulta != null) {
                            consulta.close();
                        }
                        if (conexion != null) {
                            conexion.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    txtContrasena.setText("");
                }
            }
        });
        panelBotones.add(btnIngresar);

        
        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setFont(buttonFont);
        btnRegistrarse.setForeground(Color.LIGHT_GRAY);
        btnRegistrarse.setBackground(new Color(50, 50, 50));
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.setBorderPainted(false);
        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaRegistro vr = new VentanaRegistro(mostrarventanaLogin.this);

            }
        });
        panelBotones.add(btnRegistrarse);

        
        JButton btnCancelar = new JButton("Cancelar");
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

        add(panelBotones, gbc);

        setVisible(true);
    }
}
