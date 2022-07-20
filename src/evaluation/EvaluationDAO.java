package evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import user.UserDTO;
import util.DatabaseUtil;

public class EvaluationDAO {

	
	public int write(EvaluationDTO evalutaitonDTO) {
		
		String SQL = "INSERT INTO EVALUATION VALUES(NULL, ?,?,?,?,?,?,?,?,?,?,?,?,0) ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  evalutaitonDTO.getUserID());
			pstmt.setString(2,	evalutaitonDTO.getLectureName() );
			pstmt.setString(3, 	evalutaitonDTO.getProfessorName());
			pstmt.setInt(4,  evalutaitonDTO.getLectureYear() );
			pstmt.setString(5, evalutaitonDTO.getSemesterDivide() );
			pstmt.setString(6, evalutaitonDTO.getLectureDivide() );
			pstmt.setString(7, evalutaitonDTO.getEvaluationTitle() );
			pstmt.setString(8, evalutaitonDTO.getEvaluationContent());
			pstmt.setString(9, evalutaitonDTO.getTotalScore());
			pstmt.setString(10, evalutaitonDTO.getCreditScore() );
			pstmt.setString(11, evalutaitonDTO.getComfortableScore());
			pstmt.setString(12, evalutaitonDTO.getLectureScore());
			return pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
				
			try {		if(conn != null) conn.close(); }catch(Exception e) { e.printStackTrace();}
			try {		if(pstmt != null) pstmt.close(); }catch(Exception e) { e.printStackTrace();}
			try {		if(rs != null) rs.close(); }catch(Exception e) { e.printStackTrace();}
			
		}
		return -2; // 데이터베이스 오류
	}
	
		public int join(UserDTO user) {
		
		String SQL = "INSERT INTO USER VALUES(?, ?, ?, ?, false) ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserEmail());
			pstmt.setString(4, user.getUserEmailHash());
			return pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
				
			try {		if(conn != null) conn.close(); }catch(Exception e) { e.printStackTrace();}
			try {		if(pstmt != null) pstmt.close(); }catch(Exception e) { e.printStackTrace();}
			try {		if(rs != null) rs.close(); }catch(Exception e) { e.printStackTrace();}
				
				
		}
		return -1; // 회원가입 실패.
	}
		public String getUserEmail(String userID) {

			String SQL = "SELECT userEmail FROM USER WHERE userID = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DatabaseUtil.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					return rs.getString(1);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
					
				try {		if(conn != null) conn.close(); }catch(Exception e) { e.printStackTrace();}
				try {		if(pstmt != null) pstmt.close(); }catch(Exception e) { e.printStackTrace();}
				try {		if(rs != null) rs.close(); }catch(Exception e) { e.printStackTrace();}
					
					
			}
			return null; // 회원가입 실패.
		}
			
		
		
		public boolean getUserEmailChecked(String userID) {
			
			String SQL = "SELECT userEmailChecked FROM USER WHERE userID = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DatabaseUtil.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					return rs.getBoolean(1);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
					
				try {		if(conn != null) conn.close(); }catch(Exception e) { e.printStackTrace();}
				try {		if(pstmt != null) pstmt.close(); }catch(Exception e) { e.printStackTrace();}
				try {		if(rs != null) rs.close(); }catch(Exception e) { e.printStackTrace();}
					
					
			}
			return false; // 회원가입 실패.
		}
		
	
		public boolean setUserEmailChecked(String userID) {
			
			String SQL = "UPDATE USER SET userEmailChecked = true Where userID = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DatabaseUtil.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				pstmt.executeUpdate();
				return true;
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
					
				try {		if(conn != null) conn.close(); }catch(Exception e) { e.printStackTrace();}
				try {		if(pstmt != null) pstmt.close(); }catch(Exception e) { e.printStackTrace();}
				try {		if(rs != null) rs.close(); }catch(Exception e) { e.printStackTrace();}
					
					
			}
			return false; // 회원가입 실패.
		}
		
		public ArrayList<EvaluationDTO> getList(String lectureDivide, String searchType, String search, int pageNumber){
			if(lectureDivide.equals("전체")) {
				lectureDivide="";
			}
			ArrayList<EvaluationDTO> evaluationList = null;
			String SQL = "";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				if(searchType.equals("최신순")) {
					SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) LIKE " +
				"? ORDER BY evaluationID DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
						
				} else if(searchType.equals("추천순")) {
					SQL = "SELECT * FROM EVALUATION WHERE lectureDivide LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) LIKE " +
							"? ORDER BY likeCount DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
				}
				conn = DatabaseUtil.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, "%" + lectureDivide + "%");
				pstmt.setString(2, "%" + search + "%");
				rs = pstmt.executeQuery();
				evaluationList = new ArrayList<EvaluationDTO>();
				if(rs.next()) {
					EvaluationDTO evaluation = new EvaluationDTO(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getInt(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8),
							rs.getString(9),
							rs.getString(10),
							rs.getString(11),
							rs.getString(12),
							rs.getString(13),
							rs.getInt(14)
							);
					evaluationList.add(evaluation);
				}
			
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
					
				try {		if(conn != null) conn.close(); }catch(Exception e) { e.printStackTrace();}
				try {		if(pstmt != null) pstmt.close(); }catch(Exception e) { e.printStackTrace();}
				try {		if(rs != null) rs.close(); }catch(Exception e) { e.printStackTrace();}
					
			}
				
			return evaluationList;
			
			}
			public int like(String evaluationID) {
				
				String SQL = "UPDATE EVALUATION SET likeCount = likeCount + 1 WHERE evaluationID = ?";
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					conn = DatabaseUtil.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setInt(1, Integer.parseInt(evaluationID));
					
					return pstmt.executeUpdate();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
						
					try {		if(conn != null) conn.close(); }catch(Exception e) { e.printStackTrace();}
					try {		if(pstmt != null) pstmt.close(); }catch(Exception e) { e.printStackTrace();}
					try {		if(rs != null) rs.close(); }catch(Exception e) { e.printStackTrace();}
						
						
				}
				return -1; 
			}
			
			public int delete(String evaluationID) {
				
				String SQL = "DELETE FROM EVALUATION WHERE evaluationID = ?";
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					conn = DatabaseUtil.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setInt(1, Integer.parseInt(evaluationID));
				
					return pstmt.executeUpdate();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
						
					try {		if(conn != null) conn.close(); }catch(Exception e) { e.printStackTrace();}
					try {		if(pstmt != null) pstmt.close(); }catch(Exception e) { e.printStackTrace();}
					try {		if(rs != null) rs.close(); }catch(Exception e) { e.printStackTrace();}
						
						
				}
				return -1; 
			}
			public String getUserID(String evaluationID) {
				
				String SQL = "SELECT userID FROM EVALUATION WHERE evaluationID = ?";
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					conn = DatabaseUtil.getConnection();
					pstmt = conn.prepareStatement(SQL);
					pstmt.setInt(1, Integer.parseInt(evaluationID));
					rs = pstmt.executeQuery();
					while(rs.next()) {
						return rs.getString(1);
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
						
					try {		if(conn != null) conn.close(); }catch(Exception e) { e.printStackTrace();}
					try {		if(pstmt != null) pstmt.close(); }catch(Exception e) { e.printStackTrace();}
					try {		if(rs != null) rs.close(); }catch(Exception e) { e.printStackTrace();}
						
						
				}
				return null;
			}
}

