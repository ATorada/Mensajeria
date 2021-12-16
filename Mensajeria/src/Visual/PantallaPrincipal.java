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

public class PantallaPrincipal extends JFrame {

	private JPanel contentPane;
	private String Usuario;
	private Controlador controlador;
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
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		ArrayList amigos = controlador.ObtenerAmigos(Usuario);
		
		for (int i = 0; i < amigos.size(); i++) {
			System.out.println(amigos.get(i));
		}
		
	}
}
