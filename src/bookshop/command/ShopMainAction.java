package bookshop.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;

public class ShopMainAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		List<MngrDataBean> bookList = null;
		List<List<MngrDataBean>> bookLists = new ArrayList<List<MngrDataBean>>();
		
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		
		// 카테고리별 최신상품 3새씩 얻어내서 List에 저장
		for (int i = 1; i <= 3; i++) {
			bookList = bookProcess.getBooks(i + "00", 3);
			if (bookList != null && !bookList.isEmpty()) {
				bookLists.add(bookList);
			}
		}
		
		// 해당 페이지로 보낼 내용 설정
		request.setAttribute("bookLists", bookLists);
		// 사용자 화면을 의미하는 값을 설정
		request.setAttribute("type", new Integer(1));
		return "/shop/shopMain.jsp";
	}

}
