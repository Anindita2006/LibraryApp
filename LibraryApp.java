package practiceprograms_N5;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class LibraryApp {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    private JTextField titleField, authorField, isbnField, categoryField, yearField, searchField;

    private java.util.List<Book> books = new ArrayList<>();

    public LibraryApp() {
        frame = new JFrame("Library App");
        frame.setSize(1100, 650);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(30, 36, 45));


        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(20, 80, 380, 420);
        leftPanel.setBackground(new Color(40, 48, 60));
        leftPanel.setLayout(null);
        frame.add(leftPanel);

        JLabel titleLabel = createLabel("Title", 30);
        JLabel authorLabel = createLabel("Author", 110);
        JLabel isbnLabel = createLabel("ISBN", 190);
        JLabel categoryLabel = createLabel("Category", 270);
        JLabel yearLabel = createLabel("Year", 350);

        leftPanel.add(titleLabel);
        leftPanel.add(authorLabel);
        leftPanel.add(isbnLabel);
        leftPanel.add(categoryLabel);
        leftPanel.add(yearLabel);

        titleField = createField(60);
        authorField = createField(140);
        isbnField = createField(220);
        categoryField = createField(300);
        yearField = createField(380);

        leftPanel.add(titleField);
        leftPanel.add(authorField);
        leftPanel.add(isbnField);
        leftPanel.add(categoryField);
        leftPanel.add(yearField);

  
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(430, 80, 630, 60);
        searchPanel.setBackground(new Color(40, 48, 60));
        searchPanel.setLayout(null);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setBounds(20, 20, 100, 20);
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(90, 18, 350, 24);
        searchField.setBackground(new Color(60, 70, 85));
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        searchPanel.add(searchField);

        JButton searchBtn = createButton("Search");
        searchBtn.setBounds(450, 18, 80, 24);
        searchPanel.add(searchBtn);

        JButton clearBtn = createButton("Clear");
        clearBtn.setBounds(540, 18, 80, 24);
        searchPanel.add(clearBtn);

        frame.add(searchPanel);


        model = new DefaultTableModel(new String[]{"ID", "Title", "Author", "ISBN", "Category", "Year", "Status"}, 0);
        table = new JTable(model);

        table.setBackground(new Color(40, 48, 60));
        table.setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(70, 80, 100));
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(430, 150, 630, 350);
        frame.add(scrollPane);

        JButton addBtn = createButton("Add Book");
        JButton deleteBtn = createButton("Delete");
        JButton issueBtn = createButton("Issue");
        JButton returnBtn = createButton("Return");
        JButton dashboardBtn = createButton("Dashboard");

        addBtn.setBounds(200, 530, 120, 35);
        deleteBtn.setBounds(330, 530, 120, 35);
        issueBtn.setBounds(460, 530, 120, 35);
        returnBtn.setBounds(590, 530, 120, 35);
        dashboardBtn.setBounds(720, 530, 120, 35);

        frame.add(addBtn);
        frame.add(deleteBtn);
        frame.add(issueBtn);
        frame.add(returnBtn);
        frame.add(dashboardBtn);


        addBtn.addActionListener(e -> addBook());
        deleteBtn.addActionListener(e -> deleteBook());
        issueBtn.addActionListener(e -> changeStatus("Issued"));
        returnBtn.addActionListener(e -> changeStatus("Available"));
        searchBtn.addActionListener(e -> searchBooks());
        clearBtn.addActionListener(e -> resetSearch());

        dashboardBtn.addActionListener(e -> openDashboard());

        frame.setVisible(true);
    }

    private JLabel createLabel(String text, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(20, y, 100, 25);
        return lbl;
    }

    private JTextField createField(int y) {
        JTextField tf = new JTextField();
        tf.setBounds(120, y, 220, 25);
        tf.setBackground(new Color(60, 70, 85));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        return tf;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        return btn;
    }

    private void addBook() {
        if (titleField.getText().isEmpty() || authorField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        int id = books.size() + 1;

        Book b = new Book(id,
                titleField.getText(),
                authorField.getText(),
                isbnField.getText(),
                categoryField.getText(),
                yearField.getText(),
                "Available");

        books.add(b);

        model.addRow(new Object[]{b.id, b.title, b.author, b.isbn, b.category, b.year, b.status});
        clearFields();
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        model.removeRow(row);
        books.remove(row);
    }

    private void changeStatus(String newStatus) {
        int row = table.getSelectedRow();
        if (row == -1) return;

        model.setValueAt(newStatus, row, 6);
        books.get(row).status = newStatus;
    }

    private void searchBooks() {
        String key = searchField.getText().trim();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + key));

        table.setRowSorter(sorter);
    }

    private void resetSearch() {
        searchField.setText("");
        table.setRowSorter(null);
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        categoryField.setText("");
        yearField.setText("");
    }


    private void openDashboard() {
        JFrame d = new JFrame("Dashboard");
        d.setSize(500, 400);
        d.setLocationRelativeTo(frame);
        d.setLayout(null);
        d.getContentPane().setBackground(new Color(40, 48, 60));

        JLabel stats = new JLabel();
        stats.setForeground(Color.WHITE);
        stats.setBounds(20, 20, 460, 30);
        stats.setFont(new Font("Segoe UI", Font.BOLD, 16));

        int total = books.size();
        int issued = 0;
        int available = 0;

        for (Book b : books) {
            if (b.status.equals("Issued")) issued++;
            else available++;
        }

        stats.setText("Total: " + total + "   Issued: " + issued + "   Available: " + available);
        d.add(stats);

        DefaultTableModel dm = new DefaultTableModel(new String[]{"ID", "Title", "Status"}, 0);
        for (Book b : books) {
            dm.addRow(new Object[]{b.id, b.title, b.status});
        }

        JTable dashTable = new JTable(dm);
        dashTable.setRowHeight(24);
        dashTable.setForeground(Color.WHITE);
        dashTable.setBackground(new Color(60, 70, 85));

        JTableHeader h = dashTable.getTableHeader();
        h.setBackground(Color.WHITE);
        h.setForeground(Color.BLACK);

        JScrollPane sp = new JScrollPane(dashTable);
        sp.setBounds(20, 70, 450, 260);
        d.add(sp);

        d.setVisible(true);
    }

    class Book {
        int id;
        String title, author, isbn, category, year, status;

        Book(int id, String title, String author, String isbn, String category, String year, String status) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.category = category;
            this.year = year;
            this.status = status;
        }
    }

    public static void main(String[] args) {
        new LibraryApp();
    }
}






