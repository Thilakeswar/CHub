package org.utils.APIConnections;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestHttpClient
{
	//TODO:Need to add all test cases for HTTP API Requests made using the HttpClient
	@Test
	public void testGetAPI() throws Exception
	{
		HttpRequestParams httpRequestParams = new HttpRequestParams.Builder()
			.setUrl("https://reqres.in/api/users?page=2")
			.setMethod(HttpConstants.METHOD_GET)
			.canLogResponse(true)
			.build();
		HttpResponse httpResponse = HttpClient.invoke(httpRequestParams);
		Assertions.assertEquals(true, httpResponse.isSuccess());
	}

	@Test
	public void testPostAPI() throws Exception
	{
		Map<String, Object> requsetMap = new HashMap<>();
		requsetMap.put("name", "morpheus");
		requsetMap.put("job", "leader");

		HttpRequestParams httpRequestParams = new HttpRequestParams.Builder()
			.setUrl("https://reqres.in/api/users")
			.setMethod(HttpConstants.METHOD_POST)
			.canLogResponse(true)
			.setJson(requsetMap.toString())
			.build();
		HttpResponse httpResponse = HttpClient.invoke(httpRequestParams);
		Assertions.assertEquals(true, httpResponse.isSuccess());
	}

	@Test
	public void testPutAPI() throws Exception
	{
		Map<String, Object> requsetMap = new HashMap<>();
		requsetMap.put("name", "morpheus");
		requsetMap.put("job", "zion resident");

		HttpRequestParams httpRequestParams = new HttpRequestParams.Builder()
			.setUrl("https://reqres.in/api/users/2")
			.setMethod(HttpConstants.METHOD_PUT)
			.canLogResponse(true)
			.setJson(requsetMap.toString())
			.build();
		HttpResponse httpResponse = HttpClient.invoke(httpRequestParams);
		Assertions.assertEquals(true, httpResponse.isSuccess());
	}

	@Test
	public void testDeleteAPI() throws Exception
	{
		HttpRequestParams httpRequestParams = new HttpRequestParams.Builder()
			.setUrl("https://reqres.in/api/users/2")
			.setMethod(HttpConstants.METHOD_DELETE)
			.canLogResponse(true)
			.build();
		HttpResponse httpResponse = HttpClient.invoke(httpRequestParams);
		Assertions.assertEquals(true, httpResponse.isSuccess());
	}

}
