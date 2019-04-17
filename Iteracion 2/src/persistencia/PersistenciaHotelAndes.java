package persistencia;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import negocio.Apartan;
import negocio.Consumen;
import negocio.Convencion;
import negocio.Habitacion;
import negocio.HabitacionConvencion;
import negocio.Ofrecen;
import negocio.Plan;
import negocio.Producto;
import negocio.Reserva;
import negocio.ReservaConvencion;
import negocio.Servicio;
import negocio.Sirven;
import negocio.Tipo;
import negocio.Usuario;





public class PersistenciaHotelAndes {

	private static Logger log = Logger.getLogger(PersistenciaHotelAndes.class.getName());

	public final static String SQL = "javax.jdo.query.SQL";

	private static PersistenciaHotelAndes instance;
	private SQLPlan sqlPlan;
	private SQLConsumen sqlContienen;
	private SQLHabitacion sqlHabitacion;
	private SQLOfrecen sqlOfrecen;
	private SQLProducto sqlProducto;
	private SQLReserva sqlReserva;
	private SQLServicio sqlServicio;
	private SQLSirven sqlSirven;
	private SQLUsuario sqlUsuario;
	private SQLTipo sqlTipo;
	private SQLApartan sqlApartan;
	private SQLUtil sqlUtil;
	private SQLConvencion sqlConvencion;
	private SQLReservaConvencion sqlReservaConvencion;
	private SQLHabitacionConvencion sqlHabitacionConvencion;



	private PersistenceManagerFactory pmf;


	private List <String> tablas;

