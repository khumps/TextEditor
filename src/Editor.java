import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledEditorKit;

public class Editor extends JPanel
    {
	private Listener listener = new Listener(this);
	private JEditorPane file = new JEditorPane();
	private JFileChooser files = new JFileChooser();
	private JMenuBar menu = new JMenuBar();
	private StyledEditorKit style = new StyledEditorKit();
	private JMenu fileMenu = new JMenu("File");
	private JMenu editMenu = new JMenu("Edit");
	private DefaultStyledDocument doc = new DefaultStyledDocument();
	JFrame frame = new JFrame();

	public Editor()
	    {
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		menu.setFont(new Font(getFont().getFontName(), Font.BOLD, 40));
		file.set
		frame.add(file);
		initMenuBar();
		frame.pack();
		frame.setVisible(true);
	    }

	private void initMenuBar()
	    {
		frame.setJMenuBar(menu);
		initFileMenu();
		// initEditMenu();
	    }

	private void initFileMenu()
	    {
		menu.add(fileMenu);
		Utils.newMenuItem(Utils.button, "New", "newFile", listener, fileMenu);
		Utils.newMenuItem(Utils.button, "Open", "openFile", listener, fileMenu);
		Utils.newMenuItem(Utils.button, "Save", "saveFile", listener, fileMenu);
	    }

	public static void main(String[] args)
	    {
		new Editor();
	    }
    }
