package negocio;

public class Producto implements VOProducto {

	private long id;
	private String nombre;
	private double costo;
	private int cantidad;
	
	public Producto()
	{
		this.id=0;
		this.nombre="";
		this.costo=0;
		this.cantidad=0;
	}
	
	public Producto(long pid, String pnombre, double pcosto, int pcantidad)
	{
		this.id=pid;
		this.nombre=pnombre;
		this.costo=pcosto;
		this.cantidad=pcantidad;  
	}
	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public double getCosto() {
		// TODO Auto-generated method stub
		return costo;
	}

	@Override
	public int getCantidad() {
		// TODO Auto-generated method stub
		return cantidad;
	}
	public void setId(long pid)
	{
		this.id=pid;
	}
	public void setNombre(String pnombre)
	{
		this.nombre=pnombre;
	}
	public void setCosto(double pcosto)
	{
		this.costo=pcosto;
	}

	public void setCantidad(int pcantidad)
	{
		this.cantidad=pcantidad;
	}
	
	@Override
	public String toString()
	{
		return "Producto [id=" + id + ", nombre=" + nombre + ", costo=" + costo + ", cantidad"+cantidad +"]";
	}
}
