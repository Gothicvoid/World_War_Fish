package comgothicvoid.httpsgithub.world_war_fish;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/11/17.
 */
public class GameLost {
    //按钮图片资源
    private Bitmap bmpReplay, bmpReplayP, bmpQuit, bmpQuitP, bmpTitle;
    //按钮的坐标
    private int btnRx, btnRy, btnQx, btnQy;
    //按钮是否按下标识位
    private Boolean RisPress, QisPress;
    //菜单初始化
    public GameLost(Bitmap bmpTitle, Bitmap bmpReplay, Bitmap bmpReplayP,
                    Bitmap bmpQuit, Bitmap bmpQuitP){
        this.bmpTitle = bmpTitle;
        this.bmpReplay = bmpReplay;
        this.bmpReplayP = bmpReplayP;
        this.bmpQuit = bmpQuit;
        this.bmpQuitP = bmpQuitP;

        //重新开始按钮位置
        btnRx = MyGameView.screenW / 2 - bmpReplay.getWidth() / 2;
        btnRy = MyGameView.screenH - bmpReplay.getHeight() - bmpQuit.getHeight();
        RisPress = false;
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
        if(RisPress) canvas.drawBitmap(bmpReplayP, btnRx, btnRy, paint);
        else canvas.drawBitmap(bmpReplay, btnRx, btnRy, paint);
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
            //判定用户是否点击了重新开始按钮
            if(pointX > btnRx && pointX < btnRx + bmpReplay.getWidth()){
                if(pointY >btnRy && pointY < btnRy + bmpReplay.getHeight()){
                    RisPress = true;
                } else RisPress = false;
            } else RisPress = false;
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
            if(pointX > btnRx && pointX < btnRx + bmpReplay.getWidth()){
                if(pointY >btnRy && pointY < btnRy + bmpReplay.getHeight()){
                    //还原Button状态为未按下状态
                    RisPress = false;
                    //改变当前游戏状态为开始游戏
                    MyGameView.isReplay = true;
                    MyGameView.gamestate = MyGameView.GAME_RUN;
                }
            }
        }
    }
}