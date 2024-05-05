import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorder implements Border {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius, this.radius, this.radius, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return false; // We are not filling the border, so it's not opaque
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Create a rounded rectangle shape
        Shape borderShape = new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius);

        // Set clip to the rounded rectangle shape
        g2.setClip(borderShape);

        // Fill the clip area with the component's background color
        g2.setColor(c.getBackground());
        g2.fillRect(x, y, width, height);

        g2.dispose(); // Dispose the Graphics2D object
    }
}
