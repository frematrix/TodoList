package todo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class EditWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtExpire;
	private Task task;
	private JComboBox cmbStatus;
	private boolean finishedEdit;

	public Task getTask() {
		return task;
	}
	
	public boolean isFinished() {
		return finishedEdit;
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditWindow dialog = new EditWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditWindow() {
		this.setModal(true);
		
		setFont(new Font("Century Gothic", Font.PLAIN, 17));
		setTitle("Edit");
		setBackground(new Color(255, 240, 245));
		setBounds(100, 100, 372, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 240, 245));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblName = new JLabel("Name");
			lblName.setFont(new Font("Century Gothic", Font.PLAIN, 15));
			lblName.setBounds(10, 34, 73, 31);
			contentPanel.add(lblName);
		}
		{
			JLabel lblExpire = new JLabel("Expire");
			lblExpire.setFont(new Font("Century Gothic", Font.PLAIN, 15));
			lblExpire.setBounds(10, 76, 73, 31);
			contentPanel.add(lblExpire);
		}
		{
			JLabel lblStatus = new JLabel("Status");
			lblStatus.setFont(new Font("Century Gothic", Font.PLAIN, 15));
			lblStatus.setBounds(10, 122, 73, 31);
			contentPanel.add(lblStatus);
		}
		{
			JButton btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					saveTask();
				}
			});
			btnSave.setBackground(new Color(255, 192, 203));
			btnSave.setFont(new Font("Century Gothic", Font.PLAIN, 15));
			btnSave.setBounds(56, 191, 89, 23);
			contentPanel.add(btnSave);
		}
		{
			JButton btnCancel = new JButton("Cancel");
			btnCancel.setFont(new Font("Century Gothic", Font.PLAIN, 15));
			btnCancel.setBackground(new Color(255, 192, 203));
			btnCancel.setBounds(215, 191, 89, 23);
			contentPanel.add(btnCancel);
		}
		{
			txtName = new JTextField();
			txtName.setFont(new Font("Century Gothic", Font.PLAIN, 15));
			txtName.setBounds(89, 34, 215, 27);
			contentPanel.add(txtName);
			txtName.setColumns(10);
		}
		{
			txtExpire = new JTextField();
			txtExpire.setFont(new Font("Century Gothic", Font.PLAIN, 15));
			txtExpire.setColumns(10);
			txtExpire.setBounds(89, 76, 215, 27);
			contentPanel.add(txtExpire);
		}
		
		cmbStatus = new JComboBox();
		cmbStatus.setModel(new DefaultComboBoxModel(Status.values()));
		cmbStatus.setBackground(new Color(255, 240, 245));
		cmbStatus.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		cmbStatus.setBounds(89, 122, 215, 28);
		contentPanel.add(cmbStatus);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
	
	public EditWindow(Task editTask) {
		this();
		
		this.task = editTask;
		
		txtName.setText(editTask.getName());
		txtExpire.setText(editTask.getExpireDate());
		cmbStatus.setSelectedItem(editTask.getStatusType());
		
	}
	
	protected void saveTask() {
		
		if (task == null) {
			
			if (!txtName.getText().isBlank() && !txtExpire.getText().isBlank()) {
				task = new Task(txtName.getText(),txtExpire.getText(),(Status)cmbStatus.getSelectedItem());
				finishedEdit = true;
				
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(this, "Every data must be filled","Error",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else {
			
			if (!cmbStatus.getSelectedItem().equals(task.getStatusType())) {
				
				task.setName(txtName.getText());
				task.setExpireDate(txtExpire.getText());
				task.setStatusType((Status)cmbStatus.getSelectedItem());
				try {
					TaskDB.modify(task);
					dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(this, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "Nothing's been modified","Error",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}

	
}
