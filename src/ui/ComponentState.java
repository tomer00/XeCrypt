package ui;

import utils.Rect;

public class ComponentState {

    public Rect rect;
    public boolean isHoverd = false;
    public boolean isSelected = false;

    public ComponentState(Rect rect) {
        this.rect = rect;
    }
}
