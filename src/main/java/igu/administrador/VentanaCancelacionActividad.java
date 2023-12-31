package igu.administrador;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;

import persistencia.administrador.ActividadDto;

public class VentanaCancelacionActividad extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblDuracion;
	private JCheckBox chckSemana;
	private JCheckBox chckVariasSemanas;
	private JCheckBox chckMeses;
	private JPanel panelFechas;
	private JPanel p2VariasSemana;
	private JPanel p1Semana;
	private JPanel p3Meses;
	private JLabel lblSFechaInicio;
	private JLabel lblSFechaFinal;
	private JButton btnCancelarActividades;
	private JButton btnAtras;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCheckBox chckUnicaActividad;
	private JPanel pnEstaActividad;
	private JLabel lblVSSeleccionSemana;
	private JLabel lblMFechaInicio;
	private JLabel lblMFechaFin;
	private PanelActividad panelActividad;
	private JCalendar calendarSemana;
	private JDateChooser dateChooserVSInicio;
	private JDateChooser dateChooserVSFin;
	private JDateChooser dateChooserVMInicio;
	private JDateChooser dateChooserVMFin;
	private JMonthChooser monthChooser;
	private JMonthChooser monthChooser_1;
	private JCheckBox chckTodo;

	/**
	 * Create the dialog.
	 *
	 * @param panelActividad
	 */
	public VentanaCancelacionActividad(PanelActividad panelActividad) {
		this.panelActividad = panelActividad;
		setResizable(false);
		setTitle("Cancelación de actividades");
		setBounds(100, 100, 485, 530);
		getContentPane().setLayout(null);
		contentPanel.setBounds(10, 10, 471, 497);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		contentPanel.add(getLblDuracion());
		contentPanel.add(getChckSemana());
		contentPanel.add(getChckVariasSemanas());
		contentPanel.add(getChckMeses());
		contentPanel.add(getPanelFechas());
		contentPanel.add(getBtnCancelarActividades());
		contentPanel.add(getBtnAtras());
		contentPanel.add(getChckUnicaActividad());
		contentPanel.add(getChckTodo());
	}

	private JLabel getLblDuracion() {
		if (lblDuracion == null) {
			lblDuracion = new JLabel("Duración del tiempo de cancelación de la actividad:");
			lblDuracion.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblDuracion.setBounds(20, 10, 386, 27);
		}
		return lblDuracion;
	}

	private JCheckBox getChckSemana() {
		if (chckSemana == null) {
			chckSemana = new JCheckBox("Durante una semana concreta");
			chckSemana.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout) (panelFechas.getLayout());
					cl.show(panelFechas, "Semana");
				}
			});
			buttonGroup.add(chckSemana);
			chckSemana.setFont(new Font("Tahoma", Font.PLAIN, 15));
			chckSemana.setBounds(20, 67, 254, 21);
		}
		return chckSemana;
	}

	private JCheckBox getChckVariasSemanas() {
		if (chckVariasSemanas == null) {
			chckVariasSemanas = new JCheckBox("Durante varias semanas");
			chckVariasSemanas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout) (panelFechas.getLayout());
					cl.show(panelFechas, "VariasSemanas");
				}
			});
			buttonGroup.add(chckVariasSemanas);
			chckVariasSemanas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			chckVariasSemanas.setBounds(20, 91, 192, 21);
		}
		return chckVariasSemanas;
	}

	private JCheckBox getChckMeses() {
		if (chckMeses == null) {
			chckMeses = new JCheckBox("Durante un mes o varios meses");
			chckMeses.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout) (panelFechas.getLayout());
					cl.show(panelFechas, "Meses");
				}
			});
			buttonGroup.add(chckMeses);
			chckMeses.setFont(new Font("Tahoma", Font.PLAIN, 15));
			chckMeses.setBounds(20, 115, 254, 21);
		}
		return chckMeses;
	}

	private JPanel getPanelFechas() {
		if (panelFechas == null) {
			panelFechas = new JPanel();
			panelFechas.setBounds(14, 185, 417, 229);
			panelFechas.setLayout(new CardLayout(0, 0));
			panelFechas.add(getPnEstaActividad(), "EstaActividad");
			panelFechas.add(getP1Semana(), "Semana");
			panelFechas.add(getP2VariasSemana(), "VariasSemanas");
			panelFechas.add(getP3Meses(), "Meses");
		}
		return panelFechas;
	}

	private JPanel getP2VariasSemana() {
		if (p2VariasSemana == null) {
			p2VariasSemana = new JPanel();
			p2VariasSemana.setLayout(null);
			p2VariasSemana.add(getLblSFechaInicio());
			p2VariasSemana.add(getLblSFechaFinal());
			p2VariasSemana.add(getDateChooserVSInicio());
			p2VariasSemana.add(getDateChooserVSFin());
		}
		return p2VariasSemana;
	}

	private JPanel getP1Semana() {
		if (p1Semana == null) {
			p1Semana = new JPanel();
			p1Semana.setLayout(null);
			p1Semana.add(getLblVSSeleccionSemana());
			p1Semana.add(getCalendarSemana());
		}
		return p1Semana;
	}

	private JPanel getP3Meses() {
		if (p3Meses == null) {
			p3Meses = new JPanel();
			p3Meses.setLayout(null);
			p3Meses.add(getLblMFechaInicio());
			p3Meses.add(getLblMFechaFin());
			p3Meses.add(getDateChooserVMInicio());
			p3Meses.add(getDateChooserVMFin());
		}
		return p3Meses;
	}

	private JLabel getLblSFechaInicio() {
		if (lblSFechaInicio == null) {
			lblSFechaInicio = new JLabel("Fecha inicio cancelación:");
			lblSFechaInicio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSFechaInicio.setBounds(27, 10, 173, 24);
		}
		return lblSFechaInicio;
	}

	private JLabel getLblSFechaFinal() {
		if (lblSFechaFinal == null) {
			lblSFechaFinal = new JLabel("Fecha final cancelación:");
			lblSFechaFinal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSFechaFinal.setBounds(228, 6, 161, 32);
		}
		return lblSFechaFinal;
	}

	private JButton getBtnCancelarActividades() {
		if (btnCancelarActividades == null) {
			btnCancelarActividades = new JButton("Cancelar Actividades");
			btnCancelarActividades.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (getChckSemana().isSelected()) {
						cancelarActividades(1);
					} else if (getChckVariasSemanas().isSelected()) {
						cancelarActividades(2);
					} else if (getChckMeses().isSelected()) {
						cancelarActividades(3);
					} else if (getChckTodo().isSelected()) {
						cancelarActividades(4);
					} else {
						cancelarActividades(0);
					}
				}
			});
			btnCancelarActividades.setBackground(new Color(124, 252, 0));
			btnCancelarActividades.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnCancelarActividades.setBounds(54, 425, 179, 36);
		}
		return btnCancelarActividades;
	}

	protected void cancelarActividades(int i) {
		if (i == 1) { // elimina las actividades de esta semana
			cancelarActividadSemana();

		} else if (i == 2) { // elimina las actividades durante varias semanas
			cancelarActividadVariasSemanas();

		} else if (i == 3) { // elimina las actividades durante varios meses
			cancelarActividadVariosMeses();

		} else if (i == 4) { // elimina todas las actividades futuras
			cancelarTodas();
			dispose();

		} else { // elimina solo la que esta seleccionada
			panelActividad.cancelarActividad(panelActividad.getActividadDto());

		}

		this.dispose();
		panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador().repintarTabla();
		panelActividad.getVentanaAsignacionMonitores().repintaPanelesActividad();
	}

	private void cancelarActividadVariosMeses() {
		if (getDateChooserVMInicio().getDate() == null || getDateChooserVMFin().getDate() == null) {
			JOptionPane.showMessageDialog(this,
					"Es necesario seleccionar dos fechas. Se puede seleccionar el mismo mes en ambos.");
		} else {

			ActividadDto actividad = panelActividad.getActividadDto(); // actividad seleccionada
			LocalDate ahora = LocalDate.now(); // fecha actual

			LocalDate fechaSeleccionadaInicio = getDateChooserVMInicio().getDate().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fechaSeleccionadaFin = getDateChooserVMFin().getDate().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();

			// comprobacion de que los datos sea correcta
			if (fechaSeleccionadaInicio.isBefore(fechaSeleccionadaFin)
					&& comprobarMesAño(ahora, fechaSeleccionadaInicio)
					&& comprobarMesAño(ahora, fechaSeleccionadaInicio)) {
				// seleccionamos el ultimo dia del ultimo mes a borrar
				LocalDate ultdiaSelect = fechaSeleccionadaFin.with(lastDayOfMonth());
				Timestamp ultDiaT = Timestamp.valueOf(ultdiaSelect.atTime(23, 59));

				Timestamp priDiaT;
				// si estamos en el primer mes a borrar
				if (ahora.getYear() == fechaSeleccionadaInicio.getYear()
						&& ahora.getMonth() == fechaSeleccionadaInicio.getMonth()) {
					priDiaT = Timestamp.valueOf(LocalDateTime.now());
				} else {
					LocalDate primerDiaSelect = fechaSeleccionadaInicio.with(firstDayOfMonth());
					priDiaT = Timestamp.valueOf(primerDiaSelect.atTime(07, 59));
				}
				// lista de actividades de ese tipo entre estas fechas
				List<ActividadDto> acts = panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador()
						.getAdministradorCRUD().listarActividadesEntreFechas(actividad.getTipo(), priDiaT, ultDiaT);

				boolean avisar = false;
				for (ActividadDto a : acts) {
					if (compobarFechas(a, actividad)) {
						avisar = true;
						panelActividad.avisarSocios(a);
						panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador().getAdministradorCRUD()
								.borrarActividad(a);
					}
				}

				if (avisar) {
					panelActividad.mostrarVentanaContactoMeses();
				}
			}

		}

	}

	private boolean comprobarMesAño(LocalDate ahora, LocalDate fecha) {
		if (ahora.getYear() == fecha.getYear()) {
			if (ahora.getMonthValue() > fecha.getMonthValue()) {
				return false;
			}
		} else if (ahora.getYear() > fecha.getYear()) {
			return false;
		}
		return true;

	}

	private void cancelarActividadVariasSemanas() {
		if (getDateChooserVSInicio().getDate() == null || getDateChooserVSFin().getDate() == null) {
			JOptionPane.showMessageDialog(this, "Es necesario seleccionar dos fechas en semanas diferentes.");
		} else {
			ActividadDto actividad = panelActividad.getActividadDto(); // actividad seleccionada
			LocalDate ahora = LocalDate.now(); // fecha actual

			LocalDate fechaSeleccionadaInicio = getDateChooserVSInicio().getDate().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fechaSeleccionadaFin = getDateChooserVSFin().getDate().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();

			if (fechaSeleccionadaInicio.isBefore(fechaSeleccionadaFin)
					&& isLocalDateInTheSameWeek(ahora, fechaSeleccionadaInicio)
					|| (!isLocalDateInTheSameWeek(ahora, fechaSeleccionadaInicio)
							&& ahora.compareTo(fechaSeleccionadaInicio) < 0)) {
				// calculams el domingo de la ultima semana seleccionada
				LocalDate domingoSelect = fechaSeleccionadaFin.with(DayOfWeek.SUNDAY);
				Timestamp domT = Timestamp.valueOf(domingoSelect.atTime(23, 59));

				// calculamos el lunes de la semana
				Timestamp lunesT;
				if (isLocalDateInTheSameWeek(domingoSelect, ahora)) { // si el dia actual esta en la semana seleccionada
					lunesT = Timestamp.valueOf(LocalDateTime.now()); // el lunes pasa a ser el dia de ahora
				} else {
					LocalDate lunesSelect = fechaSeleccionadaInicio.with(DayOfWeek.MONDAY);
					lunesT = Timestamp.valueOf(lunesSelect.atTime(07, 59));
				}
				// lista de actividades de ese tipo entre estas fechas
				List<ActividadDto> acts = panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador()
						.getAdministradorCRUD().listarActividadesEntreFechas(actividad.getTipo(), lunesT, domT);

				boolean avisar = false;
				for (ActividadDto a : acts) {
					if (compobarFechas(a, actividad)) {
						avisar = true;
						panelActividad.avisarSocios(a);
						panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador().getAdministradorCRUD()
								.borrarActividad(a);
					}
				}

				if (avisar) {
					panelActividad.mostrarVentanaContactoMeses();
				}
			}
		}

//		List<ActividadDto> acti = panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador().getAdministradorCRUD().listarActividadesTipo(actividad.getTipo());
//		for(ActividadDto a : acti) {
//			System.out.println(" -----------"+ a.getIdActividad()+"    fecha inicio  :  "+ a.getFechaInicio()+"    fecha fin:  "+ a.getFechaFin());
//
//		}

	}

	private void cancelarActividadSemana() {
		if (getCalendarSemana().getDate() == null) {
			JOptionPane.showMessageDialog(this,
					"Es necesario seleccionar dos fechas. Se puede seleccionar el mismo mes en ambos.");
		} else {
			ActividadDto actividad = panelActividad.getActividadDto(); // actividad seleccionada

			LocalDate ahora = LocalDate.now(); // fecha actual

			// fecha seleccionada
			LocalDate date = getCalendarSemana().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			// comprueba que no la fecha seleccionada no este en una semana anterior
			if (isLocalDateInTheSameWeek(ahora, date)
					|| (!isLocalDateInTheSameWeek(ahora, date) && ahora.compareTo(date) < 0)) {
				LocalDate domingoSelect = date.with(DayOfWeek.SUNDAY);
				Timestamp domT = Timestamp.valueOf(domingoSelect.atTime(23, 59));

				Timestamp lunesT;
				if (isLocalDateInTheSameWeek(domingoSelect, ahora)) { // si el dia actual esta en la semana seleccionada
					lunesT = Timestamp.valueOf(LocalDateTime.now()); // el lunes pasa a ser el dia de ahora
				} else {
					LocalDate lunesSelect = date.with(DayOfWeek.MONDAY);
					lunesT = Timestamp.valueOf(lunesSelect.atTime(07, 59));
				}

				// lista de actividades de ese tipo entre las fechas
				List<ActividadDto> acts = panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador()
						.getAdministradorCRUD().listarActividadesEntreFechas(actividad.getTipo(), lunesT, domT);

				boolean avisar = false;
				for (ActividadDto a : acts) {
					if (compobarFechas(a, actividad)) {
						avisar = true;
						panelActividad.avisarSocios(a);
						panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador().getAdministradorCRUD()
								.borrarActividad(a);
					}
				}

				if (avisar) {
					panelActividad.mostrarVentanaContactoMeses();
				}
			}
		}
	}

	// comprueba que la actividad se encuentre entre las dos fechas entre las que
	// hay que borrar las actividades
	private boolean compobarFechas(ActividadDto a, ActividadDto actividad) {
		if (actividad.getFechaInicio().getHours() != a.getFechaInicio().getHours()) { // si no son a la misma hora
			return false;
		} else {
			if ((actividad.getFechaFin().getHours() != a.getFechaFin().getHours())
					|| a.getFechaInicio().before(actividad.getFechaInicio())) {
				return false;
			}
		}
		return true;
	}

	// comprueba si dos localDate estan en la misma fecha
	public static boolean isLocalDateInTheSameWeek(LocalDate date1, LocalDate date2) {
		LocalDate sundayBeforeDate1 = date1.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate saturdayAfterDate1 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		return ((date2.isEqual(sundayBeforeDate1) || date2.isAfter(sundayBeforeDate1))
				&& (date2.isEqual(saturdayAfterDate1) || date2.isBefore(saturdayAfterDate1)));
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			btnAtras.setBackground(new Color(255, 0, 0));
			btnAtras.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnAtras.setBounds(318, 425, 113, 36);
		}
		return btnAtras;
	}

	private JCheckBox getChckUnicaActividad() {
		if (chckUnicaActividad == null) {
			chckUnicaActividad = new JCheckBox("Esta actividad solamente");
			chckUnicaActividad.setSelected(true);
			chckUnicaActividad.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout) (panelFechas.getLayout());
					cl.show(panelFechas, "EstaActividad");
				}
			});
			buttonGroup.add(chckUnicaActividad);
			chckUnicaActividad.setFont(new Font("Tahoma", Font.PLAIN, 15));
			chckUnicaActividad.setBounds(20, 43, 196, 21);
		}
		return chckUnicaActividad;
	}

	private JPanel getPnEstaActividad() {
		if (pnEstaActividad == null) {
			pnEstaActividad = new JPanel();
		}
		return pnEstaActividad;
	}

	private JLabel getLblVSSeleccionSemana() {
		if (lblVSSeleccionSemana == null) {
			lblVSSeleccionSemana = new JLabel("Selecciona la semana en la que desea cancelarlas actividades:");
			lblVSSeleccionSemana.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblVSSeleccionSemana.setBounds(10, 10, 397, 25);
		}
		return lblVSSeleccionSemana;
	}

	private JLabel getLblMFechaInicio() {
		if (lblMFechaInicio == null) {
			lblMFechaInicio = new JLabel("Mes de inicio:");
			lblMFechaInicio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblMFechaInicio.setBounds(29, 10, 127, 25);
		}
		return lblMFechaInicio;
	}

	private JLabel getLblMFechaFin() {
		if (lblMFechaFin == null) {
			lblMFechaFin = new JLabel("Mes de fin:");
			lblMFechaFin.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblMFechaFin.setBounds(263, 10, 127, 25);
		}
		return lblMFechaFin;
	}

	private JCalendar getCalendarSemana() {
		if (calendarSemana == null) {
			calendarSemana = new JCalendar();
			calendarSemana.setBounds(84, 45, 199, 138);
			LocalDate currentDate = LocalDate.now();
			calendarSemana.getDayChooser().setDay(currentDate.getDayOfMonth());
			calendarSemana.getMonthChooser().setMonth(currentDate.getMonth().getValue() - 1);
			calendarSemana.getYearChooser().setStartYear(currentDate.getYear());
		}
		return calendarSemana;
	}

	private JDateChooser getDateChooserVSInicio() {
		if (dateChooserVSInicio == null) {
			dateChooserVSInicio = new JDateChooser();
			dateChooserVSInicio.setBounds(27, 44, 154, 24);
		}
		return dateChooserVSInicio;
	}

	private JDateChooser getDateChooserVSFin() {
		if (dateChooserVSFin == null) {
			dateChooserVSFin = new JDateChooser();
			dateChooserVSFin.setBounds(228, 44, 154, 24);
		}
		return dateChooserVSFin;
	}

	private JDateChooser getDateChooserVMInicio() {
		if (dateChooserVMInicio == null) {
			dateChooserVMInicio = new JDateChooser();
			dateChooserVMInicio.setBounds(29, 45, 112, 19);
		}
		return dateChooserVMInicio;
	}

	private JDateChooser getDateChooserVMFin() {
		if (dateChooserVMFin == null) {
			dateChooserVMFin = new JDateChooser();
			dateChooserVMFin.setBounds(252, 45, 112, 19);
		}
		return dateChooserVMFin;
	}

	private JCheckBox getChckTodo() {
		if (chckTodo == null) {
			chckTodo = new JCheckBox("Todas las apariciones futuras");
			chckTodo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			chckTodo.setBounds(20, 139, 254, 21);
			chckTodo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout) (panelFechas.getLayout());
					cl.show(panelFechas, "EstaActividad");
				}
			});
			buttonGroup.add(chckTodo);
		}
		return chckTodo;
	}

	private void cancelarTodas() {
		ActividadDto ac = panelActividad.getActividadDto();

		List<ActividadDto> acts = panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador()
				.getAdministradorCRUD().listarActividadesTipo(ac.getTipo());

		for (ActividadDto a : acts) {
			if (comprobarFechas(a, ac)) {
				panelActividad.avisarSociosMeses(a);
				panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador().getAdministradorCRUD()
						.borrarActividad(a);
			}
		}

	}

	@SuppressWarnings("deprecation")
	private boolean comprobarFechas(ActividadDto act, ActividadDto referencia) {
		if (act.getFechaInicio().getHours() != referencia.getFechaInicio().getHours()) {
			return false;
		}

		LocalDate actDate = LocalDate.of(act.getFechaInicio().getYear(), act.getFechaInicio().getMonth() + 1,
				act.getFechaInicio().getDate());
		LocalDate referenciaDate = LocalDate.of(referencia.getFechaInicio().getYear(),
				referencia.getFechaInicio().getMonth() + 1, referencia.getFechaInicio().getDate());

		while (actDate.isAfter(referenciaDate) || actDate.compareTo(referenciaDate) == 0) {
			if (actDate.compareTo(referenciaDate) == 0) {
				return true;
			}
			actDate = actDate.minusDays(7);
		}
		return false;
	}
}
