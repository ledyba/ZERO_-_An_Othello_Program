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
  static int move[] = new int[2];
  static double best_move_value = 0;
  static int p[];
  static int master_depth = 12;//深さ
  static int count[][] = new int[master_depth + 1][2];
  static othello.computer.end.piece_turn_over pto = new othello.computer.end.piece_turn_over();
  static othello.computer.end.can_piece_turn_over cpto = new othello.computer.end.can_piece_turn_over();
  public static int[] start(int turn,int turn_count) {
    System.out.println("\n(end) thinking start...");
    p=(int[])MainFrame.p.clone();
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
        if(p[10*y+x]==1){
          System.out.print("●");
        }else if(p[10*y+x]==-1){
          System.out.print("○");
        }else{
          System.out.print("　");
        }
      }
      System.out.println("");
    }

    run(true,master_depth,turn,turn_count,-9999,9999);
    System.out.println("\nBest Move ("+move[0]+","+move[1]+"):"+best_move_value+"\n");
    for(int i=master_depth;i>0;i--){
      System.out.println((master_depth-i+1)+":"+count[i][0]+"(- "+count[i][1]+")");
      count[i][0]=0;
      count[i][1]=0;
    }
    return move;
  }
//とりあえずNegaScout
  public static int run(boolean last,int depth , int turn ,int turn_count , int alpha, int beta){
    if(depth <= 0 || turn_count > 60){
      return eval(turn);
    }else{
      int[] bkp = (int[])p.clone(); //局面のバックアップ
      int move_count = 0;
      int a = alpha;
      int b = alpha+1;
      int s = 0;
      int min = -9999;
      for (int x = 1; x <= 8; x++) {
        for (int y = 1; y <= 8; y++) {
            if(pto.main(x,y,turn)){
              s = -1 * run(true,depth - 1, turn * -1, turn_count + 1,-b, -a);
              count[depth][0]++;
              move_count++;
                if (s > a && s < b && move_count > 1 ) {
                  s = -1 * run(true, depth - 1, turn * -1, turn_count + 1, -beta, -s);
                }
                p = (int[])bkp.clone(); //戻す
                if(s > min){
                  if (depth == master_depth) {
                    //System.out.println("x:"+x+" y:"+y+" eval:"+s);
                    move[0] = x;
                    move[1] = y;
                    best_move_value=s;
                  }
                  min = s;
                }
              if(min >= beta){
                count[depth][1]++;
                return min;
              }
              if (depth == master_depth){
                System.out.println("x:" + x + " y:" + y + " eval:" + s);
              }
              a = min;
              b = a+1;
            }
        }
      }
      if(move_count == 0){//パス処理
        if(last == false){//前回もダメ＝ここは葉っぱ
            return eval(turn);
        }else{
          return -1 * run(false,depth - 1, turn * -1 , turn_count, -beta, -alpha);
        }
      }
      return min;
    }
  }


  public static int eval(int turn){
    int eval = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if(p[10*x+y] == turn){
          eval++;
        }else if(p[10*x+y] == turn * -1){
          eval--;
        }
      }
    }
    return eval;
  }
}