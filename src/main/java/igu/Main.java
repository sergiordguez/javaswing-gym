package igu;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import util.Database;

public class Main {

	public static void main(String[] args) {
		FlatLightLaf.setup();
		UIManager.put("Component.hideMnemonics", false);
		Database db = new Database();
		db.createDatabase(false);
		db.loadDatabase();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentanaInicial frame = new VentanaInicial(db);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
