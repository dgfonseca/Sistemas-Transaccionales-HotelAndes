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
import negocio.Contienen;
import negocio.Habitacion;
import negocio.Hospedan;
import negocio.Ofrecen;
import negocio.PagoPSE;
import negocio.Plan;
import negocio.Producto;
import negocio.Reserva;
import negocio.Servicio;
import negocio.Sirven;
import negocio.Tipo;
import negocio.Usuario;





public class PersistenciaHotelAndes {

	private static Logger log = Logger.getLogger(PersistenciaHotelAndes.class.getName());

	public final static String SQL = "javax.jdo.query.SQL";

	private static PersistenciaHotelAndes instance;
	private SQLPagoPSE sqlPago;
	private SQLPlan sqlPlan;
	private SQLContienen sqlContienen;
	private SQLHabitacion sqlHabitacion;
	private SQLHospedan sqlHospedan;
	private SQLOfrecen sqlOfrecen;
	private SQLProducto sqlProducto;
	private SQLReserva sqlReserva;
	private SQLServicio sqlServicio;
	private SQLSirven sqlSirven;
	private SQLUsuario sqlUsuario;
	private SQLTipo sqlTipo;
	private SQLApartan sqlApartan;
	private SQLUtil sqlUtil;


	
	private PersistenceManagerFactory pmf;


	private List <String> tablas;

	public PersistenciaHotelAndes()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
		tablas.add("PAGOSPSE");
		tablas.add("PLANES");
		tablas.add("CONTIENEN");
		tablas.add("HABITACIONES");
		tablas.add("HOSPEDAN");
		tablas.add("OFRECEN");
		tablas.add("PRODUCTOS");
		tablas.add("RESERVAS");
		tablas.add("SERVICIOS");
		tablas.add("SIRVEN");
		tablas.add("USUARIO");
		tablas.add("TIPO");
		tablas.add("APARTAN");

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
	 * Cierra la conexión con la base de datos
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


		sqlPago = new SQLPagoPSE(this);
		sqlPlan=new SQLPlan(this);
		sqlContienen=new SQLContienen(this);
		sqlHabitacion= new SQLHabitacion(this);
		sqlHospedan = new SQLHospedan(this);
		sqlOfrecen= new SQLOfrecen(this);
		sqlProducto=new SQLProducto(this);
		sqlReserva = new SQLReserva(this);
		sqlServicio=new SQLServicio(this);
		sqlSirven=new SQLSirven(this);
		sqlUsuario=new SQLUsuario(this);
		sqlUtil=new SQLUtil(this);
		sqlTipo=new SQLTipo(this);
		sqlApartan=new SQLApartan(this);

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
	public String darTablaContienen()
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


	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
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


