package bookshop.bean;

public class CartDataBean {
	private int cartId;	//장바구니의 아이디
	private String buyer;	//구매자
	private int bookId;	//구매된 책의 아이디
	private String bookTitle;	//구매된 책명
	private int  buyPrice;	//판매가
	private byte buyCount;	//판매수량
	private String bookImage;	//책이미지
	
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public int getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	public byte getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(byte buyCount) {
		this.buyCount = buyCount;
	}
	public String getBookImage() {
		return bookImage;
	}
	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}
	
}
