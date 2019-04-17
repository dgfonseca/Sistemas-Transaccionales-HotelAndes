package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Apartan;
import negocio.HabitacionMantenimiento;

class SQLHabitacionMantenimiento {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra ac� para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaHotelAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicaci�n
	 */
	private PersistenciaHotelAndes pp;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLHabitacionMantenimiento (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionaHabitacionMantenimiento(PersistenceManager pm, long idProducto, int numeroHabitacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHabitacionMantenimiento () + "(id_mantenimiento, numeroHabitacion) values (?, ?)");
        q.setParameters(idProducto, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public long eliminarApartan (PersistenceManager pm, long idProducto, int numeroHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacionMantenimiento() + " WHERE id_mantenimiento = ? AND numeroHabitacion = ?");
        q.setParameters(idProducto, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public List<HabitacionMantenimiento> darHabitacionMantenimiento (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacionMantenimiento ());
		q.setResultClass(HabitacionMantenimiento.class);
		List<HabitacionMantenimiento> resp = (List<HabitacionMantenimiento>) q.execute();
		return resp;
	}
	
	public List<Object[]> darHabitacionesEnMantenimientoFecha(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "select *\n" + 
				"from mantenimientos m, habitacion_mantenimientos hm\n" + 
				"where m.id = hm.id_mantenimiento");
		List<Object[]> resp = (List<Object[]>) q.executeList();
		return resp;
	}


}
