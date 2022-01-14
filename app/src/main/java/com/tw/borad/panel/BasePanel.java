package com.tw.borad.panel;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;

public class BasePanel extends JPanel {
    public BasePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public BasePanel(LayoutManager layout) {
        super(layout);
    }

    public BasePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public BasePanel() {
        super();
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }

    @Override
    public PanelUI getUI() {
        return super.getUI();
    }

    @Override
    public void setUI(PanelUI ui) {
        super.setUI(ui);
    }

    @Override
    public String getUIClassID() {
        return super.getUIClassID();
    }

    @Override
    protected String paramString() {
        return super.paramString();
    }

    @Override
    public AccessibleContext getAccessibleContext() {
        return super.getAccessibleContext();
    }
    protected JButton initButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        this.add(button);
        return button;
    }

    protected JLabel initJLabel(String text, int x, int y, int width, int height) {
        JLabel jLabel = new JLabel(text);
        jLabel.setBounds(x, y, width, height);
        this.add(jLabel);
        return jLabel;
    }

    protected JTextField initJTextField(int x, int y, int width, int height) {
        JTextField jTextField = new JTextField(width);
        jTextField.setBounds(x, y, width, height);
        this.add(jTextField);
        return jTextField;
    }

    protected JComboBox<String> initJComboBox(int x, int y, int width, int height) {
        JComboBox<String> box = new JComboBox<>();
        box.setBounds(x, y, width, height);
        this.add(box);
        return box;
    }

    protected JCheckBox initJCheckBox(String text, int x, int y, int width, int height) {
        JCheckBox box = new JCheckBox();
        box.setText(text);
        box.setBounds(x, y, width, height);
        this.add(box);
        return box;
    }
}
