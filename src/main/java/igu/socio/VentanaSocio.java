package igu.socio;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import igu.VentanaInicial;
import persistencia.socio.SocioCRUD;

public class VentanaSocio extends JDialog {

	private JPanel contentPane;
	private VentanaInicial vi;
	private JButton btnReservar;
	private JButton btnCancelar;
	private JButton btnHorario;
	private SocioCRUD sc;
	private JButton btnListado;
	private JButton btnPasados;
	private JButton btnAnularAlquiler;

	/**
	 * Create the frame.
	 */
	public VentanaSocio(VentanaInicial vi) {
		setResizable(false);
		setTitle("Gimnasio: Elección Socio");
		this.vi = vi;
		this.sc = new SocioCRUD(vi.getDatabase());
		setBounds(100, 100, 359, 603);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnReservar());
		contentPane.add(getBtnCancelar());
		contentPane.add(getBtnHorario());
		contentPane.add(getBtnListado());
		contentPane.add(getBtnPasados());
		contentPane.add(getBtnAnularAlquiler());
	}

	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Alquilar Instalación");
			btnReservar.setMnemonic('R');
			btnReservar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVistaSocio1();
				}
			});
			btnReservar.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnReservar.setBackground(new Color(65, 105, 225));
			btnReservar.setBounds(59, 241, 218, 51);
		}
		return btnReservar;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar Reserva");
			btnCancelar.setMnemonic('C');
			btnCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVistaSocio2();
				}
			});
			btnCancelar.setBackground(new Color(65, 105, 225));
			btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnCancelar.setBounds(59, 317, 218, 51);
		}
		return btnCancelar;
	}

	private void mostrarVistaSocio1() {
		VentanaReservaInstalacion vs = new VentanaReservaInstalacion(this);
		vs.setLocationRelativeTo(this);
		vs.setVisible(true);
	}

	private void mostrarVistaSocio2() {
		VentanaCancelacion vs = new VentanaCancelacion(this);
		vs.setLocationRelativeTo(this);
		vs.setVisible(true);
	}

	public VentanaInicial getVI() {
		return this.vi;
	}

	private JButton getBtnHorario() {
		if (btnHorario == null) {
			btnHorario = new JButton("Ver horario actividades");
			btnHorario.setMnemonic('V');
			btnHorario.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentnanaHorario();
				}
			});
			btnHorario.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnHorario.setBounds(59, 452, 218, 51);
			btnHorario.setBackground(new Color(65, 105, 225));
		}
		return btnHorario;
	}

	private void mostrarVentnanaHorario() {
		VentanaHorario dialog = new VentanaHorario(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	public SocioCRUD getSc() {
		return sc;
	}

	private JButton getBtnListado() {
		if (btnListado == null) {
			btnListado = new JButton("Horario Reservas y Alquileres");
			btnListado.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaListado();
				}
			});
			btnListado.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnListado.setBounds(59, 97, 218, 51);
			btnListado.setBackground(new Color(65, 105, 225));
		}
		return btnListado;
	}

	private void mostrarVentanaListado() {
		VentanaListado dialog = new VentanaListado(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private JButton getBtnPasados() {
		if (btnPasados == null) {
			btnPasados = new JButton("Alquileres Pasados");
			btnPasados.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarAlquileresPasados();
				}
			});
			btnPasados.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnPasados.setBounds(59, 170, 218, 51);
			btnPasados.setBackground(new Color(65, 105, 225));
		}
		return btnPasados;
	}

	private void mostrarAlquileresPasados() {
		VentanaAlquileresPasados va = new VentanaAlquileresPasados(this);
		va.setLocationRelativeTo(this);
		va.setModal(true);
		va.setVisible(true);
	}

	private JButton getBtnAnularAlquiler() {
		if (btnAnularAlquiler == null) {
			btnAnularAlquiler = new JButton("Anular alquileres");
			btnAnularAlquiler.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaAnularAlquileres();
				}
			});
			btnAnularAlquiler.setMnemonic('A');
			btnAnularAlquiler.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnAnularAlquiler.setBackground(new Color(65, 105, 225));
			btnAnularAlquiler.setBounds(60, 390, 218, 51);
		}
		return btnAnularAlquiler;
	}

	private void mostrarVentanaAnularAlquileres() {
		VentanaAnulacionAlquiler dialog = new VentanaAnulacionAlquiler(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

}