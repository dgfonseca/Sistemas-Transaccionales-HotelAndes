package negocio;

public class Contienen implements VOContienen {

	private long idProducto;
	private int numeroHabitacion;
	public Contienen() {

		this.idProducto=0;
		this.numeroHabitacion=0;
	}
	public Contienen(Long pid,int numHabitacion)
	{
		this.idProducto=pid;
		this.numeroHabitacion=numHabitacion;
	}

	@Override
	public long getIdProducto() {
		// TODO Auto-generated method stub
		return idProducto;
	}

	@Override
	public int getNumeroHabitacion() {
		// TODO Auto-generated method stub
		return numeroHabitacion;
	}

}
