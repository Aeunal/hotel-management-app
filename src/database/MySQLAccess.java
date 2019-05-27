package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public static void main(String[] args) {
		MySQLAccess m = new MySQLAccess();
		try {
			m.openConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet getDataWithQuery(String query) throws Exception {
		openConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery(query);
		return resultSet;
	}
	
	public void useDB() {
		try {
			openConnection();
			statement = connect.createStatement();
			statement.executeQuery("use Hotel_Management");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addExampleData() {
		try {
			insertAddress(
					"38050", 
					"Barbaros Mahallesi, Erkilet Blv. Sümer Kampüsü, 38080 Kocasinan/Kayseri",
					"Turkey", 
					"Kayseri", 
					"contact@agu.edu.tr",
					"(0352) 224 88 00");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createTable(String query) throws Exception {
		openConnection();
		preparedStatement = connect.prepareStatement(query);
		preparedStatement.executeUpdate();
	}
	
	public void insertAddress(String postCode, String AddressText, String Country, String City, String Email, String Phone) throws Exception {
		openConnection();
		preparedStatement = connect.prepareStatement(Query.insertAddress);
		// Parameters start with 1
		preparedStatement.setString(1, postCode);
		preparedStatement.setString(2, AddressText);
		preparedStatement.setString(3, Country);
		preparedStatement.setString(4, City);
		preparedStatement.setString(5, Email);
		preparedStatement.setString(6, Phone);
		preparedStatement.executeUpdate();
	}

	public void writeDataBaseToConsole() throws Exception {
		try {
			openConnection();
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from comments");
			writeResultSet(resultSet);
			resultSet = statement.executeQuery("select * from comments");
			writeMetaData(resultSet);
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}

	}

	private void deleteData(String table, String key, String value) throws Exception {
		// Remove again the insert comment
		preparedStatement = connect.prepareStatement("delete from "+table+" where "+key+"= ? ;");
		preparedStatement.setString(1, value);
		preparedStatement.executeUpdate();
	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query
		
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}
	
	public String[] getMetaKeys(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query
		int keyCount = resultSet.getMetaData().getColumnCount();
		String[] keys = new String[keyCount+1];
		for (int i = 1; i <= keyCount; i++) {
			keys[i] = resultSet.getMetaData().getColumnName(i);
		}
		return keys;
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		String leftAlignFormat = "| %.5s | %.10s | %.5s | %.10s | %.10s |%n";
		String separatrix = "+-------+------------+-------+------------+------------+%n";
		System.out.format(separatrix);
		System.out.format("| User  | Website    | Summ. | Date       | Comment    |%n");
		System.out.format(separatrix);
		while (resultSet.next()) {
			// It is possible to get the columns via name also possible to get the columns via the column number which starts at 1 e.g. resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summary = resultSet.getString("summary");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.format(leftAlignFormat, user, website, summary, date, comment);
			System.out.format(separatrix);
		}
	}

	private void openConnection() throws Exception {
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");

		// Setup the connection with the DB
		String username = "root";
		String password = "sqluserpw";
		String database_name = "Hotel_Management";
		String server = "localhost";
		String url = "jdbc:mysql://localhost/Hotel_Management";
		String connection = "jdbc:mysql://" +server + "/?" + database_name + "&user=" + username + "&password=" + password;
		connect = DriverManager
				.getConnection(url, username, password);

	}

	// You need to close the resultSet
	public void closeConnection() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	public void insertRoom(String text, String text2, String text3, String text4, String text5) throws Exception {
		openConnection();
		preparedStatement = connect.prepareStatement(Query.insertRoom);
		// Parameters start with 1
		preparedStatement.setInt(1, Integer.parseInt(text));
		preparedStatement.setInt(2, Integer.parseInt(text2));
		preparedStatement.setInt(3, Integer.parseInt(text3));
		preparedStatement.setInt(4, Integer.parseInt(text4));
		preparedStatement.setInt(5, Integer.parseInt(text5));
		preparedStatement.executeUpdate();
	}

}
