package igu.socio;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import logica.socio.TablaLogicaSocio2;
import persistencia.administrador.ReservaDto;

public class VentanaCancelacion extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private VentanaSocio conect;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblActividades;
	private JButton btnCancelar;
	private TablaLogicaSocio2 tablaLogica;
	private JScrollPane scrollPane;
	private JList<ReservaDto> list;

	/**
	 * Create the frame.
	 */
	public VentanaCancelacion(VentanaSocio conect) {
		this.tablaLogica = new TablaLogicaSocio2(conect.getSc());
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.conect = conect;
		setTitle("Gimnasio: Socio: Cancelar Actividad");
		setBounds(100, 100, 359, 603);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPanel());
		rellenarLista();
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(10, 11, 323, 542);
			panel.setLayout(null);
			panel.add(getLblActividades());
			panel.add(getBtnCancelar());
			panel.add(getScrollPane());
		}
		return panel;
	}

	private JLabel getLblActividades() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Actividades");
			lblActividades.setForeground(Color.BLUE);
			lblActividades.setFont(new Font("Tahoma", Font.PLAIN, 25));
			lblActividades.setBounds(90, 48, 152, 37);
		}
		return lblActividades;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar Reserva");
			btnCancelar.setMnemonic('C');
			btnCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (list.getSelectedValue() == null) {
						JOptionPane.showMessageDialog(null, "Debes selecciona una actividad para cancelar la reserva");
					} else {
						JOptionPane.showMessageDialog(null, "Has cancelado la reserva");
						mostrarVentanaSocio();
					}
				}
			});
			btnCancelar.setBounds(186, 502, 127, 29);
		}
		return btnCancelar;
	}

	private void rellenarLista() {
		DefaultListModel<ReservaDto> modelo = new DefaultListModel<>();
		tablaLogica.generarTabla(conect.getVI().getSocio());
		List<ReservaDto> actividades = tablaLogica.getTabla();
		for (ReservaDto actividade : actividades) {
			modelo.addElement(actividade);
		}
		getList().setModel(modelo);
	}

	private void mostrarVentanaSocio() {
		conect.setLocationRelativeTo(this);
		conect.setModal(true);
		conect.setVisible(true);
		this.dispose();
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 86, 303, 397);
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}

	private JList<ReservaDto> getList() {
		if (list == null) {
			list = new JList<>();
		}
		return list;
	}
}
