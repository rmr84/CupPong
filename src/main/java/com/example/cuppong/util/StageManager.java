package com.example.cuppong.util;

import javafx.stage.Stage;
import java.util.ArrayList;

public class StageManager {
    public static final int MAINMENU = 0;
    public static final int HOWTOPLAY = 1;
    public static final int LOBBY = 2;
    public static final int PLAY = 3;
    public static final int RESULT = 4;
    private static ArrayList<Stage> _stages;
    private int _currentstage = 0;

    private static volatile StageManager instance = null;
    private static Object lockobj = new Object();

    private StageManager() {
        _stages = new ArrayList<>(5);
    }

    public static StageManager getInstance() {
        StageManager result = instance;
        if (result==null) {
            synchronized (lockobj) {
                result = instance;
                if (result==null) {
                    instance = result = new StageManager();
                }
            }
        }
        return result;
    }

    public void show(int stage) {
        if (_stages.get(stage).isShowing()) {
            System.out.println("[show] Stage " + stage + " is showing.");
            return;
        }

        _currentstage = stage;
        _stages.get(stage).show();
    }

    public void hide(int stage) {
        if (!_stages.get(stage).isShowing()) {
            System.out.println("[hide] Stage " + stage + " not showing.");
            return;
        }

        _stages.get(stage).hide();
    }

    public void showhide(int _show, int _hide) {
        hide(_hide);
        show(_show);
    }

    public ArrayList<Stage> getStages() {
        return _stages;
    }

    public void add(Stage stage) {
        _stages.add(stage);
    }

    public int currentStage() {
        return _currentstage;
    }
}
