package persistencia;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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



	@SuppressWarnings("rawtypes")
	public long adicionarUsuario (PersistenceManager pm, int identificacion, String nombre, String tipoid, String correo, long idTipo) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaUsuario () + "(identificacion, nombre, tipoIdentificacion, correo, idTipo ) values ( ?, ?, ?, ?,?)");
		q.setParameters(identificacion, nombre, tipoid, correo, idTipo);
		return (long) q.executeUnique();
	}



	@SuppressWarnings("rawtypes")
	public long eliminarUsuarioPorIdentificacion (PersistenceManager pm, int identificacion)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaUsuario () + " WHERE identificacion = ?");
		q.setParameters(identificacion);
		return (long) q.executeUnique();
	}




	@SuppressWarnings("rawtypes")
	public List<Usuario> darUsuarioPorIdentificacion (PersistenceManager pm, long identificacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaUsuario () + " WHERE identificacion = ?");
		q.setParameters(identificacion);
		List<Usuario> resp= new LinkedList<>();
		List results=q.executeList();
		for(Object obj:results)
		{
			Object[] datos = (Object[]) obj;
			long ident =  ((BigDecimal) datos [0]).longValue ();
			String nombre=(String)datos[1];
			String tipoIdent=(String)datos[2];
			String correo=(String)datos[3];
			long iditpo =  ((BigDecimal) datos [4]).longValue ();
			resp.add(new Usuario(ident, nombre, tipoIdent, correo, iditpo));
		}
		return resp;
	}




	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Usuario> darUsuarios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaUsuario ());
		q.setResultClass(Usuario.class);
		return (List<Usuario>) q.executeList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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

	@SuppressWarnings("rawtypes")
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
			resp.add(new Usuario(identificacion, nombre, tipoIdent, correo, iditpo));

		}
		return resp;


	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object[]> darConsumoPorUsuarioDado(PersistenceManager pm,long id,long ini,long fin)
	{
		String sql1="SELECT US.IDENTIFICACION, SUM(RES.COSTO) ";
		sql1+=" FROM "+ pp.darTablaUsuario() +" US";
		sql1+=" INNER JOIN "+pp.darTablaReserva() +" res";
		sql1+=" ON US.IDENTIFICACION=RES.ID_USUARIO ";
		sql1+="WHERE(us.identificacion=?) and (res.fechainicio between ? and ?) and (res.fechafin between ? and ?)";
		sql1+=" GROUP BY US.IDENTIFICACION";
		Query q=pm.newQuery(SQL,sql1);
		q.setParameters(id,ini,fin,ini,fin);
		return q.executeList();
	}



	@SuppressWarnings("unchecked")
	public List<Object[]> requerimientoFuncionalConsulta9(PersistenceManager pm,long id,long ini,long fin)
	{
		String sql1=" Select us.nombre, us.identificacion, count(*) ";
		sql1+="from "+ pp.darTablaUsuario()+ " us " ;
		sql1+=" inner join "+pp.darTablaReserva() +" res" ;
		sql1+=" on us.identificacion=res.id_usuario ";
		sql1+=" inner join "+ pp.darTablaApartan()+ " ap " ;
		sql1+=" on res.id=ap.idreserva " ;
		sql1+=" inner join "+pp.darTablaHabitacion()+" hab " ;
		sql1+=" on ap.numerohabitacion=hab.numerohabitacion " ;
		sql1+=" inner join "+ pp.darTablaSirven()+ " sirv " ;
		sql1+=" on hab.numerohabitacion=sirv.numerohabitacion " ;
		sql1+=" where (sirv.FECHAUSO between "+ ini+ " and " +fin+" )  and  sirv.IDSERVICIO = " +id ; 
		sql1+=" group by us.identificacion, us.nombre ";
		@SuppressWarnings("rawtypes")
		Query q=pm.newQuery(SQL,sql1);
		return q.executeList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Object[]> requerimientoFuncionalConsulta10(PersistenceManager pm,long id,long ini,long fin)
	{
		
		String sql1="select nombre,identificacion from usuario where identificacion not in( Select us.identificacion from usuario us inner join reserva res on us.identificacion=res.id_usuario";
			   sql1+="	inner join apartan ap on res.id=ap.idreserva inner join	habitacion hab on ap.numerohabitacion=hab.numerohabitacion ";
				sql1+="inner join sirven sirv on hab.numerohabitacion=sirv.numerohabitacion where sirv.FECHAUSO between "+ ini+" and "+ fin+ " and sirv.IDSERVICIO="+id+")";
				Query q=pm.newQuery(SQL,sql1);
				return q.executeList();
		
		
	}
	
	
	
	
	
}
