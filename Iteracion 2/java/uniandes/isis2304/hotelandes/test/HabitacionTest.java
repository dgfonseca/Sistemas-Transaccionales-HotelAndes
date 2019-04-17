package uniandes.isis2304.hotelandes.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import negocio.HotelAndes;
import negocio.Habitacion;

public class HabitacionTest {
	private static Logger log = Logger.getLogger(HabitacionTest.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    
	/**
	 * La clase que se quiere probar
	 */
    private HotelAndes parranderos;
	
    /* ****************************************************************
	 * 			Métodos de prueba para la tabla HabitacionBebida - Creación y borrado
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla HabitacionBebida
	 * 1. Adicionar un Habitacion de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Habitacion de bebida por su identificador
	 * 4. Borrar un Habitacion de bebida por su nombre
	 */
    @Test
	public void CRDHabitacionBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre HabitacionBebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Habitacionbebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Habitacionbebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los Habitacions de bebida con la tabla vacía
			List <Habitacion> lista = parranderos.darHabitaciones();
			assertEquals ("No debe haber Habitacions de bebida creados!!", 0, lista.size ());

			// Lectura de los Habitacions de bebida con un Habitacion de bebida adicionado

			Habitacion HabitacionBebida1 = parranderos.adicionarHabitacion(1, 100001, 20, "cripcion", "T") ;
			lista = parranderos.darHabitaciones();
			assertEquals ("Debe haber un Habitacion de bebida creado !!", 1, lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", HabitacionBebida1, lista.get (0));

			// Lectura de los Habitacions de bebida con dos Habitacions de bebida adicionados
			Habitacion HabitacionBebida2 = parranderos.adicionarHabitacion(1, 1000002, 12, "asdas", "F");
			lista = parranderos.darHabitaciones();
			assertEquals ("Debe haber dos Habitacions de bebida creados !!", 2, lista.size ());
			assertTrue ("El primer Habitacion de bebida adicionado debe estar en la tabla", HabitacionBebida1.equals (lista.get (0)) || HabitacionBebida1.equals (lista.get (1)));
			assertTrue ("El segundo Habitacion de bebida adicionado debe estar en la tabla", HabitacionBebida2.equals (lista.get (0)) || HabitacionBebida2.equals (lista.get (1)));

			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla HabitacionBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla HabitacionBebida");
		}
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

    /**
     * Método de prueba de la restricción de unicidad sobre el nombre de HabitacionBebida
     */
	@Test
	public void unicidadHabitacionBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del Habitacion de bebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Habitacionbebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Habitacionbebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Habitacions de bebida con la tabla vacía
			List <Habitacion> lista = parranderos.darHabitaciones();
			assertEquals ("No debe haber Habitacions de bebida creados!!", 0, lista.size ());

			// Lectura de los Habitacions de bebida con un Habitacion de bebida adicionado
			Habitacion HabitacionBebida1 = parranderos.adicionarHabitacion(3, 200001, 1212, "asd", "T");
			lista = parranderos.darHabitaciones();
			assertEquals ("Debe haber un Habitacion de bebida creado !!", 1, lista.size ());

			Habitacion HabitacionBebida2 = parranderos.adicionarHabitacion(3, 2020001, 1212, "asd", "T");
			assertNull ("No puede adicionar dos Habitacions de bebida con el mismo nombre !!", HabitacionBebida2);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla HabitacionBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla HabitacionBebida");
		}    				
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

	/* ****************************************************************
	 * 			Métodos de configuración
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param HabitacionTest - El Habitacion de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del Habitacion especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración de tablas válido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "HabitacionBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
