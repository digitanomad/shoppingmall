package bookshop.bean;

import java.sql.Timestamp;

public class MngrDataBean {
	private int bookId; //책의 등록번호
	private String bookKind; //책의 분류
	private String bookTitle; //책이름
	private int bookPrice; //책가격
	private short bookCount; //책의 재고수량
	private String author; //저자
	private String publishingCom; //출판사
	private String publishingDate; //출판일
	private String bookImage; //책이미지명
	private String bookContent; //책의내용
	private byte discountRate; //책의 할인율
	private Timestamp regDate; //책의 등록날짜
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookKind() {
		return bookKind;
	}
	public void setBookKind(String bookKind) {
		this.bookKind = bookKind;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public int getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}
	public short getBookCount() {
		return bookCount;
	}
	public void setBookCount(short bookCount) {
		this.bookCount = bookCount;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublishingCom() {
		return publishingCom;
	}
	public void setPublishingCom(String publishingCom) {
		this.publishingCom = publishingCom;
	}
	public String getPublishingDate() {
		return publishingDate;
	}
	public void setPublishingDate(String publishingDate) {
		this.publishingDate = publishingDate;
	}
	public String getBookImage() {
		return bookImage;
	}
	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}
	public String getBookContent() {
		return bookContent;
	}
	public void setBookContent(String bookContent) {
		this.bookContent = bookContent;
	}
	public byte getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(byte discountRate) {
		this.discountRate = discountRate;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	
}
