/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zero.game.board;

/**
 *
 * @author PSI
 */
public class Disc extends Point {
    public static final byte EMPTY = 0;
    public static final byte WHITE = -1;
    public static final byte BLACK = 1;
    public static final byte WALL = 2;
    private byte color;
    public Disc(){
        super();
        color = EMPTY;
    }

    public byte getColor() {
        return color;
    }
    public Disc(byte color){
        super();
        this.color = color;
    }
    public Disc(byte x,byte y,byte color){
        super(x,y);
        this.color = color;
    }
    public Disc(Point pt,byte color){
        super(pt);
        this.color = color;
    }
    boolean isWall(){
        return this.color == WALL;
    }
    boolean isEmpty(){
        return this.color == EMPTY;
    }
    boolean isFriend(byte color){
        return this.color == color;
    }
    boolean isFoe(byte color){
        return this.color == -color;
    }
}
