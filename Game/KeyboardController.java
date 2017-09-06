package Game;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;

/**
 * Created by jakub on 02.09.17.
 */
public class KeyboardController {

    private static HashMap<String, Boolean> keys;

    public KeyboardController(Scene scene) {

        keys = new HashMap<>(10);

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        keys.put(e.getCode().toString(), true);
                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        keys.replace(e.getCode().toString(), false);
                    }
                });
    }

    public static Boolean isKeyPressed(String code) {
        return keys.getOrDefault(code, false);
    }

}
