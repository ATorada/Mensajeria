package Visual;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import Controlador.Controlador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JLabel lblNewLabel_2;
	private JTextField textField_1;
	private Controlador controlador = new Controlador();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
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
		
		JLabel lblNewLabel = new JLabel("Usuario",SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(175, 49, 86, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a",SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(175, 122, 86, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Iniciar Sesi\u00F3n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controlador.loginUsuario(textField_1.getText(), passwordField.getText())) {
					PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(textField_1.getText());
					pantallaPrincipal.setLocationRelativeTo(null);
					pantallaPrincipal.setVisible(true);
				}
			}
		});
		btnNewButton.setBackground(new Color(230, 230, 250));
		btnNewButton.setBounds(89, 205, 121, 28);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Registrarse");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register register = new Register();
				register.setLocationRelativeTo(null);
				register.setVisible(true);
			}
		});
		btnNewButton_1.setBackground(new Color(230, 230, 250));
		btnNewButton_1.setBounds(235, 205, 101, 28);
		contentPane.add(btnNewButton_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(122, 68, 29, 39);
		ImageIcon iconUser = new ImageIcon("C:/Users/alumno/Desktop/icono.png");
		iconUser.getImage().flush();
		lblNewLabel_2.setIcon(iconUser);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setBounds(122, 142, 29, 39);
		ImageIcon iconContra = new ImageIcon("C:/Users/alumno/Desktop/contra.png");
		iconContra.getImage().flush();
		lblNewLabel_2_1.setIcon(iconContra);
		contentPane.add(lblNewLabel_2_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 204, 255));
		panel.setBounds(163, 148, 121, 28);
		contentPane.add(panel);
		panel.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(0, 0, 121, 28);
		panel.add(passwordField);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(204, 204, 255));
		panel_1.setBounds(163, 75, 121, 28);
		contentPane.add(panel_1);
		
		textField_1 = new JTextField();
		textField_1.setText("");
		textField_1.setColumns(10);
		textField_1.setBounds(0, 0, 121, 28);
		panel_1.add(textField_1);

	}
}
