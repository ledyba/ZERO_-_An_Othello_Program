package zero.player.human;
import zero.player.human.*;
import zero.player.*;

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
public class humanMG extends PlayerManager{
    public humanMG() {
    }
    public Player getPlayer(){
        return new input(this.getGC());
    }
}
