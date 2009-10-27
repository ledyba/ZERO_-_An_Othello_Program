package othello.computer.opening;

import othello.*;

import othello.computer.*;
import java.util.*;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 * <p>説明: I want this program to be good at othello!</p>
 * <p>著作権: Copyright (c) 2003-2004 b7 Sun-Soft</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version Version 0.5
 */
//序盤は移植完了！もういじるな！

public class thinking {
  static int BestMove[] = new int[2];
  static double best_move_value = 0;
  static double[] important =
      //開放度，場所，周りの空きの多さ，石数の多さ，着手可能な場所
      {
      10, 1, 0.25, 1, 1};

  static int[] value = { //仮
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 45, -11, 4, -1, -1, 4, -11, 45, 0,
      0, -11, -16, -1, -3, -3, -1, -16, -11, 0,
      0, 4, -1, 2, -1, -1, 2, -1, 4, 0,
      0, -1, -3, -1, 0, 0, -1, -3, -1, 0,
      0, -1, -3, -1, 0, 0, -1, -3, -1, 0,
      0, 4, -1, 2, -1, -1, 2, -1, 4, 0,
      0, -11, -16, -1, -3, -3, -1, -16, -11, 0,
      0, 45, -11, 4, -1, -1, 4, -11, 45, 0, };

  static int master_depth = 7; //深さ
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
          System.out.print("●");
        }
        else if (Piece[10 * y + x] == -1) {
          System.out.print("○");
        }
        else {
          System.out.print("　");
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

//とりあえずNegaMax
  public static double run(boolean last, int depth, int turn, int turn_count,
                           int open_value, double alpha, double beta) {
    if (depth <= 0 || turn_count > 60) {
      return eval(open_value, turn, Piece);
    }
    else {
      int[] bkp = PieceBackup[depth-1];
      System.arraycopy(Piece,0,bkp,0,89);
      double s = 0;
      List MoveList = new ArrayList();//手のリスト化
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


      if (MoveList.size() == 0) { //パス処理
        if (last == false) { //前回もダメ＝ここは葉っぱ
          return eval(open_value, turn, Piece);
        }
        else {
          //両者打てないというのは完封の可能性もある．
          int[] piece_amount = new int[2];
          //[0]:自分 [1]:相手
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
          if (piece_amount[0] <= 0) { //自分が0
            return -9999;
          }
          else if (piece_amount[1] <= 0) { //相手0
            return 9999;
          }
          else { //それ以外は普通に評価値を返す
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

  public static double eval(int open_value, int turn, int[] p) { //評価値計算
    double eval = 0;
    //開放度，場所，周りの石，石の多さ
    eval =
        -important[0] * open_value //開放度
        + important[1] * place(turn) //場所
        - important[2] * around(turn) //周りの石
        - important[3] * count(turn) //石の多さ
        //+ important[4] * can_move_value(p,turn)//着手数
        ;
    return eval;
  }

  public static int can_move_value(int[] bkp, int turn) { //着手数計算
    //返り値：自分が置ける場所-相手が置ける場所
    int eval = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if (bkp[10 * x + y] == 0) { //何も場所であること．このチェックを入れれば速くなる？
          if (cpto.main(x, y, turn, bkp)) { //自分が置ける
            eval++;
          }
          else if (cpto.main(x, y, turn, bkp)) { //相手が置ける
            eval--;
          }
        }
      }
    }
    return eval;
  }

  public static int open_value(int[] bkp, int turn) { //開放度計算
    //返り値：動かされた石の周りの空きの数
    int eval = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if (bkp[10 * x + y] != turn && Piece[10 * x + y] == turn) {
          if (x > 1) {
            if (bkp[10 * (x - 1) + y] == 0) { //動かされた駒を検出
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

  public static int around(int turn) { //周りの石を少なくする（≒着手数を少なくする）
    //返り値：自分の石の周りの空きの数．
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

  public static int count(int turn) { //序盤は石が少ないほうが良いといわれる
    //返り値：自分の石の割合[%]
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

  public static double place(int turn) { //場所による重みを計算
    //返り値：配列「value」の足し算
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
