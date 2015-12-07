import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Superscalar.RegisterFile;
import cache.Cache;
import cache.CacheHandler;
import mainMemory.MainMemory;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JButton;


public class GUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField r0;
	private JTextField r1;
	private JLabel lblR_2;
	private JTextField r2;
	private JLabel lblR_3;
	private JTextField r3;
	private JLabel lblR_4;
	private JTextField r4;
	private JLabel lblR_5;
	private JTextField r5;
	private JLabel lblR_6;
	private JTextField r6;
	private JLabel lblR_7;
	private JTextField r7;
	private JLabel lblRegisters;
	private JLabel lblExecuteCycles;
	private JLabel lblLoad;
	private JTextField load;
	private JLabel lblStore;
	private JTextField store;
	private JLabel lblJump;
	private JTextField jump;
	private JLabel lblRet;
	private JTextField ret;
	private JLabel lblAdd;
	private JTextField add;
	private JLabel lblNand;
	private JTextField nand;
	private JLabel lblMuliply;
	private JTextField mult;
	private JLabel lblNOfSuperscalar;
	private JTextField nSuperscalar;
	private JLabel lblCacheSize;
	private JTextField blockSize;
	private JLabel lblROBNo;
	private JTextField robNo;
	public static int noCache = 0;
	public static int Cycles = 1;
	public static RegisterFile registerFile = new RegisterFile();
	public static int superNumber;
	public static int LOAD, STORE, ADD, MULT, JUMP, NAND, RET;
	public static int BLOCK_SIZE, ROB_SIZE, MEM_ACCESS_TIME;
	public static MainMemory mainmemory;
	public static ArrayList<Cache> caches = new ArrayList<Cache>();
	public static CacheHandler cHanlder;
	private JLabel lblMemoryAccessTime;
	private JTextField memoryAccessTime;

	public static int ORG;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblR = new JLabel("R0");
		lblR.setBounds(6, 35, 61, 16);
		contentPane.add(lblR);
		
		r0 = new JTextField();
		r0.setBounds(32, 29, 50, 28);
		contentPane.add(r0);
		r0.setColumns(5);
		
		JLabel lblR_1 = new JLabel("R1");
		lblR_1.setBounds(6, 63, 61, 16);
		contentPane.add(lblR_1);
		
		r1 = new JTextField();
		r1.setBounds(32, 57, 50, 28);
		contentPane.add(r1);
		r1.setColumns(10);
		
		lblR_2 = new JLabel("R2");
		lblR_2.setBounds(6, 91, 61, 16);
		contentPane.add(lblR_2);
		
		r2 = new JTextField();
		r2.setBounds(32, 84, 50, 28);
		contentPane.add(r2);
		r2.setColumns(10);
		
		lblR_3 = new JLabel("R3");
		lblR_3.setBounds(6, 119, 61, 16);
		contentPane.add(lblR_3);
		
		r3 = new JTextField();
		r3.setBounds(32, 113, 50, 28);
		contentPane.add(r3);
		r3.setColumns(10);
		
		lblR_4 = new JLabel("R4");
		lblR_4.setBounds(6, 147, 61, 16);
		contentPane.add(lblR_4);
		
		r4 = new JTextField();
		r4.setBounds(32, 141, 50, 28);
		contentPane.add(r4);
		r4.setColumns(10);
		
		lblR_5 = new JLabel("R5");
		lblR_5.setBounds(6, 175, 61, 16);
		contentPane.add(lblR_5);
		
		r5 = new JTextField();
		r5.setBounds(32, 169, 50, 28);
		contentPane.add(r5);
		r5.setColumns(10);
		
		lblR_6 = new JLabel("R6");
		lblR_6.setBounds(6, 203, 61, 16);
		contentPane.add(lblR_6);
		
		r6 = new JTextField();
		r6.setBounds(32, 197, 50, 28);
		contentPane.add(r6);
		r6.setColumns(10);
		
		lblR_7 = new JLabel("R7");
		lblR_7.setBounds(6, 230, 61, 16);
		contentPane.add(lblR_7);
		
		r7 = new JTextField();
		r7.setBounds(32, 224, 50, 28);
		contentPane.add(r7);
		r7.setColumns(10);
		
		lblRegisters = new JLabel("Registers:");
		lblRegisters.setBounds(6, 6, 70, 16);
		contentPane.add(lblRegisters);
		
		lblExecuteCycles = new JLabel("Execute Cycles:");
		lblExecuteCycles.setBounds(131, 6, 100, 16);
		contentPane.add(lblExecuteCycles);
		
		lblLoad = new JLabel("Load");
		lblLoad.setBounds(131, 35, 61, 16);
		contentPane.add(lblLoad);
		
		load = new JTextField();
		load.setBounds(169, 29, 50, 28);
		contentPane.add(load);
		load.setColumns(10);
		
		lblStore = new JLabel("Store");
		lblStore.setBounds(131, 63, 61, 16);
		contentPane.add(lblStore);
		
		store = new JTextField();
		store.setBounds(169, 57, 50, 28);
		contentPane.add(store);
		store.setColumns(10);
		
		lblJump = new JLabel("Jump");
		lblJump.setBounds(131, 91, 61, 16);
		contentPane.add(lblJump);
		
		jump = new JTextField();
		jump.setBounds(169, 85, 50, 28);
		contentPane.add(jump);
		jump.setColumns(10);
		
		lblRet = new JLabel("Ret");
		lblRet.setBounds(131, 119, 61, 16);
		contentPane.add(lblRet);
		
		ret = new JTextField();
		ret.setBounds(169, 113, 50, 28);
		contentPane.add(ret);
		ret.setColumns(10);
		
		lblAdd = new JLabel("Add");
		lblAdd.setBounds(131, 147, 61, 16);
		contentPane.add(lblAdd);
		
		add = new JTextField();
		add.setBounds(169, 141, 50, 28);
		contentPane.add(add);
		add.setColumns(10);
		
		lblNand = new JLabel("Nand");
		lblNand.setBounds(131, 175, 61, 16);
		contentPane.add(lblNand);
		
		nand = new JTextField();
		nand.setBounds(169, 169, 50, 28);
		contentPane.add(nand);
		nand.setColumns(10);
		
		lblMuliply = new JLabel("Mult");
		lblMuliply.setBounds(131, 203, 61, 16);
		contentPane.add(lblMuliply);
		
		mult = new JTextField();
		mult.setBounds(169, 197, 50, 28);
		contentPane.add(mult);
		mult.setColumns(10);
		
		lblNOfSuperscalar = new JLabel("No. of Superscalar:");
		lblNOfSuperscalar.setBounds(231, 91, 120, 16);
		contentPane.add(lblNOfSuperscalar);
		
		nSuperscalar = new JTextField();
		nSuperscalar.setBounds(354, 85, 50, 28);
		contentPane.add(nSuperscalar);
		nSuperscalar.setColumns(10);
		
		lblCacheSize = new JLabel("Block Size:");
		lblCacheSize.setBounds(281, 35, 80, 16);
		contentPane.add(lblCacheSize);
		
		blockSize = new JTextField();
		blockSize.setBounds(354, 29, 50, 28);
		contentPane.add(blockSize);
		blockSize.setColumns(10);
		
		lblROBNo = new JLabel("No. of ROB Entry:");
		lblROBNo.setBounds(242, 63, 150, 16);
		contentPane.add(lblROBNo);
		
		robNo = new JTextField();
		robNo.setBounds(354, 57, 50, 28);
		contentPane.add(robNo);
		robNo.setColumns(10);
		
		JButton btnHandleCaches = new JButton("Handle Caches");
		btnHandleCaches.setBackground(Color.ORANGE);
		btnHandleCaches.setBounds(269, 225, 140, 29);
		contentPane.add(btnHandleCaches);
		btnHandleCaches.addActionListener(this);
		btnHandleCaches.setActionCommand("btnHandleCaches");
		
		lblMemoryAccessTime = new JLabel("Memory Hit Time:");
		lblMemoryAccessTime.setBounds(231, 119, 120, 16);
		contentPane.add(lblMemoryAccessTime);
		
		memoryAccessTime = new JTextField();
		memoryAccessTime.setBounds(354, 113, 50, 28);
		contentPane.add(memoryAccessTime);
		memoryAccessTime.setColumns(10);
	}
	public void CloseFrame() {
		super.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		 
		
		if (e.getActionCommand().equals("btnHandleCaches")) {
			
			// Registers
			int r0Value = Integer.parseInt(r0.getText());
			int r1Value = Integer.parseInt(r1.getText()); 
			int r2Value = Integer.parseInt(r2.getText()); 
			int r3Value = Integer.parseInt(r3.getText()); 
			int r4Value = Integer.parseInt(r4.getText()); 
			int r5Value = Integer.parseInt(r5.getText()); 
			int r6Value = Integer.parseInt(r6.getText()); 
			int r7Value = Integer.parseInt(r7.getText()); 
			
			registerFile.setRegister("R0", r0Value);
			registerFile.setRegister("R1", r1Value);
			registerFile.setRegister("R2", r2Value);
			registerFile.setRegister("R3", r3Value);
			registerFile.setRegister("R4", r4Value);
			registerFile.setRegister("R5", r5Value);
			registerFile.setRegister("R6", r6Value);
			registerFile.setRegister("R7", r7Value);
			
			System.out.println("R0" + r0.getText() + "R1" + r1Value);
			
			// Execute cycles
			
			int loadValue = Integer.parseInt(load.getText());
			int storeValue = Integer.parseInt(store.getText());
			int jumpValue = Integer.parseInt(jump.getText());
			int retValue = Integer.parseInt(ret.getText());
			int addValue = Integer.parseInt(add.getText());
			int nandValue = Integer.parseInt(nand.getText());
			int multiplyValue = Integer.parseInt(mult.getText());
			
			LOAD = loadValue;
			STORE = storeValue;
			JUMP = jumpValue;
			RET = retValue;
			ADD = addValue;
			NAND = nandValue;
			MULT = multiplyValue;
		}
		
		// no. of superscalar
		int nSuperscalarValue = Integer.parseInt(nSuperscalar.getText());
		superNumber = nSuperscalarValue;
		
		// Cache 
		int blockSizeValue = Integer.parseInt(blockSize.getText());
		int robNoValue = Integer.parseInt(robNo.getText());
		int memAccessTime = Integer.parseInt(memoryAccessTime.getText());
		
		BLOCK_SIZE = blockSizeValue;
		ROB_SIZE = robNoValue;
		MEM_ACCESS_TIME = memAccessTime;
		
		CloseFrame();
		
		CacheInfoGUI cacheGui = new CacheInfoGUI();
		cacheGui.setVisible(true);
	}

	public static void finalizeCache() {
		Cache[] cacheArray = new Cache[caches.size()];
		for (int i = 0; i < caches.size(); i++) {
			cacheArray[i] = caches.get(i);
		}
		mainmemory = new MainMemory(MEM_ACCESS_TIME);
		cHanlder = new CacheHandler(cacheArray.length, mainmemory, BLOCK_SIZE, cacheArray);
		
		System.out.println(cHanlder);
	}
	
	
}

