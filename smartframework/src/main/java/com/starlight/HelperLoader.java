package com.starlight;

import com.starlight.annotation.Controller;
import com.starlight.helper.BeanHelper;
import com.starlight.helper.ClassHelper;
import com.starlight.helper.ControllerHelper;
import com.starlight.helper.IocHelper;
import com.starlight.utils.ClassUtil;

/**
 * Created by huangxinguang on 2017/6/30 下午4:29.
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
