package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Habitacion;



class SQLHabitacion {

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
	public SQLHabitacion (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	
	@SuppressWarnings("rawtypes")
	public long adicionarHabitacion (PersistenceManager pm, int capacidad, int numeroHabitacion, int costo, String descripcion, String disponible) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHabitacion() + " (CAPACIDAD, NUMEROHABITACION , COSTO , DESCRIPCION, DISPONIBLE) values ( ?,?,?,?,?)");
        q.setParameters(capacidad, numeroHabitacion, costo, descripcion, disponible);
        return (long) q.executeUnique();
	}

	
	
	@SuppressWarnings("rawtypes")
	public long eliminarHabitacionPorNumero (PersistenceManager pm, int numHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacion () + " WHERE numeroHabitacion = ?");
        q.setParameters(numHabitacion);
        return (long) q.executeUnique();
	}

	

	
	@SuppressWarnings("rawtypes")
	public Habitacion darHabitacionPorNumero (PersistenceManager pm, int numHabitacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacion () + " WHERE numeroHabitacion = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(numHabitacion);
		return (Habitacion) q.executeUnique();
	}

	


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Habitacion> darHabitaciones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHabitacion ());
		q.setResultClass(Habitacion.class);
		return (List<Habitacion>) q.executeList();
	}
	
	
	//RF1
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object[]> darHabitacionesYDineroRecolectado (PersistenceManager pm, long inicio, long fin)
	{		
		String sql1 = "SELECT hab.numeroHabitacion, sum (serv.costo)";
		sql1 += " FROM " + pp.darTablaHabitacion()+"  hab";
		sql1+= " INNER JOIN "+pp.darTablaSirven()+ " sirv";
		sql1+=" on hab.numerohabitacion=sirv.numerohabitacion";
		sql1+= " INNER JOIN "+ pp.darTablaServicio() +" serv";
		sql1+= " on sirv.idservicio=serv.id";
		sql1+= " INNER JOIN "+pp.darTablaApartan()+" ap";
		sql1+= "  ON HAB.NUMEROHABITACION=AP.NUMEROHABITACION";
		sql1+=" INNER JOIN "+pp.darTablaReserva() +" res";
		sql1+=" ON AP.IDRESERVA=RES.ID";
		sql1+=" WHERE RES.FECHAINICIO BETWEEN " +inicio+" AND "+fin;
		sql1 += " GROUP BY hab.numeroHabitacion";
		

		Query q = pm.newQuery(SQL, sql1);
		return q.executeList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object[]> darIndiceOcupacionHabitacion (PersistenceManager pm )
	{
        String sql1 = "SELECT numeroHabitacion";
        sql1 += " FROM " + pp.darTablaApartan();
       	
       	String sql = "SELECT count (*) FROM (" + sql1 + ")";
		Query q = pm.newQuery(SQL, sql);
        return q.executeList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Habitacion> darHabitacionesCapacidad (PersistenceManager pm, int cap)
	{
		String sql1 = "SELECT *";
		sql1 += " FROM " + pp.darTablaHabitacion();
		sql1 += " WHERE CAPACIDAD = " + cap;
		
		Query q = pm.newQuery(SQL, sql1);
		q.setResultClass(Habitacion.class);
		return (List<Habitacion>) q.executeList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object[]> darHabitacionesOcupadas(PersistenceManager pm)
	{
		String sql = "select r.fechaInicio, r.fechafin, h.numerohabitacion\r\n" + 
				"from reserva r, apartan a, habitacion h\r\n" + 
				"where r.id = a.idreserva and a.numerohabitacion = h.numerohabitacion\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select rc.fechainicio, rc.fechafin, h.numeroHabitacion\r\n" + 
				"from reserva_convenciones rc, habitacion_convencion hc, habitacion h\r\n" + 
				"where rc.id = hc.id_reserva and hc.numerohabitacion = h.numerohabitacion";
		
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}

}
