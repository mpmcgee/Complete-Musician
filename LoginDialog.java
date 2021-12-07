import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class LoginDialog extends JDialog {

    //labels and fields for dialog
    JLabel title = new JLabel("Complete Musician");
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userField = new JTextField(15);
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Log in");
    JButton registerButton = new JButton("Register");
    JLabel statusLabel = new JLabel(" ");

    //constructor
    public LoginDialog() {
        //make visible
        setVisible(true);

        //create panel layout and add components
        JPanel p3 = new JPanel(new GridLayout(2, 1));
        p3.add(userLabel);
        p3.add(passwordLabel);

        JPanel p4 = new JPanel(new GridLayout(2, 1));
        p4.add(userField);
        p4.add(passwordField);

        JPanel p1 = new JPanel();
        p1.add(p3);
        p1.add(p4);

        JPanel p2 = new JPanel();
        p2.add(registerButton);
        p2.add(loginButton);


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
        //if closed, delete object
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //if window is closed, end program
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //listener for login button
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //load users from user file
                LLUser users = Loader.loadUsers();
                //get username and password from panel fields and call login method with these values
                User user = users.logIn(userField.getText(), passwordField.getText());
                //if this method returns a value that is not null
                if (Objects.nonNull(user)){
                    //make dialog invisible
                    setVisible(false);
                    //load the values into this user for the session
                    user = Loader.loadSession(user);
                    //load the tabbed pane for this user
                    new SimpleTabbedPane(user);
                } else {
                    //if user info returned null, print Invalid Credentials
                    statusLabel.setText("Invalid credentials.");
                }
            }
        });

        //register button listener
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //create a new register dialog
                new RegisterDialog();
                //make this dialog invisible
                setVisible(false);
            }
        });

    }
}
