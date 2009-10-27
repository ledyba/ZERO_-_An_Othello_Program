package othello;

/**
 * <p>�^�C�g��: ZERO</p>
 * <p>����: </p>
 * <p>���쌠: Copyright (c) 2003 RyoHirafuji</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version 1.0
 */

public class Frame_hikkuri {
  static int sentaku[]=new int[2];
  static int turn=0;
  public static boolean rule(int x, int y, int t){//���[���I
    boolean check=false;
    sentaku[0]=x;
    sentaku[1]=y;
    turn=t;
    switch(turn){
      case 0:
        check=black_rule();
      break;
      case 1:
        check=white_rule();
      break;
    }
    return check;
  }
  public static boolean black_rule(){
    boolean check=false;
    boolean s_check=false;
    int tmp=0;
    int p=0;
    if(sentaku[0] != 8){
      for (int i = sentaku[0] + 1; i <= 8; i++) { //��
        if (Frame.b[i][sentaku[1]] == 1) {
          tmp = i - sentaku[0] - 1;
          p=i;
          break;
        }
      }
      if (tmp > 0) {
        check = true;
        for (int i = sentaku[0]+1; i <= p; i++) {
          if (Frame.b[i][sentaku[1]] == 0) {
            check = false;
          }
        }
      }else{
        check=false;
      }
    }else {
        tmp = 0;
        check = false;
      }
    if(check){
      for(int i=sentaku[0];i<=p-1;i++){//��
        Frame.b[i][sentaku[1]]=1;
        Frame.ban[i][sentaku[1]].setIcon(Frame.black_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[0] != 1){
    for(int i=sentaku[0]-1;i>=1;i--){//�E
      if (Frame.b[i][sentaku[1]] == 1) {
        tmp = sentaku[0] - i -1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = sentaku[0]-1; i >= p; i--) {
        if (Frame.b[i][sentaku[1]] == 0) {
          check = false;
        }

      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
      check=false;
    }
    if(check){
      for(int i=sentaku[0];i>=p+1;i--){//�E
        Frame.b[i][sentaku[1]]=1;
        Frame.ban[i][sentaku[1]].setIcon(Frame.black_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[1] != 1){
    for(int i=sentaku[1]-1;i>=1;i--){//��
      if (Frame.b[sentaku[0]][i] == 1) {
        tmp = sentaku[1] - i -1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = sentaku[1]-1; i >= p; i--) {
        if (Frame.b[sentaku[0]][i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
      check=false;
    }
    if(check){
      check=true;
      for(int i=sentaku[1];i>=p+1;i--){//��
        Frame.b[sentaku[0]][i]=1;
        Frame.ban[sentaku[0]][i].setIcon(Frame.black_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[1] != 8){
    for(int i=sentaku[1]+1;i<=8;i++){//��
      if (Frame.b[sentaku[0]][i] == 1) {
        tmp = i - sentaku[1]-1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = sentaku[1]+1; i <= p; i++) {
        if (Frame.b[sentaku[0]][i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      check=true;
      for(int i=sentaku[1];i<=p-1;i++){//��
        Frame.b[sentaku[0]][i]=1;
        Frame.ban[sentaku[0]][i].setIcon(Frame.black_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    //+-:���� 8-�`,���C��
    //-+:�E��
    //++:����
    //--:�E��
    if(sentaku[0] != 8 || sentaku[1] != 1){
    for(int i=1;i<=Math.min(8-sentaku[0],sentaku[1]);i++){//����
      if (Frame.b[sentaku[0]+i][sentaku[1]-i] == 1) {
        tmp = i-1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = 1; i <= p; i++) {
        if (Frame.b[sentaku[0]+i][sentaku[1]-i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      check=true;
      for(int i=0;i<=p-1;i++){//����
        Frame.b[sentaku[0]+i][sentaku[1]-i]=1;
        Frame.ban[sentaku[0]+i][sentaku[1]-i].setIcon(Frame.black_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check = false;
    if(sentaku[0] != 1 || sentaku[1] != 8){
    for(int i=1;i<=Math.min(sentaku[0],8-sentaku[1]);i++){//�E��
      if (Frame.b[sentaku[0]-i][sentaku[1]+i] == 1) {
        tmp = i-1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = 1; i <= p; i++) {
        if (Frame.b[sentaku[0]-i][sentaku[1]+i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      for (int i=0; i <= p-1; i++) {
        Frame.b[sentaku[0] - i][sentaku[1] + i] = 1;
        Frame.ban[sentaku[0] - i][sentaku[1] + i].setIcon(Frame.black_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[0] != 8 || sentaku[1] != 1){
    for(int i=1;i<=Math.min(8-sentaku[0],8-sentaku[1]);i++){//����
      if (Frame.b[sentaku[0] + i][sentaku[1] + i] == 1) {
        tmp = i - 1;
        p = i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = 1; i <= p; i++) {
        if (Frame.b[sentaku[0]+i][sentaku[1]+i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      for(int i=0;i<=p-1;i++){
        Frame.b[sentaku[0]+i][sentaku[1]+i]=1;
        Frame.ban[sentaku[0]+i][sentaku[1]+i].setIcon(Frame.black_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[0] != 8 || sentaku[1] != 1){
    for(int i=1;i<=Math.min(sentaku[0],sentaku[1]);i++){//�E��
      if (Frame.b[sentaku[0]-i][sentaku[1]-i] == 1) {
        tmp = i-1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = 1; i <= p; i++) {
        if (Frame.b[sentaku[0]-i][sentaku[1]-i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      for(int i=0;i<=p-1;i++){
        Frame.b[sentaku[0]-i][sentaku[1]-i]=1;
        Frame.ban[sentaku[0]-i][sentaku[1]-i].setIcon(Frame.black_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    return s_check;
  }
  public static boolean white_rule(){
    boolean check=false;
    boolean s_check=false;
    int tmp=0;
    int p=0;
    if(sentaku[0] != 8){
      for (int i = sentaku[0] + 1; i <= 8; i++) { //��
        if (Frame.b[i][sentaku[1]] == 2) {
          tmp = i - sentaku[0] - 1;
          p=i;
          break;
        }
      }
      if (tmp > 0) {
        check = true;
        for (int i = sentaku[0]+1; i <= p; i++) {
          if (Frame.b[i][sentaku[1]] == 0) {
            check = false;
          }
        }
      }else{
        check=false;
      }
    }else {
        tmp = 0;
        check = false;
      }
    if(check){
      for(int i=sentaku[0];i<=p-1;i++){//��
        Frame.b[i][sentaku[1]]=2;
        Frame.ban[i][sentaku[1]].setIcon(Frame.white_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[0] != 1){
    for(int i=sentaku[0]-1;i>=1;i--){//�E
      if (Frame.b[i][sentaku[1]] == 2) {
        tmp = sentaku[0] - i -1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = sentaku[0]-1; i >= p; i--) {
        if (Frame.b[i][sentaku[1]] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
      check=false;
    }
    if(check){
      for(int i=sentaku[0];i>=p+1;i--){//�E
        Frame.b[i][sentaku[1]]=2;
        Frame.ban[i][sentaku[1]].setIcon(Frame.white_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[1] != 1){
    for(int i=sentaku[1]-1;i>=1;i--){//��
      if (Frame.b[sentaku[0]][i] == 2) {
        tmp = sentaku[1] - i -1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = sentaku[1]-1; i >= p; i--) {
        if (Frame.b[sentaku[0]][i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
      check=false;
    }
    if(check){
      check=true;
      for(int i=sentaku[1];i>=p+1;i--){//��
        Frame.b[sentaku[0]][i]=2;
        Frame.ban[sentaku[0]][i].setIcon(Frame.white_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[1] != 8){
    for(int i=sentaku[1]+1;i<=8;i++){//��
      if (Frame.b[sentaku[0]][i] == 2) {
        tmp = i - sentaku[1]-1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = sentaku[1]+1; i <= p; i++) {
        if (Frame.b[sentaku[0]][i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      check=true;
      for(int i=sentaku[1];i<=p-1;i++){//��
        Frame.b[sentaku[0]][i]=2;
        Frame.ban[sentaku[0]][i].setIcon(Frame.white_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    //+-:���� 8-�`,���C��
    //-+:�E��
    //++:����
    //--:�E��
    if(sentaku[0] != 8 || sentaku[1] != 1){
    for(int i=1;i<=Math.min(8-sentaku[0],sentaku[1]);i++){//����
      if (Frame.b[sentaku[0]+i][sentaku[1]-i] == 2) {
        tmp = i-1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = 1; i <= p; i++) {
        if (Frame.b[sentaku[0]+i][sentaku[1]-i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      check=true;
      for(int i=0;i<=p-1;i++){//����
        Frame.b[sentaku[0]+i][sentaku[1]-i]=2;
        Frame.ban[sentaku[0]+i][sentaku[1]-i].setIcon(Frame.white_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check = false;
    if(sentaku[0] != 1 || sentaku[1] != 8){
    for(int i=1;i<=Math.min(sentaku[0],8-sentaku[1]);i++){//�E��
      if (Frame.b[sentaku[0]-i][sentaku[1]+i] == 2) {
        tmp = i-1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = 1; i <= p; i++) {
        if (Frame.b[sentaku[0]-i][sentaku[1]+i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      for (int i=0; i <= p-1; i++) {
        Frame.b[sentaku[0] - i][sentaku[1] + i] = 2;
        Frame.ban[sentaku[0] - i][sentaku[1] + i].setIcon(Frame.white_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[0] != 8 || sentaku[1] != 1){
    for(int i=1;i<=Math.min(8-sentaku[0],8-sentaku[1]);i++){//����
      if (Frame.b[sentaku[0] + i][sentaku[1] + i] == 2) {
        tmp = i - 1;
        p = i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = 1; i <= p; i++) {
        if (Frame.b[sentaku[0]+i][sentaku[1]+i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      check=true;
      for(int i=0;i<=p-1;i++){
        Frame.b[sentaku[0]+i][sentaku[1]+i]=2;
        Frame.ban[sentaku[0]+i][sentaku[1]+i].setIcon(Frame.white_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    if(sentaku[0] != 8 || sentaku[1] != 1){
    for(int i=1;i<=Math.min(sentaku[0],sentaku[1]);i++){//�E��
      if (Frame.b[sentaku[0]-i][sentaku[1]-i] == 2) {
        tmp = i-1;
        p=i;
        break;
      }
    }
    if (tmp > 0) {
      check = true;
      for (int i = 1; i <= p; i++) {
        if (Frame.b[sentaku[0]-i][sentaku[1]-i] == 0) {
          check = false;
        }
      }
    }else{
      check=false;
    }
    }else{
      tmp=0;
    }
    if(check){
      check=true;
      for(int i=0;i<=p-1;i++){
        Frame.b[sentaku[0]-i][sentaku[1]-i]=2;
        Frame.ban[sentaku[0]-i][sentaku[1]-i].setIcon(Frame.white_img);
        s_check=true;
      }
    }
    tmp=0;
    p=0;
    check=false;
    return s_check;
  }

}