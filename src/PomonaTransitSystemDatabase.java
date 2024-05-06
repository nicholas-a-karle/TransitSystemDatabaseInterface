import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PomonaTransitSystemDatabase extends Database {

    public PomonaTransitSystemDatabase (String databaseName, String jdbc_url, String username, String password) throws Exception {
        super(databaseName, jdbc_url, username, password);
        initializeTables();
    }

    public void addRandomData(int num_drivers, int num_buses, int num_stops, int num_trips, int num_offerings) {

        Random random = new Random();

        String[] firstNames = {
            "Alice",
            "Bob",
            "Charlie",
            "David",
            "Emily",
            "Frank",
            "Grace",
            "Hannah",
            "Isaac",
            "Julia",
            "Liam",
            "Olivia",
            "Noah",
            "Emma",
            "William",
            "Ava",
            "James",
            "Isabella",
            "Benjamin",
            "Sophia",
            "Alexander",
            "Charlotte",
            "Michael",
            "Amelia",
            "Daniel",
            "Mia",
            "Ethan",
            "Harper",
            "Jacob",
            "Evelyn",
            "Aiden",
            "Chloe",
            "Jackson",
            "Madison",
            "Mason",
            "Abigail",
            "Elijah",
            "Ella",
            "Ethan",
            "Emily",
            "Lucas",
            "Avery",
            "Logan",
            "Addison",
            "Carter",
            "Aubrey",
            "Alexander",
            "Lillian",
            "Sebastian",
            "Sofia"
        };
        String[] lastNames = {
            "Smith",
            "Johnson",
            "Williams",
            "Jones",
            "Brown",
            "Davis",
            "Miller",
            "Wilson",
            "Moore",
            "Taylor",
            "Anderson",
            "Thomas",
            "Jackson",
            "White",
            "Harris",
            "Martin",
            "Thompson",
            "Garcia",
            "Martinez",
            "Robinson",
            "Clark",
            "Rodriguez",
            "Lewis",
            "Lee",
            "Walker",
            "Hall",
            "Allen",
            "Young",
            "King",
            "Gonzalez",
            "Hernandez",
            "Nelson",
            "Mitchell",
            "Perez",
            "Roberts",
            "Turner",
            "Phillips",
            "Campbell",
            "Parker",
            "Evans",
            "Edwards",
            "Collins",
            "Stewart",
            "Sanchez",
            "Morris",
            "Rogers",
            "Reed",
            "Cook",
            "Morgan"
        };

        // add drivers
        ArrayList<String> drivers = new ArrayList<String>();
        for (int i = 0; i < num_drivers; ++i) {
            String driverName = firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];

            String driverTelephoneNumber = "(";
            driverTelephoneNumber += (1 + random.nextInt(8));
            driverTelephoneNumber += (0 + random.nextInt(9));
            driverTelephoneNumber += (0 + random.nextInt(9));
            driverTelephoneNumber += ")";
            driverTelephoneNumber += (1 + random.nextInt(8));
            driverTelephoneNumber += (0 + random.nextInt(9));
            driverTelephoneNumber += (0 + random.nextInt(9));
            driverTelephoneNumber += "-";
            driverTelephoneNumber += (1 + random.nextInt(8));
            driverTelephoneNumber += (0 + random.nextInt(9));
            driverTelephoneNumber += (0 + random.nextInt(9));
            driverTelephoneNumber += (0 + random.nextInt(9));

            addDriver(driverName, driverTelephoneNumber);
            drivers.add(driverName);
        }

        String[] busModels = {
            "Volvo B7RLE",
            "Mercedes-Benz Sprinter",
            "MAN Lion's City",
            "Scania OmniCity",
            "Neoplan Tourliner",
            "Van Hool CX45",
            "Iveco Crossway",
            "Temsa Avenue",
            "Alexander Dennis Enviro500",
            "Solaris Urbino",
            "Dennis Dart",
            "Setra S 416 GT-HD",
            "Volvo 9700",
            "MCI D4500",
            "Nova Bus LFS",
            "Van Hool A330",
            "Gillig Low Floor",
            "New Flyer Xcelsior",
            "Optare Solo",
            "Alexander Dennis Enviro200",
            "Blue Bird All American",
            "Irizar i6",
            "MAN Lion's Regio",
            "Prevost X3-45",
            "Wright StreetLite",
            "Neoplan Cityliner",
            "Iveco Magelys",
            "Optare Versa",
            "Scania Interlink",
            "BYD K9"
        };

        // add busses
        ArrayList<Integer> buses = new ArrayList<Integer>();
        for (int i = 0; i < num_drivers; ++i) {
            int busID = i;
            String model = busModels[random.nextInt(busModels.length)];
            int year = random.nextInt(2025 - 1990) + 1990;

            addBus(busID, model, year);
            buses.add(busID);
        }

        String[] streetNames = {
            "Main Street",
            "Elm Street",
            "Maple Avenue",
            "Oak Street",
            "Cedar Lane",
            "Pine Street",
            "Washington Boulevard",
            "Park Avenue",
            "Highland Drive",
            "Broadway",
            "Lakeview Drive",
            "Sunset Boulevard",
            "Church Street",
            "Riverside Drive",
            "Greenwood Avenue",
            "Hillcrest Road",
            "Smith Street",
            "Spring Street",
            "Chestnut Street",
            "Forest Avenue",
            "Sycamore Lane",
            "Meadowbrook Drive",
            "Westminster Road",
            "Lincoln Street",
            "Magnolia Avenue",
            "Cherry Blossom Lane",
            "Victoria Street",
            "Willow Court",
            "Crescent Avenue",
            "Birchwood Drive",
            "River Street",
            "Golden Gate Avenue",
            "Sunrise Drive",
            "Fairview Terrace",
            "Linden Avenue",
            "Cambridge Street",
            "Orchard Lane",
            "Parkside Avenue",
            "Morningside Drive",
            "Harbor View Road"
        };

        // add stop
        ArrayList<String> stops = new ArrayList<String>();
        for (int i = 0; i < num_stops; ++i) {
            int stopNumber = i;

            String addressNumber = "" + random.nextInt(990999) + 1000;
            String addressStreet = streetNames[random.nextInt(streetNames.length)];
            String stopAddress = addressNumber + " " + addressStreet;

            addStop(stopNumber, stopAddress);
            stops.add(stopAddress);
        }
        
        // addTrips
        for (int i = 0; i < num_trips; ++i) {
            int tripNumber = i;
            String startLocationName = stops.get(random.nextInt(stops.size()));
            String destinationName = stops.get(random.nextInt(stops.size()));
            while (destinationName == startLocationName) {
                System.out.println("repickings");
                destinationName = stops.get(random.nextInt(stops.size()));
            }

            addTrip(tripNumber, startLocationName, destinationName);
            System.out.println("trip # " + i);
        }

        // addTripOfferings
        LocalDate currentDate = LocalDate.now();
        
        for (int i = 0; i < num_offerings; ++i) {
            int tripNumber = random.nextInt(num_trips);
            LocalDate date = currentDate.plus(ThreadLocalRandom.current().nextLong(14), ChronoUnit.DAYS);

            LocalTime scheduledStartTime = LocalTime.of(
                ThreadLocalRandom.current().nextInt(0, 18), // Hour
                ThreadLocalRandom.current().nextInt(0, 60), // Minute
                0
            );
            LocalTime scheduledArrivalTime = scheduledStartTime.plusHours(ThreadLocalRandom.current().nextInt(1, 6));
            
            String driverName = drivers.get(random.nextInt(drivers.size()));
            int busID = buses.get(random.nextInt(buses.size()));

            addTripOffering(tripNumber, date, scheduledStartTime, scheduledArrivalTime, driverName, busID);
        }

        // TripStopInfo ( TripNumber, StopNumber, SequenceNumber, DrivingTime)

        for (int i = 0; i < num_offerings; ++i) {
            int num_mid_stops = random.nextInt(9);
            for (int k = 0; k < num_mid_stops; ++k) {
                LocalTime drivingTime = LocalTime.of(ThreadLocalRandom.current().nextInt(0, 2), ThreadLocalRandom.current().nextInt(1, 59));
                int stopNumber = random.nextInt(num_stops);
                addTripStopInfo(i, stopNumber, k, drivingTime);
            }
        }
    }

    private void addTrip(int tripNumber, String startLocationName, String destinationName) {
        String sqlInsert = "INSERT INTO Trip (TripNumber, StartLocationName, DestinationName) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setInt(1, tripNumber);
            preparedStatement.setString(2, startLocationName);
            preparedStatement.setString(3, destinationName);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Trip added. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addTripOffering(int tripNumber, LocalDate date, LocalTime scheduledStartTime, LocalTime scheduledArrivalTime, String driverName, int busID) {
        String sqlInsert = "INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setInt(1, tripNumber);
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));
            preparedStatement.setTime(3, java.sql.Time.valueOf(scheduledStartTime));
            preparedStatement.setTime(4, java.sql.Time.valueOf(scheduledArrivalTime));
            preparedStatement.setString(5, driverName);
            preparedStatement.setInt(6, busID);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Trip offering added. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addStop(int stopNumber, String stopAddress) {
        String sqlInsert = "INSERT INTO Stop (StopNumber, StopAddress) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setInt(1, stopNumber);
            preparedStatement.setString(2, stopAddress);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Stop added. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addActualTripStopInfo(int tripNumber, LocalDate date, LocalTime scheduledStartTime, int stopNumber, LocalTime scheduledArrivalTime, LocalTime actualStartTime, LocalTime actualArrivalTime, int numberOfPassengerIn, int numberOfPassengerOut) {
        String sqlInsert = "INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setInt(1, tripNumber);
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));
            preparedStatement.setTime(3, java.sql.Time.valueOf(scheduledStartTime));
            preparedStatement.setInt(4, stopNumber);
            preparedStatement.setTime(5, java.sql.Time.valueOf(scheduledArrivalTime));
            preparedStatement.setTime(6, java.sql.Time.valueOf(actualStartTime));
            preparedStatement.setTime(7, java.sql.Time.valueOf(actualArrivalTime));
            preparedStatement.setInt(8, numberOfPassengerIn);
            preparedStatement.setInt(9, numberOfPassengerOut);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Actual trip stop info added. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addTripStopInfo(int tripNumber, int stopNumber, int sequenceNumber, LocalTime drivingTime) {
        String sqlInsert = "INSERT INTO TripStopInfo (TripNumber, StopNumber, SequenceNumber, DrivingTime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setInt(1, tripNumber);
            preparedStatement.setInt(2, stopNumber);
            preparedStatement.setInt(3, sequenceNumber);
            preparedStatement.setTime(4, java.sql.Time.valueOf(drivingTime));
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Trip stop info added. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    


    private void initializeTables() {
        // Trip ( TripNumber, StartLocationName, DestinationName)
        createTable("Trip", new String[] {
            "TripNumber INT PRIMARY KEY",
            "StartLocationName VARCHAR(255) NOT NULL",
            "DestinationName VARCHAR(255) NOT NULL"
        });
    
        // TripOffering ( TripNumber, Date, ScheduledStartTime, SecheduledArrivalTime, DriverName, BusID)
    
        createTable("TripOffering", new String[] {
            "TripNumber INT",
            "Date DATE",
            "ScheduledStartTime TIME",
            "ScheduledArrivalTime TIME",
            "DriverName VARCHAR(255)",
            "BusID INT",
            "FOREIGN KEY (TripNumber) REFERENCES Trip(TripNumber)"
        });
    
        // Bus ( BusID, Model, Year)
    
        createTable("Bus", new String[] {
            "BusID INT PRIMARY KEY",
            "Model VARCHAR(255)",
            "Year INT"
        });
    
        // Driver( DriverName, DriverTelephoneNumber)
    
        createTable("Driver", new String[] {
            "DriverName VARCHAR(255) PRIMARY KEY",
            "DriverTelephoneNumber VARCHAR(255)"
        });
    
        // Stop (StopNumber, StopAddress)
    
        createTable("Stop", new String[] {
            "StopNumber INT PRIMARY KEY",
            "StopAddress VARCHAR(255)"
        });
    
        // ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, SecheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOf PassengerOut)
    
        createTable("ActualTripStopInfo", new String[] {
            "TripNumber INT",
            "Date DATE",
            "ScheduledStartTime TIME",
            "StopNumber INT",
            "ScheduledArrivalTime TIME",
            "ActualStartTime TIME",
            "ActualArrivalTime TIME",
            "NumberOfPassengerIn INT",
            "NumberOfPassengerOut INT",
            "FOREIGN KEY (TripNumber) REFERENCES Trip(TripNumber)",
            "FOREIGN KEY (StopNumber) REFERENCES Stop(StopNumber)"
        });
    
        // TripStopInfo ( TripNumber, StopNumber, SequenceNumber, DrivingTime)
    
        createTable("TripStopInfo", new String[] {
            "TripNumber INT",
            "StopNumber INT",
            "SequenceNumber INT",
            "DrivingTime TIME",
            "FOREIGN KEY (TripNumber) REFERENCES Trip(TripNumber)",
            "FOREIGN KEY (StopNumber) REFERENCES Stop(StopNumber)"
        });
    }

    public String[] getSchedules(String startLocationName, String destinationName) {
        String[] schedules = null;
        String sqlQuery = "SELECT ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID FROM TripOffering "
                        + "JOIN Trip ON Trip.TripNumber = TripOffering.TripNumber "
                        + "WHERE StartLocationName = ? AND DestinationName = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, startLocationName);
            preparedStatement.setString(2, destinationName);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Count number of rows in result set
            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            schedules = new String[rowCount];

            int index = 0;
            while (resultSet.next()) {
                String startTime = resultSet.getString("ScheduledStartTime");
                String arrivalTime = resultSet.getString("ScheduledArrivalTime");
                String driverID = resultSet.getString("DriverName");
                int busID = resultSet.getInt("BusID");

                schedules[index++] = "Start Time: " + startTime + ", Arrival Time: " + arrivalTime
                        + ", Driver ID: " + driverID + ", Bus ID: " + busID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }

    public String[] getWeeklySchedule(String driver, String date) {
        String[] schedule = null;

        // Parse the provided date string to LocalDate
        LocalDate startDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

        // Calculate the end date (7 days after the start date)
        LocalDate endDate = startDate.plusDays(7);

        // SQL query to retrieve trip offerings for the driver within the next 7 days
        String sqlQuery = "SELECT * FROM TripOffering WHERE DriverName = ? AND Date BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, driver);
            preparedStatement.setString(2, startDate.toString());
            preparedStatement.setString(3, endDate.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            // Count number of rows in result set
            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            schedule = new String[rowCount];

            int index = 0;
            while (resultSet.next()) {
                String tripNumber = resultSet.getString("TripNumber");
                String tripDate = resultSet.getString("Date");
                String scheduledStartTime = resultSet.getString("ScheduledStartTime");
                String scheduledArrivalTime = resultSet.getString("ScheduledArrivalTime");
                String busID = resultSet.getString("BusID");

                // Build the schedule string
                schedule[index++] = "Trip Number: " + tripNumber + ", Date: " + tripDate +
                        ", Scheduled Start Time: " + scheduledStartTime + ", Scheduled Arrival Time: " + scheduledArrivalTime +
                        ", Bus ID: " + busID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedule;
    }

    public String[][] getTripStopInfo(String tripNumber) {
        List<String[]> tripStopInfoList = new ArrayList<>();

        String sqlQuery = "SELECT * FROM TripStopInfo WHERE TripNumber = ? ORDER BY SequenceNumber";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, tripNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String tripNum = resultSet.getString("TripNumber");
                String stopNum = resultSet.getString("StopNumber");
                String sequenceNum = resultSet.getString("SequenceNumber");
                String drivingTime = resultSet.getString("DrivingTime");

                // Create a string array to store trip stop information
                String[] tripStopInfo = {tripNum, stopNum, sequenceNum, drivingTime};
                tripStopInfoList.add(tripStopInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Convert List<String[]> to String[][]
        String[][] tripStopInfo = new String[tripStopInfoList.size()][];
        tripStopInfoList.toArray(tripStopInfo);
        System.out.println(tripStopInfo);
        return tripStopInfo;
    }
    
    public String[][] getAllTripOfferings() {
        List<String[]> tripOfferingsList = new ArrayList<>();
    
        String sqlQuery = 
        "SELECT TripOffering.TripNumber, TripOffering.Date, TripOffering.ScheduledStartTime, TripOffering.ScheduledArrivalTime" + 
        ", TripOffering.DriverName, TripOffering.BusID, Trip.StartLocationName, Trip.DestinationName " +
        "FROM TripOffering " +
        "JOIN Trip ON TripOffering.TripNumber = Trip.TripNumber ";

    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                String tripNumber = resultSet.getString("TripNumber");
                String date = resultSet.getString("Date");
                String scheduledStartTime = resultSet.getString("ScheduledStartTime");
                String scheduledArrivalTime = resultSet.getString("ScheduledArrivalTime");
                String driverName = resultSet.getString("DriverName");
                String busID = resultSet.getString("BusID");
                String startLocation = resultSet.getString("StartLocationName");
                String destination = resultSet.getString("DestinationName");
    
                // Create a string array to store trip offering details
                String[] tripOffering = {tripNumber, date, scheduledStartTime, scheduledArrivalTime, driverName, busID, startLocation, destination};
                tripOfferingsList.add(tripOffering);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Sort the list by date and time (scheduledStartTime)
        tripOfferingsList.sort(Comparator.comparing((String[] tripOffering) -> LocalDateTime.parse(tripOffering[1] + "T" + tripOffering[2])).thenComparing(tripOffering -> tripOffering[3]));
    
        // Convert List<String[]> to String[][]
        String[][] tripOfferings = new String[tripOfferingsList.size()][];
        tripOfferingsList.toArray(tripOfferings);
        return tripOfferings;
    }

    public void deleteTripOffering(String tripNumber, String date, String scheduledStartTime) {
        String sqlDelete = "DELETE FROM TripOffering WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setString(1, tripNumber);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, scheduledStartTime);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Trip offering deleted. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addTripOffering(String tripNumber, String date, String scheduledStartTime, String scheduledArrivalTime, String driverName, int busID) {
        String sqlInsert = "INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, tripNumber);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, scheduledStartTime);
            preparedStatement.setString(4, scheduledArrivalTime);
            preparedStatement.setString(5, driverName);
            preparedStatement.setInt(6, busID);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Trip offering added. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void changeDriver(String tripNumber, String date, String scheduledStartTime, String newDriverName) {
        String sqlUpdate = "UPDATE TripOffering SET DriverName = ? WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, newDriverName);
            preparedStatement.setString(2, tripNumber);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, scheduledStartTime);
    
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Driver changed for trip offering. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void changeBus(String tripNumber, String date, String scheduledStartTime, int newBusID) {
        String sqlUpdate = "UPDATE TripOffering SET BusID = ? WHERE TripNumber = ? AND Date = ? AND ScheduledStartTime = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setInt(1, newBusID);
            preparedStatement.setString(2, tripNumber);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, scheduledStartTime);
    
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Bus changed for trip offering. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDriver(String driverName, String driverTelephoneNumber) {
        String sqlInsert = "INSERT INTO Driver (DriverName, DriverTelephoneNumber) VALUES (?, ?)";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, driverName);
            preparedStatement.setString(2, driverTelephoneNumber);
    
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Driver added. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addBus(int busID, String model, int year) {
        String sqlInsert = "INSERT INTO Bus (BusID, Model, Year) VALUES (?, ?, ?)";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setInt(1, busID);
            preparedStatement.setString(2, model);
            preparedStatement.setInt(3, year);
    
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Bus added. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteBus(int busID) {
        String sqlDelete = "DELETE FROM Bus WHERE BusID = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setInt(1, busID);
    
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Bus deleted. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void recordActualTrip(int tripNumber, String date, String scheduledStartTime, int stopNumber, String scheduledArrivalTime,
                             String actualStartTime, String actualArrivalTime, int numberOfPassengerIn, int numberOfPassengerOut) {
    String sqlInsert = "INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, " +
            "ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
        preparedStatement.setInt(1, tripNumber);
        preparedStatement.setString(2, date);
        preparedStatement.setString(3, scheduledStartTime);
        preparedStatement.setInt(4, stopNumber);
        preparedStatement.setString(5, scheduledArrivalTime);
        preparedStatement.setString(6, actualStartTime);
        preparedStatement.setString(7, actualArrivalTime);
        preparedStatement.setInt(8, numberOfPassengerIn);
        preparedStatement.setInt(9, numberOfPassengerOut);

        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Actual trip recorded. Rows affected: " + rowsAffected);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
}
