package project1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;
import java.awt.Cursor;

public class CorrectPw extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField NewPWtextField;
	private JTextField NewPWtextField2;
	private JLabel DuplicatePwLabel;

	/**
	 * Launch the application.
	 */
	
	
	public void Change() {
		//0. 텍스트창을 글자로 인식하지 못하게 하기
		//1. 비밀번호가 같지 않을 경우 같게 만들어달라는 경고창 띄우기
		//	 데이터베이스 연결..
		//2. 기존의 비밀번호와 같다면 기존의 비밀번호와 다른 비밀번호를 작성하라는 문구 띄우기
		//3. 비밀번호 수정하기
		
		if(NewPWtextField.getText().equals("비밀번호를 입력해주세요.")||NewPWtextField2.getText().equals("비밀번호를 입력해주세요.")) {
			JOptionPane.showMessageDialog(contentPane, "새로운 비밀번호를 입력해 주세요", "경고", JOptionPane.WARNING_MESSAGE);
		}else {
			if(!NewPWtextField.getText().equals(NewPWtextField2.getText())) {
			 JOptionPane.showMessageDialog(contentPane, "비밀번호가 일치하지 않습니다", "경고", JOptionPane.WARNING_MESSAGE);
			}else {
				try {
					dbConnection.dbconn();
					
					String sql = "" +
							"SELECT uid, password " +
							"FROM member_table " +
							"WHERE uid = ?";
					
					System.out.println("연결성공");
										
					PreparedStatement pstmt = dbConnection.conn.prepareStatement(sql);
					pstmt.setString(1,FindPw.FINDID);
					
					
					ResultSet rs = pstmt.executeQuery();
					if(rs.next()) {
						String checkpw = rs.getString("password");
						if (NewPWtextField2.getText().equals(checkpw)) {
							JOptionPane.showMessageDialog(null, "기존의 비밀번호와 다른 비밀번호를 입력해주세요");
						} else {
							
							
								String sql2 = "UPDATE member_table SET password =? WHERE uid =?"; 
								System.out.println("연결성공123");
								
								PreparedStatement pstmt2 = dbConnection.conn.prepareStatement(sql2);
								pstmt2.setString(1, NewPWtextField.getText());
								pstmt2.setString(2, FindPw.FINDID);
								pstmt2.executeUpdate();
								System.out.println("연결성공4");
								
								
								pstmt2.close();
								JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다.");
								EventQueue.invokeLater(new Runnable() {
									public void run() {
										try {
											LoginPage frame = new LoginPage();
											frame.setVisible(true);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								});
								dispose();
								System.out.println("연결성공5");
						}	
						
					}
					rs.close();
					pstmt.close();

					

					
				
					
							
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally {
					if(dbConnection.conn != null) {
						
						try {
							dbConnection.conn.close();
						}catch(SQLException e) {}
					}
					
				}
				
			};
				
			
			}
		}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CorrectPw frame = new CorrectPw();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CorrectPw() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(CorrectPw.class.getResource("/project1/image/logo.png")));
		setTitle("씽쏭");
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (windowSize.width - 600) / 2;
        int height = (windowSize.height - 500) / 2;
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
				contentPane.requestFocusInWindow();
				
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(width, height, 600, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(254, 241, 184));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		NewPWtextField = new JTextField();
		NewPWtextField.setBorder(new LineBorder(new Color(171, 173, 179)));
		NewPWtextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(NewPWtextField2.getText().equals(NewPWtextField.getText())) {
					DuplicatePwLabel.setText("일치");
					DuplicatePwLabel.setForeground(new Color(0, 255, 64));
	               } else {
	            	   DuplicatePwLabel.setText("일치안함");
	            	   DuplicatePwLabel.setForeground(new Color(255, 0, 0));
	               }
				
			}
		});
		NewPWtextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				 if (NewPWtextField.getText().equals("비밀번호를 입력해주세요.")) {
					 NewPWtextField.setText("");
				 } 
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				
				 if (NewPWtextField.getText().isEmpty()) {
					 NewPWtextField.setText("비밀번호를 입력해주세요.");
	
				 }	
				
			}
		});
		NewPWtextField.setText("비밀번호를 입력해주세요.");
		NewPWtextField.setBounds(61, 150, 368, 53);
		contentPane.add(NewPWtextField);
		NewPWtextField.setColumns(10);
		
		NewPWtextField2 = new JTextField();
		NewPWtextField2.setBorder(new LineBorder(new Color(171, 173, 179)));
		NewPWtextField2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Change();
				
			}
		});
		NewPWtextField2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(NewPWtextField2.getText().equals(NewPWtextField.getText())) {
					DuplicatePwLabel.setText("일치");
					DuplicatePwLabel.setForeground(new Color(0, 255, 64));
	               } else {
	            	   DuplicatePwLabel.setText("일치안함");
	            	   DuplicatePwLabel.setForeground(new Color(255, 0, 0));
	               }
				
			}
		});
		NewPWtextField2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				 if (NewPWtextField2.getText().equals("비밀번호를 다시 입력해주세요.")) {
					 NewPWtextField2.setText("");
				 } 
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				
				 if (NewPWtextField2.getText().isEmpty()) {
					 NewPWtextField2.setText("비밀번호를 다시 입력해주세요.");

				 }
				
			}
		});
		NewPWtextField2.setText("비밀번호를 다시 입력해주세요.");
		NewPWtextField2.setColumns(10);
		NewPWtextField2.setBounds(61, 213, 368, 53);
		contentPane.add(NewPWtextField2);
		
		JButton ChangeButton = new JButton("");
		ChangeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ChangeButton.setBackground(new Color(254, 241, 184));
		ChangeButton.setBorder(null);
		ChangeButton.setIcon(new ImageIcon(CorrectPw.class.getResource("/project1/image/ChangePW.png")));
		ChangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Change();
				
			}
		});
		ChangeButton.setBounds(61, 276, 150, 40);
		contentPane.add(ChangeButton);
		
		JButton CancelButton = new JButton("");
		CancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		CancelButton.setIcon(new ImageIcon(CorrectPw.class.getResource("/project1/image/Cancel.png")));
		CancelButton.setBackground(new Color(254, 241, 184));
		CancelButton.setBorder(null);
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							LoginPage frame = new LoginPage();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				dispose();
				
			}
		});
		CancelButton.setBounds(279, 276, 150, 40);
		contentPane.add(CancelButton);
		
		DuplicatePwLabel = new JLabel();
		DuplicatePwLabel.setBounds(436, 213, 138, 53);
		contentPane.add(DuplicatePwLabel);
		
		JLabel lblNewLabel_1 = new JLabel(" 비밀번호 수정");
		lblNewLabel_1.setFont(new Font("한컴 고딕", Font.BOLD, 19));
		lblNewLabel_1.setBorder(new MatteBorder(0, 2, 0, 0, (Color) new Color(23, 23, 23)));
		lblNewLabel_1.setBounds(61, 92, 125, 48);
		contentPane.add(lblNewLabel_1);
		

		

	}
}
