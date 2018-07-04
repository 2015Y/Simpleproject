package eureka.run.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class MyZuulFilter extends ZuulFilter {

	final Log log = LogFactory.getLog(MyZuulFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext rc = RequestContext.getCurrentContext();
		HttpServletRequest request = rc.getRequest();
		log.info("设置的ZuulFilter过滤器起作用了，拦截的请求路径为" + request.getRequestURL());
		return null;
	}

	@Override
	public String filterType() {
		return "MY";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
