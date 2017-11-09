package com.banque.java.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeforeRenderBody;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * A simple mixin for attaching a javascript confirmation box to the onclick
 * event of any component that implements ClientElement.
 *
 * @author <a href="mailto:chris@thegodcode.net">Chris Lewis</a> Apr 18, 2008
 */
@IncludeJavaScriptLibrary("confirm.js")
public class Confirm {

	/** Message **/
	@Inject
	private Messages messages;
		
    @Parameter(value = "confirmlabel.value", defaultPrefix = BindingConstants.LITERAL)
    private String message;

    @Inject
    private RenderSupport renderSupport;

    @InjectContainer
    private ClientElement element;
    
    @AfterRender
    public void afterRender() {
            renderSupport.addScript(String.format("new Confirm('%s', '%s');",
                    element.getClientId(), messages.get(message)));
    }

}

