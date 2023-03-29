package com.MapBoard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MapBoardDAO {
	
	// DB와 연동하는 객체.
	Connection con = null;

	// DB에 SQL문을 전송하는 객체.
	PreparedStatement ps = null;

	// SQL문을 실행한 후에 결과 값을 가지고 있는 객체.
	ResultSet rs = null;

	// 쿼리문을 저장할 변수
	String sql = null;

	// MapBoardDAO 클래스를 싱글턴 방식으로 만들어 보자.
	// 1단계 : 싱글턴 방식을 객체를 만들기 위해서는 우선적으로 기본생성자의 접근제어자를 public이 아니라
	// private 으로 바꾸어 주어야 한다. 즉, 외부에서는 직접적으로 기본생성자를 호출하지 못하게 하는 방식이다.

	// 2단계 : MapBoardDAO 클래스를 정적(static) 멤버로 선언을 해 주어야 한다.
	private static MapBoardDAO instance;

	// 기본 생성자
	private MapBoardDAO() {}

	// 3단계 : 기본생성자 대신에 싱글턴 객체를 return 해 주는 getInstance() 메서드를 만들어서
	// 해당 getInstance() 메서드를 외부에서 접근할 수 있도록 해 주면 됨.
	public static MapBoardDAO getInstance() {

		if (instance == null) {
			instance = new MapBoardDAO();
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
	public void closeConn(ResultSet rs, PreparedStatement ps, Connection con) {
		
		try {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	// closeConn() 메서드 end
	
	// MapBoard 테이블의 전체 게시물의 수를 확인하는 메서드.
	public int getMapBoardCount() {
		int count = 0;
		
		try {
			openConn();
			
			sql = "select count(*) from map_board";
			
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, ps, con);
		}
		return count;
	} // getMapBoardCount() 메서드 end
	
	// MapBoard 테이블에서 현재 페이지에 해당하는 게시물을 조회하는 메서드
	public List<MapBoardDTO> getMapBoardList(int page, int rowsize) {
		// row_number() 함수 > 결과가 나온 각 행에 순차적으로 고유한 정수를 할당해 주는 분석 함수.
		// 형식) row_number() over(partition by[그룹핑할 컬럼] order by[정렬할 컬럼])
		// ==> partition by는 생략이 가능함.
		List<MapBoardDTO> list = new ArrayList<MapBoardDTO>();
		
		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize - 1);
		// 해당 페이지에서 끝 번호
		int endNo = (page * rowsize);
		
		try {
			openConn();
			
			sql = "select * from (select row_number() over(order by board_num desc) rnum, b.* from map_board b) where rnum >= ? and rnum <= ?";
			
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, startNo);
			ps.setInt(2, endNo);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				MapBoardDTO dto = new MapBoardDTO();
				
				dto.setBoard_num(rs.getInt("board_num"));
				dto.setBoard_title(rs.getString("board_title"));
				dto.setBoard_head(rs.getString("board_head"));
				dto.setBoard_hit(rs.getInt("board_hit"));
				dto.setBoard_like(rs.getInt("board_like"));
				dto.setBoard_writer(rs.getInt("board_writer"));
				dto.setBoard_regdate(rs.getString("board_regdate"));
				dto.setBoard_file(rs.getString("board_file"));
				dto.setBoard_text(rs.getString("board_text"));
				dto.setBoard_area(rs.getString("board_area"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, ps, con);
		}
		return list;
		
	} // getMapBoardList() 메서드 end
	
	// MapBoard 테이블에 게시글을 추가하는 메서드.
	public int insertMapBoard(MapBoardDTO dto) {
		int result = 0, count = 0;
		
		try {
			openConn();
			
			sql = "select max(board_num) from map_board";
			
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			sql = "insert into map_board values(?, ?, ?, default, default, ?, sysdate, ?, ?, '')";
			
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, count + 1);
			ps.setString(2, dto.getBoard_title());
			ps.setString(3, dto.getBoard_head());
			ps.setInt(4, dto.getBoard_writer());
			ps.setString(5, dto.getBoard_file());
			ps.setString(6, dto.getBoard_text());
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, ps, con);
		}
		return result;
		
	} // insertMapBoard() 메서드 end
	
	// board 테이블의 글번호에 해당하는 게시글의 조회수를 증가시키는 메서드
   public void mapBoardHit(int no) {

      try {
         openConn();
         sql = "update map_board set board_hit = board_hit + 1 where board_num = ?";
         
         ps = con.prepareStatement(sql);
         
         ps.setInt(1, no);

         ps.executeUpdate();
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeConn(rs, ps, con);
      }

   }// boardHit() 메서드 end
   
// 번호에 해당하는 게시물을 조회하는 메서드
	public MapBoardDTO getContentMapBoard(int no) {
		
		MapBoardDTO dto = null;
		
		try {
			openConn();
			
			sql = "select * from map_board where board_num = ?";
			
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, no);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				 // board 테이블에서 하나의 레코드를 가져와서 각각의 컬럼의 데이터를 DTO 객체의 setter() 메서드의 인자로 전달
				dto = new MapBoardDTO();
				
				dto.setBoard_num(rs.getInt("board_num"));
				dto.setBoard_title(rs.getString("board_title"));
				dto.setBoard_head(rs.getString("board_head"));
				dto.setBoard_hit(rs.getInt("board_hit"));
				dto.setBoard_like(rs.getInt("board_like"));
				dto.setBoard_writer(rs.getInt("board_writer"));
				dto.setBoard_regdate(rs.getString("board_regdate"));
				dto.setBoard_file(rs.getString("board_file"));
				dto.setBoard_text(rs.getString("board_text"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, ps, con);
		}
		return dto;
	}		// getContentBoard() 메서드 end
	
}
