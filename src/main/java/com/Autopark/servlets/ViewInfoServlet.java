package com.Autopark.servlets;

import com.Autopark.dtoFacade.DtoService;
import com.Autopark.infrastructure.configuratots.ObjectConfigurator;
import com.Autopark.infrastructure.configuratots.impl.AutowiredObjectConfigurator;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.impl.ApplicationContext;
import com.Autopark.infrastructure.orm.EntityManager;
import com.Autopark.infrastructure.orm.service.PostgreDataBaseService;
import com.Autopark.parser.ParserVehicleFromDB;
import com.Autopark.parser.ParserVehicleInterface;
import com.Autopark.repairAuto.Fixer;
import com.Autopark.repairAuto.MechanicService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/info")
public class ViewInfoServlet extends HttpServlet {

    private DtoService vehicleTypeService;

    @Override
    public void init() throws ServletException {
        super.init();
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        ApplicationContext context = new ApplicationContext("com.Autopark", interfaceToImplementation);
        vehicleTypeService = context.getObject(DtoService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id;
        String idString = request.getParameter("id");

        if (!idString.isEmpty()) {
            id = Long.parseLong(request.getParameter("id"));
        } else {
            throw new IllegalArgumentException("Parameter \"id\" is not set");
        }

        request.setAttribute("cars", vehicleTypeService
                .getVehicles()
                .stream()
                .filter(vehicleDto -> id == vehicleDto.getId())
                .collect(Collectors.toList()));
        request.setAttribute("rents", vehicleTypeService.getRents(id));
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewCarInfoJSP.jsp");
        dispatcher.forward(request, response);
    }

    private Map<Class<?>, Class<?>> initInterfaceToImplementation() {
        Map<Class<?>, Class<?>> map = new HashMap<>();
        map.put(ObjectConfigurator.class, AutowiredObjectConfigurator.class);
        map.put(EntityManager.class, PostgreDataBaseService.class);
        map.put(Fixer.class, MechanicService.class);
        map.put(ParserVehicleInterface.class, ParserVehicleFromDB.class);
        return map;
    }
}