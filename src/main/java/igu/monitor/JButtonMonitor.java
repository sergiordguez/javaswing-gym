package igu.monitor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import igu.socio.VentanaVerActividad;
import persistencia.administrador.ActividadDto;

public class JButtonMonitor extends JButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private VistaMonitor vm;
	private ActividadDto act;

	public JButtonMonitor(VistaMonitor vm, ActividadDto act) {
		this.vm = vm;
		this.act = act;
		formatear();
	}

	private void formatear() {
		setText(act.getIdActividad());
		setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		setHorizontalAlignment(SwingConstants.CENTER);
		setBackground(new Color(173, 255, 47));

		if (act.getPlazasTotales() == 0) {
			setBackground(new Color(173, 255, 47));
		} else {
			setBackground(new Color(255, 105, 97));
		}
		addActionListener(new VerInformacion());
	}

	class VerInformacion implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (act.getPlazasTotales() == 0) {
				mostrarVentanaVerActividad();
			} else {
				mostrarVentanaInformacion();
			}
		}
	}

	private void mostrarVentanaInformacion() {
		VentanaInformacion va = new VentanaInformacion(act, vm);
		va.setLocationRelativeTo(this);
		va.setVisible(true);
	}

	private void mostrarVentanaVerActividad() {
		VentanaVerActividad va = new VentanaVerActividad(act);
		va.setLocationRelativeTo(this);
		va.setVisible(true);
	}
}