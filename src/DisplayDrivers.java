import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DisplayDrivers extends JFrame {

    private JTable table;
    private JTextField driverNameField;
    private String[][] originalTripOfferings; // Store original data for filtering

    public DisplayDrivers(PomonaTransitSystemDatabase ptsdb) {
        setTitle("Driver's Trip Offerings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);

        // Fetch all trip offerings data
        originalTripOfferings = ptsdb.getAllTripOfferings();

        // Get current date
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.plusDays(7); // Get the end date, 7 days from now

        // Filter trip offerings for the next 7 days
        String[][] filteredTripOfferings = filterTripOfferings(originalTripOfferings, currentDate, endDate);

        // Create a panel for the driver name search bar
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel driverNameLabel = new JLabel("Driver Name:");
        driverNameField = new JTextField(20);
        searchBarPanel.add(driverNameLabel);
        searchBarPanel.add(driverNameField);
        add(searchBarPanel, BorderLayout.NORTH);

        // Create table model with filtered trip offerings data
        DefaultTableModel model = new DefaultTableModel(filteredTripOfferings,
                new String[]{"Trip Number", "Date", "Scheduled Start Time", "Scheduled Arrival Time", "Driver Name", "Bus ID", "Start Location", "Destination"});

        // Create a table and add it to a scroll pane
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        // Add action listener to driver name search field for filtering
        driverNameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String driverName = driverNameField.getText();
                filterByDriverName(driverName);
            }
        });

        setLocationRelativeTo(null); // Center the frame
    }

    private String[][] filterTripOfferings(String[][] tripOfferings, LocalDate startDate, LocalDate endDate) {
        // Create a DateTimeFormatter for parsing date and time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convert LocalDate objects to strings for comparison
        String startDateString = startDate.format(dateFormatter);
        String endDateString = endDate.format(dateFormatter);

        // Filter trip offerings for the next 7 days
        List<String[]> filteredTripOfferingsList = new ArrayList<>();
        for (String[] tripOffering : tripOfferings) {
            LocalDate tripDate = LocalDate.parse(tripOffering[1]); // Parse the date string
            if (!tripDate.isBefore(startDate) && !tripDate.isAfter(endDate)) {
                filteredTripOfferingsList.add(tripOffering);
            }
        }

        // Convert List<String[]> to String[][]
        String[][] filteredTripOfferings = new String[filteredTripOfferingsList.size()][];
        filteredTripOfferingsList.toArray(filteredTripOfferings);
        return filteredTripOfferings;
    }

    private void filterByDriverName(String driverName) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table

        for (String[] tripOffering : originalTripOfferings) {
            if (tripOffering[4].equalsIgnoreCase(driverName)) {
                model.addRow(tripOffering);
            }
        }
    }
}
