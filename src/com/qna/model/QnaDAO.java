package com.qna.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class QnaDAO {
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
	private static QnaDAO instance;

	// 기본 생성자
	private QnaDAO() {}

	// 3단계 : 기본생성자 대신에 싱글턴 객체를 return 해 주는 getInstance() 메서드를 만들어서
	// 해당 getInstance() 메서드를 외부에서 접근할 수 있도록 해 주면 됨.
	public static QnaDAO getInstance() {

		if (instance == null) {
			instance = new QnaDAO();
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
	
	// qna_board 테이블의 전체 게시물의 수를 확인하는 메서드.
	public int getQnaCount() {
		int count = 0;
		
		try {
			openConn();
			
			sql = "select count(*) from qna_board";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return count;
	} // getQnaCount() 메서드 end
	
	// qna_board 테이블의 전체 게시물을 조회하는 메서드
	public List<QnaDTO> getQnaList() {
		List<QnaDTO> list = new ArrayList<QnaDTO>();

		try {
			openConn();
			
			sql = "select * from qna_board order by qna_num desc";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				
				dto.setQna_num(rs.getInt("qna_num"));
				dto.setQna_title(rs.getString("qna_title"));
				dto.setQna_head(rs.getString("qna_head"));
				dto.setQna_hit(rs.getInt("qna_hit"));
				dto.setQna_writer(rs.getInt("qna_writer"));
				dto.setQna_regdate(rs.getString("qna_regdate"));
				dto.setQna_update(rs.getString("qna_update"));
				dto.setQna_file(rs.getString("qna_file"));
				dto.setQna_content(rs.getString("qna_content"));
				dto.setQna_pwd(rs.getString("qna_pwd"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	} // getQnaList() 메서드 end
	
	// qna_board 테이블의 게시물 번호에 해당하는 게시글의 조회수를 증가시키는 메서드.
	public void qnaHit(int no) {
		
		try {
			openConn();
			
			sql = "update qna_board set qna_hit = qna_hit + 1 where qna_num = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	} // qnaReadCount() 메서드 end
	
	// qna_board 테이블에서 게시글 번호에 해당하는 게시글의 상세내역을 조회하는 메서드.
	public QnaDTO getQnaContent(int no) {
		QnaDTO dto = null;
		
		try {
			openConn();
			
			sql = "select * from qna_board where qna_num = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new QnaDTO();
				
				dto.setQna_num(rs.getInt("qna_num"));
				dto.setQna_title(rs.getString("qna_title"));
				dto.setQna_head(rs.getString("qna_head"));
				dto.setQna_hit(rs.getInt("qna_hit"));
				dto.setQna_writer(rs.getInt("qna_writer"));
				dto.setQna_regdate(rs.getString("qna_regdate"));
				dto.setQna_update(rs.getString("qna_update"));
				dto.setQna_file(rs.getString("qna_file"));
				dto.setQna_content(rs.getString("qna_content"));
				dto.setQna_pwd(rs.getString("qna_pwd"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return dto;
	} // getQnaCont() 메서드 end
	
	// qna_board 테이블에 게시글을 추가하는 메서드.
	public int insertQna(QnaDTO dto) {
		int result = 0, count = 0;
		
		try {
			openConn();
			
			sql = "select max(qna_num) from qna_board";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1) + 1;
			}
			
			sql = "insert into qna_board values(?, ?, ?, default, ?, sysdate, '', ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, count);
			pstmt.setString(2, dto.getQna_title());
			pstmt.setString(3, dto.getQna_head());
			pstmt.setInt(4, dto.getQna_writer());
			pstmt.setString(5, dto.getQna_file());
			pstmt.setString(6, dto.getQna_content());
			pstmt.setString(7, dto.getQna_pwd());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	} // insertQna() 메서드 end
	
	// qna_board 테이블에 게시글 번호에 해당하는 게시글을 수정하는 메서드.
	public int updateQna(QnaDTO dto) {
		int result = 0;
		
		try {
			openConn();
			
			sql = "select * from qna_board where qna_num = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getQna_num());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(dto.getQna_pwd().equals(rs.getString("qna_pwd"))) {
					if(dto.getQna_file() == null) {
						// 첨부파일이 없는 경우
						sql = "update qna_board set qna_title = ?, qna_head = ?, qna_content = ?, qna_update = sysdate where qna_num = ?";
						
						pstmt = con.prepareStatement(sql);
						
						pstmt.setString(1, dto.getQna_title());
						pstmt.setString(2, dto.getQna_head());
						pstmt.setString(3, dto.getQna_content());
						pstmt.setInt(4, dto.getQna_num());
					} else {
						// 첨부파일이 있는 경우
						sql = "update qna_board set qna_title = ?, qna_head = ?, qna_content = ?, qna_file = ?, qna_update = sysdate where qna_num = ?";
						
						pstmt = con.prepareStatement(sql);
						
						pstmt.setString(1, dto.getQna_title());
						pstmt.setString(2, dto.getQna_head());
						pstmt.setString(3, dto.getQna_content());
						pstmt.setString(4, dto.getQna_file());
						pstmt.setInt(5, dto.getQna_num());
					}
					result = pstmt.executeUpdate();
				} else {
					// 비밀번호가 틀린 경우
					result = -1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
		
	} // updateQna() 메서드 end
	
	// qna_board 테이블의 게시글 번호에 해당하는 게시글을 삭제하는 메서드.
	public int deleteQna(int no) {
		int result = 0;
		
		try {
			openConn();
			
			sql = "delete from qna_board where qna_num = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
			
			sql = "update qna_board set qna_num = qna_num - 1 where qna_num > ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	} // deletQna() 메서드 end
	
	// 글번호에 해당하는 댓글 전체 리스트를 조회하는 메서드.
	public List<QnaReplyDTO> getReplyList(int no) {
		ArrayList<QnaReplyDTO> list = new ArrayList<QnaReplyDTO>();
		
		try {
			openConn();
			
			sql = "select * from qna_reply where qna_bno = ? order by qna_redate desc";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				
				QnaReplyDTO dto = new QnaReplyDTO();
				
				dto.setQna_bno(rs.getInt("qna_bno"));
				dto.setQna_recont(rs.getString("qna_recont"));
				dto.setQna_redate(rs.getString("qna_redate"));
				dto.setQna_reupdate(rs.getString("qna_reupdate"));
				dto.setQna_rewriter(rs.getString("qna_rewriter"));
				dto.setQna_rno(rs.getInt("qna_rno"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	} // getReplyList() 메서드 end
	
	// 답변 내용을 tbl_reply 테이블에 저장하는 메서드.
	public void replyInsert(QnaReplyDTO dto) {
		int result = 0, count = 0;
		
		try {
			openConn();
			
			sql = "select max(qna_rno) from qna_reply";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1) + 1;
			}
			
			sql = "insert into qna_reply values(?, ?, ?, ?, sysdate, '')";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, count);
			pstmt.setInt(2, dto.getQna_bno());
			pstmt.setString(3, dto.getQna_rewriter());
			pstmt.setString(4, dto.getQna_recont());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	} // replyInsert() 메서드 end
	
	public int searchListCount(String field, String keyword) {
		int count = 0;
		
		try {
			openConn();
			
			sql = "select count(*) from qna_board";
			
			
			if(field.equals("head")) {
				sql += " where qna_head like ?";
			} else if(field.equals("title")) {
				sql += " where qna_title like ?";
			} else if(field.equals("writer")) {
				sql += " where board_writer like ?";
			}
			
			sql += " order by qna_num desc";
			
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, '%' + keyword + '%');
			
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return count;
	} // searchListCount() 메서드 end
	
	// board 테이블에서 검색한 내용을 가지고 페이징 처리를 하는 메서드.
	public List<QnaDTO> getSearchQnaList(String field, String keyword, int page, int rowsize) {
		List<QnaDTO> searchList = new ArrayList<QnaDTO>();
		
		// 해당 페이지에서 시작번호
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		try {
			openConn();
			
			sql = "select * from (select row_number() over(order by qna_num desc) rnum, b.* from qna_board b";
			
			if(field.equals("head")) {
				sql += " where qna_head like ?)";
			} else if(field.equals("title")) {
				sql += " where qna_title like ?)";
			} else if(field.equals("writer")) {
				sql += " where board_writer like ?)";
			}
			
			sql += " where rnum >= ? and rnum <= ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, '%' + keyword + '%');
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaDTO dto = new QnaDTO();
				
				dto.setQna_num(rs.getInt("qna_num"));
				dto.setQna_title(rs.getString("qna_title"));
				dto.setQna_head(rs.getString("qna_head"));
				dto.setQna_hit(rs.getInt("qna_hit"));
				dto.setQna_writer(rs.getInt("qna_writer"));
				dto.setQna_regdate(rs.getString("qna_regdate"));
				dto.setQna_update(rs.getString("qna_update"));
				dto.setQna_file(rs.getString("qna_file"));
				dto.setQna_content(rs.getString("qna_content"));
				dto.setQna_pwd(rs.getString("qna_pwd"));
				
				searchList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return searchList;
	} // getSearchBoardList() 메서드 end
}