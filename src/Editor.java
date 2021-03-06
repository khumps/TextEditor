import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

/**
 * Main window that displays the text editor
 * 
 * @author Kevin Humphreys
 * 
 */
public class Editor extends JFrame {
	private JPanel options;
	protected JTabbedPane tabs = new JTabbedPane();
	private JFileChooser fileChooser;
	private JMenuBar menu = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	protected EditorPane currentPane;
	private File lastFile = null;
	private Listener listener = new Listener(this);
	private Timer timer = new Timer(30, listener);
	private JSlider fontSlider;
	private final int minFontSize = 12;
	private final int maxFontSize = 40;
	protected int currentFontSize = minFontSize;
	protected JLabel currentFont = new JLabel("Font Size: " + minFontSize);

	public Editor() {
		setTitle("Kevin's Java Editor");
		timer.setActionCommand("timer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// menu.setFont(new Font(getFont().getFontName(), Font.BOLD, 40));
		addMouseListener(listener);
		init();
		// setDefaultSize(100);
		pack();
		setVisible(true);
		timer.start();
	}

	protected void addFile(String name, String text) {
		EditorPane temp = new EditorPane(listener, text, name,
				fontSlider.getValue());
		tabs.addTab(name, temp);
		tabs.setSelectedComponent(temp);
		currentPane = temp;
	}

	private void init() {
		initMenuBar();
		initFileChooser();
		initTabs();
	}

	private void initMenuBar() {
		setJMenuBar(menu);
		initFileMenu();
		initOptions();

	}
	
	private void initTabs()
	{
		tabs.add(currentPane = new EditorPane(listener, "", "New Class",
				fontSlider.getValue()), "New Class");
		tabs.addChangeListener(listener);
		add(tabs);
	}

	private void initFileMenu() {
		menu.add(fileMenu);
		Utils.newMenuItem(Utils.button, "New", "newFile", listener, fileMenu);
		Utils.newMenuItem(Utils.button, "Open", "openFile", listener, fileMenu);
		Utils.newMenuItem(Utils.button, "Save", "saveFile", listener, fileMenu);
		Utils.newMenuItem(Utils.button, "Close Tab", "closeTab", listener,
				fileMenu);
	}

	private void initFontSlider(int fontSize) {
		fontSlider = new JSlider(minFontSize, maxFontSize, fontSize);
		fontSlider.setPreferredSize(new Dimension(100, 25));
		fontSlider.addChangeListener(listener);
		menu.add(fontSlider);
	}

	/**
	 * 
	 */
	private void initOptions() {
		options = new JPanel();
		options.setPreferredSize(new Dimension(100, 25));
		menu.add(options);
		initFontSlider(minFontSize);
		options.add(currentFont);
		options.add(fontSlider);
	}

	protected void initFileChooser() {
		String[] types = { "java" };
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Java Files",
				types));
	}

	protected void openFileChooser() {
		if (lastFile != null)
			fileChooser = new JFileChooser(lastFile);
		fileChooser.showOpenDialog(this);
		String doc = "";
		String toAdd = null;
		try {
			lastFile = fileChooser.getSelectedFile();
			if (lastFile != null) {
				FileReader reader = new FileReader(lastFile);
				BufferedReader bReader = new BufferedReader(reader);
				while ((toAdd = bReader.readLine()) != null)
					doc += toAdd;
				addFile(lastFile.getName(), doc);
				bReader.close();
			}
		} catch (IOException e) {
		}

	}

	protected void saveFileChooser() {
		if (lastFile != null)
			fileChooser = new JFileChooser(lastFile);
		fileChooser.showSaveDialog(this);
		try {
			lastFile = fileChooser.getSelectedFile();
			if (lastFile != null) {
				FileWriter writer = new FileWriter(lastFile);
				writer.write(currentPane.getDocument().getText(0,
						currentPane.getDocument().getLength()));
				writer.close();
			}
		} catch (IOException | BadLocationException e) {
		}
	}

	public static void setDefaultSize(int size) {

		Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
		Object[] keys = keySet.toArray(new Object[keySet.size()]);

		for (Object key : keys) {

			if (key != null && key.toString().toLowerCase().contains("font")) {

				System.out.println(key);
				Font font = UIManager.getDefaults().getFont(key);
				if (font != null) {
					font = font.deriveFont((float) size);
					UIManager.put(key, font);
				}

			}

		}

	}

	public static void main(String[] args) {
		new Editor();
	}

}
