package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.BaseException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    SetmealDishMapper setmealDishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Transactional
    @Override
    public void save(DishDTO dishDTO) {

        //清缓存
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);


        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.insert(dish);
        //获取id
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!=null && flavors.size()>0){
        flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            dishFlavorMapper.insertBatch(flavors);
        }
//        flavors.forEach(dishFlavor ->
//                dishFlavor.setDishId(id));
//        dishFlavorMapper.insertBatch(flavors);
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
        ids.forEach(id->cleanCache("dish_"+id));
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
        //清缓存
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);

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
    public List<Dish> queryByCategoryId(Long id) {
        Dish dish = new Dish();
        dish.setCategoryId(id);
        List<Dish> dishVOS = dishMapper.queryByCategoryId(dish);
        return dishVOS;
    }

    @Override
    public void updateDishStatus(int status,Long id) {
        Dish dish = Dish.builder().id(id).status(status).build();

        if (setmealDishMapper.queryConnect(dish)==0){
            dishMapper.update(dish);
        }else throw new BaseException(MessageConstant.DISH_ON_SALE2);


    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        String key="dish_"+dish.getCategoryId();
        //查询redis中是否存在菜品数据
        List<DishVO> list= (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (list!=null && list.size()>0){
            //存在就直接输出
            return list;
        }
        List<Dish> dishList = dishMapper.queryByCategoryId(dish);
        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }
        //如果不存在就存入redis
        redisTemplate.opsForValue().set(key,dishVOList);
        return dishVOList;
    }



    //
    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}

