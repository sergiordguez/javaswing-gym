package igu.socio;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import logica.socio.TablaLogicaSocio2;
import persistencia.administrador.AlquilerDto;
import persistencia.socio.SocioCRUD;

public class VentanaAnulacionAlquiler extends JDialog {

	private static final long serialVersionUID = 1L;
	private VentanaSocio conect;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblAlquileres;
	private JList<AlquilerDto> list;
	private JButton btnCancelar;
	private SocioCRUD sc;
	private TablaLogicaSocio2 tablaLogica;

	/**
	 * Create the frame.
	 */
	public VentanaAnulacionAlquiler(VentanaSocio conect) {
		this.tablaLogica = new TablaLogicaSocio2(conect.getSc());
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (confirmarAtras()) {
					dispose();
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.conect = conect;
		this.sc = new SocioCRUD(conect.getVI().getDatabase());
		setTitle("Gimnasio: Socio: Anular Alquiler");
		setBounds(100, 100, 359, 603);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPanel());
		rellenarLista();
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

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(10, 11, 323, 542);
			panel.setLayout(null);
			panel.add(getLblAlquileres());
			panel.add(getList());
			panel.add(getBtnCancelar());
		}
		return panel;
	}

	private JLabel getLblAlquileres() {
		if (lblAlquileres == null) {
			lblAlquileres = new JLabel("Alquileres");
			lblAlquileres.setForeground(Color.BLUE);
			lblAlquileres.setFont(new Font("Tahoma", Font.PLAIN, 25));
			lblAlquileres.setBounds(90, 48, 152, 37);
		}
		return lblAlquileres;
	}

	private JList<AlquilerDto> getList() {
		if (list == null) {
			list = new JList<>();
			list.setBounds(10, 102, 303, 372);
		}
		return list;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar Alquiler");
			btnCancelar.setMnemonic('C');
			btnCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (list.getSelectedValue() == null) {
						JOptionPane.showMessageDialog(null, "Debes selecciona un alquiler para cancelar la reserva");
					} else {
						cancelarAlquiler(list.getSelectedValue());
						JOptionPane.showMessageDialog(null, "Has cancelado el alquiler");
						mostrarVentanaSocio();
					}
				}
			});
			btnCancelar.setBounds(186, 502, 127, 29);
		}
		return btnCancelar;
	}

	private void rellenarLista() {
		DefaultListModel<AlquilerDto> modelo = new DefaultListModel<>();
		tablaLogica.generarTabla(conect.getVI().getSocio());
		List<AlquilerDto> actividades = tablaLogica.getAlquileresFuturos();
		actividades.forEach(i -> modelo.addElement(i));
		getList().setModel(modelo);
	}

	private void mostrarVentanaSocio() {
		conect.setLocationRelativeTo(this);
		conect.setModal(true);
		conect.setVisible(true);
		this.dispose();
	}

	private void cancelarAlquiler(AlquilerDto alq) {
		alq.setEnVigor(0);
		alq.setPagado(1);
		sc.cancelAlquiler(alq);
	}

	private Timestamp convertTo00PM(Timestamp ts) {
		return new Timestamp(ts.getYear(), ts.getMonth(), ts.getDate(), 0, 0, 0, 0);
	}
}
