package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import database.MySQLAccess;
import database.Query;
import net.proteanit.sql.DbUtils;

public class GeneralDatabaseShow extends JFrame {
	
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	
	MySQLAccess sqlAccess;
	JFrame parent;
	private JLabel lblNewLabel;
	private JLabel lblSelected;
	private JButton btnNewButton;
	
	private String title;
	private String query;
	
	public GeneralDatabaseShow(JFrame parent, MySQLAccess sql, String title, String query) {
		this.title = title;
		this.query = query;
		this.parent = parent;
		this.sqlAccess = sql;
		initialize();
	}
	
	void initialize() {
		setBackground(Color.LIGHT_GRAY);
		getContentPane().setBackground(SystemColor.control);
		setBounds(100, 100, 640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		

		lblSelected = new JLabel("Selected: None");
		lblSelected.setBounds(109, 411, 104, 14);
		getContentPane().add(lblSelected);

		lblNewLabel = new JLabel(title);
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		lblNewLabel.setBounds(10, 11, 208, 23);
		getContentPane().add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 74, 604, 326);
		getContentPane().add(scrollPane);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(10, 407, 89, 23);
		getContentPane().add(btnSearch);

		table = new JTable();
		scrollPane.setViewportView(table);
		
		try {
			table.setModel(
					DbUtils.resultSetToTableModel(
							sqlAccess.getDataWithQuery(query)));
			
			btnNewButton = new JButton("Return to Control Panel");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.setVisible(true);
					setVisible(false);
				}
			});
			btnNewButton.setBounds(396, 14, 218, 31);
			getContentPane().add(btnNewButton);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			sqlAccess.closeConnection();
		}
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected = table.getSelectedRow();
				if(selected < 0) 
					JOptionPane.showMessageDialog(null, "Error! Nothing is selected.");
				else 
					lblSelected.setText("Selected: "+selected);
			}
		});
	}

}
