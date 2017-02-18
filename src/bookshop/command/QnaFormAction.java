package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;

public class QnaFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String bookKind = request.getParameter("book_kind");
		int bookId =Integer.parseInt(request.getParameter("book_id"));  
		
		// book_id에 해당하는 book_title을 얻어냄
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		String bookTitle = bookProcess.getBookTitle(bookId);
	    
		request.setAttribute("book_kind", bookKind);
	    request.setAttribute("book_id", new Integer(bookId));
	    request.setAttribute("book_title", bookTitle);
	    request.setAttribute("qora", new Integer(1));
		request.setAttribute("type", new Integer(1));
		
		return "/qna/qnaForm.jsp";
	}

}
