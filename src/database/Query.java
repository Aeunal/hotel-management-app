package database;

public class Query {

	public static String
		insertAddress = "insert into Address values (default, ?, ?, ?, ?, ?, ?)",
		insertRoom = "insert into Room values (default, ?, ?, ?, ?, ?)";
	
	public static String 
		getAddresses = "select * from Address",
		getHotels = "select * from Hotel",
		getCustomers = "select * from Customer natural join Person",
		getEmployees = "select * from Employee natural join Person",
		getReservations = "select * from Reservation",
		getPayments = "select * from Payment",
		getRooms = "select * from Room";
	
}








