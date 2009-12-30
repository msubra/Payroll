/*
 * Created on Jun 9, 2004
 */
package db;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author maheshexp
 */
public class MainWindow extends JFrame {
	Container c;
	JFrame f1, f2;
	static Connection con;

	public MainWindow() throws Exception {
		super("Main Window");
		this.setSize(400, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = this.getContentPane();
		init();
		this.setResizable(false);
		this.validate();
		this.show();
	}

	public static Connection getConnection() {
		return con;
	}

	public static Statement getStatement() throws SQLException {
		return con.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
	}

	void init() throws Exception {
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		Class.forName(driver);
		String url = "jdbc:odbc:STAFF";
		con = DriverManager.getConnection(url);

		Statement s = getStatement();
		ResultSet rs = s.executeQuery("select * from Personal");

		s = getStatement();
		ResultSet rs1 = s.executeQuery("select * from salary");

		f1 = new TableWindow(rs);
		f2 = new TableWindow(rs1);

		JButton b1 = new JButton("Personal Details");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f1.show();
			}
		});

		JButton b2 = new JButton("Staff Details");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f2.show();
			}
		});

		JButton b3 = new JButton("Exit");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		c.setLayout(new FlowLayout());
		JLabel text = new JLabel("<html>Payroll Processing Application <i>for Boon's Company</i></html>");
		c.add(text);
		c.add(b1);
		c.add(b2);
		c.add(b3);
	}
	
	public static void main(String[] args) throws Exception{
		new MainWindow();
	}
}