import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import cache.CacheHandler;

import Superscalar.SuperScalar;

import java.awt.Font;

public class Results extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDCacheAMAT;
	private JTextField textFieldICacheAMAT;
	private JTextField textFieldIPC;
	private JTextField textFieldBranchMiss;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel lblCycles;
	private JTextField textFieldCycles;
	private JTable cacheHitRates;
	private JScrollPane scrollPane_1;
	private JLabel lblFinallyWeAre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Results frame = new Results();
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
	public Results() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAmatD = new JLabel("D-cache AMAT: ");
		lblAmatD.setBounds(40, 41, 108, 16);
		contentPane.add(lblAmatD);

		textFieldDCacheAMAT = new JTextField();
		textFieldDCacheAMAT.setEnabled(false);
		textFieldDCacheAMAT.setBounds(160, 35, 88, 28);
		contentPane.add(textFieldDCacheAMAT);
		textFieldDCacheAMAT.setColumns(10);
		textFieldDCacheAMAT
				.setText(SuperScalar.cacheHandler.getDataAMAT() + "");

		JLabel lblAmatI = new JLabel("I-cache AMAT: ");
		lblAmatI.setBounds(40, 81, 97, 16);
		contentPane.add(lblAmatI);

		textFieldICacheAMAT = new JTextField();
		textFieldICacheAMAT.setEditable(false);
		textFieldICacheAMAT.setEnabled(false);
		textFieldICacheAMAT.setBounds(160, 75, 88, 28);
		contentPane.add(textFieldICacheAMAT);
		textFieldICacheAMAT.setColumns(10);
		textFieldICacheAMAT.setText(SuperScalar.cacheHandler
				.getInstructionAMAT() + "");

		JLabel lblipc = new JLabel("IPC: ");
		lblipc.setBounds(266, 41, 61, 16);
		contentPane.add(lblipc);

		textFieldIPC = new JTextField();
		textFieldIPC.setEditable(false);
		textFieldIPC.setEnabled(false);
		textFieldIPC.setBounds(410, 35, 70, 28);
		contentPane.add(textFieldIPC);
		textFieldIPC.setColumns(10);
		textFieldIPC.setText(SuperScalar.cacheHandler.getIPC() + "");

		JLabel lblbranchMiss = new JLabel("Branch Mispredictions: ");
		lblbranchMiss.setBounds(260, 87, 153, 16);
		contentPane.add(lblbranchMiss);

		textFieldBranchMiss = new JTextField();
		textFieldBranchMiss.setEditable(false);
		textFieldBranchMiss.setEnabled(false);
		double branchmiss = SuperScalar.numberOfBranches == 0 ? 0.0
				: (SuperScalar.mispredictedBranches / SuperScalar.numberOfBranches) * 100;
		textFieldBranchMiss.setText(branchmiss + "%");
		textFieldBranchMiss.setBounds(410, 81, 70, 28);
		contentPane.add(textFieldBranchMiss);
		textFieldBranchMiss.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(276, 172, -133, 16);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setForeground(Color.GRAY);
		table.setBackground(Color.GREEN);

		lblCycles = new JLabel("Cycles:");
		lblCycles.setBounds(50, 128, 61, 16);
		contentPane.add(lblCycles);

		textFieldCycles = new JTextField();
		textFieldCycles.setEditable(false);
		textFieldCycles.setEnabled(false);
		textFieldCycles.setBounds(160, 122, 88, 28);
		contentPane.add(textFieldCycles);
		textFieldCycles.setText(GUI.Cycles + "");
		textFieldCycles.setColumns(10);

		String[] cols = { "Cache No.", "D-Hit Rate", "I-Hit Rate" };
		int numOfCaches = SuperScalar.cacheHandler.getCaches().length;
		Object[][] data = new Object[numOfCaches][3];

		scrollPane_1 = new JScrollPane();
		scrollPane_1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(266, 128, 200, 99);
		contentPane.add(scrollPane_1);
		cacheHitRates = new JTable(data, cols);
		cacheHitRates.setBackground(Color.GRAY);
		scrollPane_1.setViewportView(cacheHitRates);
		for (int i = 0; i < numOfCaches; i++) {
			double DHitRate;
			int dHit = CacheHandler.getCaches()[i].getdHit();
			int dMiss = CacheHandler.getCaches()[i].getdMiss();
			if(dHit == 0 && dMiss == 0) {
				DHitRate = 100;
			} else {
				DHitRate = (dHit / (dHit + dMiss)) * 100;
			}
			int iHit = CacheHandler.getCaches()[i].getiHit();
			int iMiss = CacheHandler.getCaches()[i].getiMiss();
			
			double IHitRate;
			if(iHit == 0 && iMiss == 0) {
				IHitRate = 100;
			} else {
				IHitRate = (iHit/ (iHit + iMiss))*100;
			}
			cacheHitRates.setValueAt(i, i, 0);
			cacheHitRates.setValueAt(DHitRate + "%", i, 1);
			cacheHitRates.setValueAt(IHitRate + "%", i, 1);
		}

		lblFinallyWeAre = new JLabel("FINALLY WE ARE DONE!! ");
		lblFinallyWeAre.setFont(new Font("Lucida Bright", Font.BOLD, 16));
		lblFinallyWeAre.setForeground(Color.RED);
		lblFinallyWeAre.setBounds(21, 185, 207, 16);
		contentPane.add(lblFinallyWeAre);
	}
}
