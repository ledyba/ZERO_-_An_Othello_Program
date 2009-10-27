/*
��@�T���@�g�p�̏I�՗p�N���X�D������̕����x����ɕs���m�H
�ŏI�X�V���F2003/1/18
*/
package othello.AI.shuban;
import othello.AI.*;
/**
 * <p>�^�C�g��: ZERO</p>
 * <p>����: </p>
 * <p>���쌠: Copyright (c) 2003 RyoHirafuji</p>
 * <p>��Ж�: </p>
 * @author ������
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
        System.out.println("���ǖʂ̕]���F"+hyo(my_turn));
        //for(int i=1;i<=max;i++){
       //   kmax = i;
        //  for(int j=0;j<=i;j++){
        //    cut[j]=0;
        //    kyokumen_count[j] = 0;
        //  }
          saiki(1, saishu_max, my_turn, ai.isi, -99999, 99999);
          System.out.println("�őP��F" + saizente[1][0] + "," + saizente[1][1]
                           +"("+saizente[1][2]+")�@turn:" + my_turn);
        //  System.out.println("�[���F"+i+"�̒T���������I");
        //}

        //rondom();
        ai.sentaku[0]=saizente[1][0];
        ai.sentaku[1]=saizente[1][1];
        for(int i=1;i<=saishu_max;i++){
          System.out.println("�[���@"+i+"�@�̋ǖʂ��@"+kyokumen_count[i]
                             +" (- "+ cut[i]+")�@�ǂ݂܂���");
        }
        ai.game();
    }
    public static int saiki(int hukasa  ,int max, int turn ,int isi , int alpha ,int beta){
      int t=0;
      int temp=0;
      int tmp_b[][] = new int[9][9];
      int hyoka=0;
      boolean check = false;
      int scout[][];
      int scout_count = 0;
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
      if(hukasa > max){
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
	if(hukasa == max){//MAX�������炱�̒l��Ԃ�
		return scount[0][2];
	}
      if(pass_check == false){//�p�X�`�F�b�N������ׂāC������������p�X
        return saiki(hukasa + 1, max, t , isi +1 , -9999, 9999);
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
          temp=saiki(hukasa + 1 ,max, t, isi+1 , alpha , beta);
          if(hukasa == 1){
            System.out.println("���W:("+scout[i][0]+","+scout[i][1]+")�@�]���l�F"+temp);
          }
          if( turn == my_turn ){
            if( temp >= alpha ){
              if(temp == alpha){
                if(((int)(Math.random()*3)) == 0){
                  saizente[hukasa][0] = scout[i][0];
                  saizente[hukasa][1] = scout[i][1];
                  saizente[hukasa][2] = temp;
                }
              }else{
                saizente[hukasa][0] = scout[i][0];
                saizente[hukasa][1] = scout[i][1];
                saizente[hukasa][2] = temp;
              }
              alpha = temp;
            }
            if( alpha >= beta ){//���l�����l��菬�������J�b�g
              cut[hukasa]++;
              return (alpha);
            }
              }else{
                if( temp <= beta ){
                  if (temp == beta) {
                    if(((int)(Math.random()*3)) == 0){
                      saizente[hukasa][0] = scout[i][0];
                      saizente[hukasa][1] = scout[i][1];
                      saizente[hukasa][2] = temp;

                    }
                  }else{
                    saizente[hukasa][0] = scout[i][0];
                    saizente[hukasa][1] = scout[i][1];
                    saizente[hukasa][2] = temp;
                  }
                  beta = temp;
                }
                    if( alpha >= beta ){//���l�����l��菬�������J�b�g
                      cut[hukasa]++;
                      return (beta);
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
                  temp=hyo(my_turn);

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

