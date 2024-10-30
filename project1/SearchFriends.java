package project1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SearchFriends extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField SearchtextField;
	public static String findfriend;
	public static Connection conn = null;

public void search() {
		
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
			pstmt.setString(1, SearchtextField.getText());

			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				if(SearchtextField.getText().equals(LoginPage.ID)) {
				JOptionPane.showMessageDialog(null, "자신의 아이디는 추가할 수 없습니다.", "확인", JOptionPane.ERROR_MESSAGE);
				}else {
				findfriend = SearchtextField.getText();
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
				dispose();
				}
				}else{
					JOptionPane.showMessageDialog(null, "존재하지 않는 아이디 입니다.", "확인", JOptionPane.ERROR_MESSAGE);}
			rs.close();
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
					SearchFriends frame = new SearchFriends();
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
	public SearchFriends() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				SearchtextField.requestFocusInWindow();
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SearchFriends.class.getResource("/project1/image/logo.png")));
		setTitle("씽쏭");
		
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (windowSize.width - 600) / 2;
        int height = (windowSize.height - 330) / 2;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(width, height, 600, 330);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(254, 241, 184));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton CancelButton = new JButton(">   취소");
		CancelButton.setBorder(null);
		CancelButton.setHorizontalAlignment(SwingConstants.LEFT);
		CancelButton.setBackground(new Color(254, 241, 184));
		CancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
		SpringLayout sl_contentPane = new SpringLayout();
		sl_contentPane.putConstraint(SpringLayout.NORTH, CancelButton, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, CancelButton, 501, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, CancelButton, 33, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, CancelButton, 574, SpringLayout.WEST, contentPane);
		contentPane.setLayout(sl_contentPane);
		contentPane.add(CancelButton);
		
		JPanel panel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 36, SpringLayout.SOUTH, CancelButton);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 41, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, -31, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, -36, SpringLayout.EAST, contentPane);
		panel.setBorder(new LineBorder(new Color(171, 174, 179)));
		panel.setBackground(new Color(255, 251, 230));
		contentPane.add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel disLabel = new JLabel("추가 하려는 친구의 아이디를 입력해주세요.");
		sl_panel.putConstraint(SpringLayout.NORTH, disLabel, 34, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, disLabel, 126, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, disLabel, 65, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, disLabel, 397, SpringLayout.WEST, panel);
		panel.add(disLabel);
		disLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		SearchtextField = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, SearchtextField, 87, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, SearchtextField, 133, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, SearchtextField, 118, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, SearchtextField, 367, SpringLayout.WEST, panel);
		SearchtextField.setBorder(new LineBorder(new Color(171, 173, 179)));
		panel.add(SearchtextField);
		SearchtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		SearchtextField.setColumns(10);
		
		JButton SearchButton = new JButton("");
		sl_panel.putConstraint(SpringLayout.NORTH, SearchButton, 88, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, SearchButton, 379, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, SearchButton, 439, SpringLayout.WEST, panel);
		SearchButton.setBorder(null);
		SearchButton.setIcon(new ImageIcon(SearchFriends.class.getResource("/project1/image/Search.png")));
		SearchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.add(SearchButton);
		SearchButton.setBackground(new Color(255, 251, 230));
		SearchButton.setFont(new Font("굴림", Font.PLAIN, 10));
		
		JLabel IDLabel = new JLabel("아이디");
		sl_panel.putConstraint(SpringLayout.NORTH, IDLabel, 75, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, IDLabel, 36, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, IDLabel, 128, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, IDLabel, 138, SpringLayout.WEST, panel);
		panel.add(IDLabel);
		IDLabel.setFont(new Font("한컴 고딕", Font.BOLD, 25));
		IDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel SearchFriendLabel = new JLabel(" 친구 추가");
		sl_panel.putConstraint(SpringLayout.NORTH, SearchFriendLabel, 23, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, SearchFriendLabel, 12, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, SearchFriendLabel, 65, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, SearchFriendLabel, 153, SpringLayout.WEST, panel);
		panel.add(SearchFriendLabel);
		SearchFriendLabel.setBorder(new MatteBorder(0, 2, 0, 0, (Color) new Color(0, 0, 0)));
		SearchFriendLabel.setHorizontalAlignment(SwingConstants.LEFT);
		SearchFriendLabel.setFont(new Font("한컴 고딕", Font.BOLD, 25));
		SearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
	}
}
