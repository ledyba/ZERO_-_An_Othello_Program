package zero.player.common;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 *
 * <p>����: ���W��\���I�u�W�F�N�g�ł�</p>
 *
 * <p>���쌠: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
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
     * ���W����Ֆʏ�̔ԍ���Ԃ��܂�
     * @return int
     */
    public byte getNumber(){
        return (byte)((X-1)*8 + Y-1);
    }
    /**
     * ���W����z����ł̔ԍ���Ԃ��܂�
     * @return int
     */
    public int getArrayNumber(){
        return X*10 + Y;
    }
    /**
     * ��̊֐��̃X�^�e�B�b�N�ŁB
     * @param x byte
     * @param y byte
     * @return byte
     */
    public static byte getArrayNumber(byte x,byte y){
        return (byte)(x*10 + y);
    }
    /**
     * ���W�̃e�L�X�g�\����Ԃ��܂�
     * @return String
     */
    public String getText(){
        String str = "("+String.valueOf(X)+","+String.valueOf(Y)+")";
        return str;
    }
    public static final char KifuX[] = {'a','b','c','d','e','f','g','h',};
    public static final char KifuY[] = {'1','2','3','4','5','6','7','8',};
    /**
     * ���W�̊����\����Ԃ��܂��B
     * @return String
     */
    public String getTextKifu(){
        char[] str = {KifuX[X-1],KifuY[Y-1]};
        String ret = new String(str);
        return ret;
    }
}
