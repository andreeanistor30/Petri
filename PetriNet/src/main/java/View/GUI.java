/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Business.NetModes;
import Business.Simulation;
import Business.SteppedSimulation;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.*;

public class GUI extends javax.swing.JFrame {
    private Simulation simulator;

    private ArrayList buttonGroup1 = new ArrayList();
    public GUI() {
        initComponents();
        buttonGroup1.add(btnSelect);
        buttonGroup1.add(btnRun);
        buttonGroup1.add(btnRunStep);
        buttonGroup1.add(btnLocation);
        buttonGroup1.add(btnTransition);
    }
 private void disableButtons(javax.swing.JToggleButton button) {
        for (int i = 0; i < this.jToolBar1.getComponents().length; i++) {
            Component component = this.jToolBar1.getComponent(i);
            if (component instanceof javax.swing.JToggleButton) {
                if (!component.equals(button)) {
                    javax.swing.JToggleButton b = (JToggleButton) component;
                    b.setSelected(false);
                    }
                }
            }

        }
    public void steppedSimulation(java.awt.event.ActionEvent evt) {
        NetModes.mode = NetModes.simulationMode;
        setEnabledButtons((JButton) evt.getSource(), buttonGroup1, false);

        if (simulator == null) {
            simulator = new Simulation(true, this);
            simulator.start();
        } else {
            if (simulator.isAlive()) {
                if (simulator.isPaused()) {
                    simulator.setPaused(false);
                    synchronized (simulator) {
                        this.simulator.notify();
                    }
                }
            }
        }
    }
    public void stopSimulation(java.awt.event.ActionEvent evt) {
        setEnabledButtons((JButton) evt.getSource(), buttonGroup1, true);
        this.txtClock.setText("0");
        if (simulator != null) {
            simulator.setStop(true);
            while (simulator.isAlive()) {
            }
            simulator = null;
            this.btnStop.setVisible(false);
            canvas1.repaint();
        }
    }
    private void setEnabledButtons(JButton button, ArrayList buttons, boolean bool) {

        for (int j = 0; j < buttons.size(); j++) {
            AbstractButton btn = (AbstractButton) buttons.get(j);
            for (int i = 0; i < this.jToolBar1.getComponents().length; i++) {
                if (this.jToolBar1.getComponent(i) instanceof AbstractButton) {
                    AbstractButton b = (AbstractButton) this.jToolBar1.getComponent(i);
                    if (btn.equals(b) && !btn.equals(button)) {
                        if (b instanceof JToggleButton) {
                            ((JToggleButton) b).setSelected(false);
                        }
                        b.setEnabled(bool);

                    }
                }
            }
        }
        enableControlButtons(!bool);
    }
    public void enableControlButtons(boolean b) {
        btnStop.setVisible(b);
    }
    public void continuousSimulation(java.awt.event.ActionEvent evt) {
        NetModes.mode = NetModes.simulationMode;
        setEnabledButtons(null, buttonGroup1, false);
        ((JButton) (evt.getSource())).setEnabled(false);
        if (simulator == null) {
            simulator = new SteppedSimulation(false, this);
            simulator.start();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        canvas1 = new View.Canvas();
        jToolBar1 = new javax.swing.JToolBar();
        btnSelect = new javax.swing.JToggleButton();
        btnRun = new javax.swing.JButton();
        btnRunStep = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnLocation = new javax.swing.JToggleButton();
        btnTransition = new javax.swing.JToggleButton();
        btnArc = new javax.swing.JToggleButton();
        txtClock = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1024, 1024));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        jScrollPane3.setViewportView(canvas1);

        jToolBar1.setBackground(new java.awt.Color(255, 204, 204));
        jToolBar1.setRollover(true);

