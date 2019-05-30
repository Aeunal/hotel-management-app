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
			e.printStackTrace();
		}
		
	}
	
	public ResultSet getDataWithQuery(String query) {
		try {
			openConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//closeConnection();
		}
		return resultSet;
	}
	
	public ResultSet getDataWithQuery(String query, String parameter) {
		try {
			openConnection();
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, parameter);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//closeConnection();
		}
		return resultSet;
	}
	
	public ResultSet getDataWithQuery(String query, int parameter) {
		try {
			openConnection();
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setInt(1, parameter);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//closeConnection();
		}
		return resultSet;
	}
	
	public void useDB() {
		try {
			openConnection();
			statement = connect.createStatement();
			statement.executeQuery("use Hotel_Management");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		/*
						insert into address values (default, 38110, null, "Turkey", "Kayseri", "mail@hotel.com", "(0234)3253452");
						insert into person values (default, @@identity, "Mehmet", null, "Ateþ");
						insert into customer values (default, @@identity, now());
		 */
	}
	
	public void createTable(String query) {
		try {
			openConnection();
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
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
		String prefix = "?autoReconnect=true&useSSL=false";
		String connection = "jdbc:mysql://" +server + "/?" + database_name + "&user=" + username + "&password=" + password;
		connect = DriverManager
				.getConnection(url+prefix, username, password);

	}
	
	private void openConnectionAsUser() throws Exception {
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");

		// Setup the connection with the DB
		String username = "sqluser";
		String password = "sqluserpw";
		String database_name = "Hotel_Management";
		String server = "localhost";
		String url = "jdbc:mysql://localhost/Hotel_Management";
		String prefix = "?autoReconnect=true&useSSL=false";
		String connection = "jdbc:mysql://" +server + "/?" + database_name + "&user=" + username + "&password=" + password;
		connect = DriverManager
				.getConnection(url+prefix, username, password);

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
	
	public void insert(String query, int from, String[] statement, String[] types) throws Exception {
		openConnection();
		preparedStatement = connect.prepareStatement(query);
		// Parameters start with 1
		for(int i = 1; i < statement.length; i++)
			switch(types[i]) {
			default:
			break; case "date":
			case "string":
				preparedStatement.setString(i, statement[i]);
			break; case "bool":
				preparedStatement.setBoolean(i, statement[i].equals("true"));
			break; case "int":	
				preparedStatement.setInt(i, Integer.parseInt(statement[i]));
			}
		preparedStatement.executeUpdate();
		//closeConnection();
	}

	public void insertRoom(String text, String text2, String text3, String text4, String text5) {
		try {
			openConnection();
			preparedStatement = connect.prepareStatement(Query.insertRoom);
			// Parameters start with 1
			preparedStatement.setInt(1, Integer.parseInt(text));
			preparedStatement.setInt(2, Integer.parseInt(text2));
			preparedStatement.setInt(3, Integer.parseInt(text3));
			preparedStatement.setInt(4, Integer.parseInt(text4));
			preparedStatement.setInt(5, Integer.parseInt(text5));
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//closeConnection();
			
		}
	}
	
	public void updateRoom(int newNum, int newFloor, int newPrice, int newSize, int roomNum) {
		try {
			openConnection();
			preparedStatement = connect.prepareStatement(Query.updateRoom);
			preparedStatement.setInt(1, newNum);
			preparedStatement.setInt(2, newFloor);
			preparedStatement.setInt(3, newPrice);
			preparedStatement.setInt(4, newSize);
			preparedStatement.setInt(5, roomNum);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	public void deleteRoom(int RoomID) {
		try {
			openConnection();
			preparedStatement = connect.prepareStatement(Query.delRoom);
			preparedStatement.setInt(1, RoomID);
			preparedStatement.executeUpdate();
			//statement = connect.createStatement();
			//statement.execute(Query.manualDelRoom+RoomID);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void deleteTuple(String query, int selected) {
		try {
			openConnection();
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setInt(1, selected);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//closeConnection();
		}
	}
	
	public void runScript(String query) {
		try {
			openConnection();
			statement = connect.createStatement();
			statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void addUser(String[] text) {
		String FirstName = text[0];
		String MiddleName = text[1];
		String LastName = text[2];
		int Postal = Integer.parseInt(text[3]);
		String Address = text[4];
		String Country = text[5];
		String City = text[6];
		String Email = text[7];
		String Phone = text[8];
		try {
			openConnection();
			try {
				preparedStatement = connect.prepareStatement(Query.insertAddress);
				preparedStatement.setInt(1, Postal);
				preparedStatement.setString(2, Address);
				preparedStatement.setString(3, Country);
				preparedStatement.setString(4, City);
				preparedStatement.setString(5, Email);
				preparedStatement.setString(6, Phone);
				preparedStatement.executeUpdate();
				preparedStatement.executeUpdate();
			} catch (Exception e) {} finally {
				try {
					preparedStatement = connect.prepareStatement(Query.personInsert);
					preparedStatement.setString(1, FirstName);
					preparedStatement.setString(2, MiddleName);
					preparedStatement.setString(3, LastName);
					preparedStatement.executeUpdate();
				} catch (Exception e2) {} finally {
					preparedStatement = connect.prepareStatement(Query.customerInsert);
					preparedStatement.executeUpdate();
				}
			}
		} catch (Exception e) {} finally {
			closeConnection();
		}
	}
	
}
