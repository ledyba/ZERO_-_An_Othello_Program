package othello.computer;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 *
 * <p>����: I want this program to be good at othello!</p>
 *
 * <p>���쌠: Copyright (c) 2003-2004 by Sun-Soft</p>
 *
 * <p>��Ж�: </p>
 *
 * @author ������
 * @version Version 0.5
 */
//�������킷�I�u�W�F�N�g
public class Move {
  public int X;
  public int Y;
  public int TURN;
  public int VALUE;//�J���x
  public Move(int x,int y, int turn,int value) {
    X=x;
    Y=y;
    TURN = turn;
    VALUE = value;
  }
}
