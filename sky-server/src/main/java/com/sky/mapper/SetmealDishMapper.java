package com.sky.mapper;

import com.sky.entity.Dish;
import com.sky.entity.SetmealDish;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<SetmealDish> getSetmealIdsByDishIds(List<Long> ids);

    int queryConnect(Dish dish);
}
