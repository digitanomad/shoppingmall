package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CartDBBean {
	private static CartDBBean instance = new CartDBBean();
	   
    public static CartDBBean getInstance() {
    	return instance;
    }
   
    private CartDBBean() {}
   
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/jsptest");
        return ds.getConnection();
    }
    
    /**
     * [장바구니에 담기]를 클릭하면 수행되는 것으로 cart 테이블에 새로운 레코드를 추가
     * @param cart
     */
    public void insertCart(CartDataBean cart) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	String sql = "";
    	
    	try {
    		conn = getConnection();
    		sql = "insert into cart (book_id, buyer," +
            		"book_title,buy_price,buy_count,book_image) " +
            		"values (?,?,?,?,?,?)";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, cart.getBookId());
            pstmt.setString(2, cart.getBuyer());
            pstmt.setString(3, cart.getBookTitle());
            pstmt.setInt(4, cart.getBuyPrice());
            pstmt.setByte(5, cart.getBuyCount());
            pstmt.setString(6, cart.getBookImage());
            
            pstmt.executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    }
    
    /**
     * id에 해당하는 레코드의 수를 얻어내는 메소드
     * @param id
     * @return
     */
    public int getListCount(String id) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	String sql = "";
    	
    	int x = 0;
    	
    	try {
    		conn = getConnection();
    		sql = "select count(*) from cart where buyer=?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
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
     * id에 해당하는 레코드의 목록을 얻어내는 메소드
     * @param id
     * @param count
     * @return
     */
    public List<CartDataBean> getCart(String id, int count) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	CartDataBean cart = null;
    	String sql = "";
    	
    	List<CartDataBean> lists = null;
    	
    	try {
    		conn = getConnection();
    		sql = "select * from cart where buyer=?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		
    		rs = pstmt.executeQuery();
    		
    		lists = new ArrayList<CartDataBean>(count);
    		while (rs.next()) {
				cart = new CartDataBean();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setBookId(rs.getInt("book_id"));
				cart.setBookTitle(rs.getString("book_title"));
				cart.setBuyPrice(rs.getInt("buy_price"));
				cart.setBuyCount(rs.getByte("buy_count")); 
				cart.setBookImage(rs.getString("book_image"));
           	 
				lists.add(cart);
			}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    	
    	return lists;
    }
    
    /**
     * 장바구니에서 수량을 수정시 실행되는 메소드
     * @param cartId
     * @param count
     */
    public void updateCount(int cartId, byte count) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	String sql = "";
    	
    	try {
    		conn = getConnection();
    		sql = "update cart set buy_count=? where cart_id=?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setByte(1, count);
            pstmt.setInt(2, cartId);
            
            pstmt.executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    }
    
    /**
     * 장바구니에서 cart_id에대한 레코드를 삭제하는 메소드
     * @param cartId
     */
    public void deleteList(int cartId) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	String sql = "";
    	
    	try {
    		conn = getConnection();
    		sql = "delete from cart where cart_id=?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, cartId);
            
            pstmt.executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    }
    
    /**
     * id에 해당하는 모든 레코드를 삭제하는 메소드로 [장바구니 비우기]단추를 클릭시 실행
     * @param id
     */
    public void deleteAll(String id) {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	String sql = "";
    	
    	try {
    		conn = getConnection();
    		sql = "delete from cart where buyer=?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
            
            pstmt.executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    	}
    }
}
