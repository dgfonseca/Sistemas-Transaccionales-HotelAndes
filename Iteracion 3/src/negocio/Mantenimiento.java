package negocio;

public class Mantenimiento implements VOMantenimiento {

	private long id;
	private long fechaInicio;
	private long fechaFin;
	private String descripcion;
	public Mantenimiento() {

		this.id=0;
		this.fechaFin=0;
		this.fechaInicio=0;
		this.descripcion="";
	
	}
	public Mantenimiento(long pid, long pfi, long pff, String pd)
	{
		this.id=pid;
		this.fechaInicio=pfi;
		this.fechaFin=pff;
		this.descripcion=pd;
	}
	
	public void setId(long pid)
	{
		this.id=pid;
	}
	public void setFechaInicio(long p)
	{
		this.fechaInicio=p;
	}
	public void setFechaFin(long p)
	{
		this.fechaFin=p;
	}
	public void setDescripcion(String p)
	{
		this.descripcion=p;
	}
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public long getFechaInicio() {
		// TODO Auto-generated method stub
		return fechaInicio;
	}

	@Override
	public long getFechaFin() {
		// TODO Auto-generated method stub
		return fechaFin;
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		return descripcion;
	}

}
