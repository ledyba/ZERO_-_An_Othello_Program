package othello.computer.middle;
import othello.*;
import othello.computer.Move;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 * <p>����: I want this program to be good at othello!</p>
 * <p>���쌠: Copyright (c) 2003-2004 by Sun-Soft</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version Version 0.5
 */

public class thinking {
  static int BestMove[] = new int[2];
  static double best_move_value = 0;
  static double[] important=
      //�J���x�C�ꏊ�C����̐΂̑����C�m��΁C���萔�C�E�C���O
      {1,1,0.25,10,1,10};
  static int[] value = {//��
      0 ,0,0,0,0,0,0,0,0,0,
      0,45,-11,  4, -1, -1,  4,-11, 45,0,
      0,-11,-16, -1, -3, -3, -1,-16,-11,0,
      0,  4, -1,  2, -1, -1,  2, -1,  4,0,
      0, -1, -3, -1,  0,  0, -1, -3, -1,0,
      0, -1, -3, -1,  0,  0, -1, -3, -1,0,
      0,  4, -1,  2, -1, -1,  2, -1,  4,0,
      0,-11,-16, -1, -3, -3, -1,-16,-11,0,
      0, 45,-11,  4, -1, -1,  4,-11, 45,0,};
  static final int master_depth = 6;//�[��
  static final int Piece[] = new int[89];
  private final static int PieceBackup[][] = new int[master_depth][89];
  static int count[][] = new int[master_depth + 1][2];
  static othello.computer.middle.piece_turn_over pto = new othello.computer.middle.piece_turn_over();
  static othello.computer.middle.can_piece_turn_over cpto = new othello.computer.middle.can_piece_turn_over();
  public static int[] start(int turn,int turn_count) {
    System.out.println("\n(middle) thinking start...");
    System.arraycopy(MainFrame.p,0,Piece,0,89);
    System.out.println("--------------------");
    System.out.print("Turn:");
    if(turn == 1){
      System.out.println("Black,"+turn_count);
    }else{
      System.out.println("White,"+turn_count);
    }
    System.out.println("--------------------");
     for(int x=1;x<=8;x++){
      for(int y=1;y<=8;y++){
        if(Piece[10*y+x]==1){
          System.out.print("��");
        }else if(Piece[10*y+x]==-1){
          System.out.print("��");
        }else{
          System.out.print("�@");
        }
      }
      System.out.println("");
    }

    run(true,master_depth,turn,turn_count,0,-9999,9999);
    System.out.println("\nBest Move "+MainFrame.kifu_table[BestMove[1]*10+BestMove[0]]+" : "+best_move_value+"\n");
    for(int i=master_depth;i>0;i--){
      System.out.println((master_depth-i+1)+":"+count[i][0]+"(- "+count[i][1]+")");
      count[i][0]=0;
      count[i][1]=0;
    }
    return BestMove;
  }
//�Ƃ肠����NegaMax
  public static double run(boolean last, int depth, int turn, int turn_count,
                           int open_value, double alpha, double beta) {
    if (depth <= 0 || turn_count > 60) {
      return eval(open_value, turn, Piece);
    }
    else {
      int[] bkp = PieceBackup[depth-1];
      System.arraycopy(Piece,0,bkp,0,89);
      double s = 0;
      List MoveList = new ArrayList();//��̃��X�g��
      for (int x = 1; x <= 8; x++) {
        for (int y = 1; y <= 8; y++) {
          if(pto.main(x,y,turn)){
            int open = - open_value(bkp,turn);
            MoveList.add(new Move(x,y,turn,open));
            System.arraycopy(bkp,0,Piece,0,89);
          }
        }
      }
      for(int i=0;i<MoveList.size();i++){
        for(int j=i+1;j<MoveList.size();j++){
          if(((Move)MoveList.get(j)).VALUE > ((Move)MoveList.get(i)).VALUE){
            Move tmp = ((Move)MoveList.get(j));
            MoveList.set(j,(Move)MoveList.get(i));
            MoveList.set(i,tmp);
          }
        }
      }


      if (MoveList.size() == 0) { //�p�X����
        if (last == false) { //�O����_���������͗t����
          return eval(open_value, turn, Piece);
        }
        else {
          //���ґłĂȂ��Ƃ����̂͊����̉\��������D
          int[] piece_amount = new int[2];
          //[0]:���� [1]:����
          for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
              if (Piece[10 * x + y] == turn) {
                piece_amount[0]++;
              }
              else if (Piece[10 * x + y] == turn * -1) {
                piece_amount[1]++;
              }
            }
          }
          if (piece_amount[0] <= 0) { //������0
            return -9999;
          }
          else if (piece_amount[1] <= 0) { //����0
            return 9999;
          }
          else { //����ȊO�͕��ʂɕ]���l��Ԃ�
            return -1 *
                run(false, depth, turn * -1, turn_count, -open_value, -beta,
                    -alpha);
          }
        }
      }


