package zero.player.zero;

import zero.player.PlayerManager;
import zero.player.Player;
import zero.player.zero.*;

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
public class zeroMG extends PlayerManager {
    public zeroMG() {
    }
    public Player getPlayer(){
        return new main(this.getGC());
    }
}
