import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class Utils
    {
	static Dimension button = new Dimension(100, 30);

	public static JMenuItem newMenuItem(Dimension size, String text, String command,
		ActionListener listener, JComponent bar)
	    {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.addActionListener(listener);
		menuItem.setActionCommand(command);
		menuItem.setPreferredSize(size);
		bar.add(menuItem);
		return menuItem;
	    }

	public static JButton newButton(Dimension size, String text, String command,
		ActionListener listener)
	    {
		JButton button = new JButton(text);
		button.addActionListener(listener);
		button.setActionCommand(command);
		button.setPreferredSize(size);
		return button;
	    }
    }