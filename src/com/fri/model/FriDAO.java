package com.fri.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.member.model.MemberDTO;
import com.qna.model.QnaDTO;


public class FriDAO {
	// DB와 연동하는 객체.
	Connection con = null;

	// DB에 SQL문을 전송하는 객체.
	PreparedStatement pstmt = null;

	// SQL문을 실행한 후에 결과 값을 가지고 있는 객체.
	ResultSet rs = null;

	// 쿼리문을 저장할 변수
	String sql = null;

	// QnaDAO 클래스를 싱글턴 방식으로 만들어 보자.
	// 1단계 : 싱글턴 방식을 객체를 만들기 위해서는 우선적으로 기본생성자의 접근제어자를 public이 아니라
	// private 으로 바꾸어 주어야 한다. 즉, 외부에서는 직접적으로 기본생성자를 호출하지 못하게 하는 방식이다.

	// 2단계 : QnaDAO 클래스를 정적(static) 멤버로 선언을 해 주어야 한다.
	private static FriDAO instance;

	// 기본 생성자
	private FriDAO() {}

	// 3단계 : 기본생성자 대신에 싱글턴 객체를 return 해 주는 getInstance() 메서드를 만들어서
	// 해당 getInstance() 메서드를 외부에서 접근할 수 있도록 해 주면 됨.
	public static FriDAO getInstance() {

		if (instance == null) {
			instance = new FriDAO();
		}

		return instance;
	} // getInstance() 메서드 end

	// DB를 연동하는 작업을 진행하는 메서드.
	public void openConn() {
		try {
			// 1단계 : JNDI 서버 객체 생성
			// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를 연결해주는 개념이 Context 객체이며,
			// InitialContext 객체는 네이밍 서비스를 이용하기 위한 시작점이 됨.
			Context initCtx = new InitialContext();
			
			// 2단계 : Context 객체를 얻어와야 함.
			// "java:comp.env" 라는 이름의 인수로 Context 객체를 얻어옴.
			// "java:comp.env"는 현재 웹 애플리케이션에서 네이밍 서비스를 이용 시 루트 디렉토리라고 생각하면 됨.
			// 즉, 현재 웹 애플리케이션이 사용할 수 있는 모든 자원은 "java:comp.env" 아래에 위치를 하게 됨.
			Context ctx = (Context)initCtx.lookup("java:comp/env");
			
			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾게 됨.
			// "java:comp.env" 아래에 위치한 "jdbc/myoracle" 자원을 얻어옴.
			// 이 자원이 바로 데이터소스(커넥션풀)임. 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.
			DataSource ds = (DataSource)ctx.lookup("jdbc/myoracle");
			
			// 4단계 : DataSource 객체를 이용하여 커넥션을 하나 가져온다.
			con = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	// openConn() 메서드 end
	
	// DB에 연결되어 있던 자원 종료하는 메서드.
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
		
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	// closeConn() 메서드 end

	
	
	
	//친구 수락 창 보여주는 폼 페이지
	public List<FriDTO> acc(int mnum){		
		List<FriDTO> list = new ArrayList<FriDTO>();
		
		try {

			openConn();
			sql ="select * from friend where friend_response = ? and request_wait = '1'";

			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mnum);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				FriDTO dto = new FriDTO();
				
				dto.setFriend_request(rs.getInt("friend_request"));
				list.add(dto);
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
			
		}
		return list;
	}
	
	
	
	
	 
	//친구 창 보여주기
	public List<FriDTO> FList(int mnum){		
		List<FriDTO> F_List = new ArrayList<FriDTO>();
		try {

			openConn();
			sql ="select * from friend where (friend_response = ? or friend_request = ?) and request_accept = '1'";
			
		
			/*
			 * sql=
			 * "select * from member m join friend f on m.member_num = f.friend_request) and f.request_accept = '1'"
			 * ;
			 */		
			
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, mnum);
			pstmt.setInt(2, mnum);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				FriDTO dto = new FriDTO();
				if(rs.getInt("friend_request") == mnum) {
					dto.setFriend_response(rs.getInt("friend_response"));
				}else if(rs.getInt("friend_response") == mnum) {
					dto.setFriend_request(rs.getInt("friend_request"));
				}
				F_List.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
			
		}
		return F_List;
	}
	
