package zero.player;

import zero.player.common.Board;
import javax.swing.JPanel;

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
public class GameManager {
    private Board Board;//ボード
    private JPanel BoardPanel;
    public GameManager(JPanel panel) {
        Board = new Board();//ボード
        this.BoardPanel = panel;
    }
    public Board getBoard(){
        return this.Board;
    }
    public gameController makeGC(PlayerManager pl1,PlayerManager pl2){
        return new gameController(this.getBoard(),this.BoardPanel,pl1,pl2);
    }
}
