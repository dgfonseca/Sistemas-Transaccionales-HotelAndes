package uniandes.isis2304.hotelandes.test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.fail;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import negocio.Mantenimiento;
import negocio.HotelAndes;

public class MantenimientoTest {
	private static Logger log = Logger.getLogger(MantenimientoTest.class.getName());
	
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
	 * 			Métodos de prueba para la tabla MantenimientoBebida - Creación y borrado
	 *****************************************************************/
	@SuppressWarnings("unused")
	/**
	 * Método que prueba las operaciones sobre la tabla MantenimientoBebida
	 * 1. Adicionar un Mantenimiento de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Mantenimiento de bebida por su identificador
	 * 4. Borrar un Mantenimiento de bebida por su nombre
	 */
    @Test
	public void CRDMantenimientoBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre MantenimientoBebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Mantenimientobebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Mantenimientobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los Mantenimientos de bebida con la tabla vacía
			List <Mantenimiento> lista = parranderos.darMantenimientos();
			assertEquals ("No debe haber Mantenimientos nuevas creadas!!", lista.size(), lista.size ());

			// Lectura de los Mantenimientos de bebida con un Mantenimiento de bebida adicionado

			Mantenimiento MantenimientoBebida1 = parranderos.adicionarMantenimiento(12, 20190000, 20190201, "Sillas") ;
			lista = parranderos.darMantenimientos();
			assertEquals ("Debe haber un Mantenimiento de bebida creado !!", lista.size(), lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", MantenimientoBebida1, null);

			// Lectura de los Mantenimientos de bebida con dos Mantenimientos de bebida adicionados
			Mantenimiento MantenimientoBebida2 = parranderos.adicionarMantenimiento(121, 20190000, 20190201, "Sillas") ;
			lista = parranderos.darMantenimientos();
			assertEquals ("Debe haber varias Mantenimientoes de bebida creados !!", lista.size(), lista.size ());

			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla MantenimientoBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
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
     * Método de prueba de la restricción de unicidad sobre el nombre de MantenimientoBebida
     */
	@SuppressWarnings("unused")
	@Test
	public void unicidadMantenimientoBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del Mantenimiento de bebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Mantenimientobebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Mantenimientobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Mantenimientos de bebida con la tabla vacía
			List <Mantenimiento> lista = parranderos.darMantenimientos();
			assertEquals ("No deberian haber Mantenimientoes mayores a!!"+lista.size(), lista.size(), lista.size ());

			// Lectura de los Mantenimientos de bebida con un Mantenimiento de bebida adicionado
			Mantenimiento MantenimientoBebida1 = parranderos.adicionarMantenimiento(122, 20190000, 20190201, "Sillas") ;
			lista = parranderos.darMantenimientos();
			assertEquals ("Debe haber un Mantenimiento de bebida creado !!", lista.size(), lista.size ());

		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla MantenimientoBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
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
	 * 			Métodos de configuración
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param MantenimientoTest - El Mantenimiento de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del Mantenimiento especificado
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
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "MantenimientoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
