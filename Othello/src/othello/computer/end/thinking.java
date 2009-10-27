package othello.computer.end;
import othello.*;
/**
 * <p>タイトル: Othello Program "ZERO"</p>
 * <p>説明: I want this program to be good at othello!</p>
 * <p>著作権: Copyright (c) 2003-2004 by Sun-Soft</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version Version 0.5
 */

//移植完了！いじくるな！

public class thinking {
  static int BestMove[] = new int[2];
  static double best_move_value = 0;
  static final int master_depth = 14;//深さ
  static final int Piece[] = new int[89];
  static final int PieceBackup[][] = new int[master_depth][89];
  static int count[][] = new int[master_depth + 1][2];
  static othello.computer.end.piece_turn_over pto = new othello.computer.end.piece_turn_over();
  static othello.computer.end.can_piece_turn_over cpto = new othello.computer.end.can_piece_turn_over();
  public static int[] start(int turn,int turn_count) {
    System.out.println("\n(end) thinking start...");
    System.arraycopy(MainFrame.p,0,Piece,0,89);
    System.out.println("--------------------");
    System.out.print("Turn:");
    if(turn == 1){
      System.out.println("Black,"+turn_count);
    }else{
      System.out.println("White,"+turn_count);
    }
    System.out.println("--------------------");
     for(int x=1;x<=8;x++){
      for(int y=1;y<=8;y++){
        if(Piece[10*y+x]==1){
          System.out.print("●");
        }else if(Piece[10*y+x]==-1){
          System.out.print("○");
        }else{
          System.out.print("　");
        }
      }
      System.out.println("");
    }

    run(true,master_depth,turn,turn_count,-9999,9999);
    System.out.println("\nBest Move "+MainFrame.kifu_table[BestMove[1]*10+BestMove[0]]+" : "+best_move_value+"\n");
    for(int i=master_depth;i>0;i--){
      System.out.println((master_depth-i+1)+":"+count[i][0]+"(- "+count[i][1]+")");
      count[i][0]=0;
      count[i][1]=0;
    }
    return BestMove;
  }
//とりあえずNegaMax
  public static double run(boolean last,int depth , int turn ,int turn_count , double alpha, double beta){
    if(depth <= 0 || turn_count > 60){
      return eval(turn);
    }else{
      int[] bkp = PieceBackup[depth-1];
      System.arraycopy(Piece,0,bkp,0,89);
      int move_count = 0;
      double s = 0; //NegaMaxの要．
      for (int x = 1; x <= 8; x++) {
        for (int y = 1; y <= 8; y++) {
            if(pto.main(x,y,turn)){
              s = -1 * run(true,depth - 1, turn * -1, turn_count + 1, -beta, -alpha);
              count[depth][0]++;
              move_count++;
              System.arraycopy(bkp,0,Piece,0,89);
              if(depth == master_depth){
                System.out.println(MainFrame.kifu_table[y*10+x]+" eval:"+s);
              }
              if(s >= beta){
                count[depth][1]++;
                return s;
              }else{
                if (s > alpha) {
                  if (depth == master_depth) {
                    BestMove[0] = x;
                    BestMove[1] = y;
                    best_move_value = s;
                  }
                  alpha = s;
                }
              }
            }
        }
      }
      if(move_count == 0){//パス処理
        if(last == false){//前回もダメ＝ここは葉っぱ
          return eval(turn);
        }else{
          return -1 * run(false,depth, turn * -1 , turn_count, -beta, -alpha);
        }
      }
      return alpha;
    }
  }

  public static double eval(int turn){
    int eval = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if(Piece[10*x+y] == turn){
          eval++;
        }else if(Piece[10*x+y] == turn * -1){
          eval--;
        }
      }
    }
    return eval;
  }
}
