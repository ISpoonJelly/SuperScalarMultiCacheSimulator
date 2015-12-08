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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel cycleNo = new JLabel("Cycle No. : " + GUI.Cycles++);
		cycleNo.setForeground(Color.BLUE);
		cycleNo.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		cycleNo.setBounds(24, 20, 150, 16);
		contentPane.add(cycleNo);
		
		JLabel lblScoreboard = new JLabel("ScoreBoard");
		lblScoreboard.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblScoreboard.setForeground(Color.DARK_GRAY);
		lblScoreboard.setBounds(24, 49, 150, 16);
		contentPane.add(lblScoreboard);
		
		String [] cols = {"FU", "Busy", "Op", "Vj", "Vk", "Qj", "Qk", "Dest", "A"};
		Object [][] data = new Object[10][9];
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(24, 77, 300, 165);
		contentPane.add(scrollPane);
		tableScoreboard = new JTable(data, cols);
		scrollPane.setViewportView(tableScoreboard);
		//tableScoreboard.setValueAt("FU", 0, 0);
		tableScoreboard.setFillsViewportHeight(true);
		tableScoreboard.setForeground(Color.BLACK);
		tableScoreboard.setBackground(Color.PINK);
		tableScoreboard.setBorder(new EmptyBorder(0, 0, 0, 0));
		tableScoreboard.setColumnSelectionAllowed(true);
		tableScoreboard.setCellSelectionEnabled(true);
		
		lblNewLabel = new JLabel("ROB");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblNewLabel.setBounds(375, 49, 61, 16);
		contentPane.add(lblNewLabel);
		
		scrollPanerob = new JScrollPane();
		scrollPanerob.setBounds(375, 77, 300, 140);
		contentPane.add(scrollPanerob);
		
		String [] robCols = {"Head/Tail", "Id", "Dest", "Value", "Ready"};
		Object [][] robData = new Object[3][5];
		robTable = new JTable(robData, robCols);
		robTable.setFillsViewportHeight(true);
		robTable.setCellSelectionEnabled(true);
		robTable.setColumnSelectionAllowed(true);
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
		scrollPane_1.setViewportView(registerFileTable);
		
		btnNext = new JButton("Next Cycle");
		btnNext.setBackground(Color.BLUE);
		btnNext.setBounds(522, 425, 117, 29);
		btnNext.setActionCommand("nextCycle");
		btnNext.addActionListener(this);
		contentPane.add(btnNext);
		
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
			
			i++;
			
			
		}
	}
	
	public void updateROB() {
		//for (int i =1; i < SuperScalar.rob..length; i++) {
			
		//}
	}
	
	//TODO initialize objects with entered data
	
	public void CloseFrame() {
		super.dispose();
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("btnNext")) {
			CloseFrame();
			Output outputFrame = new Output();
			outputFrame.setVisible(true);
		}
		
	}
}
