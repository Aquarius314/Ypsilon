package Game;

import javafx.scene.Scene;

import java.util.HashMap;

/**
 * Created by jakub on 03.09.17.
 */
public class MouseController {

    private static HashMap<String, Boolean> buttons;

    private static double mouseX, mouseY;

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public MouseController(Scene scene) {

        buttons = new HashMap<>(10);

        scene.setOnMousePressed(
                e -> {
                    buttons.put(e.getButton().toString(), true);
                    MouseController.mouseX = e.getX();
                    MouseController.mouseY = e.getY();
                });

        scene.setOnMouseMoved(
                e -> {
                    MouseController.mouseX = e.getX();
                    MouseController.mouseY = e.getY();
                });

        scene.setOnMouseDragged(
                e -> {
                    MouseController.mouseX = e.getX();
                    MouseController.mouseY = e.getY();
                }
        );

        scene.setOnMouseReleased(
                e -> buttons.replace(e.getButton().toString(), false));
    }

    public static Boolean isButtonPressed(String code) {
        return buttons.getOrDefault(code, false);
    }

}
