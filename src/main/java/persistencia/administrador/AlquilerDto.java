package persistencia.administrador;

import java.sql.Timestamp;

public class AlquilerDto implements Colisionable {
	private String idAlquiler;
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private int enVigor; // 0 es anulado, 1 es en vigor
	private int pagado; // 0 es no pagado, 1 es pendiente
	private String idSocio;
	private String instalacion;

	public AlquilerDto() {

	}

	public AlquilerDto(String idAlquiler, Timestamp fechaInicio, Timestamp fechaFin, int enVigor, int pagado,
			String idSocio, String instalacion) {
		this.idAlquiler = idAlquiler;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.enVigor = enVigor;
		this.pagado = pagado;
		this.idSocio = idSocio;
		this.instalacion = instalacion;
	}

	public String getIdAlquiler() {
		return idAlquiler;
	}

	public void setIdAlquiler(String idAlquiler) {
		this.idAlquiler = idAlquiler;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getInstalacion() {
		return instalacion;
	}

	public void setInstalacion(String instalacion) {
		this.instalacion = instalacion;
	}

	public String getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(String idSocio) {
		this.idSocio = idSocio;
	}

	public int getEnVigor() {
		return enVigor;
	}

	public void setEnVigor(int enVigor) {
		this.enVigor = enVigor;
	}

	public int getPagado() {
		return pagado;
	}

	public void setPagado(int pagado) {
		this.pagado = pagado;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return "[" + fechaInicio.getDate() + "/" + (fechaInicio.getMonth() + 1) + "/" + (1900 + fechaInicio.getYear())
				+ " - " + fechaFin.getDate() + "/" + (fechaFin.getMonth() + 1) + "/" + (1900 + fechaInicio.getYear())
				+ "] - ID: " + idAlquiler;
	}

	@Override
	public String ToStringColision() {
		return String.format("%s: %s - %s", instalacion, fechaInicio.toString(), fechaFin.toString());
	}

}
