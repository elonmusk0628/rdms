package com.ruoyi.common.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import com.ruoyi.common.utils.http.HttpHelper;
import com.ruoyi.common.constant.Constants;

/**
 * 构建可重复读取inputStream的request
 * 
 * @author ruoyi
 */
public class RepeatedlyRequestWrapper extends HttpServletRequestWrapper
{
    // 将request数据缓存到这个数组里面
    private final byte[] body;

    /**
     * 构建可重复读取inputStream的request
     * 如果使用原生的 HttpServletRequest ，
     * 只能读取一次，如果想要二次读取就会报错。 因此需要能够重复读取 InputStream 的方法。
     * request的inputStream只能被读取一次，
     * 多次读取将报错，那么如何才能重复读取呢？答案之一是：增加缓冲，记录已读取的内容。
     * @param request 客户端Http请求
     * @param response Http响应
     * @throws IOException 读写异常
     */
    public RepeatedlyRequestWrapper(HttpServletRequest request, ServletResponse response) throws IOException
    {
        super(request);
        request.setCharacterEncoding(Constants.UTF8);
        response.setCharacterEncoding(Constants.UTF8);

        body = HttpHelper.getBodyString(request).getBytes(Constants.UTF8);
    }

    /**
     *
     * @return BufferedReader结果
     * @throws IOException 读写异常
     */
    @Override
    public BufferedReader getReader() throws IOException
    {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 取得读写inputStream
     * @return 读写inputStream结果
     * @throws IOException 读写异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream()
        {
            @Override
            public int read() throws IOException
            {
                return bais.read();
            }

            @Override
            public int available() throws IOException
            {
                return body.length;
            }

            @Override
            public boolean isFinished()
            {
                return false;
            }

            @Override
            public boolean isReady()
            {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener)
            {

            }
        };
    }
}
