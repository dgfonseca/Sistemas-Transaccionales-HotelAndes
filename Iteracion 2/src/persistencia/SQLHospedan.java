package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Contienen;
import negocio.Hospedan;

class SQLHospedan {

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
	public SQLHospedan (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionarHospedan(PersistenceManager pm, int identificacion, Long idReserva) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHospedan () + "(identificacion, idReserva) values (?, ?)");
        q.setParameters(identificacion, idReserva);
        return (long) q.executeUnique();
	}


	public long eliminarHospedan (PersistenceManager pm, int identificacion, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHospedan () + " WHERE identificacion = ? AND idReserva = ?");
        q.setParameters(identificacion, idReserva);
        return (long) q.executeUnique();
	}


	public List<Hospedan> darHospedan (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHospedan ());
		q.setResultClass(Hospedan.class);
		List<Hospedan> resp = (List<Hospedan>) q.execute();
		return resp;
	}
}
