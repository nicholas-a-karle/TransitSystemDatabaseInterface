import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Display extends JFrame {

    private JTable table;
    private JScrollPane scrollPane;
    private PomonaTransitSystemDatabase database;
    private JTextField[] filterTextFields;

    public Display(PomonaTransitSystemDatabase ptsdb) {
        setTitle("Pomona Transit System Database Access");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        database = ptsdb;

        // Create text fields for each column header
        filterTextFields = new JTextField[6]; // StartLocationName and DestinationName plus the specified columns
        for (int i = 0; i < filterTextFields.length; i++) {
            filterTextFields[i] = new JTextField(10);
        }

        // Create a panel for text fields
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.setBackground(Color.LIGHT_GRAY);
        String[] columnNames = {"Scheduled Start Time", "Scheduled Arrival Time", "Driver ID", "Bus ID", "Start Location", "Destination"};
        for (int i = 0; i < columnNames.length; i++) {
            filterPanel.add(new JLabel(columnNames[i] + ": "));
            filterPanel.add(filterTextFields[i]);
        }
        add(filterPanel, BorderLayout.NORTH);

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();

        // Create a table and add it to a scroll pane
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch trip offerings data and populate the table
        displayTripOfferings();

        // Add action listeners to text fields for filtering
        for (int i = 0; i < filterTextFields.length; i++) {
            final int index = i;
            filterTextFields[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String text = filterTextFields[index].getText();
                    filterTableColumn(index, text);
                }
            });
        }

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a menu
        JMenu windowMenu = new JMenu("Options");

        // Create menu items
        JMenuItem editorMenuItem = new JMenuItem("Trip Editor");
        editorMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Editor GUI when "Editor" is selected
                openTripEditor();
            }
        });

        JMenuItem displayStopsMenuItem = new JMenuItem("Display Stops");
        displayStopsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Display Stops GUI when "Display Stops" is selected
                openDisplayStops();
            }
        });

        JMenuItem editor2MenuItem = new JMenuItem("Fleet Editor");
        editor2MenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Display Stops GUI when "Display Stops" is selected
                openFleetEditor();
            }
        });

        JMenuItem displayDriversMenuItem = new JMenuItem("Display Drivers");
        displayDriversMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Display Stops GUI when "Display Stops" is selected
                openDisplayDrivers();
            }
        });

        JMenuItem insertorMenuTime = new JMenuItem("Insert Actual Trip");
        insertorMenuTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open Display Stops GUI when "Display Stops" is selected
                openInsertActualTrip();
            }
        });

        // Add menu items to the menu
        windowMenu.add(editorMenuItem);
        windowMenu.add(editor2MenuItem);
        windowMenu.add(insertorMenuTime);
        windowMenu.add(displayStopsMenuItem);
        windowMenu.add(displayDriversMenuItem);
        

        // Add the menu to the menu bar
        menuBar.add(windowMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);
    }

    private void displayTripOfferings() {
        // Get trip offerings data from the database
        String[][] tripOfferings = null;
        try {
            tripOfferings = database.getAllTripOfferings();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Extract only the specified columns: ScheduledStartTime, ScheduledArrivalTime, DriverID, BusID, StartLocationName, DestinationName
        String[][] filteredTripOfferings = new String[tripOfferings.length][6];
        for (int i = 0; i < tripOfferings.length; i++) {
            filteredTripOfferings[i][0] = tripOfferings[i][2]; // ScheduledStartTime
            filteredTripOfferings[i][1] = tripOfferings[i][3]; // ScheduledArrivalTime
            filteredTripOfferings[i][2] = tripOfferings[i][4]; // DriverID
            filteredTripOfferings[i][3] = tripOfferings[i][5]; // BusID
            filteredTripOfferings[i][4] = tripOfferings[i][6]; // StartLocationName
            filteredTripOfferings[i][5] = tripOfferings[i][7]; // DestinationName
        }

        // Create table model with filtered trip offerings data
        DefaultTableModel model = new DefaultTableModel(filteredTripOfferings, new String[]{"Scheduled Start Time", "Scheduled Arrival Time", "Driver ID", "Bus ID", "Start Location", "Destination"});

        // Set the table model to the table
        table.setModel(model);
    }

    private void filterTableColumn(int columnIndex, String text) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        if (text.trim().length() != 0) {
            filters.add(RowFilter.regexFilter("(?i)" + text, columnIndex));
        }
        sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    private void openTripEditor() {
        // Open Editor GUI
        TripEditor tripEditor = new TripEditor(database);
        tripEditor.setVisible(true);
    }
    
    private void openFleetEditor() {
        // Open Editor GUI
        FleetEditor fleetEditor = new FleetEditor(database);
        fleetEditor.setVisible(true);
    }

    private void openDisplayStops() {
        // Open Display Stops GUI
        DisplayStops displayStops = new DisplayStops(database);
        displayStops.setVisible(true);
    }

    private void openDisplayDrivers() {
        // Open Display Stops GUI
        DisplayDrivers displayStops = new DisplayDrivers(database);
        displayStops.setVisible(true);
    }

    private void openInsertActualTrip() {
        // Open Display Stops GUI
        ActualInserter actualInserter = new ActualInserter(database);
        actualInserter.setVisible(true);
    }
}
