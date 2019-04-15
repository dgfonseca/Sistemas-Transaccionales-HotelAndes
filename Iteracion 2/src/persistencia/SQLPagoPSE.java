package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.PagoPSE;

class SQLPagoPSE {
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
	private PersistenciaHotelAndes ph;

	public SQLPagoPSE(PersistenciaHotelAndes pph)
	{
		this.ph=pph;
	}

	public long adicionarPago(PersistenceManager pm, int pnumFactura, double numCuenta, String banco , double saldopagar, int ide)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaPagoPSE () + "(numFactura, numCuenta, banco, saldoPagar, identificacion) values (?,?, ?, ?, ?)");
		q.setParameters(pnumFactura, numCuenta, banco, saldopagar,ide);
		return (long) q.executeUnique();
	}

	public long eliminarFacturasPorNumeroFactura (PersistenceManager pm, int numFactura)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaPagoPSE () + " WHERE numeroFactura = ?");
		q.setParameters(numFactura);
		return (long) q.executeUnique();
	}

	public List<PagoPSE> darPagosPorNumeroCuenta (PersistenceManager pm, double numcuenta) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPagoPSE () + " WHERE numeroCuenta = ?");
		q.setResultClass(PagoPSE.class);
		q.setParameters(numcuenta);
		return (List<PagoPSE>) q.executeList();
	}


public List<PagoPSE> darPagos (PersistenceManager pm)
{
	Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPagoPSE ());
	q.setResultClass(PagoPSE.class);
	return (List<PagoPSE>) q.executeList();
}
}
