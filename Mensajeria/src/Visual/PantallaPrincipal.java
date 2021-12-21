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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class PantallaPrincipal extends JFrame {

	private JPanel contentPane;
	private String Usuario;
	private Controlador controlador;
	private JPanel [] amigosPanel; 
	private JPanel panelAmigos;
	private JLabel lblConverGrupo;
	private JTextField textMensaje;

	public PantallaPrincipal(String Usuario) {
		this.Usuario = Usuario;

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
		panel.setBounds(377, 58, 291, 391);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(3, 341, 286, 47);
		panel_1.setBackground(new Color(204, 204, 255));
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		textMensaje = new JTextField();
		textMensaje.setEnabled(false);
		textMensaje.setBounds(2, 2, 282, 43);
		panel_1.add(textMensaje);
		textMensaje.setColumns(10);
		
		JPanel panelVacio = new JPanel();
		panelVacio.setVisible(true);
		panelVacio.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelVacio.setBackground(new Color(236, 230, 250));

		panelVacio.setBounds(10, 58, 291, 391);
		
		contentPane.add(panelVacio);
		panelVacio.setLayout(null);
		
		panelAmigos = new JPanel();
		panelAmigos.setVisible(false);
		panelAmigos.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelAmigos.setBackground(new Color(236, 230, 250));

		panelAmigos.setBounds(10, 58, 291, 391);
		
		contentPane.add(panelAmigos);
		panelAmigos.setLayout(null);
		
		JLabel lblAmigosGrupos = new JLabel();
		lblAmigosGrupos.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblAmigosGrupos.setBounds(127, 20, 59, 21);
		panelVacio.add(lblAmigosGrupos);
		
		
		ImageIcon iconGroup = new ImageIcon(Login.class.getResource("/img/grupo.png"));
		iconGroup.getImage().flush();
		
		ImageIcon iconUser = new ImageIcon(Login.class.getResource("/img/conver.png"));
		iconGroup.getImage().flush();
		
		JPanel panelOpciones = new JPanel();
		panelOpciones.setBounds(10, 11, 291, 46);
		contentPane.add(panelOpciones);
		panelOpciones.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelOpciones.setLayout(null);
		panelOpciones.setBackground(new Color(248, 248, 255));
		JButton btnNewButton = new JButton(iconGroup);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAmigosGrupos.setText("Grupos");
			}
		});
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBounds(66, 9, 46, 29);
		panelOpciones.add(btnNewButton);
		JButton btnNewButton_1 = new JButton(iconUser);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAmigosGrupos.setText("Amigos");
				panelVacio.setVisible(false);
				panelAmigos.setVisible(true);
				panelAmigos.add(lblAmigosGrupos);
				cargarAmigos();
			}
		});
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setBounds(10, 9, 46, 29);
		panelOpciones.add(btnNewButton_1);
		
		JPanel panelNombre = new JPanel();
		panelNombre.setBounds(377, 11, 291, 46);
		contentPane.add(panelNombre);
		panelNombre.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelNombre.setBackground(new Color(248, 248, 255));
		panelNombre.setLayout(null);
		
		lblConverGrupo = new JLabel();
		lblConverGrupo.setBounds(10, 16, 106, 17);
		panelNombre.add(lblConverGrupo);

		
	}
	
	private void cargarAmigos() {
		
		ArrayList<String> amigos = controlador.ObtenerAmigos(Usuario);
		amigosPanel = new JPanel[amigos.size()]; 
		
		int altura = 55;
		for (int i = 0; i < amigos.size(); i++) {
			amigosPanel[i] = new JPanel();
			amigosPanel[i].setBackground(Color.WHITE);
			amigosPanel[i].setBounds(71, altura, 164, 33);
			amigosPanel[i].setBorder(new LineBorder(new Color(204, 204, 255), 2));
			panelAmigos.add(amigosPanel[i]);
			amigosPanel[i].setLayout(null);
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setText(amigos.get(i).toString());
			lblNewLabel_1.setBounds(23, 9, 109, 14);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			amigosPanel[i].add(lblNewLabel_1);
			
			String usuarioString = amigos.get(i);
			
			JLabel lblNewLabel_2 = new JLabel("");
			ImageIcon iconDM = new ImageIcon(Login.class.getResource("/img/DM.png"));
			iconDM.getImage().flush();
			lblNewLabel_2.setIcon(iconDM);
			lblNewLabel_2.setBounds(125, 9, 115, 16);
			lblNewLabel_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					lblConverGrupo.setText(usuarioString);
					textMensaje.setEnabled(true);
				}
			});
			amigosPanel[i].add(lblNewLabel_2);
			altura += 36;
		}
	}
}
