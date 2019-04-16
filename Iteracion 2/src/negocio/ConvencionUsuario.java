package negocio;

public class ConvencionUsuario implements VOConvencionUsuario {

	private long idUsuario;
	private long idConvencion;
	public ConvencionUsuario() {
		this.idUsuario=0;
		this.idConvencion=0;
	}
	public ConvencionUsuario(long pidu, long pidc)
	{
		this.idUsuario=pidu;
		this.idConvencion=pidc;
	}

	@Override
	public long getIdUsuario() {
		// TODO Auto-generated method stub
		return idUsuario;
	}

	@Override
	public long getIdConvencion() {
		// TODO Auto-generated method stub
		return idConvencion;
	}
	public void setIdUsuario(long pid)
	{
		this.idUsuario=pid;
	}
	public void setIdConvencion(long pid)
	{
		this.idConvencion=pid;
	}

}
