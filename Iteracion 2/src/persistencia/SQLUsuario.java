package persistencia;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Habitacion;
import negocio.Usuario;

class SQLUsuario {

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
	public SQLUsuario (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	
	public long adicionarUsuario (PersistenceManager pm, int identificacion, String nombre, String tipoid, String correo, long idTipo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaUsuario () + "(identificacion, nombre, tipoIdentificacion, correo, idTipo ) values ( ?, ?, ?, ?,?)");
        q.setParameters(identificacion, nombre, tipoid, correo, idTipo);
        return (long) q.executeUnique();
	}

	
	
	public long eliminarUsuarioPorIdentificacion (PersistenceManager pm, int identificacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaUsuario () + " WHERE identificacion = ?");
        q.setParameters(identificacion);
        return (long) q.executeUnique();
	}

	

	
	public Usuario darUsuarioPorIdentificacion (PersistenceManager pm, int identificacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaUsuario () + " WHERE identificacion = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(identificacion);
		return (Usuario) q.executeUnique();
	}

	


	public List<Usuario> darUsuarios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaUsuario ());
		q.setResultClass(Usuario.class);
		return (List<Usuario>) q.executeList();
	}
	
	public List<Object[]> darConsumoUsuario (PersistenceManager pm, String pnombre)
	{		
		String sql1 = "SELECT user.nombre, sum (reserva.costo) as numVisitas";
		sql1 += " FROM " + pp.darTablaUsuario()+ " user";
		sql1 += "INNER JOIN" + pp.darTablaHospedan() + "hospeda";
		sql1+= "ON user.identificacion = hospeda.identificacion";
		sql1+= "INNER JOIN"+ pp.darTablaReserva()+ "reserva";
		sql1+= "on hospeda.idreserva=reserva.id";
		sql1+="WHERE user.nombre="+pnombre;
		sql1 += " GROUP BY user.nombre";
		Query q = pm.newQuery(SQL, sql1);
		return q.executeList();
	}
	
	public List<Usuario> darBuenosClientes(PersistenceManager pm)
	{
		Query q=pm.newQuery(SQL,"SELECT US.IDENTIFICACION,US.NOMBRE,US.TIPOIDENTIFICACION,US.CORREO,us.idtipo,SUM(RES.COSTO) FROM " + pp.darTablaUsuario() +" us INNER JOIN "+ pp.darTablaReserva()+" RES ON RES.ID_USUARIO=US.IDENTIFICACION GROUP BY US.IDENTIFICACION,US.NOMBRE,US.TIPOIDENTIFICACION,US.CORREO, us.idtipo HAVING SUM(RES.COSTO)>15000000");
		List<Usuario> resp= new LinkedList<>();
		List results=q.executeList();
		for(Object obj:results)
		{
			Object[] datos = (Object[]) obj;
			long identificacion =  ((BigDecimal) datos [0]).longValue ();
			String nombre=(String)datos[1];
			String tipoIdent=(String)datos[2];
			String correo=(String)datos[3];
			long iditpo =  ((BigDecimal) datos [4]).longValue ();
			resp.add(new Usuario(identificacion, tipoIdent, iditpo, correo, nombre));
			
		}
		return resp;
		
		
	}

}
