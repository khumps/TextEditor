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

public class Listener extends MouseAdapter implements ActionListener,
		KeyListener {
	protected Editor editor;
	private JOptionPane refactor = new JOptionPane();
	private String refactorTo;

	public Listener(Editor editor) {
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("timer")) {
			braceHighlights();
			editor.style.updateHighlights();
			editor.style.drawTextHighlights();
		}

		if (command.equals("openFile"))
			editor.openFileChooser();
		if (command.equals("saveFile"))
			editor.saveFileChooser();

	}

	private void braceHighlights() {
		char c = '\0';
		Document d = editor.getDocument();
		try {
			if (editor.getCaretPosition() < d.getLength()
					&& editor.getCaretPosition() > 0)
				c = d.getText(0, editor.getDocument().getLength()).charAt(
						editor.getCaretPosition());
		} catch (BadLocationException e1) {
		}
		if (editor.style.getCharHighlights().contains(c)) {
			editor.style.drawBraceHighlights(editor.getCaretPosition(), c);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			refactor.showInputDialog("Refactor to: ");
			// editor.style.getSelection().refactoring = true;
			editor.style.getSelection().refactor(
					editor.style.getSelection().text, refactorTo);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			editor.style.getSelection().refactoring = false;

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
