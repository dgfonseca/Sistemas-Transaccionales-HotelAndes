package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Tipo;



class SQLTipo {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
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
	public SQLTipo (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	
	
	@SuppressWarnings("rawtypes")
	public long adicionarTipo (PersistenceManager pm, long idTipoBebida, String nombre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaTipo  () + "(id, nombre) values (?, ?)");
        q.setParameters(idTipoBebida, nombre);
        return (long) q.executeUnique();            
	}

	
	@SuppressWarnings("rawtypes")
	public long eliminarTipoNombre (PersistenceManager pm, String nombreTipoBebida)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTipo  () + " WHERE nombre = ?");
        q.setParameters(nombreTipoBebida);
        return (long) q.executeUnique();            
	}


	@SuppressWarnings("rawtypes")
	public long eliminarTipoPorId (PersistenceManager pm, long idTipoBebida)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTipo  () + " WHERE id = ?");
        q.setParameters(idTipoBebida);
        return (long) q.executeUnique();            
	}


	@SuppressWarnings("rawtypes")
	public Tipo darTipoPorId (PersistenceManager pm, long idTipoBebida) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTipo  () + " WHERE id = ?");
		q.setResultClass(Tipo.class);
		q.setParameters(idTipoBebida);
		return (Tipo) q.executeUnique();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Tipo> darTiposPorNombre (PersistenceManager pm, String nombreTipoBebida) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTipo  () + " WHERE nombre = ?");
		q.setResultClass(Tipo.class);
		q.setParameters(nombreTipoBebida);
		return (List<Tipo>) q.executeList();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Tipo> darTipos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTipo  ());
		q.setResultClass(Tipo.class);
		return (List<Tipo>) q.executeList();
	}
}
