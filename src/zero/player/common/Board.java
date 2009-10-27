package zero.player.common;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 *
 * <p>説明: ボードを表現します</p>
 *
 * <p>著作権: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class Board {
    private Disc[] DISC = new Disc[10*10];//周りは全部壁にします
    public Board() {
        for(byte i=0;i<10;i++){//中身です
            for(byte j=0;j<10;j++){
                Point pt = new Point(i, j);
                DISC[i*10+j] = new Disc(pt, Disc.NULL);
            }
        }
        boardInit();//駒の初期配置
    }
    public void boardInit(){
        for(byte i=0;i<10;i++){
            DISC[i*10].setColor(Disc.WALL);
            DISC[i*10+9].setColor(Disc.WALL);
            DISC[i].setColor(Disc.WALL);
            DISC[90+i].setColor(Disc.WALL);
        }
        for(byte i=1;i<9;i++){//中身です
            for(byte j=1;j<9;j++){
                DISC[i*10+j].setColor(Disc.NULL);
            }
        }
        //こまの初期配置
        DISC[Point.getArrayNumber((byte)4,(byte)4)].setColor(Disc.WHITE);
        DISC[Point.getArrayNumber((byte)5,(byte)5)].setColor(Disc.WHITE);
        DISC[Point.getArrayNumber((byte)4,(byte)5)].setColor(Disc.BLACK);
        DISC[Point.getArrayNumber((byte)5,(byte)4)].setColor(Disc.BLACK);
    }
    /**
     * ディスクを返します．
     * @param pt point
     * @return Disc
     */
    public Disc getDisc(Point pt){
        return DISC[pt.getArrayNumber()];
    }
    /**
     * ディスクの配列を返します．
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
