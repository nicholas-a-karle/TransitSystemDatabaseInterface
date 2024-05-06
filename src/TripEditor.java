import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.NumberFormatter;

public class TripEditor extends JFrame {

    private JTextField tripNumberField;
    private JTextField dateField;
    private JTextField startTimeField;
    private JTextField arrivalTimeField;
    private JTextField driverNameField;
    private JFormattedTextField busIDField;

    private JButton deleteButton;
    private JButton addButton;
    private JButton changeDriverButton;
    private JButton changeBusButton;

    private PomonaTransitSystemDatabase database;

    public TripEditor(PomonaTransitSystemDatabase ptsdb) {
        setTitle("Editor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to avoid closing main frame
        setLayout(new GridLayout(6, 1));

        database = ptsdb;

        JPanel tripPanel = new JPanel(new GridLayout(1, 2));
        JLabel tripNumberLabel = new JLabel("Trip Number:");
        tripNumberField = new JTextField();
        tripPanel.add(tripNumberLabel);
        tripPanel.add(tripNumberField);
        add(tripPanel);

        JPanel datePanel = new JPanel(new GridLayout(1, 2));
        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField();
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        add(datePanel);

        JPanel startTimePanel = new JPanel(new GridLayout(1, 2));
        JLabel startTimeLabel = new JLabel("Scheduled Start Time:");
        startTimeField = new JTextField();
        startTimePanel.add(startTimeLabel);
        startTimePanel.add(startTimeField);
        add(startTimePanel);

        JPanel arrivalTimePanel = new JPanel(new GridLayout(1, 2));
        JLabel arrivalTimeLabel = new JLabel("Scheduled Arrival Time:");
        arrivalTimeField = new JTextField();
        arrivalTimePanel.add(arrivalTimeLabel);
        arrivalTimePanel.add(arrivalTimeField);
        add(arrivalTimePanel);

        JPanel driverPanel = new JPanel(new GridLayout(1, 2));
        JLabel driverNameLabel = new JLabel("Driver Name:");
        driverNameField = new JTextField();
        driverPanel.add(driverNameLabel);
        driverPanel.add(driverNameField);
        add(driverPanel);

        JPanel busPanel = new JPanel(new GridLayout(1, 2));
        JLabel busIDLabel = new JLabel("Bus ID:");
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        busIDField = new JFormattedTextField(formatter);
        busPanel.add(busIDLabel);
        busPanel.add(busIDField);
        add(busPanel);

        deleteButton = new JButton("Delete");
        addButton = new JButton("Add");
        changeDriverButton = new JButton("Change Driver");
        changeBusButton = new JButton("Change Bus");

        add(deleteButton);
        add(addButton);
        add(changeDriverButton);
        add(changeBusButton);

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTripOffering();
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTripOffering();
            }
        });

        changeDriverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeDriver();
            }
        });

        changeBusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeBus();
            }
        });

        setLocationRelativeTo(null); // Center the frame
    }

    private void deleteTripOffering() {
        String tripNumber = tripNumberField.getText();
        String date = dateField.getText();
        String startTime = startTimeField.getText();

        // Perform delete operation in the database
        try {
            database.deleteTripOffering(tripNumber, date, startTime);
            JOptionPane.showMessageDialog(this, "Trip Offering deleted successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting Trip Offering: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTripOffering() {
        String tripNumber = tripNumberField.getText();
        String date = dateField.getText();
        String startTime = startTimeField.getText();
        String arrivalTime = arrivalTimeField.getText();
        String driverName = driverNameField.getText();
        int busID = (int) busIDField.getValue(); // Get the integer value

        // Perform add operation in the database
        try {
            database.addTripOffering(tripNumber, date, startTime, arrivalTime, driverName, busID);
            JOptionPane.showMessageDialog(this, "Trip Offering added successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding Trip Offering: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeDriver() {
        String tripNumber = tripNumberField.getText();
        String date = dateField.getText();
        String startTime = startTimeField.getText();
        String newDriverName = driverNameField.getText();

        // Perform change driver operation in the database
        try {
            database.changeDriver(tripNumber, date, startTime, newDriverName);
            JOptionPane.showMessageDialog(this, "Driver changed successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error changing Driver: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeBus() {
        String tripNumber = tripNumberField.getText();
        String date = dateField.getText();
        String startTime = startTimeField.getText();
        int newBusID = (int) busIDField.getValue(); // Get the integer value

        // Perform change bus operation in the database
        try {
            database.changeBus(tripNumber, date, startTime, newBusID);
            JOptionPane.showMessageDialog(this, "Bus changed successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error changing Bus: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
