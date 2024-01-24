package controllers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import models.Nota;

public class NotasMain {
    private static final Color COLOR_BACKGROUND = Color.WHITE;
    private static final Color COLOR_PRINCIPAL = Color.decode("#EEF5FF");
    private static final Color COLOR_PRINCIPAL_VARIANTE = Color.decode("#B4D4FF");
    private static final Color COLOR_FUENTE = Color.BLACK;
    private final Dimension SIZE = new Dimension(400, 400);
    private static ArrayList<Nota> notas;
    private Nota notaSeleccionada;
    private static String[] nombreColumnas = {ResourceBundle.getBundle("languages").getString("lNombre"),
            ResourceBundle.getBundle("languages").getString("lApellidos"),
            ResourceBundle.getBundle("languages").getString("lNota")};
    private JLabel lTitulo;
    private JTable table;
    private JPanel buttonPanel;
    private JScrollPane tableScrollPane;
    public JPanel mainPanel;
    private JButton bCrear;
    private JButton bModificar;
    private JButton bEliminar;

    public NotasMain(JFrame frame) {
        mainPanel.setBackground(COLOR_BACKGROUND);
        bCrear.setBackground(COLOR_PRINCIPAL);
        bModificar.setBackground(COLOR_PRINCIPAL);
        bEliminar.setBackground(COLOR_PRINCIPAL);
        table.setBackground(COLOR_PRINCIPAL);
        table.getTableHeader().setBackground(COLOR_PRINCIPAL_VARIANTE);

        try{
            notas = controllers.Streams.importarNotas();
        }catch (IOException e){
            JOptionPane.showMessageDialog(frame, ResourceBundle.getBundle("languages").getString("lErrorCuerpo"), ResourceBundle.getBundle("languages").getString("lErrorTitulo"),JOptionPane.ERROR_MESSAGE);
        }catch (Exception e){
            e.printStackTrace();
            notas = new ArrayList<Nota>();
        }
        setEnabledButtons(true, false, false);
        table.setModel(new DefaultTableModel(nombreColumnas, 0));
        actualizarTabla(table);

        bCrear.setToolTipText("Ctrl+A");

        bCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameForm = new JFrame(ResourceBundle.getBundle("languages").getString("bCrear"));
                frameForm.setContentPane(new NotasForm(frameForm, table, notas).mainPanel);
                frameForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameForm.pack();
                frameForm.setBounds(frame.getLocation().x + SIZE.width / 2 - 50, frame.getLocation().y + SIZE.height / 2 - 100, SIZE.width, SIZE.height);
                frameForm.setVisible(true);
                frameForm.setResizable(false);
            }
        });

        bModificar.setToolTipText("Ctrl+M");

        bModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameForm = new JFrame(ResourceBundle.getBundle("languages").getString("bModificar"));
                frameForm.setContentPane(new NotasForm(frameForm, table, notas, notaSeleccionada).mainPanel);
                frameForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameForm.pack();
                frameForm.setBounds(frame.getLocation().x + SIZE.width / 2, frame.getLocation().y + SIZE.height / 2 - 100, SIZE.width, SIZE.height);
                frameForm.setVisible(true);
                frameForm.setResizable(false);
                setEnabledButtons(true, false, false);
                notaSeleccionada = null;
            }
        });

        bEliminar.setToolTipText("Ctrl+D");

        bEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notas.remove(notaSeleccionada);
                actualizarTabla(table);
                setEnabledButtons(true, false, false);
                notaSeleccionada = null;
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (table.getSelectedRow() != -1) {
                    for (int i = 0; i < notas.size(); i++) {
                        if (notas.get(i).getNombre().equals(table.getValueAt(table.getSelectedRow(), 0))
                                && notas.get(i).getApellidos().equals(table.getValueAt(table.getSelectedRow(), 1))
                                && notas.get(i).getNota().equals(table.getValueAt(table.getSelectedRow(), 2))) {
                            notaSeleccionada = notas.get(i);
                            setEnabledButtons(true, true, true);
                        }
                    }
                } else {
                    setEnabledButtons(true, false, false);
                }
            }
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_A) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                    bCrear.doClick();
                }else if ((e.getKeyCode() == KeyEvent.VK_M) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0 && notaSeleccionada != null)) {
                    bModificar.doClick();
                }else if ((e.getKeyCode() == KeyEvent.VK_D) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0 && notaSeleccionada != null)) {
                    bEliminar.doClick();
                }
            }
        });

        table.requestFocus();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(JOptionPane.showConfirmDialog(frame, ResourceBundle.getBundle("languages").getString("lSalirCuerpo"), ResourceBundle.getBundle("languages").getString("lSalirTitulo"), JOptionPane.OK_CANCEL_OPTION) == 0)
                try {
                    controllers.Streams.exportarNotas(notas);
                }catch(Exception ex) {
                    JOptionPane.showMessageDialog(frame, ResourceBundle.getBundle("languages").getString("lErrorCuerpoSalir"), ResourceBundle.getBundle("languages").getString("lErrorTitulo"),JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void actualizarTabla(JTable table){
        String[][] data = new String[notas.size()][3];
        for(int i = 0; i < notas.size(); i++){
            String[] row = {notas.get(i).getNombre(), notas.get(i).getApellidos(), notas.get(i).getNota()};
            data[i] = row;
        }
        table.setModel(new DefaultTableModel(data, nombreColumnas));
        table.setCellSelectionEnabled(false);
        table.repaint();
        table.revalidate();
    }
    private void setEnabledButtons (boolean crear, boolean modificar, boolean eliminar){
        if(crear){
            bCrear.setBackground(COLOR_PRINCIPAL);
        }else{
            bCrear.setBackground(COLOR_PRINCIPAL_VARIANTE);
        }
        bCrear.setEnabled(crear);

        if(modificar){
            bModificar.setBackground(COLOR_PRINCIPAL);
        }else{
            bModificar.setBackground(COLOR_PRINCIPAL_VARIANTE);
        }
        bModificar.setEnabled(modificar);

        if(eliminar){
            bEliminar.setBackground(COLOR_PRINCIPAL);
        }else{
            bEliminar.setBackground(COLOR_PRINCIPAL_VARIANTE);
        }
        bEliminar.setEnabled(eliminar);
    }

    public ArrayList<Nota> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }
}
