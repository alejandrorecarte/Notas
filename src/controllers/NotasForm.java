package controllers;

import models.Nota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static controllers.NotasMain.actualizarTabla;

public class NotasForm {
    private static final Color COLOR_BACKGROUND = Color.decode("#EEF5FF");
    private static final Color COLOR_PRINCIPAL = Color.WHITE;
    private static final Color COLOR_PRINCIPAL_VARIANTE = Color.decode("#B4D4FF");
    private static final Color COLOR_FUENTE = Color.BLACK;
    JPanel mainPanel;
    private JLabel lNombre;
    private JLabel lApellidos;
    private JLabel lNota;
    private JTextField tfNombre;
    private JTextField tfApellidos;
    private JButton bGuardar;
    private JTextArea taNota;
    private JScrollPane panelScrollNota;
    private JButton bCancelar;
    private KeyAdapter actionGuardar = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if(e.getKeyCode() == KeyEvent.VK_S && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
                bGuardar.doClick();
            }
        }
    };

    private KeyAdapter actionCancelar = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                bCancelar.doClick();
            }
        }
    };

    public NotasForm(JFrame frame, JTable table, ArrayList<Nota> notas){
        mainPanel.setBackground(COLOR_BACKGROUND);
        bGuardar.setBackground(COLOR_PRINCIPAL);
        bCancelar.setBackground(COLOR_PRINCIPAL);

        taNota.setLineWrap(true);
        taNota.setWrapStyleWord(true);
        tfNombre.requestFocus();
        bGuardar.setToolTipText("Ctrl+S");
        bCancelar.setToolTipText("ESC");
        bGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notas.add(new Nota(String.valueOf(tfNombre.getText()), String.valueOf(tfApellidos.getText()), String.valueOf(taNota.getText())));
                frame.dispose();
                actualizarTabla(table);
            }
        });

        bCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        tfNombre.addKeyListener(actionGuardar);
        tfApellidos.addKeyListener(actionGuardar);
        taNota.addKeyListener(actionGuardar);
        tfNombre.addKeyListener(actionCancelar);
        tfApellidos.addKeyListener(actionCancelar);
        taNota.addKeyListener(actionCancelar);
    }

    public NotasForm(JFrame frame, JTable table, ArrayList<Nota> notas, Nota notaSeleccionada){
        mainPanel.setBackground(COLOR_BACKGROUND);
        bGuardar.setBackground(COLOR_PRINCIPAL);
        bCancelar.setBackground(COLOR_PRINCIPAL);

        tfNombre.setText(notaSeleccionada.getNombre());
        tfApellidos.setText(notaSeleccionada.getApellidos());
        taNota.setText(notaSeleccionada.getNota());
        tfNombre.requestFocus();
        bGuardar.setToolTipText("Ctrl+S");
        bCancelar.setToolTipText("ESC");
        bGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notas.remove(notaSeleccionada);
                notas.add(new Nota(String.valueOf(tfNombre.getText()), String.valueOf(tfApellidos.getText()), String.valueOf(taNota.getText())));
                frame.dispose();
                actualizarTabla(table);
            }
        });

        bCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        tfNombre.addKeyListener(actionGuardar);
        tfApellidos.addKeyListener(actionGuardar);
        taNota.addKeyListener(actionGuardar);
        tfNombre.addKeyListener(actionCancelar);
        tfApellidos.addKeyListener(actionCancelar);
        taNota.addKeyListener(actionCancelar);
    }
}
