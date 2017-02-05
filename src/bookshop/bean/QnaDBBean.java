package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class QnaDBBean {
	// QnaDBBean 전역 객체 생성 - 한 개의 객체만 생성해서 공유
	private static QnaDBBean instance = new QnaDBBean();
	
	public static QnaDBBean getInstance() {
		return instance;
	}
	
	private QnaDBBean() {}
	
	/**
	 * 커넥션풀로부터 Connection 객체를 얻어냄.
	 * @return
	 * @throws NamingException 
	 * @throws SQLException 
	 */
	private Connection getConnection() throws NamingException, SQLException {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/jsptest");
		return ds.getConnection();
	}
	
	/**
	 * qna테이블에 글을 추가 - 사용자가 작성하는 글
	 * @param article
	 * @return
	 */
	@SuppressWarnings("resource")
	public int insertArticle(QnaDataBean article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		int groupId = 1;
		
		try {
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select max(qna_id) from qna");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				x = rs.getInt(1);
			}
			
			if (x > 0) {
				groupId = rs.getInt(1) + 1;
			}
			
			// 쿼리를 작성, board 테이블에 새로운 레코드 추가
			String sql = "insert into qna(book_id,book_title,qna_writer,qna_content,";
		    sql += "group_id,qora,reply,reg_date) values(?,?,?,?,?,?,?,?)";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, article.getBookId());
            pstmt.setString(2, article.getBookTitle());
            pstmt.setString(3, article.getQnaWriter());
            pstmt.setString(4, article.getQnaContent());
            pstmt.setInt(5, groupId);
            pstmt.setInt(6, article.getQora());
            pstmt.setInt(7, article.getReply());
			pstmt.setTimestamp(8, article.getRegDate());
			
			pstmt.executeUpdate();
			
			// 레코드 추가 성공
			x = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); }catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
			if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
		
		return x;
	}
	
	/**
	 * qna 테이블에 글을 추가 - 관리자가 작성한 답변
	 * @param article
	 * @param qnaId
	 * @return
	 */
	@SuppressWarnings("resource")
	public int insertArticle(QnaDataBean article, int qnaId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int x = 0;
		
		try {
			conn = getConnection();
			
			// 쿼리를 작성, board 테이블에 새로운 레코드 추가
			String sql = "insert into qna(book_id,book_title,qna_writer,qna_content,";
		    sql += "group_id,qora,reply,reg_date) values(?,?,?,?,?,?,?,?)";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, article.getBookId());
            pstmt.setString(2, article.getBookTitle());
            pstmt.setString(3, article.getQnaWriter());
            pstmt.setString(4, article.getQnaContent());
            pstmt.setInt(5, article.getGroupId());
            pstmt.setInt(6, article.getQora());
            pstmt.setInt(7, article.getReply());
			pstmt.setTimestamp(8, article.getRegDate());
			
			pstmt.executeUpdate();
			
			sql = "update qna set reply=? where qna_id=?";
			pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
		    pstmt.setInt(2, qnaId);
            pstmt.executeUpdate();
			
			// 레코드 추가 성공
			x = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
			if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
		
		return x;
	}
	
	/**
	 * qna 테이블에 저장된 전체글의 수를 얻어냄
	 * @return
	 */
	public int getArticleCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		
		try {
			conn = getConnection();
			
			String sql = "select count(*) from qna";
            
            pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); }catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
			if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
		
		return x;
	}
	
	/**
	 * 특정 책에 대해 작성한 qna글의 수를 얻어냄
	 * @param bookId
	 * @return
	 */
	public int getArticleCount(int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		
		try {
			conn = getConnection();
			
			String sql = "select count(*) from qna where book_id = "+ bookId;
            
            pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); }catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
			if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
		
		return x;
	}
	
	/**
	 * 지정한 수에 해당하는 qna글의 수를 얻어냄.
	 * @param count
	 * @return
	 */
	public List<QnaDataBean> getArticles(int count) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaDataBean> articleList = null;
		
		try {
			conn = getConnection();
			
			String sql = "select * from qna order by group_id desc, qora asc";
            
            pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				articleList = new ArrayList<QnaDataBean>(count);
				do {
					QnaDataBean article= new QnaDataBean();
					article.setQnaId(rs.getInt("qna_id")); 
					article.setBookId(rs.getInt("book_id"));
					article.setBookTitle(rs.getString("book_title"));
					article.setQnaWriter(rs.getString("qna_writer"));
					article.setQnaContent(rs.getString("qna_content"));
					article.setGroupId(rs.getInt("group_id"));
					article.setQora(rs.getByte("qora"));
					article.setReply(rs.getByte("reply"));
					article.setRegDate(rs.getTimestamp("reg_date"));
					
					articleList.add(article);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); }catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
			if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
		
		return articleList;
	}
	
	/**
	 * 특정 책에 대해 작성한 qna글을 지정한 수만큼 얻어냄.
	 * @param count
	 * @param bookId
	 * @return
	 */
	public List<QnaDataBean> getArticles(int count, int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaDataBean> articleList = null;
		
		try {
			conn = getConnection();
			
			String sql = "select * from qna where book_id=" + bookId + " order by group_id desc, qora asc";
            
            pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				articleList = new ArrayList<QnaDataBean>(count);
				do {
					QnaDataBean article= new QnaDataBean();
					article.setQnaId(rs.getInt("qna_id")); 
					article.setBookId(rs.getInt("book_id"));
					article.setBookTitle(rs.getString("book_title"));
					article.setQnaWriter(rs.getString("qna_writer"));
					article.setQnaContent(rs.getString("qna_content"));
					article.setGroupId(rs.getInt("group_id"));
					article.setQora(rs.getByte("qora"));
					article.setReply(rs.getByte("reply"));
					article.setRegDate(rs.getTimestamp("reg_date"));
					
					articleList.add(article);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); }catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
			if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
		
		return articleList;
	}
	
	/**
	 * QnA글 수정폼에서 사용할 글의 내용
	 * @param qnaId
	 * @return
	 */
	public QnaDataBean updateGetArticle(int qnaId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		QnaDataBean article = null;
		
		try {
			conn = getConnection();
			
			String sql = "select * from qna where qna_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				article = new QnaDataBean();
				article.setQnaId(rs.getInt("qna_id")); 
				article.setBookId(rs.getInt("book_id"));
				article.setBookTitle(rs.getString("book_title"));
                article.setQnaWriter(rs.getString("qna_writer"));
                article.setQnaContent(rs.getString("qna_content"));
                article.setGroupId(rs.getInt("group_id"));
                article.setQora(rs.getByte("qora"));
                article.setReply(rs.getByte("reply"));
			    article.setRegDate(rs.getTimestamp("reg_date")); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return article;
	}
	
	/**
	 * QnA글 수정처리에서 사용
	 * @param article
	 * @return
	 */
	public int updateArticle(QnaDataBean article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int x = -1;
		
		try {
			conn = getConnection();
			
			String sql = "update qna set qna_content=? where qna_id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getQnaContent());
			pstmt.setInt(2, article.getQnaId());
			pstmt.executeUpdate();
			x = 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return x;
	}
	
	/**
	 * QnA글 수정삭제 처리 시 사용
	 * @param qnaId
	 * @return
	 */
	public int deleteArticle(int qnaId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int x = -1;
		
		try {
			conn = getConnection();
			
			String sql = "delete from qna where qna_id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaId);
			pstmt.executeUpdate();
			x = 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return x;
	}
	
}
