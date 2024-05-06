import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FleetEditor extends JFrame {

    private JTextField driverNameField;
    private JTextField driverTelephoneNumberField;
    private JTextField busIDField;
    private JTextField modelField;
    private JTextField yearField;

    private JButton addDriverButton;
    private JButton addBusButton;
    private JButton deleteBusButton;

    private PomonaTransitSystemDatabase database;

    public FleetEditor(PomonaTransitSystemDatabase ptsdb) {
        setTitle("Fleet Editor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to avoid closing main frame
        setLayout(new GridLayout(5, 1));

        database = ptsdb;

        JPanel driverPanel = new JPanel(new GridLayout(1, 3));
        JLabel driverNameLabel = new JLabel("Driver Name:");
        driverNameField = new JTextField();
        JLabel driverTelephoneNumberLabel = new JLabel("Driver Telephone Number:");
        driverTelephoneNumberField = new JTextField();
        driverPanel.add(driverNameLabel);
        driverPanel.add(driverNameField);
        driverPanel.add(driverTelephoneNumberLabel);
        driverPanel.add(driverTelephoneNumberField);
        add(driverPanel);

        JPanel busPanel = new JPanel(new GridLayout(1, 3));
        JLabel busIDLabel = new JLabel("Bus ID:");
        busIDField = new JTextField();
        JLabel modelLabel = new JLabel("Model:");
        modelField = new JTextField();
        JLabel yearLabel = new JLabel("Year:");
        yearField = new JTextField();
        busPanel.add(busIDLabel);
        busPanel.add(busIDField);
        busPanel.add(modelLabel);
        busPanel.add(modelField);
        busPanel.add(yearLabel);
        busPanel.add(yearField);
        add(busPanel);

        addDriverButton = new JButton("Add Driver");
        addBusButton = new JButton("Add Bus");
        deleteBusButton = new JButton("Delete Bus");

        add(addDriverButton);
        add(addBusButton);
        add(deleteBusButton);

        addDriverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDriver();
            }
        });

        addBusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBus();
            }
        });

        deleteBusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBus();
            }
        });

        setLocationRelativeTo(null); // Center the frame
    }

    private void addDriver() {
        String driverName = driverNameField.getText();
        String driverTelephoneNumber = driverTelephoneNumberField.getText();

        // Perform add driver operation in the database
        try {
            database.addDriver(driverName, driverTelephoneNumber);
            JOptionPane.showMessageDialog(this, "Driver added successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding Driver: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addBus() {
        int busID = Integer.parseInt(busIDField.getText());
        String model = modelField.getText();
        int year = Integer.parseInt(yearField.getText());

        // Perform add bus operation in the database
        try {
            database.addBus(busID, model, year);
            JOptionPane.showMessageDialog(this, "Bus added successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding Bus: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBus() {
        int busID = Integer.parseInt(busIDField.getText());

        // Perform delete bus operation in the database
        try {
            database.deleteBus(busID);
            JOptionPane.showMessageDialog(this, "Bus deleted successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting Bus: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
