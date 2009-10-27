package zero.player;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 *
 * <p>説明: プレイヤクラスのマネージャです</p>
 *
 * <p>著作権: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class PlayerManager {
    public PlayerManager() {
    }
    private gameController gc;
    /**
     * プレイヤクラスを返します
     * @return Player
     */
    public Player getPlayer(){
        return new Player();
    }
    /**
     * ゲームコントローラを設定します．
     * @param gc gameController
     */
    public void setGC(gameController GC){
        gc = GC;
    }
    /**
     * ゲームコントローラを返します．
     * @return gameController
     */
    public gameController getGC(){
        return gc;
    }
}
