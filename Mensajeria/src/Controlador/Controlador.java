package Controlador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    
    
    public boolean loginUsuario(String Usuario, String Contra)  throws SQLException {
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
			e.printStackTrace();
		}
	}

	public String[][] cargarMensajesConver(String usuario, String usuarioString) {
		
		Connection cnConnection = c.conexion();
        try {
        	
        	PreparedStatement psIDConversacion = cnConnection.prepareStatement("SELECT id_conversacion FROM conversacion WHERE (usuario1 = ? AND usuario2= ?) OR  (usuario1 = ? AND usuario2= ?);");
    		psIDConversacion.setString(1, usuario);
    		psIDConversacion.setString(2, usuarioString);
    		psIDConversacion.setString(3, usuarioString);
    		psIDConversacion.setString(4, usuario);
        	ResultSet rsID = psIDConversacion.executeQuery();
        	rsID.next();
        	int IDConver = rsID.getInt(1);
        	
        	PreparedStatement psCantidadMensajes = cnConnection.prepareStatement("SELECT count(*) FROM mensaje_conversacion_usuario WHERE id_conversacion=?;");
        	psCantidadMensajes.setInt(1, IDConver);
        	ResultSet rsCantidadMensajes = psCantidadMensajes.executeQuery();
        	rsCantidadMensajes.next();
        	String[][] Mensajes = new String[rsCantidadMensajes.getInt(1)][2];
        	
        	PreparedStatement psMensajeEnviado = cnConnection.prepareStatement("SELECT usuario,id_mensaje FROM mensaje_conversacion_usuario WHERE id_conversacion=?;");
        	psMensajeEnviado.setInt(1, IDConver);
        	ResultSet rsMensajes = psMensajeEnviado.executeQuery();
        	
        	int contador = 0;
        	while(rsMensajes.next()) {
        		Mensajes[contador][0] = rsMensajes.getString("usuario");
        		
            	PreparedStatement psTextoMensaje = cnConnection.prepareStatement("SELECT texto FROM mensaje WHERE id_mensaje=?;");
            	psTextoMensaje.setInt(1, rsMensajes.getInt("id_mensaje"));
            	ResultSet rsTextoMensaje = psTextoMensaje.executeQuery();
            	rsTextoMensaje.next();
            	Mensajes[contador][1]= rsTextoMensaje.getString(1);
            	contador++;
        	}
        	
        	return Mensajes;
        	
		} catch (SQLException e) {	
			e.printStackTrace();
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
			e.printStackTrace();
		}
		
	}

	public void borrarConversacion(String usuario, String amigo) {
		Connection cnConnection = c.conexion();		try {
    	PreparedStatement psIDConversacion = cnConnection.prepareStatement("SELECT id_conversacion FROM conversacion WHERE (usuario1 = ? AND usuario2= ?) OR  (usuario1 = ? AND usuario2= ?);");
		psIDConversacion.setString(1, usuario);
		psIDConversacion.setString(2, amigo);
		psIDConversacion.setString(3, amigo);
		psIDConversacion.setString(4, usuario);
    	ResultSet rsID = psIDConversacion.executeQuery();
    	rsID.next();
    	int IDConver = rsID.getInt(1);
    	PreparedStatement psBorrarConversacion = cnConnection.prepareStatement("SELECT borrarconversacion(?);");
    	psBorrarConversacion.setInt(1, IDConver);
    	psBorrarConversacion.executeQuery();
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		
	}

	public void borrarMensajes(String usuario, String amigo) {
		
		Connection cnConnection = c.conexion();
		try {
    	PreparedStatement psIDConversacion = cnConnection.prepareStatement("SELECT id_conversacion FROM conversacion WHERE (usuario1 = ? AND usuario2= ?) OR  (usuario1 = ? AND usuario2= ?);");
		psIDConversacion.setString(1, usuario);
		psIDConversacion.setString(2, amigo);
		psIDConversacion.setString(3, amigo);
		psIDConversacion.setString(4, usuario);
    	ResultSet rsID = psIDConversacion.executeQuery();
    	rsID.next();
    	int IDConver = rsID.getInt(1);
    	
    	PreparedStatement psMensajeEnviado = cnConnection.prepareStatement("SELECT id_mensaje FROM mensaje_conversacion_usuario WHERE id_conversacion=?;");
    	psMensajeEnviado.setInt(1, IDConver);
    	ResultSet rsMensajes = psMensajeEnviado.executeQuery();
    	
    	
    	while(rsMensajes.next()) {
    	PreparedStatement psBorrarMensaje = cnConnection.prepareStatement("SELECT borrarmensaje(?);");
    	psBorrarMensaje.setInt(1, rsMensajes.getInt(1));
    	psBorrarMensaje.executeQuery();
    	}
    	
		} catch (SQLException e) {	
			e.printStackTrace();
		}
	}

	public String[][] ObtenerGrupos(String Usuario) {
		
		Connection cnConnection = c.conexion();
        try {

        	PreparedStatement psCantidadGrupos = cnConnection.prepareStatement("SELECT count(*) FROM usuariogrupo WHERE usuario=?;");
        	psCantidadGrupos.setString(1, Usuario);
        	ResultSet rsCantidadGrupos = psCantidadGrupos.executeQuery();
        	rsCantidadGrupos.next();
        	
        	String[][] Grupos = new String[rsCantidadGrupos.getInt(1)][2];
        	
        	
        	
        	PreparedStatement psGruposID = cnConnection.prepareStatement("SELECT id_grupo FROM usuariogrupo WHERE usuario=?;");
        	psGruposID.setString(1, Usuario);
        	ResultSet rsGruposID = psGruposID.executeQuery();
        	
        	
        	
        	int contador = 0;
        	while (rsGruposID.next()) {
        		Grupos[contador][0] = String.valueOf(rsGruposID.getInt(1));
            	PreparedStatement psGrupoNombre = cnConnection.prepareStatement("SELECT nombre_grupo FROM grupo WHERE id_grupo=?;");
            	psGrupoNombre.setInt(1, rsGruposID.getInt(1));
            	ResultSet rsGrupoNombre = psGrupoNombre.executeQuery();
            	rsGrupoNombre.next();
            	Grupos[contador][1] = rsGrupoNombre.getString(1);
            	contador++;
			}

        	cnConnection.close();
        	return Grupos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
        
	}


	public String[][] cargarMensajesGrupo(int id) {
		Connection cnConnection = c.conexion();
        try {
        	
        	
        	PreparedStatement psCantidadMensajes = cnConnection.prepareStatement("SELECT count(*) FROM mensaje_grupo_usuario WHERE id_grupo=?;");
        	psCantidadMensajes.setInt(1, id);
        	ResultSet rsCantidadMensajes = psCantidadMensajes.executeQuery();
        	rsCantidadMensajes.next();
        	String[][] Mensajes = new String[rsCantidadMensajes.getInt(1)][2];
        	
        	PreparedStatement psMensajeEnviado = cnConnection.prepareStatement("SELECT usuario,id_mensaje FROM mensaje_grupo_usuario WHERE id_grupo=?;");
        	psMensajeEnviado.setInt(1, id);
        	ResultSet rsMensajes = psMensajeEnviado.executeQuery();
        	
        	int contador = 0;
        	while(rsMensajes.next()) {
        		Mensajes[contador][0] = rsMensajes.getString("usuario");
        		
            	PreparedStatement psTextoMensaje = cnConnection.prepareStatement("SELECT texto FROM mensaje WHERE id_mensaje=?;");
            	psTextoMensaje.setInt(1, rsMensajes.getInt("id_mensaje"));
            	ResultSet rsTextoMensaje = psTextoMensaje.executeQuery();
            	rsTextoMensaje.next();
            	Mensajes[contador][1]= rsTextoMensaje.getString(1);
            	contador++;
        	}
        	
        	return Mensajes;
        	
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return null;
	}
    
	public void enviarMensajeGrupo(String usuario, String text, int id) {
		Connection cnConnection = c.conexion();
		
        
        try {
        		cnConnection.setAutoCommit(false);
            	PreparedStatement psCrearMensaje = cnConnection.prepareStatement("INSERT INTO public.mensaje(texto) VALUES (?);");
            	psCrearMensaje.setString(1, text);
            	psCrearMensaje.executeUpdate();
            	
            	PreparedStatement psEnviarMensaje = cnConnection.prepareStatement("INSERT INTO public.mensaje_grupo_usuario(usuario, id_grupo, id_mensaje, fecha, hora) VALUES (?, ?, lastval(), current_date, current_time);");
            	psEnviarMensaje.setString(1, usuario);
            	psEnviarMensaje.setInt(2, id);
            	psEnviarMensaje.executeUpdate();
            	cnConnection.commit();
            	cnConnection.close();
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		
	}

	public void borrarGrupo(int grupoActual) {
		Connection cnConnection = c.conexion();
		try {
    	PreparedStatement psBorrarGrupo = cnConnection.prepareStatement("SELECT borrargrupo(?);");
    	psBorrarGrupo.setInt(1, grupoActual);
    	psBorrarGrupo.executeQuery();
    	cnConnection.close();
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		
	}

	public boolean esAdmin(String usuario, int grupoActual) {
		Connection cnConnection = c.conexion();
		
		
		
		try {
		PreparedStatement psesAdmin = cnConnection.prepareStatement("SELECT administra FROM usuariogrupo WHERE usuario=? AND id_grupo=?;");
		psesAdmin.setString(1, usuario);
		psesAdmin.setInt(2, grupoActual);
    	ResultSet rsesAdmin = psesAdmin.executeQuery();
    	rsesAdmin.next();
    	cnConnection.close();
    	return rsesAdmin.getBoolean("administra");
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		
		return false;
	}

	public ArrayList<String> cargarUsuariosGrupo(int grupoActual) {
		Connection cnConnection = c.conexion();
		ArrayList<String> personasGrupo = new ArrayList<String>();
		
		
		try {
		PreparedStatement psUsuariosGrupo = cnConnection.prepareStatement("SELECT usuario FROM usuariogrupo WHERE id_grupo=?;");
		psUsuariosGrupo.setInt(1, grupoActual);
    	ResultSet rsUsuariosGrupo = psUsuariosGrupo.executeQuery();
    	
    	while(rsUsuariosGrupo.next()) {
    		personasGrupo.add(rsUsuariosGrupo.getString("usuario"));
    	}
    	
    	cnConnection.close();
    	return personasGrupo;
		} catch (SQLException e) {	
			e.printStackTrace();
		}
			
		
		return null;
	}

	public void borrarUsuarioGrupo(int grupoActual, String usuarioString) {
		
		Connection cnConnection = c.conexion();
		try {
    	PreparedStatement psBorrarUsuarioGrupo = cnConnection.prepareStatement("DELETE FROM public.usuariogrupo WHERE usuario=? AND id_grupo=?");
    	psBorrarUsuarioGrupo.setString(1, usuarioString);
    	psBorrarUsuarioGrupo.setInt(2, grupoActual);
    	psBorrarUsuarioGrupo.executeUpdate();
    	cnConnection.close();
		} catch (SQLException e) {	
			JOptionPane.showMessageDialog(null, "Debes borrar el grupo para borrar el último usuario.");
		}
		
	}

	public void añadirGrupo(String usuarioActual, String nombreGrupo) {
		Connection cnConnection = c.conexion();
        try {
        		cnConnection.setAutoCommit(false);
            	PreparedStatement psCrearGrupo = cnConnection.prepareStatement("INSERT INTO public.grupo(nombre_grupo) VALUES (?);");
            	psCrearGrupo.setString(1, nombreGrupo);
            	psCrearGrupo.executeUpdate();
            	
            	PreparedStatement psUnirPrimerUsuario = cnConnection.prepareStatement("INSERT INTO public.usuariogrupo(usuario, id_grupo, administra) VALUES (?, lastval(), true);");
            	psUnirPrimerUsuario.setString(1, usuarioActual);
            	psUnirPrimerUsuario.executeUpdate();
            	cnConnection.commit();
            	cnConnection.close();
		} catch (SQLException e) {	
			
		}
		
	}

	public void añadirAGrupo(int id, String usuario, boolean administra) {
		Connection cnConnection = c.conexion();
        try {
            	PreparedStatement psAñadirUsuario = cnConnection.prepareStatement("INSERT INTO public.usuariogrupo(usuario,id_grupo,administra) VALUES (?,?,?);");
            	psAñadirUsuario.setString(1, usuario);
            	psAñadirUsuario.setInt(2, id);
            	psAñadirUsuario.setBoolean(3, administra);
            	psAñadirUsuario.executeUpdate();
		} catch (SQLException e) {	
			JOptionPane.showMessageDialog(null, "Ese usuario no existe.");
		}
		
		
	}
	
	
}
