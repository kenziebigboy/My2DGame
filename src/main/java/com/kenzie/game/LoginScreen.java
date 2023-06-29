package com.kenzie.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// YouTube tutorial by Career & Tech HQ https://www.youtube.com/watch?v=gBtuj_MjgtY
public class LoginScreen extends JFrame implements ActionListener{
    JPasswordField password;
    JLabel labelPassword, labelUserName, labelMessage, labelTitle;
    JTextField userName;
    JButton loginButton, resetButton;
    JCheckBox showPassword;
    public LoginScreen(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setTitle("User login");
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        labelUserName = new JLabel("Username");
        labelUserName.setBounds(150,200,100,40);
        labelUserName.setFont(new Font("Arial", Font.PLAIN, 20));
        labelUserName.setForeground(Color.BLUE);

        labelPassword = new JLabel("Password");
        labelPassword.setBounds(150,250,100,40);
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        labelPassword.setForeground(Color.BLUE);

        labelMessage = new JLabel();
        labelMessage.setBounds(250,330,300,40);

        userName = new JTextField();
        userName.setBounds(250,200,300,40);

        password = new JPasswordField();
        password.setBounds(250,250,300,40);

        showPassword = new JCheckBox("Show password");
        showPassword.setBounds(550,250,300,40);
        showPassword.addActionListener(this);

        loginButton = new JButton("Sign in");
        loginButton.setBounds(250,300,100,40);
        loginButton.addActionListener(this);

        resetButton = new JButton("Reset");
        resetButton.setBounds(375,300,100,40);
        resetButton.addActionListener(this);

        this.add(labelUserName);
        this.add(userName);
        this.add(labelPassword);
        this.add(password);
        this.add(loginButton);
        this.add(labelMessage);
        this.add(resetButton);
        this.add(showPassword);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton){
            String userText = userName.getText();
            String passwordText = new String(password.getPassword());
            //for() {
                if (userText.equals("Rome") && passwordText.equals("Pass")) {
                    //labelMessage.setText("Login Successful");
                    JOptionPane.showMessageDialog(this, "Login Successful");
                } else {
                    //labelMessage.setText("Invalid Username and Password");
                    JOptionPane.showMessageDialog(this, "Invalid Username and Password");
                }
            //}
        }
        if(e.getSource() == resetButton){
            userName.setText("");
            password.setText("");
        }
        if(e.getSource() == showPassword){
            if(showPassword.isSelected()){
                password.setEchoChar((char) 0);
            }
            else {
                password.setEchoChar('*');
            }
        }
    }
}
