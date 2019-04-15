package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Contienen;
import negocio.Sirven;

class SQLSirven {

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
	public SQLSirven (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionarSirven(PersistenceManager pm, long idServicio, int numeroHabitacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaSirven () + "(idServicio, numeroHabitacion) values (?, ?)");
        q.setParameters(idServicio, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public long eliminarSirven (PersistenceManager pm, long idProducto, int numeroHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSirven () + " WHERE idServicio = ? AND numeroHabitacion = ?");
        q.setParameters(idProducto, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public List<Sirven> darSirven (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSirven ());
		q.setResultClass(Sirven.class);
		List<Sirven> resp = (List<Sirven>) q.execute();
		return resp;
	}


}
