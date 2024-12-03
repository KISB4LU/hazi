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

    /**
     * bejelentkező oldal
     */
    public loginPage() {
        uh = new userHandler();
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

        login = new JButton("Login");
        register = new JButton("Register");
        JPanel buttons = new JPanel();

        error = new JLabel();
        error.setVisible(false);
        error.setForeground(Color.RED);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        buttons.add(login);
        buttons.add(register);

        add(namepanel);
        add(passwordpanel);
        add(error);
        add(buttons);
    }
    public JButton getLogin() {
        return login;
    }
    public JButton getRegister() {
        return register;
    }

    /**
     * ellenörzi a belépési adatokat
     * @return
     */
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
