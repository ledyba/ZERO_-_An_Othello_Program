package zero.player.human;

import zero.player.Player;
import zero.player.common.Point;
import zero.player.common.Disc;
import zero.player.*;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 *
 * <p>説明: 人間専用</p>
 *
 * <p>著作権: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class input extends Player {
    public input() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Point selectedPoint = null;
    protected gameController GC;
    public input(gameController gc) {
        GC = gc;
    }

    /**
     * プレイヤの名前を取得．もちろん，「人間」です．
     * @return String
     */
    public static String getPlayerName() {
        return "Human";
    }

    /**
     * スレッド開始
     */
    public synchronized void run() {
        pressedDiscButton(null);
        try {
            while (selectedPoint == null) {
                wait(10); //処理が回ってくるまで放置
            }
            GC.setPoint(selectedPoint); //ポイント設定
            //おしまい
        } catch (InterruptedException e) {

        }
    }

    /**
     * ボタンが押されたときの処理：実質人間専用
     * @param point point
     */
    public synchronized void pressedDiscButton(Point point) {
        selectedPoint = point;
        //処理再開
    }

    private void jbInit() throws Exception {
    }

}