      Iterator iterator = MoveList.iterator();
      while (iterator.hasNext()) {
        Move move = (Move)iterator.next();
          if (pto.main(move.X, move.Y, turn)) {
            s = -1 * run(true, depth - 1, turn * -1, turn_count + 1,
                         -1 * (open_value + open_value(bkp, turn)), -beta,
                         -alpha);
            count[depth][0]++;
            if (depth == master_depth) {
              System.out.println(MainFrame.kifu_table[move.Y * 10 + move.X] + " eval:" +
                                 s);
            }
            System.arraycopy(bkp,0,Piece,0,89);
            if (s >= beta) {
              count[depth][1]++;
              return s;
            }
            else {
              if (s > alpha) {
                if (depth == master_depth) {
                  BestMove[0] = move.X;
                  BestMove[1] = move.Y;
                  best_move_value = s;
                }
                alpha = s;
              }
            }
          }
      }
      return alpha;
    }
  }


  public static double eval(int open_value,int turn,int[] p){//�]���l�v�Z
  double eval = 0;
  //�J���x�C�ꏊ�C����̐΁C�m��΁C���萔�C�E�C���O
  eval=
      - important[0] * open_value            //�J���x
      + important[1] * place(turn)           //�ꏊ
      - important[2] * around(turn)          //����̐�
      + important[3] * decided_value(p,turn)//�m���
      + important[4] * can_move_value(p,turn)//���萔
      + important[5] * (yama_value(p,turn) - wing_value(p,turn))//�E�C���O
      ;
  return eval;
  }
  public static int wing_value(int[] bkp,int turn){//�E�C���O�v�Z
    //�Ԃ�l�F�����̃E�C���O-����̃E�C���O
    int eval=0;
    if(bkp[11] == 0 && bkp[18] == 0){
      for(int i=3;i<=6;i++){
        if(bkp[13] == bkp[10+i]){
        }else{
          break;
        }
        if((bkp[12] == 0 || bkp[17] == 0) && (bkp[12] == bkp[13] || bkp[17] == bkp[13])){
        }else{
          break;
        }
        eval += bkp[13] / turn;
      }
    }
    if(bkp[81] == 0 && bkp[88] == 0){
      for(int i=3;i<=6;i++){
        if(bkp[83] == bkp[80+i]){
        }else{
          break;
        }
        if((bkp[82] == 0 || bkp[87] == 0) && (bkp[82] == bkp[83] || bkp[87] == bkp[83])){
        }else{
          break;
        }
        eval += bkp[83] / turn;
      }
    }
    if(bkp[11] == 0 && bkp[81] == 0){
      for(int i=3;i<=6;i++){
        if(bkp[10*i+1] == bkp[10*i+1]){
        }else{
          break;
        }
        if((bkp[21] == 0 || bkp[71] == 0) && (bkp[21] == bkp[31] || bkp[71] == bkp[31])){
        }else{
          break;
        }
        eval += bkp[31] / turn;
      }
    }
    if(bkp[18] == 0 && bkp[88] == 0){
      for(int i=3;i<=6;i++){
        if(bkp[10*i+8] == bkp[10*i+8]){
        }else{
          break;
        }
        if((bkp[28] == 0 || bkp[78] == 0) && (bkp[28] == bkp[38] || bkp[78] == bkp[38])){
        }else{
          break;
        }
        eval += bkp[38] / turn;
      }
    }
    return eval;
  }
  public static int yama_value(int[] bkp,int turn){//�R�v�Z
    //�Ԃ�l�F�����̎R-����̎R
    int eval=0;
    if(bkp[11] == 0 && bkp[18] == 0){
      for(int i=2;i<=7;i++){
        if(bkp[12] == bkp[10+i]){
        }else{
          break;
        }
        eval += bkp[12] / turn;
      }
    }
    if(bkp[81] == 0 && bkp[88] == 0){
      for(int i=2;i<=7;i++){
        if(bkp[82] == bkp[80+i]){
        }else{
          break;
        }
        eval += bkp[82] / turn;
      }
    }
    if(bkp[11] == 0 && bkp[81] == 0){
      for(int i=2;i<=7;i++){
        if(bkp[10*i+1] == bkp[10*i+1]){
        }else{
          break;
        }
        eval += bkp[21] / turn;
      }
    }
    if(bkp[18] == 0 && bkp[88] == 0){
      for(int i=2;i<=7;i++){
        if(bkp[10*i+8] == bkp[10*i+8]){
        }else{
          break;
        }
        eval += bkp[28] / turn;
      }
    }
    return eval;
  }
  public static int decided_value(int[] bkp,int turn){//(�Ȉ�)�m��Όv�Z
    //�Ԃ�l�F�����̊m��΃X�R�A-����̊m��΃X�R�A
    int eval=0;
    //�܂��C�e�͂������ɐ΂��u����Ă��邩����
    if(bkp[11] != 0){//1,1
      for(int i=1;i <= 8;i++){
          if(bkp[10+i] == bkp[11]){//10 + i
            eval += bkp[11] / turn;//turn = bkp[11]�Ȃ�C�{�C����ȊO�Ȃ�}�C�i�X�ɂȂ�D
          }else{
            break;
          }
      }
      for(int i=1;i <= 8;i++){
        if(bkp[10*i+1] == bkp[11]){//10 * i + 1
          eval += bkp[11] / turn;//turn = bkp[11]�Ȃ�C�{�C����ȊO�Ȃ�}�C�i�X�ɂȂ�D
        }else{
          break;
        }
      }
    }

    if(bkp[18] != 0){//1,8
      for(int i=8;i>= 1;i--){
          if(bkp[10+i] == bkp[18]){//10 + i
            eval += bkp[18] / turn;//turn = bkp[18]�Ȃ�C�{�C����ȊO�Ȃ�}�C�i�X�ɂȂ�D
          }else{
            break;
          }
      }
      for(int i=1;i <= 8;i++){
        if(bkp[10*i+8] == bkp[18]){//10 * i + 1
          eval += bkp[18] / turn;//turn = bkp[18]�Ȃ�C�{�C����ȊO�Ȃ�}�C�i�X�ɂȂ�D
        }else{
          break;
        }
      }
    }
    if(bkp[81] != 0){//8,1
      for(int i=1;i <= 8;i++){
          if(bkp[10+i] == bkp[81]){//80 + i
            eval += bkp[81] / turn;//turn = bkp[81]�Ȃ�C�{�C����ȊO�Ȃ�}�C�i�X�ɂȂ�D
          }else{
            break;
          }
      }
      for(int i=8;i >= 1;i--){
        if(bkp[10*i+1] == bkp[81]){//10 * i + 1
          eval += bkp[81] / turn;//turn = bkp[81]�Ȃ�C�{�C����ȊO�Ȃ�}�C�i�X�ɂȂ�D
        }else{
          break;
        }
      }
    }

    if(bkp[88] != 0){//8,8
      for(int i=8;i >= 1;i--){
        if(bkp[80+i] == bkp[88]){//80 + 1
          eval += bkp[88] / turn;//turn = bkp[88]�Ȃ�C�{�C����ȊO�Ȃ�}�C�i�X�ɂȂ�D
        }else{
          break;
        }
      }
      for(int i=8;i >= 1;i--){
        if(bkp[10*i+8] == bkp[88]){//10 * i + 8
          eval += bkp[88] / turn;//turn = bkp[88]�Ȃ�C�{�C����ȊO�Ȃ�}�C�i�X�ɂȂ�D
        }else{
          break;
        }
      }
    }
    //���̗񂪂��ׂĖ��܂��Ă���Ȃ�C�����͂��ׂĊm��΂��I
    boolean check[] = new boolean[4];
    check[0]=true;
    check[1]=true;
    check[2]=true;
    check[3]=true;
    for(int i=1;i<=8;i++){
      if(check[0] && bkp[10+i] == 0){
        check[0]=false;
      }
      if(check[1] && bkp[80+i] == 0){
        check[1]=false;
      }
      if(check[2] && bkp[10*i+1] == 0){
        check[2]=false;
      }
      if(check[3] && bkp[10*i+8] == 0){
        check[3]=false;
      }
    }
    if(check[0]){
      for(int i=1;i<=8;i++){
        eval += bkp[10+i] / turn;
      }
    }
    if(check[1]){
      for(int i=1;i<=8;i++){
        eval += bkp[80+i] / turn;
      }
    }
    if(check[2]){
      for(int i=1;i<=8;i++){
        eval += bkp[10*i+1] / turn;
      }
    }
    if(check[3]){
      for(int i=1;i<=8;i++){
        eval += bkp[10*i+8] / turn;
      }
    }
    return eval;
  }
  public static int can_move_value(int[] bkp,int turn){//���萔�v�Z
  //�Ԃ�l�F�������u����ꏊ-���肪�u����ꏊ
  int eval = 0;
  for (int x = 1; x <= 8; x++) {
    for (int y = 1; y <= 8; y++) {
      if(bkp[10*x + y] == 0){//�����ꏊ�ł��邱�ƁD���̃`�F�b�N������Α����Ȃ�H
        if(cpto.main(x,y,turn,bkp)){//�������u����
          eval++;
        }else if(cpto.main(x,y,turn,bkp)){//���肪�u����
          eval--;
        }
      }
    }
  }
  return eval;
  }
  public static int open_value(int[] bkp,int turn){//�J���x�v�Z
    //�Ԃ�l�F�������ꂽ�΂̎���̋󂫂̐�
    int eval=0;
    for (int x = 1; x <= 8; x++) {
        for (int y = 1; y <= 8; y++) {
          if(bkp[10*x+y] != turn && Piece[10*x+y] == turn){
            if(x > 1){
              if(bkp[10*(x-1)+y] == 0){//�������ꂽ������o
                eval++;
              }
            }
            if(x < 8){
              if(bkp[10*(x+1)+y] == 0){
                eval++;
              }
            }
            if(y > 1){
              if(bkp[10*x+y-1] == 0){
                eval++;
              }
            }
            if(y < 8){
              if(bkp[10*x+y+1] == 0){
                eval++;
              }
            }
            if(x > 1 && y >1){
              if(bkp[10*(x-1)+y-1] == 0){
                eval++;
              }
            }
            if(x < 8 && y >1){
              if(bkp[10*(x+1)+y-1] == 0){
                eval++;
              }
            }
            if(x > 1 && y < 8){
              if(bkp[10*(x-1)+y+1] == 0){
                eval++;
              }
            }
            if(x < 8 && y < 8){
              if(bkp[10*(x+1)+y+1] == 0){
                eval++;
              }
            }
          }
        }
      }
    return eval;
  }
  public static int around(int turn){//����̐΂����Ȃ�����i�����萔�����Ȃ�����j
    //�Ԃ�l�F�����̐΂̎���̋󂫂̐��D
    int eval=0;
    for (int x = 1; x <= 8; x++) {
        for (int y = 1; y <= 8; y++) {
          if (Piece[10 * x + y] == turn) {
            if (x > 1) {
              if (Piece[10 * (x - 1) + y] == 0) {
                eval++;
              }
            }
            if (x < 8) {
              if (Piece[10 * (x + 1) + y] == 0) {
                eval++;
              }
            }
            if (y > 1) {
              if (Piece[10 * x + y - 1] == 0) {
                eval++;
              }
            }
            if (y < 8) {
              if (Piece[10 * x + y + 1] == 0) {
                eval++;
              }
            }
            if (x > 1 && y > 1) {
              if (Piece[10 * (x - 1) + y - 1] == 0) {
                eval++;
              }
            }
            if (x < 8 && y > 1) {
              if (Piece[10 * (x + 1) + y - 1] == 0) {
                eval++;
              }
            }
            if (x > 1 && y < 8) {
              if (Piece[10 * (x - 1) + y + 1] == 0) {
                eval++;
              }
            }
            if (x < 8 && y < 8) {
              if (Piece[10 * (x + 1) + y + 1] == 0) {
                eval++;
              }
            }
          }
        }
      }
    return eval;
  }

  public static double place(int turn){//�ꏊ�ɂ��d�݂��v�Z
    //�Ԃ�l�F�z��uvalue�v�̑����Z
    double eval = 0;
    for (int x = 1; x <= 8; x++) {
      for (int y = 1; y <= 8; y++) {
        if(Piece[10*x+y] == turn){
          eval += value[10*x+y];
        }else if(Piece[10*x+y] == turn * -1){
          eval -= value[10*x+y];
        }
      }
    }
    return eval;
  }
}