	//친구 추가 가능한 애들 뽑기
	public List<MemberDTO> OtherMembers(int logInMemberNum) {
	    List<MemberDTO> otherMembers = new ArrayList<MemberDTO>();
	    
	    try {
	        openConn();
	        sql = "SELECT * FROM member WHERE member_num != ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, logInMemberNum);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            MemberDTO mdto = new MemberDTO();
	            // Set member properties from the result set
	            mdto.setMember_num(rs.getInt("member_num"));
	            mdto.setMember_age(rs.getInt("member_age"));
	            mdto.setMember_name(rs.getString("member_name"));
	            mdto.setMember_phone(rs.getString("member_phone"));
	            mdto.setMember_email(rs.getString("member_email"));
	            mdto.setMember_addr1(rs.getString("member_addr1"));
	            mdto.setMember_addr2(rs.getString("member_addr2"));
	            mdto.setMember_addr3(rs.getString("member_addr3"));
	            mdto.setMember_self(rs.getString("member_self"));
	            mdto.setMember_nick(rs.getString("member_nick"));
	            mdto.setMember_gender(rs.getString("member_gender"));
	            mdto.setMember_id(rs.getString("member_id"));
	            mdto.setMember_pwd(rs.getString("member_pwd"));
	            
	            otherMembers.add(mdto);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }
	    
	    return otherMembers;
	}//친구 추가 가능한 리스트 end
	
	public int fri_request(int lno,int fno){
		
		int count = 0, result = 0;
		try {

			openConn();
			
			sql = "select max(*) from friend";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			
			sql = "insert into friend values(?,?,?,'1','0')";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, count + 1);
			pstmt.setInt(2, lno);
			pstmt.setInt(3, fno);
			
			result = pstmt.executeUpdate();

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	}//fir_request() 메서드  end
	
	
	// 친구신청을 보내기 전에, 친구신청을 이미 건 상태인지 혹은 이미 친구인지 체크하는 메서드
	// 만약 친구신청 상태이거나 친구인 상태면 1을 반환하고,
	// 친구신청을 보낼 수 있는 상태면 0을 반환
	public int fri_checkfriend(int lno,int fno) {
		int result = 0, count = 0;
		try {
			openConn();
			sql ="select count(*) from friend where friend_request = ? and friend_response = ? and (request_wait = '1' or request_accept = '1')";
			pstmt  = con.prepareStatement(sql);
			pstmt.setInt(1, lno);
			pstmt.setInt(2, fno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
				if(result == 1) {
					return result;
				}
			}
			
			sql ="select count(*) from friend where friend_request = ? and friend_response = ? and (request_wait = '1' or request_accept = '1')";
			pstmt  = con.prepareStatement(sql);
			pstmt.setInt(1, fno);
			pstmt.setInt(2, lno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
				if(result == 1) {
					return result;
				}
			}
			
			sql = "select max(friend_num) from friend";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1) + 1;
			}
			
			sql = "insert into friend values(?,?,?,'1','0')";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setInt(2, lno);
			pstmt.setInt(3, fno);
			result = pstmt.executeUpdate();
			if(result == 1) {
				result = 0;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	}// fri_checkfriend 메서드 end

	
	//친구 신청을 받는 메서드
	public int fri_acc(int my, int fri) {
		int result = 0;
		try {

			openConn();
			sql="update friend set request_wait = '0', request_accept = '1' where friend_request = ? and friend_response = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fri);
			pstmt.setInt(2, my);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
	
	public int delete(int my, int fri) {
		
			int result = 0;
			try {

				openConn();
				sql= "delete from friend where (friend_request = ? and friend_response = ? and request_accept = '1') or (friend_request = ? and friend_response = ? and request_accept = '1')";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, fri);
				pstmt.setInt(2, my);
				pstmt.setInt(3, my);
				pstmt.setInt(4, fri);
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeConn(rs, pstmt, con);
			}
			return result;
	}
	
	public List<MesDTO> getMessage(int my, int fri) {
			List<MesDTO> list = new ArrayList<MesDTO>();
		try {
			openConn();
			sql = "select * from message where (message_request = ? and message_response = ?) or (message_request = ? and message_response = ?) order by message_num";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fri);
			pstmt.setInt(2, my);
			pstmt.setInt(3, my);
			pstmt.setInt(4, fri);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MesDTO dto = new MesDTO();
				dto.setMessage_content(rs.getString("message_content"));
				dto.setMessage_num(rs.getInt("message_num"));
				dto.setMessage_request(rs.getInt("message_request"));
				dto.setMessage_response(rs.getInt("message_response"));
				list.add(dto);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	public void insertMessage(int my, int fri, String message) {
		int count = 0;
		try {
			openConn();
			sql = "select count(*) from message";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			sql = "insert into message values(?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, count + 1);
			pstmt.setInt(2, my);
			pstmt.setInt(3, fri);
			pstmt.setString(4, message);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}
}