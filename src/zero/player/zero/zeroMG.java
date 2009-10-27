package zero.player.zero;

import zero.player.PlayerManager;
import zero.player.Player;
import zero.player.zero.*;

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
public class zeroMG extends PlayerManager {
    public zeroMG() {
    }
    public Player getPlayer(){
        return new main(this.getGC());
    }
}
