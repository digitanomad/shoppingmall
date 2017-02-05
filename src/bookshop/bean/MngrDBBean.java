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

import work.crypt.BCrypt;
import work.crypt.SHA256;

public class MngrDBBean {
	// MngrDBBean 전역 객체 생성 - 한 개의 객체만 생성해서 공유
	private static MngrDBBean instance = new MngrDBBean();
	
	public static MngrDBBean getInstance() {
		return instance;
	}
	
	private MngrDBBean() {}
	
	/**
	 * 커넥션 풀에서 커넥션 객체를 얻어내는 메소드
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
	 * 관리자 인증 메소드
	 * @param id
	 * @param passwd
	 * @return
	 */
	public int userCheck(String id, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;
		
		SHA256 sha = SHA256.getInsatnce();
		try {
			conn = getConnection();
			
			String orgPass = passwd;
			String shaPass = sha.getSha256(orgPass.getBytes());
			
			pstmt = conn.prepareStatement("select managerPasswd from manager where managerId = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				String dbPasswd = rs.getString("managerPasswd");
				if (BCrypt.checkpw(shaPass, dbPasswd)) {
					// 인증성공
					x = 1;
				} else {
					// 비밀번호 틀림
					x = 0;
				}
			} else {
				// 아이디 없음
				x = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return x;
	}
	
	/**
	 * @param book
	 */
	public void insertBook(MngrDataBean book) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "insert into book (book_kind, book_title, book_price,";
			sql += "book_count,author,publishing_com,publishing_date,book_image,";
            sql += "book_content,discount_rate,reg_date) values (?,?,?,?,?,?,?,?,?,?,?)";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.getBookKind());
            pstmt.setString(2, book.getBookTitle());
            pstmt.setInt(3, book.getBookPrice());
            pstmt.setShort(4, book.getBookCount());
            pstmt.setString(5, book.getAuthor());
            pstmt.setString(6, book.getPublishingCom());
			pstmt.setString(7, book.getPublishingDate());
			pstmt.setString(8, book.getBookImage());
			pstmt.setString(9, book.getBookContent());
			pstmt.setByte(10,book.getDiscountRate());
			pstmt.setTimestamp(11, book.getRegDate());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
			if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
	}
	
	/**
	 * 이미 등록된 책을 검증
	 * @param kind
	 * @param bookName
	 * @param author
	 * @return
	 */
	public int registedBookConfirm(String kind, String bookName, String author) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;
		
		try {
			conn = getConnection();
			
			String sql = "select book_name from book ";
            sql += " where book_kind = ? and book_name = ? and author = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, bookName);
			pstmt.setString(3, author);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				// 해당 책이 이미 등록되어 있음
				x = 1;
			} else {
				// 해당 책이 이미 등록되어 있지 않음
				x = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return x;
	}
	
	/**
	 * 전체등록된 책의 수를 얻어내는 메소드
	 * @return
	 */
	public int getBookCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		
		try {
			conn = getConnection();
			
			String sql = "select count(*) from book";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return x;
	}
	
	/**
	 * 해당 분류의 책의 수를 얻어내는 메소드
	 * @param bookKind
	 * @return
	 */
	public int getBookCount(String bookKind) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int x = 0;
		int kind = Integer.parseInt(bookKind);
		
		try {
			conn = getConnection();
			String sql = "select count(*) from book where book_kind=" + kind;
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return x;
	}
	
