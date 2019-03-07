package beike.visitorsystem.infrastructure.service;

import beike.visitorsystem.infrastructure.model.Infrastructure;
import beike.visitorsystem.infrastructure.model.InfrastructureType;

import java.math.BigInteger;
import java.util.List;

public interface InfrastructureService {

    boolean addInfrastructure(Infrastructure infrastructure);

    boolean updateInfrastructure(Infrastructure infrastructure);

    boolean deleteInfrastructureById(BigInteger id);

    int getSumOfInfrastructureTypes();
    //NO USE
    //List<Infrastructure> getInfrastructuresByTypeId(BigInteger typeId);

    //Infrastructure getInfrastructureDetailById(BigInteger id);


    boolean addInfrastructureType(InfrastructureType infrastructureType,List<Infrastructure> infrastructureList);

    boolean updateInfrastructureType(InfrastructureType infrastructureType);

    boolean deleteInfrastructureType(BigInteger id);

    List<InfrastructureType> getInfrastructureTypes(int page,int number);

    InfrastructureType getInfrastructureTypeDetailById(BigInteger id);
}
