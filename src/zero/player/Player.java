package zero.player;
import zero.player.common.Point;
/**
 * <p>タイトル: Othello Program "ZERO"</p>
 *
 * <p>説明: </p>
 *
 * <p>著作権: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class Player extends Thread{//プレイヤはかならずこのクラスを継承
    /**
     * プレイヤ名を返します．
     * @return String
     */
    public static String getPlayerName(){
        return "default";
    }

    /**
     * スレッド開始
     */
    public void run(){}

    /**
     * ボタンが押されたときの処理：実質人間専用
     * @param point point
     */
    public void pressedDiscButton(Point point){
    }
}
