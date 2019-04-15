package persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


class SQLUtil {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaHotelAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaHotelAndes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLUtil (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	
	
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqHotelandes () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}


	public long [] limpiarHotelandes (PersistenceManager pm)
	{
        Query qContienen = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContienen ());          
        Query qHabitacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacion ());
        Query qHospedan = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHospedan() );
        Query qOfrecen = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfrecen() );
        Query qPagoPse = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPagoPSE());
        Query qPlan = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPlan());
        Query qProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto() );
        Query qReserva = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva() );
        Query qServicio = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicio());
        Query qSirven = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSirven() );
        Query qUsuario = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaUsuario());

        

        long contienenEliminados = (long) qContienen.executeUnique ();
        long habitacionEliminados = (long) qHabitacion.executeUnique ();
        long hospedanEliminadas = (long) qHospedan.executeUnique ();
        long ofrecenEliminadas = (long) qOfrecen.executeUnique ();
        long pagosEliminados = (long) qPagoPse.executeUnique ();
        long planEliminados = (long) qPlan.executeUnique ();
        long productoEliminados = (long) qProducto.executeUnique ();
        long reservaEliminados = (long) qReserva.executeUnique ();
        long servicioEliminados = (long) qServicio.executeUnique ();
        long sirvenEliminados = (long) qSirven.executeUnique ();
        long usuarioEliminados = (long) qUsuario.executeUnique ();

        return new long[] {contienenEliminados, habitacionEliminados, hospedanEliminadas, ofrecenEliminadas, 
        		pagosEliminados, planEliminados, productoEliminados,reservaEliminados,servicioEliminados,sirvenEliminados,usuarioEliminados};
	}

}
