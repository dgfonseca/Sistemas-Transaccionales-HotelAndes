package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Ofrecen;

 class SQLOfrecen {

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
	public SQLOfrecen (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionarOfrecen(PersistenceManager pm, long idServicio, Long idProducto) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfrecen () + "(idServicio, idProducto) values (?, ?)");
        q.setParameters(idServicio, idProducto);
        return (long) q.executeUnique();
	}


	public long eliminarOfrecen (PersistenceManager pm, long idServicio, long idProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfrecen () + " WHERE idServicio = ? AND idProducto = ?");
        q.setParameters(idServicio, idProducto);
        return (long) q.executeUnique();
	}


	public List<Ofrecen> darOfrecen (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOfrecen ());
		q.setResultClass(Ofrecen.class);
		List<Ofrecen> resp = (List<Ofrecen>) q.execute();
		return resp;
	}

}
