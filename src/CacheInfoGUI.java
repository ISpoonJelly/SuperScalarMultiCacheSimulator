import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cache.Cache;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;

public class CacheInfoGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField cacheSize;
	private JTextField accessTime;
	private JTextField writePolicy;
	private JTextField associativity;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CacheInfoGUI frame = new CacheInfoGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	// level,  CacheSize,  blockSize,  assoc,  accessTime, writeBack
	
	/**
	 * Create the frame.
	 */
	public CacheInfoGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCacheSize = new JLabel("Cache Size:");
		lblCacheSize.setBounds(22, 53, 80, 16);
		contentPane.add(lblCacheSize);
		
		cacheSize = new JTextField();
		cacheSize.setBounds(106, 47, 50, 28);
		contentPane.add(cacheSize);
		cacheSize.setColumns(10);
		
		JLabel lblAccessTime = new JLabel("Access Time:");
		lblAccessTime.setBounds(22, 81, 90, 16);
		contentPane.add(lblAccessTime);
		
		accessTime = new JTextField();
		accessTime.setBounds(106, 75, 50, 28);
		contentPane.add(accessTime);
		accessTime.setColumns(10);
		
		JLabel lblWritePolicy = new JLabel("Write Policy:");
		lblWritePolicy.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblWritePolicy.setForeground(Color.BLUE);
		lblWritePolicy.setBounds(22, 146, 90, 16);
		contentPane.add(lblWritePolicy);
		
		JLabel lblCache = new JLabel("CACHE : " + GUI.noCache++);
		lblCache.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblCache.setBounds(25, 16, 100, 16);
		contentPane.add(lblCache);
		
		JLabel lblNewLabel = new JLabel("(Enter 1 for WriteBack & 2 for WriteThrough)");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		lblNewLabel.setBounds(22, 165, 300, 16);
		contentPane.add(lblNewLabel);
		
		writePolicy = new JTextField();
		writePolicy.setBounds(106, 137, 50, 28);
		contentPane.add(writePolicy);
		writePolicy.setColumns(10);
		
		JButton btnNewButton = new JButton("Add another Cache");
		btnNewButton.setBounds(22, 193, 150, 29);
		contentPane.add(btnNewButton);
		btnNewButton.setActionCommand("btnNewButton");
		btnNewButton.addActionListener(this);
		
		JButton btnDone = new JButton("Done");
		btnDone.setBounds(214, 193, 117, 29);
		contentPane.add(btnDone);
		btnDone.setActionCommand("done");
		
		JLabel lblAssociativity = new JLabel("Associativity");
		lblAssociativity.setBounds(22, 109, 100, 16);
		contentPane.add(lblAssociativity);
		
		associativity = new JTextField();
		associativity.setBounds(106, 103, 50, 28);
		contentPane.add(associativity);
		associativity.setColumns(10);
		btnDone.addActionListener(this);
		
		
	}
	
	public void CloseFrame() {
		super.dispose();
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		int sizeCache = Integer.parseInt(cacheSize.getText());
		int timeAccess = Integer.parseInt(accessTime.getText());
		int policyWrite = Integer.parseInt(writePolicy.getText());
		int assoc = Integer.parseInt(associativity.getText());
		if (e.getActionCommand().equals("btnNewButton")) {
			System.out.println("HEREEEEEEEE!");
			GUI.caches.add(new Cache(GUI.noCache - 1, sizeCache, GUI.BLOCK_SIZE, assoc, timeAccess, policyWrite==1?true:false));
			CloseFrame();
			CacheInfoGUI cacheGui = new CacheInfoGUI();
			cacheGui.setVisible(true);
		}else  if (e.getActionCommand().equals("done")) {
			GUI.caches.add(new Cache(GUI.noCache - 1, sizeCache, GUI.BLOCK_SIZE, assoc, timeAccess, policyWrite==1?true:false));
			GUI.finalizeCache();
			CloseFrame();
			WriteProgram writeProg = new WriteProgram();
			writeProg.setVisible(true);
		}
		
	}
}
