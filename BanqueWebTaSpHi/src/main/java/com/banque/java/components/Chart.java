package com.banque.java.components;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.Response;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class Chart {
    @Inject
    private ComponentResources _resources;
    
    @Inject
    private TypeCoercer _coercer;

    /**list(array) of paired values(label & value): [String,Number,String,Number,...]*/
    @Parameter(required=true)
    private JFreeChart _chart;
    
    @Persist
    private JFreeChart _context;
    
    @Parameter(required=true)
    private int _width;

    @Parameter(required=true)
    private int _height;


    void beginRender(MarkupWriter writer)
    {
        _context=_chart;
        Object[] params = { new Integer(_width),
                            new Integer(_height) };
        
        //generate action link
        Link link = _resources.createEventLink("chart", params);
        Element img = writer.element("img", "src", link);
        _resources.renderInformalParameters(writer);
    }
    
    void afterRender(MarkupWriter writer)
    {
        writer.end();
    }

    public StreamResponse onChart(final int width, final int height/*, Object ...rest*/){
            
        return new StreamResponse(){
            public String getContentType(){
                return "image/jpeg";
            }
            public InputStream getStream() throws IOException {
                BufferedImage image  = _context.createBufferedImage(width, height);
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream() ;
                ChartUtilities.writeBufferedImageAsPNG(byteArray, image) ;
                return new ByteArrayInputStream(byteArray.toByteArray());
            }
            public void prepareResponse(Response response){}
        };
    }}