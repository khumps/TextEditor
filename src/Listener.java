import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

public class Listener extends MouseAdapter implements ActionListener,
		KeyListener {
	protected Editor editor;
	private JOptionPane refactor = new JOptionPane();
	private String refactorTo;
	// protected UndoHandler undoHandler = new UndoHandler();
	protected UndoManager undoManager = new UndoManager();

	public Listener(Editor editor) {
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("timer")) {
			braceHighlights();
			editor.currentPane.style.updateHighlights();
			editor.currentPane.style.drawTextHighlights();
		}

		if (command.equals("openFile"))
			editor.openFileChooser();
		if (command.equals("saveFile"))
			editor.saveFileChooser();

	}

	private void braceHighlights() {
		char c = '\0';
		Document d = editor.currentPane.getDocument();
		try {
			if (editor.currentPane.getCaretPosition() < d.getLength()
					&& editor.currentPane.getCaretPosition() > 0)
				c = d.getText(0, editor.currentPane.getDocument().getLength())
						.charAt(editor.currentPane.getCaretPosition());
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
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			// editor.style.getSelection().refactoring = true;
			editor.currentPane.style.getSelection().refactor(temp,
					JOptionPane.showInputDialog("Refactor to: "), temp2,
					editor.currentPane);

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
}
