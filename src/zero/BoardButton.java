package zero;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import zero.player.common.Point;
import zero.player.common.Board;
import zero.player.common.Disc;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 *
 * <p>説明: ボード専用のボタンクラスです．</p>
 *
 * <p>著作権: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class BoardButton extends JButton {
    //左上が(1,1,)
    private Point POINT;
    private MainFrame MF;
    //アイコン
    public static final ImageIcon WHITE_IMG = new ImageIcon(zero.MainFrame.class.
            getResource("img/white.gif"));
    public static final ImageIcon BLACK_IMG = new ImageIcon(zero.MainFrame.class.
            getResource("img/black.gif"));
    public static final ImageIcon NULL_IMG = new ImageIcon(zero.MainFrame.class.
            getResource("img/null.gif"));

    /**
     * アイコン・テキストなしのアイコンを作成します．
     */
    public BoardButton(MainFrame MF) {
        super();
        this.MF = MF;
    }

    /**
     * 状態に合わせてアイコンを返します
     * @param point point
     */
    public Icon getIcon() {
        Board board = MF.getBoard();
//        System.out.println(POINT.X+":"+POINT.Y+":"+POINT.getArrayNumber());
        switch (board.getDisc(POINT).getColor()){
        case Disc.BLACK:
            return BLACK_IMG;
        case Disc.WHITE:
            return WHITE_IMG;
        case Disc.NULL:
            return NULL_IMG;
        default:
            System.out.println("ERROE:in BoardButton#getIcon");
            return NULL_IMG;
        }
    }
    /**
     * ボタンのボード上の位置を設定します．
     * @param point point
     */
    public void setBoardPoint(Point point) {
        POINT = point;
    }

    /**
     * ボタンのボード上の座標をかえします．
     * @return point bp
     */
    public Point getBoardPoint() {
        return POINT;
    }
}
