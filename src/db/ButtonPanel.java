/*
 * Created on Jun 8, 2004
 */
package db;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * @author maheshexp
 */
public class ButtonPanel extends JPanel {
	DisplayPanel disp;
	boolean isedit = true;

	public ButtonPanel(DisplayPanel disp) {
		this.disp = disp;
		init();
	}

	private void init() {
		this.setLayout(new FlowLayout());

		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!disp.result.isLast()) {
						disp.result.next();
						disp.showrec();
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton prev = new JButton("Previous");
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!disp.result.isFirst()) {
						disp.result.previous();
						disp.showrec();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		final JToggleButton addNew = new JToggleButton("Add/Save");
		addNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disp.setEdit(addNew.isSelected());
				if (addNew.isSelected()) {
					for (int i = 0; i < disp.text.length; i++) {
						disp.text[i].setText("");
					}
				}
				else{
					if (disp.tName.equalsIgnoreCase("salary")) {
						disp.calculate();
					}
					disp.insert();
				}
			}
		});

		final JToggleButton delete = new JToggleButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disp.delete();
			}
		});

		this.add(prev);
		this.add(next);
		this.add(addNew);
		this.add(delete);
	}
}