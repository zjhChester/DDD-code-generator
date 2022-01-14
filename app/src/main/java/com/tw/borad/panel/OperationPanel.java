package com.tw.borad.panel;

import javax.swing.*;

public class OperationPanel extends BasePanel {
    private JLabel companyNameLabel;
    private JLabel projectNameLabel;
    private JLabel databaseNameLabel;
    private JLabel tableNameLabel;
    private JTextField companyNameFiled;
    private JTextField projectNameFiled;
    private JTextField projectPath;
    private JTextField testPath;
    private JComboBox<String> databaseBox;
    private JComboBox<String> tableBox;
    private JCheckBox poDomain;
    private JCheckBox controller;
    private JCheckBox useCase;
    private JCheckBox repoInterface;
    private JCheckBox repoImpl;
    private JCheckBox service;
    private JCheckBox jpaRepo;
    private JCheckBox testBuilder;
    private JButton startGenerate;

    private void initLayOut() {
        this.setLayout(null);
        this.setBounds(0, 0, 500, 600);
        databaseNameLabel = initJLabel("databases:", 20, 10, 100, 30);
        databaseBox = initJComboBox(180, 10, 200, 30);
        tableNameLabel = initJLabel("tables:", 20, 40, 100, 30);
        tableBox = initJComboBox(180, 40, 200, 30);
        companyNameLabel = initJLabel("company-name:", 20, 70, 140, 30);
        companyNameFiled = initJTextField(180, 70, 200, 30);
        projectNameLabel = initJLabel("project-name:", 20, 100, 200, 30);
        projectNameFiled = initJTextField(180, 100, 200, 30);
        initJLabel("project-path", 20, 130, 200, 30);
        projectPath = initJTextField(180, 130, 200, 30);
        initJLabel("test-path", 20, 160, 200, 30);
        testPath = initJTextField(180, 160, 200, 30);

        poDomain = initJCheckBox("poDomain", 20, 190, 100, 30);
        controller = initJCheckBox("controller", 120, 190, 100, 30);
        useCase = initJCheckBox("useCase", 220, 190, 100, 30);
        repoInterface = initJCheckBox("repoInterface", 320, 190, 140, 30);
        repoImpl = initJCheckBox("repoImpl", 20, 220, 100, 30);
        service = initJCheckBox("service", 120, 220, 100, 30);
        jpaRepo = initJCheckBox("jpaRepo", 220, 220, 100, 30);
        testBuilder = initJCheckBox("testBuilder", 320, 220, 100, 30);
        startGenerate = initButton("StartGenerate", 320, 280, 150, 30);
    }

    public OperationPanel() {
    }

    public JLabel getTableNameLabel() {
        return tableNameLabel;
    }

    public JComboBox<String> getTableBox() {
        return tableBox;
    }

    public static OperationPanel init() {
        OperationPanel panel = new OperationPanel();
        panel.initLayOut();
        return panel;
    }

    public JLabel getCompanyNameLabel() {
        return companyNameLabel;
    }

    public JLabel getProjectNameLabel() {
        return projectNameLabel;
    }

    public JLabel getDatabaseNameLabel() {
        return databaseNameLabel;
    }

    public JTextField getCompanyNameFiled() {
        return companyNameFiled;
    }

    public JTextField getProjectNameFiled() {
        return projectNameFiled;
    }

    public JComboBox<String> getDatabaseBox() {
        return databaseBox;
    }

    public JButton getStartGenerate() {
        return startGenerate;
    }

    public JTextField getProjectPath() {
        return projectPath;
    }

    public JTextField getTestPath() {
        return testPath;
    }

    public JCheckBox getPoDomain() {
        return poDomain;
    }

    public JCheckBox getController() {
        return controller;
    }

    public JCheckBox getUseCase() {
        return useCase;
    }

    public JCheckBox getRepoInterface() {
        return repoInterface;
    }

    public JCheckBox getRepoImpl() {
        return repoImpl;
    }

    public JCheckBox getService() {
        return service;
    }

    public JCheckBox getJpaRepo() {
        return jpaRepo;
    }

    public JCheckBox getTestBuilder() {
        return testBuilder;
    }
}
