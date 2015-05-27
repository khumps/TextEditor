import java.awt.Color;
import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledEditorKit;

public class Editor extends JPanel
	{
		private Listener listener = new Listener(this);
		private JEditorPane file = new JEditorPane();
		private JFileChooser files = new JFileChooser();
		private JMenuBar menu = new JMenuBar();
		private JMenu fileMenu = new JMenu("File");
		private JMenu editMenu = new JMenu("Edit");
		private DefaultStyledDocument doc = new DefaultStyledDocument();
		protected TextStyle style = new TextStyle(file);
		JFrame frame = new JFrame();
		private Timer timer = new Timer(30, listener);

		public Editor()
			{
				timer.setActionCommand("timer");
				frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
				menu.setFont(new Font(getFont().getFontName(), Font.BOLD, 40));
				frame.add(file);
				initMenuBar();
				createHighlights();
				frame.pack();
				frame.setVisible(true);
				timer.start();
			}

		private void initMenuBar() {
			frame.setJMenuBar(menu);
			initFileMenu();
			// initEditMenu();
		}

		private void initFileMenu() {
			menu.add(fileMenu);
			Utils.newMenuItem(Utils.button, "New", "newFile", listener, fileMenu);
			Utils.newMenuItem(Utils.button, "Open", "openFile", listener, fileMenu);
			Utils.newMenuItem(Utils.button, "Save", "saveFile", listener, fileMenu);
		}

		public void createHighlights() {
			style.addHighlight("new", Color.GREEN);
			style.addHighlight("class", Color.BLUE);

		}

		public static void main(String[] args) {
			new Editor();
		}
	}
