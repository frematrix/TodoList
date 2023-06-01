package todo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class todolistMain {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel tableModel;
	List<Task> tasks;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					todolistMain window = new todolistMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public todolistMain() {
		initialize();
		try {
			TaskDB.connect();
			tasks = TaskDB.readDatas();
			fillTable(tasks);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage(), "DB connection error", JOptionPane.ERROR_MESSAGE);
			
		}
		
	}

	private void fillTable(List<Task> tasks) {
		
		for (Task task : tasks) {
			Object[] datas = new Object[] {task.getName(),task.getExpireDate(),task.getStatusType()};
			tableModel.insertRow(table.getRowCount(), datas);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 240, 245));
		frame.setBounds(100, 100, 419, 432);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tasks = new ArrayList<Task>();
		dataToTable();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 105, 343, 208);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		String[] columnName = {"name","expire","status"};
		tableModel = new DefaultTableModel(null,columnName);
		table.setModel(tableModel);
		
		JLabel lblNewLabel = new JLabel("TODO LIST");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\frema\\Documents\\2.Study\\Prog\\Ruander\\JAVA_work\\nap202303_gyakorlas\\src\\todolist\\to-do-list.png"));
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		lblNewLabel.setBounds(27, 14, 232, 60);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				delete();
			}
		});
		btnNewButton_2.setBackground(new Color(255, 192, 203));
		btnNewButton_2.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnNewButton_2.setBounds(281, 336, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.setBackground(new Color(240, 128, 128));
		btnExit.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnExit.setBounds(281, 28, 89, 39);
		frame.getContentPane().add(btnExit);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edit();
			}
		});
		btnEdit.setBackground(new Color(255, 192, 203));
		btnEdit.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnEdit.setBounds(153, 336, 89, 23);
		frame.getContentPane().add(btnEdit);
		
		JButton btnNew = new JButton("New");
		btnNew.setBackground(new Color(255, 192, 203));
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newTask();
			}
		});
		btnNew.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnNew.setBounds(27, 336, 89, 23);
		frame.getContentPane().add(btnNew);
	}

	protected void delete() {
		
		int valasz = JOptionPane.showOptionDialog(frame, "Do you want to delete?", "Delete", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		if (table.getSelectedRow() != -1 && valasz == JOptionPane.YES_OPTION) {
			
			Task deleteTask = tasks.get(table.getSelectedRow());
			
			try {
				TaskDB.delete(deleteTask);
				tasks.remove(deleteTask);
				tableModel.removeRow(table.getSelectedRow());
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
		
	}

	protected void edit() {
		
		if (table.getSelectedRow() != -1) {
			
			EditWindow newWindow = new EditWindow(tasks.get(table.getSelectedRow()));
			newWindow.setVisible(true);
			
			tableModel.setValueAt(newWindow.getTask().getName(), table.getSelectedRow(), 0);
			tableModel.setValueAt(newWindow.getTask().getExpireDate(), table.getSelectedRow(), 1);
			tableModel.setValueAt(newWindow.getTask().getStatusType(), table.getSelectedRow(), 2);
			
		}
		else {
			JOptionPane.showMessageDialog(frame, "Nothing selected to edit!","Warning",JOptionPane.WARNING_MESSAGE);
		}
		
	}

	private void dataToTable() {
		
		for (Task task : tasks) {
			Object[] taskTableForm = new Object[] {task.getName(), task.getExpireDate(), task.getStatusType()};
			tableModel.insertRow(table.getRowCount(), taskTableForm);
		}
		
	}

	protected void newTask() {
		
		EditWindow newEdit = new EditWindow();
		newEdit.setVisible(true);
		
		if (newEdit.isFinished()==true) {
			Task newTask = newEdit.getTask();
			try {
				TaskDB.newData(newTask);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, e.getMessage(), "DB saving error", JOptionPane.ERROR_MESSAGE);
			}
			tasks.add(newTask);
			
			
			Object[] taskTableForm = new Object[] {newTask.getName(), newTask.getExpireDate(), newTask.getStatusType()};
			tableModel.insertRow(table.getRowCount(), taskTableForm);
			
		}
		
	}
}
