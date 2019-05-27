package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import database.MySQLAccess;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;

public class MainFrame {

	private JFrame frmHotelManagementSystem;
	MySQLAccess sqlAccess;


	private WorkInProgress wip;
	private ControlPanel controlPanel;
	private ControlPanel userControlPanel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmHotelManagementSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		sqlAccess = new MySQLAccess();
		//sqlAccess.useDB();
		
		frmHotelManagementSystem = new JFrame();
		frmHotelManagementSystem.getContentPane().setBackground(SystemColor.control);
		frmHotelManagementSystem.setTitle("Hotel Management System");
		frmHotelManagementSystem.setBounds(100, 100, 367, 180);
		frmHotelManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHotelManagementSystem.getContentPane().setLayout(null);
		
		JLabel lblLoginPage = new JLabel("Hotel Management System");
		lblLoginPage.setFont(new Font("Verdana", Font.PLAIN, 17));
		lblLoginPage.setBounds(10, 11, 290, 37);
		frmHotelManagementSystem.getContentPane().add(lblLoginPage);
		
		JButton btnUserLogin = new JButton("User Login");
		btnUserLogin.setBounds(180, 59, 161, 71);
		frmHotelManagementSystem.getContentPane().add(btnUserLogin);
		
		JButton btnAdminLogin = new JButton("Admin Login");
		btnAdminLogin.setBounds(9, 59, 161, 71);
		frmHotelManagementSystem.getContentPane().add(btnAdminLogin);
		
		wip = new WorkInProgress(frmHotelManagementSystem);
		controlPanel = new ControlPanel(frmHotelManagementSystem, sqlAccess);
		userControlPanel = new ControlPanel(frmHotelManagementSystem, sqlAccess, true);
		
		btnUserLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userControlPanel.setVisible(true);
				frmHotelManagementSystem.setVisible(false);
			}
		});
		btnAdminLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmHotelManagementSystem.setVisible(false);
				controlPanel.setVisible(true);
			}
		});
		
		
		
	}
}
