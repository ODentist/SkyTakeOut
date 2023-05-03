package generator.mapper;

import com.sky.entity.Orders;

/**
* @author O.Dentist
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2023-04-28 15:03:00
* @Entity generator.domain.Orders
*/
public interface OrdersMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

}
