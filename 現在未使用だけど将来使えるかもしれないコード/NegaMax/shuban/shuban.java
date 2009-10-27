package othello.AI.shuban;
import othello.AI.*;
//NegaMaxを試すも，失敗！！！
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
  static int saizente[][];
  static String saizente_hash[];
  static int kmax;
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
          saizente = new int[saishu_max + 1][3];
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
          System.out.println("最善手：" + saizente[1][0] + "," + saizente[1][1]
                           +"("+saizente[1][2]+")　turn:" + my_turn);
        //  System.out.println("深さ："+i+"の探索を完了！");
        //}

        //rondom();
        ai.sentaku[0]=saizente[1][0];
        ai.sentaku[1]=saizente[1][1];
        for(int i=1;i<=saishu_max;i++){
          System.out.println("深さ　"+i+"　の局面を　"+kyokumen_count[i]
                             +" (- "+ cut[i]+")　読みました");
        }
        ai.game();
    }
    public static int saiki(int hukasa  ,int end, int turn ,int isi ,int alpha, int beta){
      int t=0;
      int tmp_b[][] = new int[9][9];
      int hyoka=0;
      boolean check = false;
      int tmp=0;
      int w_isi=0;
      int b_isi=0;
      int max = -9999;
      int tyakushu = 0;
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
          if(rule.rule(i, j, turn)){
          tyakushu++;
          }
        }
      }
      if(hukasa > end){
        if(turn == 1){
          return w_isi - b_isi;
        }else{
          return b_isi - w_isi;
        }
      }
      if(tyakushu == 0){//パスチェックをしらべて，そうだったらパス
        return -1 * saiki(hukasa + 1, end, t , isi +1 , -beta , -alpha);
      }
      if((isi > 64) || (w_isi == 0 || b_isi == 0)){
        if(turn == 1){
          return w_isi - b_isi;
        }else{
          return b_isi - w_isi;
        }
      }

      //本探索
      for (int i = 1; i <= 8; i++) {
        for (int j = 1; j <= 8; j++) {
          if (rule.rule(i, j, turn)) { //ルールチェック
            for (int a = 1; a <= 8; a++) { //盤面の記憶
              for (int c = 1; c <= 8; c++) {
                tmp_b[a][c] = b[a][c];
              }
            }
            hikkuri.rule(i, j, turn); //ひっくり返し
            kyokumen_count[hukasa]++;
            tmp = -1 *
                (saiki(hukasa + 1, end, t, isi + 1 , -beta , -Math.max(alpha , max)));
            for (int a = 1; a <= 8; a++) { //盤面を元に戻す！
              for (int c = 1; c <= 8; c++) {
                b[a][c] = tmp_b[a][c];
              }
            }
            if (hukasa == 1) {
              System.out.println("座標:(" + i + "," + j +
                                 ")　評価値：" + tmp);
            }
            if (tmp >= beta) { //かっと
              cut[hukasa]++;
              return tmp;
            }
            if(max <= tmp){
              if(max == tmp){
                if(((int)(Math.random() * 3)) == 0){
                  saizente[hukasa][0] = i;
                  saizente[hukasa][1] = j;
                  saizente[hukasa][2] = tmp;
                }
              }else{
                saizente[hukasa][0] = i;
                saizente[hukasa][1] = j;
                saizente[hukasa][2] = tmp;
              }
              max = tmp;
            }
          }
        }
      }
              return max;
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

