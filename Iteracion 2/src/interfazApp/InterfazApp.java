/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import negocio.Habitacion;
import negocio.HotelAndes;
import negocio.Plan;
import negocio.Reserva;
import negocio.Servicio;
import negocio.Sirven;
import negocio.Tipo;
import negocio.Usuario;


/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private HotelAndes hotel;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        hotel = new HotelAndes (tableConfig);
//        hotel = new HotelAndes ();
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
    
    public void adicionarTipo()
    {
    	try
    	{
    		String text="Id de tipo en uso, no usar los id's siguientes";
    		for(int i=0; i<hotel.darTipos().size();i++)
    		{
    			text+=","+hotel.darTipos().get(i).getId();
    		}
    		panelDatos.actualizarInterfaz(text);
    		String tipo = JOptionPane.showInputDialog(this, "Tipo de usuario");
    		String idTipo=JOptionPane.showInputDialog(this, "Id Tipo");
    		
    		long id=Long.parseLong(idTipo);
        	if(tipo != null)
        	{
        		Tipo ti = hotel.adicionarTipo(tipo,id);
        		if(ti == null)
        		{
        			throw new Exception("No se pudo agregar el tipo de usuario " + tipo);
        		}
        		
        		String rta = "Agregar nuevo tipo de usuario \n\n";
        		rta += "Tipo de usuario " + tipo  + " agregado exitosamente \n";
        		rta += "Operacion terminada";
        		panelDatos.actualizarInterfaz(rta);
        	}
        	else
        	{
        		panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
        	}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		panelDatos.actualizarInterfaz(generarMensajeError(e));
    	}
    	
    }
   
    
    
    public void adicionarUsuario()
    {
    	try
    	{
    		String idUsuario = JOptionPane.showInputDialog(this, "Identificacion del usuario");
    		
    		
    		if(idUsuario != null)
    		{
    			String tipoId = JOptionPane.showInputDialog(this, "Tipo de identificacion");
    			String nombre = JOptionPane.showInputDialog(this, "Nombre del usuario");
        		String correo = JOptionPane.showInputDialog(this, "Correo de usuario");
        		String tipoUsuario = JOptionPane.showInputDialog(this, "Tipo de usuario");
    			int identificacion = Integer.parseInt(idUsuario);
    			int idTipo = Integer.parseInt(tipoUsuario);
    			Usuario u = hotel.adicionarUsuario(nombre, identificacion, tipoId, correo, idTipo);
    			if (u == null)
    			{
    				throw new Exception ("No se pudo crear el usuario con id " + identificacion);
    			}
    			String rta = "Adicionar nuevo usuario \n\n";
    			rta += "Usuario "+ identificacion  + " añadido exitosamente. \n";
    			rta += "Operación terminada";
    			panelDatos.actualizarInterfaz(rta);
    		}
    		else
        	{
        		panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
        	}
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    	
    	
    }
    



	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
//		try 
//		{
//    		// Ejecución de la demo y recolección de los resultados
//			long eliminados [] = parranderos.limpiarParranderos();
//			
//			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
//			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
//			resultado += eliminados [0] + " Gustan eliminados\n";
//			resultado += eliminados [1] + " Sirven eliminados\n";
//			resultado += eliminados [2] + " Visitan eliminados\n";
//			resultado += eliminados [3] + " Bebidas eliminadas\n";
//			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
//			resultado += eliminados [5] + " Bebedores eliminados\n";
//			resultado += eliminados [6] + " Bares eliminados\n";
//			resultado += "\nLimpieza terminada";
//   
//			panelDatos.actualizarInterfaz(resultado);
//		} 
//		catch (Exception e) 
//		{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Hotel Andes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Luis Ruiz - David Fonseca\n";
		resultado += " * Abril de 2019\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    /**
     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una líea para cada tipo de bebida recibido
     */
    private String listarTiposBebida(List lista) 
    {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
//        for (VOTipoBebida tb : lista)
//        {
//        	resp += i++ + ". " + tb.toString() + "\n";
//        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
     */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazApp interfaz = new InterfazApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
    
    
    
    ///lo mio///
    
    public void adicionarReserva()
    {
    	try
    	{
    		
    		String id = JOptionPane.showInputDialog(this, "Identificacion de la Reserva");
    		
    		
    		if(id != null)
    		{
    			String capacidad = JOptionPane.showInputDialog(this, "Cantidad Personas");
        		String costo = JOptionPane.showInputDialog(this, "Costo de la reserva");
        		String ini=JOptionPane.showInputDialog(this,"Hora Inicio");
        		String fini=JOptionPane.showInputDialog(this,"Hora Fin");
        		String descripcion = JOptionPane.showInputDialog(this, "Descripcion");
        		String nombre=JOptionPane.showInputDialog(this,"Esta registrado true T false F");
        		String pagoo=JOptionPane.showInputDialog(this,"Esta pago T o F");
        		String plan=JOptionPane.showInputDialog(this,"Id del plan");
        		String identificacion=JOptionPane.showInputDialog(this,"Identificacion del usuario de la reserva");
        		long idplan=Long.parseLong(plan);
        		long idusuario=Long.parseLong(identificacion);
        		long iden=Long.parseLong(id);
    			long inicio=Long.parseLong(ini);
    			long fin=Long.parseLong(fini);
    			int capa = Integer.parseInt(capacidad);
    			double cos=Double.parseDouble(costo);
    			Reserva u = hotel.adicionarReserva(iden, capa, inicio, fin, cos, descripcion, nombre, pagoo, idplan, idusuario);
    			if (u == null)
    			{
    				throw new Exception ("No se pudo crear el servicio con id " + id);
    			}
    			String rta = "Adicionar nuevo servicio \n\n";
    			rta += "servicio "+ id  + " añadido exitosamente. \n";
    			rta += "Operación terminada";
    			panelDatos.actualizarInterfaz(rta);
    		}
    		else
        	{
        		panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
        	}
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    
    public void adicionarServicio()
    {
    	try
    	{
    		String id = JOptionPane.showInputDialog(this, "Identificacion del Servicio");
    		
    		
    		if(id != null)
    		{
    			String capacidad = JOptionPane.showInputDialog(this, "Capacidad del Servicio");
        		String costo = JOptionPane.showInputDialog(this, "Costo del Servicio");
        		String ini=JOptionPane.showInputDialog(this,"Hora Apertura");
        		String fini=JOptionPane.showInputDialog(this,"Hora cierre");
        		String descripcion = JOptionPane.showInputDialog(this, "Descripcion");
        		String nombre=JOptionPane.showInputDialog(this,"Nombre del servicio");
        		long iden=Long.parseLong(id);
    			long inicio=Long.parseLong(ini);
    			long fin=Long.parseLong(fini);
    			int capa = Integer.parseInt(capacidad);
    			double cos=Double.parseDouble(costo);
    			Servicio u = hotel.adicionarServicio(iden,capa, inicio, fin, cos, nombre, descripcion);
    			if (u == null)
    			{
    				throw new Exception ("No se pudo crear el servicio con id " + id);
    			}
    			String rta = "Adicionar nuevo servicio \n\n";
    			rta += "servicio "+ id  + " añadido exitosamente. \n";
    			rta += "Operación terminada";
    			panelDatos.actualizarInterfaz(rta);
    		}
    		else
        	{
        		panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
        	}
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    
    
    
    public void adicionarHabitacion()
    {
    	try
    	{
    		String numeroHabitacion = JOptionPane.showInputDialog(this, "Identificacion de la habitacion");
    		
    		
    		if(numeroHabitacion != null)
    		{
    			String capacidad = JOptionPane.showInputDialog(this, "Capacidad de habitacion");
        		String costo = JOptionPane.showInputDialog(this, "Costo de habitacion");
        		String descripcion = JOptionPane.showInputDialog(this, "Descripcion de habitacion");
        		String disponible=JOptionPane.showInputDialog(this,"Disponibilidad T si esta diponible o F si no lo esta");
        		if(disponible.length()>1)
        		{
        			throw new Exception("Tama�o Incorrecto en disponibilidad, solo se puede Usar T o F");
        		}
    			int num = Integer.parseInt(numeroHabitacion);
    			int capa = Integer.parseInt(capacidad);
    			int cos=Integer.parseInt(costo);
    			Habitacion u = hotel.adicionarHabitacion(capa, num, cos, descripcion, disponible);
    			if (u == null)
    			{
    				throw new Exception ("No se pudo crear la habitacion con id " + numeroHabitacion);
    			}
    			String rta = "Adicionar nueva habitacion \n\n";
    			rta += "habitacion "+ numeroHabitacion  + " añadido exitosamente. \n";
    			rta += "Operación terminada";
    			panelDatos.actualizarInterfaz(rta);
    		}
    		else
        	{
        		panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
        	}
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    public void adicionarSirven()
    {
    	try
    	{
    		String text="Servicios : ";
    		for(int i=0; i<hotel.darServicios().size();i++)
    		{
    			text+="identificacion: "+hotel.darServicios().get(i).getid()+" Nombre: "+hotel.darServicios().get(i).getNombre();
    		}
    		panelDatos.actualizarInterfaz(text);
    		String idServicio = JOptionPane.showInputDialog(this, "Id del servicio a cargar");
    		String numeroHabitacion=JOptionPane.showInputDialog(this, "Numero de habitacion para cargar servicio");
    		String fecha=JOptionPane.showInputDialog(this,"Fecha de uso del servicio");
    		long ids=Long.parseLong(idServicio);
    		int numHabitacion=Integer.parseInt(numeroHabitacion);
    		long uso=Long.parseLong(fecha);

        	if(idServicio != null)
        	{
        		Sirven ti = hotel.adicionarSirven(ids, numHabitacion,uso);
        		if(ti == null)
        		{
        			throw new Exception("No se pudo agregar el Servicio a la habitacion" + numHabitacion);
        		}
        		
        		String rta = "Agregar nuevo servicio a habitacion \n\n";
        		rta += "servicio a habitacion " + ti  + " agregado exitosamente \n";
        		rta += "Operacion terminada";
        		panelDatos.actualizarInterfaz(rta);
        	}
        	else
        	{
        		panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
        	}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		panelDatos.actualizarInterfaz(generarMensajeError(e));
    	}
    	
    }
    	
    
    public void registrarLlegada()
    {
    	try
    	{
    		
    		String idR = JOptionPane.showInputDialog(this, "Id de reserva a registrar la llegada");
    		
    		long id=Long.parseLong(idR);
        	if(idR != null)
        	{
        		long ti = hotel.registrarLlegada(id);
        		if(ti == 0)
        		{
        			throw new Exception("No se pudo registrar la llegada " + idR);
        		}
        		
        		String rta = "Registrar una llegada de un usuario a una reserva \n\n";
        		rta += "Llegada " + ti  + " registrada exitosamente \n";
        		rta += "Operacion terminada";
        		panelDatos.actualizarInterfaz(rta);
        	}
        	else
        	{
        		panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
        	}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		panelDatos.actualizarInterfaz(generarMensajeError(e));
    	}
    	
    
    }
    
    
    public void registrarSalida()
    {
    	try
    	{
    		
    		String idR = JOptionPane.showInputDialog(this, "Id de reserva a registrar la salida");
    		
    		long id=Long.parseLong(idR);
        	if(idR != null)
        	{
        		long ti = hotel.registrarSalida(id);
        		if(ti == 0)
        		{
        			throw new Exception("No se pudo registrar la salida " + idR);
        		}
        		
        		String rta = "Registrar una salida de un usuario a una reserva \n\n";
        		rta += "Salida " + ti  + " registrada exitosamente \n";
        		rta += "Operacion terminada";
        		panelDatos.actualizarInterfaz(rta);
        	}
        	else
        	{
        		panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
        	}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		panelDatos.actualizarInterfaz(generarMensajeError(e));
    	}
    	
    
    }
    	
    
    
    
    public void adicionarPlan()
    {
    	try
    	{
    		String text="Id de plan en uso, no usar los id's siguientes";
    		for(int i=0; i<hotel.darPlanes().size();i++)
    		{
    			text+=","+hotel.darPlanes().get(i).geId();
    		}
    		panelDatos.actualizarInterfaz(text);
    		String nombre = JOptionPane.showInputDialog(this, "Nombre de plan");
    		String descripcion=JOptionPane.showInputDialog(this, "descripcion de plan");
    		String idTipo=JOptionPane.showInputDialog(this,"id del plan");
    		
    		long id=Long.parseLong(idTipo);
        	if(idTipo != null)
        	{
        		Plan ti = hotel.adicionarPlan(id, nombre, descripcion);
        		if(ti == null)
        		{
        			throw new Exception("No se pudo agregar el tipo de usuario " + nombre);
        		}
        		
        		String rta = "Agregar nuevo plan \n\n";
        		rta += "Plan " + ti  + " agregado exitosamente \n";
        		rta += "Operacion terminada";
        		panelDatos.actualizarInterfaz(rta);
        	}
        	else
        	{
        		panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
        	}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		panelDatos.actualizarInterfaz(generarMensajeError(e));
    	}
    	
    }
    public void darServiciosPopulares()
    {
    	try
    	{
    		String texto = "";
    		List<long[]>rta = hotel.darServiciosPopulares();
    		for(int i=0;i<rta.size();i++)
    		{
    			texto+=" Servicio con id: "+rta.get(i)[0]+" Cantidad Veces: "+rta.get(i)[1];
    		}
    		panelDatos.actualizarInterfaz(texto);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		panelDatos.actualizarInterfaz(generarMensajeError(e));
    	}
    }
    public void darHabitacionesYDinero()
    {
    	try
    	{
    		String text="";
    		String ini=JOptionPane.showInputDialog(this,"Hora Apertura");
    		String fini=JOptionPane.showInputDialog(this,"Hora cierre");
    		long inicio=Long.parseLong(ini);
			long fin=Long.parseLong(fini);
    		List<long[]> rtas = hotel.darHabitacionesYDinero(inicio, fin);
    		for(int i=0;i<rtas.size();i++)
    		{
    			text+="Habitacion: "+ rtas.get(i)[0] +" Dinero: "+ rtas.get(i)[1]+" ---";
    			
    		}
    		panelDatos.actualizarInterfaz(text);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		panelDatos.actualizarInterfaz(generarMensajeError(e));
    	}
    	
    
    
    }
    
}
