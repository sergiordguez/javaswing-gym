package persistencia.administrador;

import java.sql.Timestamp;

public class ActividadDto implements Colisionable {
	private String idActividad;
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private String tipo;
	private String instalacion;
	private String monitor;
	private int plazasTotales;
	private int plazasDisponibles;

	public ActividadDto() {

	}

	public ActividadDto(String idActividad, String tipo, String instalacion, String monitor, int plazasTotales,
			int plazasDisponibles, Timestamp fechaInicio, Timestamp fechaFin) {
		this.idActividad = idActividad;
		this.tipo = tipo;
		this.instalacion = instalacion;
		this.monitor = monitor;
		this.plazasTotales = plazasTotales;
		this.plazasDisponibles = plazasDisponibles;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public String getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(String idActividad) {
		this.idActividad = idActividad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getInstalacion() {
		return instalacion;
	}

	public void setInstalacion(String instalacion) {
		this.instalacion = instalacion;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public int getPlazasTotales() {
		return plazasTotales;
	}

	public void setPlazasTotales(int plazasTotales) {
		this.plazasTotales = plazasTotales;
	}

	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
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

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return "[" + fechaInicio.getDate() + "/" + (fechaInicio.getMonth() + 1) + "/" + (1900 + fechaInicio.getYear())
				+ " - " + fechaFin.getDate() + "/" + (fechaFin.getMonth() + 1) + "/" + (1900 + fechaInicio.getYear())
				+ "] - Actividad: " + tipo;
	}

	@Override
	public String ToStringColision() {
		return String.format("%s: %s - %s", tipo, fechaInicio.toString(), fechaFin.toString());
	}

}