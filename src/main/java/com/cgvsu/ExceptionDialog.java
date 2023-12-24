package com.cgvsu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ExceptionDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public ExceptionDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        contentPane.setPreferredSize(new Dimension(800, 200));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public static void throwExceptionWindow(){
        ExceptionDialog dialog = new ExceptionDialog();
        dialog.pack();
        dialog.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - dialog.getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - dialog.getHeight()/2);
        dialog.setVisible(true);
    }

}