	public Tipo adicionarTipoBebida(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idTipo = nextval ();
			long tuplasInsertadas = sqlTipo.adicionarTipo(pm, idTipo, nombre);
			tx.commit();

			log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Tipo (idTipo , nombre);
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
	public List<Tipo> darTipos ()
	{
		return sqlTipo.darTipos (pmf.getPersistenceManager());
	}

	public Tipo darTipoPorId (long idTipoBebida)
	{
		return sqlTipo.darTipoPorId (pmf.getPersistenceManager(), idTipoBebida);
	}

	public List<Tipo> darTipoPorNombre (String nombre)
	{
		return sqlTipo.darTiposPorNombre (pmf.getPersistenceManager(), nombre);
	}


	//METODOS AGREGACION//

	public Habitacion adicionarHabitacion(int capacidad, int numeroHabitacion, double costo, String descripcion,char disponible,long idReserva) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long tuplasInsertadas = sqlHabitacion.adicionarHabitacion(pm, capacidad, numeroHabitacion, costo, descripcion, disponible, idReserva);
			tx.commit();

			log.trace ("Inserción Habitacion: " + numeroHabitacion + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Habitacion(capacidad, numeroHabitacion, costo, descripcion, disponible, idReserva);
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

	public Servicio adicionarServicio(int personas, long inicio, long fin, double costo,String nombre, String descripcion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();  
			long id = nextval ();
			long tuplasInsertadas = sqlServicio.adicionarServicio(pm, id, inicio, fin, personas, costo, nombre, descripcion);
			tx.commit();

			log.trace ("Inserción Habitacion: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
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

	public Reserva adicionarReserva(int personas, long inicio, long fin, double costo, String descripcion, char registrado, char pago, long idreserva,int idpago) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();  
			long id = nextval ();
			long tuplasInsertadas = sqlReserva.adicionarProducto(pm, id, inicio, fin, personas, costo, registrado, pago, idreserva, idpago);
			tx.commit();

			log.trace ("Inserción Habitacion: " + personas + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Reserva(id, inicio, fin, personas, costo, registrado, pago, idreserva, idpago);
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


	public PagoPSE adicionarPago(int numFactura, double numCuenta, String banco,double saldopagar, int identificacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();  
			long id = nextval ();
			long tuplasInsertadas = sqlPago.adicionarPago(pm,(int)id, numCuenta, banco, saldopagar,identificacion);
			tx.commit();

			log.trace ("Inserción Habitacion: " + numCuenta + ": " + tuplasInsertadas + " tuplas insertadas");
			return new PagoPSE((int)id, numCuenta, banco, saldopagar,identificacion);
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
	
	
	public Plan adicionarPlan(String nombre, String descripcion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();  
            long id = nextval ();
            long tuplasInsertadas = sqlPlan.adicionarPlan(pm, id, nombre, descripcion);
            tx.commit();
            
            log.trace ("Inserción Habitacion: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
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
            
            log.trace ("Inserción Habitacion: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
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
            
            
            log.trace ("Inserción Habitacion: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            return new Usuario(identificacion, tipoid, idTipo, correo, nombre);
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
	
	
	public Ofrecen adicionarOfrecen(long idServicio, long idProducto) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlOfrecen.adicionarOfrecen(pm, idServicio, idProducto);
            tx.commit();

            log.trace ("Inserción de gustan: [" + idServicio + ", " + idProducto + "]. " + tuplasInsertadas + " tuplas insertadas");

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
	
	
	
	public Contienen adicionarContienen(long idProducto, int numeroHabitacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlContienen.adicionarContienen(pm, idProducto, numeroHabitacion);
            tx.commit();

            log.trace ("Inserción de gustan: [" + numeroHabitacion + ", " + idProducto + "]. " + tuplasInsertadas + " tuplas insertadas");

            return new Contienen (idProducto, numeroHabitacion);
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

            log.trace ("Inserción de gustan: [" + numeroHabitacion + ", " + idServicio + "]. " + tuplasInsertadas + " tuplas insertadas");

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

            log.trace ("Inserción de gustan: [" + numeroHabitacion + ", " + idServicio + "]. " + tuplasInsertadas + " tuplas insertadas");

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
	
	public Hospedan adicionarHospedan(int identificacion, long idReserva) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlHospedan.adicionarHospedan(pm, identificacion, idReserva);
            tx.commit();

            log.trace ("Inserción de gustan: [" + identificacion + ", " + idReserva + "]. " + tuplasInsertadas + " tuplas insertadas");

            return new Hospedan (identificacion, idReserva);
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
	
	
	
	
	//Requerimientos Funcionales de busqueda//
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
	
	public List<long []> darHabitacionesYDineroRecolectado ()
	{
		List<long []> resp = new LinkedList<long []> ();
		List<Object []> tuplas =  sqlHabitacion.darHabitacionesYDineroRecolectado(pmf.getPersistenceManager());
        for ( Object [] tupla : tuplas)
        {
			long [] datosResp = new long [2];
			
			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
			resp.add (datosResp);
        }
        return resp;
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
	public List<Contienen> darContienen ()
	{
		return sqlContienen.darContienen (pmf.getPersistenceManager());
	}
	
	
	public List<Habitacion> darHabitaciones ()
	{
		return sqlHabitacion.darHabitaciones (pmf.getPersistenceManager());
	}
	
	public List<Hospedan> darHospedan ()
	{
		return sqlHospedan.darHospedan (pmf.getPersistenceManager());
	}
	public List<Ofrecen> darOfrecen ()
	{
		return sqlOfrecen.darOfrecen (pmf.getPersistenceManager());
	}
	public List<PagoPSE> darPagos ()
	{
		return sqlPago.darPagos (pmf.getPersistenceManager());
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
	
	
	


}
