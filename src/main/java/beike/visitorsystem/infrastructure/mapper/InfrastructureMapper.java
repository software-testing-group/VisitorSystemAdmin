package beike.visitorsystem.infrastructure.mapper;

import beike.visitorsystem.infrastructure.model.Infrastructure;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface InfrastructureMapper {

    boolean addInfrastructure(@Param("i")Infrastructure infrastructure);

    boolean updateInfrastructure(@Param("i")Infrastructure infrastructure);

    boolean deleteInfrastructureById(@Param("id")BigInteger id);

    boolean deleteInfrastructuresByInfrastructureTypeId(@Param("typeId")BigInteger typeId);

    List<Infrastructure> getInfrastructuresByTypeId(@Param("typeId")BigInteger typeId);

    //Infrastructure getInfrastructureDetailById(@Param("id")BigInteger id);
}
