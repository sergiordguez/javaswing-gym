package igu.socio;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import persistencia.administrador.ActividadDto;

public class JButtonActividad extends JButton {

	private ActividadDto act;
	private VentanaHorario vh;

	public JButtonActividad(VentanaHorario vh, ActividadDto act) {
		this.vh = vh;
		this.act = act;
		formatear();
	}

	public JButtonActividad(ActividadDto act) {
		this.act = act;
		formatear();
	}

	public ActividadDto getActividad() {
		return act;
	}

	private void formatear() {
		setText(act.getIdActividad());
		setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		setHorizontalAlignment(SwingConstants.CENTER);

		if (act.getPlazasTotales() == 0) {
			setBackground(new Color(173, 255, 47));
		} else {
			setBackground(new Color(255, 105, 97));
		}
		addActionListener(new VerActividad());
	}

	class VerActividad implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (act.getPlazasTotales() == 0) {
				mostrarVentanaVerActividad();
			} else {
				mostrarVentanaActividad();
			}

		}
	}

	private void mostrarVentanaActividad() {
		VentanaVerActividadReserva va = new VentanaVerActividadReserva(vh, act);
		va.setLocationRelativeTo(this);
		va.setVisible(true);
	}

	private void mostrarVentanaVerActividad() {
		VentanaVerActividad va = new VentanaVerActividad(act);
		va.setLocationRelativeTo(this);
		va.setVisible(true);
	}

}
