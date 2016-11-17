package comgothicvoid.httpsgithub.world_war_fish;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/11/15.
 */
public class Player {
    //玩家位图、坐标、目的坐标、当前坐标
    private Bitmap bmpPlayer;
    public int x, y;
    private int dx, dy, cx, cy;
    //玩家移动速度
    private int speed = 50;
    //玩家左右方向标识,为真向右，为假向左
    private boolean isRight = false;
    //玩家移动距离
    private int d, xd, yd;
    //玩家当前移动距离
    private int cdx, cdy;
    //玩家大小等级
    public int size = 1;

    //玩家的构造函数
    public Player(Bitmap bmpPlayer){
        this.bmpPlayer = bmpPlayer;
        x = MyGameView.screenW / 2 - bmpPlayer.getWidth() / 2;
        y = MyGameView.screenH / 2 - bmpPlayer.getHeight() / 2;
        cx = x + bmpPlayer.getWidth() / 2;
        cy = y + bmpPlayer.getHeight() / 2;
        dx = cx;
        dy = cy;
    }
    //玩家的绘图函数
    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        //大小
        switch (size){
            case 1:
                canvas.scale(0.1f, 0.1f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 2:
                canvas.scale(0.2f, 0.2f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 3:
                canvas.scale(0.3f, 0.3f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 4:
                canvas.scale(0.4f, 0.4f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 5:
                canvas.scale(0.5f, 0.5f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 6:
                canvas.scale(0.6f, 0.6f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 7:
                canvas.scale(0.7f, 0.7f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 8:
                canvas.scale(0.8f, 0.8f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 9:
                canvas.scale(0.9f, 0.9f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 10:
                break;
            case 11:
                canvas.scale(1.1f, 1.1f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 12:
                canvas.scale(1.2f, 1.2f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 13:
                canvas.scale(1.3f, 1.3f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 14:
                canvas.scale(1.4f, 1.4f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 15:
                canvas.scale(1.5f, 1.5f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 16:
                canvas.scale(1.6f, 1.6f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 17:
                canvas.scale(1.7f, 1.7f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 18:
                canvas.scale(1.8f, 1.8f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 19:
                canvas.scale(1.9f, 1.9f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
            case 20:
                canvas.scale(2f, 2f, x + bmpPlayer.getWidth() / 2,
                        y + bmpPlayer.getHeight() / 2);
                break;
        }
        //是否转向
        if(isRight) canvas.scale(-1, 1, x + bmpPlayer.getWidth() / 2,
                y + bmpPlayer.getHeight() / 2);
        canvas.drawBitmap(bmpPlayer, x, y, paint);
        canvas.restore();
    }
    //玩家的触屏监听
    public void onTouchEvent(MotionEvent event) {
        //获取用户当前触屏位置
        dx = (int) event.getX();
        dy = (int) event.getY();
        xd = dx - cx;
        yd = dy - cy;
        d = (int)Math.sqrt(xd * xd + yd * yd);
        if(xd > 0) isRight = true;
        if(xd < 0) isRight = false;
        cdx = 0;
        cdy = 0;
    }
    //玩家的逻辑
    public void logic(){
        //处理玩家移动,由于只能用int,所以有误差,不能准确地点哪走哪
        if(cdx < Math.abs(xd)){
            x += speed * xd / d;
            cdx += Math.abs(speed * xd / d);
            cx = x + bmpPlayer.getWidth() / 2;
        }
        if(cdy < Math.abs(yd)){
            y += speed * yd / d;
            cdy += Math.abs(speed * yd / d);
            cy = y + bmpPlayer.getHeight() / 2;
        }
    }
    //判断玩家与杂鱼间的碰撞
    public boolean isCollsionWith(Fish fi){
        int cx2 = fi.x + fi.bmpfish.getWidth() / 2; //杂鱼中心横坐标
        int cy2 = fi.y + fi.bmpfish.getHeight() / 2;    //杂鱼中心纵坐标
        int w2 = fi.bmpfish.getWidth() * fi.size / 10;  //杂鱼宽度
        int h2 = fi.bmpfish.getHeight() * fi.size / 10; //杂鱼高度
        int w1 = bmpPlayer.getWidth() * size / 10;  //玩家宽度
        int h1 = bmpPlayer.getHeight() * size / 10; //玩家高度
        if(cx - w1 / 2 >= cx2 + w2 / 2) return false;
        else if(cx + w1 / 2 <= cx2 - w2 / 2) return false;
        else if(cy - h1 / 2 >= cy2 + h2 / 2) return false;
        else if(cy + h1 / 2 <= cy2 - h2 / 2) return false;
        else return true;
    }
}
