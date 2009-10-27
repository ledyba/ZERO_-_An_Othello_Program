package othello.computer;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 *
 * <p>説明: I want this program to be good at othello!</p>
 *
 * <p>著作権: Copyright (c) 2003-2004 by Sun-Soft</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
 * @version Version 0.5
 */
//手をあらわすオブジェクト
public class Move {
  public int X;
  public int Y;
  public int TURN;
  public int VALUE;//開放度
  public Move(int x,int y, int turn,int value) {
    X=x;
    Y=y;
    TURN = turn;
    VALUE = value;
  }
}
