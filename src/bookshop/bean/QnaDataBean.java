package bookshop.bean;

import java.sql.Timestamp;

public class QnaDataBean {
	private int qnaId;	//qna글번호
	private int bookId;	//책의 등록번호
	private String bookTitle;	//책이름
	private String qnaWriter;	//qna작성자
	private String qnaContent;	//qna내용
	private int groupId;	//qna그룹아이디
	private byte qora;	//qna그룹내의 순서
	private byte reply;	//답변여부
	private Timestamp regDate;	//qna작성일
	
	public int getQnaId() {
		return qnaId;
	}
	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
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
	public String getQnaWriter() {
		return qnaWriter;
	}
	public void setQnaWriter(String qnaWriter) {
		this.qnaWriter = qnaWriter;
	}
	public String getQnaContent() {
		return qnaContent;
	}
	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public byte getQora() {
		return qora;
	}
	public void setQora(byte qora) {
		this.qora = qora;
	}
	public byte getReply() {
		return reply;
	}
	public void setReply(byte reply) {
		this.reply = reply;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
}
