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

import Modelo.ConexionBBDD;

public class Controlador {
	
	ConexionBBDD c;

    public Controlador() {
        this.c = new ConexionBBDD();
        
    }
    
    public void registrarUsuario(String Usuario, String Contra) {
    	Connection cnConnection = c.conexion();
        
        try {
        	PreparedStatement psRegistro = cnConnection.prepareStatement("INSERT INTO public.usuario(usuario, contra) VALUES (?, ?);");
        	psRegistro.setString(1, Usuario);
        	try {
				psRegistro.setString(2, cifrarContra(Contra));
			} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	psRegistro.executeUpdate();
			cnConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    
    public boolean loginUsuario(String Usuario, String Contra) {
    	Connection cnConnection = c.conexion();
        
        try {
        	Statement stmt = cnConnection.createStatement();
        	String stringLogin = "SELECT count(*) FROM usuario WHERE usuario = ? AND contra = ?;";
        	stringLogin = stringLogin.replaceFirst("\\?", Usuario);
        	try {
				stringLogin = stringLogin.replaceFirst("\\?", cifrarContra(Contra));
			} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	ResultSet rs = stmt.executeQuery(stringLogin);
        	cnConnection.close();
        	return rs.getInt(1) == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }
    
    private String cifrarContra(String contra) throws NoSuchAlgorithmException, NoSuchProviderException {
    	
    	SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
    	
        byte[] salt = new byte[16];

        sr.nextBytes(salt);
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        
        md.update(salt.toString().getBytes());
        
        byte[] bytes = md.digest(contra.getBytes());

        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        
        System.out.println(sb.length());
        
        return sb.toString();
        
    }
    
}
