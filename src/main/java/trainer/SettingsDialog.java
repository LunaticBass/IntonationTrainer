
package trainer;

import java.util.Optional;

public class SettingsDialog extends javax.swing.JDialog {
    GamePanel panel;
    boolean start;
    

    public SettingsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        keyBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        typeBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        difficultyBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        startBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Challenge settings");

        keyBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab", "A", "A#/Bb", "B" }));

        jLabel2.setText("Key");

        typeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Major scale", "Minor scale(natural)" }));
        typeBox.setLightWeightPopupEnabled(false);

        jLabel3.setText("Type");

        difficultyBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "easy", "normal", "hard" }));
        difficultyBox.setSelectedIndex(1);

        jLabel4.setText("Difficulty");

        startBtn.setText("Start");
        startBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startBtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel5.setText("concert pitch A = 443Hz");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(startBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(keyBox, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(difficultyBox, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5)))))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keyBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(difficultyBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(startBtn)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBtnActionPerformed
        this.start = true;
        
        double keyRootFrequency = keyRootselector();       
        double[] timing = challengeTypeselector()[0];
        double[] pattern = challengeTypeselector()[1];
        double recordLength = difficultySelector();
        
        panel = new GamePanel(keyRootFrequency, timing, pattern, recordLength);  
        
       dispose();
    }//GEN-LAST:event_startBtnActionPerformed

    public Optional<GamePanel> getPanelka(){
        if(this.start){
            return Optional.of(panel);
        }else{
            return Optional.empty();
        }        
    }
    
    public double keyRootselector(){ // select the root note (443Hz concert pitch)
        double keyRootFrequency = 0; 
        switch(keyBox.getSelectedIndex()){
            case 0:
                keyRootFrequency = 16.4630625; // C0
                break;
            case 1:
                keyRootFrequency = 17.44203125; // C#0
                break;
            case 2:
                keyRootFrequency = 18.4791875; // D0
                break;
            case 3:
                keyRootFrequency = 19.578; // D#0
                break;
            case 4:
                keyRootFrequency = 20.7421875; // E0
                break;
            case 5:
                keyRootFrequency = 21.9755625; // F0
                break;
            case 6:
                keyRootFrequency = 23.2823125; // F#0
                break;
            case 7:
                keyRootFrequency = 24.66675; // G0
                break;
            case 8:
                keyRootFrequency = 26.1335; // G#0
                break;
            case 9:
                keyRootFrequency = 27.6875; // A0
                break;
            case 10:
                keyRootFrequency = 29.333875; // A#0
                break;
            case 11:
                keyRootFrequency = 31.078125; // B0
                break;
        }
        return keyRootFrequency;
    }
    
    public double[][] challengeTypeselector(){ // major scale, minor scale, later triads, etc.
        double[][] settingsArray = new double[2][];
        settingsArray[0] = new double[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}; // default timing with scale, 2x7 notes, every note length is 2 querter notes
        
        switch(typeBox.getSelectedIndex()){
            case 0:
                settingsArray[1] = new double[]{0,200,400,500,700,900,1100,0,0,1100,900,700,500,400,200,0}; // major scale pattern, difference from root tone in cents
                break;
            case 1:
                settingsArray[1] = new double[]{0,200,300,500,700,800,1000,0,0,1000,800,700,500,300,200,0}; // minor scale pattern, difference from root tone in cents
                break;
        }
       
        return settingsArray;
    }
    
    public double difficultySelector(){ // selecting difficulty = speed
        double difficulty = 0;
        switch(difficultyBox.getSelectedIndex()){
            case 0:
                difficulty = 20;
                break;
            case 1:
                difficulty = 15;
                break;
            case 2:
                difficulty = 10;
                break;
        }
        return difficulty;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SettingsDialog dialog = new SettingsDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> difficultyBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox<String> keyBox;
    private javax.swing.JButton startBtn;
    private javax.swing.JComboBox<String> typeBox;
    // End of variables declaration//GEN-END:variables
}