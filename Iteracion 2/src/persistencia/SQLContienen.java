package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Contienen;



class SQLContienen {

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
	public SQLContienen (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionarContienen(PersistenceManager pm, long idProducto, int numeroHabitacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContienen () + "(idProducto, numeroHabitacion) values (?, ?)");
        q.setParameters(idProducto, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public long eliminarContienen (PersistenceManager pm, long idProducto, int numeroHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContienen () + " WHERE idProducto = ? AND numeroHabitacion = ?");
        q.setParameters(idProducto, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public List<Contienen> darContienen (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContienen ());
		q.setResultClass(Contienen.class);
		List<Contienen> resp = (List<Contienen>) q.execute();
		return resp;
	}

}
