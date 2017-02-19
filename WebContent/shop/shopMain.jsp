<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="../js/jquery-1.11.0.min.js"></script>

<div id="cata" class="box2">
	<ul>
		<li><a href="/shoppingmall/list.do?book_kind=100">문학</a>
		<li><a href="/shoppingmall/list.do?book_kind=200">어학</a>
		<li><a href="/shoppingmall/list.do?book_kind=300">컴퓨터</a>
		<li><a href="/shoppingmall/list.do?book_kind=all">전체</a>
	</ul>
</div>

<div id="shop" class="box2">
	<c:forEach var="bookList" items="${bookLists}">
		<c:set var="bookKind" value="${bookList.get(0).getBookKind()}"/>
		<c:if test="${bookList ne null || bookList.size() > 0}">
			<c:if test="${bookKind=='100'}">
				<c:set var="bookKindName" value="문학"/>
			</c:if>
			<c:if test="${bookKind=='200'}">
				<c:set var="bookKindName" value="외국어"/>
			</c:if>
			<c:if test="${bookKind=='300'}">
				<c:set var="bookKindName" value="컴퓨터"/>
			</c:if>
			<p class="b">[${bookKindName}] 분류의 신간목록:<a href="/shoppingmall/list.do?book_kind=${bookKind}">더보기</a></p>
	    	<c:forEach var="book" items="${bookList}">
			<table class="vhcenter">
				<tr height="30"> 
					<td rowspan="4"  width="100">
						<a href="/shoppingmall/bookContent.do?book_id=${book.getBookId()}&book_kind=${book.getBookKind()}">
						<img src="/shoppingmall/bookImage/${book.getBookImage()}" class="listimage"></a>
					</td>
	        		<td width="350" class="vhcenter">
	          			<a href="/shoppingmall/bookContent.do?book_id=${book.getBookId()}&book_kind=${book.getBookKind()}" class="b">
	              		${book.getBookTitle()}</a>
	              	</td>
	        		<td rowspan="4" width="100">
						<c:if test="${book.getBookCount()==0}">
						일시품절
						</c:if>
						<c:if test="${book.getBookCount()!=0}">
						구매가능
						</c:if>
			       </td>      
				</tr>
				<tr height="30"><td width="350">출판사 :${book.getPublishingCom()}</td></tr>
	       		<tr height="30"><td width="350">저자 : ${book.getAuthor()}</td></tr>
				<tr height="30">
					<td width="350"><c:set var="price" value="${book.getBookPrice()}"/>
	          		<c:set var="rate" value="${book.getDiscountRate()}"/>
	          		정가 : <fmt:formatNumber value="${price}" type="number" pattern="#,##0"/>원<br>
	          		<strong class="bred">판매가:<fmt:formatNumber value="${price*((100.0-rate)/100)}" type="number" pattern="#,##0"/>원</strong>
	        		</td>
	        	</tr> 
			</table>
			</c:forEach>
		</c:if>
	<br>
	</c:forEach>
</div>