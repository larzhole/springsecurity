package com.example.demo.utilities.web;

import com.example.demo.utilities.annotations.DTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Collection;

@ControllerAdvice
public class DtoMapperResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoMapperResponseBodyAdvice(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && returnType.hasMethodAnnotation(DTO.class);
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer,
                                           MediaType mediaType,
                                           MethodParameter returnType,
                                           ServerHttpRequest serverHttpRequest,
                                           ServerHttpResponse serverHttpResponse) {

        Assert.state(returnType.hasMethodAnnotation(DTO.class), "No DTO annotation");
        final DTO annotation = returnType.getMethodAnnotation(DTO.class);
        final Class<?> dtoType = annotation.value();
        final Object value = bodyContainer.getValue();
        final Object returnValue;

        if (value instanceof Page) {
            returnValue = ((Page<?>) value).map(it -> modelMapper.map(it, dtoType));
        } else if (value instanceof Collection) {
            returnValue = ((Collection<?>) value).stream().map(it -> modelMapper.map(it, dtoType));
        } else {
            returnValue = modelMapper.map(value, dtoType);
        }

        bodyContainer.setValue(returnValue);
    }
}
