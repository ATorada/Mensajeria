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
    
}
