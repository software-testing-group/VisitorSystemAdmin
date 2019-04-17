package beike.visitorsystem.visitor.service;

import beike.visitorsystem.visitor.model.VisitorIdentity;
import beike.visitorsystem.visitor.model.VisitorUser;

import java.math.BigInteger;
import java.util.List;



public interface VisitorService {
    public List<VisitorUser> getAllVisitorUser(int page,int number);
    public List<VisitorUser> getAllNoBannedVisitorUser(int page,int number);
    public List<VisitorUser> getAllBannedVisitorUser(int page,int number);
    public boolean defriendVisitorUser(BigInteger VisitorUserId);
    public boolean refriendVisitorUser(BigInteger VisitorUserId);
    public List<VisitorIdentity> getAllVisitorIdentity(int count,int number);
    public List<VisitorIdentity> getAllNoBannedVisitorIdentity(int page,int number);
    public List<VisitorIdentity> getAllBannedVisitorIdentity(int page,int number);
    public boolean defriendVisitorIdentity(BigInteger VisitorIdentityId);
    public boolean refriendVisitorIdentity(BigInteger VisitorIdentityId);
    public VisitorUser getVisitorDetailsById(BigInteger VisitorUserId);
    public VisitorUser getVisitorReservationById(BigInteger VisitorUserId);
    public List<VisitorUser> getAllVisitorUserAndIdentity(int page,int number);
    public int getSumVisitorUser();
    public int getSumBannedVisitorUser();
    public int getSumNoBannedVisitorUser();
    public int getSumIdentity();
    public int getSumBannedIdentity();
    public int getSumNoBannedIdentity();

    public int getSumByIdentity(String identity,int isBanned);
    public int getSumByName(String name,int isBanned);


    public List<VisitorUser> getVisitorUserByIdentity(String identityCard,int page,int number);
    public VisitorIdentity getVisitorIdentityById(BigInteger VisitorIdentityId);
    public List<VisitorIdentity> getIdentityByCard(String identityCard,int isBanned,int page,int number);
    public List<VisitorIdentity> getIdentityByName(String name,int isBanned,int page,int number);
}
