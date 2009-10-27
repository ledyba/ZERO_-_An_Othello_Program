package zero.player;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 *
 * <p>����: �v���C���N���X�̃}�l�[�W���ł�</p>
 *
 * <p>���쌠: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class PlayerManager {
    public PlayerManager() {
    }
    private gameController gc;
    /**
     * �v���C���N���X��Ԃ��܂�
     * @return Player
     */
    public Player getPlayer(){
        return new Player();
    }
    /**
     * �Q�[���R���g���[����ݒ肵�܂��D
     * @param gc gameController
     */
    public void setGC(gameController GC){
        gc = GC;
    }
    /**
     * �Q�[���R���g���[����Ԃ��܂��D
     * @return gameController
     */
    public gameController getGC(){
        return gc;
    }
}
