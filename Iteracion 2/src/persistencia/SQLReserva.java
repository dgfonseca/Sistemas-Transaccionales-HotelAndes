package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Producto;
import negocio.Reserva;

class SQLReserva {

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
	public SQLReserva (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	
	public long adicionarProducto (PersistenceManager pm, long id, long inicio, long fin, int personas,double costo, char registrado, char pago,long idreserva, int idPago) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva () + "(id, fechaInicio, fechaFin, cantidadPersonas,costo,estaRegistrado,estaPago, idPlan, numeroFactura) values (?, ?, ?, ?,?,?,?,?,?)");
        q.setParameters(id, inicio, fin, personas,costo,registrado,pago, idreserva, idPago);
        return (long) q.executeUnique();
	}

	
	
	public long eliminarReservaPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
	

	


	public Reserva darReservaPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva () + " WHERE id = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(id);
		return (Reserva) q.executeUnique();
	}
	


	


	public List<Reserva> darReservas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva ());
		q.setResultClass(Reserva.class);
		return (List<Reserva>) q.executeList();
	}

}
