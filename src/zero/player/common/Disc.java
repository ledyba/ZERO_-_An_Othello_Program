package zero.player.common;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 *
 * <p>説明: 駒一つ一つをあらわすクラスです</p>
 *
 * <p>著作権: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class Disc {
    public static final byte BLACK = 1;//黒
    public static final byte WHITE = -1;//しろ
    public static final byte NULL = 0;//なにもなし
    public static final byte WALL = 2;//壁

    private Point POINT;
    private byte COLOR = this.NULL;
    public Disc(Point point,byte color) {//オブジェクトを作成します
        POINT = point;
        this.COLOR=color;
    }
    /**
     * カラーをセットします
     * @param Color int
     */
    public void setColor(byte Color){
        COLOR = Color;
    }
    /**
     * カラーをひっくり返します
     */
    public void turnColor(){
        //要検討
        COLOR *= -1;
    }
    /**
     * カラーをかえします
     * @return int
     */
    public int getColor(){
        return COLOR;
    }
}
