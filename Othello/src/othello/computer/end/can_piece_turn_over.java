package othello.computer.end;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 * <p>����: I want this program to be good at othello!</p>
 * <p>���쌠: Copyright (c) 2003-2004 by Sun-Soft</p>
 * <p>��Ж�: </p>
 * @author ������
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
    if(p[10*x+y] == 0){//�Y���͂����܂���(��
      if (turn_x(8, 1, t)) //�E
        check = true;
      if (turn_x(0, -1, t)) //��
        check = true;

      if (turn_y(8, 1, t)) //��
        check = true;
      if (turn_y(0, -1, t)) //��
        check = true;

      if (turn_naname(8, 8, 1, 1, t)) ///�E��
        check = true;
      if (turn_naname(8, 0, 1, -1, t)) //�E��
        check = true;
      if (turn_naname(0, 8, -1, 1, t)) //����
        check = true;
      if (turn_naname(0, 0, -1, -1, t)) //����
        check = true;
    }
    return check;
  }
  static boolean turn_x(int xn,int xh,int t){
    /*
     �E�Ђ�����Ԃ��`�F�b�J�[���Ђ�����Ԃ����[�`��X�����p
     �Exn   :x������������̏ꍇ(�v�͉E)�̏ꍇ��8,���ꂢ������0
     �Exh   :x��������ꍇ(�v�͉E)�̏ꍇ�̓v���X�P�C�ւ�̂̓}�C�i�X�P
     �Et    :���̃^�[���̐l�̋��\������(���(��)1�C��-1�C�Ȃ���0)
     �Et_img:���̃^�[���̐l�̋�̉摜�D
     ���F�P���ȏ㉺��΂߂̏ꍇ�͖ʓ|�Ȃ̂ŕʃ��[�`����p���邱��
     */
    boolean check = false;
    int c = 0;
    int tmp = 0;
    if(x != xn){
      for(int i=1;i<=(xh*x*-1)+xn;i++){
        if (p[(x+(xh*i))*10+y] == t) {//�����̋����������Break;
          tmp = i-1;
          c=i;
          check = true;//�ꍇ�ɂ���邪�C�Ƃ肠�����Ђ�����Ԃ���D
          break;
        }else if(p[(x+(xh*i))*10+y] == 0){
          //�����̋�������ꂸ��
          //�󔒂����m������Ђ�����Ԃ��Ȃ�
          check = false;
          break;
        }
      }
      if(tmp == 0){//�����̋��������Ă��Ԃ�0�Ȃ�ׂ荇���Ă��邾���Ȃ̂Ń_���_���D
        check = false;
      }
    }
    return check;

  }
  static boolean turn_y(int yn,int yh,int t){
    /*
     �E�Ђ�����Ԃ��`�F�b�J�[���Ђ�����Ԃ����[�`��Y�����p
     �Eyn   :y������������̏ꍇ(�v�͉�)�̏ꍇ��8,���ꂢ������0
     �Eyh   :��������ꍇ(�v�͉�)�̏ꍇ�̓v���X�P�C�ւ�̂̓}�C�i�X�P
     �Et    :���̃^�[���̐l�̋��\������(���(��)1�C��-1�C�Ȃ���0)
     �Et_img:���̃^�[���̐l�̋�̉摜�D
     ���F�P���ȍ��E��΂߂̏ꍇ�͖ʓ|�Ȃ̂ŕʃ��[�`����p���邱��
     */
    boolean check = false;
    int c = 0;
    int tmp = 0;
    if(y != yn){
      for(int i=1;i<=(yh*y*-1)+yn;i++){
        if (p[(x*10+y+(yh*i))] == t) {//�����̋����������Break;
          tmp = i-1;
          c=i;
          check = true;//�ꍇ�ɂ���邪�C�Ƃ肠�����Ђ�����Ԃ���D
          break;
        }else if(p[x*10+y+(yh*i)] == 0){
          //�����̋�������ꂸ��
          //�󔒂����m������Ђ�����Ԃ��Ȃ�
          check = false;
          break;
        }
      }
      if(tmp == 0){//�����̋��������Ă��Ԃ�0�Ȃ�ׂ荇���Ă��邾���Ȃ̂Ń_���_���D
        check = false;
      }
    }
    return check;
  }

  static boolean turn_naname(int xn,int yn,int xh,int yh,int t){
    /*
     �E�Ђ�����Ԃ��`�F�b�J�[���Ђ�����Ԃ����[�`���΂ߗp
     �Exn   :x������������̏ꍇ(�v�͉E)�̏ꍇ��8,���ꂢ������0
     �Eyn   :����D�v�͉��D
     �Exh   :x��������ꍇ(�v�͉E)�̏ꍇ�̓v���X�P�C�ւ�̂̓}�C�i�X�P
     �Eyh   :����D���D
     �Et    :���̃^�[���̐l�̋��\������(���(��)1�C��-1�C�Ȃ���0)
     �Et_img:���̃^�[���̐l�̋�̉摜�D
     ���F�P���ȏ㉺�E���̏ꍇ�͖ʓ|�Ȃ̂ŕʃ��[�`����p���邱��
     */
    boolean check = false;
    int c = 0;
    int tmp = 0;
    if(x != xn && y != yn){
      for(int i=1;i<=Math.min((xh*x*-1)+xn,(yh*y*-1)+yn);i++){
        if (p[(x+(xh*i))*10+(y+(yh*i))] == t) {//�����̋����������Break;
          tmp = i-1;
          c=i;
          check = true;//�ꍇ�ɂ���邪�C�Ƃ肠�����Ђ�����Ԃ���D
          break;
        }else if(p[(x+(xh*i))*10+(y+(yh*i))] == 0){
          //�����̋�������ꂸ��
          //�󔒂����m������Ђ�����Ԃ��Ȃ�
          check = false;
          break;
        }
      }
      if(tmp == 0){//�����̋��������Ă��Ԃ�0�Ȃ�ׂ荇���Ă��邾���Ȃ̂Ń_���_���D
        check = false;
      }
    }

    return check;
  }

}
