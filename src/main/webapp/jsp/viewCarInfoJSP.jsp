<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 10.10.2022
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.Autopark.dto.VehicleDto" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page import="com.Autopark.dto.RentDto" %>
<%@ page import="com.Autopark.Auto.Rent" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Информация о машине</title>
    <link href="../resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <%
            List<VehicleDto> dtoVehicles = (List<VehicleDto>) request.getAttribute("cars");
            List<RentDto> dtoRents = (List<RentDto>) request.getAttribute("rents");
            AtomicReference<Predicate<VehicleDto>> filterVehicles = new AtomicReference<>(vehicleDto -> true);
            AtomicReference<Predicate<RentDto>> filterRents = new AtomicReference<>(rentDto -> true);
            dtoVehicles = dtoVehicles.stream().filter(filterVehicles.get()).collect(Collectors.toList());
            dtoRents = dtoRents.stream().filter(filterRents.get()).collect(Collectors.toList());
        %>
        <a class="ml-20" href="${pageContext.request.contextPath}/viewCars">Назад</a>
        <a class="ml-20" href="${pageContext.request.contextPath}/">На главную</a>
        <br />
        <br />
        <hr />
        <br />
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
                <th>Бак</th>
                <th>Расход</th>
                <th>Коэффициент налога</th>
                <th>Км на полный бак</th>
            </tr>
            <% if (dtoVehicles.size() != 1) {
                throw new IllegalArgumentException("There is " + dtoVehicles.size() + " cars with id=" + request.getParameter("id") + ", but must be 1");
            }%>
            <%
                VehicleDto dto = dtoVehicles.get(0);
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
                <td><%=dto.getTankVolume()%></td>
                <td><%=dto.getPer100km()%></td>
                <td><%=dto.getTaxCoefficient()%></td>
                <td><%=dto.getMaxKm()%></td>
            </tr>
        </table>
        <p>Налог за месяц <%=dto.getTax()%></p>

        <table>
            <caption>Аренды</caption>
            <tr>
                <th>Дата</th>
                <th>Стоимость</th>
            </tr>
            <% if (dtoRents.size() == 0) {%>
            <tr><td colspan="10">
                Данную машину еще не арендовывали</td> </tr>
            <%}%>
            <% for (RentDto rent : dtoRents) {%>
            <tr>
                <td><%=rent.getDate()%></td>
                <td><%=rent.getCost()%></td>
            </tr>
            <%}%>
        </table>
        <p>Сумма <%=dto.getIncome()%></p>
        <p>Доход <%=dto.getIncome() - dto.getTax()%></p>
    </div>
</div>
</body>
</html>
