import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class MainMemoryData extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField addressValue;
	private JTextField dataValue;
	public int address;
	public int data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMemoryData frame = new MainMemoryData();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainMemoryData() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Address");
		lblNewLabel.setBounds(29, 119, 61, 16);
		contentPane.add(lblNewLabel);
		
		addressValue = new JTextField();
		addressValue.setBounds(85, 113, 134, 28);
		contentPane.add(addressValue);
		addressValue.setColumns(10);
		
		
		
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(245, 119, 61, 16);
		contentPane.add(lblData);
		
		dataValue = new JTextField();
		dataValue.setBounds(289, 113, 134, 28);
		contentPane.add(dataValue);
		dataValue.setColumns(10);
		
		
		JButton btnAddMore = new JButton("Add More");
		btnAddMore.setBounds(102, 185, 117, 29);
		contentPane.add(btnAddMore);
		btnAddMore.addActionListener(this);
		btnAddMore.setActionCommand("addMoreButton");
		
		JButton btnDone = new JButton("Done");
		btnDone.setBounds(257, 185, 117, 29);
		contentPane.add(btnDone);
		btnDone.addActionListener(this);
		btnDone.setActionCommand("doneButton");
		
		JLabel lblAddDataTo = new JLabel("Add Data to MainMemory");
		lblAddDataTo.setBounds(29, 40, 184, 16);
		contentPane.add(lblAddDataTo);
		
	}
	
	public void CloseFrame() {
		super.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("doneButton")) {
			if(!dataValue.getText().equals("")){
				data = Integer.parseInt(dataValue.getText());
			}
			else data = -1;
			if(!addressValue.getText().equals("")){
				address = Integer.parseInt(addressValue.getText());
				}
				else address = -1;
			if(address != -1 && data != -1){
				GUI.mainmemory.addData(address, data);
			}
			CloseFrame();
			Output output = new Output();
			output.setVisible(true);
		}
		else if (e.getActionCommand().equals("addMoreButton")) {
			if(!dataValue.getText().equals("")){
				data = Integer.parseInt(dataValue.getText());
			}
			else data = -1;
			if(!addressValue.getText().equals("")){
				address = Integer.parseInt(addressValue.getText());
				}
				else address = -1;
			if(address != -1 && data != -1){
				GUI.mainmemory.addData(address, data);
			}
			CloseFrame();
			MainMemoryData mainMemData = new MainMemoryData();
			mainMemData.setVisible(true);
		}
	
		
		
	}

}
