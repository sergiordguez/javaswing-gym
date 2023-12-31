package persistencia.administrador;

public class MonitorDto {
	private String idMonitor;
	private String nombre;
	private String apellido;

	public MonitorDto() {

	}

	public MonitorDto(String idMonitor, String nombre, String apellido) {
		this.idMonitor = idMonitor;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public String getIdMonitor() {
		return idMonitor;
	}

	public void setIdMonitor(String idMonitor) {
		this.idMonitor = idMonitor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
