package com.example.clockfx.Utils;

import com.example.clockfx.Controllers.ClockController;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class UpdateComponents extends TimerTask {
    ClockController target;

    List<String> quotes;
    Random random = new Random();
    public UpdateComponents(ClockController target, List<String> quotes){
        this.target = target;
        this.quotes = quotes;
    }
    @Override
    public void run() {
        String quote = null;
        if (!quotes.isEmpty()){
            quote = quotes.get(random.nextInt(quotes.size()));

        }
        
        target.updateComponents(quote);

    }
}
