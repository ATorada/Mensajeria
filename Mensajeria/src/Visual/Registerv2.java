package Visual;

import java.awt.EventQueue;

import javax.swing.JDialog;

public class Registerv2 extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registerv2 dialog = new Registerv2();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Registerv2() {
		setBounds(100, 100, 450, 300);

	}

}
