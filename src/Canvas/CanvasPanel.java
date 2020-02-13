package Canvas;

import javax.swing.*;
import java.awt.*;
 
 
public class CanvasPanel extends JPanel {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
    public CanvasPanel(boolean isDoubleBuffered, MyCanvas canvas) {
        super(isDoubleBuffered);
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane(canvas);
        pane.getViewport().setBackground(Color.DARK_GRAY);
        add(pane, BorderLayout.CENTER);
    }
 
}