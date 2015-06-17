import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

public class Listener extends MouseAdapter implements ActionListener,
		KeyListener, ChangeListener {
	protected Editor editor;
	// protected UndoHandler undoHandler = new UndoHandler();
	protected UndoManager undoManager = new UndoManager();

	public Listener(Editor editor) {
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("timer")) {
			// braceHighlights();
			editor.currentPane.style.updateHighlights();
			editor.currentPane.style.drawTextHighlights();
		}

		if (command.equals("openFile"))
			editor.openFileChooser();
		if (command.equals("saveFile"))
			editor.saveFileChooser();
		if (command.equals("closeTab")) {
			editor.tabs.remove(editor.currentPane);
			editor.currentPane = (EditorPane) editor.tabs
					.getComponentAt(editor.tabs.getTabCount() - 1);
			editor.tabs.setSelectedIndex(editor.tabs.getTabCount() - 1);
		}
		if (command.equals("newFile")) {
			EditorPane tempPane = new EditorPane(this, "", "New Java Class",
					editor.currentFontSize);
			editor.tabs.addTab("New Java Class", tempPane);
			editor.tabs.setSelectedComponent(tempPane);
		}
	}

	private void braceHighlights() {
		char c = '\0';
		Document d = editor.currentPane.getDocument();
		try {
			if (editor.currentPane.getCaretPosition() < d.getLength()
					&& editor.currentPane.getCaretPosition() > 0)
				c = d.getText(0, editor.currentPane.getDocument().getLength())
						.charAt(editor.currentPane.getCaretPosition());
			System.out.println(c);

		} catch (BadLocationException e1) {
		}
		if (editor.currentPane.style.getCharHighlights().contains(c)) {
			editor.currentPane.style.drawBraceHighlights(
					editor.currentPane.getCaretPosition(), c);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			String temp = editor.currentPane.style.getSelection().text;
			String temp2 = "";
			try {
				temp2 = editor.currentPane.getDocument().getText(0,
						editor.currentPane.getDocument().getLength());
			} catch (BadLocationException e2) {
				e2.printStackTrace();
			}
			if (!editor.currentPane.style.getSelection().text.isEmpty())
				editor.currentPane.style.getSelection().refactor(temp,
						JOptionPane.showInputDialog("Refactor to: "), temp2,
						editor.currentPane);
			else
				JOptionPane.showMessageDialog(editor,
						"Please select something to refactor",
						"Cant refactor empty selection", 0);

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			editor.currentPane.style.getSelection().refactoring = false;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slide = (JSlider) e.getSource();
		editor.currentPane.setFont(editor.currentPane.getFont().deriveFont(
				(float) slide.getValue()));
		editor.currentFont.setText("Font Size: " + slide.getValue());
		editor.currentFontSize = slide.getValue();
	}
}
