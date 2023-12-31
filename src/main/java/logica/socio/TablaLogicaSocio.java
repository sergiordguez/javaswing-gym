package logica.socio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.administrador.ActividadDto;
import persistencia.socio.SocioCRUD;

public class TablaLogicaSocio {

	private SocioCRUD sc;
	public final static int DIM = 15;
	private Map<Integer, List<ActividadDto>> tabla = new HashMap<>();

	public TablaLogicaSocio(SocioCRUD sc) {
		this.sc = sc;
	}

	public void generarTabla(Timestamp fecha) {
		tabla = new HashMap<>();
		List<ActividadDto> actividades = sc.listarActividadesHoy(fecha);

		for (ActividadDto act : actividades) {
			int firstHour = act.getFechaInicio().getHours();
			int lastHour = act.getFechaFin().getHours();

			for (int i = firstHour; i < lastHour; i++) {
				if (tabla.get(i) != null) {
					tabla.get(i).add(act);
				} else {
					tabla.put(i, new ArrayList<>());
					tabla.get(i).add(act);
				}

			}
		}
	}

	public Map<Integer, List<ActividadDto>> getTabla() {
		return tabla;
	}
}
