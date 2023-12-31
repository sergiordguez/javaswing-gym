package logica.socio;

import persistencia.administrador.InstalacionDto;
import persistencia.socio.SocioCRUD;

public class TablaLogicaSocioInstalacion {

	private SocioCRUD sc;
	private InstalacionDto[] tabla;

	public TablaLogicaSocioInstalacion(SocioCRUD sc) {
		this.sc = sc;
	}

	public void generarTabla() {
		InstalacionDto[] instalaciones = sc.listarInstalaciones();
		this.tabla = instalaciones;
	}

	public InstalacionDto[] getTabla() {
		return this.tabla;
	}
}
