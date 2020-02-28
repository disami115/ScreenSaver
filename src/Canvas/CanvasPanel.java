package Canvas;

import javax.swing.*;
import java.awt.*;
 
 
public class CanvasPanel extends JPanel {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JScrollPane pane;
	
    public CanvasPanel(boolean isDoubleBuffered, MyCanvas canvas) {
        super(isDoubleBuffered);
        setLayout(new BorderLayout());
        pane = new JScrollPane(canvas);
        //pane.getViewport().setBackground(Color.DARK_GRAY);
        //changePanel(canvas.img.getWidth(null) - canvas.X, canvas.img.getHeight(null) - canvas.Y);
        add(pane, BorderLayout.CENTER);
    }
    
    public static void changePanel(int w, int h) {
    	pane.setBounds(0, 0, w, h);
    }
 
}