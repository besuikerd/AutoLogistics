package com.besuikerd.autologistics.client.lib.gui.element;

import com.besuikerd.autologistics.common.lib.util.tuple.Vector2;

public interface CaretPositionUpdatedListener {
    void onPositionUpdated(Vector2 oldPosition, Vector2 newPosition);
}
