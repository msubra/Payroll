/*
 * Created on Jun 9, 2004
 */
package db;

import java.sql.ResultSet;

import javax.swing.JFrame;

/**
 * @author maheshexp
 */
public class TableWindow extends JFrame {

	public TableWindow(ResultSet result) throws Exception {
		super("Staff Table Viewer");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		DisplayPanel display = new DisplayPanel(result);
		ButtonPanel buttons = new ButtonPanel(display);

		this.getContentPane().add(display);
		this.getContentPane().add(buttons, "South");

		this.setSize(400, 50 * result.getMetaData().getColumnCount());
		this.validate();
	}

}