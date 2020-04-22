package com.github.guigumua.robot.starter.test.bean.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.guigumua.robot.starter.test.bean.entity.Setu;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author vicente
 * @since 2020-04-21
 */
public interface SetuMapper extends BaseMapper<Setu> {
	@Select("SELECT *\r\n"
			+ "FROM `setu` AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `setu`)-(SELECT MIN(id) FROM `setu`))+(SELECT MIN(id) FROM `setu`)) AS id) AS t2\r\n"
			+ "WHERE t1.id >= t2.id\r\n" + "ORDER BY t1.id LIMIT 1;")
	Setu randomSetu();
}
