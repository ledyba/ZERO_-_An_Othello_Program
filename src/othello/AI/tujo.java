package othello.AI;
import javax.swing.*;
import othello.*;
/**
 * <p>�^�C�g��: ZERO</p>
 * <p>����: </p>
 * <p>���쌠: Copyright (c) 2003 RyoHirafuji</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version 1.0
 */

public class tujo {
  static int max=4;//�����͂�����P��[���ǂ�
  static int kyokumen_count[]=new int[max+2];
  static int b[][] = new int[9][9];
  static int master[][] = new int[9][9];
  static Icon ban[][]=new Icon[9][9];
  static sasite saizente = new sasite();
  static int my_turn=ai.turn;
  public void tujo(int t) {
      for (int i = 1; i <= 8; i++) {
          for (int j = 1; j <= 8; j++) {
            b[i][j] = ai.b[i][j];
          }
        }
      my_turn =t;
      for(int i=1;i<=max+1;i++){
        kyokumen_count[i]=0;
      }
      saiki(1,my_turn,ai.isi);
      //rondom();
      System.out.println("�őP��F" + saizente.x + "," + saizente.y
                         +"("+saizente.hyoka+")"+ "�@turn:" + my_turn);
      ai.sentaku[0]=saizente.x;
      ai.sentaku[1]=saizente.y;
      for(int i=1;i<=max+1;i++){
        System.out.println("�[���@"+i+"�@�̋ǖʂ��@"+kyokumen_count[i]+"�@�ǂ݂܂���");
      }
      ai.game();
  }
  public static void rondom(){
    boolean tmp_check = false;
    for (int i = 1; i <= 8; i++) {
      for (int j = 8; j >= 1; j--) {
        if (tmp_check == false) {
          tmp_check = rule.rule(i, j, ai.turn);
          saizente.x=i;
          saizente.y=j;
        }
      }
    }
  }
  public static int saiki(int hukasa , int turn ,int isi){
    sasite sasite = new sasite();
    int tmp_b[][] = new int[9][9];
    int hyoka=0;
    boolean check = false;
    int w_isi=0;
    int b_isi=0;
    boolean tmp_check=false;
    for(int i=1;i<=8;i++){
      for(int j=1;j<=8;j++){
        switch(b[i][j]){
          case 1:
            b_isi++;
          break;
          case 2:
            w_isi++;
          break;
        }
          if(tmp_check==false){
            tmp_check = rule.rule(i, j ,turn);
          }
      }
      }
      if(tmp_check){
        if (w_isi == 0 || b_isi == 0) {
          check = false;
        }
        else {
          check = true;
        }
        if (isi != 64 && check) {
          for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
              for(int a=1;a<=8;a++){//�Ֆʂ̋L��
                for(int c=1;c<=8;c++){
                tmp_b[a][c]=b[a][c];
                }
              }

              boolean cc_check=hikkuri.rule(i, j, turn);
              if (cc_check) {//���[�������Ȃ�
                kyokumen_count[hukasa]++;
                if (hukasa > max) {//MAX�ł����
                  hyoka = hyo(turn);
              }else {//MAX�Ŗ������
                  int t = 0;
                  switch (turn) {//�ċA�O�Ȃ̂Ń^�[���ύX
                    case 0:
                      t = 1;
                      break;
                    case 1:
                      t = 0;
                      break;
                  }
                  int t_hyoka = saiki(hukasa + 1, t, isi + 1);//�ċA
                  //System.out.println("���݁F"+i+","+j+"�@�[���F"+hukasa+"�@�^�[���F"+turn+"�@�]���l�F"+t_hyoka);
                  //for (int a = 1; a <= 8; a++) { //�Ֆʂ�\���I
                  //  for (int c = 8; c >= 1; c--) {
                  //    System.out.print(b[c][a]);
                  //  }
                  //  System.out.println("");
                  //}
                  //System.out.println("");
                  if (saizente.hyoka != 0) {//�őP��̎����グ
                    if (t_hyoka > saizente.hyoka && my_turn == turn) {
                      saizente.x = i;
                      saizente.y = j;
                      saizente.hyoka = t_hyoka;
                    }
                    else if (t_hyoka < saizente.hyoka && my_turn != turn) {
                      saizente.x = i;
                      saizente.y = j;
                      saizente.hyoka = t_hyoka;
                    }
                  }
                  else {
                    saizente.x = i;
                    saizente.y = j;
                    saizente.hyoka = t_hyoka;
                  }
                }//Max�łȂ����
              }//���[�������Ȃ�Ђ����肩����END
              for (int a = 1; a <= 8; a++) { //�Ֆʂ����ɖ߂��I
                for (int c = 1; c <= 8; c++) {
                  b[a][c] = tmp_b[a][c];
                }
              }
            }//�J��Ԃ�
          }//�J��Ԃ�
            }else {//�΂�64�C�������͊i�^�[���̂ǂ��炩������
          //�G���[�E�]���n��Ԃ�
          hyoka = hyo(turn);
        }
      }//������
      else{//���̏�ł��̃^�[�����u���Ȃ�������C�^�[����ς��Ă������I
        switch (turn) {
          case 0:
            turn = 1;
            break;
          case 1:
            turn = 0;
            break;
        }
        if(hukasa+1 <= max){
          saiki(hukasa + 1, turn, isi + 1);
        }else{
          hyoka=hyo(turn);
        }
      }
    return hyoka;
  }
  public static int hyo(int turn){
    int hyok=0;
    int my_koma=turn++;
    for(int i=1;i<=8;i++){
      for(int j=1;j<=8;j++){
        if(b[i][j] == my_koma){
          hyok++;
        }
      }
    }
    return hyok;
  }
}