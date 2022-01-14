package com.tw.borad.panel;

import javax.swing.*;

public class DatabaseConnectionPanel extends BasePanel {
    private String username;
    private String url;
    private String password;
    private JLabel urlArea;
    private JLabel usernameArea;
    private JLabel passwordArea;
    private JTextField urlField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JLabel testStatus;
    private JButton confirm;
    private JButton testConnect;

    private static final String DEFAULT_URL = "localhost:3306";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "root";

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public JTextField getUrlField() {
        return urlField;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JLabel getTestStatus() {
        return testStatus;
    }

    public JButton getConfirm() {
        return confirm;
    }

    public JButton getTestConnect() {
        return testConnect;
    }

    public DatabaseConnectionPanel() {
        this.setLayout(null);
        this.setBounds(0, 0, 500, 345);
    }

    public void setDatabasePanelLayout() {
        urlArea = initJLabel("database url(host:port):", 30, 20, 170, 30);
        usernameArea = initJLabel("database username:", 30, 100, 160, 30);
        passwordArea = initJLabel("database password:", 30, 180, 160, 30);
        urlField = initJTextField(30, 60, 300, 30);
        usernameField = initJTextField(30, 140, 300, 30);
        passwordField = new JPasswordField();
        testStatus = initJLabel("Not Connected", 60, 260, 100, 30);
        passwordField.setBounds(30, 220, 300, 30);
        JButton setDefaultDataButton = initButton("Set Default Data Source", 230, 20, 250, 30);
        testConnect = initButton("Test", 300, 280, 100, 30);
        confirm = initButton("Confirm", 400, 280, 100, 30);
        this.add(passwordField);

        setDefaultDataButton.addActionListener(x->{
            this.urlField.setText(DEFAULT_URL);
            this.usernameField.setText(DEFAULT_USERNAME);
            this.passwordField.setText(DEFAULT_PASSWORD);
        });
    }


}
