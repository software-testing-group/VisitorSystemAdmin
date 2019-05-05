package beike.visitorsystem.infrastructure.service;

import beike.visitorsystem.infrastructure.mapper.InfrastructureMapper;
import beike.visitorsystem.infrastructure.mapper.InfrastructureTypeMapper;
import beike.visitorsystem.infrastructure.model.Infrastructure;
import beike.visitorsystem.infrastructure.model.InfrastructureType;
import beike.visitorsystem.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class InfrastructureServiceImpl implements InfrastructureService {

    @Autowired
    InfrastructureMapper infrastructureMapper;
    @Autowired
    InfrastructureTypeMapper infrastructureTypeMapper;
    @Autowired
    GenerateID generateID;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //OK往一个设施类中添加具体设施(需要设施类id: 可以在controller中把设施类id包装到infrastructure对象里)
    public boolean addInfrastructure(Infrastructure infrastructure)
    {
        if(infrastructure.getTypeId()==null||infrastructure.getName()==null||infrastructure.getName().equals("")){
            return false;
        }
        BigDecimal longitudeTop = new BigDecimal(180);
        BigDecimal latitudeTop = new BigDecimal(90);
        if(infrastructure.getLongitude()!=null){  // 若经度不为空，检验经度
            if(infrastructure.getLongitude().abs().compareTo(longitudeTop)==1){  // 如果经度超限
                return false;
            }
        }
        if(infrastructure.getLatitude()!=null){  // 若纬度不为空，检验纬度
            if(infrastructure.getLatitude().abs().compareTo(latitudeTop)==1){ // 如果纬度超限
                return false;
            }
        }
        BigInteger infrastructureId = generateID.getID(); //为具体设施设置id
        infrastructure.setId(infrastructureId);
        infrastructure.setCreateTime(df.format(new Date()).toString());
        infrastructure.setUpdateTime(df.format(new Date()).toString());
        return infrastructureMapper.addInfrastructure(infrastructure);
    }

    //OK更新某个具体设施的信息
    public boolean updateInfrastructure(Infrastructure infrastructure)
    {
        if(infrastructure.getName()==null||infrastructure.getName().equals("")){
            return false;
        }
        BigDecimal longitudeTop = new BigDecimal(180);
        BigDecimal latitudeTop = new BigDecimal(90);
        if(infrastructure.getLongitude()!=null){  // 若经度不为空，检验经度
            if(infrastructure.getLongitude().abs().compareTo(longitudeTop)==1){  // 如果经度超限
                return false;
            }
        }
        if(infrastructure.getLatitude()!=null){  // 若纬度不为空，检验纬度
            if(infrastructure.getLatitude().abs().compareTo(latitudeTop)==1){ // 如果纬度超限
                return false;
            }
        }
        infrastructure.setUpdateTime(df.format(new Date()).toString());
        return infrastructureMapper.updateInfrastructure(infrastructure);
    }
    //OK在一个设施类中删除某一具体设施
    public boolean deleteInfrastructureById(BigInteger id)
    {
        return infrastructureMapper.deleteInfrastructureById(id);
    }


    //OK添加设施类(同时要添加具体设施项)
    public boolean addInfrastructureType(InfrastructureType infrastructureType,List<Infrastructure> infrastructureList)
    {
        BigInteger infrastructureTypeId = generateID.getID();
        infrastructureType.setId(infrastructureTypeId);
        infrastructureType.setCreateTime(df.format(new Date()).toString());
        infrastructureType.setUpdateTime(df.format(new Date()).toString());

        if(infrastructureType.getName()!=null&&infrastructureTypeMapper.addInfrastructureType(infrastructureType))
        {
            for(Infrastructure temp :infrastructureList)
            {
                BigInteger infrastructureId = generateID.getID(); //为具体设施设置id
                temp.setId(infrastructureId);
                temp.setTypeId(infrastructureTypeId);
                temp.setCreateTime(df.format(new Date()).toString());
                temp.setUpdateTime(df.format(new Date()).toString());
                infrastructureMapper.addInfrastructure(temp);
            }
        }
        else
        {
            return false;
        }
        return true;
    }
    //OK更新设施类的信息
    public boolean updateInfrastructureType(InfrastructureType infrastructureType)
    {
        infrastructureType.setUpdateTime(df.format(new Date()).toString());
        if(infrastructureTypeMapper.updateInfrastructureType(infrastructureType))
        { }
        else
        {
            return false;
        }
        return true;
    }
    //OK删除某个设施类(同时要删除其中所有的设施项)
    public boolean deleteInfrastructureType(BigInteger id)
    {
       if(infrastructureTypeMapper.deleteInfrastructureType(id))
       {
           infrastructureMapper.deleteInfrastructuresByInfrastructureTypeId(id);
       }
       else
       {
           return false;
       }
        return true;
    }
    //OK获得设施类的列表
    public List<InfrastructureType> getInfrastructureTypes(int page,int number)
    {
        List<InfrastructureType> infrastructureTypeList = infrastructureTypeMapper.getInfrastructureTypes((page - 1)*number, number);
        return  infrastructureTypeList;
    }
    //OK获得某一设施类的详细信息
    public InfrastructureType getInfrastructureTypeDetailById(BigInteger id)
    {
        InfrastructureType infrastructureType = infrastructureTypeMapper.getInfrastructureTypeDetailById(id);
        return infrastructureType;
    }

    public int getSumOfInfrastructureTypes()
    {
        return infrastructureTypeMapper.getSumOfInfrastructureTypes();
    }
}
