package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Apartan;
import negocio.ServicioMantenimiento;

class SQLServicioMantenimiento {

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
	public SQLServicioMantenimiento (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionaServicioMantenimiento(PersistenceManager pm, long idmantenimiento, int idservicio) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaServicioMantenimiento () + "(id_mantenimiento, id_servicio) values (?, ?)");
        q.setParameters(idmantenimiento, idservicio);
        return (long) q.executeUnique();
	}


	public long eliminarServicioMantenimiento (PersistenceManager pm, long idProducto, int numeroHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicioMantenimiento () + " WHERE id_mantenimiento = ? AND id_servicio = ?");
        q.setParameters(idProducto, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public List<ServicioMantenimiento> darServicioMantenimiento (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicioMantenimiento ());
		q.setResultClass(ServicioMantenimiento.class);
		List<ServicioMantenimiento> resp = (List<ServicioMantenimiento>) q.execute();
		return resp;
	}
	
	public List<Object[]> darServiciosEnMantenimientoFecha(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "select *\n" + 
				"from mantenimientos m, servicio_mantenimiento sm\n" + 
				"where m.id = sm.id_mantenimiento");
		List<Object[]> resp = (List<Object[]>) q.executeList();
		return resp;
	}
	
	public long eliminarServicioMantenimientoIdMantenimiento(PersistenceManager pm, long idMantenimiento)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicioMantenimiento() + " WHERE ID_MANTENIMIENTO = ? ");
		q.setParameters(idMantenimiento);
		return (long) q.executeUnique();
	}
}
