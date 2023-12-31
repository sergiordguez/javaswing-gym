package igu.administrador;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import com.toedter.calendar.JCalendar;

import persistencia.administrador.ActividadDto;
import persistencia.administrador.Colisionable;
import persistencia.administrador.InstalacionDto;
import persistencia.administrador.TipoActividadDto;

public class VentanaCreacionActividadesVariosDias extends JDialog {
	private static final long serialVersionUID = 1L;
	private VentanaAdministrador va;
	private JPanel pn1;
	private JLabel lbCBTipo;
	private JComboBox<TipoActividadDto> cBTipo;
	private JLabel lbCBInstalacion;
	private JComboBox<InstalacionDto> cBInstalacion;
	private JLabel lbTFPlazas;
	private JLabel lbPlazas;
	private JCheckBox chckbxNecesitaRecursos;
	private JCheckBox chckbxPlazasIlimitadas;
	private JTextArea tAAvisoPlazas;
	private JButton btnSiguiente1;
	private JSpinner spPlazas;
	private JPanel pn2;
	private JLabel lbCBFechaInicio;
	private JComboBox<String> cBFechaInicio;
	private JCheckBox chckbxTodoElDia;
	private JLabel lbCBFechaFin;
	private JComboBox<String> cBFechaFin;
	private JButton btnSiguiente2;
	private JLabel lbCBDiaSemana;
	private JComboBox<DiasDeLaSemana> cBDiaSemana;
	private JButton btnAñadir2;
	private JScrollPane sPFechas;
	private JTextArea tAFechas;
	private JTextArea tAAvisoHora;
	private JButton btnAtras2;
	private JTextArea tAAvisoSiguiente;
	private JPanel pn3;
	private JCalendar calendar;
	private JButton btnCrear;
	private JTextArea tACalendario;
	private JButton btnAtras3;
	private JButton btnBorrar2;
	private JButton btnAñadir3;
	private JCheckBox chckbxVariasSemanas;
	private JComboBox<String> cBSemana;
	private JLabel lbSemanaInicio;
	private JLabel lbSemanaFin;
	private JPanel pn4;
	private JLabel lbCBHoraInicio;
	private JComboBox<String> cbHoraInicio;
	private JCheckBox chckbxTodoElDiaMes;
	private JLabel lbCBHoraFin;
	private JComboBox<String> cbHoraFinal;
	private JButton btnSiguiente2Mes;
	private JLabel lbDiaMes;
	private JComboBox<Integer> cbDiaMes;
	private JButton btnAñadirMes;
	private JScrollPane sPFechasMes;
	private JTextArea tAAvisoHoraMes;
	private JButton btnAtras2Mes;
	private JTextArea tAAvisoSiguienteMes;
	private JButton btnBorrarMes;
	private JPanel pn5;
	private JButton btnCrearMes;
	private JTextArea tACalendarioMes;
	private JButton btnAtras3Mes;
	private JLabel lbInicio;
	private JLabel lbMesInicial;
	private JSpinner spMesInicial;
	private JLabel lbAñoInicial;
	private JSpinner spAñoInicial;
	private JLabel lbFin;
	private JLabel lbMesFin;
	private JSpinner spMesFin;
	private JLabel lbAñoFin;
	private JSpinner spAñoFin;
	private JTextArea tAFechasMes;
	private JComboBox<String> cbSemanaMes;

	private enum DiasDeLaSemana {
		Lunes, Martes, Miércoles, Jueves, Viernes, Sábado, Domingo;
	}

