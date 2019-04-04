package com.dreamer.netty.firstexample;

import java.net.URI;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * Created by Administrator on 2019\4\1 0001.
 */
public class TestHttpServerHandle extends SimpleChannelInboundHandler<HttpObject>{
    @Override
    protected void channelRead0(ChannelHandlerContext context, HttpObject httpObject) throws Exception {
    	if(httpObject instanceof HttpRequest) {
            System.out.println("执行了channelRead0");
            HttpRequest httpRequest = (HttpRequest)httpObject;
            System.out.println("请求方法名:"+httpRequest.method().name());
            
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求favicon.ico");
                return;
            }
            
	        ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
	        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
	                HttpResponseStatus.OK,content);
	        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
	        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
	
	        context.writeAndFlush(response);
    	}
    }
}
