package othello;

import javax.swing.UIManager;
import java.awt.*;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 * <p>説明: I want this program to be good at othello!</p>
 * <p>著作権: Copyright (c) 2003-2004 by Sun-Soft</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version Version 0.5
 */

public class zero {
  boolean packFrame = false;

  //アプリケーションのビルド
  public zero() {
    MainFrame frame = new MainFrame();
    //validate() はサイズを調整する
    //pack() は有効なサイズ情報をレイアウトなどから取得する
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //ウィンドウを中央に配置
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }
  //Main メソッド
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new zero();
  }
}