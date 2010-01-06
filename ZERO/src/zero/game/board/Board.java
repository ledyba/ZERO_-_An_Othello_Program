/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zero.game.board;

import java.util.Iterator;


/**
 *
 * @author PSI
 */
public class Board {
    public static final byte NONE          = (byte)0;
    public static final byte UPPER         = (byte)1;
    public static final byte UPPER_LEFT    = (byte)2;
    public static final byte LEFT          = (byte)4;
    public static final byte LOWER_LEFT    = (byte)8;
    public static final byte LOWER         = (byte)16;
    public static final byte LOWER_RIGHT   = (byte)32;
    public static final byte RIGHT         = (byte)64;
    public static final byte UPPER_RIGHT   = (byte)128;

    public static final byte BOARD_SIZE       = 8;
    public static final byte DISC_MAX       = (byte)((BOARD_SIZE+2) * (BOARD_SIZE+2));
    public static final byte TURN_MAX       = 60;

    private final byte[] RawBoard = new byte[DISC_MAX];
    private byte CurrentColor;
    //一応ログとっておいた方がよさそうなのはこっち
    private Log Log;

    public void init(){
        for(int i=0;i<RawBoard.length;i++){
            RawBoard[i] = Disc.EMPTY;
        }
        for(byte i=0;i<BOARD_SIZE+2;i++){
            RawBoard[Point.getBoardIndex(      i, (byte)9)] = Disc.EMPTY;
            RawBoard[Point.getBoardIndex(      i, (byte)0)] = Disc.EMPTY;
            RawBoard[Point.getBoardIndex((byte)9,       i)] = Disc.EMPTY;
            RawBoard[Point.getBoardIndex((byte)0,       i)] = Disc.EMPTY;
        }
        RawBoard[Point.getBoardIndex((byte)4, (byte)4)] = Disc.WHITE;
        RawBoard[Point.getBoardIndex((byte)5, (byte)5)] = Disc.WHITE;
        RawBoard[Point.getBoardIndex((byte)5, (byte)4)] = Disc.BLACK;
        RawBoard[Point.getBoardIndex((byte)4, (byte)5)] = Disc.BLACK;
        CurrentColor = Disc.BLACK;
        Log.init();
        LogElem elem = Log.getLastElem();
        elem.setDiscStorage(Disc.EMPTY, (byte)(BOARD_SIZE*BOARD_SIZE-4));
        elem.setDiscStorage(Disc.WHITE, (byte)2);
        elem.setDiscStorage(Disc.BLACK, (byte)2);
        initMovable();
    }
    public boolean move(Point pt){
        if(!pt.isValidRange() ||
                Log.getNowElem().getMobility(pt.getBoardIndex()) == NONE){
            return false;
        }
        flipDiscs(pt);
        Log.next();
        CurrentColor *= -1;
        initMovable();
        return true;
    }
    public boolean pass(){
        if(Log.getNowElem().getMovable().size() > 0){
            return false;
        }
        if(isGameOver()){
            return false;
        }
        Log.getNowElem().setMoved(null);
        Log.next();
        CurrentColor *= -1;
        initMovable();
        return true;
    }
    public boolean undo(){
        LogElem elem = Log.undo();
        if(elem == null){
            return false;
        }
        CurrentColor *= -1;
        PointStack updated = elem.getUpdated();
        Iterator<Point> it = updated.getIterator();
        RawBoard[elem.getMoved().getBoardIndex()] = Board.NONE;
        while(it.hasNext()){
            RawBoard[it.next().getBoardIndex()] *= -1;
        }
        return true;
    }
    public boolean isGameOver(){
        if(getTurns() == TURN_MAX){
            return true;
        }
        if(Log.getNowElem().getMovable().size() > 0){
            return false;
        }
        //まだひっくり返せるならゲームオーバーじゃない
        Disc disc = new Disc((byte)-CurrentColor);
        for(byte y=1;y<=8;y++){
            disc.setY(y);
            for(byte x=1;x<=8;x++){
                disc.setX(x);
                if(checkMobility(disc) != NONE){
                    return false;
                }
            }
        }
        return true;
    }
    public byte getColor(Point pt){
        return (byte)RawBoard[pt.getBoardIndex()];
    }
    public byte getCurrentColor(){
        return CurrentColor;
    }
    public byte countDisc(byte color){
        return Log.getNowElem().getDiscStorage(color);
    }
    public PointStack getUpdated(){
        LogElem elem = Log.getLastElem();
        if(elem == null){
           return null;
        }
        return elem.getUpdated();
    }
    public PointStack getMovablePos(){
        return Log.getNowElem().getMovable();
    }
    public byte getTurns(){
        return Log.getNowTurnCount();
    }
    private void flipDiscs(Point pt){
        LogElem elem = Log.getNowElem();
        PointStack updated = elem.getUpdated();
        byte mobility = elem.getMobility(pt.getBoardIndex());
        byte x,y;
        byte dx = pt.getX();
        byte dy = pt.getY();
        byte dcolor = CurrentColor;
        int idx;
        //自分自身
        RawBoard[pt.getBoardIndex()] = dcolor;
        updated.push(pt);
        //UPPER
        if((mobility & UPPER) != NONE){
            y = (byte)(dy-1);
            while(dcolor != RawBoard[(idx = Disc.getBoardIndex(dx, y))]){
                RawBoard[idx] = dcolor;
                updated.push(dx,y);
                y--;
            }
        }
        //UPPER_LEFT
        if((mobility & UPPER_LEFT) != NONE){
            x = (byte)(dx-1);
            y = (byte)(dy-1);
            while(dcolor != RawBoard[(idx = Disc.getBoardIndex(x, y))]){
                RawBoard[idx] = dcolor;
                updated.push(x,y);
                x--;
                y--;
            }
        }
        //LEFT
        if((mobility & LEFT) != NONE){
            x = (byte)(dx-1);
            while(dcolor != RawBoard[(idx = Disc.getBoardIndex(x, dy))]){
                RawBoard[idx] = dcolor;
                updated.push(x,dy);
                x--;
            }
        }
        //LOWER_LEFT
        if((mobility & UPPER_LEFT) != NONE){
            x = (byte)(dx-1);
            y = (byte)(dy+1);
            while(dcolor != RawBoard[(idx = Disc.getBoardIndex(x, y))]){
                RawBoard[idx] = dcolor;
                updated.push(x,y);
                x--;
                y++;
            }
        }
        //LOWER
        if((mobility & LOWER) != NONE){
            y = (byte)(dy+1);
            while(dcolor != RawBoard[(idx = Disc.getBoardIndex(dx, y))]){
                RawBoard[idx] = dcolor;
                updated.push(dx,y);
                y++;
            }
        }
        //LOWER_RIGHT
        if((mobility & UPPER_LEFT) != NONE){
            x = (byte)(dx+1);
            y = (byte)(dy+1);
            while(dcolor != RawBoard[(idx = Disc.getBoardIndex(x, y))]){
                RawBoard[idx] = dcolor;
                updated.push(x,y);
                x++;
                y++;
            }
        }
        //RIGHT
        if((mobility & RIGHT) != NONE){
            x = (byte)(dx+1);
            while(dcolor != RawBoard[(idx = Disc.getBoardIndex(x, dy))]){
                RawBoard[idx] = dcolor;
                updated.push(x,dy);
                x++;
            }
        }
        //UPPER_RIGHT
        if((mobility & UPPER_LEFT) != NONE){
            x = (byte)(dx+1);
            y = (byte)(dy-1);
            while(dcolor != RawBoard[(idx = Disc.getBoardIndex(x, y))]){
                RawBoard[idx] = dcolor;
                updated.push(x,y);
                x++;
                y--;
            }
        }
        //押した手を設定
        elem.setMoved(pt);
        //石数のカウント
        int diff = updated.size();
        elem.setDiscStorage((byte)(dcolor), (byte)(elem.getDiscStorage(dcolor) + diff));
        elem.setDiscStorage((byte)(-dcolor), (byte)(elem.getDiscStorage((byte)-dcolor) - diff - 1));
        elem.setDiscStorage((byte)(Disc.EMPTY), (byte)(elem.getDiscStorage(Disc.EMPTY) - 1));
    }
    private void initMovable(){
        Disc disc = new Disc((byte)0,(byte)0,CurrentColor);
        LogElem elem = Log.getNowElem();
        byte[] mobility_list =  elem.getMobility();
        for(byte y=1;y<=BOARD_SIZE;y++){
            disc.setY(y);
            for(byte x=1;x<=BOARD_SIZE;x++){
                disc.setX(x);
                if(NONE != (mobility_list[disc.getBoardIndex()] = checkMobility(disc))){
                    elem.getMovable().push(disc);
                }
            }
        }
    }
    private byte checkMobility(Disc disc){
        byte mobility = NONE;
        byte dx = disc.getX();
        byte dy = disc.getY();
        byte dcolor = disc.getColor();
        byte x,y;
        if(RawBoard[Disc.getBoardIndex(dx, dy)] != Disc.EMPTY){
            return NONE;
        }
        //UPPER
        if(-dcolor == RawBoard[Disc.getBoardIndex(dx, (byte)(dy-1))]){
            y = (byte)(dy-2);
            while(-dcolor == RawBoard[Disc.getBoardIndex(dx, y)]){
                y--;
            }
            if(dcolor == RawBoard[Disc.getBoardIndex(dx, y)]){
                mobility |= UPPER;
            }
        }
        //UPPER_LEFT
        if(-dcolor == RawBoard[Disc.getBoardIndex((byte)(dx-1), (byte)(dy-1))]){
            x = (byte)(dx-2);
            y = (byte)(dy-2);
            while(-dcolor == RawBoard[Disc.getBoardIndex(x, y)]){
                x--;
                y--;
            }
            if(dcolor == RawBoard[Disc.getBoardIndex(x, y)]){
                mobility |= UPPER_LEFT;
            }
        }
        //LEFT
        if(-dcolor == RawBoard[Disc.getBoardIndex((byte)(dx-1), dy)]){
            x = (byte)(dx-2);
            while(-dcolor == RawBoard[Disc.getBoardIndex(x, dy)]){
                x--;
            }
            if(dcolor == RawBoard[Disc.getBoardIndex(x, dy)]){
                mobility |= LEFT;
            }
        }
        //LOWER_LEFT
        if(-dcolor == RawBoard[Disc.getBoardIndex((byte)(dx-1), (byte)(dy+1))]){
            x = (byte)(dx-2);
            y = (byte)(dy+2);
            while(-dcolor == RawBoard[Disc.getBoardIndex(x, y)]){
                x--;
                y++;
            }
            if(dcolor == RawBoard[Disc.getBoardIndex(x, y)]){
                mobility |= LOWER_LEFT;
            }
        }
        //LOWER
        if(-dcolor == RawBoard[Disc.getBoardIndex(dx, (byte)(dy+1))]){
            y = (byte)(dy+2);
            while(-dcolor == RawBoard[Disc.getBoardIndex(dx, y)]){
                y++;
            }
            if(dcolor == RawBoard[Disc.getBoardIndex(dx, y)]){
                mobility |= LOWER;
            }
        }
        //LOWER_RIGHT
        if(-dcolor == RawBoard[Disc.getBoardIndex((byte)(dx+1), (byte)(dy+1))]){
            x = (byte)(dx+2);
            y = (byte)(dy+2);
            while(-dcolor == RawBoard[Disc.getBoardIndex(x, y)]){
                x++;
                y++;
            }
            if(dcolor == RawBoard[Disc.getBoardIndex(x, y)]){
                mobility |= LOWER_RIGHT;
            }
        }
        //RIGHT
        if(-dcolor == RawBoard[Disc.getBoardIndex((byte)(dx+1), dy)]){
            x = (byte)(dx+2);
            while(-dcolor == RawBoard[Disc.getBoardIndex(x, dy)]){
                x++;
            }
            if(dcolor == RawBoard[Disc.getBoardIndex(x, dy)]){
                mobility |= RIGHT;
            }
        }
        //UPPER_RIGHT
        if(-dcolor == RawBoard[Disc.getBoardIndex((byte)(dx+1), (byte)(dy-1))]){
            x = (byte)(dx+2);
            y = (byte)(dy-2);
            while(-dcolor == RawBoard[Disc.getBoardIndex(x, y)]){
                x++;
                y--;
            }
            if(dcolor == RawBoard[Disc.getBoardIndex(x, y)]){
                mobility |= UPPER_RIGHT;
            }
        }
        return mobility;
    }
}

