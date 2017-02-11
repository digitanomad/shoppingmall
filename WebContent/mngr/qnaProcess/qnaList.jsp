<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/mngr/qnaProcess/qnaList.js"></script>

<c:if test="${empty sessionScope.id}">
  <meta http-equiv="Refresh" content="0;url=/shoppingmall/mg/managerMain.do" >
</c:if>

<div id="qnaHeader">
  <button id="bookMain">관리자 메인으로</button>
</div>

<c:if test="${count ==0 }">
  <p>등록된 QnA가 없습니다.
</c:if>

<c:if test="${count > 0}">
<div id="qnaList">
	<c:forEach var="qna" items="${qnaLists}">
		<ul>
			<c:if test="${qna.getQora() == 1}">
			<li><p>[${qna.getBookTitle()}] 상품에 대한 QnA</p>
				<p>{${qna.getQnaWriter()}<small class="date">(${qna.getRegDate()})</small></p>
				<p>${qna.getQnaContent()}</p>
			</c:if>
			<c:if test="${qna.getReply()==0}">
        		<p><button id="reply" name="${qna.getQnaId()}" onclick="reply(this)">답변하기</button></p>
      		</c:if>
      		<c:if test="${qna.getQora()==2}">
        		<li class="re">
            	<p>${qna.getQna_content()}</p>
            	<p><c:if test="${qna.getQnaWriter()=='manager'}">관리자</c:if>
               	<small class="date">(${qna.getRegDate()})</small></p>
            	<p><button id="editReply" name="${qna.getQnaId()}" onclick="edit(this)">수정</button></p>
      		</c:if>
      	</ul>
	</c:forEach>
</div>
</c:if>