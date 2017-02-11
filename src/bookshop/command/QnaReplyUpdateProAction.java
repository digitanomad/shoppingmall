package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;
import bookshop.bean.QnaDataBean;

public class QnaReplyUpdateProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		int qnaId =  Integer.parseInt(request.getParameter("qna_id"));
		String qnaContent = request.getParameter("qna_content");
		
		// QnA 답변글 수정 정보 설정
		QnaDataBean qna = new QnaDataBean();
		qna.setQnaId(qnaId);
		qna.setQnaContent(qnaContent);
		
		// QnA 답변글 수정 처리
		QnaDBBean qnaProcess = QnaDBBean.getInstance();
		int check = qnaProcess.updateArticle(qna);
		
		request.setAttribute("check", new Integer(check));
		return "/mngr/qnaProcess/qnaReplyUpdatePro.jsp";
	}

}
