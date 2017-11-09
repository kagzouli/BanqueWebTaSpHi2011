package com.banque.java.services;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.urlrewriter.RewriteRuleApplicability;
import org.apache.tapestry5.urlrewriter.SimpleRequestWrapper;
import org.apache.tapestry5.urlrewriter.URLRewriteContext;
import org.apache.tapestry5.urlrewriter.URLRewriterRule;

public class WebModule {
	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {

		configuration.add("tapestry.supported-locales", "en,fr");
	}
	
	public static void contributeURLRewriter(OrderedConfiguration<URLRewriterRule> configuration)
	{
	    
	    URLRewriterRule rule1 = new URLRewriterRule()
	    {

	        public Request process(Request request, URLRewriteContext context)
	        {
	            final String path = request.getPath();
	            if (path.startsWith("/banque/crediter.html"))
	            {
	            	String vl = request.getParameter("vl");
	            	
	            	String newPath = "/banque/crediter";
	            	if (!StringUtils.isEmpty(vl)){
	            		  if (vl.equals("en") || (vl.equals("fr"))){
	            			  newPath = "/" + vl + newPath;
	            		  }
	            	}
	            	
	                request = new SimpleRequestWrapper(request, newPath);
	            }
	            
	            return request;
	            
	        }

	        public RewriteRuleApplicability applicability()
	        {
	            return RewriteRuleApplicability.BOTH;
	        } 
	        
	    };
	 
	    
	    configuration.add("rule1", rule1);		    
	}

}
