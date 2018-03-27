package stage2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Calendar;

public class Clock {


    float x;
    float y;

    public Clock() {
        x = 960;
        y = 300;
    }

    public void timeSet(GraphicsContext gc){
        Calendar calendar = Calendar.getInstance();
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR);
        double ts = Math.toRadians(second*6);
        double tm = Math.toRadians(minute*6) + ts/60;
        double th = Math.toRadians(hour*30) + tm/12;
        int size = 180;
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha(0.35);
        gc.fillOval(5+x-(size/2),5+y-(size/2),size,size);
        gc.setGlobalAlpha(1);
        gc.setFill(Color.WHITE);
        gc.fillOval(x-(size/2),y-(size/2),size,size);
        gc.setLineWidth(0.8);
        gc.strokeLine(x,y, x + (0.9*size/2) * Math.sin(ts), y - (0.9*size/2) * Math.cos(ts));
        gc.setLineWidth(1.6);
        gc.strokeLine(x,y, x + 70 *Math.sin(tm),y - 70 *Math.cos(tm));
        gc.setLineWidth(2.8);
        gc.strokeLine(x,y, x + 50 *Math.sin(th),y - 50 *Math.cos(th));


    }

}
