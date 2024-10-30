package project1;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import java.awt.Cursor;

public class StateMessage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField StateMessagetextField;
	public static String StateMessage;
	public static Connection conn = null;
	public static String alreadyMsg;
	
	public void alreadyMsg() {
		
		 try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				conn = DriverManager.getConnection(
						"jdbc:mysql://222.119.100.89:3382/chatdb",
						"chat153",
						"153123"
						);
			 
          String sql = ""+
                "select StateMsg from MEMBER_TABLE WHERE uid = ? ";
                          
          
          PreparedStatement pstmt = conn.prepareStatement(sql);
          
          pstmt.setString(1, LoginPage.ID );
    
          ResultSet rs = pstmt.executeQuery();
          if(rs.next()) {
        	  alreadyMsg = rs.getString("stateMsg");
        	  
          }
          rs.close();
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
		
	}
	
	public void UpdateMSG() {
		 try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				conn = DriverManager.getConnection(
						"jdbc:mysql://222.119.100.89:3382/chatdb",
						"chat153",
						"153123"
						);
			 
             String sql = ""+
                   "UPDATE MEMBER_TABLE SET statemsg = ? WHERE uid = ? ";
                             
             
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setString(1,StateMessagetextField.getText() );
             pstmt.setString(2, LoginPage.ID );
       
             pstmt.executeUpdate();
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

	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public StateMessage() {
		setResizable(false);
		alreadyMsg();
		setIconImage(Toolkit.getDefaultToolkit().getImage(StateMessage.class.getResource("/project1/image/logo.png")));
		setTitle("씽쏭");
		
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (windowSize.width - 600) / 2;
        int height = (windowSize.height - 170) / 2;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(width, height, 600, 170);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(254, 241, 184));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBounds(117, 89, 150, 40);
		btnNewButton.setBackground(new Color(254, 241, 184));
		btnNewButton.setIcon(new ImageIcon(StateMessage.class.getResource("/project1/image/Setting.png")));
		btnNewButton.setBorder(null);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateMSG();
					
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
		contentPane.setLayout(null);
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 20));
		contentPane.add(btnNewButton);
		
		JButton CancelButton = new JButton("");
		CancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		CancelButton.setBounds(317, 89, 150, 40);
		CancelButton.setBorder(null);
		CancelButton.setOpaque(false);
		CancelButton.setBackground(new Color(254, 241, 184));
		CancelButton.setIcon(new ImageIcon(StateMessage.class.getResource("/project1/image/Cancel.png")));
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateMSG();
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
		CancelButton.setFont(new Font("굴림", Font.PLAIN, 20));
		contentPane.add(CancelButton);
		
		StateMessagetextField = new JTextField(alreadyMsg);
		StateMessagetextField.setBounds(17, 15, 549, 64);
		StateMessagetextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateMSG();
				
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
		StateMessagetextField.setFont(new Font("굴림", Font.PLAIN, 20));
		contentPane.add(StateMessagetextField);
		
		StateMessagetextField.setColumns(10);
	}
}
