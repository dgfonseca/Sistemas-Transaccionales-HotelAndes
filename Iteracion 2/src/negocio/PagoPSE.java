package negocio;

public class PagoPSE implements VOPagoPSE {

	private int numeroFactura;
	private double numeroCuenta;
	private String banco;
	private double saldoAPagar;
	private int identificacion;
	
	
	public PagoPSE()
	{
		this.banco="";
		this.numeroCuenta=0;
		this. numeroFactura=0;
		this.saldoAPagar=0;
		this.identificacion=0;
	}
	public PagoPSE(int pnumeroFactura,double pnumeroCuenta, String pbanco,double pSaldoAPagar,int pidentificacion)
	{
		this.numeroCuenta=pnumeroCuenta;
		this.numeroFactura=pnumeroFactura;
		this.banco=pbanco;
		this.saldoAPagar=pSaldoAPagar;
		this.identificacion=pidentificacion;
	}
	
	public int getIdentificacion()
	{
		return identificacion;
	}
	public void setIdentificacion(int pidentificacion)
	{
		this.identificacion=pidentificacion;
	}
	@Override
	public int getNumeroFactura() {
		// TODO Auto-generated method stub
		return numeroFactura;
	}

	@Override
	public double getNumeroCuenta() {
		// TODO Auto-generated method stub
		return numeroCuenta;
	}

	@Override
	public String darBanco() {
		// TODO Auto-generated method stub
		return banco;
	}

	@Override
	public double saldoAPagar() {
		// TODO Auto-generated method stub
		return saldoAPagar;
	}
	public void setNumeroFactura(int pnumerofactura)
	{
		this.numeroFactura=pnumerofactura;
	}
	public void setNumeroCuenta(double pnumeroCuenta)
	{
		this.numeroCuenta=pnumeroCuenta;
	}
	public void setBanco(String pbanco)
	{
		this.banco=pbanco;
	}
	public void setSaldoAPagar(double psaldo)
	{
		this.saldoAPagar=psaldo;
	}
	public String toString()
	{
		return "PagoPSE [numeroFactura=" + numeroFactura + ", numeroCuenta=" + numeroCuenta + ", banco=" + banco + ", saldo a pagar"+ saldoAPagar+"]";
	}
	

}
