package com.ck.rt1.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import com.ck.rt1.model.Stylesheet;
import com.ck.rt1.model.Template;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}
	
    public Converter<Stylesheet, String> getStylesheetToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.ck.rt1.model.Stylesheet, java.lang.String>() {
            public String convert(Stylesheet stylesheet) {
//                return new StringBuilder().append(stylesheet.getName()).append(' ').append(stylesheet.getContent()).toString();
                return stylesheet.getName();
            }
        };
    }
    
    public Converter<Template, String> getTemplateToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.ck.rt1.model.Template, java.lang.String>() {
            public String convert(Template template) {
//                return new StringBuilder().append(template.getName()).append(' ').append(template.getContent()).toString();
                return template.getName();
            }
        };
    }
    
}
