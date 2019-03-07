package beike.visitorsystem.scenery.mapper;

import beike.visitorsystem.scenery.model.RouteBindingAttraction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface RouteBindingAttractionMapper {
    //NO.1
    boolean addRouteBindingAttraction(@Param("RBA") RouteBindingAttraction routeBindingAttraction);
    // Ӧ���ò���
    // boolean updateRouteBindingAttraction(RouteBindingAttraction routeBindingAttraction);
    // ��Ϊɾ��·���е�һ�������ɾ��·���е����о���
    //NO.2
    boolean deleteRouteBindingAttractionByAttractionId(@Param("routeId") BigInteger routeId,@Param("attractionId")BigInteger attractionId);
    //NO.3
    boolean deleteRouteBindingAttractionsByRouteId(@Param("routeId")BigInteger routeId);
    // ����binding��������ʱ��Ҫ��֤����˳�򣬲�������ʾ��·���еľ�������
    // ������������ǰ���������������������
    // �Ƿ���Ҫ��һ������order�Ľӿ�  Yes!
    //NO.4
    boolean updateOrderOfRouteBindingAttractionById(@Param("id") BigInteger id,@Param("order") int order);

    /*
    ----��ȹ���ÿͶ������Ա�������
    ·��ģ�鲻���޸ľ������Ϣ���������ԡ���ӡ�ɾ����Ƶ�ļ���ͼƬ�ļ�
    ·��ģ��ֻ�ܲ鿴�������Ϣ����ֻ�ܶ�
    ·��ģ��Ψһ�ܹ��޸ĵľ�ֻ��binding�����е���Ϣ����order�� �Ծ�����·���е�˳������޸�
    ·��ģ���ܶ�binding�����������ɾ����
    */
}
