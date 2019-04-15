package negocio;

public class Sirven implements VOSirven {

	private long idServicio;
	private int numeroHabitacion;
	public Sirven() {
		this.idServicio=0;
		this.numeroHabitacion=0;
	}
	public Sirven(long pservicio,int pproducto)
	{
		this.idServicio=pservicio;
		this.numeroHabitacion=pproducto;
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
