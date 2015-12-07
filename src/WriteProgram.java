import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextArea;

public class WriteProgram extends JFrame implements ActionListener{

	private JPanel contentPane;
	String instr;
	private JTextArea program;
	private JTextField org;
	int orgValue;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WriteProgram frame = new WriteProgram();
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
	public WriteProgram() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWriteYourProgram = new JLabel("Write Your Program:");
		lblWriteYourProgram.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblWriteYourProgram.setBounds(25, 16, 200, 30);
		contentPane.add(lblWriteYourProgram);
		
		JButton submit = new JButton("Submit");
		submit.setBackground(Color.RED);
		submit.setBounds(133, 231, 117, 29);
		contentPane.add(submit);
		submit.setActionCommand("submitBtn");
		submit.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 81, 346, 146);
		contentPane.add(scrollPane);
		
		 program = new JTextArea();
		scrollPane.setViewportView(program);
		
		JLabel lblOrg = new JLabel("ORG");
		lblOrg.setBounds(35, 48, 61, 16);
		contentPane.add(lblOrg);
		
		org = new JTextField();
		org.setBounds(77, 42, 50, 28);
		contentPane.add(org);
		
		org.setColumns(10);
		
		
		
	}
	
	public void CloseFrame() {
		super.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("submitBtn")) {
			instr = program.getText();
			orgValue = Integer.parseInt(org.getText());
			String [] instructions = instr.split("\n");
			GUI.ORG = orgValue;
			GUI.mainmemory.addInstruction(GUI.ORG, instructions);
			CloseFrame();
			MainMemoryData memMemoryData = new MainMemoryData();
			memMemoryData.setVisible(true);
		}
		
	}
}
