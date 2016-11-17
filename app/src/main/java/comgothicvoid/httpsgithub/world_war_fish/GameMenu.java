package comgothicvoid.httpsgithub.world_war_fish;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/11/13.
 */
public class GameMenu {
    //按钮图片资源
    private Bitmap bmpPlay, bmpPlayP, bmpQuit, bmpQuitP, bmpTitle;
    //按钮的坐标
    private int btnPx, btnPy, btnQx, btnQy;
    //按钮是否按下标识位
    private Boolean PisPress, QisPress;
    //菜单初始化
    public GameMenu(Bitmap bmpTitle, Bitmap bmpPlay, Bitmap bmpPlayP,
                    Bitmap bmpQuit, Bitmap bmpQuitP){
        this.bmpTitle = bmpTitle;
        this.bmpPlay = bmpPlay;
        this.bmpPlayP = bmpPlayP;
        this.bmpQuit = bmpQuit;
        this.bmpQuitP = bmpQuitP;

        //开始游戏按钮位置
        btnPx = MyGameView.screenW / 2 - bmpPlay.getWidth() / 2;
        btnPy = MyGameView.screenH - bmpPlay.getHeight() - bmpQuit.getHeight();
        PisPress = false;
        //退出游戏按钮位置
        btnQx = MyGameView.screenW / 2 - bmpQuit.getWidth() / 2;
        btnQy = MyGameView.screenH - bmpQuit.getHeight();
        QisPress = false;
    }
    //绘图函数
    public void draw(Canvas canvas, Paint paint){
        //绘制标题
        canvas.drawBitmap(bmpTitle, MyGameView.screenW / 2 - bmpTitle.getWidth() / 2, 50, paint);
        //根据状态绘制按钮
        if(PisPress) canvas.drawBitmap(bmpPlayP, btnPx, btnPy, paint);
        else canvas.drawBitmap(bmpPlay, btnPx, btnPy, paint);
        if(QisPress) canvas.drawBitmap(bmpQuitP, btnQx, btnQy, paint);
        else canvas.drawBitmap(bmpQuit, btnQx, btnQy, paint);
    }
    //菜单触屏监听
    public void onTouchEvent(MotionEvent event){
        //获取用户当前触屏位置
        int pointX = (int)event.getX();
        int pointY = (int)event.getY();
        //当用户是按下或移动
        if(event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_MOVE){
            //判定用户是否点击了开始游戏按钮
            if(pointX > btnPx && pointX < btnPx + bmpPlay.getWidth()){
                if(pointY >btnPy && pointY < btnPy + bmpPlay.getHeight()){
                    PisPress = true;
                } else PisPress = false;
            } else PisPress = false;
            //判定用户是否点击了退出游戏按钮
            if(pointX > btnQx && pointX < btnQx + bmpQuit.getWidth()){
                if(pointY >btnQy && pointY < btnQy + bmpQuit.getHeight()){
                    QisPress = true;
                } else QisPress = false;
            } else QisPress = false;
        } else if(event.getAction() == MotionEvent.ACTION_UP){
            //抬起判断是否点击按钮，防止用户移动到别处
            if(pointX > btnQx && pointX < btnQx + bmpQuit.getWidth()){
                if(pointY >btnQy && pointY < btnQy + bmpQuit.getHeight()){
                    //还原Button状态为未按下状态
                    QisPress = false;
                    //退出游戏
                    MainActivity.instance.finish();
                    System.exit(0);
                }
            }
            if(pointX > btnPx && pointX < btnPx + bmpPlay.getWidth()){
                if(pointY >btnPy && pointY < btnPy + bmpPlay.getHeight()){
                    //还原Button状态为未按下状态
                    PisPress = false;
                    //改变当前游戏状态为开始游戏
                    MyGameView.gamestate = MyGameView.GAME_RUN;
                }
            }
        }
    }
}