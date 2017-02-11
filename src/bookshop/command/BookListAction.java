package bookshop.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;

public class BookListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		List<MngrDataBean> bookList = null;
		String bookKind = request.getParameter("book_kind");
		int count = 0;
		
		// DB연동 - 전체 상품의 수를 얻어냄
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		count = bookProcess.getBookCount();
		
		if (count > 0) {
			// 상품 전체를 테이블에서 얻어내서 bookList에 저장
			bookList = bookProcess.getBooks(bookKind);
			// bookList를 뷰에서 사용할 수 있도록 request 속성에 저장
			request.setAttribute("bookList", bookList);
		}
		
		// 뷰에서 사용할 속성
		request.setAttribute("count", new Integer(count));
		request.setAttribute("book_kind", bookKind);
		request.setAttribute("type", new Integer(0));
		return "/mngr/productProcess/bookList.jsp";
	}

}
