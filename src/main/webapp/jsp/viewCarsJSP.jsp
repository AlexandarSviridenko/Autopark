<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 10.10.2022
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.Autopark.dto.VehicleDto" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Просмотр машин</title>
    <link href="../resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <%
            List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");
            Set<String> uniqTypes =
                    dtoList.stream().map(VehicleDto::getTypeName).collect(Collectors.toSet());
            Set<String> uniqModels =
                    dtoList.stream().map(VehicleDto::getModelName).collect(Collectors.toSet());
            Set<String> uniqColors =
                    dtoList.stream().map(VehicleDto::getColor).collect(Collectors.toSet());
            Set<String> uniqEngineNames =
                    dtoList.stream().map(VehicleDto::getEngineName).collect(Collectors.toSet());
            Set<Integer> uniqWeight =
                    dtoList.stream().map(VehicleDto::getWeight).collect(Collectors.toSet());
            Set<Integer> uniqManufactureYears =
                    dtoList.stream().map(VehicleDto::getManufactureYear).collect(Collectors.toSet());
            AtomicReference<Predicate<VehicleDto>> filter = new AtomicReference<>(vehicleDto -> true);
            Optional.ofNullable(request.getParameter("type")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> vehicleDto.getTypeName().equals(s))));
            Optional.ofNullable(request.getParameter("model")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> vehicleDto.getModelName().equals(s))));
            Optional.ofNullable(request.getParameter("color")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> vehicleDto.getColor().equals(s))));
            Optional.ofNullable(request.getParameter("engine")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> vehicleDto.getEngineName().equals(s))));
            Optional.ofNullable(request.getParameter("weight")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> Integer.valueOf(vehicleDto.getWeight()).equals(Integer.parseInt(s)))));
            Optional.ofNullable(request.getParameter("manufactureYear")).filter(s ->
                    !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> Integer.valueOf(vehicleDto.getManufactureYear()).equals(Integer.parseInt(s)))));
            dtoList = dtoList.stream().filter(filter.get()).collect(Collectors.toList());
        %>
        <a class="ml-20" href="${pageContext.request.contextPath}/">На главную</a>
        <a class="ml-20" href="${pageContext.request.contextPath}/viewCars">Очистить фильтры</a>
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
                <th>&#x1F4CB;</th>
            </tr>
            <% if (dtoList.size() == 0) {%>
            <tr><td colspan="10">
                Нет машин соответствующих параметрам</td> </tr>
            <%}%>
            <%
                for(VehicleDto dto : dtoList) {
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
                <td><a href="<%="/info?id=" + dto.getId()%>">&#x1F4C3;</a></td>
            </tr>
            <%}%>
        </table>
        <br />
        <div>
            <hr />
            <br />
            <form method="get" action="${pageContext.request.contextPath}/viewCars" class="flex">
                <div class="ml-20">
                    <p>Тип</p>
                    <label>
                        <select name="type" >
                            <option value=""
                                    <%=request.getParameter("type") == null ? "selected" : ""%>>Не выбрано</option>
                            <%for (String s : uniqTypes) {%>
                            <option value="<%=s%>"
                                    <%=(request.getParameter("type") != null &&
                                            s.equals(request.getParameter("type")) ? "selected" : "")%>><%=s%></option>
                            <%}%>
                        </select>
                    </label>
                </div>
                <div class="ml-20">
                    <p>Модель</p>
                    <label>
                        <select name="model" >
                            <option value=""
                                    <%=request.getParameter("model") == null ? "selected" : ""%>>Не выбрано</option>
                            <%for (String s : uniqModels) {%>
                            <option value="<%=s%>"
                                    <%=(request.getParameter("model") != null &&
                                            s.equals(request.getParameter("model")) ? "selected" : "")%>><%=s%></option>
                            <%}%>
                        </select>
                    </label>
                </div>
                <div class="ml-20">
                    <p>Двигатель</p>
                    <label>
                        <select name="engine" >
                            <option value=""
                                    <%=request.getParameter("engine") == null ? "selected" : ""%>>Не выбрано</option>
                            <%for (String s : uniqEngineNames) {%>
                            <option value="<%=s%>"
                                    <%=(request.getParameter("engine") != null &&
                                            s.equals(request.getParameter("engine")) ? "selected" : "")%>><%=s%></option>
                            <%}%>
                        </select>
                    </label>
                </div>
                <div class="ml-20">
                    <p>Цвет</p>
                    <label>
                        <select name="color" >
                            <option value=""
                                    <%=request.getParameter("color") == null ? "selected" : ""%>>Не выбрано</option>
                            <%for (String s : uniqColors) {%>
                            <option value="<%=s%>"
                                    <%=(request.getParameter("color") != null &&
                                            s.equals(request.getParameter("color")) ? "selected" : "")%>><%=s%></option>
                            <%}%>
                        </select>
                    </label>
                </div>
                <div class="ml-20">
                    <p>Масса</p>
                    <select name="weight" >
                        <option value=""
                                <%=request.getParameter("weight") == null ? "selected" : ""%>>Не выбрано</option>
                        <%for (Integer i : uniqWeight) {%>
                        <option value="<%=i%>"
                                <%=(request.getParameter("weight") != null && !request.getParameter("weight").isEmpty() &&
                                        i.equals(Integer.parseInt(request.getParameter("weight"))) ? "selected" : "")%>><%=i%></option>
                        <%}%>
                    </select>
                </div>
                <div class="ml-20">
                    <p>Год выпуска</p>
                    <label>
                        <select name="manufactureYear" >
                            <option value=""
                                    <%=request.getParameter("manufactureYear") == null ? "selected" : ""%>>Не выбрано</option>
                            <%for (Integer i : uniqManufactureYears) {%>
                            <option value="<%=i%>"
                                    <%=(request.getParameter("manufactureYear") != null && !request.getParameter("manufactureYear").isEmpty() &&
                                            i.equals(Integer.parseInt(request.getParameter("manufactureYear"))) ? "selected" : "")%>><%=i%></option>
                            <%}%>
                        </select>
                    </label>
                </div>
                <button class="ml-20" type="submit">Выбрать</button>
            </form>
            <br />
            <hr />
        </div>
    </div>
</div>
</body>
</html>