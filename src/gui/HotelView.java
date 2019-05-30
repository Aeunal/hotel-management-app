package gui;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.MySQLAccess;
import database.Query;
import net.proteanit.sql.DbUtils;
import java.awt.Font;

@SuppressWarnings("serial")
public class HotelView extends JFrame {

	private JTable table;
	private JScrollPane scrollPane;

	MySQLAccess sqlAccess;
	JFrame parent;
	Integer[] roomIDs;
	private JFrame me;

	private JLabel lblNewLabel;
	private JLabel lblSelected;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	private JLabel lblRooms;
	private JLabel lblRoomManagement;
	private JButton btnAddRoom;
	private JButton btnRefresh;
	private JButton btnUpdateRoom;
	private JButton btnUpdateRoom_1;
	private JButton button_2;
	private JLabel lblSelectedRoom;
	private JButton btnUpdateHotel;
	private JButton button;

	public HotelView(JFrame parent, MySQLAccess sql) {

		this.parent = parent;
		this.sqlAccess = sql;

		initialize();
	}

	private void initialize() {
		me = this;
		setBackground(Color.LIGHT_GRAY);
		getContentPane().setBackground(SystemColor.control);
		setBounds(100, 100, 640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		lblSelected = new JLabel("Selected: None");
		lblSelected.setBounds(119, 221, 168, 14);
		getContentPane().add(lblSelected);

		lblNewLabel = new JLabel("Hotels");
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblNewLabel.setBounds(20, 45, 137, 23);
		getContentPane().add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 79, 604, 131);
		getContentPane().add(scrollPane);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 284, 604, 119);
		getContentPane().add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(20, 217, 89, 23);
		getContentPane().add(btnSearch);

		table = new JTable();
		scrollPane.setViewportView(table);

		try {
			table.setModel(DbUtils.resultSetToTableModel(sqlAccess.getDataWithQuery(Query.getHotels)));
			table_1.setModel(DbUtils.resultSetToTableModel(sqlAccess.getDataWithQuery(Query.getRooms)));

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

			DataFormEnter roomFormView = new DataFormEnter(this, sqlAccess, Query.getRoomsForInsert, Query.insertRoom,
					Query.roomValues);
			btnAddRoom = new JButton("Add Room");
			btnAddRoom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					roomFormView.setVisible(true);
					setVisible(false);
				}
			});
			btnAddRoom.setBounds(454, 250, 160, 23);
			getContentPane().add(btnAddRoom);

			DataFormEnter hotelFormView = new DataFormEnter(this, sqlAccess, Query.getHotelForInsert, Query.insertHotel,
					Query.hotelValues);
			JButton btnAddHotel = new JButton("Add Hotel");
			btnAddHotel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hotelFormView.setVisible(true);
					setVisible(false);
				}
			});
			btnAddHotel.setBounds(454, 50, 160, 23);
			getContentPane().add(btnAddHotel);

			btnRefresh = new JButton("Refresh");
			btnRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					update();
				}
			});
			btnRefresh.setBounds(307, 20, 89, 23);
			getContentPane().add(btnRefresh);

			btnUpdateRoom = new JButton("Delete Room");
			btnUpdateRoom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selected = table_1.getSelectedRow();
					if (selected < 0)
						JOptionPane.showMessageDialog(null, "Error! Nothing is selected.");
					else {
						int number = (Integer) table_1.getValueAt(selected, 1);
						sqlAccess.deleteRoom(number);
						lblSelectedRoom.setText("Deleted Room " + number);
						update();	
					}
				}
			});
			btnUpdateRoom.setBounds(454, 410, 160, 23);
			getContentPane().add(btnUpdateRoom);

			btnUpdateRoom_1 = new JButton("Update Hotel");
			btnUpdateRoom_1.setBounds(289, 217, 160, 23);
			getContentPane().add(btnUpdateRoom_1);

			button_2 = new JButton("Search");
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selected = table_1.getSelectedRow();
					if (selected < 0)
						JOptionPane.showMessageDialog(null, "Error! Nothing is selected.");
					else {
						lblSelectedRoom.setText("Searching Fixtures...");
						int number = (Integer) table_1.getValueAt(selected, 1);
						GeneralDatabaseShow fixtureView = new GeneralDatabaseShow(me, sqlAccess,
								"Fixtures of Room " + number, Query.ManualSearchFixturesbyRoomNumber + number);
						fixtureView.setVisible(true);
						setVisible(false);
					}
				}
			});
			button_2.setBounds(20, 410, 89, 23);
			getContentPane().add(button_2);

			lblSelectedRoom = new JLabel("Selected: None");
			lblSelectedRoom.setBounds(119, 414, 147, 14);
			getContentPane().add(lblSelectedRoom);

			btnUpdateHotel = new JButton("Delete Hotel");
			btnUpdateHotel.setBounds(454, 217, 160, 23);
			getContentPane().add(btnUpdateHotel);

			button = new JButton("Update Room");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selected = table_1.getSelectedRow();
					int roomID = roomIDs[selected];
					if (selected < 0)
						JOptionPane.showMessageDialog(null, "Error! Nothing is selected.");
					else {
						Object num = table_1.getValueAt(selected, 1), floor = table_1.getValueAt(selected, 2),
								price = table_1.getValueAt(selected, 3), size = table_1.getValueAt(selected, 4);
						sqlAccess.updateRoom(
								(int) (isInt(num) ? num : (Integer.parseInt((String) num))),
								(int) (isInt(floor) ? floor : (Integer.parseInt((String) floor))),
								(int) (isInt(price) ? price : (Integer.parseInt((String) price))),
								(int) (isInt(size) ? size : (Integer.parseInt((String) size))), roomID);
						lblSelectedRoom.setText("Updated Selected Tuple.");
					}
				}
			});
			button.setBounds(289, 410, 160, 23);
			getContentPane().add(button);

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			sqlAccess.closeConnection();
		}

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected = table.getSelectedRow();
				if (selected < 0)
					JOptionPane.showMessageDialog(null, "Error! Nothing is selected.");
				else {
					String name = (String) table.getValueAt(selected, 0);
					// int id = (Integer) table.getValueAt(selected, 0);
					lblSelected.setText("Selected: " + name);
					// search(id);
					searchRoom(selected + 1);
				}
			}
		});
	}

	boolean isInt(Object o) {
		return o.getClass().getName().equals("java.lang.Integer");
	}

	boolean isStr(Object o) {
		return o.getClass().getName().equals("java.lang.String");
	}

	public void searchRoom(int selected) {
		try {
			ResultSet room = sqlAccess.getDataWithQuery(Query.searchRoomsbyHotelID, selected);
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while (room.next()) {
				ids.add(room.getInt("RoomNumber"));
			}
			roomIDs = new Integer[ids.size()];
			ids.toArray(roomIDs);
			table_1.setModel(
					DbUtils.resultSetToTableModel(sqlAccess.getDataWithQuery(Query.searchRoomsbyHotelID, selected)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update() {
		try {
			ResultSet room = sqlAccess.getDataWithQuery(Query.getRooms);
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while (room.next()) {
				ids.add(room.getInt("RoomNumber"));
			}
			roomIDs = new Integer[ids.size()];
			ids.toArray(roomIDs);
			table_1.setModel(DbUtils.resultSetToTableModel(sqlAccess.getDataWithQuery(Query.getRooms)));
			table.setModel(DbUtils.resultSetToTableModel(sqlAccess.getDataWithQuery(Query.getHotels)));
		} catch (Exception e) {
		}
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		update();
	}
}
