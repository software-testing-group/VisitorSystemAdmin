package beike.visitorsystem.infrastructure.mapper;

import beike.visitorsystem.infrastructure.model.InfrastructureType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface InfrastructureTypeMapper {

    boolean addInfrastructureType(@Param("it")InfrastructureType infrastructureType);

    boolean updateInfrastructureType(@Param("it")InfrastructureType infrastructureType);

    boolean deleteInfrastructureType(@Param("id")BigInteger id);

    List<InfrastructureType> getInfrastructureTypes(@Param("page") int page, @Param("number") int number);

    InfrastructureType getInfrastructureTypeDetailById(@Param("id")BigInteger id);

    public List<InfrastructureType> getInfrastructureTypeList();

    int getSumOfInfrastructureTypes();
}
