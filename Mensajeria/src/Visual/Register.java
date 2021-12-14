package Visual;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;

public class Register extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField_2;
	private JTextField textField_1;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setTitle("Registro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		contentPane.setBackground(new Color(230, 230, 250));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		btnNewButton_1.setBackground(new Color(230, 230, 250));
		btnNewButton_1.setBounds(165, 311, 107, 28);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Repetir Contrase\u00F1a", SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(157, 233, 127, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_3 = new JLabel("Reg\u00EDstrate");
		lblNewLabel_3.setFont(new Font("Segoe UI Semilight", Font.BOLD, 24));
		lblNewLabel_3.setBounds(160, 21, 120, 39);
		contentPane.add(lblNewLabel_3);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(204, 204, 255));
		panel.setBounds(157, 183, 121, 28);
		contentPane.add(panel);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(0, 0, 121, 28);
		panel.add(passwordField_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(204, 204, 255));
		panel_1.setBounds(157, 110, 121, 28);
		contentPane.add(panel_1);
		
		textField_1 = new JTextField();
		textField_1.setText("");
		textField_1.setColumns(10);
		textField_1.setBounds(0, 0, 121, 28);
		panel_1.add(textField_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(204, 204, 255));
		panel_2.setBounds(157, 263, 121, 28);
		contentPane.add(panel_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(0, 0, 121, 28);
		panel_2.add(passwordField);
		
	}
}
