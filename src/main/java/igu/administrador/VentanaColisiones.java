package igu.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import persistencia.administrador.Colisionable;

public class VentanaColisiones extends JDialog {
	private static final long serialVersionUID = 1L;
	private VentanaCreacionActividadesVariosDias vcavd;
	private List<Colisionable> colisiones;
	private JScrollPane scrColisiones;
	private JPanel pnColisiones;
	private JButton btnOK;
	private JLabel lbColisiones;

	public VentanaColisiones(VentanaCreacionActividadesVariosDias vcavd, List<Colisionable> colisiones) {
		setResizable(false);
		this.vcavd = vcavd;
		this.colisiones = colisiones;
		setBounds(100, 100, 645, 450);
		getContentPane().setLayout(null);
		getContentPane().add(getScrColisiones());
		getContentPane().add(getBtnOK());
		getContentPane().add(getLbColisiones());
		mostrarInformacionColisiones();
	}

	private void mostrarInformacionColisiones() {
		for (Colisionable colision : colisiones) {
			getPnColisiones().add(new JLabel(colision.ToStringColision()));
		}

	}

	private JScrollPane getScrColisiones() {
		if (scrColisiones == null) {
			scrColisiones = new JScrollPane();
			scrColisiones.setBorder(null);
			scrColisiones.setEnabled(false);
			scrColisiones.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrColisiones.setBounds(30, 57, 560, 282);
			scrColisiones.setViewportView(getPnColisiones());
		}
		return scrColisiones;
	}

	private JPanel getPnColisiones() {
		if (pnColisiones == null) {
			pnColisiones = new JPanel();
			pnColisiones.setLayout(new GridLayout(0, 1, 0, 0));
		}
		return pnColisiones;
	}

	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton("OK");
			btnOK.setMnemonic('O');
			btnOK.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cerrarVentana();
				}
			});
			btnOK.setForeground(Color.BLACK);
			btnOK.setFont(new Font("Arial", Font.BOLD, 13));
			btnOK.setBackground(new Color(173, 255, 47));
			btnOK.setBounds(398, 355, 192, 35);
		}
		return btnOK;
	}

	private void cerrarVentana() {
		dispose();
		vcavd.dispose();
	}

	private JLabel getLbColisiones() {
		if (lbColisiones == null) {
			lbColisiones = new JLabel("No se ha podido planificar la actividad debido a las siguientes colisiones:");
			lbColisiones.setHorizontalAlignment(SwingConstants.LEFT);
			lbColisiones.setFont(new Font("Arial Black", Font.PLAIN, 14));
			lbColisiones.setBounds(29, 11, 580, 35);
		}
		return lbColisiones;
	}
}
