package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;

public class BookDeleteProAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		String bookKind = request.getParameter("book_kind");
		
		// DB연동 - book_id에 해당하는 상품을 삭제
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		bookProcess.deleteBook(bookId); 
		
		request.setAttribute("book_kind", bookKind);
		return "/mngr/productProcess/bookDeletePro.jsp";
	}

}
