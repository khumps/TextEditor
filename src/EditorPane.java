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

/**
 * The place where the user inputs text which is displayed with proper
 * formatting
 * 
 * @author Kevin Humphreys
 * 
 */
public class EditorPane extends JTextPane {

	protected CodeStyler style = new CodeStyler(this);
	private Timer timer;
	protected String name;

	public EditorPane(Listener listener, String text, String name, int fontSize) {
		this.name = name;
		timer = new Timer(30, listener);
		setText(text);
		setPreferredSize(new Dimension(1000, 1000));
		setFont(new Font("Consolas", Font.PLAIN, fontSize));
		// setSelectionColor(Color.BLUE);
		timer.setActionCommand("timer");
		addMouseListener(listener);
		createHighlights();
		timer.start();
	}

	public void createHighlights() {
		addTextHighlights();
		// addBraceHighlights();
	}

	// Adds all of the java keywords to the list of things to highlight
	public void addTextHighlights() {
		style.addHighlight(Color.ORANGE, "abstract", "assert", "boolean",
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
