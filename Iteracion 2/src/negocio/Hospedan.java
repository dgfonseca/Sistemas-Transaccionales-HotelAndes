package negocio;

public class Hospedan implements VOHospedan {

	private int identificacion;
	private long idReserva;
	public Hospedan() {
		this.identificacion=0;
		this.idReserva=0;
	}
	public Hospedan(int pservicio,long pproducto)
	{
		this.identificacion=pservicio;
		this.idReserva=pproducto;
	}

	@Override
	public int getIdentificacion() {
		// TODO Auto-generated method stub
		return identificacion;
	}

	@Override
	public long getIdReserva() {
		// TODO Auto-generated method stub
		return idReserva;
	}

}
