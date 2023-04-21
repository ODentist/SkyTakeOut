package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.BaseException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    SetmealDishMapper setmealDishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Transactional
    @Override
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.insert(dish);
        //获取id
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(dishFlavor ->
                dishFlavor.setDishId(id));
        dishFlavorMapper.insertBatch(flavors);
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),
                dishPageQueryDTO.getPageSize());
        Page<DishVO>page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 批量删除菜品
     * @param ids
     */
    @Transactional
    @Override
    public void delete(List<Long> ids) {
        //是否起售，起售（status==1）不可删除
        for (Long id : ids) {
            Dish dish=dishMapper.getById(id);
            if (dish.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //要验证该菜品是否关联了套餐，如果关联了，就不能删除
            if (setmealDishMapper.getSetmealIdsByDishIds(ids) !=null
                && setmealDishMapper.getSetmealIdsByDishIds(ids).size()>0){
                //已关联
                throw new DeletionNotAllowedException(
                        MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
        dishMapper.delete(ids);
    }


    @Override
    public DishVO getByIdWithFlavor(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //修改dish表的信息
        dishMapper.update(dish);

        dishFlavorMapper.deleteById(dishDTO.getId());

        List<DishFlavor> flavors = dishDTO.getFlavors();
        //更改菜品口味对应的id
        if (flavors!=null&&flavors.size()>0){
            flavors.forEach(dishFlavor ->
                    dishFlavor.setDishId(dishDTO.getId()));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public List<DishVO> queryByCategoryId(Long id) {
        List<DishVO> dishVOS = dishMapper.queryByCategoryId(id);
        return dishVOS;
    }

    @Override
    public void updateDishStatus(int status,Long id) {
        Dish dish = Dish.builder().id(id).status(status).build();

        if (setmealDishMapper.queryConnect(dish)==0){
            dishMapper.update(dish);
        }else throw new BaseException(MessageConstant.DISH_ON_SALE2);


    }


}

