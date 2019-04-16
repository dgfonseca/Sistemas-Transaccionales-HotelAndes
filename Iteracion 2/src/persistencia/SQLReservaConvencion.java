package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Consumen;
import negocio.Convencion;
import negocio.ReservaConvencion;

class SQLReservaConvencion {

	private final static String SQL = PersistenciaHotelAndes.SQL;
	private PersistenciaHotelAndes pp;

	public SQLReservaConvencion(PersistenciaHotelAndes ppp) {
		this.pp=ppp;
		// TODO Auto-generated constructor stub
	}
	
	public long adicionarReservaConvencion(PersistenceManager pm, long id, long fechaInicio, long fechaFin, double costo, long idplan, long idconvencion)
	{
		Query q = pm.newQuery(SQL,"INSERT INTO " + pp.darTablaReservaConvenciones() + " (id,fechainicio,fechafin,costo,id_plan,id_convencion) values(?,?,?,?,?,?)");
		q.setParameters(id,fechaInicio,fechaFin,costo,idplan,idconvencion);
		return (long) q.executeUnique();
	}
	public long eliminarReservaConvencion(PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReservaConvenciones() + " WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}
	
	public List<ReservaConvencion> darReservaConvenciones(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ pp.darTablaReservaConvenciones());
		q.setResultClass(Consumen.class);
		List<ReservaConvencion> rta = (List<ReservaConvencion>) q.execute();
		return rta;
	}

}
