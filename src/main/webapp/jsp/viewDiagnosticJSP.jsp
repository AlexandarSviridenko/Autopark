<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 10.10.2022
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.Autopark.dto.VehicleDto" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page import="com.Autopark.dto.RentDto" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="com.Autopark.dto.OrderDto" %>
<%@ page import="com.Autopark.entity.Orders" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Машины после диагностики</title>
    <link href="../resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <%
            List<VehicleDto> dtoVehicles = (List<VehicleDto>) request.getAttribute("cars");
            List<OrderDto> dtoOrdersBeforeRepairing = (List< OrderDto>) request.getAttribute("ordersBeforeRepairing");
            List<OrderDto> dtoOrdersAfterRepairing = (List< OrderDto>) request.getAttribute("ordersAfterRepairing");
            AtomicReference<Predicate<VehicleDto>> filterVehicles = new AtomicReference<>(vehicleDto -> true);
            AtomicReference<Predicate<OrderDto>> filterOrdersBeforeRepairing = new AtomicReference<>(orderDto -> true);
            AtomicReference<Predicate<OrderDto>> filterOrdersAfterRepairing = new AtomicReference<>(orderDto -> true);
            dtoVehicles = dtoVehicles.stream().filter(filterVehicles.get()).collect(Collectors.toList());
            dtoOrdersBeforeRepairing = dtoOrdersBeforeRepairing.stream().filter(filterOrdersBeforeRepairing.get()).collect(Collectors.toList());
            dtoOrdersAfterRepairing = dtoOrdersAfterRepairing.stream().filter(filterOrdersAfterRepairing.get()).collect(Collectors.toList());
        %>
        <a class="ml-20" href="${pageContext.request.contextPath}/">На главную</a>
        <br />
        <br />
        <hr />
        <br />
        <table>
            <caption>Машины после диагностики</caption>
            <tr>
                <th>Тип</th>
                <th>Модель</th>
                <th>Номер</th>
                <th>Масса</th>
                <th>Дата выпуска</th>
                <th>Цвет</th>
                <th>Модель двигателя</th>
                <th>Пробег</th>
                <th>Бак</th>
                <th>Была исправна</th>
                <th>Починена</th>
            </tr>
            <% if (dtoVehicles.size() == 0) {%>
            <tr><td colspan="10">
                Нет машин в автопарке</td> </tr>
            <%}%>
            <% for (VehicleDto dto : dtoVehicles) { %>
            <tr>
                <td><%=dto.getTypeName()%></td>
                <td><%=dto.getModelName()%></td>
                <td><%=dto.getRegistrationNumber()%></td>
                <td><%=dto.getWeight()%></td>
                <td><%=dto.getManufactureYear()%></td>
                <td><%=dto.getColor()%></td>
                <td><%=dto.getEngineName()%></td>
                <td><%=dto.getMileage()%></td>
                <td><%=dto.getTankVolume()%></td>
                <td><%=dtoOrdersBeforeRepairing.stream().anyMatch(order -> order.getVehicleId() == dto.getId()) ? "нет" : "да"%></td>
                <td><%=dtoOrdersAfterRepairing.stream().anyMatch(order -> order.getVehicleId() == dto.getId()) ? "нет" : "да"%></td>
            </tr>
            <%}%>
        </table>
    </div>
</div>
</body>
</html>