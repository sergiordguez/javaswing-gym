package persistencia.administrador;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import persistencia.socio.SocioDto;
import util.Database;

public class AdministradorCRUD {
	Database db;

	public AdministradorCRUD(Database db) {
		this.db = db;
	}

	public AdministradorDto[] listarAdministradores() {
		AdministradorDto[] listaAdministradores = db
				.executeQueryPojo(AdministradorDto.class, "SELECT * FROM ADMINISTRADOR")
				.toArray(new AdministradorDto[0]);
		return listaAdministradores;
	}

	public void añadirTipoActividad(TipoActividadDto dtoTipo, List<String> listaRecursos) {
		db.executeUpdate("INSERT INTO TIPOACTIVIDAD VALUES(?, ?)", dtoTipo.getNombre(), dtoTipo.getIntensidad());

		for (String sRecurso : listaRecursos) {
			db.executeUpdate("INSERT INTO NECESITA VALUES(?, ?)", dtoTipo.getNombre(), sRecurso);
		}
	}

	public TipoActividadDto[] listarTiposActividades() {
		TipoActividadDto[] listaTipoActividades = db
				.executeQueryPojo(TipoActividadDto.class, "SELECT * FROM TIPOACTIVIDAD")
				.toArray(new TipoActividadDto[0]);
		return listaTipoActividades;
	}

	public void añadirActividad(Timestamp fechaInicio, Timestamp fechaFin, String tipo, String instalacion,
			int plazasTotales) {
		db.executeUpdate(
				"INSERT INTO ACTIVIDAD(idActividad, fechaInicio, fechaFin, tipo, instalacion, plazasTotales, plazasDisponibles) VALUES(?, ?, ?, ?, ?, ?, ?)",
				UUID.randomUUID().toString(), fechaInicio.toString(), fechaFin.toString(), tipo, instalacion,
				plazasTotales, plazasTotales);
	}

	public void añadirAlquiler(Timestamp fechaInicio, Timestamp fechaFin, int enVigor, int pagado, String idSocio,
			String instalacion) {
		List<AlquilerDto> alquileres = db.executeQueryPojo(AlquilerDto.class, "SELECT * FROM ALQUILER ");
		int i = alquileres.size() + 1;

		db.executeUpdate(
				"INSERT INTO ALQUILER(idAlquiler, fechaInicio, fechaFin, enVigor, pagado, idSocio, instalacion) VALUES(?, ?, ?, ?, ?, ?, ?)",
				"ALQ-" + i, fechaInicio.toString(), fechaFin.toString(), enVigor, pagado, idSocio, instalacion);

	}

	public List<ActividadDto> listarActividades(Timestamp fecha, String instalacion) {
		Timestamp lunes = calcularLunes(fecha);
		Timestamp domingo = calcularDomingo(fecha);
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD A WHERE a.instalacion = ?", instalacion);

		List<ActividadDto> listaActividadesFiltrada = new ArrayList<>();
		for (ActividadDto actividad : listaActividades) {
			if (actividad.getFechaInicio().after(lunes) && actividad.getFechaFin().before(domingo)) {
				listaActividadesFiltrada.add(actividad);
			}
		}

		return listaActividadesFiltrada;
	}

	public List<ActividadDto> listarActividadesFecha(Timestamp inicio, Timestamp fin, InstalacionDto instalacion) {
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD A WHERE a.instalacion = ? AND a.fechaInicio BETWEEN ? AND ?", instalacion,
				inicio, fin);
		return listaActividades;
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularLunes(Timestamp fecha) {
		int diaDeLaSemana = convertirDia(fecha.getDay());

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_WEEK, -diaDeLaSemana);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		Timestamp lunes = new Timestamp(cal.getTime().getTime());
		lunes.setNanos(0);

