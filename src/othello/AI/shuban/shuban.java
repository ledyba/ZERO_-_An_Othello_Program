package othello.AI.shuban;
import othello.AI.*;

/**
 * <p>タイトル: ZERO</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003 RyoHirafuji</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
import othello.*;
public class shuban {
  static int saishu_max=0;
  static int kyokumen_count[];
  static int cut[];
  static int b[][] = new int[9][9];
  static int master[][] = new int[9][9];
  static int saizente[];
  static String saizente_hash[];
  static int kmax;
  static int saizen;
  static shuban_rule rule= new shuban_rule();
  static shuban_hikkuri hikkuri= new shuban_hikkuri();
  static int my_turn=ai.turn;
  public void shuban(int t , int hukasa) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
              b[i][j] = ai.b[i][j];
            }
          }
          saishu_max= hukasa;
          kyokumen_count = new int[saishu_max+1];
          cut = new int[saishu_max+1];
          saizente = new int[2];
          saizente_hash = new String[saishu_max+1];
        my_turn =t;
        for(int i=1;i<=saishu_max;i++){
          kyokumen_count[i]=0;
          cut[i]=0;
        }
        System.out.println("現局面の評価："+hyo(my_turn));
        //for(int i=1;i<=max;i++){
       //   kmax = i;
        //  for(int j=0;j<=i;j++){
        //    cut[j]=0;
        //    kyokumen_count[j] = 0;
        //  }
          saiki(1,saishu_max, my_turn, ai.isi , -9999 , 9999);
          System.out.println("最善手：" + saizente[0] + "," + saizente[1]
                           +"("+saizen+")　turn:" + my_turn);
        //  System.out.println("深さ："+i+"の探索を完了！");
        //}

        //rondom();
        ai.sentaku[0]=saizente[0];
        ai.sentaku[1]=saizente[1];
        for(int i=1;i<=saishu_max;i++){
          System.out.println("深さ　"+i+"　の局面を　"+kyokumen_count[i]
                             +" (- "+ cut[i]+")　読みました");
        }
        ai.game();
    }
    public static int saiki(int hukasa ,int end, int turn ,int isi , int alpha ,int beta){
      int t=0;
      int temp=0;
      int tmp_b[][] = new int[9][9];
      int hyoka=0;
      boolean check = false;
      int w_isi=0;
      int b_isi=0;
      boolean pass_check=false;
      switch(turn){
        case 1:
          t=0;
          break;
        case 0:
          t=1;
          break;
      }
      for(int i=1;i<=8;i++){//白い石と黒い石をしらべる
        for(int j=1;j<=8;j++){
          switch(b[i][j]){
            case 1:
              b_isi++;
              break;
            case 2:
              w_isi++;
              break;
          }
        }
      }
      if(hukasa > end){
        if(my_turn == 1){
          return w_isi - b_isi;
        }else{
          return b_isi - w_isi;
        }
      }
      if( turn==my_turn ){
        alpha = -9999;
      }else{
        beta = 9999;
      }
      if((isi > 64) || (w_isi == 0 || b_isi == 0)){
        if(my_turn == 1){
          return w_isi - b_isi;
        }else{
          return b_isi - w_isi;
        }
      }
        /*
        //ハッシュ生成
        hash = hash_seisei();
        if(hash .equals(saizente_hash[hukasa]) && kmax > 2){
        if( turn == my_turn ){
          alpha = saizente[hukasa][2];
         }else{
            beta = saizente[hukasa][2];
          }
        }*/
        for (int i = 1; i <= 8; i++) {
          for (int j = 1; j <= 8; j++) {
            for(int a=1;a<=8;a++){//盤面の記憶
              for(int c=1;c<=8;c++){
                tmp_b[a][c]=b[a][c];
              }
            }
            if(hikkuri.rule(i, j, turn)){//ひっくり返し＋ルールチェック
              pass_check=true;
              kyokumen_count[hukasa]++;
              temp=saiki(hukasa + 1, end, t, isi+1 , alpha , beta);
              //if(kmax == max && hukasa == 1){
                if(hukasa == 1){
                System.out.println("座標:("+i+","+j+")　評価値："+temp);
              }
              if( turn == my_turn ){
                if( temp >= alpha ){
                  if(hukasa == 1){
                    if (temp == alpha) {
                      if ( ( (int) (Math.random() * 3)) == 0) {
                        saizente[0] = i;
                        saizente[1] = j;
                        saizen = temp;
                      }
                    }
                    else {
                      saizente[0] = i;
                      saizente[1] = j;
                      saizen = temp;
                    }
                  }
                  alpha = temp;
                }
                if( alpha >= beta ){//β値がα値より小さい＝カット
                  cut[hukasa]++;
                  return alpha;

                }
                  }else{
                    if( temp <= beta ){
                      if(hukasa == 1){
                        if (temp == beta) {
                          if ( ( (int) (Math.random() * 3)) == 0) {
                            saizente[0] = i;
                            saizente[1] = j;
                            saizen = temp;

                          }
                        }
                        else {
                          saizente[0] = i;
                          saizente[1] = j;
                          saizen = temp;

                        }
                      }
                      beta = temp;
                    }
                        if( alpha >= beta ){//β値がα値より小さい＝カット
                          cut[hukasa]++;
                          return beta;
                        }
                  }
              for (int a = 1; a <= 8; a++) { //盤面を元に戻す！
                for (int c = 1; c <= 8; c++) {
                  b[a][c] = tmp_b[a][c];
                }
              }
            }
          }
        }
        if(pass_check == false){//パスチェックをしらべて，そうだったらパス
          return saiki(hukasa + 1,end, t , isi +1 , -9999, 9999);
        }
        if( turn == my_turn )return( alpha );
        else return( beta );
      }

      public static String hash_seisei(){
        String hash="";
          for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
              hash += String.valueOf(b[j][i]);
            }
          }
        return hash;
      }

  public static int hyo(int turn){
    int hyok=0;
    int my_koma=0;
    int tekikoma=0;
    switch (turn) {
      case 0:
        my_koma = 1;
        tekikoma = 2;
        break;
      case 1:
        my_koma = 2;
        tekikoma = 1;
        break;
    }
    for(int i=1;i<=8;i++){
      for(int j=1;j<=8;j++){
        if(b[i][j] == my_koma){
          hyok++;
        }else if(b[i][j] == tekikoma){
          hyok--;
        }
      }
    }
    return hyok;
  }
}

