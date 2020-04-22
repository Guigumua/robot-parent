package com.github.guigumua.robot.starter.test.bean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.guigumua.robot.starter.test.bean.entity.Setu;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author vicente
 * @since 2020-04-21
 */
public interface SetuService extends IService<Setu> {
	Setu randomSetu();
}
