package zero.player;
import zero.player.common.Point;
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
public class Player extends Thread{//�v���C���͂��Ȃ炸���̃N���X���p��
    /**
     * �v���C������Ԃ��܂��D
     * @return String
     */
    public static String getPlayerName(){
        return "default";
    }

    /**
     * �X���b�h�J�n
     */
    public void run(){}

    /**
     * �{�^���������ꂽ�Ƃ��̏����F�����l�Ԑ�p
     * @param point point
     */
    public void pressedDiscButton(Point point){
    }
}
