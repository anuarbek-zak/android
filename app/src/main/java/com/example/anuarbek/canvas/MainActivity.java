package com.example.anuarbek.canvas;

import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    class DrawView extends View {

        Paint p;
        RectF rectf;
        RectF rectf1;
        Path path;
        Path path1;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
            p.setStrokeWidth(3);
            p.setStyle(Paint.Style.STROKE);
            rectf = new RectF(50,500,400,800);
            rectf1 = new RectF(130,550,280,750);
            path = new Path();
            path1 = new Path();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(255, 255, 255);

            // очистка path
            path.reset();


            // треугольник
            path.moveTo(50, 500);
            path.lineTo(400, 500);
            path.lineTo(225, 300);
            path.close();

            // квадрат и круг
            path.addRect(rectf, Path.Direction.CW);
            path.addRect(rectf1, Path.Direction.CW);
            path.addCircle(550, 150, 80, Path.Direction.CW);
            path.addCircle(170, 650, 15, Path.Direction.CW);

            path.moveTo(500, 800);
            path.lineTo(660, 800);
            path.lineTo(580, 700);
            path.close();
            path.moveTo(500, 600);
            path.lineTo(660, 600);
            path.lineTo(580, 500);
            path.close();
            path.moveTo(500, 700);
            path.lineTo(660, 700);
            path.lineTo(580, 600);
            path.close();

            // рисование path
            p.setColor(Color.BLACK);
            canvas.drawPath(path, p);

        }

    }


}
