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
import com.Autopark.service.TypesService;
import com.Autopark.service.VehicleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.Autopark.Main.initInterfaceToImplementation;

@WebServlet("/viewTypes")
public class ViewCarTypesServlet extends HttpServlet {
    @Autowired
    private DtoService vehicleTypeService;

    @Override
    public void init() throws ServletException{
        super.init();
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        ApplicationContext context = new ApplicationContext("com.Autopark", interfaceToImplementation);
        vehicleTypeService = context.getObject(DtoService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("types", vehicleTypeService.getTypes());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewTypesJSP.jsp");
        dispatcher.forward(req, res);
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
