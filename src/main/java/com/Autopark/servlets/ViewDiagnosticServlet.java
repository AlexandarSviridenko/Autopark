package com.Autopark.servlets;

import com.Autopark.Auto.AutoCheck;
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

@WebServlet("/viewDiagnostic")
public class ViewDiagnosticServlet extends HttpServlet {

    private DtoService vehicleTypeService;

    private AutoCheck autoCheck;

    private ApplicationContext context;

    @Override
    public void init() throws ServletException{
        super.init();
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        context = new ApplicationContext("com.Autopark", interfaceToImplementation);
        vehicleTypeService = context.getObject(DtoService.class);
        autoCheck = context.getObject(AutoCheck.class);
        autoCheck.vehiclesFromDBToWorkroom(context);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cars", vehicleTypeService.getVehicles());
        request.setAttribute("ordersBeforeRepairing", vehicleTypeService.getOrders());

        autoCheck.repairVehiclesInWorkroom(context);

        request.setAttribute("ordersAfterRepairing", vehicleTypeService.getOrders());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewDiagnosticJSP.jsp");
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