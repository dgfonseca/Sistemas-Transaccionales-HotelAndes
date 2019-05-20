package negocio;

public class Reserva implements VOReserva {

	private long id;
	private long fechaInicio;
	private long fechaFin;
	private int numeroPersonas;
	private double costo;
	private String registrado;
	private String pago;
	private long idPlan;
	private long idUsuario;
	public Reserva() {
		this.id=0;
        this.fechaFin=0;
        this.fechaInicio=0;
        this.numeroPersonas=0;
        this.costo=0;
        this.registrado="F";
        this.pago="F";
        this.idPlan=0;
        this.idUsuario=0;
	}
	public Reserva(long pid, long pfechainicio,long pfechafin,int pnumeropersonas,double pcosto,String pregistrado,String ppago, long pPlan,long idpago)
	{
		this.id=pid;
		this.fechaFin=pfechafin;
		this.fechaInicio=pfechainicio;
		this.numeroPersonas=pnumeropersonas;
		this.costo=pcosto;
		this.registrado=pregistrado;
		this.pago=ppago;
		this.idPlan=pPlan;
		this.idUsuario=idpago;
		
		
	}
	
	
	public void setUsuarioId(int pid)
	{
		this.idUsuario=pid;
	}
	public long getUsuarioId()
	{
		return idUsuario;
	}
	public long getPlanId()
	{
		return idPlan;
	}
	public void setPlanId(long pid)
	{
		this.idPlan=pid;
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
	public int getNumeroPersonas() {
		// TODO Auto-generated method stub
		return numeroPersonas;
	}

	@Override
	public double getCosto() {
		// TODO Auto-generated method stub
		return costo;
	}

	@Override
	public String getRegistrado() {
		// TODO Auto-generated method stub
		return registrado;
	}

	@Override
	public String getPago() {
		// TODO Auto-generated method stub
		return pago;
	}
	public void setId(long pid)
	{
		this.id=pid;
	}
	public void setFechaInicio(long pfechainicio)
	{
		this.fechaInicio=pfechainicio;
	}
	public void setFechaFin(long pfechafin)
	{
		this.fechaFin=pfechafin;
	}
	public void setNumeroPersonas(int pnumero)
	{
		this.numeroPersonas=pnumero;
	}
	public void setCosto(double pcosto)
	{
		this.costo=pcosto;
	}
	public void setRegistrado(String pregistrado)
	{
		this.registrado=pregistrado;
	}
	public void setPago(String ppago)
	{
		this.pago=ppago;
	}
	@Override
	public String toString()
	{
		return "reserva [id=" + id + ", fecha fin=" + fechaFin + ", fecha inicio=" + fechaInicio + ", numero personas"+numeroPersonas+", costo"+costo+", registrado"+registrado+", pago"+pago+ "]";
	}

}
