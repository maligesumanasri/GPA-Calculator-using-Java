import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GPA_CalculatorWithLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private HashMap<String, String> credentials;

    public GPA_CalculatorWithLogin() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        credentials = new HashMap<>();
        // Add multiple usernames and passwords
        credentials.put("22r11a0511", "22R11A0511");
        credentials.put("22r11a0512", "22R11A0512");
        credentials.put("22r11a0516", "22R11A0516");
        credentials.put("22r11a0517", "22R11A0517");
        credentials.put("22r11a0518", "22R11A0518");
        credentials.put("22r11a0528", "22R11A0528");
        credentials.put("22r11a0529", "22R11A0529");
        // Add as many as needed

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Simple validation - check against stored credentials
                if (credentials.containsKey(username) && credentials.get(username).equals(password)) {
                    dispose(); // Close the login window
                    showGpaCalculator(); // Open the GPA calculator window
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void showGpaCalculator() {
        JFrame gpaFrame = new JFrame();
        gpaFrame.setTitle("GPA Calculator");
        gpaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gpaFrame.setSize(400, 300);
        gpaFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel numSemestersLabel = new JLabel("Number of Semesters:");
        JTextField numSemestersField = new JTextField();
        JButton calculateButton = new JButton("Calculate GPA");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        panel.add(numSemestersLabel);
        panel.add(numSemestersField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(calculateButton);

        gpaFrame.add(panel, BorderLayout.NORTH);
        gpaFrame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int numSemesters = Integer.parseInt(numSemestersField.getText());
                    double totalCumulativeCredits = 0;
                    double totalCumulativeGradePoints = 0;

                    for (int semester = 1; semester <= numSemesters; semester++) {
                        int numSubjects = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of subjects for Semester " + semester + ":"));

                        double totalSemesterCredits = 0;
                        double totalSemesterGradePoints = 0;

                        for (int subject = 1; subject <= numSubjects; subject++) {
                            int creditHours = Integer.parseInt(JOptionPane.showInputDialog("Enter credit hours for Subject " + subject + ":"));
                            String grade = JOptionPane.showInputDialog("Enter grade for Subject " + subject + " (O, A+, A, B+, B, C):");

                            double gradePoints = calculateGradePoints(grade);
                            double subjectGPA = gradePoints * creditHours;

                            totalSemesterGradePoints += subjectGPA;
                            totalSemesterCredits += creditHours;
                        }

                        double semesterGPA = totalSemesterGradePoints / totalSemesterCredits;

                        resultArea.append("SGPA for Semester " + semester + ": " + semesterGPA + "\n");

                        totalCumulativeGradePoints += totalSemesterGradePoints;
                        totalCumulativeCredits += totalSemesterCredits;
                    }

                    double cgpa = totalCumulativeGradePoints / totalCumulativeCredits;

                    resultArea.append("\nCGPA: " + cgpa);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numerical values", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gpaFrame.setVisible(true);
    }

    private double calculateGradePoints(String grade) {
        switch (grade) {
            case "O":
                return 10.0;
            case "A+":
                return 9.0;
            case "A":
                return 8.0;
            case "B+":
                return 7.0;
            case "B":
                return 6.0;
            case "C":
                return 5.0;
            default:
                JOptionPane.showMessageDialog(null, "Invalid grade. Assuming grade as F (0.0)", "Grade Error", JOptionPane.WARNING_MESSAGE);
                return 0.0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GPA_CalculatorWithLogin::new);
    }
}

