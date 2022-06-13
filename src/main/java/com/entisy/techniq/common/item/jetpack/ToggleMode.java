package com.entisy.techniq.common.item.jetpack;

public enum ToggleMode {
    OFF("off"),
    HOVER("hover"),
    NORMAL("normal");

    String id;

    ToggleMode(String id) {
        this.id = id;
    }

    String getId() {
        return id;
    }

    static ToggleMode getMode(String id) {
        ToggleMode mode = null;

        switch(id) {
            case "off":
                mode = OFF;
                break;
            case "hover":
                mode = HOVER;
                break;
            case "normal":
                mode = NORMAL;
                break;
        }
        return mode;
    }
}
