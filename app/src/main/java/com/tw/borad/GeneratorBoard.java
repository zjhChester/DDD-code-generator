package com.tw.borad;

import com.tw.CodeGenerator;
import com.tw.borad.panel.DatabaseConnectionPanel;
import com.tw.borad.panel.OperationPanel;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneratorBoard extends JFrame {
    private Connection connection;
    private DatabaseConnectionPanel databaseConnectionPanel = null;
    private OperationPanel operationPanel = null;

    public static void main(String[] args) {
        start();
    }

    public static void start() throws HeadlessException {
        new GeneratorBoard("code generator v 2.1");

    }

    public GeneratorBoard(String title) throws HeadlessException {
        super(title);
        generateDatabaseConnectPanel();
    }

    private void generateDatabaseConnectPanel() {
        databaseConnectionPanel = new DatabaseConnectionPanel();
        databaseConnectionPanel.setDatabasePanelLayout();
        addActionListenerForDataBasePanel(databaseConnectionPanel);
        operationPanel = OperationPanel.init();
        getContentPane().add(databaseConnectionPanel);
        databaseConnectionPanel.setVisible(true);
        getContentPane().add(operationPanel);
        operationPanel.setVisible(false);
        initBaseBoardSetting();
    }

    private void addActionListenerForDataBasePanel(DatabaseConnectionPanel panel) {
        panel.getConfirm().addActionListener(e -> {
            checkParamAndTestConnecting(panel.getUrlField(), panel.getUsernameField(), panel.getPasswordField(),
                    panel.getTestStatus(), panel);
            this.databaseConnectionPanel.setVisible(false);
            this.operationPanel.setVisible(true);
            try {
                initDatabase();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
        panel.getTestConnect().addActionListener(e ->
                checkParamAndTestConnecting(panel.getUrlField(), panel.getUsernameField(), panel.getPasswordField(),
                        panel.getTestStatus(), panel));
    }


    private void initDatabase() throws SQLException {
        OperationPanel panel = this.operationPanel;
        JComboBox<String> box = panel.getDatabaseBox();
        box.requestFocus();
        box.addItemListener(v -> {
            setTableBoxItem(box);
        });
        String sql = "show databases";
        parseResultSet(box, sql);
        setTableBoxItem(box);
        panel.getStartGenerate().addActionListener(v -> {
             checkRequireParam();
            new CodeGenerator(panel, this.databaseConnectionPanel);
        });
    }

    private void checkRequireParam() {
        verifyFiled(this.operationPanel.getCompanyNameFiled());
        verifyFiled(this.operationPanel.getProjectNameFiled());
        verifyFiled(this.operationPanel.getTestPath());
        verifyFiled(this.operationPanel.getProjectPath());

    }

    private void verifyFiled(JTextField field) {
        if(!StringUtils.hasText(field.getText().trim())){
            JOptionPane.showConfirmDialog(this.operationPanel, "require param not be blank!",
                    "error", JOptionPane.YES_NO_OPTION);
            throw new RuntimeException("require param");
        }
    }

    private void setTableBoxItem(JComboBox<String> databaseBox) {
        System.out.println(databaseBox.getSelectedItem());
        String sql = String.format("show tables from %s", databaseBox.getSelectedItem());
        try {
            parseResultSet(this.operationPanel.getTableBox(), sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void parseResultSet(JComboBox<String> box, String sql) throws SQLException {
        box.removeAllItems();
        String[] types = {"TABLE"};
        ResultSet schemas = connection.prepareStatement(sql).executeQuery();
        while (schemas.next()) {
            box.addItem(schemas.getString(1));
        }
    }

    private void initBaseBoardSetting() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 345);
        setVisible(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void checkParamAndTestConnecting(JTextField urlField, JTextField usernameField, JTextField passwordField,
                                             JLabel testStatus, DatabaseConnectionPanel panel) {
        if (!StringUtils.hasText(urlField.getText().trim())
                || !StringUtils.hasText(usernameField.getText().trim())
                || !StringUtils.hasText(passwordField.getText().trim())) {
            JOptionPane.showConfirmDialog(panel, "Database url and username and password is required!",
                    "error", JOptionPane.YES_NO_OPTION);
            throw new RuntimeException("require param");
        }
        testStatus.setText("Connecting...");
        String realUrl = String.format("jdbc:mysql://%s", urlField.getText().trim());
        panel.setUrl(realUrl);
        panel.setUsername(usernameField.getText().trim());
        panel.setPassword(passwordField.getText().trim());
        testConnecting(panel, testStatus);
    }

    private void testConnecting(DatabaseConnectionPanel panel, JLabel testStatus) {

        try {

            connection = DriverManager.getConnection(panel.getUrl(), panel.getUsername(), panel.getPassword());
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(panel, "access denied!using password: YES.",
                    "error", JOptionPane.YES_NO_OPTION);
            testStatus.setText("Fail!");
            throw new RuntimeException("password error");
        }
        testStatus.setText("Success!");
    }

}
