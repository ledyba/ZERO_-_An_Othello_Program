package othello.computer.end;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 * <p>説明: I want this program to be good at othello!</p>
 * <p>著作権: Copyright (c) 2003-2004 by Sun-Soft</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version Version 0.5
 */

public class can_piece_turn_over {
  static int p[];
  static int x;
  static int y;
  public static boolean main(int cx,int cy,int t,int[] bkp) {
    boolean check = false;
    p=(int[])bkp.clone();
    x=cx;
    y=cy;
    if(p[10*x+y] == 0){//ズルはいけません(笑
      if (turn_x(8, 1, t)) //右
        check = true;
      if (turn_x(0, -1, t)) //左
        check = true;

      if (turn_y(8, 1, t)) //下
        check = true;
      if (turn_y(0, -1, t)) //上
        check = true;

      if (turn_naname(8, 8, 1, 1, t)) ///右下
        check = true;
      if (turn_naname(8, 0, 1, -1, t)) //右上
        check = true;
      if (turn_naname(0, 8, -1, 1, t)) //左下
        check = true;
      if (turn_naname(0, 0, -1, -1, t)) //左上
        check = true;
    }
    return check;
  }
  static boolean turn_x(int xn,int xh,int t){
    /*
     ・ひっくり返しチェッカー兼ひっくり返しルーチンX方向用
     ・xn   :xが増える方向の場合(要は右)の場合は8,それいがいは0
     ・xh   :xが増える場合(要は右)の場合はプラス１，へるのはマイナス１
     ・t    :そのターンの人の駒を表す数字(先手(黒)1，白-1，なしは0)
     ・t_img:そのターンの人の駒の画像．
     ※：単純な上下や斜めの場合は面倒なので別ルーチンを用いること
     */
    boolean check = false;
    int c = 0;
    int tmp = 0;
    if(x != xn){
      for(int i=1;i<=(xh*x*-1)+xn;i++){
        if (p[(x+(xh*i))*10+y] == t) {//自分の駒が発見されればBreak;
          tmp = i-1;
          c=i;
          check = true;//場合にもよるが，とりあえずひっくり返せる．
          break;
        }else if(p[(x+(xh*i))*10+y] == 0){
          //自分の駒が発見されずに
          //空白を検知したらひっくり返せない
          check = false;
          break;
        }
      }
      if(tmp == 0){//自分の駒が発見されても間が0なら隣り合っているだけなのでダメダメ．
        check = false;
      }
    }
    return check;

  }
  static boolean turn_y(int yn,int yh,int t){
    /*
     ・ひっくり返しチェッカー兼ひっくり返しルーチンY方向用
     ・yn   :yが増える方向の場合(要は下)の場合は8,それいがいは0
     ・yh   :が増える場合(要は下)の場合はプラス１，へるのはマイナス１
     ・t    :そのターンの人の駒を表す数字(先手(黒)1，白-1，なしは0)
     ・t_img:そのターンの人の駒の画像．
     ※：単純な左右や斜めの場合は面倒なので別ルーチンを用いること
     */
    boolean check = false;
    int c = 0;
    int tmp = 0;
    if(y != yn){
      for(int i=1;i<=(yh*y*-1)+yn;i++){
        if (p[(x*10+y+(yh*i))] == t) {//自分の駒が発見されればBreak;
          tmp = i-1;
          c=i;
          check = true;//場合にもよるが，とりあえずひっくり返せる．
          break;
        }else if(p[x*10+y+(yh*i)] == 0){
          //自分の駒が発見されずに
          //空白を検知したらひっくり返せない
          check = false;
          break;
        }
      }
      if(tmp == 0){//自分の駒が発見されても間が0なら隣り合っているだけなのでダメダメ．
        check = false;
      }
    }
    return check;
  }

  static boolean turn_naname(int xn,int yn,int xh,int yh,int t){
    /*
     ・ひっくり返しチェッカー兼ひっくり返しルーチン斜め用
     ・xn   :xが増える方向の場合(要は右)の場合は8,それいがいは0
     ・yn   :同上．要は下．
     ・xh   :xが増える場合(要は右)の場合はプラス１，へるのはマイナス１
     ・yh   :同上．下．
     ・t    :そのターンの人の駒を表す数字(先手(黒)1，白-1，なしは0)
     ・t_img:そのターンの人の駒の画像．
     ※：単純な上下右左の場合は面倒なので別ルーチンを用いること
     */
    boolean check = false;
    int c = 0;
    int tmp = 0;
    if(x != xn && y != yn){
      for(int i=1;i<=Math.min((xh*x*-1)+xn,(yh*y*-1)+yn);i++){
        if (p[(x+(xh*i))*10+(y+(yh*i))] == t) {//自分の駒が発見されればBreak;
          tmp = i-1;
          c=i;
          check = true;//場合にもよるが，とりあえずひっくり返せる．
          break;
        }else if(p[(x+(xh*i))*10+(y+(yh*i))] == 0){
          //自分の駒が発見されずに
          //空白を検知したらひっくり返せない
          check = false;
          break;
        }
      }
      if(tmp == 0){//自分の駒が発見されても間が0なら隣り合っているだけなのでダメダメ．
        check = false;
      }
    }

    return check;
  }

}
