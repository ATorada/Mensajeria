package Visual;

import BBDD.DatosBBDD;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Ángel Torada
 */
public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private JPasswordField passwordField;
    private final JLabel iconoUsuario;
    private JTextField usuarioTextField;
    private DatosBBDD controlador = new DatosBBDD();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                }
            }
        });
    }

    /**
     *
     */
    public Login() {
        setTitle("Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
        contentPane.setBackground(new Color(230, 230, 250));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel usuario = new JLabel("Usuario", SwingConstants.CENTER);
        usuario.setFont(new Font("Tahoma", Font.BOLD, 13));
        usuario.setBounds(175, 49, 86, 14);
        contentPane.add(usuario);

        JLabel contrasenya = new JLabel("Contraseña", SwingConstants.CENTER);
        contrasenya.setFont(new Font("Tahoma", Font.BOLD, 13));
        contrasenya.setBounds(175, 122, 86, 14);
        contentPane.add(contrasenya);

        JPanel borde1 = new JPanel();
        borde1.setBackground(new Color(204, 204, 255));
        borde1.setBounds(163, 148, 121, 28);
        contentPane.add(borde1);
        borde1.setLayout(null);

        JPanel borde2 = new JPanel();
        borde2.setLayout(null);
        borde2.setBackground(new Color(204, 204, 255));
        borde2.setBounds(163, 75, 121, 28);
        contentPane.add(borde2);

        JLabel lblError = new JLabel("");
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblError.setBounds(89, 28, 269, 16);
        contentPane.add(lblError);

        JButton btnNewButton = new JButton("Iniciar Sesión");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controlador.loginUsuario(usuarioTextField.getText(), passwordField.getText())) {
                        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(usuarioTextField.getText());
                        pantallaPrincipal.setLocationRelativeTo(null);
                        pantallaPrincipal.setVisible(true);
                    } else {
                        borde2.setBackground(Color.RED);
                        borde1.setBackground(Color.RED);
                        lblError.setText("El usuario o la contraseña no son correctos.");
                    }
                } catch (SQLException e1) {
                    borde2.setBackground(Color.RED);
                    borde1.setBackground(Color.RED);
                    lblError.setText("El usuario o la contraseña no son correctos.");
                }
            }
        });
        btnNewButton.setBackground(new Color(230, 230, 250));
        btnNewButton.setBounds(89, 205, 121, 28);
        contentPane.add(btnNewButton);

        JButton registrarse = new JButton("Registrarse");
        registrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                register.setLocationRelativeTo(null);
                register.setModal(true);
                register.setVisible(true);
            }
        });
        registrarse.setBackground(new Color(236, 230, 250));
        registrarse.setBounds(235, 205, 101, 28);
        contentPane.add(registrarse);

        iconoUsuario = new JLabel("");
        iconoUsuario.setBounds(122, 68, 29, 39);
        ImageIcon iconUser = new ImageIcon(Login.class.getResource("/img/icono.png"));
        iconUser.getImage().flush();
        iconoUsuario.setIcon(iconUser);
        contentPane.add(iconoUsuario);

        JLabel iconoContra = new JLabel("");
        iconoContra.setBounds(122, 142, 29, 39);
        ImageIcon iconContra = new ImageIcon(Login.class.getResource("/img/contra.png"));
        iconContra.getImage().flush();
        iconoContra.setIcon(iconContra);
        contentPane.add(iconoContra);

        usuarioTextField = new JTextField();
        usuarioTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controlador.loginUsuario(usuarioTextField.getText(), passwordField.getText())) {
                        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(usuarioTextField.getText());
                        pantallaPrincipal.setLocationRelativeTo(null);
                        pantallaPrincipal.setModal(true);
                        pantallaPrincipal.setVisible(true);
                    } else {
                        borde2.setBackground(Color.RED);
                        borde1.setBackground(Color.RED);
                        lblError.setText("El usuario o la contraseña no son correctos.");
                    }
                } catch (SQLException e1) {
                    borde2.setBackground(Color.RED);
                    borde1.setBackground(Color.RED);
                    lblError.setText("El usuario o la contraseña no son correctos.");
                }
            }
        });
        usuarioTextField.setText("");
        usuarioTextField.setColumns(10);
        usuarioTextField.setBounds(0, 0, 121, 28);

        passwordField = new JPasswordField();
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controlador.loginUsuario(usuarioTextField.getText(), passwordField.getText())) {
                        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(usuarioTextField.getText());
                        pantallaPrincipal.setLocationRelativeTo(null);
                        pantallaPrincipal.setModal(true);
                        pantallaPrincipal.setVisible(true);
                    } else {
                        borde2.setBackground(Color.RED);
                        borde1.setBackground(Color.RED);
                        lblError.setText("El usuario o la contraseña no son correctos.");
                    }
                } catch (SQLException e1) {
                    borde2.setBackground(Color.RED);
                    borde1.setBackground(Color.RED);
                    lblError.setText("El usuario o la contraseña no son correctos.");
                }
            }
        });
        passwordField.setBounds(0, 0, 121, 28);
        borde1.add(passwordField);
        borde2.add(usuarioTextField);

    }
}
