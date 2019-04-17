package beike.visitorsystem.visitor.mapper;

import beike.visitorsystem.visitor.model.VisitorUser;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
@Repository
public interface VisitorUserMapper {
    public List<VisitorUser> getAllVisitorUser(@Param("count")int count,@Param("number")int number);
    public List<VisitorUser> getAllBannedVisitorUser(@Param("count")int count,@Param("number")int number);
    public List<VisitorUser> getAllNoBannedVisitorUser(@Param("count")int count,@Param("number")int number);
    public VisitorUser getVisitorUserById(BigInteger VisitorUserId);
    public boolean defriendVisitorUser(BigInteger VisitorUserId);
    public boolean refriendVisitorUser(BigInteger VisitorUserId);
    public VisitorUser getVisitorDetailsById(BigInteger VisitorUserId);
    public VisitorUser getVisitorReservationById(BigInteger VisitorUserId);
    public List<VisitorUser> getVisitorUserByIdentity(@Param("identityCard")String identityCard,@Param("count")int count,@Param("number")int number);
    public List<VisitorUser> getAllVisitorUserAndIdentity(@Param("count")int count,@Param("number")int number);
    public int getSumVisitorUser();
    public int getSumBannedVisitorUser();
    public int getSumNoBannedVisitorUser();
    public VisitorUser getVisitorUserByOpenId(String open_id);
    public Boolean addVisitorUser(@Param("vu")VisitorUser vu);
}

