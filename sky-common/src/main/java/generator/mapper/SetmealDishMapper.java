package generator.mapper;


import com.sky.entity.SetmealDish;

/**
* @author O.Dentist
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
* @createDate 2023-04-20 19:34:17
* @Entity generator.domain.SetmealDish
*/
public interface SetmealDishMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SetmealDish record);

    int insertSelective(SetmealDish record);

    SetmealDish selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SetmealDish record);

    int updateByPrimaryKey(SetmealDish record);

}
