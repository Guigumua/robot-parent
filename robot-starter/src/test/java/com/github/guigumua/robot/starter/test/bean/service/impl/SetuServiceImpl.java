package com.github.guigumua.robot.starter.test.bean.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.guigumua.robot.starter.test.bean.entity.Setu;
import com.github.guigumua.robot.starter.test.bean.mapper.SetuMapper;
import com.github.guigumua.robot.starter.test.bean.service.SetuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author vicente
 * @since 2020-04-21
 */
@Service
public class SetuServiceImpl extends ServiceImpl<SetuMapper, Setu> implements SetuService {
	@Autowired
	private SetuMapper setuMapper;

	@Override
	public Setu randomSetu() {
		return setuMapper.randomSetu();
	}

}
