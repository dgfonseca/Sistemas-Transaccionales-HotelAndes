package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Apartan;
import negocio.Consumen;

class SQLApartan extends Object {

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
	public SQLApartan (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionaApartan(PersistenceManager pm, long idProducto, int numeroHabitacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaApartan () + "(idReserva, numeroHabitacion) values (?, ?)");
        q.setParameters(idProducto, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public long eliminarApartan (PersistenceManager pm, long idProducto, int numeroHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaApartan () + " WHERE idReserva = ? AND numeroHabitacion = ?");
        q.setParameters(idProducto, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public List<Apartan> darContienen (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaApartan ());
		q.setResultClass(Apartan.class);
		List<Apartan> resp = (List<Apartan>) q.execute();
		return resp;
	}

}