        btnRun.setIcon(new javax.swing.ImageIcon("C:\\Users\\nisto\\Downloads\\PetriNet\\PetriNet\\src\\main\\java\\GUI\\images\\play.png"));
        btnRun.setFocusable(false);
        btnRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRun);

        btnRunStep.setIcon(new javax.swing.ImageIcon("C:\\Users\\nisto\\Downloads\\PetriNet\\PetriNet\\src\\main\\java\\GUI\\images\\stepbystep.png")); // NOI18N
        btnRunStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRunStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRunStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunStepActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRunStep);

        btnStop.setIcon(new javax.swing.ImageIcon("C:\\Users\\nisto\\Downloads\\PetriNet\\PetriNet\\src\\main\\java\\GUI\\images\\stop.png")); // NOI18N
        btnStop.setFocusable(false);
        btnStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jToolBar1.add(btnStop);
        jToolBar1.add(jSeparator2);

        btnLocation.setIcon(new javax.swing.ImageIcon("C:\\Users\\nisto\\Downloads\\PetriNet\\PetriNet\\src\\main\\java\\GUI\\images\\createPlace.png")); // NOI18N
        btnLocation.setFocusable(false);
        btnLocation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLocation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocationActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLocation);

        btnTransition.setIcon(new javax.swing.ImageIcon("C:\\Users\\nisto\\Downloads\\PetriNet\\PetriNet\\src\\main\\java\\GUI\\images\\Transition.png")); // NOI18N
        btnTransition.setFocusable(false);
        btnTransition.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTransition.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransitionActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTransition);

        btnArc.setIcon(new javax.swing.ImageIcon("C:\\Users\\nisto\\Downloads\\PetriNet\\PetriNet\\src\\main\\java\\GUI\\images\\Arc.png")); // NOI18N
        btnArc.setFocusable(false);
        btnArc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnArc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnArc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArcActionPerformed(evt);
            }
        });

        jToolBar1.add(btnArc);

        btnSelect.setSelected(true);
        setSelectionMode();
        btnSelect.setIcon(new javax.swing.ImageIcon("C:\\Users\\nisto\\Downloads\\PetriNet\\PetriNet\\src\\main\\java\\GUI\\images\\Select.png"));
        btnSelect.setFocusable(false);
        btnSelect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSelect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSelect);

        btnDelete.setIcon(new javax.swing.ImageIcon("C:\\Users\\nisto\\Downloads\\PetriNet\\PetriNet\\src\\main\\java\\GUI\\images\\delete.png"));
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);

            }
        });

        jToolBar1.add(btnDelete);

        txtClock.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtClock.setText("0");
        txtClock.setHorizontalAlignment(SwingConstants.TRAILING);
        txtClock.setEditable(false);

        jToolBar1.add(txtClock);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane3)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGap(12, 12, 12))))
        );

        pack();
    }

    private void btnLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocationActionPerformed

        disableButtons((JToggleButton) evt.getSource());
        setSelectionMode();
        NetModes.mode = NetModes.placeMode;
    }

    private void btnTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransitionActionPerformed

       disableButtons((JToggleButton) evt.getSource());
       setSelectionMode();
       NetModes.mode = NetModes.transitionMode;
    }

    private void btnArcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArcActionPerformed

       disableButtons((JToggleButton) evt.getSource());
       setSelectionMode();
       NetModes.mode = NetModes.arcMode;
    }

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        stopSimulation(evt);
    }

    private void btnRunStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        steppedSimulation(evt);
    }

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            continuousSimulation(evt);
    }

     private void btnSelectActionPerformed(java.awt.event.ActionEvent evt){
        disableButtons((JToggleButton) evt.getSource());
        setSelectionMode();
        NetModes.mode = NetModes.selectMode;
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt){
        canvas1.getSelection().setSelectionEnabled(true);

        canvas1.getSelection().removeSelectedFigures();

        setSelectionMode();


    }

    private void setSelectionMode()
    {
        if(this.btnSelect.isSelected()){
            canvas1.getSelection().setSelectionEnabled(true);
        }
        else
            canvas1.getSelection().setSelectionEnabled(false);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    private javax.swing.JToggleButton btnArc;
    private javax.swing.JToggleButton btnLocation;
    private javax.swing.JToggleButton btnTransition;

    private javax.swing.JToggleButton btnSelect;
    private View.Canvas canvas1;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnRunStep;
    private javax.swing.JButton btnStop;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtClock;

    private javax.swing.JButton btnDelete;
    public View.Canvas getCanvas() {
    return canvas1;
    }

    public javax.swing.JTextField getTxtClock(){return txtClock;}

}
        
