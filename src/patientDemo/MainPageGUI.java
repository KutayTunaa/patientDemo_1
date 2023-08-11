package patientDemo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;


public class MainPageGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	protected static final String DB_URL = "jdbc:mysql://localhost:3306/patient_db";
	protected static final String USER = "root";
	protected static final String PASSWORD = "root!";
	
	private static JPanel contentPane;
	private static JTextField NINText;
	private static JTextField nameText;
	private static JTextField surnameText;
	private static JTextField cellText;
	private static JTextField birthPlaceText;
	private static JComboBox<?> genderBox;
	private static JDateChooser dateChooser;
	

	public static void main(String[] args) {
		MainPageGUI frame = new MainPageGUI();
		frame.setVisible(true);
	}

	public MainPageGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 400)); 
		pack(); 
		setResizable(false);
		setLocationRelativeTo(null); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel NINLabel = new JLabel("NIN:");
		NINLabel.setBounds(6, 32, 81, 16);
		panel.add(NINLabel);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(6, 77, 61, 16);
		panel.add(nameLabel);
		
		JLabel surnameLabel = new JLabel("Surname:");
		surnameLabel.setBounds(6, 122, 61, 16);
		panel.add(surnameLabel);
		
		JLabel cellLabel = new JLabel("Cell:");
		cellLabel.setBounds(6, 170, 36, 16);
		panel.add(cellLabel);
		
		JLabel BirthPlaceLabel = new JLabel("Birth Place:");
		BirthPlaceLabel.setBounds(6, 213, 69, 16);
		panel.add(BirthPlaceLabel);
		
		JLabel birthDateLabel = new JLabel("Birth Date:");
		birthDateLabel.setBounds(6, 254, 66, 16);
		panel.add(birthDateLabel);
		
		JLabel genderLabel = new JLabel("Gender:");
		genderLabel.setBounds(6, 293, 48, 16);
		panel.add(genderLabel);
		
		
		
		
		NINText = new JTextField();
		NINText.setBounds(99, 27, 196, 26);
		panel.add(NINText);
		NINText.setColumns(10);
		NINText.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();

		        if (!Character.isDigit(c) || NINText.getText().length() >= 11) {
		            e.consume();
		        }
		    }
		});

		
		
		nameText = new JTextField();
		nameText.setBounds(99, 72, 196, 26);
		panel.add(nameText);
		nameText.setColumns(10);
		nameText.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();

		        if (!Character.isLetter(c)) {
		            e.consume();
		        }
		    }
		});

		surnameText = new JTextField();
		surnameText.setBounds(99, 117, 196, 26);
		panel.add(surnameText);
		surnameText.setColumns(10);
		surnameText.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();

		        if (!Character.isLetter(c)) {
		            e.consume();
		        }
		    }
		});
		
		cellText = new JTextField();
		cellText.setBounds(99, 165, 196, 26);
		panel.add(cellText);
		cellText.setColumns(10);
		cellText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if(!Character.isDigit(c) || cellText.getText().length() >= 11){
					e.consume();
				}
				
			}
		});
		
		
		birthPlaceText = new JTextField();
		birthPlaceText.setBounds(99, 208, 196, 26);
		panel.add(birthPlaceText);
		birthPlaceText.setColumns(10);
		birthPlaceText.addKeyListener(new KeyAdapter () {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if(!Character.isLetter(c)){
					e.consume();
				}
			}
		});
		
		
		String[] genders = {"Default", "Female", "Male"};
		genderBox = new JComboBox(genders);
		genderBox.setBounds(99, 289, 196, 27);
		panel.add(genderBox);
		
		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		dateChooser.setBounds(99, 254, 196, 20);
		panel.add(dateChooser);
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(384, 327, 98, 29);
		panel.add(addButton);
		 addButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                try {
	                	saveDataToDatabase();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }

					private void saveDataToDatabase() throws ParseException {
					    String NIN = NINText.getText();
					    String name = nameText.getText();
					    String surname = surnameText.getText();
					    String cell = cellText.getText();
					    String birthPlace = birthPlaceText.getText();
					    String gender = genderBox.getSelectedItem().toString();
					    Date birthDate = new Date(dateChooser.getDate().getTime());

					    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
					        String sql = "INSERT INTO patient (NIN, name, surname, cell, birthplace, birthdate, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
					        PreparedStatement pstmt = conn.prepareStatement(sql);
					        pstmt.setString(1, NIN);
					        pstmt.setString(2, name);
					        pstmt.setString(3, surname);
					        pstmt.setString(4, cell);
					        pstmt.setString(5, birthPlace);
					        pstmt.setDate(6, birthDate);
					        pstmt.setString(7, gender);
					        pstmt.executeUpdate();

					        System.out.println("Veri başarıyla kaydedildi.");
					    } catch (SQLException ex) {
					        System.err.println("Veri kaydedilirken bir hata oluştu: " + ex.getMessage());
					    }			
					}

	        });
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(486, 327, 98, 29);
		panel.add(deleteButton);
	}

}
