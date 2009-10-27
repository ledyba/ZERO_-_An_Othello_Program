package zero.player;

import javax.swing.JPanel;
import zero.player.common.Board;
import zero.player.common.Point;
import zero.player.common.Disc;

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
public class gameController extends Thread { //������X���b�h�C������X���b�h�D
    private Board Board;
    private Point selectedPoint; //�I�΂ꂽ�ꏊ���󂯎�邽�߂̃|�C���g�ϐ�
    private byte nowPlayerNum = 0;
    private PlayerManager[] PlayerManagers = new PlayerManager[2]; //�v���C������
    private PlayerManager nowPlayerManager; //���̎��
    private Player nowPlayer;
    private JPanel BoardPanel;
    /**
     * �v���C�������肵�āC�Q�[�����n�߂܂�
     * @param board Board
     * @param player1 PlayerManager
     * @param player2 PlayerManager
     */
    public gameController(Board board,JPanel panel,PlayerManager player1, PlayerManager player2) {
        //�ϐ��Z�b�g
        Board = board;
        this.BoardPanel = panel;
        PlayerManagers[0] = player1;
        PlayerManagers[1] = player2;
        PlayerManagers[0].setGC(this);
        PlayerManagers[1].setGC(this);
        //�{�[�h�̏�����
        Board.boardInit();
        BoardPanel.repaint();
    }

    public void run() {
        nowPlayerManager = PlayerManagers[0];
        while (true) {
            selectedPoint = null;
            getPlayerChoice(); //�v���C���̑I�������܂��D
            if(selectedPoint == null){
                throw new NullPointerException("Any point was selected.");
            }
//            Board.setDiscColor(this.selectedPoint,Disc.BLACK);
            System.out.println(nowPlayerNum+":"+selectedPoint.getTextKifu());
            changeNowPlayer();//�v���C���̕ύX
            BoardPanel.repaint();
//            BoardPanel.revalidate();
        }
    }

    public void getPlayerChoice() {
            try {
                nowPlayer = nowPlayerManager.getPlayer(); //�v���C���擾
                nowPlayer.start(); //�v���C���J�n
                nowPlayer.join(); //�v���C���I���܂ő҂�
            } catch (InterruptedException ex) {
            }
    }

    /**
     * �e�v���C������̎���󂯎��܂��D
     * @param point point
     */
    public void setPoint(Point point) {
        selectedPoint = point;
    }

    /**
     * ���݂̎�Ԃ̃v���C����Ԃ��܂��D
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
