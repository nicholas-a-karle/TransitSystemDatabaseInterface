import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActualInserter extends JFrame {

    private JTextField tripNumberField;
    private JTextField dateField;
    private JTextField scheduledStartTimeField;
    private JTextField stopNumberField;
    private JTextField scheduledArrivalTimeField;
    private JTextField actualStartTimeField;
    private JTextField actualArrivalTimeField;
    private JTextField passengerInField;
    private JTextField passengerOutField;

    public ActualInserter(PomonaTransitSystemDatabase ptsdb) {
        setTitle("Record Actual Trip");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLayout(new GridLayout(10, 2));

        JLabel tripNumberLabel = new JLabel("Trip Number:");
        tripNumberField = new JTextField();
        add(tripNumberLabel);
        add(tripNumberField);

        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField();
        add(dateLabel);
        add(dateField);

        JLabel scheduledStartTimeLabel = new JLabel("Scheduled Start Time:");
        scheduledStartTimeField = new JTextField();
        add(scheduledStartTimeLabel);
        add(scheduledStartTimeField);

        JLabel stopNumberLabel = new JLabel("Stop Number:");
        stopNumberField = new JTextField();
        add(stopNumberLabel);
        add(stopNumberField);

        JLabel scheduledArrivalTimeLabel = new JLabel("Scheduled Arrival Time:");
        scheduledArrivalTimeField = new JTextField();
        add(scheduledArrivalTimeLabel);
        add(scheduledArrivalTimeField);

        JLabel actualStartTimeLabel = new JLabel("Actual Start Time:");
        actualStartTimeField = new JTextField();
        add(actualStartTimeLabel);
        add(actualStartTimeField);

        JLabel actualArrivalTimeLabel = new JLabel("Actual Arrival Time:");
        actualArrivalTimeField = new JTextField();
        add(actualArrivalTimeLabel);
        add(actualArrivalTimeField);

        JLabel passengerInLabel = new JLabel("Passengers In:");
        passengerInField = new JTextField();
        add(passengerInLabel);
        add(passengerInField);

        JLabel passengerOutLabel = new JLabel("Passengers Out:");
        passengerOutField = new JTextField();
        add(passengerOutLabel);
        add(passengerOutField);

        JButton recordButton = new JButton("Record");
        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tripNumber = Integer.parseInt(tripNumberField.getText());
                String date = dateField.getText();
                String scheduledStartTime = scheduledStartTimeField.getText();
                int stopNumber = Integer.parseInt(stopNumberField.getText());
                String scheduledArrivalTime = scheduledArrivalTimeField.getText();
                String actualStartTime = actualStartTimeField.getText();
                String actualArrivalTime = actualArrivalTimeField.getText();
                int passengerIn = Integer.parseInt(passengerInField.getText());
                int passengerOut = Integer.parseInt(passengerOutField.getText());

                ptsdb.recordActualTrip(tripNumber, date, scheduledStartTime, stopNumber, scheduledArrivalTime,
                        actualStartTime, actualArrivalTime, passengerIn, passengerOut);

                JOptionPane.showMessageDialog(ActualInserter.this, "Actual trip recorded successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            }
        });
        add(recordButton);

        setLocationRelativeTo(null); // Center the frame
    }

    private void clearFields() {
        tripNumberField.setText("");
        dateField.setText("");
        scheduledStartTimeField.setText("");
        stopNumberField.setText("");
        scheduledArrivalTimeField.setText("");
        actualStartTimeField.setText("");
        actualArrivalTimeField.setText("");
        passengerInField.setText("");
        passengerOutField.setText("");
    }
}
