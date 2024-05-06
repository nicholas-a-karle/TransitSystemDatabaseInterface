import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DisplayStops extends JFrame {

    private JTable table;
    private JTextField tripNumberField;
    private PomonaTransitSystemDatabase database;

    public DisplayStops(PomonaTransitSystemDatabase ptsdb) {
        setTitle("Trip Stop Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLayout(new BorderLayout());

        database = ptsdb;

        // Panel for entering trip number
        JPanel tripNumberPanel = new JPanel(new FlowLayout());
        JLabel tripNumberLabel = new JLabel("Trip Number:");
        tripNumberField = new JTextField(10);
        tripNumberPanel.add(tripNumberLabel);
        tripNumberPanel.add(tripNumberField);
        add(tripNumberPanel, BorderLayout.NORTH);

        // Table to display trip stop information
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Display trip stop information when trip number field changes
        tripNumberField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                displayTripStopInfo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                displayTripStopInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events
            }
        });

        // Display trip stop information initially
        displayTripStopInfo();

        setLocationRelativeTo(null); // Center the frame
    }

    private void displayTripStopInfo() {
        String tripNumber = tripNumberField.getText();
        String[][] tripStopInfo = database.getTripStopInfo(tripNumber);

        if (tripStopInfo != null) {
            // Create table model with trip stop information
            DefaultTableModel model = new DefaultTableModel(tripStopInfo,
                    new String[]{"Trip Number", "Stop Number", "Sequence Number", "Driving Time"});
            table.setModel(model);
        } else {
            // Clear the table if trip number is not found
            DefaultTableModel model = new DefaultTableModel(new String[0][0],
                    new String[]{"Trip Number", "Stop Number", "Sequence Number", "Driving Time"});
            table.setModel(model);
            JOptionPane.showMessageDialog(this, "Trip Number not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
