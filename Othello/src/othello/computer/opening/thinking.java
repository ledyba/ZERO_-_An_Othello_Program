package othello.computer.opening;

import othello.*;

import othello.computer.*;
import java.util.*;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 * <p>����: I want this program to be good at othello!</p>
 * <p>���쌠: Copyright (c) 2003-2004 b7 Sun-Soft</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version Version 0.5
 */
//���Ղ͈ڐA�����I����������ȁI

public class thinking {
  static int BestMove[] = new int[2];
  static double best_move_value = 0;
  static double[] important =
      //�J���x�C�ꏊ�C����̋󂫂̑����C�ΐ��̑����C����\�ȏꏊ
      {
      10, 1, 0.25, 1, 1};

  static int[] value = { //��
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 45, -11, 4, -1, -1, 4, -11, 45, 0,
      0, -11, -16, -1, -3, -3, -1, -16, -11, 0,
      0, 4, -1, 2, -1, -1, 2, -1, 4, 0,
      0, -1, -3, -1, 0, 0, -1, -3, -1, 0,
      0, -1, -3, -1, 0, 0, -1, -3, -1, 0,
      0, 4, -1, 2, -1, -1, 2, -1, 4, 0,
      0, -11, -16, -1, -3, -3, -1, -16, -11, 0,
      0, 45, -11, 4, -1, -1, 4, -11, 45, 0, };

  static int master_depth = 7; //�[��
  static final int Piece[] = new int[89];
  static final int PieceBackup[][] = new int[master_depth][89];
  static int count[][] = new int[master_depth + 1][2];
  static othello.computer.opening.piece_turn_over pto = new othello.computer.
      opening.piece_turn_over();
  static othello.computer.opening.can_piece_turn_over cpto = new othello.
      computer.opening.can_piece_turn_over();
  public static int[] start(int turn, int turn_count) {
    System.out.println("\n(opening) thinking start...");
    System.arraycopy(MainFrame.p,0,Piece,0,89);
    System.out.println("--------------------");
    System.out.print("Turn:");
    if (turn == 1) {
      System.out.println("Black," + turn_count);
    }
    else {
      System.out.println("White," + turn_count);
    }
    System.out.println("--------------------");
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if (Piece[10 * y + x] == 1) {
          System.out.print("��");
        }
        else if (Piece[10 * y + x] == -1) {
          System.out.print("��");
        }
        else {
          System.out.print("�@");
        }
      }
      System.out.println("");
    }

    run(true, master_depth, turn, turn_count, 0, -9999, 9999);
    System.out.println("\nBest Move " + MainFrame.kifu_table[BestMove[1] * 10 +
                       BestMove[0]] + " : " + best_move_value + "\n");
    for (int i = master_depth; i > 0; i--) {
      System.out.println( (master_depth - i + 1) + ":" + count[i][0] + "(- " +
                         count[i][1] + ")");
      count[i][0] = 0;
      count[i][1] = 0;
    }
    return BestMove;
  }

