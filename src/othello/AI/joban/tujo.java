package othello.AI.joban;
import othello.*;
import othello.AI.*;
/**
 * <p>�^�C�g��: ZERO</p>
 * <p>����: </p>
 * <p>���쌠: Copyright (c) 2003 RyoHirafuji</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version 1.0
 */

public class tujo {
  static       int     saishu_max =   0;//�T���̐[��
  static final double edakari_hyoka =     0;//�}����̍ۂ̐��x
  static final double[][] hyoka_omosa = {//�]���l�̏d��[�[��][�l�̎��]
      //�ЂƂÂ��C�ꏊ�C����\�ȏꏊ�C�ΐ��C�J���x,����̐�
      { 1, 1, 10,  1, 10,  0.25,},
  };
  static int teki_turn;
  static int my_koma;
  static int tekikoma;
  static int kyokumen_count[];
  static String saizente_hash[];
  static int cut[];
  static int b[][] = new int[9][9];
  static int saizente[] = new int[2];
  static double saizen=0;
  static int kmax;
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
        my_turn =t;
        saizente_hash = new String[saishu_max+1];
        for(int i=1;i<=saishu_max;i++){
          kyokumen_count[i]=0;
          cut[i]=0;
        }
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
      System.out.println("���ǖʂ̕]���F"+hyo(my_turn ,Frame.isi-3, 0));
        //for(int i=1;i<=max;i++){
       //   kmax = i;
        //  for(int j=0;j<=i;j++){
        //    cut[j]=0;
        //    kyokumen_count[j] = 0;
        //  }
         saiki(1 ,saishu_max, my_turn, ai.isi, -9999, 9999 , 0);
          System.out.println("�őP��F" + saizente[0] + "," + saizente[1]
                           +"("+saizen+")�@turn:" + my_turn);
        //  System.out.println("�[���F"+i+"�̒T���������I");
        //}
        System.out.println("");
        //rondom();
        ai.sentaku[0]=saizente[0];
        ai.sentaku[1]=saizente[1];
        for(int i=1;i<=saishu_max;i++){
          System.out.println("�[���@"+i+"�@�̋ǖʂ��@"+kyokumen_count[i]
                             +" (- "+ cut[i]+")�@�ǂ݂܂���");
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
      for(int i=1;i<=8;i++){//�����΂ƍ����΂�����ׂ�
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
      //��@�T��
      for (int i = 1; i <= 8; i++) {
        for (int j = 1; j <= 8; j++) {
          for(int a=1;a<=8;a++){//�Ֆʂ̋L��
            for(int c=1;c<=8;c++){
              tmp_b[a][c]=b[a][c];
            }
          }
          if(hikkuri.rule(i, j, turn)){//�Ђ�����Ԃ��{���[���`�F�b�N
            pass_check=true;
            scout[scout_count][0] = i;
            scout[scout_count][1] = j;
            scout[scout_count][2]=scout(t);
            scout_count++;
            for (int a = 1; a <= 8; a++) { //�Ֆʂ����ɖ߂��I
              for (int c = 1; c <= 8; c++) {
                b[a][c] = tmp_b[a][c];
              }
            }
          }
        }
      }
      if(pass_check == false){//�p�X�`�F�b�N������ׂāC������������p�X
        return saiki(hukasa + 1, max, t , isi +1 , -9999, 9999 , kaihoudo);
      }
      //��@���ʂ̃\�[�g
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
      //�{�T��
      for (int i = 0; i < tyakushu; i++) {
        for(int a=1;a<=8;a++){//�Ֆʂ̋L��
          for(int c=1;c<=8;c++){
            tmp_b[a][c]=b[a][c];
          }
        }
        if(hikkuri.rule(scout[i][0], scout[i][1], turn)){//�Ђ�����Ԃ��{���[���`�F�b�N
          kyokumen_count[hukasa]++;
          tmp=saiki(hukasa + 1 ,max, t, isi+1 , alpha , beta , kaihoudo);
          if(hukasa == 1){
            System.out.println("���W:("+scout[i][0]+","+scout[i][1]+")�@�]���l�F"+tmp);
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
            if( alpha >= beta ){//���l�����l��菬�������J�b�g
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
                    if( alpha >= beta ){//���l�����l��菬�������J�b�g
                      cut[hukasa]++;
                      return beta;
                    }

              }

              for (int a = 1; a <= 8; a++) { //�Ֆʂ����ɖ߂��I
                for (int c = 1; c <= 8; c++) {
                  b[a][c] = tmp_b[a][c];
                }
              }
            }
          }
          if( turn == my_turn )return( alpha );
        else return( beta );
        }


        public static int scout(int turn){
          int t=0;
          int value;
          int temp=0;
          int tmp_b[][] = new int[9][9];
          int hyoka=0;

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
                for(int a=1;a<=8;a++){//�Ֆʂ̋L��
                  for(int c=1;c<=8;c++){
                    tmp_b[a][c]=b[a][c];
                  }
                }
                if(hikkuri.rule(i, j, turn)){//�Ђ�����Ԃ��{���[���`�F�b�N
                  temp=value_place();

                  if( turn == my_turn ){
                      if( temp >= value ){
                          value = temp;
                        }
                  }else{
                      if( temp <= value ){
                          value = temp;
                        }
                  }
                  for (int a = 1; a <= 8; a++) { //�Ֆʂ����ɖ߂��I
                    for (int c = 1; c <= 8; c++) {
                      b[a][c] = tmp_b[a][c];
                    }
                  }
                }
              }
            }
            return( value );
          }

