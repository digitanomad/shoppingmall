package bookshop.bean;

import java.sql.Timestamp;

public class BuyDataBean {
	private Long buyId;	//구매아이디
	private String buyer;	//구매자
	private int bookId;	//구매된 책아이디
	private String bookTitle;	//구매된 책명
	private int buyPrice;	//판매가
	private byte buyCount;	//판매수량
	private String bookImage;	//책이미지
	private Timestamp buyDate;	//구매일자
	private String account;	//결제계좌
	private String deliveryName;	//배송지
	private String deliveryTel;	//배송지 전화번호
	private String deliveryAddress;	//배송지 주소
	private String sanction;	//배송상황
	
	public Long getBuyId() {
		return buyId;
	}
	public void setBuyId(Long buyId) {
		this.buyId = buyId;
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
	public Timestamp getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Timestamp buyDate) {
		this.buyDate = buyDate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getDeliveryTel() {
		return deliveryTel;
	}
	public void setDeliveryTel(String deliveryTel) {
		this.deliveryTel = deliveryTel;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getSanction() {
		return sanction;
	}
	public void setSanction(String sanction) {
		this.sanction = sanction;
	}
	
}
