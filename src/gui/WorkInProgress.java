package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WorkInProgress extends JFrame {

	private JPanel contentPane;
	private JFrame parent;
	
	public WorkInProgress(JFrame parent) {
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblThisFunctionalityIs = new JLabel("This functionality is work in progress");
		lblThisFunctionalityIs.setForeground(Color.GRAY);
		lblThisFunctionalityIs.setFont(new Font("Segoe UI Semilight", Font.BOLD | Font.ITALIC, 23));
		lblThisFunctionalityIs.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblThisFunctionalityIs, BorderLayout.CENTER);
		
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				setVisible(false);
			}
		});
		contentPane.add(btnGoBack, BorderLayout.SOUTH);
	}

}
