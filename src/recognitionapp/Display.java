package recognitionapp;

import AIToolkit.Supervisioned.KnowledgeBase.KnowledgeBaseItem;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * A Grey Panel in which the user may draw the number.
 * 
 * @author Luan
 */
public class Display extends javax.swing.JPanel {

    private JPanel[][] elements;

    private boolean drawing = false;
    private boolean isDisabled = false;
    private final Color ACTIVATED = Color.DARK_GRAY;
    private final Color DESACTIVATED = Color.GRAY;

    public Display() {
        initComponents();
        init();
    }

    /**
     * Instanciate the required components to render and interact with the display.
     */
    private void init() {
        elements = new JPanel[28][28];
        this.removeAll();
        this.setLayout(new GridLayout(28, 28));
        for (int y = 0; y < 28; y++) {
            for (int x = 0; x < 28; x++) {
                this.add(createDisplayElement(x, y));
            }
        }

        this.updateUI();
    }

    /**
     * The user may not interact with the display if it is not enabled.
     * @param enabled  Whether or not do allow the user interaction.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.isDisabled = !enabled;
    }

    /**
     * Clears and resets the display.
     */
    public void erase() {
        for (int y = 0; y < 28; y++) {
            for (int x = 0; x < 28; x++) {
                elements[y][x].setBackground(DESACTIVATED);
            }
        }
    }

    /**
     * Instanciate, configure and returns a new instance of a JPanel to be used
     * by the user to draw.
     * 
     * @param x X coordinate
     * @param y Y coordinate
     * @return An instance of a JPanel
     */
    private JPanel createDisplayElement(int x, int y) {
        elements[y][x] = new JPanel();
        elements[y][x].setBackground(DESACTIVATED);
        elements[y][x].addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent me) {
                if (isDisabled) {
                    return;
                }
                drawing = true;
                draw(y, x, 0, 0, 2, false);
            }

            private boolean inbound(int y, int x) {
                return x >= 0 && x < 28 && y >= 0 && y < 28;
            }

            private boolean inLimits(int offsetY, int offsetX, int distance) {
                int distanceBehind = distance * -1;
                return offsetX < distance && offsetX > distanceBehind
                        && offsetY < distance && offsetY > distanceBehind;
            }

            private void draw(int y, int x, int offsetY, int offsetX, int distance, boolean erasing) {
                if (isDisabled
                        || !inLimits(offsetY, offsetX, distance)
                        || !inbound(x + offsetX, y + offsetY)) {
                    return;
                }
                if (elements[y + offsetY][x + offsetX].getBackground() == ACTIVATED && erasing) {
                    elements[y + offsetY][x + offsetX].setBackground(DESACTIVATED);
                } else {
                    elements[y + offsetY][x + offsetX].setBackground(ACTIVATED);
                }

                if (offsetY <= 0) {
                    draw(y, x, offsetY - 1, offsetX, distance, erasing);
                }
                if (offsetY >= 0) {
                    draw(y, x, offsetY + 1, offsetX, distance, erasing);
                }
                if (offsetX >= 0 && offsetY == 0) {
                    draw(y, x, offsetY, offsetX + 1, distance, erasing);
                }
                if (offsetX <= 0 && offsetY == 0) {
                    draw(y, x, offsetY, offsetX - 1, distance, erasing);
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                if (drawing) {
                    draw(y, x, 0, 0, 2, false);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                drawing = false;
            }

        });

        return elements[y][x];
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 4, true));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 269, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Returns an KnowledgeBaseItem of Integer that can be used to compare it
     * with the data in the knowledge base.
     * 
     * @return KnowledgeBaseItem of the current drawing.
     */
    public KnowledgeBaseItem<Integer> getDrawingItem() {
        KnowledgeBaseItem<Integer> item = new KnowledgeBaseItem<>();
        ArrayList<Integer> items = new ArrayList<>();

        for (int y = 0; y < 28; y++) {
            for (int x = 0; x < 28; x++) {
                items.add(elements[x][y].getBackground() == ACTIVATED ? 1 : 0);
            }
        }
        item.setItems(items);

        return item;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
