package comgothicvoid.httpsgithub.world_war_fish;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Vector;

/**
 * Created by Administrator on 2016/11/9.
 */
public class MyGameView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    //用于控制SurfaceView
    private SurfaceHolder sfh;
    //声明一个画布
    private Canvas canvas;
    //声明一个画笔
    private Paint paint;
    //声明一个线程
    private Thread th;
    //线程消亡的标识位
    private boolean flag;
    //屏幕的宽高
    public static int screenW, screenH;

    //定义游戏状态常量
    public static final int GAME_MENU = 0;  //游戏初始界面
    public static final int GAME_RUN = 1;  //游戏进行中
    public static final int GAME_WON = 2;  //游戏胜利
    public static final int GAME_LOST = 3;  //游戏失败
    public static final int GAME_PAUSE = -1;  //游戏暂停
    //当前游戏状态(默认初始界面)
    public static int gamestate = GAME_MENU;
    //是否重玩标识
    public static boolean isReplay = false;
    //声明一个Resources实例便于加载图片
    private Resources res = this.getResources();
    //声明游戏中用到的各图片资源
    private Bitmap bmpBackGround;   //游戏背景
    private Bitmap bmpTitle;    //游戏标题
    private Bitmap bmpWon;  //胜利标题
    private Bitmap bmpLost; //失败标题
    private Bitmap bmpPause;    //暂停标题
    private Bitmap bmpPlay;    //开始/继续游戏
    private Bitmap bmpPlayP;    //按下开始/继续游戏
    private Bitmap bmpQuit;    //退出游戏
    private Bitmap bmpQuitP;    //按下退出游戏
    private Bitmap bmpReplay;   //重新开始
    private Bitmap bmpReplayP;   //按下重新开始
    private Bitmap bmpContinue; //继续游戏
    private Bitmap bmpContinueP;    //按下继续游戏
    private Bitmap bmpPlayer;   //玩家图片
    private Bitmap bmpPlayerDie; //玩家死亡
    private Bitmap bmpFish; //其他鱼
    private Bitmap bmpFishDie;  //其他鱼死亡

    //声明各游戏对象
    private GameMenu gameMenu;  //初始界面
    private GameWon gameWon;    //胜利界面
    private GameLost gameLost;  //失败界面
    private GamePause gamePause;    //暂停界面
    private Player player;  //玩家
    private Vector<Fish> vcFish;    //杂鱼容器
    private int createFishTime = 100;   //每次生成杂鱼的时间(毫秒)
    private int count = 0;  //计数器
    private int fishArray[][] = {
            {1,2},{2,1},{1,1},{2,2},{1,2,1},{2,1,2}
    };  //杂鱼数组,数字表示种类,二维数组的每一维都是一组杂鱼
    private int fishArrayIndex; //当前取出一维数组的下标
    private Random random;  //随机库，为生成的杂鱼赋予随机属性
    private int eaten = 0;  //玩家已吃掉的杂鱼数

    //构造函数
    public MyGameView(Context context){
        super(context);
        //实例SurfaceHolder
        sfh = this.getHolder();
        //为SurfaceView添加状态监听
        sfh.addCallback(this);
        //实例一个画笔
        paint = new Paint();
        //设置焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        //获取屏幕宽高
        screenW = this.getWidth();
        screenH = this.getHeight();
        initGame(); //便于初始化游戏
        flag = true;
        //实例、启动线程
        th = new Thread(this);
        th.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        flag = false;
    }

    //绘图函数
    public void myDraw(){
        try{
            canvas = sfh.lockCanvas();
            if(canvas != null){
                //刷屏
                canvas.drawBitmap(bmpBackGround, 0, 0, paint);
                //根据不同游戏状态进行不同绘图
                switch (gamestate){
                    case GAME_MENU:
                        gameMenu.draw(canvas, paint);   //初始界面的绘图函数
                        break;
                    case GAME_RUN:
                        player.draw(canvas, paint); //玩家的绘图函数
                        //绘制杂鱼
                        for(int i = 0; i < vcFish.size(); i++){
                            vcFish.elementAt(i).draw(canvas, paint);
                        }
                        break;
                    case GAME_WON:
                        gameWon.draw(canvas, paint);    //胜利界面的绘图函数
                        break;
                    case GAME_LOST:
                        gameLost.draw(canvas, paint);   //失败界面的绘图函数
                        break;
                    case GAME_PAUSE:
                        gamePause.draw(canvas, paint);  //暂停界面的绘图函数
                        break;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if(canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    //触屏事件监听函数
    public boolean onTouchEvent(MotionEvent event){
        //根据不同游戏状态进行不同监听
        switch (gamestate){
            case GAME_MENU:
                gameMenu.onTouchEvent(event);   //初始界面的触屏监听
                break;
            case GAME_RUN:
                player.onTouchEvent(event); //玩家的触屏监听
                break;
            case GAME_WON:
                gameWon.onTouchEvent(event);    //胜利界面的触屏监听
                break;
            case GAME_LOST:
                gameLost.onTouchEvent(event);   //失败界面的触屏监听
                break;
            case GAME_PAUSE:
                gamePause.onTouchEvent(event);  //暂停界面的触屏监听
                break;
        }
        return true;
    }

    //按键监听
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //返回键的处理,游戏进行中按返回键暂停游戏,其他状态按返回退出游戏
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(gamestate == GAME_RUN){
                gamestate = GAME_PAUSE;
            } else {
                MainActivity.instance.finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //逻辑函数
    public void logic(){
        //根据不同游戏状态运行不同逻辑
        switch (gamestate){
            case GAME_MENU:
                break;
            case GAME_RUN:
                //重新开始游戏时重置玩家和杂鱼
                if(isReplay) {
                    eaten = 0;
                    player.size = 1;
                    vcFish.clear();
                    isReplay = false;
                }
                player.logic(); //玩家逻辑
                //杂鱼逻辑
                for(int i = 0; i < vcFish.size(); i++){
                    Fish fi = vcFish.elementAt(i);
                    if(fi.isDead){
                        vcFish.removeElementAt(i);
                    } else {
                        fi.logic();
                    }
                }
                //生成杂鱼
                count++;
                if(count % createFishTime == 0){
                    for(int i = 0; i < fishArray[fishArrayIndex].length; i++){
                        if(fishArray[fishArrayIndex][i] == 1){ //从左往右鱼
                            int y = random.nextInt(screenH) - bmpFish.getHeight() / 2;
                            Fish nfish = new Fish(bmpFish, 1, -bmpFish.getWidth(), y);
                            nfish.size = random.nextInt(10);
                            nfish.speed = (random.nextInt(2) + 1) * 10;
                            vcFish.addElement(nfish);
                        } else if(fishArray[fishArrayIndex][i] == 2){   //从右往左鱼
                            int y = random.nextInt(screenH) - bmpFish.getHeight() / 2;
                            Fish nfish = new Fish(bmpFish, 2, screenW + 1, y);
                            nfish.size = random.nextInt(10);
                            nfish.speed = (random.nextInt(2) + 1) * 10;
                            vcFish.addElement(nfish);
                        }
                    }
                    fishArrayIndex++;
                    if(fishArrayIndex == 5) fishArrayIndex = 0;
                }
                //处理玩家与杂鱼间的碰撞
                for(int i = 0; i < vcFish.size(); i++){
                    if(player.isCollsionWith(vcFish.elementAt(i))){
                        if(player.size >= vcFish.elementAt(i).size){
                            eaten++;
                            if(eaten == 5) {
                                eaten = 0;
                                player.size++;
                            }
                            if(player.size == 21) gamestate = GAME_WON;
                            vcFish.elementAt(i).isDead = true;
                        } else gamestate = GAME_LOST;
                    }
                }
                break;
            case GAME_WON:
                break;
            case GAME_LOST:
                break;
            case GAME_PAUSE:
                break;
        }
    }

    private void initGame(){
        //放置游戏切入后台重新进入游戏时，游戏被重置！
        //当游戏状态处于初始界面态时，才会重置游戏
        if(gamestate == GAME_MENU){
            //加载游戏资源
            bmpBackGround = BitmapFactory.decodeResource(res,R.drawable.background);
            bmpTitle = BitmapFactory.decodeResource(res,R.drawable.title);
            bmpWon = BitmapFactory.decodeResource(res,R.drawable.won);
            bmpLost = BitmapFactory.decodeResource(res,R.drawable.lost);
            bmpPause = BitmapFactory.decodeResource(res,R.drawable.pause);
            bmpPlay = BitmapFactory.decodeResource(res,R.drawable.play);
            bmpPlayP = BitmapFactory.decodeResource(res,R.drawable.playp);
            bmpQuit = BitmapFactory.decodeResource(res,R.drawable.quit);
            bmpQuitP = BitmapFactory.decodeResource(res,R.drawable.quitp);
            bmpReplay = BitmapFactory.decodeResource(res,R.drawable.replay);
            bmpReplayP = BitmapFactory.decodeResource(res,R.drawable.replayp);
            bmpContinue = BitmapFactory.decodeResource(res,R.drawable.cont);
            bmpContinueP = BitmapFactory.decodeResource(res,R.drawable.conp);
            bmpPlayer = BitmapFactory.decodeResource(res,R.drawable.player);
            //bmpPlayerDie = BitmapFactory.decodeResource(res,R.drawable.);
            bmpFish = BitmapFactory.decodeResource(res,R.drawable.fish);
            //bmpFishDie = BitmapFactory.decodeResource(res,R.drawable.);
        }
        //实例各对象
        gameMenu = new GameMenu(bmpTitle, bmpPlay, bmpPlayP, bmpQuit, bmpQuitP);
        gameWon = new GameWon(bmpWon, bmpReplay, bmpReplayP, bmpQuit, bmpQuitP);
        gameLost = new GameLost(bmpLost, bmpReplay, bmpReplayP, bmpQuit, bmpQuitP);
        gamePause = new GamePause(bmpPause, bmpContinue, bmpContinueP, bmpQuit, bmpQuitP);
        player = new Player(bmpPlayer);
        vcFish = new Vector<Fish>();
        random = new Random();
    }

    @Override
    public void run(){
        while (flag){
            long start = System.currentTimeMillis();
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try{
                if(end - start < 50){
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
