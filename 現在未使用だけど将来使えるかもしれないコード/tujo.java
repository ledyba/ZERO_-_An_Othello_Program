package othello.AI.tyuban;
import othello.*;
import othello.AI.*;
//scout�@����Ȃ����ǂ��D
//�Ȃ�ł܂��������񂾂낤
//���՗p�u�����[���T���v
//�x���D
//20040118
/**
 * <p>�^�C�g��: ZERO</p>
 * <p>����: </p>
 * <p>���쌠: Copyright (c) 2003 RyoHirafuji</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version 1.0
 */

public class tujo {
  static int saishu_max  =   0;//�T���̐[��
  static int teki_turn;
  static int tekikoma;
  static int my_koma;
  static int kyokumen_count[];
  static int cut[];
  static String saizente_hash[];
  static int b[][] = new int[9][9];
  static int saizente[][];
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
  static int[][] hyoka_omosa = {//�]���l�̏d��[�[��][�l�̎��]
      //�ЂƂÂ��C�m��΁C�ꏊ�C����\�ȏꏊ�C�E�C���O�C�J���x ,
      //���C��3*3 , ���C��4*4 , ���C��5*5 , ���C��6*6 , ���C��7*7 ,���C��8*8 ,
      { 1, 10,  1,  3, 10,  3,  3,  3,  3,  3,  3,  3,},
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
        saizente = new int[saishu_max + 1][3];
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
      System.out.println("���ǖʂ̕]���F"+hyo(my_turn , Frame.isi-3));
      //for(int i=1;i<=max;i++){
     //   kmax = i;
      //  for(int j=0;j<=i;j++){
      //    cut[j]=0;
      //    kyokumen_count[j] = 0;
      //  }
      for(int i=1;i<=saishu_max;i++){
        for(int a=0;a<=i;a++){
          kyokumen_count[a] =0;
          cut[a] = 0;
        }
        saizente = saiki(1, i, my_turn, ai.isi, -99999, 99999);
        System.out.println("�[���F"+i+"�@�őP��F" + saizente[1][0] + "," + saizente[1][1]
                         +"("+saizente[1][2]+")�@turn:" + my_turn);
      }
      //  System.out.println("�[���F"+i+"�̒T���������I");
      //}
      //rondom();
      System.out.println("\n�őP�菇");
      for(int i=0;i<=saishu_max;i++){
        System.out.println("("+saizente[i][0]+","+saizente[i][1]+"):"+saizente[i][2]);
      }
      System.out.println("");
      ai.sentaku[0]=saizente[1][0];
      ai.sentaku[1]=saizente[1][1];
      for(int i=1;i<=saishu_max;i++){
        System.out.println("�[���@"+i+"�@�̋ǖʂ��@"+kyokumen_count[i]
                           +" (- "+ cut[i]+")�@�ǂ݂܂���");
      }
      ai.game();
  }

