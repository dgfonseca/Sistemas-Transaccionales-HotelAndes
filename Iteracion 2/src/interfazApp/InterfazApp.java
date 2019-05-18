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
import java.math.BigDecimal;
import java.util.ArrayList;
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

import negocio.Apartan;
import negocio.Habitacion;
import negocio.HabitacionConvencion;
import negocio.HabitacionMantenimiento;
import negocio.HotelAndes;
import negocio.Mantenimiento;
import negocio.Plan;
import negocio.Reserva;
import negocio.Servicio;
import negocio.ServicioMantenimiento;
import negocio.ServiciosConvencion;
import negocio.Sirven;
import negocio.Tipo;
import negocio.Usuario;


/**
 * Clase principal de la interfaz
 * @author
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


	private static final String CONTRASE�A ="HOTELANDES";

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


	private Usuario user;

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
		user=null;

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

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("ADMIN")||tip.toUpperCase().equals("ADMINISTRADOR"))
			{




				String text="";
				for(int i=0; i<hotel.darTipos().size();i++)
				{
					text+="Id del tipo: "+hotel.darTipos().get(i).getId()+" Nombre del tipo: "+hotel.darTipos().get(i).getNombre()+"\n";
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
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");

				}
				else
				{
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo puede ser realizada por un administrador");
				panelDatos.actualizarInterfaz("Esta operacion solo puede ser realizada por un administrador");
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

			String tipO=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tipO);
			if(tipO.toUpperCase().equals("ADMIN")||tipO.toUpperCase().equals("ADMINISTRADOR"))
			{
				List<Usuario> user=hotel.darUsuarios();
				String holi ="";
				for(int i=0;i<user.size();i++)
				{
					holi+="Usuario con id: "+ user.get(i).getIdentificacion()+" Usuario con nombre: "+user.get(i).getNombre()+"\n";
				}
				panelDatos.actualizarInterfaz(holi);
				String idUsuario = JOptionPane.showInputDialog(this, "Identificacion del usuario");


				if(idUsuario != null)
				{
					String tipoId = JOptionPane.showInputDialog(this, "Tipo de identificacion");
					String nombre = JOptionPane.showInputDialog(this, "Nombre del usuario");
					String correo = JOptionPane.showInputDialog(this, "Correo de usuario");

					List<Tipo> tip=hotel.darTipos();
					String rtas="";
					for(int i=0;i<tip.size();i++)
					{
						rtas+=" Tipo con nombre e id : "+tip.get(i).getNombre()+", "+tip.get(i).getId()+"\n";

					}
					panelDatos.actualizarInterfaz(rtas);

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
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");

				}
				else
				{
					panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				}
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo puede ser realizada por un administrador");
				panelDatos.actualizarInterfaz("Esta operacion solo puede ser realizada por un administrador");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}


	}

	public void cancelarReservasConvencion()
	{
		try
		{

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("USER")||tip.toUpperCase().equals("USUARIO"))
			{
				String n1 = JOptionPane.showInputDialog(this, "Por favor ingrese el id de la convención a la cual desea cancelar reservas,");
				if(n1 != null)
				{
					long id = Long.parseLong(n1);
					String nombre = hotel.darNombreConvencion(id);
					if(nombre != null)
					{
						String rta = "";
						rta += "Modificando reservas para la convención " + nombre + "\n";
						List<Object[]> habitaciones = hotel.darHabitacionConvencionIdConvencion(id);
						rta+= "Habitaciones reservadas para la convención: " + habitaciones.size() + "\n";
						for(Object[] objeto: habitaciones)
						{
							rta+= "Habitacion número " + objeto[1] + "\n"; 
						}
						panelDatos.actualizarInterfaz(rta);
						JOptionPane.showMessageDialog(this, "A continuación podrá dejar de reservar una o mas habitaciones para su convención. Por favor ingrese los números de las habitaciones separados por coma.");
						String aEliminar = JOptionPane.showInputDialog(this, "Por favor ingrese los números de las habitaciones que desea dejar de reservar. ");
						if(aEliminar != null)
						{
							String[] habs = aEliminar.split(",");
							for(int i = 0; i< habs.length; i++)
							{
								String hab = habs[i].trim();
								int num = Integer.parseInt(hab);
								long eliminadas = hotel.eliminarHabitacionConvencion(id, num);
								if(eliminadas > 0)
								{
									rta += "Se eliminó la habitación " + num + " de la reserva de la convención. \n";	
								}
								else
								{
									rta += "No se pudo eliminar la habitación " + num  + " de la reserva de la convención. \n";
								}
							}
						}
						else
						{
							rta += "Operación cancelada por el usuario. \n";
						}

						panelDatos.actualizarInterfaz(rta);
						List<Object[]> servicios = hotel.darServiciosConvencionIdConvencion(id);
						rta+= "Servicios reservados para la convención: " + servicios.size() + "\n";
						for(Object[] objeto:servicios)
						{
							rta+= "Servicio número " + objeto[1] + "\n"; 
						}
						panelDatos.actualizarInterfaz(rta);
						JOptionPane.showMessageDialog(this, "A continuación podrá dejar de reservar uno o más servicios para su convención. Por favor ingrese los números de los servicios separados por coma.");
						String aEliminar2 = JOptionPane.showInputDialog(this, "Por favor ingrese los números de los servicios que desea dejar de reservar. ");
						if(aEliminar2 != null)
						{
							String[] servs = aEliminar2.split(",");
							for(int i = 0; i<servs.length; i++)
							{
								String servicio = servs[i].trim();
								int num = Integer.parseInt(servicio);
								long elim = hotel.eliminarServiciosConvencion(id, num);
								if(elim > 0)
								{
									rta += "Se eliminó el servicio " + num + " de la reserva de la convención. \n";	
								}
								else
								{
									rta += "No se pudo eliminar el servicio " + num  + " de la reserva de la convención. \n";
								}
							}
						}
						else
						{
							rta += "Operación cancelada por el usuario. \n";
						}
						panelDatos.actualizarInterfaz(rta);
					}
					else
					{
						panelDatos.actualizarInterfaz("No existe convención con el id dado.");
					}
				}
				else
				{
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario.");
				}

			}
			else
			{
				JOptionPane.showMessageDialog(null, "Solo los usuarios tienen acceso a estas funciones");
				panelDatos.actualizarInterfaz("Solo los usuarios tienen acceso a estas funciones");

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void reservarConvencion()
	{
		try
		{

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("USER")||tip.toUpperCase().equals("USUARIO"))
			{
				String n3 = JOptionPane.showInputDialog(this, "Ingrese el id de la convención");
				String n1 = JOptionPane.showInputDialog(this, "Ingrese el número de habitaciones a reservar");
				String n2 = JOptionPane.showInputDialog(this, "Ingrese el número de personas por habitacion");
				if(n1 != null && n2 != null && n3 != null)
				{
					long id = Long.parseLong(n3);
					int numHabitaciones = Integer.parseInt(n1);
					int tamanioHabitaciones = Integer.parseInt(n2);
					String rta ="";
					String nombre = hotel.darNombreConvencion(id);
					if(nombre != null)
					{
						rta = "Reservando habitaciones para la convención "  + nombre + "\n\n";
						Object[] rc = hotel.darFechaConvencion(id);

						rta += "Fechas de la convencion: desde " + rc[0] + " hasta " + rc[1] +  "\n";

						BigDecimal f3 = (BigDecimal) rc[0];
						BigDecimal f4 = (BigDecimal) rc[1];

						List<Habitacion> habs = hotel.darHabitacionesCapacidad(tamanioHabitaciones);
						rta += "Habitaciones disponibles: \n";

						List<Habitacion> habs2 = new ArrayList<Habitacion>();
						for (Habitacion habitacion : habs) 
						{
							//rta += habitacion.getNumeroHabitacion() + "\n";
							habs2.add(habitacion);
						}

						List<Object[]> habitacionesOcupadas = hotel.darHabitacionesOcupadas();
						for(Object[] objeto : habitacionesOcupadas)
						{
							BigDecimal f1 = (BigDecimal) objeto[0];
							BigDecimal f2 = (BigDecimal) objeto[1];


							if((f3.compareTo(f1)>0 || f3.compareTo(f2)<0) || (f4.compareTo(f1)>0 || f4.compareTo(f2)<0))
							{
								BigDecimal num = (BigDecimal) objeto[2];
								//rta += "Habitacion ocupada: " + objeto[2] + " desde: " + objeto[0] + " hasta: " + objeto[1] + "\n";
								for (int i = 0; i<habs.size(); i++)
								{
									Habitacion hab = habs.get(i);
									BigDecimal num2 = new BigDecimal(hab.getNumeroHabitacion());
									if(num.equals(num2))
									{
										habs2.remove(hab);
									}
								}
							}

						}

						rta += "Habitaciones disponibles después de validar fechas: \n";
						int cont = 0;
						for(Habitacion habitacion : habs2)
						{
							rta += habitacion.getNumeroHabitacion() + "\n";
							cont ++;
						}

						if(cont >= numHabitaciones)
						{
							int cont2=0;
							rta += "Reservar habitaciones y servicios convención \n\n";
							for(int i = 0; i<numHabitaciones; i++)
							{
								HabitacionConvencion habitacion = hotel.adicionarHabitacionConvencion(id, habs2.get(i).getNumeroHabitacion());
								if(habitacion != null)
								{
									rta += "Reservada la habitación " + habitacion.getNumeroHabitacion() + "\n";
									cont2++;
								}
							}

							rta += "Reservadas  "+ cont2  + " habitaciones para " + tamanioHabitaciones + " personas cada una. \n";
							panelDatos.actualizarInterfaz(rta);

							List<Object[]> servicios = hotel.darServiciosObjeto();
							List<Object[]> serviciosDisponibles = new ArrayList<Object[]>();
							List<Object[]> serviciosOcupados = hotel.darServiciosOcupados();

							rta += "Servicios del hotel \n" + servicios.size() + "\n";
							for(Object[] ser:servicios)
							{
								//rta += "Servicio: " + ser[5] + " " + ser[6] + "\n";
								serviciosDisponibles.add(ser);
							}
							for(Object[] objeto: serviciosOcupados)
							{
								BigDecimal f1 = (BigDecimal) objeto[2];
								BigDecimal f2 = (BigDecimal) objeto[3];
								BigDecimal num = (BigDecimal) objeto[0];
								if((f3.compareTo(f1)>0 || f3.compareTo(f2)<0) || (f4.compareTo(f1)>0 || f4.compareTo(f2)<0))
								{
									for(int i = 0; i<serviciosDisponibles.size(); i++)
									{
										Object[] s = serviciosDisponibles.get(i);
										BigDecimal num2 = (BigDecimal) s[0];
										if(num.equals(num2))
										{
											serviciosDisponibles.remove(i);
										}
									}
								}
							}

							rta += "Servicios del hotel disponibles para la convencion \n";
							for(Object[] ser:serviciosDisponibles)
							{
								rta += "Servicio id: " + ser[0]+ " Nombre Servicio:" + ser[3] + " " + ser[4] + "\n";
							}
							panelDatos.actualizarInterfaz(rta);

							JOptionPane.showMessageDialog(this, "Por favor añada los id de los servicios que desea adicionar. Revise la lista generada. (Separados por coma");
							String algo = JOptionPane.showInputDialog(this, "Id, separados por coma");

							if(algo != null)
							{
								String[] ids = algo.split(",");
								for(int i = 0; i<ids.length; i++)
								{
									String idAnadir = ids[i];
									int idA = Integer.parseInt(idAnadir);
									ServiciosConvencion anadido = hotel.adicionarServicioConvencion(id, idA);
									if(anadido != null)
									{
										rta += "Anadido el servicio con id " + idA + " \n";
									}
								}
							}
							else
							{
								rta += "No ha añadido ningún servicio a la convención. \n";
							}

						}
						else
						{
							rta += "No hay sufucientes habitaciones disponibles para realizar la reserva. \n";
						}


					}
					else
					{
						rta = "La convención no existe \n\n"; 
					}



					panelDatos.actualizarInterfaz(rta);
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");

				}
				else
				{
					panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Solo los usuarios tienen acceso a estas funciones");
				panelDatos.actualizarInterfaz("Solo los usuarios tienen acceso a estas funciones");

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void iniciarMantenimiento()
	{
		try
		{
			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("EMPLEADO")||tip.toUpperCase().equals("RECEPCIONISTA"))
			{
				String rta = "";
				String n1 = JOptionPane.showInputDialog(this, "Por favor, ingrese la fecha inicial del mantenimiento. Formato AAAAMMDD");
				String n2 = JOptionPane.showInputDialog(this, "Por favor, ingrese la fecha final del manenimiento. Formato AAAAMMDD");

				rta+= "Fecha inicio mantenimiento: " + n1 + "\n";
				rta += "Fecha fin mantenimiento: " + n2  +"\n";
				panelDatos.actualizarInterfaz(rta);

				BigDecimal f2 = new BigDecimal(n2);
				BigDecimal f1 = new BigDecimal(n1);

				List<Habitacion> habitaciones = hotel.darHabitaciones();
				List<Habitacion> disponiblesMantenimiento = new ArrayList<Habitacion>();
				List<Object[]> servicios = hotel.darServiciosObjeto();
				List<Object[]> disponiblesMantenimientoServicios = new ArrayList<Object[]>();
				for(Habitacion hab: habitaciones)
				{
					disponiblesMantenimiento.add(hab);
				}
				for(Object[] servi: servicios)
				{
					disponiblesMantenimientoServicios.add(servi);
				}

				if(n1 != null && n2 != null)
				{
					List<Object[]> habs = hotel.darHabitacionesMantenimientoFecha();
					for(Object[] objeto:habs)
					{
						BigDecimal f3 = (BigDecimal) objeto[1];
						BigDecimal f4 = (BigDecimal) objeto[2];
						if((f1.compareTo(f3)>0 && f1.compareTo(f4)<0) || (f2.compareTo(f3)>0 && f2.compareTo(f4)<0))
						{
							for(int i = 0; i<disponiblesMantenimiento.size(); i++)
							{
								Habitacion habitacion = disponiblesMantenimiento.get(i);
								BigDecimal numero = (BigDecimal) objeto[5];
								String num = numero + "";
								Integer numInt = new Integer(num);
								if(habitacion.getNumeroHabitacion() == numInt)
								{
									disponiblesMantenimiento.remove(i);
								}
							}
						}
					}
					List<Object[]> servs = hotel.darServiciosMantenimientoEnFecha();
					for(Object[] serv:servs)
					{
						BigDecimal f3 = (BigDecimal) serv[1];
						BigDecimal f4 = (BigDecimal) serv[2];
						if((f1.compareTo(f3)>0 && f1.compareTo(f4)<0) || (f2.compareTo(f3)>0 && f2.compareTo(f4)<0))
						{
							for(int i = 0; i<disponiblesMantenimientoServicios.size(); i++)
							{
								Object[] objeto = disponiblesMantenimientoServicios.get(i);
								BigDecimal numero = (BigDecimal) serv[5];
								if(objeto[0] == numero)
								{
									disponiblesMantenimientoServicios.remove(i);
								}
							}
						}
					}
					rta += "Habitaciones disponibles para realizar mantenimiento en las fechas dadas: " + disponiblesMantenimiento.size() + "\n";
					for(Habitacion hab: disponiblesMantenimiento)
					{
						rta += "Habitación número " + hab.getNumeroHabitacion() + " Descripcion: " + hab.getDescripcion() + "\n";
					}
					rta += "\n\n";
					rta+= "Servicios disponibles para realizar mantenimiento en las fechas dadas: " + disponiblesMantenimientoServicios.size() + "\n";
					for(Object[] objeto: disponiblesMantenimientoServicios)
					{
						rta += "Servicio número " + objeto[0] + " Nombre: " + objeto[3] + "\n";
					}
					panelDatos.actualizarInterfaz(rta);
					JOptionPane.showMessageDialog(this, "Por favor, seleccione las habitaciones que desea poner en mantenimiento, separadas por coma.");
					String respuesta = JOptionPane.showInputDialog("Habitaciones para el mantenimiento.");

					JOptionPane.showMessageDialog(this, "Por favor, seleccione los servicios que desea poner en mantenimiento, separados por coma.");
					String respuesta2 = JOptionPane.showInputDialog("Servicios para el mantenimiento.");

					String descripcionMantenimiento = JOptionPane.showInputDialog(this, "Descripción del mantenimiento a realizar");

					String idMantenimiento = JOptionPane.showInputDialog(this, "Id del mantenimiento a realizar");

					long fI = Long.parseLong(n1);
					long fF = Long.parseLong(n2);

					Mantenimiento mantenimiento = hotel.adicionarMantenimiento(Long.parseLong(idMantenimiento), fI, fF, descripcionMantenimiento);
					if(mantenimiento != null)
					{
						rta +="Mantenimiento creado satisfactoriamente. \n";
						String[] habsRespuesta = respuesta.split(",");
						for(int i = 0; i<habsRespuesta.length; i++)
						{
							String habitacionParaMantenimiento = habsRespuesta[i].trim();
							HabitacionMantenimiento creada = hotel.adicionarHabitacionMantenimiento(Long.parseLong(idMantenimiento), Integer.parseInt(habitacionParaMantenimiento));
							if(creada != null)
							{
								rta += creada.getNumeroHabitacion() + " añadida exitosamente al mantenimiento. \n";
							}
							else
							{
								rta += "No se pudo añadir la habitación " + habitacionParaMantenimiento + " al mantenimiento. \n";
							}
						}

						String[] servRespuesta = respuesta2.split(",");
						for(int i = 0; i<servRespuesta.length; i++)
						{
							String servicioParaMantenimiento = servRespuesta[i].trim();
							ServicioMantenimiento creada = hotel.adicionarServicioMantenimiento(Long.parseLong(idMantenimiento), Integer.parseInt(servicioParaMantenimiento));
							if(creada != null)
							{
								rta+= creada.getIdServicio() + " añadida exitosamente al mantenimiento. \n";
							}
							else
							{
								rta += "No se pudo añadir el servicio " + servicioParaMantenimiento + " al mantenimiento. \n";
							}
						}
					}
					else
					{
						rta+="No se pudo crear el mantenimiento. \n";
					}

					panelDatos.actualizarInterfaz(rta);
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");



				}
				else
				{
					panelDatos.actualizarInterfaz("Operación cancelada por el usuario.");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo esta permitida para los empleados");
				panelDatos.actualizarInterfaz("Esta operacion solo esta permitida para los empleados");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void finalizarMantenimiento()
	{
		try
		{

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("EMPLEADO")||tip.toUpperCase().equals("RECEPCIONISTA"))
			{
				String rta ="";
				List<Mantenimiento> mantenimientos = hotel.darMantenimientos();
				for(Mantenimiento man: mantenimientos)
				{
					rta += "Id del mantenimiento: " + man.getId() + " Descripcion del mantenimiento: " + man.getDescripcion() + " Desde-hasta: " + man.getFechaInicio() + "-" + man.getFechaFin() + "\n";
				}
				panelDatos.actualizarInterfaz(rta);

				String idM = JOptionPane.showInputDialog("Favor ingrese el número del mantenimiento que desea dar por terminado");
				if(idM != null)
				{
					Long id = Long.parseLong(idM.trim());

					long serviciosEliminados = hotel.eliminarServicioMantenimientoIdMantenimiento(id);
					if(serviciosEliminados > 0)
					{
						rta += serviciosEliminados + " servicios terminaron el mantenimiento. \n";
						long habitacionesEliminadas = hotel.eliminarHabitacionMantenimientoIdMantenimiento(id);
						if(habitacionesEliminadas >0)
						{
							rta += habitacionesEliminadas + " habitaciones terminaron el mantenimiento. \n";
							long mantenimientoEliminado = hotel.eliminarMantenimiento(id);
							if(mantenimientoEliminado > 0)
							{
								rta += "El mantenimiento con id " + id + " fue eliminado correctamente. \n";
							}
							else
							{
								rta += "No se pudo terminar el mantenimiento en los servicios \n";
							}
							panelDatos.actualizarInterfaz(rta);
						}
						else
						{
							rta += "No se pudo terminar el mantenimiento en las habitaciones \n";
						}
						panelDatos.actualizarInterfaz(rta);
					}
					else
					{
						rta += "No se pudo terminar el mantenimiento en los servicios \n";
					}
					panelDatos.actualizarInterfaz(rta);
				}
				else
				{
					rta += "Operación cancelada por el usuario. \n";
				}

			}
			else
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo esta permitida para los empleados");
				panelDatos.actualizarInterfaz("Esta operacion solo esta permitida para los empleados");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void indiceOcupacion()
	{
		try
		{
			String rta = "";
			String n1 = JOptionPane.showInputDialog("Ingrese una fecha para verificar la ocupación del hotel. Formato AAAAMMDD");
			BigDecimal f3 = new BigDecimal(n1);
			List<Object[]> habitacionesOcupadas = hotel.darHabitacionesOcupadas();
			List<Habitacion> todasHabitaciones = hotel.darHabitaciones();
			List<Habitacion> habitacionesOcupadasEnDia = new ArrayList<Habitacion>();
			rta += "Calculando el indice de ocupación del hotel para la fecha: " + n1 + "\n";
			for(Object[] objeto: habitacionesOcupadas)
			{
				BigDecimal f1 = (BigDecimal)objeto[0];
				BigDecimal f2 = (BigDecimal)objeto[1];
				if(f1.compareTo(f3)<=0 && f2.compareTo(f3)>=0)
				{
					for(Habitacion hab:todasHabitaciones)
					{
						BigDecimal num = (BigDecimal)objeto[2];
						if(hab.getNumeroHabitacion() == Integer.parseInt(num+""))
						{
							habitacionesOcupadasEnDia.add(hab);
						}
					}
				}
			}
			rta += "El hotel cuenta con un total de " + todasHabitaciones.size() + " habitaciones. \n";
			rta+= "En la fecha solicitada, hay un total de " + habitacionesOcupadasEnDia.size() + " habitaciones ocupadas. \n";
			double ocupacion = ((double)habitacionesOcupadasEnDia.size()/(double)todasHabitaciones.size())*100;
			rta += "El índice de ocupación del hotel en este día es de " + ocupacion + "%. \n";

			panelDatos.actualizarInterfaz(rta);



		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void serviciosCiertaCategoria()
	{
		try
		{
			String rta = "Esta característica le permitirá buscar servicios dadas ciertas caracterísitcas. \n";
			rta += "Por favor seleccione el número de la característica por la cual le gustaría filtrar los servicios. \n";
			rta += "1. Filtrar por precio (entre un rango de la forma XX,YY) \n";
			rta += "2. Posibilidad de recibir el servicio en una cierta hora (de la forma HHMM) \n";
			rta += "3. Aptos para un número de personas (de la forma XX) \n";
			rta += "4. Palabra clave (buscar la palabra clave en la descripcion del servicio \n";

			panelDatos.actualizarInterfaz(rta);

			String cara = JOptionPane.showInputDialog("Seleccione el número de la caracteristica de la lista mostrada,");
			if(cara.equals("1"))
			{
				cara = JOptionPane.showInputDialog("Ingrese el rango de precio (XX,YY)");
				String[] costos = cara.split(",");
				String costo1 = costos[0].trim();
				String costo2 = costos[1].trim();

				List<Object[]> servicios = hotel.darServiciosEnPrecio(Double.parseDouble(costo1), Double.parseDouble(costo2));
				rta += "Hay un total de " + servicios.size() + " servicios en el rango de precios dado. \n";
				for(Object[] objeto : servicios)
				{
					rta += "ID: " + objeto[0] + " Capacidad: " + objeto[1] + " Costo: " + objeto[2] + " Nombre: " + objeto[3] + " Descripcion: " + objeto[4] + "\n";
				}
				panelDatos.actualizarInterfaz(rta);
			}
			else if(cara.equals("2"))
			{
				cara = JOptionPane.showInputDialog("Ingrese la hora en la que gustaría acceder al servicio. Formato HHMM");
				List<Object[]> servicios = hotel.darServiciosPosiblesEnHora(Long.parseLong(cara));
				rta += "Hay un total de " + servicios.size() + " servicios accesibles a la hora solicitada. \n";
				for(Object[] objeto : servicios)
				{
					rta += "ID: " + objeto[0] + " Capacidad: " + objeto[1] + " Costo: " + objeto[2] + " Nombre: " + objeto[3] + " Descripcion: " + objeto[4] + "\n";
				}
				panelDatos.actualizarInterfaz(rta);
			}
			else if(cara.equals("3"))
			{
				cara = JOptionPane.showInputDialog("Ingrese la capacidad deseada para buscar los servicios.");
				List<Object[]> servicios = hotel.darServiciosParaCapacidad(Integer.parseInt(cara));
				rta += "Hay un total de " + servicios.size() + " servicios accesibles con la capacidad buscada. \n";
				for(Object[] objeto : servicios)
				{
					rta += "ID: " + objeto[0] + " Capacidad: " + objeto[1] + " Costo: " + objeto[2] + " Nombre: " + objeto[3] + " Descripcion: " + objeto[4] + "\n";
				}
				panelDatos.actualizarInterfaz(rta);
			}
			else if(cara.equals("4"))
			{
				cara = JOptionPane.showInputDialog("Ingrese la palabra clave para realizar la búsqueda de servicios.");
				List<Object[]> servicios = hotel.darServiciosObjeto();
				for(Object[] objeto : servicios)
				{
					String algo = (String)objeto[4];
					if(algo.contains(cara))
					{
						rta += "ID: " + objeto[0] + " Capacidad: " + objeto[1] + " Costo: " + objeto[2] + " Nombre: " + objeto[3] + " Descripcion: " + objeto[4] + "\n";
					}

				}
				panelDatos.actualizarInterfaz(rta);
			}
			else
			{
				rta += "La opción seleccionada no se encuentra entre las opciones posibles. \n";
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

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("USER")||tip.toUpperCase().equals("USUARIO"))
			{
				List<Object[]> reser=hotel.darReservas();
				String text="";
				for(int i=0;i<reser.size();i++)
				{
					text+="Reserva con id: "+reser.get(i)[0]+"\n";
				}
				System.out.println(reser.size());
				panelDatos.actualizarInterfaz(text);
				String id = JOptionPane.showInputDialog(this, "Identificacion de la Reserva, no usar las identificaciones anteriormente desplegadas");


				if(id != null)
				{
					String capacidad = JOptionPane.showInputDialog(this, "Cantidad Personas");
					String costo = JOptionPane.showInputDialog(this, "Costo de la reserva");
					String ano=JOptionPane.showInputDialog(this,"ano de la reserva ej: 2019");
					String mes=JOptionPane.showInputDialog(this,"Mes de la reserva  ej: 05");
					String dia=JOptionPane.showInputDialog(this,"Dia de la reserva ej: 05");
					int fe1=Integer.parseInt(ano);
					int fe2=Integer.parseInt(mes);
					int fe3=Integer.parseInt(dia);

					if(fe1<2019 || (fe2<0 || fe2>12)||(fe3>3||fe3<1))
					{
						throw new Exception("Fecha invalida introduzca bien los datos");
					}
					String ini=ano+mes+dia;				
					String ano2=JOptionPane.showInputDialog(this,"ano de fin de la reserva ej: 2019");
					String mes2=JOptionPane.showInputDialog(this,"Mes de fin de la reserva ej: 05");
					String dia2=JOptionPane.showInputDialog(this,"Dia de fin de la reserva ej: 05");
					int fe11=Integer.parseInt(ano2);
					int fe22=Integer.parseInt(mes2);
					int fe33=Integer.parseInt(dia2);

					if(fe11<2019 || (fe22<0 || fe22>12)||(fe33>31||fe33<1))
					{
						throw new Exception("Fecha invalida introduzca bien los datos");
					}
					String fini=ano2+mes2+dia2;				
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
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");

				}
				else
				{
					panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Solo los usuarios tienen acceso a estas funciones");
				panelDatos.actualizarInterfaz("Solo los usuarios tienen acceso a estas funciones");

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

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("ADMIN")||tip.toUpperCase().equals("ADMINISTRADOR"))
			{
				String print="No usar los siguientes id's los cuales ya estan en uso :";
				List<Object[]> serv=hotel.darServiciosObjeto();
				for(int i=0;i<serv.size();i++)
				{
					print+=" Servicio con id: "+serv.get(i)[0] +" --- Servicio con nombre: "+ serv.get(i)[3]+"\n";
				}
				panelDatos.actualizarInterfaz(print);
				String id = JOptionPane.showInputDialog(this, "Identificacion del Servicio, no usar las identificaciones anteriormente desplegadas");


				if(id != null)
				{
					String capacidad = JOptionPane.showInputDialog(this, "Capacidad del Servicio");
					String costo = JOptionPane.showInputDialog(this, "Costo del Servicio");
					String ano=JOptionPane.showInputDialog(this,"Hora de apertura servicio ej 22");
					String mes=JOptionPane.showInputDialog(this,"Minuto de aprtura del servicio ej 05");
					int fe1=Integer.parseInt(ano);
					int fe2=Integer.parseInt(mes);

					if(fe1<0 || fe1>23 || fe2<0 || fe2>60)
					{
						throw new Exception("Fecha invalida introduzca bien los datos");
					}
					String fechaInicio=ano+mes; 
					System.out.println(fechaInicio);
					String ano2=JOptionPane.showInputDialog(this,"hora de cierre servicio ej 22");
					String mes2=JOptionPane.showInputDialog(this,"Minuto de cierre del servicio ej 05");
					int fe11=Integer.parseInt(ano);
					int fe22=Integer.parseInt(mes);

					if(fe11<0 || fe11>23 || fe22<0 || fe22>60)
					{
						throw new Exception("Fecha invalida introduzca bien los datos");
					}
					String fini=ano2+mes2;        		        		
					String descripcion = JOptionPane.showInputDialog(this, "Descripcion");
					String nombre=JOptionPane.showInputDialog(this,"Nombre del servicio");
					long iden=Long.parseLong(id);
					long inicio=Long.parseLong(fechaInicio);
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
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");

				}
				else
				{
					panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				}
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo puede ser realizada por un administrador");
				panelDatos.actualizarInterfaz("Esta operacion solo puede ser realizada por un administrador");
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


			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("ADMIN")||tip.toUpperCase().equals("ADMINISTRADOR"))
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
						throw new Exception("Tamano Incorrecto en disponibilidad, solo se puede Usar T o F");
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
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");

				}
				else
				{
					panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				}

			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo puede ser realizada por un administrador");
				panelDatos.actualizarInterfaz("Esta operacion solo puede ser realizada por un administrador");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void adicionarApartan()
	{
		try
		{

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("EMPLEADO")||tip.toUpperCase().equals("RECEPCIONISTA"))
			{
				String text="habitaciones : ";
				for(int i=0; i<hotel.darHabitaciones().size();i++)
				{
					text+="identificacion: "+hotel.darHabitaciones().get(i).getNumeroHabitacion()+" Disponibilidad: "+hotel.darHabitaciones().get(i).isDisponible()+"\n";
				}
				panelDatos.actualizarInterfaz(text);
				String idServicio = JOptionPane.showInputDialog(this, "Id de la reserva a cargar");
				String numeroHabitacion=JOptionPane.showInputDialog(this, "Numero de habitacion para cargar a la reserva");
				long ids=Long.parseLong(idServicio);
				int numHabitacion=Integer.parseInt(numeroHabitacion);

				if(idServicio != null)
				{
					Apartan ti = hotel.adicionarApartan(ids, numHabitacion);
					if(ti == null)
					{
						throw new Exception("No se pudo agregar la reserva a la habitacion" + numHabitacion);
					}

					String rta = "Agregar nuevo reserva a habitacion \n\n";
					rta += "reserva a habitacion " + ti  + " agregado exitosamente \n";
					rta += "Operacion terminada";
					panelDatos.actualizarInterfaz(rta);
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");
				}
				else
				{
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo esta permitida para los empleados");
				panelDatos.actualizarInterfaz("Esta operacion solo esta permitida para los empleados");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}
	}
	public void adicionarSirven()
	{
		try
		{

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("EMPLEADO")||tip.toUpperCase().equals("RECEPCIONISTA"))
			{
				String text="Servicios : ";
				for(int i=0; i<hotel.darServicios().size();i++)
				{
					text+="identificacion: "+hotel.darServicios().get(i)[2]+" Nombre: "+hotel.darServicios().get(i)[1];
				}
				panelDatos.actualizarInterfaz(text);
				String idServicio = JOptionPane.showInputDialog(this, "Id del servicio a cargar");
				String numeroHabitacion=JOptionPane.showInputDialog(this, "Numero de habitacion para cargar servicio");
				String ano=JOptionPane.showInputDialog(this,"Ano de uso del servicio ej: 2019");
				String mes=JOptionPane.showInputDialog(this,"Mes de uso del servicio ej: 05");
				String dia=JOptionPane.showInputDialog(this,"Dia de uso del servicio ej: 05");
				int fe1=Integer.parseInt(ano);
				int fe2=Integer.parseInt(mes);
				int fe3=Integer.parseInt(dia);

				if(fe1<2019 || (fe2<0 || fe2>12)||(fe3>31||fe3<1))
				{
					throw new Exception("Fecha invalida introduzca bien los datos");
				}
				String fecha=ano+mes+dia;
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
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");
				}
				else
				{
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}

			}
			else
			{
				JOptionPane.showMessageDialog(null, "Solo los empleados tienen acceso a estas funciones");
				panelDatos.actualizarInterfaz("Solo los empleados tienen acceso a estas funciones");

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

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("EMPLEADO")||tip.toUpperCase().equals("RECEPCIONISTA"))
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
			else
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo esta permitida para los empleados");
				panelDatos.actualizarInterfaz("Esta operacion solo esta permitida para los empleados");
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

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("EMPLEADO")||tip.toUpperCase().equals("RECEPCIONISTA"))
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
			else
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo esta permitida para los empleados");
				panelDatos.actualizarInterfaz("Esta operacion solo esta permitida para los empleados");
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

			String tip=hotel.darTipoId(user.getIdTipo()).getNombre();
			System.out.println(tip);
			if(tip.toUpperCase().equals("ADMIN")||tip.toUpperCase().equals("ADMINISTRADOR"))
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
					JOptionPane.showMessageDialog(null, "Agregado exitosamente");
				}
				else
				{
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "Esta operacion solo puede ser realizada por un administrador");
				panelDatos.actualizarInterfaz("Esta operacion solo puede ser realizada por un administrador");
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

	public void darConsumoUsuarioDado()
	{
		try
		{
			String texto = "";
			String ide =JOptionPane.showInputDialog(this,"Id de usuario a buscar");
			long id=Long.parseLong(ide);
			String ano=JOptionPane.showInputDialog(this,"Ano de inicio para determinar consumo");
			String mes=JOptionPane.showInputDialog(this,"Mes de inicio para determinar consumo");
			String dia=JOptionPane.showInputDialog(this,"Dia de inicio para determinar consumo");
			int fe1=Integer.parseInt(ano);
			int fe2=Integer.parseInt(mes);
			int fe3=Integer.parseInt(dia);

			if(fe1<2019 || (fe2<0 || fe2>12)||(fe3>31||fe3<1))
			{
				throw new Exception("Fecha invalida introduzca bien los datos");
			}
			String fecha=ano+mes+dia;
			long ini=Long.parseLong(fecha);
			String ano2=JOptionPane.showInputDialog(this,"Ano de inicio para determinar consumo");
			String mes2=JOptionPane.showInputDialog(this,"Mes de inicio para determinar consumo");
			String dia2=JOptionPane.showInputDialog(this,"Dia de inicio para determinar consumo");
			int fe12=Integer.parseInt(ano);
			int fe22=Integer.parseInt(mes);
			int fe32=Integer.parseInt(dia);

			if(fe12<2019 || (fe22<0 || fe22>12)||(fe32>31&&fe32<1))
			{
				throw new Exception("Fecha invalida introduzca bien los datos");
			}
			String finc=ano2+mes2+dia2;
			long fin=Long.parseLong(finc);
			List<Object[]> rta = hotel.darConsumoUsuarioDado(id, ini, fin);
			for(int i=0;i<rta.size();i++)
			{
				texto+="Usuario : "+rta.get(i)[0]+" Consumo: "+rta.get(i)[1];
			}
			panelDatos.actualizarInterfaz(texto);
			JOptionPane.showMessageDialog(null, texto);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}
	}
	public void darBuenosClientes() {
		try
		{
			List<Usuario>rta=hotel.darBuenosClientes();
			String texto="";
			for(int i=0;i<rta.size();i++)
			{
				texto+="| CLIENTE----> Nombre : "+rta.get(i).getNombre()+" Identificacion : "+rta.get(i).getTipoIdentificacion()+":"+ rta.get(i).getIdentificacion()+ " Correo : "+rta.get(i).getCorreoElectronico();
			}
			panelDatos.actualizarInterfaz(texto);
			JOptionPane.showMessageDialog(null, texto);
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
			String ano=JOptionPane.showInputDialog(this,"Ano de inicio para determinar consumo");
			String mes=JOptionPane.showInputDialog(this,"Mes de inicio para determinar consumo");
			String dia=JOptionPane.showInputDialog(this,"Dia de inicio para determinar consumo");
			int fe1=Integer.parseInt(ano);
			int fe2=Integer.parseInt(mes);
			int fe3=Integer.parseInt(dia);

			if(fe1<2019 || (fe2<0 || fe2>12)||(fe3>31||fe3<1))
			{
				throw new Exception("Fecha invalida introduzca bien los datos");
			}
			String fecha=ano+mes+dia;
			long ini=Long.parseLong(fecha);
			String ano2=JOptionPane.showInputDialog(this,"Ano de finalizacion para determinar consumo");
			String mes2=JOptionPane.showInputDialog(this,"Mes de finalizacion para determinar consumo");
			String dia2=JOptionPane.showInputDialog(this,"Dia de finalizacion para determinar consumo");
			int fe12=Integer.parseInt(ano);
			int fe22=Integer.parseInt(mes);
			int fe32=Integer.parseInt(dia);

			if(fe12<2019 || (fe22<0 || fe22>12)||(fe32>31||fe32<1))
			{
				throw new Exception("Fecha invalida introduzca bien los datos");
			}
			String finc=ano2+mes2+dia2;
			long fin=Long.parseLong(finc);
			List<long[]> rtas = hotel.darHabitacionesYDinero(ini, fin);
			for(int i=0;i<rtas.size();i++)
			{
				text+="Habitacion: "+ rtas.get(i)[0] +" Dinero: "+ rtas.get(i)[1]+" ---";

			}
			panelDatos.actualizarInterfaz(text);
			JOptionPane.showMessageDialog(null, text);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}
	}

	public void logIn()
	{
		try{

			String clave=JOptionPane.showInputDialog(this,"Ingrese Contrase�a");
			System.out.println(clave);
			if(clave.equals(CONTRASE�A))
			{
				String cedula=JOptionPane.showInputDialog(this, "Ingrese cedula para registrarse");
				long cc = Long.parseLong(cedula);
				List<Usuario> list=hotel.darUsuarioId(cc);
				user=list.get(0);

				panelDatos.actualizarInterfaz("Login exitoso");
			}
			else
			{
				panelDatos.actualizarInterfaz("Clave incorrecta");
			}

		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Clave o usuario invalido, contactarse con administrador para mayor informacion");
			e.printStackTrace();
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}

	}

	public void requerimientoFuncional9()
	{
		try
		{
			int id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del servicio a buscar"));
			int ini=Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fecha inicial con formato YYYYMMDDH24"));
			int fin=Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fecha inicial con formato YYYYMMDDH24"));
			List<Object[]> lista=hotel.requerimientoFuncional9(id, ini, fin);
			String txt="";
			for ( Object [] tupla : lista)
			{
				

				String nombre = ((String) tupla [0]);
				long identificacion = ((BigDecimal) tupla [1]).longValue ();
				long count=((BigDecimal) tupla [2]).longValue ();
				txt+="Usuario con nombre: "+nombre+" identificacion: "+identificacion+" cantidadServicios: "+ count +"\n";
				
			}
			panelDatos.actualizarInterfaz(txt);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "servicio o fecha invalida, contactarse con administrador para mayor informacion");
			e.printStackTrace();
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}

	}
	
	public void requerimientoFuncional10()
	{
		try
		{
			String text="Servicios : ";
			System.err.println(hotel.darServicios().size());
			for(int i=0; i<hotel.darServicios().size();i++)
			{
				text+="identificacion: "+hotel.darServicios().get(i)[0]+" Nombre: "+hotel.darServicios().get(i)[1]+"\n";
			}
			panelDatos.actualizarInterfaz(text);
			int id=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del servicio a buscar"));
			int ini=Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fecha inicial con formato YYYYMMDDH24"));
			int fin=Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fecha inicial con formato YYYYMMDDH24"));
			List<Object[]> lista=hotel.requerimientoFuncional10(id, ini, fin);
			String txt="";
			for ( Object [] tupla : lista)
			{
				

				String nombre = ((String) tupla [0]);
				long identificacion = ((BigDecimal) tupla [1]).longValue ();
				txt+="Usuario con nombre: "+nombre+" identificacion: "+identificacion +"\n";
				
			}
			panelDatos.actualizarInterfaz(txt);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "servicio o fecha invalida, contactarse con administrador para mayor informacion");
			e.printStackTrace();
			panelDatos.actualizarInterfaz(generarMensajeError(e));
		}

	}


}
