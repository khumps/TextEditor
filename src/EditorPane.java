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

public class EditorPane extends JTextPane {

	protected CodeStyler style = new CodeStyler(this);
	private Timer timer;
	private File lastFile = null;
	protected String name;

	public EditorPane(Listener listener, String text, String name) {
		this.name = name;
		setContentType("text/html");
		// setText("<html>" + text + "</html>");
		setText("<b>new</b>");
		timer = new Timer(30, listener);
		setPreferredSize(new Dimension(1000, 1000));
		setFont(new Font("Consolas", Font.PLAIN, 40));
		// setSelectionColor(Color.BLUE);
		timer.setActionCommand("timer");
		addMouseListener(listener);
		addKeyListener(listener);
		createHighlights();
		timer.start();
	}

	public void createHighlights() {
		addTextHighlights();
		addBraceHighlights();
	}

	public void addTextHighlights() {
		style.addHighlight("<b>", "</b>", "abstract", "assert", "boolean",
				"break", "byte", "case", "catch", "char", "class", "continue",
				"default", "do", "double", "else", "enum", "extends", "final",
				"finally", "float", "for", "if", "implements", "import",
				"instanceof", "int", "interface", "long", "native", "new",
				"package", "private", "protected", "public", "return", "short",
				"static", "strictfp", "super", "switch", "synchronized",
				"this", "throw", "throws", "transient", "try", "void",
				"volatile", "while"); // keywords
	}

	public void addBraceHighlights() {
		style.addBraceHighlight('{', '}', Color.GREEN);
		style.addBraceHighlight('(', ')', Color.GREEN);
		style.addBraceHighlight('[', ']', Color.GREEN);
	}
}
