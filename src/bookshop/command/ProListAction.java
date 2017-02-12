package bookshop.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;

public class ProListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		List<MngrDataBean> bookList = null;
		int count = 0;
		String bookKind = request.getParameter("book_kind");
		
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		
		// kind값이 all이면 전체 상품의 수를 얻어냄
		if(bookKind.equals("all")) {
            count = bookProcess.getBookCount(); 
		}
		// all이 아니면 해당 카테고리의 상품수를 얻어냄
		else {
			count = bookProcess.getBookCount(bookKind);
		}
		
        if (count > 0){
        	// 상품목록을 얻어냄
        	bookList = bookProcess.getBooks(bookKind);
        	request.setAttribute("bookList", bookList);
        }
        
        // 해당 뷰에서 사용할 속성
        request.setAttribute("count", new Integer(count));
        request.setAttribute("book_kind", bookKind);
        request.setAttribute("type", new Integer(1));
		
        return "/shop/showList.jsp";
	}

}
