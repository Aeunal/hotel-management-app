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

@SuppressWarnings("serial")
public class GeneralDatabaseShow extends JFrame {
	
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	
	private String searchCommand;
	private String insertCommand, insertView, insertVal;
	private String deleteCommand;
	private String updateCommand;
	
	MySQLAccess sqlAccess;
	JFrame parent;
	JFrame me;
	
	private JLabel lblNewLabel;
	private JLabel lblSelected;
	private JButton btnNewButton;
	private JButton addBtn;
	private JButton updateBtn;
	private JButton delBtn;
	private String title;
	private String query;
	
	
	public GeneralDatabaseShow(JFrame parent, MySQLAccess sql, String title, String query) {
		this.title = title;
		this.query = query;
		this.parent = parent;
		this.sqlAccess = sql;
		//initialize();
	}
	
	void addSearchCommand(String command) {
		this.searchCommand = command;
	}
	
	void addInsertCommand(String command, String view, String val) {
		this.insertCommand = command;
		this.insertView = view;
		this.insertVal = val;
	}
	
	void addDelCommand(String command) {
		this.deleteCommand = command;
	}
	
	void addUpdateCommand(String command) {
		this.updateCommand = command;
	}
	
	void initialize() {
		me = this;
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
			update();
			
			btnNewButton = new JButton("Return to Control Panel");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.setVisible(true);
					setVisible(false);
				}
			});
			btnNewButton.setBounds(396, 14, 218, 31);
			getContentPane().add(btnNewButton);
			
			addBtn = new JButton("Add");
			
			addBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						if(insertCommand == null) {
							JOptionPane.showMessageDialog(null, "Command is missing!");
						} else {
							DataFormEnter formView = new DataFormEnter(me, sqlAccess, insertView, insertCommand, insertVal);
							formView.setVisible(true);
							setVisible(false);
						}
				}
			});
			addBtn.setBounds(327, 407, 89, 23);
			getContentPane().add(addBtn);
			
			updateBtn = new JButton("Update");
			updateBtn.setBounds(426, 407, 89, 23);
			getContentPane().add(updateBtn);
			
			delBtn = new JButton("Del");
			delBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selected = table.getSelectedRow();
					if(selected < 0) { 
						JOptionPane.showMessageDialog(null, "Error! Nothing is selected.");
					} else {
						int id = (int) table.getValueAt(selected, 0);
						lblSelected.setText("Selected: "+id);
						if(deleteCommand == null) {
							JOptionPane.showMessageDialog(null, "Command is missing!");
						} else {
							sqlAccess.runScript(deleteCommand+id);
							update();
						}
					}
				}
			});
			delBtn.setBounds(525, 407, 89, 23);
			getContentPane().add(delBtn);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			sqlAccess.closeConnection();
		}
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected = table.getSelectedRow();
				if(selected < 0) { 
					JOptionPane.showMessageDialog(null, "Error! Nothing is selected.");
				} else {
					lblSelected.setText("Selected: "+selected);
					if(searchCommand == null) {
						JOptionPane.showMessageDialog(null, "Command is missing!");
					}
				}
			}
		});
		
	}
	
	public void setVisible(boolean b) {
		super.setVisible(b);
		update();
	}
	
	void update() {
		table.setModel(
				DbUtils.resultSetToTableModel(
						sqlAccess.getDataWithQuery(query)));
	}

}
