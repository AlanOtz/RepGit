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
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class Neflis extends JFrame {

    private boolean pantallaCompleta = false;
    private GraphicsDevice gd;
    private DisplayMode dmOriginal;
    private JLabel lblIconoLogin;
    private JLabel lblTextoLogin;
    private float loginOpacity = 1.0f;
    private boolean mouseOverLogin = false;
    private JPanel panelLoginContainer;
    private List<ImageIcon> fondosLogin;
    private int indiceFondoActual = 0;
    private JLabel lblFondo;
    private Timer timerCambioFondo;
    private static final int INTERVALO_CAMBIO_FONDO = 5000; 
    private Color overlayColor = new Color(0, 0, 0, 150); 
    private JLabel lblTituloNeflis; 

    public Neflis() {
        super("Neflis - Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();
        dmOriginal = gd.getDisplayMode();

        fondosLogin = new ArrayList<>();
        fondosLogin.add(new ImageIcon(getClass().getResource("/imageneslogin/fondo1.jpg")));
        fondosLogin.add(new ImageIcon(getClass().getResource("/imageneslogin/fondo2.jpg")));
        fondosLogin.add(new ImageIcon(getClass().getResource("/imageneslogin/fondo3.jpg")));
        fondosLogin.add(new ImageIcon(getClass().getResource("/imageneslogin/fondo4.jpg")));
        fondosLogin.add(new ImageIcon(getClass().getResource("/imageneslogin/fondo5.jpg")));
        fondosLogin.add(new ImageIcon(getClass().getResource("/imageneslogin/fondo6.jpg")));
        fondosLogin.add(new ImageIcon(getClass().getResource("/imageneslogin/fondo7.jpg")));
        

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        for (int i = 0; i < fondosLogin.size(); i++) {
            Image img = fondosLogin.get(i).getImage();
            Image nuevaImg = img.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
            fondosLogin.set(i, new ImageIcon(nuevaImg));
        }

        lblFondo = new JLabel(fondosLogin.isEmpty() ? null : fondosLogin.get(0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(overlayColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        lblFondo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 60)); 

      
        JPanel panelPrincipal = new JPanel(new BorderLayout());

       
        JPanel barraSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        barraSuperior.setBackground(Color.BLACK);
        barraSuperior.setPreferredSize(new Dimension(screenSize.width, 60));
        barraSuperior.setOpaque(true);

        
        lblTituloNeflis = new JLabel("NEFLIS");
        lblTituloNeflis.setForeground(Color.RED);
        lblTituloNeflis.setFont(new Font("Arial", Font.BOLD, 24));
        barraSuperior.add(lblTituloNeflis);

       
        barraSuperior.add(Box.createHorizontalGlue());

        
        panelLoginContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panelLoginContainer.setOpaque(false);

        
        ImageIcon iconoLogin = new ImageIcon(getClass().getResource("/images/ingresar.png"));
        Image imgIcon = iconoLogin.getImage();
        Image nuevaImgIcon = imgIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconoLogin = new ImageIcon(nuevaImgIcon);

        lblIconoLogin = new JLabel(iconoLogin) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, loginOpacity));
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        lblIconoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        
        lblTextoLogin = new JLabel("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, loginOpacity));
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        lblTextoLogin.setForeground(Color.WHITE);
        lblTextoLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTextoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));


        MouseAdapter loginMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new mostrarventanaLogin(Neflis.this);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouseOverLogin = true;
                loginOpacity = 0.7f;
                lblIconoLogin.repaint();
                lblTextoLogin.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseOverLogin = false;
                loginOpacity = 1.0f;
                lblIconoLogin.repaint();
                lblTextoLogin.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                loginOpacity = 0.5f;
                lblIconoLogin.repaint();
                lblTextoLogin.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                loginOpacity = mouseOverLogin ? 0.7f : 1.0f;
                lblIconoLogin.repaint();
                lblTextoLogin.repaint();
            }
        };

        lblIconoLogin.addMouseListener(loginMouseListener);
        lblTextoLogin.addMouseListener(loginMouseListener);

        panelLoginContainer.add(lblTextoLogin);
        panelLoginContainer.add(lblIconoLogin);
        barraSuperior.add(panelLoginContainer);

        
        JPanel panelFondo = new JPanel(new BorderLayout());
        panelFondo.add(lblFondo, BorderLayout.CENTER);

      
        panelPrincipal.add(barraSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelFondo, BorderLayout.CENTER);

       
        add(panelPrincipal);

        iniciarTemporizadorCambioFondo();
        setVisible(true);

        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F11) {
                    togglePantallaCompleta();
                }
            }
        });

      
        setFocusable(true);
        requestFocusInWindow();
    }

    private void iniciarTemporizadorCambioFondo() {
        if (fondosLogin.size() > 1) {
            timerCambioFondo = new Timer(INTERVALO_CAMBIO_FONDO, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    indiceFondoActual = (indiceFondoActual + 1) % fondosLogin.size();
                    lblFondo.setIcon(fondosLogin.get(indiceFondoActual));
                    lblFondo.repaint();
                }
            });
            timerCambioFondo.start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        
    }

    private void togglePantallaCompleta() {
        if (!pantallaCompleta) {
            dispose();
            setUndecorated(true);
            gd.setFullScreenWindow(this);
            pantallaCompleta = true;
        } else {
            dispose();
            setUndecorated(false);
            gd.setFullScreenWindow(null);
            setSize(dmOriginal.getWidth(), dmOriginal.getHeight());
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setLocationRelativeTo(null);
            setVisible(true);
            pantallaCompleta = false;
        }
        requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Neflis::new);
    }
}