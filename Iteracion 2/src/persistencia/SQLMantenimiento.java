package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Mantenimiento;
import negocio.Servicio;

class SQLMantenimiento {

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
	public SQLMantenimiento (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}



	public long adicionarMantenimiento (PersistenceManager pm, long id, long inicio, long fin, String descripcion) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaMantenimientos() + "(id, fechainicio, fechafin,descripcion) values (?, ?, ?, ?)");
		q.setParameters(id, inicio, fin,descripcion);
		return (long) q.executeUnique();
	}



	public List<Mantenimiento> darMantenimientos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaMantenimientos ());
		q.setResultClass(Mantenimiento.class);
		return (List<Mantenimiento>) q.executeList();
	}
	
	public long eliminarMantenimiento (PersistenceManager pm, long idMantenimiento)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaMantenimientos() + " WHERE ID = ? ");
		q.setParameters(idMantenimiento);
		return (long) q.executeUnique();
	}

	

}
