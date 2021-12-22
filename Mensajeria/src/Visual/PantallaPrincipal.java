package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JPanel panelAmigos;
	private JLabel lblConverGrupo;
	private JTextArea textMensaje;
	private JLabel lblCerrar;
	private JPanel panel;
	private JLabel lblAñadirAmigo;
	private JPanel panelVacio;
	private ArrayList<JPanel> amigosPanel = new ArrayList<JPanel>();
	private ArrayList<JPanel> mensajesPanel = new ArrayList<JPanel>();
	private JLabel lblAmigosGrupos;
	private JButton btnEnviarMensaje;
	private JButton btnBorrarConver;
	private JButton btnLimpiarChat;
	
	public PantallaPrincipal(String Usuario) {
		setResizable(false);
		this.Usuario = Usuario;
		lblAmigosGrupos = new JLabel();
		controlador = new Controlador();
		setTitle("Pantalla Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		contentPane.setBackground(new Color(210, 204, 224));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.setBackground(new Color(236, 230, 250));
		panel.setBounds(377, 58, 291, 340);
		
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(377, 400, 250, 47);
		panel_1.setBackground(new Color(204, 204, 255));
		contentPane.add(panel_1);
		

		//panel_1.add(textMensaje);
		//textMensaje.setColumns(10);
		

		panelVacio = new JPanel();
		panelVacio.setVisible(true);
		panelVacio.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelVacio.setBackground(new Color(236, 230, 250));
		panelVacio.setBounds(10, 58, 291, 391);
		contentPane.add(panelVacio);
		panelVacio.setLayout(null);
		
		//Creo el panel de amigos
		panelAmigos = new JPanel();
		panelAmigos.setVisible(false);
		panelAmigos.setBounds(10, 58, 291, 100);
		panelAmigos.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelAmigos.setBackground(new Color(236, 230, 250));
		//Pongo el layout en absoluto
		panelAmigos.setLayout(null);
		//Creo el ScrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(291, 391);
		scrollPane.setLocation(10, 58);
		
		//Establezco la view
		scrollPane.setViewportView(panelAmigos);
		//Añado el scrollPane al main Frame
		contentPane.add(scrollPane);
		
		JPanel panelGrupos = new JPanel();
		panelGrupos.setVisible(false);
		panelGrupos.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelGrupos.setBackground(new Color(236, 230, 250));
		panelGrupos.setBounds(10, 58, 291, 391);
		contentPane.add(panelGrupos);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		textMensaje = new JTextArea("");
		textMensaje.setEnabled(false);
		textMensaje.setBackground(Color.WHITE);
		textMensaje.setForeground(Color.BLACK);
		textMensaje.setBounds(2, 2, 282, 43);
		textMensaje.setLineWrap(true);
		JScrollPane scrollTexto = new JScrollPane(textMensaje);
		panel_1.add(scrollTexto);
		//scrollMsj.setBorder(new LineBorder(Color.BLACK));
		//scrollMsj.getViewport().setBackground(Color.WHITE);
		//scrollMsj.setViewportView(textMensaje);
		//scrollMsj.add(textMensaje);
		
		//panel_1.setPreferredSize(new Dimension(286, 47));
		
		
		ImageIcon iconGroup = new ImageIcon(Login.class.getResource("/img/grupo.png"));
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
				scrollPane.setVisible(false);
				panelGrupos.setVisible(true);
				panelAmigos.setVisible(false);
				panelVacio.setVisible(false);
				lblAñadirAmigo.setIcon(null);
			}
		});
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBounds(66, 9, 46, 29);
		ImageIcon iconUser = new ImageIcon(Login.class.getResource("/img/conver.png"));
		iconUser.getImage().flush();
		JButton btnNewButton_1 = new JButton(iconUser);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setVisible(true);
				panelAmigos.setVisible(true);
				panelVacio.setVisible(false);
				panelGrupos.setVisible(false);
				cargarAmigos();
			}
		});
		panelOpciones.add(btnNewButton);
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
		lblConverGrupo.setBounds(10, 16, 89, 17);
		panelNombre.add(lblConverGrupo);
		
		lblCerrar = new JLabel("");
		lblCerrar.setBounds(257, 13, 24, 22);
		lblCerrar.setVisible(false);
		panelNombre.add(lblCerrar);
		
		ImageIcon iconBorrarConver = new ImageIcon(Login.class.getResource("/img/borrarConver.png"));
		iconBorrarConver.getImage().flush();
		btnBorrarConver = new JButton(iconBorrarConver);
		btnBorrarConver.setEnabled(false);
		btnBorrarConver.setFocusPainted(false);
		btnBorrarConver.setBounds(100, 9, 46, 29);
		panelNombre.add(btnBorrarConver);
		
		ImageIcon iconBorrarMensajes = new ImageIcon(Login.class.getResource("/img/borrarMensajes.png"));
		iconBorrarMensajes.getImage().flush();
		btnLimpiarChat = new JButton(iconBorrarMensajes);
		btnLimpiarChat.setEnabled(false);
		btnLimpiarChat.setFocusPainted(false);
		btnLimpiarChat.setBounds(160, 9, 46, 29);
		panelNombre.add(btnLimpiarChat);
		
		
		ImageIcon iconSend = new ImageIcon(Login.class.getResource("/img/send.png"));
		iconSend.getImage().flush();
		btnEnviarMensaje = new JButton(iconSend);
		btnEnviarMensaje.setEnabled(false);
		btnEnviarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.enviarMensajeConver(Usuario,textMensaje.getText(),lblConverGrupo.getText());
				ArrayList<String> Mensajes = controlador.cargarMensajes(Usuario, lblConverGrupo.getText());
				panel.removeAll();
				mostrarMensajes(Mensajes);
				panel.revalidate();
				panel.repaint();
			}
		});
		btnEnviarMensaje.setBounds(629, 400, 39, 47);
		contentPane.add(btnEnviarMensaje);


		JScrollPane scrollMensaje = new JScrollPane();
		scrollMensaje.setSize(291, 340);
		scrollMensaje.setLocation(377, 58);
		scrollMensaje.setViewportView(panel);
		contentPane.add(scrollMensaje);


		
	}
	
	private void cargarAmigos() {
		ArrayList<String> amigos = controlador.ObtenerAmigos(Usuario);
		amigosPanel = new ArrayList<JPanel>();
		InicializarAñadirAmigos();
		int altura = 55;
		for (int i = 0; i < amigos.size(); i++) {
			amigosPanel.add(new JPanel());
			amigosPanel.get(i).setBackground(Color.WHITE);
			amigosPanel.get(i).setBounds(71, altura, 164, 33);
			amigosPanel.get(i).setBorder(new LineBorder(new Color(204, 204, 255), 2));
			panelAmigos.add(amigosPanel.get(i));
			amigosPanel.get(i).setLayout(null);
			
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setText(amigos.get(i).toString());
			lblNewLabel_1.setBounds(23, 9, 109, 14);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			amigosPanel.get(i).add(lblNewLabel_1);
			
			System.out.println("Sout get" + amigos.get(i));
			String usuarioString = amigos.get(i).toString();
			
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
					btnEnviarMensaje.setEnabled(true);
					btnBorrarConver.setEnabled(true);
					btnLimpiarChat.setEnabled(true);
					ImageIcon iconCerrar = new ImageIcon(Login.class.getResource("/img/cerrar.png"));
					iconDM.getImage().flush();
					lblCerrar.setIcon(iconCerrar);
					lblCerrar.setVisible(true);
					lblCerrar.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							btnEnviarMensaje.setEnabled(false);
							btnBorrarConver.setEnabled(false);
							btnLimpiarChat.setEnabled(false);
							panel.removeAll();
							panel.revalidate();
							panel.repaint();
							textMensaje.setEnabled(false);
							lblConverGrupo.setText("");
							lblCerrar.setIcon(null);
						}
					});
					if(controlador.existeConversacion(Usuario,usuarioString)) {
						System.out.println("Carga datos");
						System.out.println(usuarioString);
						ArrayList<String> Mensajes = controlador.cargarMensajes(Usuario, usuarioString);
						if(Mensajes != null) {
							for(int i = 0; i < Mensajes.size(); i++) {
								System.out.println(i + ". " + Mensajes.get(i));
							}
							panel.removeAll();
							mostrarMensajes(Mensajes);
							panel.revalidate();
							panel.repaint();
						} else {
							System.out.println("No existe la conversación");
						}
					} else {
						System.out.println("Crea conversación");
						controlador.crearConversacion(Usuario, usuarioString);
					}
				}

			});
			amigosPanel.get(i).add(lblNewLabel_2);
			JLabel lblNewLabel_3 = new JLabel("");
			ImageIcon iconBorrar = new ImageIcon(Login.class.getResource("/img/borrar.png"));
			iconBorrar.getImage().flush();
			lblNewLabel_3.setIcon(iconBorrar);
			lblNewLabel_3.setBounds(5, 9, 115, 16);
			lblNewLabel_3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					controlador.borrarAmigo(Usuario, usuarioString);
					panelAmigos.removeAll();
					cargarAmigos();
					panelAmigos.revalidate();
					panelAmigos.repaint();
					
					
				}
			});
			amigosPanel.get(i).add(lblNewLabel_3);
			altura += 36;
		}
		panelAmigos.setPreferredSize(new Dimension(0, altura));
	}
	
	protected void mostrarMensajes(ArrayList<String> mensajes) {
		mensajesPanel = new ArrayList<JPanel>();
		int altura = 10;
		
		for(int i = 0; i < mensajes.size(); i++) {
			
			mensajesPanel.add(new JPanel());
			mensajesPanel.get(i).setBackground(Color.WHITE);
			mensajesPanel.get(i).setBounds(71, altura, 164, 50);
			mensajesPanel.get(i).setLayout(null);
			
			if(mensajes.get(i).split(",")[0].equals(Usuario)) {
				mensajesPanel.get(i).setBorder(new TitledBorder(new LineBorder(new Color(230, 230, 250), 2, true), Usuario , TitledBorder.LEADING, TitledBorder.TOP, null, null));
			} else {
				mensajesPanel.get(i).setBorder(new TitledBorder(new LineBorder(new Color(230, 230, 250), 2, true), mensajes.get(i).split(",")[0], TitledBorder.LEADING, TitledBorder.TOP, null, null));
			}
			
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setText(mensajes.get(i).split(",")[1]);
			lblNewLabel_1.setBounds(23, 22, 109, 14);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			mensajesPanel.get(i).add(lblNewLabel_1);
			panel.add(mensajesPanel.get(i));
			altura += 55;
		}
		panel.setPreferredSize(new Dimension(0, altura));
		
	}

	private void InicializarAñadirAmigos() {
		lblAñadirAmigo = new JLabel("");
		lblAñadirAmigo.setBounds(170, 11, 46, 38);
		lblAmigosGrupos.setText("Amigos");
		lblAmigosGrupos.setBounds(115, 20, 59, 21);
		lblAmigosGrupos.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblAmigosGrupos.setBounds(127, 20, 59, 21);
		panelAmigos.add(lblAmigosGrupos);
		ImageIcon iconAñadir = new ImageIcon(Login.class.getResource("/img/añadirAmigo.png"));
		iconAñadir.getImage().flush();
		lblAñadirAmigo.setIcon(iconAñadir);
		lblAñadirAmigo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String amigo = JOptionPane.showInputDialog("Introduzca el usuario a añadir");
					controlador.añadirAmigo(Usuario,amigo);
					panelAmigos.removeAll();
					cargarAmigos();	
					panelAmigos.revalidate();
					panelAmigos.repaint();
				} catch (Exception exc) {
					
				}
			}
		});
		panelAmigos.add(lblAñadirAmigo);
	}
}
