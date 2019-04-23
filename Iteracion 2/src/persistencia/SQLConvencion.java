package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Convencion;

class SQLConvencion {

	private final static String SQL = PersistenciaHotelAndes.SQL;
	private PersistenciaHotelAndes pp;

	public SQLConvencion(PersistenciaHotelAndes ppp) {
		this.pp=ppp;
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("rawtypes")
	public long adicionarConvencion(PersistenceManager pm, long id, String nombre, String descripcion)
	{
		Query q = pm.newQuery(SQL,"INSERT INTO " + pp.darTablaConvenciones() + " (id,nombre,descripcion) values(?,?,?)");
		q.setParameters(id,nombre,descripcion);
		return (long) q.executeUnique();
	}
	@SuppressWarnings("rawtypes")
	public long eliminarConvencion(PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaConvenciones() + " WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Convencion> darConvenciones(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ pp.darTablaConvenciones());
		q.setResultClass(Convencion.class);
		List<Convencion> rta = (List<Convencion>) q.execute();
		return rta;
	}
	
	@SuppressWarnings("rawtypes")
	public String darNombreConvencion(PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "SELECT NOMBRE FROM " + pp.darTablaConvenciones() + " WHERE ID = ?");
		q.setParameters(id);
		return (String) q.executeUnique();
	}

}
