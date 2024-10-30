package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddFriend extends JFrame {
	
	public static Connection conn = null;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static  String whoaddname;
	private String friendID;
	
	public void Add() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://222.119.100.89:3382/chatdb",
					"chat153",
					"153123"
					);
			
			// 중복을 방지하기 위한 쿼리문 중복시 인설트 안됨 
			String sql = ""+
					" INSERT INTO myfriends (myid, friendid, friendname)"+
					" SELECT ?, ? ,? FROM DUAL WHERE NOT EXISTS "+
				    " (SELECT * FROM myfriends WHERE myid =? and friendid = ? and friendname = ?) ";

			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, LoginPage.ID);
			pstmt.setString(2, friendID);
			pstmt.setString(3, whoaddname);
			pstmt.setString(4, LoginPage.ID);
			pstmt.setString(5, friendID);
			pstmt.setString(6, whoaddname);
					
			pstmt.executeUpdate();
			pstmt.close();
			
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
			
			
		}catch(SQLIntegrityConstraintViolationException e3) {
			e3.printStackTrace();
			
		}
		
		catch (Exception e1) {
			e1.printStackTrace();
			
		}finally {
			if(conn != null) {
				
				try {
					conn.close();
				}catch(SQLException e) {}
			}
	}
	}
	
    public void whofrdname() {
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
			pstmt.setString(1, SearchFriends.findfriend);

			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				whoaddname = rs.getString("uname");
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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFriend frame = new AddFriend();
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
	public AddFriend() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				contentPane.requestFocusInWindow();
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddFriend.class.getResource("/project1/image/logo.png")));
		setTitle("씽쏭");
		whofrdname();
		
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (windowSize.width - 600) / 2;
        int height = (windowSize.height - 330) / 2;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(width, height, 600, 330);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(254, 241, 184));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		friendID = SearchFriends.findfriend;
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(171, 174, 179)));
		panel.setBackground(new Color(255, 251, 230));
		panel.setBounds(12, 46, 562, 195);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel FindFriendLabel = new JLabel("아이디: "+SearchFriends.findfriend+"님을 찾았습니다.");
		FindFriendLabel.setBounds(12, 34, 538, 69);
		panel.add(FindFriendLabel);
		FindFriendLabel.setFont(new Font("굴림", Font.BOLD, 25));
		FindFriendLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton AddButton = new JButton("");
		AddButton.setBorder(null);
		AddButton.setIcon(new ImageIcon(AddFriend.class.getResource("/project1/image/Add.png")));
		AddButton.setBounds(100, 127, 150, 40);
		panel.add(AddButton);
		AddButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		AddButton.setBackground(new Color(255, 251, 230));
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add();
			}
		});
		AddButton.setFont(new Font("굴림", Font.PLAIN, 25));
		
		JButton CancelButton = new JButton("");
		CancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		CancelButton.setBorder(null);
		CancelButton.setIcon(new ImageIcon(AddFriend.class.getResource("/project1/image/Cancel.png")));
		CancelButton.setBackground(new Color(255, 251, 230));
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
		CancelButton.setFont(new Font("굴림", Font.PLAIN, 21));
		CancelButton.setBounds(312, 127, 150, 40);
		panel.add(CancelButton);
	}
}
