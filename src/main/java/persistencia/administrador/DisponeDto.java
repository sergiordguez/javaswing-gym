package persistencia.administrador;

public class DisponeDto {
	private String nombreInstalacion;
	private String nombreRecurso;
	private int cantidad;

	public DisponeDto() {

	}

	public DisponeDto(String nombreInstalacion, String nombreRecurso, int cantidad) {
		this.nombreInstalacion = nombreInstalacion;
		this.nombreRecurso = nombreRecurso;
		this.cantidad = cantidad;
	}

	public String getNombreInstalacion() {
		return nombreInstalacion;
	}

	public void setNombreInstalacion(String nombreInstalacion) {
		this.nombreInstalacion = nombreInstalacion;
	}

	public String getNombreRecurso() {
		return nombreRecurso;
	}

	public void setNombreRecurso(String nombreRecurso) {
		this.nombreRecurso = nombreRecurso;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return getNombreRecurso() + " (" + getCantidad() + ")";
	}
}
