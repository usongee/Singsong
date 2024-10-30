package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;

public class FindId extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField FindIdToNtextField;
	private JTextField FindIdToEtextField;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	
	public void check() {
		
		if(FindIdToNtextField.getText().equals("")||FindIdToEtextField.getText().equals("") || FindIdToNtextField.getText().equals("이름을 입력해주세요.")||FindIdToEtextField.getText().equals("이메일을 입력해주세요.")) {
			JOptionPane.showMessageDialog(null, "모두 입력해주세요.");
		}else {					
			String emailForm = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
			String emailData = FindIdToEtextField.getText();
			boolean emailDataForm = Pattern.matches(emailForm, emailData);
						
			if(!emailDataForm) {
				JOptionPane.showMessageDialog(null, "사용불가능한 이메일 형식입니다.");
			} else {
				
				try {
					dbConnection.dbconn();
					String sql = "" +
							"SELECT uname, uid, uemail " +
							"FROM member_table " +
							"WHERE uname =? AND uemail = ?";
										
					PreparedStatement pstmt = dbConnection.conn.prepareStatement(sql);
					pstmt.setString(1,FindIdToNtextField.getText());
					pstmt.setString(2,FindIdToEtextField.getText());
					
					ResultSet rs = pstmt.executeQuery();
					if(rs.next()) {	
						JOptionPane.showMessageDialog(null, "고객님의 아이디는"+rs.getString("uid") +"입니다");
						dispose();
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
					} else {
						
						JOptionPane.showMessageDialog(null, "일치하는 아이디가 없습니다");

					}
					rs.close();
					pstmt.close();
					
					
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally {
					if(dbConnection.conn != null) {
						
						try {
							dbConnection.conn.close();
							System.out.println("실패");
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
					FindId frame = new FindId();
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
	public FindId() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FindId.class.getResource("/project1/image/logo.png")));
		setTitle("씽송");
		
		setBackground(new Color(254, 241, 184));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				contentPane.requestFocusInWindow();
			}
		});
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
		FindIdToNtextField.setBounds(64, 356, 459, 55);
		contentPane.add(FindIdToNtextField);
		FindIdToNtextField.setColumns(10);
		
		FindIdToEtextField = new JTextField();
		FindIdToEtextField.setBorder(new LineBorder(new Color(171, 173, 179)));
		FindIdToEtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				check();
				
			}
		});
		FindIdToEtextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
	              if (FindIdToEtextField.getText().equals("이메일을 입력해주세요.")) {
	            	  FindIdToEtextField.setText("");
	              } 
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				
			       if (FindIdToEtextField.getText().isEmpty()) {
			    	   FindIdToEtextField.setText("이메일을 입력해주세요.");
			       }
				
			}
		});
		FindIdToEtextField.setText("이메일을 입력해주세요.");
		FindIdToEtextField.setColumns(10);
		FindIdToEtextField.setBounds(64, 421, 459, 55);
		contentPane.add(FindIdToEtextField);
		
		JButton FIndIdButton = new JButton("");
		FIndIdButton.setBorder(null);
		FIndIdButton.setIcon(new ImageIcon(FindId.class.getResource("/project1/image/FindID.png")));
		FIndIdButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		FIndIdButton.setBackground(new Color(254, 241, 184));
		FIndIdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check();
			}
		});
		FIndIdButton.setBounds(114, 500, 150, 40);
		contentPane.add(FIndIdButton);
		
		JButton CancelButton = new JButton("");
		CancelButton.setIcon(new ImageIcon(FindId.class.getResource("/project1/image/Cancel.png")));
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
		CancelButton.setBounds(323, 500, 150, 40);
		contentPane.add(CancelButton);
		
		JLabel GuideLabel = new JLabel("회원가입시 작성한 정보를 입력하세요.");
		GuideLabel.setFont(new Font("굴림", Font.PLAIN, 11));
		GuideLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GuideLabel.setBounds(185, 295, 223, 37);
		contentPane.add(GuideLabel);
		
		lblNewLabel = new JLabel(" 아이디찾기");
		lblNewLabel.setBorder(new MatteBorder(0, 2, 0, 0, (Color) new Color(23, 23, 23)));
		lblNewLabel.setFont(new Font("한컴 고딕", Font.BOLD, 19));
		lblNewLabel.setBounds(64, 285, 125, 48);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(FindId.class.getResource("/project1/image/SingSong.png")));
		lblNewLabel_1.setBounds(152, 73, 271, 161);
		contentPane.add(lblNewLabel_1);
	}
}
