package othello.computer;

import othello.piece_turn_over.*;
import othello.*;
/**
 * <p>タイトル: Othello Program "ZERO"</p>
 * <p>説明: I want this program to be good at othello!</p>
 * <p>著作権: Copyright (c) 2003-2004 by Sun-Soft</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version Version 0.5
 */

public class computer extends Thread{
  int turn;
  int turn_count;
  int move[] = new int[2];
  piece_turn_over pto = new piece_turn_over();//ひっくり返しチェック　＋ひっくり返し
  public void start(int tmp_turn,int tmp_turn_count) {
    turn = tmp_turn;
    turn_count=tmp_turn_count+1;
    super.start();
  }
  public void run() {//各処理は本当はスレッドじゃありません(^^;このクラスはスレッドとして動きます．
    if(turn_count <= 13){//序盤
      othello.computer.opening.thinking thread = new othello.computer.opening.thinking();
      move=thread.start(turn,turn_count);
    }else if(turn_count > 13 && turn_count <= 46){//中盤
      othello.computer.middle.thinking thread = new othello.computer.middle.thinking();
      move=thread.start(turn,turn_count);
    }else{//終盤
      othello.computer.end.thinking thread = new othello.computer.end.thinking();
      move=thread.start(turn,turn_count);
    }

    //思考開始後はひっくり返してコントロールして終了．
    int x = move[0];
    int y = move[1];
    if(turn > 0){
      if (pto.main(x, y, turn, MainFrame.black_img)) {
        MainFrame.game_control(10*y+x);
        return;
      }
    }else if(turn < 0){
      if (pto.main(x, y, turn,MainFrame.white_img)) {
        MainFrame.game_control(10*y+x);
        return;
      }
    }
    System.out.println("ERROR!");//だってひっくり返されてないんだもんねぇ・・・
  }
}
