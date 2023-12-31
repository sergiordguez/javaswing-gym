package igu.socio;

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
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import logica.socio.TablaLogicaSocio;
import persistencia.administrador.ActividadDto;
import persistencia.socio.SocioDto;

public class VentanaHorario extends JDialog {

	private VentanaSocio vs;
	private Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
	private JPanel pn2;
	private JScrollPane sPTabla;
	private JPanel pnTabla1;
	private TablaLogicaSocio tablaLogica;
	private JPanel pnTabla;
	private JLabel lbFecha;
	private AccionarBotonSiguiente sig = new AccionarBotonSiguiente();
	private AccionarBotonAnterior ant = new AccionarBotonAnterior();
	private JLabel lbLeyenda;
	private JLabel lbPlazasIlimitadas;
	private JLabel lbPlazasLimitadas;
	private JLabel lbGreen;
	private JLabel lbRed;

	/**
	 * Create the dialog.
	 */
	public VentanaHorario(VentanaSocio vs) {
		this.vs = vs;
		this.tablaLogica = new TablaLogicaSocio(vs.getSc());
		setBounds(100, 100, 450, 696);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPn2());
		repintarTabla();
	}

	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			pn2.setLayout(null);
			pn2.add(getSPTabla());
			pn2.add(getLbFecha());
			pn2.add(getLbLeyenda());
			pn2.add(getLbPlazasIlimitadas());
			pn2.add(getLbPlazasLimitadas());
			pn2.add(getLbGreen());
			pn2.add(getLbRed());
		}
		return pn2;
	}

	private JScrollPane getSPTabla() {
		if (sPTabla == null) {
			sPTabla = new JScrollPane();
			sPTabla.setBorder(null);
			sPTabla.setEnabled(false);
			sPTabla.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			sPTabla.setBounds(62, 63, 300, 500);
			sPTabla.setViewportView(getPnTabla());
		}
		return sPTabla;
	}

	private void creaBotonesTabla() {
		pnTabla.removeAll();
		creaColumnasTabla();
		creaFilasTabla();
		getLbFecha().setText(pintarFecha());
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

		List<JButtonActividad> buttons = nuevosBotones(hour);

		for (JButtonActividad but : buttons) {
			panel.add(but);
		}

		return panel;
	}

	private List<JButtonActividad> nuevosBotones(int hour) {
		List<JButtonActividad> buttons = new ArrayList<>();

		List<ActividadDto> act = tablaLogica.getTabla().get(hour);

		if (act != null) {
			for (ActividadDto ac : act) {
				JButtonActividad but = new JButtonActividad(this, ac);
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

	private JPanel getPnTabla() {
		if (pnTabla == null) {
			pnTabla = new JPanel();
			pnTabla.setBorder(null);
			pnTabla.setBounds(0, 0, 1000, 500);
			pnTabla.setLayout(new GridLayout(16, 2, 5, 5));
		}
		return pnTabla;
	}

	private JLabel getLbFecha() {
		if (lbFecha == null) {
			lbFecha = new JLabel("New label");
			lbFecha.setHorizontalAlignment(SwingConstants.CENTER);
			lbFecha.setFont(new Font("Arial Black", Font.BOLD, 18));
			lbFecha.setBounds(62, 11, 300, 40);
		}
		return lbFecha;
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularSiguiente() {
		return new Timestamp(fechaActual.getYear(), fechaActual.getMonth(), fechaActual.getDate() + 1, 0, 0, 0, 0);
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularAnterior() {
		return new Timestamp(fechaActual.getYear(), fechaActual.getMonth(), fechaActual.getDate() - 1, 0, 0, 0, 0);
	}

	private boolean isActualDate(Timestamp fecha) {
		Timestamp hoy = new Timestamp(System.currentTimeMillis());

		if ((fecha.getYear() != hoy.getYear()) || (fecha.getMonth() != hoy.getMonth())
				|| (fecha.getDate() != hoy.getDate())) {
			return false;
		}
		return true;
	}

	private String pintarFecha() {

		String dia = Integer.toString(fechaActual.getDate());
		String mes = Integer.toString(fechaActual.getMonth() + 1);
		String año = Integer.toString(fechaActual.getYear()).substring(1, 3);

		String str = String.format("%s/%s/20%s", dia, mes, año);
		return str;
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

	private JLabel getLbLeyenda() {
		if (lbLeyenda == null) {
			lbLeyenda = new JLabel("Leyenda:");
			lbLeyenda.setFont(new Font("Arial", Font.BOLD, 13));
			lbLeyenda.setBounds(62, 573, 75, 13);
		}
		return lbLeyenda;
	}

	private JLabel getLbPlazasIlimitadas() {
		if (lbPlazasIlimitadas == null) {
			lbPlazasIlimitadas = new JLabel("Plazas Ilimitadas: ");
			lbPlazasIlimitadas.setFont(new Font("Arial", Font.PLAIN, 12));
			lbPlazasIlimitadas.setBounds(62, 596, 116, 13);
		}
		return lbPlazasIlimitadas;
	}

	private JLabel getLbPlazasLimitadas() {
		if (lbPlazasLimitadas == null) {
			lbPlazasLimitadas = new JLabel("Plazas Limitadas: ");
			lbPlazasLimitadas.setFont(new Font("Arial", Font.PLAIN, 12));
			lbPlazasLimitadas.setBounds(62, 619, 116, 13);
		}
		return lbPlazasLimitadas;
	}

	private JLabel getLbGreen() {
		if (lbGreen == null) {
			lbGreen = new JLabel(" ");
			lbGreen.setOpaque(true);
			lbGreen.setBounds(188, 596, 13, 13);
			lbGreen.setBackground(new Color(173, 255, 47));
		}
		return lbGreen;
	}

	private JLabel getLbRed() {
		if (lbRed == null) {
			lbRed = new JLabel("");
			lbRed.setOpaque(true);
			lbRed.setBounds(188, 619, 13, 13);
			lbRed.setBackground(new Color(255, 105, 97));
		}
		return lbRed;
	}

	public SocioDto getSocio() {
		return vs.getVI().getSocio();
	}

	public VentanaSocio getVS() {
		return this.vs;
	}
}
