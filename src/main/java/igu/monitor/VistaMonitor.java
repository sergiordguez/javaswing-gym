package igu.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import igu.VentanaInicial;
import logica.monitor.TablaLogicaMonitor;
import persistencia.administrador.ActividadDto;
import persistencia.monitor.MonitorCRUD;

public class VistaMonitor extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private TablaLogicaMonitor tablaLogica;
	private MonitorCRUD mc;
	private Timestamp fechaActual;
	private VentanaInicial vi;
	private JLabel lblHoy;
	private JScrollPane spnTabla;
	private AccionarBotonSiguiente sig = new AccionarBotonSiguiente();
	private AccionarBotonAnterior ant = new AccionarBotonAnterior();
	private JPanel pnTabla;

	/**
	 * Create the dialog.
	 */
	public VistaMonitor(VentanaInicial vi) {
		this.mc = new MonitorCRUD(vi.getDatabase());
		this.vi = vi;
		this.tablaLogica = new TablaLogicaMonitor(mc);
		setTitle("Gimnasio: Monitor");
		setBounds(100, 100, 720, 421);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.fechaActual = new Timestamp(System.currentTimeMillis());
		contentPanel.setLayout(null);
		contentPanel.add(getLblHoy());
		contentPanel.add(getSpnTabla());
		repintarFecha();
		repintarTabla();
	}

	private boolean isActualDate(Timestamp fecha) {
		Timestamp hoy = new Timestamp(System.currentTimeMillis());

		if ((fecha.getYear() != hoy.getYear()) || (fecha.getMonth() != hoy.getMonth())
				|| (fecha.getDate() != hoy.getDate())) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	private void repintarFecha() {
		getLblHoy().setText(fechaActual.toLocaleString().substring(0, 12));
	}

	private String pintarFecha() {

		String dia = Integer.toString(fechaActual.getDate());
		String mes = Integer.toString(fechaActual.getMonth() + 1);
		String año = Integer.toString(fechaActual.getYear()).substring(1, 3);

		String str = String.format("%s/%s/20%s", dia, mes, año);
		return str;
	}

	public VentanaInicial getVI() {
		return this.vi;
	}

	private JLabel getLblHoy() {
		if (lblHoy == null) {
			lblHoy = new JLabel("New label");
			lblHoy.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblHoy.setBounds(301, 10, 105, 31);
		}
		return lblHoy;
	}

	private JScrollPane getSpnTabla() {
		if (spnTabla == null) {
			spnTabla = new JScrollPane();
			spnTabla.setBounds(73, 52, 552, 304);
			spnTabla.setViewportView(getPnTabla());
		}
		return spnTabla;
	}

	private void creaBotonesTabla() {
		pnTabla.removeAll();
		creaColumnasTabla();
		creaFilasTabla();
		getLblHoy().setText(pintarFecha());
	}

	private void creaColumnasTabla() {
		pnTabla.add(nuevoBoton("<"));
		pnTabla.add(nuevoBoton(">"));
	}

	private void creaFilasTabla() {
		int contadorHora = 8;
		int hour = 8;
		for (int i = 8; i < 38; i++) {
			if (i % 2 == 0) {
				JLabel hora = new JLabel(contadorHora++ + ":00");
				hora.setFont(new Font("Arial", Font.PLAIN, 12));
				hora.setHorizontalAlignment(SwingConstants.CENTER);
				pnTabla.add(hora);
			} else {
				pnTabla.add(nuevoPanelActividad(hour++));
			}
		}
	}

	private JButton nuevoBoton(String str) {
		JButton boton = new JButton(str);
		boton.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		boton.setHorizontalAlignment(SwingConstants.CENTER);
		boton.setFont(new Font("Arial", Font.BOLD, 13));
		if (str.equals("<")) {
			boton.addActionListener(ant);
			if (isActualDate(fechaActual)) {
				boton.setEnabled(false);
			}
		} else if (str.equals(">")) {
			boton.addActionListener(sig);
		}

		return boton;
	}

	private JPanel nuevoPanelActividad(int hour) {
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(new FlowLayout());

		List<JButtonMonitor> buttons = nuevosBotones(hour);

		for (JButtonMonitor but : buttons) {
			panel.add(but);
		}

		return panel;
	}

	private List<JButtonMonitor> nuevosBotones(int hour) {
		List<JButtonMonitor> buttons = new ArrayList<>();

		List<ActividadDto> act = tablaLogica.getTabla().get(hour);

		if (act != null) {
			for (ActividadDto ac : act) {
				JButtonMonitor but = new JButtonMonitor(this, ac);
				buttons.add(but);
			}
		}
		return buttons;
	}

	public void repintarTabla() {
		tablaLogica.generarTabla(fechaActual);
		creaBotonesTabla();
		repaint();
		validate();
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularSiguiente() {
		return new Timestamp(fechaActual.getYear(), fechaActual.getMonth(), fechaActual.getDate() + 1, 0, 0, 0, 0);
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularAnterior() {
		return new Timestamp(fechaActual.getYear(), fechaActual.getMonth(), fechaActual.getDate() - 1, 0, 0, 0, 0);
	}

	class AccionarBotonSiguiente implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			fechaActual = calcularSiguiente();
			repintarTabla();
		}
	}

	class AccionarBotonAnterior implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			fechaActual = calcularAnterior();
			repintarTabla();
		}
	}

	private JPanel getPnTabla() {
		if (pnTabla == null) {
			pnTabla = new JPanel();
			pnTabla.setLayout(new GridLayout(16, 2, 5, 5));
		}
		return pnTabla;
	}
}
