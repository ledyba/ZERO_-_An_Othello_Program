package zero.player.common;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 *
 * <p>����: ���������킷�N���X�ł�</p>
 *
 * <p>���쌠: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class Disc {
    public static final byte BLACK = 1;//��
    public static final byte WHITE = -1;//����
    public static final byte NULL = 0;//�Ȃɂ��Ȃ�
    public static final byte WALL = 2;//��

    private Point POINT;
    private byte COLOR = this.NULL;
    public Disc(Point point,byte color) {//�I�u�W�F�N�g���쐬���܂�
        POINT = point;
        this.COLOR=color;
    }
    /**
     * �J���[���Z�b�g���܂�
     * @param Color int
     */
    public void setColor(byte Color){
        COLOR = Color;
    }
    /**
     * �J���[���Ђ�����Ԃ��܂�
     */
    public void turnColor(){
        //�v����
        COLOR *= -1;
    }
    /**
     * �J���[���������܂�
     * @return int
     */
    public int getColor(){
        return COLOR;
    }
}