	public VentanaCreacionActividadesVariosDias(VentanaAdministrador ventanaAdministrador) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (confirmarAtras()) {
					dispose();
				}
			}
		});
		this.va = ventanaAdministrador;
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		getContentPane().add(getPn1(), "pn1");
		getContentPane().add(getPn2(), "pn2");
		getContentPane().add(getPn3(), "pn3");
		getContentPane().add(getPn4(), "pn4");
		getContentPane().add(getPn5(), "pn5");
		mostrarPn1();
	}

	private boolean confirmarAtras() {
		boolean confirmacion = false;
		int respuesta = JOptionPane.showConfirmDialog(this,
				"¿Está seguro de que quiere volver atrás? (perderá los datos introducidos)", "Seleccione una opción:",
				JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			confirmacion = true;
		}
		return confirmacion;
	}

	private void mostrarPn1() {
		setSize(550, 280);
//		setSize(750, 400);
		((CardLayout) getContentPane().getLayout()).show(getContentPane(), "pn1");
	}

	private void mostrarPn2() {
		setSize(750, 400);
		((CardLayout) getContentPane().getLayout()).show(getContentPane(), "pn2");
	}

	private void mostrarPn3() {
		setSize(750, 400);
		((CardLayout) getContentPane().getLayout()).show(getContentPane(), "pn3");
	}

	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			pn1.setLayout(null);
			pn1.add(getLbCBTipo());
			pn1.add(getCBTipo());
			pn1.add(getLbCBInstalacion());
			pn1.add(getCBInstalacion());
			pn1.add(getLbTFPlazas());
			pn1.add(getLbPlazas());
			pn1.add(getChckbxNecesitaRecursos());
			pn1.add(getChckbxPlazasIlimitadas());
			pn1.add(getTAAvisoPlazas());
			pn1.add(getBtnSiguiente1());
			pn1.add(getSpPlazas());
			pn1.add(getCbSemanaMes());
		}
		return pn1;
	}

	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			pn2.setLayout(null);
			pn2.add(getLbCBFechaInicio());
			pn2.add(getCBFechaInicio());
			pn2.add(getChckbxTodoElDia());
			pn2.add(getLbCBFechaFin());
			pn2.add(getCBFechaFin());
			pn2.add(getBtnSiguiente2());
			pn2.add(getLbCBDiaSemana());
			pn2.add(getCBDiaSemana());
			pn2.add(getBtnAñadir2());
			pn2.add(getSPFechas());
			pn2.add(getTAAvisoHora());
			pn2.add(getBtnAtras2());
			pn2.add(getTAAvisoSiguiente());
			pn2.add(getBtnBorrar2());
		}
		return pn2;
	}

	private JPanel getPn3() {
		if (pn3 == null) {
			pn3 = new JPanel();
			pn3.setVisible(false);
			pn3.setLayout(null);
			pn3.add(getCalendar());
			pn3.add(getBtnCrear());
			pn3.add(getTACalendario());
			pn3.add(getBtnAtras3());
			pn3.add(getBtnAñadir3());
			pn3.add(getChckbxVariasSemanas());
			pn3.add(getCBSemana());
			pn3.add(getLbSemanaInicio());
			pn3.add(getLbSemanaFin());
		}
		return pn3;
	}

	private JLabel getLbCBTipo() {
		if (lbCBTipo == null) {
			lbCBTipo = new JLabel("Tipo:");
			lbCBTipo.setLabelFor(getCBTipo());
			lbCBTipo.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBTipo.setDisplayedMnemonic('T');
			lbCBTipo.setBounds(10, 11, 230, 22);
		}
		return lbCBTipo;
	}

	private JComboBox<TipoActividadDto> getCBTipo() {
		if (cBTipo == null) {
			cBTipo = new JComboBox<>();
			cBTipo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarLbPlazas();
				}
			});
			cBTipo.setBounds(10, 50, 230, 22);
			cBTipo.setModel(new DefaultComboBoxModel<>(va.getAdministradorCRUD().listarTiposActividades()));
		}
		return cBTipo;
	}

	private void actualizarLbPlazas() {
		if (getChckbxNecesitaRecursos().isSelected()) {
			deshabilitarSpinner();
			getChckbxPlazasIlimitadas().setEnabled(false);
			getLbPlazas().setText(Integer.toString(va.getAdministradorCRUD().obtenerCantidadRecursos(
					getCBTipo().getSelectedItem().toString(), getCBInstalacion().getSelectedItem().toString())));
		} else {
			getChckbxPlazasIlimitadas().setEnabled(true);
			if (getChckbxPlazasIlimitadas().isSelected()) {
				deshabilitarSpinner();
				getLbPlazas().setText("Plazas ilimitadas");
			} else {
				habilitarSpinner();
			}
		}
	}

	private void deshabilitarSpinner() {
		getSpPlazas().setVisible(false);
		getLbPlazas().setVisible(true);
	}

	private void habilitarSpinner() {
		getLbPlazas().setVisible(false);
		getSpPlazas().setVisible(true);
	}

	private JLabel getLbCBInstalacion() {
		if (lbCBInstalacion == null) {
			lbCBInstalacion = new JLabel("Instalación:");
			lbCBInstalacion.setLabelFor(getCBInstalacion());
			lbCBInstalacion.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBInstalacion.setDisplayedMnemonic('I');
			lbCBInstalacion.setBounds(10, 89, 230, 22);
		}
		return lbCBInstalacion;
	}

	private JComboBox<InstalacionDto> getCBInstalacion() {
		if (cBInstalacion == null) {
			cBInstalacion = new JComboBox<>();
			cBInstalacion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarLbPlazas();
				}
			});
			cBInstalacion.setBounds(10, 128, 230, 22);
			cBInstalacion.setModel(new DefaultComboBoxModel<>(va.getAdministradorCRUD().listarInstalaciones()));
		}
		return cBInstalacion;
	}

	private JLabel getLbTFPlazas() {
		if (lbTFPlazas == null) {
			lbTFPlazas = new JLabel("Plazas:");
			lbTFPlazas.setLabelFor(getSpPlazas());
			lbTFPlazas.setFont(new Font("Arial", Font.PLAIN, 12));
			lbTFPlazas.setDisplayedMnemonic('P');
			lbTFPlazas.setBounds(10, 167, 230, 22);
		}
		return lbTFPlazas;
	}

	private JLabel getLbPlazas() {
		if (lbPlazas == null) {
			lbPlazas = new JLabel("0");
			lbPlazas.setBounds(10, 206, 230, 22);
			actualizarLbPlazas();
		}
		return lbPlazas;
	}

	private JCheckBox getChckbxNecesitaRecursos() {
		if (chckbxNecesitaRecursos == null) {
			chckbxNecesitaRecursos = new JCheckBox("Necesita recursos");
			chckbxNecesitaRecursos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarLbPlazas();
				}
			});
			chckbxNecesitaRecursos.setSelected(true);
			chckbxNecesitaRecursos.setMnemonic('N');
			chckbxNecesitaRecursos.setBounds(246, 167, 115, 23);
		}
		return chckbxNecesitaRecursos;
	}

	private JCheckBox getChckbxPlazasIlimitadas() {
		if (chckbxPlazasIlimitadas == null) {
			chckbxPlazasIlimitadas = new JCheckBox("Plazas ilimitadas");
			chckbxPlazasIlimitadas.setMnemonic('a');
			chckbxPlazasIlimitadas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarLbPlazas();
				}
			});
			chckbxPlazasIlimitadas.setEnabled(false);
			chckbxPlazasIlimitadas.setBounds(246, 204, 115, 23);
		}
		return chckbxPlazasIlimitadas;
	}

	private JTextArea getTAAvisoPlazas() {
		if (tAAvisoPlazas == null) {
			tAAvisoPlazas = new JTextArea();
			tAAvisoPlazas.setWrapStyleWord(true);
			tAAvisoPlazas.setLineWrap(true);
			tAAvisoPlazas.setForeground(Color.RED);
			tAAvisoPlazas.setFont(new Font("Arial", Font.ITALIC, 12));
			tAAvisoPlazas.setEnabled(false);
			tAAvisoPlazas.setEditable(false);
			tAAvisoPlazas.setDisabledTextColor(Color.RED);
			tAAvisoPlazas.setBorder(null);
			tAAvisoPlazas.setBounds(370, 54, 155, 69);
		}
		return tAAvisoPlazas;
	}

	private JButton getBtnSiguiente1() {
		if (btnSiguiente1 == null) {
			btnSiguiente1 = new JButton("Siguiente");
			btnSiguiente1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarPlazas()) {
						if (((String) getCbSemanaMes().getSelectedItem()).equals("Semanas"))
							mostrarPn2();
						else
							mostrarPn4();
					}
				}
			});
			btnSiguiente1.setMnemonic('S');
			btnSiguiente1.setFont(new Font("Arial", Font.BOLD, 13));
			btnSiguiente1.setBackground(new Color(173, 255, 47));
			btnSiguiente1.setBounds(370, 167, 155, 60);
		}
		return btnSiguiente1;
	}

	private boolean validarPlazas() {
		if (getChckbxNecesitaRecursos().isSelected()
				&& va.getAdministradorCRUD().obtenerCantidadRecursos(getCBTipo().getSelectedItem().toString(),
						getCBInstalacion().getSelectedItem().toString()) == 0) {
			mostrarAvisoPlazas();
			return false;
		}

		getTAAvisoPlazas().setText("");
		return true;
	}

	private void mostrarAvisoPlazas() {
		getTAAvisoPlazas()
				.setText("No existen recursos suficientes en la instalación para el desarrollo de la actividad.");

	}

	private JSpinner getSpPlazas() {
		if (spPlazas == null) {
			spPlazas = new JSpinner();
			spPlazas.setVisible(false);
			spPlazas.setModel(new SpinnerNumberModel(1, 1, 99, 1));
			spPlazas.setBounds(10, 206, 75, 22);
		}
		return spPlazas;
	}

	private JLabel getLbCBFechaInicio() {
		if (lbCBFechaInicio == null) {
			lbCBFechaInicio = new JLabel("Hora inicio:");
			lbCBFechaInicio.setLabelFor(getCBFechaInicio());
			lbCBFechaInicio.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBFechaInicio.setDisplayedMnemonic('H');
			lbCBFechaInicio.setBounds(10, 89, 230, 22);
		}
		return lbCBFechaInicio;
	}

	private JComboBox<String> getCBFechaInicio() {
		if (cBFechaInicio == null) {
			cBFechaInicio = new JComboBox<>();
			cBFechaInicio.setBounds(10, 128, 230, 22);
			cBFechaInicio.setModel(new DefaultComboBoxModel<>(generarModeloHoras()));
			cBFechaInicio.setSelectedIndex(0);
		}
		return cBFechaInicio;
	}

	private String[] generarModeloHoras() {
		List<String> horas = new ArrayList<>();
		for (int i = 8; i < 24; i++) {
			horas.add(i + ":00");
		}
		return horas.toArray(new String[0]);
	}

	private JCheckBox getChckbxTodoElDia() {
		if (chckbxTodoElDia == null) {
			chckbxTodoElDia = new JCheckBox("Todo el día");
			chckbxTodoElDia.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarCombosFecha();
				}
			});
			chckbxTodoElDia.setMnemonic('O');
			chckbxTodoElDia.setBounds(246, 128, 115, 23);
		}
		return chckbxTodoElDia;
	}

	private void actualizarCombosFecha() {
		if (chckbxTodoElDia.isSelected()) {
			getCBFechaInicio().setSelectedIndex(0);
			getCBFechaInicio().setEnabled(false);
			getCBFechaFin().setSelectedIndex(1);
			getCBFechaFin().setEnabled(false);
		} else {
			getCBFechaInicio().setEnabled(true);
			getCBFechaFin().setEnabled(true);
		}
	}

	private JLabel getLbCBFechaFin() {
		if (lbCBFechaFin == null) {
			lbCBFechaFin = new JLabel("Hora final:");
			lbCBFechaFin.setLabelFor(getCBFechaFin());
			lbCBFechaFin.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBFechaFin.setDisplayedMnemonic('F');
			lbCBFechaFin.setBounds(10, 167, 230, 22);
		}
		return lbCBFechaFin;
	}

	private JComboBox<String> getCBFechaFin() {
		if (cBFechaFin == null) {
			cBFechaFin = new JComboBox<>();
			cBFechaFin.setBounds(10, 206, 230, 22);
			cBFechaFin.setModel(new DefaultComboBoxModel<>(generarModeloHoras()));
			cBFechaFin.setSelectedIndex(1);
		}
		return cBFechaFin;
	}

	private JButton getBtnSiguiente2() {
		if (btnSiguiente2 == null) {
			btnSiguiente2 = new JButton("Siguiente");
			btnSiguiente2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarSiguiente()) {
						mostrarPn3();
					}
				}
			});
			btnSiguiente2.setMnemonic('S');
			btnSiguiente2.setFont(new Font("Arial", Font.BOLD, 13));
			btnSiguiente2.setBackground(new Color(173, 255, 47));
			btnSiguiente2.setBounds(400, 290, 155, 60);
		}
		return btnSiguiente2;
	}

	private boolean validarSiguiente() {
		if (getTAFechas().getText().isBlank()) {
			mostrarAvisoSiguiente();
			return false;
		}

		getTAAvisoSiguiente().setText("");
		return true;
	}

	private void mostrarAvisoSiguiente() {
		getTAAvisoSiguiente().setText("Por favor, introduzca al menos una fecha.");
	}

	private JLabel getLbCBDiaSemana() {
		if (lbCBDiaSemana == null) {
			lbCBDiaSemana = new JLabel("Día de la semana:");
			lbCBDiaSemana.setLabelFor(getCBDiaSemana());
			lbCBDiaSemana.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBDiaSemana.setDisplayedMnemonic('D');
			lbCBDiaSemana.setBounds(10, 11, 230, 22);
		}
		return lbCBDiaSemana;
	}

	private JComboBox<DiasDeLaSemana> getCBDiaSemana() {
		if (cBDiaSemana == null) {
			cBDiaSemana = new JComboBox<>();
			cBDiaSemana.setModel(new DefaultComboBoxModel<>(DiasDeLaSemana.values()));
			cBDiaSemana.setBounds(10, 50, 230, 22);
		}
		return cBDiaSemana;
	}

	private JButton getBtnAñadir2() {
		if (btnAñadir2 == null) {
			btnAñadir2 = new JButton("Añadir");
			btnAñadir2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarFecha()) {
						añadirFecha();
					}
				}
			});
			btnAñadir2.setMnemonic('A');
			btnAñadir2.setFont(new Font("Arial", Font.BOLD, 13));
			btnAñadir2.setBackground(new Color(255, 215, 0));
			btnAñadir2.setBounds(10, 290, 155, 60);
		}
		return btnAñadir2;
	}

	private void añadirFecha() {
		String strFecha;
		if (getChckbxTodoElDia().isSelected()) {
			strFecha = getCBDiaSemana().getSelectedItem().toString() + " 8:00 23:00\n";
		} else {
			strFecha = getCBDiaSemana().getSelectedItem().toString() + " "
					+ getCBFechaInicio().getSelectedItem().toString() + " "
					+ getCBFechaFin().getSelectedItem().toString() + "\n";
		}

		if (!getTAFechas().getText().contains(strFecha)) {
			if (!colisiona(strFecha)) {
				getTAFechas().append(strFecha);
			}
		}
	}

	private boolean colisiona(String strNuevaFecha) {
		String[] arrayFechas = getTAFechas().getText().split("\\r?\\n");
		String[] arrayNuevaFecha = strNuevaFecha.split(" ");
		String diaNuevaFecha = arrayNuevaFecha[0];
		int horaInicioNuevaFecha = calcularHoraParaString(arrayNuevaFecha[1].trim());
		int horaFinNuevaFecha = calcularHoraParaString(arrayNuevaFecha[2].trim());
		boolean colisiona = false;

		if (!getTAFechas().getText().isBlank()) {
			for (String strFecha : arrayFechas) {
				String arrayFecha[] = strFecha.split(" ");
				String dia = arrayFecha[0];
				int horaInicio = calcularHoraParaString(arrayFecha[1].trim());
				int horaFin = calcularHoraParaString(arrayFecha[2].trim());

				if (diaNuevaFecha.equals(dia)) {
					colisiona = colisiona
							|| colisionaHora(horaInicioNuevaFecha, horaFinNuevaFecha, horaInicio, horaFin);
				}
			}
		}
		return colisiona;
	}

	private boolean colisionaHora(int horaInicioNuevaFecha, int horaFinNuevaFecha, int horaInicio, int horaFin) {
		return (horaInicioNuevaFecha >= horaInicio && horaFinNuevaFecha <= horaFin)
				|| (horaInicioNuevaFecha < horaInicio && horaFinNuevaFecha > horaFin)
				|| (horaInicioNuevaFecha >= horaInicio && horaFinNuevaFecha > horaFin && horaInicioNuevaFecha < horaFin)
				|| (horaInicioNuevaFecha < horaInicio && horaFinNuevaFecha <= horaFin
						&& horaFinNuevaFecha > horaInicio);
	}

	private boolean validarFecha() {
		if (!getChckbxTodoElDia().isSelected() && finMenorOIgualQueInicio()) {
			mostrarAvisoHora();
			return false;
		}

		getTAAvisoHora().setText("");
		return true;
	}

	private boolean finMenorOIgualQueInicio() {
		return calcularHoraParaString(getCBFechaFin().getSelectedItem().toString()) <= calcularHoraParaString(
				getCBFechaInicio().getSelectedItem().toString());
	}

	private int calcularHoraParaString(String sHora) {
		int hora;
		if (sHora.length() > 4) {
			hora = Integer.parseInt(sHora.substring(0, 2));
		} else {
			hora = Integer.parseInt(sHora.substring(0, 1));
		}
		return hora;
	}

	private void mostrarAvisoHora() {
		getTAAvisoHora().setText("La hora de inicio debe ser anterior a la hora de fin.");
	}

	private JScrollPane getSPFechas() {
		if (sPFechas == null) {
			sPFechas = new JScrollPane();
			sPFechas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			sPFechas.setBounds(565, 51, 155, 229);
			sPFechas.setViewportView(getTAFechas());
		}
		return sPFechas;
	}

	private JTextArea getTAFechas() {
		if (tAFechas == null) {
			tAFechas = new JTextArea();
			tAFechas.setEditable(false);
			tAFechas.setLineWrap(true);
		}
		return tAFechas;
	}

	private JTextArea getTAAvisoHora() {
		if (tAAvisoHora == null) {
			tAAvisoHora = new JTextArea();
			tAAvisoHora.setWrapStyleWord(true);
			tAAvisoHora.setLineWrap(true);
			tAAvisoHora.setForeground(Color.RED);
			tAAvisoHora.setFont(new Font("Arial", Font.ITALIC, 12));
			tAAvisoHora.setEnabled(false);
			tAAvisoHora.setEditable(false);
			tAAvisoHora.setDisabledTextColor(Color.RED);
			tAAvisoHora.setBorder(null);
			tAAvisoHora.setBounds(10, 238, 155, 42);
		}
		return tAAvisoHora;
	}

	private JButton getBtnAtras2() {
		if (btnAtras2 == null) {
			btnAtras2 = new JButton("Atrás");
			btnAtras2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarPn1();
				}
			});
			btnAtras2.setMnemonic('T');
			btnAtras2.setFont(new Font("Arial", Font.BOLD, 13));
			btnAtras2.setBackground(new Color(255, 69, 0));
			btnAtras2.setBounds(565, 290, 155, 60);
		}
		return btnAtras2;
	}

	private JTextArea getTAAvisoSiguiente() {
		if (tAAvisoSiguiente == null) {
			tAAvisoSiguiente = new JTextArea();
			tAAvisoSiguiente.setWrapStyleWord(true);
			tAAvisoSiguiente.setLineWrap(true);
			tAAvisoSiguiente.setForeground(Color.RED);
			tAAvisoSiguiente.setFont(new Font("Arial", Font.ITALIC, 12));
			tAAvisoSiguiente.setEnabled(false);
			tAAvisoSiguiente.setEditable(false);
			tAAvisoSiguiente.setDisabledTextColor(Color.RED);
			tAAvisoSiguiente.setBorder(null);
			tAAvisoSiguiente.setBounds(400, 219, 155, 60);
		}
		return tAAvisoSiguiente;
	}

	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.setBounds(10, 11, 320, 269);
			LocalDate currentDate = LocalDate.now();
			calendar.getDayChooser().setDay(currentDate.getDayOfMonth());
			calendar.getMonthChooser().setMonth(currentDate.getMonth().getValue() - 1);
			calendar.getYearChooser().setStartYear(currentDate.getYear());
		}
		return calendar;
	}

	private JButton getBtnCrear() {
		if (btnCrear == null) {
			btnCrear = new JButton("Crear");
			btnCrear.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarCalendario()) {
						cerrarYCrearActividad();
					}
				}
			});
			btnCrear.setMnemonic('C');
			btnCrear.setFont(new Font("Arial", Font.BOLD, 13));
			btnCrear.setBackground(new Color(173, 255, 47));
			btnCrear.setBounds(400, 290, 155, 60);
		}
		return btnCrear;
	}

	private void cerrarYCrearActividad() {
		List<ActividadDto> actividades = generarActividades();
		List<Colisionable> colisiones = new ArrayList<>();

		for (ActividadDto actividad : actividades) {
			colisiones.addAll(va.getAdministradorCRUD().obtenerColisiones(actividad.getInstalacion(),
					actividad.getFechaInicio(), actividad.getFechaFin()));
		}

		if (colisiones.isEmpty()) {
			for (ActividadDto actividad : actividades) {
				va.getAdministradorCRUD().añadirActividad(actividad.getFechaInicio(), actividad.getFechaFin(),
						actividad.getTipo(), actividad.getInstalacion(), actividad.getPlazasTotales());
			}

			dispose();
		} else {
			mostrarVentanaColisiones(colisiones);
		}
	}

	private List<ActividadDto> generarActividades() {
		List<ActividadDto> actividades = new ArrayList<>();
		List<Timestamp> fechasSemanas = new ArrayList<>();
		if (!getChckbxVariasSemanas().isSelected()) {
			fechasSemanas.add(new Timestamp(calendar.getDate().getTime()));
		} else {
			fechasSemanas = generarFechasParaSemanas();
		}

		String[] arrayFechas = getTAFechas().getText().split("\\r?\\n");
		for (Timestamp fechaSemana : fechasSemanas) {
			for (String strFecha : arrayFechas) {
				String[] arrayFecha = strFecha.split(" ");
				actividades.add(generarActividad(arrayFecha, fechaSemana));
			}
		}

		return actividades;
	}

	@SuppressWarnings("deprecation")
	private ActividadDto generarActividad(String[] arrayFecha, Timestamp fechaSemana) {
		Timestamp fecha = calcularFecha(fechaSemana, DiasDeLaSemana.valueOf(arrayFecha[0]));

		Timestamp fechaInicio = new Timestamp(fecha.getTime());
		fechaInicio.setHours(calcularHoraParaString(arrayFecha[1]));

		Timestamp fechaFin = new Timestamp(fecha.getTime());
		fechaFin.setHours(calcularHoraParaString(arrayFecha[2]));

		return new ActividadDto(null, getCBTipo().getSelectedItem().toString(),
				getCBInstalacion().getSelectedItem().toString(), null, calcularPlazas(), 0, fechaInicio, fechaFin);
	}

	private List<Timestamp> generarFechasParaSemanas() {
		List<Timestamp> fechas = new ArrayList<>();
		Timestamp fechaSemanaInicio = obtenerSemanaInicio();
		Timestamp fechaSemanaFin = obtenerSemanaFin();

		Timestamp fecha = fechaSemanaInicio;
		while (fecha.before(fechaSemanaFin)) {
			fechas.add(fecha);
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(fecha);
			calendario.add(Calendar.DAY_OF_YEAR, 7);
			fecha = new Timestamp(calendario.getTimeInMillis());
		}

		return fechas;
	}

	private Timestamp obtenerSemanaInicio() {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		String[] arraySemanaInicio = getLbSemanaInicio().getText().split(" ");
		Timestamp fechaSemanaInicio = null;

		if (!arraySemanaInicio[arraySemanaInicio.length - 1].contains("inicio")) {
			try {
				fechaSemanaInicio = calcularLunes(
						new Timestamp(formateador.parse(arraySemanaInicio[arraySemanaInicio.length - 1]).getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return fechaSemanaInicio;
	}

	private Timestamp obtenerSemanaFin() {
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		String[] arraySemanaFin = getLbSemanaFin().getText().split(" ");
		Timestamp fechaSemanaFin = null;

		if (!arraySemanaFin[arraySemanaFin.length - 1].contains("fin")) {
			try {
				fechaSemanaFin = calcularDomingo(
						new Timestamp(formateador.parse(arraySemanaFin[arraySemanaFin.length - 1]).getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return fechaSemanaFin;
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

	private int calcularPlazas() {
		int plazas = 0;
		if (getChckbxNecesitaRecursos().isSelected()) {
			plazas = Integer.parseInt(getLbPlazas().getText());
		} else if (!getChckbxPlazasIlimitadas().isSelected()) {
			plazas = (int) getSpPlazas().getValue();
		}

		return plazas;
	}

	private void mostrarVentanaColisiones(List<Colisionable> colisiones) {
		VentanaColisiones dialog = new VentanaColisiones(this, colisiones);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private boolean validarCalendario() {
		if (!getChckbxVariasSemanas().isSelected()) {
			if (new Timestamp(calendar.getDate().getTime())
					.before(calcularLunes(new Timestamp(System.currentTimeMillis())))) {
				getTACalendario().setText("La semana seleccionada no puede ser anterior a la actual.");
				return false;
			}
		} else {
			if (obtenerSemanaInicio() == null || obtenerSemanaFin() == null) {
				getTACalendario().setText("Por favor, seleccione semanas de inicio y fin.");
				return false;
			} else if (obtenerSemanaInicio().after(obtenerSemanaFin())) {
				getTACalendario().setText("La semana de fin no puede ser anterior a la de inicio.");
				return false;
			} else if (obtenerSemanaInicio().before(calcularLunes(new Timestamp(System.currentTimeMillis())))) {
				getTACalendario().setText("La semana de inicio no puede ser anterior a la actual.");
				return false;
			}
		}

		return true;
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

	private Timestamp calcularFecha(Timestamp fechaSemana, DiasDeLaSemana diaDeLaSemana) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(calcularLunes(fechaSemana));
		cal.add(Calendar.DAY_OF_WEEK, diaDeLaSemana.ordinal());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		Timestamp fecha = new Timestamp(cal.getTime().getTime());
		fecha.setNanos(0);

		return fecha;
	}

	private int convertirDia(int dia) {
		if (dia != 0) {
			return dia - 1;
		} else {
			return 6;
		}
	}

	private JTextArea getTACalendario() {
		if (tACalendario == null) {
			tACalendario = new JTextArea();
			tACalendario.setWrapStyleWord(true);
			tACalendario.setLineWrap(true);
			tACalendario.setForeground(Color.RED);
			tACalendario.setFont(new Font("Arial", Font.ITALIC, 12));
			tACalendario.setEnabled(false);
			tACalendario.setEditable(false);
			tACalendario.setDisabledTextColor(Color.RED);
			tACalendario.setBorder(null);
			tACalendario.setBounds(400, 11, 155, 60);
		}
		return tACalendario;
	}

	private JButton getBtnAtras3() {
		if (btnAtras3 == null) {
			btnAtras3 = new JButton("Atrás");
			btnAtras3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarPn2();
				}
			});
			btnAtras3.setMnemonic('T');
			btnAtras3.setFont(new Font("Arial", Font.BOLD, 13));
			btnAtras3.setBackground(new Color(255, 69, 0));
			btnAtras3.setBounds(565, 290, 155, 60);
		}
		return btnAtras3;
	}

	private JButton getBtnBorrar2() {
		if (btnBorrar2 == null) {
			btnBorrar2 = new JButton("Borrar");
			btnBorrar2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					borrarFecha();
				}
			});
			btnBorrar2.setMnemonic('B');
			btnBorrar2.setFont(new Font("Arial", Font.BOLD, 13));
			btnBorrar2.setBackground(new Color(255, 69, 0));
			btnBorrar2.setBounds(175, 290, 155, 60);
		}
		return btnBorrar2;
	}

	private void borrarFecha() {
		String[] arrayFechas = getTAFechas().getText().split("\\r?\\n");
		getTAFechas().setText("");
		for (int i = 0; i < arrayFechas.length - 1; i++) {
			getTAFechas().append(arrayFechas[i] + "\n");
		}
	}

	private JButton getBtnAñadir3() {
		if (btnAñadir3 == null) {
			btnAñadir3 = new JButton("Añadir");
			btnAñadir3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					añadirSemana();
				}
			});
			btnAñadir3.setVisible(false);
			btnAñadir3.setMnemonic('A');
			btnAñadir3.setFont(new Font("Arial", Font.BOLD, 13));
			btnAñadir3.setBackground(new Color(255, 215, 0));
			btnAñadir3.setBounds(10, 290, 155, 60);
		}
		return btnAñadir3;
	}

	private void añadirSemana() {
		String strSemana = "Semana del " + new SimpleDateFormat("dd/MM/yyyy").format(calendar.getDate());
		if (getCBSemana().getSelectedIndex() == 0) {
			getLbSemanaInicio().setText("Semana de inicio: " + strSemana);
		} else {
			getLbSemanaFin().setText("Semana de fin: " + strSemana);
		}
	}

	private JCheckBox getChckbxVariasSemanas() {
		if (chckbxVariasSemanas == null) {
			chckbxVariasSemanas = new JCheckBox("Varias semanas");
			chckbxVariasSemanas.setMnemonic('V');
			chckbxVariasSemanas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					activarVariasSemanas();
				}
			});
			chckbxVariasSemanas.setBounds(400, 257, 155, 23);
		}
		return chckbxVariasSemanas;
	}

	private void activarVariasSemanas() {
		boolean activar = false;
		if (getChckbxVariasSemanas().isSelected()) {
			activar = true;
		}

		getBtnAñadir3().setVisible(activar);
		getCBSemana().setVisible(activar);
		getLbSemanaInicio().setVisible(activar);
		getLbSemanaFin().setVisible(activar);
	}

	private JComboBox<String> getCBSemana() {
		if (cBSemana == null) {
			cBSemana = new JComboBox<>();
			cBSemana.setModel(new DefaultComboBoxModel<>(new String[] { "Semana de inicio", "Semana de fin" }));
			cBSemana.setVisible(false);
			cBSemana.setBounds(175, 291, 155, 59);
		}
		return cBSemana;
	}

	private JLabel getLbSemanaInicio() {
		if (lbSemanaInicio == null) {
			lbSemanaInicio = new JLabel("Semana de inicio:");
			lbSemanaInicio.setVisible(false);
			lbSemanaInicio.setBounds(400, 215, 320, 14);
		}
		return lbSemanaInicio;
	}

	private JLabel getLbSemanaFin() {
		if (lbSemanaFin == null) {
			lbSemanaFin = new JLabel("Semana de fin:");
			lbSemanaFin.setVisible(false);
			lbSemanaFin.setBounds(400, 236, 320, 14);
		}
		return lbSemanaFin;
	}

	private JPanel getPn4() {
		if (pn4 == null) {
			pn4 = new JPanel();
			pn4.setLayout(null);
			pn4.add(getLbCBHoraInicio());
			pn4.add(getCbHoraInicio());
			pn4.add(getChckbxTodoElDiaMes());
			pn4.add(getLbCBHoraFin());
			pn4.add(getCbHoraFinal());
			pn4.add(getBtnSiguiente2Mes());
			pn4.add(getLbDiaMes());
			pn4.add(getCbDiaMes());
			pn4.add(getBtnAñadirMes());
			pn4.add(getSPFechasMes());
			pn4.add(getTAAvisoHoraMes());
			pn4.add(getBtnAtras2Mes());
			pn4.add(getTAAvisoSiguienteMes());
			pn4.add(getBtnBorrarMes());
		}
		return pn4;
	}

	private JLabel getLbCBHoraInicio() {
		if (lbCBHoraInicio == null) {
			lbCBHoraInicio = new JLabel("Hora inicio:");
			lbCBHoraInicio.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBHoraInicio.setDisplayedMnemonic('H');
			lbCBHoraInicio.setBounds(10, 89, 230, 22);
		}
		return lbCBHoraInicio;
	}

	private JComboBox<String> getCbHoraInicio() {
		if (cbHoraInicio == null) {
			cbHoraInicio = new JComboBox<>();
			cbHoraInicio.setModel(new DefaultComboBoxModel<>(generarModeloHoras()));
			cbHoraInicio.setSelectedIndex(0);
			cbHoraInicio.setBounds(10, 128, 230, 22);
		}
		return cbHoraInicio;
	}

	private JCheckBox getChckbxTodoElDiaMes() {
		if (chckbxTodoElDiaMes == null) {
			chckbxTodoElDiaMes = new JCheckBox("Todo el día");
			chckbxTodoElDiaMes.setMnemonic('T');
			chckbxTodoElDiaMes.setBounds(246, 128, 115, 23);
		}
		return chckbxTodoElDiaMes;
	}

	private JLabel getLbCBHoraFin() {
		if (lbCBHoraFin == null) {
			lbCBHoraFin = new JLabel("Hora final:");
			lbCBHoraFin.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBHoraFin.setDisplayedMnemonic('F');
			lbCBHoraFin.setBounds(10, 167, 230, 22);
		}
		return lbCBHoraFin;
	}

	private JComboBox<String> getCbHoraFinal() {
		if (cbHoraFinal == null) {
			cbHoraFinal = new JComboBox<>();
			cbHoraFinal.setModel(new DefaultComboBoxModel<>(generarModeloHoras()));
			cbHoraFinal.setSelectedIndex(1);
			cbHoraFinal.setBounds(10, 206, 230, 22);
		}
		return cbHoraFinal;
	}

	private JButton getBtnSiguiente2Mes() {
		if (btnSiguiente2Mes == null) {
			btnSiguiente2Mes = new JButton("Siguiente");
			btnSiguiente2Mes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarSiguiente2()) {
						mostrarPn5();
					}
				}
			});
			btnSiguiente2Mes.setMnemonic('S');
			btnSiguiente2Mes.setFont(new Font("Arial", Font.BOLD, 13));
			btnSiguiente2Mes.setBackground(new Color(173, 255, 47));
			btnSiguiente2Mes.setBounds(400, 290, 155, 60);
		}
		return btnSiguiente2Mes;
	}

	private void mostrarPn5() {
		setSize(750, 400);
		((CardLayout) getContentPane().getLayout()).show(getContentPane(), "pn5");
	}

	private void mostrarPn4() {
		setSize(750, 400);
		((CardLayout) getContentPane().getLayout()).show(getContentPane(), "pn4");
	}

	private boolean validarSiguiente2() {
		if (getTAFechasMes().getText().isBlank()) {
			mostrarAvisoSiguiente2();
			return false;
		}

		getTAAvisoSiguienteMes().setText("");
		return true;
	}

	private void mostrarAvisoSiguiente2() {
		getTAAvisoSiguienteMes().setText("Por favor, introduzca al menos una fecha.");
	}

	private JLabel getLbDiaMes() {
		if (lbDiaMes == null) {
			lbDiaMes = new JLabel("Día del mes:");
			lbDiaMes.setFont(new Font("Arial", Font.PLAIN, 12));
			lbDiaMes.setDisplayedMnemonic('D');
			lbDiaMes.setBounds(10, 11, 230, 22);
		}
		return lbDiaMes;
	}

	private JComboBox<Integer> getCbDiaMes() {
		if (cbDiaMes == null) {
			cbDiaMes = new JComboBox<>();
			cbDiaMes.setModel(new DefaultComboBoxModel<>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
					15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 }));
			cbDiaMes.setBounds(10, 50, 230, 22);
		}
		return cbDiaMes;
	}

	private JButton getBtnAñadirMes() {
		if (btnAñadirMes == null) {
			btnAñadirMes = new JButton("Añadir");
			btnAñadirMes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarFecha2()) {
						añadirFecha2();
					}
				}
			});
			btnAñadirMes.setMnemonic('A');
			btnAñadirMes.setFont(new Font("Arial", Font.BOLD, 13));
			btnAñadirMes.setBackground(new Color(255, 215, 0));
			btnAñadirMes.setBounds(10, 290, 155, 60);
		}
		return btnAñadirMes;
	}

	private void añadirFecha2() {
		String strFecha;
		if (getChckbxTodoElDiaMes().isSelected()) {
			strFecha = getCbDiaMes().getSelectedItem().toString() + " 8:00 23:00\n";
		} else {
			strFecha = getCbDiaMes().getSelectedItem().toString() + " " + getCbHoraInicio().getSelectedItem().toString()
					+ " " + getCbHoraFinal().getSelectedItem().toString() + "\n";
		}

		if (!getTAFechasMes().getText().contains(strFecha)) {
			if (!colisiona(strFecha)) {
				getTAFechasMes().append(strFecha);
			}
		}
	}

	private boolean validarFecha2() {
		if (!getChckbxTodoElDiaMes().isSelected() && finMenorOIgualQueInicio2()) {
			mostrarAvisoHora2();
			return false;
		}

		getTAAvisoHoraMes().setText("");
		return true;
	}

	private void mostrarAvisoHora2() {
		getTAAvisoHoraMes().setText("La hora de inicio debe ser anterior a la hora de fin.");
	}

	private boolean finMenorOIgualQueInicio2() {
		return calcularHoraParaString(getCbHoraFinal().getSelectedItem().toString()) <= calcularHoraParaString(
				getCbHoraInicio().getSelectedItem().toString());
	}

	private JScrollPane getSPFechasMes() {
		if (sPFechasMes == null) {
			sPFechasMes = new JScrollPane();
			sPFechasMes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			sPFechasMes.setBounds(565, 48, 155, 229);
			sPFechasMes.setViewportView(getTAFechasMes());
			sPFechasMes.setViewportView(getTAFechasMes());
		}
		return sPFechasMes;
	}

	private JTextArea getTAAvisoHoraMes() {
		if (tAAvisoHoraMes == null) {
			tAAvisoHoraMes = new JTextArea();
			tAAvisoHoraMes.setWrapStyleWord(true);
			tAAvisoHoraMes.setLineWrap(true);
			tAAvisoHoraMes.setForeground(Color.RED);
			tAAvisoHoraMes.setFont(new Font("Arial", Font.ITALIC, 12));
			tAAvisoHoraMes.setEnabled(false);
			tAAvisoHoraMes.setEditable(false);
			tAAvisoHoraMes.setDisabledTextColor(Color.RED);
			tAAvisoHoraMes.setBorder(null);
			tAAvisoHoraMes.setBounds(10, 238, 155, 42);
		}
		return tAAvisoHoraMes;
	}

	private JButton getBtnAtras2Mes() {
		if (btnAtras2Mes == null) {
			btnAtras2Mes = new JButton("Atrás");
			btnAtras2Mes.setMnemonic('T');
			btnAtras2Mes.setFont(new Font("Arial", Font.BOLD, 13));
			btnAtras2Mes.setBackground(new Color(255, 69, 0));
			btnAtras2Mes.setBounds(565, 290, 155, 60);
			btnAtras2Mes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarPn1();
				}
			});
		}
		return btnAtras2Mes;
	}

	private JTextArea getTAAvisoSiguienteMes() {
		if (tAAvisoSiguienteMes == null) {
			tAAvisoSiguienteMes = new JTextArea();
			tAAvisoSiguienteMes.setWrapStyleWord(true);
			tAAvisoSiguienteMes.setLineWrap(true);
			tAAvisoSiguienteMes.setForeground(Color.RED);
			tAAvisoSiguienteMes.setFont(new Font("Arial", Font.ITALIC, 12));
			tAAvisoSiguienteMes.setEnabled(false);
			tAAvisoSiguienteMes.setEditable(false);
			tAAvisoSiguienteMes.setDisabledTextColor(Color.RED);
			tAAvisoSiguienteMes.setBorder(null);
			tAAvisoSiguienteMes.setBounds(400, 219, 155, 60);
		}
		return tAAvisoSiguienteMes;
	}

	private JButton getBtnBorrarMes() {
		if (btnBorrarMes == null) {
			btnBorrarMes = new JButton("Borrar");
			btnBorrarMes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					borrarFechaMes();
				}
			});
			btnBorrarMes.setMnemonic('B');
			btnBorrarMes.setFont(new Font("Arial", Font.BOLD, 13));
			btnBorrarMes.setBackground(new Color(255, 69, 0));
			btnBorrarMes.setBounds(175, 290, 155, 60);
		}
		return btnBorrarMes;
	}

	private void borrarFechaMes() {
		String[] arrayFechas = getTAFechasMes().getText().split("\\r?\\n");
		getTAFechasMes().setText("");
		for (int i = 0; i < arrayFechas.length - 1; i++) {
			getTAFechasMes().append(arrayFechas[i] + "\n");
		}
	}

	private JPanel getPn5() {
		if (pn5 == null) {
			pn5 = new JPanel();
			pn5.setLayout(null);
			pn5.add(getBtnCrearMes());
			pn5.add(getTACalendarioMes());
			pn5.add(getBtnAtras3Mes());
			pn5.add(getLbInicio());
			pn5.add(getLbMesInicial());
			pn5.add(getSpMesInicial());
			pn5.add(getLbAñoInicial());
			pn5.add(getSpAñoInicial());
			pn5.add(getLbFin());
			pn5.add(getLbMesFin());
			pn5.add(getSpMesFin());
			pn5.add(getLbAñoFin());
			pn5.add(getSpAñoFin());
		}
		return pn5;
	}

	private JButton getBtnCrearMes() {
		if (btnCrearMes == null) {
			btnCrearMes = new JButton("Crear");
			btnCrearMes.setMnemonic('C');
			btnCrearMes.setFont(new Font("Arial", Font.BOLD, 13));
			btnCrearMes.setBackground(new Color(173, 255, 47));
			btnCrearMes.setBounds(400, 290, 155, 60);
			btnCrearMes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarFechas()) {
						cerrarYCrearActividad2();
					}
				}
			});
		}
		return btnCrearMes;
	}

	private void cerrarYCrearActividad2() {
		List<ActividadDto> actividades = generarActividades2();
		List<Colisionable> colisiones = new ArrayList<>();

		for (ActividadDto actividad : actividades) {
			colisiones.addAll(va.getAdministradorCRUD().obtenerColisiones(actividad.getInstalacion(),
					actividad.getFechaInicio(), actividad.getFechaFin()));
		}

		if (colisiones.isEmpty()) {
			for (ActividadDto actividad : actividades) {
				va.getAdministradorCRUD().añadirActividad(actividad.getFechaInicio(), actividad.getFechaFin(),
						actividad.getTipo(), actividad.getInstalacion(), actividad.getPlazasTotales());
			}

			dispose();
		} else {
			mostrarVentanaColisiones(colisiones);
		}
	}

	@SuppressWarnings("deprecation")
	private List<ActividadDto> generarActividades2() {
		List<ActividadDto> actividades = new ArrayList<>();
		String[] arrayFechas = getTAFechasMes().getText().split("\\r?\\n");
		for (String strFecha : arrayFechas) {
			String[] arrayFecha = strFecha.split(" ");

			Timestamp fechaInicio = new Timestamp((Integer) getSpAñoInicial().getValue() - 1900,
					calculateMonth((String) getSpMesInicial().getValue()), Integer.parseInt(arrayFecha[0]), 0, 0, 0, 0);
			fechaInicio.setHours(calcularHoraParaString(arrayFecha[1]));

			Timestamp fechaFin = new Timestamp((Integer) getSpAñoFin().getValue() - 1900,
					calculateMonth((String) getSpMesFin().getValue()), Integer.parseInt(arrayFecha[0]), 0, 0, 0, 0);
			fechaFin.setHours(calcularHoraParaString(arrayFecha[2]));

			while (fechaInicio.before(fechaFin)) {
				Timestamp horaInicio = new Timestamp(fechaInicio.getTime());

				Timestamp horaFin = new Timestamp(fechaInicio.getTime());

				horaFin.setHours(fechaFin.getHours());

				actividades.add(new ActividadDto(null, getCBTipo().getSelectedItem().toString(),
						getCBInstalacion().getSelectedItem().toString(), null, calcularPlazas(), 0, horaInicio,
						horaFin));

				if (fechaInicio.getMonth() < 11) {
					fechaInicio.setMonth(fechaInicio.getMonth() + 1);
				} else {
					fechaInicio.setMonth(0);
					fechaInicio.setYear(fechaInicio.getYear() + 1);
				}
			}

		}

		return actividades;
	}

	@SuppressWarnings("deprecation")
	private boolean validarFechas() {
		Timestamp initialDate = new Timestamp((Integer) getSpAñoInicial().getValue() - 1900,
				calculateMonth((String) getSpMesInicial().getValue()), 1, 0, 0, 0, 0);

		Timestamp endDate = new Timestamp((Integer) getSpAñoFin().getValue() - 1900,
				calculateMonth((String) getSpMesFin().getValue()), 1, 0, 0, 0, 0);

		if (initialDate.before(new Timestamp(System.currentTimeMillis()))) {
			mostrarAvisoFecha();
			return false;
		} else if (endDate.before(initialDate)) {
			mostrarOtroAvisoFecha();
			return false;
		}

		return true;
	}

	private int calculateMonth(String value) {
		switch (value) {
		case "Enero":
			return 0;
		case "Febrero":
			return 1;
		case "Marzo":
			return 2;
		case "Abril":
			return 3;
		case "Mayo":
			return 4;
		case "Junio":
			return 5;
		case "Julio":
			return 6;
		case "Agosto":
			return 7;
		case "Septiembre":
			return 8;
		case "Octubre":
			return 9;
		case "Noviembre":
			return 10;
		default:
			return 11;
		}
	}

	private void mostrarAvisoFecha() {
		getTACalendarioMes().setText("El mes seleccionado no puede ser anterior al actual.");
	}

	private void mostrarOtroAvisoFecha() {
		getTACalendarioMes().setText("El mes final no puede ser anterior al inicial.");
	}

	private JTextArea getTACalendarioMes() {
		if (tACalendarioMes == null) {
			tACalendarioMes = new JTextArea();
			tACalendarioMes.setWrapStyleWord(true);
			tACalendarioMes.setLineWrap(true);
			tACalendarioMes.setForeground(Color.RED);
			tACalendarioMes.setFont(new Font("Arial", Font.ITALIC, 12));
			tACalendarioMes.setEnabled(false);
			tACalendarioMes.setEditable(false);
			tACalendarioMes.setDisabledTextColor(Color.RED);
			tACalendarioMes.setBorder(null);
			tACalendarioMes.setBounds(400, 220, 155, 60);
		}
		return tACalendarioMes;
	}

	private JButton getBtnAtras3Mes() {
		if (btnAtras3Mes == null) {
			btnAtras3Mes = new JButton("Atrás");
			btnAtras3Mes.setMnemonic('A');
			btnAtras3Mes.setFont(new Font("Arial", Font.BOLD, 13));
			btnAtras3Mes.setBackground(new Color(255, 69, 0));
			btnAtras3Mes.setBounds(565, 290, 155, 60);
		}
		return btnAtras3Mes;
	}

	private JLabel getLbInicio() {
		if (lbInicio == null) {
			lbInicio = new JLabel("Selecciona el mes inicial:");
			lbInicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbInicio.setBounds(10, 10, 172, 28);
		}
		return lbInicio;
	}

	private JLabel getLbMesInicial() {
		if (lbMesInicial == null) {
			lbMesInicial = new JLabel("Mes:");
			lbMesInicial.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbMesInicial.setBounds(20, 48, 29, 20);
		}
		return lbMesInicial;
	}

	private JSpinner getSpMesInicial() {
		if (spMesInicial == null) {
			spMesInicial = new JSpinner();
			spMesInicial.setFont(new Font("Tahoma", Font.PLAIN, 12));
			spMesInicial.setModel(new SpinnerListModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
					"Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
			spMesInicial.setBounds(59, 48, 109, 22);
		}
		return spMesInicial;
	}

	private JLabel getLbAñoInicial() {
		if (lbAñoInicial == null) {
			lbAñoInicial = new JLabel("Año:");
			lbAñoInicial.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbAñoInicial.setBounds(206, 48, 29, 20);
		}
		return lbAñoInicial;
	}

	private JSpinner getSpAñoInicial() {
		if (spAñoInicial == null) {
			spAñoInicial = new JSpinner();
			spAñoInicial.setModel(
					new SpinnerNumberModel(Integer.valueOf(2022), Integer.valueOf(2022), null, Integer.valueOf(1)));
			spAñoInicial.setFont(new Font("Tahoma", Font.PLAIN, 12));
			spAñoInicial.setBounds(245, 48, 109, 22);
		}
		return spAñoInicial;
	}

	private JLabel getLbFin() {
		if (lbFin == null) {
			lbFin = new JLabel("Selecciona el mes final:");
			lbFin.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbFin.setBounds(10, 125, 172, 28);
		}
		return lbFin;
	}

	private JLabel getLbMesFin() {
		if (lbMesFin == null) {
			lbMesFin = new JLabel("Mes:");
			lbMesFin.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbMesFin.setBounds(20, 163, 29, 20);
		}
		return lbMesFin;
	}

	private JSpinner getSpMesFin() {
		if (spMesFin == null) {
			spMesFin = new JSpinner();
			spMesFin.setModel(new SpinnerListModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
					"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
			spMesFin.setFont(new Font("Tahoma", Font.PLAIN, 12));
			spMesFin.setBounds(59, 163, 109, 22);
		}
		return spMesFin;
	}

	private JLabel getLbAñoFin() {
		if (lbAñoFin == null) {
			lbAñoFin = new JLabel("Año:");
			lbAñoFin.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbAñoFin.setBounds(206, 163, 29, 20);
		}
		return lbAñoFin;
	}

	private JSpinner getSpAñoFin() {
		if (spAñoFin == null) {
			spAñoFin = new JSpinner();
			spAñoFin.setModel(
					new SpinnerNumberModel(Integer.valueOf(2022), Integer.valueOf(2022), null, Integer.valueOf(1)));
			spAñoFin.setFont(new Font("Tahoma", Font.PLAIN, 12));
			spAñoFin.setBounds(245, 163, 109, 22);
		}
		return spAñoFin;
	}

	private JTextArea getTAFechasMes() {
		if (tAFechasMes == null) {
			tAFechasMes = new JTextArea();
			tAFechasMes.setEditable(false);
		}
		return tAFechasMes;
	}

	private JComboBox<String> getCbSemanaMes() {
		if (cbSemanaMes == null) {
			cbSemanaMes = new JComboBox<>();
			cbSemanaMes.setModel(new DefaultComboBoxModel<>(new String[] { "Semanas", "Meses" }));
			cbSemanaMes.setBounds(370, 135, 156, 22);
		}
		return cbSemanaMes;
	}
}
