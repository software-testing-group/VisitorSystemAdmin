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

    //OK��һ����ʩ������Ӿ�����ʩ(��Ҫ��ʩ��id: ������controller�а���ʩ��id��װ��infrastructure������)
    public boolean addInfrastructure(Infrastructure infrastructure)
    {
        if(infrastructure.getTypeId()==null||infrastructure.getName()==null||infrastructure.getName().equals("")){
            return false;
        }
        BigDecimal longitudeTop = new BigDecimal(180);
        BigDecimal latitudeTop = new BigDecimal(90);
        if(infrastructure.getLongitude()!=null){  // �����Ȳ�Ϊ�գ����龭��
            if(infrastructure.getLongitude().abs().compareTo(longitudeTop)==1){  // ������ȳ���
                return false;
            }
        }
        if(infrastructure.getLatitude()!=null){  // ��γ�Ȳ�Ϊ�գ�����γ��
            if(infrastructure.getLatitude().abs().compareTo(latitudeTop)==1){ // ���γ�ȳ���
                return false;
            }
        }
        BigInteger infrastructureId = generateID.getID(); //Ϊ������ʩ����id
        infrastructure.setId(infrastructureId);
        infrastructure.setCreateTime(df.format(new Date()).toString());
        infrastructure.setUpdateTime(df.format(new Date()).toString());
        return infrastructureMapper.addInfrastructure(infrastructure);
    }

    //OK����ĳ��������ʩ����Ϣ
    public boolean updateInfrastructure(Infrastructure infrastructure)
    {
        if(infrastructure.getName()==null||infrastructure.getName().equals("")){
            return false;
        }
        BigDecimal longitudeTop = new BigDecimal(180);
        BigDecimal latitudeTop = new BigDecimal(90);
        if(infrastructure.getLongitude()!=null){  // �����Ȳ�Ϊ�գ����龭��
            if(infrastructure.getLongitude().abs().compareTo(longitudeTop)==1){  // ������ȳ���
                return false;
            }
        }
        if(infrastructure.getLatitude()!=null){  // ��γ�Ȳ�Ϊ�գ�����γ��
            if(infrastructure.getLatitude().abs().compareTo(latitudeTop)==1){ // ���γ�ȳ���
                return false;
            }
        }
        infrastructure.setUpdateTime(df.format(new Date()).toString());
        return infrastructureMapper.updateInfrastructure(infrastructure);
    }
    //OK��һ����ʩ����ɾ��ĳһ������ʩ
    public boolean deleteInfrastructureById(BigInteger id)
    {
        return infrastructureMapper.deleteInfrastructureById(id);
    }


    //OK�����ʩ��(ͬʱҪ��Ӿ�����ʩ��)
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
                BigInteger infrastructureId = generateID.getID(); //Ϊ������ʩ����id
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
    //OK������ʩ�����Ϣ
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
    //OKɾ��ĳ����ʩ��(ͬʱҪɾ���������е���ʩ��)
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
    //OK�����ʩ����б�
    public List<InfrastructureType> getInfrastructureTypes(int page,int number)
    {
        List<InfrastructureType> infrastructureTypeList = infrastructureTypeMapper.getInfrastructureTypes((page - 1)*number, number);
        return  infrastructureTypeList;
    }
    //OK���ĳһ��ʩ�����ϸ��Ϣ
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
