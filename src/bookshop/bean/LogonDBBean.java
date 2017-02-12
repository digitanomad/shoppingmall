package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import work.crypt.BCrypt;
import work.crypt.SHA256;

public class LogonDBBean {
	// LogonDBBean 전역 객체 생성 - 한개의 객체만 생성해서 공유
    private static LogonDBBean instance = new LogonDBBean();
    
    // LogonDBBean객체를 리턴하는 메소드
    public static LogonDBBean getInstance() {
        return instance;
    }
    
    private LogonDBBean() { }
    
    /**
     * 커넥션 풀에서 커넥션 객체를 얻어내는 메소드
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    private Connection getConnection() throws NamingException, SQLException {
    	Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/jsptest");
        return ds.getConnection();
    }
    
    /**
     * 회원가입 처리에서 사용하는 메소드
     * @param member
     */
    public void insertMember(LogonDataBean member) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	
    	SHA256 sha = SHA256.getInsatnce();
    	try {
    		conn = getConnection();
    		
    		String orgPass = member.getPasswd();
    		String shaPass = sha.getSha256(orgPass.getBytes());
    		String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
    		
    		pstmt = conn.prepareStatement("insert into member values (?,?,?,?,?,?)");
    		pstmt.setString(1, member.getId());
            pstmt.setString(2, bcPass);
            pstmt.setString(3, member.getName());
            pstmt.setTimestamp(4, member.getRegDate());
            pstmt.setString(5, member.getAddress());
            pstmt.setString(6, member.getTel());
            
            pstmt.executeUpdate();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	} finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    }
    
    /**
     * 로그인 폼 처리의 사용자 인증 처리에서 사용하는 메소드
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
    		
    		pstmt = conn.prepareStatement("select passwd from member where id = ?");
    		pstmt.setString(1, id);
    		rs = pstmt.executeQuery();
    		
    		if (rs.next()) {
				String dbPasswd = rs.getString("passwd");
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
            
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    	
    	return x;
    }
    
    /**
     * 아이디 중복 확인에서 아이디의 중복 여부를 확인하는 메소드
     * @param id
     * @return
     */
    public int confirmId(String id) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	int x = -1;
    	
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select id from member where id = ?");
    		pstmt.setString(1, id);
    		rs = pstmt.executeQuery();
    		
    		if (rs.next()) {
    			// 같은 아이디 있음
				x = 1;
			} else {
				// 같은 아이디 없음
				x = -1;
			}
            
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    	
    	return x;
    }
    
    /**
     * 주어진 id에 해당하는 회원정보를 얻어내는 메소드
     * @param id
     * @return
     */
    public LogonDataBean getMember(String id) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	LogonDataBean member = null;
    	
    	try {
    		conn = getConnection();
    		
    		pstmt = conn.prepareStatement("select * from member where id = ?");
    		pstmt.setString(1, id);
    		rs = pstmt.executeQuery();
    		
    		if (rs.next()) {
    			member = new LogonDataBean();
    			member.setId(rs.getString("id"));
    			member.setName(rs.getString("name"));
                member.setRegDate(rs.getTimestamp("reg_date"));
                member.setAddress(rs.getString("address"));
                member.setTel(rs.getString("tel"));
			}
            
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    	
    	return member;
    }
    
    /**
     * 주어진 id, passwd에 해당하는 회원정보를 얻어내는 메소드
     * @param id
     * @param passwd
     * @return
     */
    public LogonDataBean getMember(String id, String passwd) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	LogonDataBean member = null;
    	
    	SHA256 sha = SHA256.getInsatnce();
    	try {
    		conn = getConnection();
    		
    		String orgPass = passwd;
    		String shaPass = sha.getSha256(orgPass.getBytes());
    		
    		pstmt = conn.prepareStatement("select * from member where id = ?");
    		pstmt.setString(1, id);
    		rs = pstmt.executeQuery();
    		
    		if (rs.next()) {
    			String dbPasswd = rs.getString("passwd");
    			// 사용자가 입력한 비밀번호와 테이블의 비밀번호가 같으면 수행
    			if (BCrypt.checkpw(shaPass, dbPasswd)) {
    				member = new LogonDataBean();
        			member.setId(rs.getString("id"));
        			member.setName(rs.getString("name"));
                    member.setRegDate(rs.getTimestamp("reg_date"));
                    member.setAddress(rs.getString("address"));
                    member.setTel(rs.getString("tel"));
				}
			}
            
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    	
    	return member;
    }
    
    /**
     * 회원 정보 수정을 처리하는 메소드
     * @param member
     * @return
     */
    @SuppressWarnings("resource")
	public int updateMember(LogonDataBean member) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	int x = -1;
    	
    	SHA256 sha = SHA256.getInsatnce();
    	try {
    		conn = getConnection();
    		
    		String orgPass = member.getPasswd();
    		String shaPass = sha.getSha256(orgPass.getBytes());
    		
    		pstmt = conn.prepareStatement("select passwd from member where id = ?");
    		pstmt.setString(1, member.getId());
    		rs = pstmt.executeQuery();
    		
    		if (rs.next()) {
    			String dbPasswd = rs.getString("passwd");
    			// 사용자가 입력한 비밀번호와 테이블의 비밀번호가 같으면 수행
    			if (BCrypt.checkpw(shaPass, dbPasswd)) {
    				pstmt = conn.prepareStatement("update member set name=?,address=?,tel=? " + "where id=?");
                    pstmt.setString(1, member.getName());
                    pstmt.setString(2, member.getAddress());
                    pstmt.setString(3, member.getTel());
                    pstmt.setString(4, member.getId());
                    pstmt.executeUpdate();
                    // 회원정보 수정 처리 성공
                    x = 1;
				} else {
					// 회원정보 수정 처리 실패
					x = 0;
				}
			}
            
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    	
    	return x;
    }
    
    /**
     * 회원 정보를 삭제하는 메소드
     * @param id
     * @param passwd
     * @return
     */
    @SuppressWarnings("resource")
	public int deleteMember(String id, String passwd) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	int x = -1;
    	
    	SHA256 sha = SHA256.getInsatnce();
    	try {
    		conn = getConnection();
    		
    		String orgPass = passwd;
    		String shaPass = sha.getSha256(orgPass.getBytes());
    		
    		pstmt = conn.prepareStatement("select passwd from member where id = ?");
    		pstmt.setString(1, id);
    		rs = pstmt.executeQuery();
    		
    		if (rs.next()) {
    			String dbPasswd = rs.getString("passwd");
    			// 사용자가 입력한 비밀번호와 테이블의 비밀번호가 같으면 수행
    			if (BCrypt.checkpw(shaPass, dbPasswd)) {
    				pstmt = conn.prepareStatement("delete from member where id=?");
                    pstmt.setString(1, id);
                    pstmt.executeUpdate();
                    // 회원탈퇴 처리 성공
                    x = 1;
				} else {
					// 회원탈퇴 처리 실패
					x = 0;
				}
			}
            
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    	
    	return x;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
