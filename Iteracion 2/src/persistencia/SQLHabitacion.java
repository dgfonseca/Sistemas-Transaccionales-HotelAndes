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
	

	
	public long adicionarHabitacion (PersistenceManager pm, int capacidad, int numeroHabitacion, double costo, String descripcion, char disponible) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHabitacion () + "(capacidad, numeroHabitacion, costo, descripcion, disponible) values (?, ?, ?, ?, ?)");
        q.setParameters(capacidad, numeroHabitacion, costo, descripcion, disponible);
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
	public List<Object[]> darHabitacionesYDineroRecolectado (PersistenceManager pm, long inicio, long fin)
	{		
		String sql1 = "SELECT hab.numeroHabitacion, sum (serv.costo) as dinero";
		sql1 += " FROM " + pp.darTablaHabitacion()+"  hab";
		sql1+= " INNER JOIN "+pp.darTablaSirven()+ " sirv";
		sql1+=" on hab.numerohabitacion=sirv.numerohabitacion";
		sql1+= "INNER JOIN "+ pp.darTablaServicio() +" serv";
		sql1+= "on sirv.idservicio=serv.id";
		sql1+= "INNER JOIN "+pp.darTablaApartan()+" ap";
		sql1+= " on ON HAB.NUMEROHABITACION=AP.NUMEROHABITACION";
		sql1+=" INNER JOIN "+pp.darTablaReserva() +" res";
		sql1+=" ON AP.IDRESERVA=RES.ID";
		sql1+=" WHERE RES.FECHAINICIO IS BETWEEN " +inicio+" AND "+fin;
		sql1 += " GROUP BY hab.numeroHabitacion";
		

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
