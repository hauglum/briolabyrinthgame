package no.hauglum;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;
import javax.swing.JFrame;

public class WASDControls extends JFrame implements KeyListener {

    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean sPressed = false;
    private boolean dPressed = false;

    private Consumer<Direction> moveUpAction;
    private Consumer<Direction> moveLeftAction;

    public WASDControls() {
        // Set up the JFrame
        this.setTitle("WASD Controls");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setVisible(true);
    }
    public void setMoveUpAction(Consumer<Direction> moveUpAction) {
        this.moveUpAction = moveUpAction;
    }

    public void setMoveLeftAction(Consumer<Direction> moveLeftAction) {
        this.moveLeftAction = moveLeftAction;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                wPressed = true;
                break;
            case KeyEvent.VK_A:
                aPressed = true;
                break;
            case KeyEvent.VK_S:
                sPressed = true;
                break;
            case KeyEvent.VK_D:
                dPressed = true;
                break;
        }
        checkKeys();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                wPressed = false;
                break;
            case KeyEvent.VK_A:
                aPressed = false;
                break;
            case KeyEvent.VK_S:
                sPressed = false;
                break;
            case KeyEvent.VK_D:
                dPressed = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    private void checkKeys() {
        if (wPressed && aPressed) {
            if (moveUpAction != null) moveUpAction.accept(Direction.NORMAL);
            if (moveLeftAction != null) moveLeftAction.accept(Direction.INVERTED);
        } else if (wPressed && dPressed) {
            if (moveUpAction != null) moveUpAction.accept(Direction.NORMAL);
            if (moveLeftAction != null) moveLeftAction.accept(Direction.NORMAL);
        } else if (sPressed && aPressed) {
            if (moveUpAction != null) moveUpAction.accept(Direction.INVERTED);
            if (moveLeftAction != null) moveLeftAction.accept(Direction.INVERTED);
        } else if (sPressed && dPressed) {
            if (moveUpAction != null) moveUpAction.accept(Direction.INVERTED);
            if (moveLeftAction != null) moveLeftAction.accept(Direction.NORMAL);
        } else if (wPressed) {
            if (moveUpAction != null) moveUpAction.accept(Direction.NORMAL);
        } else if (aPressed) {
            if (moveLeftAction != null) moveLeftAction.accept(Direction.INVERTED);
        } else if (sPressed) {
            if (moveUpAction != null) moveUpAction.accept(Direction.INVERTED);
        } else if (dPressed) {
            if (moveLeftAction != null) moveLeftAction.accept(Direction.NORMAL);
        }
    }
}

