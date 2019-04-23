package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import negocio.Plan;

class SQLPlan {

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
	private PersistenciaHotelAndes ph;

	public SQLPlan(PersistenciaHotelAndes pph)
	{
		this.ph=pph;
	}

	@SuppressWarnings("rawtypes")
	public long adicionarPlan(PersistenceManager pm, long id,String nombre, String descripcion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaPlan () + "(id,nombre, descripcion) values (?,?,?)");
		q.setParameters(id, nombre, descripcion);
		return (long) q.executeUnique();
	}

	@SuppressWarnings("rawtypes")
	public long eliminarPlanPorNombre (PersistenceManager pm, String nombre)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaPlan()+ " WHERE nombre = ?");
		q.setParameters(nombre);
		return (long) q.executeUnique();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Plan> darPlanesPorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPlan () + " WHERE nombre = ?");
		q.setResultClass(Plan.class);
		q.setParameters(nombre);
		return (List<Plan>) q.executeList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Plan> darPlanesPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPlan () + " WHERE id = ?");
		q.setResultClass(Plan.class);
		q.setParameters(id);
		return (List<Plan>) q.executeList();
	}


@SuppressWarnings({ "rawtypes", "unchecked" })
public List<Plan> darPlanes (PersistenceManager pm)
{
	Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPlan ());
	q.setResultClass(Plan.class);
	return (List<Plan>) q.executeList();
}

}
