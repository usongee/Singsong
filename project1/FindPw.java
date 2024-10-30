package project1;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Cursor;

public class FindPw extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField FindIdToNtextField;
	private JTextField FIndPwToEtextField;
	private JTextField FIndPwToItextField;
	public static String FINDID;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	
	/**
	 * Launch the application.
	 */
	
	public void check() {
		//1. 모두 입력하지 않았을 때, 모두 입력할 것
		//2. 아이디가 형식에 맞지 않을 때 형식에 맞게 입력할 것
		//3. 이메일이 형식에 맞지 않을 경우 형식에 맞게 입력할 것
		//4. 데이터베이스에서 일치하는 정보가 있을 경우 비밀번호 변경창으로 이동
		
		//1.
		if(FindIdToNtextField.getText().equals("")||FIndPwToEtextField.getText().equals("")||FIndPwToItextField.getText().equals("") 
				|| FindIdToNtextField.getText().equals("이름을 입력해주세요")||FIndPwToEtextField.getText().equals("이메일을 입력해주세요.")||FIndPwToItextField.getText().equals("아이디를 입력해주세요.")) {
				JOptionPane.showMessageDialog(null, "모두 입력해주세요.");
		}else {
				String emailForm = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
				String emailData = FIndPwToEtextField.getText();
				boolean emailDataForm = Pattern.matches(emailForm, emailData);
				if(!emailDataForm) {
					JOptionPane.showMessageDialog(null, "이메일 형식이 올바르지 않습니다.");
				}else {							
					
					try {
						dbConnection.dbconn();
						
						String sql = "" +
								"SELECT uname, uid, uemail " +
								"FROM member_table " +
								"WHERE uid = ? AND uname = ? AND uemail=?";
						
						System.out.println("연결성공1");
											
						PreparedStatement pstmt = dbConnection.conn.prepareStatement(sql);
						pstmt.setString(1,FIndPwToItextField.getText());
						pstmt.setString(2,FindIdToNtextField.getText());
						pstmt.setString(3,FIndPwToEtextField.getText());
						
						ResultSet rs = pstmt.executeQuery();
						if(rs.next()) {		
							FINDID = FIndPwToItextField.getText();
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
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "정보가 일치하지 않습니다.");
						
	
						}
						
						rs.close();								
						dbConnection.conn.close();
						
						
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}finally {
						if(dbConnection.conn != null) {
							
							try {
								dbConnection.conn.close();
							}catch(SQLException e) {}
						}
						
					}
				}
			}
		
	}
	
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public FindPw() {
		setResizable(false);
		setTitle("씽쏭");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FindPw.class.getResource("/project1/image/logo.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
				contentPane.requestFocusInWindow();
				
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JLabel GuideLabel = new JLabel("회원가입 시 작성한 정보를 입력하세요.");
		GuideLabel.setFont(new Font("굴림", Font.PLAIN, 11));
		GuideLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GuideLabel.setBounds(210, 271, 250, 35);
		contentPane.add(GuideLabel);
		
		FindIdToNtextField = new JTextField();
		FindIdToNtextField.setBorder(new LineBorder(new Color(171, 173, 179)));
		FindIdToNtextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
	              if (FindIdToNtextField.getText().equals("이름을 입력해주세요.")) {
	            	  FindIdToNtextField.setText("");
	              } 
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				
			       if (FindIdToNtextField.getText().isEmpty()) {
			    	   FindIdToNtextField.setText("이름을 입력해주세요.");
			       }
				
			}
		});
		FindIdToNtextField.setText("이름을 입력해주세요.");
		FindIdToNtextField.setBounds(83, 333, 445, 45);
		contentPane.add(FindIdToNtextField);
		FindIdToNtextField.setColumns(10);
		
		FIndPwToEtextField = new JTextField();
		FIndPwToEtextField.setBorder(new LineBorder(new Color(171, 173, 179)));
		FIndPwToEtextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
	              if (FIndPwToEtextField.getText().equals("이메일을 입력해주세요.")) {
	            	  FIndPwToEtextField.setText("");
	              } 
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				
			       if (FIndPwToEtextField.getText().isEmpty()) {
			    	   FIndPwToEtextField.setText("이메일을 입력해주세요.");
			       }
				
			}
		});
		FIndPwToEtextField.setText("이메일을 입력해주세요.");
		FIndPwToEtextField.setColumns(10);
		FIndPwToEtextField.setBounds(83, 388, 445, 45);
		contentPane.add(FIndPwToEtextField);
		
		JButton FindPwButton = new JButton("");
		FindPwButton.setIcon(new ImageIcon(FindPw.class.getResource("/project1/image/FindPW.png")));
		FindPwButton.setBorder(null);
		FindPwButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		FindPwButton.setBackground(new Color(254, 241, 184));
		FindPwButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check();
			}
		});
		FindPwButton.setBounds(133, 507, 150, 40);
		contentPane.add(FindPwButton);
		
		JButton CancelButton = new JButton("");
		CancelButton.setIcon(new ImageIcon(FindPw.class.getResource("/project1/image/Cancel.png")));
		CancelButton.setBorder(null);
		CancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		CancelButton.setBackground(new Color(254, 241, 184));
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
		CancelButton.setBounds(328, 507, 150, 40);
		contentPane.add(CancelButton);
		
		FIndPwToItextField = new JTextField();
		FIndPwToItextField.setBorder(new LineBorder(new Color(171, 173, 179)));
		FIndPwToItextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				check();
				
			}
		});
		FIndPwToItextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
	              if (FIndPwToItextField.getText().equals("아이디를 입력해주세요.")) {
	            	  FIndPwToItextField.setText("");
	              } 
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				
			       if (FIndPwToItextField.getText().isEmpty()) {
			    	   FIndPwToItextField.setText("아이디를 입력해주세요.");
			       }
				
			}
		});
		FIndPwToItextField.setText("아이디를 입력해주세요.");
		FIndPwToItextField.setColumns(10);
		FIndPwToItextField.setBounds(83, 443, 445, 45);
		contentPane.add(FIndPwToItextField);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FindPw.class.getResource("/project1/image/SingSong.png")));
		lblNewLabel.setBounds(152, 73, 271, 161);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel(" 비밀번호 찾기");
		lblNewLabel_1.setFont(new Font("한컴 고딕", Font.BOLD, 19));
		lblNewLabel_1.setBorder(new MatteBorder(0, 2, 0, 0, (Color) new Color(23, 23, 23)));
		lblNewLabel_1.setBounds(83, 260, 125, 48);
		contentPane.add(lblNewLabel_1);
	}

}
