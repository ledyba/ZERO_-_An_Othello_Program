/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zero.game.board;

import javax.management.InvalidAttributeValueException;
import org.omg.CORBA.DynAnyPackage.Invalid;

/**
 *  a b c d e f g h -> x軸
 *  1 2 3 4 5 6 7 8
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 7
 * 8
 * |
 * ^y軸
 * とする。
 */

/**
 *
 * @author PSI
 */
public class Point {
    private byte x;
    private byte y;
    private String PointName = null;
    private static final String POINT_STRING = "abcdefgh";
    public Point(){
        this((byte)0,(byte)0);
    }
    public Point(byte x,byte y){
        setPoint((byte)0,(byte)0);
    }
    public Point(String point) throws Exception{
        setPoint(point);
    }
    public Point(Point pt){
        setPoint(pt.getX(),pt.getY());
    }
    public void setPoint(String point) throws  Exception{
        if(point.length() != 2){
            throw new Exception("Invalid Point Format");
        }
        int xx = POINT_STRING.indexOf(point.substring(0, 1));
        if(x < 0){
            throw new Exception("Invalid Point Format");
        }
        xx++;
        int yy = Integer.getInteger(point.substring(1, 2));
        if(yy <= 0 || yy > 8){
            throw new Exception("Invalid Point Format");
        }
        setPoint((byte)xx,(byte)yy);
    }
    public void setPoint(byte x,byte y){
        this.x = x;
        this.y = y;
        PointName = null;
    }
    public void setPoint(Point pt){
        setPoint(pt.x,pt.y);
    }
    public String getPoint(){
        if(PointName == null){
            PointName = POINT_STRING.substring(this.x-1,this.x) + String.valueOf(y);
        }
        return PointName;
    }

    public byte getX() {
        return x;
    }

    public void setX(byte x) {
        this.x = x;
    }


    public byte getY() {
        return y;
    }

    public void setY(byte y) {
        this.y = y;
    }
    
    @Override public String toString(){
        return getPoint();
    }
    public static int getBoardIndex(Point pt){
        return getBoardIndex(pt.getX(),pt.getY());
    }
    public static int getBoardIndex(byte x,byte y){
        return x * 10 + y +1;
    }
    public int getBoardIndex(){
        return getBoardIndex(getX(),getY());
    }
    public boolean isValidRange(){
        return this.x >= 1 && this.x <= 8 && this.y >= 1 && this.y <= 8;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }
    @Override public boolean equals(Object obj){
        Point pt = (Point)obj;
        return pt.x == this.x && pt.y == this.y;
    }
}
