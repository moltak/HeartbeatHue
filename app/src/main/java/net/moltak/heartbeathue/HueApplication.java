package net.moltak.heartbeathue;

import android.app.Application;

import net.moltak.heartbeathue.logic.HueController;

/**
 * Created by moltak on 15. 12. 13..
 */
public class HueApplication extends Application {
    private HueController hueController;

    public HueController getHueController() {
        return hueController;
    }

    public void setHueController(HueController hueController) {
        this.hueController = hueController;
    }
}
