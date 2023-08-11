package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import patientDemo.MainPageGUI;


// GUI for graphical user interface
public class LoginGUI implements ActionListener {

    private static JLabel userLabel;
    private static JTextField usernameText;
    private static JLabel paswordLabel;
    private static JPasswordField userPassword;
    private static JButton loginButton;
    private static JLabel success;
    private static JLabel errorMessage;
    private static JFrame frame;
    private static JButton btnSignUp;

    public static void main(String[] args) {

    	
    	  
        JPanel panel = new JPanel();
        frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        
      

        panel.setLayout(null);

        // it shows where should user write his/her username on panel
        userLabel = new JLabel("User:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameText = new JTextField();
        usernameText.setBounds(100, 20, 165, 25);
        panel.add(usernameText);

        // it shows where should user write his/her password on panel
        paswordLabel = new JLabel("Password:");
        paswordLabel.setBounds(10, 50, 80, 25);
        panel.add(paswordLabel);

        userPassword = new JPasswordField();
        userPassword.setBounds(100, 50, 165, 25);
        panel.add(userPassword);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 86, 80, 25);
        loginButton.addActionListener(new LoginGUI());
        panel.add(loginButton);

        success = new JLabel();
        success.setBounds(10, 123, 300, 25);
        panel.add(success);

        errorMessage = new JLabel();
        errorMessage.setBounds(10, 140, 300, 25);
        panel.add(errorMessage);
        
        btnSignUp = new JButton("Sign Up");
        btnSignUp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
        btnSignUp.setBounds(192, 87, 80, 25);
        panel.add(btnSignUp);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String username = usernameText.getText();
        @SuppressWarnings("deprecation")
		String password = userPassword.getText();


        try {
        	Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/patient_db", "root", "root");
        	
        	PreparedStatement statement = (PreparedStatement) con.prepareStatement("SELECT username, password FROM userTable WHERE username=? AND password=?"
        			);
        	
            
        	statement.setString(1, username);
        	statement.setString(2, password);
        	
        	ResultSet rs = statement.executeQuery();
        	
        	if (rs.next()) {
                MainPageGUI mainPage = new MainPageGUI();
                mainPage.setTitle("Welcome");
                mainPage.setVisible(true);
                frame.dispose();    
            }
        	else {
                JOptionPane.showMessageDialog(null, "Wrong Username & Password");
        	}
        	
			
		} catch (SQLException sqlException) {
		    JOptionPane.showMessageDialog(null, "An error occurred: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    sqlException.printStackTrace();
		}
    }

    // This Method will launch the MainPageGUI 
    public void openMainPageGUI() {
        MainPageGUI mainPageGUI = new MainPageGUI();
        mainPageGUI.setVisible(true);
        //this frame.dospose(); will close the Login window
        frame.dispose();    
    }
} 
