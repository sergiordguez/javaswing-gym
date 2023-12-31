package persistencia.administrador;

public class CuotaDto {
	private String idSocio;
	private String mes;
	private int total;

	public CuotaDto() {

	}

	public CuotaDto(String idSocio, String mes, int total) {
		super();
		this.idSocio = idSocio;
		this.mes = mes;
		this.total = total;
	}

	public String getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(String idSocio) {
		this.idSocio = idSocio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return String.valueOf(total);
	}
}
