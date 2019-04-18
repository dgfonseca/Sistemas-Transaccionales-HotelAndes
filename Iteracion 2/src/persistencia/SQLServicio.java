package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Reserva;
import negocio.Servicio;

class SQLServicio {

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
	public SQLServicio (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}



	public long adicionarServicio (PersistenceManager pm, long id, long inicio, long fin, int personas,double costo, String nombre, String descripcion) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaServicio () + "(id,  cantidadPersonas,costo,nombre,descripcion,fechaApertura, fechaCierre) values (?, ?, ?, ?,?,?,?)");
		q.setParameters(id, inicio, fin, personas,costo,nombre,descripcion);
		return (long) q.executeUnique();
	}



	public long eliminarServicioPorId (PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicio () + " WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}

	public long eliminarServicioPorNombre (PersistenceManager pm, String nombre)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicio () + " WHERE nombre = ?");
		q.setParameters(nombre);
		return (long) q.executeUnique();
	}





	public Servicio darServicioPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio () + " WHERE id = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(id);
		return (Servicio) q.executeUnique();
	}


	public List<Servicio> darServicioPorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio () + " WHERE nombre = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(nombre);
		return  q.executeList();
	}

	public List<Servicio> darServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio ());
		q.setResultClass(Servicio.class);
		List<Servicio> servicios = (List<Servicio>) q.executeList();
		return  servicios;
	}
	
	public List<Object[]> darServiciosObjeto (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio ());
		List<Object[]> servicios = (List<Object[]>) q.executeList();
		return  servicios;
	}

	public List<Object[]> dar20Servicios(PersistenceManager pm)
	{
		String sql1= "SELECT id, count(*) ";
		sql1+= "FROM "+ pp.darTablaSirven()+" sirv ";
		sql1+="INNER JOIN "+ pp.darTablaServicio() +" serv ";
		sql1+= "on serv.id=sirv.idServicio";
		sql1+=" INNER JOIN "+ pp.darTablaServiciosConvencion()+" servco ";
		sql1+=" ON SERV.ID=SERVCO.ID_SERVICIO ";
        sql1+="WHERE ROWNUM<=20 ";
        sql1+="GROUP BY id ";
        sql1+=" ORDER BY COUNT(ID) DESC";
		Query q = pm.newQuery(SQL, sql1);
		return  q.executeList();
	}
	
	public List<Object[]> darServiciosOcupados(PersistenceManager pm)
	{
		String sql = "Select s.id, sm.id_mantenimiento, m.fechaInicio, m.fechaFin\r\n" + 
				"From servicios s, servicio_mantenimiento sm, mantenimientos m\r\n" + 
				"Where s.id=sm.id_servicio and sm.id_mantenimiento = m.id  \r\n" + 
				"union all \r\n" + 
				"Select s.id, sc.id_reserva, rc.fechainicio, rc.fechafin\r\n" + 
				"From servicios s, servicios_convencion sc, reserva_convenciones rc\r\n" + 
				"Where s.id = sc.id_servicio and sc.id_reserva = rc.id";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
	
	public List<Object[]> darServiciosEnPrecio(PersistenceManager pm, double costo1, double costo2)
	{
		String sql = "Select * From " + pp.darTablaServicio() + " Where COSTO BETWEEN ? and ? ";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(costo1, costo2);
		return q.executeList();
	}
	
	public List<Object[]> darServiciosPosiblesEnHora(PersistenceManager pm, long hora)
	{
		String sql = "Select * from " + pp.darTablaServicio() + " where fechaApertura <= ? and fechaCierre >= ? ";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(hora, hora);
		return q.executeList();
	}
	
	public List<Object[]> darServiciosParaCapacidad(PersistenceManager pm, int num)
	{
		String sql = "Select * from " + pp.darTablaServicio() + " Where cantidadPersonas >= ? ";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(num);
		return q.executeList();
	}



}
