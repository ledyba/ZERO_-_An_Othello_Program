package zero.player.common;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 *
 * <p>説明: 座標を表すオブジェクトです</p>
 *
 * <p>著作権: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class Point {
    byte X;
    byte Y;
    public Point(byte x,byte y) {
        X=x;
        Y=y;
    }
    /**
     * 座標から盤面上の番号を返します
     * @return int
     */
    public byte getNumber(){
        return (byte)((X-1)*8 + Y-1);
    }
    /**
     * 座標から配列内での番号を返します
     * @return int
     */
    public int getArrayNumber(){
        return X*10 + Y;
    }
    /**
     * 上の関数のスタティック版。
     * @param x byte
     * @param y byte
     * @return byte
     */
    public static byte getArrayNumber(byte x,byte y){
        return (byte)(x*10 + y);
    }
    /**
     * 座標のテキスト表現を返します
     * @return String
     */
    public String getText(){
        String str = "("+String.valueOf(X)+","+String.valueOf(Y)+")";
        return str;
    }
    public static final char KifuX[] = {'a','b','c','d','e','f','g','h',};
    public static final char KifuY[] = {'1','2','3','4','5','6','7','8',};
    /**
     * 座標の棋譜表現を返します。
     * @return String
     */
    public String getTextKifu(){
        char[] str = {KifuX[X-1],KifuY[Y-1]};
        String ret = new String(str);
        return ret;
    }
}
