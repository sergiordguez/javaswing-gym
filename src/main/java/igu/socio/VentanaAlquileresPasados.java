package igu.socio;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import logica.socio.TablaLogicaAlquileres;
import persistencia.administrador.AlquilerDto;
import persistencia.administrador.CuotaDto;
import persistencia.socio.SocioCRUD;
import persistencia.socio.SocioDto;

public class VentanaAlquileresPasados extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaSocio vl;
	private JButton btnAnterior;
	private JButton btnSiguiente;
	private JLabel lblFecha;
	private JLabel lblAlquileres;
	private JScrollPane scrollPane;
	private JList<AlquilerDto> listAlquileres;
	private JLabel lblCuota;
	private JTextField textFieldCuota;
	private Timestamp fecha;
	private TablaLogicaAlquileres tablaLogica;
	private SocioCRUD sc;

	/**
	 * Create the frame.
	 */
	public VentanaAlquileresPasados(VentanaSocio vl) {
		this.vl = vl;
		this.fecha = new Timestamp(System.currentTimeMillis());
		this.sc = vl.getSc();
		this.tablaLogica = new TablaLogicaAlquileres(sc);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 696);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnAnterior());
		contentPane.add(getBtnSiguiente());
		contentPane.add(getLblFecha());
		contentPane.add(getLblAlquileres());
		contentPane.add(getScrollPane());
		contentPane.add(getLblCuota());
		contentPane.add(getTextFieldCuota());
		repintarFecha();
		rellenarLista(getNumMes(fecha), vl.getVI().getSocio());
		rellenarCuota(lblFecha.getText(), vl.getVI().getSocio());
	}

	private JButton getBtnAnterior() {
		if (btnAnterior == null) {
			btnAnterior = new JButton("<");
			if (isActualDate(fecha)) {
				btnAnterior.setEnabled(false);
			}
			btnAnterior.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mesAnterior();
					rellenarLista(getNumMes(fecha), vl.getVI().getSocio());
					rellenarCuota(lblFecha.getText(), vl.getVI().getSocio());
					if (isActualDate(fecha)) {
						btnAnterior.setEnabled(false);
					}
				}
			});
			btnAnterior.setBounds(39, 87, 56, 23);
		}
		return btnAnterior;
	}

	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton(">");
			btnSiguiente.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mesSiguiente();
					rellenarLista(getNumMes(fecha), vl.getVI().getSocio());
					rellenarCuota(lblFecha.getText(), vl.getVI().getSocio());
					if (!isActualDate(fecha)) {
						btnAnterior.setEnabled(true);
					}
				}
			});
			btnSiguiente.setBounds(330, 87, 56, 23);
		}
		return btnSiguiente;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("New label");
			lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 22));
			lblFecha.setBounds(176, 79, 59, 28);
		}
		return lblFecha;
	}

	private JLabel getLblAlquileres() {
		if (lblAlquileres == null) {
			lblAlquileres = new JLabel("Alquileres: ");
			lblAlquileres.setBounds(39, 151, 181, 28);
		}
		return lblAlquileres;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(39, 190, 349, 285);
			scrollPane.setViewportView(getListAlquileres());
		}
		return scrollPane;
	}

	private JList<AlquilerDto> getListAlquileres() {
		if (listAlquileres == null) {
			listAlquileres = new JList<>();
		}
		return listAlquileres;
	}

	private JLabel getLblCuota() {
		if (lblCuota == null) {
			lblCuota = new JLabel("Cuota:");
			lblCuota.setBounds(39, 495, 181, 28);
		}
		return lblCuota;
	}

	private JTextField getTextFieldCuota() {
		if (textFieldCuota == null) {
			textFieldCuota = new JTextField();
			textFieldCuota.setEditable(false);
			textFieldCuota.setBounds(39, 534, 143, 28);
			textFieldCuota.setColumns(10);
		}
		return textFieldCuota;
	}

	@SuppressWarnings("deprecation")
	private void repintarFecha() {
		getLblFecha().setText(fecha.toLocaleString().substring(3, 6).toUpperCase());
	}

	private void mesSiguiente() {
		fecha = new Timestamp((long) (fecha.getTime() + (2629743833.3)));
		repintarFecha();
	}

	private void mesAnterior() {
		fecha = new Timestamp((long) (fecha.getTime() - (2629743833.3)));
		repintarFecha();
	}

	@SuppressWarnings("deprecation")
	private boolean isActualDate(Timestamp fecha) {
		Timestamp hoy = new Timestamp(System.currentTimeMillis());

		if (fecha.getMonth() != hoy.getMonth()) {
			return false;
		}
		return true;
	}

	private void rellenarLista(int mes, SocioDto socio) {
		DefaultListModel<AlquilerDto> model = new DefaultListModel<>();
		tablaLogica.generarTablaAlquiler(socio);
		AlquilerDto[] alquiler = tablaLogica.getTablaAlquiler(mes);
		for (AlquilerDto element : alquiler) {
			model.addElement(element);
		}
		listAlquileres.setModel(model);
	}

	private void rellenarCuota(String mes, SocioDto socio) {
		tablaLogica.generarTablaCuota(mes, socio);

		CuotaDto[] cuota = tablaLogica.getTablaCuota();
		if (cuota.length == 0) {
			textFieldCuota.setText("0");
		} else
			textFieldCuota.setText(cuota[0].toString());
	}

	@SuppressWarnings("deprecation")
	private int getNumMes(Timestamp fecha) {
		return fecha.getMonth();
	}
}
