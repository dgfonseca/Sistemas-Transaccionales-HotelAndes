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
	public SQLServicio (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}



	public long adicionarServicio (PersistenceManager pm, long id, long inicio, long fin, int personas,double costo, String nombre, String descripcion) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaServicio () + "(id, fechaApertura, fechaCierre, cantidadPersonas,costo,nombre,descripcion) values (?, ?, ?, ?,?,?,?)");
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


	public Servicio darServicioPorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio () + " WHERE nombre = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(nombre);
		return (Servicio) q.executeUnique();
	}






	public List<Servicio> darServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio ());
		q.setResultClass(Servicio.class);
		return (List<Servicio>) q.executeList();
	}

	public List<Servicio> dar20Servicios(PersistenceManager pm)
	{
		String sql1= "SELECT id, count(*) ";
		sql1+= "FROM "+ pp.darTablaSirven()+" sirv ";
		sql1+="INNER JOIN "+ pp.darTablaServicio() +" serv";
		sql1+= "on serv.id=sirv.idServicio";
		sql1+=" INNER JOIN "+ pp.darTablaServiciosConvencion()+" servco ";
		sql1+=" on  ON SERV.ID=SERVCO.ID_SERVICIO ";
        sql1+="WHERE ROWNUM<=20";
        sql1+="GROUP BY id ";
        sql1+=" ORDER BY COUNT(ID) DESC";
		Query q = pm.newQuery(SQL, sql1);
		q.setResultClass(Servicio.class);
		return (List<Servicio>) q.executeList();
	}



}
