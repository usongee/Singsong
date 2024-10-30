package project1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Cursor;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class JoinPage extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection conn = null;
	JFrame frame;
	private JTextField joinName;
	private JTextField joinId;
	private JTextField joinPw;
	private JTextField joinPwConfirm;
	private JTextField joinEmail;
	private JButton joinIdConfirm;
	private boolean duplicatecheck = false;
	private String duplicateId = "";
	private JLabel duplicatePassword;
	private JComboBox<String> comboBox;
	private JLabel conditionOfId;
	private String emailForm;
	private String joinSex;
	private JPanel radioPanel;
	private JRadioButton man;
	private JRadioButton woman;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	//Launch the application.
	
	   public JPanel getRadioPanel() {
	       if (radioPanel == null) {
	          radioPanel = new JPanel();
	          radioPanel.setBounds(123, 154, 200, 33);
	          radioPanel.setBackground(new Color(254, 241, 184));
	          radioPanel.setLayout(new GridLayout(1, 2));
	          radioPanel.add(getM());

	 //배타적 선택을 위해 ButtonGroup에 두 개의 JRadioButton 추가
	          ButtonGroup group = new ButtonGroup();
	          group.add(getM());
	          radioPanel.add(getW());
	          group.add(getW());  
	       }
	       return radioPanel;
	    }
	   
	 //남자 선택 
	       public JRadioButton getM() {
	          if (man ==  null) {
	             man = new JRadioButton();
	             man.setLocation(0, 154);
	             man.setBackground(Color.WHITE);
	             man.setText("남자");
	             //man.setSelected(true); //기본적으로 선택되도록 설정
	       }
	       return man;
	      }
	       
	       public JRadioButton getW() {
	          if (woman ==  null) {
	             woman = new JRadioButton();
	             woman.setLocation(100, 154);
	             woman.setBackground(Color.WHITE);
	             woman.setText("여자");
	       }
	       return woman;
	      }
	       
	       public void check() {
	    	   
	           //필수 입력값(아이디,비밀번호,이름)을 입력하지 않았을 때 입력하도록
	            if (joinId.getText().equals("") || joinPw.getText().equals("") || joinName.getText().equals("")
	                  || joinEmail.getText().equals("") || joinId.getText().equals("아이디를 입력해주세요.") || joinPw.getText().equals("비밀번호를 입력해주세요.") || joinName.getText().equals("이름을 입력해주세요.")
	                  || joinEmail.getText().equals("이메일을 입력해주세요.")) {
	               JOptionPane.showMessageDialog(null, "모두 입력해주세요");
	            }else {   
	               
	               //성별을 선택하지 않았을 때   성별을 선택하라는 경고 메시지 띄우기            
	              // String joinSex = (String) comboBox.getSelectedItem();
	               if (!man.isSelected() && !woman.isSelected()) {
	                       JOptionPane.showMessageDialog(frame, "성별을 선택해 주세요.", "경고", JOptionPane.WARNING_MESSAGE);
	               }else {               
	                  
	                  //중복확인 하려는 id와 회원가입하려는 id가 다른 경우 회원가입 되지 못하게
	                  if (duplicateId.equals(joinId.getText())) {
	                  } else {duplicatecheck = false;}
	                  
	            //중복확인한 id와 회원가입하려는 id가 다르다면 중복체크를 하도록
	                     if (duplicatecheck == false) {
	                        JOptionPane.showMessageDialog(null, "아이디 중복체크하세요");
	                     }else {
	                        //비밀번호와 비밀번호 확인이 다른 경우, 비밀번호와 비밀번호 확인을 동일하게 작성하세요
	                        if(!joinPw.getText().equals(joinPwConfirm.getText())) {
	                           JOptionPane.showMessageDialog(null, "비밀번호 확인창을 다시 확인하세요.");
	                        }else {
	                           
	                           //이메일이 형식에 맞지 않을 경우
	                           emailForm = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
	                           String emailData = joinEmail.getText();
	                           boolean emailDataForm = Pattern.matches(emailForm, emailData);
	                           
	                           //입력된 문자가 없거나
	                           
	                           if(!emailDataForm) {
	                              JOptionPane.showMessageDialog(null, "사용불가능한 이메일 형식입니다.");
	                           } else {
	                              
	                             
	                           try {
	                              Class.forName("com.mysql.cj.jdbc.Driver");
	                         
	                         //연결하기
	                              conn = DriverManager.getConnection(
	                                 "jdbc:mysql://222.119.100.89:3382/chatdb",
	                                 "chat153",
	                                 "153123"
	                                 );
	                           
	                              
	                              
	                              String gender = man.isSelected() ? "남자" : "여자";
	      
	                              String sql = "INSERT INTO member_table(uname, uid, password, sex, uemail) VALUES (?, ?, ?, ?, ?)";
	                           
	                                    
	                              PreparedStatement pstmt = conn.prepareStatement(sql);
	                              pstmt.setString(1, joinName.getText());
	                              pstmt.setString(2, joinId.getText() );
	                              pstmt.setString(3, joinPw.getText());
	                              pstmt.setString(4, gender);
	                              pstmt.setString(5, joinEmail.getText());
	                              
	                              pstmt.executeUpdate();
	                              
	                              pstmt.close();
	                              
	                               JOptionPane.showMessageDialog(null, "가입 성공! 로그인 페이지로 이동합니다."); // 가입 성공 메시지
	                              
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
	                              
	               
	                           
	                           } catch (ClassNotFoundException e1) {
	                              e1.printStackTrace();
	                           } catch (SQLException e2) {
	                              e2.printStackTrace();
	                           } finally {
	                              if(conn != null) {
	                                 try {
	                                    conn.close();
	                                    
	                                 } catch (SQLException e3) {}
	                              }
	                           }
	                        }
	                     
	                     }
	                  }
	               }
	            }
}

	         
	       
	       public void DoubleCheck() {
	    	   

	    	            if (joinId.getText().equals("")) {
	    	               JOptionPane.showMessageDialog(null, "아이디를 입력해 주세요.");               
	    	            } else {
	    	               String regExp1 = "\\w{3,8}";
	    	               String data1 = joinId.getText();
	    	               boolean result1 = Pattern.matches(regExp1, data1);
	    	               
	    	               if(!result1) {
	    	                  JOptionPane.showMessageDialog(null, "아이디 형식이 맞지 않습니다.");   
	    	               }else {      
	    	               try {
	    	                  Class.forName("com.mysql.cj.jdbc.Driver");
	    	                
	    	                //연결하기
	    	                  conn = DriverManager.getConnection(
	    	                        "jdbc:mysql://222.119.100.89:3382/chatdb",
	    	                        "chat153",
	    	                        "153123"
	    	                        );
	    	                  String sql = "" +
	    	                        "SELECT uname, uid, password,uemail " +
	    	                        "FROM member_table " +
	    	                        "WHERE uid = ?";
	    	                  
	    	                  
	    	                                 
	    	                  PreparedStatement pstmt = conn.prepareStatement(sql);
	    	                  pstmt.setString(1,joinId.getText());
	    	                  
	    	                  ResultSet rs = pstmt.executeQuery();
	    	                  if(rs.next()) {
	    	                     JOptionPane.showMessageDialog(null, "중복된 아이디입니다.");
	    	                     joinId.setText("");
	    	                  } else {
	    	                     JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다.");
	    	                     duplicatecheck = true;
	    	                     duplicateId = joinId.getText();
	    	                  }
	    	                  rs.close();
	    	                     
	    	               
	    	                     
	    	                     
	    	               } catch (ClassNotFoundException e1) {
	    	                  e1.printStackTrace();
	    	               } catch (SQLException e2) {
	    	                  e2.printStackTrace();
	    	                  JOptionPane.showMessageDialog(null, "중복된 아이디가 있습니다.");
	    	               } finally {
	    	                  if(conn != null) {
	    	                     try {
	    	                        conn.close();
	    	                        
	    	                     } catch (SQLException e3) {}
	    	                  }
	    	               }
	    	            }
	    	         }
	    	   
	       }
	    	      
	       
	
	
	 //Launch the application.
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public JoinPage() {
		setResizable(false);
		setTitle("씽쏭");
		setIconImage(Toolkit.getDefaultToolkit().getImage(JoinPage.class.getResource("/project1/image/logo.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				contentPane.requestFocusInWindow();
			}
		});
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 662, 662);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		  frame = new JFrame();
//	      frame.getContentPane().setBackground(new Color(254, 241, 184));
//	      frame.setBounds(100, 100, 662, 932);
//	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	      frame.getContentPane().setLayout(null);
//		setContentPane(contentPane);
		
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (windowSize.width - 600) / 2;
        int height = (windowSize.height - 800) / 2;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(width, height, 600, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(254, 241, 184));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton joinButton = new JButton("");
		joinButton.setIcon(new ImageIcon(JoinPage.class.getResource("/project1/image/Join.png")));
		joinButton.setBorder(null);
		joinButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check();
			}
		});
	      joinButton.setBackground(new Color(254, 241, 184));
	      joinButton.setBounds(113, 658, 150, 40);
	      contentPane.add(joinButton);

	      
	    //취소버튼(로그인창으로 이동)
	      JButton JoinPageCancelButton = new JButton("");
	      JoinPageCancelButton.setBorder(null);
	      JoinPageCancelButton.setIcon(new ImageIcon(JoinPage.class.getResource("/project1/image/Cancel.png")));
	      JoinPageCancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	      JoinPageCancelButton.addActionListener(new ActionListener() {
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
	      JoinPageCancelButton.setBackground(new Color(254, 241, 184));
	      JoinPageCancelButton.setBounds(313, 658, 150, 40);
	      contentPane.add(JoinPageCancelButton);
	      //이벤트 만들기 addMouseListener
	      
	    //성별 라디오 패널
	          JPanel getRadioPanel = new JPanel();
	          getRadioPanel.setBackground(new Color(198, 228, 253));
	          getRadioPanel.setBounds(166, 200, 100, 23);
	          
	          lblNewLabel = new JLabel("");
	          lblNewLabel.setIcon(new ImageIcon(JoinPage.class.getResource("/project1/image/SingSong3.png")));
	          lblNewLabel.setBounds(236, -3, 133, 94);
	          contentPane.add(lblNewLabel);
	          
	          JPanel panel = new JPanel();
	          panel.setBorder(new LineBorder(new Color(171, 174, 179)));
	          panel.setBackground(new Color(255, 251, 230));
	          panel.setBounds(38, 101, 500, 525);
	          contentPane.add(panel);
	          panel.setLayout(null);
	          
	      //중복확인버튼
	      joinIdConfirm = new JButton("");   
	      joinIdConfirm.setBorder(null);
	      joinIdConfirm.setIcon(new ImageIcon(JoinPage.class.getResource("/project1/image/DoubleCheck.png")));
	      joinIdConfirm.setBounds(382, 207, 81, 33);
	      panel.add(joinIdConfirm);
	      joinIdConfirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	      joinIdConfirm.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		DoubleCheck();
	      	}
	      });
	      joinIdConfirm.setBackground(new Color(255, 251, 230));
	      
	      //이름 텍스트필드
	      joinName = new JTextField();
	      joinName.setBounds(45, 78, 325, 48);
	      panel.add(joinName);
	      joinName.setBorder(new LineBorder(new Color(171, 174, 179)));
	      joinName.setText("이름을 입력해주세요.");
	      joinName.addFocusListener(new FocusAdapter() {
	      	@Override
	      	public void focusGained(FocusEvent e) {
	      		
	              if (joinName.getText().equals("이름을 입력해주세요.")) {
	            	  joinName.setText("");
	              } 
	      		
	      	}
	      	@Override
	      	public void focusLost(FocusEvent e) {
	      		
	 	       if (joinName.getText().isEmpty()) {
	 	    	  joinName.setText("이름을 입력해주세요.");
		       }
	      		
	      	}
	      });
	      
	      joinName.setColumns(10);
	      
	      //성별 문구
	      JLabel titleSex = new JLabel("성별");
	      titleSex.setBounds(45, 146, 66, 51);
	      panel.add(titleSex);
	      titleSex.setBackground(new Color(244, 164, 96));
	      titleSex.setBorder(new EmptyBorder(0, 0, 0, 0));
	      titleSex.setHorizontalAlignment(SwingConstants.LEFT);
	      titleSex.setFont(new Font("굴림", Font.PLAIN, 12));
	      
	      //아이디 텍스트필드
	      joinId = new JTextField();
	      joinId.setBounds(45, 207, 325, 48);
	      panel.add(joinId);
	      joinId.setBorder(new LineBorder(new Color(171, 173, 179)));
	      joinId.setText("아이디를 입력해주세요.");
	      joinId.addKeyListener(new KeyAdapter() {
	      	@Override
	      	public void keyReleased(KeyEvent e) {
	      		
	            String regExp = "\\w{3,8}";          
	            String data = joinId.getText();
	            boolean result = Pattern.matches(regExp, data);   //아이디 텍스트필드에서 글자를 입력할 때의 조건
	            
	            if(result) {                           //아이디 입력 조건에 따라 안내문구 레이블에 글자를 출력하도록
	               conditionOfId.setText("사용 가능한 ID 형식입니다.");   
	            } else {
	               conditionOfId.setText("Id는 영문 숫자 3~8자리로 입력해야합니다.");
	            }   
	             
	      	}
	      });
	      joinId.addFocusListener(new FocusAdapter() {
	      	@Override
	      	public void focusGained(FocusEvent e) {
	      		
	             if (joinId.getText().equals("아이디를 입력해주세요.")) {
	                 joinId.setText("");
	              } 
	      		
	      	}
	      	@Override
	      	public void focusLost(FocusEvent e) {
	      		
	             if (joinId.getText().isEmpty()) {
	                 joinId.setText("아이디를 입력해주세요.");
	              }
	      		
	      	}
	      });
	      joinId.setColumns(10);
	      
	      //비밀번호 텍스트필드
	      joinPw = new JTextField();
	      joinPw.setBounds(45, 304, 325, 48);
	      panel.add(joinPw);
	      joinPw.setBorder(new LineBorder(new Color(171, 173, 179)));
	      joinPw.addKeyListener(new KeyAdapter() {
	      	@Override
	      	public void keyReleased(KeyEvent e) {
	      		
	             if(joinPwConfirm.getText().equals(joinPw.getText())) {
	                 duplicatePassword.setText("일치");
	                 duplicatePassword.setForeground(new Color(0, 255, 64));
	                   } else {
	                      duplicatePassword.setText("일치안함");
	                      duplicatePassword.setForeground(new Color(255, 0, 0));
	                   } 
	      		
	      	}
	      });
	      joinPw.addFocusListener(new FocusAdapter() {
	      	@Override
	      	public void focusGained(FocusEvent e) {
	      		
	             if (joinPw.getText().equals("비밀번호를 입력해주세요.")) {
	                 joinPw.setText("");
	              } 
	      		
	      	}
	      	@Override
	      	public void focusLost(FocusEvent e) {
	      		
	             if (joinPw.getText().isEmpty()) {
	                 joinPw.setText("비밀번호를 입력해주세요.");
	              }
	      		
	      	}
	      });
	      joinPw.setText("비밀번호를 입력해주세요.");
	      joinPw.setColumns(10);
	      
	      //비밀번호 확인 텍스트 필드
	      joinPwConfirm = new JTextField();
	      joinPwConfirm.setBounds(45, 370, 325, 48);
	      panel.add(joinPwConfirm);
	      joinPwConfirm.setBorder(new LineBorder(new Color(171, 173, 179)));
	      joinPwConfirm.addKeyListener(new KeyAdapter() {
	      	@Override
	      	public void keyReleased(KeyEvent e) {
	      		
	             if(joinPwConfirm.getText().equals(joinPw.getText())) {
	                 duplicatePassword.setText("일치");
	                 duplicatePassword.setForeground(new Color(0, 255, 64));
	                   } else {
	                      duplicatePassword.setText("일치안함");
	                      duplicatePassword.setForeground(new Color(255, 0, 0));
	                   }
	      		
	      	}
	      });
	      joinPwConfirm.addFocusListener(new FocusAdapter() {
	      	@Override
	      	public void focusGained(FocusEvent e) {
	      		
	             if (joinPwConfirm.getText().equals("비밀번호를 다시 입력하세요.")) {
	                 joinPwConfirm.setText("");
	              }
	 
	      	}
	      	@Override
	      	public void focusLost(FocusEvent e) {
	      		
	             if (joinPwConfirm.getText().isEmpty()) {
	                 joinPwConfirm.setText("비밀번호를 다시 입력하세요.");
	              }
	      		
	      	}
	      });
	      joinPwConfirm.setText("비밀번호를 다시 입력하세요.");
	      joinPwConfirm.setColumns(10);
	      
	      //이메일 텍스트 필드
	      joinEmail = new JTextField();
	      joinEmail.setBounds(45, 439, 325, 48);
	      panel.add(joinEmail);
	      joinEmail.setBorder(new LineBorder(new Color(171, 173, 179)));
	      joinEmail.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		
	      		check();
	      		
	      	}
	      });
	      joinEmail.addFocusListener(new FocusAdapter() {
	      	@Override
	      	public void focusGained(FocusEvent e) {
	      		
	              if (joinEmail.getText().equals("이메일을 입력해주세요.")) {
	            	  joinEmail.setText("");
	              } 
	      		
	      	}
	      	@Override
	      	public void focusLost(FocusEvent e) {
	      		
	 	       if (joinEmail.getText().isEmpty()) {
	 	    	  joinEmail.setText("이메일을 입력해주세요.");
		       }
	      		
	      	}
	      });
	      joinEmail.setText("이메일을 입력해주세요.");
	      joinEmail.setColumns(10);
	      
	      lblNewLabel_1 = new JLabel(" 회원가입");
	      lblNewLabel_1.setBounds(45, 24, 116, 33);
	      panel.add(lblNewLabel_1);
	      lblNewLabel_1.setBorder(new MatteBorder(0, 2, 0, 0, (Color) new Color(0, 0, 0)));
	      lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 22));
	      
	      
	      
	      
	      //아이디 입력 조건 안내 문구 레이블
	      conditionOfId = new JLabel("Id는 영문 숫자 3~8자리로 입력해야합니다.");
	      conditionOfId.setBounds(55, 265, 276, 26);
	      panel.add(conditionOfId);
	      panel.add(getRadioPanel());
	      
	      JLabel lblNewLabel_2 = new JLabel("");
	      lblNewLabel_2.setBounds(45, 146, 325, 48);
	      panel.add(lblNewLabel_2);
	      lblNewLabel_2.setBorder(new LineBorder(new Color(171, 174, 179)));
	      lblNewLabel_2.setOpaque(true);
	      lblNewLabel_2.setBackground(Color.WHITE);
	      
	      
	      //중복 비밀번호 안내 문구 레이블
	      duplicatePassword = new JLabel();
	      duplicatePassword.setBounds(384, 370, 104, 48);
	      panel.add(duplicatePassword);
	          
	}
	    	      }
	    	      
	    	      
	      
	      
		
	


