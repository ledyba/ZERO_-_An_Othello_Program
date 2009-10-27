package zero;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.*;
import javax.swing.JTextArea;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 *
 * <p>説明: ログ表示用フレームです</p>
 *
 * <p>著作権: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>会社名: ψ（プサイ）の興味関心空間</p>
 *
 * @author PSI
 * @version 1.0
 */
public class LogFrame extends JFrame {
    private BorderLayout borderLayout1 = new BorderLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea LogArea = new JTextArea();
    public static final Image WinIcon = Toolkit.getDefaultToolkit().createImage(zero.
            MainFrame.class.getResource("icon.png"));
    public LogFrame() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(borderLayout1);
        this.setTitle("Log");
        this.setIconImage(WinIcon);
        LogArea.setEditable(false);
        this.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(LogArea);
        LogArea.setText("Log");
    }
    public void addLog(String str){
        LogArea.append(str);
    }
}
