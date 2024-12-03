package window.login;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * ezen a panelen lehet regisztrálni
 */
public class registerPage extends JPanel {
    private userHandler uh;
    private JTextField username;
    private JPasswordField password;
    private JPasswordField confirmPassword;
    private JFormattedTextField balance;
    private JLabel errorLabel;
    private JButton confirm;
    private JButton login;
    private JPanel logPanel;
    public registerPage(JPanel loginPanel) {
        logPanel = loginPanel;
        uh = new userHandler();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel namepanel = new JPanel();
        JLabel namelabel = new JLabel("Username: ");
        username = new JTextField(20);
        namepanel.add(namelabel);
        namepanel.add(username);

        JPanel passwordpanel = new JPanel();
        JLabel passwordlabel = new JLabel("Password: ");
        password = new JPasswordField(20);
        passwordpanel.add(passwordlabel);
        passwordpanel.add(password);

        JPanel confirmpanel = new JPanel();
        JLabel confirmlabel = new JLabel("Confirm Pwd: ");
        confirmPassword = new JPasswordField(20);
        confirmpanel.add(confirmlabel);
        confirmpanel.add(confirmPassword);

        JPanel balpanel = new JPanel();
        JLabel ballabel = new JLabel("Balance: ");
        balance = new JFormattedTextField(new DecimalFormat("#.##"));
        balance.setColumns(20);
        balpanel.add(ballabel);
        balpanel.add(balance);

        JPanel buttonPanel = new JPanel();
        confirm = new JButton("Confirm");
        login = new JButton("Login");
        buttonPanel.add(confirm);
        buttonPanel.add(login);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        add(namepanel);
        add(passwordpanel);
        add(confirmpanel);
        add(balpanel);
        add(errorLabel);
        add(buttonPanel);

        login.addActionListener(e -> {
           setVisible(false);
           logPanel.setVisible(true);
        });
    }

    /**
     * ellenőrzi a megadott adatok érvényességét
     * @return
     */
    public User checkRegister(){
        boolean valid = true;
        StringBuilder error = new StringBuilder();
        if(username.getText().equals("") || password.getText().equals("") || confirmPassword.getText().equals("")) {
            valid = false;
            error.append("Username and password are required\n");
        }
        if(!password.getText().equals(confirmPassword.getText())) {
            valid = false;
            error.append("Passwords do not match\n");
        }
        if(Double.valueOf(balance.getValue().toString()) <= 0.0){
            valid = false;
            System.out.println(Double.valueOf(balance.getValue().toString()));
            error.append("Balnce must be greater than zero\n");
        }
        if(valid){
            User[] users = uh.readUsers();
            User user = new User(username.getText(), password.getText(),Double.valueOf(balance.getValue().toString()));
            if(users != null){
                User[] newUsers = new User[users.length+1];
                System.arraycopy(users, 0, newUsers, 0, users.length);
                newUsers[users.length] = user;
                uh.writeUsers(newUsers);
                return user;
            }else {
                User[] newUser = {user};
                uh.writeUsers(newUser);
                return user;
            }
        }else {
            errorLabel.setText(error.toString());
            errorLabel.setVisible(true);
            return null;
        }
    }
    public JButton getConfirm() {
        return confirm;
    }
}
