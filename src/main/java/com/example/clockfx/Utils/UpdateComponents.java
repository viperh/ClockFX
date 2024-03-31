package com.example.clockfx.Utils;

import com.example.clockfx.Controllers.ClockController;

import java.util.TimerTask;

public class UpdateComponents extends TimerTask {
    ClockController target;


    public UpdateComponents(ClockController target){
        this.target = target;
    }
    @Override
    public void run() {
        target.updateComponents();

    }
}
