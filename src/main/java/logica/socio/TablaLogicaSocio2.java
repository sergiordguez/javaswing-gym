package logica.socio;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import persistencia.administrador.AlquilerDto;
import persistencia.administrador.ReservaDto;
import persistencia.socio.SocioCRUD;
import persistencia.socio.SocioDto;

public class TablaLogicaSocio2 {

	private SocioCRUD sc;
	private List<ReservaDto> tabla = new ArrayList<>();
	private List<AlquilerDto> alquileres = new ArrayList<>();

	public TablaLogicaSocio2(SocioCRUD sc) {
		this.sc = sc;
	}

	public void generarTabla(SocioDto socio) {
		List<ReservaDto> actividades = sc.listarActividades(socio);
		List<AlquilerDto> alquileres = sc.listarAlquileresActivos(socio);
		this.tabla = actividades;
		this.alquileres = alquileres;
	}

	public List<ReservaDto> getTabla() {
		return tabla;
	}

	public List<AlquilerDto> getAlquileres() {
		return alquileres;
	}

	public List<AlquilerDto> getAlquileresFuturos() {
		List<AlquilerDto> alq = new ArrayList<>();

		for (AlquilerDto al : alquileres) {
			if (al.getFechaInicio().after(new Time(System.currentTimeMillis()))) {
				alq.add(al);
			}
		}
		return alq;
	}
}
