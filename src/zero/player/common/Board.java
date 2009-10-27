package zero.player.common;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 *
 * <p>����: �{�[�h��\�����܂�</p>
 *
 * <p>���쌠: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class Board {
    private Disc[] DISC = new Disc[10*10];//����͑S���ǂɂ��܂�
    public Board() {
        for(byte i=0;i<10;i++){//���g�ł�
            for(byte j=0;j<10;j++){
                Point pt = new Point(i, j);
                DISC[i*10+j] = new Disc(pt, Disc.NULL);
            }
        }
        boardInit();//��̏����z�u
    }
    public void boardInit(){
        for(byte i=0;i<10;i++){
            DISC[i*10].setColor(Disc.WALL);
            DISC[i*10+9].setColor(Disc.WALL);
            DISC[i].setColor(Disc.WALL);
            DISC[90+i].setColor(Disc.WALL);
        }
        for(byte i=1;i<9;i++){//���g�ł�
            for(byte j=1;j<9;j++){
                DISC[i*10+j].setColor(Disc.NULL);
            }
        }
        //���܂̏����z�u
        DISC[Point.getArrayNumber((byte)4,(byte)4)].setColor(Disc.WHITE);
        DISC[Point.getArrayNumber((byte)5,(byte)5)].setColor(Disc.WHITE);
        DISC[Point.getArrayNumber((byte)4,(byte)5)].setColor(Disc.BLACK);
        DISC[Point.getArrayNumber((byte)5,(byte)4)].setColor(Disc.BLACK);
    }
    /**
     * �f�B�X�N��Ԃ��܂��D
     * @param pt point
     * @return Disc
     */
    public Disc getDisc(Point pt){
        return DISC[pt.getArrayNumber()];
    }
    /**
     * �f�B�X�N�̔z���Ԃ��܂��D
     * @return Disc[]
     */
    public Disc[] getDiscArray(){
        return DISC;
    }
    public void setDiscColor(byte x,byte y,byte color){
        DISC[Point.getArrayNumber(x,y)].setColor(color);
    }
    public void turnDiscColor(byte x,byte y){
        DISC[Point.getArrayNumber(x,y)].turnColor();
    }
    public void setDiscColor(Point pt,byte color){
        DISC[pt.getArrayNumber()].setColor(color);
    }
    public void turnDiscColor(Point pt){
        DISC[pt.getArrayNumber()].turnColor();
    }
}
