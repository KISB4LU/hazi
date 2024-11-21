package window.login;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class registerPage extends JPanel {
    private userHandler uh;
    private JTextField username;
    private JPasswordField password;
    private JPasswordField confirmPassword;
    private JFormattedTextField balnce;
    private JLabel errorLabel;
    private JButton confirm;
    public registerPage() {
        uh = new userHandler();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        username = new JTextField(20);
        password = new JPasswordField(20);
        confirmPassword = new JPasswordField(20);
        balnce = new JFormattedTextField(new DecimalFormat("#.##"));
        confirm = new JButton("Confirm");
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        add(username);
        add(password);
        add(confirmPassword);
        add(balnce);
        add(errorLabel);
        add(confirm);
    }
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
        if(Double.valueOf(balnce.getValue().toString()) <= 0.0){
            valid = false;
            System.out.println(Double.valueOf(balnce.getValue().toString()));
            error.append("Balnce must be greater than zero\n");
        }
        if(valid){
            User[] users = uh.readUsers();
            User user = new User(username.getText(), password.getText(),Double.valueOf(balnce.getValue().toString()));
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
