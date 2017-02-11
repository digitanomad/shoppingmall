package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;

public class BookUpdateFormAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		String bookKind = request.getParameter("book_kind");
		
		// DB연동 book_id에 해당하는 상품을 얻내서 book에 저장
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		MngrDataBean book =  bookProcess.getBook(bookId);
		
		request.setAttribute("book_id", bookId);
		request.setAttribute("book_kind", bookKind);
        request.setAttribute("book", book);
		request.setAttribute("type", new Integer(0));
		return "/mngr/productProcess/bookUpdateForm.jsp";
	}

}
