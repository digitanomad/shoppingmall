package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;
import bookshop.bean.QnaDataBean;

public class QnaReplyFormAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		int qnaId = Integer.parseInt(request.getParameter("qna_id"));
		
		// qna_id에 해당하는 QnA를 가져옴
		QnaDBBean qnaProcess = QnaDBBean.getInstance();
		QnaDataBean qna = qnaProcess.updateGetArticle(qnaId);
		
		// QnA답변에 필요한 정보를 얻어냄
		int bookId = qna.getBookId();
		String bookTitle = qna.getBookTitle();
		String qnaContent = qna.getQnaContent();
		byte qora = 2;	//답변글
		
		request.setAttribute("qna_id", new Integer(qnaId));
		request.setAttribute("book_id", new Integer(bookId));
		request.setAttribute("book_title", bookTitle);
		request.setAttribute("qna_content", qnaContent);
		request.setAttribute("qora", new Integer(qora));
		request.setAttribute("type", new Integer(0));
		
		return "/mngr/qnaProcess/qnaReplyForm.jsp";
	}

}
