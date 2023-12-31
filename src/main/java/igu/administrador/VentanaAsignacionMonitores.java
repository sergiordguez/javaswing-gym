package igu.administrador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import persistencia.administrador.ActividadDto;

public class VentanaAsignacionMonitores extends JDialog {
	private static final long serialVersionUID = 1L;
	private JScrollPane scrActividades;
	private JPanel pnActividades;
	private JTextField tFMonitor;
	private JLabel lbTFMonitor;
	private JLabel lbAvisoMonitor;
	private VentanaAdministrador va;
	private Timestamp fechaInicio;
	private int indice;
	private JLabel lbBtnCrearActividad;
	private JButton btnCrearActividad;

	public VentanaAsignacionMonitores(VentanaAdministrador ventanaAdministrador, Timestamp fechaInicio, int indice) {
		setResizable(false);
		this.va = ventanaAdministrador;
		this.fechaInicio = fechaInicio;
		this.indice = indice;
		this.setBounds(100, 100, 640, 640);
		getContentPane().setLayout(null);
		getContentPane().add(getScrActividades());
		getContentPane().add(getTFMonitor());
		getContentPane().add(getLbTFMonitor());
		getContentPane().add(getLbAvisoMonitor());
		getContentPane().add(getLbBtnCrearActividad());
		getContentPane().add(getBtnCrearActividad());
		repintaPanelesActividad();
	}

	private void creaPanelesActividad() {
		getVentanaAdministrador().repintarTabla();
		List<ActividadDto> listaActividades = getVentanaAdministrador().obtenerListaActividadesParaIndice(indice);

		PanelActividad pn;
		for (ActividadDto listaActividade : listaActividades) {
			pn = new PanelActividad(this, listaActividade);
			pn.setPreferredSize(new Dimension(getScrActividades().getX(), 200));
			getPnActividades().add(pn);
		}

		if (getPnActividades().getComponentCount() == 1) {
			getPnActividades().add(new JPanel());
		}
	}

	public void repintaPanelesActividad() {
		getPnActividades().removeAll();

		creaPanelesActividad();

		repaint();
		validate();
		scrollToTheTop();
	}

	private void scrollToTheTop() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				getScrActividades().getViewport().setViewPosition(new Point(0, 0));
			}
		});
	}

	private JScrollPane getScrActividades() {
		if (scrActividades == null) {
			scrActividades = new JScrollPane();
			scrActividades.setBorder(null);
			scrActividades.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrActividades.setBounds(37, 92, 550, 418);
			scrActividades.setViewportView(getPnActividades());
		}
		return scrActividades;
	}

	private JPanel getPnActividades() {
		if (pnActividades == null) {
			pnActividades = new JPanel();
			pnActividades.setBorder(null);
			pnActividades.setLayout(new GridLayout(0, 1, 0, 0));
		}
		return pnActividades;
	}

	private JTextField getTFMonitor() {
		if (tFMonitor == null) {
			tFMonitor = new JTextField();
			tFMonitor.setColumns(10);
			tFMonitor.setBounds(457, 13, 130, 35);
		}
		return tFMonitor;
	}

	private JLabel getLbTFMonitor() {
		if (lbTFMonitor == null) {
			lbTFMonitor = new JLabel("Por favor, introduzca el identificador del monitor:");
			lbTFMonitor.setDisplayedMnemonic('P');
			lbTFMonitor.setHorizontalAlignment(SwingConstants.LEFT);
			lbTFMonitor.setLabelFor(getTFMonitor());
			lbTFMonitor.setFont(new Font("Arial Black", Font.PLAIN, 14));
			lbTFMonitor.setBounds(37, 11, 378, 35);
		}
		return lbTFMonitor;
	}

	private JLabel getLbAvisoMonitor() {
		if (lbAvisoMonitor == null) {
			lbAvisoMonitor = new JLabel((String) null);
			lbAvisoMonitor.setForeground(Color.RED);
			lbAvisoMonitor.setFont(new Font("Arial", Font.ITALIC, 12));
			lbAvisoMonitor.setBounds(37, 57, 550, 25);
		}
		return lbAvisoMonitor;
	}

	public void validarMonitor(PanelActividad panelActividad) {
		String idMonitor = getTFMonitor().getText();
		if (idMonitor.isEmpty()) {
			getLbAvisoMonitor().setText("Por favor, introduzca un identificador");
		} else if (!va.getAdministradorCRUD().existeMonitor(idMonitor)) {
			getLbAvisoMonitor().setText("No existe ningún monitor con ese identificador");
		} else if (va.getAdministradorCRUD().estaAsignado(idMonitor, panelActividad.getActividadDto().getFechaInicio(),
				panelActividad.getActividadDto().getFechaFin())) {
			getLbAvisoMonitor().setText("Dicho monitor ya está asignado a una actividad en ese momento");
		} else {
			va.getAdministradorCRUD().asignarMonitor(idMonitor, panelActividad.getActividadDto());
			va.repintarTabla();
			repintaPanelesActividad();
		}
	}

	public VentanaAdministrador getVentanaAdministrador() {
		return va;
	}

	private JLabel getLbBtnCrearActividad() {
		if (lbBtnCrearActividad == null) {
			lbBtnCrearActividad = new JLabel("De lo contrario, si desea crear una actividad:");
			lbBtnCrearActividad.setHorizontalAlignment(SwingConstants.LEFT);
			lbBtnCrearActividad.setFont(new Font("Arial Black", Font.PLAIN, 14));
			lbBtnCrearActividad.setBounds(37, 521, 370, 35);
		}
		return lbBtnCrearActividad;
	}

	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("Crear actividad");
			btnCrearActividad.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					va.mostrarVentanaCreacionActividades(fechaInicio);
					dispose();
				}
			});
			btnCrearActividad.setMnemonic('C');
			btnCrearActividad.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
			btnCrearActividad.setBounds(457, 521, 130, 35);
		}
		return btnCrearActividad;
	}

}