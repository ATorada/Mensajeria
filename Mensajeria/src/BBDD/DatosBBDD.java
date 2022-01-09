package BBDD;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Ángel Torada
 */
public class DatosBBDD {

    ConexionBBDD c;

    /**
     * Constructor que inicializa un objeto de la clase conexión
     */
    public DatosBBDD() {
        this.c = new ConexionBBDD();
    }

    /**
     * Método que se encarga de registrar un usuario en la base de datos
     *
     * @param Usuario Nombre del usuario a crear
     * @param Contra Contraseña del usuario a crear
     * @throws SQLException Lanza un error si ya existe el usuario
     */
    public void registrarUsuario(String Usuario, String Contra) throws SQLException {
        try ( Connection cnConnection = c.conexion()) {
            PreparedStatement psRegistro = cnConnection.prepareStatement("INSERT INTO public.usuario(usuario, contra) VALUES (?, ?);");
            psRegistro.setString(1, Usuario);
            try {
                psRegistro.setString(2, cifrarContra(Contra));
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                System.out.println("Algo ha salido mal con la conexión a la base de datos.");
            }
            psRegistro.executeUpdate();
        }
    }

    /**
     * Método que se encarga de retornar si un usuario con esa contraseña existe
     *
     * @param Usuario Usuario a comprobar
     * @param Contra Contraseña a comprobar
     * @return Devuelve True si está el usuario con esa contraseña y false en
     * caso contrario
     * @throws SQLException En caso de que haya una excepción la lanzará
     */
    public boolean loginUsuario(String Usuario, String Contra) throws SQLException {
        

        try {
        	Connection cnConnection = c.conexion();
            PreparedStatement psLogin = cnConnection.prepareStatement("SELECT count(*) FROM usuario WHERE usuario = ? AND contra = ?;");
            psLogin.setString(1, Usuario);
            psLogin.setString(2, cifrarContra(Contra));
            ResultSet rs = psLogin.executeQuery();
            rs.next();
            boolean resultado = rs.getInt(1) == 1;
            cnConnection.close();
            return resultado;
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }
        return false;
    }

