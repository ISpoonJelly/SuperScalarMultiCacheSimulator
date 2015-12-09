import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import Superscalar.ROBEntry;
import Superscalar.ScoreBoard;
import Superscalar.ScoreBoardEntry;
import Superscalar.SuperScalar;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;

public class Output extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable tableScoreboard;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JTable robTable;
	private JScrollPane scrollPanerob;
	private JLabel lblRegisterStatus;
	private JTable registerStatusTable;
	private JScrollPane scrollPaneRegisterStatus;
	private JLabel lblRegisterFile;
	private JTable registerFileTable;
	private JScrollPane scrollPane_1;
	private JButton btnNext; 
	private JLabel cycleNo;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Output frame = new Output();
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
	public Output() {
		// Simulating Brain
		
		
		// GUI stuff
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cycleNo = new JLabel("Cycle No. : " + GUI.Cycles++);
		cycleNo.setForeground(Color.BLUE);
		cycleNo.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cycleNo.setBounds(24, 20, 150, 16);
		contentPane.add(cycleNo);
		
		JLabel lblScoreboard = new JLabel("ScoreBoard");
		lblScoreboard.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblScoreboard.setForeground(Color.DARK_GRAY);
		lblScoreboard.setBounds(24, 49, 200, 16);
		contentPane.add(lblScoreboard);
		
		String [] cols = {"FU", "Busy", "Op", "Vj", "Vk", "Qj", "Qk", "Dest", "A"};
		int totalFU = GUI.ADD_FU + GUI.JUMP_FU + GUI.LOAD_FU + GUI.STORE_FU + GUI.RETURN_FU + GUI.MULT_FU + GUI.NAND_FU;
		Object [][] data = new Object[totalFU][9];
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(24, 77, 400, 165);
		contentPane.add(scrollPane);
		tableScoreboard = new JTable(data, cols);
		scrollPane.setViewportView(tableScoreboard);
		//tableScoreboard.setValueAt("FU", 0, 0);
		tableScoreboard.setFillsViewportHeight(true);
		tableScoreboard.setForeground(Color.BLACK);
		tableScoreboard.setBackground(Color.PINK);
		tableScoreboard.setBorder(new EmptyBorder(0, 0, 0, 0));
		tableScoreboard.setColumnSelectionAllowed(false);
		tableScoreboard.setCellSelectionEnabled(false);
		tableScoreboard.setEnabled(false);
		
		lblNewLabel = new JLabel("ROB");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblNewLabel.setBounds(450, 49, 61, 16);
		contentPane.add(lblNewLabel);
		
		scrollPanerob = new JScrollPane();
		scrollPanerob.setBounds(450, 77, 400, 140);
		scrollPanerob.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPanerob);
		
		String [] robCols = {"Head/Tail", "Id", "Dest", "Value", "Ready"};
		Object [][] robData = new Object[GUI.ROB_SIZE][5];
		robTable = new JTable(robData, robCols);
		robTable.setFillsViewportHeight(true);
		robTable.setCellSelectionEnabled(false);
		robTable.setColumnSelectionAllowed(false);
		robTable.setEnabled(false);
		robTable.setBackground(Color.LIGHT_GRAY);
		scrollPanerob.setViewportView(robTable);
		
		lblRegisterStatus = new JLabel("Register Status");
		lblRegisterStatus.setBounds(24, 268, 100, 16);
		contentPane.add(lblRegisterStatus);
		
		scrollPaneRegisterStatus = new JScrollPane();
		scrollPaneRegisterStatus.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPaneRegisterStatus.setBounds(24, 296, 500, 35);
		contentPane.add(scrollPaneRegisterStatus);
		
		String [] registerStatusCols = {"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7"};
		Object [][] registerStatusData = new Object[1][8];
		registerStatusTable = new JTable(registerStatusData, registerStatusCols);
		registerStatusTable.setBackground(Color.ORANGE);
		registerStatusTable.setEnabled(false);
		registerStatusTable.setCellSelectionEnabled(false);
		registerStatusTable.setColumnSelectionAllowed(false);
		scrollPaneRegisterStatus.setViewportView(registerStatusTable);
		
		lblRegisterFile = new JLabel("Register File");
		lblRegisterFile.setBounds(24, 343, 100, 16);
		contentPane.add(lblRegisterFile);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(24, 372, 500, 35);
		contentPane.add(scrollPane_1);
		
		
		String [] registerFileCols = {"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7"};
		Object [][] registerFileData = new Object[1][8];
		registerFileTable = new JTable(registerFileData, registerFileCols);
		registerFileTable.setBackground(Color.GREEN);
		registerFileTable.setEnabled(false);
		registerFileTable.setCellSelectionEnabled(false);
		registerFileTable.setColumnSelectionAllowed(false);
		scrollPane_1.setViewportView(registerFileTable);
		
		btnNext = new JButton("Next Cycle");
		btnNext.setBackground(Color.BLUE);
		btnNext.setBounds(522, 425, 117, 29);
		btnNext.setActionCommand("nextCycle");
		btnNext.addActionListener(this);
		contentPane.add(btnNext);
		
		if (GUI.brain.simulate()) {
			updateROB();
			updateScoreBoard();
			updateRegisterStatus();
			updateRegisterFile();
		}
		
	}
	
	public void updateScoreBoard() {
		ScoreBoard sb = SuperScalar.scoreboard;
		Iterator<Map.Entry<String, ScoreBoardEntry>> iterator = sb.getScoreBoard().entrySet().iterator();
		int i = 0;

		while (iterator.hasNext()) {
			Map.Entry<String, ScoreBoardEntry> entry = (Map.Entry<String, ScoreBoardEntry>) iterator
					.next();
			ScoreBoardEntry entryData = entry.getValue();
			tableScoreboard.setValueAt(entry.getKey(), i, 0);
			tableScoreboard.setValueAt(entryData.isBusy(), i, 1);
			if (entryData.isBusy()) {
				tableScoreboard.setValueAt(entryData.getOperationName(), i, 2);
				tableScoreboard.setValueAt(entryData.getVj(), i, 3);
				tableScoreboard.setValueAt(entryData.getVk(), i, 4);
				tableScoreboard.setValueAt(entryData.getQj(), i, 5);
				tableScoreboard.setValueAt(entryData.getQk(), i, 6);
				tableScoreboard.setValueAt(entryData.getDestination(), i, 7);
				tableScoreboard.setValueAt(entryData.getA(), i, 8);

			}
			else {
				tableScoreboard.setValueAt("", i, 2);
				tableScoreboard.setValueAt("", i, 3);
				tableScoreboard.setValueAt("", i, 4);
				tableScoreboard.setValueAt("", i, 5);
				tableScoreboard.setValueAt("", i, 6);
				tableScoreboard.setValueAt("", i, 7);
				tableScoreboard.setValueAt("", i, 8);
				
			}
			
			i++;
			
			
		}
	}
	
	public void updateROB() {
		ROBEntry[] robEntries = SuperScalar.rob.getROBEntry();
		for (int i =1; i < robEntries.length; i++) {
			if (SuperScalar.rob.getHead() == i) {
				if (SuperScalar.rob.getTail() == i)
					robTable.setValueAt("Head/Tail", i - 1, 0);
				else {
					robTable.setValueAt("Head", i - 1, 0);
				}
			} else if (SuperScalar.rob.getTail() == i) 
						robTable.setValueAt("Tail", i - 1, 0);
			
			robTable.setValueAt(i, i - 1, 1);
			if(robEntries[i] == null){
				robTable.setValueAt("", i - 1, 2);
				robTable.setValueAt("", i - 1, 3);
				robTable.setValueAt("", i - 1, 4);
			}
			else{
				robTable.setValueAt(robEntries[i].getDest(), i - 1, 2);
				robTable.setValueAt(robEntries[i].getValue(), i - 1, 3);
				robTable.setValueAt(robEntries[i].isReady(), i - 1, 4);
			}

		}
	}
	
	public void updateRegisterStatus() {
		/*Iterator<Map.Entry<String, Integer>> iterator = SuperScalar.registerStatus.getRegStatus()
				.entrySet().iterator();

		int i = 0;
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
			registerStatusTable.setValueAt(entry.getValue(), 0 , i++);
		}*/
		registerStatusTable.setValueAt(SuperScalar.registerStatus.getRegStatus().get("R0"), 0, 0);
		registerStatusTable.setValueAt(SuperScalar.registerStatus.getRegStatus().get("R1"), 0, 1);
		registerStatusTable.setValueAt(SuperScalar.registerStatus.getRegStatus().get("R2"), 0, 2);
		registerStatusTable.setValueAt(SuperScalar.registerStatus.getRegStatus().get("R3"), 0, 3);
		registerStatusTable.setValueAt(SuperScalar.registerStatus.getRegStatus().get("R4"), 0, 4);
		registerStatusTable.setValueAt(SuperScalar.registerStatus.getRegStatus().get("R5"), 0, 5);
		registerStatusTable.setValueAt(SuperScalar.registerStatus.getRegStatus().get("R6"), 0, 6);
		registerStatusTable.setValueAt(SuperScalar.registerStatus.getRegStatus().get("R7"), 0, 7);
	}
	
	public void updateRegisterFile() {
		/*Iterator<Map.Entry<String, Integer>> iterator = SuperScalar.registerFile.getRegisterFile()
				.entrySet().iterator();

		int i = 0;
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
			registerFileTable.setValueAt(entry.getValue(), 0 , i++);
		}*/
		
		registerFileTable.setValueAt(SuperScalar.registerFile.getRegisterFile().get("R0"), 0, 0);
		registerFileTable.setValueAt(SuperScalar.registerFile.getRegisterFile().get("R1"), 0, 1);
		registerFileTable.setValueAt(SuperScalar.registerFile.getRegisterFile().get("R2"), 0, 2);
		registerFileTable.setValueAt(SuperScalar.registerFile.getRegisterFile().get("R3"), 0, 3);
		registerFileTable.setValueAt(SuperScalar.registerFile.getRegisterFile().get("R4"), 0, 4);
		registerFileTable.setValueAt(SuperScalar.registerFile.getRegisterFile().get("R5"), 0, 5);
		registerFileTable.setValueAt(SuperScalar.registerFile.getRegisterFile().get("R6"), 0, 6);
		registerFileTable.setValueAt(SuperScalar.registerFile.getRegisterFile().get("R7"), 0, 7);
	}
	
	//TODO initialize objects with entered data
	
	public void CloseFrame() {
		super.dispose();
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("nextCycle")) {
			boolean ch = GUI.brain.simulate();
			System.out.println(ch);
			if (ch) {
				System.out.println("UPDATED");
				updateROB();
				updateScoreBoard();
				updateRegisterStatus();
				updateRegisterFile();
				cycleNo.setText("Cycle No. : "  + GUI.Cycles++ + "");
				
			}
			else {
				CloseFrame();
				Results results = new Results();
				results.setVisible(true);
			}
			
			//CloseFrame();
			//Output outputFrame = new Output();
			//outputFrame.setVisible(true);
		}
		
	}
}
