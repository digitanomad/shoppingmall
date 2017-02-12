package bookshop.command;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;
import bookshop.bean.QnaDBBean;
import bookshop.bean.QnaDataBean;

public class BookContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		List<QnaDataBean> qnaLists;
		String bookKind = request.getParameter("book_kind");
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		
		// book_id에 해당하는 상품을 얻어냄
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		MngrDataBean book = bookProcess.getBook(bookId);
		
		// book_id에 해당하는 상품의 QnA 수를 얻어냄
		QnaDBBean qnaProcess = QnaDBBean.getInstance();
		int count = qnaProcess.getArticleCount(bookId);
		
		if (count > 0){
			// book_id에 해당하는 상품의 QnA를 얻어냄 
			qnaLists = qnaProcess.getArticles(count, bookId);
        	request.setAttribute("qnaLists", qnaLists);
        }

		request.setAttribute("book", book);
		request.setAttribute("book_id", new Integer(bookId));
		request.setAttribute("book_kind", bookKind);
		request.setAttribute("count", new Integer(count));
		request.setAttribute("type", new Integer(1));
		
		return "/shop/bookContent.jsp";
	}

}
