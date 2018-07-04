package eureka.run;

import javax.servlet.http.HttpServletRequest;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

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
		log.info("Request path:" + request.getRequestURL());
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
