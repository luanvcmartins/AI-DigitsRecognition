package recognitionapp;

import AIToolkit.Distances.CosineDistance;
import AIToolkit.Distances.DTWDistance;
import AIToolkit.Distances.EuclideanDistance;
import AIToolkit.Distances.ManhattanDistance;
import AIToolkit.Supervisioned.KnowledgeBase.KnowledgeBase;
import AIToolkit.Supervisioned.KnowledgeBase.KnowledgeBaseItem;
import AIToolkit.Supervisioned.NearestNeighbor.KNearestNeighbor;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;
import sun.security.krb5.internal.KDCOptions;

/**
 * The main form and start point of this application.
 *
 * @author luan
 */
public class MainApp extends javax.swing.JFrame {

    // We will keep the reference to the algorithm here:
    private KNearestNeighbor<Integer, String> knn;

    /**
     * Creates new form MainApp
     */
    public MainApp() {
        knn = new KNearestNeighbor<>();
        initComponents();
        loadingPanel.setVisible(false);

        txtFileLocation.setText("C:\\Users\\Luan\\Downloads\\mnist_png.tar\\mnist_png\\mnist_png\\training\\");

        txtK.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                knn.setK(Integer.parseInt(txtK.getValue().toString()));
            }
        });
    }

    /**
     * Instanciate the knowledge base by reading the images and storing in the
     * memory.
     *
     * @param directoryLocation The location of the base.
     * @throws IOException
     */
    private void readData(String directoryLocation) {
        new Thread(() -> {
            disableUI();
            loadingPanel.setVisible(true);
            txtStatus.setText("Starting to load knowleged base.");

            File directory = new File(directoryLocation);
            if (!directory.exists()) {
                loadingPanel.setVisible(false);
                enableUI();
                JOptionPane.showMessageDialog(this, "The specified directory doesn't exists.");
                return;
            }

            // We will keep the read data here:
            ArrayList<KnowledgeBaseItem<Integer>> baseItems = new ArrayList<>();

            // The given URL should point to the folder with the examples:
            for (File classes : directory.listFiles()) {
                txtStatus.setText("Reading class " + classes.getName() + ". Please wait.");
                // We are looking for directories, we will skip any files:
                if (classes.isFile()) {
                    continue;
                }
                
                int maxSample = 1000, cSample = 0;
                for (File example : classes.listFiles()) {
                    if (++cSample > maxSample) {
                        break;
                    }
                    // We will ignore any directories, we are looking for samples
                    // of this class, .png files:
                    if (!example.isFile()) {
                        continue;
                    }

                    try {
                        // We will read the image and put it in a bufferedImage instance:
                        BufferedImage bi = ImageIO.read(example);
                        Raster img = bi.getData();
                        ArrayList<Integer> items = new ArrayList<>();

                        // For each pixel of the image...
                        for (int y = 0; y < img.getWidth(); y++) {
                            for (int x = 0; x < img.getHeight(); x++) {
                                // The 4 item is the alpha channel which we will ignore it:
                                int[] pixels = new int[3];
                                img.getPixel(y, x, pixels);

                                // We devide by 3 to grayscale it:
                                if (pixels[0] > 1) {
                                    items.add(1);
                                } else {
                                    items.add(0);
                                }
                            }
                        }

                        // We now add it to the knowledge base item list:
                        baseItems.add(new KnowledgeBaseItem<>(items, classes.getName()));
                    } catch (Exception ignored) {
                    }
                }
            }
            txtStatus.setText("Done.");

            // We are now ready to create the knowledge base:
            KnowledgeBase<Integer> base = new KnowledgeBase<>(baseItems);
            knn = new KNearestNeighbor<>(200, base, new EuclideanDistance());
            enableUI();
            loadingPanel.setVisible(false);
        }).start();
    }

    /**
     * Disable all components of the UI.
     */
    private void disableUI() {
        btnDetect.setEnabled(false);
        btnSelect.setEnabled(false);
        txtK.setEnabled(false);
        btnEraser.setEnabled(false);
        cmbAlgorithm.setEnabled(false);
        txtFileLocation.setEnabled(false);
        display.setEnabled(false);
    }

    /**
     * Enable all components of the UI.
     */
    private void enableUI() {
        btnDetect.setEnabled(true);
        btnSelect.setEnabled(true);
        txtK.setEnabled(true);
        btnEraser.setEnabled(true);
        cmbAlgorithm.setEnabled(true);
        txtFileLocation.setEnabled(true);
        display.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtFileLocation = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnSelect = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        cmbAlgorithm = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtK = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        display = new recognitionapp.Display();
        jPanel2 = new javax.swing.JPanel();
        btnDetect = new javax.swing.JButton();
        loadingPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JLabel();
        btnEraser = new javax.swing.JButton();
        txtLastResult = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(530, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuration"));
        jPanel1.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N

        txtFileLocation.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txtFileLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFileLocationActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setText("MNIST PNG Dataset location:");

        btnSelect.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        btnSelect.setText("Select");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        cmbAlgorithm.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cmbAlgorithm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Euclidean Distance", "Manhattan Distance", "Data Time Warping", "Cosine Distance" }));
        cmbAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAlgorithmActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel2.setText("Distance:");

        txtK.setModel(new javax.swing.SpinnerNumberModel(3, 3, 500, 3));
        txtK.setToolTipText("");
        txtK.setValue(7);
        txtK.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtKPropertyChange(evt);
            }
        });

        jLabel4.setText("K-Value:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtFileLocation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelect))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtK, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFileLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelect))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(583, 61));

        btnDetect.setText("Detect");
        btnDetect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetectActionPerformed(evt);
            }
        });

        loadingPanel.setPreferredSize(new java.awt.Dimension(254, 60));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recognitionapp/resource/loading.GIF"))); // NOI18N

        txtStatus.setText("jLabel4");

        javax.swing.GroupLayout loadingPanelLayout = new javax.swing.GroupLayout(loadingPanel);
        loadingPanel.setLayout(loadingPanelLayout);
        loadingPanelLayout.setHorizontalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadingPanelLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStatus)
                .addGap(0, 134, Short.MAX_VALUE))
        );
        loadingPanelLayout.setVerticalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadingPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnEraser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recognitionapp/resource/eraser.png"))); // NOI18N
        btnEraser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEraserActionPerformed(evt);
            }
        });

        txtLastResult.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtLastResult.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loadingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEraser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLastResult, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDetect, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loadingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDetect, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnEraser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLastResult)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("<html>\n<center>\nAfter selecting the MNIST dataset location,<br>\n draw a digit between 0 and 9 and<br>\nclick \"detect\" to run KNN to<br>\nguess what your digit is.");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(169, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(display, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(display, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDetectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetectActionPerformed
        new Thread(() -> {
            try {
                // Disable the components and update the progress:
                loadingPanel.setVisible(true);
                txtLastResult.setText("");
                txtStatus.setText("Running KNN. Please wait.");
                disableUI();
                
                // Execute the algorithm:
                String result = knn.classify(display.getDrawingItem());
                
                // Show the result and resset the UI:
                txtStatus.setText("Done.");
                loadingPanel.setVisible(false);
                enableUI();
                txtLastResult.setText("Last result: " + result);
                JOptionPane.showMessageDialog(this, "Digit detected: " + result);
            } catch (Exception ex) {
                // If an error happened it is likely to do with the dataset:
                loadingPanel.setVisible(false);
                enableUI();
                JOptionPane.showMessageDialog(this, "Something went wrong. Make sure you instanciated the knowledge base correctly.", "", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }).start();
    }//GEN-LAST:event_btnDetectActionPerformed

    private void btnEraserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEraserActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to erase the board?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            display.erase();
            txtLastResult.setText("");
        }
    }//GEN-LAST:event_btnEraserActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            txtFileLocation.setText(fc.getSelectedFile().getPath());
            readData(txtFileLocation.getText());
        }
    }//GEN-LAST:event_btnSelectActionPerformed

    private void txtFileLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFileLocationActionPerformed
        readData(txtFileLocation.getText());
    }//GEN-LAST:event_txtFileLocationActionPerformed

    private void txtKPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtKPropertyChange
        knn.setK(Integer.parseInt(txtK.getValue().toString()));
    }//GEN-LAST:event_txtKPropertyChange

    private void cmbAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAlgorithmActionPerformed
        switch (cmbAlgorithm.getSelectedIndex()) {
            case 0:
                knn.setDistance(new EuclideanDistance());
                break;
            case 1:
                knn.setDistance(new ManhattanDistance());
                break;
            case 2:
                knn.setDistance(new DTWDistance());
                break;
            case 3:
                knn.setDistance(new CosineDistance());
                break;
        }
    }//GEN-LAST:event_cmbAlgorithmActionPerformed

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
            // javax.swing.UIManager.setLookAndFeel(new MaterialLookAndFeel());
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetect;
    private javax.swing.JButton btnEraser;
    private javax.swing.JButton btnSelect;
    private javax.swing.JComboBox<String> cmbAlgorithm;
    private recognitionapp.Display display;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel loadingPanel;
    private javax.swing.JTextField txtFileLocation;
    private javax.swing.JSpinner txtK;
    private javax.swing.JLabel txtLastResult;
    private javax.swing.JLabel txtStatus;
    // End of variables declaration//GEN-END:variables
}
