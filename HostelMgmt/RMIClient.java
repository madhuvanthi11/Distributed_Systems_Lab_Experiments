import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * RMI Client with Swing UI for searching room information
 */
public class RMIClient extends JFrame {
    
    private RoomService roomService;
    private JTextField roomNumberField;
    private JTextArea resultArea;
    private JButton searchButton;
    private JButton showAllButton;
    private JComboBox<String> roomDropdown;
    
    public RMIClient() {
        setTitle("Hostel Room Information Service");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Connect to RMI service
        connectToService();
        
        // Create UI components
        createUI();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Connect to the RMI service
     */
    private void connectToService() {
        try {
            // Get the registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Look up the service
            roomService = (RoomService) registry.lookup("RoomService");
            
            System.out.println("Connected to Room Service successfully");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Failed to connect to server. Please ensure the server is running.\n" + 
                e.getMessage(),
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    /**
     * Create the user interface
     */
    private void createUI() {
        // Top panel with title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(41, 128, 185));
        JLabel titleLabel = new JLabel("Hostel Room Information Service");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Center panel with search functionality
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Room"));
        
        JLabel label = new JLabel("Room Number:");
        roomNumberField = new JTextField(15);
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(46, 204, 113));
        searchButton.setForeground(Color.WHITE);
        
        // Add dropdown for quick selection
        try {
            List<String> rooms = roomService.getAllRoomNumbers();
            roomDropdown = new JComboBox<>(rooms.toArray(new String[0]));
            roomDropdown.insertItemAt("-- Select Room --", 0);
            roomDropdown.setSelectedIndex(0);
            roomDropdown.addActionListener(e -> {
                String selected = (String) roomDropdown.getSelectedItem();
                if (selected != null && !selected.startsWith("--")) {
                    roomNumberField.setText(selected);
                }
            });
            
            searchPanel.add(label);
            searchPanel.add(roomNumberField);
            searchPanel.add(new JLabel("or"));
            searchPanel.add(roomDropdown);
            searchPanel.add(searchButton);
            
        } catch (Exception e) {
            searchPanel.add(label);
            searchPanel.add(roomNumberField);
            searchPanel.add(searchButton);
        }
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        showAllButton = new JButton("Show All Rooms");
        showAllButton.setBackground(new Color(52, 152, 219));
        showAllButton.setForeground(Color.WHITE);
        buttonPanel.add(showAllButton);
        
        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Room Details"));
        
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Add event listeners
        searchButton.addActionListener(e -> searchRoom());
        showAllButton.addActionListener(e -> showAllRooms());
        
        roomNumberField.addActionListener(e -> searchRoom());
    }
    
    /**
     * Search for a specific room
     */
    private void searchRoom() {
        String roomNumber = roomNumberField.getText().trim();
        
        if (roomNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a room number",
                "Input Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Call remote method
            RoomInfo info = roomService.getRoomInfo(roomNumber);
            
            if (info == null) {
                resultArea.setText("Room not found: " + roomNumber + 
                    "\n\nPlease check the room number and try again.");
            } else {
                displayRoomInfo(info);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error searching room: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Display room information
     */
    private void displayRoomInfo(RoomInfo info) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("           ROOM DETAILS\n");
        sb.append("========================================\n\n");
        sb.append("Room Number: ").append(info.getRoomNumber()).append("\n\n");
        sb.append("Occupants:\n");
        for (int i = 0; i < info.getOccupantNames().size(); i++) {
            sb.append("  ").append(i + 1).append(". ")
              .append(info.getOccupantNames().get(i)).append("\n");
        }
        sb.append("\nWarden Contact: ").append(info.getWardenContact()).append("\n");
        sb.append("\n========================================");
        
        resultArea.setText(sb.toString());
    }
    
    /**
     * Show all available rooms
     */
    private void showAllRooms() {
        try {
            List<String> rooms = roomService.getAllRoomNumbers();
            
            StringBuilder sb = new StringBuilder();
            sb.append("========================================\n");
            sb.append("        ALL AVAILABLE ROOMS\n");
            sb.append("========================================\n\n");
            sb.append("Total Rooms: ").append(rooms.size()).append("\n\n");
            
            for (String room : rooms) {
                RoomInfo info = roomService.getRoomInfo(room);
                sb.append("Room ").append(room).append(": ");
                sb.append(info.getOccupantNames().size()).append(" occupant(s)\n");
            }
            
            sb.append("\n========================================\n");
            sb.append("Enter a room number above to view details");
            
            resultArea.setText(sb.toString());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error fetching rooms: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and show the client UI
        SwingUtilities.invokeLater(() -> new RMIClient());
    }
}