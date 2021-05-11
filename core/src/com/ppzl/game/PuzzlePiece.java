package com.ppzl.game;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
//it represents once puzzle piece which is an image button. It can be assigned to a slot.
public class PuzzlePiece extends ImageButton {
    final private int id;
    private int slotId = -1;
    public PuzzlePiece(int id, Drawable imageUp, Drawable imageDown, Drawable imageChecked) {
        super(imageUp, imageDown, imageChecked);
        this.id = id;
    }
    public int getId () {
        return id;
    }

    public int getSlotId () {
        return slotId;
    }

    public void setSlotId (int slotId) {
        this.slotId = slotId;
    }

}
