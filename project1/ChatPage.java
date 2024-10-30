package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.text.BadLocationException;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;




import javax.swing.JScrollPane;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


         // 두둥 대망의 채팅페이지 입니다.
         // 근데 사실 MainPage가 더 복잡합니다 ...ㅎㅎ
		 // 저는 오늘 아침 떡국을 먹었습니다. 
         // 안물어 봤다구요 ? 예 저도 압니다. 
public class ChatPage extends JFrame {
	
	
    //serialVersionUID .... 이건 뭘까요
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//채팅 메세지를 보내는 칸입니다.
	private JTextField SendtextField;
	//ChatContent는 클래스로 선언되어 있습니다. 우리는 ChatContent객체를 생성하여 db테이블인 chatcontent에 들어갈 내용을 담을 것입니다.
	public static ChatContent chat ;
	public static Connection conn = null;
	
	//왼쪽 오른쪽 정렬을 위해 사용한 것입니다.
	// StyledDocument는 저희가 배우지 않은 내용이니 그냥 정렬을 위해 사용했구나 정도만 아셔도 될 것 같습니다.
	private static StyledDocument document;
	//TextArea를 처음에 사용했으나 정렬의 한계를 느껴 JTextPane로 바꿨습니다.
	private static JTextPane ChattextPane;


    //매우 많아 보이지만 사실 사용되는 부분은 하나 입니다.
	//글자수에 따라 줄바꿈을 이렇게 수동적 조건식으로 넣어준 것입니다.
	//글자 수에 따라 조건에 맞는 부분을 찾아갑니다.
	//여기서 우리가 채팅 메세지를 매개 변수로 넣어주면 chat에는 해당 정보가 저장됩니다.
	private static void LineChange(String message) {
		
		//우선 메세지의 길이를 저장합니다.
		int count = message.length();
		//System.out.println(count);
		if(count>=80) {
			//80자가 넘어가면 error에 담긴 메세지가 출력됩니다.
			String error = "너무 많은 글자를 입력하셨습니다.";
			chat = new ChatContent(LoginPage.ID , error, LoginPage.MyName ,MainPage.whofriendId);		
		}else if(count>60) {
			//80이하 60가 넘어가면 각 조건에 따라 메세지를 잘라 2번째줄, 3번째 줄 등등으로 출력됩니다.
			//즉 줄바꿈이 일어나는 것 처럼 보이긴 하지만 메세지를 조각내어 붙이는 작업을 한 것입니다.
			//밑에 다른 조건들도 마찬가지입니다.
			String message1 = message.substring(0, 19);
			String message2 = message.substring(20, 39);
			String message3 = message.substring(40, 59);
			String message4 = message.substring(60);
			String TotalMessage = message1 + "\n" + message2  + "\n" + message3 + "\n" + message4;
			chat = new ChatContent(LoginPage.ID , TotalMessage, LoginPage.MyName ,MainPage.whofriendId);
		}else if(count>=40) {	

			String message1 = message.substring(0, 19);
			String message2 = message.substring(20, 39);
			String message3 = message.substring(40);
			String TotalMessage = message1 + "\n" + message2  + "\n" + message3;
			chat = new ChatContent(LoginPage.ID , TotalMessage, LoginPage.MyName ,MainPage.whofriendId);
		}else if(count>=20) {

			String message1 = message.substring(0, 19);
			String message2 = message.substring(20);
			String TotalMessage = message1 + "\n" + message2;
			chat = new ChatContent(LoginPage.ID , TotalMessage, LoginPage.MyName ,MainPage.whofriendId);
		}else {

			String TotalMessage = message;
			chat = new ChatContent(LoginPage.ID , TotalMessage, LoginPage.MyName ,MainPage.whofriendId);
			
		}
		
	}
	
    //창현아 이건 니가 설명해줘
	//아마 오른쪽 왼쪽 정렬을 위한 것입니다.
    private static void textPrint(String string, AlignEnum alignEnum) {
        try {
            SimpleAttributeSet attributes = new SimpleAttributeSet();
            StyleConstants.setFontSize(attributes, 20);

            // 스타일을 적용할 위치를 기존 텍스트의 끝으로 설정
            int insertPosition = document.getLength();

            // 새로운 줄 삽입
            string += "\n";

            // 스타일 적용
            document.insertString(insertPosition, string, attributes);

            // 정렬 스타일 적용
            if (alignEnum == AlignEnum.RIGHT) {
                StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_RIGHT);
            } else if (alignEnum == AlignEnum.LEFT) {
                StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_LEFT);
            }

