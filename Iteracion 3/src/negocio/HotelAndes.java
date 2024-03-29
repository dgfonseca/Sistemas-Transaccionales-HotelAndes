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
	 * 			M�todos
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
	 * Cierra la conexi�n con la base de datos (Unidad de persistencia)
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
	
	public Consumen adicionarContienen (long idProducto,int numeroHabitacion)
	{
        log.info ("Adicionando Contienen: " + idProducto+" "+  numeroHabitacion);
        Consumen tipoBebida = pp.adicionarContienen(idProducto, numeroHabitacion);		
        log.info ("Adicionando Contienen: " + tipoBebida);
        return tipoBebida;
	}
	
	public Habitacion adicionarHabitacion (int capacidad,int numeroHabitacion, int costo, String descripcion, String disponible)
	{
        log.info ("Adicionando Habitacion: " + descripcion+" "+  numeroHabitacion);
        Habitacion tipoBebida = pp.adicionarHabitacion(capacidad, numeroHabitacion, costo, descripcion, disponible);
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
	
	
	
	public Plan adicionarPlan (long id,String nombre,String descripcion)
	{
        log.info ("Adicionando Plan: " + nombre+" "+  descripcion);
        Plan tipoBebida = pp.adicionarPlan(id,nombre, descripcion);		
        log.info ("Adicionando Plan: " + tipoBebida);
        return tipoBebida;
	}
	
	public List<Plan> darPlanes()
	{
		return pp.darPlanes();
	}
	
	public List<Object[]> darServicios()
	{
		return pp.darServicios();
	}
	public List<Object[]> darServiciosObjeto()
	{
		return pp.darServiciosObjeto();
	}
	
	public long registrarSalida(long idReserva)
	{
        log.info ("Registrando salida: " + idReserva);
		return pp.registrarSalida(idReserva);
	}
	public long registrarLlegada(long idReserva)
	{
        log.info ("Registrando llegada: " + idReserva);
		return pp.registrarLlegada(idReserva);
	}
	
	public Producto adicionarProducto (long id,double costo,String nombre,int cantidad)
	{
        log.info ("Adicionando Producto: " + nombre+" "+  cantidad);
        Producto tipoBebida = pp.adicionarProducto(id,costo, nombre, cantidad);		
        log.info ("Adicionando Producto: " + tipoBebida);
        return tipoBebida;
	}
	
	public List<Object[]> darConsumoUsuarioDado(long id,long ini,long fin)
	{
		return pp.darConsumoUsuarioDado(id, ini, fin);
	}
	
	public Reserva adicionarReserva (long id,int personas,long inicio, long fin, double costo, String descripcion, String registrado,String pago,long idplan,long idusuario) throws Exception
	{
		if(inicio>20190000&&fin>=inicio) {
        log.info ("Adicionando Reserva: " + id+" "+  descripcion);
        Reserva tipoBebida = pp.adicionarReserva(id, personas, inicio, fin, costo, descripcion, registrado, pago, idplan, idusuario);		
        log.info ("Adicionando Reserva: " + tipoBebida);
        return tipoBebida;}
		else
		{
			throw new Exception("Ese a�o es invalido, tiene que ser escrito a�o-mes-dia, sin las barras espaciadoras ej 20190211 donde a�o es 2019 mes es 02 y dia es 11");
		}
	}
	
	public Servicio adicionarServicio (long id,int personas,long inicio,long fin,double costo,String nombre,String descripcion) throws Exception
	{
        log.info ("Adicionando servicio: " + nombre+" "+  descripcion);
        Servicio tipoBebida = pp.adicionarServicio(id,personas, inicio, fin, costo, nombre, descripcion);		
        log.info ("Adicionando Servicio: " + tipoBebida);
        return tipoBebida;
	}
	
	public Sirven adicionarSirven (long idServicio,int numeroHabitacion, long fechaUso) throws Exception
	{
		if(fechaUso>20190000) {
        log.info ("Adicionando sirven: " + idServicio+" "+  numeroHabitacion);
        Sirven tipoBebida = pp.adicionarSirven(idServicio, numeroHabitacion, fechaUso);		
        log.info ("Adicionando sirven: " + tipoBebida);
        return tipoBebida;}
		else throw new Exception("Ese a�o es invalido, tiene que ser escrito a�o-mes-dia, sin las barras espaciadoras ej 20190211 donde a�o es 2019 mes es 02 y dia es 11");

	}
	
	public Tipo adicionarTipo (String nombre, long idtipo)
	{
        log.info ("Adicionando Tipo: " + nombre);
        Tipo tipoBebida = pp.adicionarTipoUsuario(idtipo,nombre);		
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
	public List<Tipo> darTipos()
	{
		return pp.darTipos();
	}
	
	//Requerimientos de consulta//
	public List<long[]> darConsumoUsuario(String pnombre)
	{
        log.info ("Adicionando usuario: " + pnombre);
		return pp.darConsumoYUsuario(pnombre);

	}
	public List<long[]> darHabitacionesYDinero(long inicio, long fin) throws Exception
	{
        log.info ("Listando habitaciones");
        if(inicio>=20190000&&fin>=inicio)
        	return pp.darHabitacionesYDineroRecolectado(inicio,fin);
        else throw new Exception("Ese a�o es invalido, tiene que ser escrito a�o-mes-dia, sin las barras espaciadoras ej 20190211 donde a�o es 2019 mes es 02 y dia es 11");

	}
	
	public List<long[]> darHabitacionesIndiceOcupacion()
	{
        log.info ("Listando Habitaciones");
		return pp.darHabitacionesEIndiceOcupacion();
	}
	public List<long[]> darServiciosPopulares()
	{
		log.info("Listando Servicios");
		return pp.darServiciosPopulares();
	}

	public Servicio darServicioPorId(long pid)
	{
		return pp.darServicioPorId(pid);
	}
	public List<Habitacion> darHabitaciones()
	{
		return pp.darHabitaciones();
	}
	public List<Habitacion> darHabitacionesCapacidad(int num)
	{
		log.info("Listando habitaciones");
		return pp.darHabitacionesCapacidad(num);
	}
	
	public String darNombreConvencion(long id)
	{
		log.info("Obteniendo nombre de convencion");
		return pp.darNombreConvencion(id);
	}
	
	public List<Object[]> darHabitacionesOcupadas()
	{
		log.info("Obteniendo habitaciones ocupadas");
		return pp.darHabitacionesOcupadas();
	}
	
	public Object[] darFechaConvencion(long id)
	{
		log.info("Obteniendo Reserva Convencion");
		return pp.darFechaConvencion(id);
	}
	
	public HabitacionConvencion adicionarHabitacionConvencion(long idReserva, int idHabitacion)
	{
		log.info("Añadiendo reserva de habitacion a convencion");
		return pp.adicionarHabitacionConvencion(idReserva, idHabitacion);
	}
	public List<Usuario> darBuenosClientes()
	{
		return pp.darBuenosClientes();
	}
	
	public List<Object[]> darServiciosOcupados()
	{
		log.info("Obteniendo servicios ocupados");
		return pp.darServiciosOcupados();
	}
	
	public ServiciosConvencion adicionarServicioConvencion(long idReserva, int idServicio)
	{
		log.info("Añadiendo reserva de servicio a convencion");
		return pp.adicionarServicioConvencion(idReserva, idServicio);
	}
	
	public List<Convencion> darConvenciones()
	{
		return pp.darConvenciones();
	}
	
	public List<Object[]> darHabitacionConvencionIdConvencion(long id)
	{
		log.info("Dando habitaciones de la convención");
		return pp.darHabitacionConvencionIdConvencion(id);
	}
	
	public List<Object[]> darServiciosConvencionIdConvencion(long id)
	{
		log.info("Dando servicios de la convencion");
		return pp.darServiciosConvencionIdConvencion(id);
	}
	
	public long eliminarHabitacionConvencion(long id, int numeroHabitacion)
	{
		log.info("Eliminando habitaciones de la convención ");
		return pp.eliminarHabitacionConvencion(id, numeroHabitacion);
	}
	
	public long eliminarServiciosConvencion(long id, int numeroServicio)
	{
		log.info("Eliminando servicios de la convención ");
		return pp.eliminarServiciosConvencion(id, numeroServicio);
	}
	
	public List<Object[]> darHabitacionesMantenimientoFecha()
	{
		log.info("Dando habitaciones en mantenimiento con fecha");
		return pp.darHabitacionesEnMantenimientoFecha();
	}
	
	public Mantenimiento adicionarMantenimiento(long idServicio, long fechaInicio, long fechaFin, String descripcion)
	{
		log.info("Generando mantenimiento");
		return pp.adicionarMantenimiento(idServicio, fechaInicio, fechaFin, descripcion);
	}
	
	public HabitacionMantenimiento adicionarHabitacionMantenimiento(long id, int numeroHabitacion)
	{
		log.info("Insertando habitacion en mantenimiento");
		return pp.adicionarHabitacionMantenimiento(id, numeroHabitacion);
	}
	
	public List<Object[]> darServiciosMantenimientoEnFecha()
	{
		log.info("Dando servicios en mantenimiento con fecha");
		return pp.darServiciosEnMantenimientoFecha();
	}
	
	public ServicioMantenimiento adicionarServicioMantenimiento(long id, int idServicio)
	{
		log.info("Insertando servicio en mantenimiento");
		return pp.adicionarServicioMantenimiento(id, idServicio);
	}
	
	public Convencion adicionarConvencion(long identificacion,String nombre,String descripcion)
	{
		return pp.adicionarConvencion(identificacion, nombre, descripcion);
	}
	
	
	public long [] limpiarParranderos ()
	{
        log.info ("Limpiando la BD de Parranderos");
        long [] borrrados = pp.limpiarParranderos();	
        log.info ("Limpiando la BD de Parranderos: Listo!");
        return borrrados;
	}
	public List<Usuario> darUsuarios()
	{
		return pp.darUsuarios();
	}
	public List<Object[]> darReservas()
	{
		return pp.darReservas();
	}
	public List<Producto> darProductos()
	{
		return pp.darProductos();
	}
	
	public List<Mantenimiento> darMantenimientos()
	{
		log.info("Dando todos los mantenimientos");
		return pp.darMantenimientos();
	}
	
	public long eliminarMantenimiento(long id)
	{
		log.info("Eliminando mantenimiento");
		return pp.eliminarMantenimiento(id);
	}
	
	public long eliminarHabitacionMantenimientoIdMantenimiento(long id)
	{
		log.info("Eliminando habitacion mantenimiento");
		return pp.eliminarHabitacionMantenimientoIdMantenimiento(id);
	}
	
	public long eliminarServicioMantenimientoIdMantenimiento(long id)
	{
		log.info("Eliminando servicio mantenimiento");
		return pp.eliminarServiciosMantenimientoIdMantenimiendo(id);
	}
	
	public List<Object[]> darServiciosEnPrecio(double costo1, double costo2)
	{
		log.info("Dando servicios en rango de precio");
		return pp.darServiciosEnPrecio(costo1, costo2);
	}
	
	public List<Object[]> darServiciosPosiblesEnHora(long hora)
	{
		log.info("Dando servicios posibles en hora");
		return pp.darServiciosPosiblesEnHora(hora);
	}
	
	public List<Object[]> darServiciosParaCapacidad(int num)
	{
		log.info("Dando servicios para capacidad");
		return pp.darServicioParaCapacidad(num);
	}
	public List<Usuario> darUsuarioId(long cc)
	{
		return pp.darUsuarioPorId(cc);
	}
	public Tipo darTipoId(long id)
	{
		return pp.darTipoId(id);
	}
	
	public List<Object[]> requerimientoFuncional9(long id,long ini,long fin)
	{
		return pp.requerimientoFuncional9(id, ini, fin);
	}
	
	public List<Object[]> requerimientoFuncional10(long id,long ini,long fin)
	{
		return pp.requerimientoFuncional10(id, ini, fin);
	}
	public List<Object[]> requerimientoFuncional12()
	{
		return pp.requerimientoFuncional12();
	}
	public List<Object[]> requerimientoFuncional11Habitaciones(long fechaIn, long fechaFin)
	{
		log.info("Dando habitaciones ocupadas en semana");
		return pp.darHabitacionesOcupadasEnSemana(fechaIn, fechaFin);
	}
	public List<Object[]> requerimientoFuncional11Servicios(long fechaIn, long fechaFin)
	{
		log.info("Dando servicios ocupadas en semana");
		return pp.darServiciosUtilizadosEnSemana(fechaIn, fechaFin);
	}

}
