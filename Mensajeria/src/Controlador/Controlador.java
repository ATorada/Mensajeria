package Controlador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import Modelo.ConexionBBDD;

public class Controlador {
	
	ConexionBBDD c;

    public Controlador() {
        this.c = new ConexionBBDD();
        
    }
    
    public void registrarUsuario(String Usuario, String Contra) throws SQLException {
    	Connection cnConnection = c.conexion();
        
       
        	PreparedStatement psRegistro = cnConnection.prepareStatement("INSERT INTO public.usuario(usuario, contra) VALUES (?, ?);");
        	psRegistro.setString(1, Usuario);
        	try {
				psRegistro.setString(2, cifrarContra(Contra));
			} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
				e.printStackTrace();
			}
        	psRegistro.executeUpdate();
			cnConnection.close();
		
        
    }
    
    
    public boolean loginUsuario(String Usuario, String Contra) {
    	Connection cnConnection = c.conexion();
        
        try {
        	//Statement stmt = cnConnection.createStatement();
        	PreparedStatement psLogin = cnConnection.prepareStatement("SELECT count(*) FROM usuario WHERE usuario = ? AND contra = ?;");
        	psLogin.setString(1, Usuario);
        	psLogin.setString(2,  cifrarContra(Contra));
        	ResultSet rs = psLogin.executeQuery();
        	rs.next();
        	boolean resultado = rs.getInt(1) == 1;
        	cnConnection.close();
        	return resultado;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }
    
    private String cifrarContra(String contra) throws NoSuchAlgorithmException, NoSuchProviderException {
    	
    	//SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
    	
        //byte[] salt = new byte[16];

        //sr.nextBytes(salt);
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        
        md.update(contra.getBytes());
        
        byte[] bytes = md.digest(contra.getBytes());

        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        
        return sb.toString();
        
    }

	public ArrayList<String> ObtenerAmigos(String Usuario) {
    	Connection cnConnection = c.conexion();
        
        try {

        	PreparedStatement psLogin = cnConnection.prepareStatement("SELECT usuario1 as Nombre FROM amigo WHERE usuario2 = ? UNION SELECT usuario2 FROM amigo WHERE usuario1 = ?;");
        	psLogin.setString(1, Usuario);
        	psLogin.setString(2, Usuario);
        	ResultSet rs = psLogin.executeQuery();
        	
        	ArrayList<String> Amigos = new ArrayList<String>();
        	
        	while (rs.next()) {
				Amigos.add(rs.getString("Nombre"));
			}

        	cnConnection.close();
        	return Amigos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
        
	}

	public void añadirAmigo(String usuario, String amigo) {
    	Connection cnConnection = c.conexion();
        
        try {

        	PreparedStatement psLogin = cnConnection.prepareStatement("SELECT count(*) FROM usuario WHERE usuario=?");
        	psLogin.setString(1, amigo);
        	ResultSet rs = psLogin.executeQuery();
        	rs.next();
        	
        	int count = rs.getInt(1);
        	
        	if (count==1) {
            	PreparedStatement psAñadir = cnConnection.prepareStatement("INSERT INTO public.amigo(usuario1, usuario2) VALUES (?, ?);");
            	psAñadir.setString(1, usuario);
            	psAñadir.setString(2, amigo);
            	psAñadir.executeUpdate();
			} 
        	cnConnection.close();
		} catch (SQLException e) {	
			JOptionPane.showMessageDialog(null, "Lo siento, ese amigo ya es tu amigo o no existe");
		}
	}

	public void borrarAmigo(String usuario, String amigo) {
    	Connection cnConnection = c.conexion();
        
        try {
            	PreparedStatement psBorrar = cnConnection.prepareStatement("DELETE FROM public.amigo WHERE (usuario1 = ? AND usuario2= ?) OR  (usuario1 = ? AND usuario2= ?);");
            	psBorrar.setString(1, usuario);
            	psBorrar.setString(2, amigo);
            	psBorrar.setString(3, amigo);
            	psBorrar.setString(4, usuario);
            	psBorrar.executeUpdate();
			
            	cnConnection.close();
		} catch (SQLException e) {	
			JOptionPane.showMessageDialog(null, "Lo siento, ese amigo ya es tu amigo o no existe");
		}
		
	}

	public boolean existeConversacion(String usuario, String usuarioString) {
		Connection cnConnection = c.conexion();
        
        try {

        	PreparedStatement psExisteConver = cnConnection.prepareStatement("SELECT count(*) FROM conversacion WHERE (usuario1 = ? AND usuario2= ?) OR  (usuario1 = ? AND usuario2= ?);");
        	psExisteConver.setString(1, usuario);
        	psExisteConver.setString(2, usuarioString);
        	psExisteConver.setString(3, usuarioString);
        	psExisteConver.setString(4, usuario);
        	ResultSet rs = psExisteConver.executeQuery();
        	rs.next();
        	
        	int count = rs.getInt(1);
        	cnConnection.close();
        	if (count==1) {
        		return true;
			}  else {
				return false;
			}
        	
		} catch (SQLException e) {	
			JOptionPane.showMessageDialog(null, "Lo siento, algo ha salido mal.");
		}
        return false;
	}
	
	
	public void crearConversacion(String usuario, String usuarioString) {
		Connection cnConnection = c.conexion();

        
        try {
            	PreparedStatement psCrearConver = cnConnection.prepareStatement("INSERT INTO public.conversacion(usuario1, usuario2) VALUES (?, ?);");
            	psCrearConver.setString(1, usuario);
            	psCrearConver.setString(2, usuarioString);
            	psCrearConver.executeUpdate();
			
            	cnConnection.close();
		} catch (SQLException e) {	
			System.out.println(e);
		}
	}

	public ArrayList<String> cargarMensajes(String usuario, String usuarioString) {
		ArrayList<String> Mensajes = new ArrayList<String>();
		Connection cnConnection = c.conexion();
		System.out.println("Usuario: " + usuario + " Amigo: " + usuarioString);
        try {
        	PreparedStatement psIDConversacion = cnConnection.prepareStatement("SELECT id_conversacion FROM conversacion WHERE (usuario1 = ? AND usuario2= ?) OR  (usuario1 = ? AND usuario2= ?);");
    		psIDConversacion.setString(1, usuario);
    		psIDConversacion.setString(2, usuarioString);
    		psIDConversacion.setString(3, usuarioString);
    		psIDConversacion.setString(4, usuario);
        	ResultSet rsID = psIDConversacion.executeQuery();
        	rsID.next();
        	int IDConver = rsID.getInt(1);
        	System.out.println("ID: " + IDConver);
        	PreparedStatement psMensajeEnviado = cnConnection.prepareStatement("SELECT usuario,id_mensaje FROM mensaje_conversacion_usuario WHERE id_conversacion=?;");
        	psMensajeEnviado.setInt(1, IDConver);
        	ResultSet rsMensajes = psMensajeEnviado.executeQuery();
        	
        	while(rsMensajes.next()) {
        		String mensaje = rsMensajes.getString("usuario")+",";
            	PreparedStatement psTextoMensaje = cnConnection.prepareStatement("SELECT texto FROM mensaje WHERE id_mensaje=?;");
            	psTextoMensaje.setInt(1, rsMensajes.getInt("id_mensaje"));
            	ResultSet rsTextoMensaje = psTextoMensaje.executeQuery();
            	rsTextoMensaje.next();
            	mensaje += rsTextoMensaje.getString(1);
            	Mensajes.add(mensaje);
        	}
        	
        	return Mensajes;
        	
		} catch (SQLException e) {	
			System.out.println(e);
		}
		return null;
	}

	public void enviarMensajeConver(String usuario, String text, String amigo) {
		Connection cnConnection = c.conexion();
		
        
        try {
        		cnConnection.setAutoCommit(false);
            	PreparedStatement psCrearMensaje = cnConnection.prepareStatement("INSERT INTO public.mensaje(texto) VALUES (?);");
            	psCrearMensaje.setString(1, text);
            	psCrearMensaje.executeUpdate();
            	PreparedStatement psIDConversacion = cnConnection.prepareStatement("SELECT id_conversacion FROM conversacion WHERE (usuario1 = ? AND usuario2= ?) OR  (usuario1 = ? AND usuario2= ?);");
        		psIDConversacion.setString(1, usuario);
        		psIDConversacion.setString(2, amigo);
        		psIDConversacion.setString(3, amigo);
        		psIDConversacion.setString(4, usuario);
            	ResultSet rsID = psIDConversacion.executeQuery();
            	rsID.next();
            	int IDConver = rsID.getInt(1);
            	PreparedStatement psEnviarMensaje = cnConnection.prepareStatement("INSERT INTO public.mensaje_conversacion_usuario(usuario, id_conversacion, id_mensaje, fecha, hora) VALUES (?, ?, lastval(), current_date, current_time);");
            	psEnviarMensaje.setString(1, usuario);
            	psEnviarMensaje.setInt(2, IDConver);
            	psEnviarMensaje.executeUpdate();
            	cnConnection.commit();
            	cnConnection.close();
		} catch (SQLException e) {	
			System.out.println(e);
		}
		
	}
    
}
