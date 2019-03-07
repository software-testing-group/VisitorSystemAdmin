package beike.visitorsystem.helpAndFeedback.mapper;

import beike.visitorsystem.helpAndFeedback.model.Help;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface HelpMapper {

    boolean addHelp(@Param("help") Help help);
    boolean updateHelp(@Param("help") Help help);
    boolean deleteHelpById(@Param("id")BigInteger id);
    List<Help> getHelps(@Param("page") int page, @Param("number") int number);
    Help getHelpDetailById(@Param("id")BigInteger id);
    public List<Help> getHelpList();

    int getSumOfHelps();
}
