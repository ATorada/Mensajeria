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
import javax.swing.JDialog;
import javax.swing.Icon;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class PantallaPrincipal extends JDialog {

	private JPanel contentPane;
	private JPanel panelUsuariosGrupo;
	private String Usuario;
	private Controlador controlador;
	private JPanel panelAmigos;
	private JLabel lblConverGrupo;
	private JTextArea textMensaje;
	private JLabel lblCerrar;
	private JPanel panel;
	private JLabel lblCrearGrupo;
	private JPanel panelVacio;
	private ArrayList<JPanel> amigosPanel = new ArrayList<JPanel>();
	private ArrayList<JPanel> mensajesPanel = new ArrayList<JPanel>();
	private ArrayList<JPanel> gruposPanel = new ArrayList<JPanel>();
	private ArrayList<JPanel> gruposUsuarios = new ArrayList<JPanel>();
	private JLabel lblAmigos;
	private JLabel lblGrupos;
	private JButton btnEnviarMensaje;
	private JButton btnBorrarConver;
	private JButton btnLimpiarChat;
	private JPanel panelGrupos;
	private int GrupoActual;
	private JPanel panelNombre;
	private JButton btnBorrarGrupo;
	private JButton btnBorrarUsuariosGrupo;
	private JScrollPane scrollMensaje;
	private boolean estaDentro;
	
	public PantallaPrincipal(String Usuario) {
		setResizable(false);
		this.Usuario = Usuario;
		lblAmigos = new JLabel();
		lblGrupos = new JLabel();
		controlador = new Controlador();
		setTitle("Pantalla Principal");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 694, 531);
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
		
		panelUsuariosGrupo = new JPanel();
		panelUsuariosGrupo.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelUsuariosGrupo.setBackground(new Color(236, 230, 250));
		panelUsuariosGrupo.setBounds(377, 58, 291, 340);
		panelUsuariosGrupo.setLayout(null);
		panelUsuariosGrupo.setVisible(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(377, 432, 250, 47);
		panel_1.setBackground(new Color(204, 204, 255));
		contentPane.add(panel_1);
		

		//panel_1.add(textMensaje);
		//textMensaje.setColumns(10);
		

		panelVacio = new JPanel();
		panelVacio.setVisible(true);
		panelVacio.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelVacio.setBackground(new Color(236, 230, 250));
		panelVacio.setBounds(10, 90, 291, 391);
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
		scrollPane.setLocation(10, 90);
		
		//Establezco la view
		scrollPane.setViewportView(panelAmigos);
		//Añado el scrollPane al main Frame
		contentPane.add(scrollPane);
		
		panelGrupos = new JPanel();
		panelGrupos.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelGrupos.setBackground(new Color(236, 230, 250));
		panelGrupos.setBounds(10, 90, 291, 391);
		contentPane.add(panelGrupos);
		panelGrupos.setLayout(null);
		

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
		panelOpciones.setBounds(10, 43, 291, 46);
		contentPane.add(panelOpciones);
		panelOpciones.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelOpciones.setLayout(null);
		panelOpciones.setBackground(new Color(248, 248, 255));
		
		JButton btnNewButton = new JButton(iconGroup);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setVisible(true);
				scrollPane.setViewportView(panelGrupos);
				panelGrupos.setVisible(true);
				panelAmigos.setVisible(false);
				panelVacio.setVisible(false);
				cargarGrupos();
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
				scrollPane.setViewportView(panelAmigos);
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
		
		

		
		panelNombre = new JPanel();
		panelNombre.setBounds(377, 43, 291, 46);
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
		
		ImageIcon iconBorrarGrupo = new ImageIcon(Login.class.getResource("/img/borrarConver.png"));
		iconBorrarGrupo.getImage().flush();
		btnBorrarGrupo = new JButton(iconBorrarGrupo);
		btnBorrarGrupo.setToolTipText("Borrar Grupo");
		btnBorrarGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.borrarGrupo(GrupoActual);
				btnEnviarMensaje.setEnabled(false);
				btnBorrarGrupo.setVisible(false);
				btnBorrarUsuariosGrupo.setVisible(false);
				panel.removeAll();
				panel.revalidate();
				panel.repaint();
				panelUsuariosGrupo.removeAll();
				panelUsuariosGrupo.revalidate();
				panelUsuariosGrupo.repaint();
				textMensaje.setEnabled(false);
				lblConverGrupo.setText("");
				lblCerrar.setIcon(null);
				panelGrupos.removeAll();
				cargarGrupos();
				panelGrupos.revalidate();
				panelGrupos.repaint();
			}
		});
		btnBorrarGrupo.setVisible(false);
		btnBorrarGrupo.setFocusPainted(false);
		btnBorrarGrupo.setBounds(100, 9, 46, 29);
		panelNombre.add(btnBorrarGrupo);
		
		ImageIcon iconBorrarConver = new ImageIcon(Login.class.getResource("/img/borrarConver.png"));
		iconBorrarConver.getImage().flush();
		btnBorrarConver = new JButton(iconBorrarConver);
		btnBorrarConver.setToolTipText("Borrar Conversaci\u00F3n");
		btnBorrarConver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.borrarConversacion(Usuario, lblConverGrupo.getText());
				btnEnviarMensaje.setEnabled(false);
				btnBorrarConver.setVisible(false);
				btnLimpiarChat.setVisible(false);
				btnBorrarUsuariosGrupo.setVisible(false);
				btnBorrarGrupo.setVisible(false);
				panel.removeAll();
				panel.revalidate();
				panel.repaint();
				textMensaje.setEnabled(false);
				lblConverGrupo.setText("");
				lblCerrar.setIcon(null);
			}
		});
		btnBorrarConver.setVisible(false);
		btnBorrarConver.setFocusPainted(false);
		btnBorrarConver.setBounds(100, 9, 46, 29);
		panelNombre.add(btnBorrarConver);
		
		ImageIcon iconBorrarMensajes = new ImageIcon(Login.class.getResource("/img/borrarMensajes.png"));
		iconBorrarMensajes.getImage().flush();
		btnLimpiarChat = new JButton(iconBorrarMensajes);
		btnLimpiarChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.borrarMensajes(Usuario, lblConverGrupo.getText());
				panel.removeAll();
				panel.revalidate();
				panel.repaint();
			}
		});
		btnLimpiarChat.setToolTipText("Borrar Mensajes");
		btnLimpiarChat.setVisible(false);
		btnLimpiarChat.setFocusPainted(false);
		btnLimpiarChat.setBounds(160, 9, 46, 29);
		panelNombre.add(btnLimpiarChat);
		
		
		ImageIcon iconBorrarUsuario = new ImageIcon(Login.class.getResource("/img/borrarUsuarioGrupo.png"));
		iconBorrarUsuario.getImage().flush();
		btnBorrarUsuariosGrupo = new JButton(iconBorrarUsuario);
		btnBorrarUsuariosGrupo.setToolTipText("Borrar Mensajes");
		btnBorrarUsuariosGrupo.setVisible(false);
		btnBorrarUsuariosGrupo.setFocusPainted(false);
		btnBorrarUsuariosGrupo.setBounds(160, 9, 46, 29);
		btnBorrarUsuariosGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					cargarUsuariosGrupo();
			}
		});
		panelNombre.add(btnBorrarUsuariosGrupo);
		
		
		ImageIcon iconSend = new ImageIcon(Login.class.getResource("/img/send.png"));
		iconSend.getImage().flush();
		btnEnviarMensaje = new JButton(iconSend);
		btnEnviarMensaje.setEnabled(false);
		btnEnviarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textMensaje.getText().trim().isEmpty()) {
					if(controlador.existeConversacion(Usuario, lblConverGrupo.getText())) {
						controlador.enviarMensajeConver(Usuario, textMensaje.getText(), lblConverGrupo.getText());
						String[][] Mensajes = controlador.cargarMensajesConver(Usuario, lblConverGrupo.getText());
						panel.removeAll();
						mostrarMensajes(Mensajes);
						panel.revalidate();
						panel.repaint();
					} else {
						controlador.enviarMensajeGrupo(Usuario, textMensaje.getText(), GrupoActual);
						String[][] Mensajes = controlador.cargarMensajesGrupo(GrupoActual);
						panel.removeAll();
						mostrarMensajes(Mensajes);
						panel.revalidate();
						panel.repaint();
					}
				}
			}
		});
		btnEnviarMensaje.setBounds(629, 432, 39, 47);
		contentPane.add(btnEnviarMensaje);

		
		

		scrollMensaje = new JScrollPane();
		scrollMensaje.setSize(291, 340);
		scrollMensaje.setLocation(377, 90);
		scrollMensaje.setViewportView(panel);
		contentPane.add(scrollMensaje);
		
		ImageIcon iconSalir = new ImageIcon(Login.class.getResource("/img/salir.png"));
		iconSalir.getImage().flush();
		JButton btnNewButton_2 = new JButton(iconSalir);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_2.setBounds(318, 11, 47, 28);
		contentPane.add(btnNewButton_2);


		
	}
	
	protected void cargarGrupos() {
		String[][] grupos = controlador.ObtenerGrupos(Usuario);
		gruposPanel = new ArrayList<JPanel>();
		InicializarAñadirGrupo();
		
		int altura = 55;
		for (int i = 0; i < grupos.length; i++) {
			gruposPanel.add(new JPanel());
			gruposPanel.get(i).setBorder(new LineBorder(new Color(204, 204, 255), 2));
			gruposPanel.get(i).setBounds(71, altura, 164, 33);
			gruposPanel.get(i).setBackground(Color.WHITE);
			gruposPanel.get(i).setLayout(null);
			panelGrupos.add(gruposPanel.get(i));

			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setText(grupos[i][1]);
			lblNewLabel_1.setBounds(23, 9, 109, 14);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			gruposPanel.get(i).add(lblNewLabel_1);
			String nombre = grupos[i][1];
			int id = Integer.valueOf(grupos[i][0]);
			JLabel lblNewLabel_2 = new JLabel("");
			ImageIcon iconDM = new ImageIcon(Login.class.getResource("/img/DM.png"));
			iconDM.getImage().flush();
			lblNewLabel_2.setIcon(iconDM);
			lblNewLabel_2.setBounds(125, 9, 115, 16);
			lblNewLabel_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					scrollMensaje.setViewportView(panel);
					panel.setVisible(true);
					GrupoActual = id;
					if(controlador.esAdmin(Usuario, GrupoActual)) {
						btnBorrarGrupo.setVisible(true);
						btnBorrarUsuariosGrupo.setVisible(true);
					}
					lblConverGrupo.setText(nombre);
					textMensaje.setEnabled(true);
					btnEnviarMensaje.setEnabled(true);
					btnBorrarConver.setVisible(false);
					
					btnLimpiarChat.setVisible(false);
					ImageIcon iconCerrar = new ImageIcon(Login.class.getResource("/img/cerrar.png"));
					iconCerrar.getImage().flush();
					lblCerrar.setIcon(iconCerrar);
					lblCerrar.setVisible(true);
					
					lblCerrar.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							scrollMensaje.setViewportView(panel);
							panel.setVisible(true);
							btnEnviarMensaje.setEnabled(false);
							btnBorrarConver.setVisible(false);
							btnLimpiarChat.setVisible(false);
							btnBorrarUsuariosGrupo.setVisible(false);
							btnBorrarGrupo.setVisible(false);
							panel.setPreferredSize(new Dimension(0,  330));
							panel.removeAll();
							panel.revalidate();
							panel.repaint();
							textMensaje.setEnabled(false);
							lblConverGrupo.setText("");
							lblCerrar.setIcon(null);
						}
					});
						String[][] Mensajes = controlador.cargarMensajesGrupo(id);
						if(Mensajes != null) {
							panel.removeAll();
							mostrarMensajes(Mensajes);
							panel.revalidate();
							panel.repaint();
						} else {
				
						}
						
						
				}
				
			});
			
			JLabel lblNewLabel_3 = new JLabel("");
			ImageIcon iconAñadir = new ImageIcon(Login.class.getResource("/img/añadirAmigo.png"));
			iconAñadir.getImage().flush();
			lblNewLabel_3.setIcon(iconAñadir);
			lblNewLabel_3.setBounds(5, 9, 25, 16);
			lblNewLabel_3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						String amigo = JOptionPane.showInputDialog("Introduzca el usuario a añadir");
						if (amigo != null) {
							int administra = JOptionPane.showConfirmDialog(null, "¿Es administrador?", "Administra", JOptionPane.YES_NO_OPTION);
							if(administra == JOptionPane.YES_OPTION) {
								controlador.añadirAGrupo(id,amigo,true);
							}else if (administra == JOptionPane.NO_OPTION) {
								controlador.añadirAGrupo(id,amigo,false);
							}
							if(panelUsuariosGrupo.isShowing()) {
								panelUsuariosGrupo.removeAll();
								cargarUsuariosGrupo();	
								panelUsuariosGrupo.revalidate();
								panelUsuariosGrupo.repaint();
							}
						}

					} catch (Exception exc) {
						
					}
					
					
				}
			});
			
			JLabel lblNewLabel_4 = new JLabel("");
			ImageIcon iconBorrar = new ImageIcon(Login.class.getResource("/img/borrar.png"));
			iconBorrar.getImage().flush();
			lblNewLabel_4.setIcon(iconBorrar);
			lblNewLabel_4.setBounds(37, 9, 115, 16);
			lblNewLabel_4.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					controlador.borrarUsuarioGrupo(id, Usuario);
					scrollMensaje.setViewportView(panel);
					panel.setVisible(true);
					btnEnviarMensaje.setEnabled(false);
					btnBorrarConver.setVisible(false);
					btnLimpiarChat.setVisible(false);
					btnBorrarUsuariosGrupo.setVisible(false);
					btnBorrarGrupo.setVisible(false);
					panel.setPreferredSize(new Dimension(0,  330));
					panelUsuariosGrupo.setVisible(false);
					panelUsuariosGrupo.removeAll();
					panelUsuariosGrupo.revalidate();
					panelUsuariosGrupo.repaint();
					panelUsuariosGrupo.removeAll();
					panel.revalidate();
					panel.repaint();
					textMensaje.setEnabled(false);
					lblConverGrupo.setText("");
					lblCerrar.setIcon(null);
				}
			});
			gruposPanel.get(i).add(lblNewLabel_2);
			gruposPanel.get(i).add(lblNewLabel_3);
			gruposPanel.get(i).add(lblNewLabel_4);
			
			altura += 36;
		}
		
		panelGrupos.setPreferredSize(new Dimension(0, altura));
	}
	
	private void cargarUsuariosGrupo() {
		panel.setVisible(false);
		scrollMensaje.setViewportView(panelUsuariosGrupo);
		panelUsuariosGrupo.setVisible(true);
		ArrayList<String> personasGrupo = controlador.cargarUsuariosGrupo(GrupoActual);
		gruposUsuarios = new ArrayList<JPanel>();
		int altura = 30;
		for (int i = 0; i < personasGrupo.size(); i++) {
			gruposUsuarios.add(new JPanel());
			gruposUsuarios.get(i).setBackground(Color.WHITE);
			gruposUsuarios.get(i).setBounds(71, altura, 164, 33);
			gruposUsuarios.get(i).setBorder(new LineBorder(new Color(204, 204, 255), 2));
			panelUsuariosGrupo.add(gruposUsuarios.get(i));
			gruposUsuarios.get(i).setLayout(null);
			String usuarioString = personasGrupo.get(i);
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setText(personasGrupo.get(i).toString());
			lblNewLabel_1.setBounds(23, 9, 109, 14);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			gruposUsuarios.get(i).add(lblNewLabel_1);
			JLabel lblNewLabel_3 = new JLabel("");
			ImageIcon iconBorrar = new ImageIcon(Login.class.getResource("/img/borrar.png"));
			iconBorrar.getImage().flush();
			lblNewLabel_3.setIcon(iconBorrar);
			lblNewLabel_3.setBounds(5, 9, 115, 16);
			lblNewLabel_3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
						controlador.borrarUsuarioGrupo(GrupoActual, usuarioString);
						if(usuarioString.equals(Usuario)) {
							scrollMensaje.setViewportView(panel);
							panel.setVisible(true);
							panelUsuariosGrupo.setVisible(false);
							btnEnviarMensaje.setEnabled(false);
							btnBorrarConver.setVisible(false);
							btnLimpiarChat.setVisible(false);
							btnBorrarUsuariosGrupo.setVisible(false);
							btnBorrarGrupo.setVisible(false);
							panel.setPreferredSize(new Dimension(0,  330));
							panel.removeAll();
							panel.revalidate();
							panel.repaint();
							panelGrupos.removeAll();
							cargarGrupos();
							panelGrupos.revalidate();
							panelGrupos.repaint();
							textMensaje.setEnabled(false);
							lblConverGrupo.setText("");
							lblCerrar.setIcon(null);

						} else {
							panelUsuariosGrupo.removeAll();
							cargarUsuariosGrupo();
							panelUsuariosGrupo.revalidate();
							panelUsuariosGrupo.repaint();
						}
						
					
				}
			});
			gruposUsuarios.get(i).add(lblNewLabel_3);
			
			
			altura += 36;
		}
		panelUsuariosGrupo.setPreferredSize(new Dimension(0, altura));
		
	}

	private void cargarAmigos() {
		ArrayList<String> amigos = controlador.ObtenerAmigos(Usuario);
		amigosPanel = new ArrayList<JPanel>();
		InicializarAñadirAmigos();
		int altura = 55;
		for (int i = 0; i < amigos.size(); i++) {
			scrollMensaje.setViewportView(panel);
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
					btnBorrarConver.setVisible(true);
					btnLimpiarChat.setVisible(true);
					ImageIcon iconCerrar = new ImageIcon(Login.class.getResource("/img/cerrar.png"));
					iconDM.getImage().flush();
					lblCerrar.setIcon(iconCerrar);
					lblCerrar.setVisible(true);
					lblCerrar.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							btnEnviarMensaje.setEnabled(false);
							btnBorrarConver.setVisible(false);
							btnLimpiarChat.setVisible(false);
							panel.setPreferredSize(new Dimension(0,  330));
							panel.removeAll();
							panel.revalidate();
							panel.repaint();
							textMensaje.setEnabled(false);
							lblConverGrupo.setText("");
							lblCerrar.setIcon(null);
							
						}
					});
					if(controlador.existeConversacion(Usuario,usuarioString)) {
						String[][] Mensajes = controlador.cargarMensajesConver(Usuario, usuarioString);
						if(Mensajes != null) {
							panel.removeAll();
							mostrarMensajes(Mensajes);
							panel.revalidate();
							panel.repaint();
							btnLimpiarChat.setVisible(true);
							btnBorrarUsuariosGrupo.setVisible(true);
						} else {
							System.out.println("No existe el grupo");
						}
					} else {
						
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
	
	protected void mostrarMensajes(String[][] mensajes) {
		mensajesPanel = new ArrayList<JPanel>();
		int altura = 10;
		
		for(int i = 0; i < mensajes.length; i++) {
			
			mensajesPanel.add(new JPanel());
			mensajesPanel.get(i).setBackground(Color.WHITE);
			mensajesPanel.get(i).setBounds(71, altura, 164, 50);
			mensajesPanel.get(i).setLayout(null);
			
			if(mensajes[i][0].equals(Usuario)) {
				mensajesPanel.get(i).setBorder(new TitledBorder(new LineBorder(new Color(230, 230, 250), 2, true), Usuario , TitledBorder.LEADING, TitledBorder.TOP, null, null));
			} else {
				mensajesPanel.get(i).setBorder(new TitledBorder(new LineBorder(new Color(230, 230, 250), 2, true), mensajes[i][0], TitledBorder.LEADING, TitledBorder.TOP, null, null));
			}
			
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setText(mensajes[i][1]);
			lblNewLabel_1.setBounds(23, 22, 109, 14);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			mensajesPanel.get(i).add(lblNewLabel_1);
			panel.add(mensajesPanel.get(i));
			altura += 55;
		}
		panel.setPreferredSize(new Dimension(0, altura));
		
	}

	private void InicializarAñadirAmigos() {
		panelAmigos.removeAll();
		JButton btnAñadirAmigo = new JButton();
		btnAñadirAmigo.setBounds(170, 11, 46, 38);
		lblAmigos.setText("Amigos");
		lblAmigos.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblAmigos.setBounds(127, 20, 59, 21);
		panelAmigos.add(lblAmigos);
		ImageIcon iconAñadir = new ImageIcon(Login.class.getResource("/img/añadirAmigo.png"));
		iconAñadir.getImage().flush();
		btnAñadirAmigo.setIcon(iconAñadir);
		btnAñadirAmigo.setBorderPainted(false);
		btnAñadirAmigo.setFocusPainted(false);
		btnAñadirAmigo.setContentAreaFilled(false);
		btnAñadirAmigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		panelAmigos.add(btnAñadirAmigo);
		panelAmigos.revalidate();
		panelAmigos.repaint();
	}
	
	
	private void InicializarAñadirGrupo() {
		panelGrupos.removeAll();
		JButton btnCrearGrupo = new JButton();
		btnCrearGrupo.setBounds(170, 11, 46, 38);
		lblGrupos.setText("Grupos");
		//lblGrupos.setBounds(115, 20, 59, 21);
		lblGrupos.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblGrupos.setBounds(127, 20, 59, 21);
		panelGrupos.add(lblGrupos);
		ImageIcon iconAñadir = new ImageIcon(Login.class.getResource("/img/añadirAmigo.png"));
		iconAñadir.getImage().flush();
		btnCrearGrupo.setBorderPainted(false);
		btnCrearGrupo.setFocusPainted(false);
		btnCrearGrupo.setContentAreaFilled(false);
		btnCrearGrupo.setIcon(iconAñadir);
		btnCrearGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombre = JOptionPane.showInputDialog("Introduzca el nombre del grupo");
						if(nombre!=null || nombre.equals("")) {
							controlador.añadirGrupo(Usuario, nombre);
							estaDentro = true;
							panelGrupos.removeAll();
							cargarGrupos();	
							panelGrupos.revalidate();
							panelGrupos.repaint();
						
					}
					
				} catch (Exception exc) {
					
				}
			}
		});
		panelGrupos.add(btnCrearGrupo);
		panelGrupos.revalidate();
		panelGrupos.repaint();
	}
}
