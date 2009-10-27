package othello.computer;

import othello.piece_turn_over.*;
import othello.*;
/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 * <p>����: I want this program to be good at othello!</p>
 * <p>���쌠: Copyright (c) 2003-2004 by Sun-Soft</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version Version 0.5
 */

public class computer extends Thread{
  int turn;
  int turn_count;
  int move[] = new int[2];
  piece_turn_over pto = new piece_turn_over();//�Ђ�����Ԃ��`�F�b�N�@�{�Ђ�����Ԃ�
  public void start(int tmp_turn,int tmp_turn_count) {
    turn = tmp_turn;
    turn_count=tmp_turn_count+1;
    super.start();
  }
  public void run() {//�e�����͖{���̓X���b�h���Ⴀ��܂���(^^;���̃N���X�̓X���b�h�Ƃ��ē����܂��D
    if(turn_count <= 13){//����
      othello.computer.opening.thinking thread = new othello.computer.opening.thinking();
      move=thread.start(turn,turn_count);
    }else if(turn_count > 13 && turn_count <= 46){//����
      othello.computer.middle.thinking thread = new othello.computer.middle.thinking();
      move=thread.start(turn,turn_count);
    }else{//�I��
      othello.computer.end.thinking thread = new othello.computer.end.thinking();
      move=thread.start(turn,turn_count);
    }

    //�v�l�J�n��͂Ђ�����Ԃ��ăR���g���[�����ďI���D
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
    System.out.println("ERROR!");//�����ĂЂ�����Ԃ���ĂȂ��񂾂���˂��E�E�E
  }
}
