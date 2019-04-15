package negocio;

public class Apartan implements VOApartan {

	private int numeroHabitacion;
	private long idReserva;
	public Apartan() {
		this.numeroHabitacion=0;
		this.idReserva=0;
		// TODO Auto-generated constructor stub
	}
	public Apartan(int num, long id)
	{
		this.numeroHabitacion=num;
		this.idReserva=id;
	}

	public void setNumeroHabitacion(int p)
	{
		this.numeroHabitacion=p;
	}
	public void setIdReserva(long p)
	{
		this.idReserva=p;
	}
	@Override
	public int getNumeroHabitacion() {
		// TODO Auto-generated method stub
		return numeroHabitacion;
	}

	@Override
	public long getIdReserva() {
		// TODO Auto-generated method stub
		return idReserva;
	}

}
