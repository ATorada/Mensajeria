package Visual;

import BBDD.DatosBBDD;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Ángel Torada
 */
public class PantallaPrincipal extends JDialog {

    //Variable globales como el usuario que inicia sesión, el grupo actual y el controlador de la base de datos
    private final String Usuario;
    private int GrupoActual;
    private DatosBBDD controlador;

    //JPanel
    private final JPanel contentPane;
    private final JPanel panelNombre;
    private JPanel panelUsuariosGrupo;
    private JPanel panelAmigos;
    private JPanel panelMensajes;
    private JPanel panelVacio;
    private JPanel panelGrupos;

    //JLabel
    private final JLabel lblAmigos;
    private final JLabel lblGrupos;
    private JLabel lblConverGrupo;
    private JLabel lblCerrar;
    private JTextArea textMensaje;

    //ArayLists de Mensajes, amigos, usuarios de un grupo y grupos
    private ArrayList<JPanel> amigosPanel = new ArrayList<JPanel>();
    private ArrayList<JPanel> mensajesPanel = new ArrayList<JPanel>();
    private ArrayList<JPanel> gruposPanel = new ArrayList<JPanel>();
    private ArrayList<JPanel> gruposUsuarios = new ArrayList<JPanel>();

    //JButtons
    private JButton btnEnviarMensaje;
    private JButton btnBorrarConver;
    private JButton btnLimpiarChat;
    private JButton btnBorrarGrupo;
    private JButton btnBorrarUsuariosGrupo;

    //JScrollPane
    private final JScrollPane scrollMensaje;

    /**
     * Constructor de la interfaz de la pantalla principal
     *
     * @param Usuario Usuario que ha iniciado sesión
     */
    public PantallaPrincipal(String Usuario) {
        setResizable(false);
        this.Usuario = Usuario;
        lblAmigos = new JLabel();
        lblGrupos = new JLabel();
        controlador = new DatosBBDD();
        setTitle("Pantalla Principal");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 694, 531);
        contentPane = new JPanel();
        contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
        contentPane.setBackground(new Color(210, 204, 224));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panelMensajes = new JPanel();
        panelMensajes.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        panelMensajes.setBackground(new Color(236, 230, 250));
        panelMensajes.setBounds(377, 58, 291, 340);
        panelMensajes.setLayout(null);

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

        panelVacio = new JPanel();
        panelVacio.setVisible(true);
        panelVacio.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        panelVacio.setBackground(new Color(236, 230, 250));
        panelVacio.setBounds(10, 90, 291, 391);
        contentPane.add(panelVacio);
        panelVacio.setLayout(null);

        panelAmigos = new JPanel();
        panelAmigos.setVisible(false);
        panelAmigos.setBounds(10, 58, 291, 100);
        panelAmigos.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        panelAmigos.setBackground(new Color(236, 230, 250));
        panelAmigos.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(291, 391);
        scrollPane.setLocation(10, 90);

        scrollPane.setViewportView(panelAmigos);
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

        JPanel panelOpciones = new JPanel();
        panelOpciones.setBounds(10, 43, 291, 46);
        contentPane.add(panelOpciones);
        panelOpciones.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        panelOpciones.setLayout(null);
        panelOpciones.setBackground(new Color(248, 248, 255));

