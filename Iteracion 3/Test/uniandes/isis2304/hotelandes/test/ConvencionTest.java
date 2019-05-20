package uniandes.isis2304.hotelandes.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import negocio.Convencion;

public class ConvencionTest {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Convencion.class.getName());
	
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
	 * 			Métodos de prueba para la tabla ConvencionBebida - Creación y borrado
	 *****************************************************************/
	@SuppressWarnings("unused")
	/**
	 * Método que prueba las operaciones sobre la tabla ConvencionBebida
	 * 1. Adicionar un Convencion de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Convencion de bebida por su identificador
	 * 4. Borrar un Convencion de bebida por su nombre
	 */
    @Test
	public void CRDConvencionBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre ConvencionBebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Convencionbebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Convencionbebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los Convencions de bebida con la tabla vacía
			List <Convencion> lista = parranderos.darConvenciones();
			assertEquals ("No debe haber Convencions de bebida creados!!", lista.size(), lista.size ());

			
			Convencion ConvencionBebida1 = parranderos.adicionarConvencion(12356, "Nombre", "Descripcion") ;
			lista = parranderos.darConvenciones();
			assertEquals ("Debe haber  Convenciones de bebida creado !!", lista.size(), lista.size ());

			// Lectura de los Convencions de bebida con dos Convencions de bebida adicionados
			String nombreConvencionBebida2 = "Cerveza";
			Convencion ConvencionBebida2 = parranderos.adicionarConvencion(1243, "Nombre", "Descripcion");
			lista = parranderos.darConvenciones();
			assertEquals ("Debe haber Convenciones de bebida creados !!",lista.size() , lista.size ());
;

			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla ConvencionBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla ConvencionBebida");
		}
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

    /**
     * Método de prueba de la restricción de unicidad sobre el nombre de ConvencionBebida
     */
	@SuppressWarnings("unused")
	@Test
	public void unicidadConvencionBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del Convencion de bebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Convencionbebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Convencionbebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Convencions de bebida con la tabla vacía
			List <Convencion> lista = parranderos.darConvenciones();
			assertEquals ("No debe haber Convencions de bebida creados!!", lista.size(), lista.size ());

			// Lectura de los Convencions de bebida con un Convencion de bebida adicionado
			Convencion ConvencionBebida1 = parranderos.adicionarConvencion(1123, "Nombre", "Descripcion");
			lista = parranderos.darConvenciones();
			assertEquals ("Debe haber un Convencion de bebida creado !!", lista.size(), lista.size ());

			Convencion ConvencionBebida2 = parranderos.adicionarConvencion(12433, "Nombres", "Descripcion");
			assertNull ("No puede adicionar dos Convencions de bebida con el mismo nombre !!", ConvencionBebida2);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla ConvencionBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla ConvencionBebida");
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
     * @param Convencion - El Convencion de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del Convencion especificado
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
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "ConvencionBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
