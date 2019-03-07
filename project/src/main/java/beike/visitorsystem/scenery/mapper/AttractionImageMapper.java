package beike.visitorsystem.scenery.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import beike.visitorsystem.scenery.model.AttractionImage;

@Repository
public interface AttractionImageMapper {
	//OK
	public boolean deleteImageById(BigInteger imageId);
	//OK
	public boolean updateImageById(@Param("imageId")BigInteger imageId,@Param("imageUrl") String imageUrl);
    //OK
	public boolean addImage(AttractionImage attractionImage);
	//---不需要这个
	public List<AttractionImage> getImageByAttractionId(BigInteger attractionId);

	
}