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

import persistencia.administrador.ActividadDto;
import persistencia.socio.SocioDto;

public class VentanaContacto extends JDialog {
	private static final long serialVersionUID = 1L;
	private PanelActividad panelActividad;
	private JScrollPane scrContacto;
	private JPanel pnContacto;
	private JButton btnOK;
	private JLabel lbContacto;

	public VentanaContacto(PanelActividad panelActividad) {
		setResizable(false);
		this.panelActividad = panelActividad;
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(null);
		getContentPane().add(getScrContacto());
		getContentPane().add(getBtnOK());
		getContentPane().add(getLbContacto());
		mostrarInformacionContacto();
	}

	public VentanaContacto(PanelActividad panelActividad, ActividadDto act) {
		setResizable(false);
		this.panelActividad = panelActividad;
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(null);
		getContentPane().add(getScrContacto());
		getContentPane().add(getBtnOK());
		getContentPane().add(getLbContacto());
		mostrarInformacionContacto(act);
	}

	private void mostrarInformacionContacto(ActividadDto act) {
		for (SocioDto socio : panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador()
				.getAdministradorCRUD().obtenerSociosActividad(act)) {
			getPnContacto().add(new JLabel(String.format("%s %s - Información de contacto: %s", socio.getNombre(),
					socio.getApellido(), socio.getInfoContacto())));
		}

	}

//	private void mostrarInformacionContacto(ActividadDto act) {
//		for (SocioDto socio : panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador()
//				.getAdministradorCRUD().obtenerSociosActividad(act)) {
//			getPnContacto().add(new JLabel(String.format("%s %s - Información de contacto: %s", socio.getNombre(),
//					socio.getApellido(), socio.getInfoContacto())));
//		}
//
//	}

	private void mostrarInformacionContacto() {
		List<String> lista = panelActividad.getListaSociosApuntados();
		for (String s : lista) {
			getPnContacto().add(new JLabel(s));
		}

	}

	private JScrollPane getScrContacto() {
		if (scrContacto == null) {
			scrContacto = new JScrollPane();
			scrContacto.setBorder(null);
			scrContacto.setEnabled(false);
			scrContacto.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrContacto.setBounds(29, 57, 385, 282);
			scrContacto.setViewportView(getPnContacto());
		}
		return scrContacto;
	}

	private JPanel getPnContacto() {
		if (pnContacto == null) {
			pnContacto = new JPanel();
			pnContacto.setLayout(new GridLayout(0, 1, 0, 0));
		}
		return pnContacto;
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
			btnOK.setBounds(221, 355, 192, 35);
		}
		return btnOK;
	}

	private void cerrarVentana() {
		dispose();
	}

	private JLabel getLbContacto() {
		if (lbContacto == null) {
			lbContacto = new JLabel("Información de contacto de los socios afectados:");
			lbContacto.setHorizontalAlignment(SwingConstants.LEFT);
			lbContacto.setFont(new Font("Arial Black", Font.PLAIN, 14));
			lbContacto.setDisplayedMnemonic('P');
			lbContacto.setBounds(29, 11, 385, 35);
		}
		return lbContacto;
	}
}
