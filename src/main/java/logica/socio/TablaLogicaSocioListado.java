package logica.socio;

import java.util.ArrayList;
import java.util.List;

import persistencia.administrador.ActividadDto;
import persistencia.administrador.AlquilerDto;
import persistencia.administrador.ReservaDto;
import persistencia.socio.SocioCRUD;
import persistencia.socio.SocioDto;

public class TablaLogicaSocioListado {

	private SocioCRUD sc;
	private List<AlquilerDto> instalaciones = new ArrayList<>();
	private List<ActividadDto> actividades = new ArrayList<>();

	public TablaLogicaSocioListado(SocioCRUD sc) {
		this.sc = sc;
	}

	public void generarTablaInstalaciones(SocioDto socio) {
		List<AlquilerDto> tabla = sc.listarAlquileres(socio);
		this.instalaciones = tabla;
	}

	public List<AlquilerDto> getInstalaciones() {
		return this.instalaciones;
	}

	public void generarTablaActividades(SocioDto socio) {
		List<ReservaDto> tabla = sc.listarReservas(socio);
		List<ActividadDto> tabla1 = new ArrayList<>();

		for (int i = 0; i < tabla.size(); i++) {
			ReservaDto result = tabla.get(i);
			tabla1 = sc.listarActividadesFecha(result.getIdActividad());
			for (int j = 0; j < tabla1.size(); j++) {
				actividades.add(tabla1.get(j));
			}
		}
	}

	public List<ActividadDto> getActividades() {
		return this.actividades;
	}
}
