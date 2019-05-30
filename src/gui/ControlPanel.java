package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import database.MySQLAccess;
import database.Query;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ControlPanel extends JFrame {

	private JPanel contentPane;
	private JFrame parent;
	private MySQLAccess sqlAccess;

	private WorkInProgress wip;
	private HotelView hotel;
	private CustomerView customer;
	
	public ControlPanel(JFrame parent, MySQLAccess sql) {

		this.parent = parent;
		this.sqlAccess = sql;
		
		wip = new WorkInProgress(this);
		hotel = new HotelView(this, sqlAccess);
		customer = new CustomerView(this, sqlAccess);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblControlPanel = new JLabel("Control Panel");
		lblControlPanel.setBounds(15, 16, 600, 31);
		lblControlPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblControlPanel.setFont(new Font("Segoe UI Light", Font.BOLD, 23));
		contentPane.add(lblControlPanel);
		
		JButton btnNewButton = new JButton("Room Management");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotel.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnNewButton.setBounds(6, 81, 196, 142);
		contentPane.add(btnNewButton);
		
		GeneralDatabaseShow reservations = new GeneralDatabaseShow(this, sqlAccess, "Reservation History", Query.getReservations);
		reservations.addDelCommand(Query.manuelDelReservation);
		reservations.initialize();
		
		JButton btnNewButton_1 = new JButton("Reservation History");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservations.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_1.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnNewButton_1.setBounds(212, 81, 196, 142);
		contentPane.add(btnNewButton_1);
		
		GeneralDatabaseShow payments = new GeneralDatabaseShow(this, sqlAccess, "Payment History", Query.getPayments);
		payments.addDelCommand(Query.manuelDelPayment);
		payments.initialize();
		
		JButton btnNewButton_2 = new JButton("Payment History");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				payments.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_2.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnNewButton_2.setBounds(418, 81, 196, 142);
		contentPane.add(btnNewButton_2);
		
		GeneralDatabaseShow employees = new GeneralDatabaseShow(this, sqlAccess, "Employee Management", Query.getEmployees);
		employees.addDelCommand(Query.manuelDelEmployee);
		employees.addInsertCommand(Query.insertEmployee, Query.getEmployeeForInsert, Query.employeeValues);
		employees.initialize();
		
		JButton btnNewButton_3 = new JButton("Employee Management");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				employees.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_3.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnNewButton_3.setBounds(6, 234, 301, 142);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Customer Management");

		GeneralDatabaseShow customers = new GeneralDatabaseShow(this, sqlAccess, "Customer Management", Query.getCustomers);
		customers.addDelCommand(Query.manuelDelCustomer);
		customers.initialize();
		
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//customer.setVisible(true);
				customers.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_4.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnNewButton_4.setBounds(317, 234, 298, 142);
		contentPane.add(btnNewButton_4);
		
		JButton btnBackToLogin = new JButton("Back to Login Screen");
		btnBackToLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				setVisible(false);
			}
		});
		btnBackToLogin.setBounds(418, 390, 197, 40);
		contentPane.add(btnBackToLogin);
		
		JLabel lblDatabaseManagementSystem = new JLabel("Database Management System - Project");
		lblDatabaseManagementSystem.setForeground(Color.GRAY);
		lblDatabaseManagementSystem.setBounds(16, 408, 234, 22);
		contentPane.add(lblDatabaseManagementSystem);
	}

}
