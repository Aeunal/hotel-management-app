package database;

public class Query {

	public static Table Room = new Table();
	
	public static String
				insertAddress = "INSERT INTO Address VALUES (default, ?, ?, ?, ?, ?, ?)",
				insertRoom = "INSERT INTO Room VALUES (default, ?, ?, ?, ?, ?)",
				roomValues = "id int int int int int",
				insertHotel = "INSERT INTO Hotel VALUES (default, ?, ?, ?)",
				hotelValues = "int int string int",
	
				insertEmployee = "INSERT INTO Employee VALUES (default, ?, ?, ?, ?, ?)",
				employeeValues = "int int int int int int int",
				
				userView = "select CustomerID, PostalCode, Country, City, Email, Phone, FirstName, MiddleName, LastName"
						+ " FROM Customer"
						+ " NATURAL JOIN Person, Address",
				userInsert = "INSERT ignore INTO Address VALUES (default, ?, null, ?, ?, ?, ?);"
						+ " INSERT ignore INTO Person VALUES (default, 2, ?, ?, ?);"
						+ " INSERT ignore INTO Customer VALUES (default, 2, now());",
				userValues = "int int string string string string string string string",
				
				addressInsert = "INSERT ignore INTO Address VALUES (default, ?, null, ?, ?, ?, ?)",
				personInsert = "INSERT ignore INTO Person VALUES (default, @@identity, ?, ?, ?)",
				customerInsert = "INSERT ignore INTO Customer VALUES (default, @@identity, now())",
				
				payView = "select PaymetID, CustomerID, Amount from payment",
				payInsert = "insert ignore into payment values (default, ?, true, now(), ?)",
				payValues = "id int int",
				
				resView = "select ReservationID, CustomerID, RoomID, Arrival, Breakfast, Nights from reservation",
				resInsert = "insert ignore into reservation values (default, ?, ?, STR_TO_DATE(?,'%m-%d-%y'), FROM_UNIXTIME(now()), false, ?, ?)",
				resValues = "id int int string bool int"
				
	;
	
	public static String 
		getAddresses = "SELECT * "
				+ " FROM Address",
		
		getHotels = "SELECT Name, StarRate, City, Country, Phone, AddressText "
				+ " FROM Hotel "
				+ " NATURAL LEFT OUTER JOIN Address"
				+ " ORDER BY HotelID",
		
		getCustomers = "SELECT CustomerID, FirstName, MiddleName, LastName, RegTime, Email, Phone, City, PostalCode"
				+ " FROM Customer "
				+ " NATURAL LEFT OUTER JOIN Person"
				+ " NATURAL JOIN Address"
				+ " GROUP BY CustomerID"
				+ " ORDER BY CustomerID",
		
		getEmployees = "SELECT EmployeeID, FirstName, MiddleName, LastName, Salary, TaskType, FacilityName, Name, Phone"
				+ " FROM Employee"
				+ " NATURAL Left OUTER JOIN Task"
				+ " NATURAL left OUTER JOIN Person"
				+ " NATURAL left outer join Facility"
				+ " NaTural left outer join FacilityType"
				+ " NATURAL JOIN Address"
				+ " GROUP BY EmployeeID"
				+ " ORDER BY EmployeeID",
		
		getReservations = "SELECT ReservationID, Email, RoomNumber, Nights, ReserveTime, Arrival, Checkout, BreakFast"
				+ " FROM Reservation"
				+ " NATURAL LEFT OUTER JOIN Customer, Room, Person, Address"
				+ " GROUP BY ReservationID"
				+ " ORDER BY ReservationID",
		
		getPayments = "SELECT PaymetID, LastName, Phone, RegTime, Paid, Amount, PayTime"
				+ " FROM Payment"
				+ " NATURAL LEFT OUTER JOIN Customer, Person, Address"
				+ " GROUP BY PaymetID"
				+ " ORDER BY PaymetID",
		
		getRooms = "SELECT Name, RoomNumber, FloorNumber, PricePerNight, maxPersons"
				+ " FROM Room"
				+ " NATURAL INNER JOIN Hotel",
						
				
		getRoomsForInsert = "SELECT * FROM Room",
		getHotelForInsert = "SELECT * FROM Hotel",
		getEmployeeForInsert = "Select * FROM Employee"
	;
	
	public static String 

	searchRoomsbyHotelID = "SELECT Name, RoomNumber, FloorNumber, PricePerNight, maxPersons"
			+ " FROM Room"
			+ " NATURAL INNER JOIN Hotel"
			+ " WHERE Room.HotelID = ?",
			
	searchRoomsbyHotelName = "SELECT Name, RoomNumber, FloorNumber, PricePerNight, maxPersons"
			+ " FROM Room"
			+ " NATURAL INNER JOIN Hotel"
			+ " WHERE Name = ?",
			

	ManualSearchFixturesbyRoomNumber = "SELECT RoomNumber, Name, Amount"
			+ " FROM Fixture"
			+ " NATURAL INNER JOIN Room"
			+ " WHERE RoomNumber = "
			
	;
						
	public static String 
		updateEmail = "UPDATE Address"
				+ " SET Email = '?'"
				+ " WHERE (AddressID = '?');",
		
		updateCheckout = "UPDATE Reservations"
				+ " SET Checkout = 'true'"
				+ " WHERE (ReservationID = '?');",
				
		updateRoom =
				" UPDATE Room" + 
				" SET RoomNumber=?, FloorNumber=?, PricePerNight=?, maxPersons=?" + 
				" WHERE RoomID=(SELECT RoomID FROM (Select * from ROOM) as template WHERE RoomNumber = ?);";
;
	
	public static String 
	delRoom = "DELETE FROM Room"
			+ " WHERE RoomNumber = ?",
			
	delCustomer = "DELETE FROM Customer WHERE CustomerID = ?",
	manuelDelCustomer = "DELETE FROM Customer WHERE CustomerID = ",
	
	delEmployee = "DELETE FROM Employee WHERE EmployeeID = ?",
	manuelDelEmployee = "DELETE FROM Employee WHERE EmployeeID = ",

	delReservation = "DELETE FROM Reservation WHERE ReservationID = ?",
	manuelDelReservation = "DELETE FROM Reservation WHERE ReservationID = ",
	
	delPayment = "DELETE FROM Payment WHERE PaymetID = ?",
	manuelDelPayment = "DELETE FROM Payment WHERE PaymetID = ",
	
	manualDelRoom = "DELETE FROM Room WHERE RoomNumber = ";
	
	static class Table {
		Table () {
			
		}
		
	}

}








