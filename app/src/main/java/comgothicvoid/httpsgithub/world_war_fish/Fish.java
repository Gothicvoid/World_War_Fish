package comgothicvoid.httpsgithub.world_war_fish;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Administrator on 2016/11/16.
 */
public class Fish {
    //杂鱼种类标识
    public int type;
    //从左往右
    public static final int TYPE_LEFT = 1;
    //从右往左
    public static final int TYPE_RIGHT = 2;
    //杂鱼位图
    public Bitmap bmpfish;
    //杂鱼坐标
    public int x, y;
    //杂鱼速度
    public int speed;
    //杂鱼大小
    public int size;
    //判断杂鱼是否已经出屏
    public boolean isDead;

    //杂鱼的构造函数
    public Fish(Bitmap bmpfish, int fishType, int x, int y){
        this.bmpfish = bmpfish;
        this.type = fishType;
        this.x = x;
        this.y = y;
    }
    //杂鱼的绘图函数
    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        //大小
        switch (size){
            case 1:
                canvas.scale(0.1f, 0.1f, x + bmpfish.getWidth() / 2,
                        y + bmpfish.getHeight() / 2);
                break;
            case 2:
                canvas.scale(0.2f, 0.2f, x + bmpfish.getWidth() / 2,
                        y + bmpfish.getHeight() / 2);
                break;
            case 3:
                canvas.scale(0.3f, 0.3f, x + bmpfish.getWidth() / 2,
                        y + bmpfish.getHeight() / 2);
                break;
            case 4:
                canvas.scale(0.4f, 0.4f, x + bmpfish.getWidth() / 2,
                        y + bmpfish.getHeight() / 2);
                break;
            case 5:
                canvas.scale(0.5f, 0.5f, x + bmpfish.getWidth() / 2,
                        y + bmpfish.getHeight() / 2);
                break;
            case 6:
                canvas.scale(0.6f, 0.6f, x + bmpfish.getWidth() / 2,
                        y + bmpfish.getHeight() / 2);
                break;
            case 7:
                canvas.scale(0.7f, 0.7f, x + bmpfish.getWidth() / 2,
                        y + bmpfish.getHeight() / 2);
                break;
            case 8:
                canvas.scale(0.8f, 0.8f, x + bmpfish.getWidth() / 2,
                        y + bmpfish.getHeight() / 2);
                break;
            case 9:
                canvas.scale(0.9f, 0.9f, x + bmpfish.getWidth() / 2,
                        y + bmpfish.getHeight() / 2);
                break;
            case 10:
                break;
        }
        //是否转向
        if(type == 1) canvas.scale(-1, 1, x + bmpfish.getWidth() / 2,
                y + bmpfish.getHeight() / 2);
        canvas.drawBitmap(bmpfish, x, y, paint);
        canvas.restore();
    }
    //杂鱼逻辑
    public void logic(){
        //不同种类杂鱼拥有不同逻辑
        switch (type){
            case TYPE_LEFT:
                if(!isDead){
                    x += speed;
                    if(x > MyGameView.screenW) isDead = true;
                }
                break;
            case TYPE_RIGHT:
                if(!isDead){
                    x -= speed;
                    if(x < -bmpfish.getWidth()) isDead = true;
                }
                break;
        }
    }
}
