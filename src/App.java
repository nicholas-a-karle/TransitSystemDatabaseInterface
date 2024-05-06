import javax.swing.SwingUtilities;

public class App {
    private static final String DATABASE_NAME = "pomona_transit_system_database";
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) throws Exception {
        // Create and start the database
        System.out.println("Starting Program");
        PomonaTransitSystemDatabase database = new PomonaTransitSystemDatabase(DATABASE_NAME, JDBC_URL, USERNAME, PASSWORD);
        //database.addRandomData(99, 50, 150, 400, 400);

        // Create and display the GUI
        SwingUtilities.invokeLater(() -> {
            Display gui = new Display(database);
            gui.setVisible(true);
        });
    }
}

