package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JPasswordField;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextField IDtextField;
	private JPasswordField passwordField;
	public static String ID ;
	public static String MyName ;
	
	
	public void myName() {
		Connection conn = null;
		try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					
					conn = DriverManager.getConnection(
							"jdbc:mysql://"
							+ ":3382/chatdb",
							"chat153",
							"153123"
							);
					
					String sql = ""+
							"SELECT uname "+
							"FROM MEMBER_TABLE "+
							"WHERE uid =?";
					
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, ID);
					
					ResultSet rs = pstmt.executeQuery();
					
					if(rs.next()) {
						MyName=rs.getString("uname");
						
						
					}
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			if(conn != null) {
				
				try {
					conn.close();
				}catch(SQLException e) {}
			}
			
		}
		
	}
	
	
	public void login() {
		
        if (IDtextField.getText().equals("") || passwordField.getText().equals("")
                || IDtextField.getText().equals("아이디") || passwordField.getText().equals("비밀번호비밀번호")) 
             {JOptionPane.showMessageDialog(null, "모두 입력해주세요");
          }else {
		
		Connection conn = null;
        try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://222.119.100.89:3382/chatdb",
					"chat153",
					"153123"
					);
			
			String sql = ""+
					"SELECT uid, password, uname "+
					"FROM MEMBER_TABLE "+
					"WHERE uid =?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, IDtextField.getText());
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				
				String userId = rs.getString("uid");
				String userpassword = rs.getString("password");
				
				 if (userpassword.equals(passwordField.getText())) {
                     //"로그인성공" 
                     
					 ID = rs.getString("uid");
					 MyName  = rs.getString("uname");
					 
					 EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									MainPage frame = new MainPage();
									frame.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					 dispose();
                 
                  } else {
                     JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");               
                    }                  
               } else {
                  JOptionPane.showMessageDialog(null, "아이디가 존재하지 않습니다.");      
            }
               rs.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			if(conn != null) {
				
				try {
					conn.close();
				}catch(SQLException e) {}
			}
	}
          }	
	
	}

	
	
	public static void main(String[] args) {

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
	}

	/**
	 * Create the frame.
	 */
	public LoginPage() {
		setResizable(false);
		setTitle("씽쏭");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				contentPane.requestFocusInWindow();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginPage.class.getResource("/project1/image/logo.png")));
		

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (windowSize.width - 600) / 2;
        int height = (windowSize.height - 800) / 2;

        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(width, height, 600, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(254, 241, 184));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("");
		titleLabel.setIcon(new ImageIcon(LoginPage.class.getResource("/project1/image/SingSong.png")));
		titleLabel.setBorder(null);
		titleLabel.setOpaque(true);
		titleLabel.setBackground(new Color(254, 241, 184));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("굴림", Font.PLAIN, 50));
		titleLabel.setBounds(0, 91, 574, 178);
		contentPane.add(titleLabel);
		
		IDtextField = new JTextField();
		IDtextField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		IDtextField.setBounds(100, 346, 400, 50);
		contentPane.add(IDtextField);
		IDtextField.setColumns(10);
		
		
		////포커스가 떠날 때 칸이 공백이라면 초기값인 "비밀번호를 다시 입력하세요" 보이기
		IDtextField.addFocusListener(new FocusAdapter() {
          @Override
          public void focusGained(FocusEvent e) {
              if (IDtextField.getText().equals("아이디")) {
            	  IDtextField.setText("");
              } 
           }
       });
		
		IDtextField.addFocusListener(new FocusAdapter() {
	          @Override
	          public void focusLost(FocusEvent e) {
	              if (IDtextField.getText().isEmpty()) {
	            	  IDtextField.setText("아이디");

	              }
	          }
	       });
		IDtextField.addActionListener(new ActionListener() {
	         @Override 
	         public void actionPerformed(ActionEvent e) {
	            passwordField.requestFocus();
	         }
	      });
		
		passwordField = new JPasswordField();
		passwordField.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		
		passwordField.addFocusListener(new FocusAdapter() {
	          @Override
	          public void focusGained(FocusEvent e) {
	              if (passwordField.getText().equals("비밀번호비밀번호")) {
	            	  passwordField.setText("");
	              } 
	           }
			@Override
			public void focusLost(FocusEvent e) {
			       if (passwordField.getText().isEmpty()) {
			    	   passwordField.setText("비밀번호비밀번호");
			       }
			}
	       });
		passwordField.setText("비밀번호비밀번호");

		
		
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		passwordField.setBounds(100, 406, 400, 50);
		contentPane.add(passwordField);
		
		
		
		JButton LoginButton = new JButton("");
		LoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		LoginButton.setBorder(null);
		LoginButton.setOpaque(false);
		LoginButton.setIcon(new ImageIcon(LoginPage.class.getResource("/project1/image/Login.png")));
		LoginButton.setBackground(new Color(254, 241, 184));
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		LoginButton.setFont(new Font("굴림", Font.PLAIN, 30));
		LoginButton.setBounds(125, 466, 150,40 );
		contentPane.add(LoginButton);
		
		JButton JoinButton = new JButton("");
		JoinButton.setBorder(null);
		JoinButton.setIcon(new ImageIcon(LoginPage.class.getResource("/project1/image/Join.png")));
		JoinButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		JoinButton.setBackground(new Color(254, 241, 184));
		JoinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JoinPage frame = new JoinPage();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				

			      dispose();   
				
				
				
			}
		});
		JoinButton.setFont(new Font("굴림", Font.PLAIN, 30));
		JoinButton.setBounds(325, 466, 150, 40);
		contentPane.add(JoinButton);
		
		JButton findPwButton = new JButton("비밀번호 찾기");
		findPwButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		findPwButton.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		findPwButton.setBackground(new Color(254, 241, 184));
		findPwButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							FindPw frame = new FindPw();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				dispose();
				
			}
		});
		findPwButton.setBounds(306, 691, 94, 41);
		contentPane.add(findPwButton);
		
		JButton findIdButton = new JButton("아이디 찾기");
		findIdButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		findIdButton.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		findIdButton.setBackground(new Color(254, 241, 184));
		findIdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							FindId frame = new FindId();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				dispose();
				
			}
		});
		findIdButton.setBounds(200, 691, 94, 41);
		contentPane.add(findIdButton);
	}
}
