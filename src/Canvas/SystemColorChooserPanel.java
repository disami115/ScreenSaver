package Canvas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import GUIs.SecondGUI;

public class SystemColorChooserPanel extends AbstractColorChooserPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void buildChooser() {
		setLayout(new GridBagLayout());
	    makeAddButton(0, Color.black); 
	    makeAddButton(0, Color.gray);
	    makeAddButton(0, Color.blue);
	    makeAddButton(0, Color.red);
	    makeAddButton(0, Color.magenta);
	    makeAddButton(0, Color.orange);
	    makeAddButton(1, Color.white);
	    makeAddButton(1, Color.lightGray);
	    makeAddButton(1, Color.cyan); 
	    makeAddButton(1, Color.pink);
	    makeAddButton(1, Color.green);
	    makeAddButton(1, Color.yellow);
	  }

	  public void updateChooser() {
	  }

	  public String getDisplayName() {
	    return "";
	  }

	  public Icon getSmallDisplayIcon() {
	    return null;
	  }
	  public Icon getLargeDisplayIcon() {
	    return null;
	  }
	  private void makeAddButton(int i, Color color) {
	    JButton button = new JButton();
	    GridBagConstraints constraints = new GridBagConstraints(); 
	    constraints.ipadx = 10;
	    constraints.ipady = 10;
	    constraints.gridy = i;
	    button.setBackground(color);
	    button.setAction(setColorAction);
	    add(button, constraints);
	  }

	  Action setColorAction = new AbstractAction() {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent evt) {
	      JButton button = (JButton) evt.getSource();
	      MyCanvas.setColor(button.getBackground());
	      SecondGUI.textField.setForeground(button.getBackground());
	      SecondGUI.userPrefsColor.putInt("r", button.getBackground().getRed());
	      SecondGUI.userPrefsColor.putInt("g", button.getBackground().getGreen());
	      SecondGUI.userPrefsColor.putInt("b", button.getBackground().getBlue());
	      getColorSelectionModel().setSelectedColor(button.getBackground());
	    }
	  };
	  
}
  
