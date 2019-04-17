package beike.visitorsystem.visitor.mapper;

import beike.visitorsystem.visitor.model.VisitorIdentity;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
@Repository
public interface VisitorIdentityMapper {
    public List<VisitorIdentity> getAllVisitorIdentity(@Param("count")int count,@Param("number")int number);
    public List<VisitorIdentity> getAllBannedVisitorIdentity(@Param("count")int count,@Param("number")int number);
    public List<VisitorIdentity> getAllNoBannedVisitorIdentity(@Param("count")int count,@Param("number")int number);
    public VisitorIdentity getVisitorIdentityById(BigInteger VisitorIdentityId);
    public boolean defriendVisitorIdentity(BigInteger VisitorIdentityId);
    public boolean refriendVisitorIdentity(BigInteger VisitorIdentityId);
    public List<VisitorIdentity> getIdentityByCard(@Param("identityCard")String identityCard,@Param("isBanned")int isBanned,@Param("count")int count,@Param("number")int number);
    public List<VisitorIdentity> getIdentityByName(@Param("name")String name,@Param("isBanned")int isBanned,@Param("count")int count,@Param("number")int number);
    public int getSumIdentity();
    public int getSumBannedIdentity();
    public int getSumNoBannedIdentity();

    public int getSumByIdentity(@Param("identity")String identity,@Param("isBanned")int isBanned);
    public int getSumByName(@Param("name")String name,@Param("isBanned")int isBanned);
}
