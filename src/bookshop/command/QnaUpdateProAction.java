package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;
import bookshop.bean.QnaDataBean;

public class QnaUpdateProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		int qnaId =  Integer.parseInt(request.getParameter("qna_id"));
		String qnaContent = request.getParameter("qna_content");
		
		// 수정에 필요한 정보구성
		QnaDataBean qna = new QnaDataBean();
		qna.setQnaId(qnaId);
		qna.setQnaContent(qnaContent);
		
		// qna수정
		QnaDBBean qnaProcess = QnaDBBean.getInstance();
		int check = qnaProcess.updateArticle(qna);
		
		request.setAttribute("check", new Integer(check));
		
		return "/qna/qnaUpdatePro.jsp";
	}

}
