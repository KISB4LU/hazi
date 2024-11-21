package window.login;

import javax.swing.*;
import java.awt.*;

public class loginPage extends JPanel {
    private JTextField username;
    private JPasswordField password;
    private JLabel error;
    private JButton login;
    private JButton register;
    private userHandler uh;


    public loginPage() {
        uh = new userHandler();
        username = new JTextField(20);
        password = new JPasswordField(20);
        login = new JButton("Login");
        register = new JButton("Register");
        error = new JLabel();
        error.setVisible(false);
        error.setForeground(Color.RED);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(username);
        add(password);
        add(error);
        add(login);
        add(register);
    }
    public JButton getLogin() {
        return login;
    }
    public JButton getRegister() {
        return register;
    }
    public User login(){
        User[] users = uh.readUsers();
        for(User user : users){
            if(user.getName().equals(username.getText()) && user.getPassword().equals(password.getText())){
                return user;
            }
        }
        error.setText("Invalid Username or Password");
        error.setVisible(true);
        return null;
    }
}
