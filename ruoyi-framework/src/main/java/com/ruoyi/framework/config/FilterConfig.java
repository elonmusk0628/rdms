package com.ruoyi.framework.config;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ruoyi.common.filter.RepeatableFilter;
import com.ruoyi.common.filter.XssFilter;
import com.ruoyi.common.utils.StringUtils;

/**
 * Filter配置 过滤器配置
 *
 * @author ruoyi
 */
@Configuration
public class FilterConfig
{
    // # 排除链接(多个用逗号分隔)
    @Value("${xss.excludes}")
    private String excludes;

    // # 匹配链接 也就是这个里面的，要防止xss攻击
    @Value("${xss.urlPatterns}")
    private String urlPatterns;

    /**
     * 注册XSS过滤器
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    @ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
    public FilterRegistrationBean xssFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", excludes);
        registration.setInitParameters(initParameters);
        return registration;
    }

    /**
     * 注册XSS过滤器
     * 通过其两个属性name以及havingValue来实现的，其中name用来从application.properties中读取某个属性值。
     * 如果该值为空，则返回false;
     * 如果值不为空，则将该值与havingValue指定的值进行比较，如果一样则返回true;否则返回false。
     * 如果返回值为false，则该configuration不生效；为true则生效。
     * @return 返回结果
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean someFilterRegistration()
    {
        // 创建过滤器
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

}
