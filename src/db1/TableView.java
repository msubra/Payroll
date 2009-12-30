/*
 * Created on Aug 15, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package db1;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author maheshexp
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableView {
	JTextField[] txt;
	JLabel[] labels;
	String tableName;
	
	ResultSet result;
	int count;
	ResultSetMetaData meta;
		
	public TableView(int n, String tableName){
		txt = new JTextField[n];
		labels = new JLabel[n];
		
		this.tableName = tableName;
	}
	
	public void refresh(){
		try {
			for(int i = 0; i < txt.length; i++){
				txt[i].setText(result.getObject(i).toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