  public static int[][] saiki(int hukasa  ,int max, int turn ,int isi , int alpha ,int beta){
    int t=0;
    int tmp_b[][] = new int[9][9];
    int hyoka=0;
    boolean check = false;
    int scout[][];
    int scout_count = 0;
    int tmp[][] = new int[max+1][3];//�T�����Ɏg�p���邻�̂Ă̍s������L�����z��
    int saizen_kouho[][] = new int[max+1][3];//�őP���Ǝv�������L���C��̃m�[�h�ɓn��
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
      tmp[max][2]=hyo(my_turn , isi-3);
      tmp[max-1][2]=tmp[max][2];
      return tmp;
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
        tmp[hukasa][2]=9999;
        tmp[hukasa-1][2]=9999;
        return tmp;
      }else if(b_isi == 0 && my_turn == 0){
        tmp[hukasa][2]=-9999;
        tmp[hukasa-1][2]=-9999;
        return tmp;
      }else if(w_isi == 0 && my_turn == 1){
        tmp[hukasa][2]=-9999;
        tmp[hukasa-1][2]=-9999;
        return tmp;
      }else if(b_isi == 0 && my_turn == 1){
        tmp[hukasa][2]=9999;
        tmp[hukasa-1][2]=9999;
        return tmp;
      }else{
        if(my_turn == 1){
          if (w_isi > b_isi) {
            tmp[hukasa][2]=9999;
            tmp[hukasa-1][2]=9999;
            return tmp;
          }else{
            tmp[hukasa][2]=-9999;
            tmp[hukasa-1][2]=-9999;
            return tmp;
          }
        }else{
          if (w_isi < b_isi) {
            tmp[hukasa][2]=9999;
            tmp[hukasa-1][2]=9999;
            return tmp;
          }else{
            tmp[hukasa][2]=-9999;
            tmp[hukasa-1][2]=-9999;
            return tmp;
          }
        }
      }
    }
    //��@�T��
    for (int i = 1; i <= 8; i++) {
      for (int j = 1; j <= 8; j++) {
        if(rule.rule(i, j, turn)){//�Ђ�����Ԃ��{���[���`�F�b�N
          pass_check=true;
          scout[scout_count][0] = i;
          scout[scout_count][1] = j;
          scout[scout_count][2]=scout(t);
          scout_count++;
        }
      }
    }
    if(pass_check == false){//�p�X�`�F�b�N������ׂāC������������p�X
      return saiki(hukasa + 1, max, t , isi +1 , -9999, 9999);
    }
    for(int i=0;i<tyakushu;i++){//�����i���̂��߂ɁC���̍őP��̃|�C���g�͏グ�Ă���
      if((hukasa < max && max  > 1) &&(scout[i][0] == saizente[hukasa][0] && scout[i][1] == saizente[hukasa][1])){
        scout[i][2] = 9999;
      }
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
    if(kyokumen_count[hukasa] == 0){
      int tmp_s[][] = new int[tyakushu][2];
      for(int i=0;i < tyakushu ;i++){
          tmp_s[i] = scout[tyakushu - i - 1];
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
        tmp=saiki(hukasa + 1 ,max, t, isi+1 , alpha , beta);
        if(hukasa == 1 && max == saishu_max){
          System.out.println("���W:("+scout[i][0]+","+scout[i][1]+")�@�]���l�F"+tmp[max][2]);
        }
        if( turn == my_turn ){
          if( tmp[hukasa][2] >= alpha ){
            if(tmp[hukasa][2] == alpha){
              if(((int)(Math.random()*3)) == 0){
                tmp[hukasa][0] = scout[i][0];
                tmp[hukasa][1] = scout[i][1];
                tmp[hukasa-1][2] = tmp[hukasa][2];
                saizen_kouho = tmp;
              }
            }else{
              tmp[hukasa][0] = scout[i][0];
              tmp[hukasa][1] = scout[i][1];
              tmp[hukasa-1][2] = tmp[hukasa][2];
              saizen_kouho = tmp;
            }
            alpha = tmp[hukasa][2];
          }
          if( alpha >= beta ){//���l�����l��菬�������J�b�g
            cut[hukasa]++;
            return saizen_kouho;

          }
            }else{
              if( tmp[hukasa][2] <= beta ){
                if (tmp[hukasa][2] == beta) {
                  if(((int)(Math.random()*3)) == 0){
                    tmp[hukasa][0] = scout[i][0];
                    tmp[hukasa][1] = scout[i][1];
                    tmp[hukasa-1][2] = tmp[hukasa][2];
                    saizen_kouho = tmp;
                  }
                }else{
                  tmp[hukasa][0] = scout[i][0];
                  tmp[hukasa][1] = scout[i][1];
                  tmp[hukasa-1][2] = tmp[hukasa][2];
                  saizen_kouho = tmp;
                }
                beta = tmp[hukasa][2];
              }
                  if( alpha >= beta ){//���l�����l��菬�������J�b�g
                    cut[hukasa]++;
                    return saizen_kouho;
                  }

            }
            for (int a = 1; a <= 8; a++) { //�Ֆʂ����ɖ߂��I
              for (int c = 1; c <= 8; c++) {
                b[a][c] = tmp_b[a][c];
              }
            }
          }
        }
          return saizen_kouho;
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
      public static String hash_seisei(){
        String hash="";
        for (int i = 1; i <= 8; i++) {
          for (int j = 1; j <= 8; j++) {
            hash += String.valueOf(b[j][i]);
          }
        }
        return hash;
      }


      public static int hyo(int turn , int hukasa){
        int hyok=sekisuu();
        //�]���l�̏d��[�[��][�l�̎��]
        //�ЂƂÂ��C�m��΁C�ꏊ�C����ꏊ�C�E�C���O�C�J���x
        if(hyok == 0){
          hyok = // hyoka_omosa[hukasa][0]*retu_gyo_value()
              +hyoka_omosa[0][1] * kakuteiseki()
            + hyoka_omosa[0][2] * value_place()
            + hyoka_omosa[0][3] * tyakushu()
            //+ hyoka_omosa[0][ 5] * kaihoudo()
            + hyoka_omosa[0][4] * (yama() - wing())
            + hyoka_omosa[0][6] * naname1()
            + hyoka_omosa[0][7] * naname2()
            + hyoka_omosa[0][8] * naname3()
            + hyoka_omosa[0][9] * naname4()
            + hyoka_omosa[0][10] * naname5()
            + hyoka_omosa[0][11] * naname6()
            ;
      }
      return hyok;
    }
    public static int sekisuu(){//�������C�����̐ΐ���0��������]���l��-9999,���肾������+9999
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
    if((b[8][1] == 0 && b[1][1] == 0 ) && (b[7][1] == 0 ^ b[2][1] == 0)){//��̕�
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
      if(b[8][8] == 0 && b[1][8] == 0 && (b[7][8] == 0 ^ b[2][8] == 0)){//���̕�
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
      if((b[8][1] == 0 && b[1][1] == 0 ) && (b[7][1] == 0 ^ b[2][1] == 0)){//��̕�
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
        if(b[8][8] == 0 && b[1][8] == 0 && (b[7][8] == 0 ^ b[2][8] == 0)){//���̕�
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
      //�܂������̃^�[������
    if(b[8][1] == 0 && b[1][1] == 0){//��̕�
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
      if(b[8][8] == 0 && b[1][8] == 0){//���̕�
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
      //���ɑ���̃^�[��
      if(b[8][1] == 0 && b[1][1] == 0){//��̕�
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
        if(b[8][8] == 0 && b[1][8] == 0){//���̕�
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
    public static int naname5(){//�΂߂̃��C��(8*8)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //����������Ă���
      for(int i=0;i<=7;i++){
        if(b[8-i][1+i] == my_koma){//�E��
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
      for(int i=0;i<=7;i++){//����
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
      //�G������Ă���
      for(int i=0;i<=7;i++){
        if(b[8-i][1+i] == tekikoma){//�E��
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
      for(int i=0;i<=7;i++){//����
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

    public static int naname6(){//�΂߂̃��C��(7*7)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //����������Ă���
      for(int i=0;i<=6;i++){
        if(b[7-i][1+i] == my_koma){//�E��
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
      for(int i=0;i<=6;i++){//����
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
      for(int i=0;i<=6;i++){//����
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
        if(b[1+i][2+i] == my_koma){//�E��
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
      //�G������Ă���
      for(int i=0;i<=6;i++){
        if(b[7-i][1+i] == tekikoma){//�E��
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
      for(int i=0;i<=6;i++){//����
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
      for(int i=0;i<=6;i++){//����
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
        if(b[1+i][2+i] == tekikoma){//�E��
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

    public static int naname4(){//�΂߂̃��C��(6*6)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //����������Ă���
      for(int i=0;i<=5;i++){
        if(b[6-i][1+i] == my_koma){//�E��
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
      for(int i=0;i<=5;i++){//����
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
      for(int i=0;i<=5;i++){//����
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
        if(b[1+i][3+i] == my_koma){//�E��
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
      //�G������Ă���
      for(int i=0;i<=5;i++){
        if(b[6-i][1+i] == tekikoma){//�E��
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
      for(int i=0;i<=5;i++){//����
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
      for(int i=0;i<=5;i++){//����
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
        if(b[1+i][3+i] == tekikoma){//�E��
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
    public static int naname3(){//�΂߂̃��C��(5*5)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //����������Ă���
      for(int i=0;i<=4;i++){
        if(b[5-i][1+i] == my_koma){//�E��
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
      for(int i=0;i<=4;i++){//����
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
      for(int i=0;i<=4;i++){//����
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
        if(b[1+i][4+i] == my_koma){//�E��
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
      //�G������Ă���
      for(int i=0;i<=4;i++){
        if(b[5-i][1+i] == tekikoma){//�E��
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
      for(int i=0;i<=4;i++){//����
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
      for(int i=0;i<=4;i++){//����
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
        if(b[1+i][4+i] == tekikoma){//�E��
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
    public static int naname2(){//�΂߂̃��C��(4*4)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //����������Ă���
      for(int i=0;i<=3;i++){
        if(b[4-i][1+i] == my_koma){//�E��
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
      for(int i=0;i<=3;i++){//����
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
      for(int i=0;i<=3;i++){//����
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
        if(b[1+i][5+i] == my_koma){//�E��
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
      //�G������Ă���
      for(int i=0;i<=3;i++){
        if(b[4-i][1+i] == tekikoma){//�E��
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
      for(int i=0;i<=3;i++){//����
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
      for(int i=0;i<=3;i++){//����
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
        if(b[1+i][5+i] == tekikoma){//�E��
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

    public static int naname1(){//�΂߂̃��C��(3*3)
      int hyoka=0;
      int count=0;
      int my_hyoka=0;
      int teki_hyoka=0;
      //����������Ă���
      for(int i=0;i<=2;i++){
        if(b[3-i][1+i] == my_koma){//�E��
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
      for(int i=0;i<=2;i++){//����
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
      for(int i=0;i<=2;i++){//����
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
        if(b[1+i][6+i] == my_koma){//�E��
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
      //�G������Ă���
      for(int i=0;i<=2;i++){
        if(b[3-i][1+i] == tekikoma){//�E��
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
      for(int i=0;i<=2;i++){//����
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
      for(int i=0;i<=2;i++){//����
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
        if(b[1+i][6+i] == tekikoma){//�E��
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
    public static int kakuteiseki(){
      int hyok=0;
      //�m��ΒT��(���������݂̍s�E�񂪂��ׂĎ����̋�ƃ|�C���g�Q�{�I)
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
      //�G
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
      //�ق��̊m��΂��܂߂�(�������Ȃ��ƕӂ���������Ƃ��Ă��]�����Ⴍ�Ȃ�)
      //����ɂ���Ĉ�񂷂ׂĎ����̋�ƃ|�C���g��3�{�ɂȂ�
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
  public static int kaihoudo(){//�߂�ǂ������̂ŁC����̊J���x�ň�ԏ��������̂�T��
    int hyoka=0;
    int count=0;
    int teki_saitei=9;
    int my_saitei=9;
    for(int i=1;i<=8;i++){
      for(int j=1;j<=8;j++){
        if(( j == 8 || j == 1) || (i == 8 || i == 1)){
        }else{
          count = 0;
          if (b[i][j] == my_koma) {
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
            if (count < teki_saitei && count > 0) {
              teki_saitei = count;
            }
          }
          else if (b[i][j] == tekikoma) { //����̊J���x
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
            if (count < teki_saitei && count > 0) {
              my_saitei = count;
            }
          }
        }
      }
    }
    hyoka=(8-teki_saitei)+(my_saitei);
    return hyoka;
  }


}