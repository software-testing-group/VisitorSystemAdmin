package beike.visitorsystem.infrastructure.service;

import beike.visitorsystem.infrastructure.mapper.InfrastructureMapper;
import beike.visitorsystem.infrastructure.mapper.InfrastructureTypeMapper;
import beike.visitorsystem.infrastructure.model.Infrastructure;
import beike.visitorsystem.infrastructure.model.InfrastructureType;
import beike.visitorsystem.utils.GenerateID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        BigInteger infrastructureId = generateID.getID(); //为具体设施设置id
        infrastructure.setId(infrastructureId);
        infrastructure.setCreateTime(df.format(new Date()).toString());
        infrastructure.setUpdateTime(df.format(new Date()).toString());
        return infrastructureMapper.addInfrastructure(infrastructure);
    }
    //OK更新某个具体设施的信息
    public boolean updateInfrastructure(Infrastructure infrastructure)
    {
        infrastructure.setUpdateTime(df.format(new Date()).toString());
        return infrastructureMapper.updateInfrastructure(infrastructure);
    }
    //OK在一个设施类中删除某一具体设施
    public boolean deleteInfrastructureById(BigInteger id)
    {
        return infrastructureMapper.deleteInfrastructureById(id);
    }

    /*  根据设施类ID获取具体设施的列表(没啥用)
    public List<Infrastructure> getInfrastructuresByTypeId(BigInteger typeId)
    {
        List<Infrastructure> infrastructureList = infrastructureMapper.getInfrastructuresByTypeId(typeId);
        return infrastructureList;
    }
    */

    /*  查看某一具体设施的详细信息
    public Infrastructure getInfrastructureDetailById(BigInteger id)
    {
        Infrastructure infrastructure = infrastructureMapper.getInfrastructureDetailById(id);
        return  infrastructure;
    }
    */

    //OK添加设施类(同时要添加具体设施项)
    public boolean addInfrastructureType(InfrastructureType infrastructureType,List<Infrastructure> infrastructureList)
    {
        BigInteger infrastructureTypeId = generateID.getID();
        infrastructureType.setId(infrastructureTypeId);
        infrastructureType.setCreateTime(df.format(new Date()).toString());
        infrastructureType.setUpdateTime(df.format(new Date()).toString());

        if(infrastructureTypeMapper.addInfrastructureType(infrastructureType))
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
