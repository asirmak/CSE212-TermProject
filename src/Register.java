import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register {
	
	private static JFrame registerFrame;
	private static JLabel usernameLabel;
	private static JLabel passLabel;
	private static JTextField username;
	private static JPasswordField password;
	private static JButton signUpButton;
	private static JButton signInButton;
	private static JLabel message;
	private static boolean isLoggedInR;
	private static String userNameRN;

	public static void registerScreen() {
		registerFrame = new JFrame("Register");
		registerFrame.getContentPane().setBackground(Color.GRAY);
 		registerFrame.setSize(800, 600);
		registerFrame.setLayout(null);
		registerFrame.setLocationRelativeTo(null);
		registerFrame.setVisible(true);
		registerFrame.setResizable(false);

		usernameLabel = new JLabel("Username: ");
        usernameLabel.setForeground(Color.WHITE);

		usernameLabel.setBounds(200, 150, 100, 40);
		
		passLabel = new JLabel("Password: ");
		passLabel.setForeground(Color.WHITE);
		passLabel.setBounds(200, 200, 100, 40);
		
		username = new JTextField();
		username.setBounds(300, 150, 200, 40); 		

		password = new JPasswordField();
		password.setBounds(300, 200, 200, 40);
		
		//Sign up button
		signUpButton = new JButton("Sign up");
		signUpButton.setBounds(300, 270, 90, 40);
		
		//Sign in button
		signInButton = new JButton("Sign in");
		signInButton.setBounds(410, 270, 90, 40);
		
		message = new JLabel();
		message.setBounds(300, 330, 200, 40);
		registerFrame.add(message);
		
		registerFrame.add(usernameLabel);
		registerFrame.add(username);
		registerFrame.add(password);
		registerFrame.add(passLabel);
		registerFrame.add(signUpButton);
		registerFrame.add(signInButton);
		
		signUpButton.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent Event) {
				try {
					signUp();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		signInButton.addActionListener(new java.awt.event.ActionListener( ) {
			
			public void actionPerformed(java.awt.event.ActionEvent Event) {
				try {
					signIn();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public static void signUp() throws IOException {
		
		String filename = "data.txt";
		
        PrintWriter database = new PrintWriter(new FileWriter(filename, true));
		        
        // empty username field
		if((username.getText().equals(""))) {
			message.setText("Enter a username.");
		}
        // empty password field
		else if(String.valueOf(password.getPassword()).equals("")) {
			message.setText("Enter password.");
		}
		// if both fields are not empty
		else {
	        BufferedReader reader = new BufferedReader(new FileReader(filename));
	        boolean isUsed = false;
	        boolean isEmpty = true;
			String line;
			String userN = null;
            while ((line = reader.readLine()) != null) {
            	isEmpty = false;
                // reads username from file
                String[] components = line.split(" ");
            	for (String component : components) {
                    String[] keyValue = component.split(":");
                    if (keyValue.length == 2) {
                    	String key = keyValue[0];
                    	String value = keyValue[1];
                    	if (key.equals("username")) {
                    		userN = value;
                    	}
                    }
                }
            	
            	// check if username is used or not
				if(username.getText().equals(userN)){
					message.setText("Username is used.");
					username.setText(null);
    				password.setText(null);
    				isUsed = true;			
    				break;
				}
            }
            if(isEmpty){ // if empty database. Registeration for the first time
				database.printf("username:%s password:%s score:0\n",username.getText(),String.valueOf(password.getPassword()));
				message.setText("You registered successfully.");
				username.setText(null);
				password.setText(null);
			}
            else if(!(isUsed)) { // if username is empty, registeration will be completed
            	database.printf("username:%s password:%s score:0\n",username.getText(),String.valueOf(password.getPassword()));
				message.setText("You registered successfully.");
				username.setText(null);
				password.setText(null);
            }
            
            reader.close();
		}
		database.close();
	}
	
	public static void signIn() throws IOException {
		
		String filename = "data.txt";
		
        // empty username field
		if(  (username.getText().equals(""))) {
			message.setText("Enter a username.");
		}
        // empty password field
		else if(String.valueOf(password.getPassword()).equals("")) {
			message.setText("Enter password.");
		}
		// if both fields are not empty
		else {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			String userN = null;
			String userP = null;
            while ((line = reader.readLine()) != null) {
                // reads username and password from file
            	String[] components = line.split(" ");
            	for (String component : components) {
                    String[] keyValue = component.split(":");
                    String key = keyValue[0];
                    String value = keyValue[1];
                    if (key.equals("username")) {
                    	userN = value;
                    }
                    else if(key.equals("password")) {
                    	userP = value;
                    }
                }
				// correct username and password
				if(String.valueOf(password.getPassword()).equals(userP) && username.getText().equals(userN)) {
					message.setText("You logged in successfully.");
					isLoggedInR = true;
					userNameRN = userN;
					registerFrame.dispose();
					username.setText(null);
    				password.setText(null);
    				break;
				}
				// username not found in database
				else if(  !(username.getText().equals(userN))) {
					message.setText("No user found.");
				}
				
				// username found in database but password wrong
				else if( !(String.valueOf(password.getPassword()).equals(userP)) && username.getText().equals(userN)) {
					message.setText("You entered wrong password.");
				}
				else if(( !(String.valueOf(password.getPassword()).equals(userP)) || !(username.getText().equals(userN)))) {
					message.setText("Wrong Username or Password.");
				}
			}
            reader.close();
		}
	}


	public static boolean isLoggedInR() {
		return isLoggedInR;
	}


	public static void setLoggedInR(boolean isLoggedInR) {
		Register.isLoggedInR = isLoggedInR;
	}
	
	public static String getUserNameRN() {
		return userNameRN;
	}
}
