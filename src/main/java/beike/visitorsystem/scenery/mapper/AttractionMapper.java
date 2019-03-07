package beike.visitorsystem.scenery.mapper;



import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import beike.visitorsystem.scenery.model.Attraction;

@Repository
public interface AttractionMapper {
	public ArrayList getAllAttractionNumber();
	public boolean updateVoiceIntroductionById(@Param("attractionId") BigInteger attractionId,@Param("voiceIntroduction") String voiceIntroduction);
	public boolean updateTextIntroductionById(@Param("attractionId") BigInteger attractionId,@Param("textIntroduction") String textIntroduction);
	public boolean updateTitleById(@Param("attractionId") BigInteger attractionId,@Param("title") String title);
	public boolean updateAttractionNumberById(@Param("attractionId") BigInteger attractionId,@Param("attractionNumber") String attractionNumber);

	public Attraction getAttractionById(BigInteger attractionId);

	// ------
	// NO.1
	public boolean addAttraction(Attraction attraction);
    // NO.2
	public boolean deleteAttractionById(BigInteger attractionId);
	// NO.3
	public boolean updateAttraction(@Param("attraction")Attraction attraction);
    // NO.4
	public List<Attraction> getAllAttraction(@Param("count")int count,@Param("number")int number);
    // NO.5
	public Attraction getAttractionAndImagesById(@Param("id")BigInteger attractionId);
	public Attraction getAttractionAndAudiosById(@Param("id")BigInteger attractionId);
	// NO.6
	public Attraction getAttractionSummaryById(@Param("id")BigInteger attractionId);

	int getSumOfAttractions();
    // ------

	public Attraction getAttractionByTitle(String title);
	public int getSumAttraction();
	public List<Attraction> getAllAttractionTitle();
	public List<Attraction> getAttractionByName(String name);
	public List<Attraction> getAttractionsByRouteId(BigInteger route_id);
	public List<Attraction> getAttractions();
	public Boolean updateAttractionReadCountById(@Param("id") BigInteger attraction_id,@Param("read_count") int read_count);
	public Attraction getAimAttractionById(BigInteger id);
	public List<Attraction> getOtherAttractions(BigInteger id);
}
