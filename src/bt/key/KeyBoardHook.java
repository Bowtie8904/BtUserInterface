package bt.key;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

import bt.key.KeyAction.Action;
import bt.runtime.InstanceKiller;
import bt.runtime.Killable;
import bt.utils.log.Logger;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

/**
 * @author &#8904
 *
 */
public class KeyBoardHook implements Killable
{
    private static KeyBoardHook instance;
    private GlobalKeyboardHook keyboardHook;
    private KeyActionSet actionSet;

    public static KeyBoardHook get()
    {
        if (instance == null)
        {
            instance = new KeyBoardHook();
            Logger.global()
                  .registerSource(instance,
                                  "GLOBAL_KEYBOARD_HOOK");
            instance.setup();
            InstanceKiller.killOnShutdown(instance,
                                          1);
        }
        return instance;
    }

    protected KeyBoardHook()
    {

    }

    private void setup()
    {
        Logger.global()
              .print(this,
                     "Setting up a global keyboard hook.");

        this.keyboardHook = new GlobalKeyboardHook(false);
        this.actionSet = new KeyActionSet();

        this.keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override
            public void keyPressed(GlobalKeyEvent e)
            {
                KeyEventWrapper eventWrapper = new KeyEventWrapper(e);
                KeyBoardHook.this.actionSet.dispatchEvent(Action.KEY_PRESSED,
                                        eventWrapper);
            }

            @Override
            public void keyReleased(GlobalKeyEvent e)
            {
                KeyEventWrapper eventWrapper = new KeyEventWrapper(e);
                KeyBoardHook.this.actionSet.dispatchEvent(Action.KEY_RELEASED,
                                        eventWrapper);
            }
        });
    }

    @Override
    public void kill()
    {
        if (this.keyboardHook != null)
        {
            this.keyboardHook.shutdownHook();
            Logger.global()
                  .print(this,
                         "Killed keyboard hook.");
        }
    }

    public void addKeyAction(KeyAction action)
    {
        this.actionSet.addKeyAction(action);
    }

    public void addKeyAction(int keyCode, Consumer<KeyEvent> keyPressed)
    {
        this.actionSet.addKeyAction(new KeyAction(keyCode,
                                             keyPressed));
    }

    public void addKeyAction(int keyCode, Consumer<KeyEvent> keyPressed, Consumer<KeyEvent> keyReleased)
    {
        this.actionSet.addKeyAction(new KeyAction(keyCode,
                                             keyPressed,
                                             keyReleased));
    }

    public void addKeyAction(Consumer<KeyEvent> keyPressed)
    {
        this.actionSet.addKeyAction(new KeyAction(keyPressed));
    }

    public void addKeyAction(Consumer<KeyEvent> keyPressed, Consumer<KeyEvent> keyReleased)
    {
        this.actionSet.addKeyAction(new KeyAction(keyPressed,
                                             keyReleased));
    }

    public void addKeyAction(int keyCode, int modifier, Consumer<KeyEvent> keyPressed)
    {
        this.actionSet.addKeyAction(new KeyAction(keyCode,
                                             modifier,
                                             keyPressed));
    }

    public void addKeyAction(int keyCode, int modifier, Consumer<KeyEvent> keyPressed,
                             Consumer<KeyEvent> keyReleased)
    {
        this.actionSet.addKeyAction(new KeyAction(keyCode,
                                             modifier,
                                             keyPressed,
                                             keyReleased));
    }

    public void addKeyAction(Component component, int keyCode, Consumer<KeyEvent> keyPressed)
    {
        this.actionSet.addKeyAction(new KeyAction(component,
                                             keyCode,
                                             keyPressed));
    }

    public void addKeyAction(Component component, int keyCode, Consumer<KeyEvent> keyPressed,
                             Consumer<KeyEvent> keyReleased)
    {
        this.actionSet.addKeyAction(new KeyAction(component,
                                             keyCode,
                                             keyPressed,
                                             keyReleased));
    }

    public void addKeyAction(Component component, Consumer<KeyEvent> keyPressed)
    {
        this.actionSet.addKeyAction(new KeyAction(component,
                                             keyPressed));
    }

    public void addKeyAction(Component component, Consumer<KeyEvent> keyPressed, Consumer<KeyEvent> keyReleased)
    {
        this.actionSet.addKeyAction(new KeyAction(component,
                                             keyPressed,
                                             keyReleased));
    }

    public void addKeyAction(Component component, int keyCode, int modifier, Consumer<KeyEvent> keyPressed)
    {
        this.actionSet.addKeyAction(new KeyAction(component,
                                             keyCode,
                                             modifier,
                                             keyPressed));
    }

    public void addKeyAction(Component component, int keyCode, int modifier, Consumer<KeyEvent> keyPressed,
                             Consumer<KeyEvent> keyReleased)
    {
        this.actionSet.addKeyAction(new KeyAction(component,
                                             keyCode,
                                             modifier,
                                             keyPressed,
                                             keyReleased));
    }

    public void removeKeyAction(KeyAction action)
    {
        this.actionSet.removeKeyAction(action);
    }

    public void clearKeyAction(int keyCode)
    {
        this.actionSet.clear(keyCode);
    }

    public void clearKeyActions()
    {
        this.actionSet.clear();
    }

    public void registerKeyListener(KeyListener listener)
    {
        addKeyAction(new KeyAction(listener::keyPressed,
                                   listener::keyReleased));
    }
}