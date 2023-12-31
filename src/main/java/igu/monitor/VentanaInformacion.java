package igu.monitor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import persistencia.administrador.ActividadDto;
import persistencia.monitor.MonitorCRUD;
import persistencia.socio.SocioDto;

public class VentanaInformacion extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ActividadDto actividad;
	private MonitorCRUD mc;
	private VistaMonitor vm;
	private JLabel lbActividad;
	private JLabel lbInicio;
	private JLabel lbFin;
	private JLabel lbTipo;
	private JLabel lbInstalacion;
	private JLabel lbPlazas;
	private JLabel lbPlazas_1;
	private JLabel lbTipo_1;
	private JLabel lbInstalacion_1;
	private JLabel lbFin_1;
	private JLabel lbInicio_1;
	private JLabel lblParticipantes;
	private JTextField txtId;
	private JButton btnOk;
	private JScrollPane scrollPane;
	private JPanel pnParticipantes;

	/**
	 * Create the dialog.
	 */
	public VentanaInformacion(ActividadDto act, VistaMonitor vm) {
		setTitle("Gimnasio: Monitor: Info");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModal(true);
		this.actividad = act;
		this.mc = new MonitorCRUD(vm.getVI().getDatabase());
		this.vm = vm;
		setBounds(100, 100, 696, 434);
		getContentPane().setLayout(null);
		getContentPane().add(getLbActividad());
		getContentPane().add(getLbInicio());
		getContentPane().add(getLbFin());
		getContentPane().add(getLbTipo());
		getContentPane().add(getLbInstalacion());
		getContentPane().add(getLbPlazas());
		getContentPane().add(getLbPlazas_1());
		getContentPane().add(getLbTipo_1());
		getContentPane().add(getLbInstalacion_1());
		getContentPane().add(getLbFin_1());
		getContentPane().add(getLbInicio_1());
		getContentPane().add(getLblParticipantes());
		getContentPane().add(getTxtId());
		getContentPane().add(getBtnOk());
		getContentPane().add(getScrollPane());
		rellenarParticipantes();
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

	private JLabel getLbPlazas() {
		if (lbPlazas == null) {
			lbPlazas = new JLabel("Plazas:");
			lbPlazas.setFont(new Font("Arial", Font.PLAIN, 12));
			lbPlazas.setBounds(10, 148, 100, 13);
		}
		return lbPlazas;
	}

	private JLabel getLbPlazas_1() {
		if (lbPlazas_1 == null) {
			lbPlazas_1 = new JLabel("");

			if (actividad.getPlazasTotales() == 0) {
				lbPlazas_1.setText("Ilimitadas");
			} else {
				lbPlazas_1.setText(Integer.toString(actividad.getPlazasTotales()));
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

	private JLabel getLblParticipantes() {
		if (lblParticipantes == null) {
			lblParticipantes = new JLabel("Participantes");
			lblParticipantes.setFont(new Font("Arial Black", Font.BOLD, 18));
			lblParticipantes.setBounds(338, 10, 167, 36);
		}
		return lblParticipantes;
	}

	private void rellenarParticipantes() {
		List<SocioDto> lista = mc.getParticipantes(actividad);
		for (SocioDto dto : lista) {
			JCheckBox socio = new JCheckBox(dto.getIdSocio());
			socio.setVisible(true);
			pnParticipantes.add(socio);
		}
	}

	private JTextField getTxtId() {
		if (txtId == null) {
			txtId = new JTextField();
			txtId.setText("ID:");
			txtId.setBounds(338, 338, 233, 28);
			txtId.setColumns(10);
		}
		return txtId;
	}

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("Ok");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mc.setPlaza(txtId.getText(), actividad);
					JOptionPane.showMessageDialog(null, "El socio " + txtId.getText() + " ha sido añadido");
				}
			});
			btnOk.setBounds(581, 338, 54, 28);
		}
		return btnOk;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(338, 56, 294, 265);
			scrollPane.setViewportView(getPnParticipantes());
		}
		return scrollPane;
	}

	private JPanel getPnParticipantes() {
		if (pnParticipantes == null) {
			pnParticipantes = new JPanel();
		}
		return pnParticipantes;
	}
}