            // 스타일 적용한 위치로부터 새로운 줄에 대한 정렬 스타일 적용
            document.setParagraphAttributes(insertPosition, string.length(), attributes, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
	
    
    //말 그대로 채팅을 저희 db인 chatcontent에 보내는 메세드 입니다.
	public void Send() {
        try {
        	
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://222.119.100.89:3382/chatdb",
					"chat153",
					"153123"
					);
        	
            String sql = ""+
                  "INSERT INTO chatcontent (chat, name, datetime, myid, toid )VALUES (?, ?, now(), ? ,?)";
            
            
            //LineChange에서 chat에 대한 객체정보가 생성되었습니다.
            //해당 내용들을 알맞게 insert작업 해줍니다.
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, chat.getChatcontent());
            pstmt.setString(2, chat.getName());
            pstmt.setString(3, chat.getId());
            pstmt.setString(4, chat.getToname());         
            pstmt.executeUpdate();
            pstmt.close();
            
            
            //보내고 난후 공백으로 초기화
            SendtextField.setText("");
         }catch (Exception e1) {
            e1.printStackTrace();
         }finally {
			if(conn != null) {
				
				try {
					conn.close();
				}catch(SQLException e) {}
			}
	}
	}

	//main구동 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatPage frame = new ChatPage();
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
	
	//생성자 선언입니다.
	public ChatPage() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				SendtextField.requestFocusInWindow();
			}
		});
		setResizable(false);
		setTitle("씽쏭");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChatPage.class.getResource("/project1/image/logo.png")));
		
