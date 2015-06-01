import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

public class Editor extends JTextPane
    {
	private Listener listener = new Listener(this);
	private JFileChooser fileChooser;
	private JMenuBar menu = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	protected CodeStyler style = new CodeStyler(this);
	JFrame frame = new JFrame();
	private Timer timer = new Timer(30, listener);
	private File lastFile = null;

	public Editor()
	    {
		setPreferredSize(new Dimension(1000, 1000));
		setFont(new Font("Consolas", Font.PLAIN, 40));
		setSelectionColor(Color.BLUE);
		timer.setActionCommand("timer");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		menu.setFont(new Font(getFont().getFontName(), Font.BOLD, 40));
		frame.add(this);
		initMenuBar();
		initFileChooser();
		createHighlights();
		frame.pack();
		frame.setVisible(true);
		timer.start();
	    }

	private void initMenuBar()
	    {
		frame.setJMenuBar(menu);
		initFileMenu();
	    }

	private void initFileMenu()
	    {
		menu.add(fileMenu);
		Utils.newMenuItem(Utils.button, "New", "newFile", listener, fileMenu);
		Utils.newMenuItem(Utils.button, "Open", "openFile", listener, fileMenu);
		Utils.newMenuItem(Utils.button, "Save", "saveFile", listener, fileMenu);
	    }

	protected void initFileChooser()
	    {
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Java Files", "java"));
	    }

	protected void openFileChooser()
	    {
		if (lastFile != null)
		    fileChooser = new JFileChooser(lastFile);
		fileChooser.showOpenDialog(this);
		String doc = "";
		String toAdd = null;
		try
		    {
			lastFile = fileChooser.getSelectedFile();
			if (lastFile != null)
			    {
				FileReader reader = new FileReader(lastFile);
				BufferedReader bReader = new BufferedReader(reader);
				while ((toAdd = bReader.readLine()) != null)
				    doc += toAdd;
				setText(doc);
				bReader.close();
			    }
		    } catch (IOException e)
		    {
		    }

	    }

	protected void saveFileChooser()
	    {
		if (lastFile != null)
		    fileChooser = new JFileChooser(lastFile);
		fileChooser.showSaveDialog(this);
		try
		    {
			lastFile = fileChooser.getSelectedFile();
			if (lastFile != null)
			    {
				FileWriter writer = new FileWriter(lastFile);
				writer.write(getDocument().getText(0, getDocument().getLength()));
				writer.close();
			    }
		    } catch (IOException | BadLocationException e)
		    {
		    }
	    }

	public void createHighlights()
	    {
		addTextHighlights();
		addBraceHighlights();
	    }

	public void addTextHighlights()
	    {
		style.addHighlight(Color.ORANGE, "abstract", "assert", "boolean", "break", "byte",
			"case", "catch", "char", "class", "continue", "default", "do", "double",
			"else", "enum", "extends", "final", "finally", "float", "for", "if",
			"implements", "import", "instanceof", "int", "interface", "long", "native",
			"new", "package", "private", "protected", "public", "return", "short",
			"static", "strictfp", "super", "switch", "synchronized", "this", "throw",
			"throws", "transient", "try", "void", "volatile", "while"); // keywords
	    }

	public void addBraceHighlights()
	    {
		style.addBraceHighlight('{', '}', Color.GREEN);
		style.addBraceHighlight('(', ')', Color.GREEN);
		style.addBraceHighlight('[', ']', Color.GREEN);
	    }

	public static void main(String[] args)
	    {
		new Editor();
	    }
    }
