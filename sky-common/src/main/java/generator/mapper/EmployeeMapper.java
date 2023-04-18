package generator.mapper;


import com.sky.entity.Employee;

/**
* @author O.Dentist
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2023-04-17 21:31:52
* @Entity generator.domain.Employee
*/
public interface EmployeeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

}
