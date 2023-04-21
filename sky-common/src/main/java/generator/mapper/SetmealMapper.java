package generator.mapper;


import com.sky.entity.Setmeal;

/**
* @author O.Dentist
* @description 针对表【setmeal(套餐)】的数据库操作Mapper
* @createDate 2023-04-20 16:05:17
* @Entity generator.domain.Setmeal
*/
public interface SetmealMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Setmeal record);

    int insertSelective(Setmeal record);

    Setmeal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Setmeal record);

    int updateByPrimaryKey(Setmeal record);

}
