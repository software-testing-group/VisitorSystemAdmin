package beike.visitorsystem.visitor.service;

import beike.visitorsystem.visitor.mapper.VisitorIdentityMapper;
import beike.visitorsystem.visitor.mapper.VisitorUserMapper;
import beike.visitorsystem.visitor.model.VisitorIdentity;
import beike.visitorsystem.visitor.model.VisitorUser;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
@Service
public class VisitorServiceImpl implements VisitorService{
    @Autowired
    private VisitorUserMapper visitorUserMapper;
    @Autowired
    private VisitorIdentityMapper visitorIdentityMapper;

    @Override
    public List<VisitorUser> getAllVisitorUser(int page,int number) {


        return visitorUserMapper.getAllVisitorUser((page - 1)*number, number);
    }
    @Override
    public List<VisitorUser> getAllNoBannedVisitorUser(int page,int number) {


        return visitorUserMapper.getAllNoBannedVisitorUser((page - 1)*number, number);
    }
    @Override
    public List<VisitorUser> getAllBannedVisitorUser(int page,int number){
        return visitorUserMapper.getAllBannedVisitorUser((page - 1)*number, number);
    }
    @Override
    public boolean defriendVisitorUser(BigInteger VisitorUserId){
        VisitorUser visitorUser=visitorUserMapper.getVisitorUserById(VisitorUserId);
        if (visitorUser==null)
            return false;
            else{
                visitorUserMapper.defriendVisitorUser(VisitorUserId);
            return true;
        }

    }
    @Override
    public boolean refriendVisitorUser(BigInteger VisitorUserId){
        VisitorUser visitorUser=visitorUserMapper.getVisitorUserById(VisitorUserId);
        if (visitorUser==null)
            return false;
        else{
            visitorUserMapper.refriendVisitorUser(VisitorUserId);
            return true;
        }
    }
    @Override
    public List<VisitorIdentity> getAllVisitorIdentity(int page,int number){
        return visitorIdentityMapper.getAllVisitorIdentity((page - 1)*number, number);
    }
    @Override
    public List<VisitorIdentity> getAllNoBannedVisitorIdentity(int page,int number){
        return visitorIdentityMapper.getAllNoBannedVisitorIdentity((page - 1)*number, number);
    }
    @Override
    public List<VisitorIdentity> getAllBannedVisitorIdentity(int page,int number){
        return visitorIdentityMapper.getAllBannedVisitorIdentity((page - 1)*number, number);
    }
    @Override
    public boolean defriendVisitorIdentity(BigInteger VisitorIdentityId){
        VisitorIdentity visitorIdentity=visitorIdentityMapper.getVisitorIdentityById(VisitorIdentityId);
        if(visitorIdentity==null)
            return false;
        else{
            visitorIdentityMapper.defriendVisitorIdentity(VisitorIdentityId);
            return true;
        }
    }
    @Override
    public boolean refriendVisitorIdentity(BigInteger VisitorIdentityId){
        VisitorIdentity visitorIdentity=visitorIdentityMapper.getVisitorIdentityById(VisitorIdentityId);
        if(visitorIdentity==null)
            return false;
        else{
            visitorIdentityMapper.refriendVisitorIdentity(VisitorIdentityId);
            return true;
        }
    }
    @Override
    public VisitorUser getVisitorDetailsById(BigInteger VisitorUserId) {
    	return visitorUserMapper.getVisitorDetailsById(VisitorUserId);
    }
    @Override
    public VisitorUser getVisitorReservationById(BigInteger VisitorUserId) {
    	return visitorUserMapper.getVisitorReservationById(VisitorUserId);
    }
    @Override
    public List<VisitorUser> getAllVisitorUserAndIdentity(int page,int number){
    	return visitorUserMapper.getAllVisitorUserAndIdentity((page - 1)*number, number);
    }
    @Override
    public int getSumVisitorUser() {
    	return visitorUserMapper.getSumVisitorUser();
    }

    @Override
    public int getSumBannedVisitorUser()
    {
        return visitorUserMapper.getSumBannedVisitorUser();
    }
    @Override
    public int getSumNoBannedVisitorUser()
    {
        return visitorUserMapper.getSumNoBannedVisitorUser();
    }

    @Override
    public int getSumIdentity() {
    	return visitorIdentityMapper.getSumIdentity();
    }
    @Override
    public List<VisitorUser> getVisitorUserByIdentity(String identityCard,int page,int number) {
    	return visitorUserMapper.getVisitorUserByIdentity(identityCard,(page - 1)*number, number);
    }
    @Override
    public int getSumBannedIdentity()
    {
        return visitorIdentityMapper.getSumBannedIdentity();
    }
    @Override
    public int getSumNoBannedIdentity()
    {
        return visitorIdentityMapper.getSumNoBannedIdentity();
    }

    @Override
    public int getSumByIdentity(String identity,int isBanned)
    {
        return visitorIdentityMapper.getSumByIdentity(identity,isBanned);
    }

    @Override
    public int getSumByName(String name,int isBanned)
    {
        return visitorIdentityMapper.getSumByName(name,isBanned);
    }


    @Override
    public VisitorIdentity getVisitorIdentityById(BigInteger VisitorIdentityId) {
    	return visitorIdentityMapper.getVisitorIdentityById(VisitorIdentityId);
    }
    @Override
    public List<VisitorIdentity> getIdentityByCard(String identityCard,int isBanned,int page,int number) {
    	return visitorIdentityMapper.getIdentityByCard(identityCard,isBanned,(page - 1)*number, number);
    }

    @Override
    public List<VisitorIdentity> getIdentityByName(String name,int isBanned,int page,int number) {
        return visitorIdentityMapper.getIdentityByName(name,isBanned,(page - 1)*number, number);
    }
}
