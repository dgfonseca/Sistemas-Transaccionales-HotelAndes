package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Apartan;
import negocio.HabitacionConvencion;

public class SQLHabitacionConvencion {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra ac� para facilitar la escritura de las sentencias
	 */
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
	public SQLHabitacionConvencion (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionaHabitacionConvencion(PersistenceManager pm, long idReserva, int numeroHabitacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHabitacionConvencion() + "(id_Reserva, numeroHabitacion) values (?, ?)");
        q.setParameters(idReserva, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public long eliminarHabitacionConvencion (PersistenceManager pm, long idreserva, int numeroHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacionConvencion() + " WHERE id_Reserva = ? AND numeroHabitacion = ?");
        q.setParameters(idreserva, numeroHabitacion);
        return (long) q.executeUnique();
	}


	public List<HabitacionConvencion> darHabitacionConvencion	 (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacionConvencion ());
		q.setResultClass(HabitacionConvencion.class);
		List<HabitacionConvencion> resp = (List<HabitacionConvencion>) q.execute();
		return resp;
	}
	
	public List<Object[]> darHabitacionConvencionIdConvencion(PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacionConvencion() + " WHERE ID_RESERVA = ?");
		q.setParameters(id);
		List<Object[]> resp = (List<Object[]>) q.executeList();
		return resp;
	}

}