//�Ƃ肠����NegaMax
  public static double run(boolean last, int depth, int turn, int turn_count,
                           int open_value, double alpha, double beta) {
    if (depth <= 0 || turn_count > 60) {
      return eval(open_value, turn, Piece);
    }
    else {
      int[] bkp = PieceBackup[depth-1];
      System.arraycopy(Piece,0,bkp,0,89);
      double s = 0;
      List MoveList = new ArrayList();//��̃��X�g��
      for (int x = 1; x <= 8; x++) {
        for (int y = 1; y <= 8; y++) {
          if(pto.main(x,y,turn)){
            int open = - open_value(bkp,turn);
            MoveList.add(new Move(x,y,turn,open));
            System.arraycopy(bkp,0,Piece,0,89);
          }
        }
      }
      for(int i=0;i<MoveList.size();i++){
        for(int j=i+1;j<MoveList.size();j++){
          if(((Move)MoveList.get(j)).VALUE > ((Move)MoveList.get(i)).VALUE){
            Move tmp = ((Move)MoveList.get(j));
            MoveList.set(j,(Move)MoveList.get(i));
            MoveList.set(i,tmp);
          }
        }
      }


      if (MoveList.size() == 0) { //�p�X����
        if (last == false) { //�O����_���������͗t����
          return eval(open_value, turn, Piece);
        }
        else {
          //���ґłĂȂ��Ƃ����̂͊����̉\��������D
          int[] piece_amount = new int[2];
          //[0]:���� [1]:����
          for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
              if (Piece[10 * x + y] == turn) {
                piece_amount[0]++;
              }
              else if (Piece[10 * x + y] == turn * -1) {
                piece_amount[1]++;
              }
            }
          }
          if (piece_amount[0] <= 0) { //������0
            return -9999;
          }
          else if (piece_amount[1] <= 0) { //����0
            return 9999;
          }
          else { //����ȊO�͕��ʂɕ]���l��Ԃ�
            return -1 *
                run(false, depth, turn * -1, turn_count, -open_value, -beta,
                    -alpha);
          }
        }
      }


      Iterator iterator = MoveList.iterator();
      while (iterator.hasNext()) {
        Move move = (Move)iterator.next();
          if (pto.main(move.X, move.Y, turn)) {
            s = -1 * run(true, depth - 1, turn * -1, turn_count + 1,
                         -1 * (open_value + open_value(bkp, turn)), -beta,
                         -alpha);
            count[depth][0]++;
            if (depth == master_depth) {
              System.out.println(MainFrame.kifu_table[move.Y * 10 + move.X] + " eval:" +
                                 s);
            }
            System.arraycopy(bkp,0,Piece,0,89);
            if (s >= beta) {
              count[depth][1]++;
              return s;
            }
            else {
              if (s > alpha) {
                if (depth == master_depth) {
                  BestMove[0] = move.X;
                  BestMove[1] = move.Y;
                  best_move_value = s;
                }
                alpha = s;
              }
            }
          }
      }
      return alpha;
    }
  }

  public static double eval(int open_value, int turn, int[] p) { //�]���l�v�Z
    double eval = 0;
    //�J���x�C�ꏊ�C����̐΁C�΂̑���
    eval =
        -important[0] * open_value //�J���x
        + important[1] * place(turn) //�ꏊ
        - important[2] * around(turn) //����̐�
        - important[3] * count(turn) //�΂̑���
        //+ important[4] * can_move_value(p,turn)//���萔
        ;
    return eval;
  }

  public static int can_move_value(int[] bkp, int turn) { //���萔�v�Z
    //�Ԃ�l�F�������u����ꏊ-���肪�u����ꏊ
    int eval = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if (bkp[10 * x + y] == 0) { //�����ꏊ�ł��邱�ƁD���̃`�F�b�N������Α����Ȃ�H
          if (cpto.main(x, y, turn, bkp)) { //�������u����
            eval++;
          }
          else if (cpto.main(x, y, turn, bkp)) { //���肪�u����
            eval--;
          }
        }
      }
    }
    return eval;
  }

  public static int open_value(int[] bkp, int turn) { //�J���x�v�Z
    //�Ԃ�l�F�������ꂽ�΂̎���̋󂫂̐�
    int eval = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if (bkp[10 * x + y] != turn && Piece[10 * x + y] == turn) {
          if (x > 1) {
            if (bkp[10 * (x - 1) + y] == 0) { //�������ꂽ������o
              eval++;
            }
          }
          if (x < 8) {
            if (bkp[10 * (x + 1) + y] == 0) {
              eval++;
            }
          }
          if (y > 1) {
            if (bkp[10 * x + y - 1] == 0) {
              eval++;
            }
          }
          if (y < 8) {
            if (bkp[10 * x + y + 1] == 0) {
              eval++;
            }
          }
          if (x > 1 && y > 1) {
            if (bkp[10 * (x - 1) + y - 1] == 0) {
              eval++;
            }
          }
          if (x < 8 && y > 1) {
            if (bkp[10 * (x + 1) + y - 1] == 0) {
              eval++;
            }
          }
          if (x > 1 && y < 8) {
            if (bkp[10 * (x - 1) + y + 1] == 0) {
              eval++;
            }
          }
          if (x < 8 && y < 8) {
            if (bkp[10 * (x + 1) + y + 1] == 0) {
              eval++;
            }
          }
        }
      }
    }
    return eval;
  }

  public static int around(int turn) { //����̐΂����Ȃ�����i�����萔�����Ȃ�����j
    //�Ԃ�l�F�����̐΂̎���̋󂫂̐��D
    int eval = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if (Piece[10 * x + y] == turn) {
          if (x > 1) {
            if (Piece[10 * (x - 1) + y] == 0) {
              eval++;
            }
          }
          if (x < 8) {
            if (Piece[10 * (x + 1) + y] == 0) {
              eval++;
            }
          }
          if (y > 1) {
            if (Piece[10 * x + y - 1] == 0) {
              eval++;
            }
          }
          if (y < 8) {
            if (Piece[10 * x + y + 1] == 0) {
              eval++;
            }
          }
          if (x > 1 && y > 1) {
            if (Piece[10 * (x - 1) + y - 1] == 0) {
              eval++;
            }
          }
          if (x < 8 && y > 1) {
            if (Piece[10 * (x + 1) + y - 1] == 0) {
              eval++;
            }
          }
          if (x > 1 && y < 8) {
            if (Piece[10 * (x - 1) + y + 1] == 0) {
              eval++;
            }
          }
          if (x < 8 && y < 8) {
            if (Piece[10 * (x + 1) + y + 1] == 0) {
              eval++;
            }
          }
        }
      }
    }
    return eval;
  }

  public static int count(int turn) { //���Ղ͐΂����Ȃ��ق����ǂ��Ƃ�����
    //�Ԃ�l�F�����̐΂̊���[%]
    int piece = 0;
    int all = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if (Piece[10 * x + y] == turn) {
          piece++;
        }
        all++;
      }
    }
    return (piece / all) * 100;
  }

  public static double place(int turn) { //�ꏊ�ɂ��d�݂��v�Z
    //�Ԃ�l�F�z��uvalue�v�̑����Z
    double eval = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if (Piece[10 * x + y] == turn) {
          eval += value[10 * x + y];
        }
        else if (Piece[10 * x + y] == turn * -1) {
          eval -= value[10 * x + y];
        }
      }
    }
    return eval;
  }

}
