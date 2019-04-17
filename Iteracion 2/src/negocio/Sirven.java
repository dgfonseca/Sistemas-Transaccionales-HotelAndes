package negocio;

public class Sirven implements VOSirven {

	private long idServicio;
	private int numeroHabitacion;
	private long fechauso;
	public Sirven() {
		this.idServicio=0;
		this.numeroHabitacion=0;
		this.fechauso=0;
	}
	public Sirven(long pservicio,int pproducto, long f)
	{
		this.idServicio=pservicio;
		this.numeroHabitacion=pproducto;
		this.fechauso=f;
	}
	
	public void setFechaUso(long f)
	{
		this.fechauso=f;
	}
	public long getFechaUso()
	{
		return fechauso;
	}
	@Override
	public long getServicioId() {
		// TODO Auto-generated method stub
		return idServicio;
	}

	@Override
	public int getNumeroHabitacion() {
		// TODO Auto-generated method stub
		return numeroHabitacion;
	}

}
