package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Apartan;
import negocio.ServiciosConvencion;

class SQLServiciosConvencion {

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
	public  SQLServiciosConvencion(PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionaServiciosConvencion(PersistenceManager pm, long idreserva, int idServicio) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaServiciosConvencion () + "(id_Reserva, id_servicio) values (?, ?)");
        q.setParameters(idreserva, idServicio);
        return (long) q.executeUnique();
	}


	public long eliminarServiciosConvencion (PersistenceManager pm, long idReserva, int idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServiciosConvencion () + " WHERE id_Reserva = ? AND id_servicio = ?");
        q.setParameters(idReserva, idServicio);
        return (long) q.executeUnique();
	}


	public List<ServiciosConvencion> darServiciosConvencion (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServiciosConvencion ());
		q.setResultClass(Apartan.class);
		List<ServiciosConvencion> resp = (List<ServiciosConvencion>) q.execute();
		return resp;
	}
	
	public List<Object[]> darServiciosConvencionIdConvencion(PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServiciosConvencion() + " WHERE ID_RESERVA = ?");
		q.setParameters(id);
		List<Object[]> resp = (List<Object[]>) q.executeList();
		return resp;
	}
	


}
