package beike.visitorsystem.scenery.mapper;


import beike.visitorsystem.scenery.model.AttractionAudio;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface AttractionAudioMapper {
    //OK
    public boolean deleteAudioById(BigInteger audioId);
    //OK
    public boolean updateAudioById(@Param("audioId")BigInteger audioId,@Param("type")String type, @Param("audioUrl") String audioUrl);
    //OK
    public boolean addAudio(AttractionAudio attractionAudio);


    public List<AttractionAudio> getAttractionAudioesByAttractionId(BigInteger attraction_id);

}
