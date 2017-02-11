package bookshop.command;

import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BookUpdateProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		String fileName = "";
		// 웹 어플리케이션상의 절대 경로 저장
		String realFolder = "";
		// 파일 업로드 폴더 지정
		String saveFolder = "/bookImage";
		// 인코딩 타입
		String encType = "utf-8";
		// 최대 업로드될 파일 크기 1mb
		int maxSize = 1 * 1024 * 1024;
		
		MultipartRequest imageUp = null;
		
		// 웹 어플리케이션상의 절대 경로를 구함
		ServletContext context = request.getServletContext();
		realFolder = context.getRealPath(saveFolder);
		
		try {
			// 파일 업로드를 수행하는 MultipartRequest 객체 생성
			imageUp = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
			
			// <input type="file">인 모든 파라미터를 얻어냄
			Enumeration<?> files = imageUp.getFileNames();
			
			// 파일 정보가 있다면
			while (files.hasMoreElements()) {
				// input 태그의 속성이 file인 태그의 name 속성 값 : 파라미터 이름
				String name = (String) files.nextElement();
				// 서버에 저장된 파일 이름
				fileName = imageUp.getFilesystemName(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 폼으로부터 넘어온 정보 중 파일이 아닌 정보를 얻어냄
		MngrDataBean book = new MngrDataBean();
		int bookId = Integer.parseInt(imageUp.getParameter("book_id"));
		String bookKind = imageUp.getParameter("book_kind");
		String bookTitle = imageUp.getParameter("book_title");
		String bookPrice = imageUp.getParameter("book_price");
		String bookCount = imageUp.getParameter("book_count");
		String author = imageUp.getParameter("author");
		String publishingCom = imageUp.getParameter("publishing_com");
		String bookContent = imageUp.getParameter("book_content");
		String discountRate = imageUp.getParameter("discount_rate");
		
		String year = imageUp.getParameter("publishing_year");
		String month = (imageUp.getParameter("publishing_month").length()==1) ?
			 "0"+ imageUp.getParameter("publishing_month") :
			 imageUp.getParameter("publishing_month");
		String day = (imageUp.getParameter("publishing_day").length()==1) ?
			 "0"+ imageUp.getParameter("publishing_day"):
			  imageUp.getParameter("publishing_day");
		book.setBookKind(bookKind);
		book.setBookTitle(bookTitle);
		book.setBookPrice(Integer.parseInt(bookPrice));
		book.setBookCount(Short.parseShort(bookCount));
		book.setAuthor(author);
		book.setPublishingCom(publishingCom);
		book.setPublishingDate(year + "-" + month + "-" + day);
		book.setBookImage(fileName);
		book.setBookContent(bookContent);
		book.setDiscountRate(Byte.parseByte(discountRate));
		book.setRegDate(new Timestamp(System.currentTimeMillis()));
		
		// DB연동 - 상품 수정 처리
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		bookProcess.updateBook(book, bookId);
		
		request.setAttribute("book_kind", bookKind);
		return "/mngr/productProcess/bookUpdatePro.jsp";
	}

}