		return lunes;
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularDomingo(Timestamp fecha) {
		int diaDeLaSemana = convertirDia(fecha.getDay());

		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_WEEK, 6 - diaDeLaSemana);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		Timestamp domingo = new Timestamp(cal.getTime().getTime());
		domingo.setNanos(0);

		return domingo;
	}

	private int convertirDia(int dia) {
		if (dia != 0) {
			return dia - 1;
		} else {
			return 6;
		}
	}

	public InstalacionDto[] listarInstalaciones() {
		InstalacionDto[] listaInstalaciones = db.executeQueryPojo(InstalacionDto.class, "SELECT * FROM INSTALACION")
				.toArray(new InstalacionDto[0]);
		return listaInstalaciones;
	}

	public List<RecursoDto> listarRecursos() {
		return db.executeQueryPojo(RecursoDto.class, "SELECT * FROM RECURSO");
	}

	public int obtenerCantidadRecursos(String nombreTipo, String nombreInstalacion) {
		List<DisponeDto> listaDispone = db.executeQueryPojo(DisponeDto.class,
				"SELECT * FROM DISPONE D WHERE d.nombreInstalacion = ? AND d.nombreRecurso IN (SELECT n.nombreRecurso FROM NECESITA N WHERE n.nombreTipoActividad = ?)",
				nombreInstalacion, nombreTipo);
		List<NecesitaDto> listaNecesita = db.executeQueryPojo(NecesitaDto.class,
				"SELECT * FROM NECESITA N WHERE n.nombreTipoActividad = ?", nombreTipo);

		int menor = 1000;
		if (listaDispone.size() < listaNecesita.size()) {
			menor = 0;
		}

		for (DisponeDto dtoDispone : listaDispone) {
			if (dtoDispone.getCantidad() < menor) {
				menor = dtoDispone.getCantidad();
			}
		}
		return menor;
	}

	public boolean existeMonitor(String idMonitor) {
		List<MonitorDto> listaMonitores = db.executeQueryPojo(MonitorDto.class,
				"SELECT * FROM MONITOR M WHERE m.idMonitor = ?", idMonitor);

		return listaMonitores.size() > 0;
	}

	public boolean estaAsignado(String idMonitor, Timestamp fechaInicio, Timestamp fechaFin) {
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD A WHERE a.monitor = ?", idMonitor);

		int contador = 0;
		for (ActividadDto actividadDto : listaActividades) {
			if (!colisiona(fechaInicio, fechaFin, actividadDto.getFechaInicio(), actividadDto.getFechaFin())) {
				contador++;
			}
		}

		return listaActividades.size() - contador > 0;
	}

	private boolean colisiona(Timestamp fechaInicio, Timestamp fechaFin, Timestamp fechaInicio2, Timestamp fechaFin2) {
		if (distintaFecha(fechaInicio, fechaInicio2)
				|| !colisionaHora(fechaInicio, fechaFin, fechaInicio2, fechaFin2)) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	private boolean distintaFecha(Timestamp fechaInicio, Timestamp fechaInicio2) {
		return fechaInicio2.getYear() != fechaInicio.getYear() || fechaInicio2.getMonth() != fechaInicio.getMonth()
				|| fechaInicio2.getDate() != fechaInicio.getDate();
	}

	@SuppressWarnings("deprecation")
	private boolean colisionaHora(Timestamp fechaInicio, Timestamp fechaFin, Timestamp fechaInicio2,
			Timestamp fechaFin2) {
		return (fechaInicio2.getHours() >= fechaInicio.getHours() && fechaFin2.getHours() <= fechaFin.getHours())
				|| (fechaInicio2.getHours() < fechaInicio.getHours() && fechaFin2.getHours() > fechaFin.getHours())
				|| (fechaInicio2.getHours() >= fechaInicio.getHours() && fechaFin2.getHours() > fechaFin.getHours()
						&& fechaInicio2.getHours() < fechaFin.getHours())
				|| (fechaInicio2.getHours() < fechaInicio.getHours() && fechaFin2.getHours() <= fechaFin.getHours()
						&& fechaFin2.getHours() > fechaInicio.getHours());
	}

	public void asignarMonitor(String idMonitor, ActividadDto actividadDto) {
		db.executeUpdate("UPDATE ACTIVIDAD SET monitor = ? WHERE idActividad = ?", idMonitor,
				actividadDto.getIdActividad());
	}

	public boolean instalacionOcupada(String instalacion, Timestamp fechaInicio, Timestamp fechaFin) {
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD A WHERE a.instalacion = ?", instalacion);

		List<AlquilerDto> listaAlquileres = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER A WHERE a.instalacion = ?", instalacion);

		int contador = 0;
		for (ActividadDto actividadDto : listaActividades) {
			if (!colisiona(fechaInicio, fechaFin, actividadDto.getFechaInicio(), actividadDto.getFechaFin())) {
				contador++;
			}
		}
		for (AlquilerDto alquilerDto : listaAlquileres) {
			if (alquilerDto.getEnVigor() == 0
					|| !colisiona(fechaInicio, fechaFin, alquilerDto.getFechaInicio(), alquilerDto.getFechaFin())) {
				contador++;
			}
		}

		return listaActividades.size() + listaAlquileres.size() - contador > 0;
	}

	public List<Colisionable> obtenerColisiones(String instalacion, Timestamp fechaInicio, Timestamp fechaFin) {
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD A WHERE a.instalacion = ?", instalacion);

		List<AlquilerDto> listaAlquileres = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER A WHERE a.instalacion = ?", instalacion);

		List<Colisionable> listaColisiones = new ArrayList<>();
		for (ActividadDto actividadDto : listaActividades) {
			if (colisiona(fechaInicio, fechaFin, actividadDto.getFechaInicio(), actividadDto.getFechaFin())) {
				listaColisiones.add(actividadDto);
			}
		}
		for (AlquilerDto alquilerDto : listaAlquileres) {
			if (alquilerDto.getEnVigor() == 1
					&& colisiona(fechaInicio, fechaFin, alquilerDto.getFechaInicio(), alquilerDto.getFechaFin())) {
				listaColisiones.add(alquilerDto);
			}
		}

		return listaColisiones;
	}

	public List<AlquilerDto> listarAlquileres(Timestamp fecha, String instalacion) {
		Timestamp lunes = calcularLunes(fecha);
		Timestamp domingo = calcularDomingo(fecha);
		List<AlquilerDto> listaAlquileres = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER A WHERE a.instalacion = ?", instalacion);

		List<AlquilerDto> listaAlquileresFiltrada = new ArrayList<>();
		for (AlquilerDto alquiler : listaAlquileres) {
			if (alquiler.getFechaInicio().after(lunes) && alquiler.getFechaFin().before(domingo)) {
				listaAlquileresFiltrada.add(alquiler);
			}
		}

		return listaAlquileresFiltrada;
	}

	public List<SocioDto> obtenerSociosActividad(ActividadDto actividad) {
		List<SocioDto> listaSocios = db.executeQueryPojo(SocioDto.class,
				"SELECT * FROM SOCIO S, RESERVA R WHERE s.idSocio = r.idSocio AND r.idActividad = ?",
				actividad.getIdActividad());

		return listaSocios;
	}

	public void borrarActividad(ActividadDto actividad) {
		db.executeUpdate("DELETE FROM ACTIVIDAD WHERE idActividad = ?", actividad.getIdActividad());
		db.executeUpdate("DELETE FROM RESERVA WHERE idActividad = ?", actividad.getIdActividad());
	}

	public boolean existeSocioNumero(String numero) {
		List<SocioDto> listaSocios = db.executeQueryPojo(SocioDto.class, "SELECT * FROM SOCIO S WHERE s.idSocio = ?",
				numero);

		return listaSocios.size() > 0;
	}

	public boolean existeSocioDNI(String DNI) {
		List<SocioDto> listaSocios = db.executeQueryPojo(SocioDto.class, "SELECT * FROM SOCIO S WHERE s.dni = ?", DNI);

		return listaSocios.size() > 0;
	}

	public boolean existeSocioInfo(String info) {
		List<SocioDto> listaSocios = db.executeQueryPojo(SocioDto.class,
				"SELECT * FROM SOCIO S WHERE s.infoContacto = ?", info);

		return listaSocios.size() > 0;
	}

	public boolean existeReservaNumero(String numero, ActividadDto actividad) {
		List<ReservaDto> listaReservas = db.executeQueryPojo(ReservaDto.class,
				"SELECT * FROM RESERVA R WHERE r.idSocio = ? AND r.idActividad = ?", numero,
				actividad.getIdActividad());

		return listaReservas.size() > 0;
	}

	public boolean existeReservaDNI(String DNI, ActividadDto actividad) {
		String idSocio = db.executeQueryPojo(SocioDto.class, "SELECT * FROM SOCIO S WHERE s.dni = ?", DNI).get(0)
				.getIdSocio();
		List<ReservaDto> listaReservas = db.executeQueryPojo(ReservaDto.class,
				"SELECT * FROM RESERVA R WHERE r.idSocio = ? AND r.idActividad = ?", idSocio,
				actividad.getIdActividad());

		return listaReservas.size() > 0;
	}

	public boolean existeReservaInfo(String info, ActividadDto actividad) {
		String idSocio = db.executeQueryPojo(SocioDto.class, "SELECT * FROM SOCIO S WHERE s.infoContacto = ?", info)
				.get(0).getIdSocio();
		List<ReservaDto> listaReservas = db.executeQueryPojo(ReservaDto.class,
				"SELECT * FROM RESERVA R WHERE r.idSocio = ? AND r.idActividad = ?", idSocio,
				actividad.getIdActividad());

		return listaReservas.size() > 0;
	}

	public void reservarPlazaNumero(String numero, ActividadDto actividad) {
		db.executeUpdate("INSERT INTO RESERVA VALUES(?, ?)", numero, actividad.getIdActividad());
		db.executeUpdate("UPDATE ACTIVIDAD SET plazasDisponibles = plazasDisponibles-1 WHERE idActividad = ?",
				actividad.getIdActividad());
	}

	public void reservarPlazaDNI(String DNI, ActividadDto actividad) {
		String idSocio = db.executeQueryPojo(SocioDto.class, "SELECT * FROM SOCIO S WHERE s.dni = ?", DNI).get(0)
				.getIdSocio();
		db.executeUpdate("INSERT INTO RESERVA VALUES(?, ?)", idSocio, actividad.getIdActividad());
		db.executeUpdate("UPDATE ACTIVIDAD SET plazasDisponibles = plazasDisponibles-1 WHERE idActividad = ?",
				actividad.getIdActividad());
	}

	public void reservarPlazaInfo(String info, ActividadDto actividad) {
		String idSocio = db.executeQueryPojo(SocioDto.class, "SELECT * FROM SOCIO S WHERE s.infoContacto = ?", info)
				.get(0).getIdSocio();
		db.executeUpdate("INSERT INTO RESERVA VALUES(?, ?)", idSocio, actividad.getIdActividad());
		db.executeUpdate("UPDATE ACTIVIDAD SET plazasDisponibles = plazasDisponibles-1 WHERE idActividad = ?",
				actividad.getIdActividad());
	}

	public List<AlquilerDto> listarAlquileres() {

		List<AlquilerDto> listaAlquileres = db.executeQueryPojo(AlquilerDto.class, "SELECT * FROM ALQUILER ");
		for (AlquilerDto a : listaAlquileres) {
			System.out.println(a.getIdAlquiler() + "  f-i: " + a.getFechaInicio() + " f-f:  " + a.getFechaFin()
					+ "  e.v:  " + a.getEnVigor() + " pg: " + a.getPagado() + "  ins: " + a.getInstalacion() + "    "
					+ a.getIdSocio());
		}

		return listaAlquileres;
	}

	public void añadirAlquilerEnCuota(String idSocio, Timestamp fecha, String instalacion, int duracion) {

		List<InstalacionDto> instalaciones = db.executeQueryPojo(InstalacionDto.class,
				"SELECT * FROM INSTALACION A WHERE a.nombre= ?", instalacion);

		double precioHora = 0;
		for (InstalacionDto i : instalaciones) {
			precioHora = i.getPrecioPorHora();
		}
		System.out.println("precioHora:  " + precioHora + "  duracion  " + duracion);
		double precio = duracion * precioHora;
		System.out.println("precio:  " + precio);

		List<CuotaDto> precioTotal = db.executeQueryPojo(CuotaDto.class, "SELECT * FROM CUOTA C WHERE c.idSocio= ?",
				idSocio);
		for (CuotaDto c : precioTotal) {
			precio += c.getTotal();
		}
		System.out.println("---------------------------cuotas antes--------");
		List<CuotaDto> cuotaAntes = db.executeQueryPojo(CuotaDto.class, "SELECT * FROM CUOTA C ");
		for (CuotaDto c : cuotaAntes) {
			System.out.println(c.getIdSocio() + "- month   -" + c.getMes() + "   -total --  " + c.getTotal());
		}
		db.executeUpdate("UPDATE CUOTA SET total = ? WHERE idSocio = ?", precio, idSocio);

		System.out.println("---------------------------cuotas despues--------");

		List<CuotaDto> cuotasDespues = db.executeQueryPojo(CuotaDto.class, "SELECT * FROM CUOTA C ");
		for (CuotaDto c : cuotasDespues) {
			System.out.println(c.getIdSocio() + "- month   -" + c.getMes() + "   -total --  " + c.getTotal());
		}
	}

	public String getIdSocio(String dni) {
		List<SocioDto> socios = db.executeQueryPojo(SocioDto.class, "SELECT * FROM SOCIO S WHERE s.dni=?", dni);
		String id = "";
		for (SocioDto s : socios) {
			id = s.getIdSocio();
		}
		return id;
	}

	public void generarTicket(Timestamp horaInicio, Timestamp horaFinal, String instalacion, int duracion) {
		List<InstalacionDto> instalaciones = db.executeQueryPojo(InstalacionDto.class,
				"SELECT * FROM INSTALACION A WHERE a.nombre= ?", instalacion);

		double precioHora = 0;
		for (InstalacionDto i : instalaciones) {
			precioHora = i.getPrecioPorHora();
		}
		double precio = duracion * precioHora;
		String fechaActual = new Timestamp(System.currentTimeMillis()).toString();

		System.out.println(
				"************************************************************************************************");
		System.out.println(
				"----------------------------------------------------------------------------------------------");
		System.out.println(
				"-----------------------TICKET RECIBO DE ALQUILER DE INSTALACIONES-----------------------------");
		System.out.println(
				"--------FECHA ACTUAL:" + fechaActual + "--------------------------------------------------------");
		System.out.println(
				"-----------------------DESCRIPCIÓN DE LA COMPRA-----------------------------------------------");
		System.out.println(
				"--------INSTALACION A ALQUILAR: " + instalacion + "-----------------------------------------------");
		System.out.println(
				"--------FECHA INICIO ALQUILER: " + horaInicio.toString() + "----------------------------------");
		System.out.println(
				"--------FECHA FINAL ALQUILER: " + horaFinal.toString() + "------------------------------------");
		System.out.println(
				"--------PRECIO POR HORA: " + precioHora + "--------------------------------------------------------");
		System.out.println(
				"--------IVA: 21%-------------------------------------------------------------------------------");
		System.out.println(
				"--------PRECIO TOTAL A PAGAR: " + precio + "--------------------------------------------------------");
		System.out.println(
				"------------------------------------------------------------------------------------------------");
		System.out.println(
				"****************************GRACIAS POR SU VISITA************************************************");

	}

	public List<AlquilerDto> listarAlquileresNoPagados() {
		List<AlquilerDto> listaAlquileresSinPagar = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER A WHERE a.enVigor= ? AND a.pagado= ?", 1, 0);
		return listaAlquileresSinPagar;
	}

	@SuppressWarnings("deprecation")
	public void cargarDatosContabilidad() {
		Timestamp fechaActual = new Timestamp(System.currentTimeMillis());

		List<AlquilerDto> listaAlquileresSinPagar = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER A WHERE a.enVigor= ? AND a.pagado= ?", 1, 0);

		int diaDelMes = fechaActual.getDate(); // dia del mes actual
		int mes = fechaActual.getMonth(); // mes actual(0 enero -11 diciembre)

		for (AlquilerDto a : listaAlquileresSinPagar) {
			Timestamp fechaI = a.getFechaInicio();

			if (fechaActual.compareTo(fechaI) >= 0) {
				String idAl = a.getIdAlquiler();
				String instalacion = a.getInstalacion();
				String idSocio = a.getIdSocio();
				String horaInicio = a.getFechaInicio().toString().substring(11, 13);
				String horaFin = a.getFechaFin().toString().substring(11, 13);
				int duracion = Integer.parseInt(horaFin) - Integer.parseInt(horaInicio);

				añadirPrecioAlquilerEnCuota(diaDelMes, mes, idSocio, instalacion, duracion);
				marcarAlquilerPagado(idAl);
			}
		}
	}

	private void añadirPrecioAlquilerEnCuota(int diaDelMes, int mes, String idSocio, String instalacion, int duracion) {
		List<InstalacionDto> instalaciones = db.executeQueryPojo(InstalacionDto.class,
				"SELECT * FROM INSTALACION A WHERE a.nombre= ?", instalacion);
		double precioHora = 0;
		for (InstalacionDto i : instalaciones) {
			precioHora = i.getPrecioPorHora();
		}
		double precio = duracion * precioHora;

		int nuevoMes = calcularMes(mes, diaDelMes);
		List<CuotaDto> precioTotal = db.executeQueryPojo(CuotaDto.class,
				"SELECT * FROM CUOTA C WHERE c.idSocio= ? AND c.mes= ?", idSocio, nuevoMes);

		for (CuotaDto c : precioTotal) {
			precio += c.getTotal();
			db.executeUpdate("UPDATE CUOTA SET total = ? WHERE idSocio = ? AND mes= ?", precio, idSocio, nuevoMes);
		}
	}

	private int calcularMes(int mes, int diaDelMes) {
		// si es menor que 20 se suma 1, si no 2
		// si el mes es 11, el mes es 0
		if (diaDelMes < 20) {
			if (mes + 1 > 11) {
				mes = 0;
			} else {
				mes = mes + 1;
			}
		} else {
			if (mes + 2 == 12) {
				mes = 0;
			}
			if (mes + 2 == 13) {
				mes = 1;
			} else {
				mes = mes + 2;
			}
		}
		return mes;
	}

	private void marcarAlquilerPagado(String idAlquiler) {
		db.executeUpdate("UPDATE ALQUILER SET pagado = ? WHERE idAlquiler = ?", 1, idAlquiler);
	}

	public List<AlquilerDto> listarAlquileres(String nombreInstalacion) {
		List<AlquilerDto> listaActividades = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER WHERE instalacion = ? AND enVigor = 1", nombreInstalacion);
		List<AlquilerDto> result = new ArrayList<>();
		for (AlquilerDto alq : listaActividades) {
			if (!alq.getFechaInicio().before(new Timestamp(System.currentTimeMillis()))) {
				result.add(alq);
			}
		}
		return result;
	}

	public SocioDto buscarSocioPorId(String id) {
		List<SocioDto> socio = db.executeQueryPojo(SocioDto.class, "select * from socio where idSocio = ?", id);

		if (socio.isEmpty())
			return null;
		return socio.get(0);
	}

	public void cancelarAlquiler(AlquilerDto dto) {
		db.executeUpdate("UPDATE ALQUILER SET enVigor = 0, pagado = 1 WHERE idAlquiler = ?", dto.getIdAlquiler());
	}

	public List<ActividadDto> listarActividadesDia(InstalacionDto instalacion, Timestamp dia) {
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD A WHERE a.instalacion = ? AND a.fechaInicio BETWEEN ? AND ?",
				instalacion.getNombre(), calcularInicioDia(dia).toString(), calcularFinDia(dia).toString());

		return listaActividades;
	}

	public List<ActividadDto> listarActividadesEntreFechas(String tipo, Timestamp diaInicio, Timestamp diaFin) {
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD A WHERE a.tipo = ? AND a.fechaInicio BETWEEN ? AND ?", tipo,
				diaInicio.toString(), diaFin.toString());

		return listaActividades;
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularInicioDia(Timestamp date) {
		return new Timestamp(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0, 0);
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularFinDia(Timestamp date) {
		return new Timestamp(date.getYear(), date.getMonth(), date.getDate() + 1, 0, 0, 0, 0);
	}

	public void añadirAlquiler(AlquilerDto alq) {
		db.executeUpdate(
				"INSERT INTO ALQUILER(idAlquiler, fechaInicio, fechaFin, enVigor, pagado, idSocio, instalacion) VALUES(?, ?, ?, ?, ?, ?, ?)",
				alq.getIdAlquiler(), alq.getFechaInicio(), alq.getFechaFin(), alq.getEnVigor(), alq.getPagado(),
				alq.getIdSocio(), alq.getInstalacion());
	}

	public void añadirTercero(String idSocio) {
		db.executeUpdate("INSERT INTO SOCIO(idSocio, dni, nombre, apellido, infoContacto) VALUES(?, ?, ?, ?, ?)",
				idSocio, idSocio, "EMPRESA", "PRIVADA", "NO EXISTE");
	}

	public DisponeDto[] listarRecursosInstalacion(InstalacionDto ins) {
		DisponeDto[] listaRecursos = db.executeQueryPojo(DisponeDto.class,
				"Select * from dispone where nombreInstalacion = ?", ins.getNombre()).toArray(new DisponeDto[0]);
		return listaRecursos;
	}

	public void updateDispone(DisponeDto dis) {
		DisponeDto[] listaRecursos = db.executeQueryPojo(DisponeDto.class,
				"Select * from dispone where nombreInstalacion = ? and nombreRecurso = ?", dis.getNombreInstalacion(),
				dis.getNombreRecurso()).toArray(new DisponeDto[0]);

		if (listaRecursos.length == 0) {
			db.executeUpdate("INSERT INTO DISPONE(nombreRecurso, nombreInstalacion, cantidad) values(?,?,?)",
					dis.getNombreRecurso(), dis.getNombreInstalacion(), dis.getCantidad());
		} else {
			db.executeUpdate(
					"UPDATE DISPONE SET cantidad = cantidad + ? where nombreRecurso = ? and nombreInstalacion = ?",
					dis.getCantidad(), dis.getNombreRecurso(), dis.getNombreInstalacion());
		}

		listaRecursos = db.executeQueryPojo(DisponeDto.class,
				"Select * from dispone where nombreInstalacion = ? and nombreRecurso = ?", dis.getNombreInstalacion(),
				dis.getNombreRecurso()).toArray(new DisponeDto[0]);

		for (DisponeDto d : listaRecursos) {
			if (d.getCantidad() == 0) {
				db.executeUpdate("DELETE FROM DISPONE WHERE nombreInstalacion = ? and nombreRecurso = ?",
						d.getNombreInstalacion(), d.getNombreRecurso());
			}
		}
	}

	public boolean comprobarDisponibilidadSocio(String idSocio, Timestamp fechaInicioAlquiler,
			Timestamp fechaFinAlquiler) {
		List<AlquilerDto> listaAlquilerDia = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER A WHERE a.idSocio = ? AND a.fechaInicio BETWEEN ? AND ?", idSocio,
				calcularInicioDia(fechaInicioAlquiler).toString(), calcularFinDia(fechaFinAlquiler).toString());
		for (AlquilerDto a : listaAlquilerDia) {
			if (colisiona(a.getFechaInicio(), a.getFechaFin(), fechaInicioAlquiler, fechaFinAlquiler)) {
				return false;
			}
		}
		return true;
	}

	public List<AlquilerDto> listarAlquileresDia(InstalacionDto instalacion, Timestamp dia) {
		List<AlquilerDto> listaActividades = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER A WHERE a.instalacion = ? AND a.fechaInicio BETWEEN ? AND ?",
				instalacion.getNombre(), calcularInicioDia(dia).toString(), calcularFinDia(dia).toString());

		return listaActividades;
	}

	public List<ActividadDto> listarActividadesTipo(String tipo) {
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD A WHERE a.tipo = ?", tipo);

		return listaActividades;
	}

	public void asignarRecursos(String nombreRecurso, String nombreInstalacion, int cantidad) {
		if (!db.executeQueryPojo(DisponeDto.class,
				"SELECT * FROM DISPONE WHERE nombreRecurso = ? and nombreInstalacion = ?", nombreRecurso,
				nombreInstalacion).isEmpty()) {
			db.executeUpdate(
					"UPDATE DISPONE SET cantidad = cantidad + ? WHERE nombreRecurso = ? and nombreInstalacion = ?",
					cantidad, nombreRecurso, nombreInstalacion);
		} else {
			if (db.executeQueryPojo(RecursoDto.class, "SELECT * FROM RECURSO WHERE nombre = ?", nombreRecurso)
					.isEmpty()) {
				db.executeUpdate("INSERT INTO RECURSO(nombre) VALUES(?)", nombreRecurso);
			}
			db.executeUpdate("INSERT INTO DISPONE(cantidad, nombreRecurso, nombreInstalacion) VALUES(?, ?, ?)",
					cantidad, nombreRecurso, nombreInstalacion);
		}
	}

	public void añadirActividad(ActividadDto act) {
		db.executeUpdate("INSERT INTO ACTIVIDAD (idActividad, fechaInicio, fechaFin, instalacion) VALUES (?, ?, ?, ?)",
				act.getIdActividad(), act.getFechaInicio(), act.getFechaFin(), act.getInstalacion());
	}

	public boolean listarActividades() {
		List<ActividadDto> acts = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD WHERE idActividad = ?", "MANTENIMIENTO INF");

		return acts.size() > 0;
	}

	public List<ActividadDto> listarMantenimiento() {
		List<ActividadDto> acts = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD WHERE idActividad = ?", "MANTENIMIENTO INF");
		return acts;
	}

	public ActividadDto listarMantenimiento(InstalacionDto inst) {
		List<ActividadDto> acts = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD WHERE idActividad = ? AND instalacion = ?", "MANTENIMIENTO INF",
				inst.getNombre());
		return acts.get(0);
	}

	public ActividadDto[] listarInstalacionesMantenimiento() {
		ActividadDto[] acts = db.executeQueryPojo(ActividadDto.class, "SELECT * FROM ACTIVIDAD WHERE idActividad = ?",
				"MANTENIMIENTO INF").toArray(new ActividadDto[0]);
		return acts;
	}

	public void modificarHoraMantenimiento(Timestamp fin) {
		db.executeUpdate("UPDATE ACTIVIDAD SET fechaFin = ? WHERE idActividad = ?", fin, "MANTENIMIENTO INF");
	}

}