package com.banque.action.common;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 * Converter for BigDecimal objects.  This differs from the built in converter in that this converter
 * will not attempt to convert null or the empty string into a BigDecimal.  new BigDecimal(null) or
 * new BigDecimal("") throws NumberFormatException in the built in converter, which causes the param
 * setting process to exit for BigDecimal fields.  To avoid this, this converter will return null in
 * that case rather than attempting the conversion.
 * @author ddasgupta
 *
 */
public class BigDecimalConverter extends StrutsTypeConverter {

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (StringUtils.isBlank(values[0])) {
            return null;
        }
        return new BigDecimal(values[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public String convertToString(Map context, Object o) {
        String retVal = "";
        if (o instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) o;
            retVal = bd.toString();
        }
        return retVal;
    }

}
