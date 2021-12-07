import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public class RegisterDialog extends JDialog {
    //labels and fields
    JLabel title = new JLabel("Complete Musician");
    JLabel userLabel = new JLabel("Enter a username");
    JLabel passwordLabel = new JLabel("Enter a password");
    JLabel passwordReenter = new JLabel("Reenter password");
    JLabel bandNameLabel = new JLabel("Enter your band name");
    JLabel instrumentLabel = new JLabel("Instrument");
    JTextField instrumentField = new JTextField(15);
    JTextField bandNameField = new JTextField(15);
    JLabel nameLabel = new JLabel("Enter your name");
    JTextField userField = new JTextField(15);
    JTextField nameField = new JTextField(15);
    JPasswordField passwordField = new JPasswordField();
    JPasswordField passwordReenterField = new JPasswordField();
    JButton registerButton = new JButton("Register");
    JButton clearButton = new JButton("Clear");
    JLabel statusLabel = new JLabel(" ");



    public RegisterDialog() {
        //make this panel visible
        setVisible(true);

        //create panels and layout and add content
        JPanel p3 = new JPanel(new GridLayout(6, 1));
        p3.add(userLabel);
        p3.add(nameLabel);
        p3.add(bandNameLabel);
        p3.add(instrumentLabel);
        p3.add(passwordLabel);
        p3.add(passwordReenter);

        JPanel p4 = new JPanel(new GridLayout(6, 1));
        p4.add(userField);
        p4.add(nameField);
        p4.add(bandNameField);
        p4.add(instrumentField);
        p4.add(passwordField);
        p4.add(passwordReenterField);

        JPanel p1 = new JPanel();
        p1.add(p3);
        p1.add(p4);

        JPanel p2 = new JPanel();
        p2.add(clearButton);
        p2.add(registerButton);

        JPanel p5 = new JPanel(new BorderLayout());
        p5.add(p2, BorderLayout.CENTER);
        p5.add(statusLabel, BorderLayout.NORTH);
        statusLabel.setForeground(Color.red);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(p1, BorderLayout.CENTER);
        add(p5, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        //delete object on close
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //end program if dialog is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //register button listener
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //if the password and password re-entry fields do not match, print a message stating this
                if (!passwordField.getText().equals(passwordReenterField.getText())) {
                    statusLabel.setText("Passwords do not match.");
                //if any fields are null, print a message stating that all fields are required
                } else if (instrumentField.getText().equals("") || bandNameField.getText().equals("") ||
                        passwordField.getText().equals("") || passwordReenterField.getText().equals("") ||
                        userField.getText().equals("") || nameField.getText().equals("")) {
                    statusLabel.setText("All fields are required.");
                //otherwise
                } else {
                    //append the user info to the registered users file
                    try {
                        Loader.appendUser(userField.getText(), passwordField.getText(), nameField.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    //create all files for user
                    try {
                        createFiles(userField.getText(), instrumentField.getText(), bandNameField.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    //load all users
                    LLUser users = Loader.loadUsers();

                    //store user as results from login method
                    User user = users.logIn(userField.getText(), passwordField.getText());
                    //if returned object is not null
                    if (Objects.nonNull(user)) {
                        //make this dialog invisible
                        setVisible(false);
                        //load the session for this user
                        user = Loader.loadSession(user);
                        //load the tabbedPane
                        new SimpleTabbedPane(user);
                    }
                }
            }
        });
    }

    //creates files to store data for user
    public void createFiles(String username, String instrument, String band) throws IOException {
        Writer wr = new FileWriter(username + "_components.txt");
        wr.write("");
        wr.flush();
        wr.close();
        /* use instrument and band data from form and write to repertoire file so that the user is initialized with
        at least one repertoire */
        wr = new FileWriter(username + "_repertoires.txt");
        wr.write(instrument + "," + band  + "\n");
        wr.flush();
        wr.close();
        wr = new FileWriter(username + "_routines.txt");
        wr.write("");
        wr.flush();
        wr.close();
        wr = new FileWriter(username + "_sessions.txt");
        wr.write("");
        wr.flush();
        wr.close();
        wr = new FileWriter(username + "_songs.txt");
        wr.write("");
        wr.flush();
        wr.close();
    }
}

