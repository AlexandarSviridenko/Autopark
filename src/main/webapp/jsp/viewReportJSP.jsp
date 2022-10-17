<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 10.10.2022
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.Autopark.dto.VehicleDto" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page import="com.Autopark.dto.RentDto" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отчёт</title>
    <link rel="stylesheet" href="../resources/css/style.css">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <%
            double averageTax = 0.0d;
            double averageIncome = 0.0d;
            double total = 0.0d;
            List<VehicleDto> dtoVehicles = (List<VehicleDto>) request.getAttribute("cars");
            AtomicReference<Predicate<VehicleDto>> filterVehicles = new AtomicReference<>(vehicleDto -> true);
            dtoVehicles = dtoVehicles.stream().filter(filterVehicles.get()).collect(Collectors.toList());
        %>
        <a class="ml-20" href="${pageContext.request.contextPath}/">На главную</a>
        <table>
            <caption>Машины</caption>
            <tr>
                <th>Тип</th>
                <th>Модель</th>
                <th>Номер</th>
                <th>Масса</th>
                <th>Дата выпуска</th>
                <th>Цвет</th>
                <th>Модель двигателя</th>
                <th>Пробег</th>
                <th>Доход с аренд</th>
                <th>Налог</th>
                <th>Итог</th>
            </tr>
            <% if (dtoVehicles.size() == 0) {%>
            <tr><td colspan="10">
                Нет машин в автопарке</td> </tr>
            <%}%>
            <% for (VehicleDto dto : dtoVehicles) {
                averageIncome = dto.getIncome() / dtoVehicles.size();
                averageTax = dto.getTax() / dtoVehicles.size();
            %>
            <tr>
                <td><%=dto.getTypeName()%></td>
                <td><%=dto.getModelName()%></td>
                <td><%=dto.getRegistrationNumber()%></td>
                <td><%=dto.getWeight()%></td>
                <td><%=dto.getManufactureYear()%></td>
                <td><%=dto.getColor()%></td>
                <td><%=dto.getEngineName()%></td>
                <td><%=dto.getMileage()%></td>
                <td><%=dto.getIncome()%></td>
                <td><%=dto.getTax()%></td>
                <td><%=(double) Math.round ((dto.getIncome() - dto.getTax()) * 100 / 100 )%></td>
            </tr>
            <%}%>
        </table>
        <p>Средний налог за месяц <%=(double) Math.round (averageTax * 100) / 100%></p>
        <p>Средний доход за месяц <%=(double) Math.round (averageIncome * 100) / 100%></p>
        <p>Доход автопарка <%=(double) Math.round ((averageIncome - averageTax) * dtoVehicles.size() * 100) / 100%></p>
    </div>
</div>
</body>
</html>