package org.utils.APIConnections;

import java.util.Map;

public class HttpRequestParams
{
	private final String method;
	private final String url;
	private final String contentType;
	private final Map<String, String> headers;
	private final int connectionTimeOut;
	private final int readTimeOut;
	private final String json;
	private final boolean canLogResponse;

	private HttpRequestParams(Builder builder)
	{
		this.method = builder.method;
		this.url = builder.url;
		this.contentType = builder.contentType;
		this.headers = builder.headers;
		this.connectionTimeOut = builder.connectionTimeOut;
		this.readTimeOut = builder.readTimeOut;
		this.json = builder.json;
		this.canLogResponse = builder.canLogResponse;
	}

	public String getMethod()
	{
		return method;
	}

	public String getUrl()
	{
		return url;
	}

	public String getContentType()
	{
		return contentType;
	}

	public Map<String, String> getHeaders()
	{
		return headers;
	}

	public int getConnectionTimeOut()
	{
		return connectionTimeOut;
	}

	public int getReadTimeOut()
	{
		return readTimeOut;
	}

	public String getJson()
	{
		return json;
	}

	public boolean canLogResponse()
	{
		return canLogResponse;
	}

	public static class Builder
	{
		private String method;
		private String url;
		private String contentType;
		private Map<String, String> headers;
		private int connectionTimeOut = 60000;
		private int readTimeOut = 60000;
		private String json;
		private boolean canLogResponse = true;

		public Builder setMethod(String method)
		{
			this.method = method;
			return this;
		}

		public Builder setUrl(String url)
		{
			this.url = url;
			return this;
		}

		public Builder setContentType(String contentType)
		{
			this.contentType = contentType;
			return this;
		}

		public Builder setHeaders(Map<String, String> headers)
		{
			this.headers = headers;
			return this;
		}

		public Builder setJson(String json)
		{
			this.json = json;
			return this;
		}

		public Builder setConnectionTimeOut(int connectionTimeOut)
		{
			this.connectionTimeOut = connectionTimeOut;
			return this;
		}

		public Builder setReadTimeOut(int readTimeOut)
		{
			this.readTimeOut = readTimeOut;
			return this;
		}

		public Builder canLogResponse(boolean canLogResponse)
		{
			this.canLogResponse = canLogResponse;
			return this;
		}

		public HttpRequestParams build()
		{
			return new HttpRequestParams(this);
		}

	}
}