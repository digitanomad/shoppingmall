package bookshop.command;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;
import bookshop.bean.QnaDataBean;

public class QnaProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		// 폼에서 입력 후 넘어온 qna 내용
		String qnaWriter =  request.getParameter("qna_writer");
		String bookTitle =  request.getParameter("book_title");
		String qnaContent =  request.getParameter("qna_content");
		int bookId =  Integer.parseInt(request.getParameter("book_id"));
		Byte qora =  Byte.parseByte(request.getParameter("qora"));
		byte reply = 0; //답변여부 - 미답변
		
		// qna를 추가하기 위한 정보작성
		QnaDataBean qna = new QnaDataBean();
		qna.setBookId(bookId);
		qna.setBookTitle(bookTitle);
		qna.setQnaContent(qnaContent);
        qna.setQnaWriter(qnaWriter);
        qna.setReply(reply);
        qna.setRegDate(new Timestamp(System.currentTimeMillis()));
		qna.setQora(qora);
		
		// qna를 테이블에 추가
		QnaDBBean qnaProcess = QnaDBBean.getInstance();
		int check = qnaProcess.insertArticle(qna);
		
		request.setAttribute("check", new Integer(check));
		return "/qna/qnaPro.jsp";
	}

}
