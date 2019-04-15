package persistencia;

import java.math.BigDecimal;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Habitacion;
import oracle.security.o5logon.d;



class SQLHabitacion {

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
	public SQLHabitacion (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	
	public long adicionarHabitacion (PersistenceManager pm, int capacidad, int numeroHabitacion, double costo, String descripcion, char disponible, long idReserva) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHabitacion () + "(capacidad, numeroHabitacion, costo, descripcion, disponible, idReserva) values (?, ?, ?, ?, ?,?)");
        q.setParameters(capacidad, numeroHabitacion, costo, descripcion, disponible, idReserva);
        return (long) q.executeUnique();
	}

	
	
	public long eliminarHabitacionPorNumero (PersistenceManager pm, int numHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacion () + " WHERE numeroHabitacion = ?");
        q.setParameters(numHabitacion);
        return (long) q.executeUnique();
	}

	

	
	public Habitacion darHabitacionPorNumero (PersistenceManager pm, int numHabitacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacion () + " WHERE numeroHabitacion = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(numHabitacion);
		return (Habitacion) q.executeUnique();
	}

	


	public List<Habitacion> darHabitaciones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacion ());
		q.setResultClass(Habitacion.class);
		return (List<Habitacion>) q.executeList();
	}
	
	
	//RF1
	public List<Object[]> darHabitacionesYDineroRecolectado (PersistenceManager pm)
	{		
		String sql1 = "SELECT numeroHabitacion, sum (reser.costo) as dinero";
		sql1 += " FROM " + pp.darTablaHabitacion()+" habita";
		sql1+= "INNER JOIN "+pp.darTablaApartan()+ "apart";
		sql1+="on habita.numerohabitacion=apart.numerohabitacion";
		sql1+= "INNER JOIN "+ pp.darTablaReserva() +" reser";
		sql1+= "on apart.idreserva=reser.id";
		sql1 += " GROUP BY numeroHabitacion";
		

		Query q = pm.newQuery(SQL, sql1);
		return q.executeList();
	}
	
	public List<Object[]> darIndiceOcupacionHabitacion (PersistenceManager pm )
	{
        String sql1 = "SELECT numeroHabitacion";
        sql1 += " FROM " + pp.darTablaApartan();
       	
       	String sql = "SELECT count (*) FROM (" + sql1 + ")";
		Query q = pm.newQuery(SQL, sql);
        return q.executeList();
}

}
