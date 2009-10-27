package zero.player;

import javax.swing.JPanel;
import zero.player.common.Board;
import zero.player.common.Point;
import zero.player.common.Disc;

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
public class gameController extends Thread { //これもスレッド，あれもスレッド．
    private Board Board;
    private Point selectedPoint; //選ばれた場所を受け取るためのポイント変数
    private byte nowPlayerNum = 0;
    private PlayerManager[] PlayerManagers = new PlayerManager[2]; //プレイヤたち
    private PlayerManager nowPlayerManager; //今の手番
    private Player nowPlayer;
    private JPanel BoardPanel;
    /**
     * プレイヤを決定して，ゲームを始めます
     * @param board Board
     * @param player1 PlayerManager
     * @param player2 PlayerManager
     */
    public gameController(Board board,JPanel panel,PlayerManager player1, PlayerManager player2) {
        //変数セット
        Board = board;
        this.BoardPanel = panel;
        PlayerManagers[0] = player1;
        PlayerManagers[1] = player2;
        PlayerManagers[0].setGC(this);
        PlayerManagers[1].setGC(this);
        //ボードの初期化
        Board.boardInit();
        BoardPanel.repaint();
    }

    public void run() {
        nowPlayerManager = PlayerManagers[0];
        while (true) {
            selectedPoint = null;
            getPlayerChoice(); //プレイヤの選択を取ります．
            if(selectedPoint == null){
                throw new NullPointerException("Any point was selected.");
            }
//            Board.setDiscColor(this.selectedPoint,Disc.BLACK);
            System.out.println(nowPlayerNum+":"+selectedPoint.getTextKifu());
            changeNowPlayer();//プレイヤの変更
            BoardPanel.repaint();
//            BoardPanel.revalidate();
        }
    }

    public void getPlayerChoice() {
            try {
                nowPlayer = nowPlayerManager.getPlayer(); //プレイヤ取得
                nowPlayer.start(); //プレイヤ開始
                nowPlayer.join(); //プレイヤ終了まで待つ
            } catch (InterruptedException ex) {
            }
    }

    /**
     * 各プレイヤからの手を受け取ります．
     * @param point point
     */
    public void setPoint(Point point) {
        selectedPoint = point;
    }

    /**
     * 現在の手番のプレイヤを返します．
     * @return Player
     */
    public Player getNowPlayer() {
        return nowPlayer;
    }
    private void changeNowPlayer(){
        nowPlayerNum++;
        nowPlayerManager = PlayerManagers[nowPlayerNum&=1];
    }
}
