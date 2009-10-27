package zero;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import zero.player.common.Point;
import zero.player.common.Board;
import zero.player.common.Disc;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 *
 * <p>����: �{�[�h��p�̃{�^���N���X�ł��D</p>
 *
 * <p>���쌠: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class BoardButton extends JButton {
    //���オ(1,1,)
    private Point POINT;
    private MainFrame MF;
    //�A�C�R��
    public static final ImageIcon WHITE_IMG = new ImageIcon(zero.MainFrame.class.
            getResource("img/white.gif"));
    public static final ImageIcon BLACK_IMG = new ImageIcon(zero.MainFrame.class.
            getResource("img/black.gif"));
    public static final ImageIcon NULL_IMG = new ImageIcon(zero.MainFrame.class.
            getResource("img/null.gif"));

    /**
     * �A�C�R���E�e�L�X�g�Ȃ��̃A�C�R�����쐬���܂��D
     */
    public BoardButton(MainFrame MF) {
        super();
        this.MF = MF;
    }

    /**
     * ��Ԃɍ��킹�ăA�C�R����Ԃ��܂�
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
     * �{�^���̃{�[�h��̈ʒu��ݒ肵�܂��D
     * @param point point
     */
    public void setBoardPoint(Point point) {
        POINT = point;
    }

    /**
     * �{�^���̃{�[�h��̍��W���������܂��D
     * @return point bp
     */
    public Point getBoardPoint() {
        return POINT;
    }
}
