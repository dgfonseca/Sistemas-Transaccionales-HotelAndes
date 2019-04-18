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
import negocio.Reserva;

public class ReservaTest {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Reserva.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/resources/config/TablasBD_A.json"; 
	
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
	 * 			Métodos de prueba para la tabla ReservaBebida - Creación y borrado
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla ReservaBebida
	 * 1. Adicionar un Reserva de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Reserva de bebida por su identificador
	 * 4. Borrar un Reserva de bebida por su nombre
	 */
    @Test
	public void CRDReservaBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre ReservaBebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Reservabebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Reservabebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los Reservas de bebida con la tabla vacía
			List <Reserva> lista = parranderos.darReservas();
			assertEquals ("No debe haber Reservas de bebida creados!!", 0, lista.size ());

			// Lectura de los Reservas de bebida con un Reserva de bebida adicionado
			String nombreReservaBebida1 = "Vino tinto";
			long id=1000;
			Reserva ReservaBebida1 = parranderos.adicionarReserva(123123, 3, 20190000, 20190203, 39, "hola", "T", "T", 1, 1) ;
			lista = parranderos.darReservas();
			assertEquals ("Debe haber un Reserva de bebida creado !!", 1, lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", ReservaBebida1, lista.get (0));

			// Lectura de los Reservas de bebida con dos Reservas de bebida adicionados
			String nombreReservaBebida2 = "Cerveza";
			Reserva ReservaBebida2 = parranderos.adicionarReserva(12312113, 3, 20190000, 20190203, 39, "hola", "T", "T", 1, 1) ;
			lista = parranderos.darReservas();
			assertEquals ("Debe haber dos Reservas de bebida creados !!", 2, lista.size ());
			assertTrue ("El primer Reserva de bebida adicionado debe estar en la tabla", ReservaBebida1.equals (lista.get (0)) || ReservaBebida1.equals (lista.get (1)));
			assertTrue ("El segundo Reserva de bebida adicionado debe estar en la tabla", ReservaBebida2.equals (lista.get (0)) || ReservaBebida2.equals (lista.get (1)));

			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla ReservaBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla ReservaBebida");
		}
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

    /**
     * Método de prueba de la restricción de unicidad sobre el nombre de ReservaBebida
     */
	@Test
	public void unicidadReservaBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del Reserva de bebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Reservabebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Reservabebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Reservas de bebida con la tabla vacía
			List <Reserva> lista = parranderos.darReservas();
			assertEquals ("No debe haber Reservas de bebida creados!!", 0, lista.size ());

			// Lectura de los Reservas de bebida con un Reserva de bebida adicionado
			String nombreReservaBebida1 = "Vino tinto";
			Reserva ReservaBebida1 = parranderos.adicionarReserva(1231212123, 3, 20190000, 20190203, 39, "hola", "T", "T", 1, 1) ;
			lista = parranderos.darReservas();
			assertEquals ("Debe haber un Reserva de bebida creado !!", 1, lista.size ());

			Reserva ReservaBebida2 = parranderos.adicionarReserva(2123123, 3, 20190000, 20190203, 39, "hola", "T", "T", 1, 1) ;
			assertNull ("No puede adicionar dos Reservas de bebida con el mismo nombre !!", ReservaBebida2);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla ReservaBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla ReservaBebida");
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
     * @param Reserva - El Reserva de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del Reserva especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			//Aca esta el error//
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración de tablas válido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "ReservaBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