        //Botón que carga los grupos del usuario
        ImageIcon iconGroup = new ImageIcon(Login.class.getResource("/img/grupo.png"));
        iconGroup.getImage().flush();
        JButton cargarGrupos = new JButton(iconGroup);
        cargarGrupos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setVisible(true);
                scrollPane.setViewportView(panelGrupos);
                panelGrupos.setVisible(true);
                panelAmigos.setVisible(false);
                panelVacio.setVisible(false);
                cargarGrupos();
            }
        });
        cargarGrupos.setFocusPainted(false);
        cargarGrupos.setBounds(66, 9, 46, 29);

        //Botón que carga las conversaciones del usuario
        ImageIcon iconUser = new ImageIcon(Login.class.getResource("/img/conver.png"));
        iconUser.getImage().flush();
        JButton cargarAmigos = new JButton(iconUser);
        cargarAmigos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setVisible(true);
                scrollPane.setViewportView(panelAmigos);
                panelAmigos.setVisible(true);
                panelVacio.setVisible(false);
                panelGrupos.setVisible(false);
                cargarAmigos();
            }
        });
        cargarAmigos.setFocusPainted(false);
        cargarAmigos.setBounds(10, 9, 46, 29);

        panelOpciones.add(cargarGrupos);
        panelOpciones.add(cargarAmigos);

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

        //Botón que borra el grupo actual
        ImageIcon iconBorrarGrupo = new ImageIcon(Login.class.getResource("/img/borrarConver.png"));
        iconBorrarGrupo.getImage().flush();
        btnBorrarGrupo = new JButton(iconBorrarGrupo);
        btnBorrarGrupo.setToolTipText("Borrar Grupo");
        btnBorrarGrupo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Borra el grupo en la base de datos
                controlador.borrarGrupo(GrupoActual);

                //Limpia la interfaz
                btnEnviarMensaje.setEnabled(false);
                btnBorrarGrupo.setVisible(false);
                btnBorrarUsuariosGrupo.setVisible(false);
                panelMensajes.removeAll();
                panelMensajes.revalidate();
                panelMensajes.repaint();
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

        //Botón que borra la conversación actual
        ImageIcon iconBorrarConver = new ImageIcon(Login.class.getResource("/img/borrarConver.png"));
        iconBorrarConver.getImage().flush();
        btnBorrarConver = new JButton(iconBorrarConver);
        btnBorrarConver.setToolTipText("Borrar Conversación");
        btnBorrarConver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Borra la conversación en la base de datos
                controlador.borrarConversacion(Usuario, lblConverGrupo.getText());

                //Limpia la interfaz
                btnEnviarMensaje.setEnabled(false);
                btnBorrarConver.setVisible(false);
                btnLimpiarChat.setVisible(false);
                btnBorrarUsuariosGrupo.setVisible(false);
                btnBorrarGrupo.setVisible(false);
                panelMensajes.removeAll();
                panelMensajes.revalidate();
                panelMensajes.repaint();
                textMensaje.setEnabled(false);
                lblConverGrupo.setText("");
                lblCerrar.setIcon(null);
            }
        });
        btnBorrarConver.setVisible(false);
        btnBorrarConver.setFocusPainted(false);
        btnBorrarConver.setBounds(100, 9, 46, 29);
        panelNombre.add(btnBorrarConver);

        //Botón que borra los mensajes de una conversación
        ImageIcon iconBorrarMensajes = new ImageIcon(Login.class.getResource("/img/borrarMensajes.png"));
        iconBorrarMensajes.getImage().flush();
        btnLimpiarChat = new JButton(iconBorrarMensajes);
        btnLimpiarChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Borra los mensajes en la base de datos
                controlador.borrarMensajes(Usuario, lblConverGrupo.getText());

                //Limpia el panelMensajes
                panelMensajes.removeAll();
                panelMensajes.revalidate();
                panelMensajes.repaint();
            }
        });
        btnLimpiarChat.setToolTipText("Borrar Mensajes");
        btnLimpiarChat.setVisible(false);
        btnLimpiarChat.setFocusPainted(false);
        btnLimpiarChat.setBounds(160, 9, 46, 29);
        panelNombre.add(btnLimpiarChat);

        //Botón que muestra los usuarios para borrar de un grupo
        ImageIcon iconBorrarUsuario = new ImageIcon(Login.class.getResource("/img/borrarUsuarioGrupo.png"));
        iconBorrarUsuario.getImage().flush();
        btnBorrarUsuariosGrupo = new JButton(iconBorrarUsuario);
        btnBorrarUsuariosGrupo.setToolTipText("Borrar Mensajes");
        btnBorrarUsuariosGrupo.setVisible(false);
        btnBorrarUsuariosGrupo.setFocusPainted(false);
        btnBorrarUsuariosGrupo.setBounds(160, 9, 46, 29);
        btnBorrarUsuariosGrupo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarUsuariosGrupo();
            }
        });
        panelNombre.add(btnBorrarUsuariosGrupo);

        //Botón que envía un mensaje a un grupo o a una conversación
        ImageIcon iconSend = new ImageIcon(Login.class.getResource("/img/send.png"));
        iconSend.getImage().flush();
        btnEnviarMensaje = new JButton(iconSend);
        btnEnviarMensaje.setEnabled(false);
        btnEnviarMensaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textMensaje.getText().trim().isEmpty()) {
                    if (controlador.existeConversacion(Usuario, lblConverGrupo.getText())) {
                        controlador.enviarMensajeConver(Usuario, textMensaje.getText(), lblConverGrupo.getText());
                        String[][] Mensajes = controlador.cargarMensajesConver(Usuario, lblConverGrupo.getText());
                        panelMensajes.removeAll();
                        mostrarMensajes(Mensajes);
                        panelMensajes.revalidate();
                        panelMensajes.repaint();
                    } else {
                        controlador.enviarMensajeGrupo(Usuario, textMensaje.getText(), GrupoActual);
                        String[][] Mensajes = controlador.cargarMensajesGrupo(GrupoActual);
                        panelMensajes.removeAll();
                        mostrarMensajes(Mensajes);
                        panelMensajes.revalidate();
                        panelMensajes.repaint();
                    }
                }
            }
        });
        btnEnviarMensaje.setBounds(629, 432, 39, 47);
        contentPane.add(btnEnviarMensaje);

        scrollMensaje = new JScrollPane();
        scrollMensaje.setSize(291, 340);
        scrollMensaje.setLocation(377, 90);
        scrollMensaje.setViewportView(panelMensajes);
        contentPane.add(scrollMensaje);

        ImageIcon iconSalir = new ImageIcon(Login.class.getResource("/img/salir.png"));
        iconSalir.getImage().flush();
        JButton cerrarSesion = new JButton(iconSalir);
        cerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cerrarSesion.setBounds(318, 11, 47, 28);
        contentPane.add(cerrarSesion);

    }

    /**
     * Método que carga los grupos en la interfaz
     */
    protected void cargarGrupos() {
        //Obtiene los grupos de la base de datos
        String[][] grupos = controlador.ObtenerGrupos(Usuario);

        //Inicializa los JPanel de cada grupo
        gruposPanel = new ArrayList<>();

        //Inicializa el botón de crear grupos
        InicializarAnyadirGrupo();

        //Altura del primer JPanel
        int altura = 55;

        //Bucle por la cantidad de grupos que existen
        for (int i = 0; i < grupos.length; i++) {
            //Inicializa i como un JPanel
            gruposPanel.add(new JPanel());
            gruposPanel.get(i).setBorder(new LineBorder(new Color(204, 204, 255), 2));
            gruposPanel.get(i).setBounds(71, altura, 164, 33);
            gruposPanel.get(i).setBackground(Color.WHITE);
            gruposPanel.get(i).setLayout(null);
            panelGrupos.add(gruposPanel.get(i));

            //Le añade el nombre del grupo a un Label del JPanel
            JLabel nombreGrupo = new JLabel("");
            nombreGrupo.setText(grupos[i][1]);
            nombreGrupo.setBounds(23, 9, 109, 14);
            nombreGrupo.setHorizontalAlignment(SwingConstants.CENTER);
            gruposPanel.get(i).add(nombreGrupo);
            //Lo añade junto a su ID a una variable que se usa más tarde en los botones puesto que estos no pueden acceder a grupos
            String nombre = grupos[i][1];
            int id = Integer.valueOf(grupos[i][0]);

            //Se crea el label que sirve como botón para cargar el grupo y ver/enviar mensajes
            JLabel abrirMensajes = new JLabel("");
            ImageIcon iconDM = new ImageIcon(Login.class.getResource("/img/DM.png"));
            iconDM.getImage().flush();
            abrirMensajes.setIcon(iconDM);
            abrirMensajes.setBounds(125, 9, 25, 16);
            abrirMensajes.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    scrollMensaje.setViewportView(panelMensajes);
                    panelMensajes.setVisible(true);
                    //Se establece el grupo actual en una variable global
                    GrupoActual = id;

                    //Comprueba si es administrador para mostrar las opciones respectivas de un admin.
                    if (controlador.esAdmin(Usuario, GrupoActual)) {
                        btnBorrarGrupo.setVisible(true);
                        btnBorrarUsuariosGrupo.setVisible(true);
                    } else {
                        btnBorrarGrupo.setVisible(false);
                        btnBorrarUsuariosGrupo.setVisible(false);
                    }

                    lblConverGrupo.setText(nombre);
                    textMensaje.setEnabled(true);
                    btnEnviarMensaje.setEnabled(true);
                    btnBorrarConver.setVisible(false);
                    btnLimpiarChat.setVisible(false);

                    //Crea el botón de cerrar conversación
                    ImageIcon iconCerrar = new ImageIcon(Login.class.getResource("/img/cerrar.png"));
                    iconCerrar.getImage().flush();
                    lblCerrar.setIcon(iconCerrar);
                    lblCerrar.setVisible(true);

                    lblCerrar.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            //Limpia la interfaz
                            scrollMensaje.setViewportView(panelMensajes);
                            panelMensajes.setVisible(true);
                            btnEnviarMensaje.setEnabled(false);
                            btnBorrarConver.setVisible(false);
                            btnLimpiarChat.setVisible(false);
                            btnBorrarUsuariosGrupo.setVisible(false);
                            btnBorrarGrupo.setVisible(false);
                            panelMensajes.setPreferredSize(new Dimension(0, 330));
                            panelMensajes.removeAll();
                            panelMensajes.revalidate();
                            panelMensajes.repaint();
                            textMensaje.setEnabled(false);
                            lblConverGrupo.setText("");
                            lblCerrar.setIcon(null);
                        }
                    });

                    //Obtiene los mensajes de un grupo y los carga
                    String[][] Mensajes = controlador.cargarMensajesGrupo(id);
                    if (Mensajes != null) {
                        panelMensajes.removeAll();
                        mostrarMensajes(Mensajes);
                        panelMensajes.revalidate();
                        panelMensajes.repaint();
                    }
                }

            });

            //Se crea el Label que permite añadir un usuario al grupo
            JLabel añadirUsuario = new JLabel("");
            ImageIcon iconAnyadir = new ImageIcon(Login.class.getResource("/img/añadirAmigo.png"));
            iconAnyadir.getImage().flush();
            añadirUsuario.setIcon(iconAnyadir);
            añadirUsuario.setBounds(5, 9, 25, 16);
            añadirUsuario.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        String amigo = JOptionPane.showInputDialog("Introduzca el usuario a añadir");
                        if (amigo != null) {
                            if (controlador.esAdmin(Usuario, GrupoActual)) {

                                int administra = JOptionPane.showConfirmDialog(null, "Es administrador?", "Administra", JOptionPane.YES_NO_OPTION);
                                if (administra == JOptionPane.YES_OPTION) {
                                    controlador.anyadirAGrupo(id, amigo, true);
                                } else if (administra == JOptionPane.NO_OPTION) {
                                    controlador.anyadirAGrupo(id, amigo, false);
                                }
                            } else {
                                controlador.anyadirAGrupo(id, amigo, false);
                            }
                            if (panelUsuariosGrupo.isShowing()) {
                                panelUsuariosGrupo.removeAll();
                                cargarUsuariosGrupo();
                                panelUsuariosGrupo.revalidate();
                                panelUsuariosGrupo.repaint();
                            }
                        }

                    } catch (HeadlessException exc) {

                    }

                }
            });

            //Label que permite al usuario salir de un grupo, si es el último le avisará de que tiene que borrar el grupo
            JLabel salirGrupo = new JLabel("");
            ImageIcon iconBorrar = new ImageIcon(Login.class.getResource("/img/borrar.png"));
            iconBorrar.getImage().flush();
            salirGrupo.setIcon(iconBorrar);
            salirGrupo.setBounds(37, 9, 25, 16);
            salirGrupo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
						controlador.borrarUsuarioGrupo(id, Usuario);
	                    scrollMensaje.setViewportView(panelMensajes);
	                    panelMensajes.setVisible(true);
	                    btnEnviarMensaje.setEnabled(false);
	                    btnBorrarConver.setVisible(false);
	                    btnLimpiarChat.setVisible(false);
	                    btnBorrarUsuariosGrupo.setVisible(false);
	                    btnBorrarGrupo.setVisible(false);
	                    panelMensajes.setPreferredSize(new Dimension(0, 330));
	                    panelUsuariosGrupo.setVisible(false);
	                    panelUsuariosGrupo.removeAll();
	                    panelUsuariosGrupo.revalidate();
	                    panelUsuariosGrupo.repaint();
	                    panelMensajes.removeAll();
	                    panelMensajes.revalidate();
	                    panelMensajes.repaint();
	                    panelGrupos.removeAll();
	                    cargarGrupos();
	                    panelGrupos.revalidate();
	                    panelGrupos.repaint();
	                    textMensaje.setEnabled(false);
	                    lblConverGrupo.setText("");
	                    lblCerrar.setIcon(null);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Para borrar el último usuario debes borrar el grupo.");
					}

                }
            });

            //Se añaden los labels
            gruposPanel.get(i).add(abrirMensajes);
            gruposPanel.get(i).add(añadirUsuario);
            gruposPanel.get(i).add(salirGrupo);

            //Se incrementa la altura
            altura += 36;
        }

        //Se le pone un tamaño preferido para el ScrollPane
        panelGrupos.setPreferredSize(new Dimension(0, altura));
    }

    //Método que se encarga de cargar en la interfaz los usuarios de un grupo
    private void cargarUsuariosGrupo() {
        //Cambia el panelMensajes visible y el View del ScrollPane
        panelMensajes.setVisible(false);
        scrollMensaje.setViewportView(panelUsuariosGrupo);
        panelUsuariosGrupo.setVisible(true);

        //Obtiene los usuarios del grupo de la base de datos
        ArrayList<String> personasGrupo = controlador.cargarUsuariosGrupo(GrupoActual);
        gruposUsuarios = new ArrayList<>();

        //Establece la primera altura
        int altura = 30;
        //For que crea un JPanel por cada usuario del grupo
        for (int i = 0; i < personasGrupo.size(); i++) {
            //Inicializa el JPanel
            gruposUsuarios.add(new JPanel());
            gruposUsuarios.get(i).setBackground(Color.WHITE);
            gruposUsuarios.get(i).setBounds(71, altura, 164, 33);
            gruposUsuarios.get(i).setBorder(new LineBorder(new Color(204, 204, 255), 2));
            panelUsuariosGrupo.add(gruposUsuarios.get(i));
            gruposUsuarios.get(i).setLayout(null);

            //Guarda su nombre una variable usada en el botón posteriormente
            String usuarioString = personasGrupo.get(i);

            //Establece un Label con el nombre del usuario
            JLabel nombreUsuario = new JLabel("");
            nombreUsuario.setText(personasGrupo.get(i));
            nombreUsuario.setBounds(23, 9, 109, 14);
            nombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
            gruposUsuarios.get(i).add(nombreUsuario);

            //Establece el Label con función de borrar al Usuario del grupo
            JLabel borrarUsuario = new JLabel("");
            ImageIcon iconBorrar = new ImageIcon(Login.class.getResource("/img/borrar.png"));
            iconBorrar.getImage().flush();
            borrarUsuario.setIcon(iconBorrar);
            borrarUsuario.setBounds(5, 9, 25, 16);
            borrarUsuario.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    try {
						//Borra el usuario de la base de datos
						controlador.borrarUsuarioGrupo(GrupoActual, usuarioString);
						//En caso de que sea él mismo limpia la interfaz en caso de que no solo la lista de usuarios
						if (usuarioString.equals(Usuario)) {
						    scrollMensaje.setViewportView(panelMensajes);
						    panelMensajes.setVisible(true);
						    panelUsuariosGrupo.setVisible(false);
						    btnEnviarMensaje.setEnabled(false);
						    btnBorrarConver.setVisible(false);
						    btnLimpiarChat.setVisible(false);
						    btnBorrarUsuariosGrupo.setVisible(false);
						    btnBorrarGrupo.setVisible(false);
						    panelMensajes.setPreferredSize(new Dimension(0, 330));
						    panelMensajes.removeAll();
						    panelMensajes.revalidate();
						    panelMensajes.repaint();
						    textMensaje.setEnabled(false);
						    lblConverGrupo.setText("");
						    lblCerrar.setIcon(null);
						} else {
						    panelUsuariosGrupo.removeAll();
						    cargarUsuariosGrupo();
						    panelUsuariosGrupo.revalidate();
						    panelUsuariosGrupo.repaint();
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Para borrar el último usuario debes borrar el grupo.");
					}

                }
            });
            gruposUsuarios.get(i).add(borrarUsuario);
            //Aumenta la altura
            altura += 36;
        }
        //Establece el tamaño preferido del panelMensajes para el ScrollPane
        panelUsuariosGrupo.setPreferredSize(new Dimension(0, altura));

    }

    //Método que se encarga de cargar los amigos en la interfaz
    private void cargarAmigos() {
        //Cambia la View del ScrollPane
        scrollMensaje.setViewportView(panelMensajes);

        //Se obtienen los amigos del usuario desde la base de datos
        ArrayList<String> amigos = controlador.ObtenerAmigos(Usuario);
        amigosPanel = new ArrayList<>();

        //Inicializa el botón de añadir amigos.
        InicializarAnyadirAmigos();

        //Se establece la altura del primer JPanel
        int altura = 55;
        //For por cada amigo del usuario
        for (int i = 0; i < amigos.size(); i++) {

            //Inicializa el JPane
            amigosPanel.add(new JPanel());
            amigosPanel.get(i).setBackground(Color.WHITE);
            amigosPanel.get(i).setBounds(71, altura, 164, 33);
            amigosPanel.get(i).setBorder(new LineBorder(new Color(204, 204, 255), 2));
            panelAmigos.add(amigosPanel.get(i));
            amigosPanel.get(i).setLayout(null);

            //Establece el nombre del amigo en un Label y lo guarda en una variable
            JLabel nombreAmigo = new JLabel("");
            nombreAmigo.setText(amigos.get(i));
            nombreAmigo.setBounds(23, 9, 109, 14);
            nombreAmigo.setHorizontalAlignment(SwingConstants.CENTER);
            amigosPanel.get(i).add(nombreAmigo);
            String usuarioString = amigos.get(i);

            //Establece un Label como botón para enviar mensajes
            JLabel enviarMensaje = new JLabel("");
            ImageIcon iconDM = new ImageIcon(Login.class.getResource("/img/DM.png"));
            iconDM.getImage().flush();
            enviarMensaje.setIcon(iconDM);
            enviarMensaje.setBounds(125, 9, 25, 16);
            enviarMensaje.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //Establece la interfaz
                    lblConverGrupo.setText(usuarioString);
                    textMensaje.setEnabled(true);
                    btnEnviarMensaje.setEnabled(true);
                    btnBorrarConver.setVisible(true);
                    btnLimpiarChat.setVisible(true);

                    //Establece el botón de cerrar conver
                    ImageIcon iconCerrar = new ImageIcon(Login.class.getResource("/img/cerrar.png"));
                    iconDM.getImage().flush();
                    lblCerrar.setIcon(iconCerrar);
                    lblCerrar.setVisible(true);
                    lblCerrar.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            //Limpia la interfaz
                            btnEnviarMensaje.setEnabled(false);
                            btnBorrarConver.setVisible(false);
                            btnLimpiarChat.setVisible(false);
                            panelMensajes.setPreferredSize(new Dimension(0, 330));
                            panelMensajes.removeAll();
                            panelMensajes.revalidate();
                            panelMensajes.repaint();
                            textMensaje.setEnabled(false);
                            lblConverGrupo.setText("");
                            lblCerrar.setIcon(null);

                        }
                    });
                    //Si existe la conversación carga los mensajes, sino existe la crea
                    if (controlador.existeConversacion(Usuario, usuarioString)) {
                        //Obtiene los mensajes de la conversación desde la base de datos
                        String[][] Mensajes = controlador.cargarMensajesConver(Usuario, usuarioString);
                        //Si no es nulo entonces los carga
                        if (Mensajes != null) {
                            panelMensajes.removeAll();
                            mostrarMensajes(Mensajes);
                            panelMensajes.revalidate();
                            panelMensajes.repaint();
                            btnLimpiarChat.setVisible(true);
                            btnBorrarUsuariosGrupo.setVisible(true);
                        } else {
                            System.out.println("No existe la conversación");
                        }
                    } else {
                        controlador.crearConversacion(Usuario, usuarioString);
                    }

                }

            });
            amigosPanel.get(i).add(enviarMensaje);

            //Crea el Label que hace la función de botón de borrar un amigo
            JLabel borrarAmigo = new JLabel("");
            ImageIcon iconBorrar = new ImageIcon(Login.class.getResource("/img/borrar.png"));
            iconBorrar.getImage().flush();
            borrarAmigo.setIcon(iconBorrar);
            borrarAmigo.setBounds(5, 9, 25, 16);
            borrarAmigo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //Borra la amistad de la base de datos
                    controlador.borrarAmigo(Usuario, usuarioString);

                    //Recarga la interfaz
                    panelAmigos.removeAll();
                    cargarAmigos();
                    panelAmigos.revalidate();
                    panelAmigos.repaint();
                }
            });
            amigosPanel.get(i).add(borrarAmigo);

            //Aumenta la altura
            altura += 36;
        }
        //Establece el tamaño preferido del panelMensajes para el ScrollPane
        panelAmigos.setPreferredSize(new Dimension(0, altura));
    }

    /**
     * Método que se encarga de cargar los mensajes pasados por parámetro en la
     * interfaz
     *
     * @param mensajes
     */
    protected void mostrarMensajes(String[][] mensajes) {
        mensajesPanel = new ArrayList<>();
        int altura = 10;

        for (int i = 0; i < mensajes.length; i++) {

            mensajesPanel.add(new JPanel());
            mensajesPanel.get(i).setBackground(Color.WHITE);
            mensajesPanel.get(i).setBounds(71, altura, 164, 50);
            mensajesPanel.get(i).setLayout(null);

            if (mensajes[i][0].equals(Usuario)) {
                mensajesPanel.get(i).setBorder(new TitledBorder(new LineBorder(new Color(230, 230, 250), 2, true), Usuario, TitledBorder.LEADING, TitledBorder.TOP, null, null));
            } else {
                mensajesPanel.get(i).setBorder(new TitledBorder(new LineBorder(new Color(230, 230, 250), 2, true), mensajes[i][0], TitledBorder.LEADING, TitledBorder.TOP, null, null));
            }

            JLabel mensaje = new JLabel("");
            mensaje.setText(mensajes[i][1]);
            mensaje.setBounds(23, 22, 109, 14);
            mensaje.setHorizontalAlignment(SwingConstants.CENTER);
            mensajesPanel.get(i).add(mensaje);
            panelMensajes.add(mensajesPanel.get(i));
            altura += 55;
        }
        panelMensajes.setPreferredSize(new Dimension(0, altura));

    }

    //Método que se encarga de inicializar el botón de añadir amigos
    private void InicializarAnyadirAmigos() {
        //Limpia la interfaz
        panelAmigos.removeAll();

        //Establece el texto de un Label a Amigos
        lblAmigos.setText("Amigos");
        lblAmigos.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        lblAmigos.setBounds(127, 20, 59, 21);
        panelAmigos.add(lblAmigos);

        //Crea el botón
        JButton btnAnyadirAmigo = new JButton();
        btnAnyadirAmigo.setBounds(170, 11, 46, 38);
        ImageIcon iconAnyadir = new ImageIcon(Login.class.getResource("/img/añadirAmigo.png"));
        iconAnyadir.getImage().flush();
        btnAnyadirAmigo.setIcon(iconAnyadir);
        btnAnyadirAmigo.setBorderPainted(false);
        btnAnyadirAmigo.setFocusPainted(false);
        btnAnyadirAmigo.setContentAreaFilled(false);
        btnAnyadirAmigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //El método pregunta el nombre del usuario a añadir y lo añade en la base de datos
                try {
                    //Pregunta y añade el usuario
                    String amigo = JOptionPane.showInputDialog("Introduzca el usuario a añadir");
                    controlador.anyadirAmigo(Usuario, amigo);

                    //Recarga la interfaz
                    panelAmigos.removeAll();
                    cargarAmigos();
                    panelAmigos.revalidate();
                    panelAmigos.repaint();
                } catch (HeadlessException exc) {

                }
            }
        });
        panelAmigos.add(btnAnyadirAmigo);

        //Re hace la interfaz
        panelAmigos.revalidate();
        panelAmigos.repaint();
    }

    //Método que se encarga de inicializar el botón de añadir grupo
    private void InicializarAnyadirGrupo() {
        //Limpia la interfaz
        panelGrupos.removeAll();

        //Establece el texto de un label a Grupos
        lblGrupos.setText("Grupos");
        lblGrupos.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        lblGrupos.setBounds(127, 20, 59, 21);
        panelGrupos.add(lblGrupos);

        //Crea el botón para crear un grupo
        JButton btnCrearGrupo = new JButton();
        btnCrearGrupo.setBounds(170, 11, 46, 38);
        ImageIcon iconAnyadir = new ImageIcon(Login.class.getResource("/img/añadirAmigo.png"));
        iconAnyadir.getImage().flush();
        btnCrearGrupo.setBorderPainted(false);
        btnCrearGrupo.setFocusPainted(false);
        btnCrearGrupo.setContentAreaFilled(false);
        btnCrearGrupo.setIcon(iconAnyadir);
        btnCrearGrupo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Método que pregunta el nombre del grupo y lo añade a la Base de datos
                try {
                    //Pregunta, comprueba y añade el grupo
                    String nombre = JOptionPane.showInputDialog("Introduzca el nombre del grupo");
                    if (nombre != null) {
                        controlador.anyadirGrupo(Usuario, nombre);

                        //Recarga la interfaz
                        panelGrupos.removeAll();
                        cargarGrupos();
                        panelGrupos.revalidate();
                        panelGrupos.repaint();

                    }

                } catch (HeadlessException exc) {

                }
            }
        });
        panelGrupos.add(btnCrearGrupo);

        //Re hace la interfaz
        panelGrupos.revalidate();
        panelGrupos.repaint();
    }
}
