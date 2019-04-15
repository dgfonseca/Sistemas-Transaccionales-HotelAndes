package negocio;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import persistencia.PersistenciaHotelAndes;


public class HotelAndes {

	private static Logger log = Logger.getLogger(HotelAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaHotelAndes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public HotelAndes ()
	{
		pp = PersistenciaHotelAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public HotelAndes (JsonObject tableConfig)
	{
		pp = PersistenciaHotelAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	public Apartan adicionarApartan (long idServicio,int numeroHabitacion)
	{
        log.info ("Adicionando Apartan: " + idServicio+" "+  numeroHabitacion);
        Apartan tipoBebida = pp.adicionarApartan(idServicio, numeroHabitacion);		
        log.info ("Adicionando Apartan: " + tipoBebida);
        return tipoBebida;
	}
	
	public Contienen adicionarContienen (long idProducto,int numeroHabitacion)
	{
        log.info ("Adicionando Contienen: " + idProducto+" "+  numeroHabitacion);
        Contienen tipoBebida = pp.adicionarContienen(idProducto, numeroHabitacion);		
        log.info ("Adicionando Contienen: " + tipoBebida);
        return tipoBebida;
	}
	
	public Habitacion adicionarHabitacion (int capacidad,int numeroHabitacion, double costo, String descripcion, char disponible, long idReserva)
	{
        log.info ("Adicionando Habitacion: " + descripcion+" "+  numeroHabitacion);
        Habitacion tipoBebida = pp.adicionarHabitacion(capacidad, numeroHabitacion, costo, descripcion, disponible, idReserva);		
        log.info ("Adicionando HAbitacion: " + tipoBebida);
        return tipoBebida;
	}
	
	public Ofrecen adicionarOfrecen (long idServicio,long idProducto)
	{
        log.info ("Adicionando Ofrecen: " + idServicio+" "+  idProducto);
        Ofrecen tipoBebida = pp.adicionarOfrecen(idServicio, idProducto);		
        log.info ("Adicionando Ofrecen: " + tipoBebida);
        return tipoBebida;
	}
	
	public PagoPSE adicionarPago (int numFactura,int numCuenta,String banco, double saldopagar, int identificacion)
	{
        log.info ("Adicionando Pago: " + identificacion+" "+  numFactura);
        PagoPSE tipoBebida = pp.adicionarPago(numFactura, numCuenta, banco, saldopagar, identificacion);		
        log.info ("Adicionando Pago: " + tipoBebida);
        return tipoBebida;
	}
	
	public Plan adicionarPlan (String nombre,String descripcion)
	{
        log.info ("Adicionando Plan: " + nombre+" "+  descripcion);
        Plan tipoBebida = pp.adicionarPlan(nombre, descripcion);		
        log.info ("Adicionando Plan: " + tipoBebida);
        return tipoBebida;
	}
	
	public Producto adicionarProducto (double costo,String nombre,int cantidad)
	{
        log.info ("Adicionando Producto: " + nombre+" "+  cantidad);
        Producto tipoBebida = pp.adicionarProducto(costo, nombre, cantidad);		
        log.info ("Adicionando Producto: " + tipoBebida);
        return tipoBebida;
	}
	
	public Reserva adicionarReserva (int personas,long inicio, long fin, double costo, String descripcion, char registrado,char pago,long idreserva,int idpago)
	{
        log.info ("Adicionando Reserva: " + idpago+" "+  descripcion);
        Reserva tipoBebida = pp.adicionarReserva(personas, inicio, fin, costo, descripcion, registrado, pago, idreserva, idpago);		
        log.info ("Adicionando Reserva: " + tipoBebida);
        return tipoBebida;
	}
	
	public Servicio adicionarServicio (int personas,long inicio,long fin,double costo,String nombre,String descripcion)
	{
        log.info ("Adicionando servicio: " + nombre+" "+  descripcion);
        Servicio tipoBebida = pp.adicionarServicio(personas, inicio, fin, costo, nombre, descripcion);		
        log.info ("Adicionando Servicio: " + tipoBebida);
        return tipoBebida;
	}
	
	public Sirven adicionarSirven (long idServicio,int numeroHabitacion)
	{
        log.info ("Adicionando sirven: " + idServicio+" "+  numeroHabitacion);
        Sirven tipoBebida = pp.adicionarSirven(idServicio, numeroHabitacion);		
        log.info ("Adicionando sirven: " + tipoBebida);
        return tipoBebida;
	}
	
	public Tipo adicionarTipo (String nombre)
	{
        log.info ("Adicionando Tipo: " + nombre);
        Tipo tipoBebida = pp.adicionarTipoBebida(nombre);		
        log.info ("Adicionando Tipo: " + tipoBebida);
        return tipoBebida;
	}
	
	public Usuario adicionarUsuario (String nombre, int identificacion, String tipoid,String correo, long idTipo)
	{
        log.info ("Adicionando usuario: " + nombre);
        Usuario tipoBebida = pp.adicionarUsuario(identificacion, tipoid, correo, idTipo, nombre);		
        log.info ("Adicionando usuario: " + tipoBebida);
        return tipoBebida;
	}
	
	//Requerimientos de consulta//
	public List<long[]> darConsumoUsuario(String pnombre)
	{
        log.info ("Adicionando usuario: " + pnombre);
		return pp.darConsumoYUsuario(pnombre);

	}
	public List<long[]> darHabitacionesYDinero()
	{
        log.info ("Listando habitaciones");
		return pp.darHabitacionesYDineroRecolectado();
	}
	
	public List<long[]> darHabitacionesIndiceOcupacion()
	{
        log.info ("Listando Habitaciones");
		return pp.darHabitacionesEIndiceOcupacion();
	}

}