class Log{
    private final LogElem[] ElemStack = new LogElem[Board.TURN_MAX];
    private byte NowStackIdx;
    public Log(){
        init();
    }
    public void init(){
        for(int i=0;i<Board.TURN_MAX;i++){
            ElemStack[i] = new LogElem();
        }
        NowStackIdx = 0;
    }
    public LogElem getNowElem(){
        return ElemStack[NowStackIdx];
    }
    public LogElem getLastElem(){
        if(NowStackIdx > 0){
            return ElemStack[NowStackIdx-1];
        }
        return null;
    }
    public byte getNowTurnCount(){
        return NowStackIdx;
    }
    public LogElem next(){
        NowStackIdx++;
        ElemStack[NowStackIdx-1].next(ElemStack[NowStackIdx]);
        return ElemStack[NowStackIdx];
    }
    public LogElem undo(){
        if(NowStackIdx <= 0){
            return null;
        }
        NowStackIdx--;
        return ElemStack[NowStackIdx];
    }
}

class LogElem{
    private final byte Mobility[] = new byte[Board.DISC_MAX];
    private final PointStack Updated = new PointStack();
    private final PointStack Movable = new PointStack();
    private final byte Disc[] = new byte[3];
    private boolean isPass = false;
    private final Point Moved = new Point();
    public void setMoved(Point pt){
        if(pt != null){
            isPass = false;
            setMoved(pt.getX(),pt.getY());
        }else{
            isPass = true;
        }
    }
    public void setMoved(byte x,byte y){
        Moved.setPoint(x,y);
    }
    public Point getMoved(){
        return Moved;
    }
    public byte getDiscStorage(byte color) {
        return Disc[color+1];
    }
    public void setDiscStorage(byte color,byte num) {
        Disc[color+1] = num;
    }
    public void next(LogElem next){
        for(int i=0;i<3;i++){
            next.Disc[i] = this.Disc[i];
        }
        Moved.setPoint((byte)0,(byte)0);
        Updated.clear();
        Movable.clear();
    }
    public PointStack getMovable() {
        return Movable;
    }
    public PointStack getUpdated() {
        return Updated;
    }
    public byte getMobility(int idx) {
        return Mobility[idx];
    }
    public byte[] getMobility() {
        return Mobility;
    }
    public byte getMobility(byte x,byte y) {
        return Mobility[Point.getBoardIndex(x, y)];
    }
}
