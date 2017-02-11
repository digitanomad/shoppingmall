<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/mngr/productProcess/bookList.js"></script>

<c:if test="${empty sessionScope.id}">
	<meta http-equiv="Refresh" content="0;url=/shoppingmall/mg/managerMain.do" >
</c:if>

<div id="listHeader">
	<p>등록된 상품 목록(전체 상품:${count})
	<button id="regist">책 등록</button>
	<button id="bookMain">관리자 메인으로</button>
</div>
<div id="books">
	<c:if test="${count == 0}">
		<ul>
      		<li>등록된 상품이 없습니다.
    	</ul>
	</c:if>
	<c:if test="${count > 0}">
		<table>
			<tr class="title">
				<td align="center"  width="30">번호</td>
				<td align="center"  width="30">책분류</td> 
				<td align="center"  width="100">제목</td>
				<td align="center"  width="50">수량</td> 
				<td align="center"  width="50">가격</td> 
				<td align="center"  width="70">저자</td>
				<td align="center"  width="70">출판사</td>
				<td align="center"  width="50">출판일</td>
				<td align="center"  width="50">책이미지</td>
				<td align="center"  width="30">할인율</td>
				<td align="center"  width="70">등록일</td>
				<td align="center"  width="50">수정</td>
				<td align="center"  width="50">삭제</td>  
			</tr>
			
			<c:set var="number" value="${0}" />
			<c:forEach var="book" items="${bookList}">
			<tr>
				<td align="center"  width="50" >
      			<c:set var="number" value="${number+1}"/>
	  			<c:out value="${number}"/>
				</td>
				<td width="30">${book.getBookKind()}</td>
				<td width="100" align="left">${book.getBookTitle()}</td>
				<td width="50" align="right">${book.getBookPrice()}</td> 
				<td width="50" align="right">
				<c:if test="${book.getBookCount() == 0}">
					<font color="red">일시품절</font>
				</c:if>
				<c:if test="${book.getBookCount() > 0}">
				${book.getBookCount()}
				</c:if>
				</td> 
				<td width="70">${book.getAuthor()}</td>
				<td width="70">${book.getPublishingCom()}</td>
				<td width="50">${book.getPublishingDate()}</td>
				<td width="50">${book.getBookImage()}</td>
				<td width="50">${book.getDiscountRate()}</td>
				<td width="50"><fmt:formatDate pattern="yyyy-MM-dd" value="${book.getRegDate()}"/></td>
				<td width="50">
				<button id="edit" name="${book.getBookId()},${book.getBookKind()}" onclick="edit(this)">수정</button></td>
				<td width="50">
				<button id="delete" name="${book.getBookId()},${book.getBookKind()}" onclick="del(this)">삭제</button></td> 
			</tr>
			</c:forEach>
		</table>
	</c:if>
</div>