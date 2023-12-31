package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import igu.administrador.VentanaAdministrador;
import igu.monitor.VistaMonitor;
import igu.socio.VentanaSocio;
import persistencia.administrador.AdministradorCRUD;
import persistencia.administrador.AdministradorDto;
import persistencia.administrador.MonitorDto;
import persistencia.monitor.MonitorCRUD;
import persistencia.socio.SocioCRUD;
import persistencia.socio.SocioDto;
import util.Database;

public class VentanaInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	private Database db;
	private JPanel contentPane;
	private JPanel pn1;
	private JButton btnInicio;
	private JLabel lbCBSocio;
	private JComboBox<SocioDto> cBSocio;
	private JButton btnSocio;
	private JLabel lbCBAdministrador;
	private JComboBox<AdministradorDto> cBAdministrador;
	private JButton btnAdminstrador;
	private JLabel lbCBMonitor;
	private JComboBox<MonitorDto> cBMonitor;
	private JButton btnMonitor;

	public VentanaInicial(Database db) {
		this.db = db;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPn1(), BorderLayout.CENTER);
		setResizable(false);
	}

	public Database getDatabase() {
		return db;
	}

	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			pn1.setLayout(null);
			pn1.add(getBtnInicio());
			pn1.add(getLbCBSocio());
			pn1.add(getCBSocio());
			pn1.add(getBtnSocio());
			pn1.add(getLbCBAdministrador());
			pn1.add(getCBAdministrador());
			pn1.add(getBtnAdminstrador());
			pn1.add(getLbCBMonitor());
			pn1.add(getCBMonitor());
			pn1.add(getBtnMonitor());
		}
		return pn1;
	}

	private JButton getBtnInicio() {
		if (btnInicio == null) {
			btnInicio = new JButton("Continuar");
			btnInicio.setMnemonic('C');
			btnInicio.setForeground(Color.BLACK);
			btnInicio.setFont(new Font("Arial", Font.BOLD, 13));
			btnInicio.setBackground(new Color(173, 255, 47));
			btnInicio.setBounds(267, 260, 177, 60);
		}
		return btnInicio;
	}

	private JLabel getLbCBSocio() {
		if (lbCBSocio == null) {
			lbCBSocio = new JLabel("Socio:");
			lbCBSocio.setLabelFor(getCBSocio());
			lbCBSocio.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBSocio.setDisplayedMnemonic('S');
			lbCBSocio.setBounds(24, 14, 175, 25);
		}
		return lbCBSocio;
	}

	private JComboBox<SocioDto> getCBSocio() {
		if (cBSocio == null) {
			cBSocio = new JComboBox<>();
			cBSocio.setModel(new DefaultComboBoxModel<>(new SocioCRUD(db).getSocios()));
			cBSocio.setBounds(24, 53, 175, 25);
		}
		return cBSocio;
	}

	private JButton getBtnSocio() {
		if (btnSocio == null) {
			btnSocio = new JButton("Iniciar sesión");
			btnSocio.setBackground(new Color(65, 105, 225));
			btnSocio.setFont(new Font("Arial", Font.BOLD, 13));
			btnSocio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaSocio();
				}
			});
			btnSocio.setBounds(223, 53, 175, 25);
		}
		return btnSocio;
	}

	private JLabel getLbCBAdministrador() {
		if (lbCBAdministrador == null) {
			lbCBAdministrador = new JLabel("Administrador:");
			lbCBAdministrador.setLabelFor(getCBAdministrador());
			lbCBAdministrador.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBAdministrador.setDisplayedMnemonic('A');
			lbCBAdministrador.setBounds(24, 92, 175, 25);
		}
		return lbCBAdministrador;
	}

	private JComboBox<AdministradorDto> getCBAdministrador() {
		if (cBAdministrador == null) {
			cBAdministrador = new JComboBox<>();
			cBAdministrador.setModel(new DefaultComboBoxModel<>(new AdministradorCRUD(db).listarAdministradores()));
			cBAdministrador.setBounds(24, 131, 175, 25);
		}
		return cBAdministrador;
	}

	private JButton getBtnAdminstrador() {
		if (btnAdminstrador == null) {
			btnAdminstrador = new JButton("Iniciar sesión");
			btnAdminstrador.setBackground(new Color(65, 105, 225));
			btnAdminstrador.setFont(new Font("Arial", Font.BOLD, 13));
			btnAdminstrador.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaAdministrador();
				}
			});
			btnAdminstrador.setBounds(223, 131, 175, 25);
		}
		return btnAdminstrador;
	}

	private void mostrarVentanaAdministrador() {
		VentanaAdministrador va = new VentanaAdministrador(this);
		va.setLocationRelativeTo(this);
		va.setVisible(true);
		this.dispose();
	}

	private void mostrarVentanaSocio() {
		VentanaSocio vs = new VentanaSocio(this);
		vs.setLocationRelativeTo(this);
		vs.setVisible(true);
		this.dispose();
	}

	private void mostrarVentanaMonitor() {
		VistaMonitor vm = new VistaMonitor(this);
		vm.setLocationRelativeTo(this);
		vm.setVisible(true);
		this.dispose();
	}

	private JLabel getLbCBMonitor() {
		if (lbCBMonitor == null) {
			lbCBMonitor = new JLabel("Monitor:");
			lbCBMonitor.setLabelFor(getCBMonitor());
			lbCBMonitor.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBMonitor.setDisplayedMnemonic('M');
			lbCBMonitor.setBounds(24, 170, 175, 25);
		}
		return lbCBMonitor;
	}

	private JComboBox<MonitorDto> getCBMonitor() {
		if (cBMonitor == null) {
			cBMonitor = new JComboBox<>();
			cBMonitor.setModel(new DefaultComboBoxModel<>(new MonitorCRUD(db).getMonitores()));
			cBMonitor.setBounds(24, 209, 175, 25);
		}
		return cBMonitor;
	}

	private JButton getBtnMonitor() {
		if (btnMonitor == null) {
			btnMonitor = new JButton("Iniciar sesión");
			btnMonitor.setBackground(new Color(65, 105, 225));
			btnMonitor.setFont(new Font("Arial", Font.BOLD, 13));
			btnMonitor.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaMonitor();
				}
			});
			btnMonitor.setBounds(223, 209, 175, 25);
		}
		return btnMonitor;
	}

	public MonitorDto getMonitor() {
		return (MonitorDto) cBMonitor.getSelectedItem();
	}

	public SocioDto getSocio() {
		return (SocioDto) cBSocio.getSelectedItem();
	}
}