	/**
	 * 책의 제목을 얻어냄
	 * @param bookId
	 * @return
	 */
	public String getBookTitle(int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String x = "";
		
		try {
			conn = getConnection();
			String sql = "select book_title from book where book_id=" + bookId;
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				x = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return x;
	}
	
	/**
	 * 분류별 또는 전체등록된 책의 정보를 얻어내는 메소드
	 * @param bookKind
	 * @return
	 */
	public List<MngrDataBean> getBooks(String bookKind) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<MngrDataBean> bookList = null;
		
		try {
			conn = getConnection();
			String sql1 = "select * from book";
			String sql2 = "select * from book";
			sql2 += "where book_kind = ? order by reg_date desc";
			
			if ("all".equals(bookKind) || "".equals(bookKind)) {
				pstmt = conn.prepareStatement(sql1);
			} else {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, bookKind);
			}
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bookList = new ArrayList<MngrDataBean>();
				do {
					MngrDataBean book = new MngrDataBean();
					book.setBookId(rs.getInt("book_id"));
                    book.setBookKind(rs.getString("book_kind"));
                    book.setBookTitle(rs.getString("book_title"));
                    book.setBookPrice(rs.getInt("book_price"));
                    book.setBookCount(rs.getShort("book_count"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublishingCom(rs.getString("publishing_com"));
                    book.setPublishingDate(rs.getString("publishing_date"));
                    book.setBookImage(rs.getString("book_image"));
                    book.setDiscountRate(rs.getByte("discount_rate"));
                    book.setRegDate(rs.getTimestamp("reg_date"));
                    
                    bookList.add(book);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return bookList;
	}
	
	/**
	 * 쇼핑몰 메인에 표시하기 위해서 사용하는 분류별 신간책목록을 얻어내는 메소드
	 * @param bookKind
	 * @param count
	 * @return
	 */
	public MngrDataBean[] getBooks(String bookKind, int count) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		MngrDataBean[] bookList = null;
		int i = 0;
		
		try {
			conn = getConnection();
			
			String sql = "select * from book where book_kind = ? ";
            sql += "order by reg_date desc limit ?,?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bookKind);
			pstmt.setInt(2, 0);
			pstmt.setInt(3, count);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bookList = new MngrDataBean[count];
				do {
					MngrDataBean book = new MngrDataBean();
					book.setBookId(rs.getInt("book_id"));
                    book.setBookKind(rs.getString("book_kind"));
                    book.setBookTitle(rs.getString("book_title"));
                    book.setBookPrice(rs.getInt("book_price"));
                    book.setBookCount(rs.getShort("book_count"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublishingCom(rs.getString("publishing_com"));
                    book.setPublishingDate(rs.getString("publishing_date"));
                    book.setBookImage(rs.getString("book_image"));
                    book.setDiscountRate(rs.getByte("discount_rate"));
                    book.setRegDate(rs.getTimestamp("reg_date"));
                    
                    bookList[i] = book;
                    i++;
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return bookList;
	}
	
	/**
	 * bookId에 해당하는 책의 정보를 얻어내는 메소드
	 * 등록된 책을 수정하기 위해 수정폼으로 읽어들이기 위한 메소드
	 * @param bookId
	 * @return
	 */
	public MngrDataBean getBook(int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		MngrDataBean book = null;
		
		try {
			conn = getConnection();
			
			String sql = "select * from book where book_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				book = new MngrDataBean();
                
                book.setBookKind(rs.getString("book_kind"));
                book.setBookTitle(rs.getString("book_title"));
                book.setBookPrice(rs.getInt("book_price"));
                book.setBookCount(rs.getShort("book_count"));
                book.setAuthor(rs.getString("author"));
                book.setPublishingCom(rs.getString("publishing_com"));
                book.setPublishingDate(rs.getString("publishing_date"));
                book.setBookImage(rs.getString("book_image"));
                book.setBookContent(rs.getString("book_content"));
                book.setDiscountRate(rs.getByte("discount_rate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return book;
	}
	
	/**
	 * 등록된 책의 정보를 수정 시 사용하는 메소드
	 * @param book
	 * @param bookId
	 */
	public void updateBook(MngrDataBean book, int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update book set book_kind=?,book_title=?,book_price=?";
            sql += ",book_count=?,author=?,publishing_com=?,publishing_date=?";
            sql += ",book_image=?,book_content=?,discount_rate=?";
            sql += " where book_id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getBookKind());
            pstmt.setString(2, book.getBookTitle());
            pstmt.setInt(3, book.getBookPrice());
            pstmt.setShort(4, book.getBookCount());
            pstmt.setString(5, book.getAuthor());
            pstmt.setString(6, book.getPublishingCom());
			pstmt.setString(7, book.getPublishingDate());
			pstmt.setString(8, book.getBookImage());
			pstmt.setString(9, book.getBookContent());
			pstmt.setByte(10, book.getDiscountRate());
			pstmt.setInt(11, bookId);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
	}
	
	/**
	 * bookId에 해당하는 책의 정보를 삭제 시 사용하는 메소드
	 * @param bookId
	 */
	public void deleteBook(int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "delete from book where book_id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
	}
	
}
