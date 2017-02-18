package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;

public class QnaDeleteProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		int qnaId =  Integer.parseInt(request.getParameter("qna_id"));
		
		// qna_id에 해당하는 qna삭제
		QnaDBBean qnaProcess = QnaDBBean.getInstance();
		int check = qnaProcess.deleteArticle(qnaId);
		
		request.setAttribute("check", new Integer(check));
		
		return "/qna/qnaDeletePro.jsp";
	}

}
