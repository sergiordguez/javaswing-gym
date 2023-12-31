package igu.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import persistencia.administrador.AlquilerDto;

public class VentanaContabilidad extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private VentanaAdministrador va;
	private JPanel contentPane;
	private JButton btnPasarPagos;
	// private Timestamp fechaActual;
	private JScrollPane scrollPane;
	private JTextArea txtAlqNoPagados;
	private JButton btnAtras;

	/**
	 * Create the frame.
	 *
	 * @param va
	 */
	public VentanaContabilidad(VentanaAdministrador va) {
		this.va = va;
		// this.fechaActual = new Timestamp(System.currentTimeMillis());
		setTitle("Contabilidad");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 839, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnPasarPagos());
		contentPane.add(getScrollPane());
		contentPane.add(getBtnAtras());
	}

	private JButton getBtnPasarPagos() {
		if (btnPasarPagos == null) {
			btnPasarPagos = new JButton("Pasa pagos a contabilidad");
			btnPasarPagos.setMnemonic('p');
			btnPasarPagos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pasarPagosContabilidad();
					getTxtAlqNoPagados().setText(mostrarAlquileres());
					JOptionPane.showMessageDialog(null,
							"Todos los alquileres vencidos han sido pasados a contabilidad con éxito");
				}
			});
			btnPasarPagos.setBackground(new Color(173, 255, 47));
			btnPasarPagos.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnPasarPagos.setBounds(287, 22, 236, 46);
		}
		return btnPasarPagos;
	}

	protected void pasarPagosContabilidad() {
		va.getAdministradorCRUD().cargarDatosContabilidad();

	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(24, 95, 752, 186);
			scrollPane.setViewportView(getTxtAlqNoPagados());
		}
		return scrollPane;
	}

	private JTextArea getTxtAlqNoPagados() {
		if (txtAlqNoPagados == null) {
			txtAlqNoPagados = new JTextArea();
			txtAlqNoPagados.setEditable(false);
			txtAlqNoPagados.setFont(new Font("Monospaced", Font.PLAIN, 15));
			txtAlqNoPagados.setText(mostrarAlquileres());
		}
		return txtAlqNoPagados;
	}

	private String mostrarAlquileres() {
		String s = "";
		List<AlquilerDto> nopagados = va.getAdministradorCRUD().listarAlquileresNoPagados();
		if (nopagados.size() == 0) {
			s += "\n\n\n\t\tNo existe ningún alquiler no pagado";
			getTxtAlqNoPagados().setFont(new Font("Monospaced", Font.PLAIN, 18));
			getBtnPasarPagos().setEnabled(false);
		} else {
			for (AlquilerDto a : nopagados) {
				String enVigor = esVigor(a.getEnVigor());
				String pagado = esPagado(a.getPagado());
				s += "ID: " + a.getIdAlquiler() + " Fecha inicio: " + a.getFechaInicio() + "\tFecha final: "
						+ a.getFechaFin() + " \tAlquiler " + enVigor + " \tPagado: " + pagado + " \tIDSocio: "
						+ a.getIdSocio() + " \tInstalacion: " + a.getInstalacion() + "\n";
				s += "---------------------------------------------------------------------------------------"
						+ "----------------------------------------------------------------------------------";
				s += "\n";
			}

		}
		return s;
	}

	private String esPagado(int pagado) {
		if (pagado == 1) {
			return "sí";
		}
		return "no";
	}

	private String esVigor(int vigor) {
		if (vigor == 1) {
			return "en vigor";
		}
		return "no en vigor";
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			btnAtras.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnAtras.setBackground(new Color(165, 42, 42));
			btnAtras.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnAtras.setBounds(711, 291, 85, 29);
		}
		return btnAtras;
	}
}
