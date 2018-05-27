import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class introUi extends JFrame {

	private JPanel contentPane;
	private JTextField txtScreenReslolution;
	private JTextField txtSaveToLoad;
	private JTextField txtSaveLocation;
	private JTextField scrnRes;
	private JTextField loadSlot;
	private JTextField saveLocation;
	public static String resolution, loadLocation, saveLocationS;
	

	/**
	 * Launch the application.
	 */
	
	/*public void introUiStart(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					introUi frame = new introUi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public introUi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtScreenReslolution = new JTextField();
		txtScreenReslolution.setBounds(12, 13, 122, 22);
		txtScreenReslolution.setText("Screen reslolution:");
		txtScreenReslolution.setEditable(false);
		contentPane.add(txtScreenReslolution);
		txtScreenReslolution.setColumns(10);
		
		txtSaveToLoad = new JTextField();
		txtSaveToLoad.setBounds(12, 48, 122, 22);
		txtSaveToLoad.setText("Save to load from:");
		txtSaveToLoad.setEditable(false);
		contentPane.add(txtSaveToLoad);
		txtSaveToLoad.setColumns(10);
		
		txtSaveLocation = new JTextField();
		txtSaveLocation.setText("Save location:");
		txtSaveLocation.setEditable(false);
		txtSaveLocation.setBounds(12, 83, 122, 22);
		contentPane.add(txtSaveLocation);
		txtSaveLocation.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resolution = scrnRes.getText();
				loadLocation = loadSlot.getText();
				saveLocationS = saveLocation.getText();
				runner.uiDone = true;
			}
		});
		btnEnter.setBounds(12, 215, 408, 25);
		contentPane.add(btnEnter);
		
		scrnRes = new JTextField();
		scrnRes.setBounds(146, 13, 274, 22);
		contentPane.add(scrnRes);
		scrnRes.setColumns(10);
		
		loadSlot = new JTextField();
		loadSlot.setBounds(146, 48, 274, 22);
		contentPane.add(loadSlot);
		loadSlot.setColumns(10);
		
		saveLocation = new JTextField();
		saveLocation.setBounds(146, 83, 274, 22);
		contentPane.add(saveLocation);
		saveLocation.setColumns(10);
	}
}
