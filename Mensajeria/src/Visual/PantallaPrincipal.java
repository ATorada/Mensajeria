package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Controlador.Controlador;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.border.EtchedBorder;
import java.awt.Font;

public class PantallaPrincipal extends JFrame {

	private JPanel contentPane;
	private String Usuario;
	private Controlador controlador;
	private JPanel [] amigosPanel; 

	public PantallaPrincipal(String Usuario) {
		this.Usuario = Usuario;
		System.out.println(this.Usuario);
		controlador = new Controlador();
		setTitle("Pantalla Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		contentPane.setBackground(new Color(210, 204, 224));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.setBackground(new Color(236, 230, 250));
		panel.setBounds(377, 11, 291, 438);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_2.setBackground(new Color(248, 248, 255));
		panel_2.setBounds(0, 0, 291, 46);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setBackground(new Color(236, 230, 250));

		panel_1.setBounds(10, 11, 291, 438);
		
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_2_1.setLayout(null);
		panel_2_1.setBackground(new Color(248, 248, 255));
		panel_2_1.setBounds(0, 0, 291, 46);
		panel_1.add(panel_2_1);
		
		JLabel lblNewLabel = new JLabel("Amigos");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblNewLabel.setBounds(127, 60, 59, 21);
		panel_1.add(lblNewLabel);
		
		
		ImageIcon iconGroup = new ImageIcon(Login.class.getResource("/img/grupo.png"));
		iconGroup.getImage().flush();
		JButton btnNewButton = new JButton(iconGroup);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBounds(66, 9, 46, 29);
		panel_2_1.add(btnNewButton);
		
		ImageIcon iconUser = new ImageIcon(Login.class.getResource("/img/conver.png"));
		iconGroup.getImage().flush();
		JButton btnNewButton_1 = new JButton(iconUser);
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setBounds(10, 9, 46, 29);
		panel_2_1.add(btnNewButton_1);
	
		
		ArrayList<String> amigos = controlador.ObtenerAmigos(Usuario);
		amigosPanel = new JPanel[amigos.size()]; 
		
		int altura = 85;
		for (int i = 0; i < amigos.size(); i++) {
			amigosPanel[i] = new JPanel();
			amigosPanel[i].setBackground(Color.WHITE);
			amigosPanel[i].setBounds(71, altura, 164, 33);
			amigosPanel[i].setBorder(new LineBorder(new Color(204, 204, 255), 2));
			panel_1.add(amigosPanel[i]);
			amigosPanel[i].setLayout(null);
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setText(amigos.get(i).toString());
			lblNewLabel_1.setBounds(23, 9, 109, 14);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			amigosPanel[i].add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("");
			ImageIcon iconDM = new ImageIcon(Login.class.getResource("/img/DM.png"));
			iconGroup.getImage().flush();
			lblNewLabel_2.setIcon(iconDM);
			lblNewLabel_2.setBounds(125, 9, 115, 16);
			lblNewLabel_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Holi");
				}
			});
			amigosPanel[i].add(lblNewLabel_2);
			altura += 36;
		}
		
	}
}
