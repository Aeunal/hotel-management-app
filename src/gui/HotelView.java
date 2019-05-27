package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.ScrollPaneConstants;
import java.awt.Font;

public class HotelView extends JFrame {

	private JTable table;
	private JScrollPane scrollPane;
	
	MySQLAccess sqlAccess;
	JFrame parent;
	
	private JLabel lblNewLabel;
	private JLabel lblSelected;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	private JLabel lblRooms;
	private JLabel lblRoomManagement;
	private JButton btnAddRoom;

	public HotelView(JFrame parent, MySQLAccess sql) {
		
		this.parent = parent;
		this.sqlAccess = sql;
		
		initialize();
	}
	
	private void initialize() {
		setBackground(Color.LIGHT_GRAY);
		getContentPane().setBackground(SystemColor.control);
		setBounds(100, 100, 640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		

		lblSelected = new JLabel("Selected: None");
		lblSelected.setBounds(111, 221, 104, 14);
		getContentPane().add(lblSelected);
		
				lblNewLabel = new JLabel("Hotels");
				lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
				lblNewLabel.setBounds(20, 45, 137, 23);
				getContentPane().add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 79, 604, 131);
		getContentPane().add(scrollPane);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 284, 604, 146);
		getContentPane().add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(10, 217, 89, 23);
		getContentPane().add(btnSearch);

		table = new JTable();
		scrollPane.setViewportView(table);
		
		try {
			table.setModel(
					DbUtils.resultSetToTableModel(
							sqlAccess.getDataWithQuery(Query.getHotels)));
			table_1.setModel(
					DbUtils.resultSetToTableModel(
							sqlAccess.getDataWithQuery(Query.getRooms)));
			
			lblRooms = new JLabel("Rooms");
			lblRooms.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
			lblRooms.setBounds(20, 241, 147, 32);
			getContentPane().add(lblRooms);
			
			lblRoomManagement = new JLabel("Room Management");
			lblRoomManagement.setForeground(Color.GRAY);
			lblRoomManagement.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
			lblRoomManagement.setBounds(10, 11, 246, 32);
			getContentPane().add(lblRoomManagement);
			
			JButton btnBack = new JButton("Return to Control Panel");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.setVisible(true);
					setVisible(false);
				}
			});
			btnBack.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnBack.setBounds(406, 11, 208, 32);
			getContentPane().add(btnBack);
			
			DataFormEnter formView = new DataFormEnter(this, sqlAccess, Query.getRooms);
			btnAddRoom = new JButton("Add Room");
			btnAddRoom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					formView.setVisible(true);
					setVisible(false);
				}
			});
			btnAddRoom.setBounds(406, 250, 191, 23);
			getContentPane().add(btnAddRoom);
			
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
	
	public void update() {
		try {
			table_1.setModel(
					DbUtils.resultSetToTableModel(
							sqlAccess.getDataWithQuery(Query.getRooms)));
		} catch (Exception e) {}
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		update();
	}
}