          public static int kaihoudo(int b2[][] , int turn){
            //�����Ƃ܂Ƃ߂�΍������ł��邩���D�D�D
            int hyoka=0;
            int count=0;
              for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                  if (b2[i][j] != turn+1 && b[i][j] == turn+1) { //�Ђ�����Ԃ���Ă������݂�I
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
          public static String hash_seisei(){
        String hash="";
          for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
              hash += String.valueOf(b[j][i]);
            }
          }
        return hash;
      }


    public static double hyo(int turn ,int isi, int kaihoudo){
      double hyok=0;
      //�ЂƂÂ��C�ꏊ�C����\�ȏꏊ�C�ΐ��C�J���x,����̐�
      hyok=0
          //+ hyoka_omosa[0][0]* retu_gyo_value()
          + hyoka_omosa[0][1]* value_place()
          //+ hyoka_omosa[0][2]* tyakushu()//�J���x���_�𓊓�����΂��Ԃ�C�K�v�Ȃ�
          + hyoka_omosa[0][3]* sekisuu()
          + hyoka_omosa[0][4]*  kaihoudo
          + hyoka_omosa[0][5]* mawari(turn)
          ;
      return hyok;
    }
    public static int sekisuu(){
      int hyoka=0;
      int count=0;
      int goukei=0;
      for(int i=1;i<=8;i++){
        for(int j=1;j<=8;j++){
          if(b[i][j] == my_koma){
            count--;
          }
          goukei++;
        }
      }
      hyoka=(count/goukei)*100;
      return hyoka;
    }
    public static int mawari(int turn){
      int count = 0;
      int hyoka=0;
      for (int i = 1; i <= 8; i++) {
        for (int j = 1; j <= 8; j++) {
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
            if(b[i][j] == turn+1){
              hyoka -= count;
            }else{
              hyoka += count;
            }
        }
      }
      return hyoka;
    }
    public static int retu_gyo_value(){
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
      int count[]=new int[2];
      count[0]=0;
      count[0]=0;
      boolean check_gyo=true;
      boolean check_retu=true;

      for(int i=1;i<=8;i++){//1��E�s�̂����S�������̋������(�������ꑱ���I)
        if(b[i][1] == my_koma && check_gyo==true){//�s
          count[0]++;
        }else if( count[0] > 0&&b[i][1] != my_koma){
          check_gyo=false;
          count[0] =0;
        }
        if(b[i][1] == my_koma && check_retu==true){//��
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
    for(int i=1;i<=8;i++){//1��E�s�̂����S���G�̋������(�������ꑱ���I)
      if(b[i][1] == tekikoma && check_gyo==true){//�s
          count[0]++;
      }else if( count[0] > 0&&b[i][1] != tekikoma){
        check_gyo=false;
        count[0] =0;
      }
      if(b[i][1] == tekikoma && check_retu==true){//��
        count[1]++;
      }else if(count[0] > 0&& b[i][1] != tekikoma){
        check_gyo=false;
        count[1] =0;
      }
      hyok -= (count[0]+count[1]);

    }
     return hyok;
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
}
