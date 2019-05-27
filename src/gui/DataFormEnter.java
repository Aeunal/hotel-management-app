package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.MySQLAccess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataFormEnter extends JFrame {

	private JPanel contentPane;
	private JTextField[] textField;
	private JLabel[] label;
	
	private String[] keys;
	private int keyCount = 0;
	
	private MySQLAccess sqlAccess;
	private JFrame parent;
	private String query;

	public DataFormEnter(JFrame parent, MySQLAccess sqlAccess, String query) {
		this.query = query;
		this.sqlAccess = sqlAccess;
		this.parent = parent;
		try {
			String[] keys = sqlAccess.getMetaKeys(sqlAccess.getDataWithQuery(query));
			keyCount = keys.length;
		} catch(Exception e) {}
		initialize(keyCount);
		
	}
	
	void initialize(int elementCount) {
		textField = new JTextField[elementCount];
		label = new JLabel[elementCount];
		
		int topMargin = 50;
		int bottomMargin = 100;
		int elementHeight = 40;
		int totalHeight = topMargin + elementHeight * elementCount + bottomMargin;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, totalHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterForm = new JLabel("Add New Entry");
		lblEnterForm.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
		lblEnterForm.setBounds(29, 11, 155, 30);
		contentPane.add(lblEnterForm);
		
		JButton btnEnter = new JButton("Insert to Table");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sqlAccess.insertRoom(
							textField[2].getText(),
							textField[3].getText(),
							textField[4].getText(),
							textField[5].getText(),
							textField[6].getText()
						);
					JOptionPane.showMessageDialog(null, "Values Inserted!");
					textField[2].setText("");
					textField[3].setText("");
					textField[4].setText("");
					textField[5].setText("");
					textField[6].setText("");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				sqlAccess.closeConnection();
			}
		});
		btnEnter.setBounds(254, totalHeight - bottomMargin, 200, 38);
		contentPane.add(btnEnter);
		
		JButton btnGoBack = new JButton("Cancel");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				setVisible(false);
			}
		});
		btnGoBack.setBounds(10, totalHeight - bottomMargin, 200, 38);
		contentPane.add(btnGoBack);
		
		try {
			String[] keys = sqlAccess.getMetaKeys(sqlAccess.getDataWithQuery(query));
			
			topMargin -= elementHeight;
			for(int i = 2; i < elementCount; i++) {
				label[i] = new JLabel(keys[i]);
				label[i].setBounds(29,topMargin + i*elementHeight,80,14);
				contentPane.add(label[i]);
				
				textField[i] = new JTextField();
				textField[i].setBounds(175, topMargin + i*elementHeight, 279, 20);
				contentPane.add(textField[i]);
				textField[i].setColumns(10);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
