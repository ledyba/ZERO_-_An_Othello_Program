package othello.AI.tyuban;
import othello.*;
import othello.AI.*;
//scout法じゃないけどさ．
//なんでまちがえたんだろう
/**
 * <p>タイトル: ZERO</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003 RyoHirafuji</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class tujo {
  static int saishu_max  =   0;//探索の深さ
  static final double edakari_hyoka = 0;//前向き枝刈りする際の差
  static int teki_turn;
  static int tekikoma;
  static int my_koma;
  static int kyokumen_count[];
  static int cut[];
  static String saizente_hash[];
  static int b[][] = new int[9][9];
  static int saizente[] = new int[2];
  static double saizen=0;
  static int saidai_max=0;
  static int my_turn=ai.turn;
  static int[][] hyoka_value = {
      { 45,-11,  4, -1, -1,  4,-11, 45,},
      {-11,-16, -1, -3, -3, -1,-16,-11,},
      {  4, -1,  2, -1, -1,  2, -1,  4,},
      { -1, -3, -1,  0,  0, -1, -3, -1,},
      { -1, -3, -1,  0,  0, -1, -3, -1,},
      {  4, -1,  2, -1, -1,  2, -1,  4,},
      {-11,-16, -1, -3, -3, -1,-16,-11,},
      { 45,-11,  4, -1, -1,  4,-11, 45,}};
  static double[][] hyoka_omosa = {//評価値の重さ[深さ][値の種類]
      //ひとつづき，確定石，場所，着手可能な場所，ウイング，開放度 ,
      //ライン3*3 , ライン4*4 , ライン5*5 , ライン6*6 , ライン7*7 ,ライン8*8 ,周りの石
      { 1, 10,  1,  1, 10,  1,
      3,  3,  3,  3,  3,  3, 0.25,},
  };

  public tujo(){
  }
public void start(int t , int hukasa) {
      for (int i = 1; i <= 8; i++) {
          for (int j = 1; j <= 8; j++) {
            b[i][j] = ai.b[i][j];
          }
        }
        saishu_max= hukasa;
        kyokumen_count = new int[saishu_max+1];
        cut = new int[saishu_max+1];
        saizente_hash = new String[saishu_max+1];
      my_turn =t;
      switch(my_turn){
        case 0:
          my_koma = 1;
          tekikoma = 2;
          teki_turn = 1;
          break;
        case 1:
          my_koma = 2;
          tekikoma = 1;
          teki_turn = 0;
          break;
      }
      for(int i=1;i<=saishu_max;i++){
        kyokumen_count[i]=0;
        cut[i]=0;
      }
      System.out.println("現局面の評価："+hyo(my_turn , Frame.isi-3 , kaihoudo(b ,my_turn)));
      //for(int i=1;i<=max;i++){
     //   kmax = i;
      //  for(int j=0;j<=i;j++){
      //    cut[j]=0;
      //    kyokumen_count[j] = 0;
      //  }
        saiki(1 , saishu_max, my_turn, ai.isi, -99999, 99999 ,0);
        System.out.println("最善手：" + saizente[0] + "," + saizente[1]
                         +"("+saizen+")　turn:" + my_turn);
      //  System.out.println("深さ："+i+"の探索を完了！");
      //}
      //rondom();
      /*
      System.out.println("\n最善手順");
      for(int i=1;i<=saishu_max;i++){
        System.out.println("("+saizente[i][0]+","+saizente[i][1]+"):"+saizente[i][2]);
      }
*/
      System.out.println("");
      ai.sentaku[0]=saizente[0];
      ai.sentaku[1]=saizente[1];
      for(int i=1;i<=saishu_max;i++){
        System.out.println("深さ　"+i+"　の局面を　"+kyokumen_count[i]
                           +" (- "+ cut[i]+")　読みました");
      }
      ai.game();
  }
      public static double saiki(int hukasa  ,int max, int turn ,int isi , double alpha ,double beta ,int kaihoudo){
        int t=0;
        int tmp_b[][] = new int[9][9];
        double hyoka=0;
        boolean check = false;
        int scout[][];
        int scout_count = 0;
        double tmp;
        int w_isi=0;
        int b_isi=0;
        int tyakushu = 0;
        boolean pass_check=false;
        switch(turn){
          case 1:
            t=0;
            break;
          case 0:
            t=1;
            break;
        }
        if(hukasa > max){
          return hyo(my_turn , isi -3 , kaihoudo);
        }
        if( turn==my_turn ){
          alpha = -9999;
        }else{
          beta = 9999;
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
        scout = new int[tyakushu][3];
        if((isi > 64) || (w_isi == 0 || b_isi == 0)){
          if(w_isi == 0 && my_turn == 0){
            return 9999;
          }else if(b_isi == 0 && my_turn == 0){
            return -9999;
          }else if(w_isi == 0 && my_turn == 1){
            return -9999;
          }else if(b_isi == 0 && my_turn == 1){
            return 9999;
          }else{
            if(my_turn == 1){
              if (w_isi > b_isi) {
                return 9999;
              }else{
                return -9999;
              }
            }else{
              if (w_isi < b_isi) {
                return 9999;
              }else{
                return -9999;
              }
            }
          }
        }
        //偵察探索
        for (int i = 1; i <= 8; i++) {
          for (int j = 1; j <= 8; j++) {
            for(int a=1;a<=8;a++){//盤面の記憶
              for(int c=1;c<=8;c++){
                tmp_b[a][c]=b[a][c];
              }
            }
            if(hikkuri.rule(i, j, turn)){//ひっくり返し＋ルールチェック
              pass_check=true;
              scout[scout_count][0] = i;
              scout[scout_count][1] = j;
              scout[scout_count][2]=(int)scout(t);
              scout_count++;
              for (int a = 1; a <= 8; a++) { //盤面を元に戻す！
                for (int c = 1; c <= 8; c++) {
                  b[a][c] = tmp_b[a][c];
                }
              }
            }
          }
        }
        if(pass_check == false){//パスチェックをしらべて，そうだったらパス
          return saiki(hukasa + 1, max, t , isi +1 , -9999, 9999 , kaihoudo);
        }
        //偵察結果のソート
        int tmp_sasite[] = new int[3];
        for(int i=0;i<tyakushu-1;i++){
          for(int j=i+1;j<tyakushu;j++){
            if(scout[j][2] > scout[i][2]){
              tmp_sasite  = scout[j];
              scout[j]=scout[i];
              scout[i] = tmp_sasite;
            }
          }
        }
        if(turn != my_turn){
          int tmp_s[][] = new int[tyakushu][2];
          for(int i=0;i < tyakushu ;i++){
            tmp_s[i] = scout[tyakushu - i -1];
          }
          for(int i=0;i < tyakushu ;i++){
            scout[i] = tmp_s[i];
          }
        }
        /*
    for(int i=0;i<tyakushu;i++){
          System.out.println("scout["+i+"] ("+scout[i][0]+","+scout[i][1]+"):"+scout[i][2]);
        }*/
        //本探索
        for (int i = 0; i < tyakushu; i++) {
          for(int a=1;a<=8;a++){//盤面の記憶
            for(int c=1;c<=8;c++){
              tmp_b[a][c]=b[a][c];
            }
          }
          if(hikkuri.rule(scout[i][0], scout[i][1], turn)){//ひっくり返し＋ルールチェック
            kyokumen_count[hukasa]++;
            tmp=saiki(hukasa + 1 ,max, t, isi+1 , alpha , beta , kaihoudo);
            if(hukasa == 1){
              System.out.println("座標:("+scout[i][0]+","+scout[i][1]+")　評価値："+tmp);
            }
            if( turn == my_turn ){
              if( tmp >= alpha ){
                if(hukasa == 1){
                  if (tmp == alpha) {
                    if ( ( (int) (Math.random() * 3)) == 0) {
                      saizente[0] = scout[i][0];
                      saizente[1] = scout[i][1];
                      saizen = tmp;
                    }
                  }
                  else {
                    saizente[0] = scout[i][0];
                    saizente[1] = scout[i][1];
                    saizen = tmp;
                  }
                }
                alpha = tmp;
              }
              if( alpha >= beta ){//β値がα値より小さい＝カット
                cut[hukasa]++;
                return alpha;

              }
                }else{
                  if( tmp <= beta ){
                    if(hukasa == 1){
                      if (tmp == beta) {
                        if ( ( (int) (Math.random() * 3)) == 0) {
                          saizente[0] = scout[i][0];
                          saizente[1] = scout[i][1];
                          saizen = tmp;

                        }
                      }
                      else {
                        saizente[0] = scout[i][0];
                        saizente[1] = scout[i][1];
                        saizen = tmp;

                      }
                    }
                    beta = tmp;
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
            if( turn == my_turn )return( alpha );
          else return( beta );
          }


      public static double scout(int turn){
        int t=0;
        double value;
        double temp=0;
        int tmp_b[][] = new int[9][9];
        double hyoka=0;

        switch(turn){
          case 1:
            t=0;
            break;
          case 0:
            t=1;
            break;
        }
        if( turn==my_turn ){
          value = -9999;
        }else{
          value = 9999;
        }
          for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
              for(int a=1;a<=8;a++){//盤面の記憶
                for(int c=1;c<=8;c++){
                  tmp_b[a][c]=b[a][c];
                }
              }
              if(hikkuri.rule(i, j, turn)){//ひっくり返し＋ルールチェック
                temp=hyoka_omosa[0][2] * value_place()+hyoka_omosa[0][5] * kaihoudo(tmp_b,turn);

                if( turn == my_turn ){
                    if( temp >= value ){
                        value = temp;
                      }
                }else{
                    if( temp <= value ){
                        value = temp;
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
          return( value );
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


      public static double hyo(int turn , int hukasa ,int kaihoudo){
        double hyok=sekisuu();
        //評価値の重さ[深さ][値の種類]
        //ひとつづき，確定石，場所，着手場所，ウイング，開放度
        if(hyok == 0){
          hyok = 0
              //+ hyoka_omosa[hukasa][0]*retu_gyo_value()
              + hyoka_omosa[0][1] * kakuteiseki()
              + hyoka_omosa[0][2] * value_place()
              + hyoka_omosa[0][3] * tyakushu()//開放度理論を投入すればたぶん，必要ない
              + hyoka_omosa[0][5] * kaihoudo
              + hyoka_omosa[0][4] * (yama() - wing())
              + hyoka_omosa[0][12] * mawari(turn)
              //+ hyoka_omosa[0][6] * naname1()
              //+ hyoka_omosa[0][7] * naname2()
              //+ hyoka_omosa[0][8] * naname3()
              //+ hyoka_omosa[0][9] * naname4()
              //+ hyoka_omosa[0][10] * naname5()
              //+ hyoka_omosa[0][11] * naname6()
              ;
      }
      return hyok;
    }
    public static int mawari(int turn){
      int count = 0;
      int hyoka=0;
      for (int i = 1; i <= 8; i++) {
        for (int j = 1; j <= 8; j++) {
          if(b[i][j] > 0){
            count = 0;
            if (i > 1) {
              if (b[i - 1][j] == 0) {
                count++;
              }
            }
            if (i < 8) {
              if (b[i + 1][j] == 0) {
                count++;
              }
            }
            if (j > 1) {
              if (b[i][j - 1] == 0) {
                count++;
              }
            }
            if (j < 8) {
              if (b[i][j + 1] == 0) {
                count++;
              }
            }
            if (i > 1 && j < 8) {
              if (b[i - 1][j + 1] == 0) {
                count++;
              }
            }
            if (i > 1 && j > 1) {
              if (b[i - 1][j - 1] == 0) {
                count++;
              }
            }
            if (i < 8 && j < 8) {
              if (b[i + 1][j + 1] == 0) {
                count++;
              }
            }
            if (i < 8 && j > 1) {
              if (b[i + 1][j - 1] == 0) {
                count++;
              }
            }
            if(b[i][j] == my_koma){
              hyoka -= count;
            }else if(b[i][j] == tekikoma){
              hyoka += count;
            }
          }
        }
      }
      return hyoka;
    }
    public static int sekisuu(){//もちろん，自分の石数が0だったら評価値は-9999,相手だったら+9999
      int my_isi=0;
      int teki_isi=0;
      for(int i=1;i<=8;i++){
        for(int j=1;j<=8;j++){
          if(b[i][j] == my_koma){
            my_isi--;
          }else if(b[i][j] == tekikoma){
            teki_isi++;
          }
        }
      }
      if(my_isi==0){
        return -9999;
      }else if(teki_isi==0){
        return 9999;
      }else{
        return 0;
      }
    }
    public static int hyper_gusu(){
    int hyoka = 0;
    return hyoka;
    }

    public static int wing(){
    int hyoka = 0;
    boolean check = false;
    if((b[8][1] == 0 && b[1][1] == 0 ) && (b[7][1] == 0 ^ b[2][1] == 0)){//上の辺
      check = true;
      for (int i = 3; i <= 6; i++) {
        if (b[i][1] != my_koma) {
          check = false;
        }
      }
      if (check) {
        hyoka++;
      }
    }
      if(b[8][8] == 0 && b[1][8] == 0 && (b[7][8] == 0 ^ b[2][8] == 0)){//下の辺
        check = true;
        for (int i = 3; i <= 6; i++) {
          if (b[i][8] != my_koma) {
            check = false;
          }
        }
        if (check) {
          hyoka++;
        }
      }

      if(b[8][1] == 0 && b[8][8] == 0 && (b[8][2] == 0 ^ b[8][7] == 0)){
        check = true;
        for (int i = 3; i <= 6; i++) {
          if (b[8][i] != my_koma) {
            check = false;
          }
        }
        if (check) {
          hyoka++;
        }
      }
      if(b[1][1] == 0 && b[1][8] == 0 && (b[1][2] == 0 ^ b[1][7] == 0)){
        check = true;
        for (int i = 3; i <= 6; i++) {
          if (b[1][i] != my_koma) {
            check = false;
          }
        }
        if (check) {
          hyoka++;
        }
      }
      if((b[8][1] == 0 && b[1][1] == 0 ) && (b[7][1] == 0 ^ b[2][1] == 0)){//上の辺
        check = true;
        for (int i = 3; i <= 6; i++) {
          if (b[i][1] != tekikoma) {
            check = false;
          }
        }
        if (check){
          hyoka--;
        }
      }
        if(b[8][8] == 0 && b[1][8] == 0 && (b[7][8] == 0 ^ b[2][8] == 0)){//下の辺
          check = true;
          for (int i = 3; i <= 6; i++) {
            if (b[i][8] != tekikoma) {
              check = false;
            }
          }
          if (check) {
            hyoka--;
          }
        }

        if(b[8][1] == 0 && b[8][8] == 0 && (b[8][2] == 0 ^ b[8][7] == 0)){
          check = true;
          for (int i = 3; i <= 6; i++) {
            if (b[8][i] != tekikoma) {
              check = false;
            }
          }
          if (check) {
            hyoka--;
          }
        }
        if(b[1][1] == 0 && b[1][8] == 0 && (b[1][2] == 0 ^ b[1][7] == 0)){
          check = true;
          for (int i = 3; i <= 6; i++) {
            if (b[1][i] != tekikoma) {
              check = false;
            }
          }
          if (check) {
            hyoka--;
          }
        }
    return hyoka;
    }
    public static int yama(){
      boolean check = false;
      int hyoka = 0;
      //まず自分のターンから
    if(b[8][1] == 0 && b[1][1] == 0){//上の辺
      check = true;
      for (int i = 2; i <= 7; i++) {
        if (b[i][1] != my_koma) {
          check = false;
        }
      }
      if (check) {
        hyoka++;
      }
    }
      if(b[8][8] == 0 && b[1][8] == 0){//下の辺
        check = true;
        for (int i = 2; i <= 7; i++) {
          if (b[i][8] != my_koma) {
            check = false;
          }
        }
        if (check) {
          hyoka++;
        }
      }

      if(b[8][1] == 0 && b[8][8] == 0){
        check = true;
        for (int i = 2; i <= 7; i++) {
          if (b[8][i] != my_koma) {
            check = false;
          }
        }
        if (check) {
          hyoka++;
        }
      }
      if(b[1][1] == 0 && b[1][8] == 0){
        check = true;
        for (int i = 2; i <= 7; i++) {
          if (b[1][i] != my_koma) {
            check = false;
          }
        }
        if (check) {
          hyoka++;
        }
      }
      //次に相手のターン
      if(b[8][1] == 0 && b[1][1] == 0){//上の辺
        check = true;
        for (int i = 2; i <= 7; i++) {
          if (b[i][1] != tekikoma) {
            check = false;
          }
        }
        if (check) {
          hyoka--;
        }
      }
        if(b[8][8] == 0 && b[1][8] == 0){//下の辺
          check = true;
          for (int i = 2; i <= 7; i++) {
            if (b[i][8] != tekikoma) {
              check = false;
            }
          }
          if (check) {
            hyoka--;
          }
        }

        if(b[8][1] == 0 && b[8][8] == 0){
          check = true;
          for (int i = 2; i <= 7; i++) {
            if (b[8][i] != tekikoma) {
              check = false;
            }
          }
          if (check) {
            hyoka--;
          }
        }
        if(b[1][1] == 0 && b[1][8] == 0){
          check = true;
          for (int i = 2; i <= 7; i++) {
            if (b[1][i] != tekikoma) {
              check = false;
            }
          }
          if (check) {
            hyoka--;
          }
        }
    return hyoka;
    }
    public static int naname5(){//斜めのライン(8*8)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //自分が作っている
      for(int i=0;i<=7;i++){
        if(b[8-i][1+i] == my_koma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=7;i++){//左下
        if(b[1+i][8-i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }

        count = 0;
      //敵が作っている
      for(int i=0;i<=7;i++){
        if(b[8-i][1+i] == tekikoma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=7;i++){//左下
        if(b[1+i][8-i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      hyoka=my_hyoka-teki_hyoka;
    return hyoka;
    }

    public static int naname6(){//斜めのライン(7*7)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //自分が作っている
      for(int i=0;i<=6;i++){
        if(b[7-i][1+i] == my_koma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=6;i++){//左下
        if(b[2+i][8-i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=6;i++){//左上
        if(b[2+i][1+i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=6;i++){
        if(b[1+i][2+i] == my_koma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      //敵が作っている
      for(int i=0;i<=6;i++){
        if(b[7-i][1+i] == tekikoma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=6;i++){//左下
        if(b[2+i][8-i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=6;i++){//左上
        if(b[2+i][1+i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=6;i++){
        if(b[1+i][2+i] == tekikoma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      hyoka=my_hyoka-teki_hyoka;
    return hyoka;
    }

    public static int naname4(){//斜めのライン(6*6)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //自分が作っている
      for(int i=0;i<=5;i++){
        if(b[6-i][1+i] == my_koma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=5;i++){//左下
        if(b[3+i][8-i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=5;i++){//左上
        if(b[3+i][1+i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=5;i++){
        if(b[1+i][3+i] == my_koma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      //敵が作っている
      for(int i=0;i<=5;i++){
        if(b[6-i][1+i] == tekikoma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=5;i++){//左下
        if(b[3+i][8-i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=5;i++){//左上
        if(b[3+i][1+i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=5;i++){
        if(b[1+i][3+i] == tekikoma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      hyoka=my_hyoka-teki_hyoka;
    return hyoka;
    }
    public static int naname3(){//斜めのライン(5*5)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //自分が作っている
      for(int i=0;i<=4;i++){
        if(b[5-i][1+i] == my_koma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=4;i++){//左下
        if(b[4+i][8-i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=4;i++){//左上
        if(b[4+i][1+i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=4;i++){
        if(b[1+i][4+i] == my_koma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      //敵が作っている
      for(int i=0;i<=4;i++){
        if(b[5-i][1+i] == tekikoma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=4;i++){//左下
        if(b[4+i][8-i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=4;i++){//左上
        if(b[4+i][1+i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=4;i++){
        if(b[1+i][4+i] == tekikoma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      hyoka=my_hyoka-teki_hyoka;
    return hyoka;
    }
    public static int naname2(){//斜めのライン(4*4)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //自分が作っている
      for(int i=0;i<=3;i++){
        if(b[4-i][1+i] == my_koma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=3;i++){//左下
        if(b[5+i][8-i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=3;i++){//左上
        if(b[5+i][1+i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=3;i++){
        if(b[1+i][5+i] == my_koma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      //敵が作っている
      for(int i=0;i<=3;i++){
        if(b[4-i][1+i] == tekikoma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=3;i++){//左下
        if(b[5+i][8-i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=3;i++){//左上
        if(b[5+i][1+i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=3;i++){
        if(b[1+i][5+i] == tekikoma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      hyoka=my_hyoka-teki_hyoka;
    return hyoka;
    }

    public static int naname1(){//斜めのライン(3*3)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //自分が作っている
      for(int i=0;i<=2;i++){
        if(b[3-i][1+i] == my_koma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=2;i++){//左下
        if(b[6+i][8-i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=2;i++){//左上
        if(b[6+i][1+i] == my_koma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      for(int i=0;i<=2;i++){
        if(b[1+i][6+i] == my_koma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        my_hyoka++;
      }
      count=0;
      //敵が作っている
      for(int i=0;i<=2;i++){
        if(b[3-i][1+i] == tekikoma){//右上
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=2;i++){//左下
        if(b[6+i][8-i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=2;i++){//左上
        if(b[6+i][1+i] == tekikoma){
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      for(int i=0;i<=2;i++){
        if(b[1+i][6+i] == tekikoma){//右下
          count++;
        }else{
          count = 0;
          break;
        }
      }
      if(count != 0){
        teki_hyoka++;
      }
      count=0;
      hyoka=my_hyoka-teki_hyoka;
    return hyoka;
    }
    public static int retu_gyo_value(){
      int hyok=0;
      int count[]=new int[2];
      count[0]=0;
      count[0]=0;
      boolean check_gyo=true;
      boolean check_retu=true;

      for(int i=1;i<=8;i++){//1列・行のうち全部自分の駒だったら(ただし一続き！)
        if(b[i][1] == my_koma && check_gyo==true){//行
          count[0]++;
        }else if( count[0] > 0&&b[i][1] != my_koma){
          check_gyo=false;
          count[0] =0;
        }
        if(b[i][1] == my_koma && check_retu==true){//列
          count[1]++;
        }else if(count[0] > 0&& b[i][1] != my_koma){
          check_gyo=false;
          count[1] =0;
        }
        hyok += (count[0]+count[1]);
      }

      count[0]=0; count[1]=0;
    check_gyo=true;
    check_retu=true;
    for(int i=1;i<=8;i++){//1列・行のうち全部敵の駒だったら(ただし一続き！)
      if(b[i][1] == tekikoma && check_gyo==true){//行
          count[0]++;
      }else if( count[0] > 0&&b[i][1] != tekikoma){
        check_gyo=false;
        count[0] =0;
      }
      if(b[i][1] == tekikoma && check_retu==true){//列
        count[1]++;
      }else if(count[0] > 0&& b[i][1] != tekikoma){
        check_gyo=false;
        count[1] =0;
      }
      hyok -= (count[0]+count[1]);

    }
    return hyok;
    }
    public static int kakuteiseki(){
      int hyok=0;
      //確定石探索(ただしすみの行・列がすべて自分の駒だとポイント２倍！)
      for(int i=1;i<=8;i++){
        if(b[i][1] == my_koma){
          hyok++;
        }else{
          break;
        }
      }
      for(int i=8;i>=1;i--){
        if(b[i][1] == my_koma){
          hyok++;
        }else{
          break;
        }
      }
      //
      for(int i=1;i<=8;i++){
        if(b[i][8] == my_koma){
          hyok++;
        }else{
          break;
        }
      }
      for(int i=8;i>=1;i--){
        if(b[i][8] == my_koma){
          hyok++;
        }else{
          break;
        }
      }
      //
      for(int i=1;i<=8;i++){
        if(b[1][i] == my_koma){
          hyok++;
        }else{
          break;
        }
      }
      for(int i=8;i>=1;i--){
        if(b[1][i] == my_koma){
          hyok++;
        }else{
          break;
        }
      }
      for(int i=1;i<=8;i++){
        if(b[8][i] == my_koma){
          hyok++;
        }else{
          break;
        }
      }
      for(int i=8;i>=1;i--){
        if(b[8][i] == my_koma){
          hyok++;
        }else{
          break;
        }
      }
      //敵
      for(int i=1;i<=8;i++){
        if(b[i][1] == tekikoma){
          hyok--;
        }else{
          break;
        }
      }
      for(int i=8;i>=1;i--){
        if(b[i][1] == tekikoma){
          hyok--;
        }else{
          break;
        }
      }
      //
      for(int i=1;i<=8;i++){
        if(b[i][8] == tekikoma){
          hyok--;
        }else{
          break;
        }
      }
      for(int i=8;i>=1;i--){
        if(b[i][8] == tekikoma){
          hyok--;
        }else{
          break;
        }
      }
      //
      for(int i=1;i<=8;i++){
        if(b[1][i] == tekikoma){
          hyok--;
        }else{
          break;
        }
      }
      for(int i=8;i>=1;i--){
        if(b[1][i] == tekikoma){
          hyok--;
        }else{
          break;
        }
      }
      for(int i=1;i<=8;i++){
        if(b[8][i] == tekikoma){
          hyok--;
        }else{
          break;
        }
      }
      for(int i=8;i>=1;i--){
        if(b[8][i] == tekikoma){
          hyok--;
        }else{
          break;
        }
      }
      //ほかの確定石も含める(こうしないと辺がごっそりとられても評価が低くなる)
      //これによって一列すべて自分の駒だとポイントは3倍になる
      int hyoka = 0;
      boolean check[] = new boolean[4];
      check[0]=true;
      check[1]=true;
      check[2]=true;
      check[3]=true;
      for(int i=1;i<=8;i++){
        if(check[0] && b[1][i] == 0){
          check[0]=false;
        }
        if(check[1] && b[8][i] == 0){
          check[1]=false;
        }
        if(check[2] && b[i][1] == 0){
          check[2]=false;
        }
        if(check[3] && b[i][8] == 0){
          check[3]=false;
        }
      }
      if(check[0]){
        for(int i=1;i<=8;i++){
          if(b[1][i] == my_koma){
            hyoka++;
          }else{
            hyoka--;
          }
        }
      }
      if(check[1]){
        for(int i=1;i<=8;i++){
          if(b[8][i] == my_koma){
            hyoka++;
          }else{
            hyoka--;
          }
        }
      }
      if(check[2]){
        for(int i=1;i<=8;i++){
          if(b[i][1] == my_koma){
            hyoka++;
          }else{
            hyoka--;
          }
        }
      }
      if(check[3]){
        for(int i=1;i<=8;i++){
          if(b[i][8] == my_koma){
            hyoka++;
          }else{
            hyoka--;
          }
        }
      }
       return hyok+hyoka;
  }
  public static int value_place(){
    int hyok=0;
    for(int i=1;i<=8;i++){
      for(int j=1;j<=8;j++){
        if(b[i][j] == my_koma){
          hyok += hyoka_value[i-1][j-1];
        }else if(b[i][j] == tekikoma){
          hyok -= hyoka_value[i-1][j-1];
        }
      }
    }
    return hyok;
  }
  public static int tyakushu(){
    int hyoka=0;

    for(int i=1;i<=8;i++){
      for(int j=1;j<=8;j++){
        if(rule.rule(i,j,teki_turn)){
          hyoka--;
        }else if(rule.rule(i,j,my_turn)){
          hyoka++;
        }
      }
    }
    return hyoka;
  }
  public static int kaihoudo(int b2[][] , int turn){
    //もっとまとめれば高速化できるかも．．．
    //返り値：動かされたコマの周りの空きの数
    int hyoka=0;
    int count=0;
      for (int i = 1; i <= 8; i++) {
        for (int j = 1; j <= 8; j++) {
          if (b2[i][j] != turn+1 && b[i][j] == turn+1) { //ひっくり返されている駒をみる！
            count = 0;
            if (i > 1) {
              if (b[i - 1][j] == 0) {
                count++;
              }
            }
            if (i < 8) {
              if (b[i + 1][j] == 0) {
                count++;
              }
            }
            if (j > 1) {
              if (b[i][j - 1] == 0) {
                count++;
              }
            }
            if (j < 8) {
              if (b[i][j + 1] == 0) {
                count++;
              }
            }
            if (i > 1 && j < 8) {
              if (b[i - 1][j + 1] == 0) {
                count++;
              }
            }
            if (i > 1 && j > 1) {
              if (b[i - 1][j - 1] == 0) {
                count++;
              }
            }
            if (i < 8 && j < 8) {
              if (b[i + 1][j + 1] == 0) {
                count++;
              }
            }
            if (i < 8 && j > 1) {
              if (b[i + 1][j - 1] == 0) {
                count++;
              }
            }
            hyoka += count;
          }
        }
      }
    return hyoka;
  }


}
