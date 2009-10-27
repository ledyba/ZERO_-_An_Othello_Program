package zero.player.human;

import zero.player.Player;
import zero.player.common.Point;
import zero.player.common.Disc;
import zero.player.*;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 *
 * <p>����: �l�Ԑ�p</p>
 *
 * <p>���쌠: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
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
     * �v���C���̖��O���擾�D�������C�u�l�ԁv�ł��D
     * @return String
     */
    public static String getPlayerName() {
        return "Human";
    }

    /**
     * �X���b�h�J�n
     */
    public synchronized void run() {
        pressedDiscButton(null);
        try {
            while (selectedPoint == null) {
                wait(10); //����������Ă���܂ŕ��u
            }
            GC.setPoint(selectedPoint); //�|�C���g�ݒ�
            //�����܂�
        } catch (InterruptedException e) {

        }
    }

    /**
     * �{�^���������ꂽ�Ƃ��̏����F�����l�Ԑ�p
     * @param point point
     */
    public synchronized void pressedDiscButton(Point point) {
        selectedPoint = point;
        //�����ĊJ
    }

    private void jbInit() throws Exception {
    }

}
