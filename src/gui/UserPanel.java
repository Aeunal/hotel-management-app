package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.MySQLAccess;
import database.Query;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class UserPanel extends JFrame {

	private JPanel contentPane;

	JFrame parent;
	MySQLAccess sqlAccess;
	
	public UserPanel(JFrame parent, MySQLAccess sql) {
		this.parent = parent;
		this.sqlAccess = sql;
		initialize();
	}
	
	void initialize() {

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 367, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton reservation = new JButton("Make Reservation");
		DataFormEnter addReservation = new DataFormEnter(this, sqlAccess, Query.resView, Query.resInsert, Query.resValues);
		reservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addReservation.setVisible(true);
				setVisible(false);
			}
		});
		reservation.setBounds(9, 65, 143, 65);
		contentPane.add(reservation);
		
		JButton pay = new JButton("Pay");
		DataFormEnter addPayment = new DataFormEnter(this, sqlAccess, Query.payView, Query.payInsert, Query.payValues);
		pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPayment.setVisible(true);
				setVisible(false);
			}
		});
		pay.setBounds(162, 65, 73, 65);
		contentPane.add(pay);
		
		JLabel lblUserPanel = new JLabel("User Panel");
		lblUserPanel.setForeground(Color.DARK_GRAY);
		lblUserPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserPanel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		lblUserPanel.setBounds(9, 11, 143, 37);
		contentPane.add(lblUserPanel);
		
		JButton btnAddUser = new JButton("Add User");
		AddUserForm addUser = new AddUserForm(this, sqlAccess);
		//DataFormEnter addUser = new DataFormEnter(this, sqlAccess, Query.userView, Query.userInsert, Query.userValues);
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addUser.setVisible(true);
				setVisible(false);
			}
		});
		btnAddUser.setBounds(245, 65, 96, 65);
		contentPane.add(btnAddUser);
		
		JButton btnGoBack = new JButton("Go back to Login Screen");
		btnGoBack.setFont(new Font("Tahoma", Font.ITALIC, 11));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				setVisible(false);
			}
		});
		btnGoBack.setBounds(162, 11, 179, 31);
		contentPane.add(btnGoBack);
	}
	
	void update() {
		
	}
	
	public void setVisible(boolean b) {
		super.setVisible(b);
		update();
	}
}
