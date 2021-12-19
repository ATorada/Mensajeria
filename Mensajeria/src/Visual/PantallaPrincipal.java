package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Controlador.Controlador;

import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class PantallaPrincipal extends JFrame {

	private JPanel contentPane;
	private String Usuario;
	private Controlador controlador;
	private JPanel [] amigosPanel; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaPrincipal frame = new PantallaPrincipal(null);
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
	public PantallaPrincipal(String Usuario) {
		this.Usuario = Usuario;
		System.out.println(this.Usuario);
		controlador = new Controlador();
		setTitle("Pantalla Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		contentPane.setBackground(new Color(230, 230, 250));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 291, 438);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(377, 11, 291, 438);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		

		

		
		JLabel lblNewLabel = new JLabel("Amigos");
		lblNewLabel.setBounds(131, 29, 46, 14);
		panel_1.add(lblNewLabel);
	
		
		ArrayList amigos = controlador.ObtenerAmigos(Usuario);
		amigosPanel = new JPanel[amigos.size()]; 
		
		int altura = 54;
		for (int i = 0; i < amigos.size(); i++) {
			System.out.println(amigos.get(i));
			amigosPanel[i] = new JPanel();
			amigosPanel[i].setBounds(71, altura, 164, 33);
			amigosPanel[i].setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
			amigosPanel[i].setBackground(new Color(230, 230, 250));
			panel_1.add(amigosPanel[i]);
			amigosPanel[i].setLayout(null);
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setText(amigos.get(i).toString());
			lblNewLabel_1.setBounds(10, 11, 109, 14);
			amigosPanel[i].add(lblNewLabel_1);
			altura += 32;
			
			/*
			JPanel panel_2 = new JPanel();
			panel_1.setBounds(71, altura, 164, 33);
			contentPane.add(panel_2);
			panel_2.setLayout(null);
			*/
		}
		
	}
}
