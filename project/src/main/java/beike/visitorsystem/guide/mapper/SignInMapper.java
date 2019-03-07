package beike.visitorsystem.guide.mapper;


import beike.visitorsystem.guide.model.SignIn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface SignInMapper {

    public boolean addSignIn(@Param("si") SignIn si);
    public List<BigInteger> getSignInStatusByUserId(BigInteger user_id);
    public List<BigInteger> getSignInByUserId(BigInteger user_id);

}