    //Método que se encarga de cifrar la contraseña con el cifrado de tipo Sha-256
    private String cifrarContra(String contra) throws NoSuchAlgorithmException, NoSuchProviderException {

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

    /**
     * Método que se encarga de obtener los amigos de un usuario pasado como
     * parámetro
     *
     * @param Usuario Usuario del cual se pedirán los amigos
     * @return Devolverá un Arraylist con los amigos del usuario
     */
    public ArrayList<String> ObtenerAmigos(String Usuario) {
        

        try {
        	Connection cnConnection = c.conexion();
            PreparedStatement psLogin = cnConnection.prepareStatement("SELECT usuario1 as Nombre FROM amigo WHERE usuario2 = ? UNION SELECT usuario2 FROM amigo WHERE usuario1 = ?;");
            psLogin.setString(1, Usuario);
            psLogin.setString(2, Usuario);
            ResultSet rs = psLogin.executeQuery();

            ArrayList<String> Amigos = new ArrayList<>();

            while (rs.next()) {
                Amigos.add(rs.getString("Nombre"));
            }

            cnConnection.close();
            return Amigos;
        } catch (SQLException e) {
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }
        
        return null;

    }

    /**
     * Método que se encarga de añadir un amigo al Usuario
     *
     * @param usuario Usuario el cual va a añadir
     * @param amigo Amigo el cual se va a añadir
     */
    public void anyadirAmigo(String usuario, String amigo) {

        try {
            Connection cnConnection = c.conexion();
            PreparedStatement psLogin = cnConnection.prepareStatement("SELECT count(*) FROM usuario WHERE usuario=?");
            psLogin.setString(1, amigo);
            ResultSet rs = psLogin.executeQuery();
            rs.next();

            int count = rs.getInt(1);

            if (count == 1) {
                PreparedStatement psAnyadir = cnConnection.prepareStatement("INSERT INTO public.amigo(usuario1, usuario2) VALUES (?, ?);");
                psAnyadir.setString(1, usuario);
                psAnyadir.setString(2, amigo);
                psAnyadir.executeUpdate();
            } else {
            	//En caso de que no exista avisará al usuario.
                JOptionPane.showMessageDialog(null, "Lo siento, ese amigo ya es tu amigo o no existe");
            }
            cnConnection.close();
        } catch (SQLException e) {
            //En caso de que no exista avisará al usuario.
            JOptionPane.showMessageDialog(null, "Lo siento, ese amigo ya es tu amigo o no existe");
        }
    }

    /**
     * Método que se encarga de borrar un amigo del usuario
     *
     * @param usuario Usuario del cual se va a borrar el amigo
     * @param amigo Amigo el cual va a ser borrado del usuario
     */
    public void borrarAmigo(String usuario, String amigo) {

        try {
            Connection cnConnection = c.conexion();
        	PreparedStatement psBorrar = cnConnection.prepareStatement("DELETE FROM public.amigo WHERE (usuario1 = ? AND usuario2= ?) OR  (usuario1 = ? AND usuario2= ?);");
            psBorrar.setString(1, usuario);
            psBorrar.setString(2, amigo);
            psBorrar.setString(3, amigo);
            psBorrar.setString(4, usuario);
            psBorrar.executeUpdate();

            cnConnection.close();
        } catch (SQLException e) {
            //Avisará en caso de que ese amigo no esté agregado ya
            //No debería saltar
            JOptionPane.showMessageDialog(null, "Lo siento no existe este amigo en su lista de amigo");
        }

    }

    /**
     * Método que se encarga de comprobar si una conversación existe
     *
     * @param usuario Usuario que ha iniciado sesión
     * @param usuarioString Amigo del Usuario
     * @return Devuelve True si existe y false en caso contrario
     */
    public boolean existeConversacion(String usuario, String usuarioString) {

        try {
            Connection cnConnection = c.conexion();
            PreparedStatement psExisteConver = cnConnection.prepareStatement("SELECT count(*) FROM conversacion WHERE (usuario1 = ? AND usuario2= ?) OR  (usuario1 = ? AND usuario2= ?);");
            psExisteConver.setString(1, usuario);
            psExisteConver.setString(2, usuarioString);
            psExisteConver.setString(3, usuarioString);
            psExisteConver.setString(4, usuario);
            ResultSet rs = psExisteConver.executeQuery();
            rs.next();

            int count = rs.getInt(1);
            cnConnection.close();
            return count == 1;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lo siento, algo ha salido mal.");
        }
        return false;
    }

    /**
     * Método que se encarga de crear una conversación
     *
     * @param usuario Usuario que ha iniciado sesión
     * @param usuarioString Amigo del usuario
     */
    public void crearConversacion(String usuario, String usuarioString) {

        try {
            Connection cnConnection = c.conexion();
            PreparedStatement psCrearConver = cnConnection.prepareStatement("INSERT INTO public.conversacion(usuario1, usuario2) VALUES (?, ?);");
            psCrearConver.setString(1, usuario);
            psCrearConver.setString(2, usuarioString);
            psCrearConver.executeUpdate();

            cnConnection.close();
        } catch (SQLException e) {
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }
    }

    /**
     * Método que se encarga de obtener los mensajes de una conversación
     *
     * @param usuario Usuario que ha iniciado sesión
     * @param usuarioString Amigo de la conversación
     * @return Devuelve un Array de String que contiene el mensaje y el usuario
     * que lo ha mandado
     */
    public String[][] cargarMensajesConver(String usuario, String usuarioString) {

        try {
            Connection cnConnection = c.conexion();
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
            while (rsMensajes.next()) {
                Mensajes[contador][0] = rsMensajes.getString("usuario");

                PreparedStatement psTextoMensaje = cnConnection.prepareStatement("SELECT texto FROM mensaje WHERE id_mensaje=?;");
                psTextoMensaje.setInt(1, rsMensajes.getInt("id_mensaje"));
                ResultSet rsTextoMensaje = psTextoMensaje.executeQuery();
                rsTextoMensaje.next();
                Mensajes[contador][1] = rsTextoMensaje.getString(1);
                contador++;
            }

            return Mensajes;

        } catch (SQLException e) {
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }
        return null;
    }

    /**
     * Método que se encarga de crear un mensaje y enviarlo en una conversación
     *
     * @param usuario Usuario que va a enviar el mensaje
     * @param text Texto que va a enviar
     * @param amigo Amigo de la conversación
     */
    public void enviarMensajeConver(String usuario, String text, String amigo) {

        try {
            Connection cnConnection = c.conexion();
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
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }

    }

    /**
     * Método que se encarga de borrar una conversación
     *
     * @param usuario Usuario que ha iniciado sesión
     * @param amigo Amigo del usuario
     */
    public void borrarConversacion(String usuario, String amigo) {
        try {
            Connection cnConnection = c.conexion();
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
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }

    }

    /**
     * Método que se encarga de borrar todos los mensajes de una conversación
     *
     * @param usuario Usuario que ha iniciado sesión
     * @param amigo Amigo del usuario
     */
    public void borrarMensajes(String usuario, String amigo) {

        try {
            Connection cnConnection = c.conexion();
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

            while (rsMensajes.next()) {
                PreparedStatement psBorrarMensaje = cnConnection.prepareStatement("SELECT borrarmensaje(?);");
                psBorrarMensaje.setInt(1, rsMensajes.getInt(1));
                psBorrarMensaje.executeQuery();
            }

        } catch (SQLException e) {
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }
    }

    /**
     * Método que se encarga de obtener los grupos en los que está un usuario
     *
     * @param Usuario Usuario del que se van a obtener los grupos
     * @return Devuelve un Array con los grupos y su identificador
     */
    public String[][] ObtenerGrupos(String Usuario) {

        try {
            Connection cnConnection = c.conexion();
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
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }
        return null;

    }

    /**
     * Método que se encarga de obtener los mensajes de un grupo
     *
     * @param id Identificador del grupo
     * @return Array con los mensajes del grupo y quién los ha enviado
     */
    public String[][] cargarMensajesGrupo(int id) {
        
        try {
            Connection cnConnection = c.conexion();
            PreparedStatement psCantidadMensajes = cnConnection.prepareStatement("SELECT count(*) FROM mensaje_grupo_usuario WHERE id_grupo=?;");
            psCantidadMensajes.setInt(1, id);
            ResultSet rsCantidadMensajes = psCantidadMensajes.executeQuery();
            rsCantidadMensajes.next();
            String[][] Mensajes = new String[rsCantidadMensajes.getInt(1)][2];

            PreparedStatement psMensajeEnviado = cnConnection.prepareStatement("SELECT usuario,id_mensaje FROM mensaje_grupo_usuario WHERE id_grupo=?;");
            psMensajeEnviado.setInt(1, id);
            ResultSet rsMensajes = psMensajeEnviado.executeQuery();

            int contador = 0;
            while (rsMensajes.next()) {
                Mensajes[contador][0] = rsMensajes.getString("usuario");

                PreparedStatement psTextoMensaje = cnConnection.prepareStatement("SELECT texto FROM mensaje WHERE id_mensaje=?;");
                psTextoMensaje.setInt(1, rsMensajes.getInt("id_mensaje"));
                ResultSet rsTextoMensaje = psTextoMensaje.executeQuery();
                rsTextoMensaje.next();
                Mensajes[contador][1] = rsTextoMensaje.getString(1);
                contador++;
            }

            return Mensajes;

        } catch (SQLException e) {
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }
        return null;
    }

    /**
     * Método que se encagra de crear un mensaje y enviarlo en un grupo
     *
     * @param usuario Usuario que envía el mensaje
     * @param text Texto del mensaje
     * @param id Identificador del grupo
     */
    public void enviarMensajeGrupo(String usuario, String text, int id) {
        

        try {
            Connection cnConnection = c.conexion();
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
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }

    }

    /**
     * Método que se encarga de borrar un grupo
     *
     * @param grupoActual Grupo que se va a borrar
     */
    public void borrarGrupo(int grupoActual) {
        
        try {
            Connection cnConnection = c.conexion();
            PreparedStatement psBorrarGrupo = cnConnection.prepareStatement("SELECT borrargrupo(?);");
            psBorrarGrupo.setInt(1, grupoActual);
            psBorrarGrupo.executeQuery();
            cnConnection.close();
        } catch (SQLException e) {
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }

    }

    /**
     * Método que se encarga de devolver si un usuario es Admin o no
     *
     * @param usuario Usuario actual
     * @param grupoActual Grupo en el que se va a mirar si es admin o no
     * @return Devuelve true en caso de que sí lo sea y false en caso contrario
     */
    public boolean esAdmin(String usuario, int grupoActual) {
        

        try {
            Connection cnConnection = c.conexion();
            PreparedStatement psesAdmin = cnConnection.prepareStatement("SELECT administra FROM usuariogrupo WHERE usuario=? AND id_grupo=?;");
            psesAdmin.setString(1, usuario);
            psesAdmin.setInt(2, grupoActual);
            ResultSet rsesAdmin = psesAdmin.executeQuery();
            rsesAdmin.next();
            cnConnection.close();
            return rsesAdmin.getBoolean("administra");
        } catch (SQLException e) {
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }

        return false;
    }

    /**
     * Método que se encarga de obtener los usuarios de un grupo
     *
     * @param grupoActual Grupo del cual se van a obtener los usuarios
     * @return Devuelve un array list con los usuarios del grupo
     */
    public ArrayList<String> cargarUsuariosGrupo(int grupoActual) {
        ArrayList<String> personasGrupo = new ArrayList<>();

        try {
            Connection cnConnection = c.conexion();
            PreparedStatement psUsuariosGrupo = cnConnection.prepareStatement("SELECT usuario FROM usuariogrupo WHERE id_grupo=?;");
            psUsuariosGrupo.setInt(1, grupoActual);
            ResultSet rsUsuariosGrupo = psUsuariosGrupo.executeQuery();

            while (rsUsuariosGrupo.next()) {
                personasGrupo.add(rsUsuariosGrupo.getString("usuario"));
            }

            cnConnection.close();
            return personasGrupo;
        } catch (SQLException e) {
            System.out.println("Algo ha salido mal con la conexión a la base de datos.");
        }

        return null;
    }

    /**
     * Método que se encarga de borrar un usuario de un grupo
     *
     * @param grupoActual Grupo del cual se va a borrar el Usuario
     * @param usuarioString Usuario a borrar del grupo
     * @throws SQLException 
     */
    public void borrarUsuarioGrupo(int grupoActual, String usuarioString) throws SQLException {

        Connection cnConnection = c.conexion();

        PreparedStatement psBorrarUsuarioGrupo = cnConnection.prepareStatement("DELETE FROM public.usuariogrupo WHERE usuario=? AND id_grupo=?");
        psBorrarUsuarioGrupo.setString(1, usuarioString);
        psBorrarUsuarioGrupo.setInt(2, grupoActual);
        psBorrarUsuarioGrupo.executeUpdate();
        cnConnection.close();


    }

    /**
     * Método que se encarga de añadir un Nuevo grupo
     *
     * @param usuarioActual Usuario administrador que se agregará por defecto
     * @param nombreGrupo Nombre del grupo que se va a crear
     */
    public void anyadirGrupo(String usuarioActual, String nombreGrupo) {
        try {
            Connection cnConnection = c.conexion();
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

    /**
     * Método que se encarga de añadir un usuario a un grupo
     *
     * @param id Identificador del grupo donde se va a añadir un usuario
     * @param usuario Usuario que se va a añadir
     * @param administra Parámetro en caso de que ese usuario sea administrador
     * o no
     */
    public void anyadirAGrupo(int id, String usuario, boolean administra) {
        try {
            Connection cnConnection = c.conexion();
            PreparedStatement psAnyadirUsuario = cnConnection.prepareStatement("INSERT INTO public.usuariogrupo(usuario,id_grupo,administra) VALUES (?,?,?);");
            psAnyadirUsuario.setString(1, usuario);
            psAnyadirUsuario.setInt(2, id);
            psAnyadirUsuario.setBoolean(3, administra);
            psAnyadirUsuario.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ese usuario no existe  o ya está en el grupo.");
        }

    }

}