//	    ChattextPane.setWrapStyleWord(true);
//	    ChattextPane.setLineWrap(true);

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (windowSize.width - 600) / 2;
        int height = (windowSize.height - 800) / 2;
	    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(width, height, 635, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(254, 241, 184));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(130, 135, 144)));
		scrollPane.setMaximumSize(new Dimension(596, 576));
		scrollPane.setFont(new Font("굴림", Font.PLAIN, 20));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 71, 596, 588);
		contentPane.add(scrollPane);
		
		StyleContext context = new StyleContext();
	    document = new DefaultStyledDocument(context);
		JTextPane ChattextPane = new JTextPane(document);
		ChattextPane.setBackground(new Color(255, 251, 230));
		ChattextPane.setEditable(false);
		ChattextPane.setMaximumSize(new Dimension(596, 576));
		ChattextPane.setFont(new Font("굴림", Font.PLAIN, 20));
		scrollPane.setViewportView(ChattextPane);
		

		
		  //쓰레드를 생성합니다.
		  //우리는 채팅을 보내는 작업과 채팅 내역을 지속적으로 업데이트해 textpanel에서 갱신되게 해야하니다.
		  //즉 2가지의 다중작업을 해야하니 쓰레드가 필요합니다.
		  //해당 쓰레드는 textpanel에서 지속적으로 대화내용을 갱신하게합니다.
	      Thread thread = new Thread() {
	          @Override
	          public void run() {
	
	             //무한적으로 계속 갱신되게 하기 위한 반복문
	             while(true) {
	                //값이 새로 리셋될때마다 스크롤이 위로 올라가는 것을 고정
	            	 ChattextPane.setCaretPosition(ChattextPane.getDocument().getLength());
	       
	             try {
	            	 //1초는 넘 빨라용 
	                Thread.sleep(3000);
	                //요걸 안해주면 대화내용이 안 업서지
	                ChattextPane.setText("");
	      
	             } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	             }
	             Connection conn = null;
	             try {
//	     			Connection conn = null;
	     			try {
						Class.forName("com.mysql.cj.jdbc.Driver");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	    			conn = DriverManager.getConnection(
	    					"jdbc:mysql://222.119.100.89:3382/chatdb",
	    					"chat153",
	    					"153123"
	    					);
	            	//내 아이디 혹은 상대방 아이디가 있는 대화내용 필터
	                String sql = ""+" SELECT myid, num, chat, name, datetime, toid FROM chatcontent  "+ " WHERE "+" (myid = ?  AND toid = ?) OR (myid = ?  AND toid = ?)"+" order by datetime"; 

	                PreparedStatement pstmt = conn.prepareStatement(sql);
	                pstmt.setString(1,LoginPage.ID);
	                pstmt.setString(2,MainPage.whofriendId);
	                pstmt.setString(3,MainPage.whofriendId);
	                pstmt.setString(4,LoginPage.ID);
	                ResultSet rs = pstmt.executeQuery();
	                while(rs.next()) {
	                   rs.getString("chat");
	                   rs.getString("name");
	                   rs.getString("num");
	                   rs.getString("datetime");
	                   rs.getString("myid");
	                   	                   
	                   //만약 myid와 내 아이디와 일치한다는 것은 왼쪽에 대화가 출력되어야 한다는 말이므로 이렇게 구성
	                   if(rs.getString("myid").equals(LoginPage.ID)) {
	                	   String message =rs.getString("datetime")+", <"+LoginPage.MyName+">"+" : \n"+rs.getString("chat")+"\n"+" ";
	                	   ChatPage.textPrint( message , AlignEnum.LEFT);
	                	   
            	   
	                	 //만약 myid가 상대아이디와 일치한다는 것은 오른쪽에 대화가 출력되어야 한다는 말이므로 이렇게 구성   
	                   }else {
	                	   String message =rs.getString("datetime")+", <"+MainPage.YouName+">"+" : \n"+rs.getString("chat")+"\n"+" ";
	                	   ChatPage.textPrint( message , AlignEnum.RIGHT);
  
	                   }
	                
	                }
	                rs.close();
	                pstmt.close();
	       
	             }catch(SQLException e) {
	                
	                e.printStackTrace();
	                
	             }finally {
	     			if(conn != null) {
	    				try {
	    					conn.close();			
	    					//System.out.println("연결끊기");
	    				}catch(SQLException e) {}
	    			}
	    	}
	             
	             }            
	          }
	          
	       };
	       
	       thread.start();
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(new Color(253, 233, 138));
		panel.setBounds(12, 669, 596, 88);
		contentPane.add(panel);
		panel.setLayout(null);
		
		SendtextField = new JTextField();
		SendtextField.setBounds(12, 10, 491, 63);
		panel.add(SendtextField);
		SendtextField.setBorder(new LineBorder(new Color(171, 173, 179)));
		SendtextField.setFont(new Font("굴림", Font.PLAIN, 20));
		
		//enter 했을때 이벤트
		SendtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//해당 내용을 받아 message에 넣어줌 
				String message = SendtextField.getText();
				
				//LineChange에 message를 넣어 chat 객체가 변경됨
				LineChange(message);
				
				//db인 chatcontent에 보내는 메세드 입니다.
				Send();
			}
		});
		SendtextField.setColumns(10);
		
		
		//전송 버튼 눌렀을 때 이벤트
		JButton SendButton = new JButton("");
		SendButton.setIcon(new ImageIcon(ChatPage.class.getResource("/project1/image/Send.png")));
		SendButton.setHorizontalTextPosition(SwingConstants.LEFT);
		SendButton.setBorder(null);
		SendButton.setBounds(521, 10, 63, 63);
		panel.add(SendButton);
		SendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		SendButton.setBackground(new Color(253, 233, 138));
		SendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//해당 내용을 받아 message에 넣어줌 
				String message = SendtextField.getText();
				
				//LineChange에 message를 넣어 chat 객체가 변경됨
				LineChange(message);
				
				//db인 chatcontent에 보내는 메세드 입니다.
				Send();
			}
		});
		SendButton.setFont(new Font("한컴 고딕", Font.PLAIN, 30));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(new Color(253, 233, 138));
		panel_1.setBounds(12, 10, 596, 51);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton ExitButton = new JButton("ㅣ나가기");
		ExitButton.setBounds(501, 10, 83, 31);
		panel_1.add(ExitButton);
		ExitButton.setBorder(null);
		ExitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ExitButton.setBackground(new Color(253, 233, 138));
		ExitButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				thread.stop();
				
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
			}
		});
		ExitButton.setFont(new Font("한컴 고딕", Font.PLAIN, 20));
		
		
		//MaInPage에서 친구의 이름과 LoginPage에서 제이름을 받아와 구성합니다.
		JLabel MyNameLabel = new JLabel(LoginPage.MyName+"님과 "+MainPage.YouName+"님의 채팅창");
		MyNameLabel.setBounds(0, 2, 489, 49);
		panel_1.add(MyNameLabel);
		MyNameLabel.setBackground(new Color(254, 241, 184));
		
		MyNameLabel.setFont(new Font("굴림", Font.PLAIN, 30));
		MyNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
	}
}
