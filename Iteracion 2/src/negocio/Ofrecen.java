package negocio;

public class Ofrecen implements VOOfrecen{

	private long idServicio;
	private long idProducto;
	public Ofrecen() {
		this.idServicio=0;
		this.idProducto=0;
	}
	public Ofrecen(long pservicio,long pproducto)
	{
		this.idServicio=pservicio;
		this.idProducto=pproducto;
	}

	@Override
	public long getIdServicio() {
		// TODO Auto-generated method stub
		return idServicio;
	}

	@Override
	public long getIdProducto() {
		// TODO Auto-generated method stub
		return idProducto;
	}

}
