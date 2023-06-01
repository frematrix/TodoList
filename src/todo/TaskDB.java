package todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaskDB {
	
	private static Connection conn;
	private static PreparedStatement stmt;
	
	public static void connect() throws SQLException {
		
		String sqlPath = "jdbc:mysql://localhost:3306/todolistDB?useSSL=false";
		try {
			conn = (Connection) DriverManager.getConnection(sqlPath, "root", "password");
		} catch (Exception e) {
			throw new SQLException("Connection is failed due to database error.");
		}
		
		
	}

	public static List<Task> readDatas() throws SQLException {
		
		List<Task> tasks = new ArrayList<Task>();
		
		try {
			stmt = conn.prepareStatement("SELECT * FROM todolisttable");
			
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				tasks.add(new Task(result.getString("name"), result.getString("expireDate"), Status.valueOf(result.getString("status").toString())));
			}
			result.close();
			
			return tasks;
			
			
		} catch (Exception e) {
			throw new SQLException("Reading is failed due to database error.");
		}
		
		
	}

	public static void newData(Task newTask) throws SQLException {
		
		try {
			stmt = conn.prepareStatement("INSERT INTO todolisttable (name,expireDate,status) VALUES (?,?,?)");
			
			stmt.setString(1, newTask.getName());
			stmt.setString(2, newTask.getExpireDate());
			stmt.setString(3, String.valueOf(newTask.getStatusType()));
			
			stmt.executeUpdate();
			stmt.clearParameters();
			
		} catch (Exception e) {
			throw new SQLException("Saving is failed due to database error.");
		}
		
	}

	public static void modify(Task task) throws SQLException {
		
		try {
			stmt = conn.prepareStatement("UPDATE todolisttable SET expireDate=?,status=? WHERE name=?");
			
			stmt.setString(1, task.getExpireDate());
			stmt.setString(2, String.valueOf(task.getStatusType()));
			stmt.setString(3, task.getName());
			
			stmt.executeUpdate();
			stmt.clearParameters();
			
		} catch (Exception e) {
			throw new SQLException("Modifying is failed due to database error.");
		}
		
	}

	public static void delete(Task deleteTask) throws SQLException {
		
		try {
			stmt = conn.prepareStatement("DELETE FROM todolisttable WHERE name=?");
			stmt.setString(1, deleteTask.getName());
			
			stmt.executeUpdate();
			stmt.clearParameters();
			
		} catch (Exception e) {
			throw new SQLException("Deleting is failed due to database error.");
		}
		
		
	}
	
	

}
