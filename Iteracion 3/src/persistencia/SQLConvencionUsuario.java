package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Consumen;
import negocio.ConvencionUsuario;

class SQLConvencionUsuario {

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
	public SQLConvencionUsuario (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	@SuppressWarnings("rawtypes")
	public long adicionarContienen(PersistenceManager pm, long idConvencion, int idUsuario) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaConvencionUsuario() + "(ID_CONVENCION, ID_USUARIO) values (?, ?)");
        q.setParameters(idConvencion, idUsuario);
        return (long) q.executeUnique();
	}


	@SuppressWarnings("rawtypes")
	public long eliminarContienen (PersistenceManager pm, long idConvencion, int idUsuario)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaConvencionUsuario () + " WHERE ID_CONVENCION = ? AND ID_USUARIO = ?");
        q.setParameters(idConvencion, idUsuario);
        return (long) q.executeUnique();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ConvencionUsuario> darContienen (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaConvencionUsuario ());
		q.setResultClass(Consumen.class);
		List<ConvencionUsuario> resp = (List<ConvencionUsuario>) q.execute();
		return resp;
	}


}
