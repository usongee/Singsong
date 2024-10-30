package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;



public class MainPage extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//채팅할 사람의 이름 필드
	static String whofriendChat;
	
	//채팅할 사람의 아이디 필드
	static String whofriendId; 
	
	//해당 페이지의 콘텐츠패널
	private JPanel contentPane;
	
	//클래스로 ProFileList를 만들어 놓았습니다. 해당 ArrayList에는 pProfileList클래스를 활용한 친구의 이름과 아이디가 들어갑니다.
	private ArrayList<ProFileList> pProfileList;
	
	//채팅할 사람의 이름 필드(whofriendChat와 같습니다. 하나는 없어도 되지만 삭제했다가 꼬일까봐 놔두었습니다. 무시해도됨) 
	public static String YouName;
	
	//자신의 상태메세지 담을 필드
	public static String msg;
	
	//친구들의 상태메세지를 담을 리스트 입니다.
	public ArrayList <String> StateMsg = new ArrayList <String>();
	
	//이 부분이 조금 어려울 수 있습니다. 클래스 맨 밑에  DefaultListCellRenderer 내부 클래스와 createImageMap 필드를 활용하기 위한 Map입니다. 
	private final Map<String, ImageIcon> imageMap;
	
	//자기 프로필 사진 필드
	private JLabel MyImageLabel;

	
	//제 프로필 사진을 세팅하기 위한 메서드입니다. 로그인 페이지에서 가져온 아이디와 대조하여 sex가 남자냐 여자냐에 따라 프로필 사진 출력
	public void MyIcon() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://222.119.100.89:3382/chatdb",
					"chat153",
					"153123"
					);
	         String sql = "" + "SELECT uname, uid, password, sex " + " FROM  member_table WHERE "
	                               +" uid = ?";
    
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, LoginPage.ID);

	         ResultSet rs = pstmt.executeQuery();
	         
	         if(rs.next()){
	            if(rs.getString("sex").equals("남자")) {
	            	MyImageLabel.setIcon(new ImageIcon(getClass().getResource("image/BM.png")));
	            }else if(rs.getString("sex").equals("여자"))  {
	            	MyImageLabel.setIcon(new ImageIcon(getClass().getResource("image/BW.png")));
	            }
	         }else {JOptionPane.showMessageDialog(null, "오류입니다.", "확인", JOptionPane.ERROR_MESSAGE);}
	             
	      }catch (Exception e) {      
	         e.printStackTrace();

	      }finally {
				if(conn != null) {
					
					try {
						conn.close();
					}catch(SQLException e) {}
				}
				
			}
	}
	

	//친구들의 아이디에 따라 상태메세지를 ArrayList인 StateMsg에 담아 줍니다.
	public void receiveStateMsg2() {
	      
		Connection conn = null;
	      try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				conn = DriverManager.getConnection(
						"jdbc:mysql://222.119.100.89:3382/chatdb",
						"chat153",
						"153123"
						);
	    	  
	         String sql = "" + "SELECT uname, uid, password, sex, statemsg " + " FROM  member_table WHERE "
	                               +" uid = ?";
	         
	         
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         
	             
	            //여기서 왜 반복문을 쓰느냐,  receivefrdListData()를 실행하고 나면 pProfileList에는 친구들의 아이디와 이름이 담겨있습니다.
	            //해당 아이디에 해당하는 상태메세지를 db에서 불러와 하나씩 담아주어야 합니다.
	            //uid의 파라미터는 지속적으로 바뀌면서 친구 uid에 해당하는 상태메세지를 불러와 리스트에 담아줍니다.
	            for(int i = 0; i < pProfileList.size();i++) {   
	               
	            	//pProfileList의 i번째 인덱스에 접근하면 아이디와 이름에 접근가능합니다.
	            	//그 ProFileList 객체를 새로 담아줍니다.
	            	
	            	ProFileList pProFile = pProfileList.get(i);
	            	
	            	//pProfileList의 i번째 인덱스에 있는 ProFileList객체 안에 아이디를 파라미터 값에 넣어주어 해당 아이디를 가진 상태메세지 대조
	                pstmt.setString(1, pProFile.getId());
	                ResultSet rs = pstmt.executeQuery();
	                
	                
	                if(rs.next()) {       
	                	//리스트에 넣어주기 이젠 StateMsg에는 아이디별 상태메세지가 존재합니다. 
	                	StateMsg.add(rs.getString("statemsg"));
	
	                } 
	            
	            }
	             
	      }catch (Exception e) {      
	         e.printStackTrace();

	      }finally {
				if(conn != null) {
					
					try {
						conn.close();
					}catch(SQLException e) {}
				}
				
			}
	      
	      
	   }
	
	//내 상태메세지를 출력해줍니다. 이 메서드는 리턴 값을 반환해주는 메서드이기 때문에 상태메세지 레이블에 넣어주기만 하면 됩니다.
	public String receiveStateMsg1() {
		Connection conn = null;
	      try {
	    	  
	    	  
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				conn = DriverManager.getConnection(
						"jdbc:mysql://222.119.100.89:3382/chatdb",
						"chat153",
						"153123"
						);
	    	  
	         String sql =  "" + "SELECT statemsg " + " FROM  member_table WHERE "
	                    +" uid = ? ";
	                  
	         PreparedStatement pstmt = conn.prepareStatement(sql);

	         pstmt.setString(1, LoginPage.ID);   
	         ResultSet rs = pstmt.executeQuery();
	         	
	         // 내 아이디와 비교하여 존재하는 상태메세지를 출력할 것입니다.
	         // 출력한 값을 맨 위에서 선언한 msg에 담아줍니다.
	          if(rs.next()) {
	             msg = rs.getString("statemsg");

	          }
	
	          pstmt.close();    

	      }catch (Exception e1) {
	         e1.printStackTrace();
	   
	      }finally {
				if(conn != null) {
					
					try {
						conn.close();
					}catch(SQLException e) {}
				}
		}
	      
	   // 여기서 리턴을 해줄 것인데, 만약 상태메세지를 가지지 않은 신규 가입자는 null값이 뜰것입니다.
	   // 그렇다면 null값을 받았을 경우 밑의 메세지를 리턴해주고 그렇지 않다면 존재하는 상태메세지 값을 리턴해줍니다.
	     if(msg==null) {
	    	 return "상태메세지를 입력해주세요.";
	     }else if(msg.equals("")) {
	    	 return "상태메세지를 입력해주세요.";
	    	 
	     }
	     
	     else {
	    	 return msg;
	     }

	}
	
	
	//친구의 아이디와 이름을 객체에 담아 주는 역할을 할 메서드입니다.
	public void receivefrdListData() {
		Connection conn = null;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://222.119.100.89:3382/chatdb",
					"chat153",
					"153123"
					);
			String sql = ""+
		               "SELECT myid, friendid, friendname FROM myfriends WHERE myid = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			//내 아이디와 일치하는 친구 목록을 받아올것입니다. 때문에 파라미터는 나의 내 아이디로 합니다.
			pstmt.setString(1, LoginPage.ID);
			
			ResultSet rs = pstmt.executeQuery();
			
			// 위에 선언한 ArrayList인 pProfileList에 ArrayList객체를 넣어줍니다.
			// 위에선 선언만 되어 있으므로 생성을 하지 않으면 기능하지 않습니다.
			this.pProfileList = new ArrayList<ProFileList>();
	
			//while반복문을 통해 ProFileList객체인 pList를 생성해줍니다.
			// pList에 친구의 아이디와 이름을 넣어줍니다.
			// 그리고 그 pList객체를 ArrayList인 pProfileList에 넣어줍니다.
			while(rs.next()) {
	
				ProFileList pList = new ProFileList();
	        	  pList.setId(rs.getString("friendid"));
	        	  pList.setName(rs.getString("friendname"));
	        	  this.pProfileList.add(pList);
	        	  
	        	  
			
		}

          pstmt.close();
	
	}catch (Exception e1) {
        e1.printStackTrace();
        
     }
		finally {
			if(conn != null) {
				
				try {
					conn.close();
				}catch(SQLException e) {}
			}
	}
}
	
	//main을 구동합니다.
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	
	//MainPage의 생성자 선언
	//모든 ui요소들과 부가적인 기능들이 들어갑니다.
	public MainPage() {
		setFocusableWindowState(false);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainPage.class.getResource("/project1/image/logo.png")));
		setTitle("씽쏭");
		
		//이 메서드는 위에서 언급했듯이 ArrayList인 pProfileList에 친구의 이름과 아이디를가진 ProFileList객체를 넣어줍니다.
		receivefrdListData();
			
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
		
		JLabel MyProfileLabel = new JLabel(LoginPage.MyName+"님");
		MyProfileLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		MyProfileLabel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		MyProfileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MyProfileLabel.setFont(new Font("굴림", Font.BOLD, 20));
		MyProfileLabel.setBounds(231, 27, 135, 54);
		contentPane.add(MyProfileLabel);
		
		JButton LogoutButton = new JButton("로그아웃");
		LogoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		LogoutButton.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(171, 174, 179)));
		LogoutButton.setBackground(new Color(254, 241, 184));
		LogoutButton.setFont(new Font("굴림", Font.PLAIN, 15));
		LogoutButton.addActionListener(new ActionListener() {
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
		LogoutButton.setBounds(506, 10, 68, 35);
		contentPane.add(LogoutButton);
		
		
		//위에서 언급했듯 친구들의 상태메세지를 StateMsg  배열에 담아줍니다. 
		receiveStateMsg2();
		
		//String 문자 배열인 nameList를 생성합니다. 이 문자 배열의 크기는 친구의 수만큼 만들어져야 합니다.
		//nameList 문자 배열에는 친구들의 프로필 사진을 제외한 이름, 아이디, 상태메세지를 담아집니다.
		//위에서 배열에 담았던 행위들이 여기서 모두 취합된다고 생각하시면 됩니다.
		
		String[] nameList = new String[pProfileList.size()];
		
		 if(!this.pProfileList.isEmpty()){
			 
			
        	  for(int i = 0; i < this.pProfileList.size();i++) {
        		  //pProfileList의 i번째 인덱스에 있는 pList를 얻어 pList에 담아줍니다.(이름과 아이디)
        		  ProFileList pList = this.pProfileList.get(i);
        		  
        		  //conform은 상태메세지를 담아줄것입니다. 상태메세지는 따로 담았기 때문입니다.
        		  String conform ;
        		  
        		  
        		  //conform에 i번째 상태메세지를 담아줍니다.
        		  conform = StateMsg.get(i);
        		  
        		  //이건 확인용입니다.
        		  System.out.println(conform);
        		  
        		  
        		  //친구의 상태메세지가 없을 수도 있습니다. 없다면 없다는 것을 출력하고 있다면 해당 상태메세지를 담아줍니다.
        		  //모두 합쳐진 출력 메세지는 nameList[i]에 순차적으로 담아줍니다.
        		  if(conform == null) {
        			  nameList[i] = pList.getName()+"("+pList.getId()+") : "+"상태 메세지가 없습니다." ;
        		  }else if(conform.equals("")) {
        			  nameList[i] = pList.getName()+"("+pList.getId()+") : "+"상태 메세지가 없습니다." ;
        		  }
        		  else{nameList[i] = pList.getName()+"("+pList.getId()+") : " +conform;}
              }
        	  
          }
		
		    //createImageMap에 nameList를 매개변수로 넣고 그에 해당해여 필터된 남자, 여자 이미지 아이콘들이 
		    //imageMap에 들어갑니다.
		    //imageMap은 밑에서 선언한 practiceRenderer()클래스에서 활용됩니다.
            imageMap = createImageMap(nameList);
            
            //Jlist 생성시에 nameList를 담아주면 친구들의 이름, 아이디, 상태메세지를 가진 리스트가 생성됩니다.
            JList list = new JList(nameList);
            list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            //setCellRenderer에 new practiceRenderer()를 추가하여 list를 매개변수로 하여 
            //프로필 사진과 텍스트(친구정보)가 결합된 list가 완성됩니다.
            //practiceRenderer()에 대한 이해는 저도 부족해서 좀 더 알아보고 말씀드리겠습니다.
            list.setCellRenderer(new practiceRenderer());
            
            
            //리스트 클릭스 채팅방으로 이동(내 아이디와 상대 아이디를 필터 합니다.)
            list.addMouseListener(new MouseAdapter() {
            	
    		    public void mouseClicked(MouseEvent evt) {
    		    	try {
    		        JList list = (JList)evt.getSource();
    		        //더블 클릭 이벤트
    		        if (evt.getClickCount() == 2) {
    		        	
    		        
    		        	
    		        	
    		        	//확인용 출력
    		        	System.out.println(whofriendChat);
    		        	
    		        	//우리가 클릭했을 때 값은 친구의 아이디만 있는게 아닙니다. 
    		        	//이름과 아이디, 상태메세지가 같이 있으니 우린 아이디를 출력할 수 있어야합니다.
    		        	// 때문에 특수 문자 '('와 ')'를 기준으로 아이디를 잘라 스태틱으로 만들어 줘야합니다.
    		        	//스태틱으로 만든 값은 채팅페에지에서 접근하여 필터역할을 합니다.
    		        	//친구의 이름도 필요한데 채팅페이지에서 누구와 누구의 대화를 나오게 하기 위해 여기서 이름도 스태틱으로 넣어줍니다.
    		        	
    		        	int splitidx = list.getSelectedValue().toString().indexOf("(");
    		        	int splitidx2 = list.getSelectedValue().toString().indexOf(")");
    		        	
    		        	whofriendId=list.getSelectedValue().toString().substring(splitidx+1,splitidx2);
    		        	whofriendChat =list.getSelectedValue().toString().substring(0,splitidx);
    		        	System.out.println(whofriendId);
    		        	
    		        	//앞서 말했듯 얘는 필요없습니다.. whofriendChat과 똑같이 친구의 이름을 담아줍니다.
    	                 YouName =list.getSelectedValue().toString().substring(0,splitidx);
    		        	
    	                 
    	               //확인용 출력
    		        	System.out.println(whofriendChat);
    		       
    		        	
    		        	
    					JOptionPane.showMessageDialog(null, "채팅방에 입장합니다." , "입장", JOptionPane.INFORMATION_MESSAGE);
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

    					dispose();

    		            // Double-click detected
    		            int index = list.locationToIndex(evt.getPoint());
    		        } else if (evt.getClickCount() == 3) {

    		            // Triple-click detected
    		            int index = list.locationToIndex(evt.getPoint());
    		        }
    		    	}catch(Exception e) {
    		    		JOptionPane.showMessageDialog(null, "친구가 존재하지 않습니다. 친구를 추가해주세요." , "입장", JOptionPane.WARNING_MESSAGE);
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
    		    		//System.out.println("123");
    		    	}
    		    }
    		});

            JScrollPane scroll = new JScrollPane(list);
            scroll.setPreferredSize(new Dimension(300, 400));
            
            scroll.setBounds(28, 184, 525, 497);
            
            contentPane.add(scroll);
		

		
		JLabel FriendListLabel = new JLabel("친구 목록");
		FriendListLabel.setBackground(new Color(128, 255, 128));
		FriendListLabel.setOpaque(true);
		FriendListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		FriendListLabel.setFont(new Font("굴림", Font.PLAIN, 30));
//		scrollPane.setColumnHeaderView(FriendListLabel);
		
		JButton FindFriendButton = new JButton("");
		FindFriendButton.setBorder(null);
		FindFriendButton.setIcon(new ImageIcon(MainPage.class.getResource("/project1/image/FindFriend.png")));
		FindFriendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		FindFriendButton.setBackground(new Color(254, 241, 184));
		FindFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SearchFriends frame = new SearchFriends();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				dispose();
			}
		});
		FindFriendButton.setFont(new Font("굴림", Font.BOLD, 20));
		FindFriendButton.setBounds(28, 704, 525, 35);
		contentPane.add(FindFriendButton);
		

		JLabel StateMessageLabel = new JLabel(receiveStateMsg1());
		StateMessageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		StateMessageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		StateMessageLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		StateMessageLabel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		StateMessageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							StateMessage frame = new StateMessage();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				dispose();
			}
		});
		StateMessageLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		StateMessageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		StateMessageLabel.setBounds(231, 91, 285, 54);
		contentPane.add(StateMessageLabel);
		
		MyImageLabel = new JLabel("");
		MyImageLabel.setBorder(null);
		MyImageLabel.setBounds(53, 24, 150, 150);
		MyIcon();
		contentPane.add(MyImageLabel);
		
	}
	 public class practiceRenderer extends DefaultListCellRenderer {    
	        Font font = new Font("helvitica", Font.BOLD, 22);

	        @Override
	        public Component getListCellRendererComponent(
	                JList list, Object value, int index,
	                boolean isSelected, boolean cellHasFocus) {

	            JLabel label = (JLabel) super.getListCellRendererComponent(
	                    list, value, index, isSelected, cellHasFocus);
	            label.setIcon(imageMap.get((String) value));
	            label.setHorizontalTextPosition(JLabel.RIGHT);
	            label.setFont(font);
	            return label;
	        }
	    }
	 
 
	    //이미지를 생성하여 Map형식으로 넣어줌
	    private Map<String, ImageIcon> createImageMap(String[] list) {
	        Map<String, ImageIcon> map = new HashMap<>();
	        Connection conn = null;
	        
	        //list에는 아까 우리가 담아준 친구의 이름,아이디,메세지가 들어갑니다.
	        //이 향상된 for문은 그 것을 하나씩 s에 담을 것이고 이미지를 합치는 역할을 할것입니다.
	        // 때문에 s에서 아이디만 출력하여 sex가 남자인지 여자인지를 판별하여 이미지를 구별해 넣어줍니다.
	        for (String s : list) {
	        	
	        	try {
	        		
					Class.forName("com.mysql.cj.jdbc.Driver");
					
					conn = DriverManager.getConnection(
							"jdbc:mysql://222.119.100.89:3382/chatdb",
							"chat153",
							"153123"
							);
	        		
	    			String sql = "" + "SELECT uname, uid, password, sex " + " FROM  member_table WHERE "
	    		                         +" uid = ?";
	    			
	    			
	    			PreparedStatement pstmt = conn.prepareStatement(sql);
	    			pstmt.setString(1, s.substring(s.indexOf("(")+1,s.indexOf(")"))); 
	    			

	    			ResultSet rs = pstmt.executeQuery();
	    			
	    			if(rs.next()){
	    				
	    				if(rs.getString("sex").equals("남자")) {
	    				 map.put(s, new ImageIcon(
	    		                    getClass().getResource("image/TBM.png")));
	    				}
	    		        	
	    		        	else {
	    		        		map.put(s, new ImageIcon(
	    		                        getClass().getResource("image/TBW.png")));
	    		        	}
	    			}
	    				 
	    		}catch (Exception e) {		
	    			e.printStackTrace();

	    		}finally {
	    			if(conn != null) {
	    				
	    				try {
	    					conn.close();
	    				}catch(SQLException e) {}
	    			}
	    	}
	
	        }
	        return map;
	    }
	
}
