package logica.socio;

import java.sql.Timestamp;

import persistencia.administrador.AlquilerDto;
import persistencia.administrador.CuotaDto;
import persistencia.socio.SocioCRUD;
import persistencia.socio.SocioDto;

public class TablaLogicaAlquileres {

	private SocioCRUD sc;
	private CuotaDto[] tablaCuota;
	private AlquilerDto[] tablaAlquiler;

	public TablaLogicaAlquileres(SocioCRUD sc) {
		this.sc = sc;
	}

	public void generarTablaCuota(String mes, SocioDto socio) {
		CuotaDto[] cuotas = sc.listarCuota(mes, socio);
		this.tablaCuota = cuotas;
	}

	public CuotaDto[] getTablaCuota() {
		return this.tablaCuota;
	}

	/*
	 * public CuotaDto getTablaCuota() { if (tablaCuota.length == 1) { return
	 * tablaCuota[0]; } return new CuotaDto(); }
	 */

	public void generarTablaAlquiler(SocioDto socio) {
		AlquilerDto[] alquiler = sc.listarAlquileresMes(socio);
		this.tablaAlquiler = alquiler;
	}

	@SuppressWarnings("deprecation")
	public AlquilerDto[] getTablaAlquiler(int numMes) {
		AlquilerDto[] result = new AlquilerDto[tablaAlquiler.length];

		for (int i = 0; i < tablaAlquiler.length; i++) {
			if (tablaAlquiler[i] == null) {
				break;
			} else {
				Timestamp fecha = tablaAlquiler[i].getFechaInicio();
				if (fecha != null) {
					int mes = fecha.getMonth();
					if (mes == (numMes)) {
						result[i] = tablaAlquiler[i];
					}
				}
			}
		}

		return result;
	}
}
