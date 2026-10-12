/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SimpleContactBook extends JFrame {

    ArrayList<String[]> contacts = new ArrayList<>();

    JTextField nameField, phoneField, emailField, searchField;
    DefaultTableModel model;
    JTable table;

    Color purple = new Color(105, 65, 190);
    Color background = new Color(240, 242, 255);

    public SimpleContactBook() {
        setTitle("Simple Contact Book - Aarti Kumari");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(background);
        main.setBorder(BorderFactory.createEmptyBorder(18, 20, 20, 20));
        setContentPane(main);

        // Colorful header
        JPanel header = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(
                    0, 0, new Color(80, 55, 180),
                    getWidth(), getHeight(), new Color(35, 160, 210)
                ));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        header.setPreferredSize(new Dimension(800, 75));
        header.setLayout(new GridLayout(2, 1));

        JLabel title = new JLabel(
            "MY SIMPLE CONTACT BOOK", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 25));

        JLabel subtitle = new JLabel(
            "Manage your contacts easily", SwingConstants.CENTER);
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        header.add(title);
        header.add(subtitle);
        main.add(header, BorderLayout.NORTH);

        // Main content area
        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setOpaque(false);
        main.add(content, BorderLayout.CENTER);

        // Contact form
        JPanel form = new JPanel(new GridLayout(2, 3, 12, 8));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(215, 210, 240)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        form.add(makeLabel("Full Name"));
        form.add(makeLabel("Phone Number"));
        form.add(makeLabel("Email Address"));

        form.add(nameField);
        form.add(phoneField);
        form.add(emailField);

        // Buttons
        JButton addButton = makeButton("Add Contact",
            new Color(35, 165, 100));
        JButton deleteButton = makeButton("Delete Selected",
            new Color(225, 65, 75));
        JButton searchButton = makeButton("Search",
            new Color(45, 115, 220));
        JButton showButton = makeButton("Show All",
            new Color(135, 75, 190));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        actions.setOpaque(false);
        actions.add(addButton);
        actions.add(deleteButton);

        searchField = new JTextField(18);

        JPanel searchPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 8));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(makeLabel("Search by name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showButton);

        JPanel upper = new JPanel(new BorderLayout(8, 8));
        upper.setOpaque(false);
        upper.add(form, BorderLayout.NORTH);
        upper.add(actions, BorderLayout.CENTER);
        upper.add(searchPanel, BorderLayout.SOUTH);

        // Contact table
        model = new DefaultTableModel(
            new String[]{"Name", "Phone Number", "Email Address"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(210, 220, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(225, 225, 240));
        table.setShowVerticalLines(false);

        table.getTableHeader().setBackground(purple);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(
            new Color(215, 210, 240)));

        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel tableTitle = makeLabel("Your Contacts");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 17));
        tableTitle.setForeground(purple);

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scroll, BorderLayout.CENTER);

        content.add(upper, BorderLayout.NORTH);
        content.add(tablePanel, BorderLayout.CENTER);

        // Button actions
        addButton.addActionListener(e -> addContact());
        deleteButton.addActionListener(e -> deleteContact());
        searchButton.addActionListener(e -> searchContact());
        showButton.addActionListener(e -> {
            searchField.setText("");
            showAllContacts();
        });

        setVisible(true);
    }

    JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(new Color(65, 60, 90));
        return label;
    }

    JButton makeButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        return button;
    }

    void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields.");
            return;
        }

        if (!phone.matches("[0-9]{10}")) {
            JOptionPane.showMessageDialog(this,
                "Enter a valid 10-digit phone number.");
            return;
        }

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            JOptionPane.showMessageDialog(this,
                "Enter a valid email address.");
            return;
        }

        contacts.add(new String[]{name, phone, email});
        showAllContacts();

        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");

        JOptionPane.showMessageDialog(this,
            "Contact added successfully!");
    }

    void deleteContact() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a contact from the table.");
            return;
        }

        String phone = model.getValueAt(row, 1).toString();

        contacts.removeIf(c -> c[1].equals(phone));
        showAllContacts();

        JOptionPane.showMessageDialog(this,
            "Contact deleted successfully!");
    }

    void searchContact() {
        String query = searchField.getText().trim().toLowerCase();

        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a name to search.");
            return;
        }

        model.setRowCount(0);

        for (String[] contact : contacts) {
            if (contact[0].toLowerCase().contains(query)) {
                model.addRow(contact);
            }
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No matching contact found.");
        }
    }

    void showAllContacts() {
        model.setRowCount(0);

        for (String[] contact : contacts) {
            model.addRow(contact);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleContactBook());
    }
}
