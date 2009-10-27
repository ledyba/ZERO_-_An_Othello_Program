package zero.player;

import zero.player.common.Board;
import javax.swing.JPanel;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 *
 * <p>����: </p>
 *
 * <p>���쌠: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class GameManager {
    private Board Board;//�{�[�h
    private JPanel BoardPanel;
    public GameManager(JPanel panel) {
        Board = new Board();//�{�[�h
        this.BoardPanel = panel;
    }
    public Board getBoard(){
        return this.Board;
    }
    public gameController makeGC(PlayerManager pl1,PlayerManager pl2){
        return new gameController(this.getBoard(),this.BoardPanel,pl1,pl2);
    }
}
