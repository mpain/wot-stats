package ru.mpain.wot.common;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import java.io.IOException;

/**
 * User: sergey
 * Date: 2/16/12
 * Time: 5:40 PM
 */
public final class JsonHelper {
	public static final String DEFAULT_ENCODING = "utf-8";

	private JsonHelper() {
	}

	public static <T> String safeJson(T data) {
		try {
			return json(data);
		} catch (IOException ignored) {
		}

		return "{ errorMessage: 'Serialization failed' }";
	}
	
	public static <T> String json(T data) throws JsonGenerationException, JsonMappingException, IOException {
		return json(data, DEFAULT_ENCODING);
	}

	public static <T> String json(T data, String encoding) throws JsonGenerationException, JsonMappingException, IOException {
		if (data == null) {
			return "{ errorMessage: 'Object is null' }";
		}

		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector secondary = new JaxbAnnotationIntrospector();
		mapper.getSerializationConfig()
				.withAnnotationIntrospector(new AnnotationIntrospector.Pair(primary, secondary))
				.with(SerializationConfig.Feature.INDENT_OUTPUT)
				.withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		return mapper.writeValueAsString(data);
	}

	public static <T> T fromJson(Class<T> clazz, String data) throws JsonGenerationException, JsonMappingException, IOException {
		return fromJson(clazz, data, DEFAULT_ENCODING, false);
	}

	public static <T> T fromJson(Class<T> clazz, String data, String encoding) throws JsonGenerationException, JsonMappingException, IOException {
		return fromJson(clazz, data, encoding, false);
	}

	public static <T> T fromJson(Class<T> clazz, String data, String encoding, boolean primaryJaxb) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector secondary = new JaxbAnnotationIntrospector();

		AnnotationIntrospector introspector = (primaryJaxb) ? secondary : new AnnotationIntrospector.Pair(primary, secondary);
		mapper.getDeserializationConfig().withAnnotationIntrospector(introspector);

		return mapper.readValue(data, clazz);
	}
}
