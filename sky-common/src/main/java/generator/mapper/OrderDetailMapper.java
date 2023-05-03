package generator.mapper;

import com.sky.entity.OrderDetail;

/**
* @author O.Dentist
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2023-04-28 15:38:18
* @Entity generator.domain.OrderDetail
*/
public interface OrderDetailMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

}