	public PersistenciaHotelAndes()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
		tablas.add("PAGOSPSE");
		tablas.add("PLAN");
		tablas.add("CONSUMEN");
		tablas.add("HABITACION");
		tablas.add("HOSPEDAN");
		tablas.add("OFRECEN");
		tablas.add("PRODUCTOS");
		tablas.add("RESERVA");
		tablas.add("SERVICIOS");
		tablas.add("SIRVEN");
		tablas.add("USUARIO");
		tablas.add("TIPO");
		tablas.add("APARTAN");
		tablas.add("CONVENCIONES");
		tablas.add("CONVENCION_USUARIO");
		tablas.add("RESERVA_CONVENCIONES");
		tablas.add("SERVICIOS_CONVENCION");
		tablas.add("HABITACION_CONVENCION");
		tablas.add("MANTENIMIENTOS");
		tablas.add("SERVICIO_MANTENIMIENTO");
		tablas.add("HABITACION_MANTENIMIENTOs");

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
	}


	private PersistenciaHotelAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}


	public static PersistenciaHotelAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaHotelAndes ();
		}
		return instance;
	}


	public static PersistenciaHotelAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaHotelAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexi�n con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{


		sqlPlan=new SQLPlan(this);
		sqlContienen=new SQLConsumen(this);
		sqlHabitacion= new SQLHabitacion(this);
		sqlOfrecen= new SQLOfrecen(this);
		sqlProducto=new SQLProducto(this);
		sqlReserva = new SQLReserva(this);
		sqlServicio=new SQLServicio(this);
		sqlSirven=new SQLSirven(this);
		sqlUsuario=new SQLUsuario(this);
		sqlUtil=new SQLUtil(this);
		sqlTipo=new SQLTipo(this);
		sqlApartan=new SQLApartan(this);
		sqlConvencion=new SQLConvencion(this);
		sqlReservaConvencion = new SQLReservaConvencion(this);
		sqlHabitacionConvencion = new SQLHabitacionConvencion(this);

	}

	public String darSeqHotelandes ()
	{
		return tablas.get (0);
	}
	public String darTablaPagoPSE()
	{
		return tablas.get(1);
	}
	public String darTablaPlan()
	{
		return tablas.get(2);
	}
	public String darTablaConsumen()
	{
		return tablas.get(3);
	}
	public String darTablaHabitacion()
	{
		return tablas.get(4);
	}
	public String darTablaHospedan() {
		return tablas.get(5);
	}
	public String darTablaOfrecen()
	{
		return tablas.get(6);
	}
	public String darTablaProducto() {
		return tablas.get(7);
	}
	public String darTablaReserva()
	{
		return tablas.get(8);
	}
	public String darTablaServicio()
	{
		return tablas.get(9);
	}
	public String darTablaSirven()
	{
		return tablas.get(10);
	}
	public String darTablaUsuario()
	{
		return tablas.get(11);
	}
	public String darTablaTipo()
	{
		return tablas.get(12);
	}
	public String darTablaApartan()
	{
		return tablas.get(13);
	}
	public String darTablaConvenciones()
	{
		return tablas.get(14);
	}
	public String darTablaConvencionUsuario()
	{
		return tablas.get(15);
	}
	public String darTablaReservaConvenciones()
	{
		return tablas.get(16);
	}
	public String darTablaServiciosConvencion()
	{
		return tablas.get(17);
	}
	public String darTablaHabitacionConvencion()
	{
		return tablas.get(18);
	}
	public String darTablaMantenimientos()
	{
		return tablas.get(19);
	}
	public String darTablaServicioMantenimiento()
	{
		return tablas.get(20);
	}
	public String darTablaHabitacionMantenimiento()
	{
		return tablas.get(21);
	}


	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle espec�fico del problema encontrado
	 * @param e - La excepci�n que ocurrio
	 * @return El mensaje de la excepci�n JDO
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


	public Tipo adicionarTipoUsuario(long idtipo, String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			System.out.println(idtipo);
			System.out.println(nombre);
			long tuplasInsertadas = sqlTipo.adicionarTipo(pm, idtipo, nombre);
			tx.commit();

			log.trace ("Inserci�n de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Tipo (idtipo , nombre);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public List<Tipo> darTipos ()
	{
		return sqlTipo.darTipos (pmf.getPersistenceManager());
	}
	public List<Convencion> darConvenciones()
	{
		return sqlConvencion.darConvenciones(pmf.getPersistenceManager());
	}










	public long [] limpiarHotelAndes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarHotelandes (pm);
			tx.commit ();
			log.info ("Borrada la base de datos");
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}


	//Dar los elementos de la tabla//
	public List<Consumen> darContienen ()
	{
		return sqlContienen.darContienen (pmf.getPersistenceManager());
	}


	public List<Habitacion> darHabitaciones ()
	{
		return sqlHabitacion.darHabitaciones (pmf.getPersistenceManager());
	}
	
	public List<Habitacion> darHabitacionesCapacidad(int num)
	{
		return sqlHabitacion.darHabitacionesCapacidad(pmf.getPersistenceManager(), num);
	}

	public List<Ofrecen> darOfrecen ()
	{
		return sqlOfrecen.darOfrecen (pmf.getPersistenceManager());
	}


	public List<Plan> darPlanes ()
	{
		return sqlPlan.darPlanes (pmf.getPersistenceManager());
	}
	public List<Producto> darProductos ()
	{
		return sqlProducto.darProductos (pmf.getPersistenceManager());
	}
	public List<Reserva> darReservas ()
	{
		return sqlReserva.darReservas (pmf.getPersistenceManager());
	}
	public List<Servicio> darServicios ()
	{
		return sqlServicio.darServicios (pmf.getPersistenceManager());
	}
	public List<Sirven> darSirven ()
	{
		return sqlSirven.darSirven (pmf.getPersistenceManager());
	}
	public List<Tipo> darTipo ()
	{
		return sqlTipo.darTipos (pmf.getPersistenceManager());
	}
	public List<Usuario> darUsuarios ()
	{
		return sqlUsuario.darUsuarios (pmf.getPersistenceManager());
	}

	public List<Apartan> darApartan()
	{
		return sqlApartan.darContienen(pmf.getPersistenceManager());
	}




///////////////////////REQUERIMIENTOS FUNCIONALES DE CONSULTA////////////////////////////////
///////////////////////REQUERIMIENTOS FUNCIONALES DE CONSULTA////////////////////////////////
///////////////////////REQUERIMIENTOS FUNCIONALES DE CONSULTA////////////////////////////////
///////////////////////REQUERIMIENTOS FUNCIONALES DE CONSULTA////////////////////////////////
///////////////////////REQUERIMIENTOS FUNCIONALES DE CONSULTA////////////////////////////////
///////////////////////REQUERIMIENTOS FUNCIONALES DE CONSULTA////////////////////////////////	
	public List<long []> darHabitacionesEIndiceOcupacion ()
	{
		List<long []> resp = new LinkedList<long []> ();
		List<Object []> tuplas =  sqlHabitacion.darIndiceOcupacionHabitacion (pmf.getPersistenceManager());
		for ( Object [] tupla : tuplas)
		{
			long [] datosResp = new long [2];

			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
			resp.add (datosResp);
		}
		return resp;
	}


	/**
	 * Requerimiento funcional 1
	 * @param inicio
	 * @param fin
	 * @return
	 */
	public List<long []> darHabitacionesYDineroRecolectado (long inicio, long fin)
	{
		List<long []> resp = new LinkedList<long []> ();
		List<Object []> tuplas =  sqlHabitacion.darHabitacionesYDineroRecolectado(pmf.getPersistenceManager(),inicio,fin);
		for ( Object [] tupla : tuplas)
		{
			long [] datosResp = new long [2];

			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
			resp.add (datosResp);
		}
		return resp;
	}

	/**
	 * Requerimiento Funcional 2
	 * @return
	 */
	public List<Servicio> darServiciosPopulares(){
		return sqlServicio.dar20Servicios(pmf.getPersistenceManager());

	}
	
	

	public List<long []> darConsumoYUsuario (String pnombre)
	{
		List<long []> resp = new LinkedList<long []> ();
		List<Object []> tuplas =  sqlUsuario.darConsumoUsuario(pmf.getPersistenceManager(),pnombre);
		for ( Object [] tupla : tuplas)
		{
			long [] datosResp = new long [2];

			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
			resp.add (datosResp);
		}
		return resp;
	}
	
	
///////////////////////////////////METODOS AGREGACION/////////////////////////////////////////////////
///////////////////////////////////METODOS AGREGACION/////////////////////////////////////////////////
///////////////////////////////////METODOS AGREGACION/////////////////////////////////////////////////
///////////////////////////////////METODOS AGREGACION/////////////////////////////////////////////////
///////////////////////////////////METODOS AGREGACION/////////////////////////////////////////////////
	
	
	
	public Habitacion adicionarHabitacion(int capacidad, int numeroHabitacion, int costo, String descripcion,String disponible) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long tuplasInsertadas = sqlHabitacion.adicionarHabitacion(pm, capacidad, numeroHabitacion, costo, descripcion, disponible);
			tx.commit();

			log.trace ("Inserci�n Habitacion: " + numeroHabitacion + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Habitacion(capacidad, numeroHabitacion, costo, descripcion, disponible);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Servicio adicionarServicio(long id,int personas, long inicio, long fin, double costo,String nombre, String descripcion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();  
			long tuplasInsertadas = sqlServicio.adicionarServicio(pm, id, inicio, fin, personas, costo, nombre, descripcion);
			tx.commit();

			log.trace ("Inserci�n Habitacion: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Servicio(personas, id, costo, nombre, inicio, fin, descripcion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Reserva adicionarReserva(long id,int personas, long inicio, long fin, double costo, String descripcion, String registrado, String pago, long idplan,long idusuario) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			System.out.println("ENTROOO");
			tx.begin();  
			long tuplasInsertadas = sqlReserva.adicionarReserva(pm, id, inicio, fin, personas, costo, registrado, pago, idplan, idusuario);
			tx.commit();

			log.trace ("Inserci�n Habitacion: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Reserva(id, inicio, fin,personas, costo, registrado, pago, idplan, idusuario);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Plan adicionarPlan(long id,String nombre, String descripcion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();  
			long tuplasInsertadas = sqlPlan.adicionarPlan(pm, id, nombre, descripcion);
			tx.commit();

			log.trace ("Inserci�n Habitacion: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Plan(id, nombre, descripcion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public Producto adicionarProducto( double costo,String nombre, int cantidad) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();  
			long id = nextval ();
			long tuplasInsertadas = sqlProducto.adicionarProducto(pm, id, nombre, costo, cantidad);
			tx.commit();

			log.trace ("Inserci�n Habitacion: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Producto(id, nombre, costo, cantidad);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Usuario adicionarUsuario(int identificacion, String tipoid, String correo,long idTipo , String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();  
			long tuplasInsertadas = sqlUsuario.adicionarUsuario(pm, identificacion, nombre, tipoid, correo, idTipo);
			tx.commit();


			log.trace ("Inserci�n Habitacion: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Usuario(identificacion, tipoid, idTipo, correo, nombre);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public Ofrecen adicionarOfrecen(long idServicio, long idProducto) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlOfrecen.adicionarOfrecen(pm, idServicio, idProducto);
			tx.commit();

			log.trace ("Inserci�n de gustan: [" + idServicio + ", " + idProducto + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Ofrecen (idServicio, idProducto);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	public Consumen adicionarContienen(long idProducto, int numeroHabitacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlContienen.adicionarContienen(pm, idProducto, numeroHabitacion);
			tx.commit();

			log.trace ("Inserci�n de gustan: [" + numeroHabitacion + ", " + idProducto + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Consumen (idProducto, numeroHabitacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public Sirven adicionarSirven(long idServicio, int numeroHabitacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlSirven.adicionarSirven(pm, idServicio, numeroHabitacion);
			tx.commit();

			log.trace ("Inserci�n de gustan: [" + numeroHabitacion + ", " + idServicio + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Sirven (idServicio, numeroHabitacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Apartan adicionarApartan(long idServicio, int numeroHabitacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlApartan.adicionaApartan(pm, idServicio, numeroHabitacion);
			tx.commit();

			log.trace ("Inserci�n de gustan: [" + numeroHabitacion + ", " + idServicio + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Apartan (numeroHabitacion, idServicio);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	
	public long registrarLlegada (long idReserva)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReserva.registrarLlegada(pm, idReserva);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public long registrarSalida (long idReserva)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlReserva.registrarSalida(pm, idReserva);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public String darNombreConvencion(long id)
	{
		return sqlConvencion.darNombreConvencion(pmf.getPersistenceManager(), id);
	}
	
	public List<Object[]> darHabitacionesOcupadas()
	{
		return sqlHabitacion.darHabitacionesOcupadas(pmf.getPersistenceManager());
	}
	
	public Object[] darFechaConvencion(long id)
	{
		return sqlReservaConvencion.darFechaConvencion(pmf.getPersistenceManager(), id);
	}
	
	public HabitacionConvencion adicionarHabitacionConvencion(long idReserva, int idHabitacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long tuplasInsertadas = sqlHabitacionConvencion.adicionaHabitacionConvencion(pmf.getPersistenceManager(), idReserva, idHabitacion);
			tx.commit();

			log.trace ("Inserci�n Habitacion convención: " + idHabitacion +" " + "idReserva" + ": " + tuplasInsertadas + " tuplas insertadas");
			return new HabitacionConvencion(idReserva, idHabitacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	






}
