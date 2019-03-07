package beike.visitorsystem.infrastructure.controller;


import beike.visitorsystem.infrastructure.model.Infrastructure;
import beike.visitorsystem.infrastructure.model.InfrastructureType;
import beike.visitorsystem.infrastructure.service.InfrastructureService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/infrastructure")
public class InfrastructureController {

    @Autowired
    InfrastructureService infrastructureService;

    @RequestMapping(value = "/addInfrastructureType",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean addInfrastructureType(String infrastructureTypeName,String infrastructures) {

        InfrastructureType infrastructureType = new InfrastructureType();
        infrastructureType.setName(infrastructureTypeName);

        JSONObject json = new JSONObject();
        List<Infrastructure> infrastructureList = json.parseArray(infrastructures,Infrastructure.class);

        return infrastructureService.addInfrastructureType(infrastructureType,infrastructureList);
    }

    @RequestMapping(value = "/deleteInfrastructureType",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean deleteInfrastructureType(BigInteger id)
    {
        return infrastructureService.deleteInfrastructureType(id);
    }

    @RequestMapping(value = "/editInfrastructureType",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean editInfrastructureType(InfrastructureType infrastructureType)
    {
        return infrastructureService.updateInfrastructureType(infrastructureType);
    }

    @RequestMapping(value = "/getInfrastructureTypes",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<InfrastructureType> getInfrastructureTypes(int page)
    {
        return infrastructureService.getInfrastructureTypes(page,10);
    }

    @RequestMapping(value = "/getInfrastructureTypeDetailById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public InfrastructureType getInfrastructureTypeDetailById(BigInteger id)
    {
        return infrastructureService.getInfrastructureTypeDetailById(id);
    }

    @RequestMapping(value = "/getSumOfInfrastructureTypes",produces = "application/json;charset=utf-8")
    @ResponseBody
    public int getSumOfInfrastructureTypes()
    {
        return infrastructureService.getSumOfInfrastructureTypes();
    }


     //需要在前端把设施类id包装到infrastructure对象里
     @RequestMapping(value = "/addInfrastructure",produces = "application/json;charset=utf-8")
     @ResponseBody
      public boolean addInfrastructure(Infrastructure infrastructure)
     {
         return infrastructureService.addInfrastructure(infrastructure);
     }

    @RequestMapping(value = "/updateInfrastructure",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean updateInfrastructure(Infrastructure infrastructure)
    {
        return infrastructureService.updateInfrastructure(infrastructure);
    }

    @RequestMapping(value = "/deleteInfrastructure",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean deleteInfrastructure(BigInteger id)
    {
        return infrastructureService.deleteInfrastructureById(id);
    }

}
