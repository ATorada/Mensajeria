package Visual;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Controlador.Controlador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Register extends JDialog {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField_2;
	private JTextField textField_1;
	private JPasswordField passwordField;

	public Register() {
		setTitle("Registro");
		setBounds(100, 100, 451, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		contentPane.setBackground(new Color(230, 230, 250));
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
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a", SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(173, 153, 86, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Usuario", SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(173, 82, 86, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Registrarse");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controlador controlador = new Controlador();
				if(passwordField.getText().equals(passwordField_2.getText())) {
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
		btnNewButton_1.setBackground(new Color(230, 230, 250));
		btnNewButton_1.setBounds(165, 330, 107, 28);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Repetir Contrase\u00F1a", SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(157, 233, 127, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_3 = new JLabel("Reg\u00EDstrate");
		lblNewLabel_3.setFont(new Font("Segoe UI Semilight", Font.BOLD, 24));
		lblNewLabel_3.setBounds(160, 21, 120, 39);
		contentPane.add(lblNewLabel_3);
		
	}
}
