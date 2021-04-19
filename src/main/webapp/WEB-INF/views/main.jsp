<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>LB Currency</title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/style.css">

    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/css/select2.min.css" rel="stylesheet" />

    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/js/script.js"></script>

    <script> getHolidays() </script>

</head>
<body>
<div class="selection_submition_block">
    <form:form method="post"  action="${contextPath}/currency_list" modelAttribute="currencyRateModel" >
            <div class="currency_code_select_block" >
                <spring:bind path="currencyCode">
                    <h6 class="label_selection" >Valiuta</h6>
                    <div class="form-group">
                        <form:select path="currencyCode"  id="currencyCode" type="text" class="form-control" >
                            <form:option value="" label="--Visos--"></form:option>
                            <form:options items="${currencyList}" itemValue="currencyCode" itemLabel="ltName"/>

                        </form:select>
                    </div>
                </spring:bind>
            </div>

        <div class="input_date_block">
            <h6 class="label_selection" >Periodas Nuo</h6>
            <div class="form-group">
            <spring:bind path="dateFrom">
                <form:input path="dateFrom" cssClass="date_input" type="text" required="required" placeholder="Date From" id="datepicker1" autocomplete="off"/>
            </spring:bind>
            </div>
        </div>

        <div class="input_date_block">
            <h6 class="label_selection" >Periodas Iki</h6>
            <div class="form-group">
            <spring:bind path="dateTo">
                <form:input path="dateTo" cssClass="date_input" type="text" required="required" placeholder="Date To" id="datepicker2" autocomplete="off"/>
            </spring:bind>
            </div>
        </div>

    <button class="button_submit" type="submit" id="submit"> Submit</button>

        <c:if test="${not empty currencyRateList}">
        <div class="download_btn_block">
            <button class="button_submit" onclick="location.href='/?download=download' ">Atsisiųsti</button>
        </div>
        </c:if>
    </form:form>



</div>
<div class="result_block">
<c:if test="${not empty currencyRateList}">


    <table class="table-sm" style=" width:600px; margin-left:auto; margin-right:auto">
        <thead class="thead-dark">
            <tr class="tr_header">
                <th scope="col">Currency Code</th>
                <th scope="col">Date</th>
                <th scope="col">Amount</th>
            </tr>
        </thead>
        <c:forEach items="${currencyRateList}" var="item">
            <tr class="tr_currency">
                <td class="collum">${item.currency.currencyCode}</td>
                <td class="collum" colspan="2">${item.currency.ltName}</td>
            </tr>
            <c:forEach items="${item.currencyRateList}" var="item2">
                <tr class="tr_rate">
                    <td></td>
                    <td class="collum">${item2.date}</td>
                    <td class="collum">${item2.amount}</td>
                </tr>
            </c:forEach>
            <tr class="tr_change">
                <td>Change(Pokytis)</td>
                <td></td>
                <td class="collum">${item.change}</td>
            </tr>

        </c:forEach>
        <tbody>

        </tbody>
    </table>
</c:if>
</div>


<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/js/select2.min.js"></script>

<script>

    $(document).ready(function(){

        $("#currencyCode").select2();

    });
</script>

</body>
</html>
