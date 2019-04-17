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
import negocio.Mantenimiento;

public class MantenimientoTest {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(Mantenimiento.class.getName());
	
	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD tambi�n
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
	 * 			M�todos de prueba para la tabla MantenimientoBebida - Creaci�n y borrado
	 *****************************************************************/
	/**
	 * M�todo que prueba las operaciones sobre la tabla MantenimientoBebida
	 * 1. Adicionar un Mantenimiento de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Mantenimiento de bebida por su identificador
	 * 4. Borrar un Mantenimiento de bebida por su nombre
	 */
    @Test
	public void CRDMantenimientoBebidaTest() 
	{
    	// Probar primero la conexi�n a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre MantenimientoBebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Mantenimientobebida incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Mantenimientobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los Mantenimientos de bebida con la tabla vac�a
			List <Mantenimiento> lista = parranderos.darMantenimientos();
			assertEquals ("No debe haber Mantenimientos de bebida creados!!", 0, lista.size ());

			// Lectura de los Mantenimientos de bebida con un Mantenimiento de bebida adicionado
			String nombreMantenimientoBebida1 = "Vino tinto";
			long id=1000;
			Mantenimiento MantenimientoBebida1 = parranderos.adicionarMantenimiento() ;
			lista = parranderos.darMantenimientos();
			assertEquals ("Debe haber un Mantenimiento de bebida creado !!", 1, lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", MantenimientoBebida1, lista.get (0));

			// Lectura de los Mantenimientos de bebida con dos Mantenimientos de bebida adicionados
			String nombreMantenimientoBebida2 = "Cerveza";
			Mantenimiento MantenimientoBebida2 = parranderos.adicionarMantenimiento (nombreMantenimientoBebida2,20202);
			lista = parranderos.darMantenimientos();
			assertEquals ("Debe haber dos Mantenimientos de bebida creados !!", 2, lista.size ());
			assertTrue ("El primer Mantenimiento de bebida adicionado debe estar en la tabla", MantenimientoBebida1.equals (lista.get (0)) || MantenimientoBebida1.equals (lista.get (1)));
			assertTrue ("El segundo Mantenimiento de bebida adicionado debe estar en la tabla", MantenimientoBebida2.equals (lista.get (0)) || MantenimientoBebida2.equals (lista.get (1)));

			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecuci�n de las pruebas de operaciones sobre la tabla MantenimientoBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla MantenimientoBebida");
		}
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

    /**
     * M�todo de prueba de la restricci�n de unicidad sobre el nombre de MantenimientoBebida
     */
	@Test
	public void unicidadMantenimientoBebidaTest() 
	{
    	// Probar primero la conexi�n a la base de datos
		try
		{
			log.info ("Probando la restricci�n de UNICIDAD del nombre del Mantenimiento de bebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Mantenimientobebida incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Mantenimientobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Mantenimientos de bebida con la tabla vac�a
			List <Mantenimiento> lista = parranderos.darMantenimientos();
			assertEquals ("No debe haber Mantenimientos de bebida creados!!", 0, lista.size ());

			// Lectura de los Mantenimientos de bebida con un Mantenimiento de bebida adicionado
			String nombreMantenimientoBebida1 = "Vino tinto";
			Mantenimiento MantenimientoBebida1 = parranderos.adicionarMantenimiento (nombreMantenimientoBebida1,99);
			lista = parranderos.darMantenimientos();
			assertEquals ("Debe haber un Mantenimiento de bebida creado !!", 1, lista.size ());

			Mantenimiento MantenimientoBebida2 = parranderos.adicionarMantenimiento (nombreMantenimientoBebida1,1021);
			assertNull ("No puede adicionar dos Mantenimientos de bebida con el mismo nombre !!", MantenimientoBebida2);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecuci�n de las pruebas de UNICIDAD sobre la tabla MantenimientoBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla MantenimientoBebida");
		}    				
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

	/* ****************************************************************
	 * 			M�todos de configuraci�n
	 *****************************************************************/
    /**
     * Lee datos de configuraci�n para la aplicaci�n, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param Mantenimiento - El Mantenimiento de configuraci�n deseada
     * @param archConfig - Archivo Json que contiene la configuraci�n
     * @return Un objeto JSON con la configuraci�n del Mantenimiento especificado
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
			log.info ("Se encontr� un archivo de configuraci�n de tablas v�lido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de tablas v�lido: ", "MantenimientoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
