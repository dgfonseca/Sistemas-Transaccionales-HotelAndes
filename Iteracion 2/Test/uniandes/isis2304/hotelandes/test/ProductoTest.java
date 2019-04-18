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
import negocio.Producto;

public class ProductoTest {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Producto.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A ="./src/resources/config/TablasBD_A.json"; 
	
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
	 * 			Métodos de prueba para la tabla ProductoBebida - Creación y borrado
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla ProductoBebida
	 * 1. Adicionar un Producto de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Producto de bebida por su identificador
	 * 4. Borrar un Producto de bebida por su nombre
	 */
    @Test
	public void CRDProductoBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre ProductoBebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Productobebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Productobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los Productos de bebida con la tabla vacía
			List <Producto> lista = parranderos.darProductos();
			assertEquals ("No debe haber Productos de bebida creados!!", 0, lista.size ());

			// Lectura de los Productos de bebida con un Producto de bebida adicionado
			String nombreProductoBebida1 = "Vino tinto";
			long id=1000;
			Producto ProductoBebida1 = parranderos.adicionarProducto(123, 123, "hola", 12);
			lista = parranderos.darProductos();
			assertEquals ("Debe haber un Producto de bebida creado !!", 1, lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", ProductoBebida1, lista.get (0));

			// Lectura de los Productos de bebida con dos Productos de bebida adicionados
			String nombreProductoBebida2 = "Cerveza";
			Producto ProductoBebida2 =  parranderos.adicionarProducto(123123, 123, "hola", 12);
			lista = parranderos.darProductos();
			assertEquals ("Debe haber dos Productos de bebida creados !!", 2, lista.size ());
			assertTrue ("El primer Producto de bebida adicionado debe estar en la tabla", ProductoBebida1.equals (lista.get (0)) || ProductoBebida1.equals (lista.get (1)));
			assertTrue ("El segundo Producto de bebida adicionado debe estar en la tabla", ProductoBebida2.equals (lista.get (0)) || ProductoBebida2.equals (lista.get (1)));

			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla ProductoBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla ProductoBebida");
		}
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

    /**
     * Método de prueba de la restricción de unicidad sobre el nombre de ProductoBebida
     */
	@Test
	public void unicidadProductoBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del Producto de bebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Productobebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Productobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Productos de bebida con la tabla vacía
			List <Producto> lista = parranderos.darProductos();
			assertEquals ("No debe haber Productos de bebida creados!!", 0, lista.size ());

			// Lectura de los Productos de bebida con un Producto de bebida adicionado
			String nombreProductoBebida1 = "Vino tinto";
			Producto ProductoBebida1 =  parranderos.adicionarProducto(13323, 123, "hola", 12);
			lista = parranderos.darProductos();
			assertEquals ("Debe haber un Producto de bebida creado !!", 1, lista.size ());

			Producto ProductoBebida2 =  parranderos.adicionarProducto(1434323, 123, "hola", 12);
			assertNull ("No puede adicionar dos Productos de bebida con el mismo nombre !!", ProductoBebida2);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla ProductoBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla ProductoBebida");
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
     * @param Producto - El Producto de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del Producto especificado
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
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "ProductoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
