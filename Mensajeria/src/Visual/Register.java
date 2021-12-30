package Visual;

import BBDD.DatosBBDD;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Ángel Torada
 */
public class Register extends JDialog {

    //Variables
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private JPasswordField passwordField_2;
    private JTextField textField_1;
    private JPasswordField passwordField;

    /**
     * Constructor
     */
    public Register() {

        setTitle("Registro");
        setBounds(100, 100, 451, 424);
        contentPane = new JPanel();
        contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
        contentPane.setBackground(new Color(230, 230, 250));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelUsuario = new JPanel();
        panelUsuario.setLayout(null);
        panelUsuario.setBackground(new Color(204, 204, 255));
        panelUsuario.setBounds(157, 110, 121, 28);
        contentPane.add(panelUsuario);

        textField_1 = new JTextField();
        textField_1.setText("");
        textField_1.setColumns(10);
        textField_1.setBounds(0, 0, 121, 28);
        panelUsuario.add(textField_1);

        JPanel panelRepContra = new JPanel();
        panelRepContra.setLayout(null);
        panelRepContra.setBackground(new Color(204, 204, 255));
        panelRepContra.setBounds(157, 263, 121, 28);
        contentPane.add(panelRepContra);

        passwordField = new JPasswordField();
        passwordField.setBounds(0, 0, 121, 28);
        panelRepContra.add(passwordField);

        JPanel panelContra = new JPanel();
        panelContra.setLayout(null);
        panelContra.setBackground(new Color(204, 204, 255));
        panelContra.setBounds(157, 183, 121, 28);
        contentPane.add(panelContra);

        passwordField_2 = new JPasswordField();
        passwordField_2.setBounds(0, 0, 121, 28);
        panelContra.add(passwordField_2);

        JLabel lblError = new JLabel("");
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblError.setForeground(new Color(255, 0, 0));
        lblError.setBounds(116, 305, 212, 16);
        contentPane.add(lblError);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setBounds(116, 32, 29, 39);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("");
        lblNewLabel_2_1.setBounds(116, 106, 29, 39);
        contentPane.add(lblNewLabel_2_1);

        JLabel contrasenya = new JLabel("Contraseña", SwingConstants.CENTER);
        contrasenya.setFont(new Font("Tahoma", Font.BOLD, 13));
        contrasenya.setBounds(173, 153, 86, 14);
        contentPane.add(contrasenya);

        JLabel usuario = new JLabel("Usuario", SwingConstants.CENTER);
        usuario.setFont(new Font("Tahoma", Font.BOLD, 13));
        usuario.setBounds(173, 82, 86, 14);
        contentPane.add(usuario);

        //Botón que se encarga  de registrar al usuario
        JButton registrarse = new JButton("Registrarse");
        registrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosBBDD controlador = new DatosBBDD();
                if (passwordField.getText().equals(passwordField_2.getText())) {
                    try {
                        controlador.registrarUsuario(textField_1.getText(), passwordField.getText());
                        JOptionPane.showMessageDialog(null, "Se ha creado el usuario correctamente.");
                        dispose();
                    } catch (SQLException e1) {
                        lblError.setText("Hay un usuario con ese nombre.");
                        panelUsuario.setBackground(Color.RED);
                        panelContra.setBackground(new Color(204, 204, 255));
                        panelRepContra.setBackground(new Color(204, 204, 255));
                    }
                } else {
                    lblError.setText("Las contraseñas no son iguales");
                    panelUsuario.setBackground(new Color(204, 204, 255));
                    panelContra.setBackground(Color.RED);
                    panelRepContra.setBackground(Color.RED);
                }

            }
        });

        registrarse.setBackground(new Color(230, 230, 250));
        registrarse.setBounds(165, 330, 107, 28);
        contentPane.add(registrarse);

        JLabel repetirContra = new JLabel("Repetir Contraseña", SwingConstants.CENTER);
        repetirContra.setFont(new Font("Tahoma", Font.BOLD, 13));
        repetirContra.setBounds(157, 233, 127, 14);
        contentPane.add(repetirContra);

        JLabel registrate = new JLabel("Regístrate");
        registrate.setFont(new Font("Segoe UI Semilight", Font.BOLD, 24));
        registrate.setBounds(160, 21, 120, 39);
        contentPane.add(registrate);

    }
}
