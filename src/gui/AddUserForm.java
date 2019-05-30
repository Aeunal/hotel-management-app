package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import database.MySQLAccess;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class AddUserForm extends JFrame {

	private JPanel contentPane;
	private JTextField textMail;
	private JTextField textCountry;
	private JTextField textName;
	private JLabel lblCountry;
	private JLabel lblCity;
	private JLabel lblPostalcode;
	private JTextField textPhone;
	private JTextField textPostal;
	private JTextField textCity;
	private JLabel lblCreateNewUser;

	JFrame parent;
	MySQLAccess sqlAccess;
	
	public AddUserForm(JFrame parent, MySQLAccess sqlAccess) {
		this.parent = parent;
		this.sqlAccess = sqlAccess;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textMail = new JTextField();
		textMail.setBounds(270, 80, 255, 20);
		contentPane.add(textMail);
		textMail.setColumns(10);
		
		textCountry = new JTextField();
		textCountry.setBounds(428, 119, 97, 20);
		contentPane.add(textCountry);
		textCountry.setColumns(10);
		
		textName = new JTextField();
		textName.setBounds(270, 49, 255, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		
		Canvas canvas = new Painter();
		canvas.setBackground(Color.LIGHT_GRAY);
		canvas.setBounds(10, 10, 136, 147);
		contentPane.add(canvas);
		
		JLabel lblNameSurname = new JLabel("Name \r\nSurname");
		lblNameSurname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNameSurname.setBounds(163, 50, 136, 14);
		contentPane.add(lblNameSurname);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(163, 83, 97, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(202, 122, 97, 14);
		contentPane.add(lblPhone);
		
		JLabel lblAddress = new JLabel("Address Text");
		lblAddress.setBounds(10, 177, 97, 14);
		contentPane.add(lblAddress);
		
		lblCountry = new JLabel("Country");
		lblCountry.setBounds(377, 122, 80, 14);
		contentPane.add(lblCountry);
		
		lblCity = new JLabel("City");
		lblCity.setBounds(377, 149, 80, 14);
		contentPane.add(lblCity);
		
		lblPostalcode = new JLabel("PostalCode");
		lblPostalcode.setBounds(199, 149, 80, 14);
		contentPane.add(lblPostalcode);
		
		textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(270, 119, 97, 20);
		contentPane.add(textPhone);
		
		textPostal = new JTextField();
		textPostal.setColumns(10);
		textPostal.setBounds(270, 147, 97, 20);
		contentPane.add(textPostal);
		
		textCity = new JTextField();
		textCity.setColumns(10);
		textCity.setBounds(428, 146, 97, 20);
		contentPane.add(textCity);
		
		lblCreateNewUser = new JLabel("Create New User");
		lblCreateNewUser.setForeground(Color.GRAY);
		lblCreateNewUser.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblCreateNewUser.setBounds(152, 10, 181, 22);
		contentPane.add(lblCreateNewUser);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 198, 515, 72);
		contentPane.add(textArea);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textName.getText();
				String[] names = name.split(" ");
				String FirstName = names[0];
				String LastName = names[names.length-1];
				String MiddleName = name.substring(FirstName.length(), name.length()-LastName.length()).trim();
				String Phone = textPhone.getText();
				String Email = textMail.getText();
				String Country = textCountry.getText();
				String City = textCity.getText();
				String Postal = textPostal.getText();
				String Address = textArea.getText();
				String[] input = {
					FirstName, MiddleName, LastName, Postal, Address, Country, City, Email, Phone
				};
				sqlAccess.addUser(input);
			}
		});
		btnSubmit.setBounds(436, 15, 89, 23);
		contentPane.add(btnSubmit);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				setVisible(false);
			}
		});
		btnBack.setBounds(337, 15, 89, 23);
		contentPane.add(btnBack);
	}
}

class Painter extends Canvas {
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.fillOval(35, 22, 70, 70);
		g.fill3DRect(20, 90, 100, 150, true);
		g.draw3DRect(0, 0, getWidth()-1, getHeight()-1, true);
	}
}
