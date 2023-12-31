package igu.socio;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import persistencia.administrador.ActividadDto;
import persistencia.socio.SocioCRUD;

public class VentanaVerActividadReserva extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ActividadDto actividad;
	private JLabel lbActividad;
	private JLabel lbInicio;
	private JLabel lbFin;
	private JLabel lbTipo;
	private JLabel lbInstalacion;
	private JLabel lbMonitor;
	private JLabel lbPlazas;
	private JLabel lbMonitor_1;
	private JLabel lbPlazas_1;
	private JLabel lbTipo_1;
	private JLabel lbInstalacion_1;
	private JLabel lbFin_1;
	private JLabel lbInicio_1;
	private JButton btnReserva;
	private SocioCRUD sc;
	private VentanaHorario vh;

	/**
	 * Create the dialog.
	 */
	public VentanaVerActividadReserva(VentanaHorario vh, ActividadDto act) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModal(true);
		this.vh = vh;
		this.actividad = act;
		this.sc = vh.getVS().getSc();
		setBounds(100, 100, 414, 302);
		getContentPane().setLayout(null);
		getContentPane().add(getLbActividad());
		getContentPane().add(getLbInicio());
		getContentPane().add(getLbFin());
		getContentPane().add(getLbTipo());
		getContentPane().add(getLbInstalacion());
		getContentPane().add(getLbMonitor());
		getContentPane().add(getLbPlazas());
		getContentPane().add(getLbMonitor_1());
		getContentPane().add(getLbPlazas_1());
		getContentPane().add(getLbTipo_1());
		getContentPane().add(getLbInstalacion_1());
		getContentPane().add(getLbFin_1());
		getContentPane().add(getLbInicio_1());
		getContentPane().add(getBtnReserva());
	}

	private JLabel getLbActividad() {
		if (lbActividad == null) {
			lbActividad = new JLabel(actividad.getIdActividad());
			lbActividad.setFont(new Font("Arial Black", Font.BOLD, 18));
			lbActividad.setBounds(10, 10, 167, 36);
		}
		return lbActividad;
	}

	private JLabel getLbInicio() {
		if (lbInicio == null) {
			lbInicio = new JLabel("Fecha de Inicio:");
			lbInicio.setFont(new Font("Arial", Font.PLAIN, 12));
			lbInicio.setBounds(10, 56, 100, 13);
		}
		return lbInicio;
	}

	private JLabel getLbFin() {
		if (lbFin == null) {
			lbFin = new JLabel("Fecha de Fin:");
			lbFin.setFont(new Font("Arial", Font.PLAIN, 12));
			lbFin.setBounds(10, 79, 100, 13);
		}
		return lbFin;
	}

	private JLabel getLbTipo() {
		if (lbTipo == null) {
			lbTipo = new JLabel("Tipo de Actividad:");
			lbTipo.setFont(new Font("Arial", Font.PLAIN, 12));
			lbTipo.setBounds(10, 125, 100, 13);
		}
		return lbTipo;
	}

	private JLabel getLbInstalacion() {
		if (lbInstalacion == null) {
			lbInstalacion = new JLabel("Instalación:");
			lbInstalacion.setFont(new Font("Arial", Font.PLAIN, 12));
			lbInstalacion.setBounds(10, 102, 100, 13);
		}
		return lbInstalacion;
	}

	private JLabel getLbMonitor() {
		if (lbMonitor == null) {
			lbMonitor = new JLabel("Monitor:");
			lbMonitor.setFont(new Font("Arial", Font.PLAIN, 12));
			lbMonitor.setBounds(10, 171, 100, 13);
		}
		return lbMonitor;
	}

	private JLabel getLbPlazas() {
		if (lbPlazas == null) {
			lbPlazas = new JLabel("Plazas Disp:");
			lbPlazas.setFont(new Font("Arial", Font.PLAIN, 12));
			lbPlazas.setBounds(10, 148, 100, 13);
		}
		return lbPlazas;
	}

	private JLabel getLbMonitor_1() {
		if (lbMonitor_1 == null) {
			lbMonitor_1 = new JLabel("");

			if (actividad.getMonitor() == null) {
				lbMonitor_1.setText("No hay monitor asignado");

			} else {
				lbMonitor_1.setText(actividad.getMonitor());
			}

			lbMonitor_1.setFont(new Font("Arial", Font.PLAIN, 12));
			lbMonitor_1.setBounds(120, 171, 148, 13);
		}
		return lbMonitor_1;
	}

	private JLabel getLbPlazas_1() {
		if (lbPlazas_1 == null) {
			lbPlazas_1 = new JLabel("");

			if (actividad.getPlazasTotales() == 0) {
				lbPlazas_1.setText("Ilimitadas");
			} else {
				lbPlazas_1.setText(Integer.toString(actividad.getPlazasDisponibles()));
			}

			lbPlazas_1.setFont(new Font("Arial", Font.PLAIN, 12));
			lbPlazas_1.setBounds(120, 148, 148, 13);
		}
		return lbPlazas_1;
	}

	private JLabel getLbTipo_1() {
		if (lbTipo_1 == null) {
			lbTipo_1 = new JLabel(actividad.getTipo());
			lbTipo_1.setFont(new Font("Arial", Font.PLAIN, 12));
			lbTipo_1.setBounds(120, 125, 148, 13);
		}
		return lbTipo_1;
	}

	private JLabel getLbInstalacion_1() {
		if (lbInstalacion_1 == null) {
			lbInstalacion_1 = new JLabel(actividad.getInstalacion());
			lbInstalacion_1.setFont(new Font("Arial", Font.PLAIN, 12));
			lbInstalacion_1.setBounds(120, 102, 148, 13);
		}
		return lbInstalacion_1;
	}

	private JLabel getLbFin_1() {
		if (lbFin_1 == null) {
			lbFin_1 = new JLabel(actividad.getFechaFin().toString());
			lbFin_1.setFont(new Font("Arial", Font.PLAIN, 12));
			lbFin_1.setBounds(120, 79, 148, 13);
		}
		return lbFin_1;
	}

	private JLabel getLbInicio_1() {
		if (lbInicio_1 == null) {
			lbInicio_1 = new JLabel(actividad.getFechaInicio().toString());
			lbInicio_1.setFont(new Font("Arial", Font.PLAIN, 12));
			lbInicio_1.setBounds(120, 57, 148, 13);
		}
		return lbInicio_1;
	}

	private JButton getBtnReserva() {
		if (btnReserva == null) {
			btnReserva = new JButton("Reservar Plaza");
			btnReserva.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (actividad.getPlazasDisponibles() == 0) {
						JOptionPane.showMessageDialog(null, "Las plazas para la actividad están completas");
					} else if (sc.existeReservaNumero(vh.getSocio().getIdSocio(), actividad)) {
						JOptionPane.showMessageDialog(null,
								"Ya tenias reserva para la actividad " + actividad.getIdActividad());
					} else {
						sc.setReserva(vh.getSocio(), actividad);
						sc.restarPlazas(actividad);
						JOptionPane.showMessageDialog(null, "Has reservado plaza para " + actividad.getIdActividad());
					}
				}
			});
			btnReserva.setBounds(283, 229, 105, 23);
		}
		return btnReserva;
	}
}
