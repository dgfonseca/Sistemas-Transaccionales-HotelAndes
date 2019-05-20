package negocio;

public class Servicio implements VOServicio{

	private int capacidad;
	private long id;
	private double costo;
	private String nombre;
	private long fechaApertura;
	private long fechaCierre;
	private String descripcion;

	public Servicio()
	{
		this.capacidad=0;
		this.id=0;
		this.costo=0;
		this.nombre="";
		this.fechaApertura=0;
		this.fechaCierre=0;
		this.descripcion="";
	}
	public Servicio(int pcapacidad, long pid,double pcosto,String pnombre, long papertura,long pcierre,String pdescripcion)
	{
		this.capacidad=pcapacidad;
		this.id=pid;
		this.costo=pcosto;
		this.nombre=pnombre;
		this.fechaApertura=papertura;
		this.fechaCierre=pcierre;
		this.descripcion=pdescripcion;
	}

	@Override
	public int getCapacidad() {
		// TODO Auto-generated method stub
		return capacidad;
	}

	@Override
	public long getid() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public double getCosto() {
		// TODO Auto-generated method stub
		return costo;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public long getFechaApertura() {
		// TODO Auto-generated method stub
		return fechaApertura;
	}

	@Override
	public long getFechaCierre() {
		// TODO Auto-generated method stub
		return fechaCierre;
	}

	@Override
	public String getDesctipcion() {
		// TODO Auto-generated method stub
		return descripcion;
	}
	public void setId(long pid)
	{
		this.id=pid;
	}

	public void setCapacidad(int pcapacidad)
	{
		this.capacidad=pcapacidad;
	}
	public void setCosto(double pcosto)
	{
		this.costo=pcosto;
	}
	public void setNombre(String pnombre)
	{
		this.nombre=pnombre;
	}
	public void setFechaApertura(long pfecha)
	{
		this.fechaApertura=pfecha;
	}
	public void setFechaCierre(long pcierre)
	{
		this.fechaCierre=pcierre;
	}
	public void setDescripcion(String pdescripcion)
	{
		this.descripcion=pdescripcion;
	}
	public String toString()
	{
		return "Servicio [id=" + id + ", fecha fin=" + fechaCierre + ", fecha inicio=" + fechaApertura + ", capacidad"+capacidad+", costo"+costo+", descripcion"+descripcion+ "]";

	}
}
