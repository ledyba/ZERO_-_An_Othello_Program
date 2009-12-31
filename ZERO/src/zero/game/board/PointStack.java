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
public class PointStack implements Iterator {
    private Point Stack[] = new Point[Board.DISC_MAX];
    private int StackIndex = 0;
    private int IteratorIndex = 0;
    public PointStack(){
        for(Point stack : Stack){
            stack = new Point();
        }
    }
    public void clear(){
        StackIndex = 0;
        IteratorIndex = Board.DISC_MAX+1;
    }
    public void push(Point pt){
        Stack[StackIndex].setPoint(pt);
        StackIndex++;
    }
    public void push(byte x,byte y){
        Stack[StackIndex].setPoint(x,y);
        StackIndex++;
    }
    public int size(){
        return StackIndex;
    }
    public Iterator<Point> getIterator(){
        IteratorIndex = 0;
        return this;
    }
    public boolean hasNext() {
        return IteratorIndex <= StackIndex;
    }
    public Point next() {
        if(IteratorIndex <= StackIndex){
            Point pt = Stack[IteratorIndex];
            IteratorIndex++;
            return pt;
        }
        return null;
    }
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
