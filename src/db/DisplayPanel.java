/*
 * Created on Jun 8, 2004
 */
package db;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author maheshexp
 */
public class DisplayPanel extends JPanel {
	ResultSet result;
	JLabel[] label;
	JTextField[] text;
	int count;
	ResultSetMetaData meta;
	String tName;

	public DisplayPanel(ResultSet result) {
		this.result = result;
		init();
	}

	private void init() {
		try {
			meta = result.getMetaData(); //get meta data
			count = meta.getColumnCount();
			tName = meta.getTableName(1); //get table name
			System.out.println(tName);

			this.setLayout(new GridLayout(count, 2));
			label = new JLabel[count];
			text = new JTextField[count];

			for (int i = 1; i <= count; i++) {
				label[i - 1] = new JLabel(meta.getColumnLabel(i));
				this.add(label[i - 1]);

				text[i - 1] = new JTextField();
				this.add(text[i - 1]);
				if (tName.equalsIgnoreCase("salary") && i == 2) {
					text[i - 1].addKeyListener(new ValueUpdate());
				}
			}

			//move to first record
			result.first();
			showrec();
			//intially display the results
			setEdit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * class to update the values automatically
	 */
	class ValueUpdate extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			calculate();
		}
	}

	public void calculate() {
		String txt = text[1].getText();
		txt = txt.equals("") ? "0" : txt;
		double amt = Double.parseDouble(txt);
		double hra = amt * 0.10;
		double pf = amt * 0.20;
		double total = amt + hra + pf;

		text[2].setText(total + "");
		text[3].setText(hra + "");
		text[4].setText(pf + "");
	}

	public void showrec() {
		try {
			for (int i = 0; i < count; i++) {
				String col = meta.getColumnName(i + 1);
				Object res = result.getObject(col);
				if (res != null)
					text[i].setText(res.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setEdit(boolean show) {
		for (int i = 0; i < text.length; i++) {
			text[i].setEnabled(show);
		}
	}

	public void insert() {
		try {
			Statement s = MainWindow.getStatement();
			String ins = "INSERT INTO " + tName + " VALUES ( ";

			//generate INSERT statement
			for (int i = 1; i <= count; i++) {
				String value = text[i - 1].getText();

				switch (meta.getColumnType(i)) {
					case Types.INTEGER :
					case Types.FLOAT :
					case Types.DOUBLE :
					case Types.TINYINT :
					case Types.REAL :
						break;
					default :
						value = "\'" + value + "\'";
				}
				ins = ins + value + ",";
			}
			ins = ins.substring(0, ins.length() - 1);
			ins = ins + " )";
			s.execute(ins);

			//update with new records
			result = s.executeQuery("SELECT * from " + tName);
			result.last(); //move to last record
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		try {
			result.deleteRow();
			Statement s = MainWindow.getStatement();
			result = s.executeQuery("SELECT * from " + tName);
			result.moveToCurrentRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}