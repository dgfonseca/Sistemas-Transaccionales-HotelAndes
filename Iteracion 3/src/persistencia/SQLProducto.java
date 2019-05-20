package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Producto;

class SQLProducto {

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
	public SQLProducto (PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	

	
	@SuppressWarnings("rawtypes")
	public long adicionarProducto (PersistenceManager pm, long id, String nombre, double costo, int cantidad) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProducto () + "(id, nombre, costo, cantidad) values (?, ?, ?, ?)");
        q.setParameters(id, nombre, costo, cantidad);
        return (long) q.executeUnique();
	}

	
	
	@SuppressWarnings("rawtypes")
	public long eliminarProductoPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
	
	@SuppressWarnings("rawtypes")
	public long eliminarProductoPorNombre (PersistenceManager pm, String nom)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto () + " WHERE nombre = ?");
        q.setParameters(nom);
        return (long) q.executeUnique();
	}

	


	@SuppressWarnings("rawtypes")
	public Producto darProductoPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE id = ?");
		q.setResultClass(Producto.class);
		q.setParameters(id);
		return (Producto) q.executeUnique();
	}
	
	@SuppressWarnings("rawtypes")
	public Producto darProductoPorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE nombre = ?");
		q.setResultClass(Producto.class);
		q.setParameters(nombre);
		return (Producto) q.executeUnique();
	}

	


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Producto> darProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto ());
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
}
