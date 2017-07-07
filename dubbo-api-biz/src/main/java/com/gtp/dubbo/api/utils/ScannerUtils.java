package com.gtp.dubbo.api.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

/**
 * 扫描工具
 * 
 * @author gaotingping@cyberzone.cn
 *
 */
public class ScannerUtils{

	private static final String RESOURCE_PATTERN = "/**/*.class";

	private static final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	public static List<Class<?>> scan(List<String> packages,Class<? extends Annotation> filter) throws IOException,ClassNotFoundException {
		
		if(CollectionUtils.isEmpty(packages) || filter == null){
			return null;
		}
		
	    TypeFilter typeFilter = new AnnotationTypeFilter(filter, false);

	    List<Class<?>> result=new ArrayList<>();
	    
		for (String pkg : packages) {
				String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
				Resource[] resources = resourcePatternResolver.getResources(pattern);
				MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader reader = readerFactory.getMetadataReader(resource);
						if (typeFilter.match(reader, readerFactory)) {
							result.add(Class.forName(reader.getClassMetadata().getClassName()));
						}
					}
				}
		}
		
		return result;
	}
}